package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zac extends zab {
    private WeakReference<OnImageLoadedListener> zang;

    public zac(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        Asserts.checkNotNull(onImageLoadedListener);
        this.zang = new WeakReference(onImageLoadedListener);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zamz);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zac)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zac zac = (zac) obj;
        OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zang.get();
        OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) zac.zang.get();
        return onImageLoadedListener2 != null && onImageLoadedListener != null && Objects.equal(onImageLoadedListener2, onImageLoadedListener) && Objects.equal(zac.zamz, this.zamz);
    }

    protected final void zaa(Drawable drawable, boolean z, boolean z2, boolean z3) {
        if (!z2) {
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zang.get();
            if (onImageLoadedListener != null) {
                onImageLoadedListener.onImageLoaded(this.zamz.uri, drawable, z3);
            }
        }
    }
}
