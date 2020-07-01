package com.swmansion.rnscreens;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.facebook.react.modules.core.ChoreographerCompat.FrameCallback;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.modules.core.ReactChoreographer.CallbackType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScreenContainer extends ViewGroup {
    private final Set<Screen> mActiveScreens = new HashSet();
    @Nullable
    private FragmentTransaction mCurrentTransaction;
    private FrameCallback mFrameCallback = new FrameCallback() {
        public void doFrame(long j) {
            ScreenContainer.this.updateIfNeeded();
        }
    };
    private boolean mIsAttached;
    private boolean mNeedUpdate;
    protected final ArrayList<Screen> mScreens = new ArrayList();

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    public ScreenContainer(Context context) {
        super(context);
    }

    protected void markUpdated() {
        if (!this.mNeedUpdate) {
            this.mNeedUpdate = true;
            ReactChoreographer.getInstance().postFrameCallback(CallbackType.NATIVE_ANIMATED_MODULE, this.mFrameCallback);
        }
    }

    protected void notifyChildUpdate() {
        markUpdated();
    }

    protected void addScreen(Screen screen, int i) {
        this.mScreens.add(i, screen);
        screen.setContainer(this);
        markUpdated();
    }

    protected void removeScreenAt(int i) {
        ((Screen) this.mScreens.get(i)).setContainer(null);
        this.mScreens.remove(i);
        markUpdated();
    }

    protected int getScreenCount() {
        return this.mScreens.size();
    }

    protected Screen getScreenAt(int i) {
        return (Screen) this.mScreens.get(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0012  */
    protected final androidx.fragment.app.FragmentActivity findRootFragmentActivity() {
        /*
        r3 = this;
        r0 = r3;
    L_0x0001:
        r1 = r0 instanceof com.facebook.react.ReactRootView;
        if (r1 != 0) goto L_0x0010;
    L_0x0005:
        r2 = r0.getParent();
        if (r2 == 0) goto L_0x0010;
    L_0x000b:
        r0 = r0.getParent();
        goto L_0x0001;
    L_0x0010:
        if (r1 == 0) goto L_0x0034;
    L_0x0012:
        r0 = (com.facebook.react.ReactRootView) r0;
        r0 = r0.getContext();
    L_0x0018:
        r1 = r0 instanceof androidx.fragment.app.FragmentActivity;
        if (r1 != 0) goto L_0x0027;
    L_0x001c:
        r2 = r0 instanceof android.content.ContextWrapper;
        if (r2 == 0) goto L_0x0027;
    L_0x0020:
        r0 = (android.content.ContextWrapper) r0;
        r0 = r0.getBaseContext();
        goto L_0x0018;
    L_0x0027:
        if (r1 == 0) goto L_0x002c;
    L_0x0029:
        r0 = (androidx.fragment.app.FragmentActivity) r0;
        return r0;
    L_0x002c:
        r0 = new java.lang.IllegalStateException;
        r1 = "In order to use RNScreens components your app's activity need to extend ReactFragmentActivity or ReactCompatActivity";
        r0.<init>(r1);
        throw r0;
    L_0x0034:
        r0 = new java.lang.IllegalStateException;
        r1 = "ScreenContainer is not attached under ReactRootView";
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenContainer.findRootFragmentActivity():androidx.fragment.app.FragmentActivity");
    }

    protected FragmentTransaction getOrCreateTransaction() {
        if (this.mCurrentTransaction == null) {
            this.mCurrentTransaction = findRootFragmentActivity().getSupportFragmentManager().beginTransaction();
            this.mCurrentTransaction.setReorderingAllowed(true);
        }
        return this.mCurrentTransaction;
    }

    protected void tryCommitTransaction() {
        FragmentTransaction fragmentTransaction = this.mCurrentTransaction;
        if (fragmentTransaction != null) {
            fragmentTransaction.commitAllowingStateLoss();
            this.mCurrentTransaction = null;
        }
    }

    private void attachScreen(Screen screen) {
        getOrCreateTransaction().add(getId(), screen.getFragment());
        this.mActiveScreens.add(screen);
    }

    private void moveToFront(Screen screen) {
        FragmentTransaction orCreateTransaction = getOrCreateTransaction();
        Fragment fragment = screen.getFragment();
        orCreateTransaction.remove(fragment);
        orCreateTransaction.add(getId(), fragment);
    }

    private void detachScreen(Screen screen) {
        getOrCreateTransaction().remove(screen.getFragment());
        this.mActiveScreens.remove(screen);
    }

    protected boolean isScreenActive(Screen screen, List<Screen> list) {
        return screen.isActive();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsAttached = true;
        updateIfNeeded();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsAttached = false;
    }

    private void updateIfNeeded() {
        if (this.mNeedUpdate && this.mIsAttached) {
            this.mNeedUpdate = false;
            onUpdate();
        }
    }

    protected void onUpdate() {
        int i;
        Set hashSet = new HashSet(this.mActiveScreens);
        int size = this.mScreens.size();
        for (i = 0; i < size; i++) {
            Screen screen = (Screen) this.mScreens.get(i);
            if (!isScreenActive(screen, this.mScreens) && this.mActiveScreens.contains(screen)) {
                detachScreen(screen);
            }
            hashSet.remove(screen);
        }
        if (!hashSet.isEmpty()) {
            Object[] toArray = hashSet.toArray();
            for (Object obj : toArray) {
                detachScreen((Screen) obj);
            }
        }
        int size2 = this.mScreens.size();
        i = 0;
        for (size = 0; size < size2; size++) {
            if (isScreenActive((Screen) this.mScreens.get(size), this.mScreens)) {
                i++;
            }
        }
        boolean z = i > 1;
        i = this.mScreens.size();
        Object obj2 = null;
        for (int i2 = 0; i2 < i; i2++) {
            Screen screen2 = (Screen) this.mScreens.get(i2);
            boolean isScreenActive = isScreenActive(screen2, this.mScreens);
            if (isScreenActive && !this.mActiveScreens.contains(screen2)) {
                attachScreen(screen2);
                obj2 = 1;
            } else if (isScreenActive && obj2 != null) {
                moveToFront(screen2);
            }
            screen2.setTransitioning(z);
        }
        tryCommitTransaction();
    }
}
