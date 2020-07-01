package com.google.android.gms.internal.base;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;
import com.google.firebase.storage.internal.ExponentialBackoffSender;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class zae extends Drawable implements Callback {
    private int mAlpha;
    private int mFrom;
    private boolean zand;
    private int zanl;
    private long zanm;
    private int zann;
    private int zano;
    private int zanp;
    private boolean zanq;
    private zah zanr;
    private Drawable zans;
    private Drawable zant;
    private boolean zanu;
    private boolean zanv;
    private boolean zanw;
    private int zanx;

    public zae(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = zaf.zany;
        }
        this.zans = drawable;
        drawable.setCallback(this);
        zah zah = this.zanr;
        zah.zaoa = drawable.getChangingConfigurations() | zah.zaoa;
        if (drawable2 == null) {
            drawable2 = zaf.zany;
        }
        this.zant = drawable2;
        drawable2.setCallback(this);
        zah zah2 = this.zanr;
        zah2.zaoa = drawable2.getChangingConfigurations() | zah2.zaoa;
    }

    zae(zah zah) {
        this.zanl = 0;
        this.zano = 255;
        this.mAlpha = 0;
        this.zand = true;
        this.zanr = new zah(zah);
    }

    public final void invalidateDrawable(Drawable drawable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final int getChangingConfigurations() {
        return (super.getChangingConfigurations() | this.zanr.mChangingConfigurations) | this.zanr.zaoa;
    }

    public final void setAlpha(int i) {
        if (this.mAlpha == this.zano) {
            this.mAlpha = i;
        }
        this.zano = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zans.setColorFilter(colorFilter);
        this.zant.setColorFilter(colorFilter);
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zans.getIntrinsicWidth(), this.zant.getIntrinsicWidth());
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zans.getIntrinsicHeight(), this.zant.getIntrinsicHeight());
    }

    protected final void onBoundsChange(Rect rect) {
        this.zans.setBounds(rect);
        this.zant.setBounds(rect);
    }

    public final ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.zanr.mChangingConfigurations = getChangingConfigurations();
        return this.zanr;
    }

    public final int getOpacity() {
        if (!this.zanw) {
            this.zanx = Drawable.resolveOpacity(this.zans.getOpacity(), this.zant.getOpacity());
            this.zanw = true;
        }
        return this.zanx;
    }

    private final boolean canConstantState() {
        if (!this.zanu) {
            boolean z = (this.zans.getConstantState() == null || this.zant.getConstantState() == null) ? false : true;
            this.zanv = z;
            this.zanu = true;
        }
        return this.zanv;
    }

    public final Drawable mutate() {
        if (!this.zanq && super.mutate() == this) {
            if (canConstantState()) {
                this.zans.mutate();
                this.zant.mutate();
                this.zanq = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    public final Drawable zacd() {
        return this.zant;
    }

    public final void startTransition(int i) {
        this.mFrom = 0;
        this.zann = this.zano;
        this.mAlpha = 0;
        this.zanp = ExponentialBackoffSender.RND_MAX;
        this.zanl = 1;
        invalidateSelf();
    }

    public final void draw(Canvas canvas) {
        int i = this.zanl;
        Object obj = 1;
        if (i == 1) {
            this.zanm = SystemClock.uptimeMillis();
            this.zanl = 2;
            obj = null;
        } else if (i == 2 && this.zanm >= 0) {
            float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.zanm)) / ((float) this.zanp);
            if (uptimeMillis < 1.0f) {
                obj = null;
            }
            if (obj != null) {
                this.zanl = 0;
            }
            this.mAlpha = (int) ((((float) this.zann) * Math.min(uptimeMillis, 1.0f)) + 0.0f);
        }
        i = this.mAlpha;
        boolean z = this.zand;
        Drawable drawable = this.zans;
        Drawable drawable2 = this.zant;
        if (obj != null) {
            if (!z || i == 0) {
                drawable.draw(canvas);
            }
            int i2 = this.zano;
            if (i == i2) {
                drawable2.setAlpha(i2);
                drawable2.draw(canvas);
            }
            return;
        }
        if (z) {
            drawable.setAlpha(this.zano - i);
        }
        drawable.draw(canvas);
        if (z) {
            drawable.setAlpha(this.zano);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zano);
        }
        invalidateSelf();
    }
}
