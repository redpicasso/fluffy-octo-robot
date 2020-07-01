package com.facebook.soloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public final class SysUtil {
    private static final byte APK_SIGNATURE_VERSION = (byte) 1;

    @DoNotOptimize
    @TargetApi(21)
    private static final class LollipopSysdeps {
        private LollipopSysdeps() {
        }

        @DoNotOptimize
        public static String[] getSupportedAbis() {
            return Build.SUPPORTED_ABIS;
        }

        @DoNotOptimize
        public static void fallocateIfSupported(FileDescriptor fileDescriptor, long j) throws IOException {
            try {
                Os.posix_fallocate(fileDescriptor, 0, j);
            } catch (Throwable e) {
                if (e.errno != OsConstants.EOPNOTSUPP && e.errno != OsConstants.ENOSYS && e.errno != OsConstants.EINVAL) {
                    throw new IOException(e.toString(), e);
                }
            }
        }
    }

    public static int findAbiScore(String[] strArr, String str) {
        int i = 0;
        while (i < strArr.length) {
            if (strArr[i] != null && str.equals(strArr[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static void deleteOrThrow(File file) throws IOException {
        if (!file.delete()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("could not delete file ");
            stringBuilder.append(file);
            throw new IOException(stringBuilder.toString());
        }
    }

    public static String[] getSupportedAbis() {
        if (VERSION.SDK_INT >= 21) {
            return LollipopSysdeps.getSupportedAbis();
        }
        return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
    }

    public static void fallocateIfSupported(FileDescriptor fileDescriptor, long j) throws IOException {
        if (VERSION.SDK_INT >= 21) {
            LollipopSysdeps.fallocateIfSupported(fileDescriptor, j);
        }
    }

    public static void dumbDeleteRecursive(File file) throws IOException {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File dumbDeleteRecursive : listFiles) {
                    dumbDeleteRecursive(dumbDeleteRecursive);
                }
            } else {
                return;
            }
        }
        if (!file.delete() && file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("could not delete: ");
            stringBuilder.append(file);
            throw new IOException(stringBuilder.toString());
        }
    }

    static void mkdirOrThrow(File file) throws IOException {
        if (!file.mkdirs() && !file.isDirectory()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("cannot mkdir: ");
            stringBuilder.append(file);
            throw new IOException(stringBuilder.toString());
        }
    }

    static int copyBytes(RandomAccessFile randomAccessFile, InputStream inputStream, int i, byte[] bArr) throws IOException {
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, 0, Math.min(bArr.length, i - i2));
            if (read == -1) {
                break;
            }
            randomAccessFile.write(bArr, 0, read);
            i2 += read;
        }
        return i2;
    }

    /* JADX WARNING: Missing block: B:20:0x0053, code:
            if (r3 != null) goto L_0x0055;
     */
    /* JADX WARNING: Missing block: B:22:?, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:23:0x0059, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:24:0x005a, code:
            r3.addSuppressed(r0);
     */
    /* JADX WARNING: Missing block: B:25:0x005e, code:
            r0.close();
     */
    static void fsyncRecursive(java.io.File r3) throws java.io.IOException {
        /*
        r0 = r3.isDirectory();
        if (r0 == 0) goto L_0x002f;
    L_0x0006:
        r0 = r3.listFiles();
        if (r0 == 0) goto L_0x0018;
    L_0x000c:
        r3 = 0;
    L_0x000d:
        r1 = r0.length;
        if (r3 >= r1) goto L_0x004e;
    L_0x0010:
        r1 = r0[r3];
        fsyncRecursive(r1);
        r3 = r3 + 1;
        goto L_0x000d;
    L_0x0018:
        r0 = new java.io.IOException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "cannot list directory ";
        r1.append(r2);
        r1.append(r3);
        r3 = r1.toString();
        r0.<init>(r3);
        throw r0;
    L_0x002f:
        r0 = r3.getPath();
        r1 = "_lock";
        r0 = r0.endsWith(r1);
        if (r0 == 0) goto L_0x003c;
    L_0x003b:
        goto L_0x004e;
    L_0x003c:
        r0 = new java.io.RandomAccessFile;
        r1 = "r";
        r0.<init>(r3, r1);
        r3 = 0;
        r1 = r0.getFD();	 Catch:{ Throwable -> 0x0051 }
        r1.sync();	 Catch:{ Throwable -> 0x0051 }
        r0.close();
    L_0x004e:
        return;
    L_0x004f:
        r1 = move-exception;
        goto L_0x0053;
    L_0x0051:
        r3 = move-exception;
        throw r3;	 Catch:{ all -> 0x004f }
    L_0x0053:
        if (r3 == 0) goto L_0x005e;
    L_0x0055:
        r0.close();	 Catch:{ Throwable -> 0x0059 }
        goto L_0x0061;
    L_0x0059:
        r0 = move-exception;
        r3.addSuppressed(r0);
        goto L_0x0061;
    L_0x005e:
        r0.close();
    L_0x0061:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SysUtil.fsyncRecursive(java.io.File):void");
    }

    public static byte[] makeApkDepBlock(File file, Context context) throws IOException {
        file = file.getCanonicalFile();
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeByte((byte) 1);
            obtain.writeString(file.getPath());
            obtain.writeLong(file.lastModified());
            obtain.writeInt(getAppVersionCode(context));
            byte[] marshall = obtain.marshall();
            return marshall;
        } finally {
            obtain.recycle();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x0012 A:{RETURN, ExcHandler: android.content.pm.PackageManager.NameNotFoundException (unused android.content.pm.PackageManager$NameNotFoundException), Splitter: B:2:0x0007} */
    /* JADX WARNING: Missing block: B:5:0x0012, code:
            return 0;
     */
    public static int getAppVersionCode(android.content.Context r2) {
        /*
        r0 = r2.getPackageManager();
        r1 = 0;
        if (r0 == 0) goto L_0x0012;
    L_0x0007:
        r2 = r2.getPackageName();	 Catch:{ NameNotFoundException -> 0x0012, NameNotFoundException -> 0x0012 }
        r2 = r0.getPackageInfo(r2, r1);	 Catch:{ NameNotFoundException -> 0x0012, NameNotFoundException -> 0x0012 }
        r2 = r2.versionCode;	 Catch:{ NameNotFoundException -> 0x0012, NameNotFoundException -> 0x0012 }
        return r2;
    L_0x0012:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SysUtil.getAppVersionCode(android.content.Context):int");
    }
}
