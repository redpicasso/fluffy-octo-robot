package org.reactnative.facedetector;

import android.content.Context;
import android.util.Log;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions.Builder;

public class RNFaceDetector {
    public static int ACCURATE_MODE = 2;
    public static int ALL_CLASSIFICATIONS = 2;
    public static int ALL_CONTOURS = 2;
    public static int ALL_LANDMARKS = 2;
    public static int FAST_MODE = 1;
    public static int NO_CLASSIFICATIONS = 1;
    public static int NO_CONTOURS = 1;
    public static int NO_LANDMARKS = 1;
    private Builder mBuilder = new Builder().setPerformanceMode(this.mMode).setLandmarkMode(this.mLandmarkType).setClassificationMode(this.mClassificationType).setMinFaceSize(this.mMinFaceSize);
    private int mClassificationType = NO_CLASSIFICATIONS;
    private FirebaseVisionFaceDetector mFaceDetector = null;
    private int mLandmarkType = NO_LANDMARKS;
    private float mMinFaceSize = 0.15f;
    private int mMode = FAST_MODE;

    public boolean isOperational() {
        return true;
    }

    public RNFaceDetector(Context context) {
    }

    public FirebaseVisionFaceDetector getDetector() {
        if (this.mFaceDetector == null) {
            createFaceDetector();
        }
        return this.mFaceDetector;
    }

    public void setClassificationType(int i) {
        if (i != this.mClassificationType) {
            release();
            this.mBuilder.setClassificationMode(i);
            this.mClassificationType = i;
        }
    }

    public void setLandmarkType(int i) {
        if (i != this.mLandmarkType) {
            release();
            this.mBuilder.setLandmarkMode(i);
            this.mLandmarkType = i;
        }
    }

    public void setMode(int i) {
        if (i != this.mMode) {
            release();
            this.mBuilder.setPerformanceMode(i);
            this.mMode = i;
        }
    }

    public void setTracking(boolean z) {
        release();
        if (z) {
            this.mBuilder.enableTracking();
        }
    }

    public void release() {
        FirebaseVisionFaceDetector firebaseVisionFaceDetector = this.mFaceDetector;
        if (firebaseVisionFaceDetector != null) {
            try {
                firebaseVisionFaceDetector.close();
            } catch (Exception unused) {
                Log.e("RNCamera", "Attempt to close FaceDetector failed");
            }
            this.mFaceDetector = null;
        }
    }

    private void createFaceDetector() {
        this.mFaceDetector = FirebaseVision.getInstance().getVisionFaceDetector(this.mBuilder.build());
    }
}
