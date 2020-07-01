package io.grpc;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

@CheckReturnValue
public class Context {
    static final int CONTEXT_DEPTH_WARN_THRESH = 1000;
    private static final PersistentHashArrayMappedTrie<Key<?>, Object> EMPTY_ENTRIES = new PersistentHashArrayMappedTrie();
    public static final Context ROOT = new Context(null, EMPTY_ENTRIES);
    private static final Logger log = Logger.getLogger(Context.class.getName());
    private static final AtomicReference<Storage> storage = new AtomicReference();
    final CancellableContext cancellableAncestor;
    final int generation;
    final PersistentHashArrayMappedTrie<Key<?>, Object> keyValueEntries;
    private ArrayList<ExecutableListener> listeners;
    private CancellationListener parentListener;

    @interface CanIgnoreReturnValue {
    }

    public interface CancellationListener {
        void cancelled(Context context);
    }

    @interface CheckReturnValue {
    }

    private enum DirectExecutor implements Executor {
        INSTANCE;

        public String toString() {
            return "Context.DirectExecutor";
        }

        public void execute(Runnable runnable) {
            runnable.run();
        }
    }

    private class ExecutableListener implements Runnable {
        private final Executor executor;
        private final CancellationListener listener;

        /* synthetic */ ExecutableListener(Context context, Executor executor, CancellationListener cancellationListener, AnonymousClass1 anonymousClass1) {
            this(executor, cancellationListener);
        }

        private ExecutableListener(Executor executor, CancellationListener cancellationListener) {
            this.executor = executor;
            this.listener = cancellationListener;
        }

        private void deliver() {
            try {
                this.executor.execute(this);
            } catch (Throwable th) {
                Context.log.log(Level.INFO, "Exception notifying context listener", th);
            }
        }

        public void run() {
            this.listener.cancelled(Context.this);
        }
    }

    public static final class Key<T> {
        private final T defaultValue;
        private final String name;

        Key(String str) {
            this(str, null);
        }

        Key(String str, T t) {
            this.name = (String) Context.checkNotNull(str, ConditionalUserProperty.NAME);
            this.defaultValue = t;
        }

        public T get() {
            return get(Context.current());
        }

        public T get(Context context) {
            T access$900 = context.lookup(this);
            return access$900 == null ? this.defaultValue : access$900;
        }

        public String toString() {
            return this.name;
        }
    }

    public static abstract class Storage {
        public abstract Context current();

        public abstract void detach(Context context, Context context2);

        @Deprecated
        public void attach(Context context) {
            throw new UnsupportedOperationException("Deprecated. Do not call.");
        }

        public Context doAttach(Context context) {
            Context current = current();
            attach(context);
            return current;
        }
    }

    public static final class CancellableContext extends Context implements Closeable {
        private Throwable cancellationCause;
        private boolean cancelled;
        private final Deadline deadline;
        private ScheduledFuture<?> pendingDeadline;
        private final Context uncancellableSurrogate;

        boolean canBeCancelled() {
            return true;
        }

        /* synthetic */ CancellableContext(Context context, AnonymousClass1 anonymousClass1) {
            this(context);
        }

        /* synthetic */ CancellableContext(Context context, Deadline deadline, ScheduledExecutorService scheduledExecutorService, AnonymousClass1 anonymousClass1) {
            this(context, deadline, scheduledExecutorService);
        }

        private CancellableContext(Context context) {
            super(context, context.keyValueEntries, null);
            this.deadline = context.getDeadline();
            this.uncancellableSurrogate = new Context(this, this.keyValueEntries, null);
        }

        private CancellableContext(Context context, Deadline deadline, ScheduledExecutorService scheduledExecutorService) {
            super(context, context.keyValueEntries, null);
            Deadline deadline2 = context.getDeadline();
            if (deadline2 == null || deadline2.compareTo(deadline) > 0) {
                if (deadline.isExpired()) {
                    cancel(new TimeoutException("context timed out"));
                } else {
                    this.pendingDeadline = deadline.runOnExpiration(new Runnable() {
                        public void run() {
                            try {
                                CancellableContext.this.cancel(new TimeoutException("context timed out"));
                            } catch (Throwable th) {
                                Context.log.log(Level.SEVERE, "Cancel threw an exception, which should not happen", th);
                            }
                        }
                    }, scheduledExecutorService);
                }
                deadline2 = deadline;
            }
            this.deadline = deadline2;
            this.uncancellableSurrogate = new Context(this, this.keyValueEntries, null);
        }

        public Context attach() {
            return this.uncancellableSurrogate.attach();
        }

        public void detach(Context context) {
            this.uncancellableSurrogate.detach(context);
        }

        @Deprecated
        public boolean isCurrent() {
            return this.uncancellableSurrogate.isCurrent();
        }

