package com.facebook.react.views.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.util.TypedValue;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.uimanager.ViewProps;

public class ReactDrawableHelper {
    private static final TypedValue sResolveOutValue = new TypedValue();

    @TargetApi(21)
    public static Drawable createDrawableFromJSDescription(Context context, ReadableMap readableMap) {
        String string = readableMap.getString("type");
        if ("ThemeAttrAndroid".equals(string)) {
            String string2 = readableMap.getString("attribute");
            SoftAssertions.assertNotNull(string2);
            int identifier = context.getResources().getIdentifier(string2, "attr", "android");
            String str = "Attribute ";
            StringBuilder stringBuilder;
            if (identifier == 0) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(string2);
                stringBuilder.append(" couldn't be found in the resource list");
                throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
            } else if (!context.getTheme().resolveAttribute(identifier, sResolveOutValue, true)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(string2);
                stringBuilder.append(" couldn't be resolved into a drawable");
                throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
            } else if (VERSION.SDK_INT >= 21) {
                return context.getResources().getDrawable(sResolveOutValue.resourceId, context.getTheme());
            } else {
                return context.getResources().getDrawable(sResolveOutValue.resourceId);
            }
        } else if (!"RippleAndroid".equals(string)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Invalid type for android drawable: ");
            stringBuilder2.append(string);
            throw new JSApplicationIllegalArgumentException(stringBuilder2.toString());
        } else if (VERSION.SDK_INT >= 21) {
            int i;
            Drawable drawable;
            string = ViewProps.COLOR;
            if (readableMap.hasKey(string) && !readableMap.isNull(string)) {
                i = readableMap.getInt(string);
            } else if (context.getTheme().resolveAttribute(16843820, sResolveOutValue, true)) {
                i = context.getResources().getColor(sResolveOutValue.resourceId);
            } else {
                throw new JSApplicationIllegalArgumentException("Attribute colorControlHighlight couldn't be resolved into a drawable");
            }
            string = "borderless";
            if (readableMap.hasKey(string) && !readableMap.isNull(string) && readableMap.getBoolean(string)) {
                drawable = null;
            } else {
                drawable = new ColorDrawable(-1);
            }
            return new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{i}), null, drawable);
        } else {
            throw new JSApplicationIllegalArgumentException("Ripple drawable is not available on android API <21");
        }
    }
}
