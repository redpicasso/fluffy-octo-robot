package com.google.firebase.ml.common.modeldownload;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.FirebaseApp;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(16)
public class FirebaseModelManager {
    private static final GmsLogger zzaoz = new GmsLogger("FirebaseModelManager", "");
    @GuardedBy("FirebaseModelManager.class")
    private static Map<String, FirebaseModelManager> zzax = new HashMap();
    @GuardedBy("this")
    private Map<String, FirebaseRemoteModel> zzarz = new HashMap();
    @GuardedBy("this")
    private Map<String, FirebaseRemoteModel> zzasa = new HashMap();
    @GuardedBy("this")
    private Map<String, FirebaseLocalModel> zzasb = new HashMap();

    public static synchronized FirebaseModelManager getInstance() {
        FirebaseModelManager zzf;
        synchronized (FirebaseModelManager.class) {
            zzf = zzf(FirebaseApp.getInstance());
        }
        return zzf;
    }

    public static synchronized FirebaseModelManager zzf(@NonNull FirebaseApp firebaseApp) {
        synchronized (FirebaseModelManager.class) {
            Preconditions.checkNotNull(firebaseApp, "Please provide a valid FirebaseApp");
            String persistenceKey = firebaseApp.getPersistenceKey();
            if (zzax.containsKey(persistenceKey)) {
                FirebaseModelManager firebaseModelManager = (FirebaseModelManager) zzax.get(persistenceKey);
                return firebaseModelManager;
            }
            FirebaseModelManager firebaseModelManager2 = new FirebaseModelManager();
            zzax.put(persistenceKey, firebaseModelManager2);
            return firebaseModelManager2;
        }
    }

    private FirebaseModelManager() {
    }

    public synchronized boolean registerRemoteModel(@NonNull FirebaseRemoteModel firebaseRemoteModel) {
        Preconditions.checkNotNull(firebaseRemoteModel, "FirebaseRemoteModel can not be null");
        GmsLogger gmsLogger;
        String str;
        String str2;
        String valueOf;
        if (firebaseRemoteModel.zzmk()) {
            if (this.zzasa.containsKey(firebaseRemoteModel.zzmj())) {
                gmsLogger = zzaoz;
                str = "FirebaseModelManager";
                str2 = "The base model is already registered: ";
                valueOf = String.valueOf(firebaseRemoteModel.zzmj());
                gmsLogger.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                return false;
            }
            this.zzasa.put(firebaseRemoteModel.zzmj(), firebaseRemoteModel);
        } else if (this.zzarz.containsKey(firebaseRemoteModel.zzmj())) {
            gmsLogger = zzaoz;
            str = "FirebaseModelManager";
            str2 = "The remote model name is already registered: ";
            valueOf = String.valueOf(firebaseRemoteModel.zzmj());
            gmsLogger.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return false;
        } else {
            this.zzarz.put(firebaseRemoteModel.zzmj(), firebaseRemoteModel);
        }
        return true;
    }

    public synchronized boolean registerLocalModel(@NonNull FirebaseLocalModel firebaseLocalModel) {
        Preconditions.checkNotNull(firebaseLocalModel, "FirebaseLocalModel can not be null");
        if (this.zzasb.containsKey(firebaseLocalModel.getModelName())) {
            GmsLogger gmsLogger = zzaoz;
            String str = "FirebaseModelManager";
            String str2 = "The local model name is already registered: ";
            String valueOf = String.valueOf(firebaseLocalModel.getModelName());
            gmsLogger.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return false;
        }
        this.zzasb.put(firebaseLocalModel.getModelName(), firebaseLocalModel);
        return true;
    }

    @Nullable
    public final synchronized FirebaseRemoteModel zzcb(@NonNull String str) {
        return (FirebaseRemoteModel) this.zzarz.get(str);
    }

    @Nullable
    public final synchronized FirebaseLocalModel zzcc(@NonNull String str) {
        return (FirebaseLocalModel) this.zzasb.get(str);
    }
}
