package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaz {
    private final Map<BasePendingResult<?>, Boolean> zafm = Collections.synchronizedMap(new WeakHashMap());
    private final Map<TaskCompletionSource<?>, Boolean> zafn = Collections.synchronizedMap(new WeakHashMap());

    final void zaa(BasePendingResult<? extends Result> basePendingResult, boolean z) {
        this.zafm.put(basePendingResult, Boolean.valueOf(z));
        basePendingResult.addStatusListener(new zaac(this, basePendingResult));
    }

    final <TResult> void zaa(TaskCompletionSource<TResult> taskCompletionSource, boolean z) {
        this.zafn.put(taskCompletionSource, Boolean.valueOf(z));
        taskCompletionSource.getTask().addOnCompleteListener(new zaab(this, taskCompletionSource));
    }

    final boolean zaae() {
        return (this.zafm.isEmpty() && this.zafn.isEmpty()) ? false : true;
    }

    public final void zaaf() {
        zaa(false, GoogleApiManager.zaib);
    }

    public final void zaag() {
        zaa(true, zacp.zalb);
    }

    private final void zaa(boolean z, Status status) {
        synchronized (this.zafm) {
            Map hashMap = new HashMap(this.zafm);
        }
        synchronized (this.zafn) {
            Map hashMap2 = new HashMap(this.zafn);
        }
        for (Entry entry : hashMap.entrySet()) {
            if (z || ((Boolean) entry.getValue()).booleanValue()) {
                ((BasePendingResult) entry.getKey()).zab(status);
            }
        }
        for (Entry entry2 : hashMap2.entrySet()) {
            if (z || ((Boolean) entry2.getValue()).booleanValue()) {
                ((TaskCompletionSource) entry2.getKey()).trySetException(new ApiException(status));
            }
        }
    }
}
