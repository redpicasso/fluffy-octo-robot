package com.actionsheet;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import java.util.ArrayList;
import java.util.List;

public class ActionSheetModule extends ReactContextBaseJavaModule {
    WritableMap response;

    public String getName() {
        return "ActionSheetAndroid";
    }

    public ActionSheetModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void showActionSheetWithOptions(ReadableMap readableMap, final Callback callback) {
        Context currentActivity = access$700();
        int i = 0;
        if (currentActivity == null) {
            this.response = Arguments.createMap();
            this.response.putString(ReactVideoView.EVENT_PROP_ERROR, "can't find current Activity");
            callback.invoke(this.response);
            return;
        }
        List arrayList = new ArrayList();
        String str = "options";
        if (readableMap.hasKey(str)) {
            ReadableArray array = readableMap.getArray(str);
            while (i < array.size()) {
                arrayList.add(arrayList.size(), array.getString(i));
                i++;
            }
        }
        ListAdapter arrayAdapter = new ArrayAdapter(currentActivity, R.layout.dialog_item, arrayList);
        Builder builder = new Builder(currentActivity, R.style.DialogStyle);
        String str2 = "title";
        if (!(!readableMap.hasKey(str2) || readableMap.getString(str2) == null || readableMap.getString(str2).isEmpty())) {
            builder.setTitle(readableMap.getString(str2));
        }
        builder.setAdapter(arrayAdapter, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.invoke(Integer.valueOf(i));
            }
        });
        AlertDialog create = builder.create();
        create.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
                callback.invoke(new Object[0]);
            }
        });
        create.show();
    }
}
