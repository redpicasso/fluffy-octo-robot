package com.google.firebase.storage.internal;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.storage.network.NetworkRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class Util {
    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final int MAXIMUM_TOKEN_WAIT_TIME_MS = 30000;
    public static final int NETWORK_UNAVAILABLE = -2;
    private static final String TAG = "StorageUtil";

    public static long parseDateTime(@Nullable String str) {
        long j = 0;
        if (str == null) {
            return 0;
        }
        str = str.replaceAll("Z$", "-0000");
        try {
            j = new SimpleDateFormat(ISO_8601_FORMAT, Locale.getDefault()).parse(str).getTime();
            return j;
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unable to parse datetime:");
            stringBuilder.append(str);
            Log.w(TAG, stringBuilder.toString(), e);
            return j;
        }
    }

    public static boolean equals(@Nullable Object obj, @Nullable Object obj2) {
        return Objects.equal(obj, obj2);
    }

    private static String getAuthority() throws RemoteException {
        return NetworkRequest.getAuthority();
    }

    @Nullable
    public static Uri normalize(@NonNull FirebaseApp firebaseApp, @Nullable String str) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String str2 = "gs://";
        String preserveSlashEncode;
        if (str.toLowerCase().startsWith(str2)) {
            preserveSlashEncode = SlashUtil.preserveSlashEncode(SlashUtil.normalizeSlashes(str.substring(5)));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(preserveSlashEncode);
            return Uri.parse(stringBuilder.toString());
        }
        Uri parse = Uri.parse(str);
        str = parse.getScheme();
        str2 = TAG;
        if (str == null || !(equals(str.toLowerCase(), UriUtil.HTTP_SCHEME) || equals(str.toLowerCase(), UriUtil.HTTPS_SCHEME))) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("FirebaseStorage is unable to support the scheme:");
            stringBuilder2.append(str);
            Log.w(str2, stringBuilder2.toString());
            throw new IllegalArgumentException("Uri scheme");
        }
        try {
            int indexOf = parse.getAuthority().toLowerCase().indexOf(getAuthority());
            String slashize = SlashUtil.slashize(parse.getEncodedPath());
            String str3 = "Firebase Storage URLs must point to an object in your Storage Bucket. Please obtain a URL using the Firebase Console or getDownloadUrl().";
            if (indexOf == 0) {
                String str4 = "/";
                if (slashize.startsWith(str4)) {
                    int indexOf2 = slashize.indexOf("/b/", 0);
                    indexOf = indexOf2 + 3;
                    int indexOf3 = slashize.indexOf(str4, indexOf);
                    int indexOf4 = slashize.indexOf("/o/", 0);
                    if (indexOf2 == -1 || indexOf3 == -1) {
                        Log.w(str2, str3);
                        throw new IllegalArgumentException(str3);
                    }
                    preserveSlashEncode = slashize.substring(indexOf, indexOf3);
                    slashize = indexOf4 != -1 ? slashize.substring(indexOf4 + 3) : "";
                    Preconditions.checkNotEmpty(preserveSlashEncode, "No bucket specified");
                    return new Builder().scheme("gs").authority(preserveSlashEncode).encodedPath(slashize).build();
                }
            }
            if (indexOf > 1) {
                preserveSlashEncode = parse.getAuthority().substring(0, indexOf - 1);
                Preconditions.checkNotEmpty(preserveSlashEncode, "No bucket specified");
                return new Builder().scheme("gs").authority(preserveSlashEncode).encodedPath(slashize).build();
            }
            Log.w(str2, str3);
            throw new IllegalArgumentException(str3);
        } catch (RemoteException unused) {
            throw new UnsupportedEncodingException("Could not parse Url because the Storage network layer did not load");
        }
    }

    @Nullable
    public static String getCurrentAuthToken(@Nullable InternalAuthProvider internalAuthProvider) {
        Object token;
        StringBuilder stringBuilder;
        String str = TAG;
        if (internalAuthProvider != null) {
            try {
                token = ((GetTokenResult) Tasks.await(internalAuthProvider.getAccessToken(false), 30000, TimeUnit.MILLISECONDS)).getToken();
            } catch (ExecutionException e) {
                token = e;
                stringBuilder = new StringBuilder();
                stringBuilder.append("error getting token ");
                stringBuilder.append(token);
                Log.e(str, stringBuilder.toString());
                return null;
            } catch (InterruptedException e2) {
                token = e2;
                stringBuilder = new StringBuilder();
                stringBuilder.append("error getting token ");
                stringBuilder.append(token);
                Log.e(str, stringBuilder.toString());
                return null;
            } catch (TimeoutException e3) {
                token = e3;
                stringBuilder = new StringBuilder();
                stringBuilder.append("error getting token ");
                stringBuilder.append(token);
                Log.e(str, stringBuilder.toString());
                return null;
            }
        }
        token = null;
        if (!TextUtils.isEmpty(token)) {
            return token;
        }
        Log.w(str, "no auth token for request");
        return null;
    }
}
