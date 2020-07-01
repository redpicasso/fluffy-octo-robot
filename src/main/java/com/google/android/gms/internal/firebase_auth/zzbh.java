package com.google.android.gms.internal.firebase_auth;

import java.util.Set;

public final class zzbh {
    static int zza(Set<?> set) {
        int i = 0;
        for (Object next : set) {
            i = ~(~(i + (next != null ? next.hashCode() : 0)));
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x001c A:{RETURN, ExcHandler: java.lang.NullPointerException (unused java.lang.NullPointerException), Splitter: B:6:0x000b} */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x001c A:{RETURN, ExcHandler: java.lang.NullPointerException (unused java.lang.NullPointerException), Splitter: B:6:0x000b} */
    static boolean zza(java.util.Set<?> r4, @org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r5) {
        /*
        r0 = 1;
        if (r4 != r5) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = r5 instanceof java.util.Set;
        r2 = 0;
        if (r1 == 0) goto L_0x001c;
    L_0x0009:
        r5 = (java.util.Set) r5;
        r1 = r4.size();	 Catch:{ NullPointerException -> 0x001c, NullPointerException -> 0x001c }
        r3 = r5.size();	 Catch:{ NullPointerException -> 0x001c, NullPointerException -> 0x001c }
        if (r1 != r3) goto L_0x001c;
    L_0x0015:
        r4 = r4.containsAll(r5);	 Catch:{ NullPointerException -> 0x001c, NullPointerException -> 0x001c }
        if (r4 == 0) goto L_0x001c;
    L_0x001b:
        return r0;
    L_0x001c:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzbh.zza(java.util.Set, java.lang.Object):boolean");
    }
}
