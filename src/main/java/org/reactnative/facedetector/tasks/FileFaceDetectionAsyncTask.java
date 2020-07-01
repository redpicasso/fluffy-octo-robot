package org.reactnative.facedetector.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import java.io.File;
import java.util.List;
import org.reactnative.facedetector.FaceDetectorUtils;
import org.reactnative.facedetector.RNFaceDetector;

public class FileFaceDetectionAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String DETECT_LANDMARKS_OPTION_KEY = "detectLandmarks";
    private static final String ERROR_TAG = "E_FACE_DETECTION_FAILED";
    private static final String MODE_OPTION_KEY = "mode";
    private static final String RUN_CLASSIFICATIONS_OPTION_KEY = "runClassifications";
    private Context mContext;
    private int mHeight = 0;
    private ReadableMap mOptions;
    private int mOrientation = 0;
    private String mPath;
    private Promise mPromise;
    private RNFaceDetector mRNFaceDetector;
    private String mUri;
    private int mWidth = 0;

    public FileFaceDetectionAsyncTask(Context context, ReadableMap readableMap, Promise promise) {
        this.mUri = readableMap.getString("uri");
        this.mPromise = promise;
        this.mOptions = readableMap;
        this.mContext = context;
    }

    protected void onPreExecute() {
        String str = this.mUri;
        String str2 = ERROR_TAG;
        if (str == null) {
            this.mPromise.reject(str2, "You have to provide an URI of an image.");
            cancel(true);
            return;
        }
        this.mPath = Uri.parse(str).getPath();
        str = this.mPath;
        String str3 = "`.";
        Promise promise;
        StringBuilder stringBuilder;
        if (str == null) {
            promise = this.mPromise;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid URI provided: `");
            stringBuilder.append(this.mUri);
            stringBuilder.append(str3);
            promise.reject(str2, stringBuilder.toString());
            cancel(true);
            return;
        }
        Object obj = (str.startsWith(this.mContext.getCacheDir().getPath()) || this.mPath.startsWith(this.mContext.getFilesDir().getPath())) ? 1 : null;
        if (obj == null) {
            this.mPromise.reject(str2, "The image has to be in the local app's directories.");
            cancel(true);
            return;
        }
        if (!new File(this.mPath).exists()) {
            promise = this.mPromise;
            stringBuilder = new StringBuilder();
            stringBuilder.append("The file does not exist. Given path: `");
            stringBuilder.append(this.mPath);
            stringBuilder.append(str3);
            promise.reject(str2, stringBuilder.toString());
            cancel(true);
        }
    }

    protected Void doInBackground(Void... voidArr) {
        StringBuilder stringBuilder;
        String str = ERROR_TAG;
        if (isCancelled()) {
            return null;
        }
        this.mRNFaceDetector = detectorForOptions(this.mOptions, this.mContext);
        try {
            this.mOrientation = new ExifInterface(this.mPath).getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        } catch (Throwable e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Reading orientation from file `");
            stringBuilder.append(this.mPath);
            stringBuilder.append("` failed.");
            Log.e(str, stringBuilder.toString(), e);
        }
        try {
            this.mRNFaceDetector.getDetector().detectInImage(FirebaseVisionImage.fromFilePath(this.mContext, Uri.parse(this.mUri))).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                public void onSuccess(List<FirebaseVisionFace> list) {
                    FileFaceDetectionAsyncTask.this.serializeEventData(list);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exception) {
                    String str = "Text recognition task failed";
                    String str2 = FileFaceDetectionAsyncTask.ERROR_TAG;
                    Log.e(str2, str, exception);
                    FileFaceDetectionAsyncTask.this.mPromise.reject(str2, str, (Throwable) exception);
                }
            });
        } catch (Throwable e2) {
            e2.printStackTrace();
            stringBuilder = new StringBuilder();
            String str2 = "Creating Firebase Image from uri";
            stringBuilder.append(str2);
            stringBuilder.append(this.mUri);
            String str3 = "failed";
            stringBuilder.append(str3);
            Log.e(str, stringBuilder.toString(), e2);
            Promise promise = this.mPromise;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str2);
            stringBuilder2.append(this.mUri);
            stringBuilder2.append(str3);
            promise.reject(str, stringBuilder2.toString(), e2);
        }
        return null;
    }

    private void serializeEventData(List<FirebaseVisionFace> list) {
        WritableMap createMap = Arguments.createMap();
        WritableArray createArray = Arguments.createArray();
        for (FirebaseVisionFace serializeFace : list) {
            WritableMap serializeFace2 = FaceDetectorUtils.serializeFace(serializeFace);
            String str = "yawAngle";
            serializeFace2.putDouble(str, ((-serializeFace2.getDouble(str)) + 360.0d) % 360.0d);
            str = "rollAngle";
            serializeFace2.putDouble(str, ((-serializeFace2.getDouble(str)) + 360.0d) % 360.0d);
            createArray.pushMap(serializeFace2);
        }
        createMap.putArray("faces", createArray);
        WritableMap createMap2 = Arguments.createMap();
        createMap2.putInt("width", this.mWidth);
        createMap2.putInt("height", this.mHeight);
        createMap2.putInt(ReactVideoView.EVENT_PROP_ORIENTATION, this.mOrientation);
        createMap2.putString("uri", this.mUri);
        createMap.putMap("image", createMap2);
        this.mRNFaceDetector.release();
        this.mPromise.resolve(createMap);
    }

    private static RNFaceDetector detectorForOptions(ReadableMap readableMap, Context context) {
        RNFaceDetector rNFaceDetector = new RNFaceDetector(context);
        rNFaceDetector.setTracking(false);
        String str = MODE_OPTION_KEY;
        if (readableMap.hasKey(str)) {
            rNFaceDetector.setMode(readableMap.getInt(str));
        }
        str = RUN_CLASSIFICATIONS_OPTION_KEY;
        if (readableMap.hasKey(str)) {
            rNFaceDetector.setClassificationType(readableMap.getInt(str));
        }
        str = DETECT_LANDMARKS_OPTION_KEY;
        if (readableMap.hasKey(str)) {
            rNFaceDetector.setLandmarkType(readableMap.getInt(str));
        }
        return rNFaceDetector;
    }
}
