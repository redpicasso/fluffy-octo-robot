package com.google.firebase.auth.api.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;

public final class zzey {
    private final String packageName;
    private final String zzqu;

    public zzey(Context context) {
        this(context, context.getPackageName());
    }

    private zzey(Context context, String str) {
        String str2 = "FBA-PackageInfo";
        Preconditions.checkNotNull(context);
        this.packageName = Preconditions.checkNotEmpty(str);
        String str3;
        try {
            byte[] packageCertificateHashBytes = AndroidUtilsLight.getPackageCertificateHashBytes(context, this.packageName);
            if (packageCertificateHashBytes == null) {
                str3 = "single cert required: ";
                str = String.valueOf(str);
                Log.e(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
                this.zzqu = null;
                return;
            }
            this.zzqu = Hex.bytesToStringUppercase(packageCertificateHashBytes, false);
        } catch (NameNotFoundException unused) {
            str3 = "no pkg: ";
            str = String.valueOf(str);
            Log.e(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
            this.zzqu = null;
        }
    }

    public final String getPackageName() {
        return this.packageName;
    }

    @Nullable
    public final String zzeo() {
        return this.zzqu;
    }
}
