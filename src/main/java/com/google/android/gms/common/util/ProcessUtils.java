package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

@KeepForSdk
public class ProcessUtils {
    private static String zzhf;
    private static int zzhg;

    private ProcessUtils() {
    }

    @KeepForSdk
    @Nullable
    public static String getMyProcessName() {
        if (zzhf == null) {
            if (zzhg == 0) {
                zzhg = Process.myPid();
            }
            zzhf = zzd(zzhg);
        }
        return zzhf;
    }

    @Nullable
    private static String zzd(int i) {
        Closeable closeable;
        Throwable th;
        String str = null;
        if (i <= 0) {
            return null;
        }
        Closeable zzk;
        try {
            StringBuilder stringBuilder = new StringBuilder(25);
            stringBuilder.append("/proc/");
            stringBuilder.append(i);
            stringBuilder.append("/cmdline");
            zzk = zzk(stringBuilder.toString());
            try {
                str = zzk.readLine().trim();
                IOUtils.closeQuietly(zzk);
            } catch (IOException unused) {
                IOUtils.closeQuietly(zzk);
            } catch (Throwable th2) {
                Throwable th3 = th2;
                closeable = zzk;
                th = th3;
                IOUtils.closeQuietly(closeable);
                throw th;
            }
            return str;
        } catch (IOException unused2) {
            zzk = null;
        } catch (Throwable th4) {
            th = th4;
            IOUtils.closeQuietly(closeable);
            throw th;
        }
    }

    private static BufferedReader zzk(String str) throws IOException {
        ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            return bufferedReader;
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }
}
