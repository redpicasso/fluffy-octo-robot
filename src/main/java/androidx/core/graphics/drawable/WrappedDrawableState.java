package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

final class WrappedDrawableState extends ConstantState {
    int mChangingConfigurations;
    ConstantState mDrawableState;
    ColorStateList mTint = null;
    Mode mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;

    WrappedDrawableState(@Nullable WrappedDrawableState wrappedDrawableState) {
        if (wrappedDrawableState != null) {
            this.mChangingConfigurations = wrappedDrawableState.mChangingConfigurations;
            this.mDrawableState = wrappedDrawableState.mDrawableState;
            this.mTint = wrappedDrawableState.mTint;
            this.mTintMode = wrappedDrawableState.mTintMode;
        }
    }

    @NonNull
    public Drawable newDrawable() {
        return newDrawable(null);
    }

    @NonNull
    public Drawable newDrawable(@Nullable Resources resources) {
        if (VERSION.SDK_INT >= 21) {
            return new WrappedDrawableApi21(this, resources);
        }
        return new WrappedDrawableApi14(this, resources);
    }

    public int getChangingConfigurations() {
        int i = this.mChangingConfigurations;
        ConstantState constantState = this.mDrawableState;
        return i | (constantState != null ? constantState.getChangingConfigurations() : 0);
    }

    boolean canConstantState() {
        return this.mDrawableState != null;
    }
}