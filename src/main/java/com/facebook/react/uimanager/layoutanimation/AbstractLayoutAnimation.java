package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BaseInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.IllegalViewOperationException;
import java.util.Map;
import javax.annotation.Nullable;

abstract class AbstractLayoutAnimation {
    private static final Map<InterpolatorType, BaseInterpolator> INTERPOLATOR = MapBuilder.of(InterpolatorType.LINEAR, new LinearInterpolator(), InterpolatorType.EASE_IN, new AccelerateInterpolator(), InterpolatorType.EASE_OUT, new DecelerateInterpolator(), InterpolatorType.EASE_IN_EASE_OUT, new AccelerateDecelerateInterpolator());
    private static final boolean SLOWDOWN_ANIMATION_MODE = false;
    @Nullable
    protected AnimatedPropertyType mAnimatedProperty;
    private int mDelayMs;
    protected int mDurationMs;
    @Nullable
    private Interpolator mInterpolator;

    @Nullable
    abstract Animation createAnimationImpl(View view, int i, int i2, int i3, int i4);

    abstract boolean isValid();

    AbstractLayoutAnimation() {
    }

    public void reset() {
        this.mAnimatedProperty = null;
        this.mDurationMs = 0;
        this.mDelayMs = 0;
        this.mInterpolator = null;
    }

    public void initializeFromConfig(ReadableMap readableMap, int i) {
        String str = "property";
        this.mAnimatedProperty = readableMap.hasKey(str) ? AnimatedPropertyType.fromString(readableMap.getString(str)) : null;
        str = ReactVideoView.EVENT_PROP_DURATION;
        if (readableMap.hasKey(str)) {
            i = readableMap.getInt(str);
        }
        this.mDurationMs = i;
        String str2 = "delay";
        this.mDelayMs = readableMap.hasKey(str2) ? readableMap.getInt(str2) : 0;
        str2 = "type";
        if (readableMap.hasKey(str2)) {
            this.mInterpolator = getInterpolator(InterpolatorType.fromString(readableMap.getString(str2)), readableMap);
            if (!isValid()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid layout animation : ");
                stringBuilder.append(readableMap);
                throw new IllegalViewOperationException(stringBuilder.toString());
            }
            return;
        }
        throw new IllegalArgumentException("Missing interpolation type.");
    }

    @Nullable
    public final Animation createAnimation(View view, int i, int i2, int i3, int i4) {
        if (!isValid()) {
            return null;
        }
        Animation createAnimationImpl = createAnimationImpl(view, i, i2, i3, i4);
        if (createAnimationImpl != null) {
            createAnimationImpl.setDuration((long) (this.mDurationMs * 1));
            createAnimationImpl.setStartOffset((long) (this.mDelayMs * 1));
            createAnimationImpl.setInterpolator(this.mInterpolator);
        }
        return createAnimationImpl;
    }

    private static Interpolator getInterpolator(InterpolatorType interpolatorType, ReadableMap readableMap) {
        Interpolator simpleSpringInterpolator;
        if (interpolatorType.equals(InterpolatorType.SPRING)) {
            simpleSpringInterpolator = new SimpleSpringInterpolator(SimpleSpringInterpolator.getSpringDamping(readableMap));
        } else {
            simpleSpringInterpolator = (Interpolator) INTERPOLATOR.get(interpolatorType);
        }
        if (simpleSpringInterpolator != null) {
            return simpleSpringInterpolator;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Missing interpolator for type : ");
        stringBuilder.append(interpolatorType);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
