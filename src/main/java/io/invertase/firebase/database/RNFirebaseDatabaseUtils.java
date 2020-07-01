package io.invertase.firebase.database;

import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.MutableData;
import io.invertase.firebase.Utils;
import javax.annotation.Nullable;

public class RNFirebaseDatabaseUtils {
    private static final String TAG = "RNFirebaseDatabaseUtils";

    public static WritableMap snapshotToMap(DataSnapshot dataSnapshot, @Nullable String str) {
        WritableMap createMap = Arguments.createMap();
        createMap.putMap("snapshot", snapshotToMap(dataSnapshot));
        createMap.putString("previousChildName", str);
        return createMap;
    }

    public static WritableMap snapshotToMap(DataSnapshot dataSnapshot) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("key", dataSnapshot.getKey());
        createMap.putBoolean("exists", dataSnapshot.exists());
        createMap.putBoolean("hasChildren", dataSnapshot.hasChildren());
        createMap.putDouble("childrenCount", (double) dataSnapshot.getChildrenCount());
        createMap.putArray("childKeys", getChildKeys(dataSnapshot));
        Utils.mapPutValue("priority", dataSnapshot.getPriority(), createMap);
        String str = "value";
        if (dataSnapshot.hasChildren()) {
            Object castValue = castValue(dataSnapshot);
            if (castValue instanceof WritableNativeArray) {
                createMap.putArray(str, (WritableArray) castValue);
            } else {
                createMap.putMap(str, (WritableMap) castValue);
            }
        } else {
            Utils.mapPutValue(str, dataSnapshot.getValue(), createMap);
        }
        return createMap;
    }

    public static <Any> Any castValue(DataSnapshot dataSnapshot) {
        if (dataSnapshot.hasChildren()) {
            if (isArray(dataSnapshot)) {
                return buildArray(dataSnapshot);
            }
            return buildMap(dataSnapshot);
        } else if (dataSnapshot.getValue() == null) {
            return null;
        } else {
            String name = dataSnapshot.getValue().getClass().getName();
            int i = -1;
            switch (name.hashCode()) {
                case 344809556:
                    if (name.equals("java.lang.Boolean")) {
                        i = 0;
                        break;
                    }
                    break;
                case 398795216:
                    if (name.equals("java.lang.Long")) {
                        i = 1;
                        break;
                    }
                    break;
                case 761287205:
                    if (name.equals("java.lang.Double")) {
                        i = 2;
                        break;
                    }
                    break;
                case 1195259493:
                    if (name.equals("java.lang.String")) {
                        i = 3;
                        break;
                    }
                    break;
            }
            if (i == 0 || i == 1 || i == 2 || i == 3) {
                return dataSnapshot.getValue();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid type: ");
            stringBuilder.append(name);
            Log.w(TAG, stringBuilder.toString());
            return null;
        }
    }

    public static <Any> Any castValue(MutableData mutableData) {
        if (mutableData.hasChildren()) {
            if (isArray(mutableData)) {
                return buildArray(mutableData);
            }
            return buildMap(mutableData);
        } else if (mutableData.getValue() == null) {
            return null;
        } else {
            String name = mutableData.getValue().getClass().getName();
            int i = -1;
            switch (name.hashCode()) {
                case 344809556:
                    if (name.equals("java.lang.Boolean")) {
                        i = 0;
                        break;
                    }
                    break;
                case 398795216:
                    if (name.equals("java.lang.Long")) {
                        i = 1;
                        break;
                    }
                    break;
                case 761287205:
                    if (name.equals("java.lang.Double")) {
                        i = 2;
                        break;
                    }
                    break;
                case 1195259493:
                    if (name.equals("java.lang.String")) {
                        i = 3;
                        break;
                    }
                    break;
            }
            if (i == 0 || i == 1 || i == 2 || i == 3) {
                return mutableData.getValue();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid type: ");
            stringBuilder.append(name);
            Log.w(TAG, stringBuilder.toString());
            return null;
        }
    }

    private static boolean isArray(DataSnapshot dataSnapshot) {
        long childrenCount = (dataSnapshot.getChildrenCount() * 2) - 1;
        long j = -1;
        for (DataSnapshot key : dataSnapshot.getChildren()) {
            try {
                long parseLong = Long.parseLong(key.getKey());
                if (parseLong > j && parseLong <= childrenCount) {
                    j = parseLong;
                }
            } catch (NumberFormatException unused) {
                return false;
            }
            return false;
        }
        return true;
    }

    private static boolean isArray(MutableData mutableData) {
        long childrenCount = (mutableData.getChildrenCount() * 2) - 1;
        long j = -1;
        for (MutableData key : mutableData.getChildren()) {
            try {
                long parseLong = Long.parseLong(key.getKey());
                if (parseLong > j && parseLong <= childrenCount) {
                    j++;
                }
            } catch (NumberFormatException unused) {
                return false;
            }
            return false;
        }
        return true;
    }

    private static <Any> WritableArray buildArray(DataSnapshot dataSnapshot) {
        WritableArray createArray = Arguments.createArray();
        long j = 0;
        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
            long parseLong = Long.parseLong(dataSnapshot2.getKey());
            if (parseLong > j) {
                while (j < parseLong) {
                    createArray.pushNull();
                    j++;
                }
                j = parseLong;
            }
            Object castValue = castValue(dataSnapshot2);
            String name = castValue.getClass().getName();
            int i = -1;
            switch (name.hashCode()) {
                case -1658217206:
                    if (name.equals("com.facebook.react.bridge.WritableNativeMap")) {
                        i = 4;
                        break;
                    }
                    break;
                case -124438905:
                    if (name.equals("com.facebook.react.bridge.WritableNativeArray")) {
                        i = 5;
                        break;
                    }
                    break;
                case 344809556:
                    if (name.equals("java.lang.Boolean")) {
                        i = 0;
                        break;
                    }
                    break;
                case 398795216:
                    if (name.equals("java.lang.Long")) {
                        i = 1;
                        break;
                    }
                    break;
                case 761287205:
                    if (name.equals("java.lang.Double")) {
                        i = 2;
                        break;
                    }
                    break;
                case 1195259493:
                    if (name.equals("java.lang.String")) {
                        i = 3;
                        break;
                    }
                    break;
            }
            if (i == 0) {
                createArray.pushBoolean(((Boolean) castValue).booleanValue());
            } else if (i == 1) {
                createArray.pushDouble((double) ((Long) castValue).longValue());
            } else if (i == 2) {
                createArray.pushDouble(((Double) castValue).doubleValue());
            } else if (i == 3) {
                createArray.pushString((String) castValue);
            } else if (i == 4) {
                createArray.pushMap((WritableMap) castValue);
            } else if (i != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid type: ");
                stringBuilder.append(castValue.getClass().getName());
                Log.w(TAG, stringBuilder.toString());
            } else {
                createArray.pushArray((WritableArray) castValue);
            }
            j++;
        }
        return createArray;
    }

    private static <Any> WritableArray buildArray(MutableData mutableData) {
        WritableArray createArray = Arguments.createArray();
        long j = 0;
        for (MutableData mutableData2 : mutableData.getChildren()) {
            long parseLong = Long.parseLong(mutableData2.getKey());
            if (parseLong > j) {
                while (j < parseLong) {
                    createArray.pushNull();
                    j++;
                }
                j = parseLong;
            }
            Object castValue = castValue(mutableData2);
            String name = castValue.getClass().getName();
            int i = -1;
            switch (name.hashCode()) {
                case -1658217206:
                    if (name.equals("com.facebook.react.bridge.WritableNativeMap")) {
                        i = 4;
                        break;
                    }
                    break;
                case -124438905:
                    if (name.equals("com.facebook.react.bridge.WritableNativeArray")) {
                        i = 5;
                        break;
                    }
                    break;
                case 344809556:
                    if (name.equals("java.lang.Boolean")) {
                        i = 0;
                        break;
                    }
                    break;
                case 398795216:
                    if (name.equals("java.lang.Long")) {
                        i = 1;
                        break;
                    }
                    break;
                case 761287205:
                    if (name.equals("java.lang.Double")) {
                        i = 2;
                        break;
                    }
                    break;
                case 1195259493:
                    if (name.equals("java.lang.String")) {
                        i = 3;
                        break;
                    }
                    break;
            }
            if (i == 0) {
                createArray.pushBoolean(((Boolean) castValue).booleanValue());
            } else if (i == 1) {
                createArray.pushDouble((double) ((Long) castValue).longValue());
            } else if (i == 2) {
                createArray.pushDouble(((Double) castValue).doubleValue());
            } else if (i == 3) {
                createArray.pushString((String) castValue);
            } else if (i == 4) {
                createArray.pushMap((WritableMap) castValue);
            } else if (i != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid type: ");
                stringBuilder.append(castValue.getClass().getName());
                Log.w(TAG, stringBuilder.toString());
            } else {
                createArray.pushArray((WritableArray) castValue);
            }
            j++;
        }
        return createArray;
    }

    private static <Any> WritableMap buildMap(DataSnapshot dataSnapshot) {
        WritableMap createMap = Arguments.createMap();
        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
            Object castValue = castValue(dataSnapshot2);
            String name = castValue.getClass().getName();
            int i = -1;
            switch (name.hashCode()) {
                case -1658217206:
                    if (name.equals("com.facebook.react.bridge.WritableNativeMap")) {
                        i = 4;
                        break;
                    }
                    break;
                case -124438905:
                    if (name.equals("com.facebook.react.bridge.WritableNativeArray")) {
                        i = 5;
                        break;
                    }
                    break;
                case 344809556:
                    if (name.equals("java.lang.Boolean")) {
                        i = 0;
                        break;
                    }
                    break;
                case 398795216:
                    if (name.equals("java.lang.Long")) {
                        i = 1;
                        break;
                    }
                    break;
                case 761287205:
                    if (name.equals("java.lang.Double")) {
                        i = 2;
                        break;
                    }
                    break;
                case 1195259493:
                    if (name.equals("java.lang.String")) {
                        i = 3;
                        break;
                    }
                    break;
            }
            if (i == 0) {
                createMap.putBoolean(dataSnapshot2.getKey(), ((Boolean) castValue).booleanValue());
            } else if (i == 1) {
                createMap.putDouble(dataSnapshot2.getKey(), (double) ((Long) castValue).longValue());
            } else if (i == 2) {
                createMap.putDouble(dataSnapshot2.getKey(), ((Double) castValue).doubleValue());
            } else if (i == 3) {
                createMap.putString(dataSnapshot2.getKey(), (String) castValue);
            } else if (i == 4) {
                createMap.putMap(dataSnapshot2.getKey(), (WritableMap) castValue);
            } else if (i != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid type: ");
                stringBuilder.append(castValue.getClass().getName());
                Log.w(TAG, stringBuilder.toString());
            } else {
                createMap.putArray(dataSnapshot2.getKey(), (WritableArray) castValue);
            }
        }
        return createMap;
    }

    private static <Any> WritableMap buildMap(MutableData mutableData) {
        WritableMap createMap = Arguments.createMap();
        for (MutableData mutableData2 : mutableData.getChildren()) {
            Object castValue = castValue(mutableData2);
            String name = castValue.getClass().getName();
            int i = -1;
            switch (name.hashCode()) {
                case -1658217206:
                    if (name.equals("com.facebook.react.bridge.WritableNativeMap")) {
                        i = 4;
                        break;
                    }
                    break;
                case -124438905:
                    if (name.equals("com.facebook.react.bridge.WritableNativeArray")) {
                        i = 5;
                        break;
                    }
                    break;
                case 344809556:
                    if (name.equals("java.lang.Boolean")) {
                        i = 0;
                        break;
                    }
                    break;
                case 398795216:
                    if (name.equals("java.lang.Long")) {
                        i = 1;
                        break;
                    }
                    break;
                case 761287205:
                    if (name.equals("java.lang.Double")) {
                        i = 2;
                        break;
                    }
                    break;
                case 1195259493:
                    if (name.equals("java.lang.String")) {
                        i = 3;
                        break;
                    }
                    break;
            }
            if (i == 0) {
                createMap.putBoolean(mutableData2.getKey(), ((Boolean) castValue).booleanValue());
            } else if (i == 1) {
                createMap.putDouble(mutableData2.getKey(), (double) ((Long) castValue).longValue());
            } else if (i == 2) {
                createMap.putDouble(mutableData2.getKey(), ((Double) castValue).doubleValue());
            } else if (i == 3) {
                createMap.putString(mutableData2.getKey(), (String) castValue);
            } else if (i == 4) {
                createMap.putMap(mutableData2.getKey(), (WritableMap) castValue);
            } else if (i != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid type: ");
                stringBuilder.append(castValue.getClass().getName());
                Log.w(TAG, stringBuilder.toString());
            } else {
                createMap.putArray(mutableData2.getKey(), (WritableArray) castValue);
            }
        }
        return createMap;
    }

    public static WritableArray getChildKeys(DataSnapshot dataSnapshot) {
        WritableArray createArray = Arguments.createArray();
        if (dataSnapshot.hasChildren()) {
            for (DataSnapshot key : dataSnapshot.getChildren()) {
                createArray.pushString(key.getKey());
            }
        }
        return createArray;
    }
}
