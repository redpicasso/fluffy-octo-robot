package com.google.firebase.auth.internal;

import android.text.TextUtils;
import com.google.android.gms.internal.firebase_auth.zzay;
import com.google.android.gms.internal.firebase_auth.zzeu;
import com.google.firebase.auth.zzac;
import com.google.firebase.auth.zzx;
import java.util.ArrayList;
import java.util.List;

public final class zzap {
    public static List<zzx> zzg(List<zzeu> list) {
        if (list == null || list.isEmpty()) {
            return zzay.zzce();
        }
        List<zzx> arrayList = new ArrayList();
        for (zzeu zzeu : list) {
            Object obj;
            if (zzeu == null || TextUtils.isEmpty(zzeu.zzbk())) {
                obj = null;
            } else {
                zzac zzac = new zzac(zzeu.zzbl(), zzeu.getDisplayName(), zzeu.zzex(), zzeu.zzbk());
            }
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
