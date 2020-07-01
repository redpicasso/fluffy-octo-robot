package com.google.android.gms.internal.firebase_auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp.zzl;
import com.google.android.gms.internal.firebase_auth.zzp.zzl.zza;
import com.google.firebase.auth.api.internal.zzfd;
import java.util.Arrays;
import java.util.List;

public final class zzfg implements zzfd<zzl> {
    @Nullable
    private String zzhy;
    private String zzib;
    private String zzif;
    private String zzig;
    private String zzjv;
    private String zzkc;
    private zzfk zzsh = new zzfk();
    private zzfk zzsi = new zzfk();
    private boolean zzsj = true;
    private String zzsk;

    public final boolean zzcp(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzsi.zzez().contains(str);
    }

    @Nullable
    public final String getEmail() {
        return this.zzif;
    }

    @Nullable
    public final String getPassword() {
        return this.zzig;
    }

    @Nullable
    public final String getDisplayName() {
        return this.zzjv;
    }

    @Nullable
    public final String zzam() {
        return this.zzkc;
    }

    @NonNull
    public final zzfg zzcq(String str) {
        this.zzib = Preconditions.checkNotEmpty(str);
        return this;
    }

    @NonNull
    public final zzfg zzcr(@Nullable String str) {
        if (str == null) {
            this.zzsi.zzez().add("EMAIL");
        } else {
            this.zzif = str;
        }
        return this;
    }

    @NonNull
    public final zzfg zzcs(@Nullable String str) {
        if (str == null) {
            this.zzsi.zzez().add("PASSWORD");
        } else {
            this.zzig = str;
        }
        return this;
    }

    @NonNull
    public final zzfg zzct(@Nullable String str) {
        if (str == null) {
            this.zzsi.zzez().add("DISPLAY_NAME");
        } else {
            this.zzjv = str;
        }
        return this;
    }

    @NonNull
    public final zzfg zzcu(@Nullable String str) {
        if (str == null) {
            this.zzsi.zzez().add("PHOTO_URL");
        } else {
            this.zzkc = str;
        }
        return this;
    }

    @NonNull
    public final zzfg zzcv(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzsh.zzez().add(str);
        return this;
    }

    @NonNull
    public final zzfg zzcw(String str) {
        this.zzsk = Preconditions.checkNotEmpty(str);
        return this;
    }

    @NonNull
    public final zzfg zzcx(@Nullable String str) {
        this.zzhy = str;
        return this;
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzd = zzl.zzaj().zzf(this.zzsj).zzd(this.zzsh.zzez());
        List zzez = this.zzsi.zzez();
        zzv[] zzvArr = new zzv[zzez.size()];
        for (int i = 0; i < zzez.size(); i++) {
            zzv zzv;
            String str = (String) zzez.get(i);
            int i2 = -1;
            switch (str.hashCode()) {
                case -333046776:
                    if (str.equals("DISPLAY_NAME")) {
                        i2 = 1;
                        break;
                    }
                    break;
                case 66081660:
                    if (str.equals("EMAIL")) {
                        i2 = 0;
                        break;
                    }
                    break;
                case 1939891618:
                    if (str.equals("PHOTO_URL")) {
                        i2 = 3;
                        break;
                    }
                    break;
                case 1999612571:
                    if (str.equals("PASSWORD")) {
                        i2 = 2;
                        break;
                    }
                    break;
            }
            if (i2 == 0) {
                zzv = zzv.EMAIL;
            } else if (i2 == 1) {
                zzv = zzv.DISPLAY_NAME;
            } else if (i2 == 2) {
                zzv = zzv.PASSWORD;
            } else if (i2 != 3) {
                zzv = zzv.USER_ATTRIBUTE_NAME_UNSPECIFIED;
            } else {
                zzv = zzv.PHOTO_URL;
            }
            zzvArr[i] = zzv;
        }
        zzhs.zza zzc = zzd.zzc(Arrays.asList(zzvArr));
        String str2 = this.zzib;
        if (str2 != null) {
            zzc.zzap(str2);
        }
        str2 = this.zzif;
        if (str2 != null) {
            zzc.zzar(str2);
        }
        str2 = this.zzig;
        if (str2 != null) {
            zzc.zzas(str2);
        }
        str2 = this.zzjv;
        if (str2 != null) {
            zzc.zzaq(str2);
        }
        str2 = this.zzkc;
        if (str2 != null) {
            zzc.zzau(str2);
        }
        str2 = this.zzsk;
        if (str2 != null) {
            zzc.zzat(str2);
        }
        str2 = this.zzhy;
        if (str2 != null) {
            zzc.zzav(str2);
        }
        return (zzl) ((zzhs) zzc.zzih());
    }
}
