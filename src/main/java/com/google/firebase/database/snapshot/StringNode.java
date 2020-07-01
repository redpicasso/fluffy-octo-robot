package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.Node.HashVersion;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class StringNode extends LeafNode<StringNode> {
    private final String value;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    /* renamed from: com.google.firebase.database.snapshot.StringNode$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion = new int[HashVersion.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.google.firebase.database.snapshot.Node.HashVersion.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion = r0;
            r0 = $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.database.snapshot.Node.HashVersion.V1;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.database.snapshot.Node.HashVersion.V2;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.snapshot.StringNode.1.<clinit>():void");
        }
    }

    public StringNode(String str, Node node) {
        super(node);
        this.value = str;
    }

    public Object getValue() {
        return this.value;
    }

    public String getHashRepresentation(HashVersion hashVersion) {
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion[hashVersion.ordinal()];
        String str = "string:";
        StringBuilder stringBuilder;
        if (i == 1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(getPriorityHash(hashVersion));
            stringBuilder.append(str);
            stringBuilder.append(this.value);
            return stringBuilder.toString();
        } else if (i == 2) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(getPriorityHash(hashVersion));
            stringBuilder.append(str);
            stringBuilder.append(Utilities.stringHashV2Representation(this.value));
            return stringBuilder.toString();
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Invalid hash version for string node: ");
            stringBuilder2.append(hashVersion);
            throw new IllegalArgumentException(stringBuilder2.toString());
        }
    }

    public StringNode updatePriority(Node node) {
        return new StringNode(this.value, node);
    }

    protected LeafType getLeafType() {
        return LeafType.String;
    }

    protected int compareLeafValues(StringNode stringNode) {
        return this.value.compareTo(stringNode.value);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof StringNode)) {
            return false;
        }
        StringNode stringNode = (StringNode) obj;
        if (this.value.equals(stringNode.value) && this.priority.equals(stringNode.priority)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
