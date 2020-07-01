package com.google.firebase.ml.vision.face;

import androidx.annotation.NonNull;
import com.facebook.react.uimanager.ViewProps;
import com.google.android.gms.internal.firebase_ml.zzkj;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;

public class FirebaseVisionFaceLandmark {
    public static final int LEFT_CHEEK = 1;
    public static final int LEFT_EAR = 3;
    public static final int LEFT_EYE = 4;
    public static final int MOUTH_BOTTOM = 0;
    public static final int MOUTH_LEFT = 5;
    public static final int MOUTH_RIGHT = 11;
    public static final int NOSE_BASE = 6;
    public static final int RIGHT_CHEEK = 7;
    public static final int RIGHT_EAR = 9;
    public static final int RIGHT_EYE = 10;
    private final int type;
    private final FirebaseVisionPoint zzayk;

    public @interface LandmarkType {
    }

    FirebaseVisionFaceLandmark(@LandmarkType int i, @NonNull FirebaseVisionPoint firebaseVisionPoint) {
        this.type = i;
        this.zzayk = firebaseVisionPoint;
    }

    @LandmarkType
    public int getLandmarkType() {
        return this.type;
    }

    public FirebaseVisionPoint getPosition() {
        return this.zzayk;
    }

    public String toString() {
        return zzkj.zzaz("FirebaseVisionFaceLandmark").zzb("type", this.type).zzh(ViewProps.POSITION, this.zzayk).toString();
    }
}
