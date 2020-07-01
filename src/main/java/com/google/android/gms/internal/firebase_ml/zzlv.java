package com.google.android.gms.internal.firebase_ml;

import java.util.concurrent.Executor;

enum zzlv implements Executor {
    INSTANCE;

    public final String toString() {
        return "MoreExecutors.directExecutor()";
    }

    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
