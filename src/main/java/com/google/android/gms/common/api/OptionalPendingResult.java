package com.google.android.gms.common.api;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public abstract class OptionalPendingResult<R extends Result> extends PendingResult<R> {
    public abstract R get();

    public abstract boolean isDone();
}
