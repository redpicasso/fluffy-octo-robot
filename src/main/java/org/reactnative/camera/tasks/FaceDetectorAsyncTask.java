package org.reactnative.camera.tasks;

import android.os.AsyncTask;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata.Builder;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import java.util.List;
import org.reactnative.camera.utils.ImageDimensions;
import org.reactnative.facedetector.FaceDetectorUtils;
import org.reactnative.facedetector.RNFaceDetector;

public class FaceDetectorAsyncTask extends AsyncTask<Void, Void, Void> {
    private String TAG = "RNCamera";
    private FaceDetectorAsyncTaskDelegate mDelegate;
    private RNFaceDetector mFaceDetector;
    private int mHeight;
    private byte[] mImageData;
    private ImageDimensions mImageDimensions;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mRotation;
    private double mScaleX;
    private double mScaleY;
    private int mWidth;

    public FaceDetectorAsyncTask(FaceDetectorAsyncTaskDelegate faceDetectorAsyncTaskDelegate, RNFaceDetector rNFaceDetector, byte[] bArr, int i, int i2, int i3, float f, int i4, int i5, int i6, int i7, int i8) {
        this.mImageData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mRotation = i3;
        this.mDelegate = faceDetectorAsyncTaskDelegate;
        this.mFaceDetector = rNFaceDetector;
        this.mImageDimensions = new ImageDimensions(i, i2, i3, i4);
        this.mScaleX = ((double) i5) / ((double) (((float) this.mImageDimensions.getWidth()) * f));
        this.mScaleY = ((double) i6) / ((double) (((float) this.mImageDimensions.getHeight()) * f));
        this.mPaddingLeft = i7;
        this.mPaddingTop = i8;
    }

    protected Void doInBackground(Void... voidArr) {
        if (!(isCancelled() || this.mDelegate == null || this.mFaceDetector == null)) {
            this.mFaceDetector.getDetector().detectInImage(FirebaseVisionImage.fromByteArray(this.mImageData, new Builder().setWidth(this.mWidth).setHeight(this.mHeight).setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12).setRotation(getFirebaseRotation()).build())).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                public void onSuccess(List<FirebaseVisionFace> list) {
                    FaceDetectorAsyncTask.this.mDelegate.onFacesDetected(FaceDetectorAsyncTask.this.serializeEventData(list));
                    FaceDetectorAsyncTask.this.mDelegate.onFaceDetectingTaskCompleted();
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exception) {
                    String access$000 = FaceDetectorAsyncTask.this.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Text recognition task failed");
                    stringBuilder.append(exception);
                    Log.e(access$000, stringBuilder.toString());
                    FaceDetectorAsyncTask.this.mDelegate.onFaceDetectingTaskCompleted();
                }
            });
        }
        return null;
    }

    private int getFirebaseRotation() {
        int i = this.mRotation;
        if (i == -90) {
            return 3;
        }
        if (i != 0) {
            if (i == 90) {
                return 1;
            }
            if (i == 180) {
                return 2;
            }
            if (i == 270) {
                return 3;
            }
            String str = this.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad rotation value: ");
            stringBuilder.append(this.mRotation);
            Log.e(str, stringBuilder.toString());
        }
        return 0;
    }

    private WritableArray serializeEventData(List<FirebaseVisionFace> list) {
        WritableArray createArray = Arguments.createArray();
        for (FirebaseVisionFace serializeFace : list) {
            WritableMap serializeFace2 = FaceDetectorUtils.serializeFace(serializeFace, this.mScaleX, this.mScaleY, this.mWidth, this.mHeight, this.mPaddingLeft, this.mPaddingTop);
            if (this.mImageDimensions.getFacing() == 1) {
                serializeFace2 = FaceDetectorUtils.rotateFaceX(serializeFace2, this.mImageDimensions.getWidth(), this.mScaleX);
            } else {
                serializeFace2 = FaceDetectorUtils.changeAnglesDirection(serializeFace2);
            }
            createArray.pushMap(serializeFace2);
        }
        return createArray;
    }
}
