package com.android.vending.expansion.zipfile;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class APKExpansionSupport {
    private static final String EXP_PATH = "/Android/obb/";

    static String[] getAPKExpansionFiles(Context context, int i, int i2) {
        String packageName = context.getPackageName();
        Vector vector = new Vector();
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(externalStorageDirectory.toString());
            stringBuilder.append(EXP_PATH);
            stringBuilder.append(packageName);
            File file = new File(stringBuilder.toString());
            if (file.exists()) {
                String str = ".obb";
                String str2 = ".";
                if (i > 0) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(file);
                    stringBuilder2.append(File.separator);
                    stringBuilder2.append("main.");
                    stringBuilder2.append(i);
                    stringBuilder2.append(str2);
                    stringBuilder2.append(packageName);
                    stringBuilder2.append(str);
                    String stringBuilder3 = stringBuilder2.toString();
                    if (new File(stringBuilder3).isFile()) {
                        vector.add(stringBuilder3);
                    }
                }
                if (i2 > 0) {
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(file);
                    stringBuilder4.append(File.separator);
                    stringBuilder4.append("patch.");
                    stringBuilder4.append(i2);
                    stringBuilder4.append(str2);
                    stringBuilder4.append(packageName);
                    stringBuilder4.append(str);
                    packageName = stringBuilder4.toString();
                    if (new File(packageName).isFile()) {
                        vector.add(packageName);
                    }
                }
            }
        }
        String[] strArr = new String[vector.size()];
        vector.toArray(strArr);
        return strArr;
    }

    public static ZipResourceFile getResourceZipFile(String[] strArr) throws IOException {
        ZipResourceFile zipResourceFile = null;
        for (String str : strArr) {
            if (zipResourceFile == null) {
                zipResourceFile = new ZipResourceFile(str);
            } else {
                zipResourceFile.addPatchFile(str);
            }
        }
        return zipResourceFile;
    }

    public static ZipResourceFile getAPKExpansionZipFile(Context context, int i, int i2) throws IOException {
        return getResourceZipFile(getAPKExpansionFiles(context, i, i2));
    }
}
