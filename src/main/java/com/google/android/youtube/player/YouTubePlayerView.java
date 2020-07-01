package com.google.android.youtube.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import androidx.core.view.ViewCompat;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.internal.aa;
import com.google.android.youtube.player.internal.ab;
import com.google.android.youtube.player.internal.n;
import com.google.android.youtube.player.internal.s;
import com.google.android.youtube.player.internal.y;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class YouTubePlayerView extends ViewGroup implements Provider {
    private final a a;
    private final Set<View> b;
    private final b c;
    private com.google.android.youtube.player.internal.b d;
    private s e;
    private View f;
    private n g;
    private Provider h;
    private Bundle i;
    private OnInitializedListener j;
    private boolean k;
    private boolean l;

    private final class a implements OnGlobalFocusChangeListener {
        private a() {
        }

        /* synthetic */ a(YouTubePlayerView youTubePlayerView, byte b) {
            this();
        }

        public final void onGlobalFocusChanged(View view, View view2) {
            if (YouTubePlayerView.this.e != null && YouTubePlayerView.this.b.contains(view2) && !YouTubePlayerView.this.b.contains(view)) {
                YouTubePlayerView.this.e.g();
            }
        }
    }

    interface b {
        void a(YouTubePlayerView youTubePlayerView);

        void a(YouTubePlayerView youTubePlayerView, String str, OnInitializedListener onInitializedListener);
    }

    public YouTubePlayerView(Context context) {
        this(context, null);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet, int i) {
        if (context instanceof YouTubeBaseActivity) {
            this(context, attributeSet, i, ((YouTubeBaseActivity) context).a());
            return;
        }
        throw new IllegalStateException("A YouTubePlayerView can only be created with an Activity  which extends YouTubeBaseActivity as its context.");
    }

    YouTubePlayerView(Context context, AttributeSet attributeSet, int i, b bVar) {
        super((Context) ab.a((Object) context, (Object) "context cannot be null"), attributeSet, i);
        this.c = (b) ab.a((Object) bVar, (Object) "listener cannot be null");
        if (getBackground() == null) {
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        setClipToPadding(false);
        this.g = new n(context);
        requestTransparentRegion(this.g);
        addView(this.g);
        this.b = new HashSet();
        this.a = new a(this, (byte) 0);
    }

    private void a(View view) {
        Object obj = (view == this.g || (this.e != null && view == this.f)) ? 1 : null;
        if (obj == null) {
            throw new UnsupportedOperationException("No views can be added on top of the player");
        }
    }

    private void a(YouTubeInitializationResult youTubeInitializationResult) {
        this.e = null;
        this.g.c();
        OnInitializedListener onInitializedListener = this.j;
        if (onInitializedListener != null) {
            onInitializedListener.onInitializationFailure(this.h, youTubeInitializationResult);
            this.j = null;
        }
    }

    final void a() {
        s sVar = this.e;
        if (sVar != null) {
            sVar.b();
        }
    }

    final void a(final Activity activity, Provider provider, String str, OnInitializedListener onInitializedListener, Bundle bundle) {
        if (this.e == null && this.j == null) {
            ab.a((Object) activity, (Object) "activity cannot be null");
            this.h = (Provider) ab.a((Object) provider, (Object) "provider cannot be null");
            this.j = (OnInitializedListener) ab.a((Object) onInitializedListener, (Object) "listener cannot be null");
            this.i = bundle;
            this.g.b();
            this.d = aa.a().a(getContext(), str, new com.google.android.youtube.player.internal.t.a() {
                public final void a() {
                    if (YouTubePlayerView.this.d != null) {
                        YouTubePlayerView.a(YouTubePlayerView.this, activity);
                    }
                    YouTubePlayerView.this.d = null;
                }

                public final void b() {
                    if (!(YouTubePlayerView.this.l || YouTubePlayerView.this.e == null)) {
                        YouTubePlayerView.this.e.f();
                    }
                    YouTubePlayerView.this.g.a();
                    YouTubePlayerView youTubePlayerView = YouTubePlayerView.this;
                    if (youTubePlayerView.indexOfChild(youTubePlayerView.g) < 0) {
                        youTubePlayerView = YouTubePlayerView.this;
                        youTubePlayerView.addView(youTubePlayerView.g);
                        youTubePlayerView = YouTubePlayerView.this;
                        youTubePlayerView.removeView(youTubePlayerView.f);
                    }
                    YouTubePlayerView.this.f = null;
                    YouTubePlayerView.this.e = null;
                    YouTubePlayerView.this.d = null;
                }
            }, new com.google.android.youtube.player.internal.t.b() {
                public final void a(YouTubeInitializationResult youTubeInitializationResult) {
                    YouTubePlayerView.this.a(youTubeInitializationResult);
                    YouTubePlayerView.this.d = null;
                }
            });
            this.d.e();
        }
    }

    final void a(boolean z) {
        if (!z || VERSION.SDK_INT >= 14) {
            this.k = z;
            return;
        }
        y.a("Could not enable TextureView because API level is lower than 14", new Object[0]);
        this.k = false;
    }

    public final void addFocusables(ArrayList<View> arrayList, int i) {
        Collection arrayList2 = new ArrayList();
        super.addFocusables(arrayList2, i);
        arrayList.addAll(arrayList2);
        this.b.clear();
        this.b.addAll(arrayList2);
    }

    public final void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        Collection arrayList2 = new ArrayList();
        super.addFocusables(arrayList2, i, i2);
        arrayList.addAll(arrayList2);
        this.b.clear();
        this.b.addAll(arrayList2);
    }

    public final void addView(View view) {
        a(view);
        super.addView(view);
    }

    public final void addView(View view, int i) {
        a(view);
        super.addView(view, i);
    }

    public final void addView(View view, int i, int i2) {
        a(view);
        super.addView(view, i, i2);
    }

    public final void addView(View view, int i, LayoutParams layoutParams) {
        a(view);
        super.addView(view, i, layoutParams);
    }

    public final void addView(View view, LayoutParams layoutParams) {
        a(view);
        super.addView(view, layoutParams);
    }

    final void b() {
        s sVar = this.e;
        if (sVar != null) {
            sVar.c();
        }
    }

    final void b(boolean z) {
        s sVar = this.e;
        if (sVar != null) {
            sVar.b(z);
            c(z);
        }
    }

    final void c() {
        s sVar = this.e;
        if (sVar != null) {
            sVar.d();
        }
    }

    final void c(boolean z) {
        this.l = true;
        s sVar = this.e;
        if (sVar != null) {
            sVar.a(z);
        }
    }

    public final void clearChildFocus(View view) {
        if (hasFocusable()) {
            requestFocus();
        } else {
            super.clearChildFocus(view);
        }
    }

    final void d() {
        s sVar = this.e;
        if (sVar != null) {
            sVar.e();
        }
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.e != null) {
            if (keyEvent.getAction() == 0) {
                return this.e.a(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent);
            } else {
                if (keyEvent.getAction() == 1) {
                    return this.e.b(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent);
                }
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    final Bundle e() {
        s sVar = this.e;
        return sVar == null ? this.i : sVar.h();
    }

    public final void focusableViewAvailable(View view) {
        super.focusableViewAvailable(view);
        this.b.add(view);
    }

    public final void initialize(String str, OnInitializedListener onInitializedListener) {
        ab.a(str, (Object) "Developer key cannot be null or empty");
        this.c.a(this, str, onInitializedListener);
    }

    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalFocusChangeListener(this.a);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        s sVar = this.e;
        if (sVar != null) {
            sVar.a(configuration);
        }
    }

    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalFocusChangeListener(this.a);
    }

    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (getChildCount() > 0) {
            getChildAt(0).layout(0, 0, i3 - i, i4 - i2);
        }
    }

    protected final void onMeasure(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            childAt.measure(i, i2);
            setMeasuredDimension(childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            return;
        }
        setMeasuredDimension(0, 0);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        this.b.add(view2);
    }

    public final void setClipToPadding(boolean z) {
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
    }
}
