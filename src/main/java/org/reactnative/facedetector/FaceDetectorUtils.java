package org.reactnative.facedetector;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

public class FaceDetectorUtils {
    private static final String[] landmarkNames = new String[]{"bottomMouthPosition", "leftCheekPosition", "leftEarPosition", "leftEyePosition", "leftMouthPosition", "noseBasePosition", "rightCheekPosition", "rightEarPosition", "rightEyePosition", "rightMouthPosition"};

    public static double valueMirroredHorizontally(double d, int i, double d2) {
        return (((double) i) - (d / d2)) * d2;
    }

    public static WritableMap serializeFace(FirebaseVisionFace firebaseVisionFace) {
        return serializeFace(firebaseVisionFace, 1.0d, 1.0d, 0, 0, 0, 0);
    }

    public static WritableMap serializeFace(FirebaseVisionFace firebaseVisionFace, double d, double d2, int i, int i2, int i3, int i4) {
        FirebaseVisionFace firebaseVisionFace2;
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("faceID", firebaseVisionFace.getTrackingId() != -1 ? firebaseVisionFace.getTrackingId() : 0);
        createMap.putDouble("rollAngle", (double) firebaseVisionFace.getHeadEulerAngleZ());
        createMap.putDouble("yawAngle", (double) firebaseVisionFace.getHeadEulerAngleY());
        if (firebaseVisionFace.getSmilingProbability() != -1.0f) {
            createMap.putDouble("smilingProbability", (double) firebaseVisionFace.getSmilingProbability());
        }
        if (firebaseVisionFace.getLeftEyeOpenProbability() != -1.0f) {
            createMap.putDouble("leftEyeOpenProbability", (double) firebaseVisionFace.getLeftEyeOpenProbability());
        }
        if (firebaseVisionFace.getRightEyeOpenProbability() != -1.0f) {
            createMap.putDouble("rightEyeOpenProbability", (double) firebaseVisionFace.getRightEyeOpenProbability());
        }
        int[] iArr = new int[]{0, 1, 3, 4, 5, 6, 7, 9, 10, 11};
        for (int i5 = 0; i5 < iArr.length; i5++) {
            firebaseVisionFace2 = firebaseVisionFace;
            FirebaseVisionFaceLandmark landmark = firebaseVisionFace.getLandmark(iArr[i5]);
            if (landmark != null) {
                createMap.putMap(landmarkNames[i5], mapFromPoint(landmark.getPosition(), d, d2, i, i2, i3, i4));
            }
        }
        firebaseVisionFace2 = firebaseVisionFace;
        WritableMap createMap2 = Arguments.createMap();
        Float valueOf = Float.valueOf(firebaseVisionFace.getBoundingBox().exactCenterX() - ((float) (firebaseVisionFace.getBoundingBox().width() / 2)));
        Float valueOf2 = Float.valueOf(firebaseVisionFace.getBoundingBox().exactCenterY() - ((float) (firebaseVisionFace.getBoundingBox().height() / 2)));
        float f = (float) (i / 2);
        if (firebaseVisionFace.getBoundingBox().exactCenterX() < f) {
            valueOf = Float.valueOf(valueOf.floatValue() + ((float) (i3 / 2)));
        } else if (firebaseVisionFace.getBoundingBox().exactCenterX() > f) {
            valueOf = Float.valueOf(valueOf.floatValue() - ((float) (i3 / 2)));
        }
        f = (float) (i2 / 2);
        if (firebaseVisionFace.getBoundingBox().exactCenterY() < f) {
            valueOf2 = Float.valueOf(valueOf2.floatValue() + ((float) (i4 / 2)));
        } else if (firebaseVisionFace.getBoundingBox().exactCenterY() > f) {
            valueOf2 = Float.valueOf(valueOf2.floatValue() - ((float) (i4 / 2)));
        }
        createMap2.putDouble("x", ((double) valueOf.floatValue()) * d);
        createMap2.putDouble("y", ((double) valueOf2.floatValue()) * d2);
        WritableMap createMap3 = Arguments.createMap();
        createMap3.putDouble("width", ((double) firebaseVisionFace.getBoundingBox().width()) * d);
        createMap3.putDouble("height", ((double) firebaseVisionFace.getBoundingBox().height()) * d2);
        WritableMap createMap4 = Arguments.createMap();
        createMap4.putMap("origin", createMap2);
        createMap4.putMap("size", createMap3);
        createMap.putMap("bounds", createMap4);
        return createMap;
    }

    public static WritableMap rotateFaceX(WritableMap writableMap, int i, double d) {
        String str = "bounds";
        ReadableMap map = writableMap.getMap(str);
        String str2 = "origin";
        WritableMap positionTranslatedHorizontally = positionTranslatedHorizontally(positionMirroredHorizontally(map.getMap(str2), i, d), -map.getMap("size").getDouble("width"));
        WritableMap createMap = Arguments.createMap();
        createMap.merge(map);
        createMap.putMap(str2, positionTranslatedHorizontally);
        for (String str3 : landmarkNames) {
            ReadableMap map2 = writableMap.hasKey(str3) ? writableMap.getMap(str3) : null;
            if (map2 != null) {
                writableMap.putMap(str3, positionMirroredHorizontally(map2, i, d));
            }
        }
        writableMap.putMap(str, createMap);
        return writableMap;
    }

    public static WritableMap changeAnglesDirection(WritableMap writableMap) {
        String str = "rollAngle";
        writableMap.putDouble(str, ((-writableMap.getDouble(str)) + 360.0d) % 360.0d);
        str = "yawAngle";
        writableMap.putDouble(str, ((-writableMap.getDouble(str)) + 360.0d) % 360.0d);
        return writableMap;
    }

    public static WritableMap mapFromPoint(FirebaseVisionPoint firebaseVisionPoint, double d, double d2, int i, int i2, int i3, int i4) {
        WritableMap createMap = Arguments.createMap();
        Float x = firebaseVisionPoint.getX();
        Float y = firebaseVisionPoint.getY();
        float f = (float) (i / 2);
        if (firebaseVisionPoint.getX().floatValue() < f) {
            x = Float.valueOf(x.floatValue() + ((float) (i3 / 2)));
        } else if (firebaseVisionPoint.getX().floatValue() > f) {
            x = Float.valueOf(x.floatValue() - ((float) (i3 / 2)));
        }
        float f2 = (float) (i2 / 2);
        if (firebaseVisionPoint.getY().floatValue() < f2) {
            y = Float.valueOf(y.floatValue() + ((float) (i4 / 2)));
        } else if (firebaseVisionPoint.getY().floatValue() > f2) {
            y = Float.valueOf(y.floatValue() - ((float) (i4 / 2)));
        }
        createMap.putDouble("x", ((double) x.floatValue()) * d);
        createMap.putDouble("y", ((double) y.floatValue()) * d2);
        return createMap;
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
