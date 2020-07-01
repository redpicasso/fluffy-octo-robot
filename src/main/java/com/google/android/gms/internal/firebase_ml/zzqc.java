package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.NonNull;
import com.drew.metadata.exif.makernotes.OlympusMakernoteDirectory;
import com.google.android.gms.internal.firebase_ml.zzmd.zzq;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import java.util.ArrayList;
import java.util.List;

public final class zzqc extends zzpo<List<FirebaseVisionImageLabel>> {
    public zzqc(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionCloudDetectorOptions firebaseVisionCloudDetectorOptions) {
        super(firebaseApp, "LABEL_DETECTION", firebaseVisionCloudDetectorOptions);
        zznu.zza(firebaseApp, 1).zza(zzq.zzjx(), zzmn.CLOUD_IMAGE_LABEL_CREATE);
    }

    protected final int zznh() {
        return OlympusMakernoteDirectory.TAG_PREVIEW_IMAGE;
    }

    protected final int zzni() {
        return 480;
    }

    public final Task<List<FirebaseVisionImageLabel>> detectInImage(@NonNull FirebaseVisionImage firebaseVisionImage) {
        zznu.zza(this.zzapo, 1).zza(zzq.zzjx(), zzmn.CLOUD_IMAGE_LABEL_DETECT);
        return super.zza(firebaseVisionImage);
    }

    protected final /* synthetic */ Object zza(@NonNull zzir zzir, float f) {
        if (zzir.zzhn() == null) {
            return new ArrayList();
        }
        List<zzjb> zzhn = zzir.zzhn();
        List arrayList = new ArrayList();
        for (zzjb zza : zzhn) {
            FirebaseVisionImageLabel zza2 = FirebaseVisionImageLabel.zza(zza);
            if (zza2 != null) {
                arrayList.add(zza2);
            }
        }
        return arrayList;
    }
}
