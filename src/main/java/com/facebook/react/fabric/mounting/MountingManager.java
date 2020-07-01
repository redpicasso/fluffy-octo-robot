package com.facebook.react.fabric.mounting;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.AnyThread;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.fabric.jsi.EventEmitterWrapper;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.RootViewManager;
import com.facebook.react.uimanager.StateWrapper;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.ViewManagerRegistry;
import com.facebook.yoga.YogaMeasureMode;
import com.google.common.primitives.Ints;
import java.util.concurrent.ConcurrentHashMap;

public class MountingManager {
    private final RootViewManager mRootViewManager = new RootViewManager();
    private final ConcurrentHashMap<Integer, ViewState> mTagToViewState = new ConcurrentHashMap();
    private final ViewFactory mViewFactory;
    private final ViewManagerRegistry mViewManagerRegistry;

    private static class ViewState {
        public ReadableMap mCurrentLocalData;
        public ReactStylesDiffMap mCurrentProps;
        public ReadableMap mCurrentState;
        public EventEmitterWrapper mEventEmitter;
        final boolean mIsRoot;
        final int mReactTag;
        @Nullable
        final View mView;
        @Nullable
        final ViewManager mViewManager;

        private ViewState(int i, @Nullable View view, @Nullable ViewManager viewManager) {
            this(i, view, viewManager, false);
        }

        private ViewState(int i, @Nullable View view, ViewManager viewManager, boolean z) {
            this.mReactTag = i;
            this.mView = view;
            this.mIsRoot = z;
            this.mViewManager = viewManager;
        }

        public String toString() {
            boolean z = this.mViewManager == null;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewState [");
            stringBuilder.append(this.mReactTag);
            stringBuilder.append("] - isRoot: ");
            stringBuilder.append(this.mIsRoot);
            stringBuilder.append(" - props: ");
            stringBuilder.append(this.mCurrentProps);
            stringBuilder.append(" - localData: ");
            stringBuilder.append(this.mCurrentLocalData);
            stringBuilder.append(" - viewManager: ");
            stringBuilder.append(this.mViewManager);
            stringBuilder.append(" - isLayoutOnly: ");
            stringBuilder.append(z);
            return stringBuilder.toString();
        }
    }

    public MountingManager(ViewManagerRegistry viewManagerRegistry) {
        this.mViewManagerRegistry = viewManagerRegistry;
        this.mViewFactory = new ViewManagerFactory(viewManagerRegistry);
    }

    @UiThread
    public void addRootView(int i, View view) {
        if (view.getId() == -1) {
            this.mTagToViewState.put(Integer.valueOf(i), new ViewState(i, view, this.mRootViewManager, true));
            view.setId(i);
            return;
        }
        throw new IllegalViewOperationException("Trying to add a root view with an explicit id already set. React Native uses the id field to track react tags and will overwrite this field. If that is fine, explicitly overwrite the id field to View.NO_ID before calling addRootView.");
    }

    @UiThread
    private void dropView(View view) {
        UiThreadUtil.assertOnUiThread();
        int id = view.getId();
        ViewState viewState = getViewState(id);
        ViewManager viewManager = viewState.mViewManager;
        if (!(viewState.mIsRoot || viewManager == null)) {
            viewManager.onDropViewInstance(view);
        }
        if ((view instanceof ViewGroup) && (viewManager instanceof ViewGroupManager)) {
            ViewGroup viewGroup = (ViewGroup) view;
            ViewGroupManager viewGroupManager = getViewGroupManager(viewState);
            for (int childCount = viewGroupManager.getChildCount(viewGroup) - 1; childCount >= 0; childCount--) {
                View childAt = viewGroupManager.getChildAt(viewGroup, childCount);
                if (this.mTagToViewState.get(Integer.valueOf(childAt.getId())) != null) {
                    dropView(childAt);
                }
                viewGroupManager.removeViewAt(viewGroup, childCount);
            }
        }
        this.mTagToViewState.remove(Integer.valueOf(id));
        Context context = view.getContext();
        if (context instanceof ThemedReactContext) {
            this.mViewFactory.recycle((ThemedReactContext) context, ((ViewManager) Assertions.assertNotNull(viewManager)).getName(), view);
        }
    }

