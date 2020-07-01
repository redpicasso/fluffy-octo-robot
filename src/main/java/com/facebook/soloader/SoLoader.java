package com.facebook.soloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.text.TextUtils;
import android.util.Log;
import dalvik.system.BaseDexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class SoLoader {
    static final boolean DEBUG = false;
    public static final int SOLOADER_ALLOW_ASYNC_INIT = 2;
    public static final int SOLOADER_DISABLE_BACKUP_SOSOURCE = 8;
    public static final int SOLOADER_ENABLE_EXOPACKAGE = 1;
    public static final int SOLOADER_LOOK_IN_ZIP = 4;
    private static final String SO_STORE_NAME_MAIN = "lib-main";
    private static final String SO_STORE_NAME_SPLIT = "lib-";
    static final boolean SYSTRACE_LIBRARY_LOADING;
    static final String TAG = "SoLoader";
    @GuardedBy("sSoSourcesLock")
    @Nullable
    private static ApplicationSoSource sApplicationSoSource;
    @GuardedBy("sSoSourcesLock")
    @Nullable
    private static UnpackingSoSource[] sBackupSoSources;
    @GuardedBy("sSoSourcesLock")
    private static int sFlags;
    private static final Set<String> sLoadedAndMergedLibraries = Collections.newSetFromMap(new ConcurrentHashMap());
    @GuardedBy("SoLoader.class")
    private static final HashSet<String> sLoadedLibraries = new HashSet();
    @GuardedBy("SoLoader.class")
    private static final Map<String, Object> sLoadingLibraries = new HashMap();
    @Nullable
    static SoFileLoader sSoFileLoader;
    @GuardedBy("sSoSourcesLock")
    @Nullable
    private static SoSource[] sSoSources = null;
    private static final ReentrantReadWriteLock sSoSourcesLock = new ReentrantReadWriteLock();
    private static int sSoSourcesVersion = 0;
    @Nullable
    private static SystemLoadLibraryWrapper sSystemLoadLibraryWrapper = null;

    @DoNotOptimize
    @TargetApi(14)
    private static class Api14Utils {
        private Api14Utils() {
        }

        public static String getClassLoaderLdLoadLibrary() {
            ClassLoader classLoader = SoLoader.class.getClassLoader();
            if (classLoader instanceof BaseDexClassLoader) {
                try {
                    return (String) BaseDexClassLoader.class.getMethod("getLdLibraryPath", new Class[0]).invoke((BaseDexClassLoader) classLoader, new Object[0]);
                } catch (Throwable e) {
                    throw new RuntimeException("Cannot call getLdLibraryPath", e);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ClassLoader ");
            stringBuilder.append(classLoader.getClass().getName());
            stringBuilder.append(" should be of type BaseDexClassLoader");
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public static final class WrongAbiError extends UnsatisfiedLinkError {
        WrongAbiError(Throwable th) {
            super("APK was built for a different platform");
            initCause(th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0030 A:{PHI: r1 , ExcHandler: java.lang.NoClassDefFoundError (unused java.lang.NoClassDefFoundError), Splitter: B:1:0x0028} */
    /* JADX WARNING: Missing block: B:6:0x0030, code:
            SYSTRACE_LIBRARY_LOADING = r1;
     */
    /* JADX WARNING: Missing block: B:7:0x0032, code:
            return;
     */
    static {
        /*
        r0 = new java.util.concurrent.locks.ReentrantReadWriteLock;
        r0.<init>();
        sSoSourcesLock = r0;
        r0 = 0;
        sSoSources = r0;
        r1 = 0;
        sSoSourcesVersion = r1;
        r2 = new java.util.HashSet;
        r2.<init>();
        sLoadedLibraries = r2;
        r2 = new java.util.HashMap;
        r2.<init>();
        sLoadingLibraries = r2;
        r2 = new java.util.concurrent.ConcurrentHashMap;
        r2.<init>();
        r2 = java.util.Collections.newSetFromMap(r2);
        sLoadedAndMergedLibraries = r2;
        sSystemLoadLibraryWrapper = r0;
        r0 = android.os.Build.VERSION.SDK_INT;	 Catch:{ NoClassDefFoundError -> 0x0030, NoClassDefFoundError -> 0x0030 }
        r2 = 18;
        if (r0 < r2) goto L_0x0030;
    L_0x002e:
        r0 = 1;
        r1 = 1;
    L_0x0030:
        SYSTRACE_LIBRARY_LOADING = r1;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.<clinit>():void");
    }

    public static void init(Context context, int i) throws IOException {
        init(context, i, null);
    }

    private static void init(Context context, int i, @Nullable SoFileLoader soFileLoader) throws IOException {
        ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            initSoLoader(soFileLoader);
            initSoSources(context, i, soFileLoader);
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    public static void init(Context context, boolean z) {
        try {
            init(context, z ? 1 : 0);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void initSoSources(Context context, int i, @Nullable SoFileLoader soFileLoader) throws IOException {
        String str = "init exiting";
        String str2 = TAG;
        sSoSourcesLock.writeLock().lock();
        try {
            if (sSoSources == null) {
                Log.d(str2, "init start");
                sFlags = i;
                ArrayList arrayList = new ArrayList();
                String str3 = System.getenv("LD_LIBRARY_PATH");
                if (str3 == null) {
                    str3 = "/vendor/lib:/system/lib";
                }
                String[] split = str3.split(":");
                for (int i2 = 0; i2 < split.length; i2++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("adding system library source: ");
                    stringBuilder.append(split[i2]);
                    Log.d(str2, stringBuilder.toString());
                    arrayList.add(new DirectorySoSource(new File(split[i2]), 2));
                }
                if (context != null) {
                    i &= 1;
                    String str4 = SO_STORE_NAME_MAIN;
                    if (i != 0) {
                        sBackupSoSources = null;
                        Log.d(str2, "adding exo package source: lib-main");
                        arrayList.add(0, new ExoSoSource(context, str4));
                    } else {
                        ApplicationInfo applicationInfo = context.getApplicationInfo();
                        Object obj = ((applicationInfo.flags & 1) == 0 || (applicationInfo.flags & 128) != 0) ? null : 1;
                        if (obj != null) {
                            i = 0;
                        } else {
                            sApplicationSoSource = new ApplicationSoSource(context, VERSION.SDK_INT <= 17 ? 1 : 0);
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("adding application source: ");
                            stringBuilder2.append(sApplicationSoSource.toString());
                            Log.d(str2, stringBuilder2.toString());
                            arrayList.add(0, sApplicationSoSource);
                            i = 1;
                        }
                        if ((sFlags & 8) != 0) {
                            sBackupSoSources = null;
                        } else {
                            File file = new File(context.getApplicationInfo().sourceDir);
                            Collection arrayList2 = new ArrayList();
                            ApkSoSource apkSoSource = new ApkSoSource(context, file, str4, i);
                            arrayList2.add(apkSoSource);
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("adding backup source from : ");
                            stringBuilder3.append(apkSoSource.toString());
                            Log.d(str2, stringBuilder3.toString());
                            if (VERSION.SDK_INT >= 21 && context.getApplicationInfo().splitSourceDirs != null) {
                                Log.d(str2, "adding backup sources from split apks");
                                String[] strArr = context.getApplicationInfo().splitSourceDirs;
                                int length = strArr.length;
                                int i3 = 0;
                                int i4 = 0;
                                while (i3 < length) {
                                    File file2 = new File(strArr[i3]);
                                    StringBuilder stringBuilder4 = new StringBuilder();
                                    stringBuilder4.append(SO_STORE_NAME_SPLIT);
                                    int i5 = i4 + 1;
                                    stringBuilder4.append(i4);
                                    ApkSoSource apkSoSource2 = new ApkSoSource(context, file2, stringBuilder4.toString(), i);
                                    StringBuilder stringBuilder5 = new StringBuilder();
                                    stringBuilder5.append("adding backup source: ");
                                    stringBuilder5.append(apkSoSource2.toString());
                                    Log.d(str2, stringBuilder5.toString());
                                    arrayList2.add(apkSoSource2);
                                    i3++;
                                    i4 = i5;
                                }
                            }
                            sBackupSoSources = (UnpackingSoSource[]) arrayList2.toArray(new UnpackingSoSource[arrayList2.size()]);
                            arrayList.addAll(0, arrayList2);
                        }
                    }
                }
                SoSource[] soSourceArr = (SoSource[]) arrayList.toArray(new SoSource[arrayList.size()]);
                i = makePrepareFlags();
                int length2 = soSourceArr.length;
                while (true) {
                    int i6 = length2 - 1;
                    if (length2 <= 0) {
                        break;
                    }
                    StringBuilder stringBuilder6 = new StringBuilder();
                    stringBuilder6.append("Preparing SO source: ");
                    stringBuilder6.append(soSourceArr[i6]);
                    Log.d(str2, stringBuilder6.toString());
                    soSourceArr[i6].prepare(i);
                    length2 = i6;
                }
                sSoSources = soSourceArr;
                sSoSourcesVersion++;
                StringBuilder stringBuilder7 = new StringBuilder();
                stringBuilder7.append("init finish: ");
                stringBuilder7.append(sSoSources.length);
                stringBuilder7.append(" SO sources prepared");
                Log.d(str2, stringBuilder7.toString());
            }
            Log.d(str2, str);
            sSoSourcesLock.writeLock().unlock();
        } catch (Throwable th) {
            Log.d(str2, str);
            sSoSourcesLock.writeLock().unlock();
        }
    }

    private static int makePrepareFlags() {
        sSoSourcesLock.writeLock().lock();
        try {
            int i = (sFlags & 2) != 0 ? 1 : 0;
            sSoSourcesLock.writeLock().unlock();
            return i;
        } catch (Throwable th) {
            sSoSourcesLock.writeLock().unlock();
        }
    }

    private static synchronized void initSoLoader(@Nullable SoFileLoader soFileLoader) {
        synchronized (SoLoader.class) {
            if (soFileLoader != null) {
                sSoFileLoader = soFileLoader;
                return;
            }
            final Runtime runtime = Runtime.getRuntime();
            final Method nativeLoadRuntimeMethod = getNativeLoadRuntimeMethod();
            final boolean z = nativeLoadRuntimeMethod != null;
            final String classLoaderLdLoadLibrary = z ? Api14Utils.getClassLoaderLdLoadLibrary() : null;
            final String makeNonZipPath = makeNonZipPath(classLoaderLdLoadLibrary);
            sSoFileLoader = new SoFileLoader() {
                /* JADX WARNING: Missing block: B:18:0x0035, code:
            if (r1 == null) goto L_?;
     */
                /* JADX WARNING: Missing block: B:19:0x0037, code:
            r0 = new java.lang.StringBuilder();
            r0.append("Error when loading lib: ");
            r0.append(r1);
            r0.append(" lib hash: ");
            r0.append(getLibHash(r9));
            r0.append(" search path is ");
            r0.append(r10);
            android.util.Log.e(com.facebook.soloader.SoLoader.TAG, r0.toString());
     */
                /* JADX WARNING: Missing block: B:42:?, code:
            return;
     */
                /* JADX WARNING: Missing block: B:43:?, code:
            return;
     */
                public void load(java.lang.String r9, int r10) {
                    /*
                    r8 = this;
                    r0 = r2;
                    if (r0 == 0) goto L_0x00bb;
                L_0x0004:
                    r0 = 4;
                    r10 = r10 & r0;
                    r1 = 1;
                    r2 = 0;
                    if (r10 != r0) goto L_0x000c;
                L_0x000a:
                    r10 = 1;
                    goto L_0x000d;
                L_0x000c:
                    r10 = 0;
                L_0x000d:
                    if (r10 == 0) goto L_0x0012;
                L_0x000f:
                    r10 = r3;
                    goto L_0x0014;
                L_0x0012:
                    r10 = r4;
                L_0x0014:
                    r0 = 0;
                    r3 = r5;	 Catch:{ IllegalAccessException -> 0x0076, IllegalArgumentException -> 0x0074, InvocationTargetException -> 0x0072 }
                    monitor-enter(r3);	 Catch:{ IllegalAccessException -> 0x0076, IllegalArgumentException -> 0x0074, InvocationTargetException -> 0x0072 }
                    r4 = r6;	 Catch:{ all -> 0x006d }
                    r5 = r5;	 Catch:{ all -> 0x006d }
                    r6 = 3;
                    r6 = new java.lang.Object[r6];	 Catch:{ all -> 0x006d }
                    r6[r2] = r9;	 Catch:{ all -> 0x006d }
                    r2 = com.facebook.soloader.SoLoader.class;
                    r2 = r2.getClassLoader();	 Catch:{ all -> 0x006d }
                    r6[r1] = r2;	 Catch:{ all -> 0x006d }
                    r1 = 2;
                    r6[r1] = r10;	 Catch:{ all -> 0x006d }
                    r1 = r4.invoke(r5, r6);	 Catch:{ all -> 0x006d }
                    r1 = (java.lang.String) r1;	 Catch:{ all -> 0x006d }
                    if (r1 != 0) goto L_0x0067;
                L_0x0034:
                    monitor-exit(r3);	 Catch:{ all -> 0x0062 }
                    if (r1 == 0) goto L_0x00be;
                L_0x0037:
                    r0 = new java.lang.StringBuilder;
                    r0.<init>();
                    r2 = "Error when loading lib: ";
                    r0.append(r2);
                    r0.append(r1);
                    r1 = " lib hash: ";
                    r0.append(r1);
                    r9 = r8.getLibHash(r9);
                    r0.append(r9);
                    r9 = " search path is ";
                    r0.append(r9);
                    r0.append(r10);
                    r9 = r0.toString();
                    r10 = "SoLoader";
                    android.util.Log.e(r10, r9);
                    goto L_0x00be;
                L_0x0062:
                    r0 = move-exception;
                    r7 = r1;
                    r1 = r0;
                    r0 = r7;
                    goto L_0x006e;
                L_0x0067:
                    r0 = new java.lang.UnsatisfiedLinkError;	 Catch:{ all -> 0x0062 }
                    r0.<init>(r1);	 Catch:{ all -> 0x0062 }
                    throw r0;	 Catch:{ all -> 0x0062 }
                L_0x006d:
                    r1 = move-exception;
                L_0x006e:
                    monitor-exit(r3);	 Catch:{ all -> 0x006d }
                    throw r1;	 Catch:{ IllegalAccessException -> 0x0076, IllegalArgumentException -> 0x0074, InvocationTargetException -> 0x0072 }
                L_0x0070:
                    r1 = move-exception;
                    goto L_0x008e;
                L_0x0072:
                    r1 = move-exception;
                    goto L_0x0077;
                L_0x0074:
                    r1 = move-exception;
                    goto L_0x0077;
                L_0x0076:
                    r1 = move-exception;
                L_0x0077:
                    r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0070 }
                    r2.<init>();	 Catch:{ all -> 0x0070 }
                    r3 = "Error: Cannot load ";
                    r2.append(r3);	 Catch:{ all -> 0x0070 }
                    r2.append(r9);	 Catch:{ all -> 0x0070 }
                    r0 = r2.toString();	 Catch:{ all -> 0x0070 }
                    r2 = new java.lang.RuntimeException;	 Catch:{ all -> 0x0070 }
                    r2.<init>(r0, r1);	 Catch:{ all -> 0x0070 }
                    throw r2;	 Catch:{ all -> 0x0070 }
                L_0x008e:
                    if (r0 == 0) goto L_0x00ba;
                L_0x0090:
                    r2 = new java.lang.StringBuilder;
                    r2.<init>();
                    r3 = "Error when loading lib: ";
                    r2.append(r3);
                    r2.append(r0);
                    r0 = " lib hash: ";
                    r2.append(r0);
                    r9 = r8.getLibHash(r9);
                    r2.append(r9);
                    r9 = " search path is ";
                    r2.append(r9);
                    r2.append(r10);
                    r9 = r2.toString();
                    r10 = "SoLoader";
                    android.util.Log.e(r10, r9);
                L_0x00ba:
                    throw r1;
                L_0x00bb:
                    java.lang.System.load(r9);
                L_0x00be:
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.1.load(java.lang.String, int):void");
                }

                private String getLibHash(String str) {
                    Throwable th;
                    InputStream fileInputStream;
                    try {
                        File file = new File(str);
                        MessageDigest instance = MessageDigest.getInstance("MD5");
                        fileInputStream = new FileInputStream(file);
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read > 0) {
                                instance.update(bArr, 0, read);
                            } else {
                                str = String.format("%32x", new Object[]{new BigInteger(1, instance.digest())});
                                fileInputStream.close();
                                return str;
                            }
                        }
                    } catch (IOException e) {
                        return e.toString();
                    } catch (SecurityException e2) {
                        return e2.toString();
                    } catch (NoSuchAlgorithmException e3) {
                        return e3.toString();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
            };
        }
    }

    @Nullable
    private static Method getNativeLoadRuntimeMethod() {
        Throwable e;
        if (VERSION.SDK_INT >= 23 && VERSION.SDK_INT <= 27) {
            try {
                Method declaredMethod = Runtime.class.getDeclaredMethod("nativeLoad", new Class[]{String.class, ClassLoader.class, String.class});
                declaredMethod.setAccessible(true);
                return declaredMethod;
            } catch (NoSuchMethodException e2) {
                e = e2;
                Log.w(TAG, "Cannot get nativeLoad method", e);
                return null;
            } catch (SecurityException e3) {
                e = e3;
                Log.w(TAG, "Cannot get nativeLoad method", e);
                return null;
            }
        }
        return null;
    }

    public static void setInTestMode() {
        setSoSources(new SoSource[]{new NoopSoSource()});
    }

    public static void deinitForTest() {
        setSoSources(null);
    }

    static void setSoSources(SoSource[] soSourceArr) {
        sSoSourcesLock.writeLock().lock();
        try {
            sSoSources = soSourceArr;
            sSoSourcesVersion++;
        } finally {
            sSoSourcesLock.writeLock().unlock();
        }
    }

    static void setSoFileLoader(SoFileLoader soFileLoader) {
        sSoFileLoader = soFileLoader;
    }

    static void resetStatus() {
        synchronized (SoLoader.class) {
            sLoadedLibraries.clear();
            sLoadingLibraries.clear();
            sSoFileLoader = null;
        }
        setSoSources(null);
    }

    public static void setSystemLoadLibraryWrapper(SystemLoadLibraryWrapper systemLoadLibraryWrapper) {
        sSystemLoadLibraryWrapper = systemLoadLibraryWrapper;
    }

    public static boolean loadLibrary(String str) {
        return loadLibrary(str, 0);
    }

    public static boolean loadLibrary(String str, int i) throws UnsatisfiedLinkError {
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources == null) {
                if ("http://www.android.com/".equals(System.getProperty("java.vendor.url"))) {
                    assertInitialized();
                } else {
                    boolean contains;
                    synchronized (SoLoader.class) {
                        contains = sLoadedLibraries.contains(str) ^ 1;
                        if (contains) {
                            if (sSystemLoadLibraryWrapper != null) {
                                sSystemLoadLibraryWrapper.loadLibrary(str);
                            } else {
                                System.loadLibrary(str);
                            }
                        }
                    }
                    sSoSourcesLock.readLock().unlock();
                    return contains;
                }
            }
            sSoSourcesLock.readLock().unlock();
            String mapLibName = MergedSoMapping.mapLibName(str);
            return loadLibraryBySoName(System.mapLibraryName(mapLibName != null ? mapLibName : str), str, mapLibName, i | 2, null);
        } catch (Throwable th) {
            sSoSourcesLock.readLock().unlock();
        }
    }

    static void loadLibraryBySoName(String str, int i, ThreadPolicy threadPolicy) {
        loadLibraryBySoName(str, null, null, i, threadPolicy);
    }

    /* JADX WARNING: Missing block: B:20:0x003d, code:
            monitor-enter(r4);
     */
    /* JADX WARNING: Missing block: B:21:0x003e, code:
            if (r2 != 0) goto L_0x00b0;
     */
    /* JADX WARNING: Missing block: B:23:?, code:
            r0 = com.facebook.soloader.SoLoader.class;
     */
    /* JADX WARNING: Missing block: B:24:0x0042, code:
            monitor-enter(r0);
     */
    /* JADX WARNING: Missing block: B:27:0x0049, code:
            if (sLoadedLibraries.contains(r7) == false) goto L_0x0051;
     */
    /* JADX WARNING: Missing block: B:28:0x004b, code:
            if (r9 != null) goto L_0x0050;
     */
    /* JADX WARNING: Missing block: B:29:0x004d, code:
            monitor-exit(r0);
     */
    /* JADX WARNING: Missing block: B:31:?, code:
            monitor-exit(r4);
     */
    /* JADX WARNING: Missing block: B:32:0x004f, code:
            return false;
     */
    /* JADX WARNING: Missing block: B:33:0x0050, code:
            r2 = 1;
     */
    /* JADX WARNING: Missing block: B:35:?, code:
            monitor-exit(r0);
     */
    /* JADX WARNING: Missing block: B:36:0x0052, code:
            if (r2 != 0) goto L_0x00b0;
     */
    /* JADX WARNING: Missing block: B:38:?, code:
            r0 = TAG;
            r5 = new java.lang.StringBuilder();
            r5.append("About to load: ");
            r5.append(r7);
            android.util.Log.d(r0, r5.toString());
            doLoadLibraryBySoName(r7, r10, r11);
     */
    /* JADX WARNING: Missing block: B:40:?, code:
            r10 = com.facebook.soloader.SoLoader.class;
     */
    /* JADX WARNING: Missing block: B:41:0x006f, code:
            monitor-enter(r10);
     */
    /* JADX WARNING: Missing block: B:43:?, code:
            r11 = TAG;
            r0 = new java.lang.StringBuilder();
            r0.append("Loaded: ");
            r0.append(r7);
            android.util.Log.d(r11, r0.toString());
            sLoadedLibraries.add(r7);
     */
    /* JADX WARNING: Missing block: B:44:0x008b, code:
            monitor-exit(r10);
     */
    /* JADX WARNING: Missing block: B:49:0x0090, code:
            r7 = move-exception;
     */
    /* JADX WARNING: Missing block: B:50:0x0091, code:
            r8 = r7.getMessage();
     */
    /* JADX WARNING: Missing block: B:51:0x0095, code:
            if (r8 == null) goto L_0x00a5;
     */
    /* JADX WARNING: Missing block: B:55:0x00a4, code:
            throw new com.facebook.soloader.SoLoader.WrongAbiError(r7);
     */
    /* JADX WARNING: Missing block: B:56:0x00a5, code:
            throw r7;
     */
    /* JADX WARNING: Missing block: B:57:0x00a6, code:
            r7 = move-exception;
     */
    /* JADX WARNING: Missing block: B:59:0x00ac, code:
            throw new java.lang.RuntimeException(r7);
     */
    /* JADX WARNING: Missing block: B:66:0x00b4, code:
            if (android.text.TextUtils.isEmpty(r8) != false) goto L_0x00bf;
     */
    /* JADX WARNING: Missing block: B:68:0x00bc, code:
            if (sLoadedAndMergedLibraries.contains(r8) == false) goto L_0x00bf;
     */
    /* JADX WARNING: Missing block: B:69:0x00be, code:
            r1 = true;
     */
    /* JADX WARNING: Missing block: B:70:0x00bf, code:
            if (r9 == null) goto L_0x0117;
     */
    /* JADX WARNING: Missing block: B:71:0x00c1, code:
            if (r1 != false) goto L_0x0117;
     */
    /* JADX WARNING: Missing block: B:73:0x00c5, code:
            if (SYSTRACE_LIBRARY_LOADING == false) goto L_0x00e0;
     */
    /* JADX WARNING: Missing block: B:74:0x00c7, code:
            r9 = new java.lang.StringBuilder();
            r9.append("MergedSoMapping.invokeJniOnload[");
            r9.append(r8);
            r9.append("]");
            com.facebook.soloader.Api18TraceUtils.beginTraceSection(r9.toString());
     */
    /* JADX WARNING: Missing block: B:76:?, code:
            r9 = TAG;
            r10 = new java.lang.StringBuilder();
            r10.append("About to merge: ");
            r10.append(r8);
            r10.append(" / ");
            r10.append(r7);
            android.util.Log.d(r9, r10.toString());
            com.facebook.soloader.MergedSoMapping.invokeJniOnload(r8);
            sLoadedAndMergedLibraries.add(r8);
     */
    /* JADX WARNING: Missing block: B:79:0x0108, code:
            if (SYSTRACE_LIBRARY_LOADING == false) goto L_0x0117;
     */
    /* JADX WARNING: Missing block: B:80:0x010a, code:
            com.facebook.soloader.Api18TraceUtils.endSection();
     */
    /* JADX WARNING: Missing block: B:83:0x0111, code:
            if (SYSTRACE_LIBRARY_LOADING != false) goto L_0x0113;
     */
    /* JADX WARNING: Missing block: B:84:0x0113, code:
            com.facebook.soloader.Api18TraceUtils.endSection();
     */
    /* JADX WARNING: Missing block: B:86:0x0117, code:
            monitor-exit(r4);
     */
    /* JADX WARNING: Missing block: B:88:0x011a, code:
            return r2 ^ 1;
     */
    private static boolean loadLibraryBySoName(java.lang.String r7, @javax.annotation.Nullable java.lang.String r8, @javax.annotation.Nullable java.lang.String r9, int r10, @javax.annotation.Nullable android.os.StrictMode.ThreadPolicy r11) {
        /*
        r0 = android.text.TextUtils.isEmpty(r8);
        r1 = 0;
        if (r0 != 0) goto L_0x0010;
    L_0x0007:
        r0 = sLoadedAndMergedLibraries;
        r0 = r0.contains(r8);
        if (r0 == 0) goto L_0x0010;
    L_0x000f:
        return r1;
    L_0x0010:
        r0 = com.facebook.soloader.SoLoader.class;
        monitor-enter(r0);
        r2 = sLoadedLibraries;	 Catch:{ all -> 0x011e }
        r2 = r2.contains(r7);	 Catch:{ all -> 0x011e }
        r3 = 1;
        if (r2 == 0) goto L_0x0022;
    L_0x001c:
        if (r9 != 0) goto L_0x0020;
    L_0x001e:
        monitor-exit(r0);	 Catch:{ all -> 0x011e }
        return r1;
    L_0x0020:
        r2 = 1;
        goto L_0x0023;
    L_0x0022:
        r2 = 0;
    L_0x0023:
        r4 = sLoadingLibraries;	 Catch:{ all -> 0x011e }
        r4 = r4.containsKey(r7);	 Catch:{ all -> 0x011e }
        if (r4 == 0) goto L_0x0032;
    L_0x002b:
        r4 = sLoadingLibraries;	 Catch:{ all -> 0x011e }
        r4 = r4.get(r7);	 Catch:{ all -> 0x011e }
        goto L_0x003c;
    L_0x0032:
        r4 = new java.lang.Object;	 Catch:{ all -> 0x011e }
        r4.<init>();	 Catch:{ all -> 0x011e }
        r5 = sLoadingLibraries;	 Catch:{ all -> 0x011e }
        r5.put(r7, r4);	 Catch:{ all -> 0x011e }
    L_0x003c:
        monitor-exit(r0);	 Catch:{ all -> 0x011e }
        monitor-enter(r4);
        if (r2 != 0) goto L_0x00b0;
    L_0x0040:
        r0 = com.facebook.soloader.SoLoader.class;
        monitor-enter(r0);	 Catch:{ all -> 0x011b }
        r5 = sLoadedLibraries;	 Catch:{ all -> 0x00ad }
        r5 = r5.contains(r7);	 Catch:{ all -> 0x00ad }
        if (r5 == 0) goto L_0x0051;
    L_0x004b:
        if (r9 != 0) goto L_0x0050;
    L_0x004d:
        monitor-exit(r0);	 Catch:{ all -> 0x00ad }
        monitor-exit(r4);	 Catch:{ all -> 0x011b }
        return r1;
    L_0x0050:
        r2 = 1;
    L_0x0051:
        monitor-exit(r0);	 Catch:{ all -> 0x00ad }
        if (r2 != 0) goto L_0x00b0;
    L_0x0054:
        r0 = "SoLoader";
        r5 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00a6, UnsatisfiedLinkError -> 0x0090 }
        r5.<init>();	 Catch:{ IOException -> 0x00a6, UnsatisfiedLinkError -> 0x0090 }
        r6 = "About to load: ";
        r5.append(r6);	 Catch:{ IOException -> 0x00a6, UnsatisfiedLinkError -> 0x0090 }
        r5.append(r7);	 Catch:{ IOException -> 0x00a6, UnsatisfiedLinkError -> 0x0090 }
        r5 = r5.toString();	 Catch:{ IOException -> 0x00a6, UnsatisfiedLinkError -> 0x0090 }
        android.util.Log.d(r0, r5);	 Catch:{ IOException -> 0x00a6, UnsatisfiedLinkError -> 0x0090 }
        doLoadLibraryBySoName(r7, r10, r11);	 Catch:{ IOException -> 0x00a6, UnsatisfiedLinkError -> 0x0090 }
        r10 = com.facebook.soloader.SoLoader.class;
        monitor-enter(r10);	 Catch:{ all -> 0x011b }
        r11 = "SoLoader";
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x008d }
        r0.<init>();	 Catch:{ all -> 0x008d }
        r5 = "Loaded: ";
        r0.append(r5);	 Catch:{ all -> 0x008d }
        r0.append(r7);	 Catch:{ all -> 0x008d }
        r0 = r0.toString();	 Catch:{ all -> 0x008d }
        android.util.Log.d(r11, r0);	 Catch:{ all -> 0x008d }
        r11 = sLoadedLibraries;	 Catch:{ all -> 0x008d }
        r11.add(r7);	 Catch:{ all -> 0x008d }
        monitor-exit(r10);	 Catch:{ all -> 0x008d }
        goto L_0x00b0;
    L_0x008d:
        r7 = move-exception;
        monitor-exit(r10);	 Catch:{ all -> 0x008d }
        throw r7;	 Catch:{ all -> 0x011b }
    L_0x0090:
        r7 = move-exception;
        r8 = r7.getMessage();	 Catch:{ all -> 0x011b }
        if (r8 == 0) goto L_0x00a5;
    L_0x0097:
        r9 = "unexpected e_machine:";
        r8 = r8.contains(r9);	 Catch:{ all -> 0x011b }
        if (r8 == 0) goto L_0x00a5;
    L_0x009f:
        r8 = new com.facebook.soloader.SoLoader$WrongAbiError;	 Catch:{ all -> 0x011b }
        r8.<init>(r7);	 Catch:{ all -> 0x011b }
        throw r8;	 Catch:{ all -> 0x011b }
    L_0x00a5:
        throw r7;	 Catch:{ all -> 0x011b }
    L_0x00a6:
        r7 = move-exception;
        r8 = new java.lang.RuntimeException;	 Catch:{ all -> 0x011b }
        r8.<init>(r7);	 Catch:{ all -> 0x011b }
        throw r8;	 Catch:{ all -> 0x011b }
    L_0x00ad:
        r7 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00ad }
        throw r7;	 Catch:{ all -> 0x011b }
    L_0x00b0:
        r10 = android.text.TextUtils.isEmpty(r8);	 Catch:{ all -> 0x011b }
        if (r10 != 0) goto L_0x00bf;
    L_0x00b6:
        r10 = sLoadedAndMergedLibraries;	 Catch:{ all -> 0x011b }
        r10 = r10.contains(r8);	 Catch:{ all -> 0x011b }
        if (r10 == 0) goto L_0x00bf;
    L_0x00be:
        r1 = 1;
    L_0x00bf:
        if (r9 == 0) goto L_0x0117;
    L_0x00c1:
        if (r1 != 0) goto L_0x0117;
    L_0x00c3:
        r9 = SYSTRACE_LIBRARY_LOADING;	 Catch:{ all -> 0x011b }
        if (r9 == 0) goto L_0x00e0;
    L_0x00c7:
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011b }
        r9.<init>();	 Catch:{ all -> 0x011b }
        r10 = "MergedSoMapping.invokeJniOnload[";
        r9.append(r10);	 Catch:{ all -> 0x011b }
        r9.append(r8);	 Catch:{ all -> 0x011b }
        r10 = "]";
        r9.append(r10);	 Catch:{ all -> 0x011b }
        r9 = r9.toString();	 Catch:{ all -> 0x011b }
        com.facebook.soloader.Api18TraceUtils.beginTraceSection(r9);	 Catch:{ all -> 0x011b }
    L_0x00e0:
        r9 = "SoLoader";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x010e }
        r10.<init>();	 Catch:{ all -> 0x010e }
        r11 = "About to merge: ";
        r10.append(r11);	 Catch:{ all -> 0x010e }
        r10.append(r8);	 Catch:{ all -> 0x010e }
        r11 = " / ";
        r10.append(r11);	 Catch:{ all -> 0x010e }
        r10.append(r7);	 Catch:{ all -> 0x010e }
        r7 = r10.toString();	 Catch:{ all -> 0x010e }
        android.util.Log.d(r9, r7);	 Catch:{ all -> 0x010e }
        com.facebook.soloader.MergedSoMapping.invokeJniOnload(r8);	 Catch:{ all -> 0x010e }
        r7 = sLoadedAndMergedLibraries;	 Catch:{ all -> 0x010e }
        r7.add(r8);	 Catch:{ all -> 0x010e }
        r7 = SYSTRACE_LIBRARY_LOADING;	 Catch:{ all -> 0x011b }
        if (r7 == 0) goto L_0x0117;
    L_0x010a:
        com.facebook.soloader.Api18TraceUtils.endSection();	 Catch:{ all -> 0x011b }
        goto L_0x0117;
    L_0x010e:
        r7 = move-exception;
        r8 = SYSTRACE_LIBRARY_LOADING;	 Catch:{ all -> 0x011b }
        if (r8 == 0) goto L_0x0116;
    L_0x0113:
        com.facebook.soloader.Api18TraceUtils.endSection();	 Catch:{ all -> 0x011b }
    L_0x0116:
        throw r7;	 Catch:{ all -> 0x011b }
    L_0x0117:
        monitor-exit(r4);	 Catch:{ all -> 0x011b }
        r7 = r2 ^ 1;
        return r7;
    L_0x011b:
        r7 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x011b }
        throw r7;
    L_0x011e:
        r7 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x011e }
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.loadLibraryBySoName(java.lang.String, java.lang.String, java.lang.String, int, android.os.StrictMode$ThreadPolicy):boolean");
    }

    public static File unpackLibraryAndDependencies(String str) throws UnsatisfiedLinkError {
        assertInitialized();
        try {
            return unpackLibraryBySoName(System.mapLibraryName(str));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x0121 A:{REMOVE} */
    private static void doLoadLibraryBySoName(java.lang.String r12, int r13, android.os.StrictMode.ThreadPolicy r14) throws java.io.IOException {
        /*
        r0 = sSoSourcesLock;
        r0 = r0.readLock();
        r0.lock();
        r0 = sSoSources;	 Catch:{ all -> 0x01b1 }
        r1 = "couldn't find DSO to load: ";
        r2 = "SoLoader";
        if (r0 == 0) goto L_0x0183;
    L_0x0011:
        r0 = sSoSourcesLock;
        r0 = r0.readLock();
        r0.unlock();
        r0 = 0;
        r3 = 1;
        if (r14 != 0) goto L_0x0024;
    L_0x001e:
        r14 = android.os.StrictMode.allowThreadDiskReads();
        r4 = 1;
        goto L_0x0025;
    L_0x0024:
        r4 = 0;
    L_0x0025:
        r5 = SYSTRACE_LIBRARY_LOADING;
        if (r5 == 0) goto L_0x0042;
    L_0x0029:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "SoLoader.loadLibrary[";
        r5.append(r6);
        r5.append(r12);
        r6 = "]";
        r5.append(r6);
        r5 = r5.toString();
        com.facebook.soloader.Api18TraceUtils.beginTraceSection(r5);
    L_0x0042:
        r5 = 0;
    L_0x0043:
        r6 = 3;
        r7 = sSoSourcesLock;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r7 = r7.readLock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r7.lock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r7 = sSoSourcesVersion;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8 = 0;
    L_0x0050:
        if (r5 != 0) goto L_0x009d;
    L_0x0052:
        r9 = sSoSources;	 Catch:{ all -> 0x0092 }
        r9 = r9.length;	 Catch:{ all -> 0x0092 }
        if (r8 >= r9) goto L_0x009d;
    L_0x0057:
        r9 = sSoSources;	 Catch:{ all -> 0x0092 }
        r9 = r9[r8];	 Catch:{ all -> 0x0092 }
        r5 = r9.loadLibrary(r12, r13, r14);	 Catch:{ all -> 0x0092 }
        if (r5 != r6) goto L_0x008f;
    L_0x0061:
        r9 = sBackupSoSources;	 Catch:{ all -> 0x0092 }
        if (r9 == 0) goto L_0x008f;
    L_0x0065:
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0092 }
        r8.<init>();	 Catch:{ all -> 0x0092 }
        r9 = "Trying backup SoSource for ";
        r8.append(r9);	 Catch:{ all -> 0x0092 }
        r8.append(r12);	 Catch:{ all -> 0x0092 }
        r8 = r8.toString();	 Catch:{ all -> 0x0092 }
        android.util.Log.d(r2, r8);	 Catch:{ all -> 0x0092 }
        r8 = sBackupSoSources;	 Catch:{ all -> 0x0092 }
        r9 = r8.length;	 Catch:{ all -> 0x0092 }
        r10 = 0;
    L_0x007d:
        if (r10 >= r9) goto L_0x009d;
    L_0x007f:
        r11 = r8[r10];	 Catch:{ all -> 0x0092 }
        r11.prepare(r12);	 Catch:{ all -> 0x0092 }
        r11 = r11.loadLibrary(r12, r13, r14);	 Catch:{ all -> 0x0092 }
        if (r11 != r3) goto L_0x008c;
    L_0x008a:
        r5 = r11;
        goto L_0x009d;
    L_0x008c:
        r10 = r10 + 1;
        goto L_0x007d;
    L_0x008f:
        r8 = r8 + 1;
        goto L_0x0050;
    L_0x0092:
        r13 = move-exception;
        r0 = sSoSourcesLock;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r0 = r0.readLock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r0.unlock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        throw r13;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
    L_0x009d:
        r8 = sSoSourcesLock;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8 = r8.readLock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8.unlock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8 = r13 & 2;
        r9 = 2;
        if (r8 != r9) goto L_0x00e3;
    L_0x00ab:
        if (r5 != 0) goto L_0x00e3;
    L_0x00ad:
        r8 = sSoSourcesLock;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8 = r8.writeLock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8.lock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8 = sApplicationSoSource;	 Catch:{ all -> 0x00d8 }
        if (r8 == 0) goto L_0x00c7;
    L_0x00ba:
        r8 = sApplicationSoSource;	 Catch:{ all -> 0x00d8 }
        r8 = r8.checkAndMaybeUpdate();	 Catch:{ all -> 0x00d8 }
        if (r8 == 0) goto L_0x00c7;
    L_0x00c2:
        r8 = sSoSourcesVersion;	 Catch:{ all -> 0x00d8 }
        r8 = r8 + r3;
        sSoSourcesVersion = r8;	 Catch:{ all -> 0x00d8 }
    L_0x00c7:
        r8 = sSoSourcesVersion;	 Catch:{ all -> 0x00d8 }
        if (r8 == r7) goto L_0x00cd;
    L_0x00cb:
        r7 = 1;
        goto L_0x00ce;
    L_0x00cd:
        r7 = 0;
    L_0x00ce:
        r8 = sSoSourcesLock;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8 = r8.writeLock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r8.unlock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        goto L_0x00e4;
    L_0x00d8:
        r13 = move-exception;
        r0 = sSoSourcesLock;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r0 = r0.writeLock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        r0.unlock();	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
        throw r13;	 Catch:{ Throwable -> 0x013a, all -> 0x010f }
    L_0x00e3:
        r7 = 0;
    L_0x00e4:
        if (r7 != 0) goto L_0x0043;
    L_0x00e6:
        r13 = SYSTRACE_LIBRARY_LOADING;
        if (r13 == 0) goto L_0x00ed;
    L_0x00ea:
        com.facebook.soloader.Api18TraceUtils.endSection();
    L_0x00ed:
        if (r4 == 0) goto L_0x00f2;
    L_0x00ef:
        android.os.StrictMode.setThreadPolicy(r14);
    L_0x00f2:
        if (r5 == 0) goto L_0x00f7;
    L_0x00f4:
        if (r5 == r6) goto L_0x00f7;
    L_0x00f6:
        goto L_0x014c;
    L_0x00f7:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r13.append(r1);
        r13.append(r12);
        r12 = r13.toString();
        android.util.Log.e(r2, r12);
        r13 = new java.lang.UnsatisfiedLinkError;
        r13.<init>(r12);
        throw r13;
    L_0x010f:
        r13 = move-exception;
        r0 = SYSTRACE_LIBRARY_LOADING;
        if (r0 == 0) goto L_0x0117;
    L_0x0114:
        com.facebook.soloader.Api18TraceUtils.endSection();
    L_0x0117:
        if (r4 == 0) goto L_0x011c;
    L_0x0119:
        android.os.StrictMode.setThreadPolicy(r14);
    L_0x011c:
        if (r5 == 0) goto L_0x0122;
    L_0x011e:
        if (r5 != r6) goto L_0x0121;
    L_0x0120:
        goto L_0x0122;
    L_0x0121:
        throw r13;
    L_0x0122:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r13.append(r1);
        r13.append(r12);
        r12 = r13.toString();
        android.util.Log.e(r2, r12);
        r13 = new java.lang.UnsatisfiedLinkError;
        r13.<init>(r12);
        throw r13;
    L_0x013a:
        r13 = move-exception;
        r0 = SYSTRACE_LIBRARY_LOADING;
        if (r0 == 0) goto L_0x0142;
    L_0x013f:
        com.facebook.soloader.Api18TraceUtils.endSection();
    L_0x0142:
        if (r4 == 0) goto L_0x0147;
    L_0x0144:
        android.os.StrictMode.setThreadPolicy(r14);
    L_0x0147:
        if (r5 == 0) goto L_0x014d;
    L_0x0149:
        if (r5 != r6) goto L_0x014c;
    L_0x014b:
        goto L_0x014d;
    L_0x014c:
        return;
    L_0x014d:
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r14.append(r1);
        r14.append(r12);
        r12 = r14.toString();
        r14 = r13.getMessage();
        if (r14 != 0) goto L_0x0166;
    L_0x0162:
        r14 = r13.toString();
    L_0x0166:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r13.append(r12);
        r12 = " caused by: ";
        r13.append(r12);
        r13.append(r14);
        r12 = r13.toString();
        android.util.Log.e(r2, r12);
        r13 = new java.lang.UnsatisfiedLinkError;
        r13.<init>(r12);
        throw r13;
    L_0x0183:
        r13 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01b1 }
        r13.<init>();	 Catch:{ all -> 0x01b1 }
        r14 = "Could not load: ";
        r13.append(r14);	 Catch:{ all -> 0x01b1 }
        r13.append(r12);	 Catch:{ all -> 0x01b1 }
        r14 = " because no SO source exists";
        r13.append(r14);	 Catch:{ all -> 0x01b1 }
        r13 = r13.toString();	 Catch:{ all -> 0x01b1 }
        android.util.Log.e(r2, r13);	 Catch:{ all -> 0x01b1 }
        r13 = new java.lang.UnsatisfiedLinkError;	 Catch:{ all -> 0x01b1 }
        r14 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01b1 }
        r14.<init>();	 Catch:{ all -> 0x01b1 }
        r14.append(r1);	 Catch:{ all -> 0x01b1 }
        r14.append(r12);	 Catch:{ all -> 0x01b1 }
        r12 = r14.toString();	 Catch:{ all -> 0x01b1 }
        r13.<init>(r12);	 Catch:{ all -> 0x01b1 }
        throw r13;	 Catch:{ all -> 0x01b1 }
    L_0x01b1:
        r12 = move-exception;
        r13 = sSoSourcesLock;
        r13 = r13.readLock();
        r13.unlock();
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.doLoadLibraryBySoName(java.lang.String, int, android.os.StrictMode$ThreadPolicy):void");
    }

    @Nullable
    public static String makeNonZipPath(String str) {
        if (str == null) {
            return null;
        }
        CharSequence charSequence = ":";
        String[] split = str.split(charSequence);
        Iterable arrayList = new ArrayList(split.length);
        for (String str2 : split) {
            if (!str2.contains("!")) {
                arrayList.add(str2);
            }
        }
        return TextUtils.join(charSequence, arrayList);
    }

    static File unpackLibraryBySoName(String str) throws IOException {
        sSoSourcesLock.readLock().lock();
        int i = 0;
        while (i < sSoSources.length) {
            try {
                File unpackLibrary = sSoSources[i].unpackLibrary(str);
                if (unpackLibrary != null) {
                    return unpackLibrary;
                }
                i++;
            } finally {
                sSoSourcesLock.readLock().unlock();
            }
        }
        sSoSourcesLock.readLock().unlock();
        throw new FileNotFoundException(str);
    }

    private static void assertInitialized() {
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources == null) {
                throw new RuntimeException("SoLoader.init() not yet called");
            }
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static void prependSoSource(SoSource soSource) throws IOException {
        String str = TAG;
        sSoSourcesLock.writeLock().lock();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Prepending to SO sources: ");
            stringBuilder.append(soSource);
            Log.d(str, stringBuilder.toString());
            assertInitialized();
            soSource.prepare(makePrepareFlags());
            Object obj = new SoSource[(sSoSources.length + 1)];
            obj[0] = soSource;
            System.arraycopy(sSoSources, 0, obj, 1, sSoSources.length);
            sSoSources = obj;
            sSoSourcesVersion++;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Prepended to SO sources: ");
            stringBuilder.append(soSource);
            Log.d(str, stringBuilder.toString());
        } finally {
            sSoSourcesLock.writeLock().unlock();
        }
    }

    public static String makeLdLibraryPath() {
        String str = TAG;
        sSoSourcesLock.readLock().lock();
        String join;
        try {
            assertInitialized();
            Log.d(str, "makeLdLibraryPath");
            Iterable arrayList = new ArrayList();
            SoSource[] soSourceArr = sSoSources;
            for (SoSource addToLdLibraryPath : soSourceArr) {
                addToLdLibraryPath.addToLdLibraryPath(arrayList);
            }
            join = TextUtils.join(":", arrayList);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("makeLdLibraryPath final path: ");
            stringBuilder.append(join);
            Log.d(str, stringBuilder.toString());
            return join;
        } finally {
            join = sSoSourcesLock.readLock();
            join.unlock();
        }
    }

    public static boolean areSoSourcesAbisSupported() {
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources != null) {
                String[] supportedAbis = SysUtil.getSupportedAbis();
                for (SoSource soSourceAbis : sSoSources) {
                    String[] soSourceAbis2 = soSourceAbis.getSoSourceAbis();
                    int i = 0;
                    while (i < soSourceAbis2.length) {
                        boolean z = false;
                        for (int i2 = 0; i2 < supportedAbis.length && !z; i2++) {
                            z = soSourceAbis2[i].equals(supportedAbis[i2]);
                        }
                        if (z) {
                            i++;
                        } else {
                            String str = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("abi not supported: ");
                            stringBuilder.append(soSourceAbis2[i]);
                            Log.e(str, stringBuilder.toString());
                        }
                    }
                }
                sSoSourcesLock.readLock().unlock();
                return true;
            }
            sSoSourcesLock.readLock().unlock();
            return false;
        } catch (Throwable th) {
            sSoSourcesLock.readLock().unlock();
        }
    }
}
