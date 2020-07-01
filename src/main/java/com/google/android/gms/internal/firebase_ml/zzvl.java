package com.google.android.gms.internal.firebase_ml;

final class zzvl {
    private static final zzvj zzbqt = zzsg();
    private static final zzvj zzbqu = new zzvk();

    static zzvj zzse() {
        return zzbqt;
    }

    static zzvj zzsf() {
        return zzbqu;
    }

    private static zzvj zzsg() {
        try {
            return (zzvj) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
