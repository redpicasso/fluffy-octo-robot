package cl.json.social;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import cl.json.RNShareModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.common.primitives.Ints;

public abstract class SingleShareIntent extends ShareIntent {
    protected String appStoreURL = null;
    protected String playStoreURL = null;

    public SingleShareIntent(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        System.out.println(getPackage());
        if (!(getPackage() == null && getDefaultWebLink() == null && getPlayStoreLink() == null)) {
            if (ShareIntent.isPackageInstalled(getPackage(), this.reactContext)) {
                System.out.println("INSTALLED");
                if (getComponentClass() != null) {
                    getIntent().setComponent(new ComponentName(getPackage(), getComponentClass()));
                } else {
                    getIntent().setPackage(getPackage());
                }
                super.open(readableMap);
            } else {
                String replace;
                System.out.println("NOT INSTALLED");
                if (getDefaultWebLink() != null) {
                    replace = getDefaultWebLink().replace("{url}", ShareIntent.urlEncode(readableMap.getString(ImagesContract.URL))).replace("{message}", ShareIntent.urlEncode(readableMap.getString("message")));
                } else {
                    replace = getPlayStoreLink() != null ? getPlayStoreLink() : "";
                }
                setIntent(new Intent(new Intent("android.intent.action.VIEW", Uri.parse(replace))));
            }
        }
        super.open(readableMap);
    }

    protected void openIntentChooser() throws ActivityNotFoundException {
        String str = "forceDialog";
        boolean hasKey = this.options.hasKey(str);
        Boolean valueOf = Boolean.valueOf(true);
        if (hasKey && this.options.getBoolean(str)) {
            Activity currentActivity = this.reactContext.getCurrentActivity();
            Intent createChooser;
            if (currentActivity == null) {
                TargetChosenReceiver.sendCallback(false, "Something went wrong");
            } else if (TargetChosenReceiver.isSupported()) {
                createChooser = Intent.createChooser(getIntent(), this.chooserTitle, TargetChosenReceiver.getSharingSenderIntent(this.reactContext));
                createChooser.setFlags(Ints.MAX_POWER_OF_TWO);
                currentActivity.startActivityForResult(createChooser, RNShareModule.SHARE_REQUEST_CODE);
            } else {
                createChooser = Intent.createChooser(getIntent(), this.chooserTitle);
                createChooser.setFlags(Ints.MAX_POWER_OF_TWO);
                currentActivity.startActivityForResult(createChooser, RNShareModule.SHARE_REQUEST_CODE);
                TargetChosenReceiver.sendCallback(true, valueOf, "OK");
            }
        } else {
            getIntent().setFlags(268435456);
            this.reactContext.startActivity(getIntent());
            TargetChosenReceiver.sendCallback(true, valueOf, getIntent().getPackage());
        }
    }
}
