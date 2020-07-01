package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import androidx.collection.ArraySet;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.signin.SignInOptions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@KeepForSdk
@VisibleForTesting
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class ClientSettings {
    public static final String KEY_CLIENT_SESSION_ID = "com.google.android.gms.common.internal.ClientSettings.sessionId";
    private final Account account;
    private final Set<Scope> zaof;
    private final Set<Scope> zaog;
    private final Map<Api<?>, OptionalApiSettings> zaoh;
    private final int zaoi;
    private final View zaoj;
    private final String zaok;
    private final String zaol;
    private final SignInOptions zaom;
    private final boolean zaon;
    private Integer zaoo;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static final class Builder {
        private Account account;
        private Map<Api<?>, OptionalApiSettings> zaoh;
        private int zaoi = 0;
        private View zaoj;
        private String zaok;
        private String zaol;
        private SignInOptions zaom = SignInOptions.DEFAULT;
        private ArraySet<Scope> zaop;
        private boolean zaoq;

        public final Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public final Builder addRequiredScope(Scope scope) {
            if (this.zaop == null) {
                this.zaop = new ArraySet();
            }
            this.zaop.add(scope);
            return this;
        }

        public final Builder addAllRequiredScopes(Collection<Scope> collection) {
            if (this.zaop == null) {
                this.zaop = new ArraySet();
            }
            this.zaop.addAll((Collection) collection);
            return this;
        }

        public final Builder setOptionalApiSettingsMap(Map<Api<?>, OptionalApiSettings> map) {
            this.zaoh = map;
            return this;
        }

        public final Builder setGravityForPopups(int i) {
            this.zaoi = i;
            return this;
        }

        public final Builder setViewForPopups(View view) {
            this.zaoj = view;
            return this;
        }

        @KeepForSdk
        public final Builder setRealClientPackageName(String str) {
            this.zaok = str;
            return this;
        }

        public final Builder setRealClientClassName(String str) {
            this.zaol = str;
            return this;
        }

        public final Builder setSignInOptions(SignInOptions signInOptions) {
            this.zaom = signInOptions;
            return this;
        }

        public final Builder enableSignInClientDisconnectFix() {
            this.zaoq = true;
            return this;
        }

        @KeepForSdk
        public final ClientSettings build() {
            return new ClientSettings(this.account, this.zaop, this.zaoh, this.zaoi, this.zaoj, this.zaok, this.zaol, this.zaom, this.zaoq);
        }
    }

    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static final class OptionalApiSettings {
        public final Set<Scope> mScopes;

        public OptionalApiSettings(Set<Scope> set) {
            Preconditions.checkNotNull(set);
            this.mScopes = Collections.unmodifiableSet(set);
        }
    }

    @KeepForSdk
    public static ClientSettings createDefault(Context context) {
        return new com.google.android.gms.common.api.GoogleApiClient.Builder(context).buildClientSettings();
    }

    @KeepForSdk
    public ClientSettings(Account account, Set<Scope> set, Map<Api<?>, OptionalApiSettings> map, int i, View view, String str, String str2, SignInOptions signInOptions) {
        this(account, set, map, i, view, str, str2, signInOptions, false);
    }

    public ClientSettings(Account account, Set<Scope> set, Map<Api<?>, OptionalApiSettings> map, int i, View view, String str, String str2, SignInOptions signInOptions, boolean z) {
        Map map2;
        this.account = account;
        this.zaof = set == null ? Collections.emptySet() : Collections.unmodifiableSet(set);
        if (map2 == null) {
            map2 = Collections.emptyMap();
        }
        this.zaoh = map2;
        this.zaoj = view;
        this.zaoi = i;
        this.zaok = str;
        this.zaol = str2;
        this.zaom = signInOptions;
        this.zaon = z;
        Set hashSet = new HashSet(this.zaof);
        for (OptionalApiSettings optionalApiSettings : this.zaoh.values()) {
            hashSet.addAll(optionalApiSettings.mScopes);
        }
        this.zaog = Collections.unmodifiableSet(hashSet);
    }

    @KeepForSdk
    @Deprecated
    @Nullable
    public final String getAccountName() {
        Account account = this.account;
        return account != null ? account.name : null;
    }

    @KeepForSdk
    @Nullable
    public final Account getAccount() {
        return this.account;
    }

    @KeepForSdk
    public final Account getAccountOrDefault() {
        Account account = this.account;
        if (account != null) {
            return account;
        }
        return new Account("<<default account>>", "com.google");
    }

    @KeepForSdk
    public final int getGravityForPopups() {
        return this.zaoi;
    }

    @KeepForSdk
    public final Set<Scope> getRequiredScopes() {
        return this.zaof;
    }

    @KeepForSdk
    public final Set<Scope> getAllRequestedScopes() {
        return this.zaog;
    }

    public final Map<Api<?>, OptionalApiSettings> getOptionalApiSettings() {
        return this.zaoh;
    }

    @KeepForSdk
    @Nullable
    public final String getRealClientPackageName() {
        return this.zaok;
    }

    @Nullable
    public final String getRealClientClassName() {
        return this.zaol;
    }

    @KeepForSdk
    @Nullable
    public final View getViewForPopups() {
        return this.zaoj;
    }

    @Nullable
    public final SignInOptions getSignInOptions() {
        return this.zaom;
    }

    @Nullable
    public final Integer getClientSessionId() {
        return this.zaoo;
    }

    public final void setClientSessionId(Integer num) {
        this.zaoo = num;
    }

    @KeepForSdk
    public final Set<Scope> getApplicableScopes(Api<?> api) {
        OptionalApiSettings optionalApiSettings = (OptionalApiSettings) this.zaoh.get(api);
        if (optionalApiSettings == null || optionalApiSettings.mScopes.isEmpty()) {
            return this.zaof;
        }
        Set<Scope> hashSet = new HashSet(this.zaof);
        hashSet.addAll(optionalApiSettings.mScopes);
        return hashSet;
    }

    public final boolean isSignInClientDisconnectFixEnabled() {
        return this.zaon;
    }
}
