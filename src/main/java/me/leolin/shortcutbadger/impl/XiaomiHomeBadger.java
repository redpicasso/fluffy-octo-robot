package me.leolin.shortcutbadger.impl;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

@Deprecated
public class XiaomiHomeBadger implements Badger {
    public static final String EXTRA_UPDATE_APP_COMPONENT_NAME = "android.intent.extra.update_application_component_name";
    public static final String EXTRA_UPDATE_APP_MSG_TEXT = "android.intent.extra.update_application_message_text";
    public static final String INTENT_ACTION = "android.intent.action.APPLICATION_MESSAGE_UPDATE";
    private ResolveInfo resolveInfo;

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:me.leolin.shortcutbadger.impl.XiaomiHomeBadger.executeBadge(android.content.Context, android.content.ComponentName, int):void, dom blocks: []
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
    public void executeBadge(android.content.Context r5, android.content.ComponentName r6, int r7) throws me.leolin.shortcutbadger.ShortcutBadgeException {
        /*
        r4 = this;
        r0 = "";
        r1 = "android.app.MiuiNotification";	 Catch:{ Exception -> 0x0032 }
        r1 = java.lang.Class.forName(r1);	 Catch:{ Exception -> 0x0032 }
        r1 = r1.newInstance();	 Catch:{ Exception -> 0x0032 }
        r2 = r1.getClass();	 Catch:{ Exception -> 0x0032 }
        r3 = "messageCount";	 Catch:{ Exception -> 0x0032 }
        r2 = r2.getDeclaredField(r3);	 Catch:{ Exception -> 0x0032 }
        r3 = 1;	 Catch:{ Exception -> 0x0032 }
        r2.setAccessible(r3);	 Catch:{ Exception -> 0x0032 }
        if (r7 != 0) goto L_0x001e;
    L_0x001c:
        r3 = r0;
        goto L_0x0022;
    L_0x001e:
        r3 = java.lang.Integer.valueOf(r7);	 Catch:{ Exception -> 0x002a }
    L_0x0022:
        r3 = java.lang.String.valueOf(r3);	 Catch:{ Exception -> 0x002a }
        r2.set(r1, r3);	 Catch:{ Exception -> 0x002a }
        goto L_0x0070;
    L_0x002a:
        r3 = java.lang.Integer.valueOf(r7);	 Catch:{ Exception -> 0x0032 }
        r2.set(r1, r3);	 Catch:{ Exception -> 0x0032 }
        goto L_0x0070;
        r1 = new android.content.Intent;
        r2 = "android.intent.action.APPLICATION_MESSAGE_UPDATE";
        r1.<init>(r2);
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = r6.getPackageName();
        r2.append(r3);
        r3 = "/";
        r2.append(r3);
        r6 = r6.getClassName();
        r2.append(r6);
        r6 = r2.toString();
        r2 = "android.intent.extra.update_application_component_name";
        r1.putExtra(r2, r6);
        if (r7 != 0) goto L_0x005e;
    L_0x005d:
        goto L_0x0062;
    L_0x005e:
        r0 = java.lang.Integer.valueOf(r7);
    L_0x0062:
        r6 = java.lang.String.valueOf(r0);
        r0 = "android.intent.extra.update_application_message_text";
        r1.putExtra(r0, r6);
        me.leolin.shortcutbadger.util.BroadcastHelper.sendIntentExplicitly(r5, r1);	 Catch:{ ShortcutBadgeException -> 0x006f }
        goto L_0x0070;
    L_0x0070:
        r6 = android.os.Build.MANUFACTURER;
        r0 = "Xiaomi";
        r6 = r6.equalsIgnoreCase(r0);
        if (r6 == 0) goto L_0x007d;
    L_0x007a:
        r4.tryNewMiuiBadge(r5, r7);
    L_0x007d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: me.leolin.shortcutbadger.impl.XiaomiHomeBadger.executeBadge(android.content.Context, android.content.ComponentName, int):void");
    }

    @TargetApi(16)
    private void tryNewMiuiBadge(Context context, int i) throws ShortcutBadgeException {
        if (this.resolveInfo == null) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            this.resolveInfo = context.getPackageManager().resolveActivity(intent, 65536);
        }
        if (this.resolveInfo != null) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            Builder builder = new Builder(context);
            CharSequence charSequence = "";
            Notification build = builder.setContentTitle(charSequence).setContentText(charSequence).setSmallIcon(this.resolveInfo.getIconResource()).build();
            try {
                Object obj = build.getClass().getDeclaredField("extraNotification").get(build);
                obj.getClass().getDeclaredMethod("setMessageCount", new Class[]{Integer.TYPE}).invoke(obj, new Object[]{Integer.valueOf(i)});
                notificationManager.notify(0, build);
            } catch (Exception e) {
                throw new ShortcutBadgeException("not able to set badge", e);
            }
        }
    }

    public List<String> getSupportLaunchers() {
        return Arrays.asList(new String[]{"com.miui.miuilite", "com.miui.home", "com.miui.miuihome", "com.miui.miuihome2", "com.miui.mihome", "com.miui.mihome2", "com.i.miui.launcher"});
    }
}
