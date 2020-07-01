package com.facebook.react.modules.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.common.logging.FLog;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.module.annotations.ReactModule;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(name = "ImageEditingManager")
public class ImageEditingManager extends ReactContextBaseJavaModule {
    private static final int COMPRESS_QUALITY = 90;
    @SuppressLint({"InlinedApi"})
    private static final String[] EXIF_ATTRIBUTES = new String[]{ExifInterface.TAG_F_NUMBER, ExifInterface.TAG_DATETIME, ExifInterface.TAG_DATETIME_DIGITIZED, ExifInterface.TAG_EXPOSURE_TIME, ExifInterface.TAG_FLASH, ExifInterface.TAG_FOCAL_LENGTH, ExifInterface.TAG_GPS_ALTITUDE, ExifInterface.TAG_GPS_ALTITUDE_REF, ExifInterface.TAG_GPS_DATESTAMP, ExifInterface.TAG_GPS_LATITUDE, ExifInterface.TAG_GPS_LATITUDE_REF, ExifInterface.TAG_GPS_LONGITUDE, ExifInterface.TAG_GPS_LONGITUDE_REF, ExifInterface.TAG_GPS_PROCESSING_METHOD, ExifInterface.TAG_GPS_TIMESTAMP, ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.TAG_ISO_SPEED_RATINGS, ExifInterface.TAG_MAKE, ExifInterface.TAG_MODEL, ExifInterface.TAG_ORIENTATION, ExifInterface.TAG_SUBSEC_TIME, ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, ExifInterface.TAG_WHITE_BALANCE};
    private static final List<String> LOCAL_URI_PREFIXES = Arrays.asList(new String[]{"file://", RNFetchBlobConst.FILE_PREFIX_CONTENT});
    protected static final String NAME = "ImageEditingManager";
    private static final String TEMP_FILE_PREFIX = "ReactNative_cropped_image_";

    private static class CleanTask extends GuardedAsyncTask<Void, Void> {
        private final Context mContext;

        private CleanTask(ReactContext reactContext) {
            super(reactContext);
            this.mContext = reactContext;
        }

        protected void doInBackgroundGuarded(Void... voidArr) {
            cleanDirectory(this.mContext.getCacheDir());
            File externalCacheDir = this.mContext.getExternalCacheDir();
            if (externalCacheDir != null) {
                cleanDirectory(externalCacheDir);
            }
        }

        private void cleanDirectory(File file) {
            File[] listFiles = file.listFiles(new FilenameFilter() {
                public boolean accept(File file, String str) {
                    return str.startsWith(ImageEditingManager.TEMP_FILE_PREFIX);
                }
            });
            if (listFiles != null) {
                for (File delete : listFiles) {
                    delete.delete();
                }
            }
        }
    }

    private static class CropTask extends GuardedAsyncTask<Void, Void> {
        final Context mContext;
        final Callback mError;
        final int mHeight;
        final Callback mSuccess;
        int mTargetHeight;
        int mTargetWidth;
        final String mUri;
        final int mWidth;
        final int mX;
        final int mY;

