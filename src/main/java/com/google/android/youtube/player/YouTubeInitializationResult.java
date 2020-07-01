package com.google.android.youtube.player;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.google.android.youtube.player.internal.ab;
import com.google.android.youtube.player.internal.y;

public enum YouTubeInitializationResult {
    SUCCESS,
    INTERNAL_ERROR,
    UNKNOWN_ERROR,
    SERVICE_MISSING,
    SERVICE_VERSION_UPDATE_REQUIRED,
    SERVICE_DISABLED,
    SERVICE_INVALID,
    ERROR_CONNECTING_TO_SERVICE,
    CLIENT_LIBRARY_UPDATE_REQUIRED,
    NETWORK_ERROR,
    DEVELOPER_KEY_INVALID,
    INVALID_APPLICATION_SIGNATURE;

    /* renamed from: com.google.android.youtube.player.YouTubeInitializationResult$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = null;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            a[com.google.android.youtube.player.YouTubeInitializationResult.SERVICE_VERSION_UPDATE_REQUIRED.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.google.android.youtube.player.YouTubeInitializationResult.values();
            r0 = r0.length;
            r0 = new int[r0];
            a = r0;
            r0 = a;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.android.youtube.player.YouTubeInitializationResult.SERVICE_MISSING;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = a;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.android.youtube.player.YouTubeInitializationResult.SERVICE_DISABLED;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = a;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.android.youtube.player.YouTubeInitializationResult.SERVICE_VERSION_UPDATE_REQUIRED;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.YouTubeInitializationResult.1.<clinit>():void");
        }
    }

    private static final class a implements OnClickListener {
        private final Activity a;
        private final Intent b;
        private final int c;

        public a(Activity activity, Intent intent, int i) {
            this.a = (Activity) ab.a((Object) activity);
            this.b = (Intent) ab.a((Object) intent);
            this.c = ((Integer) ab.a(Integer.valueOf(i))).intValue();
        }

        public final void onClick(DialogInterface dialogInterface, int i) {
            try {
                this.a.startActivityForResult(this.b, this.c);
                dialogInterface.dismiss();
            } catch (Throwable e) {
                y.a("Can't perform resolution for YouTubeInitalizationError", e);
            }
        }
    }

    public final Dialog getErrorDialog(Activity activity, int i) {
        return getErrorDialog(activity, i, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0042  */
    public final android.app.Dialog getErrorDialog(android.app.Activity r6, int r7, android.content.DialogInterface.OnCancelListener r8) {
        /*
        r5 = this;
        r0 = new android.app.AlertDialog$Builder;
        r0.<init>(r6);
        if (r8 == 0) goto L_0x000a;
    L_0x0007:
        r0.setOnCancelListener(r8);
    L_0x000a:
        r8 = com.google.android.youtube.player.YouTubeInitializationResult.AnonymousClass1.a;
        r1 = r5.ordinal();
        r8 = r8[r1];
        r1 = 3;
        r2 = 2;
        r3 = 1;
        if (r8 == r3) goto L_0x0026;
    L_0x0017:
        if (r8 == r2) goto L_0x001d;
    L_0x0019:
        if (r8 == r1) goto L_0x0026;
    L_0x001b:
        r8 = 0;
        goto L_0x002e;
    L_0x001d:
        r8 = com.google.android.youtube.player.internal.z.a(r6);
        r8 = com.google.android.youtube.player.internal.z.a(r8);
        goto L_0x002e;
    L_0x0026:
        r8 = com.google.android.youtube.player.internal.z.a(r6);
        r8 = com.google.android.youtube.player.internal.z.b(r8);
    L_0x002e:
        r4 = new com.google.android.youtube.player.YouTubeInitializationResult$a;
        r4.<init>(r6, r8, r7);
        r7 = new com.google.android.youtube.player.internal.m;
        r7.<init>(r6);
        r6 = com.google.android.youtube.player.YouTubeInitializationResult.AnonymousClass1.a;
        r8 = r5.ordinal();
        r6 = r6[r8];
        if (r6 == r3) goto L_0x008d;
    L_0x0042:
        if (r6 == r2) goto L_0x007e;
    L_0x0044:
        if (r6 == r1) goto L_0x0067;
    L_0x0046:
        r6 = new java.lang.IllegalArgumentException;
        r7 = "Unexpected errorReason: ";
        r8 = r5.name();
        r8 = java.lang.String.valueOf(r8);
        r0 = r8.length();
        if (r0 == 0) goto L_0x005d;
    L_0x0058:
        r7 = r7.concat(r8);
        goto L_0x0063;
    L_0x005d:
        r8 = new java.lang.String;
        r8.<init>(r7);
        r7 = r8;
    L_0x0063:
        r6.<init>(r7);
        throw r6;
    L_0x0067:
        r6 = r7.h;
        r6 = r0.setTitle(r6);
        r8 = r7.i;
        r6 = r6.setMessage(r8);
        r7 = r7.j;
    L_0x0075:
        r6 = r6.setPositiveButton(r7, r4);
        r6 = r6.create();
        return r6;
    L_0x007e:
        r6 = r7.e;
        r6 = r0.setTitle(r6);
        r8 = r7.f;
        r6 = r6.setMessage(r8);
        r7 = r7.g;
        goto L_0x0075;
    L_0x008d:
        r6 = r7.b;
        r6 = r0.setTitle(r6);
        r8 = r7.c;
        r6 = r6.setMessage(r8);
        r7 = r7.d;
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.youtube.player.YouTubeInitializationResult.getErrorDialog(android.app.Activity, int, android.content.DialogInterface$OnCancelListener):android.app.Dialog");
    }

    public final boolean isUserRecoverableError() {
        int i = AnonymousClass1.a[ordinal()];
        return i == 1 || i == 2 || i == 3;
    }
}
