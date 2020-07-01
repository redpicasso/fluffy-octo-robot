package com.airbnb.lottie.animation;

import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.os.LocaleList;
import androidx.annotation.NonNull;

public class LPaint extends Paint {
    public void setTextLocales(@NonNull LocaleList localeList) {
    }

    public LPaint(int i) {
        super(i);
    }

    public LPaint(Mode mode) {
        setXfermode(new PorterDuffXfermode(mode));
    }

    public LPaint(int i, Mode mode) {
        super(i);
        setXfermode(new PorterDuffXfermode(mode));
    }
}
