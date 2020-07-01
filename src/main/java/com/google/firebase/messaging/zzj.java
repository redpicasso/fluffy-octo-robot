package com.google.firebase.messaging;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_messaging.zzk;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-messaging@@20.0.0 */
final class zzj implements Closeable {
    private final URL zza;
    @Nullable
    private Task<Bitmap> zzb;
    @Nullable
    private volatile InputStream zzc;

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.firebase.messaging.zzj.zza(java.lang.String):com.google.firebase.messaging.zzj, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.lang.Iterable.forEach(Unknown Source)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    @androidx.annotation.Nullable
    public static com.google.firebase.messaging.zzj zza(java.lang.String r3) {
        /*
        r0 = android.text.TextUtils.isEmpty(r3);
        r1 = 0;
        if (r0 == 0) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = new com.google.firebase.messaging.zzj;	 Catch:{ MalformedURLException -> 0x0013 }
        r2 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0013 }
        r2.<init>(r3);	 Catch:{ MalformedURLException -> 0x0013 }
        r0.<init>(r2);	 Catch:{ MalformedURLException -> 0x0013 }
        return r0;
        r0 = "Not downloading image, bad URL: ";
        r3 = java.lang.String.valueOf(r3);
        r2 = r3.length();
        if (r2 == 0) goto L_0x0025;
    L_0x0020:
        r3 = r0.concat(r3);
        goto L_0x002a;
    L_0x0025:
        r3 = new java.lang.String;
        r3.<init>(r0);
    L_0x002a:
        r0 = "FirebaseMessaging";
        android.util.Log.w(r0, r3);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zzj.zza(java.lang.String):com.google.firebase.messaging.zzj");
    }

    private zzj(URL url) {
        this.zza = url;
    }

    public final void zza(Executor executor) {
        this.zzb = Tasks.call(executor, new zzi(this));
    }

    public final Task<Bitmap> zza() {
        return (Task) Preconditions.checkNotNull(this.zzb);
    }

    public final Bitmap zzb() throws IOException {
        String valueOf = String.valueOf(this.zza);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 22);
        stringBuilder.append("Starting download of: ");
        stringBuilder.append(valueOf);
        String str = "FirebaseMessaging";
        Log.i(str, stringBuilder.toString());
        byte[] zzc = zzc();
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(zzc, 0, zzc.length);
        if (decodeByteArray != null) {
            if (Log.isLoggable(str, 3)) {
                String valueOf2 = String.valueOf(this.zza);
                StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(valueOf2).length() + 31);
                stringBuilder2.append("Successfully downloaded image: ");
                stringBuilder2.append(valueOf2);
                Log.d(str, stringBuilder2.toString());
            }
            return decodeByteArray;
        }
        str = String.valueOf(this.zza);
        StringBuilder stringBuilder3 = new StringBuilder(String.valueOf(str).length() + 24);
        stringBuilder3.append("Failed to decode image: ");
        stringBuilder3.append(str);
        throw new IOException(stringBuilder3.toString());
    }

    /* JADX WARNING: Missing block: B:19:0x006b, code:
            if (r0 != null) goto L_0x006d;
     */
    /* JADX WARNING: Missing block: B:20:0x006d, code:
            if (r1 != null) goto L_0x006f;
     */
    /* JADX WARNING: Missing block: B:22:?, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:23:0x0073, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:24:0x0074, code:
            com.google.android.gms.internal.firebase_messaging.zzm.zza(r1, r0);
     */
    /* JADX WARNING: Missing block: B:25:0x0078, code:
            r0.close();
     */
    private final byte[] zzc() throws java.io.IOException {
        /*
        r7 = this;
        r0 = r7.zza;
        r0 = r0.openConnection();
        r1 = r0.getContentLength();
        r2 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        if (r1 > r2) goto L_0x007c;
    L_0x000e:
        r0 = r0.getInputStream();
        r1 = 0;
        r7.zzc = r0;	 Catch:{ Throwable -> 0x0069 }
        r3 = 1048577; // 0x100001 float:1.46937E-39 double:5.18066E-318;
        r3 = com.google.android.gms.internal.firebase_messaging.zzj.zza(r0, r3);	 Catch:{ Throwable -> 0x0069 }
        r1 = com.google.android.gms.internal.firebase_messaging.zzj.zza(r3);	 Catch:{ Throwable -> 0x0069 }
        if (r0 == 0) goto L_0x0025;
    L_0x0022:
        r0.close();
    L_0x0025:
        r0 = 2;
        r3 = "FirebaseMessaging";
        r0 = android.util.Log.isLoggable(r3, r0);
        if (r0 == 0) goto L_0x005b;
    L_0x002e:
        r0 = r1.length;
        r4 = r7.zza;
        r4 = java.lang.String.valueOf(r4);
        r5 = java.lang.String.valueOf(r4);
        r5 = r5.length();
        r5 = r5 + 34;
        r6 = new java.lang.StringBuilder;
        r6.<init>(r5);
        r5 = "Downloaded ";
        r6.append(r5);
        r6.append(r0);
        r0 = " bytes from ";
        r6.append(r0);
        r6.append(r4);
        r0 = r6.toString();
        android.util.Log.v(r3, r0);
    L_0x005b:
        r0 = r1.length;
        if (r0 > r2) goto L_0x005f;
    L_0x005e:
        return r1;
    L_0x005f:
        r0 = new java.io.IOException;
        r1 = "Image exceeds max size of 1048576";
        r0.<init>(r1);
        throw r0;
    L_0x0067:
        r2 = move-exception;
        goto L_0x006b;
    L_0x0069:
        r1 = move-exception;
        throw r1;	 Catch:{ all -> 0x0067 }
    L_0x006b:
        if (r0 == 0) goto L_0x007b;
    L_0x006d:
        if (r1 == 0) goto L_0x0078;
    L_0x006f:
        r0.close();	 Catch:{ Throwable -> 0x0073 }
        goto L_0x007b;
    L_0x0073:
        r0 = move-exception;
        com.google.android.gms.internal.firebase_messaging.zzm.zza(r1, r0);
        goto L_0x007b;
    L_0x0078:
        r0.close();
    L_0x007b:
        throw r2;
    L_0x007c:
        r0 = new java.io.IOException;
        r1 = "Content-Length exceeds max size of 1048576";
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zzj.zzc():byte[]");
    }

    public final void close() {
        zzk.zza(this.zzc);
    }
}
