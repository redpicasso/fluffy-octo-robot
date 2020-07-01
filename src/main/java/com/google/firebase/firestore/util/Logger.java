package com.google.firebase.firestore.util;

import android.util.Log;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Logger {
    private static Level logLevel = Level.WARN;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.util.Logger$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$util$Logger$Level = new int[Level.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$google$firebase$firestore$util$Logger$Level[com.google.firebase.firestore.util.Logger.Level.NONE.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.google.firebase.firestore.util.Logger.Level.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$firestore$util$Logger$Level = r0;
            r0 = $SwitchMap$com$google$firebase$firestore$util$Logger$Level;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.firestore.util.Logger.Level.DEBUG;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$firestore$util$Logger$Level;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.firestore.util.Logger.Level.WARN;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$firestore$util$Logger$Level;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.firestore.util.Logger.Level.NONE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.util.Logger.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Level {
        DEBUG,
        WARN,
        NONE
    }

    public static void setLogLevel(Level level) {
        logLevel = level;
    }

    public static boolean isDebugEnabled() {
        return logLevel.ordinal() >= Level.DEBUG.ordinal();
    }

    private static void doLog(Level level, String str, String str2, Object... objArr) {
        if (level.ordinal() >= logLevel.ordinal()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("(%s) [%s]: ", new Object[]{"19.0.0", str}));
            stringBuilder.append(String.format(str2, objArr));
            str = stringBuilder.toString();
            int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$util$Logger$Level[level.ordinal()];
            str2 = "Firestore";
            if (i == 1) {
                Log.i(str2, str);
            } else if (i == 2) {
                Log.w(str2, str);
            } else if (i == 3) {
                throw new IllegalStateException("Trying to log something on level NONE");
            }
        }
    }

    public static void warn(String str, String str2, Object... objArr) {
        doLog(Level.WARN, str, str2, objArr);
    }

    public static void debug(String str, String str2, Object... objArr) {
        doLog(Level.DEBUG, str, str2, objArr);
    }
}
