package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
@RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String TAG = "TypefaceCompatApi21Impl";
    private static Method sAddFontWeightStyle = null;
    private static Method sCreateFromFamiliesWithDefault = null;
    private static Class sFontFamily = null;
    private static Constructor sFontFamilyCtor = null;
    private static boolean sHasInitBeenCalled = false;

    TypefaceCompatApi21Impl() {
    }

    private static void init() {
        Method method;
        Method method2;
        Throwable e;
        if (!sHasInitBeenCalled) {
            Class cls;
            sHasInitBeenCalled = true;
            Constructor constructor = null;
            try {
                cls = Class.forName(FONT_FAMILY_CLASS);
                Constructor constructor2 = cls.getConstructor(new Class[0]);
                method = cls.getMethod(ADD_FONT_WEIGHT_STYLE_METHOD, new Class[]{String.class, Integer.TYPE, Boolean.TYPE});
                Object newInstance = Array.newInstance(cls, 1);
                constructor = Typeface.class.getMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{newInstance.getClass()});
                method2 = constructor;
                constructor = constructor2;
            } catch (ClassNotFoundException e2) {
                e = e2;
                Log.e(TAG, e.getClass().getName(), e);
                method2 = constructor;
                cls = method2;
                method = cls;
                sFontFamilyCtor = constructor;
                sFontFamily = cls;
                sAddFontWeightStyle = method;
                sCreateFromFamiliesWithDefault = method2;
            } catch (NoSuchMethodException e3) {
                e = e3;
                Log.e(TAG, e.getClass().getName(), e);
                method2 = constructor;
                cls = method2;
                method = cls;
                sFontFamilyCtor = constructor;
                sFontFamily = cls;
                sAddFontWeightStyle = method;
                sCreateFromFamiliesWithDefault = method2;
            }
            sFontFamilyCtor = constructor;
            sFontFamily = cls;
            sAddFontWeightStyle = method;
            sCreateFromFamiliesWithDefault = method2;
        }
    }

    private File getFile(@NonNull ParcelFileDescriptor parcelFileDescriptor) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/proc/self/fd/");
            stringBuilder.append(parcelFileDescriptor.getFd());
            String readlink = Os.readlink(stringBuilder.toString());
            if (OsConstants.S_ISREG(Os.stat(readlink).st_mode)) {
                return new File(readlink);
            }
        } catch (ErrnoException unused) {
            return null;
        }
    }

    private static Object newFamily() {
        Throwable e;
        init();
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new RuntimeException(e);
        } catch (InstantiationException e3) {
            e = e3;
            throw new RuntimeException(e);
        } catch (InvocationTargetException e4) {
            e = e4;
            throw new RuntimeException(e);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object obj) {
        Throwable e;
        init();
        try {
            Array.set(Array.newInstance(sFontFamily, 1), 0, obj);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, new Object[]{r0});
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new RuntimeException(e);
        } catch (InvocationTargetException e3) {
            e = e3;
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontWeightStyle(Object obj, String str, int i, boolean z) {
        Throwable e;
        init();
        try {
            return ((Boolean) sAddFontWeightStyle.invoke(obj, new Object[]{str, Integer.valueOf(i), Boolean.valueOf(z)})).booleanValue();
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new RuntimeException(e);
        } catch (InvocationTargetException e3) {
            e = e3;
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0065 A:{ExcHandler: all (th java.lang.Throwable), Splitter: B:10:0x0020} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:45:0x0065, code:
            r5 = th;
     */
    /* JADX WARNING: Missing block: B:46:0x0066, code:
            r7 = null;
     */
    /* JADX WARNING: Missing block: B:50:0x006a, code:
            r7 = move-exception;
     */
    /* JADX WARNING: Missing block: B:51:0x006b, code:
            r3 = r7;
            r7 = r5;
            r5 = r3;
     */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r5, android.os.CancellationSignal r6, @androidx.annotation.NonNull androidx.core.provider.FontsContractCompat.FontInfo[] r7, int r8) {
        /*
        r4 = this;
        r0 = r7.length;
        r1 = 0;
        r2 = 1;
        if (r0 >= r2) goto L_0x0006;
    L_0x0005:
        return r1;
    L_0x0006:
        r7 = r4.findBestInfo(r7, r8);
        r8 = r5.getContentResolver();
        r7 = r7.getUri();	 Catch:{ IOException -> 0x007f }
        r0 = "r";
        r6 = r8.openFileDescriptor(r7, r0, r6);	 Catch:{ IOException -> 0x007f }
        if (r6 != 0) goto L_0x0020;
    L_0x001a:
        if (r6 == 0) goto L_0x001f;
    L_0x001c:
        r6.close();	 Catch:{ IOException -> 0x007f }
    L_0x001f:
        return r1;
    L_0x0020:
        r7 = r4.getFile(r6);	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        if (r7 == 0) goto L_0x0037;
    L_0x0026:
        r8 = r7.canRead();	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        if (r8 != 0) goto L_0x002d;
    L_0x002c:
        goto L_0x0037;
    L_0x002d:
        r5 = android.graphics.Typeface.createFromFile(r7);	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        if (r6 == 0) goto L_0x0036;
    L_0x0033:
        r6.close();	 Catch:{ IOException -> 0x007f }
    L_0x0036:
        return r5;
    L_0x0037:
        r7 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        r8 = r6.getFileDescriptor();	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        r7.<init>(r8);	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        r5 = super.createFromInputStream(r5, r7);	 Catch:{ Throwable -> 0x0050, all -> 0x004d }
        r7.close();	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        if (r6 == 0) goto L_0x004c;
    L_0x0049:
        r6.close();	 Catch:{ IOException -> 0x007f }
    L_0x004c:
        return r5;
    L_0x004d:
        r5 = move-exception;
        r8 = r1;
        goto L_0x0056;
    L_0x0050:
        r5 = move-exception;
        throw r5;	 Catch:{ all -> 0x0052 }
    L_0x0052:
        r8 = move-exception;
        r3 = r8;
        r8 = r5;
        r5 = r3;
    L_0x0056:
        if (r8 == 0) goto L_0x0061;
    L_0x0058:
        r7.close();	 Catch:{ Throwable -> 0x005c, all -> 0x0065 }
        goto L_0x0064;
    L_0x005c:
        r7 = move-exception;
        r8.addSuppressed(r7);	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        goto L_0x0064;
    L_0x0061:
        r7.close();	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
    L_0x0064:
        throw r5;	 Catch:{ Throwable -> 0x0068, all -> 0x0065 }
    L_0x0065:
        r5 = move-exception;
        r7 = r1;
        goto L_0x006e;
    L_0x0068:
        r5 = move-exception;
        throw r5;	 Catch:{ all -> 0x006a }
    L_0x006a:
        r7 = move-exception;
        r3 = r7;
        r7 = r5;
        r5 = r3;
    L_0x006e:
        if (r6 == 0) goto L_0x007e;
    L_0x0070:
        if (r7 == 0) goto L_0x007b;
    L_0x0072:
        r6.close();	 Catch:{ Throwable -> 0x0076 }
        goto L_0x007e;
    L_0x0076:
        r6 = move-exception;
        r7.addSuppressed(r6);	 Catch:{ IOException -> 0x007f }
        goto L_0x007e;
    L_0x007b:
        r6.close();	 Catch:{ IOException -> 0x007f }
    L_0x007e:
        throw r5;	 Catch:{ IOException -> 0x007f }
    L_0x007f:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi21Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, androidx.core.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i) {
        Object newFamily = newFamily();
        FontFileResourceEntry[] entries = fontFamilyFilesResourceEntry.getEntries();
        int length = entries.length;
        int i2 = 0;
        while (i2 < length) {
            FontFileResourceEntry fontFileResourceEntry = entries[i2];
            File tempFile = TypefaceCompatUtil.getTempFile(context);
            if (tempFile == null) {
                return null;
            }
            try {
                if (!TypefaceCompatUtil.copyToFile(tempFile, resources, fontFileResourceEntry.getResourceId())) {
                    tempFile.delete();
                    return null;
                } else if (addFontWeightStyle(newFamily, tempFile.getPath(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic())) {
                    tempFile.delete();
                    i2++;
                } else {
                    tempFile.delete();
                    return null;
                }
            } catch (RuntimeException unused) {
                tempFile.delete();
                return null;
            } catch (Throwable th) {
                tempFile.delete();
                throw th;
            }
        }
        return createFromFamiliesWithDefault(newFamily);
    }
}
