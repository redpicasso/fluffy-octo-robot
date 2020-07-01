package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import javax.annotation.Nullable;

class LayoutUpdateAnimation extends AbstractLayoutAnimation {
    private static final boolean USE_TRANSLATE_ANIMATION = false;

    LayoutUpdateAnimation() {
    }

    boolean isValid() {
        return this.mDurationMs > 0;
    }

    @Nullable
    Animation createAnimationImpl(View view, int i, int i2, int i3, int i4) {
        Object obj = null;
        Object obj2 = (view.getX() == ((float) i) && view.getY() == ((float) i2)) ? null : 1;
        if (!(view.getWidth() == i3 && view.getHeight() == i4)) {
            obj = 1;
        }
        if (obj2 == null && obj == null) {
            return null;
        }
        return new PositionAndSizeAnimation(view, i, i2, i3, i4);
    }
}