        private CropTask(ReactContext reactContext, String str, int i, int i2, int i3, int i4, Callback callback, Callback callback2) {
            super(reactContext);
            this.mTargetWidth = 0;
            this.mTargetHeight = 0;
            if (i < 0 || i2 < 0 || i3 <= 0 || i4 <= 0) {
                throw new JSApplicationIllegalArgumentException(String.format("Invalid crop rectangle: [%d, %d, %d, %d]", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)}));
            }
            this.mContext = reactContext;
            this.mUri = str;
            this.mX = i;
            this.mY = i2;
            this.mWidth = i3;
            this.mHeight = i4;
            this.mSuccess = callback;
            this.mError = callback2;
        }

        public void setTargetSize(int i, int i2) {
            if (i <= 0 || i2 <= 0) {
                throw new JSApplicationIllegalArgumentException(String.format("Invalid target size: [%d, %d]", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
            }
            this.mTargetWidth = i;
            this.mTargetHeight = i2;
        }

        private InputStream openBitmapInputStream() throws IOException {
            InputStream openInputStream;
            if (ImageEditingManager.isLocalUri(this.mUri)) {
                openInputStream = this.mContext.getContentResolver().openInputStream(Uri.parse(this.mUri));
            } else {
                openInputStream = new URL(this.mUri).openConnection().getInputStream();
            }
            if (openInputStream != null) {
                return openInputStream;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot open bitmap: ");
            stringBuilder.append(this.mUri);
            throw new IOException(stringBuilder.toString());
        }

        protected void doInBackgroundGuarded(Void... voidArr) {
            try {
                Bitmap cropAndResize;
                Options options = new Options();
                Object obj = (this.mTargetWidth <= 0 || this.mTargetHeight <= 0) ? null : 1;
                if (obj != null) {
                    cropAndResize = cropAndResize(this.mTargetWidth, this.mTargetHeight, options);
                } else {
                    cropAndResize = crop(options);
                }
                String str = options.outMimeType;
                if (str == null || str.isEmpty()) {
                    throw new IOException("Could not determine MIME type");
                }
                File access$300 = ImageEditingManager.createTempFile(this.mContext, str);
                ImageEditingManager.writeCompressedBitmapToFile(cropAndResize, str, access$300);
                if (str.equals("image/jpeg")) {
                    ImageEditingManager.copyExif(this.mContext, Uri.parse(this.mUri), access$300);
                }
                this.mSuccess.invoke(Uri.fromFile(access$300).toString());
            } catch (Exception e) {
                this.mError.invoke(e.getMessage());
            }
        }

        private Bitmap crop(Options options) throws IOException {
            InputStream openBitmapInputStream = openBitmapInputStream();
            BitmapRegionDecoder newInstance = BitmapRegionDecoder.newInstance(openBitmapInputStream, false);
            try {
                Bitmap decodeRegion = newInstance.decodeRegion(new Rect(this.mX, this.mY, this.mX + this.mWidth, this.mY + this.mHeight), options);
                return decodeRegion;
            } finally {
                if (openBitmapInputStream != null) {
                    openBitmapInputStream.close();
                }
                newInstance.recycle();
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:26:0x00d8 A:{REMOVE} */
        private android.graphics.Bitmap cropAndResize(int r20, int r21, android.graphics.BitmapFactory.Options r22) throws java.io.IOException {
            /*
            r19 = this;
            r1 = r19;
            r0 = r20;
            r2 = r21;
            r3 = r22;
            com.facebook.infer.annotation.Assertions.assertNotNull(r22);
            r4 = new android.graphics.BitmapFactory$Options;
            r4.<init>();
            r5 = 1;
            r4.inJustDecodeBounds = r5;
            r5 = r19.openBitmapInputStream();
            r6 = 0;
            android.graphics.BitmapFactory.decodeStream(r5, r6, r4);	 Catch:{ all -> 0x00d1 }
            if (r5 == 0) goto L_0x0020;
        L_0x001d:
            r5.close();
        L_0x0020:
            r5 = r1.mWidth;
            r7 = (float) r5;
            r8 = r1.mHeight;
            r9 = (float) r8;
            r7 = r7 / r9;
            r9 = (float) r0;
            r10 = (float) r2;
            r11 = r9 / r10;
            r12 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
            r7 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1));
            if (r7 <= 0) goto L_0x0042;
        L_0x0031:
            r7 = (float) r8;
            r7 = r7 * r11;
            r9 = (float) r8;
            r11 = r1.mX;
            r11 = (float) r11;
            r5 = (float) r5;
            r5 = r5 - r7;
            r5 = r5 / r12;
            r11 = r11 + r5;
            r5 = r1.mY;
            r5 = (float) r5;
            r8 = (float) r8;
            r10 = r10 / r8;
            goto L_0x0055;
        L_0x0042:
            r7 = (float) r5;
            r10 = (float) r5;
            r10 = r10 / r11;
            r11 = r1.mX;
            r11 = (float) r11;
            r13 = r1.mY;
            r13 = (float) r13;
            r8 = (float) r8;
            r8 = r8 - r10;
            r8 = r8 / r12;
            r8 = r8 + r13;
            r5 = (float) r5;
            r5 = r9 / r5;
            r9 = r10;
            r10 = r5;
            r5 = r8;
        L_0x0055:
            r8 = r1.mWidth;
            r12 = r1.mHeight;
            r0 = com.facebook.react.modules.camera.ImageEditingManager.getDecodeSampleSize(r8, r12, r0, r2);
            r3.inSampleSize = r0;
            r0 = 0;
            r4.inJustDecodeBounds = r0;
            r2 = r19.openBitmapInputStream();
            r12 = android.graphics.BitmapFactory.decodeStream(r2, r6, r3);	 Catch:{ all -> 0x00ca }
            if (r12 == 0) goto L_0x00b1;
        L_0x006c:
            if (r2 == 0) goto L_0x0071;
        L_0x006e:
            r2.close();
        L_0x0071:
            r0 = r3.inSampleSize;
            r0 = (float) r0;
            r11 = r11 / r0;
            r13 = (double) r11;
            r13 = java.lang.Math.floor(r13);
            r13 = (int) r13;
            r0 = r3.inSampleSize;
            r0 = (float) r0;
            r5 = r5 / r0;
            r4 = (double) r5;
            r4 = java.lang.Math.floor(r4);
            r14 = (int) r4;
            r0 = r3.inSampleSize;
            r0 = (float) r0;
            r7 = r7 / r0;
            r4 = (double) r7;
            r4 = java.lang.Math.floor(r4);
            r15 = (int) r4;
            r0 = r3.inSampleSize;
            r0 = (float) r0;
            r9 = r9 / r0;
            r4 = (double) r9;
            r4 = java.lang.Math.floor(r4);
            r0 = (int) r4;
            r2 = r3.inSampleSize;
            r2 = (float) r2;
            r10 = r10 * r2;
            r2 = new android.graphics.Matrix;
            r2.<init>();
            r2.setScale(r10, r10);
            r18 = 1;
            r16 = r0;
            r17 = r2;
            r0 = android.graphics.Bitmap.createBitmap(r12, r13, r14, r15, r16, r17, r18);
            return r0;
        L_0x00b1:
            r0 = new java.io.IOException;	 Catch:{ all -> 0x00ca }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ca }
            r3.<init>();	 Catch:{ all -> 0x00ca }
            r4 = "Cannot decode bitmap: ";
            r3.append(r4);	 Catch:{ all -> 0x00ca }
            r4 = r1.mUri;	 Catch:{ all -> 0x00ca }
            r3.append(r4);	 Catch:{ all -> 0x00ca }
            r3 = r3.toString();	 Catch:{ all -> 0x00ca }
            r0.<init>(r3);	 Catch:{ all -> 0x00ca }
            throw r0;	 Catch:{ all -> 0x00ca }
        L_0x00ca:
            r0 = move-exception;
            if (r2 == 0) goto L_0x00d0;
        L_0x00cd:
            r2.close();
        L_0x00d0:
            throw r0;
        L_0x00d1:
            r0 = move-exception;
            r2 = r0;
            if (r5 == 0) goto L_0x00d8;
        L_0x00d5:
            r5.close();
        L_0x00d8:
            throw r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.camera.ImageEditingManager.CropTask.cropAndResize(int, int, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
        }
    }

    public String getName() {
        return NAME;
    }

    public ImageEditingManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        new CleanTask(access$100()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public Map<String, Object> getConstants() {
        return Collections.emptyMap();
    }

    public void onCatalystInstanceDestroy() {
        new CleanTask(access$100()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @ReactMethod
    public void cropImage(String str, ReadableMap readableMap, Callback callback, Callback callback2) {
        ReadableMap readableMap2 = readableMap;
        String str2 = "offset";
        ReadableMap readableMap3 = null;
        ReadableMap map = readableMap2.hasKey(str2) ? readableMap2.getMap(str2) : null;
        String str3 = "size";
        if (readableMap2.hasKey(str3)) {
            readableMap3 = readableMap2.getMap(str3);
        }
        if (!(map == null || readableMap3 == null)) {
            str3 = "x";
            if (map.hasKey(str3)) {
                String str4 = "y";
                if (map.hasKey(str4)) {
                    String str5 = "width";
                    if (readableMap3.hasKey(str5)) {
                        String str6 = "height";
                        if (readableMap3.hasKey(str6)) {
                            if (str == null || str.isEmpty()) {
                                throw new JSApplicationIllegalArgumentException("Please specify a URI");
                            }
                            CropTask cropTask = new CropTask(access$100(), str, (int) map.getDouble(str3), (int) map.getDouble(str4), (int) readableMap3.getDouble(str5), (int) readableMap3.getDouble(str6), callback, callback2);
                            str2 = "displaySize";
                            if (readableMap2.hasKey(str2)) {
                                readableMap2 = readableMap2.getMap(str2);
                                cropTask.setTargetSize((int) readableMap2.getDouble(str5), (int) readableMap2.getDouble(str6));
                            }
                            cropTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                            return;
                        }
                    }
                }
            }
        }
        throw new JSApplicationIllegalArgumentException("Please specify offset and size");
    }

    private static void copyExif(Context context, Uri uri, File file) throws IOException {
        File fileFromUri = getFileFromUri(context, uri);
        if (fileFromUri == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't get real path for uri: ");
            stringBuilder.append(uri);
            FLog.w(ReactConstants.TAG, stringBuilder.toString());
            return;
        }
        android.media.ExifInterface exifInterface = new android.media.ExifInterface(fileFromUri.getAbsolutePath());
        android.media.ExifInterface exifInterface2 = new android.media.ExifInterface(file.getAbsolutePath());
        for (String str : EXIF_ATTRIBUTES) {
            String attribute = exifInterface.getAttribute(str);
            if (attribute != null) {
                exifInterface2.setAttribute(str, attribute);
            }
        }
        exifInterface2.saveAttributes();
    }

    @Nullable
    private static File getFileFromUri(Context context, Uri uri) {
        if (uri.getScheme().equals(UriUtil.LOCAL_FILE_SCHEME)) {
            return new File(uri.getPath());
        }
        File equals = uri.getScheme().equals("content");
        if (equals != null) {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        Object string = query.getString(0);
                        if (!TextUtils.isEmpty(string)) {
                            equals = new File(string);
                            return equals;
                        }
                    }
                    query.close();
                } finally {
                    query.close();
                }
            }
        }
        return null;
    }

    private static boolean isLocalUri(String str) {
        for (String startsWith : LOCAL_URI_PREFIXES) {
            if (str.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    private static String getFileExtensionForType(@Nullable String str) {
        if ("image/png".equals(str)) {
            return ".png";
        }
        return "image/webp".equals(str) ? ".webp" : ".jpg";
    }

    private static CompressFormat getCompressFormatForType(String str) {
        if ("image/png".equals(str)) {
            return CompressFormat.PNG;
        }
        if ("image/webp".equals(str)) {
            return CompressFormat.WEBP;
        }
        return CompressFormat.JPEG;
    }

    private static void writeCompressedBitmapToFile(Bitmap bitmap, String str, File file) throws IOException {
        OutputStream fileOutputStream = new FileOutputStream(file);
        try {
            bitmap.compress(getCompressFormatForType(str), 90, fileOutputStream);
        } finally {
            fileOutputStream.close();
        }
    }

    private static File createTempFile(Context context, @Nullable String str) throws IOException {
        File externalCacheDir = context.getExternalCacheDir();
        File cacheDir = context.getCacheDir();
        if (externalCacheDir == null && cacheDir == null) {
            throw new IOException("No cache directory available");
        }
        if (externalCacheDir == null || (cacheDir != null && externalCacheDir.getFreeSpace() <= cacheDir.getFreeSpace())) {
            externalCacheDir = cacheDir;
        }
        return File.createTempFile(TEMP_FILE_PREFIX, getFileExtensionForType(str), externalCacheDir);
    }

    private static int getDecodeSampleSize(int i, int i2, int i3, int i4) {
        int i5 = 1;
        if (i2 > i3 || i > i4) {
            i2 /= 2;
            i /= 2;
            while (i / i5 >= i3 && i2 / i5 >= i4) {
                i5 *= 2;
            }
        }
        return i5;
    }
}
