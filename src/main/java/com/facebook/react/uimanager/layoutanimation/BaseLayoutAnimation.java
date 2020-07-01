package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import com.facebook.react.uimanager.IllegalViewOperationException;

abstract class BaseLayoutAnimation extends AbstractLayoutAnimation {

    /* renamed from: com.facebook.react.uimanager.layoutanimation.BaseLayoutAnimation$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType = new int[AnimatedPropertyType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType[com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType.SCALE_Y.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType = r0;
            r0 = $SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType.OPACITY;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType.SCALE_XY;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType.SCALE_X;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.facebook.react.uimanager.layoutanimation.AnimatedPropertyType.SCALE_Y;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.layoutanimation.BaseLayoutAnimation.1.<clinit>():void");
        }
    }

    abstract boolean isReverse();

    BaseLayoutAnimation() {
    }

    boolean isValid() {
        return this.mDurationMs > 0 && this.mAnimatedProperty != null;
    }

    Animation createAnimationImpl(View view, int i, int i2, int i3, int i4) {
        if (this.mAnimatedProperty != null) {
            i = AnonymousClass1.$SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType[this.mAnimatedProperty.ordinal()];
            float f = 0.0f;
            if (i == 1) {
                float alpha = isReverse() ? view.getAlpha() : 0.0f;
                if (!isReverse()) {
                    f = view.getAlpha();
                }
                return new OpacityAnimation(view, alpha, f);
            } else if (i == 2) {
                float f2 = isReverse() ? 1.0f : 0.0f;
                float f3 = isReverse() ? 0.0f : 1.0f;
                return new ScaleAnimation(f2, f3, f2, f3, 1, 0.5f, 1, 0.5f);
            } else if (i == 3) {
                return new ScaleAnimation(isReverse() ? 1.0f : 0.0f, isReverse() ? 0.0f : 1.0f, 1.0f, 1.0f, 1, 0.5f, 1, 0.0f);
            } else if (i == 4) {
                return new ScaleAnimation(1.0f, 1.0f, isReverse() ? 1.0f : 0.0f, isReverse() ? 0.0f : 1.0f, 1, 0.0f, 1, 0.5f);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Missing animation for property : ");
                stringBuilder.append(this.mAnimatedProperty);
                throw new IllegalViewOperationException(stringBuilder.toString());
            }
        }
        throw new IllegalViewOperationException("Missing animated property from animation config");
    }
}
