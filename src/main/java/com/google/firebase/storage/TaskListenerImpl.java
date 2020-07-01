package com.google.firebase.storage;

import android.app.Activity;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.storage.internal.ActivityLifecycleListener;
import com.google.firebase.storage.internal.SmartHandler;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class TaskListenerImpl<TListenerType, TResult extends ProvideError> {
    private final HashMap<TListenerType, SmartHandler> mHandlerMap = new HashMap();
    private final Queue<TListenerType> mListenerQueue = new ConcurrentLinkedQueue();
    private OnRaise<TListenerType, TResult> mOnRaise;
    private int mTargetStates;
    private StorageTask<TResult> mTask;

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    interface OnRaise<TListenerType, TResult> {
        void raise(@NonNull TListenerType tListenerType, @NonNull TResult tResult);
    }

    @PublicApi
    public TaskListenerImpl(@NonNull StorageTask<TResult> storageTask, int i, @NonNull OnRaise<TListenerType, TResult> onRaise) {
        this.mTask = storageTask;
        this.mTargetStates = i;
        this.mOnRaise = onRaise;
    }

    public int getListenerCount() {
        return Math.max(this.mListenerQueue.size(), this.mHandlerMap.size());
    }

    @PublicApi
    public void addListener(@Nullable Activity activity, @Nullable Executor executor, @NonNull TListenerType tListenerType) {
        Object obj;
        SmartHandler smartHandler;
        Preconditions.checkNotNull(tListenerType);
        synchronized (this.mTask.getSyncObject()) {
            boolean z = true;
            obj = (this.mTask.getInternalState() & this.mTargetStates) != 0 ? 1 : null;
            this.mListenerQueue.add(tListenerType);
            smartHandler = new SmartHandler(executor);
            this.mHandlerMap.put(tListenerType, smartHandler);
            if (activity != null) {
                if (VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        z = false;
                    }
                    Preconditions.checkArgument(z, "Activity is already destroyed!");
                }
                ActivityLifecycleListener.getInstance().runOnActivityStopped(activity, tListenerType, TaskListenerImpl$$Lambda$1.lambdaFactory$(this, tListenerType));
            }
        }
        if (obj != null) {
            smartHandler.callBack(TaskListenerImpl$$Lambda$2.lambdaFactory$(this, tListenerType, this.mTask.snapState()));
        }
    }

    @PublicApi
    public void onInternalStateChanged() {
        if ((this.mTask.getInternalState() & this.mTargetStates) != 0) {
            ProvideError snapState = this.mTask.snapState();
            for (Object next : this.mListenerQueue) {
                SmartHandler smartHandler = (SmartHandler) this.mHandlerMap.get(next);
                if (smartHandler != null) {
                    smartHandler.callBack(TaskListenerImpl$$Lambda$3.lambdaFactory$(this, next, snapState));
                }
            }
        }
    }

    @PublicApi
    /* renamed from: removeListener */
    public void lambda$addListener$0(@NonNull TListenerType tListenerType) {
        Preconditions.checkNotNull(tListenerType);
        synchronized (this.mTask.getSyncObject()) {
            this.mHandlerMap.remove(tListenerType);
            this.mListenerQueue.remove(tListenerType);
            ActivityLifecycleListener.getInstance().removeCookie(tListenerType);
        }
    }
}
