package com.google.firebase.ml.vision.cloud.landmark;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.internal.firebase_ml.zzjb;
import com.google.android.gms.internal.firebase_ml.zzjk;
import com.google.android.gms.internal.firebase_ml.zzpm;
import com.google.firebase.ml.vision.common.FirebaseVisionLatLng;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
public class FirebaseVisionCloudLandmark {
    private final List<FirebaseVisionLatLng> locations;
    private final String mid;
    private final float zzatw;
    private final String zzawy;
    private final Rect zzawz;

    public String getEntityId() {
        return this.mid;
    }

    public String getLandmark() {
        return this.zzawy;
    }

    @Nullable
    public Rect getBoundingBox() {
        return this.zzawz;
    }

    public float getConfidence() {
        return this.zzatw;
    }

    public List<FirebaseVisionLatLng> getLocations() {
        return this.locations;
    }

    private FirebaseVisionCloudLandmark(@Nullable String str, float f, @Nullable Rect rect, @Nullable String str2, @NonNull List<FirebaseVisionLatLng> list) {
        this.zzawz = rect;
        String str3 = "";
        if (str == null) {
            str = str3;
        }
        this.zzawy = str;
        if (str2 != null) {
            str3 = str2;
        }
        this.mid = str3;
        this.locations = list;
        float f2 = 0.0f;
        if (Float.compare(f, 0.0f) >= 0) {
            f2 = Float.compare(f, 1.0f) > 0 ? 1.0f : f;
        }
        this.zzatw = f2;
    }

    @Nullable
    static FirebaseVisionCloudLandmark zza(@Nullable zzjb zzjb, float f) {
        if (zzjb == null) {
            return null;
        }
        List arrayList;
        float zza = zzpm.zza(zzjb.zzhv());
        Rect zza2 = zzpm.zza(zzjb.zzhu(), f);
        String description = zzjb.getDescription();
        String mid = zzjb.getMid();
        List<zzjk> locations = zzjb.getLocations();
        if (locations == null) {
            arrayList = new ArrayList();
        } else {
            List arrayList2 = new ArrayList();
            for (zzjk zzjk : locations) {
                if (!(zzjk.zzhy() == null || zzjk.zzhy().zzhw() == null || zzjk.zzhy().zzhx() == null)) {
                    arrayList2.add(new FirebaseVisionLatLng(zzjk.zzhy().zzhw().doubleValue(), zzjk.zzhy().zzhx().doubleValue()));
                }
            }
            arrayList = arrayList2;
        }
        return new FirebaseVisionCloudLandmark(description, zza, zza2, mid, arrayList);
    }
}
