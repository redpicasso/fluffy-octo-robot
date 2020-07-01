package com.google.android.gms.dynamic;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zaf implements zaa {
    private final /* synthetic */ DeferredLifecycleHelper zart;

    zaf(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.zart = deferredLifecycleHelper;
    }

    public final int getState() {
        return 5;
    }

    public final void zaa(LifecycleDelegate lifecycleDelegate) {
        this.zart.zaru.onResume();
    }
}
