package com.google.maps.android.clustering.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue.IdleHandler;
import android.util.SparseArray;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.logging.type.LogSeverity;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.R;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.ClusterManager.OnClusterClickListener;
import com.google.maps.android.clustering.ClusterManager.OnClusterInfoWindowClickListener;
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener;
import com.google.maps.android.clustering.ClusterManager.OnClusterItemInfoWindowClickListener;
import com.google.maps.android.geometry.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultClusterRenderer<T extends ClusterItem> implements ClusterRenderer<T> {
    private static final TimeInterpolator ANIMATION_INTERP = new DecelerateInterpolator();
    private static final int[] BUCKETS = new int[]{10, 20, 50, 100, LogSeverity.INFO_VALUE, 500, 1000};
    private static final boolean SHOULD_ANIMATE = (VERSION.SDK_INT >= 11);
    private boolean mAnimate;
    private OnClusterClickListener<T> mClickListener;
    private final ClusterManager<T> mClusterManager;
    private Map<Cluster<T>, Marker> mClusterToMarker = new HashMap();
    private Set<? extends Cluster<T>> mClusters;
    private ShapeDrawable mColoredCircleBackground;
    private final float mDensity;
    private final IconGenerator mIconGenerator;
    private SparseArray<BitmapDescriptor> mIcons = new SparseArray();
    private OnClusterInfoWindowClickListener<T> mInfoWindowClickListener;
    private OnClusterItemClickListener<T> mItemClickListener;
    private OnClusterItemInfoWindowClickListener<T> mItemInfoWindowClickListener;
    private final GoogleMap mMap;
    private MarkerCache<T> mMarkerCache = new MarkerCache();
    private Map<Marker, Cluster<T>> mMarkerToCluster = new HashMap();
    private Set<MarkerWithPosition> mMarkers = Collections.newSetFromMap(new ConcurrentHashMap());
    private int mMinClusterSize = 4;
    private final ViewModifier mViewModifier = new ViewModifier(this, null);
    private float mZoom;

    @TargetApi(12)
    private class AnimationTask extends AnimatorListenerAdapter implements AnimatorUpdateListener {
        private final LatLng from;
        private MarkerManager mMarkerManager;
        private boolean mRemoveOnComplete;
        private final Marker marker;
        private final MarkerWithPosition markerWithPosition;
        private final LatLng to;

        /* synthetic */ AnimationTask(DefaultClusterRenderer defaultClusterRenderer, MarkerWithPosition markerWithPosition, LatLng latLng, LatLng latLng2, AnonymousClass1 anonymousClass1) {
            this(markerWithPosition, latLng, latLng2);
        }

        private AnimationTask(MarkerWithPosition markerWithPosition, LatLng latLng, LatLng latLng2) {
            this.markerWithPosition = markerWithPosition;
            this.marker = markerWithPosition.marker;
            this.from = latLng;
            this.to = latLng2;
        }

        public void perform() {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            ofFloat.setInterpolator(DefaultClusterRenderer.ANIMATION_INTERP);
            ofFloat.addUpdateListener(this);
            ofFloat.addListener(this);
            ofFloat.start();
        }

        public void onAnimationEnd(Animator animator) {
            if (this.mRemoveOnComplete) {
                DefaultClusterRenderer.this.mClusterToMarker.remove((Cluster) DefaultClusterRenderer.this.mMarkerToCluster.get(this.marker));
                DefaultClusterRenderer.this.mMarkerCache.remove(this.marker);
                DefaultClusterRenderer.this.mMarkerToCluster.remove(this.marker);
                this.mMarkerManager.remove(this.marker);
            }
            this.markerWithPosition.position = this.to;
        }

        public void removeOnAnimationComplete(MarkerManager markerManager) {
            this.mMarkerManager = markerManager;
            this.mRemoveOnComplete = true;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            double animatedFraction = (double) valueAnimator.getAnimatedFraction();
            double d = ((this.to.latitude - this.from.latitude) * animatedFraction) + this.from.latitude;
            double d2 = this.to.longitude - this.from.longitude;
            if (Math.abs(d2) > 180.0d) {
                d2 -= Math.signum(d2) * 360.0d;
            }
            this.marker.setPosition(new LatLng(d, (d2 * animatedFraction) + this.from.longitude));
        }
    }

    private class CreateMarkerTask {
        private final LatLng animateFrom;
        private final Cluster<T> cluster;
        private final Set<MarkerWithPosition> newMarkers;

        public CreateMarkerTask(Cluster<T> cluster, Set<MarkerWithPosition> set, LatLng latLng) {
            this.cluster = cluster;
            this.newMarkers = set;
            this.animateFrom = latLng;
        }

        private void perform(MarkerModifier markerModifier) {
            Object markerWithPosition;
            if (DefaultClusterRenderer.this.shouldRenderAsCluster(this.cluster)) {
                Marker marker = (Marker) DefaultClusterRenderer.this.mClusterToMarker.get(this.cluster);
                if (marker == null) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = this.animateFrom;
                    if (latLng == null) {
                        latLng = this.cluster.getPosition();
                    }
                    markerOptions = markerOptions.position(latLng);
                    DefaultClusterRenderer.this.onBeforeClusterRendered(this.cluster, markerOptions);
                    marker = DefaultClusterRenderer.this.mClusterManager.getClusterMarkerCollection().addMarker(markerOptions);
                    DefaultClusterRenderer.this.mMarkerToCluster.put(marker, this.cluster);
                    DefaultClusterRenderer.this.mClusterToMarker.put(this.cluster, marker);
                    markerWithPosition = new MarkerWithPosition(marker, null);
                    LatLng latLng2 = this.animateFrom;
                    if (latLng2 != null) {
                        markerModifier.animate(markerWithPosition, latLng2, this.cluster.getPosition());
                    }
                } else {
                    markerWithPosition = new MarkerWithPosition(marker, null);
                }
                DefaultClusterRenderer.this.onClusterRendered(this.cluster, marker);
                this.newMarkers.add(markerWithPosition);
                return;
            }
            for (Object markerWithPosition2 : this.cluster.getItems()) {
                Object markerWithPosition3;
                Marker marker2 = DefaultClusterRenderer.this.mMarkerCache.get(markerWithPosition2);
                if (marker2 == null) {
                    MarkerOptions markerOptions2 = new MarkerOptions();
                    LatLng latLng3 = this.animateFrom;
                    if (latLng3 != null) {
                        markerOptions2.position(latLng3);
                    } else {
                        markerOptions2.position(markerWithPosition2.getPosition());
                    }
                    if (markerWithPosition2.getTitle() != null && markerWithPosition2.getSnippet() != null) {
                        markerOptions2.title(markerWithPosition2.getTitle());
                        markerOptions2.snippet(markerWithPosition2.getSnippet());
                    } else if (markerWithPosition2.getSnippet() != null) {
                        markerOptions2.title(markerWithPosition2.getSnippet());
                    } else if (markerWithPosition2.getTitle() != null) {
                        markerOptions2.title(markerWithPosition2.getTitle());
                    }
                    DefaultClusterRenderer.this.onBeforeClusterItemRendered(markerWithPosition2, markerOptions2);
                    marker2 = DefaultClusterRenderer.this.mClusterManager.getMarkerCollection().addMarker(markerOptions2);
                    markerWithPosition3 = new MarkerWithPosition(marker2, null);
                    DefaultClusterRenderer.this.mMarkerCache.put(markerWithPosition2, marker2);
                    LatLng latLng4 = this.animateFrom;
                    if (latLng4 != null) {
                        markerModifier.animate(markerWithPosition3, latLng4, markerWithPosition2.getPosition());
                    }
                } else {
                    markerWithPosition3 = new MarkerWithPosition(marker2, null);
                }
                DefaultClusterRenderer.this.onClusterItemRendered(markerWithPosition2, marker2);
                this.newMarkers.add(markerWithPosition3);
            }
        }
    }

    private static class MarkerCache<T> {
        private Map<T, Marker> mCache;
        private Map<Marker, T> mCacheReverse;

        private MarkerCache() {
            this.mCache = new HashMap();
            this.mCacheReverse = new HashMap();
        }

        /* synthetic */ MarkerCache(AnonymousClass1 anonymousClass1) {
            this();
        }

        public Marker get(T t) {
            return (Marker) this.mCache.get(t);
        }

        public T get(Marker marker) {
            return this.mCacheReverse.get(marker);
        }

        public void put(T t, Marker marker) {
            this.mCache.put(t, marker);
            this.mCacheReverse.put(marker, t);
        }

        public void remove(Marker marker) {
            Object obj = this.mCacheReverse.get(marker);
            this.mCacheReverse.remove(marker);
            this.mCache.remove(obj);
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class MarkerModifier extends Handler implements IdleHandler {
        private static final int BLANK = 0;
        private final Condition busyCondition;
        private final Lock lock;
        private Queue<AnimationTask> mAnimationTasks;
        private Queue<CreateMarkerTask> mCreateMarkerTasks;
        private boolean mListenerAdded;
        private Queue<CreateMarkerTask> mOnScreenCreateMarkerTasks;
        private Queue<Marker> mOnScreenRemoveMarkerTasks;
        private Queue<Marker> mRemoveMarkerTasks;

        /* synthetic */ MarkerModifier(DefaultClusterRenderer defaultClusterRenderer, AnonymousClass1 anonymousClass1) {
            this();
        }

        private MarkerModifier() {
            super(Looper.getMainLooper());
            this.lock = new ReentrantLock();
            this.busyCondition = this.lock.newCondition();
            this.mCreateMarkerTasks = new LinkedList();
            this.mOnScreenCreateMarkerTasks = new LinkedList();
            this.mRemoveMarkerTasks = new LinkedList();
            this.mOnScreenRemoveMarkerTasks = new LinkedList();
            this.mAnimationTasks = new LinkedList();
        }

        public void add(boolean z, CreateMarkerTask createMarkerTask) {
            this.lock.lock();
            sendEmptyMessage(0);
            if (z) {
                this.mOnScreenCreateMarkerTasks.add(createMarkerTask);
            } else {
                this.mCreateMarkerTasks.add(createMarkerTask);
            }
            this.lock.unlock();
        }

        public void remove(boolean z, Marker marker) {
            this.lock.lock();
            sendEmptyMessage(0);
            if (z) {
                this.mOnScreenRemoveMarkerTasks.add(marker);
            } else {
                this.mRemoveMarkerTasks.add(marker);
            }
            this.lock.unlock();
        }

        public void animate(MarkerWithPosition markerWithPosition, LatLng latLng, LatLng latLng2) {
            this.lock.lock();
            this.mAnimationTasks.add(new AnimationTask(DefaultClusterRenderer.this, markerWithPosition, latLng, latLng2, null));
            this.lock.unlock();
        }

        @TargetApi(11)
        public void animateThenRemove(MarkerWithPosition markerWithPosition, LatLng latLng, LatLng latLng2) {
            this.lock.lock();
            AnimationTask animationTask = new AnimationTask(DefaultClusterRenderer.this, markerWithPosition, latLng, latLng2, null);
            animationTask.removeOnAnimationComplete(DefaultClusterRenderer.this.mClusterManager.getMarkerManager());
            this.mAnimationTasks.add(animationTask);
            this.lock.unlock();
        }

        public void handleMessage(Message message) {
            if (!this.mListenerAdded) {
                Looper.myQueue().addIdleHandler(this);
                this.mListenerAdded = true;
            }
            removeMessages(0);
            this.lock.lock();
            int i = 0;
            while (i < 10) {
                try {
                    performNextTask();
                    i++;
                } catch (Throwable th) {
                    this.lock.unlock();
                }
            }
            if (isBusy()) {
                sendEmptyMessageDelayed(0, 10);
            } else {
                this.mListenerAdded = false;
                Looper.myQueue().removeIdleHandler(this);
                this.busyCondition.signalAll();
            }
            this.lock.unlock();
        }

        @TargetApi(11)
        private void performNextTask() {
            if (!this.mOnScreenRemoveMarkerTasks.isEmpty()) {
                removeMarker((Marker) this.mOnScreenRemoveMarkerTasks.poll());
            } else if (!this.mAnimationTasks.isEmpty()) {
                ((AnimationTask) this.mAnimationTasks.poll()).perform();
            } else if (!this.mOnScreenCreateMarkerTasks.isEmpty()) {
                ((CreateMarkerTask) this.mOnScreenCreateMarkerTasks.poll()).perform(this);
            } else if (!this.mCreateMarkerTasks.isEmpty()) {
                ((CreateMarkerTask) this.mCreateMarkerTasks.poll()).perform(this);
            } else if (!this.mRemoveMarkerTasks.isEmpty()) {
                removeMarker((Marker) this.mRemoveMarkerTasks.poll());
            }
        }

        private void removeMarker(Marker marker) {
            DefaultClusterRenderer.this.mClusterToMarker.remove((Cluster) DefaultClusterRenderer.this.mMarkerToCluster.get(marker));
            DefaultClusterRenderer.this.mMarkerCache.remove(marker);
            DefaultClusterRenderer.this.mMarkerToCluster.remove(marker);
            DefaultClusterRenderer.this.mClusterManager.getMarkerManager().remove(marker);
        }

        public boolean isBusy() {
            try {
                this.lock.lock();
                boolean z = (this.mCreateMarkerTasks.isEmpty() && this.mOnScreenCreateMarkerTasks.isEmpty() && this.mOnScreenRemoveMarkerTasks.isEmpty() && this.mRemoveMarkerTasks.isEmpty() && this.mAnimationTasks.isEmpty()) ? false : true;
                this.lock.unlock();
                return z;
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }

        public void waitUntilFree() {
            while (isBusy()) {
                sendEmptyMessage(0);
                this.lock.lock();
                try {
                    if (isBusy()) {
                        this.busyCondition.await();
                    }
                    this.lock.unlock();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                } catch (Throwable th) {
                    this.lock.unlock();
                }
            }
        }

        public boolean queueIdle() {
            sendEmptyMessage(0);
            return true;
        }
    }

    private static class MarkerWithPosition {
        private final Marker marker;
        private LatLng position;

        /* synthetic */ MarkerWithPosition(Marker marker, AnonymousClass1 anonymousClass1) {
            this(marker);
        }

        private MarkerWithPosition(Marker marker) {
            this.marker = marker;
            this.position = marker.getPosition();
        }

        public boolean equals(Object obj) {
            return obj instanceof MarkerWithPosition ? this.marker.equals(((MarkerWithPosition) obj).marker) : false;
        }

        public int hashCode() {
            return this.marker.hashCode();
        }
    }

    private class RenderTask implements Runnable {
        final Set<? extends Cluster<T>> clusters;
        private Runnable mCallback;
        private float mMapZoom;
        private Projection mProjection;
        private SphericalMercatorProjection mSphericalMercatorProjection;

        /* synthetic */ RenderTask(DefaultClusterRenderer defaultClusterRenderer, Set set, AnonymousClass1 anonymousClass1) {
            this(set);
        }

        private RenderTask(Set<? extends Cluster<T>> set) {
            this.clusters = set;
        }

        public void setCallback(Runnable runnable) {
            this.mCallback = runnable;
        }

        public void setProjection(Projection projection) {
            this.mProjection = projection;
        }

        public void setMapZoom(float f) {
            this.mMapZoom = f;
            this.mSphericalMercatorProjection = new SphericalMercatorProjection(Math.pow(2.0d, (double) Math.min(f, DefaultClusterRenderer.this.mZoom)) * 256.0d);
        }

        @SuppressLint({"NewApi"})
        public void run() {
            if (this.clusters.equals(DefaultClusterRenderer.this.mClusters)) {
                this.mCallback.run();
                return;
            }
            List list;
            List list2 = null;
            MarkerModifier markerModifier = new MarkerModifier(DefaultClusterRenderer.this, null);
            float f = this.mMapZoom;
            Object obj = f > DefaultClusterRenderer.this.mZoom ? 1 : null;
            float access$1000 = f - DefaultClusterRenderer.this.mZoom;
            Set<MarkerWithPosition> access$1300 = DefaultClusterRenderer.this.mMarkers;
            LatLngBounds latLngBounds = this.mProjection.getVisibleRegion().latLngBounds;
            if (DefaultClusterRenderer.this.mClusters == null || !DefaultClusterRenderer.SHOULD_ANIMATE) {
                list = null;
            } else {
                list = new ArrayList();
                for (Cluster cluster : DefaultClusterRenderer.this.mClusters) {
                    if (DefaultClusterRenderer.this.shouldRenderAsCluster(cluster) && latLngBounds.contains(cluster.getPosition())) {
                        list.add(this.mSphericalMercatorProjection.toPoint(cluster.getPosition()));
                    }
                }
            }
            Object newSetFromMap = Collections.newSetFromMap(new ConcurrentHashMap());
            for (Cluster cluster2 : this.clusters) {
                boolean contains = latLngBounds.contains(cluster2.getPosition());
                if (obj != null && contains && DefaultClusterRenderer.SHOULD_ANIMATE) {
                    Point access$1500 = DefaultClusterRenderer.findClosestCluster(list, this.mSphericalMercatorProjection.toPoint(cluster2.getPosition()));
                    if (access$1500 == null || !DefaultClusterRenderer.this.mAnimate) {
                        markerModifier.add(true, new CreateMarkerTask(cluster2, newSetFromMap, null));
                    } else {
                        markerModifier.add(true, new CreateMarkerTask(cluster2, newSetFromMap, this.mSphericalMercatorProjection.toLatLng(access$1500)));
                    }
                } else {
                    markerModifier.add(contains, new CreateMarkerTask(cluster2, newSetFromMap, null));
                }
            }
            markerModifier.waitUntilFree();
            access$1300.removeAll(newSetFromMap);
            if (DefaultClusterRenderer.SHOULD_ANIMATE) {
                list2 = new ArrayList();
                for (Cluster cluster3 : this.clusters) {
                    if (DefaultClusterRenderer.this.shouldRenderAsCluster(cluster3) && latLngBounds.contains(cluster3.getPosition())) {
                        list2.add(this.mSphericalMercatorProjection.toPoint(cluster3.getPosition()));
                    }
                }
            }
            for (MarkerWithPosition markerWithPosition : access$1300) {
                boolean contains2 = latLngBounds.contains(markerWithPosition.position);
                if (obj == null && access$1000 > -3.0f && contains2 && DefaultClusterRenderer.SHOULD_ANIMATE) {
                    Point access$15002 = DefaultClusterRenderer.findClosestCluster(list2, this.mSphericalMercatorProjection.toPoint(markerWithPosition.position));
                    if (access$15002 == null || !DefaultClusterRenderer.this.mAnimate) {
                        markerModifier.remove(true, markerWithPosition.marker);
                    } else {
                        markerModifier.animateThenRemove(markerWithPosition, markerWithPosition.position, this.mSphericalMercatorProjection.toLatLng(access$15002));
                    }
                } else {
                    markerModifier.remove(contains2, markerWithPosition.marker);
                }
            }
            markerModifier.waitUntilFree();
            DefaultClusterRenderer.this.mMarkers = newSetFromMap;
            DefaultClusterRenderer.this.mClusters = this.clusters;
            DefaultClusterRenderer.this.mZoom = f;
            this.mCallback.run();
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class ViewModifier extends Handler {
        private static final int RUN_TASK = 0;
        private static final int TASK_FINISHED = 1;
        private RenderTask mNextClusters;
        private boolean mViewModificationInProgress;

        private ViewModifier() {
            this.mViewModificationInProgress = false;
            this.mNextClusters = null;
        }

        /* synthetic */ ViewModifier(DefaultClusterRenderer defaultClusterRenderer, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void handleMessage(Message message) {
            if (message.what == 1) {
                this.mViewModificationInProgress = false;
                if (this.mNextClusters != null) {
                    sendEmptyMessage(0);
                }
                return;
            }
            removeMessages(0);
            if (!this.mViewModificationInProgress && this.mNextClusters != null) {
                Runnable runnable;
                Projection projection = DefaultClusterRenderer.this.mMap.getProjection();
                synchronized (this) {
                    runnable = this.mNextClusters;
                    this.mNextClusters = null;
                    this.mViewModificationInProgress = true;
                }
                runnable.setCallback(new Runnable() {
                    public void run() {
                        ViewModifier.this.sendEmptyMessage(1);
                    }
                });
                runnable.setProjection(projection);
                runnable.setMapZoom(DefaultClusterRenderer.this.mMap.getCameraPosition().zoom);
                new Thread(runnable).start();
            }
        }

        public void queue(Set<? extends Cluster<T>> set) {
            synchronized (this) {
                this.mNextClusters = new RenderTask(DefaultClusterRenderer.this, set, null);
            }
            sendEmptyMessage(0);
        }
    }

    protected void onBeforeClusterItemRendered(T t, MarkerOptions markerOptions) {
    }

    protected void onClusterItemRendered(T t, Marker marker) {
    }

    protected void onClusterRendered(Cluster<T> cluster, Marker marker) {
    }

    public DefaultClusterRenderer(Context context, GoogleMap googleMap, ClusterManager<T> clusterManager) {
        this.mMap = googleMap;
        this.mAnimate = true;
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mIconGenerator = new IconGenerator(context);
        this.mIconGenerator.setContentView(makeSquareTextView(context));
        this.mIconGenerator.setTextAppearance(R.style.amu_ClusterIcon_TextAppearance);
        this.mIconGenerator.setBackground(makeClusterBackground());
        this.mClusterManager = clusterManager;
    }

    public void onAdd() {
        this.mClusterManager.getMarkerCollection().setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                return DefaultClusterRenderer.this.mItemClickListener != null && DefaultClusterRenderer.this.mItemClickListener.onClusterItemClick((ClusterItem) DefaultClusterRenderer.this.mMarkerCache.get(marker));
            }
        });
        this.mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            public void onInfoWindowClick(Marker marker) {
                if (DefaultClusterRenderer.this.mItemInfoWindowClickListener != null) {
                    DefaultClusterRenderer.this.mItemInfoWindowClickListener.onClusterItemInfoWindowClick((ClusterItem) DefaultClusterRenderer.this.mMarkerCache.get(marker));
                }
            }
        });
        this.mClusterManager.getClusterMarkerCollection().setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                return DefaultClusterRenderer.this.mClickListener != null && DefaultClusterRenderer.this.mClickListener.onClusterClick((Cluster) DefaultClusterRenderer.this.mMarkerToCluster.get(marker));
            }
        });
        this.mClusterManager.getClusterMarkerCollection().setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            public void onInfoWindowClick(Marker marker) {
                if (DefaultClusterRenderer.this.mInfoWindowClickListener != null) {
                    DefaultClusterRenderer.this.mInfoWindowClickListener.onClusterInfoWindowClick((Cluster) DefaultClusterRenderer.this.mMarkerToCluster.get(marker));
                }
            }
        });
    }

    public void onRemove() {
        this.mClusterManager.getMarkerCollection().setOnMarkerClickListener(null);
        this.mClusterManager.getMarkerCollection().setOnInfoWindowClickListener(null);
        this.mClusterManager.getClusterMarkerCollection().setOnMarkerClickListener(null);
        this.mClusterManager.getClusterMarkerCollection().setOnInfoWindowClickListener(null);
    }

    private LayerDrawable makeClusterBackground() {
        this.mColoredCircleBackground = new ShapeDrawable(new OvalShape());
        new ShapeDrawable(new OvalShape()).getPaint().setColor(-2130706433);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{r0, this.mColoredCircleBackground});
        int i = (int) (this.mDensity * 3.0f);
        layerDrawable.setLayerInset(1, i, i, i, i);
        return layerDrawable;
    }

    private SquareTextView makeSquareTextView(Context context) {
        SquareTextView squareTextView = new SquareTextView(context);
        squareTextView.setLayoutParams(new LayoutParams(-2, -2));
        squareTextView.setId(R.id.amu_text);
        int i = (int) (this.mDensity * 12.0f);
        squareTextView.setPadding(i, i, i, i);
        return squareTextView;
    }

    protected int getColor(int i) {
        float min = 300.0f - Math.min((float) i, 300.0f);
        return Color.HSVToColor(new float[]{((min * min) / 90000.0f) * 220.0f, 1.0f, 0.6f});
    }

    protected String getClusterText(int i) {
        if (i < BUCKETS[0]) {
            return String.valueOf(i);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(i));
        stringBuilder.append("+");
        return stringBuilder.toString();
    }

    protected int getBucket(Cluster<T> cluster) {
        int size = cluster.getSize();
        int i = 0;
        if (size <= BUCKETS[0]) {
            return size;
        }
        while (true) {
            int[] iArr = BUCKETS;
            if (i >= iArr.length - 1) {
                return iArr[iArr.length - 1];
            }
            int i2 = i + 1;
            if (size < iArr[i2]) {
                return iArr[i];
            }
            i = i2;
        }
    }

    public int getMinClusterSize() {
        return this.mMinClusterSize;
    }

    public void setMinClusterSize(int i) {
        this.mMinClusterSize = i;
    }

    protected boolean shouldRenderAsCluster(Cluster<T> cluster) {
        return cluster.getSize() > this.mMinClusterSize;
    }

    public void onClustersChanged(Set<? extends Cluster<T>> set) {
        this.mViewModifier.queue(set);
    }

    public void setOnClusterClickListener(OnClusterClickListener<T> onClusterClickListener) {
        this.mClickListener = onClusterClickListener;
    }

    public void setOnClusterInfoWindowClickListener(OnClusterInfoWindowClickListener<T> onClusterInfoWindowClickListener) {
        this.mInfoWindowClickListener = onClusterInfoWindowClickListener;
    }

    public void setOnClusterItemClickListener(OnClusterItemClickListener<T> onClusterItemClickListener) {
        this.mItemClickListener = onClusterItemClickListener;
    }

    public void setOnClusterItemInfoWindowClickListener(OnClusterItemInfoWindowClickListener<T> onClusterItemInfoWindowClickListener) {
        this.mItemInfoWindowClickListener = onClusterItemInfoWindowClickListener;
    }

    public void setAnimation(boolean z) {
        this.mAnimate = z;
    }

    private static double distanceSquared(Point point, Point point2) {
        return ((point.x - point2.x) * (point.x - point2.x)) + ((point.y - point2.y) * (point.y - point2.y));
    }

    private static Point findClosestCluster(List<Point> list, Point point) {
        Point point2 = null;
        if (!(list == null || list.isEmpty())) {
            double d = 10000.0d;
            for (Point point3 : list) {
                double distanceSquared = distanceSquared(point3, point);
                if (distanceSquared < d) {
                    point2 = point3;
                    d = distanceSquared;
                }
            }
        }
        return point2;
    }

    protected void onBeforeClusterRendered(Cluster<T> cluster, MarkerOptions markerOptions) {
        int bucket = getBucket(cluster);
        BitmapDescriptor bitmapDescriptor = (BitmapDescriptor) this.mIcons.get(bucket);
        if (bitmapDescriptor == null) {
            this.mColoredCircleBackground.getPaint().setColor(getColor(bucket));
            bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(this.mIconGenerator.makeIcon(getClusterText(bucket)));
            this.mIcons.put(bucket, bitmapDescriptor);
        }
        markerOptions.icon(bitmapDescriptor);
    }

    public Marker getMarker(T t) {
        return this.mMarkerCache.get((Object) t);
    }

    public T getClusterItem(Marker marker) {
        return (ClusterItem) this.mMarkerCache.get(marker);
    }

    public Marker getMarker(Cluster<T> cluster) {
        return (Marker) this.mClusterToMarker.get(cluster);
    }

    public Cluster<T> getCluster(Marker marker) {
        return (Cluster) this.mMarkerToCluster.get(marker);
    }
}
