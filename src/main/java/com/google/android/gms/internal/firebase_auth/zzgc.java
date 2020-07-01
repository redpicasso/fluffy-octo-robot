package com.google.android.gms.internal.firebase_auth;

import java.io.InputStream;

public abstract class zzgc<MessageType extends zzjc> implements zzjm<MessageType> {
    private static final zzhf zzvq = zzhf.zzhq();

    private final MessageType zza(InputStream inputStream, zzhf zzhf) throws zzic {
        zzgr zza;
        if (inputStream == null) {
            byte[] bArr = zzht.EMPTY_BYTE_ARRAY;
            zza = zzgr.zza(bArr, 0, bArr.length, false);
        } else {
            zza = new zzgw(inputStream, 4096, null);
        }
        zzjc zzjc = (zzjc) zza(zza, zzhf);
        try {
            zza.zzs(0);
            return zzjc;
        } catch (zzic e) {
            throw e.zzh(zzjc);
        }
    }

    public final /* synthetic */ Object zzb(InputStream inputStream, zzhf zzhf) throws zzic {
        zzjc zza = zza(inputStream, zzhf);
        if (zza == null || zza.isInitialized()) {
            return zza;
        }
        zzkl zzkl;
        if (zza instanceof zzfx) {
            zzkl = new zzkl((zzfx) zza);
        } else if (zza instanceof zzfz) {
            zzkl = new zzkl((zzfz) zza);
        } else {
            zzkl = new zzkl(zza);
        }
        throw new zzic(zzkl.getMessage()).zzh(zza);
    }
}
