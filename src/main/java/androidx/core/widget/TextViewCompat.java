package androidx.core.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.annotation.StyleRes;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.text.PrecomputedTextCompat.Params;
import androidx.core.text.PrecomputedTextCompat.Params.Builder;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    private static final int LINES = 1;
    private static final String LOG_TAG = "TextViewCompat";
    private static Field sMaxModeField;
    private static boolean sMaxModeFieldFetched;
    private static Field sMaximumField;
    private static boolean sMaximumFieldFetched;
    private static Field sMinModeField;
    private static boolean sMinModeFieldFetched;
    private static Field sMinimumField;
    private static boolean sMinimumFieldFetched;

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoSizeTextType {
    }

    @RequiresApi(26)
    private static class OreoCallback implements Callback {
        private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
        private final Callback mCallback;
        private boolean mCanUseMenuBuilderReferences;
        private boolean mInitializedMenuBuilderReferences = false;
        private Class mMenuBuilderClass;
        private Method mMenuBuilderRemoveItemAtMethod;
        private final TextView mTextView;

        OreoCallback(Callback callback, TextView textView) {
            this.mCallback = callback;
            this.mTextView = textView;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mCallback.onCreateActionMode(actionMode, menu);
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            recomputeProcessTextMenuItems(menu);
            return this.mCallback.onPrepareActionMode(actionMode, menu);
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mCallback.onActionItemClicked(actionMode, menuItem);
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            this.mCallback.onDestroyActionMode(actionMode);
        }

        /* JADX WARNING: Removed duplicated region for block: B:25:0x00ab A:{RETURN, ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:6:0x0034} */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x00ab A:{RETURN, ExcHandler: java.lang.NoSuchMethodException (unused java.lang.NoSuchMethodException), Splitter: B:6:0x0034} */
        /* JADX WARNING: Removed duplicated region for block: B:5:0x002d A:{ExcHandler: java.lang.ClassNotFoundException (unused java.lang.ClassNotFoundException), Splitter: B:3:0x0014} */
        /* JADX WARNING: Missing block: B:5:0x002d, code:
            r8.mMenuBuilderClass = null;
            r8.mMenuBuilderRemoveItemAtMethod = null;
            r8.mCanUseMenuBuilderReferences = false;
     */
        /* JADX WARNING: Missing block: B:25:0x00ab, code:
            return;
     */
        private void recomputeProcessTextMenuItems(android.view.Menu r9) {
            /*
            r8 = this;
            r0 = r8.mTextView;
            r0 = r0.getContext();
            r1 = r0.getPackageManager();
            r2 = r8.mInitializedMenuBuilderReferences;
            r3 = "removeItemAt";
            r4 = 0;
            r5 = 1;
            if (r2 != 0) goto L_0x0034;
        L_0x0012:
            r8.mInitializedMenuBuilderReferences = r5;
            r2 = "com.android.internal.view.menu.MenuBuilder";
            r2 = java.lang.Class.forName(r2);	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            r8.mMenuBuilderClass = r2;	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            r2 = r8.mMenuBuilderClass;	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            r6 = new java.lang.Class[r5];	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            r7 = java.lang.Integer.TYPE;	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            r6[r4] = r7;	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            r2 = r2.getDeclaredMethod(r3, r6);	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            r8.mMenuBuilderRemoveItemAtMethod = r2;	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            r8.mCanUseMenuBuilderReferences = r5;	 Catch:{ ClassNotFoundException -> 0x002d, ClassNotFoundException -> 0x002d }
            goto L_0x0034;
        L_0x002d:
            r2 = 0;
            r8.mMenuBuilderClass = r2;
            r8.mMenuBuilderRemoveItemAtMethod = r2;
            r8.mCanUseMenuBuilderReferences = r4;
        L_0x0034:
            r2 = r8.mCanUseMenuBuilderReferences;	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            if (r2 == 0) goto L_0x0043;
        L_0x0038:
            r2 = r8.mMenuBuilderClass;	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r2 = r2.isInstance(r9);	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            if (r2 == 0) goto L_0x0043;
        L_0x0040:
            r2 = r8.mMenuBuilderRemoveItemAtMethod;	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            goto L_0x0051;
        L_0x0043:
            r2 = r9.getClass();	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r6 = new java.lang.Class[r5];	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r7 = java.lang.Integer.TYPE;	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r6[r4] = r7;	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r2 = r2.getDeclaredMethod(r3, r6);	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
        L_0x0051:
            r3 = r9.size();	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r3 = r3 - r5;
        L_0x0056:
            if (r3 < 0) goto L_0x0080;
        L_0x0058:
            r6 = r9.getItem(r3);	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r7 = r6.getIntent();	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            if (r7 == 0) goto L_0x007d;
        L_0x0062:
            r7 = "android.intent.action.PROCESS_TEXT";
            r6 = r6.getIntent();	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r6 = r6.getAction();	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r6 = r7.equals(r6);	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            if (r6 == 0) goto L_0x007d;
        L_0x0072:
            r6 = new java.lang.Object[r5];	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r7 = java.lang.Integer.valueOf(r3);	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r6[r4] = r7;	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
            r2.invoke(r9, r6);	 Catch:{ NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab, NoSuchMethodException -> 0x00ab }
        L_0x007d:
            r3 = r3 + -1;
            goto L_0x0056;
        L_0x0080:
            r0 = r8.getSupportedActivities(r0, r1);
            r2 = 0;
        L_0x0085:
            r3 = r0.size();
            if (r2 >= r3) goto L_0x00ab;
        L_0x008b:
            r3 = r0.get(r2);
            r3 = (android.content.pm.ResolveInfo) r3;
            r6 = r2 + 100;
            r7 = r3.loadLabel(r1);
            r6 = r9.add(r4, r4, r6, r7);
            r7 = r8.mTextView;
            r3 = r8.createProcessTextIntentForResolveInfo(r3, r7);
            r3 = r6.setIntent(r3);
            r3.setShowAsAction(r5);
            r2 = r2 + 1;
            goto L_0x0085;
        L_0x00ab:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.TextViewCompat.OreoCallback.recomputeProcessTextMenuItems(android.view.Menu):void");
        }

        private List<ResolveInfo> getSupportedActivities(Context context, PackageManager packageManager) {
            List<ResolveInfo> arrayList = new ArrayList();
            if (!(context instanceof Activity)) {
                return arrayList;
            }
            for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(createProcessTextIntent(), 0)) {
                if (isSupportedActivity(resolveInfo, context)) {
                    arrayList.add(resolveInfo);
                }
            }
            return arrayList;
        }

        private boolean isSupportedActivity(ResolveInfo resolveInfo, Context context) {
            boolean z = true;
            if (context.getPackageName().equals(resolveInfo.activityInfo.packageName)) {
                return true;
            }
            if (!resolveInfo.activityInfo.exported) {
                return false;
            }
            if (!(resolveInfo.activityInfo.permission == null || context.checkSelfPermission(resolveInfo.activityInfo.permission) == 0)) {
                z = false;
            }
            return z;
        }

        private Intent createProcessTextIntentForResolveInfo(ResolveInfo resolveInfo, TextView textView) {
            return createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", isEditable(textView) ^ 1).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        }

        private boolean isEditable(TextView textView) {
            return (textView instanceof Editable) && textView.onCheckIsTextEditor() && textView.isEnabled();
        }

        private Intent createProcessTextIntent() {
            return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
        }
    }

    private TextViewCompat() {
    }

    private static Field retrieveField(String str) {
        Field field = null;
        try {
            field = TextView.class.getDeclaredField(str);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException unused) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not retrieve ");
            stringBuilder.append(str);
            stringBuilder.append(" field.");
            Log.e(LOG_TAG, stringBuilder.toString());
            return field;
        }
    }

    private static int retrieveIntFromField(Field field, TextView textView) {
        try {
            field = field.getInt(textView);
            return field;
        } catch (IllegalAccessException unused) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not retrieve value of ");
            stringBuilder.append(field.getName());
            stringBuilder.append(" field.");
            Log.d(LOG_TAG, stringBuilder.toString());
            return -1;
        }
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        } else if (VERSION.SDK_INT >= 17) {
            Object obj = 1;
            if (textView.getLayoutDirection() != 1) {
                obj = null;
            }
            Drawable drawable5 = obj != null ? drawable3 : drawable;
            if (obj == null) {
                drawable = drawable3;
            }
            textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4);
        } else {
            textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        }
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        } else if (VERSION.SDK_INT >= 17) {
            Object obj = 1;
            if (textView.getLayoutDirection() != 1) {
                obj = null;
            }
            Drawable drawable5 = obj != null ? drawable3 : drawable;
            if (obj == null) {
                drawable = drawable3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable5, drawable2, drawable, drawable4);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
        if (VERSION.SDK_INT >= 18) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
        } else if (VERSION.SDK_INT >= 17) {
            Object obj = 1;
            if (textView.getLayoutDirection() != 1) {
                obj = null;
            }
            int i5 = obj != null ? i3 : i;
            if (obj == null) {
                i = i3;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(i5, i2, i, i4);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
        }
    }

    public static int getMaxLines(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 16) {
            return textView.getMaxLines();
        }
        if (!sMaxModeFieldFetched) {
            sMaxModeField = retrieveField("mMaxMode");
            sMaxModeFieldFetched = true;
        }
        Field field = sMaxModeField;
        if (field != null && retrieveIntFromField(field, textView) == 1) {
            if (!sMaximumFieldFetched) {
                sMaximumField = retrieveField("mMaximum");
                sMaximumFieldFetched = true;
            }
            field = sMaximumField;
            if (field != null) {
                return retrieveIntFromField(field, textView);
            }
        }
        return -1;
    }

    public static int getMinLines(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 16) {
            return textView.getMinLines();
        }
        if (!sMinModeFieldFetched) {
            sMinModeField = retrieveField("mMinMode");
            sMinModeFieldFetched = true;
        }
        Field field = sMinModeField;
        if (field != null && retrieveIntFromField(field, textView) == 1) {
            if (!sMinimumFieldFetched) {
                sMinimumField = retrieveField("mMinimum");
                sMinimumFieldFetched = true;
            }
            field = sMinimumField;
            if (field != null) {
                return retrieveIntFromField(field, textView);
            }
        }
        return -1;
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
        if (VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(i);
        } else {
            textView.setTextAppearance(textView.getContext(), i);
        }
    }

    @NonNull
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 18) {
            return textView.getCompoundDrawablesRelative();
        }
        if (VERSION.SDK_INT < 17) {
            return textView.getCompoundDrawables();
        }
        Object obj = 1;
        if (textView.getLayoutDirection() != 1) {
            obj = null;
        }
        Drawable[] compoundDrawables = textView.getCompoundDrawables();
        if (obj != null) {
            Drawable drawable = compoundDrawables[2];
            Drawable drawable2 = compoundDrawables[0];
            compoundDrawables[0] = drawable;
            compoundDrawables[2] = drawable2;
        }
        return compoundDrawables;
    }

    public static void setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, int i) {
        if (VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeWithDefaults(i);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(i);
        }
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, int i, int i2, int i3, int i4) throws IllegalArgumentException {
        if (VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        }
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull int[] iArr, int i) throws IllegalArgumentException {
        if (VERSION.SDK_INT >= 27) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        } else if (textView instanceof AutoSizeableTextView) {
            ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        }
    }

    public static int getAutoSizeTextType(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextType();
        }
        return textView instanceof AutoSizeableTextView ? ((AutoSizeableTextView) textView).getAutoSizeTextType() : 0;
    }

    public static int getAutoSizeStepGranularity(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeStepGranularity();
        }
        return textView instanceof AutoSizeableTextView ? ((AutoSizeableTextView) textView).getAutoSizeStepGranularity() : -1;
    }

    public static int getAutoSizeMinTextSize(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMinTextSize();
        }
        return textView instanceof AutoSizeableTextView ? ((AutoSizeableTextView) textView).getAutoSizeMinTextSize() : -1;
    }

    public static int getAutoSizeMaxTextSize(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeMaxTextSize();
        }
        return textView instanceof AutoSizeableTextView ? ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize() : -1;
    }

    @NonNull
    public static int[] getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 27) {
            return textView.getAutoSizeTextAvailableSizes();
        }
        if (textView instanceof AutoSizeableTextView) {
            return ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes();
        }
        return new int[0];
    }

    public static void setCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull Callback callback) {
        textView.setCustomSelectionActionModeCallback(wrapCustomSelectionActionModeCallback(textView, callback));
    }

    @NonNull
    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public static Callback wrapCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull Callback callback) {
        return (VERSION.SDK_INT < 26 || VERSION.SDK_INT > 27 || (callback instanceof OreoCallback)) ? callback : new OreoCallback(callback, textView);
    }

    public static void setFirstBaselineToTopHeight(@NonNull TextView textView, @Px @IntRange(from = 0) int i) {
        Preconditions.checkArgumentNonnegative(i);
        if (VERSION.SDK_INT >= 28) {
            textView.setFirstBaselineToTopHeight(i);
            return;
        }
        int i2;
        FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        if (VERSION.SDK_INT < 16 || textView.getIncludeFontPadding()) {
            i2 = fontMetricsInt.top;
        } else {
            i2 = fontMetricsInt.ascent;
        }
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), i - (-i2), textView.getPaddingRight(), textView.getPaddingBottom());
        }
    }

    public static void setLastBaselineToBottomHeight(@NonNull TextView textView, @Px @IntRange(from = 0) int i) {
        int i2;
        Preconditions.checkArgumentNonnegative(i);
        FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        if (VERSION.SDK_INT < 16 || textView.getIncludeFontPadding()) {
            i2 = fontMetricsInt.bottom;
        } else {
            i2 = fontMetricsInt.descent;
        }
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), i - i2);
        }
    }

    public static int getFirstBaselineToTopHeight(@NonNull TextView textView) {
        return textView.getPaddingTop() - textView.getPaint().getFontMetricsInt().top;
    }

    public static int getLastBaselineToBottomHeight(@NonNull TextView textView) {
        return textView.getPaddingBottom() + textView.getPaint().getFontMetricsInt().bottom;
    }

    public static void setLineHeight(@NonNull TextView textView, @Px @IntRange(from = 0) int i) {
        Preconditions.checkArgumentNonnegative(i);
        int fontMetricsInt = textView.getPaint().getFontMetricsInt(null);
        if (i != fontMetricsInt) {
            textView.setLineSpacing((float) (i - fontMetricsInt), 1.0f);
        }
    }

    @NonNull
    public static Params getTextMetricsParams(@NonNull TextView textView) {
        if (VERSION.SDK_INT >= 28) {
            return new Params(textView.getTextMetricsParams());
        }
        Builder builder = new Builder(new TextPaint(textView.getPaint()));
        if (VERSION.SDK_INT >= 23) {
            builder.setBreakStrategy(textView.getBreakStrategy());
            builder.setHyphenationFrequency(textView.getHyphenationFrequency());
        }
        if (VERSION.SDK_INT >= 18) {
            builder.setTextDirection(getTextDirectionHeuristic(textView));
        }
        return builder.build();
    }

    public static void setTextMetricsParams(@NonNull TextView textView, @NonNull Params params) {
        if (VERSION.SDK_INT >= 18) {
            textView.setTextDirection(getTextDirection(params.getTextDirection()));
        }
        if (VERSION.SDK_INT < 23) {
            float textScaleX = params.getTextPaint().getTextScaleX();
            textView.getPaint().set(params.getTextPaint());
            if (textScaleX == textView.getTextScaleX()) {
                textView.setTextScaleX((textScaleX / 2.0f) + 1.0f);
            }
            textView.setTextScaleX(textScaleX);
            return;
        }
        textView.getPaint().set(params.getTextPaint());
        textView.setBreakStrategy(params.getBreakStrategy());
        textView.setHyphenationFrequency(params.getHyphenationFrequency());
    }

    public static void setPrecomputedText(@NonNull TextView textView, @NonNull PrecomputedTextCompat precomputedTextCompat) {
        if (getTextMetricsParams(textView).equalsWithoutTextDirection(precomputedTextCompat.getParams())) {
            textView.setText(precomputedTextCompat);
            return;
        }
        throw new IllegalArgumentException("Given text can not be applied to TextView.");
    }

    @RequiresApi(18)
    private static TextDirectionHeuristic getTextDirectionHeuristic(@NonNull TextView textView) {
        if (textView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            return TextDirectionHeuristics.LTR;
        }
        int i = 0;
        if (VERSION.SDK_INT < 28 || (textView.getInputType() & 15) != 3) {
            if (textView.getLayoutDirection() == 1) {
                i = 1;
            }
            switch (textView.getTextDirection()) {
                case 2:
                    return TextDirectionHeuristics.ANYRTL_LTR;
                case 3:
                    return TextDirectionHeuristics.LTR;
                case 4:
                    return TextDirectionHeuristics.RTL;
                case 5:
                    return TextDirectionHeuristics.LOCALE;
                case 6:
                    return TextDirectionHeuristics.FIRSTSTRONG_LTR;
                case 7:
                    return TextDirectionHeuristics.FIRSTSTRONG_RTL;
                default:
                    return i != 0 ? TextDirectionHeuristics.FIRSTSTRONG_RTL : TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }
        }
        byte directionality = Character.getDirectionality(DecimalFormatSymbols.getInstance(textView.getTextLocale()).getDigitStrings()[0].codePointAt(0));
        if (directionality == (byte) 1 || directionality == (byte) 2) {
            return TextDirectionHeuristics.RTL;
        }
        return TextDirectionHeuristics.LTR;
    }

    @RequiresApi(18)
    private static int getTextDirection(@NonNull TextDirectionHeuristic textDirectionHeuristic) {
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL || textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 1;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) {
            return 2;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
            return 3;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
            return 4;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LOCALE) {
            return 5;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 6;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
            return 7;
        }
        return 1;
    }

    public static void setCompoundDrawableTintList(@NonNull TextView textView, @Nullable ColorStateList colorStateList) {
        Preconditions.checkNotNull(textView);
        if (VERSION.SDK_INT >= 23) {
            textView.setCompoundDrawableTintList(colorStateList);
        } else if (textView instanceof TintableCompoundDrawablesView) {
            ((TintableCompoundDrawablesView) textView).setSupportCompoundDrawablesTintList(colorStateList);
        }
    }

    @Nullable
    public static ColorStateList getCompoundDrawableTintList(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView);
        if (VERSION.SDK_INT >= 23) {
            return textView.getCompoundDrawableTintList();
        }
        return textView instanceof TintableCompoundDrawablesView ? ((TintableCompoundDrawablesView) textView).getSupportCompoundDrawablesTintList() : null;
    }

    public static void setCompoundDrawableTintMode(@NonNull TextView textView, @Nullable Mode mode) {
        Preconditions.checkNotNull(textView);
        if (VERSION.SDK_INT >= 23) {
            textView.setCompoundDrawableTintMode(mode);
        } else if (textView instanceof TintableCompoundDrawablesView) {
            ((TintableCompoundDrawablesView) textView).setSupportCompoundDrawablesTintMode(mode);
        }
    }

    @Nullable
    public static Mode getCompoundDrawableTintMode(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView);
        if (VERSION.SDK_INT >= 23) {
            return textView.getCompoundDrawableTintMode();
        }
        return textView instanceof TintableCompoundDrawablesView ? ((TintableCompoundDrawablesView) textView).getSupportCompoundDrawablesTintMode() : null;
    }
}
