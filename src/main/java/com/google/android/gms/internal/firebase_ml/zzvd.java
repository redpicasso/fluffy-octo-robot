package com.google.android.gms.internal.firebase_ml;

import com.google.android.gms.internal.firebase_ml.zzue.zzf;

final class zzvd implements zzwf {
    private static final zzvn zzbqk = new zzve();
    private final zzvn zzbqj;

    public zzvd() {
        this(new zzvf(zzud.zzqw(), zzrz()));
    }

    private zzvd(zzvn zzvn) {
        this.zzbqj = (zzvn) zzug.zza(zzvn, "messageInfoFactory");
    }

    public final <T> zzwe<T> zzj(Class<T> cls) {
        zzwg.zzl((Class) cls);
        zzvm zzh = this.zzbqj.zzh(cls);
        if (zzh.zzsi()) {
            if (zzue.class.isAssignableFrom(cls)) {
                return zzvu.zza(zzwg.zzsu(), zztv.zzqn(), zzh.zzsj());
            }
            return zzvu.zza(zzwg.zzss(), zztv.zzqo(), zzh.zzsj());
        } else if (zzue.class.isAssignableFrom(cls)) {
            if (zza(zzh)) {
                return zzvs.zza((Class) cls, zzh, zzvy.zzsm(), zzuy.zzry(), zzwg.zzsu(), zztv.zzqn(), zzvl.zzsf());
            }
            return zzvs.zza((Class) cls, zzh, zzvy.zzsm(), zzuy.zzry(), zzwg.zzsu(), null, zzvl.zzsf());
        } else if (zza(zzh)) {
            return zzvs.zza((Class) cls, zzh, zzvy.zzsl(), zzuy.zzrx(), zzwg.zzss(), zztv.zzqo(), zzvl.zzse());
        } else {
            return zzvs.zza((Class) cls, zzh, zzvy.zzsl(), zzuy.zzrx(), zzwg.zzst(), null, zzvl.zzse());
        }
    }

    private static boolean zza(zzvm zzvm) {
        return zzvm.zzsh() == zzf.zzbow;
    }

    private static zzvn zzrz() {
        try {
            return (zzvn) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzbqk;
        }
    }
}
