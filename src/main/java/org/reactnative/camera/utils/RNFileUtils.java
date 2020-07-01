package org.reactnative.camera.utils;

import android.net.Uri;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RNFileUtils {
    public static File ensureDirExists(File file) throws IOException {
        if (file.isDirectory() || file.mkdirs()) {
            return file;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't create directory '");
        stringBuilder.append(file);
        stringBuilder.append("'");
        throw new IOException(stringBuilder.toString());
    }

    public static String getOutputFilePath(File file, String str) throws IOException {
        ensureDirExists(file);
        String uuid = UUID.randomUUID().toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file);
        stringBuilder.append(File.separator);
        stringBuilder.append(uuid);
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    public static Uri uriFromFile(File file) {
        return Uri.fromFile(file);
    }
}
