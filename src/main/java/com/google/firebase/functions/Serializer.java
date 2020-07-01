package com.google.firebase.functions;

import androidx.annotation.VisibleForTesting;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
class Serializer {
    @VisibleForTesting
    static final String LONG_TYPE = "type.googleapis.com/google.protobuf.Int64Value";
    @VisibleForTesting
    static final String UNSIGNED_LONG_TYPE = "type.googleapis.com/google.protobuf.UInt64Value";
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

    public Serializer() {
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public Object encode(Object obj) {
        if (obj == null || obj == JSONObject.NULL) {
            return JSONObject.NULL;
        }
        JSONObject jSONObject;
        if (obj instanceof Long) {
            jSONObject = new JSONObject();
            try {
                jSONObject.put("@type", LONG_TYPE);
                jSONObject.put("value", obj.toString());
                return jSONObject;
            } catch (Throwable e) {
                throw new RuntimeException("Error encoding Long.", e);
            }
        } else if ((obj instanceof Number) || (obj instanceof String) || (obj instanceof Boolean)) {
            return obj;
        } else {
            boolean z = obj instanceof JSONObject;
            if (z) {
                return obj;
            }
            boolean z2 = obj instanceof JSONArray;
            if (z2) {
                return obj;
            }
            String str = "Object keys must be strings.";
            Iterator keys;
            JSONArray jSONArray;
            if (obj instanceof Map) {
                jSONObject = new JSONObject();
                Map map = (Map) obj;
                for (Object next : map.keySet()) {
                    if (next instanceof String) {
                        try {
                            jSONObject.put((String) next, encode(map.get(next)));
                        } catch (Throwable e2) {
                            throw new RuntimeException(e2);
                        }
                    }
                    throw new IllegalArgumentException(str);
                }
                return jSONObject;
            } else if (obj instanceof List) {
                jSONArray = new JSONArray();
                for (Object encode : (List) obj) {
                    jSONArray.put(encode(encode));
                }
                return jSONArray;
            } else if (z) {
                jSONObject = new JSONObject();
                JSONObject jSONObject2 = (JSONObject) obj;
                keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String str2 = (String) keys.next();
                    if (str2 instanceof String) {
                        try {
                            jSONObject.put(str2, encode(jSONObject2.opt(str2)));
                        } catch (Throwable e22) {
                            throw new RuntimeException(e22);
                        }
                    }
                    throw new IllegalArgumentException(str);
                }
                return jSONObject;
            } else if (z2) {
                jSONArray = new JSONArray();
                JSONArray jSONArray2 = (JSONArray) obj;
                for (int i = 0; i < jSONArray2.length(); i++) {
                    jSONArray.put(encode(jSONArray2.opt(i)));
                }
                return jSONArray;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Object cannot be encoded in JSON: ");
                stringBuilder.append(obj);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
    }

    public Object decode(Object obj) {
        StringBuilder stringBuilder;
        if ((obj instanceof Number) || (obj instanceof String) || (obj instanceof Boolean)) {
            return obj;
        }
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            String str = "@type";
            if (jSONObject.has(str)) {
                str = jSONObject.optString(str);
                String optString = jSONObject.optString("value");
                String str2 = "Invalid Long format:";
                if (str.equals(LONG_TYPE)) {
                    try {
                        return Long.valueOf(Long.parseLong(optString));
                    } catch (NumberFormatException unused) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(optString);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                } else if (str.equals(UNSIGNED_LONG_TYPE)) {
                    try {
                        return Long.valueOf(Long.parseLong(optString));
                    } catch (NumberFormatException unused2) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(optString);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
            }
            Map hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str3 = (String) keys.next();
                hashMap.put(str3, decode(jSONObject.opt(str3)));
            }
            return hashMap;
        } else if (obj instanceof JSONArray) {
            List arrayList = new ArrayList();
            int i = 0;
            while (true) {
                JSONArray jSONArray = (JSONArray) obj;
                if (i >= jSONArray.length()) {
                    return arrayList;
                }
                arrayList.add(decode(jSONArray.opt(i)));
                i++;
            }
        } else if (obj == JSONObject.NULL) {
            return null;
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Object cannot be decoded from JSON: ");
            stringBuilder2.append(obj);
            throw new IllegalArgumentException(stringBuilder2.toString());
        }
    }
}
