package io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogExceptionRunnable implements Runnable {
    private static final Logger log = Logger.getLogger(LogExceptionRunnable.class.getName());
    private final Runnable task;

    public LogExceptionRunnable(Runnable runnable) {
        this.task = (Runnable) Preconditions.checkNotNull(runnable, "task");
    }

    public void run() {
        try {
            this.task.run();
        } catch (Throwable th) {
            Logger logger = log;
            Level level = Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception while executing runnable ");
            stringBuilder.append(this.task);
            logger.log(level, stringBuilder.toString(), th);
            MoreThrowables.throwIfUnchecked(th);
            AssertionError assertionError = new AssertionError(th);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LogExceptionRunnable(");
        stringBuilder.append(this.task);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
