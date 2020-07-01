package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.IntegerKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseStrokeContent implements AnimationListener, KeyPathElementContent, DrawingContent {
    @Nullable
    private BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
    private final List<BaseKeyframeAnimation<?, Float>> dashPatternAnimations;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> dashPatternOffsetAnimation;
    private final float[] dashPatternValues;
    protected final BaseLayer layer;
    private final LottieDrawable lottieDrawable;
    private final BaseKeyframeAnimation<?, Integer> opacityAnimation;
    final Paint paint = new LPaint(1);
    private final Path path = new Path();
    private final List<PathGroup> pathGroups = new ArrayList();
    private final PathMeasure pm = new PathMeasure();
    private final RectF rect = new RectF();
    private final Path trimPathPath = new Path();
    private final BaseKeyframeAnimation<?, Float> widthAnimation;

    private static final class PathGroup {
        private final List<PathContent> paths;
        @Nullable
        private final TrimPathContent trimPath;

        private PathGroup(@Nullable TrimPathContent trimPathContent) {
            this.paths = new ArrayList();
            this.trimPath = trimPathContent;
        }
    }

    BaseStrokeContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, Cap cap, Join join, float f, AnimatableIntegerValue animatableIntegerValue, AnimatableFloatValue animatableFloatValue, List<AnimatableFloatValue> list, AnimatableFloatValue animatableFloatValue2) {
        int i;
        this.lottieDrawable = lottieDrawable;
        this.layer = baseLayer;
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeCap(cap);
        this.paint.setStrokeJoin(join);
        this.paint.setStrokeMiter(f);
        this.opacityAnimation = animatableIntegerValue.createAnimation();
        this.widthAnimation = animatableFloatValue.createAnimation();
        if (animatableFloatValue2 == null) {
            this.dashPatternOffsetAnimation = null;
        } else {
            this.dashPatternOffsetAnimation = animatableFloatValue2.createAnimation();
        }
        this.dashPatternAnimations = new ArrayList(list.size());
        this.dashPatternValues = new float[list.size()];
        for (i = 0; i < list.size(); i++) {
            this.dashPatternAnimations.add(((AnimatableFloatValue) list.get(i)).createAnimation());
        }
        baseLayer.addAnimation(this.opacityAnimation);
        baseLayer.addAnimation(this.widthAnimation);
        for (i = 0; i < this.dashPatternAnimations.size(); i++) {
            baseLayer.addAnimation((BaseKeyframeAnimation) this.dashPatternAnimations.get(i));
        }
        BaseKeyframeAnimation baseKeyframeAnimation = this.dashPatternOffsetAnimation;
        if (baseKeyframeAnimation != null) {
            baseLayer.addAnimation(baseKeyframeAnimation);
        }
        this.opacityAnimation.addUpdateListener(this);
        this.widthAnimation.addUpdateListener(this);
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((BaseKeyframeAnimation) this.dashPatternAnimations.get(i2)).addUpdateListener(this);
        }
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.dashPatternOffsetAnimation;
        if (baseKeyframeAnimation2 != null) {
            baseKeyframeAnimation2.addUpdateListener(this);
        }
    }

    public void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public void setContents(List<Content> list, List<Content> list2) {
        Content content;
        TrimPathContent trimPathContent = null;
        for (int size = list.size() - 1; size >= 0; size--) {
            content = (Content) list.get(size);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent2 = (TrimPathContent) content;
                if (trimPathContent2.getType() == Type.INDIVIDUALLY) {
                    trimPathContent = trimPathContent2;
                }
            }
        }
        if (trimPathContent != null) {
            trimPathContent.addListener(this);
        }
        Object obj = null;
        for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
            content = (Content) list2.get(size2);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent3 = (TrimPathContent) content;
                if (trimPathContent3.getType() == Type.INDIVIDUALLY) {
                    if (obj != null) {
                        this.pathGroups.add(obj);
                    }
                    obj = new PathGroup(trimPathContent3);
                    trimPathContent3.addListener(this);
                }
            }
            if (content instanceof PathContent) {
                if (obj == null) {
                    obj = new PathGroup(trimPathContent);
                }
                obj.paths.add((PathContent) content);
            }
        }
        if (obj != null) {
            this.pathGroups.add(obj);
        }
    }

    public void draw(Canvas canvas, Matrix matrix, int i) {
        String str = "StrokeContent#draw";
        L.beginSection(str);
        if (Utils.hasZeroScaleAxis(matrix)) {
            L.endSection(str);
            return;
        }
        int i2 = 0;
        this.paint.setAlpha(MiscUtils.clamp((int) ((((((float) i) / 255.0f) * ((float) ((IntegerKeyframeAnimation) this.opacityAnimation).getIntValue())) / 100.0f) * 255.0f), 0, 255));
        this.paint.setStrokeWidth(((FloatKeyframeAnimation) this.widthAnimation).getFloatValue() * Utils.getScale(matrix));
        if (this.paint.getStrokeWidth() <= 0.0f) {
            L.endSection(str);
            return;
        }
        applyDashPatternIfNeeded(matrix);
        BaseKeyframeAnimation baseKeyframeAnimation = this.colorFilterAnimation;
        if (baseKeyframeAnimation != null) {
            this.paint.setColorFilter((ColorFilter) baseKeyframeAnimation.getValue());
        }
        while (i2 < this.pathGroups.size()) {
            PathGroup pathGroup = (PathGroup) this.pathGroups.get(i2);
            if (pathGroup.trimPath != null) {
                applyTrimPath(canvas, pathGroup, matrix);
            } else {
                String str2 = "StrokeContent#buildPath";
                L.beginSection(str2);
                this.path.reset();
                for (int size = pathGroup.paths.size() - 1; size >= 0; size--) {
                    this.path.addPath(((PathContent) pathGroup.paths.get(size)).getPath(), matrix);
                }
                L.endSection(str2);
                String str3 = "StrokeContent#drawPath";
                L.beginSection(str3);
                canvas.drawPath(this.path, this.paint);
                L.endSection(str3);
            }
            i2++;
        }
        L.endSection(str);
    }

    private void applyTrimPath(Canvas canvas, PathGroup pathGroup, Matrix matrix) {
        String str = "StrokeContent#applyTrimPath";
        L.beginSection(str);
        if (pathGroup.trimPath == null) {
            L.endSection(str);
            return;
        }
        this.path.reset();
        for (int size = pathGroup.paths.size() - 1; size >= 0; size--) {
            this.path.addPath(((PathContent) pathGroup.paths.get(size)).getPath(), matrix);
        }
        this.pm.setPath(this.path, false);
        float length = this.pm.getLength();
        while (this.pm.nextContour()) {
            length += this.pm.getLength();
        }
        float floatValue = (((Float) pathGroup.trimPath.getOffset().getValue()).floatValue() * length) / 360.0f;
        float floatValue2 = ((((Float) pathGroup.trimPath.getStart().getValue()).floatValue() * length) / 100.0f) + floatValue;
        float floatValue3 = ((((Float) pathGroup.trimPath.getEnd().getValue()).floatValue() * length) / 100.0f) + floatValue;
        float f = 0.0f;
        for (int size2 = pathGroup.paths.size() - 1; size2 >= 0; size2--) {
            float f2;
            this.trimPathPath.set(((PathContent) pathGroup.paths.get(size2)).getPath());
            this.trimPathPath.transform(matrix);
            this.pm.setPath(this.trimPathPath, false);
            float length2 = this.pm.getLength();
            float f3 = 1.0f;
            if (floatValue3 > length) {
                f2 = floatValue3 - length;
                if (f2 < f + length2 && f < f2) {
                    Utils.applyTrimPathIfNeeded(this.trimPathPath, floatValue2 > length ? (floatValue2 - length) / length2 : 0.0f, Math.min(f2 / length2, 1.0f), 0.0f);
                    canvas.drawPath(this.trimPathPath, this.paint);
                    f += length2;
                }
            }
            f2 = f + length2;
            if (f2 >= floatValue2 && f <= floatValue3) {
                if (f2 > floatValue3 || floatValue2 >= f) {
                    float f4 = floatValue2 < f ? 0.0f : (floatValue2 - f) / length2;
                    if (floatValue3 <= f2) {
                        f3 = (floatValue3 - f) / length2;
                    }
                    Utils.applyTrimPathIfNeeded(this.trimPathPath, f4, f3, 0.0f);
                    canvas.drawPath(this.trimPathPath, this.paint);
                } else {
                    canvas.drawPath(this.trimPathPath, this.paint);
                }
            }
            f += length2;
        }
        L.endSection(str);
    }

    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        String str = "StrokeContent#getBounds";
        L.beginSection(str);
        this.path.reset();
        for (int i = 0; i < this.pathGroups.size(); i++) {
            PathGroup pathGroup = (PathGroup) this.pathGroups.get(i);
            for (int i2 = 0; i2 < pathGroup.paths.size(); i2++) {
                this.path.addPath(((PathContent) pathGroup.paths.get(i2)).getPath(), matrix);
            }
        }
        this.path.computeBounds(this.rect, false);
        float floatValue = ((FloatKeyframeAnimation) this.widthAnimation).getFloatValue();
        RectF rectF2 = this.rect;
        floatValue /= 2.0f;
        rectF2.set(rectF2.left - floatValue, this.rect.top - floatValue, this.rect.right + floatValue, this.rect.bottom + floatValue);
        rectF.set(this.rect);
        rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
        L.endSection(str);
    }

    private void applyDashPatternIfNeeded(Matrix matrix) {
        String str = "StrokeContent#applyDashPattern";
        L.beginSection(str);
        if (this.dashPatternAnimations.isEmpty()) {
            L.endSection(str);
            return;
        }
        float scale = Utils.getScale(matrix);
        for (int i = 0; i < this.dashPatternAnimations.size(); i++) {
            float[] fArr;
            this.dashPatternValues[i] = ((Float) ((BaseKeyframeAnimation) this.dashPatternAnimations.get(i)).getValue()).floatValue();
            if (i % 2 == 0) {
                fArr = this.dashPatternValues;
                if (fArr[i] < 1.0f) {
                    fArr[i] = 1.0f;
                }
            } else {
                fArr = this.dashPatternValues;
                if (fArr[i] < 0.1f) {
                    fArr[i] = 0.1f;
                }
            }
            fArr = this.dashPatternValues;
            fArr[i] = fArr[i] * scale;
        }
        BaseKeyframeAnimation baseKeyframeAnimation = this.dashPatternOffsetAnimation;
        this.paint.setPathEffect(new DashPathEffect(this.dashPatternValues, baseKeyframeAnimation == null ? 0.0f : ((Float) baseKeyframeAnimation.getValue()).floatValue()));
        L.endSection(str);
    }

    public void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @CallSuper
    public <T> void addValueCallback(T t, @Nullable LottieValueCallback<T> lottieValueCallback) {
        if (t == LottieProperty.OPACITY) {
            this.opacityAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.STROKE_WIDTH) {
            this.widthAnimation.setValueCallback(lottieValueCallback);
        } else if (t != LottieProperty.COLOR_FILTER) {
        } else {
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
                return;
            }
            this.colorFilterAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.colorFilterAnimation.addUpdateListener(this);
            this.layer.addAnimation(this.colorFilterAnimation);
        }
    }
}