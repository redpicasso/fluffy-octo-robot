package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.google.firebase.annotations.PublicApi;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DatabaseError {
    @PublicApi
    public static final int DATA_STALE = -1;
    @PublicApi
    public static final int DISCONNECTED = -4;
    @PublicApi
    public static final int EXPIRED_TOKEN = -6;
    @PublicApi
    public static final int INVALID_TOKEN = -7;
    @PublicApi
    public static final int MAX_RETRIES = -8;
    @PublicApi
    public static final int NETWORK_ERROR = -24;
    @PublicApi
    public static final int OPERATION_FAILED = -2;
    @PublicApi
    public static final int OVERRIDDEN_BY_SET = -9;
    @PublicApi
    public static final int PERMISSION_DENIED = -3;
    @PublicApi
    public static final int UNAVAILABLE = -10;
    @PublicApi
    public static final int UNKNOWN_ERROR = -999;
    @PublicApi
    public static final int USER_CODE_EXCEPTION = -11;
    @PublicApi
    public static final int WRITE_CANCELED = -25;
    private static final Map<String, Integer> errorCodes = new HashMap();
    private static final Map<Integer, String> errorReasons = new HashMap();
    private final int code;
    private final String details;
    private final String message;

    static {
        Map map = errorReasons;
        Integer valueOf = Integer.valueOf(-1);
        map.put(valueOf, "The transaction needs to be run again with current data");
        map = errorReasons;
        Integer valueOf2 = Integer.valueOf(-2);
        map.put(valueOf2, "The server indicated that this operation failed");
        map = errorReasons;
        Integer valueOf3 = Integer.valueOf(-3);
        map.put(valueOf3, "This client does not have permission to perform this operation");
        map = errorReasons;
        Integer valueOf4 = Integer.valueOf(-4);
        map.put(valueOf4, "The operation had to be aborted due to a network disconnect");
        map = errorReasons;
        Integer valueOf5 = Integer.valueOf(-6);
        map.put(valueOf5, "The supplied auth token has expired");
        map = errorReasons;
        Integer valueOf6 = Integer.valueOf(-7);
        map.put(valueOf6, "The supplied auth token was invalid");
        map = errorReasons;
        Integer valueOf7 = Integer.valueOf(-8);
        map.put(valueOf7, "The transaction had too many retries");
        errorReasons.put(Integer.valueOf(-9), "The transaction was overridden by a subsequent set");
        errorReasons.put(Integer.valueOf(-10), "The service is unavailable");
        errorReasons.put(Integer.valueOf(-11), "User code called from the Firebase Database runloop threw an exception:\n");
        errorReasons.put(Integer.valueOf(-24), "The operation could not be performed due to a network error");
        errorReasons.put(Integer.valueOf(-25), "The write was canceled by the user.");
        errorReasons.put(Integer.valueOf(UNKNOWN_ERROR), "An unknown error occurred");
        errorCodes.put("datastale", valueOf);
        errorCodes.put("failure", valueOf2);
        errorCodes.put("permission_denied", valueOf3);
        errorCodes.put("disconnected", valueOf4);
        errorCodes.put("expired_token", valueOf5);
        errorCodes.put("invalid_token", valueOf6);
        errorCodes.put("maxretries", valueOf7);
        errorCodes.put("overriddenbyset", Integer.valueOf(-9));
        errorCodes.put("unavailable", Integer.valueOf(-10));
        errorCodes.put("network_error", Integer.valueOf(-24));
        errorCodes.put("write_canceled", Integer.valueOf(-25));
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static DatabaseError fromStatus(String str) {
        return fromStatus(str, null);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static DatabaseError fromStatus(String str, String str2) {
        return fromStatus(str, str2, null);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static DatabaseError fromCode(int i) {
        if (errorReasons.containsKey(Integer.valueOf(i))) {
            return new DatabaseError(i, (String) errorReasons.get(Integer.valueOf(i)), null);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Firebase Database error code: ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static DatabaseError fromStatus(String str, String str2, String str3) {
        Integer num = (Integer) errorCodes.get(str.toLowerCase());
        if (num == null) {
            num = Integer.valueOf(UNKNOWN_ERROR);
        }
        if (str2 == null) {
            str2 = (String) errorReasons.get(num);
        }
        return new DatabaseError(num.intValue(), str2, str3);
    }

    @PublicApi
    public static DatabaseError fromException(Throwable th) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String) errorReasons.get(Integer.valueOf(-11)));
        stringBuilder.append(stringWriter.toString());
        return new DatabaseError(-11, stringBuilder.toString());
    }

    private DatabaseError(int i, String str) {
        this(i, str, null);
    }

    private DatabaseError(int i, String str, String str2) {
        this.code = i;
        this.message = str;
        if (str2 == null) {
            str2 = "";
        }
        this.details = str2;
    }

    @PublicApi
    public int getCode() {
        return this.code;
    }

    @PublicApi
    @NonNull
    public String getMessage() {
        return this.message;
    }

    @PublicApi
    @NonNull
    public String getDetails() {
        return this.details;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DatabaseError: ");
        stringBuilder.append(this.message);
        return stringBuilder.toString();
    }

    @PublicApi
    public DatabaseException toException() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Firebase Database error: ");
        stringBuilder.append(this.message);
        return new DatabaseException(stringBuilder.toString());
    }
}
