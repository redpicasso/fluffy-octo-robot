package com.google.android.gms.internal.firebase_auth;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzjw extends zzjt<FieldDescriptorType, Object> {
    zzjw(int i) {
        super(i, null);
    }

    public final void zzfy() {
        if (!isImmutable()) {
            Entry zzbf;
            for (int i = 0; i < zzjy(); i++) {
                zzbf = zzbf(i);
                if (((zzhk) zzbf.getKey()).zzhz()) {
                    zzbf.setValue(Collections.unmodifiableList((List) zzbf.getValue()));
                }
            }
            for (Entry zzbf2 : zzjz()) {
                if (((zzhk) zzbf2.getKey()).zzhz()) {
                    zzbf2.setValue(Collections.unmodifiableList((List) zzbf2.getValue()));
                }
            }
        }
        super.zzfy();
    }
}
