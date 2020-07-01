package fr.bamlab.rnimageresizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class ImageResizer {
    private static final String IMAGE_JPEG = "image/jpeg";
    private static final String IMAGE_PNG = "image/png";
    private static final String SCHEME_CONTENT = "content";
    private static final String SCHEME_DATA = "data";
    private static final String SCHEME_FILE = "file";

    private static Bitmap resizeImage(Bitmap bitmap, int i, int i2) {
        Bitmap bitmap2 = null;
        if (bitmap == null) {
            return null;
        }
        if (i2 > 0 && i > 0) {
            float width = (float) bitmap.getWidth();
            float height = (float) bitmap.getHeight();
            float min = Math.min(((float) i) / width, ((float) i2) / height);
            try {
                bitmap2 = Bitmap.createScaledBitmap(bitmap, (int) (width * min), (int) (height * min), true);
            } catch (OutOfMemoryError unused) {
                return bitmap2;
            }
        }
    }

    public static Bitmap rotateImage(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }

    private static File saveImage(Bitmap bitmap, File file, String str, CompressFormat compressFormat, int i) throws IOException {
        if (bitmap != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(".");
            stringBuilder.append(compressFormat.name());
            File file2 = new File(file, stringBuilder.toString());
            if (file2.createNewFile()) {
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(compressFormat, i, byteArrayOutputStream);
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(toByteArray);
                fileOutputStream.flush();
                fileOutputStream.close();
                return file2;
            }
            throw new IOException("The file already exists");
        }
        throw new IOException("The bitmap couldn't be resized");
    }

    /* JADX WARNING: Missing block: B:13:0x0043, code:
            if (r2 == null) goto L_0x0048;
     */
    /* JADX WARNING: Missing block: B:14:0x0045, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:15:0x0048, code:
            r10 = r1;
     */
    private static java.io.File getFileFromUri(android.content.Context r9, android.net.Uri r10) {
        /*
        r0 = "_data";
        r1 = new java.io.File;
        r2 = r10.getPath();
        r1.<init>(r2);
        r2 = r1.exists();
        if (r2 == 0) goto L_0x0012;
    L_0x0011:
        return r1;
    L_0x0012:
        r2 = 0;
        r5 = new java.lang.String[]{r0};	 Catch:{ Exception -> 0x003b, all -> 0x0039 }
        r3 = r9.getContentResolver();	 Catch:{ Exception -> 0x003b, all -> 0x0039 }
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r4 = r10;
        r2 = r3.query(r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x003b, all -> 0x0039 }
        r9 = r2.getColumnIndexOrThrow(r0);	 Catch:{ Exception -> 0x003b, all -> 0x0039 }
        r2.moveToFirst();	 Catch:{ Exception -> 0x003b, all -> 0x0039 }
        r9 = r2.getString(r9);	 Catch:{ Exception -> 0x003b, all -> 0x0039 }
        r10 = new java.io.File;	 Catch:{ Exception -> 0x003b, all -> 0x0039 }
        r10.<init>(r9);	 Catch:{ Exception -> 0x003b, all -> 0x0039 }
        if (r2 == 0) goto L_0x0049;
    L_0x0035:
        r2.close();
        goto L_0x0049;
    L_0x0039:
        r9 = move-exception;
        goto L_0x003d;
        goto L_0x0043;
    L_0x003d:
        if (r2 == 0) goto L_0x0042;
    L_0x003f:
        r2.close();
    L_0x0042:
        throw r9;
    L_0x0043:
        if (r2 == 0) goto L_0x0048;
    L_0x0045:
        r2.close();
    L_0x0048:
        r10 = r1;
    L_0x0049:
        return r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: fr.bamlab.rnimageresizer.ImageResizer.getFileFromUri(android.content.Context, android.net.Uri):java.io.File");
    }

    public static int getOrientation(Context context, Uri uri) {
        try {
            File fileFromUri = getFileFromUri(context, uri);
            if (fileFromUri.exists()) {
                return getOrientation(new ExifInterface(fileFromUri.getAbsolutePath()));
            }
        } catch (Exception unused) {
            return 0;
        }
    }

    public static int getOrientation(ExifInterface exifInterface) {
        int attributeInt = exifInterface.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
        if (attributeInt == 3) {
            return 180;
        }
        if (attributeInt != 6) {
            return attributeInt != 8 ? 0 : 270;
        } else {
            return 90;
        }
    }

    private static int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            i3 /= 2;
            i4 /= 2;
            while (i3 / i5 >= i2 && i4 / i5 >= i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    private static Bitmap loadBitmap(Context context, Uri uri, Options options) throws IOException {
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.equalsIgnoreCase("content")) {
            try {
                return BitmapFactory.decodeFile(uri.getPath(), options);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("Error decoding image file");
            }
        }
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        if (openInputStream == null) {
            return null;
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream, null, options);
        openInputStream.close();
        return decodeStream;
    }

    private static Bitmap loadBitmapFromFile(Context context, Uri uri, int i, int i2) throws IOException {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        loadBitmap(context, uri, options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        System.out.println(options.inSampleSize);
        return loadBitmap(context, uri, options);
    }

    private static Bitmap loadBitmapFromBase64(Uri uri) {
        String schemeSpecificPart = uri.getSchemeSpecificPart();
        int indexOf = schemeSpecificPart.indexOf(44);
        if (indexOf != -1) {
            String toLowerCase = schemeSpecificPart.substring(0, indexOf).replace('\\', '/').toLowerCase();
            boolean startsWith = toLowerCase.startsWith(IMAGE_JPEG);
            Object obj = (startsWith || !toLowerCase.startsWith(IMAGE_PNG)) ? null : 1;
            if (startsWith || obj != null) {
                byte[] decode = Base64.decode(schemeSpecificPart.substring(indexOf + 1), 0);
                return BitmapFactory.decodeByteArray(decode, 0, decode.length);
            }
        }
        return null;
    }

    public static File createResizedImage(Context context, Uri uri, int i, int i2, CompressFormat compressFormat, int i3, int i4, String str) throws IOException {
        String scheme = uri.getScheme();
        Bitmap loadBitmapFromFile = (scheme == null || scheme.equalsIgnoreCase("file") || scheme.equalsIgnoreCase("content")) ? loadBitmapFromFile(context, uri, i, i2) : scheme.equalsIgnoreCase("data") ? loadBitmapFromBase64(uri) : null;
        if (loadBitmapFromFile != null) {
            Bitmap resizeImage = resizeImage(loadBitmapFromFile, i, i2);
            if (loadBitmapFromFile != resizeImage) {
                loadBitmapFromFile.recycle();
            }
            Bitmap rotateImage = rotateImage(resizeImage, (float) (getOrientation(context, uri) + i4));
            if (resizeImage != rotateImage) {
                resizeImage.recycle();
            }
            File cacheDir = context.getCacheDir();
            if (str != null) {
                cacheDir = new File(str);
            }
            cacheDir = saveImage(rotateImage, cacheDir, Long.toString(new Date().getTime()), compressFormat, i3);
            rotateImage.recycle();
            return cacheDir;
        }
        throw new IOException("Unable to load source image from path");
    }
}
