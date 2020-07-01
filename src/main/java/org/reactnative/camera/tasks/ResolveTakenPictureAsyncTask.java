package org.reactnative.camera.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import java.io.File;
import java.io.IOException;
import org.reactnative.camera.utils.RNFileUtils;

public class ResolveTakenPictureAsyncTask extends AsyncTask<Void, Void, WritableMap> {
    private static final String ERROR_TAG = "E_TAKING_PICTURE_FAILED";
    private Bitmap mBitmap;
    private File mCacheDirectory;
    private int mDeviceOrientation;
    private byte[] mImageData;
    private ReadableMap mOptions;
    private PictureSavedDelegate mPictureSavedDelegate;
    private Promise mPromise;

    /* renamed from: org.reactnative.camera.tasks.ResolveTakenPictureAsyncTask$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReadableType = new int[ReadableType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.facebook.react.bridge.ReadableType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$facebook$react$bridge$ReadableType = r0;
            r0 = $SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.facebook.react.bridge.ReadableType.Boolean;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.facebook.react.bridge.ReadableType.Map;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.reactnative.camera.tasks.ResolveTakenPictureAsyncTask.1.<clinit>():void");
        }
    }

    private int getImageRotation(int i) {
        return i != 3 ? i != 6 ? i != 8 ? 0 : 270 : 90 : 180;
    }

    public ResolveTakenPictureAsyncTask(byte[] bArr, Promise promise, ReadableMap readableMap, File file, int i, PictureSavedDelegate pictureSavedDelegate) {
        this.mPromise = promise;
        this.mOptions = readableMap;
        this.mImageData = bArr;
        this.mCacheDirectory = file;
        this.mDeviceOrientation = i;
        this.mPictureSavedDelegate = pictureSavedDelegate;
    }

    private int getQuality() {
        return (int) (this.mOptions.getDouble("quality") * 100.0d);
    }

    private void loadBitmap() throws IOException {
        if (this.mBitmap == null) {
            byte[] bArr = this.mImageData;
            this.mBitmap = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        if (this.mBitmap == null) {
            throw new IOException("Failed to decode Image Bitmap");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f4 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ff A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0106 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x011f A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01bc A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x012a A:{SYNTHETIC, Splitter: B:67:0x012a} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e2 A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00eb A:{SKIP, Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f4 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ff A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0106 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x011f A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x012a A:{SYNTHETIC, Splitter: B:67:0x012a} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01bc A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0072 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ba A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e2 A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00eb A:{SKIP, Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f4 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ff A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0106 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x011f A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01bc A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x012a A:{SYNTHETIC, Splitter: B:67:0x012a} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0072 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00a6 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ba A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e2 A:{SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00eb A:{SKIP, Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f4 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ff A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0106 A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x011f A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x012a A:{SYNTHETIC, Splitter: B:67:0x012a} */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01bc A:{Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }} */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0276 A:{SYNTHETIC, Splitter: B:132:0x0276} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0266 A:{SYNTHETIC, Splitter: B:123:0x0266} */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0254 A:{SYNTHETIC, Splitter: B:116:0x0254} */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0276 A:{SYNTHETIC, Splitter: B:132:0x0276} */
    protected com.facebook.react.bridge.WritableMap doInBackground(java.lang.Void... r17) {
        /*
        r16 = this;
        r1 = r16;
        r0 = "mirrorImage";
        r2 = "fixOrientation";
        r3 = "E_TAKING_PICTURE_FAILED";
        r4 = "exif";
        r5 = "writeExif";
        r6 = "width";
        r7 = com.facebook.react.bridge.Arguments.createMap();
        r8 = r1.mDeviceOrientation;
        r9 = "deviceOrientation";
        r7.putInt(r9, r8);
        r8 = r1.mOptions;
        r9 = "orientation";
        r8 = r8.hasKey(r9);
        if (r8 == 0) goto L_0x002a;
    L_0x0023:
        r8 = r1.mOptions;
        r8 = r8.getInt(r9);
        goto L_0x002c;
    L_0x002a:
        r8 = r1.mDeviceOrientation;
    L_0x002c:
        r9 = "pictureOrientation";
        r7.putInt(r9, r8);
        r9 = new java.io.ByteArrayInputStream;	 Catch:{ NotFoundException -> 0x0258, IOException -> 0x0246, all -> 0x0242 }
        r10 = r1.mImageData;	 Catch:{ NotFoundException -> 0x0258, IOException -> 0x0246, all -> 0x0242 }
        r9.<init>(r10);	 Catch:{ NotFoundException -> 0x0258, IOException -> 0x0246, all -> 0x0242 }
        r10 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r10 = r10.hasKey(r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r11 = "Orientation";
        r12 = 0;
        r13 = 1;
        if (r10 == 0) goto L_0x0068;
    L_0x0044:
        r10 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = r10.getBoolean(r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r2 == 0) goto L_0x0068;
    L_0x004c:
        r2 = new androidx.exifinterface.media.ExifInterface;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.<init>(r9);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r10 = r2.getAttributeInt(r11, r12);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r10 == 0) goto L_0x0069;
    L_0x0057:
        r16.loadBitmap();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r14 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r10 = r1.getImageRotation(r10);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r10 = r1.rotateBitmap(r14, r10);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r1.mBitmap = r10;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r10 = 1;
        goto L_0x006a;
    L_0x0068:
        r2 = 0;
    L_0x0069:
        r10 = 0;
    L_0x006a:
        r14 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r14 = r14.hasKey(r6);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r14 == 0) goto L_0x0083;
    L_0x0072:
        r16.loadBitmap();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r14 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r15 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r15 = r15.getInt(r6);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r14 = r1.resizeBitmap(r14, r15);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r1.mBitmap = r14;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x0083:
        r14 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r14 = r14.hasKey(r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r14 == 0) goto L_0x009e;
    L_0x008b:
        r14 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r14.getBoolean(r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r0 == 0) goto L_0x009e;
    L_0x0093:
        r16.loadBitmap();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r1.flipHorizontally(r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r1.mBitmap = r0;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x009e:
        r0 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.hasKey(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r0 == 0) goto L_0x00b0;
    L_0x00a6:
        r0 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.getBoolean(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r0 == 0) goto L_0x00b0;
    L_0x00ae:
        r0 = 1;
        goto L_0x00b1;
    L_0x00b0:
        r0 = 0;
    L_0x00b1:
        r14 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r14 = r14.hasKey(r5);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r15 = 2;
        if (r14 == 0) goto L_0x00dc;
    L_0x00ba:
        r14 = org.reactnative.camera.tasks.ResolveTakenPictureAsyncTask.AnonymousClass1.$SwitchMap$com$facebook$react$bridge$ReadableType;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r8 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r8 = r8.getType(r5);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r8 = r8.ordinal();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r8 = r14[r8];	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r8 == r13) goto L_0x00d5;
    L_0x00ca:
        if (r8 == r15) goto L_0x00cd;
    L_0x00cc:
        goto L_0x00dc;
    L_0x00cd:
        r8 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r8 = r8.getMap(r5);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r5 = 1;
        goto L_0x00de;
    L_0x00d5:
        r8 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r5 = r8.getBoolean(r5);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        goto L_0x00dd;
    L_0x00dc:
        r5 = 1;
    L_0x00dd:
        r8 = 0;
    L_0x00de:
        r14 = "height";
        if (r0 != 0) goto L_0x00e7;
    L_0x00e2:
        if (r5 == 0) goto L_0x00e5;
    L_0x00e4:
        goto L_0x00e7;
    L_0x00e5:
        r2 = 0;
        goto L_0x0122;
    L_0x00e7:
        r15 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r15 != 0) goto L_0x00f2;
    L_0x00eb:
        if (r8 != 0) goto L_0x00f2;
    L_0x00ed:
        if (r0 == 0) goto L_0x00f0;
    L_0x00ef:
        goto L_0x00f2;
    L_0x00f0:
        r2 = 0;
        goto L_0x0102;
    L_0x00f2:
        if (r2 != 0) goto L_0x00f9;
    L_0x00f4:
        r2 = new androidx.exifinterface.media.ExifInterface;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.<init>(r9);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x00f9:
        r2 = org.reactnative.camera.RNCameraViewHelper.getExifData(r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r8 == 0) goto L_0x0102;
    L_0x00ff:
        r2.merge(r8);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x0102:
        r15 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r15 == 0) goto L_0x011d;
    L_0x0106:
        r15 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r15 = r15.getWidth();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.putInt(r6, r15);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r15 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r15 = r15.getHeight();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.putInt(r14, r15);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r10 == 0) goto L_0x011d;
    L_0x011a:
        r2.putInt(r11, r13);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x011d:
        if (r0 == 0) goto L_0x0122;
    L_0x011f:
        r7.putMap(r4, r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x0122:
        r0 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r4 = "doNotSave";
        r10 = "base64";
        if (r0 != 0) goto L_0x01bc;
    L_0x012a:
        r0 = new android.graphics.BitmapFactory$Options;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0.<init>();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0.inJustDecodeBounds = r13;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = r1.mImageData;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r11 = r1.mImageData;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r11 = r11.length;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        android.graphics.BitmapFactory.decodeByteArray(r2, r12, r11, r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = r0.outWidth;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r7.putInt(r6, r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.outHeight;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r7.putInt(r14, r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.hasKey(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r0 == 0) goto L_0x0153;
    L_0x014b:
        r0 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.getBoolean(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r0 != 0) goto L_0x01a1;
    L_0x0153:
        r0 = new java.io.File;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = r16.getImagePath();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0.<init>(r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0.createNewFile();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = new java.io.FileOutputStream;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.<init>(r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r4 = r1.mImageData;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.write(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.flush();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.close();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r5 == 0) goto L_0x0183;
    L_0x0171:
        if (r8 == 0) goto L_0x0183;
    L_0x0173:
        r2 = new androidx.exifinterface.media.ExifInterface;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r4 = r0.getAbsolutePath();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.<init>(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        org.reactnative.camera.RNCameraViewHelper.setExifData(r2, r8);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.saveAttributes();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        goto L_0x0194;
    L_0x0183:
        if (r5 != 0) goto L_0x0194;
    L_0x0185:
        r2 = new androidx.exifinterface.media.ExifInterface;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r4 = r0.getAbsolutePath();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.<init>(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        org.reactnative.camera.RNCameraViewHelper.clearExifData(r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.saveAttributes();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x0194:
        r0 = android.net.Uri.fromFile(r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.toString();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = "uri";
        r7.putString(r2, r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x01a1:
        r0 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.hasKey(r10);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r0 == 0) goto L_0x022f;
    L_0x01a9:
        r0 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.getBoolean(r10);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r0 == 0) goto L_0x022f;
    L_0x01b1:
        r0 = r1.mImageData;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = 2;
        r0 = android.util.Base64.encodeToString(r0, r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r7.putString(r10, r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        goto L_0x022f;
    L_0x01bc:
        r0 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.getWidth();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r7.putInt(r6, r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = r0.getHeight();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r7.putInt(r14, r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0 = new java.io.ByteArrayOutputStream;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r0.<init>();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r6 = r1.mBitmap;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r8 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r11 = r16.getQuality();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r6.compress(r8, r11, r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r6 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r6 = r6.hasKey(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r6 == 0) goto L_0x01ee;
    L_0x01e6:
        r6 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r4 = r6.getBoolean(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r4 != 0) goto L_0x0213;
    L_0x01ee:
        r4 = r1.writeStreamToFile(r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r5 == 0) goto L_0x0201;
    L_0x01f4:
        if (r2 == 0) goto L_0x0201;
    L_0x01f6:
        r5 = new androidx.exifinterface.media.ExifInterface;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r5.<init>(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        org.reactnative.camera.RNCameraViewHelper.setExifData(r5, r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r5.saveAttributes();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x0201:
        r2 = new java.io.File;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2.<init>(r4);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = android.net.Uri.fromFile(r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = r2.toString();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r4 = "uri";
        r7.putString(r4, r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x0213:
        r2 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = r2.hasKey(r10);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r2 == 0) goto L_0x022f;
    L_0x021b:
        r2 = r1.mOptions;	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = r2.getBoolean(r10);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        if (r2 == 0) goto L_0x022f;
    L_0x0223:
        r0 = r0.toByteArray();	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r2 = 2;
        r0 = android.util.Base64.encodeToString(r0, r2);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
        r7.putString(r10, r0);	 Catch:{ NotFoundException -> 0x023f, IOException -> 0x023c, all -> 0x0239 }
    L_0x022f:
        r9.close();	 Catch:{ IOException -> 0x0233 }
        goto L_0x0238;
    L_0x0233:
        r0 = move-exception;
        r2 = r0;
        r2.printStackTrace();
    L_0x0238:
        return r7;
    L_0x0239:
        r0 = move-exception;
        r2 = r0;
        goto L_0x0274;
    L_0x023c:
        r0 = move-exception;
        r8 = r9;
        goto L_0x0248;
    L_0x023f:
        r0 = move-exception;
        r8 = r9;
        goto L_0x025a;
    L_0x0242:
        r0 = move-exception;
        r2 = r0;
        r9 = 0;
        goto L_0x0274;
    L_0x0246:
        r0 = move-exception;
        r8 = 0;
    L_0x0248:
        r2 = r1.mPromise;	 Catch:{ all -> 0x0271 }
        r4 = "An unknown I/O exception has occurred.";
        r2.reject(r3, r4, r0);	 Catch:{ all -> 0x0271 }
        r0.printStackTrace();	 Catch:{ all -> 0x0271 }
        if (r8 == 0) goto L_0x026f;
    L_0x0254:
        r8.close();	 Catch:{ IOException -> 0x026a }
        goto L_0x026f;
    L_0x0258:
        r0 = move-exception;
        r8 = 0;
    L_0x025a:
        r2 = r1.mPromise;	 Catch:{ all -> 0x0271 }
        r4 = "Documents directory of the app could not be found.";
        r2.reject(r3, r4, r0);	 Catch:{ all -> 0x0271 }
        r0.printStackTrace();	 Catch:{ all -> 0x0271 }
        if (r8 == 0) goto L_0x026f;
    L_0x0266:
        r8.close();	 Catch:{ IOException -> 0x026a }
        goto L_0x026f;
    L_0x026a:
        r0 = move-exception;
        r2 = r0;
        r2.printStackTrace();
    L_0x026f:
        r2 = 0;
        return r2;
    L_0x0271:
        r0 = move-exception;
        r2 = r0;
        r9 = r8;
    L_0x0274:
        if (r9 == 0) goto L_0x027f;
    L_0x0276:
        r9.close();	 Catch:{ IOException -> 0x027a }
        goto L_0x027f;
    L_0x027a:
        r0 = move-exception;
        r3 = r0;
        r3.printStackTrace();
    L_0x027f:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.reactnative.camera.tasks.ResolveTakenPictureAsyncTask.doInBackground(java.lang.Void[]):com.facebook.react.bridge.WritableMap");
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int i) {
        return Bitmap.createScaledBitmap(bitmap, i, (int) (((float) bitmap.getHeight()) * (((float) i) / ((float) bitmap.getWidth()))), true);
    }

    private Bitmap flipHorizontally(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private String getImagePath() throws IOException {
        ReadableMap readableMap = this.mOptions;
        String str = RNFetchBlobConst.RNFB_RESPONSE_PATH;
        if (readableMap.hasKey(str)) {
            return this.mOptions.getString(str);
        }
        return RNFileUtils.getOutputFilePath(this.mCacheDirectory, ".jpg");
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x002b A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0026 A:{SYNTHETIC, Splitter: B:21:0x0026} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x002b A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0026 A:{SYNTHETIC, Splitter: B:21:0x0026} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x002b A:{RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x001b A:{ExcHandler: all (th java.lang.Throwable), Splitter: B:1:0x0001} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0031 A:{SYNTHETIC, Splitter: B:29:0x0031} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:12:0x0018, code:
            r4 = e;
     */
    /* JADX WARNING: Missing block: B:13:0x0019, code:
            r2 = null;
     */
    /* JADX WARNING: Missing block: B:14:0x001b, code:
            r4 = th;
     */
    /* JADX WARNING: Missing block: B:22:?, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:30:?, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:31:0x0035, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:32:0x0036, code:
            r0.printStackTrace();
     */
    private java.lang.String writeStreamToFile(java.io.ByteArrayOutputStream r4) throws java.io.IOException {
        /*
        r3 = this;
        r0 = 0;
        r1 = r3.getImagePath();	 Catch:{ IOException -> 0x001d, all -> 0x001b }
        r2 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0018, all -> 0x001b }
        r2.<init>(r1);	 Catch:{ IOException -> 0x0018, all -> 0x001b }
        r4.writeTo(r2);	 Catch:{ IOException -> 0x0016 }
        r2.close();	 Catch:{ IOException -> 0x0011 }
        goto L_0x0029;
    L_0x0011:
        r4 = move-exception;
        r4.printStackTrace();
        goto L_0x0029;
    L_0x0016:
        r4 = move-exception;
        goto L_0x0020;
    L_0x0018:
        r4 = move-exception;
        r2 = r0;
        goto L_0x0020;
    L_0x001b:
        r4 = move-exception;
        goto L_0x002f;
    L_0x001d:
        r4 = move-exception;
        r1 = r0;
        r2 = r1;
    L_0x0020:
        r0 = r4;
        r0.printStackTrace();	 Catch:{ all -> 0x002d }
        if (r2 == 0) goto L_0x0029;
    L_0x0026:
        r2.close();	 Catch:{ IOException -> 0x0011 }
    L_0x0029:
        if (r0 != 0) goto L_0x002c;
    L_0x002b:
        return r1;
    L_0x002c:
        throw r0;
    L_0x002d:
        r4 = move-exception;
        r0 = r2;
    L_0x002f:
        if (r0 == 0) goto L_0x0039;
    L_0x0031:
        r0.close();	 Catch:{ IOException -> 0x0035 }
        goto L_0x0039;
    L_0x0035:
        r0 = move-exception;
        r0.printStackTrace();
    L_0x0039:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.reactnative.camera.tasks.ResolveTakenPictureAsyncTask.writeStreamToFile(java.io.ByteArrayOutputStream):java.lang.String");
    }

    protected void onPostExecute(WritableMap writableMap) {
        super.onPostExecute(writableMap);
        if (writableMap != null) {
            String str = "fastMode";
            if (this.mOptions.hasKey(str) && this.mOptions.getBoolean(str)) {
                WritableMap createMap = Arguments.createMap();
                String str2 = "id";
                createMap.putInt(str2, this.mOptions.getInt(str2));
                createMap.putMap("data", writableMap);
                this.mPictureSavedDelegate.onPictureSaved(createMap);
                return;
            }
            this.mPromise.resolve(writableMap);
        }
    }
}
