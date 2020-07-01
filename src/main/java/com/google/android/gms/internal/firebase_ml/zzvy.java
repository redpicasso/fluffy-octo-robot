package com.google.android.gms.internal.firebase_ml;

final class zzvy {
    private static final zzvw zzbro = zzsn();
    private static final zzvw zzbrp = new zzvx();

    static zzvw zzsl() {
        return zzbro;
    }

    static zzvw zzsm() {
        return zzbrp;
    }

    private static zzvw zzsn() {
        try {
            return (zzvw) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
