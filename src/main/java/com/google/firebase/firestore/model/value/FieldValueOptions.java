package com.google.firebase.firestore.model.value;

import com.google.firebase.firestore.util.Assert;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class FieldValueOptions {
    private final ServerTimestampBehavior serverTimestampBehavior;
    private final boolean timestampsInSnapshotsEnabled;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.model.value.FieldValueOptions$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$DocumentSnapshot$ServerTimestampBehavior = new int[com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$google$firebase$firestore$DocumentSnapshot$ServerTimestampBehavior[com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.NONE.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$firestore$DocumentSnapshot$ServerTimestampBehavior = r0;
            r0 = $SwitchMap$com$google$firebase$firestore$DocumentSnapshot$ServerTimestampBehavior;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.ESTIMATE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$firestore$DocumentSnapshot$ServerTimestampBehavior;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.PREVIOUS;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$firestore$DocumentSnapshot$ServerTimestampBehavior;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.NONE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.model.value.FieldValueOptions.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    enum ServerTimestampBehavior {
        NONE,
        PREVIOUS,
        ESTIMATE
    }

    private FieldValueOptions(ServerTimestampBehavior serverTimestampBehavior, boolean z) {
        this.serverTimestampBehavior = serverTimestampBehavior;
        this.timestampsInSnapshotsEnabled = z;
    }

    ServerTimestampBehavior getServerTimestampBehavior() {
        return this.serverTimestampBehavior;
    }

    boolean areTimestampsInSnapshotsEnabled() {
        return this.timestampsInSnapshotsEnabled;
    }

    public static FieldValueOptions create(com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior, boolean z) {
        ServerTimestampBehavior serverTimestampBehavior2;
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$DocumentSnapshot$ServerTimestampBehavior[serverTimestampBehavior.ordinal()];
        if (i == 1) {
            serverTimestampBehavior2 = ServerTimestampBehavior.ESTIMATE;
        } else if (i == 2) {
            serverTimestampBehavior2 = ServerTimestampBehavior.PREVIOUS;
        } else if (i == 3) {
            serverTimestampBehavior2 = ServerTimestampBehavior.NONE;
        } else {
            throw Assert.fail("Unexpected case for ServerTimestampBehavior: %s", serverTimestampBehavior.name());
        }
        return new FieldValueOptions(serverTimestampBehavior2, z);
    }
}
