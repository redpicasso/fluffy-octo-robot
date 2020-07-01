package com.google.android.gms.internal.vision;

import androidx.annotation.Nullable;
import com.google.android.gms.vision.clearcut.LogUtils;
import com.google.android.gms.vision.face.internal.client.FaceParcel;

public final class zzl {
    public static zzdu zza(zzn zzn, FaceParcel[] faceParcelArr, @Nullable String str, long j) {
        zzdu zza = LogUtils.zza(j, faceParcelArr.length);
        zza.zzqe.zzpi = new zzdq();
        zza.zzqe.zzpi.zzpn = Long.valueOf((long) zzn.height);
        zza.zzqe.zzpi.zzpm = Long.valueOf((long) zzn.width);
        zza.zzqe.zzpi.zzpo = Long.valueOf((long) zzn.id);
        zza.zzqe.zzpi.zzpp = Long.valueOf(zzn.zzat);
        zzdm zzdm = zza.zzqe.zzpk[0];
        zzdm.name = "face";
        int length = faceParcelArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            FaceParcel faceParcel = faceParcelArr[i];
            zzdt zzdt = new zzdt();
            int i3 = i2 + 1;
            zzdm.zzov[i2] = zzdt;
            zzdt.zzpy = new zzdk();
            zzdt.zzpy.zzoh = new zzds[4];
            for (i2 = 0; i2 < 4; i2++) {
                zzdt.zzpy.zzoh[i2] = new zzds();
            }
            zzdt.zzpy.zzoh[0].zzpv = Integer.valueOf((int) (faceParcel.centerX - (faceParcel.width / 2.0f)));
            zzdt.zzpy.zzoh[0].zzpw = Integer.valueOf((int) (faceParcel.centerY - (faceParcel.height / 2.0f)));
            zzdt.zzpy.zzoh[1].zzpv = Integer.valueOf((int) (((float) zzdt.zzpy.zzoh[0].zzpv.intValue()) + faceParcel.width));
            zzdt.zzpy.zzoh[1].zzpw = zzdt.zzpy.zzoh[0].zzpw;
            zzdt.zzpy.zzoh[2].zzpv = Integer.valueOf((int) (((float) zzdt.zzpy.zzoh[0].zzpv.intValue()) + faceParcel.width));
            zzdt.zzpy.zzoh[2].zzpw = Integer.valueOf((int) (((float) zzdt.zzpy.zzoh[0].zzpw.intValue()) + faceParcel.height));
            zzdt.zzpy.zzoh[3].zzpv = zzdt.zzpy.zzoh[0].zzpv;
            zzdt.zzpy.zzoh[3].zzpw = Integer.valueOf((int) (((float) zzdt.zzpy.zzoh[0].zzpw.intValue()) + faceParcel.height));
            zzdt.zzpz = Integer.valueOf(faceParcel.id);
            zzdt.zzqa = new zzdo();
            zzdt.zzqa.zzpf = Float.valueOf(faceParcel.zzdb);
            zzdt.zzqa.zzpg = Float.valueOf(faceParcel.zzdc);
            zzdt.zzqa.zzph = Float.valueOf(faceParcel.zzdd);
            zzdt.zzqa.zzpc = Float.valueOf(faceParcel.zzcg);
            zzdt.zzqa.zzpd = Float.valueOf(faceParcel.zzch);
            zzdt.zzqa.zzpe = Float.valueOf(faceParcel.zzci);
            i++;
            i2 = i3;
        }
        return zza;
    }
}
