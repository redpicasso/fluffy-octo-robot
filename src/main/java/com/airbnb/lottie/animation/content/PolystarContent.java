package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.PolystarShape.Type;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

public class PolystarContent implements PathContent, AnimationListener, KeyPathElementContent {
    private static final float POLYGON_MAGIC_NUMBER = 0.25f;
    private static final float POLYSTAR_MAGIC_NUMBER = 0.47829f;
    private final boolean hidden;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> innerRadiusAnimation;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> innerRoundednessAnimation;
    private boolean isPathValid;
    private final LottieDrawable lottieDrawable;
    private final String name;
    private final BaseKeyframeAnimation<?, Float> outerRadiusAnimation;
    private final BaseKeyframeAnimation<?, Float> outerRoundednessAnimation;
    private final Path path = new Path();
    private final BaseKeyframeAnimation<?, Float> pointsAnimation;
    private final BaseKeyframeAnimation<?, PointF> positionAnimation;
    private final BaseKeyframeAnimation<?, Float> rotationAnimation;
    private CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();
    private final Type type;

    /* renamed from: com.airbnb.lottie.animation.content.PolystarContent$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type = new int[Type.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.airbnb.lottie.model.content.PolystarShape.Type.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type = r0;
            r0 = $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.airbnb.lottie.model.content.PolystarShape.Type.STAR;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.airbnb.lottie.model.content.PolystarShape.Type.POLYGON;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.PolystarContent.1.<clinit>():void");
        }
    }

    public PolystarContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable;
        this.name = polystarShape.getName();
        this.type = polystarShape.getType();
        this.hidden = polystarShape.isHidden();
        this.pointsAnimation = polystarShape.getPoints().createAnimation();
        this.positionAnimation = polystarShape.getPosition().createAnimation();
        this.rotationAnimation = polystarShape.getRotation().createAnimation();
        this.outerRadiusAnimation = polystarShape.getOuterRadius().createAnimation();
        this.outerRoundednessAnimation = polystarShape.getOuterRoundedness().createAnimation();
        if (this.type == Type.STAR) {
            this.innerRadiusAnimation = polystarShape.getInnerRadius().createAnimation();
            this.innerRoundednessAnimation = polystarShape.getInnerRoundedness().createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(this.pointsAnimation);
        baseLayer.addAnimation(this.positionAnimation);
        baseLayer.addAnimation(this.rotationAnimation);
        baseLayer.addAnimation(this.outerRadiusAnimation);
        baseLayer.addAnimation(this.outerRoundednessAnimation);
        if (this.type == Type.STAR) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        this.pointsAnimation.addUpdateListener(this);
        this.positionAnimation.addUpdateListener(this);
        this.rotationAnimation.addUpdateListener(this);
        this.outerRadiusAnimation.addUpdateListener(this);
        this.outerRoundednessAnimation.addUpdateListener(this);
        if (this.type == Type.STAR) {
            this.innerRadiusAnimation.addUpdateListener(this);
            this.innerRoundednessAnimation.addUpdateListener(this);
        }
    }

    public void onValueChanged() {
        invalidate();
    }

    private void invalidate() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    public void setContents(List<Content> list, List<Content> list2) {
        for (int i = 0; i < list.size(); i++) {
            Content content = (Content) list.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.trimPaths.addTrimPath(trimPathContent);
                    trimPathContent.addListener(this);
                }
            }
        }
    }

    public Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[this.type.ordinal()];
        if (i == 1) {
            createStarPath();
        } else if (i == 2) {
            createPolygonPath();
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    public String getName() {
        return this.name;
    }

    private void createStarPath() {
        float f;
        int i;
        double d;
        double d2;
        float cos;
        float sin;
        float floatValue = ((Float) this.pointsAnimation.getValue()).floatValue();
        BaseKeyframeAnimation baseKeyframeAnimation = this.rotationAnimation;
        double toRadians = Math.toRadians((baseKeyframeAnimation == null ? 0.0d : (double) ((Float) baseKeyframeAnimation.getValue()).floatValue()) - 90.0d);
        double d3 = (double) floatValue;
        float f2 = (float) (6.283185307179586d / d3);
        float f3 = f2 / 2.0f;
        floatValue -= (float) ((int) floatValue);
        int i2 = (floatValue > 0.0f ? 1 : (floatValue == 0.0f ? 0 : -1));
        if (i2 != 0) {
            toRadians += (double) ((1.0f - floatValue) * f3);
        }
        float floatValue2 = ((Float) this.outerRadiusAnimation.getValue()).floatValue();
        float floatValue3 = ((Float) this.innerRadiusAnimation.getValue()).floatValue();
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.innerRoundednessAnimation;
        float floatValue4 = baseKeyframeAnimation2 != null ? ((Float) baseKeyframeAnimation2.getValue()).floatValue() / 100.0f : 0.0f;
        BaseKeyframeAnimation baseKeyframeAnimation3 = this.outerRoundednessAnimation;
        float floatValue5 = baseKeyframeAnimation3 != null ? ((Float) baseKeyframeAnimation3.getValue()).floatValue() / 100.0f : 0.0f;
        if (i2 != 0) {
            f = ((floatValue2 - floatValue3) * floatValue) + floatValue3;
            i = i2;
            d = (double) f;
            d2 = d3;
            cos = (float) (d * Math.cos(toRadians));
            sin = (float) (d * Math.sin(toRadians));
            this.path.moveTo(cos, sin);
            toRadians += (double) ((f2 * floatValue) / 2.0f);
        } else {
            d2 = d3;
            i = i2;
            double d4 = (double) floatValue2;
            float cos2 = (float) (Math.cos(toRadians) * d4);
            sin = (float) (d4 * Math.sin(toRadians));
            this.path.moveTo(cos2, sin);
            toRadians += (double) f3;
            cos = cos2;
            sin = sin;
            f = 0.0f;
        }
        d = Math.ceil(d2) * 2.0d;
        int i3 = 0;
        double d5 = toRadians;
        float f4 = f3;
        int i4 = 0;
        while (true) {
            double d6 = (double) i4;
            if (d6 < d) {
                float f5;
                float f6;
                float f7;
                float f8;
                float f9;
                float f10;
                float f11 = i3 != 0 ? floatValue2 : floatValue3;
                int i5 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
                if (i5 == 0 || d6 != d - 2.0d) {
                    f5 = f4;
                } else {
                    f5 = f4;
                    f4 = (f2 * floatValue) / 2.0f;
                }
                if (i5 == 0 || d6 != d - 1.0d) {
                    f6 = f2;
                    f7 = floatValue3;
                    f2 = f11;
                    f11 = floatValue2;
                } else {
                    f6 = f2;
                    f11 = floatValue2;
                    f7 = floatValue3;
                    f2 = f;
                }
                double d7 = (double) f2;
                float f12 = f4;
                f4 = (float) (d7 * Math.cos(d5));
                f2 = (float) (d7 * Math.sin(d5));
                if (floatValue4 == 0.0f && floatValue5 == 0.0f) {
                    this.path.lineTo(f4, f2);
                    f8 = f2;
                    f9 = floatValue4;
                    f10 = floatValue5;
                    floatValue3 = f;
                } else {
                    f9 = floatValue4;
                    f10 = floatValue5;
                    d7 = (double) ((float) (Math.atan2((double) sin, (double) cos) - 1.5707963267948966d));
                    floatValue4 = (float) Math.cos(d7);
                    floatValue2 = (float) Math.sin(d7);
                    floatValue3 = f;
                    f8 = f2;
                    float f13 = cos;
                    double atan2 = (double) ((float) (Math.atan2((double) f2, (double) f4) - 1.5707963267948966d));
                    floatValue5 = (float) Math.cos(atan2);
                    f2 = (float) Math.sin(atan2);
                    cos = i3 != 0 ? f9 : f10;
                    float f14 = ((i3 != 0 ? f7 : f11) * cos) * POLYSTAR_MAGIC_NUMBER;
                    floatValue4 *= f14;
                    f14 *= floatValue2;
                    float f15 = ((i3 != 0 ? f11 : f7) * (i3 != 0 ? f10 : f9)) * POLYSTAR_MAGIC_NUMBER;
                    floatValue5 *= f15;
                    f15 *= f2;
                    if (i != 0) {
                        if (i4 == 0) {
                            floatValue4 *= floatValue;
                            f14 *= floatValue;
                        } else if (d6 == d - 1.0d) {
                            floatValue5 *= floatValue;
                            f15 *= floatValue;
                        }
                    }
                    this.path.cubicTo(f13 - floatValue4, sin - f14, f4 + floatValue5, f8 + f15, f4, f8);
                }
                d5 += (double) f12;
                i3 ^= 1;
                i4++;
                cos = f4;
                f = floatValue3;
                floatValue2 = f11;
                f2 = f6;
                f4 = f5;
                floatValue3 = f7;
                floatValue4 = f9;
                floatValue5 = f10;
                sin = f8;
            } else {
                PointF pointF = (PointF) this.positionAnimation.getValue();
                this.path.offset(pointF.x, pointF.y);
                this.path.close();
                return;
            }
        }
    }

    private void createPolygonPath() {
        int floor = (int) Math.floor((double) ((Float) this.pointsAnimation.getValue()).floatValue());
        BaseKeyframeAnimation baseKeyframeAnimation = this.rotationAnimation;
        double toRadians = Math.toRadians((baseKeyframeAnimation == null ? 0.0d : (double) ((Float) baseKeyframeAnimation.getValue()).floatValue()) - 90.0d);
        double d = (double) floor;
        float f = (float) (6.283185307179586d / d);
        float floatValue = ((Float) this.outerRoundednessAnimation.getValue()).floatValue() / 100.0f;
        float floatValue2 = ((Float) this.outerRadiusAnimation.getValue()).floatValue();
        double d2 = (double) floatValue2;
        float cos = (float) (Math.cos(toRadians) * d2);
        float sin = (float) (Math.sin(toRadians) * d2);
        this.path.moveTo(cos, sin);
        double d3 = (double) f;
        toRadians += d3;
        d = Math.ceil(d);
        floor = 0;
        while (((double) floor) < d) {
            double d4;
            int i;
            double d5;
            double d6;
            float cos2 = (float) (Math.cos(toRadians) * d2);
            double d7 = d;
            float sin2 = (float) (d2 * Math.sin(toRadians));
            if (floatValue != 0.0f) {
                d4 = d2;
                i = floor;
                d5 = toRadians;
                double atan2 = (double) ((float) (Math.atan2((double) sin, (double) cos) - 1.5707963267948966d));
                d6 = d3;
                double atan22 = (double) ((float) (Math.atan2((double) sin2, (double) cos2) - 1.5707963267948966d));
                float f2 = (floatValue2 * floatValue) * POLYGON_MAGIC_NUMBER;
                this.path.cubicTo(cos - (((float) Math.cos(atan2)) * f2), sin - (((float) Math.sin(atan2)) * f2), cos2 + (((float) Math.cos(atan22)) * f2), sin2 + (f2 * ((float) Math.sin(atan22))), cos2, sin2);
            } else {
                d5 = toRadians;
                d4 = d2;
                d6 = d3;
                i = floor;
                this.path.lineTo(cos2, sin2);
            }
            toRadians = d5 + d6;
            floor = i + 1;
            sin = sin2;
            cos = cos2;
            d = d7;
            d2 = d4;
            d3 = d6;
        }
        PointF pointF = (PointF) this.positionAnimation.getValue();
        this.path.offset(pointF.x, pointF.y);
        this.path.close();
    }

    public void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    public <T> void addValueCallback(T t, @Nullable LottieValueCallback<T> lottieValueCallback) {
        if (t == LottieProperty.POLYSTAR_POINTS) {
            this.pointsAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_ROTATION) {
            this.rotationAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
        } else {
            BaseKeyframeAnimation baseKeyframeAnimation;
            if (t == LottieProperty.POLYSTAR_INNER_RADIUS) {
                baseKeyframeAnimation = this.innerRadiusAnimation;
                if (baseKeyframeAnimation != null) {
                    baseKeyframeAnimation.setValueCallback(lottieValueCallback);
                    return;
                }
            }
            if (t == LottieProperty.POLYSTAR_OUTER_RADIUS) {
                this.outerRadiusAnimation.setValueCallback(lottieValueCallback);
                return;
            }
            if (t == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS) {
                baseKeyframeAnimation = this.innerRoundednessAnimation;
                if (baseKeyframeAnimation != null) {
                    baseKeyframeAnimation.setValueCallback(lottieValueCallback);
                    return;
                }
            }
            if (t == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
                this.outerRoundednessAnimation.setValueCallback(lottieValueCallback);
            }
        }
    }
}
