package com.google.firebase.ml.vision.text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzpo;
import com.google.android.gms.internal.firebase_ml.zzpw;
import com.google.android.gms.internal.firebase_ml.zzqh;
import com.google.android.gms.internal.firebase_ml.zzqi;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public class FirebaseVisionTextRecognizer implements Closeable {
    public static final int CLOUD = 2;
    public static final int ON_DEVICE = 1;
    @GuardedBy("FirebaseVisionTextRecognizer.class")
    private static final Map<zzqi, FirebaseVisionTextRecognizer> zzayw = new HashMap();
    @GuardedBy("FirebaseVisionTextRecognizer.class")
    private static final Map<zzqh, FirebaseVisionTextRecognizer> zzayx = new HashMap();
    private final zzqi zzazp;
    private final zzqh zzazq;
    @RecognizerType
    private final int zzazr;

    public @interface RecognizerType {
    }

    /* JADX WARNING: Missing block: B:12:0x0035, code:
            return r4;
     */
    /* JADX WARNING: Missing block: B:18:0x0050, code:
            return r4;
     */
    public static synchronized com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer zza(@androidx.annotation.NonNull com.google.firebase.FirebaseApp r3, @androidx.annotation.Nullable com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions r4, boolean r5) {
        /*
        r0 = com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer.class;
        monitor-enter(r0);
        r1 = "FirebaseApp must not be null";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r3, r1);	 Catch:{ all -> 0x0051 }
        r1 = r3.getPersistenceKey();	 Catch:{ all -> 0x0051 }
        r2 = "Firebase app name must not be null";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r1, r2);	 Catch:{ all -> 0x0051 }
        if (r5 != 0) goto L_0x0018;
    L_0x0013:
        r1 = "Options must not be null";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r4, r1);	 Catch:{ all -> 0x0051 }
    L_0x0018:
        r1 = 0;
        if (r5 == 0) goto L_0x0036;
    L_0x001b:
        r3 = com.google.android.gms.internal.firebase_ml.zzqi.zzj(r3);	 Catch:{ all -> 0x0051 }
        r4 = zzayw;	 Catch:{ all -> 0x0051 }
        r4 = r4.get(r3);	 Catch:{ all -> 0x0051 }
        r4 = (com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer) r4;	 Catch:{ all -> 0x0051 }
        if (r4 != 0) goto L_0x0034;
    L_0x0029:
        r4 = new com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;	 Catch:{ all -> 0x0051 }
        r5 = 1;
        r4.<init>(r3, r1, r5);	 Catch:{ all -> 0x0051 }
        r5 = zzayw;	 Catch:{ all -> 0x0051 }
        r5.put(r3, r4);	 Catch:{ all -> 0x0051 }
    L_0x0034:
        monitor-exit(r0);
        return r4;
    L_0x0036:
        r3 = com.google.android.gms.internal.firebase_ml.zzqh.zza(r3, r4);	 Catch:{ all -> 0x0051 }
        r4 = zzayx;	 Catch:{ all -> 0x0051 }
        r4 = r4.get(r3);	 Catch:{ all -> 0x0051 }
        r4 = (com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer) r4;	 Catch:{ all -> 0x0051 }
        if (r4 != 0) goto L_0x004f;
    L_0x0044:
        r4 = new com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;	 Catch:{ all -> 0x0051 }
        r5 = 2;
        r4.<init>(r1, r3, r5);	 Catch:{ all -> 0x0051 }
        r5 = zzayx;	 Catch:{ all -> 0x0051 }
        r5.put(r3, r4);	 Catch:{ all -> 0x0051 }
    L_0x004f:
        monitor-exit(r0);
        return r4;
    L_0x0051:
        r3 = move-exception;
        monitor-exit(r0);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer.zza(com.google.firebase.FirebaseApp, com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions, boolean):com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer");
    }

    private FirebaseVisionTextRecognizer(@Nullable zzqi zzqi, @Nullable zzqh zzqh, @RecognizerType int i) {
        this.zzazr = i;
        this.zzazp = zzqi;
        this.zzazq = zzqh;
    }

    public Task<FirebaseVisionText> processImage(@NonNull FirebaseVisionImage firebaseVisionImage) {
        boolean z = (this.zzazp == null && this.zzazq == null) ? false : true;
        Preconditions.checkArgument(z, "Either on-device or cloud text recognizer should be enabled.");
        zzqi zzqi = this.zzazp;
        if (zzqi != null) {
            return zzqi.processImage(firebaseVisionImage);
        }
        return this.zzazq.processImage(firebaseVisionImage);
    }

    @RecognizerType
    public int getRecognizerType() {
        return this.zzazr;
    }

    public void close() throws IOException {
        zzpw zzpw = this.zzazp;
        if (zzpw != null) {
            zzpw.close();
        }
        zzpo zzpo = this.zzazq;
        if (zzpo != null) {
            zzpo.close();
        }
    }
}
