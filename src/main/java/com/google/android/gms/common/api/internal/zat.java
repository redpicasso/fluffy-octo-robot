package com.google.android.gms.common.api.internal;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
final class zat implements Runnable {
    private final /* synthetic */ zaq zaet;

    zat(zaq zaq) {
        this.zaet = zaq;
    }

    public final void run() {
        this.zaet.zaer.lock();
        try {
            this.zaet.zav();
        } finally {
            this.zaet.zaer.unlock();
        }
    }
}
