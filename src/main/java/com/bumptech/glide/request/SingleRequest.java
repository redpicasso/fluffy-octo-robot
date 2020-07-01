package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.annotation.DrawableRes;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools.Pool;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.Engine.LoadStatus;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.FactoryPools.Factory;
import com.bumptech.glide.util.pool.FactoryPools.Poolable;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.List;
import java.util.concurrent.Executor;

public final class SingleRequest<R> implements Request, SizeReadyCallback, ResourceCallback, Poolable {
    private static final String GLIDE_TAG = "Glide";
    private static final boolean IS_VERBOSE_LOGGABLE = Log.isLoggable(TAG, 2);
    private static final Pool<SingleRequest<?>> POOL = FactoryPools.threadSafe(150, new Factory<SingleRequest<?>>() {
        public SingleRequest<?> create() {
            return new SingleRequest();
        }
    });
    private static final String TAG = "Request";
    private TransitionFactory<? super R> animationFactory;
    private Executor callbackExecutor;
    private Context context;
    private Engine engine;
    private Drawable errorDrawable;
    private Drawable fallbackDrawable;
    private GlideContext glideContext;
    private int height;
    private boolean isCallingCallbacks;
    private LoadStatus loadStatus;
    @Nullable
    private Object model;
    private int overrideHeight;
    private int overrideWidth;
    private Drawable placeholderDrawable;
    private Priority priority;
    private RequestCoordinator requestCoordinator;
    @Nullable
    private List<RequestListener<R>> requestListeners;
    private BaseRequestOptions<?> requestOptions;
    @Nullable
    private RuntimeException requestOrigin;
    private Resource<R> resource;
    private long startTime;
    private final StateVerifier stateVerifier;
    @GuardedBy("this")
    private Status status;
    @Nullable
    private final String tag;
    private Target<R> target;
    @Nullable
    private RequestListener<R> targetListener;
    private Class<R> transcodeClass;
    private int width;

    private enum Status {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CLEARED
    }

    public static <R> SingleRequest<R> obtain(Context context, GlideContext glideContext, Object obj, Class<R> cls, BaseRequestOptions<?> baseRequestOptions, int i, int i2, Priority priority, Target<R> target, RequestListener<R> requestListener, @Nullable List<RequestListener<R>> list, RequestCoordinator requestCoordinator, Engine engine, TransitionFactory<? super R> transitionFactory, Executor executor) {
        SingleRequest<R> singleRequest = (SingleRequest) POOL.acquire();
        if (singleRequest == null) {
            singleRequest = new SingleRequest();
        }
        singleRequest.init(context, glideContext, obj, cls, baseRequestOptions, i, i2, priority, target, requestListener, list, requestCoordinator, engine, transitionFactory, executor);
        return singleRequest;
    }

    SingleRequest() {
        this.tag = IS_VERBOSE_LOGGABLE ? String.valueOf(super.hashCode()) : null;
        this.stateVerifier = StateVerifier.newInstance();
    }

    private synchronized void init(Context context, GlideContext glideContext, Object obj, Class<R> cls, BaseRequestOptions<?> baseRequestOptions, int i, int i2, Priority priority, Target<R> target, RequestListener<R> requestListener, @Nullable List<RequestListener<R>> list, RequestCoordinator requestCoordinator, Engine engine, TransitionFactory<? super R> transitionFactory, Executor executor) {
        this.context = context;
        this.glideContext = glideContext;
        this.model = obj;
        this.transcodeClass = cls;
        this.requestOptions = baseRequestOptions;
        this.overrideWidth = i;
        this.overrideHeight = i2;
        this.priority = priority;
        this.target = target;
        this.targetListener = requestListener;
        this.requestListeners = list;
        this.requestCoordinator = requestCoordinator;
        this.engine = engine;
        this.animationFactory = transitionFactory;
        this.callbackExecutor = executor;
        this.status = Status.PENDING;
        if (this.requestOrigin == null && glideContext.isLoggingRequestOriginsEnabled()) {
            this.requestOrigin = new RuntimeException("Glide request origin trace");
        }
    }

