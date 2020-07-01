package com.google.firebase.auth;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfe;
import com.google.android.gms.internal.firebase_auth.zzfw;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.google.firebase.auth.api.internal.zzap;
import com.google.firebase.auth.api.internal.zzdr;
import com.google.firebase.auth.api.internal.zzec;
import com.google.firebase.auth.api.internal.zzed;
import com.google.firebase.auth.api.internal.zzem;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.auth.internal.zzak;
import com.google.firebase.auth.internal.zzan;
import com.google.firebase.auth.internal.zzaq;
import com.google.firebase.auth.internal.zzas;
import com.google.firebase.auth.internal.zzat;
import com.google.firebase.auth.internal.zzau;
import com.google.firebase.auth.internal.zzax;
import com.google.firebase.auth.internal.zzg;
import com.google.firebase.auth.internal.zzj;
import com.google.firebase.auth.internal.zzm;
import com.google.firebase.auth.internal.zzz;
import com.google.firebase.internal.InternalTokenResult;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class FirebaseAuth implements InternalAuthProvider {
    private String zzhx;
    private String zzhy;
    private FirebaseApp zzik;
    private final List<IdTokenListener> zzil;
    private final List<com.google.firebase.auth.internal.IdTokenListener> zzim;
    private List<AuthStateListener> zzin;
    private zzap zzio;
    private FirebaseUser zzip;
    private zzj zziq;
    private final Object zzir;
    private final Object zzis;
    private final zzat zzit;
    private final zzak zziu;
    private zzas zziv;
    private zzau zziw;

    public interface AuthStateListener {
        void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth);
    }

    public interface IdTokenListener {
        void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth);
    }

    @VisibleForTesting
    class zzb implements com.google.firebase.auth.internal.zza {
        zzb() {
        }

        public final void zza(@NonNull zzes zzes, @NonNull FirebaseUser firebaseUser) {
            Preconditions.checkNotNull(zzes);
            Preconditions.checkNotNull(firebaseUser);
            firebaseUser.zza(zzes);
            FirebaseAuth.this.zza(firebaseUser, zzes, true);
        }
    }

    @VisibleForTesting
    class zza extends zzb implements com.google.firebase.auth.internal.zza, zzz {
        zza() {
            super();
        }

        public final void zza(Status status) {
            if (status.getStatusCode() == FirebaseError.ERROR_USER_NOT_FOUND || status.getStatusCode() == FirebaseError.ERROR_USER_TOKEN_EXPIRED || status.getStatusCode() == FirebaseError.ERROR_USER_DISABLED) {
                FirebaseAuth.this.signOut();
            }
        }
    }

    class zzc extends zzb implements com.google.firebase.auth.internal.zza, zzz {
        zzc(FirebaseAuth firebaseAuth) {
            super();
        }

        public final void zza(Status status) {
        }
    }

    @Keep
    public static FirebaseAuth getInstance() {
        return (FirebaseAuth) FirebaseApp.getInstance().get(FirebaseAuth.class);
    }

    @Keep
    public static FirebaseAuth getInstance(@NonNull FirebaseApp firebaseApp) {
        return (FirebaseAuth) firebaseApp.get(FirebaseAuth.class);
    }

    public FirebaseAuth(FirebaseApp firebaseApp) {
        this(firebaseApp, zzec.zza(firebaseApp.getApplicationContext(), new zzed(firebaseApp.getOptions().getApiKey()).zzef()), new zzat(firebaseApp.getApplicationContext(), firebaseApp.getPersistenceKey()), zzak.zzfn());
    }

    @VisibleForTesting
    private FirebaseAuth(FirebaseApp firebaseApp, zzap zzap, zzat zzat, zzak zzak) {
        this.zzir = new Object();
        this.zzis = new Object();
        this.zzik = (FirebaseApp) Preconditions.checkNotNull(firebaseApp);
        this.zzio = (zzap) Preconditions.checkNotNull(zzap);
        this.zzit = (zzat) Preconditions.checkNotNull(zzat);
        this.zziq = new zzj();
        this.zziu = (zzak) Preconditions.checkNotNull(zzak);
        this.zzil = new CopyOnWriteArrayList();
        this.zzim = new CopyOnWriteArrayList();
        this.zzin = new CopyOnWriteArrayList();
        this.zziw = zzau.zzfs();
        this.zzip = this.zzit.zzfr();
        FirebaseUser firebaseUser = this.zzip;
        if (firebaseUser != null) {
            zzes zzh = this.zzit.zzh(firebaseUser);
            if (zzh != null) {
                zza(this.zzip, zzh, false);
            }
        }
        this.zziu.zzf(this);
    }

    @Nullable
    public FirebaseUser getCurrentUser() {
        return this.zzip;
    }

    @Nullable
    public String getUid() {
        FirebaseUser firebaseUser = this.zzip;
        return firebaseUser == null ? null : firebaseUser.getUid();
    }

    public final void zza(@NonNull FirebaseUser firebaseUser, @NonNull zzes zzes, boolean z) {
        Object obj;
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(zzes);
        FirebaseUser firebaseUser2 = this.zzip;
        int i = 1;
        if (firebaseUser2 == null) {
            obj = 1;
        } else {
            int equals = firebaseUser2.zzcy().getAccessToken().equals(zzes.getAccessToken()) ^ 1;
            boolean equals2 = this.zzip.getUid().equals(firebaseUser.getUid());
            obj = (equals2 && equals == 0) ? null : 1;
            if (equals2) {
                i = 0;
            }
        }
        Preconditions.checkNotNull(firebaseUser);
        FirebaseUser firebaseUser3 = this.zzip;
        if (firebaseUser3 == null) {
            this.zzip = firebaseUser;
        } else {
            firebaseUser3.zza(firebaseUser.getProviderData());
            if (!firebaseUser.isAnonymous()) {
                this.zzip.zzcx();
            }
            this.zzip.zzb(firebaseUser.zzdb().zzdc());
        }
        if (z) {
            this.zzit.zzg(this.zzip);
        }
        if (obj != null) {
            firebaseUser2 = this.zzip;
            if (firebaseUser2 != null) {
                firebaseUser2.zza(zzes);
            }
            zzb(this.zzip);
        }
        if (i != 0) {
            zzc(this.zzip);
        }
        if (z) {
            this.zzit.zza(firebaseUser, zzes);
        }
        zzct().zzc(this.zzip.zzcy());
    }

    public final void zzcs() {
        FirebaseUser firebaseUser = this.zzip;
        if (firebaseUser != null) {
            zzat zzat = this.zzit;
            Preconditions.checkNotNull(firebaseUser);
            zzat.clear(String.format("com.google.firebase.auth.GET_TOKEN_RESPONSE.%s", new Object[]{firebaseUser.getUid()}));
            this.zzip = null;
        }
        this.zzit.clear("com.google.firebase.auth.FIREBASE_USER");
        zzb(null);
        zzc(null);
    }

    @VisibleForTesting
    private final synchronized void zza(zzas zzas) {
        this.zziv = zzas;
    }

    @VisibleForTesting
    private final synchronized zzas zzct() {
        if (this.zziv == null) {
            zza(new zzas(this.zzik));
        }
        return this.zziv;
    }

    public FirebaseApp getApp() {
        return this.zzik;
    }

    public final FirebaseApp zzcu() {
        return this.zzik;
    }

    public void addIdTokenListener(@NonNull IdTokenListener idTokenListener) {
        this.zzil.add(idTokenListener);
        this.zziw.execute(new zzj(this, idTokenListener));
    }

    @KeepForSdk
    public void addIdTokenListener(@NonNull com.google.firebase.auth.internal.IdTokenListener idTokenListener) {
        Preconditions.checkNotNull(idTokenListener);
        this.zzim.add(idTokenListener);
        zzct().zzj(this.zzim.size());
    }

    public void removeIdTokenListener(@NonNull IdTokenListener idTokenListener) {
        this.zzil.remove(idTokenListener);
    }

    @KeepForSdk
    public void removeIdTokenListener(@NonNull com.google.firebase.auth.internal.IdTokenListener idTokenListener) {
        Preconditions.checkNotNull(idTokenListener);
        this.zzim.remove(idTokenListener);
        zzct().zzj(this.zzim.size());
    }

    public void addAuthStateListener(@NonNull AuthStateListener authStateListener) {
        this.zzin.add(authStateListener);
        this.zziw.execute(new zzi(this, authStateListener));
    }

    public void removeAuthStateListener(@NonNull AuthStateListener authStateListener) {
        this.zzin.remove(authStateListener);
    }

    private final void zzb(@Nullable FirebaseUser firebaseUser) {
        String str = "FirebaseAuth";
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(uid).length() + 45);
            stringBuilder.append("Notifying id token listeners about user ( ");
            stringBuilder.append(uid);
            stringBuilder.append(" ).");
            Log.d(str, stringBuilder.toString());
        } else {
            Log.d(str, "Notifying id token listeners about a sign-out event.");
        }
        this.zziw.execute(new zzl(this, new InternalTokenResult(firebaseUser != null ? firebaseUser.zzda() : null)));
    }

    private final void zzc(@Nullable FirebaseUser firebaseUser) {
        String str = "FirebaseAuth";
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(uid).length() + 47);
            stringBuilder.append("Notifying auth state listeners about user ( ");
            stringBuilder.append(uid);
            stringBuilder.append(" ).");
            Log.d(str, stringBuilder.toString());
        } else {
            Log.d(str, "Notifying auth state listeners about a sign-out event.");
        }
        this.zziw.execute(new zzk(this));
    }

    @NonNull
    public Task<GetTokenResult> getAccessToken(boolean z) {
        return zza(this.zzip, z);
    }

    @NonNull
    public final Task<GetTokenResult> zza(@Nullable FirebaseUser firebaseUser, boolean z) {
        if (firebaseUser == null) {
            return Tasks.forException(zzdr.zzb(new Status(FirebaseError.ERROR_NO_SIGNED_IN_USER)));
        }
        zzes zzcy = firebaseUser.zzcy();
        if (!zzcy.isValid() || z) {
            return this.zzio.zza(this.zzik, firebaseUser, zzcy.zzs(), new zzn(this));
        }
        return Tasks.forResult(zzan.zzdf(zzcy.getAccessToken()));
    }

    @NonNull
    public Task<AuthResult> signInWithCredential(@NonNull AuthCredential authCredential) {
        Preconditions.checkNotNull(authCredential);
        if (authCredential instanceof EmailAuthCredential) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
            if (!emailAuthCredential.zzcr()) {
                return this.zzio.zzb(this.zzik, emailAuthCredential.getEmail(), emailAuthCredential.getPassword(), this.zzhy, new zzb());
            }
            if (zzbs(emailAuthCredential.zzco())) {
                return Tasks.forException(zzdr.zzb(new Status(17072)));
            }
            return this.zzio.zza(this.zzik, emailAuthCredential, new zzb());
        } else if (!(authCredential instanceof PhoneAuthCredential)) {
            return this.zzio.zza(this.zzik, authCredential, this.zzhy, new zzb());
        } else {
            return this.zzio.zza(this.zzik, (PhoneAuthCredential) authCredential, this.zzhy, new zzb());
        }
    }

    @NonNull
    public final Task<Void> zza(@NonNull FirebaseUser firebaseUser, @NonNull AuthCredential authCredential) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(authCredential);
        if (EmailAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
            if ("password".equals(emailAuthCredential.getSignInMethod())) {
                return this.zzio.zza(this.zzik, firebaseUser, emailAuthCredential.getEmail(), emailAuthCredential.getPassword(), firebaseUser.zzba(), new zza());
            } else if (zzbs(emailAuthCredential.zzco())) {
                return Tasks.forException(zzdr.zzb(new Status(17072)));
            } else {
                return this.zzio.zza(this.zzik, firebaseUser, emailAuthCredential, new zza());
            }
        } else if (authCredential instanceof PhoneAuthCredential) {
            return this.zzio.zza(this.zzik, firebaseUser, (PhoneAuthCredential) authCredential, this.zzhy, new zza());
        } else {
            return this.zzio.zza(this.zzik, firebaseUser, authCredential, firebaseUser.zzba(), new zza());
        }
    }

    public final Task<AuthResult> zzb(@NonNull FirebaseUser firebaseUser, @NonNull AuthCredential authCredential) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(authCredential);
        if (EmailAuthCredential.class.isAssignableFrom(authCredential.getClass())) {
            EmailAuthCredential emailAuthCredential = (EmailAuthCredential) authCredential;
            if ("password".equals(emailAuthCredential.getSignInMethod())) {
                return this.zzio.zzb(this.zzik, firebaseUser, emailAuthCredential.getEmail(), emailAuthCredential.getPassword(), firebaseUser.zzba(), new zza());
            } else if (zzbs(emailAuthCredential.zzco())) {
                return Tasks.forException(zzdr.zzb(new Status(17072)));
            } else {
                return this.zzio.zzb(this.zzik, firebaseUser, emailAuthCredential, new zza());
            }
        } else if (authCredential instanceof PhoneAuthCredential) {
            return this.zzio.zzb(this.zzik, firebaseUser, (PhoneAuthCredential) authCredential, this.zzhy, new zza());
        } else {
            return this.zzio.zzb(this.zzik, firebaseUser, authCredential, firebaseUser.zzba(), new zza());
        }
    }

    @NonNull
    public Task<AuthResult> signInWithCustomToken(@NonNull String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zza(this.zzik, str, this.zzhy, new zzb());
    }

    @NonNull
    public Task<AuthResult> signInWithEmailAndPassword(@NonNull String str, @NonNull String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zzio.zzb(this.zzik, str, str2, this.zzhy, new zzb());
    }

    @NonNull
    public Task<AuthResult> signInWithEmailLink(@NonNull String str, @NonNull String str2) {
        return signInWithCredential(EmailAuthProvider.getCredentialWithLink(str, str2));
    }

    @NonNull
    public Task<AuthResult> signInAnonymously() {
        FirebaseUser firebaseUser = this.zzip;
        if (firebaseUser == null || !firebaseUser.isAnonymous()) {
            return this.zzio.zza(this.zzik, new zzb(), this.zzhy);
        }
        zzm zzm = (zzm) this.zzip;
        zzm.zzs(false);
        return Tasks.forResult(new zzg(zzm));
    }

    public final void zza(@NonNull String str, long j, TimeUnit timeUnit, @NonNull OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks, @Nullable Activity activity, @NonNull Executor executor, boolean z) {
        long j2 = j;
        long convert = TimeUnit.SECONDS.convert(j, timeUnit);
        if (convert < 0 || convert > 120) {
            throw new IllegalArgumentException("We only support 0-120 seconds for sms-auto-retrieval timeout");
        }
        OnVerificationStateChangedCallbacks zzm;
        zzfe zzfe = new zzfe(str, convert, z, this.zzhx, this.zzhy);
        if (this.zziq.zzfe()) {
            String str2 = str;
            if (str.equals(this.zziq.getPhoneNumber())) {
                zzm = new zzm(this, onVerificationStateChangedCallbacks);
                this.zzio.zza(this.zzik, zzfe, zzm, activity, executor);
            }
        }
        zzm = onVerificationStateChangedCallbacks;
        this.zzio.zza(this.zzik, zzfe, zzm, activity, executor);
    }

    public Task<Void> updateCurrentUser(@NonNull FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            if (firebaseUser.zzba() == null || firebaseUser.zzba().equals(this.zzhy)) {
                String str = this.zzhy;
                if (str == null || str.equals(firebaseUser.zzba())) {
                    str = firebaseUser.zzcu().getOptions().getApiKey();
                    String apiKey = this.zzik.getOptions().getApiKey();
                    if (!firebaseUser.zzcy().isValid() || !apiKey.equals(str)) {
                        return zza(firebaseUser, new zzc(this));
                    }
                    zza(zzm.zza(this.zzik, firebaseUser), firebaseUser.zzcy(), true);
                    return Tasks.forResult(null);
                }
            }
            return Tasks.forException(zzdr.zzb(new Status(17072)));
        }
        throw new IllegalArgumentException("Cannot update current user with null user!");
    }

    public final Task<Void> zzd(@NonNull FirebaseUser firebaseUser) {
        return zza(firebaseUser, new zza());
    }

    @NonNull
    private final Task<Void> zza(@NonNull FirebaseUser firebaseUser, zzax zzax) {
        Preconditions.checkNotNull(firebaseUser);
        return this.zzio.zza(this.zzik, firebaseUser, zzax);
    }

    @NonNull
    public final Task<AuthResult> zzc(@NonNull FirebaseUser firebaseUser, @NonNull AuthCredential authCredential) {
        Preconditions.checkNotNull(authCredential);
        Preconditions.checkNotNull(firebaseUser);
        return this.zzio.zza(this.zzik, firebaseUser, authCredential, new zza());
    }

    @NonNull
    public final Task<AuthResult> zza(@NonNull FirebaseUser firebaseUser, @NonNull String str) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(firebaseUser);
        return this.zzio.zzd(this.zzik, firebaseUser, str, new zza());
    }

    @NonNull
    public Task<AuthResult> createUserWithEmailAndPassword(@NonNull String str, @NonNull String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zzio.zza(this.zzik, str, str2, this.zzhy, new zzb());
    }

    @NonNull
    public Task<SignInMethodQueryResult> fetchSignInMethodsForEmail(@NonNull String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zza(this.zzik, str, this.zzhy);
    }

    @NonNull
    public final Task<Void> zza(@NonNull FirebaseUser firebaseUser, @NonNull UserProfileChangeRequest userProfileChangeRequest) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(userProfileChangeRequest);
        return this.zzio.zza(this.zzik, firebaseUser, userProfileChangeRequest, new zza());
    }

    @NonNull
    public final Task<Void> zzb(@NonNull FirebaseUser firebaseUser, @NonNull String str) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotEmpty(str);
        return this.zzio.zzb(this.zzik, firebaseUser, str, new zza());
    }

    @NonNull
    public final Task<Void> zza(@NonNull FirebaseUser firebaseUser, @NonNull PhoneAuthCredential phoneAuthCredential) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotNull(phoneAuthCredential);
        return this.zzio.zza(this.zzik, firebaseUser, phoneAuthCredential, new zza());
    }

    @NonNull
    public final Task<Void> zzc(@NonNull FirebaseUser firebaseUser, @NonNull String str) {
        Preconditions.checkNotNull(firebaseUser);
        Preconditions.checkNotEmpty(str);
        return this.zzio.zzc(this.zzik, firebaseUser, str, new zza());
    }

    @NonNull
    public Task<Void> sendPasswordResetEmail(@NonNull String str) {
        Preconditions.checkNotEmpty(str);
        return sendPasswordResetEmail(str, null);
    }

    @NonNull
    public Task<Void> sendPasswordResetEmail(@NonNull String str, @Nullable ActionCodeSettings actionCodeSettings) {
        Preconditions.checkNotEmpty(str);
        if (actionCodeSettings == null) {
            actionCodeSettings = ActionCodeSettings.zzcj();
        }
        String str2 = this.zzhx;
        if (str2 != null) {
            actionCodeSettings.zzbq(str2);
        }
        actionCodeSettings.zzb(zzfw.PASSWORD_RESET);
        return this.zzio.zza(this.zzik, str, actionCodeSettings, this.zzhy);
    }

    public Task<Void> sendSignInLinkToEmail(@NonNull String str, @NonNull ActionCodeSettings actionCodeSettings) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(actionCodeSettings);
        if (actionCodeSettings.canHandleCodeInApp()) {
            String str2 = this.zzhx;
            if (str2 != null) {
                actionCodeSettings.zzbq(str2);
            }
            return this.zzio.zzb(this.zzik, str, actionCodeSettings, this.zzhy);
        }
        throw new IllegalArgumentException("You must set canHandleCodeInApp in your ActionCodeSettings to true for Email-Link Sign-in.");
    }

    public boolean isSignInWithEmailLink(@NonNull String str) {
        return EmailAuthCredential.isSignInWithEmailLink(str);
    }

    @NonNull
    public final Task<Void> zza(@Nullable ActionCodeSettings actionCodeSettings, @NonNull String str) {
        Preconditions.checkNotEmpty(str);
        if (this.zzhx != null) {
            if (actionCodeSettings == null) {
                actionCodeSettings = ActionCodeSettings.zzcj();
            }
            actionCodeSettings.zzbq(this.zzhx);
        }
        return this.zzio.zza(this.zzik, actionCodeSettings, str);
    }

    @NonNull
    public Task<ActionCodeResult> checkActionCode(@NonNull String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zzb(this.zzik, str, this.zzhy);
    }

    @NonNull
    public Task<Void> applyActionCode(@NonNull String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zzc(this.zzik, str, this.zzhy);
    }

    @NonNull
    public Task<String> verifyPasswordResetCode(@NonNull String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzio.zzd(this.zzik, str, this.zzhy);
    }

    @NonNull
    public Task<Void> confirmPasswordReset(@NonNull String str, @NonNull String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        return this.zzio.zza(this.zzik, str, str2, this.zzhy);
    }

    public Task<AuthResult> startActivityForSignInWithProvider(@NonNull Activity activity, @NonNull FederatedAuthProvider federatedAuthProvider) {
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(activity);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zziu.zza(activity, taskCompletionSource, this)) {
            return Tasks.forException(zzdr.zzb(new Status(17057)));
        }
        zzaq.zza(activity.getApplicationContext(), this);
        federatedAuthProvider.zza(activity);
        return taskCompletionSource.getTask();
    }

    public final Task<AuthResult> zza(@NonNull Activity activity, @NonNull FederatedAuthProvider federatedAuthProvider, @NonNull FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(firebaseUser);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zziu.zza(activity, taskCompletionSource, this, firebaseUser)) {
            return Tasks.forException(zzdr.zzb(new Status(17057)));
        }
        zzaq.zza(activity.getApplicationContext(), this, firebaseUser);
        federatedAuthProvider.zzb(activity);
        return taskCompletionSource.getTask();
    }

    public final Task<AuthResult> zzb(@NonNull Activity activity, @NonNull FederatedAuthProvider federatedAuthProvider, @NonNull FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(federatedAuthProvider);
        Preconditions.checkNotNull(firebaseUser);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        if (!this.zziu.zza(activity, taskCompletionSource, this, firebaseUser)) {
            return Tasks.forException(zzdr.zzb(new Status(17057)));
        }
        zzaq.zza(activity.getApplicationContext(), this, firebaseUser);
        federatedAuthProvider.zzc(activity);
        return taskCompletionSource.getTask();
    }

    @Nullable
    public Task<AuthResult> getPendingAuthResult() {
        return this.zziu.zzfo();
    }

    @NonNull
    public final Task<Void> zze(@NonNull FirebaseUser firebaseUser) {
        Preconditions.checkNotNull(firebaseUser);
        return this.zzio.zza(firebaseUser, new zzo(this, firebaseUser));
    }

    public void signOut() {
        zzcs();
        zzas zzas = this.zziv;
        if (zzas != null) {
            zzas.cancel();
        }
    }

    public void setLanguageCode(@NonNull String str) {
        Preconditions.checkNotEmpty(str);
        synchronized (this.zzir) {
            this.zzhx = str;
        }
    }

    @Nullable
    public String getLanguageCode() {
        String str;
        synchronized (this.zzir) {
            str = this.zzhx;
        }
        return str;
    }

    public final void zzf(@NonNull String str) {
        Preconditions.checkNotEmpty(str);
        synchronized (this.zzis) {
            this.zzhy = str;
        }
    }

    @Nullable
    public final String zzba() {
        String str;
        synchronized (this.zzis) {
            str = this.zzhy;
        }
        return str;
    }

    public void useAppLanguage() {
        synchronized (this.zzir) {
            this.zzhx = zzem.zzem();
        }
    }

    public FirebaseAuthSettings getFirebaseAuthSettings() {
        return this.zziq;
    }

    public Task<Void> setFirebaseUIVersion(@Nullable String str) {
        return this.zzio.setFirebaseUIVersion(str);
    }

    private final boolean zzbs(String str) {
        zzb zzbr = zzb.zzbr(str);
        return (zzbr == null || TextUtils.equals(this.zzhy, zzbr.zzba())) ? false : true;
    }
}
