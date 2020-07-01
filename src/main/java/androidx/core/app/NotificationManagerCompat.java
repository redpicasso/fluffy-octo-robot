package androidx.core.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.app.INotificationSideChannel.Stub;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class NotificationManagerCompat {
    public static final String ACTION_BIND_SIDE_CHANNEL = "android.support.BIND_NOTIFICATION_SIDE_CHANNEL";
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    public static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MAX = 5;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;
    static final int MAX_SIDE_CHANNEL_SDK_VERSION = 19;
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final int SIDE_CHANNEL_RETRY_BASE_INTERVAL_MS = 1000;
    private static final int SIDE_CHANNEL_RETRY_MAX_COUNT = 6;
    private static final String TAG = "NotifManCompat";
    @GuardedBy("sEnabledNotificationListenersLock")
    private static Set<String> sEnabledNotificationListenerPackages = new HashSet();
    @GuardedBy("sEnabledNotificationListenersLock")
    private static String sEnabledNotificationListeners;
    private static final Object sEnabledNotificationListenersLock = new Object();
    private static final Object sLock = new Object();
    @GuardedBy("sLock")
    private static SideChannelManager sSideChannelManager;
    private final Context mContext;
    private final NotificationManager mNotificationManager = ((NotificationManager) this.mContext.getSystemService("notification"));

    private static class ServiceConnectedEvent {
        final ComponentName componentName;
        final IBinder iBinder;

        ServiceConnectedEvent(ComponentName componentName, IBinder iBinder) {
            this.componentName = componentName;
            this.iBinder = iBinder;
        }
    }

    private static class SideChannelManager implements Callback, ServiceConnection {
        private static final int MSG_QUEUE_TASK = 0;
        private static final int MSG_RETRY_LISTENER_QUEUE = 3;
        private static final int MSG_SERVICE_CONNECTED = 1;
        private static final int MSG_SERVICE_DISCONNECTED = 2;
        private Set<String> mCachedEnabledPackages = new HashSet();
        private final Context mContext;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Map<ComponentName, ListenerRecord> mRecordMap = new HashMap();

        private static class ListenerRecord {
            boolean bound = false;
            final ComponentName componentName;
            int retryCount = 0;
            INotificationSideChannel service;
            ArrayDeque<Task> taskQueue = new ArrayDeque();

            ListenerRecord(ComponentName componentName) {
                this.componentName = componentName;
            }
        }

        SideChannelManager(Context context) {
            this.mContext = context;
            this.mHandlerThread = new HandlerThread("NotificationManagerCompat");
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper(), this);
        }

        public void queueTask(Task task) {
            this.mHandler.obtainMessage(0, task).sendToTarget();
        }

        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                handleQueueTask((Task) message.obj);
                return true;
            } else if (i == 1) {
                ServiceConnectedEvent serviceConnectedEvent = (ServiceConnectedEvent) message.obj;
                handleServiceConnected(serviceConnectedEvent.componentName, serviceConnectedEvent.iBinder);
                return true;
            } else if (i == 2) {
                handleServiceDisconnected((ComponentName) message.obj);
                return true;
            } else if (i != 3) {
                return false;
            } else {
                handleRetryListenerQueue((ComponentName) message.obj);
                return true;
            }
        }

        private void handleQueueTask(Task task) {
            updateListenerMap();
            for (ListenerRecord listenerRecord : this.mRecordMap.values()) {
                listenerRecord.taskQueue.add(task);
                processListenerQueue(listenerRecord);
            }
        }

        private void handleServiceConnected(ComponentName componentName, IBinder iBinder) {
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName);
            if (listenerRecord != null) {
                listenerRecord.service = Stub.asInterface(iBinder);
                listenerRecord.retryCount = 0;
                processListenerQueue(listenerRecord);
            }
        }

        private void handleServiceDisconnected(ComponentName componentName) {
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName);
            if (listenerRecord != null) {
                ensureServiceUnbound(listenerRecord);
            }
        }

        private void handleRetryListenerQueue(ComponentName componentName) {
            ListenerRecord listenerRecord = (ListenerRecord) this.mRecordMap.get(componentName);
            if (listenerRecord != null) {
                processListenerQueue(listenerRecord);
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            String str = NotificationManagerCompat.TAG;
            if (Log.isLoggable(str, 3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Connected to service ");
                stringBuilder.append(componentName);
                Log.d(str, stringBuilder.toString());
            }
            this.mHandler.obtainMessage(1, new ServiceConnectedEvent(componentName, iBinder)).sendToTarget();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            String str = NotificationManagerCompat.TAG;
            if (Log.isLoggable(str, 3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Disconnected from service ");
                stringBuilder.append(componentName);
                Log.d(str, stringBuilder.toString());
            }
            this.mHandler.obtainMessage(2, componentName).sendToTarget();
        }

        private void updateListenerMap() {
            Set enabledListenerPackages = NotificationManagerCompat.getEnabledListenerPackages(this.mContext);
            if (!enabledListenerPackages.equals(this.mCachedEnabledPackages)) {
                String str;
                StringBuilder stringBuilder;
                this.mCachedEnabledPackages = enabledListenerPackages;
                List queryIntentServices = this.mContext.getPackageManager().queryIntentServices(new Intent().setAction(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL), 0);
                Set<ComponentName> hashSet = new HashSet();
                Iterator it = queryIntentServices.iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    str = NotificationManagerCompat.TAG;
                    if (!hasNext) {
                        break;
                    }
                    ResolveInfo resolveInfo = (ResolveInfo) it.next();
                    if (enabledListenerPackages.contains(resolveInfo.serviceInfo.packageName)) {
                        ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
                        if (resolveInfo.serviceInfo.permission != null) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Permission present on component ");
                            stringBuilder.append(componentName);
                            stringBuilder.append(", not adding listener record.");
                            Log.w(str, stringBuilder.toString());
                        } else {
                            hashSet.add(componentName);
                        }
                    }
                }
                for (ComponentName componentName2 : hashSet) {
                    if (!this.mRecordMap.containsKey(componentName2)) {
                        if (Log.isLoggable(str, 3)) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Adding listener record for ");
                            stringBuilder.append(componentName2);
                            Log.d(str, stringBuilder.toString());
                        }
                        this.mRecordMap.put(componentName2, new ListenerRecord(componentName2));
                    }
                }
                Iterator it2 = this.mRecordMap.entrySet().iterator();
                while (it2.hasNext()) {
                    Entry entry = (Entry) it2.next();
                    if (!hashSet.contains(entry.getKey())) {
                        if (Log.isLoggable(str, 3)) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Removing listener record for ");
                            stringBuilder2.append(entry.getKey());
                            Log.d(str, stringBuilder2.toString());
                        }
                        ensureServiceUnbound((ListenerRecord) entry.getValue());
                        it2.remove();
                    }
                }
            }
        }

        private boolean ensureServiceBound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                return true;
            }
            listenerRecord.bound = this.mContext.bindService(new Intent(NotificationManagerCompat.ACTION_BIND_SIDE_CHANNEL).setComponent(listenerRecord.componentName), this, 33);
            if (listenerRecord.bound) {
                listenerRecord.retryCount = 0;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to bind to listener ");
                stringBuilder.append(listenerRecord.componentName);
                Log.w(NotificationManagerCompat.TAG, stringBuilder.toString());
                this.mContext.unbindService(this);
            }
            return listenerRecord.bound;
        }

        private void ensureServiceUnbound(ListenerRecord listenerRecord) {
            if (listenerRecord.bound) {
                this.mContext.unbindService(this);
                listenerRecord.bound = false;
            }
            listenerRecord.service = null;
        }

        private void scheduleListenerRetry(ListenerRecord listenerRecord) {
            if (!this.mHandler.hasMessages(3, listenerRecord.componentName)) {
                listenerRecord.retryCount++;
                int i = listenerRecord.retryCount;
                String str = NotificationManagerCompat.TAG;
                if (i > 6) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Giving up on delivering ");
                    stringBuilder.append(listenerRecord.taskQueue.size());
                    stringBuilder.append(" tasks to ");
                    stringBuilder.append(listenerRecord.componentName);
                    stringBuilder.append(" after ");
                    stringBuilder.append(listenerRecord.retryCount);
                    stringBuilder.append(" retries");
                    Log.w(str, stringBuilder.toString());
                    listenerRecord.taskQueue.clear();
                    return;
                }
                i = (1 << (listenerRecord.retryCount - 1)) * 1000;
                if (Log.isLoggable(str, 3)) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Scheduling retry for ");
                    stringBuilder2.append(i);
                    stringBuilder2.append(" ms");
                    Log.d(str, stringBuilder2.toString());
                }
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3, listenerRecord.componentName), (long) i);
            }
        }

        /* JADX WARNING: Missing block: B:22:0x0096, code:
            if (android.util.Log.isLoggable(r1, 3) == false) goto L_0x00ae;
     */
        /* JADX WARNING: Missing block: B:23:0x0098, code:
            r0 = new java.lang.StringBuilder();
            r0.append("Remote service has died: ");
            r0.append(r6.componentName);
            android.util.Log.d(r1, r0.toString());
     */
        private void processListenerQueue(androidx.core.app.NotificationManagerCompat.SideChannelManager.ListenerRecord r6) {
            /*
            r5 = this;
            r0 = 3;
            r1 = "NotifManCompat";
            r2 = android.util.Log.isLoggable(r1, r0);
            if (r2 == 0) goto L_0x0032;
        L_0x0009:
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "Processing component ";
            r2.append(r3);
            r3 = r6.componentName;
            r2.append(r3);
            r3 = ", ";
            r2.append(r3);
            r3 = r6.taskQueue;
            r3 = r3.size();
            r2.append(r3);
            r3 = " queued tasks";
            r2.append(r3);
            r2 = r2.toString();
            android.util.Log.d(r1, r2);
        L_0x0032:
            r2 = r6.taskQueue;
            r2 = r2.isEmpty();
            if (r2 == 0) goto L_0x003b;
        L_0x003a:
            return;
        L_0x003b:
            r2 = r5.ensureServiceBound(r6);
            if (r2 == 0) goto L_0x00ba;
        L_0x0041:
            r2 = r6.service;
            if (r2 != 0) goto L_0x0047;
        L_0x0045:
            goto L_0x00ba;
        L_0x0047:
            r2 = r6.taskQueue;
            r2 = r2.peek();
            r2 = (androidx.core.app.NotificationManagerCompat.Task) r2;
            if (r2 != 0) goto L_0x0052;
        L_0x0051:
            goto L_0x00ae;
        L_0x0052:
            r3 = android.util.Log.isLoggable(r1, r0);	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            if (r3 == 0) goto L_0x006c;
        L_0x0058:
            r3 = new java.lang.StringBuilder;	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            r3.<init>();	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            r4 = "Sending task ";
            r3.append(r4);	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            r3.append(r2);	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            r3 = r3.toString();	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            android.util.Log.d(r1, r3);	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
        L_0x006c:
            r3 = r6.service;	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            r2.send(r3);	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            r2 = r6.taskQueue;	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            r2.remove();	 Catch:{ DeadObjectException -> 0x0079, RemoteException -> 0x0077 }
            goto L_0x0047;
        L_0x0077:
            r0 = move-exception;
            goto L_0x007b;
            goto L_0x0092;
        L_0x007b:
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "RemoteException communicating with ";
            r2.append(r3);
            r3 = r6.componentName;
            r2.append(r3);
            r2 = r2.toString();
            android.util.Log.w(r1, r2, r0);
            goto L_0x00ae;
        L_0x0092:
            r0 = android.util.Log.isLoggable(r1, r0);
            if (r0 == 0) goto L_0x00ae;
        L_0x0098:
            r0 = new java.lang.StringBuilder;
            r0.<init>();
            r2 = "Remote service has died: ";
            r0.append(r2);
            r2 = r6.componentName;
            r0.append(r2);
            r0 = r0.toString();
            android.util.Log.d(r1, r0);
        L_0x00ae:
            r0 = r6.taskQueue;
            r0 = r0.isEmpty();
            if (r0 != 0) goto L_0x00b9;
        L_0x00b6:
            r5.scheduleListenerRetry(r6);
        L_0x00b9:
            return;
        L_0x00ba:
            r5.scheduleListenerRetry(r6);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationManagerCompat.SideChannelManager.processListenerQueue(androidx.core.app.NotificationManagerCompat$SideChannelManager$ListenerRecord):void");
        }
    }

    private interface Task {
        void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException;
    }

    private static class CancelTask implements Task {
        final boolean all;
        final int id;
        final String packageName;
        final String tag;

        CancelTask(String str) {
            this.packageName = str;
            this.id = 0;
            this.tag = null;
            this.all = true;
        }

        CancelTask(String str, int i, String str2) {
            this.packageName = str;
            this.id = i;
            this.tag = str2;
            this.all = false;
        }

        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            if (this.all) {
                iNotificationSideChannel.cancelAll(this.packageName);
            } else {
                iNotificationSideChannel.cancel(this.packageName, this.id, this.tag);
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("CancelTask[");
            stringBuilder.append("packageName:");
            stringBuilder.append(this.packageName);
            stringBuilder.append(", id:");
            stringBuilder.append(this.id);
            stringBuilder.append(", tag:");
            stringBuilder.append(this.tag);
            stringBuilder.append(", all:");
            stringBuilder.append(this.all);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    private static class NotifyTask implements Task {
        final int id;
        final Notification notif;
        final String packageName;
        final String tag;

        NotifyTask(String str, int i, String str2, Notification notification) {
            this.packageName = str;
            this.id = i;
            this.tag = str2;
            this.notif = notification;
        }

        public void send(INotificationSideChannel iNotificationSideChannel) throws RemoteException {
            iNotificationSideChannel.notify(this.packageName, this.id, this.tag, this.notif);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("NotifyTask[");
            stringBuilder.append("packageName:");
            stringBuilder.append(this.packageName);
            stringBuilder.append(", id:");
            stringBuilder.append(this.id);
            stringBuilder.append(", tag:");
            stringBuilder.append(this.tag);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    @NonNull
    public static NotificationManagerCompat from(@NonNull Context context) {
        return new NotificationManagerCompat(context);
    }

    private NotificationManagerCompat(Context context) {
        this.mContext = context;
    }

    public void cancel(int i) {
        cancel(null, i);
    }

    public void cancel(@Nullable String str, int i) {
        this.mNotificationManager.cancel(str, i);
        if (VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(new CancelTask(this.mContext.getPackageName(), i, str));
        }
    }

    public void cancelAll() {
        this.mNotificationManager.cancelAll();
        if (VERSION.SDK_INT <= 19) {
            pushSideChannelQueue(new CancelTask(this.mContext.getPackageName()));
        }
    }

    public void notify(int i, @NonNull Notification notification) {
        notify(null, i, notification);
    }

    public void notify(@Nullable String str, int i, @NonNull Notification notification) {
        if (useSideChannelForNotification(notification)) {
            pushSideChannelQueue(new NotifyTask(this.mContext.getPackageName(), i, str, notification));
            this.mNotificationManager.cancel(str, i);
            return;
        }
        this.mNotificationManager.notify(str, i, notification);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0081 A:{RETURN, PHI: r2 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:7:0x0030} */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0081 A:{RETURN, PHI: r2 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:7:0x0030} */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0081 A:{RETURN, PHI: r2 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:7:0x0030} */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0081 A:{RETURN, PHI: r2 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:7:0x0030} */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0081 A:{RETURN, PHI: r2 , ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:7:0x0030} */
    /* JADX WARNING: Missing block: B:11:0x0081, code:
            return r2;
     */
    public boolean areNotificationsEnabled() {
        /*
        r11 = this;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 24;
        if (r0 < r1) goto L_0x000d;
    L_0x0006:
        r0 = r11.mNotificationManager;
        r0 = r0.areNotificationsEnabled();
        return r0;
    L_0x000d:
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 19;
        r2 = 1;
        if (r0 < r1) goto L_0x0081;
    L_0x0014:
        r0 = r11.mContext;
        r1 = "appops";
        r0 = r0.getSystemService(r1);
        r0 = (android.app.AppOpsManager) r0;
        r1 = r11.mContext;
        r1 = r1.getApplicationInfo();
        r3 = r11.mContext;
        r3 = r3.getApplicationContext();
        r3 = r3.getPackageName();
        r1 = r1.uid;
        r4 = android.app.AppOpsManager.class;
        r4 = r4.getName();	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r4 = java.lang.Class.forName(r4);	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r5 = "checkOpNoThrow";
        r6 = 3;
        r7 = new java.lang.Class[r6];	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r8 = java.lang.Integer.TYPE;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r9 = 0;
        r7[r9] = r8;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r8 = java.lang.Integer.TYPE;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r7[r2] = r8;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r8 = java.lang.String.class;
        r10 = 2;
        r7[r10] = r8;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r5 = r4.getMethod(r5, r7);	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r7 = "OP_POST_NOTIFICATION";
        r4 = r4.getDeclaredField(r7);	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r7 = java.lang.Integer.class;
        r4 = r4.get(r7);	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r4 = (java.lang.Integer) r4;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r4 = r4.intValue();	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r6 = new java.lang.Object[r6];	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r6[r9] = r4;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r6[r2] = r1;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r6[r10] = r3;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r0 = r5.invoke(r0, r6);	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r0 = (java.lang.Integer) r0;	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        r0 = r0.intValue();	 Catch:{ ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081, ClassNotFoundException -> 0x0081 }
        if (r0 != 0) goto L_0x0080;
    L_0x007f:
        goto L_0x0081;
    L_0x0080:
        r2 = 0;
    L_0x0081:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationManagerCompat.areNotificationsEnabled():boolean");
    }

    public int getImportance() {
        return VERSION.SDK_INT >= 24 ? this.mNotificationManager.getImportance() : IMPORTANCE_UNSPECIFIED;
    }

    public void createNotificationChannel(@NonNull NotificationChannel notificationChannel) {
        if (VERSION.SDK_INT >= 26) {
            this.mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void createNotificationChannelGroup(@NonNull NotificationChannelGroup notificationChannelGroup) {
        if (VERSION.SDK_INT >= 26) {
            this.mNotificationManager.createNotificationChannelGroup(notificationChannelGroup);
        }
    }

    public void createNotificationChannels(@NonNull List<NotificationChannel> list) {
        if (VERSION.SDK_INT >= 26) {
            this.mNotificationManager.createNotificationChannels(list);
        }
    }

    public void createNotificationChannelGroups(@NonNull List<NotificationChannelGroup> list) {
        if (VERSION.SDK_INT >= 26) {
            this.mNotificationManager.createNotificationChannelGroups(list);
        }
    }

    public void deleteNotificationChannel(@NonNull String str) {
        if (VERSION.SDK_INT >= 26) {
            this.mNotificationManager.deleteNotificationChannel(str);
        }
    }

    public void deleteNotificationChannelGroup(@NonNull String str) {
        if (VERSION.SDK_INT >= 26) {
            this.mNotificationManager.deleteNotificationChannelGroup(str);
        }
    }

    @Nullable
    public NotificationChannel getNotificationChannel(@NonNull String str) {
        return VERSION.SDK_INT >= 26 ? this.mNotificationManager.getNotificationChannel(str) : null;
    }

    @Nullable
    public NotificationChannelGroup getNotificationChannelGroup(@NonNull String str) {
        if (VERSION.SDK_INT >= 28) {
            return this.mNotificationManager.getNotificationChannelGroup(str);
        }
        if (VERSION.SDK_INT >= 26) {
            for (NotificationChannelGroup notificationChannelGroup : getNotificationChannelGroups()) {
                if (notificationChannelGroup.getId().equals(str)) {
                    return notificationChannelGroup;
                }
            }
        }
        return null;
    }

    @NonNull
    public List<NotificationChannel> getNotificationChannels() {
        if (VERSION.SDK_INT >= 26) {
            return this.mNotificationManager.getNotificationChannels();
        }
        return Collections.emptyList();
    }

    @NonNull
    public List<NotificationChannelGroup> getNotificationChannelGroups() {
        if (VERSION.SDK_INT >= 26) {
            return this.mNotificationManager.getNotificationChannelGroups();
        }
        return Collections.emptyList();
    }

    @NonNull
    public static Set<String> getEnabledListenerPackages(@NonNull Context context) {
        String string = Secure.getString(context.getContentResolver(), SETTING_ENABLED_NOTIFICATION_LISTENERS);
        synchronized (sEnabledNotificationListenersLock) {
            if (string != null) {
                if (!string.equals(sEnabledNotificationListeners)) {
                    String[] split = string.split(":", -1);
                    Set hashSet = new HashSet(split.length);
                    for (String unflattenFromString : split) {
                        ComponentName unflattenFromString2 = ComponentName.unflattenFromString(unflattenFromString);
                        if (unflattenFromString2 != null) {
                            hashSet.add(unflattenFromString2.getPackageName());
                        }
                    }
                    sEnabledNotificationListenerPackages = hashSet;
                    sEnabledNotificationListeners = string;
                }
            }
        }
        return sEnabledNotificationListenerPackages;
    }

    private static boolean useSideChannelForNotification(Notification notification) {
        Bundle extras = NotificationCompat.getExtras(notification);
        return extras != null && extras.getBoolean(EXTRA_USE_SIDE_CHANNEL);
    }

    private void pushSideChannelQueue(Task task) {
        synchronized (sLock) {
            if (sSideChannelManager == null) {
                sSideChannelManager = new SideChannelManager(this.mContext.getApplicationContext());
            }
            sSideChannelManager.queueTask(task);
        }
    }
}
