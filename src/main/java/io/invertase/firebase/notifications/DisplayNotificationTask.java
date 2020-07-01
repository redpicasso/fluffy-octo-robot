package io.invertase.firebase.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.NotificationCompat.Action;
import androidx.core.app.NotificationCompat.Action.Builder;
import androidx.core.app.RemoteInput;
import com.adobe.xmp.options.PropertyOptions;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DisplayNotificationTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "DisplayNotificationTask";
    private final WeakReference<Context> contextWeakReference;
    private final Bundle notification;
    private final NotificationManager notificationManager;
    private final Promise promise;
    private final WeakReference<ReactApplicationContext> reactContextWeakReference;

    DisplayNotificationTask(Context context, ReactApplicationContext reactApplicationContext, NotificationManager notificationManager, Bundle bundle, Promise promise) {
        this.contextWeakReference = new WeakReference(context);
        this.reactContextWeakReference = new WeakReference(reactApplicationContext);
        this.promise = promise;
        this.notification = bundle;
        this.notificationManager = notificationManager;
    }

    protected void onPostExecute(Void voidR) {
        this.contextWeakReference.clear();
        this.reactContextWeakReference.clear();
    }

    /* JADX WARNING: Removed duplicated region for block: B:217:0x0502  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:212:0x04f2, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:217:0x0502, code:
            r2.reject(r16, "Could not send notification", r0);
     */
    protected java.lang.Void doInBackground(java.lang.Void... r18) {
        /*
        r17 = this;
        r1 = r17;
        r0 = "badgeIconType";
        r2 = "autoCancel";
        r3 = "title";
        r4 = "subtitle";
        r5 = "sound";
        r6 = "data";
        r7 = "body";
        r8 = "notification/display_notification_error";
        r9 = "progress";
        r10 = "defaults";
        r11 = r1.contextWeakReference;
        r11 = r11.get();
        r11 = (android.content.Context) r11;
        r12 = 0;
        if (r11 != 0) goto L_0x0022;
    L_0x0021:
        return r12;
    L_0x0022:
        r13 = r1.getMainActivityClass(r11);	 Catch:{ Exception -> 0x04f4 }
        if (r13 != 0) goto L_0x0034;
    L_0x0028:
        r0 = r1.promise;	 Catch:{ Exception -> 0x04f4 }
        if (r0 == 0) goto L_0x0033;
    L_0x002c:
        r0 = r1.promise;	 Catch:{ Exception -> 0x04f4 }
        r2 = "Could not find main activity class";
        r0.reject(r8, r2);	 Catch:{ Exception -> 0x04f4 }
    L_0x0033:
        return r12;
    L_0x0034:
        r14 = r1.notification;	 Catch:{ Exception -> 0x04f4 }
        r15 = "android";
        r14 = r14.getBundle(r15);	 Catch:{ Exception -> 0x04f4 }
        r15 = r1.notification;	 Catch:{ Exception -> 0x04f4 }
        r12 = "notificationId";
        r12 = r15.getString(r12);	 Catch:{ Exception -> 0x04f4 }
        r15 = "channelId";
        r15 = r14.getString(r15);	 Catch:{ Throwable -> 0x0052 }
        r16 = r8;
        r8 = new androidx.core.app.NotificationCompat$Builder;	 Catch:{ Throwable -> 0x0054 }
        r8.<init>(r11, r15);	 Catch:{ Throwable -> 0x0054 }
        goto L_0x0059;
    L_0x0052:
        r16 = r8;
    L_0x0054:
        r8 = new androidx.core.app.NotificationCompat$Builder;	 Catch:{ Exception -> 0x04f2 }
        r8.<init>(r11);	 Catch:{ Exception -> 0x04f2 }
    L_0x0059:
        r15 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r15 = r15.containsKey(r7);	 Catch:{ Exception -> 0x04f2 }
        if (r15 == 0) goto L_0x006b;
    L_0x0061:
        r15 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r7 = r15.getString(r7);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setContentText(r7);	 Catch:{ Exception -> 0x04f2 }
    L_0x006b:
        r7 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r7 = r7.containsKey(r6);	 Catch:{ Exception -> 0x04f2 }
        if (r7 == 0) goto L_0x007d;
    L_0x0073:
        r7 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r6 = r7.getBundle(r6);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setExtras(r6);	 Catch:{ Exception -> 0x04f2 }
    L_0x007d:
        r6 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r6 = r6.containsKey(r5);	 Catch:{ Exception -> 0x04f2 }
        if (r6 == 0) goto L_0x0093;
    L_0x0085:
        r6 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r5 = r6.getString(r5);	 Catch:{ Exception -> 0x04f2 }
        r5 = io.invertase.firebase.notifications.RNFirebaseNotificationManager.getSound(r11, r5);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setSound(r5);	 Catch:{ Exception -> 0x04f2 }
    L_0x0093:
        r5 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r5 = r5.containsKey(r4);	 Catch:{ Exception -> 0x04f2 }
        if (r5 == 0) goto L_0x00a5;
    L_0x009b:
        r5 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r4 = r5.getString(r4);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setSubText(r4);	 Catch:{ Exception -> 0x04f2 }
    L_0x00a5:
        r4 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r4 = r4.containsKey(r3);	 Catch:{ Exception -> 0x04f2 }
        if (r4 == 0) goto L_0x00b7;
    L_0x00ad:
        r4 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r3 = r4.getString(r3);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setContentTitle(r3);	 Catch:{ Exception -> 0x04f2 }
    L_0x00b7:
        r3 = r14.containsKey(r2);	 Catch:{ Exception -> 0x04f2 }
        if (r3 == 0) goto L_0x00c5;
    L_0x00bd:
        r2 = r14.getBoolean(r2);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setAutoCancel(r2);	 Catch:{ Exception -> 0x04f2 }
    L_0x00c5:
        r2 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        r3 = 26;
        if (r2 == 0) goto L_0x00e1;
    L_0x00cd:
        r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x04f2 }
        if (r2 < r3) goto L_0x00e1;
    L_0x00d1:
        r4 = r14.getDouble(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r4);	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.intValue();	 Catch:{ Throwable -> 0x00e1 }
        r8 = r8.setBadgeIconType(r0);	 Catch:{ Throwable -> 0x00e1 }
    L_0x00e1:
        r0 = "bigPicture";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        r2 = "summaryText";
        r4 = "contentTitle";
        r5 = "largeIcon";
        if (r0 == 0) goto L_0x013e;
    L_0x00ef:
        r0 = "bigPicture";
        r0 = r14.getBundle(r0);	 Catch:{ Exception -> 0x04f2 }
        r6 = new androidx.core.app.NotificationCompat$BigPictureStyle;	 Catch:{ Exception -> 0x04f2 }
        r6.<init>();	 Catch:{ Exception -> 0x04f2 }
        r7 = "picture";
        r7 = r0.getString(r7);	 Catch:{ Exception -> 0x04f2 }
        r7 = r1.getBitmap(r7);	 Catch:{ Exception -> 0x04f2 }
        if (r7 == 0) goto L_0x010a;
    L_0x0106:
        r6 = r6.bigPicture(r7);	 Catch:{ Exception -> 0x04f2 }
    L_0x010a:
        r7 = r0.containsKey(r5);	 Catch:{ Exception -> 0x04f2 }
        if (r7 == 0) goto L_0x011e;
    L_0x0110:
        r7 = r0.getString(r5);	 Catch:{ Exception -> 0x04f2 }
        r7 = r1.getBitmap(r7);	 Catch:{ Exception -> 0x04f2 }
        if (r7 == 0) goto L_0x011e;
    L_0x011a:
        r6 = r6.bigLargeIcon(r7);	 Catch:{ Exception -> 0x04f2 }
    L_0x011e:
        r7 = r0.containsKey(r4);	 Catch:{ Exception -> 0x04f2 }
        if (r7 == 0) goto L_0x012c;
    L_0x0124:
        r7 = r0.getString(r4);	 Catch:{ Exception -> 0x04f2 }
        r6 = r6.setBigContentTitle(r7);	 Catch:{ Exception -> 0x04f2 }
    L_0x012c:
        r7 = r0.containsKey(r2);	 Catch:{ Exception -> 0x04f2 }
        if (r7 == 0) goto L_0x013a;
    L_0x0132:
        r0 = r0.getString(r2);	 Catch:{ Exception -> 0x04f2 }
        r6 = r6.setSummaryText(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x013a:
        r8 = r8.setStyle(r6);	 Catch:{ Exception -> 0x04f2 }
    L_0x013e:
        r0 = "bigText";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x017a;
    L_0x0146:
        r0 = "bigText";
        r0 = r14.getBundle(r0);	 Catch:{ Exception -> 0x04f2 }
        r6 = new androidx.core.app.NotificationCompat$BigTextStyle;	 Catch:{ Exception -> 0x04f2 }
        r6.<init>();	 Catch:{ Exception -> 0x04f2 }
        r7 = "text";
        r7 = r0.getString(r7);	 Catch:{ Exception -> 0x04f2 }
        r6.bigText(r7);	 Catch:{ Exception -> 0x04f2 }
        r7 = r0.containsKey(r4);	 Catch:{ Exception -> 0x04f2 }
        if (r7 == 0) goto L_0x0168;
    L_0x0160:
        r4 = r0.getString(r4);	 Catch:{ Exception -> 0x04f2 }
        r6 = r6.setBigContentTitle(r4);	 Catch:{ Exception -> 0x04f2 }
    L_0x0168:
        r4 = r0.containsKey(r2);	 Catch:{ Exception -> 0x04f2 }
        if (r4 == 0) goto L_0x0176;
    L_0x016e:
        r0 = r0.getString(r2);	 Catch:{ Exception -> 0x04f2 }
        r6 = r6.setSummaryText(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x0176:
        r8 = r8.setStyle(r6);	 Catch:{ Exception -> 0x04f2 }
    L_0x017a:
        r0 = "category";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x018c;
    L_0x0182:
        r0 = "category";
        r0 = r14.getString(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setCategory(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x018c:
        r0 = "color";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x01a2;
    L_0x0194:
        r0 = "color";
        r0 = r14.getString(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = android.graphics.Color.parseColor(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setColor(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x01a2:
        r0 = "colorized";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x01b8;
    L_0x01aa:
        r0 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x04f2 }
        if (r0 < r3) goto L_0x01b8;
    L_0x01ae:
        r0 = "colorized";
        r0 = r14.getBoolean(r0);	 Catch:{ Throwable -> 0x01b8 }
        r8 = r8.setColorized(r0);	 Catch:{ Throwable -> 0x01b8 }
    L_0x01b8:
        r0 = "contentInfo";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x01ca;
    L_0x01c0:
        r0 = "contentInfo";
        r0 = r14.getString(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setContentInfo(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x01ca:
        r0 = r14.containsKey(r10);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x01fe;
    L_0x01d0:
        r6 = r14.getDouble(r10);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r6);	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.intValue();	 Catch:{ Exception -> 0x04f2 }
        if (r0 != 0) goto L_0x01fa;
    L_0x01de:
        r2 = r14.getIntegerArrayList(r10);	 Catch:{ Exception -> 0x04f2 }
        if (r2 == 0) goto L_0x01fa;
    L_0x01e4:
        r2 = r2.iterator();	 Catch:{ Exception -> 0x04f2 }
    L_0x01e8:
        r4 = r2.hasNext();	 Catch:{ Exception -> 0x04f2 }
        if (r4 == 0) goto L_0x01fa;
    L_0x01ee:
        r4 = r2.next();	 Catch:{ Exception -> 0x04f2 }
        r4 = (java.lang.Integer) r4;	 Catch:{ Exception -> 0x04f2 }
        r4 = r4.intValue();	 Catch:{ Exception -> 0x04f2 }
        r0 = r0 | r4;
        goto L_0x01e8;
    L_0x01fa:
        r8 = r8.setDefaults(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x01fe:
        r0 = "group";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0210;
    L_0x0206:
        r0 = "group";
        r0 = r14.getString(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setGroup(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x0210:
        r0 = "groupAlertBehaviour";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x022e;
    L_0x0218:
        r0 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x04f2 }
        if (r0 < r3) goto L_0x022e;
    L_0x021c:
        r0 = "groupAlertBehaviour";
        r6 = r14.getDouble(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r6);	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.intValue();	 Catch:{ Throwable -> 0x022e }
        r8 = r8.setGroupAlertBehavior(r0);	 Catch:{ Throwable -> 0x022e }
    L_0x022e:
        r0 = "groupSummary";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0240;
    L_0x0236:
        r0 = "groupSummary";
        r0 = r14.getBoolean(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setGroupSummary(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x0240:
        r0 = r14.containsKey(r5);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0254;
    L_0x0246:
        r0 = r14.getString(r5);	 Catch:{ Exception -> 0x04f2 }
        r0 = r1.getBitmap(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0254;
    L_0x0250:
        r8 = r8.setLargeIcon(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x0254:
        r0 = "lights";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0290;
    L_0x025c:
        r0 = "lights";
        r0 = r14.getBundle(r0);	 Catch:{ Exception -> 0x04f2 }
        r2 = "argb";
        r4 = r0.getDouble(r2);	 Catch:{ Exception -> 0x04f2 }
        r2 = java.lang.Double.valueOf(r4);	 Catch:{ Exception -> 0x04f2 }
        r4 = "onMs";
        r4 = r0.getDouble(r4);	 Catch:{ Exception -> 0x04f2 }
        r4 = java.lang.Double.valueOf(r4);	 Catch:{ Exception -> 0x04f2 }
        r5 = "offMs";
        r5 = r0.getDouble(r5);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r5);	 Catch:{ Exception -> 0x04f2 }
        r2 = r2.intValue();	 Catch:{ Exception -> 0x04f2 }
        r4 = r4.intValue();	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.intValue();	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setLights(r2, r4, r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x0290:
        r0 = "localOnly";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x02a2;
    L_0x0298:
        r0 = "localOnly";
        r0 = r14.getBoolean(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setLocalOnly(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x02a2:
        r0 = "number";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x02bc;
    L_0x02aa:
        r0 = "number";
        r4 = r14.getDouble(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r4);	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.intValue();	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setNumber(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x02bc:
        r0 = "ongoing";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x02ce;
    L_0x02c4:
        r0 = "ongoing";
        r0 = r14.getBoolean(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setOngoing(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x02ce:
        r0 = "onlyAlertOnce";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x02e0;
    L_0x02d6:
        r0 = "onlyAlertOnce";
        r0 = r14.getBoolean(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setOnlyAlertOnce(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x02e0:
        r0 = "people";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0305;
    L_0x02e8:
        r0 = "people";
        r0 = r14.getStringArrayList(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0305;
    L_0x02f0:
        r0 = r0.iterator();	 Catch:{ Exception -> 0x04f2 }
    L_0x02f4:
        r2 = r0.hasNext();	 Catch:{ Exception -> 0x04f2 }
        if (r2 == 0) goto L_0x0305;
    L_0x02fa:
        r2 = r0.next();	 Catch:{ Exception -> 0x04f2 }
        r2 = (java.lang.String) r2;	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.addPerson(r2);	 Catch:{ Exception -> 0x04f2 }
        goto L_0x02f4;
    L_0x0305:
        r0 = "priority";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x031f;
    L_0x030d:
        r0 = "priority";
        r4 = r14.getDouble(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r4);	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.intValue();	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setPriority(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x031f:
        r0 = r14.containsKey(r9);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x034d;
    L_0x0325:
        r0 = r14.getBundle(r9);	 Catch:{ Exception -> 0x04f2 }
        r2 = "max";
        r4 = r0.getDouble(r2);	 Catch:{ Exception -> 0x04f2 }
        r2 = java.lang.Double.valueOf(r4);	 Catch:{ Exception -> 0x04f2 }
        r4 = r0.getDouble(r9);	 Catch:{ Exception -> 0x04f2 }
        r4 = java.lang.Double.valueOf(r4);	 Catch:{ Exception -> 0x04f2 }
        r2 = r2.intValue();	 Catch:{ Exception -> 0x04f2 }
        r4 = r4.intValue();	 Catch:{ Exception -> 0x04f2 }
        r5 = "indeterminate";
        r0 = r0.getBoolean(r5);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setProgress(r2, r4, r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x034d:
        r0 = "remoteInputHistory";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x035f;
    L_0x0355:
        r0 = "remoteInputHistory";
        r0 = r14.getStringArray(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setRemoteInputHistory(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x035f:
        r0 = "shortcutId";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0375;
    L_0x0367:
        r0 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x04f2 }
        if (r0 < r3) goto L_0x0375;
    L_0x036b:
        r0 = "shortcutId";
        r0 = r14.getString(r0);	 Catch:{ Throwable -> 0x0375 }
        r8 = r8.setShortcutId(r0);	 Catch:{ Throwable -> 0x0375 }
    L_0x0375:
        r0 = "showWhen";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0387;
    L_0x037d:
        r0 = "showWhen";
        r0 = r14.getBoolean(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setShowWhen(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x0387:
        r0 = "smallIcon";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x03c0;
    L_0x038f:
        r0 = "smallIcon";
        r0 = r14.getBundle(r0);	 Catch:{ Exception -> 0x04f2 }
        r2 = "icon";
        r2 = r0.getString(r2);	 Catch:{ Exception -> 0x04f2 }
        r2 = r1.getIcon(r2);	 Catch:{ Exception -> 0x04f2 }
        if (r2 == 0) goto L_0x03c0;
    L_0x03a1:
        r3 = "level";
        r3 = r0.containsKey(r3);	 Catch:{ Exception -> 0x04f2 }
        if (r3 == 0) goto L_0x03bc;
    L_0x03a9:
        r3 = "level";
        r3 = r0.getDouble(r3);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r3);	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.intValue();	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setSmallIcon(r2, r0);	 Catch:{ Exception -> 0x04f2 }
        goto L_0x03c0;
    L_0x03bc:
        r8 = r8.setSmallIcon(r2);	 Catch:{ Exception -> 0x04f2 }
    L_0x03c0:
        r0 = "sortKey";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x03d2;
    L_0x03c8:
        r0 = "sortKey";
        r0 = r14.getString(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setSortKey(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x03d2:
        r0 = "ticker";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x03e4;
    L_0x03da:
        r0 = "ticker";
        r0 = r14.getString(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setTicker(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x03e4:
        r0 = "timeoutAfter";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x03fe;
    L_0x03ec:
        r0 = "timeoutAfter";
        r2 = r14.getDouble(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r2);	 Catch:{ Exception -> 0x04f2 }
        r2 = r0.longValue();	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setTimeoutAfter(r2);	 Catch:{ Exception -> 0x04f2 }
    L_0x03fe:
        r0 = "usesChronometer";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0410;
    L_0x0406:
        r0 = "usesChronometer";
        r0 = r14.getBoolean(r0);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setUsesChronometer(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x0410:
        r0 = "vibrate";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0440;
    L_0x0418:
        r0 = "vibrate";
        r0 = r14.getIntegerArrayList(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0440;
    L_0x0420:
        r2 = r0.size();	 Catch:{ Exception -> 0x04f2 }
        r2 = new long[r2];	 Catch:{ Exception -> 0x04f2 }
        r3 = 0;
    L_0x0427:
        r4 = r0.size();	 Catch:{ Exception -> 0x04f2 }
        if (r3 >= r4) goto L_0x043c;
    L_0x042d:
        r4 = r0.get(r3);	 Catch:{ Exception -> 0x04f2 }
        r4 = (java.lang.Integer) r4;	 Catch:{ Exception -> 0x04f2 }
        r4 = r4.longValue();	 Catch:{ Exception -> 0x04f2 }
        r2[r3] = r4;	 Catch:{ Exception -> 0x04f2 }
        r3 = r3 + 1;
        goto L_0x0427;
    L_0x043c:
        r8 = r8.setVibrate(r2);	 Catch:{ Exception -> 0x04f2 }
    L_0x0440:
        r0 = "visibility";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x045a;
    L_0x0448:
        r0 = "visibility";
        r2 = r14.getDouble(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r2);	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.intValue();	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setVisibility(r0);	 Catch:{ Exception -> 0x04f2 }
    L_0x045a:
        r0 = "when";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0474;
    L_0x0462:
        r0 = "when";
        r2 = r14.getDouble(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = java.lang.Double.valueOf(r2);	 Catch:{ Exception -> 0x04f2 }
        r2 = r0.longValue();	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.setWhen(r2);	 Catch:{ Exception -> 0x04f2 }
    L_0x0474:
        r0 = "actions";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x049f;
    L_0x047c:
        r0 = "actions";
        r0 = r14.getSerializable(r0);	 Catch:{ Exception -> 0x04f2 }
        r0 = (java.util.List) r0;	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.iterator();	 Catch:{ Exception -> 0x04f2 }
    L_0x0488:
        r2 = r0.hasNext();	 Catch:{ Exception -> 0x04f2 }
        if (r2 == 0) goto L_0x049f;
    L_0x048e:
        r2 = r0.next();	 Catch:{ Exception -> 0x04f2 }
        r2 = (android.os.Bundle) r2;	 Catch:{ Exception -> 0x04f2 }
        r3 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r2 = r1.createAction(r11, r2, r13, r3);	 Catch:{ Exception -> 0x04f2 }
        r8 = r8.addAction(r2);	 Catch:{ Exception -> 0x04f2 }
        goto L_0x0488;
    L_0x049f:
        r0 = "tag";
        r0 = r14.containsKey(r0);	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x04ae;
    L_0x04a7:
        r0 = "tag";
        r0 = r14.getString(r0);	 Catch:{ Exception -> 0x04f2 }
        goto L_0x04af;
    L_0x04ae:
        r0 = 0;
    L_0x04af:
        r2 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r3 = "clickAction";
        r3 = r14.getString(r3);	 Catch:{ Exception -> 0x04f2 }
        r2 = r1.createIntent(r11, r13, r2, r3);	 Catch:{ Exception -> 0x04f2 }
        r2 = r8.setContentIntent(r2);	 Catch:{ Exception -> 0x04f2 }
        r2 = r2.build();	 Catch:{ Exception -> 0x04f2 }
        r3 = r1.notificationManager;	 Catch:{ Exception -> 0x04f2 }
        r4 = r12.hashCode();	 Catch:{ Exception -> 0x04f2 }
        r3.notify(r0, r4, r2);	 Catch:{ Exception -> 0x04f2 }
        r0 = r1.reactContextWeakReference;	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.get();	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x04e7;
    L_0x04d4:
        r0 = r1.reactContextWeakReference;	 Catch:{ Exception -> 0x04f2 }
        r0 = r0.get();	 Catch:{ Exception -> 0x04f2 }
        r0 = (com.facebook.react.bridge.ReactContext) r0;	 Catch:{ Exception -> 0x04f2 }
        r2 = "notifications_notification_displayed";
        r3 = r1.notification;	 Catch:{ Exception -> 0x04f2 }
        r3 = com.facebook.react.bridge.Arguments.fromBundle(r3);	 Catch:{ Exception -> 0x04f2 }
        io.invertase.firebase.Utils.sendEvent(r0, r2, r3);	 Catch:{ Exception -> 0x04f2 }
    L_0x04e7:
        r0 = r1.promise;	 Catch:{ Exception -> 0x04f2 }
        if (r0 == 0) goto L_0x0509;
    L_0x04eb:
        r0 = r1.promise;	 Catch:{ Exception -> 0x04f2 }
        r2 = 0;
        r0.resolve(r2);	 Catch:{ Exception -> 0x04f2 }
        goto L_0x0509;
    L_0x04f2:
        r0 = move-exception;
        goto L_0x04f7;
    L_0x04f4:
        r0 = move-exception;
        r16 = r8;
    L_0x04f7:
        r2 = "DisplayNotificationTask";
        r3 = "Failed to send notification";
        android.util.Log.e(r2, r3, r0);
        r2 = r1.promise;
        if (r2 == 0) goto L_0x0509;
    L_0x0502:
        r3 = "Could not send notification";
        r4 = r16;
        r2.reject(r4, r3, r0);
    L_0x0509:
        r2 = 0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.notifications.DisplayNotificationTask.doInBackground(java.lang.Void[]):java.lang.Void");
    }

    private Action createAction(Context context, Bundle bundle, Class cls, Bundle bundle2) {
        PendingIntent createIntent;
        String string = bundle.getString("action");
        String str = "showUserInterface";
        Object obj = (bundle.containsKey(str) && bundle.getBoolean(str)) ? 1 : null;
        if (obj != null) {
            createIntent = createIntent(context, cls, bundle2, string);
        } else {
            createIntent = createBroadcastIntent(context, bundle2, string);
        }
        Builder builder = new Builder(getIcon(bundle.getString("icon")), bundle.getString("title"), createIntent);
        String str2 = "allowGeneratedReplies";
        if (bundle.containsKey(str2)) {
            builder = builder.setAllowGeneratedReplies(bundle.getBoolean(str2));
        }
        str2 = "remoteInputs";
        if (bundle.containsKey(str2)) {
            for (Bundle bundle3 : (List) bundle3.getSerializable(str2)) {
                builder = builder.addRemoteInput(createRemoteInput(bundle3));
            }
        }
        return builder.build();
    }

    private PendingIntent createIntent(Context context, Class cls, Bundle bundle, String str) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(PropertyOptions.DELETE_EXISTING);
        intent.putExtras(bundle);
        if (str != null) {
            intent.setAction(str);
        }
        return PendingIntent.getActivity(context, bundle.getString("notificationId").hashCode(), intent, 134217728);
    }

    private PendingIntent createBroadcastIntent(Context context, Bundle bundle, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bundle.getString("notificationId"));
        stringBuilder.append(str);
        String stringBuilder2 = stringBuilder.toString();
        Intent intent = new Intent(context, RNFirebaseBackgroundNotificationActionReceiver.class);
        intent.putExtra("action", str);
        intent.addFlags(PropertyOptions.DELETE_EXISTING);
        intent.putExtra("notification", bundle);
        intent.setAction("io.invertase.firebase.notifications.BackgroundAction");
        return PendingIntent.getBroadcast(context, stringBuilder2.hashCode(), intent, 134217728);
    }

    private RemoteInput createRemoteInput(Bundle bundle) {
        RemoteInput.Builder builder = new RemoteInput.Builder(bundle.getString("resultKey"));
        String str = "allowedDataTypes";
        if (bundle.containsKey(str)) {
            for (Bundle bundle2 : (List) bundle.getSerializable(str)) {
                builder.setAllowDataType(bundle2.getString("mimeType"), bundle2.getBoolean("allow"));
            }
        }
        str = "allowFreeFormInput";
        if (bundle.containsKey(str)) {
            builder.setAllowFreeFormInput(bundle.getBoolean(str));
        }
        str = "choices";
        if (bundle.containsKey(str)) {
            List stringArrayList = bundle.getStringArrayList(str);
            builder.setChoices((CharSequence[]) stringArrayList.toArray(new String[stringArrayList.size()]));
        }
        str = "label";
        if (bundle.containsKey(str)) {
            builder.setLabel(bundle.getString(str));
        }
        return builder.build();
    }

    private Bitmap getBitmap(String str) {
        if (str.startsWith("http://") || str.startsWith("https://")) {
            return getBitmapFromUrl(str);
        }
        CharSequence charSequence = "file://";
        if (str.startsWith(charSequence)) {
            return BitmapFactory.decodeFile(str.replace(charSequence, ""));
        }
        return BitmapFactory.decodeResource(((Context) this.contextWeakReference.get()).getResources(), getIcon(str));
    }

    private Bitmap getBitmapFromUrl(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            str = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            return str;
        } catch (Throwable e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to get bitmap for url: ");
            stringBuilder.append(str);
            Log.e(TAG, stringBuilder.toString(), e);
            return null;
        }
    }

    private int getIcon(String str) {
        int resourceId = RNFirebaseNotificationManager.getResourceId((Context) this.contextWeakReference.get(), "mipmap", str);
        return resourceId == 0 ? RNFirebaseNotificationManager.getResourceId((Context) this.contextWeakReference.get(), "drawable", str) : resourceId;
    }

    private Class getMainActivityClass(Context context) {
        try {
            return Class.forName(context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName());
        } catch (Throwable e) {
            Log.e(TAG, "Failed to get main activity class", e);
            return null;
        }
    }
}
