package com.google.android.gms.dynamic;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zag implements zaa {
    private final /* synthetic */ DeferredLifecycleHelper zart;

    zag(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.zart = deferredLifecycleHelper;
    }

    public final int getState() {
        return 4;
    }

    public final void zaa(LifecycleDelegate lifecycleDelegate) {
        this.zart.zaru.onStart();
    }
}
