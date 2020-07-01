package com.google.android.gms.internal.firebase_ml;

import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import java.io.File;
import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;

public final class zzov {
    private static final GmsLogger zzaoz = new GmsLogger("RemoteModelLoader", "");
    private static Map<String, zzov> zzarj = new HashMap();
    private final zzon zzaqw;
    private final zzok zzara;
    private final zzoo zzark;
    private final zzoq zzarl;
    private final zzow zzarm;
    private boolean zzarn = true;

    private zzov(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseRemoteModel firebaseRemoteModel, @NonNull zzol zzol, zzow zzow, @NonNull zzok zzok) {
        this.zzarl = new zzoq(firebaseApp, firebaseRemoteModel.zzmj(), zzol, zzok);
        this.zzaqw = new zzon(firebaseApp, firebaseRemoteModel);
        this.zzark = new zzoo(firebaseApp, this.zzarl, firebaseRemoteModel, this.zzaqw);
        this.zzarm = zzow;
        this.zzara = zzok;
    }

    public static synchronized zzov zza(@NonNull FirebaseApp firebaseApp, @NonNull FirebaseRemoteModel firebaseRemoteModel, @NonNull zzol zzol, zzow zzow, zzok zzok) {
        zzov zzov;
        synchronized (zzov.class) {
            String zzmj = firebaseRemoteModel.zzmj();
            if (!zzarj.containsKey(zzmj)) {
                zzarj.put(zzmj, new zzov(firebaseApp, firebaseRemoteModel, zzol, zzow, zzok));
            }
            zzov = (zzov) zzarj.get(zzmj);
        }
        return zzov;
    }

    @WorkerThread
    @Nullable
    public final synchronized MappedByteBuffer load() throws FirebaseMLException {
        MappedByteBuffer zzad;
        zzaoz.d("RemoteModelLoader", "Try to load newly downloaded model file.");
        zzad = zzad(this.zzarn);
        if (zzad == null) {
            zzaoz.d("RemoteModelLoader", "Loading existing model file.");
            String zzmf = this.zzarl.zzmf();
            if (zzmf == null) {
                zzaoz.d("RemoteModelLoader", "No existing model file");
                zzad = null;
            } else {
                zzad = zzbz(zzmf);
            }
        }
        if (this.zzarn) {
            this.zzarn = false;
            zzaoz.d("RemoteModelLoader", "Initial loading, check for model updates.");
            this.zzark.zzlv();
        }
        return zzad;
    }

    @WorkerThread
    @Nullable
    private final MappedByteBuffer zzad(boolean z) throws FirebaseMLException {
        Long zzlw = this.zzark.zzlw();
        String zzlx = this.zzark.zzlx();
        String str = "RemoteModelLoader";
        if (zzlw == null || zzlx == null) {
            zzaoz.d(str, "No new model is downloading.");
            this.zzark.zzly();
            return null;
        }
        Integer zzlz = this.zzark.zzlz();
        if (zzlz == null) {
            this.zzark.zzly();
            return null;
        }
        GmsLogger gmsLogger = zzaoz;
        String valueOf = String.valueOf(zzlz);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 22);
        stringBuilder.append("Download Status code: ");
        stringBuilder.append(valueOf);
        gmsLogger.d(str, stringBuilder.toString());
        if (zzlz.intValue() == 8) {
            zzaoz.d(str, "Model downloaded successfully");
            this.zzaqw.zza(zzmk.NO_ERROR, true);
            ParcelFileDescriptor zzma = this.zzark.zzma();
            if (zzma == null) {
                this.zzark.zzly();
                return null;
            }
            zzaoz.d(str, "moving downloaded model from external storage to private folder.");
            File zza;
            try {
                zza = this.zzarl.zza(zzma, zzlx, this.zzaqw);
                if (zza == null) {
                    return null;
                }
                GmsLogger gmsLogger2 = zzaoz;
                String str2 = "Moved the downloaded model to private folder successfully: ";
                valueOf = String.valueOf(zza.getParent());
                gmsLogger2.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                this.zzark.zzby(zzlx);
                if (z && this.zzarl.zzb(zza)) {
                    zzaoz.d(str, "All old models are deleted.");
                    zza = this.zzarl.zzc(zza);
                }
                return zzbz(zza.getAbsolutePath());
            } finally {
                zza = this.zzark;
                zza.zzly();
            }
        } else {
            if (zzlz.intValue() == 16) {
                this.zzaqw.zza(zzmk.DOWNLOAD_FAILED, true);
                this.zzark.zzly();
            }
            return null;
        }
    }

    public final void zzlu() throws FirebaseMLException {
        this.zzarm.zzlu();
    }

    @WorkerThread
    private final MappedByteBuffer zzbz(@NonNull String str) throws FirebaseMLException {
        return this.zzarm.zzca(str);
    }
}
