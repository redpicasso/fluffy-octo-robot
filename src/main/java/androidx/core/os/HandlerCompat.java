package androidx.core.os;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class HandlerCompat {
    private static final String TAG = "HandlerCompat";

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0056 A:{ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:6:0x0011} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0056 A:{ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:6:0x0011} */
    /* JADX WARNING: Missing block: B:20:0x0056, code:
            android.util.Log.v(TAG, "Unable to invoke Handler(Looper, Callback, boolean) constructor");
     */
    @androidx.annotation.NonNull
    public static android.os.Handler createAsync(@androidx.annotation.NonNull android.os.Looper r7) {
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 28;
        if (r0 < r1) goto L_0x000b;
    L_0x0006:
        r7 = android.os.Handler.createAsync(r7);
        return r7;
    L_0x000b:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 16;
        if (r0 < r1) goto L_0x005d;
    L_0x0011:
        r0 = android.os.Handler.class;
        r1 = 3;
        r2 = new java.lang.Class[r1];	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r3 = android.os.Looper.class;
        r4 = 0;
        r2[r4] = r3;	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r3 = android.os.Handler.Callback.class;
        r5 = 1;
        r2[r5] = r3;	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r3 = java.lang.Boolean.TYPE;	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r6 = 2;
        r2[r6] = r3;	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r0 = r0.getDeclaredConstructor(r2);	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r1 = new java.lang.Object[r1];	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r1[r4] = r7;	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r2 = 0;
        r1[r5] = r2;	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r2 = java.lang.Boolean.valueOf(r5);	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r1[r6] = r2;	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r0 = r0.newInstance(r1);	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        r0 = (android.os.Handler) r0;	 Catch:{ IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, IllegalAccessException -> 0x0056, InvocationTargetException -> 0x003d }
        return r0;
    L_0x003d:
        r7 = move-exception;
        r7 = r7.getCause();
        r0 = r7 instanceof java.lang.RuntimeException;
        if (r0 != 0) goto L_0x0053;
    L_0x0046:
        r0 = r7 instanceof java.lang.Error;
        if (r0 == 0) goto L_0x004d;
    L_0x004a:
        r7 = (java.lang.Error) r7;
        throw r7;
    L_0x004d:
        r0 = new java.lang.RuntimeException;
        r0.<init>(r7);
        throw r0;
    L_0x0053:
        r7 = (java.lang.RuntimeException) r7;
        throw r7;
    L_0x0056:
        r0 = "HandlerCompat";
        r1 = "Unable to invoke Handler(Looper, Callback, boolean) constructor";
        android.util.Log.v(r0, r1);
    L_0x005d:
        r0 = new android.os.Handler;
        r0.<init>(r7);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.os.HandlerCompat.createAsync(android.os.Looper):android.os.Handler");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0055 A:{ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:6:0x0011} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0055 A:{ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:6:0x0011} */
    /* JADX WARNING: Missing block: B:20:0x0055, code:
            android.util.Log.v(TAG, "Unable to invoke Handler(Looper, Callback, boolean) constructor");
     */
    @androidx.annotation.NonNull
    public static android.os.Handler createAsync(@androidx.annotation.NonNull android.os.Looper r7, @androidx.annotation.NonNull android.os.Handler.Callback r8) {
        /*
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 28;
        if (r0 < r1) goto L_0x000b;
    L_0x0006:
        r7 = android.os.Handler.createAsync(r7, r8);
        return r7;
    L_0x000b:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 16;
        if (r0 < r1) goto L_0x005c;
    L_0x0011:
        r0 = android.os.Handler.class;
        r1 = 3;
        r2 = new java.lang.Class[r1];	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r3 = android.os.Looper.class;
        r4 = 0;
        r2[r4] = r3;	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r3 = android.os.Handler.Callback.class;
        r5 = 1;
        r2[r5] = r3;	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r3 = java.lang.Boolean.TYPE;	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r6 = 2;
        r2[r6] = r3;	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r0 = r0.getDeclaredConstructor(r2);	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r1 = new java.lang.Object[r1];	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r1[r4] = r7;	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r1[r5] = r8;	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r2 = java.lang.Boolean.valueOf(r5);	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r1[r6] = r2;	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r0 = r0.newInstance(r1);	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        r0 = (android.os.Handler) r0;	 Catch:{ IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, IllegalAccessException -> 0x0055, InvocationTargetException -> 0x003c }
        return r0;
    L_0x003c:
        r7 = move-exception;
        r7 = r7.getCause();
        r8 = r7 instanceof java.lang.RuntimeException;
        if (r8 != 0) goto L_0x0052;
    L_0x0045:
        r8 = r7 instanceof java.lang.Error;
        if (r8 == 0) goto L_0x004c;
    L_0x0049:
        r7 = (java.lang.Error) r7;
        throw r7;
    L_0x004c:
        r8 = new java.lang.RuntimeException;
        r8.<init>(r7);
        throw r8;
    L_0x0052:
        r7 = (java.lang.RuntimeException) r7;
        throw r7;
    L_0x0055:
        r0 = "HandlerCompat";
        r1 = "Unable to invoke Handler(Looper, Callback, boolean) constructor";
        android.util.Log.v(r0, r1);
    L_0x005c:
        r0 = new android.os.Handler;
        r0.<init>(r7, r8);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.os.HandlerCompat.createAsync(android.os.Looper, android.os.Handler$Callback):android.os.Handler");
    }

    public static boolean postDelayed(@NonNull Handler handler, @NonNull Runnable runnable, @Nullable Object obj, long j) {
        if (VERSION.SDK_INT >= 28) {
            return handler.postDelayed(runnable, obj, j);
        }
        Message obtain = Message.obtain(handler, runnable);
        obtain.obj = obj;
        return handler.sendMessageDelayed(obtain, j);
    }

    private HandlerCompat() {
    }
}
