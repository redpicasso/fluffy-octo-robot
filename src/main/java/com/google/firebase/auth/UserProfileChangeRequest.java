package com.google.firebase.auth;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "UserProfileChangeRequestCreator")
@Reserved({1})
public class UserProfileChangeRequest extends AbstractSafeParcelable {
    public static final Creator<UserProfileChangeRequest> CREATOR = new zzaf();
    @Field(getter = "getDisplayName", id = 2)
    private String zzjv;
    private Uri zzjz;
    @Field(getter = "shouldRemoveDisplayName", id = 4)
    private boolean zzka;
    @Field(getter = "shouldRemovePhotoUri", id = 5)
    private boolean zzkb;
    @Field(getter = "getPhotoUrl", id = 3)
    private String zzkc;

    public static class Builder {
        private String zzjv;
        private Uri zzjz;
        private boolean zzka;
        private boolean zzkb;

        public Builder setDisplayName(@Nullable String str) {
            if (str == null) {
                this.zzka = true;
            } else {
                this.zzjv = str;
            }
            return this;
        }

        public Builder setPhotoUri(@Nullable Uri uri) {
            if (uri == null) {
                this.zzkb = true;
            } else {
                this.zzjz = uri;
            }
            return this;
        }

        public UserProfileChangeRequest build() {
            String str = this.zzjv;
            Uri uri = this.zzjz;
            return new UserProfileChangeRequest(str, uri == null ? null : uri.toString(), this.zzka, this.zzkb);
        }
    }

    @Constructor
    UserProfileChangeRequest(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) boolean z, @Param(id = 5) boolean z2) {
        this.zzjv = str;
        this.zzkc = str2;
        this.zzka = z;
        this.zzkb = z2;
        this.zzjz = TextUtils.isEmpty(str2) ? null : Uri.parse(str2);
    }

    @Nullable
    public String getDisplayName() {
        return this.zzjv;
    }

    public final String zzam() {
        return this.zzkc;
    }

    @Nullable
    public Uri getPhotoUri() {
        return this.zzjz;
    }

    public final boolean zzde() {
        return this.zzka;
    }

    public final boolean zzdf() {
        return this.zzkb;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getDisplayName(), false);
        SafeParcelWriter.writeString(parcel, 3, this.zzkc, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzka);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzkb);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