        @CanIgnoreReturnValue
        public boolean cancel(Throwable th) {
            boolean z;
            synchronized (this) {
                z = true;
                if (this.cancelled) {
                    z = false;
                } else {
                    this.cancelled = true;
                    if (this.pendingDeadline != null) {
                        this.pendingDeadline.cancel(false);
                        this.pendingDeadline = null;
                    }
                    this.cancellationCause = th;
                }
            }
            if (z) {
                notifyAndClearListeners();
            }
            return z;
        }

        public void detachAndCancel(Context context, Throwable th) {
            try {
                detach(context);
            } finally {
                cancel(th);
            }
        }

        /* JADX WARNING: Missing block: B:8:0x000d, code:
            if (super.isCancelled() == false) goto L_0x0017;
     */
        /* JADX WARNING: Missing block: B:9:0x000f, code:
            cancel(super.cancellationCause());
     */
        /* JADX WARNING: Missing block: B:10:0x0016, code:
            return true;
     */
        /* JADX WARNING: Missing block: B:12:0x0018, code:
            return false;
     */
        public boolean isCancelled() {
            /*
            r2 = this;
            monitor-enter(r2);
            r0 = r2.cancelled;	 Catch:{ all -> 0x0019 }
            r1 = 1;
            if (r0 == 0) goto L_0x0008;
        L_0x0006:
            monitor-exit(r2);	 Catch:{ all -> 0x0019 }
            return r1;
        L_0x0008:
            monitor-exit(r2);	 Catch:{ all -> 0x0019 }
            r0 = super.isCancelled();
            if (r0 == 0) goto L_0x0017;
        L_0x000f:
            r0 = super.cancellationCause();
            r2.cancel(r0);
            return r1;
        L_0x0017:
            r0 = 0;
            return r0;
        L_0x0019:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0019 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.Context.CancellableContext.isCancelled():boolean");
        }

        public Throwable cancellationCause() {
            return isCancelled() ? this.cancellationCause : null;
        }

        public Deadline getDeadline() {
            return this.deadline;
        }

        public void close() {
            cancel(null);
        }
    }

    private class ParentListener implements CancellationListener {
        private ParentListener() {
        }

        /* synthetic */ ParentListener(Context context, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void cancelled(Context context) {
            Context context2 = Context.this;
            if (context2 instanceof CancellableContext) {
                ((CancellableContext) context2).cancel(context.cancellationCause());
            } else {
                context2.notifyAndClearListeners();
            }
        }
    }

    /* synthetic */ Context(Context context, PersistentHashArrayMappedTrie persistentHashArrayMappedTrie, AnonymousClass1 anonymousClass1) {
        this(context, persistentHashArrayMappedTrie);
    }

    static Storage storage() {
        Storage storage = (Storage) storage.get();
        return storage == null ? createStorage() : storage;
    }

    private static Storage createStorage() {
        try {
            storage.compareAndSet(null, (Storage) Class.forName("io.grpc.override.ContextStorageOverride").getConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (Throwable e) {
            if (storage.compareAndSet(null, new ThreadLocalContextStorage())) {
                log.log(Level.FINE, "Storage override doesn't exist. Using default", e);
            }
        } catch (Throwable e2) {
            throw new RuntimeException("Storage override failed to initialize", e2);
        }
        return (Storage) storage.get();
    }

    public static <T> Key<T> key(String str) {
        return new Key(str);
    }

    public static <T> Key<T> keyWithDefault(String str, T t) {
        return new Key(str, t);
    }

    public static Context current() {
        Context current = storage().current();
        return current == null ? ROOT : current;
    }

    private Context(PersistentHashArrayMappedTrie<Key<?>, Object> persistentHashArrayMappedTrie, int i) {
        this.parentListener = new ParentListener(this, null);
        this.cancellableAncestor = null;
        this.keyValueEntries = persistentHashArrayMappedTrie;
        this.generation = i;
        validateGeneration(i);
    }

    private Context(Context context, PersistentHashArrayMappedTrie<Key<?>, Object> persistentHashArrayMappedTrie) {
        int i;
        this.parentListener = new ParentListener(this, null);
        this.cancellableAncestor = cancellableAncestor(context);
        this.keyValueEntries = persistentHashArrayMappedTrie;
        if (context == null) {
            i = 0;
        } else {
            i = context.generation + 1;
        }
        this.generation = i;
        validateGeneration(this.generation);
    }

    public CancellableContext withCancellation() {
        return new CancellableContext(this, null);
    }

    public CancellableContext withDeadlineAfter(long j, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        return withDeadline(Deadline.after(j, timeUnit), scheduledExecutorService);
    }

    public CancellableContext withDeadline(Deadline deadline, ScheduledExecutorService scheduledExecutorService) {
        checkNotNull(deadline, "deadline");
        checkNotNull(scheduledExecutorService, "scheduler");
        return new CancellableContext(this, deadline, scheduledExecutorService, null);
    }

    public <V> Context withValue(Key<V> key, V v) {
        return new Context(this, this.keyValueEntries.put(key, v));
    }

    public <V1, V2> Context withValues(Key<V1> key, V1 v1, Key<V2> key2, V2 v2) {
        return new Context(this, this.keyValueEntries.put(key, v1).put(key2, v2));
    }

    public <V1, V2, V3> Context withValues(Key<V1> key, V1 v1, Key<V2> key2, V2 v2, Key<V3> key3, V3 v3) {
        return new Context(this, this.keyValueEntries.put(key, v1).put(key2, v2).put(key3, v3));
    }

    public <V1, V2, V3, V4> Context withValues(Key<V1> key, V1 v1, Key<V2> key2, V2 v2, Key<V3> key3, V3 v3, Key<V4> key4, V4 v4) {
        return new Context(this, this.keyValueEntries.put(key, v1).put(key2, v2).put(key3, v3).put(key4, v4));
    }

    public Context fork() {
        return new Context(this.keyValueEntries, this.generation + 1);
    }

    boolean canBeCancelled() {
        return this.cancellableAncestor != null;
    }

    public Context attach() {
        Context doAttach = storage().doAttach(this);
        return doAttach == null ? ROOT : doAttach;
    }

    public void detach(Context context) {
        checkNotNull(context, "toAttach");
        storage().detach(this, context);
    }

    boolean isCurrent() {
        return current() == this;
    }

    public boolean isCancelled() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext == null) {
            return false;
        }
        return cancellableContext.isCancelled();
    }

    public Throwable cancellationCause() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext == null) {
            return null;
        }
        return cancellableContext.cancellationCause();
    }

