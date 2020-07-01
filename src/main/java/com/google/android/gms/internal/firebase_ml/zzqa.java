package com.google.android.gms.internal.firebase_ml;

import android.content.Context;
import android.util.SparseArray;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.vision.dynamite.face.ModuleDescriptor;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class zzqa implements zznm<List<FirebaseVisionFace>, zzpz>, zznw {
    @VisibleForTesting
    private static AtomicBoolean zzato = new AtomicBoolean(true);
    private static volatile Boolean zzaub;
    private final Context zzad;
    private final zznu zzaqs;
    private zzpu zzawc = new zzpu();
    private final FirebaseVisionFaceDetectorOptions zzaye;
    @GuardedBy("this")
    private FaceDetector zzayl;
    @GuardedBy("this")
    private FaceDetector zzaym;

    public zzqa(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions) {
        Preconditions.checkNotNull(firebaseApp, "FirebaseApp can not be null");
        Preconditions.checkNotNull(firebaseVisionFaceDetectorOptions, "FirebaseVisionFaceDetectorOptions can not be null");
        this.zzad = firebaseApp.getApplicationContext();
        this.zzaye = firebaseVisionFaceDetectorOptions;
        this.zzaqs = zznu.zza(firebaseApp, 1);
    }

    public final zznw zzlm() {
        return this;
    }

    /* JADX WARNING: Missing block: B:23:0x00e6, code:
            return;
     */
    @androidx.annotation.WorkerThread
    public final synchronized void zzlp() {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r0 = r0.getContourMode();	 Catch:{ all -> 0x00e7 }
        r1 = 2;
        if (r0 != r1) goto L_0x0096;
    L_0x000a:
        r0 = r3.zzaym;	 Catch:{ all -> 0x00e7 }
        if (r0 != 0) goto L_0x002d;
    L_0x000e:
        r0 = new com.google.android.gms.vision.face.FaceDetector$Builder;	 Catch:{ all -> 0x00e7 }
        r2 = r3.zzad;	 Catch:{ all -> 0x00e7 }
        r0.<init>(r2);	 Catch:{ all -> 0x00e7 }
        r0 = r0.setLandmarkType(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.setMode(r1);	 Catch:{ all -> 0x00e7 }
        r2 = 0;
        r0 = r0.setTrackingEnabled(r2);	 Catch:{ all -> 0x00e7 }
        r2 = 1;
        r0 = r0.setProminentFaceOnly(r2);	 Catch:{ all -> 0x00e7 }
        r0 = r0.build();	 Catch:{ all -> 0x00e7 }
        r3.zzaym = r0;	 Catch:{ all -> 0x00e7 }
    L_0x002d:
        r0 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r0 = r0.getLandmarkMode();	 Catch:{ all -> 0x00e7 }
        if (r0 == r1) goto L_0x0045;
    L_0x0035:
        r0 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r0 = r0.getClassificationMode();	 Catch:{ all -> 0x00e7 }
        if (r0 == r1) goto L_0x0045;
    L_0x003d:
        r0 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r0 = r0.getPerformanceMode();	 Catch:{ all -> 0x00e7 }
        if (r0 != r1) goto L_0x00e5;
    L_0x0045:
        r0 = r3.zzayl;	 Catch:{ all -> 0x00e7 }
        if (r0 != 0) goto L_0x00e5;
    L_0x0049:
        r0 = new com.google.android.gms.vision.face.FaceDetector$Builder;	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzad;	 Catch:{ all -> 0x00e7 }
        r0.<init>(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.getLandmarkMode();	 Catch:{ all -> 0x00e7 }
        r1 = com.google.android.gms.internal.firebase_ml.zzpv.zzbn(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.setLandmarkType(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.getClassificationMode();	 Catch:{ all -> 0x00e7 }
        r1 = com.google.android.gms.internal.firebase_ml.zzpv.zzbp(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.setClassificationType(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.getPerformanceMode();	 Catch:{ all -> 0x00e7 }
        r1 = com.google.android.gms.internal.firebase_ml.zzpv.zzbo(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.setMode(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.getMinFaceSize();	 Catch:{ all -> 0x00e7 }
        r0 = r0.setMinFaceSize(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.isTrackingEnabled();	 Catch:{ all -> 0x00e7 }
        r0 = r0.setTrackingEnabled(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.build();	 Catch:{ all -> 0x00e7 }
        r3.zzayl = r0;	 Catch:{ all -> 0x00e7 }
        monitor-exit(r3);
        return;
    L_0x0096:
        r0 = r3.zzayl;	 Catch:{ all -> 0x00e7 }
        if (r0 != 0) goto L_0x00e5;
    L_0x009a:
        r0 = new com.google.android.gms.vision.face.FaceDetector$Builder;	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzad;	 Catch:{ all -> 0x00e7 }
        r0.<init>(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.getLandmarkMode();	 Catch:{ all -> 0x00e7 }
        r1 = com.google.android.gms.internal.firebase_ml.zzpv.zzbn(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.setLandmarkType(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.getClassificationMode();	 Catch:{ all -> 0x00e7 }
        r1 = com.google.android.gms.internal.firebase_ml.zzpv.zzbp(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.setClassificationType(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.getPerformanceMode();	 Catch:{ all -> 0x00e7 }
        r1 = com.google.android.gms.internal.firebase_ml.zzpv.zzbo(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.setMode(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.getMinFaceSize();	 Catch:{ all -> 0x00e7 }
        r0 = r0.setMinFaceSize(r1);	 Catch:{ all -> 0x00e7 }
        r1 = r3.zzaye;	 Catch:{ all -> 0x00e7 }
        r1 = r1.isTrackingEnabled();	 Catch:{ all -> 0x00e7 }
        r0 = r0.setTrackingEnabled(r1);	 Catch:{ all -> 0x00e7 }
        r0 = r0.build();	 Catch:{ all -> 0x00e7 }
        r3.zzayl = r0;	 Catch:{ all -> 0x00e7 }
    L_0x00e5:
        monitor-exit(r3);
        return;
    L_0x00e7:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzqa.zzlp():void");
    }

    @WorkerThread
    public final synchronized void release() {
        if (this.zzayl != null) {
            this.zzayl.release();
            this.zzayl = null;
        }
        if (this.zzaym != null) {
            this.zzaym.release();
            this.zzaym = null;
        }
        zzato.set(true);
    }

    @WorkerThread
    @VisibleForTesting
    private final synchronized List<FirebaseVisionFace> zza(@NonNull FaceDetector faceDetector, @NonNull zzpz zzpz, long j) throws FirebaseMLException {
        List<FirebaseVisionFace> arrayList;
        if (this.zzaym != null) {
            if (zzaub == null) {
                zzaub = Boolean.valueOf(DynamiteModule.getLocalVersion(this.zzad, ModuleDescriptor.MODULE_ID) > 0);
            }
            if (!zzaub.booleanValue()) {
                throw new FirebaseMLException("No Face Contour model is bundled. Please check your app setup to include firebase-ml-vision-face-model dependency.", 14);
            }
        }
        if (faceDetector.isOperational()) {
            SparseArray detect = faceDetector.detect(zzpz.zzaxe);
            arrayList = new ArrayList();
            for (int i = 0; i < detect.size(); i++) {
                arrayList.add(new FirebaseVisionFace((Face) detect.get(detect.keyAt(i))));
            }
        } else {
            zza(zzmk.MODEL_NOT_DOWNLOADED, j, zzpz, 0, 0);
            throw new FirebaseMLException("Waiting for the face detection model to be downloaded. Please wait.", 14);
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:70:0x010f A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x010f A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0143  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0141  */
    @androidx.annotation.WorkerThread
    private final synchronized java.util.List<com.google.firebase.ml.vision.face.FirebaseVisionFace> zza(@androidx.annotation.NonNull com.google.android.gms.internal.firebase_ml.zzpz r22) throws com.google.firebase.ml.common.FirebaseMLException {
        /*
        r21 = this;
        r8 = r21;
        r0 = r22;
        monitor-enter(r21);
        r3 = android.os.SystemClock.elapsedRealtime();	 Catch:{ all -> 0x015a }
        r1 = r8.zzayl;	 Catch:{ all -> 0x015a }
        r9 = 13;
        if (r1 != 0) goto L_0x0027;
    L_0x000f:
        r1 = r8.zzaym;	 Catch:{ all -> 0x015a }
        if (r1 == 0) goto L_0x0014;
    L_0x0013:
        goto L_0x0027;
    L_0x0014:
        r2 = com.google.android.gms.internal.firebase_ml.zzmk.UNKNOWN_ERROR;	 Catch:{ all -> 0x015a }
        r6 = 0;
        r7 = 0;
        r1 = r21;
        r5 = r22;
        r1.zza(r2, r3, r5, r6, r7);	 Catch:{ all -> 0x015a }
        r0 = new com.google.firebase.ml.common.FirebaseMLException;	 Catch:{ all -> 0x015a }
        r1 = "Face detector wasn't initialized correctly. This is most likely caused by invalid face detector options.";
        r0.<init>(r1, r9);	 Catch:{ all -> 0x015a }
        throw r0;	 Catch:{ all -> 0x015a }
    L_0x0027:
        r1 = r8.zzawc;	 Catch:{ all -> 0x015a }
        r1.zzb(r0);	 Catch:{ all -> 0x015a }
        r1 = r8.zzayl;	 Catch:{ all -> 0x015a }
        r2 = 0;
        if (r1 == 0) goto L_0x0043;
    L_0x0031:
        r1 = r8.zzayl;	 Catch:{ all -> 0x015a }
        r1 = r8.zza(r1, r0, r3);	 Catch:{ all -> 0x015a }
        r5 = r8.zzaye;	 Catch:{ all -> 0x015a }
        r5 = r5.isTrackingEnabled();	 Catch:{ all -> 0x015a }
        if (r5 != 0) goto L_0x0044;
    L_0x003f:
        zzh(r1);	 Catch:{ all -> 0x015a }
        goto L_0x0044;
    L_0x0043:
        r1 = r2;
    L_0x0044:
        r5 = r8.zzaym;	 Catch:{ all -> 0x015a }
        if (r5 == 0) goto L_0x0051;
    L_0x0048:
        r2 = r8.zzaym;	 Catch:{ all -> 0x015a }
        r2 = r8.zza(r2, r0, r3);	 Catch:{ all -> 0x015a }
        zzh(r2);	 Catch:{ all -> 0x015a }
    L_0x0051:
        if (r1 != 0) goto L_0x005e;
    L_0x0053:
        if (r2 == 0) goto L_0x0056;
    L_0x0055:
        goto L_0x005e;
    L_0x0056:
        r0 = new com.google.firebase.ml.common.FirebaseMLException;	 Catch:{ all -> 0x015a }
        r1 = "No detector is enabled";
        r0.<init>(r1, r9);	 Catch:{ all -> 0x015a }
        throw r0;	 Catch:{ all -> 0x015a }
    L_0x005e:
        if (r1 != 0) goto L_0x0065;
    L_0x0060:
        r0 = r2;
    L_0x0061:
        r19 = r3;
        goto L_0x0134;
    L_0x0065:
        if (r2 != 0) goto L_0x0069;
    L_0x0067:
        r0 = r1;
        goto L_0x0061;
    L_0x0069:
        r5 = new java.util.HashSet;	 Catch:{ all -> 0x015a }
        r5.<init>();	 Catch:{ all -> 0x015a }
        r6 = r2.iterator();	 Catch:{ all -> 0x015a }
    L_0x0072:
        r7 = r6.hasNext();	 Catch:{ all -> 0x015a }
        if (r7 == 0) goto L_0x012d;
    L_0x0078:
        r7 = r6.next();	 Catch:{ all -> 0x015a }
        r7 = (com.google.firebase.ml.vision.face.FirebaseVisionFace) r7;	 Catch:{ all -> 0x015a }
        r10 = r1.iterator();	 Catch:{ all -> 0x015a }
        r11 = 0;
    L_0x0083:
        r12 = r10.hasNext();	 Catch:{ all -> 0x015a }
        if (r12 == 0) goto L_0x011c;
    L_0x0089:
        r12 = r10.next();	 Catch:{ all -> 0x015a }
        r12 = (com.google.firebase.ml.vision.face.FirebaseVisionFace) r12;	 Catch:{ all -> 0x015a }
        r13 = r7.getBoundingBox();	 Catch:{ all -> 0x015a }
        if (r13 == 0) goto L_0x00fe;
    L_0x0095:
        r13 = r12.getBoundingBox();	 Catch:{ all -> 0x015a }
        if (r13 != 0) goto L_0x009c;
    L_0x009b:
        goto L_0x00fe;
    L_0x009c:
        r13 = r7.getBoundingBox();	 Catch:{ all -> 0x015a }
        r15 = r12.getBoundingBox();	 Catch:{ all -> 0x015a }
        r16 = r13.intersect(r15);	 Catch:{ all -> 0x015a }
        if (r16 == 0) goto L_0x00fe;
    L_0x00aa:
        r14 = r13.right;	 Catch:{ all -> 0x015a }
        r9 = r15.right;	 Catch:{ all -> 0x015a }
        r9 = java.lang.Math.min(r14, r9);	 Catch:{ all -> 0x015a }
        r14 = r13.left;	 Catch:{ all -> 0x015a }
        r0 = r15.left;	 Catch:{ all -> 0x015a }
        r0 = java.lang.Math.max(r14, r0);	 Catch:{ all -> 0x015a }
        r9 = r9 - r0;
        r0 = r13.bottom;	 Catch:{ all -> 0x015a }
        r14 = r15.bottom;	 Catch:{ all -> 0x015a }
        r0 = java.lang.Math.min(r0, r14);	 Catch:{ all -> 0x015a }
        r14 = r13.top;	 Catch:{ all -> 0x015a }
        r17 = r6;
        r6 = r15.top;	 Catch:{ all -> 0x015a }
        r6 = java.lang.Math.max(r14, r6);	 Catch:{ all -> 0x015a }
        r0 = r0 - r6;
        r9 = r9 * r0;
        r0 = r10;
        r9 = (double) r9;	 Catch:{ all -> 0x015a }
        r6 = r13.right;	 Catch:{ all -> 0x015a }
        r14 = r13.left;	 Catch:{ all -> 0x015a }
        r6 = r6 - r14;
        r14 = r13.bottom;	 Catch:{ all -> 0x015a }
        r13 = r13.top;	 Catch:{ all -> 0x015a }
        r14 = r14 - r13;
        r6 = r6 * r14;
        r13 = (double) r6;	 Catch:{ all -> 0x015a }
        r6 = r15.right;	 Catch:{ all -> 0x015a }
        r18 = r0;
        r0 = r15.left;	 Catch:{ all -> 0x015a }
        r6 = r6 - r0;
        r0 = r15.bottom;	 Catch:{ all -> 0x015a }
        r15 = r15.top;	 Catch:{ all -> 0x015a }
        r0 = r0 - r15;
        r6 = r6 * r0;
        r19 = r3;
        r3 = (double) r6;	 Catch:{ all -> 0x015a }
        r13 = r13 + r3;
        r13 = r13 - r9;
        r9 = r9 / r13;
        r3 = 4603579539098121011; // 0x3fe3333333333333 float:4.172325E-8 double:0.6;
        r0 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1));
        if (r0 <= 0) goto L_0x0104;
    L_0x00fc:
        r0 = 1;
        goto L_0x0105;
    L_0x00fe:
        r19 = r3;
        r17 = r6;
        r18 = r10;
    L_0x0104:
        r0 = 0;
    L_0x0105:
        if (r0 == 0) goto L_0x010f;
    L_0x0107:
        r0 = r7.zznm();	 Catch:{ all -> 0x015a }
        r12.zza(r0);	 Catch:{ all -> 0x015a }
        r11 = 1;
    L_0x010f:
        r5.add(r12);	 Catch:{ all -> 0x015a }
        r0 = r22;
        r6 = r17;
        r10 = r18;
        r3 = r19;
        goto L_0x0083;
    L_0x011c:
        r19 = r3;
        r17 = r6;
        if (r11 != 0) goto L_0x0125;
    L_0x0122:
        r5.add(r7);	 Catch:{ all -> 0x015a }
    L_0x0125:
        r0 = r22;
        r6 = r17;
        r3 = r19;
        goto L_0x0072;
    L_0x012d:
        r19 = r3;
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x015a }
        r0.<init>(r5);	 Catch:{ all -> 0x015a }
    L_0x0134:
        r3 = com.google.android.gms.internal.firebase_ml.zzmk.NO_ERROR;	 Catch:{ all -> 0x015a }
        if (r2 != 0) goto L_0x013a;
    L_0x0138:
        r6 = 0;
        goto L_0x013f;
    L_0x013a:
        r2 = r2.size();	 Catch:{ all -> 0x015a }
        r6 = r2;
    L_0x013f:
        if (r1 != 0) goto L_0x0143;
    L_0x0141:
        r7 = 0;
        goto L_0x0148;
    L_0x0143:
        r1 = r1.size();	 Catch:{ all -> 0x015a }
        r7 = r1;
    L_0x0148:
        r1 = r21;
        r2 = r3;
        r3 = r19;
        r5 = r22;
        r1.zza(r2, r3, r5, r6, r7);	 Catch:{ all -> 0x015a }
        r1 = zzato;	 Catch:{ all -> 0x015a }
        r2 = 0;
        r1.set(r2);	 Catch:{ all -> 0x015a }
        monitor-exit(r21);
        return r0;
    L_0x015a:
        r0 = move-exception;
        monitor-exit(r21);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_ml.zzqa.zza(com.google.android.gms.internal.firebase_ml.zzpz):java.util.List<com.google.firebase.ml.vision.face.FirebaseVisionFace>");
    }

    private static void zzh(@NonNull List<FirebaseVisionFace> list) {
        for (FirebaseVisionFace zzbr : list) {
            zzbr.zzbr(-1);
        }
    }

    private final synchronized void zza(zzmk zzmk, long j, zzpz zzpz, int i, int i2) {
        this.zzaqs.zza(new zzqb(this, j, zzmk, i, i2, zzpz), zzmn.ON_DEVICE_FACE_DETECT);
    }
}
