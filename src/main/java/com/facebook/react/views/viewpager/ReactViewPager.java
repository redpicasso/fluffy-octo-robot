package com.facebook.react.views.viewpager;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.List;

public class ReactViewPager extends ViewPager {
    private final EventDispatcher mEventDispatcher;
    private boolean mIsCurrentItemFromJs;
    private boolean mScrollEnabled = true;
    private final Runnable measureAndLayout = new Runnable() {
        public void run() {
            ReactViewPager reactViewPager = ReactViewPager.this;
            reactViewPager.measure(MeasureSpec.makeMeasureSpec(reactViewPager.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(ReactViewPager.this.getHeight(), Ints.MAX_POWER_OF_TWO));
            reactViewPager = ReactViewPager.this;
            reactViewPager.layout(reactViewPager.getLeft(), ReactViewPager.this.getTop(), ReactViewPager.this.getRight(), ReactViewPager.this.getBottom());
        }
    };

    private class Adapter extends PagerAdapter {
        private boolean mIsViewPagerInIntentionallyInconsistentState;
        private final List<View> mViews;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        private Adapter() {
            this.mViews = new ArrayList();
            this.mIsViewPagerInIntentionallyInconsistentState = false;
        }

        /* synthetic */ Adapter(ReactViewPager reactViewPager, AnonymousClass1 anonymousClass1) {
            this();
        }

        void addView(View view, int i) {
            this.mViews.add(i, view);
            notifyDataSetChanged();
            ReactViewPager.this.setOffscreenPageLimit(this.mViews.size());
        }

        void removeViewAt(int i) {
            this.mViews.remove(i);
            notifyDataSetChanged();
            ReactViewPager.this.setOffscreenPageLimit(this.mViews.size());
        }

        void setViews(List<View> list) {
            this.mViews.clear();
            this.mViews.addAll(list);
            notifyDataSetChanged();
            this.mIsViewPagerInIntentionallyInconsistentState = false;
        }

        void removeAllViewsFromAdapter(ViewPager viewPager) {
            this.mViews.clear();
            viewPager.removeAllViews();
            this.mIsViewPagerInIntentionallyInconsistentState = true;
        }

        View getViewAt(int i) {
            return (View) this.mViews.get(i);
        }

        public int getCount() {
            return this.mViews.size();
        }

        public int getItemPosition(Object obj) {
            return (this.mIsViewPagerInIntentionallyInconsistentState || !this.mViews.contains(obj)) ? -2 : this.mViews.indexOf(obj);
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View view = (View) this.mViews.get(i);
            viewGroup.addView(view, 0, ReactViewPager.this.access$000());
            return view;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    private class PageChangeListener implements OnPageChangeListener {
        private PageChangeListener() {
        }

        /* synthetic */ PageChangeListener(ReactViewPager reactViewPager, AnonymousClass1 anonymousClass1) {
            this();
        }

        public void onPageScrolled(int i, float f, int i2) {
            ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageScrollEvent(ReactViewPager.this.getId(), i, f));
        }

        public void onPageSelected(int i) {
            if (!ReactViewPager.this.mIsCurrentItemFromJs) {
                ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageSelectedEvent(ReactViewPager.this.getId(), i));
            }
        }

        public void onPageScrollStateChanged(int i) {
            String str;
            if (i == 0) {
                str = "idle";
            } else if (i == 1) {
                str = "dragging";
            } else if (i == 2) {
                str = "settling";
            } else {
                throw new IllegalStateException("Unsupported pageScrollState");
            }
            ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageScrollStateChangedEvent(ReactViewPager.this.getId(), str));
        }
    }

    public ReactViewPager(ReactContext reactContext) {
        super(reactContext);
        this.mEventDispatcher = ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        this.mIsCurrentItemFromJs = false;
        setOnPageChangeListener(new PageChangeListener(this, null));
        setAdapter(new Adapter(this, null));
    }

    public Adapter getAdapter() {
        return (Adapter) super.getAdapter();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollEnabled) {
            return false;
        }
        try {
            if (super.onInterceptTouchEvent(motionEvent)) {
                NativeGestureUtil.notifyNativeGestureStarted(this, motionEvent);
                return true;
            }
        } catch (Throwable e) {
            FLog.w(ReactConstants.TAG, "Error intercepting touch event.", e);
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollEnabled) {
            return false;
        }
        try {
            return super.onTouchEvent(motionEvent);
        } catch (Throwable e) {
            FLog.w(ReactConstants.TAG, "Error handling touch event.", e);
            return false;
        }
    }

    public void setCurrentItemFromJs(int i, boolean z) {
        this.mIsCurrentItemFromJs = true;
        setCurrentItem(i, z);
        this.mIsCurrentItemFromJs = false;
    }

    public void setScrollEnabled(boolean z) {
        this.mScrollEnabled = z;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestLayout();
        post(this.measureAndLayout);
    }

    void addViewToAdapter(View view, int i) {
        getAdapter().addView(view, i);
    }

    void removeViewFromAdapter(int i) {
        getAdapter().removeViewAt(i);
    }

    int getViewCountInAdapter() {
        return getAdapter().getCount();
    }

    View getViewFromAdapter(int i) {
        return getAdapter().getViewAt(i);
    }

    public void setViews(List<View> list) {
        getAdapter().setViews(list);
    }

    public void removeAllViewsFromAdapter() {
        getAdapter().removeAllViewsFromAdapter(this);
    }
}