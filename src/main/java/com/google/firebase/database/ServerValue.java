package com.google.firebase.database;

import androidx.annotation.NonNull;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.database.core.ServerValues;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ServerValue {
    @PublicApi
    @NonNull
    public static final Map<String, String> TIMESTAMP = createServerValuePlaceholder("timestamp");

    private static Map<String, String> createServerValuePlaceholder(String str) {
        Map hashMap = new HashMap();
        hashMap.put(ServerValues.NAME_SUBKEY_SERVERVALUE, str);
        return Collections.unmodifiableMap(hashMap);
    }
}
