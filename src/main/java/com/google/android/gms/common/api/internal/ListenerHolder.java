package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zar;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class ListenerHolder<L> {
    private final zaa zajm;
    private volatile L zajn;
    private final ListenerKey<L> zajo;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static final class ListenerKey<L> {
        private final L zajn;
        private final String zajp;

        @KeepForSdk
        ListenerKey(L l, String str) {
            this.zajn = l;
            this.zajp = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ListenerKey)) {
                return false;
            }
            ListenerKey listenerKey = (ListenerKey) obj;
            return this.zajn == listenerKey.zajn && this.zajp.equals(listenerKey.zajp);
        }

        public final int hashCode() {
            return (System.identityHashCode(this.zajn) * 31) + this.zajp.hashCode();
        }
    }

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public interface Notifier<L> {
        @KeepForSdk
        void notifyListener(L l);

        @KeepForSdk
        void onNotifyListenerFailed();
    }

    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    private final class zaa extends zar {
        public zaa(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            boolean z = true;
            if (message.what != 1) {
                z = false;
            }
            Preconditions.checkArgument(z);
            ListenerHolder.this.notifyListenerInternal((Notifier) message.obj);
        }
    }

    @KeepForSdk
    ListenerHolder(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.zajm = new zaa(looper);
        this.zajn = Preconditions.checkNotNull(l, "Listener must not be null");
        this.zajo = new ListenerKey(l, Preconditions.checkNotEmpty(str));
    }

    @KeepForSdk
    public final void notifyListener(Notifier<? super L> notifier) {
        Preconditions.checkNotNull(notifier, "Notifier must not be null");
        this.zajm.sendMessage(this.zajm.obtainMessage(1, notifier));
    }

    @KeepForSdk
    public final boolean hasListener() {
        return this.zajn != null;
    }

    @KeepForSdk
    public final void clear() {
        this.zajn = null;
    }

    @NonNull
    @KeepForSdk
    public final ListenerKey<L> getListenerKey() {
        return this.zajo;
    }

    @KeepForSdk
    final void notifyListenerInternal(Notifier<? super L> notifier) {
        Object obj = this.zajn;
        if (obj == null) {
            notifier.onNotifyListenerFailed();
            return;
        }
        try {
            notifier.notifyListener(obj);
        } catch (RuntimeException e) {
            notifier.onNotifyListenerFailed();
            throw e;
        }
    }
}
