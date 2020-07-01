package io.invertase.firebase;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import javax.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    private static final String TAG = "Utils";

    public static String timestampToUTC(long j) {
        Date date = new Date((j + ((long) Calendar.getInstance().getTimeZone().getOffset(j))) * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

    public static void sendEvent(ReactContext reactContext, String str, Object obj) {
        if (reactContext != null) {
            ((RCTDeviceEventEmitter) reactContext.getJSModule(RCTDeviceEventEmitter.class)).emit(str, obj);
        } else {
            Log.d(TAG, "Missing context - cannot send event!");
        }
    }

    public static WritableMap jsonObjectToWritableMap(JSONObject jSONObject) throws JSONException {
        Iterator keys = jSONObject.keys();
        WritableMap createMap = Arguments.createMap();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            Object obj = jSONObject.get(str);
            if ((obj instanceof Float) || (obj instanceof Double)) {
                createMap.putDouble(str, jSONObject.getDouble(str));
            } else if (obj instanceof Number) {
                createMap.putInt(str, jSONObject.getInt(str));
            } else if (obj instanceof String) {
                createMap.putString(str, jSONObject.getString(str));
            } else if (obj instanceof JSONObject) {
                createMap.putMap(str, jsonObjectToWritableMap(jSONObject.getJSONObject(str)));
            } else if (obj instanceof JSONArray) {
                createMap.putArray(str, jsonArrayToWritableArray(jSONObject.getJSONArray(str)));
            } else if (obj == JSONObject.NULL) {
                createMap.putNull(str);
            }
        }
        return createMap;
    }

    public static WritableArray jsonArrayToWritableArray(JSONArray jSONArray) throws JSONException {
        WritableArray createArray = Arguments.createArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            Object obj = jSONArray.get(i);
            if ((obj instanceof Float) || (obj instanceof Double)) {
                createArray.pushDouble(jSONArray.getDouble(i));
            } else if (obj instanceof Number) {
                createArray.pushInt(jSONArray.getInt(i));
            } else if (obj instanceof String) {
                createArray.pushString(jSONArray.getString(i));
            } else if (obj instanceof JSONObject) {
                createArray.pushMap(jsonObjectToWritableMap(jSONArray.getJSONObject(i)));
            } else if (obj instanceof JSONArray) {
                createArray.pushArray(jsonArrayToWritableArray(jSONArray.getJSONArray(i)));
            } else if (obj == JSONObject.NULL) {
                createArray.pushNull();
            }
        }
        return createArray;
    }

    public static WritableMap mapToWritableMap(Map<String, Object> map) {
        WritableMap createMap = Arguments.createMap();
        for (Entry entry : map.entrySet()) {
            mapPutValue((String) entry.getKey(), entry.getValue(), createMap);
        }
        return createMap;
    }

    private static WritableArray listToWritableArray(List<Object> list) {
        WritableArray createArray = Arguments.createArray();
        for (Object arrayPushValue : list) {
            arrayPushValue(arrayPushValue, createArray);
        }
        return createArray;
    }

    public static void arrayPushValue(@Nullable Object obj, WritableArray writableArray) {
        if (obj == null || obj == JSONObject.NULL) {
            writableArray.pushNull();
            return;
        }
        String name = obj.getClass().getName();
        Object obj2 = -1;
        switch (name.hashCode()) {
            case -2056817302:
                if (name.equals("java.lang.Integer")) {
                    obj2 = 4;
                    break;
                }
                break;
            case -527879800:
                if (name.equals("java.lang.Float")) {
                    obj2 = 2;
                    break;
                }
                break;
            case 146015330:
                if (name.equals("org.json.JSONArray$1")) {
                    obj2 = 7;
                    break;
                }
                break;
            case 344809556:
                if (name.equals("java.lang.Boolean")) {
                    obj2 = null;
                    break;
                }
                break;
            case 398795216:
                if (name.equals("java.lang.Long")) {
                    obj2 = 1;
                    break;
                }
                break;
            case 761287205:
                if (name.equals("java.lang.Double")) {
                    obj2 = 3;
                    break;
                }
                break;
            case 1195259493:
                if (name.equals("java.lang.String")) {
                    obj2 = 5;
                    break;
                }
                break;
            case 1614941136:
                if (name.equals("org.json.JSONObject$1")) {
                    obj2 = 6;
                    break;
                }
                break;
        }
        switch (obj2) {
            case null:
                writableArray.pushBoolean(((Boolean) obj).booleanValue());
                break;
            case 1:
                writableArray.pushDouble((double) ((Long) obj).longValue());
                break;
            case 2:
                writableArray.pushDouble((double) ((Float) obj).floatValue());
                break;
            case 3:
                writableArray.pushDouble(((Double) obj).doubleValue());
                break;
            case 4:
                writableArray.pushInt(((Integer) obj).intValue());
                break;
            case 5:
                writableArray.pushString((String) obj);
                break;
            case 6:
                try {
                    writableArray.pushMap(jsonObjectToWritableMap((JSONObject) obj));
                    break;
                } catch (JSONException unused) {
                    writableArray.pushNull();
                    break;
                }
            case 7:
                try {
                    writableArray.pushArray(jsonArrayToWritableArray((JSONArray) obj));
                    break;
                } catch (JSONException unused2) {
                    writableArray.pushNull();
                    break;
                }
            default:
                if (!List.class.isAssignableFrom(obj.getClass())) {
                    if (!Map.class.isAssignableFrom(obj.getClass())) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("utils:arrayPushValue:unknownType:");
                        stringBuilder.append(name);
                        Log.d(TAG, stringBuilder.toString());
                        writableArray.pushNull();
                        break;
                    }
                    writableArray.pushMap(mapToWritableMap((Map) obj));
                    break;
                }
                writableArray.pushArray(listToWritableArray((List) obj));
                break;
        }
    }

    public static void mapPutValue(String str, @Nullable Object obj, WritableMap writableMap) {
        if (obj == null || obj == JSONObject.NULL) {
            writableMap.putNull(str);
            return;
        }
        String name = obj.getClass().getName();
        Object obj2 = -1;
        switch (name.hashCode()) {
            case -2056817302:
                if (name.equals("java.lang.Integer")) {
                    obj2 = 4;
                    break;
                }
                break;
            case -527879800:
                if (name.equals("java.lang.Float")) {
                    obj2 = 2;
                    break;
                }
                break;
            case 146015330:
                if (name.equals("org.json.JSONArray$1")) {
                    obj2 = 7;
                    break;
                }
                break;
            case 344809556:
                if (name.equals("java.lang.Boolean")) {
                    obj2 = null;
                    break;
                }
                break;
            case 398795216:
                if (name.equals("java.lang.Long")) {
                    obj2 = 1;
                    break;
                }
                break;
            case 761287205:
                if (name.equals("java.lang.Double")) {
                    obj2 = 3;
                    break;
                }
                break;
            case 1195259493:
                if (name.equals("java.lang.String")) {
                    obj2 = 5;
                    break;
                }
                break;
            case 1614941136:
                if (name.equals("org.json.JSONObject$1")) {
                    obj2 = 6;
                    break;
                }
                break;
        }
        switch (obj2) {
            case null:
                writableMap.putBoolean(str, ((Boolean) obj).booleanValue());
                break;
            case 1:
                writableMap.putDouble(str, (double) ((Long) obj).longValue());
                break;
            case 2:
                writableMap.putDouble(str, (double) ((Float) obj).floatValue());
                break;
            case 3:
                writableMap.putDouble(str, ((Double) obj).doubleValue());
                break;
            case 4:
                writableMap.putInt(str, ((Integer) obj).intValue());
                break;
            case 5:
                writableMap.putString(str, (String) obj);
                break;
            case 6:
                try {
                    writableMap.putMap(str, jsonObjectToWritableMap((JSONObject) obj));
                    break;
                } catch (JSONException unused) {
                    writableMap.putNull(str);
                    break;
                }
            case 7:
                try {
                    writableMap.putArray(str, jsonArrayToWritableArray((JSONArray) obj));
                    break;
                } catch (JSONException unused2) {
                    writableMap.putNull(str);
                    break;
                }
            default:
                if (!List.class.isAssignableFrom(obj.getClass())) {
                    if (!Map.class.isAssignableFrom(obj.getClass())) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("utils:mapPutValue:unknownType:");
                        stringBuilder.append(name);
                        Log.d(TAG, stringBuilder.toString());
                        writableMap.putNull(str);
                        break;
                    }
                    writableMap.putMap(str, mapToWritableMap((Map) obj));
                    break;
                }
                writableMap.putArray(str, listToWritableArray((List) obj));
                break;
        }
    }

    public static WritableMap readableMapToWritableMap(ReadableMap readableMap) {
        WritableMap createMap = Arguments.createMap();
        createMap.merge(readableMap);
        return createMap;
    }

    public static Map<String, Object> recursivelyDeconstructReadableMap(ReadableMap readableMap) {
        return readableMap.toHashMap();
    }

    public static List<Object> recursivelyDeconstructReadableArray(ReadableArray readableArray) {
        return readableArray.toArrayList();
    }

    public static boolean isAppInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null) {
            return false;
        }
        List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                boolean z = true;
                try {
                    if (((ReactContext) context).getLifecycleState() != LifecycleState.RESUMED) {
                        z = false;
                    }
                } catch (ClassCastException unused) {
                    return z;
                }
            }
        }
        return false;
    }

    public static int getResId(Context context, String str) {
        int identifier = context.getResources().getIdentifier(str, "string", context.getPackageName());
        if (identifier == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("resource ");
            stringBuilder.append(str);
            stringBuilder.append(" could not be found");
            Log.e(TAG, stringBuilder.toString());
        }
        return identifier;
    }
}
