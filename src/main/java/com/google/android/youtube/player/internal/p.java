package com.google.android.youtube.player.internal;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.youtube.player.YouTubeThumbnailView;

public final class p extends a {
    private final Handler a = new Handler(Looper.getMainLooper());
    private b b;
    private k c;
    private boolean d;
    private boolean e;

    private final class a extends com.google.android.youtube.player.internal.j.a {
        private a() {
        }

        /* synthetic */ a(p pVar, byte b) {
            this();
        }

        public final void a(Bitmap bitmap, String str, boolean z, boolean z2) {
            final boolean z3 = z;
            final boolean z4 = z2;
            final Bitmap bitmap2 = bitmap;
            final String str2 = str;
            p.this.a.post(new Runnable() {
                public final void run() {
                    p.this.d = z3;
                    p.this.e = z4;
                    p.this.a(bitmap2, str2);
                }
            });
        }

        public final void a(final String str, final boolean z, final boolean z2) {
            p.this.a.post(new Runnable() {
                public final void run() {
                    p.this.d = z;
                    p.this.e = z2;
                    p.this.b(str);
                }
            });
        }
    }

    public p(b bVar, YouTubeThumbnailView youTubeThumbnailView) {
        super(youTubeThumbnailView);
        this.b = (b) ab.a((Object) bVar, (Object) "connectionClient cannot be null");
        this.c = bVar.a(new a(this, (byte) 0));
    }

    public final void a(String str) {
        try {
            this.c.a(str);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public final void a(String str, int i) {
        try {
            this.c.a(str, i);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    protected final boolean a() {
        return super.a() && this.c != null;
    }

    public final void c() {
        try {
            this.c.a();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public final void d() {
        try {
            this.c.b();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public final void e() {
        try {
            this.c.c();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public final boolean f() {
        return this.e;
    }

    public final boolean g() {
        return this.d;
    }

    public final void h() {
        try {
            this.c.d();
        } catch (RemoteException unused) {
            this.b.d();
            this.c = null;
            this.b = null;
        }
    }
}
