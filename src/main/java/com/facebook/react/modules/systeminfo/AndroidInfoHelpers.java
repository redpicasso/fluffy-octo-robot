package com.facebook.react.modules.systeminfo;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.facebook.react.R;
import java.util.Locale;

public class AndroidInfoHelpers {
    public static final String DEVICE_LOCALHOST = "localhost";
    public static final String EMULATOR_LOCALHOST = "10.0.2.2";
    public static final String GENYMOTION_LOCALHOST = "10.0.3.2";
    public static final String METRO_HOST_PROP_NAME = "metro.host";
    private static final String TAG = "AndroidInfoHelpers";
    private static String metroHostPropValue;

    private static boolean isRunningOnGenymotion() {
        return Build.FINGERPRINT.contains("vbox");
    }

    private static boolean isRunningOnStockEmulator() {
        return Build.FINGERPRINT.contains("generic");
    }

    public static String getServerHost(Integer num) {
        return getServerIpAddress(num.intValue());
    }

    public static String getServerHost(Context context) {
        return getServerIpAddress(getDevServerPort(context).intValue());
    }

    public static String getAdbReverseTcpCommand(Integer num) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("adb reverse tcp:");
        stringBuilder.append(num);
        stringBuilder.append(" tcp:");
        stringBuilder.append(num);
        return stringBuilder.toString();
    }

    public static String getAdbReverseTcpCommand(Context context) {
        return getAdbReverseTcpCommand(getDevServerPort(context));
    }

    public static String getInspectorProxyHost(Context context) {
        return getServerIpAddress(getInspectorProxyPort(context).intValue());
    }

    public static String getFriendlyDeviceName() {
        if (isRunningOnGenymotion()) {
            return Build.MODEL;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Build.MODEL);
        stringBuilder.append(" - ");
        stringBuilder.append(VERSION.RELEASE);
        stringBuilder.append(" - API ");
        stringBuilder.append(VERSION.SDK_INT);
        return stringBuilder.toString();
    }

    private static Integer getDevServerPort(Context context) {
        return Integer.valueOf(context.getResources().getInteger(R.integer.react_native_dev_server_port));
    }

    private static Integer getInspectorProxyPort(Context context) {
        return Integer.valueOf(context.getResources().getInteger(R.integer.react_native_dev_server_port));
    }

    private static String getServerIpAddress(int i) {
        String metroHostPropValue = getMetroHostPropValue();
        if (metroHostPropValue.equals("")) {
            metroHostPropValue = isRunningOnGenymotion() ? GENYMOTION_LOCALHOST : isRunningOnStockEmulator() ? EMULATOR_LOCALHOST : DEVICE_LOCALHOST;
        }
        return String.format(Locale.US, "%s:%d", new Object[]{metroHostPropValue, Integer.valueOf(i)});
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0065 A:{SYNTHETIC, Splitter: B:36:0x0065} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0065 A:{SYNTHETIC, Splitter: B:36:0x0065} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0072 A:{SYNTHETIC, Splitter: B:45:0x0072} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0072 A:{SYNTHETIC, Splitter: B:45:0x0072} */
    /* JADX WARNING: Missing block: B:21:0x003f, code:
            if (r2 != null) goto L_0x0041;
     */
    /* JADX WARNING: Missing block: B:23:?, code:
            r2.destroy();
     */
    /* JADX WARNING: Missing block: B:38:0x0068, code:
            if (r2 != null) goto L_0x0041;
     */
    /* JADX WARNING: Missing block: B:40:?, code:
            r1 = metroHostPropValue;
     */
    /* JADX WARNING: Missing block: B:42:0x006e, code:
            return r1;
     */
    private static synchronized java.lang.String getMetroHostPropValue() {
        /*
        r0 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.class;
        monitor-enter(r0);
        r1 = metroHostPropValue;	 Catch:{ all -> 0x007b }
        if (r1 == 0) goto L_0x000b;
    L_0x0007:
        r1 = metroHostPropValue;	 Catch:{ all -> 0x007b }
        monitor-exit(r0);
        return r1;
    L_0x000b:
        r1 = 0;
        r2 = java.lang.Runtime.getRuntime();	 Catch:{ Exception -> 0x0055, all -> 0x0051 }
        r3 = "/system/bin/getprop";
        r4 = "metro.host";
        r3 = new java.lang.String[]{r3, r4};	 Catch:{ Exception -> 0x0055, all -> 0x0051 }
        r2 = r2.exec(r3);	 Catch:{ Exception -> 0x0055, all -> 0x0051 }
        r3 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x004f }
        r4 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x004f }
        r5 = r2.getInputStream();	 Catch:{ Exception -> 0x004f }
        r6 = "UTF-8";
        r6 = java.nio.charset.Charset.forName(r6);	 Catch:{ Exception -> 0x004f }
        r4.<init>(r5, r6);	 Catch:{ Exception -> 0x004f }
        r3.<init>(r4);	 Catch:{ Exception -> 0x004f }
        r1 = "";
    L_0x0032:
        r4 = r3.readLine();	 Catch:{ Exception -> 0x004a, all -> 0x0045 }
        if (r4 == 0) goto L_0x003a;
    L_0x0038:
        r1 = r4;
        goto L_0x0032;
    L_0x003a:
        metroHostPropValue = r1;	 Catch:{ Exception -> 0x004a, all -> 0x0045 }
        r3.close();	 Catch:{ Exception -> 0x003f }
    L_0x003f:
        if (r2 == 0) goto L_0x006b;
    L_0x0041:
        r2.destroy();	 Catch:{ all -> 0x007b }
        goto L_0x006b;
    L_0x0045:
        r1 = move-exception;
        r7 = r3;
        r3 = r1;
        r1 = r7;
        goto L_0x0070;
    L_0x004a:
        r1 = move-exception;
        r7 = r3;
        r3 = r1;
        r1 = r7;
        goto L_0x0058;
    L_0x004f:
        r3 = move-exception;
        goto L_0x0058;
    L_0x0051:
        r2 = move-exception;
        r3 = r2;
        r2 = r1;
        goto L_0x0070;
    L_0x0055:
        r2 = move-exception;
        r3 = r2;
        r2 = r1;
    L_0x0058:
        r4 = TAG;	 Catch:{ all -> 0x006f }
        r5 = "Failed to query for metro.host prop:";
        com.facebook.common.logging.FLog.w(r4, r5, r3);	 Catch:{ all -> 0x006f }
        r3 = "";
        metroHostPropValue = r3;	 Catch:{ all -> 0x006f }
        if (r1 == 0) goto L_0x0068;
    L_0x0065:
        r1.close();	 Catch:{ Exception -> 0x0068 }
    L_0x0068:
        if (r2 == 0) goto L_0x006b;
    L_0x006a:
        goto L_0x0041;
    L_0x006b:
        r1 = metroHostPropValue;	 Catch:{ all -> 0x007b }
        monitor-exit(r0);
        return r1;
    L_0x006f:
        r3 = move-exception;
    L_0x0070:
        if (r1 == 0) goto L_0x0075;
    L_0x0072:
        r1.close();	 Catch:{ Exception -> 0x0075 }
    L_0x0075:
        if (r2 == 0) goto L_0x007a;
    L_0x0077:
        r2.destroy();	 Catch:{ all -> 0x007b }
    L_0x007a:
        throw r3;	 Catch:{ all -> 0x007b }
    L_0x007b:
        r1 = move-exception;
        monitor-exit(r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.systeminfo.AndroidInfoHelpers.getMetroHostPropValue():java.lang.String");
    }
}
