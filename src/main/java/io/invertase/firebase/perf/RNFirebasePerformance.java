package io.invertase.firebase.perf;

import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.HttpMetric;
import com.google.firebase.perf.metrics.Trace;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RNFirebasePerformance extends ReactContextBaseJavaModule {
    private static final String TAG = "RNFirebasePerformance";
    private HashMap<String, HttpMetric> httpMetrics = new HashMap();
    private HashMap<String, Trace> traces = new HashMap();

    public String getName() {
        return TAG;
    }

    public RNFirebasePerformance(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        Log.d(TAG, "New instance");
    }

    @ReactMethod
    public void setPerformanceCollectionEnabled(Boolean bool, Promise promise) {
        FirebasePerformance.getInstance().setPerformanceCollectionEnabled(bool.booleanValue());
        promise.resolve(null);
    }

    @ReactMethod
    public void getTraceAttribute(String str, String str2, Promise promise) {
        promise.resolve(getOrCreateTrace(str).getAttribute(str2));
    }

    @ReactMethod
    public void getTraceAttributes(String str, Promise promise) {
        Map attributes = getOrCreateTrace(str).getAttributes();
        WritableMap createMap = Arguments.createMap();
        for (Entry entry : attributes.entrySet()) {
            createMap.putString((String) entry.getKey(), (String) entry.getValue());
        }
        promise.resolve(createMap);
    }

    @ReactMethod
    public void getTraceLongMetric(String str, String str2, Promise promise) {
        promise.resolve(Integer.valueOf(Long.valueOf(getOrCreateTrace(str).getLongMetric(str2)).intValue()));
    }

    @ReactMethod
    public void incrementTraceMetric(String str, String str2, Integer num, Promise promise) {
        getOrCreateTrace(str).incrementMetric(str2, num.longValue());
        promise.resolve(null);
    }

    @ReactMethod
    public void putTraceAttribute(String str, String str2, String str3, Promise promise) {
        getOrCreateTrace(str).putAttribute(str2, str3);
        if (getOrCreateTrace(str).getAttributes().containsKey(str2)) {
            promise.resolve(Boolean.valueOf(true));
        } else {
            promise.resolve(Boolean.valueOf(false));
        }
    }

    @ReactMethod
    public void putTraceMetric(String str, String str2, Integer num, Promise promise) {
        getOrCreateTrace(str).putMetric(str2, num.longValue());
        promise.resolve(null);
    }

    @ReactMethod
    public void removeTraceAttribute(String str, String str2, Promise promise) {
        getOrCreateTrace(str).removeAttribute(str2);
        promise.resolve(null);
    }

    @ReactMethod
    public void startTrace(String str, Promise promise) {
        getOrCreateTrace(str).start();
        promise.resolve(null);
    }

    @ReactMethod
    public void stopTrace(String str, Promise promise) {
        getOrCreateTrace(str).stop();
        this.traces.remove(str);
        promise.resolve(null);
    }

    @ReactMethod
    public void getHttpMetricAttribute(String str, String str2, String str3, Promise promise) {
        promise.resolve(getOrCreateHttpMetric(str, str2).getAttribute(str3));
    }

    @ReactMethod
    public void getHttpMetricAttributes(String str, String str2, Promise promise) {
        Map attributes = getOrCreateHttpMetric(str, str2).getAttributes();
        WritableMap createMap = Arguments.createMap();
        for (Entry entry : attributes.entrySet()) {
            createMap.putString((String) entry.getKey(), (String) entry.getValue());
        }
        promise.resolve(createMap);
    }

    @ReactMethod
    public void putHttpMetricAttribute(String str, String str2, String str3, String str4, Promise promise) {
        getOrCreateHttpMetric(str, str2).putAttribute(str3, str4);
        if (getOrCreateHttpMetric(str, str2).getAttributes().containsKey(str3)) {
            promise.resolve(Boolean.valueOf(true));
        } else {
            promise.resolve(Boolean.valueOf(false));
        }
    }

    @ReactMethod
    public void removeHttpMetricAttribute(String str, String str2, String str3, Promise promise) {
        getOrCreateHttpMetric(str, str2).removeAttribute(str3);
        promise.resolve(null);
    }

    @ReactMethod
    public void setHttpMetricResponseCode(String str, String str2, Integer num, Promise promise) {
        getOrCreateHttpMetric(str, str2).setHttpResponseCode(num.intValue());
        promise.resolve(null);
    }

    @ReactMethod
    public void setHttpMetricRequestPayloadSize(String str, String str2, Integer num, Promise promise) {
        getOrCreateHttpMetric(str, str2).setRequestPayloadSize(num.longValue());
        promise.resolve(null);
    }

    @ReactMethod
    public void setHttpMetricResponseContentType(String str, String str2, String str3, Promise promise) {
        getOrCreateHttpMetric(str, str2).setResponseContentType(str3);
        promise.resolve(null);
    }

    @ReactMethod
    public void setHttpMetricResponsePayloadSize(String str, String str2, Integer num, Promise promise) {
        getOrCreateHttpMetric(str, str2).setResponsePayloadSize(num.longValue());
        promise.resolve(null);
    }

    @ReactMethod
    public void startHttpMetric(String str, String str2, Promise promise) {
        getOrCreateHttpMetric(str, str2).start();
        promise.resolve(null);
    }

    @ReactMethod
    public void stopHttpMetric(String str, String str2, Promise promise) {
        getOrCreateHttpMetric(str, str2).stop();
        HashMap hashMap = this.httpMetrics;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(str2);
        hashMap.remove(stringBuilder.toString());
        promise.resolve(null);
    }

    private Trace getOrCreateTrace(String str) {
        if (this.traces.containsKey(str)) {
            return (Trace) this.traces.get(str);
        }
        Trace newTrace = FirebasePerformance.getInstance().newTrace(str);
        this.traces.put(str, newTrace);
        return newTrace;
    }

    private HttpMetric getOrCreateHttpMetric(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(str2);
        String stringBuilder2 = stringBuilder.toString();
        if (this.httpMetrics.containsKey(stringBuilder2)) {
            return (HttpMetric) this.httpMetrics.get(stringBuilder2);
        }
        HttpMetric newHttpMetric = FirebasePerformance.getInstance().newHttpMetric(str, mapStringToMethod(str2));
        this.httpMetrics.put(stringBuilder2, newHttpMetric);
        return newHttpMetric;
    }

    private java.lang.String mapStringToMethod(java.lang.String r11) {
        /*
        r10 = this;
        r0 = r11.hashCode();
        r1 = "DELETE";
        r2 = "CONNECT";
        r3 = "TRACE";
        r4 = "PATCH";
        r5 = "POST";
        r6 = "HEAD";
        r7 = "PUT";
        r8 = "GET";
        r9 = "OPTIONS";
        switch(r0) {
            case -531492226: goto L_0x005b;
            case 70454: goto L_0x0053;
            case 79599: goto L_0x004b;
            case 2213344: goto L_0x0043;
            case 2461856: goto L_0x003b;
            case 75900968: goto L_0x0033;
            case 80083237: goto L_0x002a;
            case 1669334218: goto L_0x0022;
            case 2012838315: goto L_0x001a;
            default: goto L_0x0019;
        };
    L_0x0019:
        goto L_0x0063;
    L_0x001a:
        r11 = r11.equals(r1);
        if (r11 == 0) goto L_0x0063;
    L_0x0020:
        r11 = 1;
        goto L_0x0064;
    L_0x0022:
        r11 = r11.equals(r2);
        if (r11 == 0) goto L_0x0063;
    L_0x0028:
        r11 = 0;
        goto L_0x0064;
    L_0x002a:
        r11 = r11.equals(r3);
        if (r11 == 0) goto L_0x0063;
    L_0x0030:
        r11 = 8;
        goto L_0x0064;
    L_0x0033:
        r11 = r11.equals(r4);
        if (r11 == 0) goto L_0x0063;
    L_0x0039:
        r11 = 5;
        goto L_0x0064;
    L_0x003b:
        r11 = r11.equals(r5);
        if (r11 == 0) goto L_0x0063;
    L_0x0041:
        r11 = 6;
        goto L_0x0064;
    L_0x0043:
        r11 = r11.equals(r6);
        if (r11 == 0) goto L_0x0063;
    L_0x0049:
        r11 = 3;
        goto L_0x0064;
    L_0x004b:
        r11 = r11.equals(r7);
        if (r11 == 0) goto L_0x0063;
    L_0x0051:
        r11 = 7;
        goto L_0x0064;
    L_0x0053:
        r11 = r11.equals(r8);
        if (r11 == 0) goto L_0x0063;
    L_0x0059:
        r11 = 2;
        goto L_0x0064;
    L_0x005b:
        r11 = r11.equals(r9);
        if (r11 == 0) goto L_0x0063;
    L_0x0061:
        r11 = 4;
        goto L_0x0064;
    L_0x0063:
        r11 = -1;
    L_0x0064:
        switch(r11) {
            case 0: goto L_0x0072;
            case 1: goto L_0x0071;
            case 2: goto L_0x0070;
            case 3: goto L_0x006f;
            case 4: goto L_0x006e;
            case 5: goto L_0x006d;
            case 6: goto L_0x006c;
            case 7: goto L_0x006b;
            case 8: goto L_0x006a;
            default: goto L_0x0067;
        };
    L_0x0067:
        r11 = "";
        return r11;
    L_0x006a:
        return r3;
    L_0x006b:
        return r7;
    L_0x006c:
        return r5;
    L_0x006d:
        return r4;
    L_0x006e:
        return r9;
    L_0x006f:
        return r6;
    L_0x0070:
        return r8;
    L_0x0071:
        return r1;
    L_0x0072:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.perf.RNFirebasePerformance.mapStringToMethod(java.lang.String):java.lang.String");
    }
}
