package org.reactnative.camera.tasks;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata.Builder;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionText.Element;
import com.google.firebase.ml.vision.text.FirebaseVisionText.Line;
import com.google.firebase.ml.vision.text.FirebaseVisionText.TextBlock;
import java.util.List;
import org.reactnative.camera.utils.ImageDimensions;

public class TextRecognizerAsyncTask extends AsyncTask<Void, Void, Void> {
    private String TAG = "RNCamera";
    private TextRecognizerAsyncTaskDelegate mDelegate;
    private int mHeight;
    private byte[] mImageData;
    private ImageDimensions mImageDimensions;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mRotation;
    private double mScaleX;
    private double mScaleY;
    private ThemedReactContext mThemedReactContext;
    private int mWidth;

    public static double valueMirroredHorizontally(double d, int i, double d2) {
        return (((double) i) - (d / d2)) * d2;
    }

    public TextRecognizerAsyncTask(TextRecognizerAsyncTaskDelegate textRecognizerAsyncTaskDelegate, ThemedReactContext themedReactContext, byte[] bArr, int i, int i2, int i3, float f, int i4, int i5, int i6, int i7, int i8) {
        this.mDelegate = textRecognizerAsyncTaskDelegate;
        this.mImageData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mRotation = i3;
        this.mImageDimensions = new ImageDimensions(i, i2, i3, i4);
        this.mScaleX = ((double) i5) / ((double) (((float) this.mImageDimensions.getWidth()) * f));
        this.mScaleY = ((double) i6) / ((double) (((float) this.mImageDimensions.getHeight()) * f));
        this.mPaddingLeft = i7;
        this.mPaddingTop = i8;
    }

    protected Void doInBackground(Void... voidArr) {
        if (!(isCancelled() || this.mDelegate == null)) {
            FirebaseVision.getInstance().getOnDeviceTextRecognizer().processImage(FirebaseVisionImage.fromByteArray(this.mImageData, new Builder().setWidth(this.mWidth).setHeight(this.mHeight).setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12).setRotation(getFirebaseRotation()).build())).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    TextRecognizerAsyncTask.this.mDelegate.onTextRecognized(TextRecognizerAsyncTask.this.serializeEventData(firebaseVisionText.getTextBlocks()));
                    TextRecognizerAsyncTask.this.mDelegate.onTextRecognizerTaskCompleted();
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exception) {
                    String access$000 = TextRecognizerAsyncTask.this.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Text recognition task failed");
                    stringBuilder.append(exception);
                    Log.e(access$000, stringBuilder.toString());
                    TextRecognizerAsyncTask.this.mDelegate.onTextRecognizerTaskCompleted();
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
        if (i == 0) {
            return 0;
        }
        if (i == 90) {
            return 1;
        }
        if (i == 180) {
            return 2;
        }
        String str = this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad rotation value: ");
        stringBuilder.append(this.mRotation);
        Log.e(str, stringBuilder.toString());
        return 0;
    }

    private WritableArray serializeEventData(List<TextBlock> list) {
        WritableArray createArray = Arguments.createArray();
        for (TextBlock serializeBloc : list) {
            WritableMap serializeBloc2 = serializeBloc(serializeBloc);
            if (this.mImageDimensions.getFacing() == 1) {
                serializeBloc2 = rotateTextX(serializeBloc2);
            }
            createArray.pushMap(serializeBloc2);
        }
        return createArray;
    }

    private WritableMap serializeBloc(TextBlock textBlock) {
        WritableMap createMap = Arguments.createMap();
        WritableArray createArray = Arguments.createArray();
        for (Line serializeLine : textBlock.getLines()) {
            createArray.pushMap(serializeLine(serializeLine));
        }
        createMap.putArray("components", createArray);
        createMap.putString("value", textBlock.getText());
        createMap.putMap("bounds", processBounds(textBlock.getBoundingBox()));
        createMap.putString("type", "block");
        return createMap;
    }

    private WritableMap serializeLine(Line line) {
        WritableMap createMap = Arguments.createMap();
        WritableArray createArray = Arguments.createArray();
        for (Element serializeElement : line.getElements()) {
            createArray.pushMap(serializeElement(serializeElement));
        }
        createMap.putArray("components", createArray);
        createMap.putString("value", line.getText());
        createMap.putMap("bounds", processBounds(line.getBoundingBox()));
        createMap.putString("type", "line");
        return createMap;
    }

    private WritableMap serializeElement(Element element) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("value", element.getText());
        createMap.putMap("bounds", processBounds(element.getBoundingBox()));
        createMap.putString("type", "element");
        return createMap;
    }

    private WritableMap processBounds(Rect rect) {
        WritableMap createMap = Arguments.createMap();
        int i = rect.left;
        int i2 = rect.top;
        if (rect.left < this.mWidth / 2) {
            i += this.mPaddingLeft / 2;
        } else if (rect.left > this.mWidth / 2) {
            i -= this.mPaddingLeft / 2;
        }
        if (rect.top < this.mHeight / 2) {
            i2 += this.mPaddingTop / 2;
        } else if (rect.top > this.mHeight / 2) {
            i2 -= this.mPaddingTop / 2;
        }
        createMap.putDouble("x", ((double) i) * this.mScaleX);
        createMap.putDouble("y", ((double) i2) * this.mScaleY);
        WritableMap createMap2 = Arguments.createMap();
        createMap2.putDouble("width", ((double) rect.width()) * this.mScaleX);
        createMap2.putDouble("height", ((double) rect.height()) * this.mScaleY);
        WritableMap createMap3 = Arguments.createMap();
        createMap3.putMap("origin", createMap);
        createMap3.putMap("size", createMap2);
        return createMap3;
    }

    private WritableMap rotateTextX(WritableMap writableMap) {
        String str = "bounds";
        ReadableMap map = writableMap.getMap(str);
        String str2 = "origin";
        WritableMap positionTranslatedHorizontally = positionTranslatedHorizontally(positionMirroredHorizontally(map.getMap(str2), this.mImageDimensions.getWidth(), this.mScaleX), -map.getMap("size").getDouble("width"));
        WritableMap createMap = Arguments.createMap();
        createMap.merge(map);
        createMap.putMap(str2, positionTranslatedHorizontally);
        writableMap.putMap(str, createMap);
        str = "components";
        ReadableArray array = writableMap.getArray(str);
        WritableArray createArray = Arguments.createArray();
        for (int i = 0; i < array.size(); i++) {
            createMap = Arguments.createMap();
            createMap.merge(array.getMap(i));
            rotateTextX(createMap);
            createArray.pushMap(createMap);
        }
        writableMap.putArray(str, createArray);
        return writableMap;
    }

    public static WritableMap positionTranslatedHorizontally(ReadableMap readableMap, double d) {
        WritableMap createMap = Arguments.createMap();
        createMap.merge(readableMap);
        String str = "x";
        createMap.putDouble(str, readableMap.getDouble(str) + d);
        return createMap;
    }

    public static WritableMap positionMirroredHorizontally(ReadableMap readableMap, int i, double d) {
        WritableMap createMap = Arguments.createMap();
        createMap.merge(readableMap);
        String str = "x";
        createMap.putDouble(str, valueMirroredHorizontally(readableMap.getDouble(str), i, d));
        return createMap;
    }
}
