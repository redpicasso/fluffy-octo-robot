package com.learnium.RNDeviceInfo.resolver;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;

public class DeviceIdResolver {
    private final Context context;

    public DeviceIdResolver(Context context) {
        this.context = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0011 A:{ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:4:0x000c} */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0011 A:{ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:4:0x000c} */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0011 A:{ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:4:0x000c} */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0005 A:{ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0005 A:{ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0005 A:{ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:3:0x0005, code:
            java.lang.System.err.println("N/A: Unsupported version of com.google.firebase:firebase-iid in your project.");
     */
    /* JADX WARNING: Missing block: B:7:0x0011, code:
            java.lang.System.err.println("N/A: Unsupported version of com.google.android.gms.iid in your project.");
     */
    /* JADX WARNING: Missing block: B:8:0x0018, code:
            java.lang.System.err.println("Can't generate id. Please add com.google.firebase:firebase-iid to your project.");
     */
    /* JADX WARNING: Missing block: B:9:0x0021, code:
            return androidx.core.os.EnvironmentCompat.MEDIA_UNKNOWN;
     */
    public java.lang.String getInstanceIdSync() {
        /*
        r2 = this;
        r0 = r2.getFirebaseInstanceId();	 Catch:{ ClassNotFoundException -> 0x000c, NoSuchMethodException -> 0x0005, NoSuchMethodException -> 0x0005, NoSuchMethodException -> 0x0005, NoSuchMethodException -> 0x0005 }
        return r0;
    L_0x0005:
        r0 = java.lang.System.err;
        r1 = "N/A: Unsupported version of com.google.firebase:firebase-iid in your project.";
        r0.println(r1);
    L_0x000c:
        r0 = r2.getGmsInstanceId();	 Catch:{ ClassNotFoundException -> 0x0018, NoSuchMethodException -> 0x0011, NoSuchMethodException -> 0x0011, NoSuchMethodException -> 0x0011, NoSuchMethodException -> 0x0011 }
        return r0;
    L_0x0011:
        r0 = java.lang.System.err;
        r1 = "N/A: Unsupported version of com.google.android.gms.iid in your project.";
        r0.println(r1);
    L_0x0018:
        r0 = java.lang.System.err;
        r1 = "Can't generate id. Please add com.google.firebase:firebase-iid to your project.";
        r0.println(r1);
        r0 = "unknown";
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.learnium.RNDeviceInfo.resolver.DeviceIdResolver.getInstanceIdSync():java.lang.String");
    }

    String getGmsInstanceId() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object invoke = Class.forName("com.google.android.gms.iid.InstanceID").getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{this.context});
        return (String) invoke.getClass().getMethod("getId", new Class[0]).invoke(invoke, new Object[0]);
    }

    String getFirebaseInstanceId() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object invoke = Class.forName("com.google.firebase.iid.FirebaseInstanceId").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        return (String) invoke.getClass().getMethod("getId", new Class[0]).invoke(invoke, new Object[0]);
    }
}
