package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.core.utilities.Predicate;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class TrackedQueryManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Predicate<Map<QueryParams, TrackedQuery>> HAS_ACTIVE_DEFAULT_PREDICATE = new Predicate<Map<QueryParams, TrackedQuery>>() {
        public boolean evaluate(Map<QueryParams, TrackedQuery> map) {
            TrackedQuery trackedQuery = (TrackedQuery) map.get(QueryParams.DEFAULT_PARAMS);
            return trackedQuery != null && trackedQuery.active;
        }
    };
    private static final Predicate<Map<QueryParams, TrackedQuery>> HAS_DEFAULT_COMPLETE_PREDICATE = new Predicate<Map<QueryParams, TrackedQuery>>() {
        public boolean evaluate(Map<QueryParams, TrackedQuery> map) {
            TrackedQuery trackedQuery = (TrackedQuery) map.get(QueryParams.DEFAULT_PARAMS);
            return trackedQuery != null && trackedQuery.complete;
        }
    };
    private static final Predicate<TrackedQuery> IS_QUERY_PRUNABLE_PREDICATE = new Predicate<TrackedQuery>() {
        public boolean evaluate(TrackedQuery trackedQuery) {
            return trackedQuery.active ^ 1;
        }
    };
    private static final Predicate<TrackedQuery> IS_QUERY_UNPRUNABLE_PREDICATE = new Predicate<TrackedQuery>() {
        public boolean evaluate(TrackedQuery trackedQuery) {
            return TrackedQueryManager.IS_QUERY_PRUNABLE_PREDICATE.evaluate(trackedQuery) ^ 1;
        }
    };
    private final Clock clock;
    private long currentQueryId = 0;
    private final LogWrapper logger;
    private final PersistenceStorageEngine storageLayer;
    private ImmutableTree<Map<QueryParams, TrackedQuery>> trackedQueryTree;

    private static void assertValidTrackedQuery(QuerySpec querySpec) {
        boolean z = !querySpec.loadsAllData() || querySpec.isDefault();
        Utilities.hardAssert(z, "Can't have tracked non-default query that loads all data");
    }

    private static QuerySpec normalizeQuery(QuerySpec querySpec) {
        return querySpec.loadsAllData() ? QuerySpec.defaultQueryAtPath(querySpec.getPath()) : querySpec;
    }

    public TrackedQueryManager(PersistenceStorageEngine persistenceStorageEngine, LogWrapper logWrapper, Clock clock) {
        this.storageLayer = persistenceStorageEngine;
        this.logger = logWrapper;
        this.clock = clock;
        this.trackedQueryTree = new ImmutableTree(null);
        resetPreviouslyActiveTrackedQueries();
        for (TrackedQuery trackedQuery : this.storageLayer.loadTrackedQueries()) {
            this.currentQueryId = Math.max(trackedQuery.id + 1, this.currentQueryId);
            cacheTrackedQuery(trackedQuery);
        }
    }

    private void resetPreviouslyActiveTrackedQueries() {
        try {
            this.storageLayer.beginTransaction();
            this.storageLayer.resetPreviouslyActiveTrackedQueries(this.clock.millis());
            this.storageLayer.setTransactionSuccessful();
        } finally {
            this.storageLayer.endTransaction();
        }
    }

    public TrackedQuery findTrackedQuery(QuerySpec querySpec) {
        querySpec = normalizeQuery(querySpec);
        Map map = (Map) this.trackedQueryTree.get(querySpec.getPath());
        return map != null ? (TrackedQuery) map.get(querySpec.getParams()) : null;
    }

    public void removeTrackedQuery(QuerySpec querySpec) {
        querySpec = normalizeQuery(querySpec);
        this.storageLayer.deleteTrackedQuery(findTrackedQuery(querySpec).id);
        Map map = (Map) this.trackedQueryTree.get(querySpec.getPath());
        map.remove(querySpec.getParams());
        if (map.isEmpty()) {
            this.trackedQueryTree = this.trackedQueryTree.remove(querySpec.getPath());
        }
    }

    public void setQueryActive(QuerySpec querySpec) {
        setQueryActiveFlag(querySpec, true);
    }

    public void setQueryInactive(QuerySpec querySpec) {
        setQueryActiveFlag(querySpec, false);
    }

    private void setQueryActiveFlag(QuerySpec querySpec, boolean z) {
        QuerySpec normalizeQuery = normalizeQuery(querySpec);
        TrackedQuery findTrackedQuery = findTrackedQuery(normalizeQuery);
        long millis = this.clock.millis();
        if (findTrackedQuery != null) {
            findTrackedQuery = findTrackedQuery.updateLastUse(millis).setActiveState(z);
        } else {
            long j = this.currentQueryId;
            this.currentQueryId = 1 + j;
            TrackedQuery trackedQuery = new TrackedQuery(j, normalizeQuery, millis, false, z);
        }
        saveTrackedQuery(findTrackedQuery);
    }

    public void setQueryCompleteIfExists(QuerySpec querySpec) {
        TrackedQuery findTrackedQuery = findTrackedQuery(normalizeQuery(querySpec));
        if (findTrackedQuery != null && !findTrackedQuery.complete) {
            saveTrackedQuery(findTrackedQuery.setComplete());
        }
    }

    public void setQueriesComplete(Path path) {
        this.trackedQueryTree.subtree(path).foreach(new TreeVisitor<Map<QueryParams, TrackedQuery>, Void>() {
            public Void onNodeValue(Path path, Map<QueryParams, TrackedQuery> map, Void voidR) {
                for (Entry value : map.entrySet()) {
                    TrackedQuery trackedQuery = (TrackedQuery) value.getValue();
                    if (!trackedQuery.complete) {
                        TrackedQueryManager.this.saveTrackedQuery(trackedQuery.setComplete());
                    }
                }
                return null;
            }
        });
    }

    public boolean isQueryComplete(QuerySpec querySpec) {
        boolean z = true;
        if (includedInDefaultCompleteQuery(querySpec.getPath())) {
            return true;
        }
        if (querySpec.loadsAllData()) {
            return false;
        }
        Map map = (Map) this.trackedQueryTree.get(querySpec.getPath());
        if (!(map != null && map.containsKey(querySpec.getParams()) && ((TrackedQuery) map.get(querySpec.getParams())).complete)) {
            z = false;
        }
        return z;
    }

    public PruneForest pruneOldQueries(CachePolicy cachePolicy) {
        int i;
        List queriesMatching = getQueriesMatching(IS_QUERY_PRUNABLE_PREDICATE);
        long calculateCountToPrune = calculateCountToPrune(cachePolicy, (long) queriesMatching.size());
        PruneForest pruneForest = new PruneForest();
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Pruning old queries.  Prunable: ");
            stringBuilder.append(queriesMatching.size());
            stringBuilder.append(" Count to prune: ");
            stringBuilder.append(calculateCountToPrune);
            logWrapper.debug(stringBuilder.toString(), new Object[0]);
        }
        Collections.sort(queriesMatching, new Comparator<TrackedQuery>() {
            public int compare(TrackedQuery trackedQuery, TrackedQuery trackedQuery2) {
                return Utilities.compareLongs(trackedQuery.lastUse, trackedQuery2.lastUse);
            }
        });
        PruneForest pruneForest2 = pruneForest;
        for (i = 0; ((long) i) < calculateCountToPrune; i++) {
            TrackedQuery trackedQuery = (TrackedQuery) queriesMatching.get(i);
            pruneForest2 = pruneForest2.prune(trackedQuery.querySpec.getPath());
            removeTrackedQuery(trackedQuery.querySpec);
        }
        for (i = (int) calculateCountToPrune; i < queriesMatching.size(); i++) {
            pruneForest2 = pruneForest2.keep(((TrackedQuery) queriesMatching.get(i)).querySpec.getPath());
        }
        List<TrackedQuery> queriesMatching2 = getQueriesMatching(IS_QUERY_UNPRUNABLE_PREDICATE);
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper2 = this.logger;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unprunable queries: ");
            stringBuilder2.append(queriesMatching2.size());
            logWrapper2.debug(stringBuilder2.toString(), new Object[0]);
        }
        for (TrackedQuery trackedQuery2 : queriesMatching2) {
            pruneForest2 = pruneForest2.keep(trackedQuery2.querySpec.getPath());
        }
        return pruneForest2;
    }

    private static long calculateCountToPrune(CachePolicy cachePolicy, long j) {
        return j - Math.min((long) Math.floor((double) (((float) j) * (1.0f - cachePolicy.getPercentOfQueriesToPruneAtOnce()))), cachePolicy.getMaxNumberOfQueriesToKeep());
    }

    public Set<ChildKey> getKnownCompleteChildren(Path path) {
        Set<ChildKey> hashSet = new HashSet();
        Set filteredQueryIdsAtPath = filteredQueryIdsAtPath(path);
        if (!filteredQueryIdsAtPath.isEmpty()) {
            hashSet.addAll(this.storageLayer.loadTrackedQueryKeys(filteredQueryIdsAtPath));
        }
        Iterator it = this.trackedQueryTree.subtree(path).getChildren().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            ChildKey childKey = (ChildKey) entry.getKey();
            ImmutableTree immutableTree = (ImmutableTree) entry.getValue();
            if (immutableTree.getValue() != null && HAS_DEFAULT_COMPLETE_PREDICATE.evaluate((Map) immutableTree.getValue())) {
                hashSet.add(childKey);
            }
        }
        return hashSet;
    }

    public void ensureCompleteTrackedQuery(Path path) {
        if (!includedInDefaultCompleteQuery(path)) {
            QuerySpec defaultQueryAtPath = QuerySpec.defaultQueryAtPath(path);
            TrackedQuery findTrackedQuery = findTrackedQuery(defaultQueryAtPath);
            if (findTrackedQuery == null) {
                long j = this.currentQueryId;
                this.currentQueryId = 1 + j;
                TrackedQuery trackedQuery = new TrackedQuery(j, defaultQueryAtPath, this.clock.millis(), true, false);
            } else {
                findTrackedQuery = findTrackedQuery.setComplete();
            }
            saveTrackedQuery(findTrackedQuery);
        }
    }

    public boolean hasActiveDefaultQuery(Path path) {
        return this.trackedQueryTree.rootMostValueMatching(path, HAS_ACTIVE_DEFAULT_PREDICATE) != null;
    }

    public long countOfPrunableQueries() {
        return (long) getQueriesMatching(IS_QUERY_PRUNABLE_PREDICATE).size();
    }

    void verifyCache() {
        List loadTrackedQueries = this.storageLayer.loadTrackedQueries();
        final List arrayList = new ArrayList();
        this.trackedQueryTree.foreach(new TreeVisitor<Map<QueryParams, TrackedQuery>, Void>() {
            public Void onNodeValue(Path path, Map<QueryParams, TrackedQuery> map, Void voidR) {
                for (TrackedQuery add : map.values()) {
                    arrayList.add(add);
                }
                return null;
            }
        });
        Collections.sort(arrayList, new Comparator<TrackedQuery>() {
            public int compare(TrackedQuery trackedQuery, TrackedQuery trackedQuery2) {
                return Utilities.compareLongs(trackedQuery.id, trackedQuery2.id);
            }
        });
        boolean equals = loadTrackedQueries.equals(arrayList);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tracked queries out of sync.  Tracked queries: ");
        stringBuilder.append(arrayList);
        stringBuilder.append(" Stored queries: ");
        stringBuilder.append(loadTrackedQueries);
        Utilities.hardAssert(equals, stringBuilder.toString());
    }

    private boolean includedInDefaultCompleteQuery(Path path) {
        return this.trackedQueryTree.findRootMostMatchingPath(path, HAS_DEFAULT_COMPLETE_PREDICATE) != null;
    }

    private Set<Long> filteredQueryIdsAtPath(Path path) {
        Set<Long> hashSet = new HashSet();
        Map map = (Map) this.trackedQueryTree.get(path);
        if (map != null) {
            for (TrackedQuery trackedQuery : map.values()) {
                if (!trackedQuery.querySpec.loadsAllData()) {
                    hashSet.add(Long.valueOf(trackedQuery.id));
                }
            }
        }
        return hashSet;
    }

    private void cacheTrackedQuery(TrackedQuery trackedQuery) {
        assertValidTrackedQuery(trackedQuery.querySpec);
        Map map = (Map) this.trackedQueryTree.get(trackedQuery.querySpec.getPath());
        if (map == null) {
            map = new HashMap();
            this.trackedQueryTree = this.trackedQueryTree.set(trackedQuery.querySpec.getPath(), map);
        }
        TrackedQuery trackedQuery2 = (TrackedQuery) map.get(trackedQuery.querySpec.getParams());
        boolean z = trackedQuery2 == null || trackedQuery2.id == trackedQuery.id;
        Utilities.hardAssert(z);
        map.put(trackedQuery.querySpec.getParams(), trackedQuery);
    }

    private void saveTrackedQuery(TrackedQuery trackedQuery) {
        cacheTrackedQuery(trackedQuery);
        this.storageLayer.saveTrackedQuery(trackedQuery);
    }

    private List<TrackedQuery> getQueriesMatching(Predicate<TrackedQuery> predicate) {
        List<TrackedQuery> arrayList = new ArrayList();
        Iterator it = this.trackedQueryTree.iterator();
        while (it.hasNext()) {
            for (TrackedQuery trackedQuery : ((Map) ((Entry) it.next()).getValue()).values()) {
                if (predicate.evaluate(trackedQuery)) {
                    arrayList.add(trackedQuery);
                }
            }
        }
        return arrayList;
    }
}
