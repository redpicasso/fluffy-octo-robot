package com.facebook.react.views.common;

import android.content.Context;
import android.content.ContextWrapper;
import javax.annotation.Nullable;

public class ContextUtils {
    @Nullable
    public static <T> T findContextOfType(@Nullable Context context, Class<? extends T> cls) {
        T context2;
        while (!cls.isInstance(context2)) {
            if (!(context2 instanceof ContextWrapper)) {
                return null;
            }
            T baseContext = ((ContextWrapper) context2).getBaseContext();
            if (context2 == baseContext) {
                return null;
            }
            context2 = baseContext;
        }
        return context2;
    }
}
