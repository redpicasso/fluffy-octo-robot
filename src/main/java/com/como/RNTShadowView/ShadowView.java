package com.como.RNTShadowView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;

public class ShadowView extends ViewGroup {
    Paint borderPaint = new Paint();
    int borderRadius = 0;
    int borderShadowColor;
    double borderWidth;
    int margin;
    Bitmap shadowBitmap = null;
    int shadowColor;
    int shadowColorToDraw;
    int shadowOffsetX = 0;
    int shadowOffsetY = ((int) (Resources.getSystem().getDisplayMetrics().density * -2.0f));
    int shadowOpacity;
    int shadowRadius = 0;
    Paint viewPaint = new Paint();

    public ShadowView(Context context) {
        super(context);
        init();
    }

    public ShadowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ShadowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            this.shadowBitmap = createShadowForView();
            invalidate();
        }
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(0);
        this.viewPaint.setColor(i);
        createShadowColor();
        invalidate();
    }

    private void init() {
        setLayerType(0, this.viewPaint);
        this.viewPaint.setAntiAlias(true);
        this.borderPaint.setAntiAlias(true);
        this.borderPaint.setStyle(Style.STROKE);
        this.borderPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.shadowColor = ViewCompat.MEASURED_STATE_MASK;
        this.shadowColorToDraw = ViewCompat.MEASURED_STATE_MASK;
        createShadowColor();
        invalidate();
    }

    public void setBorderRadius(double d) {
        this.borderRadius = (int) (d * ((double) Resources.getSystem().getDisplayMetrics().density));
        invalidate();
    }

    public void setShadowOffsetX(double d) {
        this.shadowOffsetX = (int) (d * ((double) Resources.getSystem().getDisplayMetrics().density));
        invalidate();
    }

    public void setShadowOffsetY(double d) {
        this.shadowOffsetY = (int) (d * ((double) Resources.getSystem().getDisplayMetrics().density));
        invalidate();
    }

    public void setShadowColor(int i) {
        this.shadowColor = i;
        createShadowColor();
        invalidate();
    }

    public void setShadowOpacity(double d) {
        this.shadowOpacity = (int) (Math.min(Math.max(0.0d, d), 1.0d) * 255.0d);
        createShadowColor();
        invalidate();
    }

    public void setShadowRadius(double d) {
        this.shadowRadius = (int) Math.max(0.2d, d);
        this.margin = (int) (((double) this.shadowRadius) * 6.2d);
        invalidate();
    }

    public void setBorderColor(int i) {
        this.borderPaint.setColor(i);
        createShadowColor();
        invalidate();
    }

    public void setBorderWidth(double d) {
        this.borderWidth = (d * ((double) Resources.getSystem().getDisplayMetrics().density)) * 1.1d;
        invalidate();
    }

    private void createShadowColor() {
        int red = Color.red(this.shadowColor);
        int green = Color.green(this.shadowColor);
        int blue = Color.blue(this.shadowColor);
        int alpha = Color.alpha(this.shadowColor);
        int alpha2 = Color.alpha(this.borderPaint.getColor());
        int i = this.shadowOpacity;
        alpha2 = (int) (((double) i) * (((double) alpha2) / 255.0d));
        this.shadowColorToDraw = Color.argb((int) (((double) i) * (((double) alpha) / 255.0d)), red, green, blue);
        this.borderShadowColor = Color.argb(alpha2, red, green, blue);
    }

    protected void onDraw(Canvas canvas) {
        if (getWidth() != 0) {
            Rect rect = new Rect(0, 0, this.shadowBitmap.getWidth(), this.shadowBitmap.getHeight());
            int i = this.shadowOffsetX;
            int i2 = this.margin;
            canvas.drawBitmap(this.shadowBitmap, rect, new Rect(i - i2, this.shadowOffsetY - i2, (getWidth() + this.margin) + this.shadowOffsetX, (getHeight() + this.margin) + this.shadowOffsetY), this.viewPaint);
            RectF rectF = new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
            int i3 = this.borderRadius;
            canvas.drawRoundRect(rectF, (float) i3, (float) i3, this.viewPaint);
        }
    }

    public Bitmap createShadowForView() {
        Bitmap createBitmap = Bitmap.createBitmap(getWidth() + (this.margin * 2), getHeight() + (this.margin * 2), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(this.shadowColorToDraw);
        int i = this.margin;
        RectF rectF = new RectF((float) i, (float) i, (float) (createBitmap.getWidth() - this.margin), (float) (createBitmap.getHeight() - this.margin));
        i = this.borderRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        return BlurBuilder.blur(getContext(), createBitmap, (float) this.shadowRadius);
    }
}
