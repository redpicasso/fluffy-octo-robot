package androidx.core.content.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.content.pm.ShortcutInfoCompat.Builder;
import java.util.ArrayList;
import java.util.List;

public class ShortcutManagerCompat {
    @VisibleForTesting
    static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
    @VisibleForTesting
    static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT";
    private static volatile ShortcutInfoCompatSaver<?> sShortcutInfoCompatSaver;

    private ShortcutManagerCompat() {
    }

    public static boolean isRequestPinShortcutSupported(@NonNull Context context) {
        if (VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported();
        }
        String str = INSTALL_SHORTCUT_PERMISSION;
        if (ContextCompat.checkSelfPermission(context, str) != 0) {
            return false;
        }
        for (ResolveInfo resolveInfo : context.getPackageManager().queryBroadcastReceivers(new Intent(ACTION_INSTALL_SHORTCUT), 0)) {
            CharSequence charSequence = resolveInfo.activityInfo.permission;
            if (!TextUtils.isEmpty(charSequence)) {
                if (str.equals(charSequence)) {
                }
            }
            return true;
        }
        return false;
    }

    public static boolean requestPinShortcut(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat, @Nullable final IntentSender intentSender) {
        if (VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).requestPinShortcut(shortcutInfoCompat.toShortcutInfo(), intentSender);
        }
        if (!isRequestPinShortcutSupported(context)) {
            return false;
        }
        Intent addToIntent = shortcutInfoCompat.addToIntent(new Intent(ACTION_INSTALL_SHORTCUT));
        if (intentSender == null) {
            context.sendBroadcast(addToIntent);
            return true;
        }
        context.sendOrderedBroadcast(addToIntent, null, new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                try {
                    intentSender.sendIntent(context, 0, null, null, null);
                } catch (SendIntentException unused) {
                }
            }
        }, null, -1, null, null);
        return true;
    }

    @NonNull
    public static Intent createShortcutResultIntent(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Intent createShortcutResultIntent = VERSION.SDK_INT >= 26 ? ((ShortcutManager) context.getSystemService(ShortcutManager.class)).createShortcutResultIntent(shortcutInfoCompat.toShortcutInfo()) : null;
        if (createShortcutResultIntent == null) {
            createShortcutResultIntent = new Intent();
        }
        return shortcutInfoCompat.addToIntent(createShortcutResultIntent);
    }

    public static boolean addDynamicShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        if (VERSION.SDK_INT >= 25) {
            List arrayList = new ArrayList();
            for (ShortcutInfoCompat toShortcutInfo : list) {
                arrayList.add(toShortcutInfo.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).addDynamicShortcuts(arrayList)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).addShortcuts(list);
        return true;
    }

    public static int getMaxShortcutCountPerActivity(@NonNull Context context) {
        return VERSION.SDK_INT >= 25 ? ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getMaxShortcutCountPerActivity() : 0;
    }

    @NonNull
    public static List<ShortcutInfoCompat> getDynamicShortcuts(@NonNull Context context) {
        if (VERSION.SDK_INT >= 25) {
            List<ShortcutInfo> dynamicShortcuts = ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getDynamicShortcuts();
            List<ShortcutInfoCompat> arrayList = new ArrayList(dynamicShortcuts.size());
            for (ShortcutInfo builder : dynamicShortcuts) {
                arrayList.add(new Builder(context, builder).build());
            }
            return arrayList;
        }
        try {
            return getShortcutInfoSaverInstance(context).getShortcuts();
        } catch (Exception unused) {
            return new ArrayList();
        }
    }

    public static boolean updateShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        if (VERSION.SDK_INT >= 25) {
            List arrayList = new ArrayList();
            for (ShortcutInfoCompat toShortcutInfo : list) {
                arrayList.add(toShortcutInfo.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).updateShortcuts(arrayList)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).addShortcuts(list);
        return true;
    }

    public void removeDynamicShortcuts(@NonNull Context context, @NonNull List<String> list) {
        if (VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeDynamicShortcuts(list);
        }
        getShortcutInfoSaverInstance(context).removeShortcuts(list);
    }

    public static void removeAllDynamicShortcuts(@NonNull Context context) {
        if (VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeAllDynamicShortcuts();
        }
        getShortcutInfoSaverInstance(context).removeAllShortcuts();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:androidx.core.content.pm.ShortcutManagerCompat.getShortcutInfoSaverInstance(android.content.Context):androidx.core.content.pm.ShortcutInfoCompatSaver, dom blocks: []
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
        */
    private static androidx.core.content.pm.ShortcutInfoCompatSaver getShortcutInfoSaverInstance(android.content.Context r6) {
        /*
        r0 = sShortcutInfoCompatSaver;
        if (r0 != 0) goto L_0x003e;
    L_0x0004:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 23;
        if (r0 < r1) goto L_0x0033;
    L_0x000a:
        r0 = androidx.core.content.pm.ShortcutManagerCompat.class;	 Catch:{ Exception -> 0x0032 }
        r0 = r0.getClassLoader();	 Catch:{ Exception -> 0x0032 }
        r1 = "androidx.sharetarget.ShortcutInfoCompatSaverImpl";	 Catch:{ Exception -> 0x0032 }
        r2 = 0;	 Catch:{ Exception -> 0x0032 }
        r0 = java.lang.Class.forName(r1, r2, r0);	 Catch:{ Exception -> 0x0032 }
        r1 = "getInstance";	 Catch:{ Exception -> 0x0032 }
        r3 = 1;	 Catch:{ Exception -> 0x0032 }
        r4 = new java.lang.Class[r3];	 Catch:{ Exception -> 0x0032 }
        r5 = android.content.Context.class;	 Catch:{ Exception -> 0x0032 }
        r4[r2] = r5;	 Catch:{ Exception -> 0x0032 }
        r0 = r0.getMethod(r1, r4);	 Catch:{ Exception -> 0x0032 }
        r1 = 0;	 Catch:{ Exception -> 0x0032 }
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x0032 }
        r3[r2] = r6;	 Catch:{ Exception -> 0x0032 }
        r6 = r0.invoke(r1, r3);	 Catch:{ Exception -> 0x0032 }
        r6 = (androidx.core.content.pm.ShortcutInfoCompatSaver) r6;	 Catch:{ Exception -> 0x0032 }
        sShortcutInfoCompatSaver = r6;	 Catch:{ Exception -> 0x0032 }
        goto L_0x0033;
    L_0x0033:
        r6 = sShortcutInfoCompatSaver;
        if (r6 != 0) goto L_0x003e;
    L_0x0037:
        r6 = new androidx.core.content.pm.ShortcutInfoCompatSaver$NoopImpl;
        r6.<init>();
        sShortcutInfoCompatSaver = r6;
    L_0x003e:
        r6 = sShortcutInfoCompatSaver;
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.pm.ShortcutManagerCompat.getShortcutInfoSaverInstance(android.content.Context):androidx.core.content.pm.ShortcutInfoCompatSaver");
    }
}
