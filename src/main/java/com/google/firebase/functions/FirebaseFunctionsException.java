package com.google.firebase.functions;

import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.brentvatne.react.ReactVideoView;
import com.google.firebase.FirebaseException;
import com.google.logging.type.LogSeverity;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
public class FirebaseFunctionsException extends FirebaseException {
    @NonNull
    private final Code code;
    @Nullable
    private final Object details;

    /* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
    public enum Code {
        OK(0),
        CANCELLED(1),
        UNKNOWN(2),
        INVALID_ARGUMENT(3),
        DEADLINE_EXCEEDED(4),
        NOT_FOUND(5),
        ALREADY_EXISTS(6),
        PERMISSION_DENIED(7),
        RESOURCE_EXHAUSTED(8),
        FAILED_PRECONDITION(9),
        ABORTED(10),
        OUT_OF_RANGE(11),
        UNIMPLEMENTED(12),
        INTERNAL(13),
        UNAVAILABLE(14),
        DATA_LOSS(15),
        UNAUTHENTICATED(16);
        
        private static final SparseArray<Code> STATUS_LIST = null;
        private final int value;

        static {
            STATUS_LIST = buildStatusList();
        }

        private Code(int i) {
            this.value = i;
        }

        private static SparseArray<Code> buildStatusList() {
            SparseArray<Code> sparseArray = new SparseArray();
            Code[] values = values();
            int length = values.length;
            int i = 0;
            while (i < length) {
                Code code = values[i];
                Code code2 = (Code) sparseArray.get(code.ordinal());
                if (code2 == null) {
                    sparseArray.put(code.ordinal(), code);
                    i++;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Code value duplication between ");
                    stringBuilder.append(code2);
                    stringBuilder.append("&");
                    stringBuilder.append(code.name());
                    throw new IllegalStateException(stringBuilder.toString());
                }
            }
            return sparseArray;
        }

        static Code fromValue(int i) {
            return (Code) STATUS_LIST.get(i, UNKNOWN);
        }

        static Code fromHttpStatus(int i) {
            if (i == LogSeverity.INFO_VALUE) {
                return OK;
            }
            if (i == 409) {
                return ABORTED;
            }
            if (i == 429) {
                return RESOURCE_EXHAUSTED;
            }
            if (i == 400) {
                return INVALID_ARGUMENT;
            }
            if (i == 401) {
                return UNAUTHENTICATED;
            }
            if (i == 403) {
                return PERMISSION_DENIED;
            }
            if (i == 404) {
                return NOT_FOUND;
            }
            if (i == 503) {
                return UNAVAILABLE;
            }
            if (i == 504) {
                return DEADLINE_EXCEEDED;
            }
            switch (i) {
                case 499:
                    return CANCELLED;
                case 500:
                    return INTERNAL;
                case 501:
                    return UNIMPLEMENTED;
                default:
                    return UNKNOWN;
            }
        }
    }

    @Nullable
    static FirebaseFunctionsException fromResponse(Code code, @Nullable String str, Serializer serializer) {
        Object opt;
        String str2 = "message";
        String str3 = NotificationCompat.CATEGORY_STATUS;
        String name = code.name();
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject(ReactVideoView.EVENT_PROP_ERROR);
            if (jSONObject.opt(str3) instanceof String) {
                code = Code.valueOf(jSONObject.getString(str3));
                name = code.name();
            }
            if (jSONObject.opt(str2) instanceof String) {
                name = jSONObject.getString(str2);
            }
            opt = jSONObject.opt("details");
            if (opt != null) {
                try {
                    opt = serializer.decode(opt);
                } catch (IllegalArgumentException unused) {
                    code = Code.INTERNAL;
                    name = code.name();
                }
            }
        } catch (IllegalArgumentException unused2) {
            opt = null;
        } catch (JSONException unused3) {
            opt = null;
        }
        if (code == Code.OK) {
            return null;
        }
        return new FirebaseFunctionsException(name, code, opt);
    }

    FirebaseFunctionsException(@NonNull String str, @NonNull Code code, @Nullable Object obj) {
        super(str);
        this.code = code;
        this.details = obj;
    }

    FirebaseFunctionsException(@NonNull String str, @NonNull Code code, @Nullable Object obj, Throwable th) {
        super(str, th);
        this.code = code;
        this.details = obj;
    }

    @NonNull
    public Code getCode() {
        return this.code;
    }

    @Nullable
    public Object getDetails() {
        return this.details;
    }
}
