package com.google.android.gms.internal.firebase_ml;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzwi extends zzwh<FieldDescriptorType, Object> {
    zzwi(int i) {
        super(i, null);
    }

    public final void zzpt() {
        if (!isImmutable()) {
            Entry zzdp;
            for (int i = 0; i < zzsx(); i++) {
                zzdp = zzdp(i);
                if (((zzty) zzdp.getKey()).zzqt()) {
                    zzdp.setValue(Collections.unmodifiableList((List) zzdp.getValue()));
                }
            }
            for (Entry zzdp2 : zzsy()) {
                if (((zzty) zzdp2.getKey()).zzqt()) {
                    zzdp2.setValue(Collections.unmodifiableList((List) zzdp2.getValue()));
                }
            }
        }
        super.zzpt();
    }
}
