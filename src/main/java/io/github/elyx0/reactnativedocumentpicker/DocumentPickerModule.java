package io.github.elyx0.reactnativedocumentpicker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public class DocumentPickerModule extends ReactContextBaseJavaModule {
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "ACTIVITY_DOES_NOT_EXIST";
    private static final String E_DOCUMENT_PICKER_CANCELED = "DOCUMENT_PICKER_CANCELED";
    private static final String E_FAILED_TO_SHOW_PICKER = "FAILED_TO_SHOW_PICKER";
    private static final String E_INVALID_DATA_RETURNED = "INVALID_DATA_RETURNED";
    private static final String E_UNABLE_TO_OPEN_FILE_TYPE = "UNABLE_TO_OPEN_FILE_TYPE";
    private static final String E_UNEXPECTED_EXCEPTION = "UNEXPECTED_EXCEPTION";
    private static final String E_UNKNOWN_ACTIVITY_RESULT = "UNKNOWN_ACTIVITY_RESULT";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_SIZE = "size";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_URI = "uri";
    private static final String NAME = "RNDocumentPicker";
    private static final String OPTION_MULIPLE = "multiple";
    private static final String OPTION_TYPE = "type";
    private static final int READ_REQUEST_CODE = 41;
    private final ActivityEventListener activityEventListener = new BaseActivityEventListener() {
        public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
            if (i == 41 && DocumentPickerModule.this.promise != null) {
                DocumentPickerModule documentPickerModule = DocumentPickerModule.this;
                documentPickerModule.onShowActivityResult(i2, intent, documentPickerModule.promise);
                DocumentPickerModule.this.promise = null;
            }
        }
    };
    private Promise promise;

    public String getName() {
        return NAME;
    }

    private String[] readableArrayToStringArray(ReadableArray readableArray) {
        int size = readableArray.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = readableArray.getString(i);
        }
        return strArr;
    }

    public DocumentPickerModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addActivityEventListener(this.activityEventListener);
    }

    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        access$700().removeActivityEventListener(this.activityEventListener);
    }

    @ReactMethod
    public void pick(ReadableMap readableMap, Promise promise) {
        String str = OPTION_MULIPLE;
        String str2 = "type";
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Current activity does not exist");
            return;
        }
        this.promise = promise;
        try {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("*/*");
            boolean z = false;
            if (!readableMap.isNull(str2)) {
                ReadableArray array = readableMap.getArray(str2);
                if (array.size() > 1) {
                    if (VERSION.SDK_INT >= 19) {
                        intent.putExtra("android.intent.extra.MIME_TYPES", readableArrayToStringArray(array));
                    } else {
                        Log.e(NAME, "Multiple type values not supported below API level 19");
                    }
                } else if (array.size() == 1) {
                    intent.setType(array.getString(0));
                }
            }
            if (!readableMap.isNull(str) && readableMap.getBoolean(str)) {
                z = true;
            }
            if (VERSION.SDK_INT >= 18) {
                intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", z);
            }
            if (VERSION.SDK_INT < 19) {
                intent = Intent.createChooser(intent, null);
            }
            currentActivity.startActivityForResult(intent, 41, Bundle.EMPTY);
        } catch (ActivityNotFoundException e) {
            this.promise.reject(E_UNABLE_TO_OPEN_FILE_TYPE, e.getLocalizedMessage());
            this.promise = null;
        } catch (Exception e2) {
            e2.printStackTrace();
            this.promise.reject(E_FAILED_TO_SHOW_PICKER, e2.getLocalizedMessage());
            this.promise = null;
        }
    }

    public void onShowActivityResult(int i, Intent intent, Promise promise) {
        if (i == 0) {
            promise.reject(E_DOCUMENT_PICKER_CANCELED, "User canceled document picker");
        } else if (i == -1) {
            ClipData clipData;
            Uri uri = null;
            if (intent != null) {
                uri = intent.getData();
                clipData = intent.getClipData();
            } else {
                clipData = null;
            }
            try {
                WritableArray createArray = Arguments.createArray();
                if (uri != null) {
                    createArray.pushMap(getMetadata(uri));
                } else if (clipData == null || clipData.getItemCount() <= 0) {
                    promise.reject(E_INVALID_DATA_RETURNED, "Invalid data returned by intent");
                    return;
                } else {
                    i = clipData.getItemCount();
                    for (int i2 = 0; i2 < i; i2++) {
                        createArray.pushMap(getMetadata(clipData.getItemAt(i2).getUri()));
                    }
                }
                promise.resolve(createArray);
            } catch (Throwable e) {
                promise.reject(E_UNEXPECTED_EXCEPTION, e.getLocalizedMessage(), e);
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown activity result: ");
            stringBuilder.append(i);
            promise.reject(E_UNKNOWN_ACTIVITY_RESULT, stringBuilder.toString());
        }
    }

    private WritableMap getMetadata(Uri uri) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("uri", uri.toString());
        ContentResolver contentResolver = access$700().getContentResolver();
        String str = "type";
        createMap.putString(str, contentResolver.getType(uri));
        Cursor query = contentResolver.query(uri, null, null, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    int columnIndex = query.getColumnIndex("_display_name");
                    if (!query.isNull(columnIndex)) {
                        createMap.putString("name", query.getString(columnIndex));
                    }
                    if (VERSION.SDK_INT >= 19) {
                        columnIndex = query.getColumnIndex("mime_type");
                        if (!query.isNull(columnIndex)) {
                            createMap.putString(str, query.getString(columnIndex));
                        }
                    }
                    columnIndex = query.getColumnIndex("_size");
                    if (!query.isNull(columnIndex)) {
                        createMap.putInt(FIELD_SIZE, query.getInt(columnIndex));
                    }
                }
            } catch (Throwable th) {
                if (query != null) {
                    query.close();
                }
            }
        }
        if (query != null) {
            query.close();
        }
        return createMap;
    }
}