    @UiThread
    public void removeRootView(int i) {
        UiThreadUtil.assertOnUiThread();
        ViewState viewState = (ViewState) this.mTagToViewState.get(Integer.valueOf(i));
        if (viewState == null || !viewState.mIsRoot) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("View with tag ");
            stringBuilder.append(i);
            stringBuilder.append(" is not registered as a root view");
            SoftAssertions.assertUnreachable(stringBuilder.toString());
        }
        if (viewState.mView != null) {
            dropView(viewState.mView);
        }
    }

    @UiThread
    public void addViewAt(int i, int i2, int i3) {
        UiThreadUtil.assertOnUiThread();
        ViewState viewState = getViewState(i);
        ViewGroup viewGroup = (ViewGroup) viewState.mView;
        ViewState viewState2 = getViewState(i2);
        View view = viewState2.mView;
        if (view != null) {
            getViewGroupManager(viewState).addView(viewGroup, view, i3);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to find view for viewState ");
        stringBuilder.append(viewState2);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private ViewState getViewState(int i) {
        ViewState viewState = (ViewState) this.mTagToViewState.get(Integer.valueOf(i));
        if (viewState != null) {
            return viewState;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to find viewState view ");
        stringBuilder.append(viewState);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void receiveCommand(int i, int i2, @Nullable ReadableArray readableArray) {
        ViewState viewState = getViewState(i);
        StringBuilder stringBuilder;
        if (viewState.mViewManager == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find viewState manager for tag ");
            stringBuilder.append(i);
            throw new IllegalStateException(stringBuilder.toString());
        } else if (viewState.mView != null) {
            viewState.mViewManager.receiveCommand(viewState.mView, i2, readableArray);
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find viewState view for tag ");
            stringBuilder.append(i);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    private static ViewGroupManager<ViewGroup> getViewGroupManager(ViewState viewState) {
        if (viewState.mViewManager != null) {
            return (ViewGroupManager) viewState.mViewManager;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to find ViewManager for view: ");
        stringBuilder.append(viewState);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @UiThread
    public void removeViewAt(int i, int i2) {
        UiThreadUtil.assertOnUiThread();
        ViewState viewState = getViewState(i);
        ViewGroup viewGroup = (ViewGroup) viewState.mView;
        if (viewGroup != null) {
            getViewGroupManager(viewState).removeViewAt(viewGroup, i2);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to find view for tag ");
        stringBuilder.append(i);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @UiThread
    public void createViewWithProps(ThemedReactContext themedReactContext, String str, int i, @Nullable ReadableMap readableMap, boolean z) {
        if (this.mTagToViewState.get(Integer.valueOf(i)) == null) {
            ViewManager viewManager;
            View orCreateView;
            ReactStylesDiffMap reactStylesDiffMap = readableMap != null ? new ReactStylesDiffMap(readableMap) : null;
            if (z) {
                viewManager = this.mViewManagerRegistry.get(str);
                orCreateView = this.mViewFactory.getOrCreateView(str, reactStylesDiffMap, themedReactContext);
                orCreateView.setId(i);
            } else {
                orCreateView = null;
                viewManager = orCreateView;
            }
            ViewState viewState = new ViewState(i, orCreateView, viewManager, null);
            viewState.mCurrentProps = reactStylesDiffMap;
            this.mTagToViewState.put(Integer.valueOf(i), viewState);
        }
    }

    @UiThread
    public void updateProps(int i, ReadableMap readableMap) {
        if (readableMap != null) {
            UiThreadUtil.assertOnUiThread();
            ViewState viewState = getViewState(i);
            viewState.mCurrentProps = new ReactStylesDiffMap(readableMap);
            View view = viewState.mView;
            if (view != null) {
                ((ViewManager) Assertions.assertNotNull(viewState.mViewManager)).updateProperties(view, viewState.mCurrentProps);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find view for tag ");
            stringBuilder.append(i);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    @UiThread
    public void updateLayout(int i, int i2, int i3, int i4, int i5) {
        UiThreadUtil.assertOnUiThread();
        ViewState viewState = getViewState(i);
        if (!viewState.mIsRoot) {
            View view = viewState.mView;
            if (view != null) {
                view.measure(MeasureSpec.makeMeasureSpec(i4, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(i5, Ints.MAX_POWER_OF_TWO));
                ViewParent parent = view.getParent();
                if (parent instanceof RootView) {
                    parent.requestLayout();
                }
                view.layout(i2, i3, i4 + i2, i5 + i3);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find View for tag: ");
            stringBuilder.append(i);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    @UiThread
    public void deleteView(int i) {
        UiThreadUtil.assertOnUiThread();
        View view = getViewState(i).mView;
        if (view != null) {
            dropView(view);
        } else {
            this.mTagToViewState.remove(Integer.valueOf(i));
        }
    }

    @UiThread
    public void updateLocalData(int i, ReadableMap readableMap) {
        UiThreadUtil.assertOnUiThread();
        ViewState viewState = getViewState(i);
        if (viewState.mCurrentProps != null) {
            if (viewState.mCurrentLocalData != null) {
                String str = "hash";
                if (readableMap.hasKey(str) && viewState.mCurrentLocalData.getDouble(str) == readableMap.getDouble(str) && viewState.mCurrentLocalData.equals(readableMap)) {
                    return;
                }
            }
            viewState.mCurrentLocalData = readableMap;
            ViewManager viewManager = viewState.mViewManager;
            if (viewManager != null) {
                Object updateLocalData = viewManager.updateLocalData(viewState.mView, viewState.mCurrentProps, new ReactStylesDiffMap(viewState.mCurrentLocalData));
                if (updateLocalData != null) {
                    viewManager.updateExtraData(viewState.mView, updateLocalData);
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find ViewManager for view: ");
            stringBuilder.append(viewState);
            throw new IllegalStateException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Can not update local data to view without props: ");
        stringBuilder2.append(i);
        throw new IllegalStateException(stringBuilder2.toString());
    }

    @UiThread
    public void updateState(int i, StateWrapper stateWrapper) {
        UiThreadUtil.assertOnUiThread();
        ViewState viewState = getViewState(i);
        ReadableMap state = stateWrapper.getState();
        if (viewState.mCurrentState == null || !viewState.mCurrentState.equals(state)) {
            viewState.mCurrentState = state;
            ViewManager viewManager = viewState.mViewManager;
            if (viewManager != null) {
                viewManager.updateState(viewState.mView, stateWrapper);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to find ViewManager for tag: ");
            stringBuilder.append(i);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    @UiThread
    public void preallocateView(ThemedReactContext themedReactContext, String str, int i, @Nullable ReadableMap readableMap, boolean z) {
        if (this.mTagToViewState.get(Integer.valueOf(i)) == null) {
            createViewWithProps(themedReactContext, str, i, readableMap, z);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View for component ");
        stringBuilder.append(str);
        stringBuilder.append(" with tag ");
        stringBuilder.append(i);
        stringBuilder.append(" already exists.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    @UiThread
    public void updateEventEmitter(int i, EventEmitterWrapper eventEmitterWrapper) {
        UiThreadUtil.assertOnUiThread();
        getViewState(i).mEventEmitter = eventEmitterWrapper;
    }

    @AnyThread
    public long measure(Context context, String str, ReadableMap readableMap, ReadableMap readableMap2, ReadableMap readableMap3, float f, YogaMeasureMode yogaMeasureMode, float f2, YogaMeasureMode yogaMeasureMode2) {
        String str2 = str;
        return this.mViewManagerRegistry.get(str).measure(context, readableMap, readableMap2, readableMap3, f, yogaMeasureMode, f2, yogaMeasureMode2);
    }

    @AnyThread
    @Nullable
    public EventEmitterWrapper getEventEmitter(int i) {
        ViewState viewState = (ViewState) this.mTagToViewState.get(Integer.valueOf(i));
        if (viewState == null) {
            return null;
        }
        return viewState.mEventEmitter;
    }
}