    @NonNull
    public StateVerifier getVerifier() {
        return this.stateVerifier;
    }

    public synchronized void recycle() {
        assertNotCallingCallbacks();
        this.context = null;
        this.glideContext = null;
        this.model = null;
        this.transcodeClass = null;
        this.requestOptions = null;
        this.overrideWidth = -1;
        this.overrideHeight = -1;
        this.target = null;
        this.requestListeners = null;
        this.targetListener = null;
        this.requestCoordinator = null;
        this.animationFactory = null;
        this.loadStatus = null;
        this.errorDrawable = null;
        this.placeholderDrawable = null;
        this.fallbackDrawable = null;
        this.width = -1;
        this.height = -1;
        this.requestOrigin = null;
        POOL.release(this);
    }

    /* JADX WARNING: Missing block: B:38:0x00a4, code:
            return;
     */
    public synchronized void begin() {
        /*
        r3 = this;
        monitor-enter(r3);
        r3.assertNotCallingCallbacks();	 Catch:{ all -> 0x00ad }
        r0 = r3.stateVerifier;	 Catch:{ all -> 0x00ad }
        r0.throwIfRecycled();	 Catch:{ all -> 0x00ad }
        r0 = com.bumptech.glide.util.LogTime.getLogTime();	 Catch:{ all -> 0x00ad }
        r3.startTime = r0;	 Catch:{ all -> 0x00ad }
        r0 = r3.model;	 Catch:{ all -> 0x00ad }
        if (r0 != 0) goto L_0x003a;
    L_0x0013:
        r0 = r3.overrideWidth;	 Catch:{ all -> 0x00ad }
        r1 = r3.overrideHeight;	 Catch:{ all -> 0x00ad }
        r0 = com.bumptech.glide.util.Util.isValidDimensions(r0, r1);	 Catch:{ all -> 0x00ad }
        if (r0 == 0) goto L_0x0025;
    L_0x001d:
        r0 = r3.overrideWidth;	 Catch:{ all -> 0x00ad }
        r3.width = r0;	 Catch:{ all -> 0x00ad }
        r0 = r3.overrideHeight;	 Catch:{ all -> 0x00ad }
        r3.height = r0;	 Catch:{ all -> 0x00ad }
    L_0x0025:
        r0 = r3.getFallbackDrawable();	 Catch:{ all -> 0x00ad }
        if (r0 != 0) goto L_0x002d;
    L_0x002b:
        r0 = 5;
        goto L_0x002e;
    L_0x002d:
        r0 = 3;
    L_0x002e:
        r1 = new com.bumptech.glide.load.engine.GlideException;	 Catch:{ all -> 0x00ad }
        r2 = "Received null model";
        r1.<init>(r2);	 Catch:{ all -> 0x00ad }
        r3.onLoadFailed(r1, r0);	 Catch:{ all -> 0x00ad }
        monitor-exit(r3);
        return;
    L_0x003a:
        r0 = r3.status;	 Catch:{ all -> 0x00ad }
        r1 = com.bumptech.glide.request.SingleRequest.Status.RUNNING;	 Catch:{ all -> 0x00ad }
        if (r0 == r1) goto L_0x00a5;
    L_0x0040:
        r0 = r3.status;	 Catch:{ all -> 0x00ad }
        r1 = com.bumptech.glide.request.SingleRequest.Status.COMPLETE;	 Catch:{ all -> 0x00ad }
        if (r0 != r1) goto L_0x004f;
    L_0x0046:
        r0 = r3.resource;	 Catch:{ all -> 0x00ad }
        r1 = com.bumptech.glide.load.DataSource.MEMORY_CACHE;	 Catch:{ all -> 0x00ad }
        r3.onResourceReady(r0, r1);	 Catch:{ all -> 0x00ad }
        monitor-exit(r3);
        return;
    L_0x004f:
        r0 = com.bumptech.glide.request.SingleRequest.Status.WAITING_FOR_SIZE;	 Catch:{ all -> 0x00ad }
        r3.status = r0;	 Catch:{ all -> 0x00ad }
        r0 = r3.overrideWidth;	 Catch:{ all -> 0x00ad }
        r1 = r3.overrideHeight;	 Catch:{ all -> 0x00ad }
        r0 = com.bumptech.glide.util.Util.isValidDimensions(r0, r1);	 Catch:{ all -> 0x00ad }
        if (r0 == 0) goto L_0x0065;
    L_0x005d:
        r0 = r3.overrideWidth;	 Catch:{ all -> 0x00ad }
        r1 = r3.overrideHeight;	 Catch:{ all -> 0x00ad }
        r3.onSizeReady(r0, r1);	 Catch:{ all -> 0x00ad }
        goto L_0x006a;
    L_0x0065:
        r0 = r3.target;	 Catch:{ all -> 0x00ad }
        r0.getSize(r3);	 Catch:{ all -> 0x00ad }
    L_0x006a:
        r0 = r3.status;	 Catch:{ all -> 0x00ad }
        r1 = com.bumptech.glide.request.SingleRequest.Status.RUNNING;	 Catch:{ all -> 0x00ad }
        if (r0 == r1) goto L_0x0076;
    L_0x0070:
        r0 = r3.status;	 Catch:{ all -> 0x00ad }
        r1 = com.bumptech.glide.request.SingleRequest.Status.WAITING_FOR_SIZE;	 Catch:{ all -> 0x00ad }
        if (r0 != r1) goto L_0x0085;
    L_0x0076:
        r0 = r3.canNotifyStatusChanged();	 Catch:{ all -> 0x00ad }
        if (r0 == 0) goto L_0x0085;
    L_0x007c:
        r0 = r3.target;	 Catch:{ all -> 0x00ad }
        r1 = r3.getPlaceholderDrawable();	 Catch:{ all -> 0x00ad }
        r0.onLoadStarted(r1);	 Catch:{ all -> 0x00ad }
    L_0x0085:
        r0 = IS_VERBOSE_LOGGABLE;	 Catch:{ all -> 0x00ad }
        if (r0 == 0) goto L_0x00a3;
    L_0x0089:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ad }
        r0.<init>();	 Catch:{ all -> 0x00ad }
        r1 = "finished run method in ";
        r0.append(r1);	 Catch:{ all -> 0x00ad }
        r1 = r3.startTime;	 Catch:{ all -> 0x00ad }
        r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1);	 Catch:{ all -> 0x00ad }
        r0.append(r1);	 Catch:{ all -> 0x00ad }
        r0 = r0.toString();	 Catch:{ all -> 0x00ad }
        r3.logV(r0);	 Catch:{ all -> 0x00ad }
    L_0x00a3:
        monitor-exit(r3);
        return;
    L_0x00a5:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x00ad }
        r1 = "Cannot restart a running request";
        r0.<init>(r1);	 Catch:{ all -> 0x00ad }
        throw r0;	 Catch:{ all -> 0x00ad }
    L_0x00ad:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.begin():void");
    }

    private void cancel() {
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        this.target.removeCallback(this);
        LoadStatus loadStatus = this.loadStatus;
        if (loadStatus != null) {
            loadStatus.cancel();
            this.loadStatus = null;
        }
    }

    private void assertNotCallingCallbacks() {
        if (this.isCallingCallbacks) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    public synchronized void clear() {
        assertNotCallingCallbacks();
        this.stateVerifier.throwIfRecycled();
        if (this.status != Status.CLEARED) {
            cancel();
            if (this.resource != null) {
                releaseResource(this.resource);
            }
            if (canNotifyCleared()) {
                this.target.onLoadCleared(getPlaceholderDrawable());
            }
            this.status = Status.CLEARED;
        }
    }

    private void releaseResource(Resource<?> resource) {
        this.engine.release(resource);
        this.resource = null;
    }

    public synchronized boolean isRunning() {
        boolean z;
        z = this.status == Status.RUNNING || this.status == Status.WAITING_FOR_SIZE;
        return z;
    }

    public synchronized boolean isComplete() {
        return this.status == Status.COMPLETE;
    }

    public synchronized boolean isResourceSet() {
        return isComplete();
    }

    public synchronized boolean isCleared() {
        return this.status == Status.CLEARED;
    }

    public synchronized boolean isFailed() {
        return this.status == Status.FAILED;
    }

    private Drawable getErrorDrawable() {
        if (this.errorDrawable == null) {
            this.errorDrawable = this.requestOptions.getErrorPlaceholder();
            if (this.errorDrawable == null && this.requestOptions.getErrorId() > 0) {
                this.errorDrawable = loadDrawable(this.requestOptions.getErrorId());
            }
        }
        return this.errorDrawable;
    }

    private Drawable getPlaceholderDrawable() {
        if (this.placeholderDrawable == null) {
            this.placeholderDrawable = this.requestOptions.getPlaceholderDrawable();
            if (this.placeholderDrawable == null && this.requestOptions.getPlaceholderId() > 0) {
                this.placeholderDrawable = loadDrawable(this.requestOptions.getPlaceholderId());
            }
        }
        return this.placeholderDrawable;
    }

    private Drawable getFallbackDrawable() {
        if (this.fallbackDrawable == null) {
            this.fallbackDrawable = this.requestOptions.getFallbackDrawable();
            if (this.fallbackDrawable == null && this.requestOptions.getFallbackId() > 0) {
                this.fallbackDrawable = loadDrawable(this.requestOptions.getFallbackId());
            }
        }
        return this.fallbackDrawable;
    }

    private Drawable loadDrawable(@DrawableRes int i) {
        return DrawableDecoderCompat.getDrawable(this.glideContext, i, this.requestOptions.getTheme() != null ? this.requestOptions.getTheme() : this.context.getTheme());
    }

    private synchronized void setErrorPlaceholder() {
        if (canNotifyStatusChanged()) {
            Drawable drawable = null;
            if (this.model == null) {
                drawable = getFallbackDrawable();
            }
            if (drawable == null) {
                drawable = getErrorDrawable();
            }
            if (drawable == null) {
                drawable = getPlaceholderDrawable();
            }
            this.target.onLoadFailed(drawable);
        }
    }

    /* JADX WARNING: Missing block: B:27:0x00f0, code:
            return;
     */
    public synchronized void onSizeReady(int r22, int r23) {
        /*
        r21 = this;
        r15 = r21;
        monitor-enter(r21);
        r0 = r15.stateVerifier;	 Catch:{ all -> 0x00f7 }
        r0.throwIfRecycled();	 Catch:{ all -> 0x00f7 }
        r0 = IS_VERBOSE_LOGGABLE;	 Catch:{ all -> 0x00f7 }
        if (r0 == 0) goto L_0x0026;
    L_0x000c:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f7 }
        r0.<init>();	 Catch:{ all -> 0x00f7 }
        r1 = "Got onSizeReady in ";
        r0.append(r1);	 Catch:{ all -> 0x00f7 }
        r1 = r15.startTime;	 Catch:{ all -> 0x00f7 }
        r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1);	 Catch:{ all -> 0x00f7 }
        r0.append(r1);	 Catch:{ all -> 0x00f7 }
        r0 = r0.toString();	 Catch:{ all -> 0x00f7 }
        r15.logV(r0);	 Catch:{ all -> 0x00f7 }
    L_0x0026:
        r0 = r15.status;	 Catch:{ all -> 0x00f7 }
        r1 = com.bumptech.glide.request.SingleRequest.Status.WAITING_FOR_SIZE;	 Catch:{ all -> 0x00f7 }
        if (r0 == r1) goto L_0x002e;
    L_0x002c:
        monitor-exit(r21);
        return;
    L_0x002e:
        r0 = com.bumptech.glide.request.SingleRequest.Status.RUNNING;	 Catch:{ all -> 0x00f7 }
        r15.status = r0;	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r0 = r0.getSizeMultiplier();	 Catch:{ all -> 0x00f7 }
        r1 = r22;
        r1 = maybeApplySizeMultiplier(r1, r0);	 Catch:{ all -> 0x00f7 }
        r15.width = r1;	 Catch:{ all -> 0x00f7 }
        r1 = r23;
        r0 = maybeApplySizeMultiplier(r1, r0);	 Catch:{ all -> 0x00f7 }
        r15.height = r0;	 Catch:{ all -> 0x00f7 }
        r0 = IS_VERBOSE_LOGGABLE;	 Catch:{ all -> 0x00f7 }
        if (r0 == 0) goto L_0x0066;
    L_0x004c:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f7 }
        r0.<init>();	 Catch:{ all -> 0x00f7 }
        r1 = "finished setup for calling load in ";
        r0.append(r1);	 Catch:{ all -> 0x00f7 }
        r1 = r15.startTime;	 Catch:{ all -> 0x00f7 }
        r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1);	 Catch:{ all -> 0x00f7 }
        r0.append(r1);	 Catch:{ all -> 0x00f7 }
        r0 = r0.toString();	 Catch:{ all -> 0x00f7 }
        r15.logV(r0);	 Catch:{ all -> 0x00f7 }
    L_0x0066:
        r1 = r15.engine;	 Catch:{ all -> 0x00f7 }
        r2 = r15.glideContext;	 Catch:{ all -> 0x00f7 }
        r3 = r15.model;	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r4 = r0.getSignature();	 Catch:{ all -> 0x00f7 }
        r5 = r15.width;	 Catch:{ all -> 0x00f7 }
        r6 = r15.height;	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r7 = r0.getResourceClass();	 Catch:{ all -> 0x00f7 }
        r8 = r15.transcodeClass;	 Catch:{ all -> 0x00f7 }
        r9 = r15.priority;	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r10 = r0.getDiskCacheStrategy();	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r11 = r0.getTransformations();	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r12 = r0.isTransformationRequired();	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r13 = r0.isScaleOnlyOrNoTransform();	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r14 = r0.getOptions();	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r0 = r0.isMemoryCacheable();	 Catch:{ all -> 0x00f7 }
        r22 = r0;
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r16 = r0.getUseUnlimitedSourceGeneratorsPool();	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r17 = r0.getUseAnimationPool();	 Catch:{ all -> 0x00f7 }
        r0 = r15.requestOptions;	 Catch:{ all -> 0x00f7 }
        r18 = r0.getOnlyRetrieveFromCache();	 Catch:{ all -> 0x00f7 }
        r0 = r15.callbackExecutor;	 Catch:{ all -> 0x00f7 }
        r15 = r22;
        r19 = r21;
        r20 = r0;
        r0 = r1.load(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20);	 Catch:{ all -> 0x00f3 }
        r1 = r21;
        r1.loadStatus = r0;	 Catch:{ all -> 0x00f1 }
        r0 = r1.status;	 Catch:{ all -> 0x00f1 }
        r2 = com.bumptech.glide.request.SingleRequest.Status.RUNNING;	 Catch:{ all -> 0x00f1 }
        if (r0 == r2) goto L_0x00d1;
    L_0x00ce:
        r0 = 0;
        r1.loadStatus = r0;	 Catch:{ all -> 0x00f1 }
    L_0x00d1:
        r0 = IS_VERBOSE_LOGGABLE;	 Catch:{ all -> 0x00f1 }
        if (r0 == 0) goto L_0x00ef;
    L_0x00d5:
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f1 }
        r0.<init>();	 Catch:{ all -> 0x00f1 }
        r2 = "finished onSizeReady in ";
        r0.append(r2);	 Catch:{ all -> 0x00f1 }
        r2 = r1.startTime;	 Catch:{ all -> 0x00f1 }
        r2 = com.bumptech.glide.util.LogTime.getElapsedMillis(r2);	 Catch:{ all -> 0x00f1 }
        r0.append(r2);	 Catch:{ all -> 0x00f1 }
        r0 = r0.toString();	 Catch:{ all -> 0x00f1 }
        r1.logV(r0);	 Catch:{ all -> 0x00f1 }
    L_0x00ef:
        monitor-exit(r21);
        return;
    L_0x00f1:
        r0 = move-exception;
        goto L_0x00f9;
    L_0x00f3:
        r0 = move-exception;
        r1 = r21;
        goto L_0x00f9;
    L_0x00f7:
        r0 = move-exception;
        r1 = r15;
    L_0x00f9:
        monitor-exit(r21);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.onSizeReady(int, int):void");
    }

    private static int maybeApplySizeMultiplier(int i, float f) {
        return i == Integer.MIN_VALUE ? i : Math.round(f * ((float) i));
    }

    private boolean canSetResource() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        return requestCoordinator == null || requestCoordinator.canSetImage(this);
    }

    private boolean canNotifyCleared() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        return requestCoordinator == null || requestCoordinator.canNotifyCleared(this);
    }

    private boolean canNotifyStatusChanged() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        return requestCoordinator == null || requestCoordinator.canNotifyStatusChanged(this);
    }

    private boolean isFirstReadyResource() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        return requestCoordinator == null || !requestCoordinator.isAnyResourceSet();
    }

    private void notifyLoadSuccess() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        if (requestCoordinator != null) {
            requestCoordinator.onRequestSuccess(this);
        }
    }

    private void notifyLoadFailed() {
        RequestCoordinator requestCoordinator = this.requestCoordinator;
        if (requestCoordinator != null) {
            requestCoordinator.onRequestFailed(this);
        }
    }

    public synchronized void onResourceReady(Resource<?> resource, DataSource dataSource) {
        this.stateVerifier.throwIfRecycled();
        this.loadStatus = null;
        if (resource == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected to receive a Resource<R> with an object of ");
            stringBuilder.append(this.transcodeClass);
            stringBuilder.append(" inside, but instead got null.");
            onLoadFailed(new GlideException(stringBuilder.toString()));
            return;
        }
        Object obj = resource.get();
        if (obj == null || !this.transcodeClass.isAssignableFrom(obj.getClass())) {
            releaseResource(resource);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Expected to receive an object of ");
            stringBuilder2.append(this.transcodeClass);
            stringBuilder2.append(" but instead got ");
            stringBuilder2.append(obj != null ? obj.getClass() : "");
            stringBuilder2.append("{");
            stringBuilder2.append(obj);
            stringBuilder2.append("} inside Resource{");
            stringBuilder2.append(resource);
            stringBuilder2.append("}.");
            stringBuilder2.append(obj != null ? "" : " To indicate failure return a null Resource object, rather than a Resource object containing null data.");
            onLoadFailed(new GlideException(stringBuilder2.toString()));
        } else if (canSetResource()) {
            onResourceReady(resource, obj, dataSource);
        } else {
            releaseResource(resource);
            this.status = Status.COMPLETE;
        }
    }

    /* JADX WARNING: Missing block: B:18:0x00a5, code:
            if (r10.targetListener.onResourceReady(r12, r10.model, r10.target, r13, r6) != false) goto L_0x00a9;
     */
    private synchronized void onResourceReady(com.bumptech.glide.load.engine.Resource<R> r11, R r12, com.bumptech.glide.load.DataSource r13) {
        /*
        r10 = this;
        monitor-enter(r10);
        r6 = r10.isFirstReadyResource();	 Catch:{ all -> 0x00c2 }
        r0 = com.bumptech.glide.request.SingleRequest.Status.COMPLETE;	 Catch:{ all -> 0x00c2 }
        r10.status = r0;	 Catch:{ all -> 0x00c2 }
        r10.resource = r11;	 Catch:{ all -> 0x00c2 }
        r11 = r10.glideContext;	 Catch:{ all -> 0x00c2 }
        r11 = r11.getLogLevel();	 Catch:{ all -> 0x00c2 }
        r0 = 3;
        if (r11 > r0) goto L_0x006b;
    L_0x0014:
        r11 = "Glide";
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00c2 }
        r0.<init>();	 Catch:{ all -> 0x00c2 }
        r1 = "Finished loading ";
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = r12.getClass();	 Catch:{ all -> 0x00c2 }
        r1 = r1.getSimpleName();	 Catch:{ all -> 0x00c2 }
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = " from ";
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r0.append(r13);	 Catch:{ all -> 0x00c2 }
        r1 = " for ";
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = r10.model;	 Catch:{ all -> 0x00c2 }
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = " with size [";
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = r10.width;	 Catch:{ all -> 0x00c2 }
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = "x";
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = r10.height;	 Catch:{ all -> 0x00c2 }
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = "] in ";
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = r10.startTime;	 Catch:{ all -> 0x00c2 }
        r1 = com.bumptech.glide.util.LogTime.getElapsedMillis(r1);	 Catch:{ all -> 0x00c2 }
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r1 = " ms";
        r0.append(r1);	 Catch:{ all -> 0x00c2 }
        r0 = r0.toString();	 Catch:{ all -> 0x00c2 }
        android.util.Log.d(r11, r0);	 Catch:{ all -> 0x00c2 }
    L_0x006b:
        r11 = 1;
        r10.isCallingCallbacks = r11;	 Catch:{ all -> 0x00c2 }
        r7 = 0;
        r0 = r10.requestListeners;	 Catch:{ all -> 0x00be }
        if (r0 == 0) goto L_0x0093;
    L_0x0073:
        r0 = r10.requestListeners;	 Catch:{ all -> 0x00be }
        r8 = r0.iterator();	 Catch:{ all -> 0x00be }
        r9 = 0;
    L_0x007a:
        r0 = r8.hasNext();	 Catch:{ all -> 0x00be }
        if (r0 == 0) goto L_0x0094;
    L_0x0080:
        r0 = r8.next();	 Catch:{ all -> 0x00be }
        r0 = (com.bumptech.glide.request.RequestListener) r0;	 Catch:{ all -> 0x00be }
        r2 = r10.model;	 Catch:{ all -> 0x00be }
        r3 = r10.target;	 Catch:{ all -> 0x00be }
        r1 = r12;
        r4 = r13;
        r5 = r6;
        r0 = r0.onResourceReady(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x00be }
        r9 = r9 | r0;
        goto L_0x007a;
    L_0x0093:
        r9 = 0;
    L_0x0094:
        r0 = r10.targetListener;	 Catch:{ all -> 0x00be }
        if (r0 == 0) goto L_0x00a8;
    L_0x0098:
        r0 = r10.targetListener;	 Catch:{ all -> 0x00be }
        r2 = r10.model;	 Catch:{ all -> 0x00be }
        r3 = r10.target;	 Catch:{ all -> 0x00be }
        r1 = r12;
        r4 = r13;
        r5 = r6;
        r0 = r0.onResourceReady(r1, r2, r3, r4, r5);	 Catch:{ all -> 0x00be }
        if (r0 == 0) goto L_0x00a8;
    L_0x00a7:
        goto L_0x00a9;
    L_0x00a8:
        r11 = 0;
    L_0x00a9:
        r11 = r11 | r9;
        if (r11 != 0) goto L_0x00b7;
    L_0x00ac:
        r11 = r10.animationFactory;	 Catch:{ all -> 0x00be }
        r11 = r11.build(r13, r6);	 Catch:{ all -> 0x00be }
        r13 = r10.target;	 Catch:{ all -> 0x00be }
        r13.onResourceReady(r12, r11);	 Catch:{ all -> 0x00be }
    L_0x00b7:
        r10.isCallingCallbacks = r7;	 Catch:{ all -> 0x00c2 }
        r10.notifyLoadSuccess();	 Catch:{ all -> 0x00c2 }
        monitor-exit(r10);
        return;
    L_0x00be:
        r11 = move-exception;
        r10.isCallingCallbacks = r7;	 Catch:{ all -> 0x00c2 }
        throw r11;	 Catch:{ all -> 0x00c2 }
    L_0x00c2:
        r11 = move-exception;
        monitor-exit(r10);
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.onResourceReady(com.bumptech.glide.load.engine.Resource, java.lang.Object, com.bumptech.glide.load.DataSource):void");
    }

    public synchronized void onLoadFailed(GlideException glideException) {
        onLoadFailed(glideException, 5);
    }

    private synchronized void onLoadFailed(GlideException glideException, int i) {
        this.stateVerifier.throwIfRecycled();
        glideException.setOrigin(this.requestOrigin);
        int logLevel = this.glideContext.getLogLevel();
        if (logLevel <= i) {
            String str = GLIDE_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Load failed for ");
            stringBuilder.append(this.model);
            stringBuilder.append(" with size [");
            stringBuilder.append(this.width);
            stringBuilder.append("x");
            stringBuilder.append(this.height);
            stringBuilder.append("]");
            Log.w(str, stringBuilder.toString(), glideException);
            if (logLevel <= 4) {
                glideException.logRootCauses(GLIDE_TAG);
            }
        }
        this.loadStatus = null;
        this.status = Status.FAILED;
        i = 1;
        this.isCallingCallbacks = true;
        try {
            int i2;
            if (this.requestListeners != null) {
                i2 = 0;
                for (RequestListener onLoadFailed : this.requestListeners) {
                    i2 |= onLoadFailed.onLoadFailed(glideException, this.model, this.target, isFirstReadyResource());
                }
            } else {
                i2 = 0;
            }
            if (this.targetListener == null || !this.targetListener.onLoadFailed(glideException, this.model, this.target, isFirstReadyResource())) {
                i = 0;
            }
            if ((i2 | i) == 0) {
                setErrorPlaceholder();
            }
            notifyLoadFailed();
        } finally {
            this.isCallingCallbacks = false;
        }
    }

    public synchronized boolean isEquivalentTo(Request request) {
        boolean z = false;
        if (!(request instanceof SingleRequest)) {
            return false;
        }
        SingleRequest singleRequest = (SingleRequest) request;
        synchronized (singleRequest) {
            if (this.overrideWidth == singleRequest.overrideWidth && this.overrideHeight == singleRequest.overrideHeight && Util.bothModelsNullEquivalentOrEquals(this.model, singleRequest.model) && this.transcodeClass.equals(singleRequest.transcodeClass) && this.requestOptions.equals(singleRequest.requestOptions) && this.priority == singleRequest.priority && listenerCountEquals(singleRequest)) {
                z = true;
            }
        }
        return z;
    }

    private synchronized boolean listenerCountEquals(SingleRequest<?> singleRequest) {
        boolean z;
        synchronized (singleRequest) {
            z = false;
            if ((this.requestListeners == null ? 0 : this.requestListeners.size()) == (singleRequest.requestListeners == null ? 0 : singleRequest.requestListeners.size())) {
                z = true;
            }
        }
        return z;
    }

    private void logV(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" this: ");
        stringBuilder.append(this.tag);
        Log.v(TAG, stringBuilder.toString());
    }
}
