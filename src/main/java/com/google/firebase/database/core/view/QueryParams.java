package com.google.firebase.database.core.view;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.filter.IndexedFilter;
import com.google.firebase.database.core.view.filter.LimitedFilter;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.core.view.filter.RangedFilter;
import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityIndex;
import com.google.firebase.database.snapshot.PriorityUtilities;
import com.google.firebase.database.snapshot.StringNode;
import com.google.firebase.database.util.JsonMapper;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class QueryParams {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final QueryParams DEFAULT_PARAMS = new QueryParams();
    private static final String INDEX = "i";
    private static final String INDEX_END_NAME = "en";
    private static final String INDEX_END_VALUE = "ep";
    private static final String INDEX_START_NAME = "sn";
    private static final String INDEX_START_VALUE = "sp";
    private static final String LIMIT = "l";
    private static final String VIEW_FROM = "vf";
    private Index index = PriorityIndex.getInstance();
    private ChildKey indexEndName = null;
    private Node indexEndValue = null;
    private ChildKey indexStartName = null;
    private Node indexStartValue = null;
    private String jsonSerialization = null;
    private Integer limit;
    private ViewFrom viewFrom;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    /* renamed from: com.google.firebase.database.core.view.QueryParams$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$core$view$QueryParams$ViewFrom = new int[ViewFrom.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.google.firebase.database.core.view.QueryParams.ViewFrom.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$database$core$view$QueryParams$ViewFrom = r0;
            r0 = $SwitchMap$com$google$firebase$database$core$view$QueryParams$ViewFrom;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.database.core.view.QueryParams.ViewFrom.LEFT;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$database$core$view$QueryParams$ViewFrom;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.database.core.view.QueryParams.ViewFrom.RIGHT;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.view.QueryParams.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private enum ViewFrom {
        LEFT,
        RIGHT
    }

    public boolean hasStart() {
        return this.indexStartValue != null;
    }

    public Node getIndexStartValue() {
        if (hasStart()) {
            return this.indexStartValue;
        }
        throw new IllegalArgumentException("Cannot get index start value if start has not been set");
    }

    public ChildKey getIndexStartName() {
        if (hasStart()) {
            ChildKey childKey = this.indexStartName;
            if (childKey != null) {
                return childKey;
            }
            return ChildKey.getMinName();
        }
        throw new IllegalArgumentException("Cannot get index start name if start has not been set");
    }

    public boolean hasEnd() {
        return this.indexEndValue != null;
    }

    public Node getIndexEndValue() {
        if (hasEnd()) {
            return this.indexEndValue;
        }
        throw new IllegalArgumentException("Cannot get index end value if start has not been set");
    }

    public ChildKey getIndexEndName() {
        if (hasEnd()) {
            ChildKey childKey = this.indexEndName;
            if (childKey != null) {
                return childKey;
            }
            return ChildKey.getMaxName();
        }
        throw new IllegalArgumentException("Cannot get index end name if start has not been set");
    }

    public boolean hasLimit() {
        return this.limit != null;
    }

    public boolean hasAnchoredLimit() {
        return hasLimit() && this.viewFrom != null;
    }

    public int getLimit() {
        if (hasLimit()) {
            return this.limit.intValue();
        }
        throw new IllegalArgumentException("Cannot get limit if limit has not been set");
    }

    public Index getIndex() {
        return this.index;
    }

    private QueryParams copy() {
        QueryParams queryParams = new QueryParams();
        queryParams.limit = this.limit;
        queryParams.indexStartValue = this.indexStartValue;
        queryParams.indexStartName = this.indexStartName;
        queryParams.indexEndValue = this.indexEndValue;
        queryParams.indexEndName = this.indexEndName;
        queryParams.viewFrom = this.viewFrom;
        queryParams.index = this.index;
        return queryParams;
    }

    public QueryParams limitToFirst(int i) {
        QueryParams copy = copy();
        copy.limit = Integer.valueOf(i);
        copy.viewFrom = ViewFrom.LEFT;
        return copy;
    }

    public QueryParams limitToLast(int i) {
        QueryParams copy = copy();
        copy.limit = Integer.valueOf(i);
        copy.viewFrom = ViewFrom.RIGHT;
        return copy;
    }

    public QueryParams startAt(Node node, ChildKey childKey) {
        Utilities.hardAssert(!(node instanceof LongNode));
        QueryParams copy = copy();
        copy.indexStartValue = node;
        copy.indexStartName = childKey;
        return copy;
    }

    public QueryParams endAt(Node node, ChildKey childKey) {
        Utilities.hardAssert(!(node instanceof LongNode));
        QueryParams copy = copy();
        copy.indexEndValue = node;
        copy.indexEndName = childKey;
        return copy;
    }

    public QueryParams orderBy(Index index) {
        QueryParams copy = copy();
        copy.index = index;
        return copy;
    }

    public boolean isViewFromLeft() {
        ViewFrom viewFrom = this.viewFrom;
        if (viewFrom != null) {
            return viewFrom == ViewFrom.LEFT;
        } else {
            return hasStart();
        }
    }

    public Map<String, Object> getWireProtocolParams() {
        ChildKey childKey;
        Map<String, Object> hashMap = new HashMap();
        if (hasStart()) {
            hashMap.put(INDEX_START_VALUE, this.indexStartValue.getValue());
            childKey = this.indexStartName;
            if (childKey != null) {
                hashMap.put(INDEX_START_NAME, childKey.asString());
            }
        }
        if (hasEnd()) {
            hashMap.put(INDEX_END_VALUE, this.indexEndValue.getValue());
            childKey = this.indexEndName;
            if (childKey != null) {
                hashMap.put(INDEX_END_NAME, childKey.asString());
            }
        }
        Integer num = this.limit;
        if (num != null) {
            String str = LIMIT;
            hashMap.put(str, num);
            ViewFrom viewFrom = this.viewFrom;
            if (viewFrom == null) {
                if (hasStart()) {
                    viewFrom = ViewFrom.LEFT;
                } else {
                    viewFrom = ViewFrom.RIGHT;
                }
            }
            int i = AnonymousClass1.$SwitchMap$com$google$firebase$database$core$view$QueryParams$ViewFrom[viewFrom.ordinal()];
            String str2 = VIEW_FROM;
            if (i == 1) {
                hashMap.put(str2, str);
            } else if (i == 2) {
                hashMap.put(str2, "r");
            }
        }
        if (!this.index.equals(PriorityIndex.getInstance())) {
            hashMap.put(INDEX, this.index.getQueryDefinition());
        }
        return hashMap;
    }

    public boolean loadsAllData() {
        return (hasStart() || hasEnd() || hasLimit()) ? false : true;
    }

    public boolean isDefault() {
        return loadsAllData() && this.index.equals(PriorityIndex.getInstance());
    }

    public boolean isValid() {
        return (hasStart() && hasEnd() && hasLimit() && !hasAnchoredLimit()) ? false : true;
    }

    public String toJSON() {
        if (this.jsonSerialization == null) {
            try {
                this.jsonSerialization = JsonMapper.serializeJson(getWireProtocolParams());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return this.jsonSerialization;
    }

    public static QueryParams fromQueryObject(Map<String, Object> map) {
        QueryParams queryParams = new QueryParams();
        String str = LIMIT;
        queryParams.limit = (Integer) map.get(str);
        String str2 = INDEX_START_VALUE;
        if (map.containsKey(str2)) {
            queryParams.indexStartValue = normalizeValue(NodeUtilities.NodeFromJSON(map.get(str2)));
            str2 = (String) map.get(INDEX_START_NAME);
            if (str2 != null) {
                queryParams.indexStartName = ChildKey.fromString(str2);
            }
        }
        str2 = INDEX_END_VALUE;
        if (map.containsKey(str2)) {
            queryParams.indexEndValue = normalizeValue(NodeUtilities.NodeFromJSON(map.get(str2)));
            str2 = (String) map.get(INDEX_END_NAME);
            if (str2 != null) {
                queryParams.indexEndName = ChildKey.fromString(str2);
            }
        }
        str2 = (String) map.get(VIEW_FROM);
        if (str2 != null) {
            queryParams.viewFrom = str2.equals(str) ? ViewFrom.LEFT : ViewFrom.RIGHT;
        }
        String str3 = (String) map.get(INDEX);
        if (str3 != null) {
            queryParams.index = Index.fromQueryDefinition(str3);
        }
        return queryParams;
    }

    public NodeFilter getNodeFilter() {
        if (loadsAllData()) {
            return new IndexedFilter(getIndex());
        }
        if (hasLimit()) {
            return new LimitedFilter(this);
        }
        return new RangedFilter(this);
    }

    public String toString() {
        return getWireProtocolParams().toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        QueryParams queryParams = (QueryParams) obj;
        Integer num = this.limit;
        if (!num == null ? num.equals(queryParams.limit) : queryParams.limit == null) {
            return false;
        }
        Index index = this.index;
        if (!index == null ? index.equals(queryParams.index) : queryParams.index == null) {
            return false;
        }
        ChildKey childKey = this.indexEndName;
        if (!childKey == null ? childKey.equals(queryParams.indexEndName) : queryParams.indexEndName == null) {
            return false;
        }
        Node node = this.indexEndValue;
        if (!node == null ? node.equals(queryParams.indexEndValue) : queryParams.indexEndValue == null) {
            return false;
        }
        childKey = this.indexStartName;
        if (!childKey == null ? childKey.equals(queryParams.indexStartName) : queryParams.indexStartName == null) {
            return false;
        }
        node = this.indexStartValue;
        if (node == null ? queryParams.indexStartValue == null : node.equals(queryParams.indexStartValue)) {
            return isViewFromLeft() == queryParams.isViewFromLeft();
        } else {
            return false;
        }
    }

    public int hashCode() {
        Integer num = this.limit;
        int i = 0;
        int intValue = (((num != null ? num.intValue() : 0) * 31) + (isViewFromLeft() ? 1231 : 1237)) * 31;
        Node node = this.indexStartValue;
        intValue = (intValue + (node != null ? node.hashCode() : 0)) * 31;
        ChildKey childKey = this.indexStartName;
        intValue = (intValue + (childKey != null ? childKey.hashCode() : 0)) * 31;
        node = this.indexEndValue;
        intValue = (intValue + (node != null ? node.hashCode() : 0)) * 31;
        childKey = this.indexEndName;
        intValue = (intValue + (childKey != null ? childKey.hashCode() : 0)) * 31;
        Index index = this.index;
        if (index != null) {
            i = index.hashCode();
        }
        return intValue + i;
    }

    private static Node normalizeValue(Node node) {
        if ((node instanceof StringNode) || (node instanceof BooleanNode) || (node instanceof DoubleNode) || (node instanceof EmptyNode)) {
            return node;
        }
        if (node instanceof LongNode) {
            return new DoubleNode(Double.valueOf(((Long) node.getValue()).doubleValue()), PriorityUtilities.NullPriority());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected value passed to normalizeValue: ");
        stringBuilder.append(node.getValue());
        throw new IllegalStateException(stringBuilder.toString());
    }
}
