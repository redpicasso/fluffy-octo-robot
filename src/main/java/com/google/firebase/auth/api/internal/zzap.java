package com.google.firebase.auth.api.internal;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzew;
import com.google.android.gms.internal.firebase_auth.zzf;
import com.google.android.gms.internal.firebase_auth.zzfe;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.android.gms.internal.firebase_auth.zzk;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.zza;
import com.google.firebase.auth.internal.zzaa;
import com.google.firebase.auth.internal.zzax;
import com.google.firebase.auth.internal.zzi;
import com.google.firebase.auth.internal.zzm;
import com.google.firebase.auth.internal.zzo;
import com.google.firebase.auth.internal.zzz;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public final class zzap extends zzag {
    private final Context zzml;
    private final zzee zzmm;
    private final Future<zzaj<zzee>> zzmn = zzdq();

    zzap(Context context, zzee zzee) {
        this.zzml = context;
        this.zzmm = zzee;
    }

    final Future<zzaj<zzee>> zzdq() {
        Future<zzaj<zzee>> future = this.zzmn;
        if (future != null) {
            return future;
        }
        return zzf.zzb().zza(zzk.zzm).submit(new zzdn(this.zzmm, this.zzml));
    }

    public final Task<GetTokenResult> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzax zzax) {
        zzan zzan = (zzbd) new zzbd(str).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zza(zzan), zzan);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, String str, @Nullable String str2, zza zza) {
        zzan zzan = (zzcp) new zzcp(str, str2).zza(firebaseApp).zzb(zza);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, AuthCredential authCredential, @Nullable String str, zza zza) {
        zzan zzan = (zzcn) new zzcn(authCredential, str).zza(firebaseApp).zzb(zza);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, AuthCredential authCredential, @Nullable String str, zzax zzax) {
        zzan zzan = (zzbn) new zzbn(authCredential, str).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, AuthCredential authCredential, @Nullable String str, zzax zzax) {
        zzan zzan = (zzbp) new zzbp(authCredential, str).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, zza zza, @Nullable String str) {
        zzan zzan = (zzcl) new zzcl(str).zza(firebaseApp).zzb(zza);
        return zza(zzb(zzan), zzan);
    }

    public final void zza(FirebaseApp firebaseApp, zzfe zzfe, OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, @Nullable Activity activity, Executor executor) {
        zzan zzan = (zzdl) new zzdl(zzfe).zza(firebaseApp).zza(onVerificationStateChangedCallbacks, activity, executor);
        zza(zzb(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, UserProfileChangeRequest userProfileChangeRequest, zzax zzax) {
        zzan zzan = (zzdh) new zzdh(userProfileChangeRequest).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzax zzax) {
        zzan zzan = (zzdb) new zzdb(str).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zzc(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzax zzax) {
        zzan zzan = (zzdd) new zzdd(str).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, PhoneAuthCredential phoneAuthCredential, zzax zzax) {
        zzan zzan = (zzdf) new zzdf(phoneAuthCredential).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, String str, String str2, String str3, zza zza) {
        zzan zzan = (zzax) new zzax(str, str2, str3).zza(firebaseApp).zzb(zza);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, String str, String str2, @Nullable String str3, zza zza) {
        zzan zzan = (zzcr) new zzcr(str, str2, str3).zza(firebaseApp).zzb(zza);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, EmailAuthCredential emailAuthCredential, zza zza) {
        zzan zzan = (zzct) new zzct(emailAuthCredential).zza(firebaseApp).zzb(zza);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, String str2, @Nullable String str3, zzax zzax) {
        zzan zzan = (zzbv) new zzbv(str, str2, str3).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, String str2, String str3, zzax zzax) {
        zzan zzan = (zzbx) new zzbx(str, str2, str3).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, EmailAuthCredential emailAuthCredential, zzax zzax) {
        zzan zzan = (zzbr) new zzbr(emailAuthCredential).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, EmailAuthCredential emailAuthCredential, zzax zzax) {
        zzan zzan = (zzbt) new zzbt(emailAuthCredential).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, PhoneAuthCredential phoneAuthCredential, @Nullable String str, zza zza) {
        zzan zzan = (zzcv) new zzcv(phoneAuthCredential, str).zza(firebaseApp).zzb(zza);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, PhoneAuthCredential phoneAuthCredential, @Nullable String str, zzax zzax) {
        zzan zzan = (zzbz) new zzbz(phoneAuthCredential, str).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zzb(FirebaseApp firebaseApp, FirebaseUser firebaseUser, PhoneAuthCredential phoneAuthCredential, @Nullable String str, zzax zzax) {
        zzan zzan = (zzcb) new zzcb(phoneAuthCredential, str).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    public final Task<SignInMethodQueryResult> zza(FirebaseApp firebaseApp, String str, @Nullable String str2) {
        zzan zzan = (zzbb) new zzbb(str, str2).zza(firebaseApp);
        return zza(zza(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, String str, ActionCodeSettings actionCodeSettings, @Nullable String str2) {
        actionCodeSettings.zzb(zzfw.PASSWORD_RESET);
        zzan zzan = (zzch) new zzch(str, actionCodeSettings, str2, "sendPasswordResetEmail").zza(firebaseApp);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zzb(FirebaseApp firebaseApp, String str, ActionCodeSettings actionCodeSettings, @Nullable String str2) {
        actionCodeSettings.zzb(zzfw.EMAIL_SIGNIN);
        zzan zzan = (zzch) new zzch(str, actionCodeSettings, str2, "sendSignInLinkToEmail").zza(firebaseApp);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, @Nullable ActionCodeSettings actionCodeSettings, String str) {
        zzan zzan = (zzcf) new zzcf(str, actionCodeSettings).zza(firebaseApp);
        return zza(zzb(zzan), zzan);
    }

    public final Task<ActionCodeResult> zzb(FirebaseApp firebaseApp, String str, @Nullable String str2) {
        zzan zzan = (zzat) new zzat(str, str2).zza(firebaseApp);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zzc(FirebaseApp firebaseApp, String str, @Nullable String str2) {
        zzan zzan = (zzar) new zzar(str, str2).zza(firebaseApp);
        return zza(zzb(zzan), zzan);
    }

    public final Task<String> zzd(FirebaseApp firebaseApp, String str, @Nullable String str2) {
        zzan zzan = (zzdj) new zzdj(str, str2).zza(firebaseApp);
        return zza(zzb(zzan), zzan);
    }

    public final Task<Void> zza(FirebaseApp firebaseApp, String str, String str2, @Nullable String str3) {
        zzan zzan = (zzav) new zzav(str, str2, str3).zza(firebaseApp);
        return zza(zzb(zzan), zzan);
    }

    public final Task<AuthResult> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, AuthCredential authCredential, zzax zzax) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotNull(authCredential);
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzax);
        List zzcw = firebaseUser.zzcw();
        if (zzcw != null && zzcw.contains(authCredential.getProvider())) {
            return Tasks.forException(zzdr.zzb(new Status(FirebaseError.ERROR_PROVIDER_ALREADY_LINKED)));
        }
        zzan zzan;
        if (authCredential instanceof EmailAuthCredential) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
            if (emailAuthCredential.zzcr()) {
                zzan = (zzbl) new zzbl(emailAuthCredential).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
                return zza(zzb(zzan), zzan);
            }
            zzan = (zzbf) new zzbf(emailAuthCredential).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
            return zza(zzb(zzan), zzan);
        } else if (authCredential instanceof PhoneAuthCredential) {
            zzan = (zzbj) new zzbj((PhoneAuthCredential) authCredential).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
            return zza(zzb(zzan), zzan);
        } else {
            Preconditions.checkNotNull(firebaseApp);
            Preconditions.checkNotNull(authCredential);
            Preconditions.checkNotNull(firebaseUser);
            Preconditions.checkNotNull(zzax);
            zzan = (zzbh) new zzbh(authCredential).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
            return zza(zzb(zzan), zzan);
        }
    }

    public final Task<AuthResult> zzd(FirebaseApp firebaseApp, FirebaseUser firebaseUser, String str, zzax zzax) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzax);
        List zzcw = firebaseUser.zzcw();
        if ((zzcw != null && !zzcw.contains(str)) || firebaseUser.isAnonymous()) {
            return Tasks.forException(zzdr.zzb(new Status(FirebaseError.ERROR_NO_SUCH_PROVIDER, str)));
        }
        Object obj = -1;
        if (str.hashCode() == 1216985755 && str.equals("password")) {
            obj = null;
        }
        zzan zzan;
        if (obj != null) {
            zzan = (zzcz) new zzcz(str).zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
            return zza(zzb(zzan), zzan);
        }
        zzan = (zzcx) new zzcx().zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zzb(zzan), zzan);
    }

    @NonNull
    public final Task<Void> zza(FirebaseApp firebaseApp, FirebaseUser firebaseUser, zzax zzax) {
        zzan zzan = (zzcd) new zzcd().zza(firebaseApp).zzf(firebaseUser).zzb(zzax).zza((zzz) zzax);
        return zza(zza(zzan), zzan);
    }

    @NonNull
    public final Task<Void> zza(FirebaseUser firebaseUser, zzaa zzaa) {
        zzan zzan = (zzaz) new zzaz().zzf(firebaseUser).zzb(zzaa).zza((zzz) zzaa);
        return zza(zzb(zzan), zzan);
    }

    @NonNull
    public final Task<Void> setFirebaseUIVersion(String str) {
        zzan zzcj = new zzcj(str);
        return zza(zzb(zzcj), zzcj);
    }

    @NonNull
    @VisibleForTesting
    static zzm zza(FirebaseApp firebaseApp, zzem zzem) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotNull(zzem);
        List arrayList = new ArrayList();
        arrayList.add(new zzi(zzem, FirebaseAuthProvider.PROVIDER_ID));
        List zzes = zzem.zzes();
        if (!(zzes == null || zzes.isEmpty())) {
            for (int i = 0; i < zzes.size(); i++) {
                arrayList.add(new zzi((zzew) zzes.get(i)));
            }
        }
        FirebaseUser zzm = new zzm(firebaseApp, arrayList);
        zzm.zza(new zzo(zzem.getLastSignInTimestamp(), zzem.getCreationTimestamp()));
        zzm.zzs(zzem.isNewUser());
        zzm.zzb(zzem.zzdo());
        zzm.zzb(com.google.firebase.auth.internal.zzap.zzg(zzem.zzbc()));
        return zzm;
    }

    @NonNull
    @VisibleForTesting
    private final <ResultT> Task<ResultT> zza(Task<ResultT> task, zzan<zzdp, ResultT> zzan) {
        return task.continueWithTask(new zzao(this, zzan));
    }
}
