package com.google.android.gms.common.api.internal;

import android.app.Activity;
import androidx.annotation.MainThread;
import androidx.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<zaa> zaco;

    @VisibleForTesting(otherwise = 2)
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    static class zaa extends LifecycleCallback {
        private List<Runnable> zacn = new ArrayList();

        private static zaa zaa(Activity activity) {
            zaa zaa;
            synchronized (activity) {
                LifecycleFragment fragment = LifecycleCallback.getFragment(activity);
                zaa = (zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", zaa.class);
                if (zaa == null) {
                    zaa = new zaa(fragment);
                }
            }
            return zaa;
        }

        private zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        private final synchronized void zaa(Runnable runnable) {
            this.zacn.add(runnable);
        }

        @MainThread
        public void onStop() {
            synchronized (this) {
                List<Runnable> list = this.zacn;
                this.zacn = new ArrayList();
            }
            for (Runnable run : list) {
                run.run();
            }
        }
    }

    public zaa(Activity activity) {
        this(zaa.zaa(activity));
    }

    @VisibleForTesting(otherwise = 2)
    private zaa(zaa zaa) {
        this.zaco = new WeakReference(zaa);
    }

    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        zaa zaa = (zaa) this.zaco.get();
        if (zaa != null) {
            zaa.zaa(runnable);
            return this;
        }
        throw new IllegalStateException("The target activity has already been GC'd");
    }
}
