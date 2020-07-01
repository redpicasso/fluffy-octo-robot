package androidx.core.text;

import android.icu.util.ULocale;
import android.os.Build.VERSION;
import android.util.Log;
import androidx.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Locale;

public final class ICUCompat {
    private static final String TAG = "ICUCompat";
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    static {
        String str = "addLikelySubtags";
        String str2 = "libcore.icu.ICU";
        if (VERSION.SDK_INT < 21) {
            try {
                Class cls = Class.forName(str2);
                if (cls != null) {
                    sGetScriptMethod = cls.getMethod("getScript", new Class[]{String.class});
                    sAddLikelySubtagsMethod = cls.getMethod(str, new Class[]{String.class});
                }
            } catch (Throwable e) {
                sGetScriptMethod = null;
                sAddLikelySubtagsMethod = null;
                Log.w(TAG, e);
            }
        } else if (VERSION.SDK_INT < 24) {
            try {
                sAddLikelySubtagsMethod = Class.forName(str2).getMethod(str, new Class[]{Locale.class});
            } catch (Throwable e2) {
                throw new IllegalStateException(e2);
            }
        }
    }

    @Nullable
    public static String maximizeAndGetScript(Locale locale) {
        String str = TAG;
        if (VERSION.SDK_INT >= 24) {
            return ULocale.addLikelySubtags(ULocale.forLocale(locale)).getScript();
        }
        if (VERSION.SDK_INT >= 21) {
            try {
                locale = ((Locale) sAddLikelySubtagsMethod.invoke(null, new Object[]{locale})).getScript();
                return locale;
            } catch (Throwable e) {
                Log.w(str, e);
                return locale.getScript();
            } catch (Throwable e2) {
                Log.w(str, e2);
                return locale.getScript();
            }
        }
        String addLikelySubtags = addLikelySubtags(locale);
        if (addLikelySubtags != null) {
            return getScript(addLikelySubtags);
        }
        return null;
    }

    private static String getScript(String str) {
        String str2 = TAG;
        try {
            if (sGetScriptMethod != null) {
                return (String) sGetScriptMethod.invoke(null, new Object[]{str});
            }
        } catch (Throwable e) {
            Log.w(str2, e);
        } catch (Throwable e2) {
            Log.w(str2, e2);
        }
        return null;
    }

    private static String addLikelySubtags(Locale locale) {
        String str = TAG;
        String locale2 = locale.toString();
        try {
            if (sAddLikelySubtagsMethod != null) {
                return (String) sAddLikelySubtagsMethod.invoke(null, new Object[]{locale2});
            }
        } catch (Throwable e) {
            Log.w(str, e);
        } catch (Throwable e2) {
            Log.w(str, e2);
        }
        return locale2;
    }

    private ICUCompat() {
    }
}
