package com.reactnative.ivpusic.imagepicker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class Compression {
    int getRotationInDegreesForOrientationTag(int i) {
        return i != 3 ? i != 6 ? i != 8 ? 0 : -90 : 90 : 180;
    }

    Compression() {
    }

    File resize(String str, int i, int i2, int i3) throws IOException {
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        int width = decodeFile.getWidth();
        int height = decodeFile.getHeight();
        int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
        Matrix matrix = new Matrix();
        matrix.postRotate((float) getRotationInDegreesForOrientationTag(attributeInt));
        float f = ((float) width) / ((float) height);
        float f2 = (float) i;
        float f3 = (float) i2;
        if (f2 / f3 > 1.0f) {
            i = (int) (f3 * f);
        } else {
            i2 = (int) (f2 / f);
        }
        int i4 = i;
        int i5 = i2;
        Bitmap createBitmap = Bitmap.createBitmap(Bitmap.createScaledBitmap(decodeFile, i4, i5, true), 0, 0, i4, i5, matrix, true);
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!externalStoragePublicDirectory.exists()) {
            Log.d("image-crop-picker", "Pictures Directory is not existing. Will create this directory.");
            externalStoragePublicDirectory.mkdirs();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UUID.randomUUID());
        stringBuilder.append(".jpg");
        File file = new File(externalStoragePublicDirectory, stringBuilder.toString());
        OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        createBitmap.compress(CompressFormat.JPEG, i3, bufferedOutputStream);
        bufferedOutputStream.close();
        decodeFile.recycle();
        createBitmap.recycle();
        return file;
    }

    File compressImage(ReadableMap readableMap, String str, Options options) throws IOException {
        String str2 = "compressImageMaxWidth";
        Double d = null;
        Integer valueOf = readableMap.hasKey(str2) ? Integer.valueOf(readableMap.getInt(str2)) : null;
        String str3 = "compressImageMaxHeight";
        Integer valueOf2 = readableMap.hasKey(str3) ? Integer.valueOf(readableMap.getInt(str3)) : null;
        String str4 = "compressImageQuality";
        if (readableMap.hasKey(str4)) {
            d = Double.valueOf(readableMap.getDouble(str4));
        }
        Object obj = null;
        Object obj2 = (d == null || d.doubleValue() == 1.0d) ? 1 : null;
        Object obj3 = (valueOf == null || valueOf.intValue() >= options.outWidth) ? 1 : null;
        Object obj4 = (valueOf2 == null || valueOf2.intValue() >= options.outHeight) ? 1 : null;
        List asList = Arrays.asList(new String[]{"image/jpeg", "image/jpg", "image/png", "image/gif", "image/tiff"});
        if (options.outMimeType != null && asList.contains(options.outMimeType.toLowerCase())) {
            obj = 1;
        }
        str4 = "image-crop-picker";
        if (obj2 == null || obj3 == null || obj4 == null || obj == null) {
            Integer valueOf3;
            Log.d(str4, "Image compression activated");
            int doubleValue = d != null ? (int) (d.doubleValue() * 100.0d) : 100;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Compressing image with quality ");
            stringBuilder.append(doubleValue);
            Log.d(str4, stringBuilder.toString());
            if (valueOf == null) {
                valueOf = Integer.valueOf(options.outWidth);
            } else {
                valueOf = Integer.valueOf(Math.min(valueOf.intValue(), options.outWidth));
            }
            if (valueOf2 == null) {
                valueOf3 = Integer.valueOf(options.outHeight);
            } else {
                valueOf3 = Integer.valueOf(Math.min(valueOf2.intValue(), options.outHeight));
            }
            return resize(str, valueOf.intValue(), valueOf3.intValue(), doubleValue);
        }
        Log.d(str4, "Skipping image compression");
        return new File(str);
    }

    synchronized void compressVideo(Activity activity, ReadableMap readableMap, String str, String str2, Promise promise) {
        promise.resolve(str);
    }
}
