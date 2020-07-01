package com.swmansion.rnscreens;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import com.facebook.react.uimanager.PixelUtil;
import com.google.common.primitives.Ints;
import com.swmansion.rnscreens.ScreenStackHeaderSubview.Type;

public class ScreenStackHeaderConfig extends ViewGroup {
    private static final float TOOLBAR_ELEVATION = PixelUtil.toPixelFromDIP(4.0f);
    private OnBackPressedCallback mBackCallback = new OnBackPressedCallback(false) {
        public void handleOnBackPressed() {
            ScreenStackHeaderConfig.this.getScreenStack().dismiss(ScreenStackHeaderConfig.this.getScreen());
        }
    };
    private OnClickListener mBackClickListener = new OnClickListener() {
        public void onClick(View view) {
            ScreenStackHeaderConfig.this.getScreenStack().dismiss(ScreenStackHeaderConfig.this.getScreen());
        }
    };
    private int mBackgroundColor;
    private final ScreenStackHeaderSubview[] mConfigSubviews = new ScreenStackHeaderSubview[3];
    private int mHeight;
    private boolean mIsBackButtonHidden;
    private boolean mIsHidden;
    private boolean mIsShadowHidden;
    private int mSubviewsCount = 0;
    private int mTintColor;
    private String mTitle;
    private int mTitleColor;
    private String mTitleFontFamily;
    private int mTitleFontSize;
    private final Toolbar mToolbar;
    private int mWidth;

    /* renamed from: com.swmansion.rnscreens.ScreenStackHeaderConfig$3 */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type = new int[Type.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type[com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.CENTER.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type = r0;
            r0 = $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.LEFT;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.RIGHT;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.TITLE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.CENTER;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenStackHeaderConfig.3.<clinit>():void");
        }
    }

    private static final class ToolbarWithLayoutLoop extends Toolbar {
        private boolean mLayoutEnqueued = false;
        private final Runnable mLayoutRunnable = new Runnable() {
            public void run() {
                ToolbarWithLayoutLoop.this.mLayoutEnqueued = false;
                ToolbarWithLayoutLoop toolbarWithLayoutLoop = ToolbarWithLayoutLoop.this;
                toolbarWithLayoutLoop.measure(MeasureSpec.makeMeasureSpec(toolbarWithLayoutLoop.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(ToolbarWithLayoutLoop.this.getHeight(), Ints.MAX_POWER_OF_TWO));
                toolbarWithLayoutLoop = ToolbarWithLayoutLoop.this;
                toolbarWithLayoutLoop.layout(toolbarWithLayoutLoop.getLeft(), ToolbarWithLayoutLoop.this.getTop(), ToolbarWithLayoutLoop.this.getRight(), ToolbarWithLayoutLoop.this.getBottom());
            }
        };

        public ToolbarWithLayoutLoop(Context context) {
            super(context);
        }

        public void requestLayout() {
            super.requestLayout();
            if (!this.mLayoutEnqueued) {
                this.mLayoutEnqueued = true;
                post(this.mLayoutRunnable);
            }
        }
    }

    public ScreenStackHeaderConfig(Context context) {
        super(context);
        setVisibility(8);
        this.mToolbar = new ToolbarWithLayoutLoop(context);
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(16843827, typedValue, true)) {
            this.mToolbar.setBackgroundColor(typedValue.data);
        }
        this.mWidth = 0;
        this.mHeight = 0;
        if (context.getTheme().resolveAttribute(16843499, typedValue, true)) {
            this.mHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        }
    }

