package com.google.android.gms.internal.firebase_ml;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.ml.common.FirebaseMLException;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

@WorkerThread
public final class zzoi {
    private static final GmsLogger zzaoz = new GmsLogger("ModelLoader", "");
    @Nullable
    private final zzov zzaql;
    @Nullable
    private final zzog zzaqm;

    public zzoi(@Nullable zzov zzov, @Nullable zzog zzog) {
        boolean z = (zzov == null && zzog == null) ? false : true;
        Preconditions.checkArgument(z, "At least one of RemoteModelLoader or LocalModelLoader must be non-null.");
        this.zzaql = zzov;
        this.zzaqm = zzog;
    }

    public final synchronized <T> void zza(zzoj<T> zzoj) throws FirebaseMLException {
        boolean zza;
        Throwable th;
        List arrayList = new ArrayList();
        boolean z = false;
        Throwable e = null;
        try {
            zza = zza(zzoj, arrayList);
            th = null;
        } catch (Throwable e2) {
            th = e2;
            zza = false;
        }
        if (!zza) {
            try {
                z = zzb(zzoj, arrayList);
            } catch (Exception e3) {
                e = e3;
            }
            if (!z) {
                arrayList.add(zzmk.NO_VALID_MODEL);
                if (th != null) {
                    throw new FirebaseMLException("Remote model load failed: ", 14, th);
                } else if (e != null) {
                    throw new FirebaseMLException("Local model load failed: ", 14, e);
                } else {
                    throw new FirebaseMLException("Cannot load custom model", 14);
                }
            }
        }
    }

    private final synchronized <T> boolean zza(zzoj<T> zzoj, List<zzmk> list) throws FirebaseMLException {
        if (this.zzaql != null) {
            try {
                MappedByteBuffer load = this.zzaql.load();
                if (load != null) {
                    zzoj.zzb(load);
                    this.zzaql.zzlu();
                    zzaoz.d("ModelLoader", "Remote model source is loaded successfully");
                    return true;
                }
                zzaoz.d("ModelLoader", "Remote model source can NOT be loaded, try local model.");
                list.add(zzmk.CLOUD_MODEL_LOADER_LOADS_NO_MODEL);
            } catch (FirebaseMLException e) {
                zzaoz.d("ModelLoader", "Remote model source can NOT be loaded, try local model.");
                list.add(zzmk.CLOUD_MODEL_LOADER_ERROR);
                throw e;
            }
        }
        return false;
    }

    private final synchronized <T> boolean zzb(zzoj<T> zzoj, List<zzmk> list) throws FirebaseMLException {
        if (this.zzaqm != null) {
            MappedByteBuffer load = this.zzaqm.load();
            if (load != null) {
                try {
                    zzoj.zzb(load);
                    this.zzaqm.zzlu();
                    zzaoz.d("ModelLoader", "Local model source is loaded successfully");
                    return true;
                } catch (RuntimeException e) {
                    list.add(zzmk.LOCAL_MODEL_INVALID);
                    throw e;
                }
            }
        }
        return false;
    }
}
