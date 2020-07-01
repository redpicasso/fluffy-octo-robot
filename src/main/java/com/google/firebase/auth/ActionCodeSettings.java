package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.internal.firebase_auth.zzfw;

@Class(creator = "ActionCodeSettingsCreator")
public class ActionCodeSettings extends AbstractSafeParcelable {
    public static final Creator<ActionCodeSettings> CREATOR = new zzc();
    @Field(getter = "getUrl", id = 1)
    private final String url;
    @Field(getter = "getIOSBundle", id = 2)
    private final String zzhk;
    @Field(getter = "getIOSAppStoreId", id = 3)
    private final String zzhl;
    @Field(getter = "getAndroidPackageName", id = 4)
    private final String zzhm;
    @Field(getter = "getAndroidInstallApp", id = 5)
    private final boolean zzhn;
    @Field(getter = "getAndroidMinimumVersion", id = 6)
    private final String zzho;
    @Field(getter = "canHandleCodeInApp", id = 7)
    private final boolean zzhp;
    @Field(getter = "getLocaleHeader", id = 8)
    private String zzhq;
    @Field(getter = "getRequestType", id = 9)
    private int zzhr;
    @Field(getter = "getDynamicLinkDomain", id = 10)
    private String zzhs;

    public static class Builder {
        private String url;
        private String zzhk;
        private String zzhm;
        private boolean zzhn;
        private String zzho;
        private boolean zzhp;
        private String zzhs;

        private Builder() {
            this.zzhp = false;
        }

        public Builder setUrl(@NonNull String str) {
            this.url = str;
            return this;
        }

        public Builder setIOSBundleId(@NonNull String str) {
            this.zzhk = str;
            return this;
        }

        public Builder setAndroidPackageName(@NonNull String str, boolean z, @Nullable String str2) {
            this.zzhm = str;
            this.zzhn = z;
            this.zzho = str2;
            return this;
        }

        public Builder setHandleCodeInApp(boolean z) {
            this.zzhp = z;
            return this;
        }

        public Builder setDynamicLinkDomain(String str) {
            this.zzhs = str;
            return this;
        }

        public ActionCodeSettings build() {
            if (this.url != null) {
                return new ActionCodeSettings(this, null);
            }
            throw new IllegalArgumentException("Cannot build ActionCodeSettings with null URL. Call #setUrl(String) before calling build()");
        }

        /* synthetic */ Builder(zza zza) {
            this();
        }
    }

    @Constructor
    ActionCodeSettings(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) String str3, @Param(id = 4) String str4, @Param(id = 5) boolean z, @Param(id = 6) String str5, @Param(id = 7) boolean z2, @Param(id = 8) String str6, @Param(id = 9) int i, @Param(id = 10) String str7) {
        this.url = str;
        this.zzhk = str2;
        this.zzhl = str3;
        this.zzhm = str4;
        this.zzhn = z;
        this.zzho = str5;
        this.zzhp = z2;
        this.zzhq = str6;
        this.zzhr = i;
        this.zzhs = str7;
    }

    private ActionCodeSettings(Builder builder) {
        this.url = builder.url;
        this.zzhk = builder.zzhk;
        this.zzhl = null;
        this.zzhm = builder.zzhm;
        this.zzhn = builder.zzhn;
        this.zzho = builder.zzho;
        this.zzhp = builder.zzhp;
        this.zzhs = builder.zzhs;
    }

    public static ActionCodeSettings zzcj() {
        return new ActionCodeSettings(new Builder());
    }

    public String getUrl() {
        return this.url;
    }

    public String getIOSBundle() {
        return this.zzhk;
    }

    public final String zzck() {
        return this.zzhl;
    }

    public String getAndroidPackageName() {
        return this.zzhm;
    }

    public boolean getAndroidInstallApp() {
        return this.zzhn;
    }

    public String getAndroidMinimumVersion() {
        return this.zzho;
    }

    public boolean canHandleCodeInApp() {
        return this.zzhp;
    }

    public final void zzbq(@NonNull String str) {
        this.zzhq = str;
    }

    public final String zzcl() {
        return this.zzhq;
    }

    public final void zzb(@NonNull zzfw zzfw) {
        this.zzhr = zzfw.zzbq();
    }

    public final int getRequestType() {
        return this.zzhr;
    }

    public final String zzcm() {
        return this.zzhs;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getUrl(), false);
        SafeParcelWriter.writeString(parcel, 2, getIOSBundle(), false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhl, false);
        SafeParcelWriter.writeString(parcel, 4, getAndroidPackageName(), false);
        SafeParcelWriter.writeBoolean(parcel, 5, getAndroidInstallApp());
        SafeParcelWriter.writeString(parcel, 6, getAndroidMinimumVersion(), false);
        SafeParcelWriter.writeBoolean(parcel, 7, canHandleCodeInApp());
        SafeParcelWriter.writeString(parcel, 8, this.zzhq, false);
        SafeParcelWriter.writeInt(parcel, 9, this.zzhr);
        SafeParcelWriter.writeString(parcel, 10, this.zzhs, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    /* synthetic */ ActionCodeSettings(Builder builder, zza zza) {
        this(builder);
    }
}
