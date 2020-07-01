package com.drew.tools;

import com.drew.lang.annotations.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0013  */
    public static void saveBytes(@com.drew.lang.annotations.NotNull java.io.File r2, @com.drew.lang.annotations.NotNull byte[] r3) throws java.io.IOException {
        /*
        r0 = 0;
        r1 = new java.io.FileOutputStream;	 Catch:{ all -> 0x0010 }
        r1.<init>(r2);	 Catch:{ all -> 0x0010 }
        r1.write(r3);	 Catch:{ all -> 0x000d }
        r1.close();
        return;
    L_0x000d:
        r2 = move-exception;
        r0 = r1;
        goto L_0x0011;
    L_0x0010:
        r2 = move-exception;
    L_0x0011:
        if (r0 == 0) goto L_0x0016;
    L_0x0013:
        r0.close();
    L_0x0016:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.tools.FileUtil.saveBytes(java.io.File, byte[]):void");
    }

    @NotNull
    public static byte[] readBytes(@NotNull File file) throws IOException {
        Throwable th;
        int length = (int) file.length();
        byte[] bArr = new byte[length];
        int i = 0;
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            while (i != length) {
                try {
                    int read = fileInputStream2.read(bArr, i, length - i);
                    if (read == -1) {
                        break;
                    }
                    i += read;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                }
            }
            fileInputStream2.close();
            return bArr;
        } catch (Throwable th3) {
            th = th3;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    @NotNull
    public static byte[] readBytes(@NotNull String str) throws IOException {
        return readBytes(new File(str));
    }
}
