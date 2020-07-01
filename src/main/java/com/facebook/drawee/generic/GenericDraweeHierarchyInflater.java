package com.facebook.drawee.generic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import com.facebook.infer.annotation.ReturnsOwnership;
import javax.annotation.Nullable;

public class GenericDraweeHierarchyInflater {
    public static GenericDraweeHierarchy inflateHierarchy(Context context, @Nullable AttributeSet attributeSet) {
        return inflateBuilder(context, attributeSet).build();
    }

    public static GenericDraweeHierarchyBuilder inflateBuilder(Context context, @Nullable AttributeSet attributeSet) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("GenericDraweeHierarchyBuilder#inflateBuilder");
        }
        GenericDraweeHierarchyBuilder updateBuilder = updateBuilder(new GenericDraweeHierarchyBuilder(context.getResources()), context, attributeSet);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
        return updateBuilder;
    }

    /* JADX WARNING: Removed duplicated region for block: B:142:0x0211  */
    public static com.facebook.drawee.generic.GenericDraweeHierarchyBuilder updateBuilder(com.facebook.drawee.generic.GenericDraweeHierarchyBuilder r18, android.content.Context r19, @javax.annotation.Nullable android.util.AttributeSet r20) {
        /*
        r0 = r18;
        r1 = r19;
        r2 = r20;
        if (r2 == 0) goto L_0x021f;
    L_0x0008:
        r6 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy;
        r2 = r1.obtainStyledAttributes(r2, r6);
        r7 = r2.getIndexCount();	 Catch:{ all -> 0x01ff }
        r5 = 1;
        r6 = 0;
        r8 = 0;
        r9 = 1;
        r10 = 1;
        r11 = 1;
        r12 = 1;
        r13 = 1;
        r14 = 1;
        r15 = 1;
        r17 = 0;
    L_0x001e:
        if (r8 >= r7) goto L_0x01a5;
    L_0x0020:
        r3 = r2.getIndex(r8);	 Catch:{ all -> 0x01a2 }
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_actualImageScaleType;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x0031;
    L_0x0028:
        r3 = getScaleTypeFromXml(r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setActualImageScaleType(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x0031:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_placeholderImage;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x003e;
    L_0x0035:
        r3 = getDrawable(r1, r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setPlaceholderImage(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x003e:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_pressedStateOverlayImage;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x004b;
    L_0x0042:
        r3 = getDrawable(r1, r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setPressedStateOverlay(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x004b:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_progressBarImage;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x0058;
    L_0x004f:
        r3 = getDrawable(r1, r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setProgressBarImage(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x0058:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_fadeDuration;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x0065;
    L_0x005c:
        r4 = 0;
        r3 = r2.getInt(r3, r4);	 Catch:{ all -> 0x01a2 }
        r0.setFadeDuration(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x0065:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_viewAspectRatio;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x0072;
    L_0x0069:
        r4 = 0;
        r3 = r2.getFloat(r3, r4);	 Catch:{ all -> 0x01a2 }
        r0.setDesiredAspectRatio(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x0072:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_placeholderImageScaleType;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x007e;
    L_0x0076:
        r3 = getScaleTypeFromXml(r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setPlaceholderImageScaleType(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x007e:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_retryImage;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x008a;
    L_0x0082:
        r3 = getDrawable(r1, r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setRetryImage(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x008a:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_retryImageScaleType;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x0096;
    L_0x008e:
        r3 = getScaleTypeFromXml(r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setRetryImageScaleType(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x0096:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_failureImage;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x00a2;
    L_0x009a:
        r3 = getDrawable(r1, r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setFailureImage(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x00a2:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_failureImageScaleType;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x00ae;
    L_0x00a6:
        r3 = getScaleTypeFromXml(r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setFailureImageScaleType(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x00ae:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_progressBarImageScaleType;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x00ba;
    L_0x00b2:
        r3 = getScaleTypeFromXml(r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setProgressBarImageScaleType(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x00ba:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_progressBarAutoRotateInterval;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x00c6;
    L_0x00be:
        r3 = r2.getInteger(r3, r6);	 Catch:{ all -> 0x01a2 }
        r6 = r3;
    L_0x00c3:
        r4 = 0;
        goto L_0x019c;
    L_0x00c6:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_backgroundImage;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x00d2;
    L_0x00ca:
        r3 = getDrawable(r1, r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setBackground(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x00d2:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_overlayImage;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x00de;
    L_0x00d6:
        r3 = getDrawable(r1, r2, r3);	 Catch:{ all -> 0x01a2 }
        r0.setOverlay(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x00de:
        r4 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundAsCircle;	 Catch:{ all -> 0x01a2 }
        if (r3 != r4) goto L_0x00ef;
    L_0x00e2:
        r4 = getRoundingParams(r18);	 Catch:{ all -> 0x01a2 }
        r1 = 0;
        r3 = r2.getBoolean(r3, r1);	 Catch:{ all -> 0x01a2 }
        r4.setRoundAsCircle(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x00ef:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundedCornerRadius;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x00fc;
    L_0x00f3:
        r4 = r17;
        r1 = r2.getDimensionPixelSize(r3, r4);	 Catch:{ all -> 0x01a2 }
        r17 = r1;
        goto L_0x00c3;
    L_0x00fc:
        r4 = r17;
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundTopLeft;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x010a;
    L_0x0102:
        r1 = r2.getBoolean(r3, r9);	 Catch:{ all -> 0x01a2 }
        r9 = r1;
    L_0x0107:
        r17 = r4;
        goto L_0x00c3;
    L_0x010a:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundTopRight;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x0114;
    L_0x010e:
        r1 = r2.getBoolean(r3, r11);	 Catch:{ all -> 0x01a2 }
        r11 = r1;
        goto L_0x0107;
    L_0x0114:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundBottomLeft;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x011e;
    L_0x0118:
        r1 = r2.getBoolean(r3, r15);	 Catch:{ all -> 0x01a2 }
        r15 = r1;
        goto L_0x0107;
    L_0x011e:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundBottomRight;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x0128;
    L_0x0122:
        r1 = r2.getBoolean(r3, r13);	 Catch:{ all -> 0x01a2 }
        r13 = r1;
        goto L_0x0107;
    L_0x0128:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundTopStart;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x0132;
    L_0x012c:
        r1 = r2.getBoolean(r3, r10);	 Catch:{ all -> 0x01a2 }
        r10 = r1;
        goto L_0x0107;
    L_0x0132:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundTopEnd;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x013c;
    L_0x0136:
        r1 = r2.getBoolean(r3, r12);	 Catch:{ all -> 0x01a2 }
        r12 = r1;
        goto L_0x0107;
    L_0x013c:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundBottomStart;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x0146;
    L_0x0140:
        r1 = r2.getBoolean(r3, r5);	 Catch:{ all -> 0x01a2 }
        r5 = r1;
        goto L_0x0107;
    L_0x0146:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundBottomEnd;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x0150;
    L_0x014a:
        r1 = r2.getBoolean(r3, r14);	 Catch:{ all -> 0x01a2 }
        r14 = r1;
        goto L_0x0107;
    L_0x0150:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundWithOverlayColor;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x0164;
    L_0x0154:
        r1 = getRoundingParams(r18);	 Catch:{ all -> 0x01a2 }
        r17 = r4;
        r4 = 0;
        r3 = r2.getColor(r3, r4);	 Catch:{ all -> 0x01a2 }
        r1.setOverlayColor(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x0164:
        r17 = r4;
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundingBorderWidth;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x0179;
    L_0x016a:
        r1 = getRoundingParams(r18);	 Catch:{ all -> 0x01a2 }
        r4 = 0;
        r3 = r2.getDimensionPixelSize(r3, r4);	 Catch:{ all -> 0x01a2 }
        r3 = (float) r3;	 Catch:{ all -> 0x01a2 }
        r1.setBorderWidth(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x0179:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundingBorderColor;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x018b;
    L_0x017d:
        r1 = getRoundingParams(r18);	 Catch:{ all -> 0x01a2 }
        r4 = 0;
        r3 = r2.getColor(r3, r4);	 Catch:{ all -> 0x01a2 }
        r1.setBorderColor(r3);	 Catch:{ all -> 0x01a2 }
        goto L_0x00c3;
    L_0x018b:
        r1 = com.facebook.drawee.R.styleable.GenericDraweeHierarchy_roundingBorderPadding;	 Catch:{ all -> 0x01a2 }
        if (r3 != r1) goto L_0x00c3;
    L_0x018f:
        r1 = getRoundingParams(r18);	 Catch:{ all -> 0x01a2 }
        r4 = 0;
        r3 = r2.getDimensionPixelSize(r3, r4);	 Catch:{ all -> 0x01a2 }
        r3 = (float) r3;	 Catch:{ all -> 0x01a2 }
        r1.setPadding(r3);	 Catch:{ all -> 0x01a2 }
    L_0x019c:
        r8 = r8 + 1;
        r1 = r19;
        goto L_0x001e;
    L_0x01a2:
        r0 = move-exception;
        goto L_0x0208;
    L_0x01a5:
        r4 = 0;
        r2.recycle();
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 17;
        if (r1 < r2) goto L_0x01de;
    L_0x01af:
        r1 = r19.getResources();
        r1 = r1.getConfiguration();
        r1 = r1.getLayoutDirection();
        r2 = 1;
        if (r1 != r2) goto L_0x01de;
    L_0x01be:
        if (r9 == 0) goto L_0x01c4;
    L_0x01c0:
        if (r12 == 0) goto L_0x01c4;
    L_0x01c2:
        r1 = 1;
        goto L_0x01c5;
    L_0x01c4:
        r1 = 0;
    L_0x01c5:
        if (r11 == 0) goto L_0x01cb;
    L_0x01c7:
        if (r10 == 0) goto L_0x01cb;
    L_0x01c9:
        r2 = 1;
        goto L_0x01cc;
    L_0x01cb:
        r2 = 0;
    L_0x01cc:
        if (r13 == 0) goto L_0x01d2;
    L_0x01ce:
        if (r5 == 0) goto L_0x01d2;
    L_0x01d0:
        r5 = 1;
        goto L_0x01d3;
    L_0x01d2:
        r5 = 0;
    L_0x01d3:
        if (r15 == 0) goto L_0x01da;
    L_0x01d5:
        if (r14 == 0) goto L_0x01da;
    L_0x01d7:
        r16 = 1;
        goto L_0x01dc;
    L_0x01da:
        r16 = 0;
    L_0x01dc:
        r3 = r5;
        goto L_0x01fc;
    L_0x01de:
        if (r9 == 0) goto L_0x01e4;
    L_0x01e0:
        if (r10 == 0) goto L_0x01e4;
    L_0x01e2:
        r1 = 1;
        goto L_0x01e5;
    L_0x01e4:
        r1 = 0;
    L_0x01e5:
        if (r11 == 0) goto L_0x01eb;
    L_0x01e7:
        if (r12 == 0) goto L_0x01eb;
    L_0x01e9:
        r2 = 1;
        goto L_0x01ec;
    L_0x01eb:
        r2 = 0;
    L_0x01ec:
        if (r13 == 0) goto L_0x01f2;
    L_0x01ee:
        if (r14 == 0) goto L_0x01f2;
    L_0x01f0:
        r3 = 1;
        goto L_0x01f3;
    L_0x01f2:
        r3 = 0;
    L_0x01f3:
        if (r15 == 0) goto L_0x01f9;
    L_0x01f5:
        if (r5 == 0) goto L_0x01f9;
    L_0x01f7:
        r5 = 1;
        goto L_0x01fa;
    L_0x01f9:
        r5 = 0;
    L_0x01fa:
        r16 = r5;
    L_0x01fc:
        r4 = r17;
        goto L_0x0226;
    L_0x01ff:
        r0 = move-exception;
        r5 = 1;
        r9 = 1;
        r10 = 1;
        r11 = 1;
        r12 = 1;
        r13 = 1;
        r14 = 1;
        r15 = 1;
    L_0x0208:
        r2.recycle();
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 17;
        if (r1 < r2) goto L_0x021e;
    L_0x0211:
        r1 = r19.getResources();
        r1 = r1.getConfiguration();
        r1 = r1.getLayoutDirection();
        r2 = 1;
    L_0x021e:
        throw r0;
    L_0x021f:
        r2 = 1;
        r4 = 0;
        r1 = 1;
        r3 = 1;
        r6 = 0;
        r16 = 1;
    L_0x0226:
        r5 = r18.getProgressBarImage();
        if (r5 == 0) goto L_0x023a;
    L_0x022c:
        if (r6 <= 0) goto L_0x023a;
    L_0x022e:
        r5 = new com.facebook.drawee.drawable.AutoRotateDrawable;
        r7 = r18.getProgressBarImage();
        r5.<init>(r7, r6);
        r0.setProgressBarImage(r5);
    L_0x023a:
        if (r4 <= 0) goto L_0x0257;
    L_0x023c:
        r5 = getRoundingParams(r18);
        if (r1 == 0) goto L_0x0244;
    L_0x0242:
        r1 = (float) r4;
        goto L_0x0245;
    L_0x0244:
        r1 = 0;
    L_0x0245:
        if (r2 == 0) goto L_0x0249;
    L_0x0247:
        r2 = (float) r4;
        goto L_0x024a;
    L_0x0249:
        r2 = 0;
    L_0x024a:
        if (r3 == 0) goto L_0x024e;
    L_0x024c:
        r3 = (float) r4;
        goto L_0x024f;
    L_0x024e:
        r3 = 0;
    L_0x024f:
        if (r16 == 0) goto L_0x0253;
    L_0x0251:
        r4 = (float) r4;
        goto L_0x0254;
    L_0x0253:
        r4 = 0;
    L_0x0254:
        r5.setCornersRadii(r1, r2, r3, r4);
    L_0x0257:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.drawee.generic.GenericDraweeHierarchyInflater.updateBuilder(com.facebook.drawee.generic.GenericDraweeHierarchyBuilder, android.content.Context, android.util.AttributeSet):com.facebook.drawee.generic.GenericDraweeHierarchyBuilder");
    }

    @ReturnsOwnership
    private static RoundingParams getRoundingParams(GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder) {
        if (genericDraweeHierarchyBuilder.getRoundingParams() == null) {
            genericDraweeHierarchyBuilder.setRoundingParams(new RoundingParams());
        }
        return genericDraweeHierarchyBuilder.getRoundingParams();
    }

    @Nullable
    private static Drawable getDrawable(Context context, TypedArray typedArray, int i) {
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId == 0) {
            return null;
        }
        return context.getResources().getDrawable(resourceId);
    }

    @Nullable
    private static ScaleType getScaleTypeFromXml(TypedArray typedArray, int i) {
        switch (typedArray.getInt(i, -2)) {
            case -1:
                return null;
            case 0:
                return ScaleType.FIT_XY;
            case 1:
                return ScaleType.FIT_START;
            case 2:
                return ScaleType.FIT_CENTER;
            case 3:
                return ScaleType.FIT_END;
            case 4:
                return ScaleType.CENTER;
            case 5:
                return ScaleType.CENTER_INSIDE;
            case 6:
                return ScaleType.CENTER_CROP;
            case 7:
                return ScaleType.FOCUS_CROP;
            case 8:
                return ScaleType.FIT_BOTTOM_START;
            default:
                throw new RuntimeException("XML attribute not specified!");
        }
    }
}
