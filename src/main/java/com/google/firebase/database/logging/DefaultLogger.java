package com.google.firebase.database.logging;

import com.google.firebase.database.logging.Logger.Level;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DefaultLogger implements Logger {
    private final Set<String> enabledComponents;
    private final Level minLevel;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    /* renamed from: com.google.firebase.database.logging.DefaultLogger$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$logging$Logger$Level = new int[Level.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$google$firebase$database$logging$Logger$Level[com.google.firebase.database.logging.Logger.Level.DEBUG.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.google.firebase.database.logging.Logger.Level.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$database$logging$Logger$Level = r0;
            r0 = $SwitchMap$com$google$firebase$database$logging$Logger$Level;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.database.logging.Logger.Level.ERROR;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$database$logging$Logger$Level;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.database.logging.Logger.Level.WARN;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$database$logging$Logger$Level;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.database.logging.Logger.Level.INFO;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$google$firebase$database$logging$Logger$Level;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.google.firebase.database.logging.Logger.Level.DEBUG;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.logging.DefaultLogger.1.<clinit>():void");
        }
    }

    public DefaultLogger(Level level, List<String> list) {
        if (list != null) {
            this.enabledComponents = new HashSet(list);
        } else {
            this.enabledComponents = null;
        }
        this.minLevel = level;
    }

    public Level getLogLevel() {
        return this.minLevel;
    }

    public void onLogMessage(Level level, String str, String str2, long j) {
        if (shouldLog(level, str)) {
            str2 = buildLogMessage(level, str, str2, j);
            int i = AnonymousClass1.$SwitchMap$com$google$firebase$database$logging$Logger$Level[level.ordinal()];
            if (i == 1) {
                error(str, str2);
            } else if (i == 2) {
                warn(str, str2);
            } else if (i == 3) {
                info(str, str2);
            } else if (i == 4) {
                debug(str, str2);
            } else {
                throw new RuntimeException("Should not reach here!");
            }
        }
    }

    protected String buildLogMessage(Level level, String str, String str2, long j) {
        Date date = new Date(j);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" [");
        stringBuilder.append(level);
        stringBuilder.append("] ");
        stringBuilder.append(str);
        stringBuilder.append(": ");
        stringBuilder.append(str2);
        return stringBuilder.toString();
    }

    protected void error(String str, String str2) {
        System.err.println(str2);
    }

    protected void warn(String str, String str2) {
        System.out.println(str2);
    }

    protected void info(String str, String str2) {
        System.out.println(str2);
    }

    protected void debug(String str, String str2) {
        System.out.println(str2);
    }

    protected boolean shouldLog(Level level, String str) {
        return level.ordinal() >= this.minLevel.ordinal() && (this.enabledComponents == null || level.ordinal() > Level.DEBUG.ordinal() || this.enabledComponents.contains(str));
    }
}
