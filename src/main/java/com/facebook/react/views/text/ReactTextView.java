package com.facebook.react.views.text;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils.TruncateAt;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.ViewGroup.LayoutParams;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintContextWrapper;
import androidx.core.os.EnvironmentCompat;
import androidx.core.view.GravityCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactCompoundView;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.views.view.ReactViewBackgroundManager;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import javax.annotation.Nullable;

public class ReactTextView extends AppCompatTextView implements ReactCompoundView {
    private static final LayoutParams EMPTY_LAYOUT_PARAMS = new LayoutParams(0, 0);
    private boolean mContainsImages;
    private int mDefaultGravityHorizontal = (getGravity() & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK);
    private int mDefaultGravityVertical = (getGravity() & 112);
    private TruncateAt mEllipsizeLocation = TruncateAt.END;
    private int mLinkifyMaskType = 0;
    private boolean mNotifyOnInlineViewLayout;
    private int mNumberOfLines = Integer.MAX_VALUE;
    private ReactViewBackgroundManager mReactBackgroundManager = new ReactViewBackgroundManager(this);
    private Spannable mSpanned;
    private int mTextAlign = 0;

    public boolean hasOverlappingRendering() {
        return false;
    }

    public ReactTextView(Context context) {
        super(context);
    }

    private WritableMap inlineViewJson(int i, int i2, int i3, int i4, int i5, int i6) {
        WritableMap createMap = Arguments.createMap();
        String str = Param.INDEX;
        String str2 = "visibility";
        if (i == 8) {
            createMap.putString(str2, "gone");
            createMap.putInt(str, i2);
        } else if (i == 0) {
            createMap.putString(str2, ViewProps.VISIBLE);
            createMap.putInt(str, i2);
            createMap.putDouble(ViewProps.LEFT, (double) PixelUtil.toDIPFromPixel((float) i3));
            createMap.putDouble(ViewProps.TOP, (double) PixelUtil.toDIPFromPixel((float) i4));
            createMap.putDouble(ViewProps.RIGHT, (double) PixelUtil.toDIPFromPixel((float) i5));
            createMap.putDouble(ViewProps.BOTTOM, (double) PixelUtil.toDIPFromPixel((float) i6));
        } else {
            createMap.putString(str2, EnvironmentCompat.MEDIA_UNKNOWN);
            createMap.putInt(str, i2);
        }
        return createMap;
    }

