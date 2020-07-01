package androidx.core.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.Typeface.Builder;
import android.graphics.fonts.FontVariationAxis;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import androidx.core.provider.FontsContractCompat;
import androidx.core.provider.FontsContractCompat.FontInfo;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

@RequiresApi(26)
@RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    protected final Method mAbortCreation;
    protected final Method mAddFontFromAssetManager;
    protected final Method mAddFontFromBuffer;
    protected final Method mCreateFromFamiliesWithDefault;
    protected final Class mFontFamily;
    protected final Constructor mFontFamilyCtor;
    protected final Method mFreeze;

    public TypefaceCompatApi26Impl() {
        Constructor obtainFontFamilyCtor;
        Method obtainAddFontFromAssetManagerMethod;
        Method obtainAddFontFromBufferMethod;
        Method obtainFreezeMethod;
        Method obtainAbortCreationMethod;
        Method method;
        Throwable e;
        StringBuilder stringBuilder;
        Class cls = null;
        try {
            Class obtainFontFamily = obtainFontFamily();
            obtainFontFamilyCtor = obtainFontFamilyCtor(obtainFontFamily);
            obtainAddFontFromAssetManagerMethod = obtainAddFontFromAssetManagerMethod(obtainFontFamily);
            obtainAddFontFromBufferMethod = obtainAddFontFromBufferMethod(obtainFontFamily);
            obtainFreezeMethod = obtainFreezeMethod(obtainFontFamily);
            obtainAbortCreationMethod = obtainAbortCreationMethod(obtainFontFamily);
            cls = obtainCreateFromFamiliesWithDefaultMethod(obtainFontFamily);
            Class cls2 = obtainFontFamily;
            method = cls;
            cls = cls2;
        } catch (ClassNotFoundException e2) {
            e = e2;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to collect necessary methods for class ");
            stringBuilder.append(e.getClass().getName());
            Log.e(TAG, stringBuilder.toString(), e);
            method = cls;
            obtainFontFamilyCtor = method;
            obtainAddFontFromAssetManagerMethod = obtainFontFamilyCtor;
            obtainAddFontFromBufferMethod = obtainAddFontFromAssetManagerMethod;
            obtainFreezeMethod = obtainAddFontFromBufferMethod;
            obtainAbortCreationMethod = obtainFreezeMethod;
            this.mFontFamily = cls;
            this.mFontFamilyCtor = obtainFontFamilyCtor;
            this.mAddFontFromAssetManager = obtainAddFontFromAssetManagerMethod;
            this.mAddFontFromBuffer = obtainAddFontFromBufferMethod;
            this.mFreeze = obtainFreezeMethod;
            this.mAbortCreation = obtainAbortCreationMethod;
            this.mCreateFromFamiliesWithDefault = method;
        } catch (NoSuchMethodException e3) {
            e = e3;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to collect necessary methods for class ");
            stringBuilder.append(e.getClass().getName());
            Log.e(TAG, stringBuilder.toString(), e);
            method = cls;
            obtainFontFamilyCtor = method;
            obtainAddFontFromAssetManagerMethod = obtainFontFamilyCtor;
            obtainAddFontFromBufferMethod = obtainAddFontFromAssetManagerMethod;
            obtainFreezeMethod = obtainAddFontFromBufferMethod;
            obtainAbortCreationMethod = obtainFreezeMethod;
            this.mFontFamily = cls;
            this.mFontFamilyCtor = obtainFontFamilyCtor;
            this.mAddFontFromAssetManager = obtainAddFontFromAssetManagerMethod;
            this.mAddFontFromBuffer = obtainAddFontFromBufferMethod;
            this.mFreeze = obtainFreezeMethod;
            this.mAbortCreation = obtainAbortCreationMethod;
            this.mCreateFromFamiliesWithDefault = method;
        }
        this.mFontFamily = cls;
        this.mFontFamilyCtor = obtainFontFamilyCtor;
        this.mAddFontFromAssetManager = obtainAddFontFromAssetManagerMethod;
        this.mAddFontFromBuffer = obtainAddFontFromBufferMethod;
        this.mFreeze = obtainFreezeMethod;
        this.mAbortCreation = obtainAbortCreationMethod;
        this.mCreateFromFamiliesWithDefault = method;
    }

    private boolean isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.mAddFontFromAssetManager != null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x000a A:{ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000a A:{ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:4:0x000b, code:
            return null;
     */
    @androidx.annotation.Nullable
    private java.lang.Object newFamily() {
        /*
        r2 = this;
        r0 = r2.mFontFamilyCtor;	 Catch:{ IllegalAccessException -> 0x000a, IllegalAccessException -> 0x000a, IllegalAccessException -> 0x000a }
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ IllegalAccessException -> 0x000a, IllegalAccessException -> 0x000a, IllegalAccessException -> 0x000a }
        r0 = r0.newInstance(r1);	 Catch:{ IllegalAccessException -> 0x000a, IllegalAccessException -> 0x000a, IllegalAccessException -> 0x000a }
        return r0;
    L_0x000a:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi26Impl.newFamily():java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x0041 A:{RETURN, ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:4:0x0041, code:
            return false;
     */
    private boolean addFontFromAssetManager(android.content.Context r4, java.lang.Object r5, java.lang.String r6, int r7, int r8, int r9, @androidx.annotation.Nullable android.graphics.fonts.FontVariationAxis[] r10) {
        /*
        r3 = this;
        r0 = 0;
        r1 = r3.mAddFontFromAssetManager;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r2 = 8;
        r2 = new java.lang.Object[r2];	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = r4.getAssets();	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r2[r0] = r4;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = 1;
        r2[r4] = r6;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = 2;
        r6 = java.lang.Integer.valueOf(r0);	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r2[r4] = r6;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = 3;
        r6 = java.lang.Boolean.valueOf(r0);	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r2[r4] = r6;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = 4;
        r6 = java.lang.Integer.valueOf(r7);	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r2[r4] = r6;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = 5;
        r6 = java.lang.Integer.valueOf(r8);	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r2[r4] = r6;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = 6;
        r6 = java.lang.Integer.valueOf(r9);	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r2[r4] = r6;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = 7;
        r2[r4] = r10;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = r1.invoke(r5, r2);	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = (java.lang.Boolean) r4;	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        r4 = r4.booleanValue();	 Catch:{ IllegalAccessException -> 0x0041, IllegalAccessException -> 0x0041 }
        return r4;
    L_0x0041:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi26Impl.addFontFromAssetManager(android.content.Context, java.lang.Object, java.lang.String, int, int, int, android.graphics.fonts.FontVariationAxis[]):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x002c A:{RETURN, ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:4:0x002c, code:
            return false;
     */
    private boolean addFontFromBuffer(java.lang.Object r4, java.nio.ByteBuffer r5, int r6, int r7, int r8) {
        /*
        r3 = this;
        r0 = 0;
        r1 = r3.mAddFontFromBuffer;	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r2 = 5;
        r2 = new java.lang.Object[r2];	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r2[r0] = r5;	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r5 = 1;
        r6 = java.lang.Integer.valueOf(r6);	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r2[r5] = r6;	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r5 = 2;
        r6 = 0;
        r2[r5] = r6;	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r5 = 3;
        r6 = java.lang.Integer.valueOf(r7);	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r2[r5] = r6;	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r5 = 4;
        r6 = java.lang.Integer.valueOf(r8);	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r2[r5] = r6;	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r4 = r1.invoke(r4, r2);	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r4 = (java.lang.Boolean) r4;	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        r4 = r4.booleanValue();	 Catch:{ IllegalAccessException -> 0x002c, IllegalAccessException -> 0x002c }
        return r4;
    L_0x002c:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi26Impl.addFontFromBuffer(java.lang.Object, java.nio.ByteBuffer, int, int, int):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x0028 A:{RETURN, ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:4:0x0028, code:
            return null;
     */
    @androidx.annotation.Nullable
    protected android.graphics.Typeface createFromFamiliesWithDefault(java.lang.Object r6) {
        /*
        r5 = this;
        r0 = 0;
        r1 = r5.mFontFamily;	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r2 = 1;
        r1 = java.lang.reflect.Array.newInstance(r1, r2);	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r3 = 0;
        java.lang.reflect.Array.set(r1, r3, r6);	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r6 = r5.mCreateFromFamiliesWithDefault;	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r4 = 3;
        r4 = new java.lang.Object[r4];	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r4[r3] = r1;	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r1 = -1;
        r3 = java.lang.Integer.valueOf(r1);	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r4[r2] = r3;	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r2 = 2;
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r4[r2] = r1;	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r6 = r6.invoke(r0, r4);	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        r6 = (android.graphics.Typeface) r6;	 Catch:{ IllegalAccessException -> 0x0028, IllegalAccessException -> 0x0028 }
        return r6;
    L_0x0028:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi26Impl.createFromFamiliesWithDefault(java.lang.Object):android.graphics.Typeface");
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x0010 A:{RETURN, ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:1:0x0001} */
    /* JADX WARNING: Missing block: B:4:0x0010, code:
            return false;
     */
    private boolean freeze(java.lang.Object r4) {
        /*
        r3 = this;
        r0 = 0;
        r1 = r3.mFreeze;	 Catch:{ IllegalAccessException -> 0x0010, IllegalAccessException -> 0x0010 }
        r2 = new java.lang.Object[r0];	 Catch:{ IllegalAccessException -> 0x0010, IllegalAccessException -> 0x0010 }
        r4 = r1.invoke(r4, r2);	 Catch:{ IllegalAccessException -> 0x0010, IllegalAccessException -> 0x0010 }
        r4 = (java.lang.Boolean) r4;	 Catch:{ IllegalAccessException -> 0x0010, IllegalAccessException -> 0x0010 }
        r4 = r4.booleanValue();	 Catch:{ IllegalAccessException -> 0x0010, IllegalAccessException -> 0x0010 }
        return r4;
    L_0x0010:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi26Impl.freeze(java.lang.Object):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:2:0x0008 A:{RETURN, ExcHandler: java.lang.IllegalAccessException (unused java.lang.IllegalAccessException), Splitter: B:0:0x0000} */
    /* JADX WARNING: Missing block: B:2:0x0008, code:
            return;
     */
    private void abortCreation(java.lang.Object r3) {
        /*
        r2 = this;
        r0 = r2.mAbortCreation;	 Catch:{ IllegalAccessException -> 0x0008, IllegalAccessException -> 0x0008 }
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ IllegalAccessException -> 0x0008, IllegalAccessException -> 0x0008 }
        r0.invoke(r3, r1);	 Catch:{ IllegalAccessException -> 0x0008, IllegalAccessException -> 0x0008 }
    L_0x0008:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi26Impl.abortCreation(java.lang.Object):void");
    }

    @Nullable
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, fontFamilyFilesResourceEntry, resources, i);
        }
        Object newFamily = newFamily();
        if (newFamily == null) {
            return null;
        }
        FontFileResourceEntry[] entries = fontFamilyFilesResourceEntry.getEntries();
        int length = entries.length;
        int i2 = 0;
        while (i2 < length) {
            FontFileResourceEntry fontFileResourceEntry = entries[i2];
            if (addFontFromAssetManager(context, newFamily, fontFileResourceEntry.getFileName(), fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic(), FontVariationAxis.fromFontVariationSettings(fontFileResourceEntry.getVariationSettings()))) {
                i2++;
            } else {
                abortCreation(newFamily);
                return null;
            }
        }
        if (freeze(newFamily)) {
            return createFromFamiliesWithDefault(newFamily);
        }
        return null;
    }

    @Nullable
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fontInfoArr, int i) {
        ParcelFileDescriptor openFileDescriptor;
        Throwable th;
        Throwable th2;
        if (fontInfoArr.length < 1) {
            return null;
        }
        if (isFontFamilyPrivateAPIAvailable()) {
            Map prepareFontData = FontsContractCompat.prepareFontData(context, fontInfoArr, cancellationSignal);
            Object newFamily = newFamily();
            if (newFamily == null) {
                return null;
            }
            Object obj = null;
            for (FontInfo fontInfo : fontInfoArr) {
                ByteBuffer byteBuffer = (ByteBuffer) prepareFontData.get(fontInfo.getUri());
                if (byteBuffer != null) {
                    if (addFontFromBuffer(newFamily, byteBuffer, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic())) {
                        obj = 1;
                    } else {
                        abortCreation(newFamily);
                        return null;
                    }
                }
            }
            if (obj == null) {
                abortCreation(newFamily);
                return null;
            } else if (!freeze(newFamily)) {
                return null;
            } else {
                Typeface createFromFamiliesWithDefault = createFromFamiliesWithDefault(newFamily);
                if (createFromFamiliesWithDefault == null) {
                    return null;
                }
                return Typeface.create(createFromFamiliesWithDefault, i);
            }
        }
        FontInfo findBestInfo = findBestInfo(fontInfoArr, i);
        try {
            openFileDescriptor = context.getContentResolver().openFileDescriptor(findBestInfo.getUri(), "r", cancellationSignal);
            if (openFileDescriptor == null) {
                if (openFileDescriptor != null) {
                    openFileDescriptor.close();
                }
                return null;
            }
            try {
                Typeface build = new Builder(openFileDescriptor.getFileDescriptor()).setWeight(findBestInfo.getWeight()).setItalic(findBestInfo.isItalic()).build();
                if (openFileDescriptor != null) {
                    openFileDescriptor.close();
                }
                return build;
            } catch (Throwable th22) {
                Throwable th3 = th22;
                th22 = th;
                th = th3;
            }
        } catch (IOException unused) {
            return null;
        }
        if (openFileDescriptor != null) {
            if (th22 != null) {
                try {
                    openFileDescriptor.close();
                } catch (Throwable th4) {
                    th22.addSuppressed(th4);
                }
            } else {
                openFileDescriptor.close();
            }
        }
        throw th;
        throw th;
    }

    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, i, str, i2);
        }
        Object newFamily = newFamily();
        if (newFamily == null) {
            return null;
        }
        if (!addFontFromAssetManager(context, newFamily, str, 0, -1, -1, null)) {
            abortCreation(newFamily);
            return null;
        } else if (freeze(newFamily)) {
            return createFromFamiliesWithDefault(newFamily);
        } else {
            return null;
        }
    }

    protected Class obtainFontFamily() throws ClassNotFoundException {
        return Class.forName(FONT_FAMILY_CLASS);
    }

    protected Constructor obtainFontFamilyCtor(Class cls) throws NoSuchMethodException {
        return cls.getConstructor(new Class[0]);
    }

    protected Method obtainAddFontFromAssetManagerMethod(Class cls) throws NoSuchMethodException {
        return cls.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class});
    }

    protected Method obtainAddFontFromBufferMethod(Class cls) throws NoSuchMethodException {
        return cls.getMethod(ADD_FONT_FROM_BUFFER_METHOD, new Class[]{ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE});
    }

    protected Method obtainFreezeMethod(Class cls) throws NoSuchMethodException {
        return cls.getMethod(FREEZE_METHOD, new Class[0]);
    }

    protected Method obtainAbortCreationMethod(Class cls) throws NoSuchMethodException {
        return cls.getMethod(ABORT_CREATION_METHOD, new Class[0]);
    }

    protected Method obtainCreateFromFamiliesWithDefaultMethod(Class cls) throws NoSuchMethodException {
        Method declaredMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{Array.newInstance(cls, 1).getClass(), Integer.TYPE, Integer.TYPE});
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }
}
