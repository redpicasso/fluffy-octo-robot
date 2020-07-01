package org.reactnative.camera.tasks;

import android.os.AsyncTask;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class BarCodeScannerAsyncTask extends AsyncTask<Void, Void, Result> {
    private int mCameraViewHeight;
    private int mCameraViewWidth;
    private BarCodeScannerAsyncTaskDelegate mDelegate;
    private int mHeight;
    private byte[] mImageData;
    private boolean mLimitScanArea;
    private final MultiFormatReader mMultiFormatReader;
    private float mRatio;
    private float mScanAreaHeight;
    private float mScanAreaWidth;
    private float mScanAreaX;
    private float mScanAreaY;
    private int mWidth;

    public BarCodeScannerAsyncTask(BarCodeScannerAsyncTaskDelegate barCodeScannerAsyncTaskDelegate, MultiFormatReader multiFormatReader, byte[] bArr, int i, int i2, boolean z, float f, float f2, float f3, float f4, int i3, int i4, float f5) {
        this.mImageData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDelegate = barCodeScannerAsyncTaskDelegate;
        this.mMultiFormatReader = multiFormatReader;
        this.mLimitScanArea = z;
        this.mScanAreaX = f;
        this.mScanAreaY = f2;
        this.mScanAreaWidth = f3;
        this.mScanAreaHeight = f4;
        this.mCameraViewWidth = i3;
        this.mCameraViewHeight = i4;
        this.mRatio = f5;
    }

    protected Result doInBackground(Void... voidArr) {
        Result result = null;
        if (!(isCancelled() || this.mDelegate == null)) {
            int i = (int) (((float) this.mCameraViewHeight) / this.mRatio);
            int i2 = this.mCameraViewWidth;
            float f = (float) i;
            float f2 = (((float) ((i - i2) / 2)) + (this.mScanAreaY * ((float) i2))) / f;
            float f3 = this.mScanAreaX;
            int i3 = this.mWidth;
            int i4 = (int) (f3 * ((float) i3));
            int i5 = this.mHeight;
            int i6 = (int) (f2 * ((float) i5));
            int i7 = (int) (this.mScanAreaWidth * ((float) i3));
            i = (int) (((this.mScanAreaHeight * ((float) i2)) / f) * ((float) i5));
            try {
                result = this.mMultiFormatReader.decodeWithState(generateBitmapFromImageData(this.mImageData, i3, i5, false, i4, i6, i7, i));
            } catch (NotFoundException unused) {
                byte[] rotateImage = rotateImage(this.mImageData, this.mWidth, this.mHeight);
                int i8 = this.mHeight;
                try {
                    i = this.mMultiFormatReader.decodeWithState(generateBitmapFromImageData(rotateImage, i8, this.mWidth, false, (i8 - i) - i6, i4, i, i7));
                } catch (NotFoundException unused2) {
                    rotateImage = this.mImageData;
                    i8 = this.mWidth;
                    i3 = this.mHeight;
                    try {
                        i = this.mMultiFormatReader.decodeWithState(generateBitmapFromImageData(rotateImage, i8, i3, true, (i8 - i7) - i4, (i3 - i) - i6, i7, i));
                    } catch (NotFoundException unused3) {
                        rotateImage = rotateImage(this.mImageData, this.mWidth, this.mHeight);
                        i8 = this.mHeight;
                        i3 = this.mWidth;
                        try {
                            i = this.mMultiFormatReader.decodeWithState(generateBitmapFromImageData(rotateImage, i8, i3, true, i6, (i3 - i7) - i4, i, i7));
                        } catch (NotFoundException unused4) {
                            return result;
                        }
                    }
                }
                result = i;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private byte[] rotateImage(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i; i4++) {
                bArr2[(((i4 * i2) + i2) - i3) - 1] = bArr[(i3 * i) + i4];
            }
        }
        return bArr2;
    }

    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (result != null) {
            this.mDelegate.onBarCodeRead(result, this.mWidth, this.mHeight);
        }
        this.mDelegate.onBarCodeScanningTaskCompleted();
    }

    private BinaryBitmap generateBitmapFromImageData(byte[] bArr, int i, int i2, boolean z, int i3, int i4, int i5, int i6) {
        if (this.mLimitScanArea) {
            LuminanceSource planarYUVLuminanceSource = new PlanarYUVLuminanceSource(bArr, i, i2, i3, i4, i5, i6, false);
        } else {
            LuminanceSource planarYUVLuminanceSource2 = new PlanarYUVLuminanceSource(bArr, i, i2, 0, 0, i, i2, false);
        }
        if (z) {
            return new BinaryBitmap(new HybridBinarizer(r1.invert()));
        }
        return new BinaryBitmap(new HybridBinarizer(r1));
    }
}
