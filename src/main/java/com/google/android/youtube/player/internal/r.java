package com.google.android.youtube.player.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import java.util.ArrayList;

public abstract class r<T extends IInterface> implements t {
    final Handler a;
    private final Context b;
    private T c;
    private ArrayList<com.google.android.youtube.player.internal.t.a> d;
    private final ArrayList<com.google.android.youtube.player.internal.t.a> e = new ArrayList();
    private boolean f = false;
    private ArrayList<com.google.android.youtube.player.internal.t.b> g;
    private boolean h = false;
    private final ArrayList<b<?>> i = new ArrayList();
    private ServiceConnection j;
    private boolean k = false;

    /* renamed from: com.google.android.youtube.player.internal.r$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[YouTubeInitializationResult.values().length];

        static {
            try {
                a[YouTubeInitializationResult.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    final class a extends Handler {
        a() {
        }

        public final void handleMessage(Message message) {
            if (message.what == 3) {
                r.this.a((YouTubeInitializationResult) message.obj);
            } else if (message.what == 4) {
                synchronized (r.this.d) {
                    if (r.this.k && r.this.f() && r.this.d.contains(message.obj)) {
                        ((com.google.android.youtube.player.internal.t.a) message.obj).a();
                    }
                }
            } else if (message.what == 2 && !r.this.f()) {
            } else {
                if (message.what == 2 || message.what == 1) {
                    ((b) message.obj).a();
                }
            }
        }
    }

    protected abstract class b<TListener> {
        private TListener b;

        public b(TListener tListener) {
            this.b = tListener;
            synchronized (r.this.i) {
                r.this.i.add(this);
            }
        }

        public final void a() {
            Object obj;
            synchronized (this) {
                obj = this.b;
            }
            a(obj);
        }

        protected abstract void a(TListener tListener);

        public final void b() {
            synchronized (this) {
                this.b = null;
            }
        }
    }

    final class e implements ServiceConnection {
        e() {
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            r.this.b(iBinder);
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            r.this.c = null;
            r.this.h();
        }
    }

    protected final class c extends b<Boolean> {
        public final YouTubeInitializationResult b;
        public final IBinder c;

        public c(String str, IBinder iBinder) {
            super(Boolean.valueOf(true));
            this.b = r.b(str);
            this.c = iBinder;
        }

        protected final /* synthetic */ void a(Object obj) {
            if (((Boolean) obj) != null) {
                if (AnonymousClass1.a[this.b.ordinal()] != 1) {
                    r.this.a(this.b);
                } else {
                    try {
                        if (r.this.b().equals(this.c.getInterfaceDescriptor())) {
                            r.this.c = r.this.a(this.c);
                            if (r.this.c != null) {
                                r.this.g();
                            }
                        }
                    } catch (RemoteException unused) {
                        r.this.a();
                        r.this.a(YouTubeInitializationResult.INTERNAL_ERROR);
                    }
                }
            }
        }
    }

    protected final class d extends com.google.android.youtube.player.internal.c.a {
        protected d() {
        }

        public final void a(String str, IBinder iBinder) {
            r.this.a.sendMessage(r.this.a.obtainMessage(1, new c(str, iBinder)));
        }
    }

    protected r(Context context, com.google.android.youtube.player.internal.t.a aVar, com.google.android.youtube.player.internal.t.b bVar) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            this.b = (Context) ab.a((Object) context);
            this.d = new ArrayList();
            this.d.add(ab.a((Object) aVar));
            this.g = new ArrayList();
            this.g.add(ab.a((Object) bVar));
            this.a = new a();
            return;
        }
        throw new IllegalStateException("Clients must be created on the UI thread.");
    }

    private void a() {
        ServiceConnection serviceConnection = this.j;
        if (serviceConnection != null) {
            try {
                this.b.unbindService(serviceConnection);
            } catch (Throwable e) {
                Log.w("YouTubeClient", "Unexpected error from unbindService()", e);
            }
        }
        this.c = null;
        this.j = null;
    }

    private static YouTubeInitializationResult b(String str) {
        try {
            return YouTubeInitializationResult.valueOf(str);
        } catch (IllegalArgumentException unused) {
            return YouTubeInitializationResult.UNKNOWN_ERROR;
        } catch (NullPointerException unused2) {
            return YouTubeInitializationResult.UNKNOWN_ERROR;
        }
    }

    protected abstract T a(IBinder iBinder);

    protected final void a(YouTubeInitializationResult youTubeInitializationResult) {
        this.a.removeMessages(4);
        synchronized (this.g) {
            this.h = true;
            ArrayList arrayList = this.g;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                if (this.k) {
                    if (this.g.contains(arrayList.get(i))) {
                        ((com.google.android.youtube.player.internal.t.b) arrayList.get(i)).a(youTubeInitializationResult);
                    }
                    i++;
                } else {
                    return;
                }
            }
            this.h = false;
        }
    }

    protected abstract void a(i iVar, d dVar) throws RemoteException;

    protected abstract String b();

    protected final void b(IBinder iBinder) {
        try {
            a(com.google.android.youtube.player.internal.i.a.a(iBinder), new d());
        } catch (RemoteException unused) {
            Log.w("YouTubeClient", "service died");
        }
    }

    protected abstract String c();

    public void d() {
        h();
        int i = 0;
        this.k = false;
        synchronized (this.i) {
            int size = this.i.size();
            while (i < size) {
                ((b) this.i.get(i)).b();
                i++;
            }
            this.i.clear();
        }
        a();
    }

    public final void e() {
        this.k = true;
        YouTubeInitializationResult isYouTubeApiServiceAvailable = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this.b);
        if (isYouTubeApiServiceAvailable != YouTubeInitializationResult.SUCCESS) {
            Handler handler = this.a;
            handler.sendMessage(handler.obtainMessage(3, isYouTubeApiServiceAvailable));
            return;
        }
        Intent intent = new Intent(c()).setPackage(z.a(this.b));
        if (this.j != null) {
            Log.e("YouTubeClient", "Calling connect() while still connected, missing disconnect().");
            a();
        }
        this.j = new e();
        if (!this.b.bindService(intent, this.j, 129)) {
            Handler handler2 = this.a;
            handler2.sendMessage(handler2.obtainMessage(3, YouTubeInitializationResult.ERROR_CONNECTING_TO_SERVICE));
        }
    }

    public final boolean f() {
        return this.c != null;
    }

    protected final void g() {
        synchronized (this.d) {
            boolean z = true;
            ab.a(!this.f);
            this.a.removeMessages(4);
            this.f = true;
            if (this.e.size() != 0) {
                z = false;
            }
            ab.a(z);
            ArrayList arrayList = this.d;
            int size = arrayList.size();
            for (int i = 0; i < size && this.k && f(); i++) {
                if (!this.e.contains(arrayList.get(i))) {
                    ((com.google.android.youtube.player.internal.t.a) arrayList.get(i)).a();
                }
            }
            this.e.clear();
            this.f = false;
        }
    }

    protected final void h() {
        this.a.removeMessages(4);
        synchronized (this.d) {
            this.f = true;
            ArrayList arrayList = this.d;
            int size = arrayList.size();
            for (int i = 0; i < size && this.k; i++) {
                if (this.d.contains(arrayList.get(i))) {
                    ((com.google.android.youtube.player.internal.t.a) arrayList.get(i)).b();
                }
            }
            this.f = false;
        }
    }

    protected final void i() {
        if (!f()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    protected final T j() {
        i();
        return this.c;
    }
}
