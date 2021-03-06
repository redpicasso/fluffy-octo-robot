package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ScaleKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

public class AnimatableScaleValue extends BaseAnimatableValue<ScaleXY, ScaleXY> {
    AnimatableScaleValue() {
        this(new ScaleXY(1.0f, 1.0f));
    }

    public AnimatableScaleValue(ScaleXY scaleXY) {
        super((Object) scaleXY);
    }

    public AnimatableScaleValue(List<Keyframe<ScaleXY>> list) {
        super((List) list);
    }

    public BaseKeyframeAnimation<ScaleXY, ScaleXY> createAnimation() {
        return new ScaleKeyframeAnimation(this.keyframes);
    }
}
