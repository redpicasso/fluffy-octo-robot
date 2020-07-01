package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.view.QueryParams;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class OperationSource {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final OperationSource SERVER = new OperationSource(Source.Server, null, false);
    public static final OperationSource USER = new OperationSource(Source.User, null, false);
    private final QueryParams queryParams;
    private final Source source;
    private final boolean tagged;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private enum Source {
        User,
        Server
    }

    public static OperationSource forServerTaggedQuery(QueryParams queryParams) {
        return new OperationSource(Source.Server, queryParams, true);
    }

    public OperationSource(Source source, QueryParams queryParams, boolean z) {
        this.source = source;
        this.queryParams = queryParams;
        this.tagged = z;
    }

    public boolean isFromUser() {
        return this.source == Source.User;
    }

    public boolean isFromServer() {
        return this.source == Source.Server;
    }

    public boolean isTagged() {
        return this.tagged;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OperationSource{source=");
        stringBuilder.append(this.source);
        stringBuilder.append(", queryParams=");
        stringBuilder.append(this.queryParams);
        stringBuilder.append(", tagged=");
        stringBuilder.append(this.tagged);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public QueryParams getQueryParams() {
        return this.queryParams;
    }
}
