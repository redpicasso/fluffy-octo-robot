package com.google.firebase.components;

import androidx.annotation.GuardedBy;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Publisher;
import com.google.firebase.events.Subscriber;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
class EventBus implements Subscriber, Publisher {
    private final Executor defaultExecutor;
    @GuardedBy("this")
    private final Map<Class<?>, ConcurrentHashMap<EventHandler<Object>, Executor>> handlerMap = new HashMap();
    @GuardedBy("this")
    private Queue<Event<?>> pendingEvents = new ArrayDeque();

    EventBus(Executor executor) {
        this.defaultExecutor = executor;
    }

    /* JADX WARNING: Missing block: B:9:0x0010, code:
            r0 = getHandlers(r4).iterator();
     */
    /* JADX WARNING: Missing block: B:11:0x001c, code:
            if (r0.hasNext() == false) goto L_0x0032;
     */
    /* JADX WARNING: Missing block: B:12:0x001e, code:
            r1 = (java.util.Map.Entry) r0.next();
            ((java.util.concurrent.Executor) r1.getValue()).execute(com.google.firebase.components.EventBus$$Lambda$1.lambdaFactory$(r1, r4));
     */
    /* JADX WARNING: Missing block: B:13:0x0032, code:
            return;
     */
    public void publish(com.google.firebase.events.Event<?> r4) {
        /*
        r3 = this;
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r4);
        monitor-enter(r3);
        r0 = r3.pendingEvents;	 Catch:{ all -> 0x0033 }
        if (r0 == 0) goto L_0x000f;
    L_0x0008:
        r0 = r3.pendingEvents;	 Catch:{ all -> 0x0033 }
        r0.add(r4);	 Catch:{ all -> 0x0033 }
        monitor-exit(r3);	 Catch:{ all -> 0x0033 }
        return;
    L_0x000f:
        monitor-exit(r3);	 Catch:{ all -> 0x0033 }
        r0 = r3.getHandlers(r4);
        r0 = r0.iterator();
    L_0x0018:
        r1 = r0.hasNext();
        if (r1 == 0) goto L_0x0032;
    L_0x001e:
        r1 = r0.next();
        r1 = (java.util.Map.Entry) r1;
        r2 = r1.getValue();
        r2 = (java.util.concurrent.Executor) r2;
        r1 = com.google.firebase.components.EventBus$$Lambda$1.lambdaFactory$(r1, r4);
        r2.execute(r1);
        goto L_0x0018;
    L_0x0032:
        return;
    L_0x0033:
        r4 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0033 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.components.EventBus.publish(com.google.firebase.events.Event):void");
    }

    private synchronized Set<Entry<EventHandler<Object>, Executor>> getHandlers(Event<?> event) {
        Map map;
        map = (Map) this.handlerMap.get(event.getType());
        return map == null ? Collections.emptySet() : map.entrySet();
    }

    public synchronized <T> void subscribe(Class<T> cls, Executor executor, EventHandler<? super T> eventHandler) {
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(eventHandler);
        Preconditions.checkNotNull(executor);
        if (!this.handlerMap.containsKey(cls)) {
            this.handlerMap.put(cls, new ConcurrentHashMap());
        }
        ((ConcurrentHashMap) this.handlerMap.get(cls)).put(eventHandler, executor);
    }

    public <T> void subscribe(Class<T> cls, EventHandler<? super T> eventHandler) {
        subscribe(cls, this.defaultExecutor, eventHandler);
    }

    /* JADX WARNING: Missing block: B:11:0x0028, code:
            return;
     */
    public synchronized <T> void unsubscribe(java.lang.Class<T> r2, com.google.firebase.events.EventHandler<? super T> r3) {
        /*
        r1 = this;
        monitor-enter(r1);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r2);	 Catch:{ all -> 0x0029 }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r3);	 Catch:{ all -> 0x0029 }
        r0 = r1.handlerMap;	 Catch:{ all -> 0x0029 }
        r0 = r0.containsKey(r2);	 Catch:{ all -> 0x0029 }
        if (r0 != 0) goto L_0x0011;
    L_0x000f:
        monitor-exit(r1);
        return;
    L_0x0011:
        r0 = r1.handlerMap;	 Catch:{ all -> 0x0029 }
        r0 = r0.get(r2);	 Catch:{ all -> 0x0029 }
        r0 = (java.util.concurrent.ConcurrentHashMap) r0;	 Catch:{ all -> 0x0029 }
        r0.remove(r3);	 Catch:{ all -> 0x0029 }
        r3 = r0.isEmpty();	 Catch:{ all -> 0x0029 }
        if (r3 == 0) goto L_0x0027;
    L_0x0022:
        r3 = r1.handlerMap;	 Catch:{ all -> 0x0029 }
        r3.remove(r2);	 Catch:{ all -> 0x0029 }
    L_0x0027:
        monitor-exit(r1);
        return;
    L_0x0029:
        r2 = move-exception;
        monitor-exit(r1);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.components.EventBus.unsubscribe(java.lang.Class, com.google.firebase.events.EventHandler):void");
    }

    void enablePublishingAndFlushPending() {
        synchronized (this) {
            Queue queue;
            if (this.pendingEvents != null) {
                queue = this.pendingEvents;
                this.pendingEvents = null;
            } else {
                queue = null;
            }
        }
        if (queue != null) {
            for (Event publish : queue) {
                publish(publish);
            }
        }
    }
}
