package com.google.android.gms.internal.firebase_ml;

final class zztv {
    private static final zztt<?> zzbli = new zztu();
    private static final zztt<?> zzblj = zzqm();

    private static zztt<?> zzqm() {
        try {
            return (zztt) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    static zztt<?> zzqn() {
        return zzbli;
    }

    static zztt<?> zzqo() {
        zztt<?> zztt = zzblj;
        if (zztt != null) {
            return zztt;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
