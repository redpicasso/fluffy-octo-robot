package com.google.common.util.concurrent;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtIncompatible
final class ListenerCallQueue<L> {
    private static final Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
    private final List<PerListenerQueue<L>> listeners = Collections.synchronizedList(new ArrayList());

    interface Event<L> {
        void call(L l);
    }

    private static final class PerListenerQueue<L> implements Runnable {
        final Executor executor;
        @GuardedBy("this")
        boolean isThreadScheduled;
        @GuardedBy("this")
        final Queue<Object> labelQueue = Queues.newArrayDeque();
        final L listener;
        @GuardedBy("this")
        final Queue<Event<L>> waitQueue = Queues.newArrayDeque();

        PerListenerQueue(L l, Executor executor) {
            this.listener = Preconditions.checkNotNull(l);
            this.executor = (Executor) Preconditions.checkNotNull(executor);
        }

        synchronized void add(Event<L> event, Object obj) {
            this.waitQueue.add(event);
            this.labelQueue.add(obj);
        }

        void dispatch() {
            boolean z;
            synchronized (this) {
                z = true;
                if (this.isThreadScheduled) {
                    z = false;
                } else {
                    this.isThreadScheduled = true;
                }
            }
            if (z) {
                try {
                    this.executor.execute(this);
                } catch (Throwable e) {
                    synchronized (this) {
                        this.isThreadScheduled = false;
                        Logger access$000 = ListenerCallQueue.logger;
                        Level level = Level.SEVERE;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Exception while running callbacks for ");
                        stringBuilder.append(this.listener);
                        stringBuilder.append(" on ");
                        stringBuilder.append(this.executor);
                        access$000.log(level, stringBuilder.toString(), e);
                        throw e;
                    }
                }
            }
        }

        /* JADX WARNING: Missing block: B:15:?, code:
            r2.call(r8.listener);
     */
        public void run() {
            /*
            r8 = this;
        L_0x0000:
            r0 = 0;
            r1 = 1;
            monitor-enter(r8);	 Catch:{ all -> 0x0050 }
            r2 = r8.isThreadScheduled;	 Catch:{ all -> 0x004d }
            com.google.common.base.Preconditions.checkState(r2);	 Catch:{ all -> 0x004d }
            r2 = r8.waitQueue;	 Catch:{ all -> 0x004d }
            r2 = r2.poll();	 Catch:{ all -> 0x004d }
            r2 = (com.google.common.util.concurrent.ListenerCallQueue.Event) r2;	 Catch:{ all -> 0x004d }
            r3 = r8.labelQueue;	 Catch:{ all -> 0x004d }
            r3 = r3.poll();	 Catch:{ all -> 0x004d }
            if (r2 != 0) goto L_0x0020;
        L_0x0018:
            r8.isThreadScheduled = r0;	 Catch:{ all -> 0x004d }
            monitor-exit(r8);	 Catch:{ all -> 0x001c }
            return;
        L_0x001c:
            r1 = move-exception;
            r2 = r1;
            r1 = 0;
            goto L_0x004e;
        L_0x0020:
            monitor-exit(r8);	 Catch:{ all -> 0x004d }
            r4 = r8.listener;	 Catch:{ RuntimeException -> 0x0027 }
            r2.call(r4);	 Catch:{ RuntimeException -> 0x0027 }
            goto L_0x0000;
        L_0x0027:
            r2 = move-exception;
            r4 = com.google.common.util.concurrent.ListenerCallQueue.logger;	 Catch:{ all -> 0x0050 }
            r5 = java.util.logging.Level.SEVERE;	 Catch:{ all -> 0x0050 }
            r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0050 }
            r6.<init>();	 Catch:{ all -> 0x0050 }
            r7 = "Exception while executing callback: ";
            r6.append(r7);	 Catch:{ all -> 0x0050 }
            r7 = r8.listener;	 Catch:{ all -> 0x0050 }
            r6.append(r7);	 Catch:{ all -> 0x0050 }
            r7 = " ";
            r6.append(r7);	 Catch:{ all -> 0x0050 }
            r6.append(r3);	 Catch:{ all -> 0x0050 }
            r3 = r6.toString();	 Catch:{ all -> 0x0050 }
            r4.log(r5, r3, r2);	 Catch:{ all -> 0x0050 }
            goto L_0x0000;
        L_0x004d:
            r2 = move-exception;
        L_0x004e:
            monitor-exit(r8);	 Catch:{ all -> 0x004d }
            throw r2;	 Catch:{ all -> 0x0050 }
        L_0x0050:
            r2 = move-exception;
            if (r1 == 0) goto L_0x005b;
        L_0x0053:
            monitor-enter(r8);
            r8.isThreadScheduled = r0;	 Catch:{ all -> 0x0058 }
            monitor-exit(r8);	 Catch:{ all -> 0x0058 }
            goto L_0x005b;
        L_0x0058:
            r0 = move-exception;
            monitor-exit(r8);	 Catch:{ all -> 0x0058 }
            throw r0;
        L_0x005b:
            throw r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ListenerCallQueue.PerListenerQueue.run():void");
        }
    }

    ListenerCallQueue() {
    }

    public void addListener(L l, Executor executor) {
        Preconditions.checkNotNull(l, CastExtraArgs.LISTENER);
        Preconditions.checkNotNull(executor, "executor");
        this.listeners.add(new PerListenerQueue(l, executor));
    }

    public void enqueue(Event<L> event) {
        enqueueHelper(event, event);
    }

    public void enqueue(Event<L> event, String str) {
        enqueueHelper(event, str);
    }

    private void enqueueHelper(Event<L> event, Object obj) {
        Preconditions.checkNotNull(event, NotificationCompat.CATEGORY_EVENT);
        Preconditions.checkNotNull(obj, "label");
        synchronized (this.listeners) {
            for (PerListenerQueue add : this.listeners) {
                add.add(event, obj);
            }
        }
    }

    public void dispatch() {
        for (int i = 0; i < this.listeners.size(); i++) {
            ((PerListenerQueue) this.listeners.get(i)).dispatch();
        }
    }
}
