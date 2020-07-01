package com.google.android.youtube.player.internal;

import android.graphics.Bitmap;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener;
import com.google.android.youtube.player.YouTubeThumbnailView;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

public abstract class a implements YouTubeThumbnailLoader {
    private final WeakReference<YouTubeThumbnailView> a;
    private OnThumbnailLoadedListener b;
    private boolean c;
    private boolean d;

    public a(YouTubeThumbnailView youTubeThumbnailView) {
        this.a = new WeakReference(ab.a((Object) youTubeThumbnailView));
    }

    private void i() {
        if (!a()) {
            throw new IllegalStateException("This YouTubeThumbnailLoader has been released");
        }
    }

    public final void a(Bitmap bitmap, String str) {
        YouTubeThumbnailView youTubeThumbnailView = (YouTubeThumbnailView) this.a.get();
        if (a() && youTubeThumbnailView != null) {
            youTubeThumbnailView.setImageBitmap(bitmap);
            OnThumbnailLoadedListener onThumbnailLoadedListener = this.b;
            if (onThumbnailLoadedListener != null) {
                onThumbnailLoadedListener.onThumbnailLoaded(youTubeThumbnailView, str);
            }
        }
    }

    public abstract void a(String str);

    public abstract void a(String str, int i);

    protected boolean a() {
        return !this.d;
    }

    public final void b() {
        if (a()) {
            y.a("The finalize() method for a YouTubeThumbnailLoader has work to do. You should have called release().", new Object[0]);
            release();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0019 A:{ExcHandler: java.lang.IllegalArgumentException (unused java.lang.IllegalArgumentException), Splitter: B:5:0x0014} */
    /* JADX WARNING: Missing block: B:7:0x0019, code:
            r3 = com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason.UNKNOWN;
     */
    public final void b(java.lang.String r3) {
        /*
        r2 = this;
        r0 = r2.a;
        r0 = r0.get();
        r0 = (com.google.android.youtube.player.YouTubeThumbnailView) r0;
        r1 = r2.a();
        if (r1 == 0) goto L_0x0020;
    L_0x000e:
        r1 = r2.b;
        if (r1 == 0) goto L_0x0020;
    L_0x0012:
        if (r0 == 0) goto L_0x0020;
    L_0x0014:
        r3 = com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason.valueOf(r3);	 Catch:{ IllegalArgumentException -> 0x0019, IllegalArgumentException -> 0x0019 }
        goto L_0x001b;
    L_0x0019:
        r3 = com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason.UNKNOWN;
    L_0x001b:
        r1 = r2.b;
        r1.onThumbnailError(r0, r3);
    L_0x0020:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.internal.a.b(java.lang.String):void");
    }

    public abstract void c();

    public abstract void d();

    public abstract void e();

    public abstract boolean f();

    public final void first() {
        i();
        if (this.c) {
            e();
            return;
        }
        throw new IllegalStateException("Must call setPlaylist first");
    }

    public abstract boolean g();

    public abstract void h();

    public final boolean hasNext() {
        i();
        return f();
    }

    public final boolean hasPrevious() {
        i();
        return g();
    }

    public final void next() {
        i();
        if (!this.c) {
            throw new IllegalStateException("Must call setPlaylist first");
        } else if (f()) {
            c();
        } else {
            throw new NoSuchElementException("Called next at end of playlist");
        }
    }

    public final void previous() {
        i();
        if (!this.c) {
            throw new IllegalStateException("Must call setPlaylist first");
        } else if (g()) {
            d();
        } else {
            throw new NoSuchElementException("Called previous at start of playlist");
        }
    }

    public final void release() {
        if (a()) {
            this.d = true;
            this.b = null;
            h();
        }
    }

    public final void setOnThumbnailLoadedListener(OnThumbnailLoadedListener onThumbnailLoadedListener) {
        i();
        this.b = onThumbnailLoadedListener;
    }

    public final void setPlaylist(String str) {
        setPlaylist(str, 0);
    }

    public final void setPlaylist(String str, int i) {
        i();
        this.c = true;
        a(str, i);
    }

    public final void setVideo(String str) {
        i();
        this.c = false;
        a(str);
    }
}
