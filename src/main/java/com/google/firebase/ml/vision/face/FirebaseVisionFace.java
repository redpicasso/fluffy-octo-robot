package com.google.firebase.ml.vision.face;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.internal.firebase_ml.zzkj;
import com.google.android.gms.internal.firebase_ml.zzkl;
import com.google.android.gms.vision.face.Contour;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour.ContourType;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.LandmarkType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.concurrent.Immutable;

@Immutable
public class FirebaseVisionFace {
    public static final int INVALID_ID = -1;
    public static final float UNCOMPUTED_PROBABILITY = -1.0f;
    private Rect zzawz;
    private int zzaxv = -1;
    private float zzaxw = -1.0f;
    private float zzaxx = -1.0f;
    private float zzaxy = -1.0f;
    private float zzaxz;
    private float zzaya;
    private final SparseArray<FirebaseVisionFaceLandmark> zzayb = new SparseArray();
    private final SparseArray<FirebaseVisionFaceContour> zzayc = new SparseArray();

    private static boolean zzbq(@LandmarkType int i) {
        return i == 0 || i == 1 || i == 7 || i == 3 || i == 9 || i == 4 || i == 10 || i == 5 || i == 11 || i == 6;
    }

    public Rect getBoundingBox() {
        return this.zzawz;
    }

    public int getTrackingId() {
        return this.zzaxv;
    }

    public FirebaseVisionFace(@NonNull Face face) {
        PointF position = face.getPosition();
        this.zzawz = new Rect((int) position.x, (int) position.y, (int) (position.x + face.getWidth()), (int) (position.y + face.getHeight()));
        this.zzaxv = face.getId();
        for (Landmark landmark : face.getLandmarks()) {
            if (zzbq(landmark.getType()) && landmark.getPosition() != null) {
                this.zzayb.put(landmark.getType(), new FirebaseVisionFaceLandmark(landmark.getType(), new FirebaseVisionPoint(Float.valueOf(landmark.getPosition().x), Float.valueOf(landmark.getPosition().y), null)));
            }
        }
        List arrayList = new ArrayList();
        Iterator it = face.getContours().iterator();
        while (true) {
            int i = 1;
            if (it.hasNext()) {
                int i2;
                Contour contour = (Contour) it.next();
                switch (contour.getType()) {
                    case 1:
                        i2 = 2;
                        break;
                    case 2:
                        i2 = 3;
                        break;
                    case 3:
                        i2 = 4;
                        break;
                    case 4:
                        i2 = 5;
                        break;
                    case 5:
                        i2 = 6;
                        break;
                    case 6:
                        i2 = 7;
                        break;
                    case 7:
                        i2 = 8;
                        break;
                    case 8:
                        i2 = 9;
                        break;
                    case 9:
                        i2 = 10;
                        break;
                    case 10:
                        i2 = 11;
                        break;
                    case 11:
                        i2 = 12;
                        break;
                    case 12:
                        i2 = 13;
                        break;
                    case 13:
                        i2 = 14;
                        break;
                    default:
                        i2 = -1;
                        break;
                }
                if (i2 > 14 || i2 <= 0) {
                    i = 0;
                }
                if (i != 0) {
                    PointF[] positions = contour.getPositions();
                    Collection arrayList2 = new ArrayList();
                    if (positions != null) {
                        for (PointF pointF : positions) {
                            arrayList2.add(new FirebaseVisionPoint(Float.valueOf(pointF.x), Float.valueOf(pointF.y), null));
                        }
                        this.zzayc.put(i2, new FirebaseVisionFaceContour(i2, arrayList2));
                        arrayList.addAll(arrayList2);
                    }
                }
            } else {
                this.zzayc.put(1, new FirebaseVisionFaceContour(1, arrayList));
                this.zzaxz = face.getEulerY();
                this.zzaya = face.getEulerZ();
                this.zzaxy = face.getIsSmilingProbability();
                this.zzaxx = face.getIsLeftEyeOpenProbability();
                this.zzaxw = face.getIsRightEyeOpenProbability();
                return;
            }
        }
    }

    public float getHeadEulerAngleY() {
        return this.zzaxz;
    }

    public float getHeadEulerAngleZ() {
        return this.zzaya;
    }

    @Nullable
    public FirebaseVisionFaceLandmark getLandmark(@LandmarkType int i) {
        return (FirebaseVisionFaceLandmark) this.zzayb.get(i);
    }

    public FirebaseVisionFaceContour getContour(@ContourType int i) {
        FirebaseVisionFaceContour firebaseVisionFaceContour = (FirebaseVisionFaceContour) this.zzayc.get(i);
        if (firebaseVisionFaceContour != null) {
            return firebaseVisionFaceContour;
        }
        return new FirebaseVisionFaceContour(i, new ArrayList());
    }

    public final SparseArray<FirebaseVisionFaceContour> zznm() {
        return this.zzayc;
    }

    public final void zza(SparseArray<FirebaseVisionFaceContour> sparseArray) {
        this.zzayc.clear();
        for (int i = 0; i < sparseArray.size(); i++) {
            this.zzayc.put(sparseArray.keyAt(i), (FirebaseVisionFaceContour) sparseArray.valueAt(i));
        }
    }

    public final void zzbr(int i) {
        this.zzaxv = -1;
    }

    public float getSmilingProbability() {
        return this.zzaxy;
    }

    public float getLeftEyeOpenProbability() {
        return this.zzaxx;
    }

    public float getRightEyeOpenProbability() {
        return this.zzaxw;
    }

    public String toString() {
        int i;
        StringBuilder stringBuilder;
        String str = "trackingId";
        str = "rightEyeOpenProbability";
        str = "leftEyeOpenProbability";
        str = "smileProbability";
        str = "eulerY";
        str = "eulerZ";
        zzkl zza = zzkj.zzaz("FirebaseVisionFace").zzh("boundingBox", this.zzawz).zzb(str, this.zzaxv).zza(str, this.zzaxw).zza(str, this.zzaxx).zza(str, this.zzaxy).zza(str, this.zzaxz).zza(str, this.zzaya);
        zzkl zzaz = zzkj.zzaz("Landmarks");
        for (i = 0; i <= 11; i++) {
            if (zzbq(i)) {
                stringBuilder = new StringBuilder(20);
                stringBuilder.append("landmark_");
                stringBuilder.append(i);
                zzaz.zzh(stringBuilder.toString(), getLandmark(i));
            }
        }
        zza.zzh("landmarks", zzaz.toString());
        zzaz = zzkj.zzaz("Contours");
        for (i = 1; i <= 14; i++) {
            stringBuilder = new StringBuilder(19);
            stringBuilder.append("Contour_");
            stringBuilder.append(i);
            zzaz.zzh(stringBuilder.toString(), getContour(i));
        }
        zza.zzh("contours", zzaz.toString());
        return zza.toString();
    }
}
