package com.airbnb.lottie.network;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.util.Pair;
import com.airbnb.lottie.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class NetworkCache {
    private final Context appContext;
    private final String url;

    NetworkCache(Context context, String str) {
        this.appContext = context.getApplicationContext();
        this.url = str;
    }

    @WorkerThread
    @Nullable
    Pair<FileExtension, InputStream> fetch() {
        try {
            File cachedFile = getCachedFile(this.url);
            if (cachedFile == null) {
                return null;
            }
            Object obj;
            FileInputStream fileInputStream = new FileInputStream(cachedFile);
            if (cachedFile.getAbsolutePath().endsWith(".zip")) {
                obj = FileExtension.ZIP;
            } else {
                obj = FileExtension.JSON;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cache hit for ");
            stringBuilder.append(this.url);
            stringBuilder.append(" at ");
            stringBuilder.append(cachedFile.getAbsolutePath());
            Logger.debug(stringBuilder.toString());
            return new Pair(obj, fileInputStream);
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    File writeTempCacheFile(InputStream inputStream, FileExtension fileExtension) throws IOException {
        File file = new File(this.appContext.getCacheDir(), filenameForUrl(this.url, fileExtension, true));
        OutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStream.close();
                    return file;
                }
            }
        } catch (Throwable th) {
            inputStream.close();
        }
    }

    void renameTempFile(FileExtension fileExtension) {
        File file = new File(this.appContext.getCacheDir(), filenameForUrl(this.url, fileExtension, true));
        File file2 = new File(file.getAbsolutePath().replace(".temp", ""));
        boolean renameTo = file.renameTo(file2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Copying temp file to real file (");
        stringBuilder.append(file2);
        stringBuilder.append(")");
        Logger.debug(stringBuilder.toString());
        if (!renameTo) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unable to rename cache file ");
            stringBuilder2.append(file.getAbsolutePath());
            stringBuilder2.append(" to ");
            stringBuilder2.append(file2.getAbsolutePath());
            stringBuilder2.append(".");
            Logger.warning(stringBuilder2.toString());
        }
    }

    @Nullable
    private File getCachedFile(String str) throws FileNotFoundException {
        File file = new File(this.appContext.getCacheDir(), filenameForUrl(str, FileExtension.JSON, false));
        if (file.exists()) {
            return file;
        }
        file = new File(this.appContext.getCacheDir(), filenameForUrl(str, FileExtension.ZIP, false));
        return file.exists() ? file : null;
    }

    private static String filenameForUrl(String str, FileExtension fileExtension, boolean z) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("lottie_cache_");
        stringBuilder.append(str.replaceAll("\\W+", ""));
        stringBuilder.append(z ? fileExtension.tempExtension() : fileExtension.extension);
        return stringBuilder.toString();
    }
}
