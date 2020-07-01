package com.google.firebase.storage;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.FirebaseException;
import com.google.firebase.annotations.PublicApi;
import com.google.logging.type.LogSeverity;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class StorageException extends FirebaseException {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    @PublicApi
    public static final int ERROR_BUCKET_NOT_FOUND = -13011;
    @PublicApi
    public static final int ERROR_CANCELED = -13040;
    @PublicApi
    public static final int ERROR_INVALID_CHECKSUM = -13031;
    @PublicApi
    public static final int ERROR_NOT_AUTHENTICATED = -13020;
    @PublicApi
    public static final int ERROR_NOT_AUTHORIZED = -13021;
    @PublicApi
    public static final int ERROR_OBJECT_NOT_FOUND = -13010;
    @PublicApi
    public static final int ERROR_PROJECT_NOT_FOUND = -13012;
    @PublicApi
    public static final int ERROR_QUOTA_EXCEEDED = -13013;
    @PublicApi
    public static final int ERROR_RETRY_LIMIT_EXCEEDED = -13030;
    @PublicApi
    public static final int ERROR_UNKNOWN = -13000;
    private static final int NETWORK_UNAVAILABLE = -2;
    private static final String TAG = "StorageException";
    static IOException sCancelException = new IOException("The operation was canceled.");
    private Throwable mCause;
    private String mDetailMessage;
    private final int mErrorCode;
    private final int mHttpResultCode;

    @PublicApi
    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    public @interface ErrorCode {
    }

    static String getErrorMessageForCode(int i) {
        if (i == ERROR_CANCELED) {
            return "The operation was cancelled.";
        }
        String str = "An unknown error occurred, please check the HTTP result code and inner exception for server response.";
        if (i == ERROR_UNKNOWN) {
            return str;
        }
        if (i == ERROR_INVALID_CHECKSUM) {
            return "Object has a checksum which does not match. Please retry the operation.";
        }
        if (i == ERROR_RETRY_LIMIT_EXCEEDED) {
            return "The operation retry limit has been exceeded.";
        }
        if (i == ERROR_NOT_AUTHORIZED) {
            return "User does not have permission to access this object.";
        }
        if (i == ERROR_NOT_AUTHENTICATED) {
            return "User is not authenticated, please authenticate using Firebase Authentication and try again.";
        }
        switch (i) {
            case ERROR_QUOTA_EXCEEDED /*-13013*/:
                return "Quota for bucket exceeded, please view quota on www.firebase.google.com/storage.";
            case ERROR_PROJECT_NOT_FOUND /*-13012*/:
                return "Project does not exist.";
            case ERROR_BUCKET_NOT_FOUND /*-13011*/:
                return "Bucket does not exist.";
            case ERROR_OBJECT_NOT_FOUND /*-13010*/:
                return "Object does not exist at location.";
            default:
                return str;
        }
    }

    private static boolean isResultSuccess(int i) {
        return i == 0 || (i >= LogSeverity.INFO_VALUE && i < 300);
    }

    StorageException(int i, Throwable th, int i2) {
        this.mDetailMessage = getErrorMessageForCode(i);
        this.mCause = th;
        this.mErrorCode = i;
        this.mHttpResultCode = i2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("StorageException has occurred.\n");
        stringBuilder.append(this.mDetailMessage);
        stringBuilder.append("\n Code: ");
        stringBuilder.append(Integer.toString(this.mErrorCode));
        stringBuilder.append(" HttpResult: ");
        stringBuilder.append(Integer.toString(this.mHttpResultCode));
        String stringBuilder2 = stringBuilder.toString();
        String str = TAG;
        Log.e(str, stringBuilder2);
        Throwable th2 = this.mCause;
        if (th2 != null) {
            Log.e(str, th2.getMessage(), this.mCause);
        }
    }

    private static int calculateErrorCode(Status status) {
        if (status.isCanceled()) {
            return ERROR_CANCELED;
        }
        return status.equals(Status.RESULT_TIMEOUT) ? ERROR_RETRY_LIMIT_EXCEEDED : ERROR_UNKNOWN;
    }

    private static int calculateErrorCode(@Nullable Throwable th, int i) {
        if (th instanceof CancelException) {
            return ERROR_CANCELED;
        }
        if (i == -2) {
            return ERROR_RETRY_LIMIT_EXCEEDED;
        }
        if (i == 401) {
            return ERROR_NOT_AUTHENTICATED;
        }
        if (i == 409) {
            return ERROR_INVALID_CHECKSUM;
        }
        if (i != 403) {
            return i != 404 ? ERROR_UNKNOWN : ERROR_OBJECT_NOT_FOUND;
        } else {
            return ERROR_NOT_AUTHORIZED;
        }
    }

    @PublicApi
    @NonNull
    public static StorageException fromErrorStatus(@NonNull Status status) {
        Preconditions.checkNotNull(status);
        Preconditions.checkArgument(status.isSuccess() ^ 1);
        return new StorageException(calculateErrorCode(status), null, 0);
    }

    @PublicApi
    @Nullable
    public static StorageException fromExceptionAndHttpCode(@Nullable Throwable th, int i) {
        if (th instanceof StorageException) {
            return (StorageException) th;
        }
        if (isResultSuccess(i) && th == null) {
            return null;
        }
        return new StorageException(calculateErrorCode(th, i), th, i);
    }

    @PublicApi
    @NonNull
    public static StorageException fromException(@NonNull Throwable th) {
        return fromExceptionAndHttpCode(th, 0);
    }

    @PublicApi
    public String getMessage() {
        return this.mDetailMessage;
    }

    @PublicApi
    public synchronized Throwable getCause() {
        if (this.mCause == this) {
            return null;
        }
        return this.mCause;
    }

    @PublicApi
    public int getErrorCode() {
        return this.mErrorCode;
    }

    @PublicApi
    public int getHttpResultCode() {
        return this.mHttpResultCode;
    }

    @PublicApi
    public boolean getIsRecoverableException() {
        return getErrorCode() == ERROR_RETRY_LIMIT_EXCEEDED;
    }
}
