package com.google.android.youtube.player.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.youtube.player.internal.t.a;
import com.google.android.youtube.player.internal.t.b;

public final class o extends r<l> implements b {
    private final String b;
    private final String c;
    private final String d;
    private boolean e;

    public o(Context context, String str, String str2, String str3, a aVar, b bVar) {
        super(context, aVar, bVar);
        this.b = (String) ab.a((Object) str);
        this.c = ab.a(str2, (Object) "callingPackage cannot be null or empty");
        this.d = ab.a(str3, (Object) "callingAppVersion cannot be null or empty");
    }

    private final void k() {
        i();
        if (this.e) {
            throw new IllegalStateException("Connection client has been released");
        }
    }

    public final IBinder a() {
        k();
        try {
            return ((l) j()).a();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public final k a(j jVar) {
        k();
        try {
            return ((l) j()).a(jVar);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    protected final void a(i iVar, d dVar) throws RemoteException {
        iVar.a(dVar, 1202, this.c, this.d, this.b, null);
    }

    public final void a(boolean z) {
        if (f()) {
            try {
                ((l) j()).a(z);
            } catch (RemoteException unused) {
                this.e = true;
            }
        }
    }

    protected final String b() {
        return "com.google.android.youtube.player.internal.IYouTubeService";
    }

    protected final String c() {
        return "com.google.android.youtube.api.service.START";
    }

    public final void d() {
        if (!this.e) {
            a(true);
        }
        super.d();
    }
}
