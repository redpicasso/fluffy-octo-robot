package com.google.firebase.database.logging;

import com.google.firebase.database.logging.Logger.Level;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class LogWrapper {
    private final String component;
    private final Logger logger;
    private final String prefix;

    private static String exceptionStacktrace(Throwable th) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public LogWrapper(Logger logger, String str) {
        this(logger, str, null);
    }

    public LogWrapper(Logger logger, String str, String str2) {
        this.logger = logger;
        this.component = str;
        this.prefix = str2;
    }

    public void error(String str, Throwable th) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(toLog(str, new Object[0]));
        stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
        stringBuilder.append(exceptionStacktrace(th));
        this.logger.onLogMessage(Level.ERROR, this.component, stringBuilder.toString(), now());
    }

    public void warn(String str) {
        warn(str, null);
    }

    public void warn(String str, Throwable th) {
        str = toLog(str, new Object[0]);
        if (th != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
            stringBuilder.append(exceptionStacktrace(th));
            str = stringBuilder.toString();
        }
        this.logger.onLogMessage(Level.WARN, this.component, str, now());
    }

    public void info(String str) {
        this.logger.onLogMessage(Level.INFO, this.component, toLog(str, new Object[0]), now());
    }

    public void debug(String str, Object... objArr) {
        debug(str, null, objArr);
    }

    public boolean logsDebug() {
        return this.logger.getLogLevel().ordinal() <= Level.DEBUG.ordinal();
    }

    public void debug(String str, Throwable th, Object... objArr) {
        if (logsDebug()) {
            str = toLog(str, objArr);
            if (th != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
                stringBuilder.append(exceptionStacktrace(th));
                str = stringBuilder.toString();
            }
            this.logger.onLogMessage(Level.DEBUG, this.component, str, now());
        }
    }

    private long now() {
        return System.currentTimeMillis();
    }

    private String toLog(String str, Object... objArr) {
        if (objArr.length > 0) {
            str = String.format(str, objArr);
        }
        if (this.prefix == null) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.prefix);
        stringBuilder.append(" - ");
        stringBuilder.append(str);
        return stringBuilder.toString();
    }
}
