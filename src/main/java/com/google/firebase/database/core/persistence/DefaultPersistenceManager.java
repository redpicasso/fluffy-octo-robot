package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.core.utilities.DefaultClock;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DefaultPersistenceManager implements PersistenceManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final CachePolicy cachePolicy;
    private final LogWrapper logger;
    private long serverCacheUpdatesSinceLastPruneCheck;
    private final PersistenceStorageEngine storageLayer;
    private final TrackedQueryManager trackedQueryManager;

    public DefaultPersistenceManager(Context context, PersistenceStorageEngine persistenceStorageEngine, CachePolicy cachePolicy) {
        this(context, persistenceStorageEngine, cachePolicy, new DefaultClock());
    }

    public DefaultPersistenceManager(Context context, PersistenceStorageEngine persistenceStorageEngine, CachePolicy cachePolicy, Clock clock) {
        this.serverCacheUpdatesSinceLastPruneCheck = 0;
        this.storageLayer = persistenceStorageEngine;
        this.logger = context.getLogger("Persistence");
        this.trackedQueryManager = new TrackedQueryManager(this.storageLayer, this.logger, clock);
        this.cachePolicy = cachePolicy;
    }

    public void saveUserOverwrite(Path path, Node node, long j) {
        this.storageLayer.saveUserOverwrite(path, node, j);
    }

    public void saveUserMerge(Path path, CompoundWrite compoundWrite, long j) {
        this.storageLayer.saveUserMerge(path, compoundWrite, j);
    }

    public void removeUserWrite(long j) {
        this.storageLayer.removeUserWrite(j);
    }

    public void removeAllUserWrites() {
        this.storageLayer.removeAllUserWrites();
    }

    public void applyUserWriteToServerCache(Path path, Node node) {
        if (!this.trackedQueryManager.hasActiveDefaultQuery(path)) {
            this.storageLayer.overwriteServerCache(path, node);
            this.trackedQueryManager.ensureCompleteTrackedQuery(path);
        }
    }

    public void applyUserWriteToServerCache(Path path, CompoundWrite compoundWrite) {
        Iterator it = compoundWrite.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            applyUserWriteToServerCache(path.child((Path) entry.getKey()), (Node) entry.getValue());
        }
    }

    public List<UserWriteRecord> loadUserWrites() {
        return this.storageLayer.loadUserWrites();
    }

    public CacheNode serverCache(QuerySpec querySpec) {
        boolean z;
        Set loadTrackedQueryKeys;
        if (this.trackedQueryManager.isQueryComplete(querySpec)) {
            TrackedQuery findTrackedQuery = this.trackedQueryManager.findTrackedQuery(querySpec);
            loadTrackedQueryKeys = (querySpec.loadsAllData() || findTrackedQuery == null || !findTrackedQuery.complete) ? null : this.storageLayer.loadTrackedQueryKeys(findTrackedQuery.id);
            z = true;
        } else {
            loadTrackedQueryKeys = this.trackedQueryManager.getKnownCompleteChildren(querySpec.getPath());
            z = false;
        }
        Node serverCache = this.storageLayer.serverCache(querySpec.getPath());
        if (loadTrackedQueryKeys == null) {
            return new CacheNode(IndexedNode.from(serverCache, querySpec.getIndex()), z, false);
        }
        Node Empty = EmptyNode.Empty();
        for (ChildKey childKey : loadTrackedQueryKeys) {
            Empty = Empty.updateImmediateChild(childKey, serverCache.getImmediateChild(childKey));
        }
        return new CacheNode(IndexedNode.from(Empty, querySpec.getIndex()), z, true);
    }

    public void updateServerCache(QuerySpec querySpec, Node node) {
        if (querySpec.loadsAllData()) {
            this.storageLayer.overwriteServerCache(querySpec.getPath(), node);
        } else {
            this.storageLayer.mergeIntoServerCache(querySpec.getPath(), node);
        }
        setQueryComplete(querySpec);
        doPruneCheckAfterServerUpdate();
    }

    public void updateServerCache(Path path, CompoundWrite compoundWrite) {
        this.storageLayer.mergeIntoServerCache(path, compoundWrite);
        doPruneCheckAfterServerUpdate();
    }

    public void setQueryActive(QuerySpec querySpec) {
        this.trackedQueryManager.setQueryActive(querySpec);
    }

    public void setQueryInactive(QuerySpec querySpec) {
        this.trackedQueryManager.setQueryInactive(querySpec);
    }

    public void setQueryComplete(QuerySpec querySpec) {
        if (querySpec.loadsAllData()) {
            this.trackedQueryManager.setQueriesComplete(querySpec.getPath());
        } else {
            this.trackedQueryManager.setQueryCompleteIfExists(querySpec);
        }
    }

    public void setTrackedQueryKeys(QuerySpec querySpec, Set<ChildKey> set) {
        this.storageLayer.saveTrackedQueryKeys(this.trackedQueryManager.findTrackedQuery(querySpec).id, set);
    }

    public void updateTrackedQueryKeys(QuerySpec querySpec, Set<ChildKey> set, Set<ChildKey> set2) {
        this.storageLayer.updateTrackedQueryKeys(this.trackedQueryManager.findTrackedQuery(querySpec).id, set, set2);
    }

    public <T> T runInTransaction(Callable<T> callable) {
        this.storageLayer.beginTransaction();
        try {
            T call = callable.call();
            this.storageLayer.setTransactionSuccessful();
            this.storageLayer.endTransaction();
            return call;
        } catch (Throwable th) {
            this.storageLayer.endTransaction();
        }
    }

    private void doPruneCheckAfterServerUpdate() {
        this.serverCacheUpdatesSinceLastPruneCheck++;
        if (this.cachePolicy.shouldCheckCacheSize(this.serverCacheUpdatesSinceLastPruneCheck)) {
            LogWrapper logWrapper;
            StringBuilder stringBuilder;
            if (this.logger.logsDebug()) {
                this.logger.debug("Reached prune check threshold.", new Object[0]);
            }
            this.serverCacheUpdatesSinceLastPruneCheck = 0;
            Object obj = 1;
            long serverCacheEstimatedSizeInBytes = this.storageLayer.serverCacheEstimatedSizeInBytes();
            if (this.logger.logsDebug()) {
                logWrapper = this.logger;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cache size: ");
                stringBuilder.append(serverCacheEstimatedSizeInBytes);
                logWrapper.debug(stringBuilder.toString(), new Object[0]);
            }
            while (obj != null && this.cachePolicy.shouldPrune(serverCacheEstimatedSizeInBytes, this.trackedQueryManager.countOfPrunableQueries())) {
                PruneForest pruneOldQueries = this.trackedQueryManager.pruneOldQueries(this.cachePolicy);
                if (pruneOldQueries.prunesAnything()) {
                    this.storageLayer.pruneCache(Path.getEmptyPath(), pruneOldQueries);
                } else {
                    obj = null;
                }
                serverCacheEstimatedSizeInBytes = this.storageLayer.serverCacheEstimatedSizeInBytes();
                if (this.logger.logsDebug()) {
                    logWrapper = this.logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Cache size after prune: ");
                    stringBuilder.append(serverCacheEstimatedSizeInBytes);
                    logWrapper.debug(stringBuilder.toString(), new Object[0]);
                }
            }
        }
    }
}