    private ReactContext getReactContext() {
        Context context = getContext();
        if (context instanceof TintContextWrapper) {
            context = ((TintContextWrapper) context).getBaseContext();
        }
        return (ReactContext) context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0104  */
    /* JADX WARNING: Missing block: B:39:0x00cc, code:
            if (r11 != false) goto L_0x00ce;
     */
    protected void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
        r18 = this;
        r7 = r18;
        r0 = r18.getText();
        r0 = r0 instanceof android.text.Spanned;
        if (r0 != 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r0 = r18.getReactContext();
        r1 = com.facebook.react.uimanager.UIManagerModule.class;
        r0 = r0.getNativeModule(r1);
        r8 = r0;
        r8 = (com.facebook.react.uimanager.UIManagerModule) r8;
        r0 = r18.getText();
        r9 = r0;
        r9 = (android.text.Spanned) r9;
        r10 = r18.getLayout();
        r0 = r9.length();
        r1 = com.facebook.react.views.text.TextInlineViewPlaceholderSpan.class;
        r11 = 0;
        r0 = r9.getSpans(r11, r0, r1);
        r12 = r0;
        r12 = (com.facebook.react.views.text.TextInlineViewPlaceholderSpan[]) r12;
        r0 = r7.mNotifyOnInlineViewLayout;
        if (r0 == 0) goto L_0x003c;
    L_0x0035:
        r0 = new java.util.ArrayList;
        r1 = r12.length;
        r0.<init>(r1);
        goto L_0x003d;
    L_0x003c:
        r0 = 0;
    L_0x003d:
        r13 = r0;
        r14 = r22 - r20;
        r15 = r23 - r21;
        r6 = r12.length;
        r5 = 0;
    L_0x0044:
        if (r5 >= r6) goto L_0x013e;
    L_0x0046:
        r0 = r12[r5];
        r1 = r0.getReactTag();
        r1 = r8.resolveView(r1);
        r2 = r9.getSpanStart(r0);
        r3 = r10.getLineForOffset(r2);
        r4 = r10.getEllipsisCount(r3);
        r16 = 1;
        if (r4 <= 0) goto L_0x0062;
    L_0x0060:
        r4 = 1;
        goto L_0x0063;
    L_0x0062:
        r4 = 0;
    L_0x0063:
        if (r4 == 0) goto L_0x0071;
    L_0x0065:
        r4 = r10.getLineStart(r3);
        r17 = r10.getEllipsisStart(r3);
        r4 = r4 + r17;
        if (r2 >= r4) goto L_0x011d;
    L_0x0071:
        r4 = r7.mNumberOfLines;
        if (r3 >= r4) goto L_0x011d;
    L_0x0075:
        r4 = r10.getLineEnd(r3);
        if (r2 < r4) goto L_0x007d;
    L_0x007b:
        goto L_0x011d;
    L_0x007d:
        r4 = r0.getWidth();
        r0 = r0.getHeight();
        r11 = r10.isRtlCharAt(r2);
        r17 = r5;
        r5 = r10.getParagraphDirection(r3);
        r23 = r6;
        r6 = -1;
        if (r5 != r6) goto L_0x0096;
    L_0x0094:
        r5 = 1;
        goto L_0x0097;
    L_0x0096:
        r5 = 0;
    L_0x0097:
        r6 = r9.length();
        r6 = r6 + -1;
        if (r2 != r6) goto L_0x00af;
    L_0x009f:
        if (r5 == 0) goto L_0x00a9;
    L_0x00a1:
        r5 = r10.getLineWidth(r3);
        r5 = (int) r5;
        r5 = r14 - r5;
        goto L_0x00cf;
    L_0x00a9:
        r5 = r10.getLineRight(r3);
        r5 = (int) r5;
        goto L_0x00ce;
    L_0x00af:
        if (r5 != r11) goto L_0x00b3;
    L_0x00b1:
        r6 = 1;
        goto L_0x00b4;
    L_0x00b3:
        r6 = 0;
    L_0x00b4:
        if (r6 == 0) goto L_0x00bb;
    L_0x00b6:
        r6 = r10.getPrimaryHorizontal(r2);
        goto L_0x00bf;
    L_0x00bb:
        r6 = r10.getSecondaryHorizontal(r2);
    L_0x00bf:
        r6 = (int) r6;
        if (r5 == 0) goto L_0x00cb;
    L_0x00c2:
        r5 = r10.getLineRight(r3);
        r5 = (int) r5;
        r5 = r5 - r6;
        r5 = r14 - r5;
        goto L_0x00cc;
    L_0x00cb:
        r5 = r6;
    L_0x00cc:
        if (r11 == 0) goto L_0x00cf;
    L_0x00ce:
        r5 = r5 - r4;
    L_0x00cf:
        if (r11 == 0) goto L_0x00d6;
    L_0x00d1:
        r6 = r18.getTotalPaddingRight();
        goto L_0x00da;
    L_0x00d6:
        r6 = r18.getTotalPaddingLeft();
    L_0x00da:
        r5 = r5 + r6;
        r6 = r20 + r5;
        r11 = r18.getTotalPaddingTop();
        r3 = r10.getLineBaseline(r3);
        r11 = r11 + r3;
        r11 = r11 - r0;
        r3 = r21 + r11;
        if (r14 <= r5) goto L_0x00f0;
    L_0x00eb:
        if (r15 > r11) goto L_0x00ee;
    L_0x00ed:
        goto L_0x00f0;
    L_0x00ee:
        r16 = 0;
    L_0x00f0:
        if (r16 == 0) goto L_0x00f5;
    L_0x00f2:
        r5 = 8;
        goto L_0x00f6;
    L_0x00f5:
        r5 = 0;
    L_0x00f6:
        r11 = r6 + r4;
        r4 = r3 + r0;
        r1.setVisibility(r5);
        r1.layout(r6, r3, r11, r4);
        r0 = r7.mNotifyOnInlineViewLayout;
        if (r0 == 0) goto L_0x0118;
    L_0x0104:
        r0 = r18;
        r1 = r5;
        r5 = r3;
        r3 = r6;
        r6 = r4;
        r4 = r5;
        r16 = r17;
        r5 = r11;
        r11 = r23;
        r0 = r0.inlineViewJson(r1, r2, r3, r4, r5, r6);
        r13.add(r0);
        goto L_0x0138;
    L_0x0118:
        r11 = r23;
        r16 = r17;
        goto L_0x0138;
    L_0x011d:
        r16 = r5;
        r11 = r6;
        r0 = 8;
        r1.setVisibility(r0);
        r0 = r7.mNotifyOnInlineViewLayout;
        if (r0 == 0) goto L_0x0138;
    L_0x0129:
        r1 = 8;
        r3 = -1;
        r4 = -1;
        r5 = -1;
        r6 = -1;
        r0 = r18;
        r0 = r0.inlineViewJson(r1, r2, r3, r4, r5, r6);
        r13.add(r0);
    L_0x0138:
        r5 = r16 + 1;
        r6 = r11;
        r11 = 0;
        goto L_0x0044;
    L_0x013e:
        r0 = r7.mNotifyOnInlineViewLayout;
        if (r0 == 0) goto L_0x0180;
    L_0x0142:
        r0 = new com.facebook.react.views.text.ReactTextView$1;
        r0.<init>();
        java.util.Collections.sort(r13, r0);
        r0 = com.facebook.react.bridge.Arguments.createArray();
        r1 = r13.iterator();
    L_0x0152:
        r2 = r1.hasNext();
        if (r2 == 0) goto L_0x0162;
    L_0x0158:
        r2 = r1.next();
        r2 = (com.facebook.react.bridge.WritableMap) r2;
        r0.pushMap(r2);
        goto L_0x0152;
    L_0x0162:
        r1 = com.facebook.react.bridge.Arguments.createMap();
        r2 = "inlineViews";
        r1.putArray(r2, r0);
        r0 = r18.getReactContext();
        r2 = com.facebook.react.uimanager.events.RCTEventEmitter.class;
        r0 = r0.getJSModule(r2);
        r0 = (com.facebook.react.uimanager.events.RCTEventEmitter) r0;
        r2 = r18.getId();
        r3 = "topInlineViewLayout";
        r0.receiveEvent(r2, r3, r1);
    L_0x0180:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.text.ReactTextView.onLayout(boolean, int, int, int, int):void");
    }

    public void setText(ReactTextUpdate reactTextUpdate) {
        this.mContainsImages = reactTextUpdate.containsImages();
        if (getLayoutParams() == null) {
            setLayoutParams(EMPTY_LAYOUT_PARAMS);
        }
        CharSequence text = reactTextUpdate.getText();
        int i = this.mLinkifyMaskType;
        if (i > 0) {
            Linkify.addLinks(text, i);
            setMovementMethod(LinkMovementMethod.getInstance());
        }
        setText(text);
        setPadding((int) Math.floor((double) reactTextUpdate.getPaddingLeft()), (int) Math.floor((double) reactTextUpdate.getPaddingTop()), (int) Math.floor((double) reactTextUpdate.getPaddingRight()), (int) Math.floor((double) reactTextUpdate.getPaddingBottom()));
        int textAlign = reactTextUpdate.getTextAlign();
        if (this.mTextAlign != textAlign) {
            this.mTextAlign = textAlign;
        }
        setGravityHorizontal(this.mTextAlign);
        if (VERSION.SDK_INT >= 23 && getBreakStrategy() != reactTextUpdate.getTextBreakStrategy()) {
            setBreakStrategy(reactTextUpdate.getTextBreakStrategy());
        }
        if (VERSION.SDK_INT >= 26 && getJustificationMode() != reactTextUpdate.getJustificationMode()) {
            setJustificationMode(reactTextUpdate.getJustificationMode());
        }
        requestLayout();
    }

    public int reactTagForTouch(float f, float f2) {
        CharSequence text = getText();
        int id = getId();
        int i = (int) f;
        int i2 = (int) f2;
        Layout layout = getLayout();
        if (layout == null) {
            return id;
        }
        i2 = layout.getLineForVertical(i2);
        int lineLeft = (int) layout.getLineLeft(i2);
        int lineRight = (int) layout.getLineRight(i2);
        if ((text instanceof Spanned) && i >= lineLeft && i <= lineRight) {
            Spanned spanned = (Spanned) text;
            try {
                i = layout.getOffsetForHorizontal(i2, (float) i);
                ReactTagSpan[] reactTagSpanArr = (ReactTagSpan[]) spanned.getSpans(i, i, ReactTagSpan.class);
                if (reactTagSpanArr != null) {
                    int length = text.length();
                    for (int i3 = 0; i3 < reactTagSpanArr.length; i3++) {
                        lineRight = spanned.getSpanStart(reactTagSpanArr[i3]);
                        int spanEnd = spanned.getSpanEnd(reactTagSpanArr[i3]);
                        if (spanEnd > i) {
                            spanEnd -= lineRight;
                            if (spanEnd <= length) {
                                id = reactTagSpanArr[i3].getReactTag();
                                length = spanEnd;
                            }
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Crash in HorizontalMeasurementProvider: ");
                stringBuilder.append(e.getMessage());
                FLog.e(ReactConstants.TAG, stringBuilder.toString());
            }
        }
        return id;
    }

    protected boolean verifyDrawable(Drawable drawable) {
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned spanned = (Spanned) getText();
            int i = 0;
            TextInlineImageSpan[] textInlineImageSpanArr = (TextInlineImageSpan[]) spanned.getSpans(0, spanned.length(), TextInlineImageSpan.class);
            int length = textInlineImageSpanArr.length;
            while (i < length) {
                if (textInlineImageSpanArr[i].getDrawable() == drawable) {
                    return true;
                }
                i++;
            }
        }
        return super.verifyDrawable(drawable);
    }

    public void invalidateDrawable(Drawable drawable) {
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned spanned = (Spanned) getText();
            int i = 0;
            TextInlineImageSpan[] textInlineImageSpanArr = (TextInlineImageSpan[]) spanned.getSpans(0, spanned.length(), TextInlineImageSpan.class);
            int length = textInlineImageSpanArr.length;
            while (i < length) {
                if (textInlineImageSpanArr[i].getDrawable() == drawable) {
                    invalidate();
                }
                i++;
            }
        }
        super.invalidateDrawable(drawable);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned spanned = (Spanned) getText();
            int i = 0;
            TextInlineImageSpan[] textInlineImageSpanArr = (TextInlineImageSpan[]) spanned.getSpans(0, spanned.length(), TextInlineImageSpan.class);
            int length = textInlineImageSpanArr.length;
            while (i < length) {
                textInlineImageSpanArr[i].onDetachedFromWindow();
                i++;
            }
        }
    }

    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned spanned = (Spanned) getText();
            int i = 0;
            TextInlineImageSpan[] textInlineImageSpanArr = (TextInlineImageSpan[]) spanned.getSpans(0, spanned.length(), TextInlineImageSpan.class);
            int length = textInlineImageSpanArr.length;
            while (i < length) {
                textInlineImageSpanArr[i].onStartTemporaryDetach();
                i++;
            }
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned spanned = (Spanned) getText();
            int i = 0;
            TextInlineImageSpan[] textInlineImageSpanArr = (TextInlineImageSpan[]) spanned.getSpans(0, spanned.length(), TextInlineImageSpan.class);
            int length = textInlineImageSpanArr.length;
            while (i < length) {
                textInlineImageSpanArr[i].onAttachedToWindow();
                i++;
            }
        }
    }

    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        if (this.mContainsImages && (getText() instanceof Spanned)) {
            Spanned spanned = (Spanned) getText();
            int i = 0;
            TextInlineImageSpan[] textInlineImageSpanArr = (TextInlineImageSpan[]) spanned.getSpans(0, spanned.length(), TextInlineImageSpan.class);
            int length = textInlineImageSpanArr.length;
            while (i < length) {
                textInlineImageSpanArr[i].onFinishTemporaryDetach();
                i++;
            }
        }
    }

    void setGravityHorizontal(int i) {
        if (i == 0) {
            i = this.mDefaultGravityHorizontal;
        }
        setGravity(i | ((getGravity() & -8) & -8388616));
    }

    void setGravityVertical(int i) {
        if (i == 0) {
            i = this.mDefaultGravityVertical;
        }
        setGravity(i | (getGravity() & -113));
    }

    public void setNumberOfLines(int i) {
        if (i == 0) {
            i = Integer.MAX_VALUE;
        }
        this.mNumberOfLines = i;
        boolean z = true;
        if (this.mNumberOfLines != 1) {
            z = false;
        }
        setSingleLine(z);
        setMaxLines(this.mNumberOfLines);
    }

    public void setEllipsizeLocation(TruncateAt truncateAt) {
        this.mEllipsizeLocation = truncateAt;
    }

    public void setNotifyOnInlineViewLayout(boolean z) {
        this.mNotifyOnInlineViewLayout = z;
    }

    public void updateView() {
        setEllipsize(this.mNumberOfLines == Integer.MAX_VALUE ? null : this.mEllipsizeLocation);
    }

    public void setBackgroundColor(int i) {
        this.mReactBackgroundManager.setBackgroundColor(i);
    }

    public void setBorderWidth(int i, float f) {
        this.mReactBackgroundManager.setBorderWidth(i, f);
    }

    public void setBorderColor(int i, float f, float f2) {
        this.mReactBackgroundManager.setBorderColor(i, f, f2);
    }

    public void setBorderRadius(float f) {
        this.mReactBackgroundManager.setBorderRadius(f);
    }

    public void setBorderRadius(float f, int i) {
        this.mReactBackgroundManager.setBorderRadius(f, i);
    }

    public void setBorderStyle(@Nullable String str) {
        this.mReactBackgroundManager.setBorderStyle(str);
    }

    public void setSpanned(Spannable spannable) {
        this.mSpanned = spannable;
    }

    public Spannable getSpanned() {
        return this.mSpanned;
    }

    public void setLinkifyMask(int i) {
        this.mLinkifyMaskType = i;
    }
}
