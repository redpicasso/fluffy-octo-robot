package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

public final class zzed extends zzge {
    private static final AtomicReference<String[]> zzjx = new AtomicReference();
    private static final AtomicReference<String[]> zzjy = new AtomicReference();
    private static final AtomicReference<String[]> zzjz = new AtomicReference();

    zzed(zzfj zzfj) {
        super(zzfj);
    }

    protected final boolean zzbk() {
        return false;
    }

    private final boolean zzgj() {
        zzae();
        return this.zzj.zzhw() && this.zzj.zzab().isLoggable(3);
    }

    @Nullable
    protected final String zzaj(String str) {
        if (str == null) {
            return null;
        }
        if (zzgj()) {
            return zza(str, zzgj.zzpo, zzgj.zzpn, zzjx);
        }
        return str;
    }

    @Nullable
    protected final String zzak(String str) {
        if (str == null) {
            return null;
        }
        if (zzgj()) {
            return zza(str, zzgi.zzpm, zzgi.zzpl, zzjy);
        }
        return str;
    }

    @Nullable
    protected final String zzal(String str) {
        if (str == null) {
            return null;
        }
        if (!zzgj()) {
            return str;
        }
        if (!str.startsWith("_exp_")) {
            return zza(str, zzgl.zzpq, zzgl.zzpp, zzjz);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("experiment_id");
        stringBuilder.append("(");
        stringBuilder.append(str);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Nullable
    private static String zza(String str, String[] strArr, String[] strArr2, AtomicReference<String[]> atomicReference) {
        Preconditions.checkNotNull(strArr);
        Preconditions.checkNotNull(strArr2);
        Preconditions.checkNotNull(atomicReference);
        Preconditions.checkArgument(strArr.length == strArr2.length);
        for (int i = 0; i < strArr.length; i++) {
            if (zzjs.zzs(str, strArr[i])) {
                synchronized (atomicReference) {
                    String[] strArr3 = (String[]) atomicReference.get();
                    if (strArr3 == null) {
                        strArr3 = new String[strArr2.length];
                        atomicReference.set(strArr3);
                    }
                    if (strArr3[i] == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(strArr2[i]);
                        stringBuilder.append("(");
                        stringBuilder.append(strArr[i]);
                        stringBuilder.append(")");
                        strArr3[i] = stringBuilder.toString();
                    }
                    str = strArr3[i];
                }
                return str;
            }
        }
        return str;
    }

    @Nullable
    protected final String zzb(zzai zzai) {
        if (zzai == null) {
            return null;
        }
        if (!zzgj()) {
            return zzai.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("origin=");
        stringBuilder.append(zzai.origin);
        stringBuilder.append(",name=");
        stringBuilder.append(zzaj(zzai.name));
        stringBuilder.append(",params=");
        stringBuilder.append(zzb(zzai.zzfq));
        return stringBuilder.toString();
    }

    @Nullable
    protected final String zza(zzaf zzaf) {
        if (zzaf == null) {
            return null;
        }
        if (!zzgj()) {
            return zzaf.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Event{appId='");
        stringBuilder.append(zzaf.zzce);
        stringBuilder.append("', name='");
        stringBuilder.append(zzaj(zzaf.name));
        stringBuilder.append("', params=");
        stringBuilder.append(zzb(zzaf.zzfq));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Nullable
    private final String zzb(zzah zzah) {
        if (zzah == null) {
            return null;
        }
        if (zzgj()) {
            return zzc(zzah.zzcv());
        }
        return zzah.toString();
    }

    @Nullable
    protected final String zzc(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (!zzgj()) {
            return bundle.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : bundle.keySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            } else {
                stringBuilder.append("Bundle[{");
            }
            stringBuilder.append(zzak(str));
            stringBuilder.append("=");
            stringBuilder.append(bundle.get(str));
        }
        stringBuilder.append("}]");
        return stringBuilder.toString();
    }
}
