package org.reactnative.barcodedetector;

import android.content.Context;
import android.util.Log;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions.Builder;

public class RNBarcodeDetector {
    public static int ALL_FORMATS = 0;
    public static int ALTERNATE_MODE = 1;
    public static int INVERTED_MODE = 2;
    public static int NORMAL_MODE;
    private FirebaseVisionBarcodeDetector mBarcodeDetector = null;
    private int mBarcodeType = 0;
    private Builder mBuilder = new Builder().setBarcodeFormats(this.mBarcodeType, new int[0]);

    public boolean isOperational() {
        return true;
    }

    public RNBarcodeDetector(Context context) {
    }

    public FirebaseVisionBarcodeDetector getDetector() {
        if (this.mBarcodeDetector == null) {
            createBarcodeDetector();
        }
        return this.mBarcodeDetector;
    }

    public void setBarcodeType(int i) {
        if (i != this.mBarcodeType) {
            release();
            this.mBuilder.setBarcodeFormats(i, new int[0]);
            this.mBarcodeType = i;
        }
    }

    public void release() {
        FirebaseVisionBarcodeDetector firebaseVisionBarcodeDetector = this.mBarcodeDetector;
        if (firebaseVisionBarcodeDetector != null) {
            try {
                firebaseVisionBarcodeDetector.close();
            } catch (Exception unused) {
                Log.e("RNCamera", "Attempt to close BarcodeDetector failed");
            }
            this.mBarcodeDetector = null;
        }
    }

    private void createBarcodeDetector() {
        this.mBarcodeDetector = FirebaseVision.getInstance().getVisionBarcodeDetector(this.mBuilder.build());
    }
}