    public Deadline getDeadline() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext == null) {
            return null;
        }
        return cancellableContext.getDeadline();
    }

    public void addListener(CancellationListener cancellationListener, Executor executor) {
        checkNotNull(cancellationListener, "cancellationListener");
        checkNotNull(executor, "executor");
        if (canBeCancelled()) {
            ExecutableListener executableListener = new ExecutableListener(this, executor, cancellationListener, null);
            synchronized (this) {
                if (isCancelled()) {
                    executableListener.deliver();
                } else if (this.listeners == null) {
                    this.listeners = new ArrayList();
                    this.listeners.add(executableListener);
                    if (this.cancellableAncestor != null) {
                        this.cancellableAncestor.addListener(this.parentListener, DirectExecutor.INSTANCE);
                    }
                } else {
                    this.listeners.add(executableListener);
                }
            }
        }
    }

    public void removeListener(CancellationListener cancellationListener) {
        if (canBeCancelled()) {
            synchronized (this) {
                if (this.listeners != null) {
                    for (int size = this.listeners.size() - 1; size >= 0; size--) {
                        if (((ExecutableListener) this.listeners.get(size)).listener == cancellationListener) {
                            this.listeners.remove(size);
                            break;
                        }
                    }
                    if (this.listeners.isEmpty()) {
                        if (this.cancellableAncestor != null) {
                            this.cancellableAncestor.removeListener(this.parentListener);
                        }
                        this.listeners = null;
                    }
                }
            }
        }
    }

    /* JADX WARNING: Missing block: B:11:0x0014, code:
            r1 = 0;
            r2 = 0;
     */
    /* JADX WARNING: Missing block: B:13:0x001a, code:
            if (r2 >= r0.size()) goto L_0x0036;
     */
    /* JADX WARNING: Missing block: B:15:0x0028, code:
            if ((io.grpc.Context.ExecutableListener.access$500((io.grpc.Context.ExecutableListener) r0.get(r2)) instanceof io.grpc.Context.ParentListener) != false) goto L_0x0033;
     */
    /* JADX WARNING: Missing block: B:16:0x002a, code:
            io.grpc.Context.ExecutableListener.access$400((io.grpc.Context.ExecutableListener) r0.get(r2));
     */
    /* JADX WARNING: Missing block: B:17:0x0033, code:
            r2 = r2 + 1;
     */
    /* JADX WARNING: Missing block: B:19:0x003a, code:
            if (r1 >= r0.size()) goto L_0x0056;
     */
    /* JADX WARNING: Missing block: B:21:0x0048, code:
            if ((io.grpc.Context.ExecutableListener.access$500((io.grpc.Context.ExecutableListener) r0.get(r1)) instanceof io.grpc.Context.ParentListener) == false) goto L_0x0053;
     */
    /* JADX WARNING: Missing block: B:22:0x004a, code:
            io.grpc.Context.ExecutableListener.access$400((io.grpc.Context.ExecutableListener) r0.get(r1));
     */
    /* JADX WARNING: Missing block: B:23:0x0053, code:
            r1 = r1 + 1;
     */
    /* JADX WARNING: Missing block: B:24:0x0056, code:
            r0 = r4.cancellableAncestor;
     */
    /* JADX WARNING: Missing block: B:25:0x0058, code:
            if (r0 == null) goto L_0x005f;
     */
    /* JADX WARNING: Missing block: B:26:0x005a, code:
            r0.removeListener(r4.parentListener);
     */
    /* JADX WARNING: Missing block: B:27:0x005f, code:
            return;
     */
    void notifyAndClearListeners() {
        /*
        r4 = this;
        r0 = r4.canBeCancelled();
        if (r0 != 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        monitor-enter(r4);
        r0 = r4.listeners;	 Catch:{ all -> 0x0060 }
        if (r0 != 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r4);	 Catch:{ all -> 0x0060 }
        return;
    L_0x000e:
        r0 = r4.listeners;	 Catch:{ all -> 0x0060 }
        r1 = 0;
        r4.listeners = r1;	 Catch:{ all -> 0x0060 }
        monitor-exit(r4);	 Catch:{ all -> 0x0060 }
        r1 = 0;
        r2 = 0;
    L_0x0016:
        r3 = r0.size();
        if (r2 >= r3) goto L_0x0036;
    L_0x001c:
        r3 = r0.get(r2);
        r3 = (io.grpc.Context.ExecutableListener) r3;
        r3 = r3.listener;
        r3 = r3 instanceof io.grpc.Context.ParentListener;
        if (r3 != 0) goto L_0x0033;
    L_0x002a:
        r3 = r0.get(r2);
        r3 = (io.grpc.Context.ExecutableListener) r3;
        r3.deliver();
    L_0x0033:
        r2 = r2 + 1;
        goto L_0x0016;
    L_0x0036:
        r2 = r0.size();
        if (r1 >= r2) goto L_0x0056;
    L_0x003c:
        r2 = r0.get(r1);
        r2 = (io.grpc.Context.ExecutableListener) r2;
        r2 = r2.listener;
        r2 = r2 instanceof io.grpc.Context.ParentListener;
        if (r2 == 0) goto L_0x0053;
    L_0x004a:
        r2 = r0.get(r1);
        r2 = (io.grpc.Context.ExecutableListener) r2;
        r2.deliver();
    L_0x0053:
        r1 = r1 + 1;
        goto L_0x0036;
    L_0x0056:
        r0 = r4.cancellableAncestor;
        if (r0 == 0) goto L_0x005f;
    L_0x005a:
        r1 = r4.parentListener;
        r0.removeListener(r1);
    L_0x005f:
        return;
    L_0x0060:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0060 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.Context.notifyAndClearListeners():void");
    }

    int listenerCount() {
        int size;
        synchronized (this) {
            size = this.listeners == null ? 0 : this.listeners.size();
        }
        return size;
    }

    public void run(Runnable runnable) {
        Context attach = attach();
        try {
            runnable.run();
        } finally {
            detach(attach);
        }
    }

    @CanIgnoreReturnValue
    public <V> V call(Callable<V> callable) throws Exception {
        Context attach = attach();
        try {
            V call = callable.call();
            return call;
        } finally {
            detach(attach);
        }
    }

    public Runnable wrap(final Runnable runnable) {
        return new Runnable() {
            public void run() {
                Context attach = Context.this.attach();
                try {
                    runnable.run();
                } finally {
                    Context.this.detach(attach);
                }
            }
        };
    }

    public <C> Callable<C> wrap(final Callable<C> callable) {
        return new Callable<C>() {
            public C call() throws Exception {
                Context attach = Context.this.attach();
                try {
                    C call = callable.call();
                    return call;
                } finally {
                    Context.this.detach(attach);
                }
            }
        };
    }

    public Executor fixedContextExecutor(final Executor executor) {
        return new Executor() {
            public void execute(Runnable runnable) {
                executor.execute(Context.this.wrap(runnable));
            }
        };
    }

    public static Executor currentContextExecutor(final Executor executor) {
        return new Executor() {
            public void execute(Runnable runnable) {
                executor.execute(Context.current().wrap(runnable));
            }
        };
    }

    private Object lookup(Key<?> key) {
        return this.keyValueEntries.get(key);
    }

    @CanIgnoreReturnValue
    private static <T> T checkNotNull(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    static CancellableContext cancellableAncestor(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof CancellableContext) {
            return (CancellableContext) context;
        }
        return context.cancellableAncestor;
    }

    private static void validateGeneration(int i) {
        if (i == 1000) {
            log.log(Level.SEVERE, "Context ancestry chain length is abnormally long. This suggests an error in application code. Length exceeded: 1000", new Exception());
        }
    }
}