    private void updateToolbarLayout() {
        this.mToolbar.measure(MeasureSpec.makeMeasureSpec(this.mWidth, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(this.mHeight, Ints.MAX_POWER_OF_TWO));
        this.mToolbar.layout(0, 0, this.mWidth, this.mHeight);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        i3 -= i;
        if (this.mWidth != i3) {
            this.mWidth = i3;
            updateToolbarLayout();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        update();
    }

    private Screen getScreen() {
        ViewParent parent = getParent();
        return parent instanceof Screen ? (Screen) parent : null;
    }

    private ScreenStack getScreenStack() {
        Screen screen = getScreen();
        if (screen != null) {
            ScreenContainer container = screen.getContainer();
            if (container instanceof ScreenStack) {
                return (ScreenStack) container;
            }
        }
        return null;
    }

    private Fragment getScreenFragment() {
        ViewParent parent = getParent();
        return parent instanceof Screen ? ((Screen) parent).getFragment() : null;
    }

    private void installBackCallback() {
        this.mBackCallback.remove();
        LifecycleOwner screenFragment = getScreenFragment();
        screenFragment.requireActivity().getOnBackPressedDispatcher().addCallback(screenFragment, this.mBackCallback);
    }

    /* JADX WARNING: Missing block: B:53:0x00e9, code:
            if (r1 != 4) goto L_0x0106;
     */
    private void update() {
        /*
        r9 = this;
        r0 = r9.getParent();
        r0 = (com.swmansion.rnscreens.Screen) r0;
        r1 = r9.mIsHidden;
        if (r1 == 0) goto L_0x0018;
    L_0x000a:
        r1 = r9.mToolbar;
        r1 = r1.getParent();
        if (r1 == 0) goto L_0x0017;
    L_0x0012:
        r1 = r9.mToolbar;
        r0.removeView(r1);
    L_0x0017:
        return;
    L_0x0018:
        r1 = r9.mToolbar;
        r1 = r1.getParent();
        if (r1 != 0) goto L_0x0025;
    L_0x0020:
        r1 = r9.mToolbar;
        r0.addView(r1);
    L_0x0025:
        r1 = r0.getFragment();
        r1 = r1.getLifecycleActivity();
        r1 = (androidx.appcompat.app.AppCompatActivity) r1;
        r2 = r9.mToolbar;
        r1.setSupportActionBar(r2);
        r1 = r1.getSupportActionBar();
        r2 = r9.getScreenStack();
        r3 = 0;
        r4 = 1;
        if (r2 != 0) goto L_0x0042;
    L_0x0040:
        r0 = 1;
        goto L_0x004a;
    L_0x0042:
        r2 = r2.getRootScreen();
        if (r2 != r0) goto L_0x0049;
    L_0x0048:
        goto L_0x0040;
    L_0x0049:
        r0 = 0;
    L_0x004a:
        if (r0 == 0) goto L_0x004e;
    L_0x004c:
        r2 = 0;
        goto L_0x0053;
    L_0x004e:
        r2 = r9.mIsBackButtonHidden;
        if (r2 != 0) goto L_0x004c;
    L_0x0052:
        r2 = 1;
    L_0x0053:
        r1.setDisplayHomeAsUpEnabled(r2);
        if (r0 != 0) goto L_0x005b;
    L_0x0058:
        r9.installBackCallback();
    L_0x005b:
        r2 = r9.mBackCallback;
        r0 = r0 ^ r4;
        r2.setEnabled(r0);
        r0 = r9.mToolbar;
        r2 = r9.mBackClickListener;
        r0.setNavigationOnClickListener(r2);
        r0 = r9.mIsShadowHidden;
        if (r0 == 0) goto L_0x006e;
    L_0x006c:
        r0 = 0;
        goto L_0x0070;
    L_0x006e:
        r0 = TOOLBAR_ELEVATION;
    L_0x0070:
        r1.setElevation(r0);
        r0 = r9.mTitle;
        r1.setTitle(r0);
        r0 = r9.getTitleTextView();
        r1 = r9.mTitleColor;
        if (r1 == 0) goto L_0x0085;
    L_0x0080:
        r2 = r9.mToolbar;
        r2.setTitleTextColor(r1);
    L_0x0085:
        if (r0 == 0) goto L_0x00a8;
    L_0x0087:
        r1 = r9.mTitleFontFamily;
        if (r1 == 0) goto L_0x00a0;
    L_0x008b:
        r1 = com.facebook.react.views.text.ReactFontManager.getInstance();
        r2 = r9.mTitleFontFamily;
        r5 = r9.getContext();
        r5 = r5.getAssets();
        r1 = r1.getTypeface(r2, r3, r5);
        r0.setTypeface(r1);
    L_0x00a0:
        r1 = r9.mTitleFontSize;
        if (r1 <= 0) goto L_0x00a8;
    L_0x00a4:
        r1 = (float) r1;
        r0.setTextSize(r1);
    L_0x00a8:
        r0 = r9.mBackgroundColor;
        if (r0 == 0) goto L_0x00b1;
    L_0x00ac:
        r1 = r9.mToolbar;
        r1.setBackgroundColor(r0);
    L_0x00b1:
        r0 = r9.mTintColor;
        if (r0 == 0) goto L_0x00c4;
    L_0x00b5:
        r0 = r9.mToolbar;
        r0 = r0.getNavigationIcon();
        if (r0 == 0) goto L_0x00c4;
    L_0x00bd:
        r1 = r9.mTintColor;
        r2 = android.graphics.PorterDuff.Mode.SRC_ATOP;
        r0.setColorFilter(r1, r2);
    L_0x00c4:
        r0 = r9.mSubviewsCount;
        if (r3 >= r0) goto L_0x0117;
    L_0x00c8:
        r0 = r9.mConfigSubviews;
        r0 = r0[r3];
        r1 = r0.getType();
        r2 = new androidx.appcompat.widget.Toolbar$LayoutParams;
        r5 = -2;
        r6 = -1;
        r2.<init>(r5, r6);
        r5 = com.swmansion.rnscreens.ScreenStackHeaderConfig.AnonymousClass3.$SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type;
        r1 = r1.ordinal();
        r1 = r5[r1];
        r5 = 3;
        r7 = 0;
        if (r1 == r4) goto L_0x00fa;
    L_0x00e3:
        r8 = 2;
        if (r1 == r8) goto L_0x00f6;
    L_0x00e6:
        if (r1 == r5) goto L_0x00ec;
    L_0x00e8:
        r5 = 4;
        if (r1 == r5) goto L_0x00f3;
    L_0x00eb:
        goto L_0x0106;
    L_0x00ec:
        r2.width = r6;
        r1 = r9.mToolbar;
        r1.setTitle(r7);
    L_0x00f3:
        r2.gravity = r4;
        goto L_0x0106;
    L_0x00f6:
        r1 = 5;
        r2.gravity = r1;
        goto L_0x0106;
    L_0x00fa:
        r1 = r9.mToolbar;
        r1.setNavigationIcon(r7);
        r1 = r9.mToolbar;
        r1.setTitle(r7);
        r2.gravity = r5;
    L_0x0106:
        r0.setLayoutParams(r2);
        r1 = r0.getParent();
        if (r1 != 0) goto L_0x0114;
    L_0x010f:
        r1 = r9.mToolbar;
        r1.addView(r0);
    L_0x0114:
        r3 = r3 + 1;
        goto L_0x00c4;
    L_0x0117:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenStackHeaderConfig.update():void");
    }

    public ScreenStackHeaderSubview getConfigSubview(int i) {
        return this.mConfigSubviews[i];
    }

    public int getConfigSubviewsCount() {
        return this.mSubviewsCount;
    }

    public void removeConfigSubview(int i) {
        if (this.mConfigSubviews[i] != null) {
            this.mSubviewsCount--;
        }
        this.mConfigSubviews[i] = null;
    }

    public void addConfigSubview(ScreenStackHeaderSubview screenStackHeaderSubview, int i) {
        if (this.mConfigSubviews[i] == null) {
            this.mSubviewsCount++;
        }
        this.mConfigSubviews[i] = screenStackHeaderSubview;
    }

    private TextView getTitleTextView() {
        int childCount = this.mToolbar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mToolbar.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                if (textView.getText().equals(this.mToolbar.getTitle())) {
                    return textView;
                }
            }
        }
        return null;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public void setTitleFontFamily(String str) {
        this.mTitleFontFamily = str;
    }

    public void setTitleFontSize(int i) {
        this.mTitleFontSize = i;
    }

    public void setTitleColor(int i) {
        this.mTitleColor = i;
    }

    public void setTintColor(int i) {
        this.mTintColor = i;
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    public void setHideShadow(boolean z) {
        this.mIsShadowHidden = z;
    }

    public void setHideBackButton(boolean z) {
        this.mIsBackButtonHidden = z;
    }

    public void setHidden(boolean z) {
        this.mIsHidden = z;
    }
}
