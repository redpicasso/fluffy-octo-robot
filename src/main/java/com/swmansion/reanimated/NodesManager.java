package com.swmansion.reanimated;

import android.util.SparseArray;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.modules.core.ReactChoreographer.CallbackType;
import com.facebook.react.uimanager.GuardedFrameCallback;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIManagerModule.CustomEventNamesResolver;
import com.facebook.react.uimanager.UIManagerReanimatedHelper;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcherListener;
import com.facebook.react.views.scroll.ReactScrollViewHelper;
import com.swmansion.reanimated.nodes.AlwaysNode;
import com.swmansion.reanimated.nodes.BezierNode;
import com.swmansion.reanimated.nodes.BlockNode;
import com.swmansion.reanimated.nodes.ClockNode;
import com.swmansion.reanimated.nodes.ClockOpNode.ClockStartNode;
import com.swmansion.reanimated.nodes.ClockOpNode.ClockStopNode;
import com.swmansion.reanimated.nodes.ClockOpNode.ClockTestNode;
import com.swmansion.reanimated.nodes.ConcatNode;
import com.swmansion.reanimated.nodes.CondNode;
import com.swmansion.reanimated.nodes.DebugNode;
import com.swmansion.reanimated.nodes.EventNode;
import com.swmansion.reanimated.nodes.JSCallNode;
import com.swmansion.reanimated.nodes.Node;
import com.swmansion.reanimated.nodes.NoopNode;
import com.swmansion.reanimated.nodes.OperatorNode;
import com.swmansion.reanimated.nodes.PropsNode;
import com.swmansion.reanimated.nodes.SetNode;
import com.swmansion.reanimated.nodes.StyleNode;
import com.swmansion.reanimated.nodes.TransformNode;
import com.swmansion.reanimated.nodes.ValueNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class NodesManager implements EventDispatcherListener {
    private static final Double ZERO = Double.valueOf(0.0d);
    public double currentFrameTimeMs;
    private final SparseArray<Node> mAnimatedNodes = new SparseArray();
    private final AtomicBoolean mCallbackPosted = new AtomicBoolean();
    private final GuardedFrameCallback mChoreographerCallback;
    private final ReactContext mContext;
    private final CustomEventNamesResolver mCustomEventNamesResolver;
    private final RCTDeviceEventEmitter mEventEmitter;
    private final Map<String, EventNode> mEventMapping = new HashMap();
    private ConcurrentLinkedQueue<Event> mEventQueue = new ConcurrentLinkedQueue();
    private List<OnAnimationFrame> mFrameCallbacks = new ArrayList();
    private final NoopNode mNoopNode;
    private Queue<NativeUpdateOperation> mOperationsInBatch = new LinkedList();
    private final ReactChoreographer mReactChoreographer;
    private final UIImplementation mUIImplementation;
    private final UIManagerModule mUIManager;
    private boolean mWantRunUpdates;
    public Set<String> nativeProps = Collections.emptySet();
    public Set<String> uiProps = Collections.emptySet();
    public final UpdateContext updateContext;

    private final class NativeUpdateOperation {
        public WritableMap mNativeProps;
        public int mViewTag;

        public NativeUpdateOperation(int i, WritableMap writableMap) {
            this.mViewTag = i;
            this.mNativeProps = writableMap;
        }
    }

    public interface OnAnimationFrame {
        void onAnimationFrame();
    }

    public NodesManager(ReactContext reactContext) {
        this.mContext = reactContext;
        this.mUIManager = (UIManagerModule) reactContext.getNativeModule(UIManagerModule.class);
        this.updateContext = new UpdateContext();
        this.mUIImplementation = this.mUIManager.getUIImplementation();
        this.mCustomEventNamesResolver = this.mUIManager.getDirectEventNamesResolver();
        this.mUIManager.getEventDispatcher().addListener(this);
        this.mEventEmitter = (RCTDeviceEventEmitter) reactContext.getJSModule(RCTDeviceEventEmitter.class);
        this.mReactChoreographer = ReactChoreographer.getInstance();
        this.mChoreographerCallback = new GuardedFrameCallback(reactContext) {
            protected void doFrameGuarded(long j) {
                NodesManager.this.onAnimationFrame(j);
            }
        };
        this.mNoopNode = new NoopNode(this);
    }

    public void onHostPause() {
        if (this.mCallbackPosted.get()) {
            stopUpdatingOnAnimationFrame();
            this.mCallbackPosted.set(true);
        }
    }

    public void onHostResume() {
        if (this.mCallbackPosted.getAndSet(false)) {
            startUpdatingOnAnimationFrame();
        }
    }

    private void startUpdatingOnAnimationFrame() {
        if (!this.mCallbackPosted.getAndSet(true)) {
            this.mReactChoreographer.postFrameCallback(CallbackType.NATIVE_ANIMATED_MODULE, this.mChoreographerCallback);
        }
    }

    private void stopUpdatingOnAnimationFrame() {
        if (this.mCallbackPosted.getAndSet(false)) {
            this.mReactChoreographer.removeFrameCallback(CallbackType.NATIVE_ANIMATED_MODULE, this.mChoreographerCallback);
        }
    }

    private void onAnimationFrame(long j) {
        this.currentFrameTimeMs = ((double) j) / 1000000.0d;
        while (!this.mEventQueue.isEmpty()) {
            handleEvent((Event) this.mEventQueue.poll());
        }
        if (!this.mFrameCallbacks.isEmpty()) {
            List list = this.mFrameCallbacks;
            this.mFrameCallbacks = new ArrayList(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ((OnAnimationFrame) list.get(i)).onAnimationFrame();
            }
        }
        if (this.mWantRunUpdates) {
            Node.runUpdates(this.updateContext);
        }
        if (!this.mOperationsInBatch.isEmpty()) {
            final Queue queue = this.mOperationsInBatch;
            this.mOperationsInBatch = new LinkedList();
            ReactContext reactContext = this.mContext;
            reactContext.runOnNativeModulesQueueThread(new GuardedRunnable(reactContext) {
                public void runGuarded() {
                    boolean isOperationQueueEmpty = UIManagerReanimatedHelper.isOperationQueueEmpty(NodesManager.this.mUIImplementation);
                    while (!queue.isEmpty()) {
                        NativeUpdateOperation nativeUpdateOperation = (NativeUpdateOperation) queue.remove();
                        ReactShadowNode resolveShadowNode = NodesManager.this.mUIImplementation.resolveShadowNode(nativeUpdateOperation.mViewTag);
                        if (resolveShadowNode != null) {
                            NodesManager.this.mUIManager.updateView(nativeUpdateOperation.mViewTag, resolveShadowNode.getViewClass(), nativeUpdateOperation.mNativeProps);
                        }
                    }
                    if (isOperationQueueEmpty) {
                        NodesManager.this.mUIImplementation.dispatchViewUpdates(-1);
                    }
                }
            });
        }
        this.mCallbackPosted.set(false);
        this.mWantRunUpdates = false;
        if (!this.mFrameCallbacks.isEmpty() || !this.mEventQueue.isEmpty()) {
            startUpdatingOnAnimationFrame();
        }
    }

    public Object getNodeValue(int i) {
        Node node = (Node) this.mAnimatedNodes.get(i);
        if (node != null) {
            return node.value();
        }
        return ZERO;
    }

    public <T extends Node> T findNodeById(int i, Class<T> cls) {
        Node node = (Node) this.mAnimatedNodes.get(i);
        if (node == null) {
            if (cls == Node.class || cls == ValueNode.class) {
                return this.mNoopNode;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested node with id ");
            stringBuilder.append(i);
            stringBuilder.append(" of type ");
            stringBuilder.append(cls);
            stringBuilder.append(" cannot be found");
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (cls.isInstance(node)) {
            return node;
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Node with id ");
            stringBuilder2.append(i);
            stringBuilder2.append(" is of incompatible type ");
            stringBuilder2.append(node.getClass());
            stringBuilder2.append(", requested type was ");
            stringBuilder2.append(cls);
            throw new IllegalArgumentException(stringBuilder2.toString());
        }
    }

    public void createNode(int i, ReadableMap readableMap) {
        if (this.mAnimatedNodes.get(i) == null) {
            Object propsNode;
            String string = readableMap.getString("type");
            if ("props".equals(string)) {
                propsNode = new PropsNode(i, readableMap, this, this.mUIImplementation);
            } else if ("style".equals(string)) {
                propsNode = new StyleNode(i, readableMap, this);
            } else if ("transform".equals(string)) {
                propsNode = new TransformNode(i, readableMap, this);
            } else if ("value".equals(string)) {
                propsNode = new ValueNode(i, readableMap, this);
            } else if ("block".equals(string)) {
                propsNode = new BlockNode(i, readableMap, this);
            } else if ("cond".equals(string)) {
                propsNode = new CondNode(i, readableMap, this);
            } else if ("op".equals(string)) {
                propsNode = new OperatorNode(i, readableMap, this);
            } else if ("set".equals(string)) {
                propsNode = new SetNode(i, readableMap, this);
            } else if ("debug".equals(string)) {
                propsNode = new DebugNode(i, readableMap, this);
            } else if ("clock".equals(string)) {
                propsNode = new ClockNode(i, readableMap, this);
            } else if ("clockStart".equals(string)) {
                propsNode = new ClockStartNode(i, readableMap, this);
            } else if ("clockStop".equals(string)) {
                propsNode = new ClockStopNode(i, readableMap, this);
            } else if ("clockTest".equals(string)) {
                propsNode = new ClockTestNode(i, readableMap, this);
            } else if (NotificationCompat.CATEGORY_CALL.equals(string)) {
                propsNode = new JSCallNode(i, readableMap, this);
            } else if ("bezier".equals(string)) {
                propsNode = new BezierNode(i, readableMap, this);
            } else if (NotificationCompat.CATEGORY_EVENT.equals(string)) {
                propsNode = new EventNode(i, readableMap, this);
            } else if (ReactScrollViewHelper.OVER_SCROLL_ALWAYS.equals(string)) {
                propsNode = new AlwaysNode(i, readableMap, this);
            } else if ("concat".equals(string)) {
                propsNode = new ConcatNode(i, readableMap, this);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported node type: ");
                stringBuilder.append(string);
                throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
            }
            this.mAnimatedNodes.put(i, propsNode);
            return;
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Animated node with ID ");
        stringBuilder2.append(i);
        stringBuilder2.append(" already exists");
        throw new JSApplicationIllegalArgumentException(stringBuilder2.toString());
    }

    public void dropNode(int i) {
        this.mAnimatedNodes.remove(i);
    }

    public void connectNodes(int i, int i2) {
        Node node = (Node) this.mAnimatedNodes.get(i);
        String str = " does not exists";
        String str2 = "Animated node with ID ";
        StringBuilder stringBuilder;
        if (node != null) {
            Node node2 = (Node) this.mAnimatedNodes.get(i2);
            if (node2 != null) {
                node.addChild(node2);
                return;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(i2);
            stringBuilder.append(str);
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(i);
        stringBuilder.append(str);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    }

    public void disconnectNodes(int i, int i2) {
        Node node = (Node) this.mAnimatedNodes.get(i);
        String str = " does not exists";
        String str2 = "Animated node with ID ";
        StringBuilder stringBuilder;
        if (node != null) {
            Node node2 = (Node) this.mAnimatedNodes.get(i2);
            if (node2 != null) {
                node.removeChild(node2);
                return;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(i2);
            stringBuilder.append(str);
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(i);
        stringBuilder.append(str);
        throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    }

    public void connectNodeToView(int i, int i2) {
        Node node = (Node) this.mAnimatedNodes.get(i);
        if (node == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Animated node with ID ");
            stringBuilder.append(i);
            stringBuilder.append(" does not exists");
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        } else if (node instanceof PropsNode) {
            ((PropsNode) node).connectToView(i2);
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Animated node connected to view should beof type ");
            stringBuilder2.append(PropsNode.class.getName());
            throw new JSApplicationIllegalArgumentException(stringBuilder2.toString());
        }
    }

    public void disconnectNodeFromView(int i, int i2) {
        Node node = (Node) this.mAnimatedNodes.get(i);
        if (node == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Animated node with ID ");
            stringBuilder.append(i);
            stringBuilder.append(" does not exists");
            throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        } else if (node instanceof PropsNode) {
            ((PropsNode) node).disconnectFromView(i2);
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Animated node connected to view should beof type ");
            stringBuilder2.append(PropsNode.class.getName());
            throw new JSApplicationIllegalArgumentException(stringBuilder2.toString());
        }
    }

    public void enqueueUpdateViewOnNativeThread(int i, WritableMap writableMap) {
        this.mOperationsInBatch.add(new NativeUpdateOperation(i, writableMap));
    }

    public void attachEvent(int i, String str, int i2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append(str);
        String stringBuilder2 = stringBuilder.toString();
        EventNode eventNode = (EventNode) this.mAnimatedNodes.get(i2);
        if (eventNode == null) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Event node ");
            stringBuilder3.append(i2);
            stringBuilder3.append(" does not exists");
            throw new JSApplicationIllegalArgumentException(stringBuilder3.toString());
        } else if (this.mEventMapping.containsKey(stringBuilder2)) {
            throw new JSApplicationIllegalArgumentException("Event handler already set for the given view and event type");
        } else {
            this.mEventMapping.put(stringBuilder2, eventNode);
        }
    }

    public void detachEvent(int i, String str, int i2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append(str);
        this.mEventMapping.remove(stringBuilder.toString());
    }

    public void configureProps(Set<String> set, Set<String> set2) {
        this.nativeProps = set;
        this.uiProps = set2;
    }

    public void getValue(int i, Callback callback) {
        callback.invoke(((Node) this.mAnimatedNodes.get(i)).value());
    }

    public void postRunUpdatesAfterAnimation() {
        this.mWantRunUpdates = true;
        startUpdatingOnAnimationFrame();
    }

    public void postOnAnimation(OnAnimationFrame onAnimationFrame) {
        this.mFrameCallbacks.add(onAnimationFrame);
        startUpdatingOnAnimationFrame();
    }

    public void onEventDispatch(Event event) {
        if (UiThreadUtil.isOnUiThread()) {
            handleEvent(event);
            return;
        }
        this.mEventQueue.offer(event);
        startUpdatingOnAnimationFrame();
    }

    private void handleEvent(Event event) {
        if (!this.mEventMapping.isEmpty()) {
            String resolveCustomEventName = this.mCustomEventNamesResolver.resolveCustomEventName(event.getEventName());
            int viewTag = event.getViewTag();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(viewTag);
            stringBuilder.append(resolveCustomEventName);
            EventNode eventNode = (EventNode) this.mEventMapping.get(stringBuilder.toString());
            if (eventNode != null) {
                event.dispatch(eventNode);
            }
        }
    }

    public void sendEvent(String str, WritableMap writableMap) {
        this.mEventEmitter.emit(str, writableMap);
    }
}
