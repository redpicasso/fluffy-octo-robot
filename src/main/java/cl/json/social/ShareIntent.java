package cl.json.social;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import cl.json.RNShareModule;
import cl.json.ShareFile;
import cl.json.ShareFiles;
import com.bumptech.glide.load.Key;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.common.primitives.Ints;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class ShareIntent {
    protected String chooserTitle = "Share";
    protected ShareFile fileShare;
    protected Intent intent;
    protected ReadableMap options;
    protected final ReactApplicationContext reactContext;

    protected String getComponentClass() {
        return null;
    }

    protected abstract String getDefaultWebLink();

    protected abstract String getPackage();

    protected abstract String getPlayStoreLink();

    public ShareIntent(ReactApplicationContext reactApplicationContext) {
        this.reactContext = reactApplicationContext;
        setIntent(new Intent("android.intent.action.SEND"));
        getIntent().setType("text/plain");
    }

    public Intent excludeChooserIntent(Intent intent, ReadableMap readableMap) {
        List arrayList = new ArrayList();
        List<HashMap> arrayList2 = new ArrayList();
        Intent intent2 = new Intent(intent.getAction());
        intent2.setType(intent.getType());
        List queryIntentActivities = this.reactContext.getPackageManager().queryIntentActivities(intent2, 0);
        if (!queryIntentActivities.isEmpty()) {
            String str;
            String str2;
            Iterator it = queryIntentActivities.iterator();
            while (true) {
                str = "className";
                str2 = "packageName";
                if (!it.hasNext()) {
                    break;
                }
                ResolveInfo resolveInfo = (ResolveInfo) it.next();
                if (resolveInfo.activityInfo != null) {
                    if (!readableMap.getArray("excludedActivityTypes").toString().contains(resolveInfo.activityInfo.packageName)) {
                        HashMap hashMap = new HashMap();
                        hashMap.put(str2, resolveInfo.activityInfo.packageName);
                        hashMap.put(str, resolveInfo.activityInfo.name);
                        hashMap.put("simpleName", String.valueOf(resolveInfo.activityInfo.loadLabel(this.reactContext.getPackageManager())));
                        arrayList2.add(hashMap);
                    }
                }
            }
            if (!arrayList2.isEmpty()) {
                Collections.sort(arrayList2, new Comparator<HashMap<String, String>>() {
                    public int compare(HashMap<String, String> hashMap, HashMap<String, String> hashMap2) {
                        String str = "simpleName";
                        return ((String) hashMap.get(str)).compareTo((String) hashMap2.get(str));
                    }
                });
                for (HashMap hashMap2 : arrayList2) {
                    intent2 = (Intent) intent.clone();
                    intent2.setPackage((String) hashMap2.get(str2));
                    intent2.setClassName((String) hashMap2.get(str2), (String) hashMap2.get(str));
                    arrayList.add(intent2);
                }
                intent = Intent.createChooser((Intent) arrayList.remove(arrayList.size() - 1), Event.SHARE);
                intent.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList.toArray(new Parcelable[0]));
                return intent;
            }
        }
        return Intent.createChooser(intent, "Share");
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        StringBuilder stringBuilder;
        this.options = readableMap;
        String str = "subject";
        if (hasValidKey(str, readableMap)) {
            getIntent().putExtra("android.intent.extra.SUBJECT", readableMap.getString(str));
        }
        if (hasValidKey("email", readableMap)) {
            getIntent().putExtra("android.intent.extra.EMAIL", new String[]{readableMap.getString("email")});
        }
        str = "title";
        if (hasValidKey(str, readableMap)) {
            this.chooserTitle = readableMap.getString(str);
        }
        str = "message";
        String str2 = "";
        Object string = hasValidKey(str, readableMap) ? readableMap.getString(str) : str2;
        String str3 = NotificationCompat.CATEGORY_SOCIAL;
        if (hasValidKey(str3, readableMap)) {
            str2 = readableMap.getString(str3);
        }
        if (str2.equals("whatsapp")) {
            str3 = readableMap.getString("whatsAppNumber");
            if (!str3.isEmpty()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str3);
                stringBuilder.append("@s.whatsapp.net");
                getIntent().putExtra("jid", stringBuilder.toString());
            }
        }
        str3 = "urls";
        String str4 = " ";
        String str5 = "android.intent.extra.STREAM";
        String str6 = "android.intent.extra.TEXT";
        if (hasValidKey(str3, readableMap)) {
            ShareFiles fileShares = getFileShares(readableMap);
            if (fileShares.isFile()) {
                ArrayList uri = fileShares.getURI();
                getIntent().setAction("android.intent.action.SEND_MULTIPLE");
                getIntent().setType(fileShares.getType());
                getIntent().putParcelableArrayListExtra(str5, uri);
                getIntent().addFlags(1);
                if (!TextUtils.isEmpty(string)) {
                    getIntent().putExtra(str6, string);
                    return;
                }
                return;
            } else if (TextUtils.isEmpty(string)) {
                getIntent().putExtra(str6, readableMap.getArray(str3).getString(0));
                return;
            } else {
                Intent intent = getIntent();
                stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(str4);
                stringBuilder.append(readableMap.getArray(str3).getString(0));
                intent.putExtra(str6, stringBuilder.toString());
                return;
            }
        }
        str3 = ImagesContract.URL;
        if (hasValidKey(str3, readableMap)) {
            this.fileShare = getFileShare(readableMap);
            if (this.fileShare.isFile()) {
                Parcelable uri2 = this.fileShare.getURI();
                getIntent().setType(this.fileShare.getType());
                getIntent().putExtra(str5, uri2);
                getIntent().addFlags(1);
                if (!TextUtils.isEmpty(string)) {
                    getIntent().putExtra(str6, string);
                }
            } else if (TextUtils.isEmpty(string)) {
                getIntent().putExtra(str6, readableMap.getString(str3));
            } else {
                Intent intent2 = getIntent();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(string);
                stringBuilder2.append(str4);
                stringBuilder2.append(readableMap.getString(str3));
                intent2.putExtra(str6, stringBuilder2.toString());
            }
        } else if (!TextUtils.isEmpty(string)) {
            getIntent().putExtra(str6, string);
        }
    }

    protected ShareFile getFileShare(ReadableMap readableMap) {
        String str = "filename";
        str = hasValidKey(str, readableMap) ? readableMap.getString(str) : null;
        String str2 = "type";
        boolean hasValidKey = hasValidKey(str2, readableMap);
        String str3 = ImagesContract.URL;
        if (hasValidKey) {
            return new ShareFile(readableMap.getString(str3), readableMap.getString(str2), str, this.reactContext);
        }
        return new ShareFile(readableMap.getString(str3), str, this.reactContext);
    }

    protected ShareFiles getFileShares(ReadableMap readableMap) {
        ArrayList arrayList = new ArrayList();
        String str = "filenames";
        if (hasValidKey(str, readableMap)) {
            ReadableArray array = readableMap.getArray(str);
            for (int i = 0; i < array.size(); i++) {
                arrayList.add(array.getString(i));
            }
        }
        str = "type";
        String str2 = "urls";
        if (hasValidKey(str, readableMap)) {
            return new ShareFiles(readableMap.getArray(str2), arrayList, readableMap.getString(str), this.reactContext);
        }
        return new ShareFiles(readableMap.getArray(str2), arrayList, this.reactContext);
    }

    protected static String urlEncode(String str) {
        try {
            str = URLEncoder.encode(str, Key.STRING_CHARSET_NAME);
            return str;
        } catch (UnsupportedEncodingException unused) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URLEncoder.encode() failed for ");
            stringBuilder.append(str);
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    protected Intent[] getIntentsToViewFile(Intent intent, Uri uri) {
        int i = 0;
        List queryIntentActivities = this.reactContext.getPackageManager().queryIntentActivities(intent, 0);
        Intent[] intentArr = new Intent[queryIntentActivities.size()];
        while (i < queryIntentActivities.size()) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivities.get(i);
            String str = resolveInfo.activityInfo.packageName;
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName(str, resolveInfo.activityInfo.name));
            intent2.setAction("android.intent.action.VIEW");
            intent2.setDataAndType(uri, intent.getType());
            intent2.addFlags(1);
            intentArr[i] = new Intent(intent2);
            i++;
        }
        return intentArr;
    }

    protected void openIntentChooser() throws ActivityNotFoundException {
        Activity currentActivity = this.reactContext.getCurrentActivity();
        if (currentActivity == null) {
            TargetChosenReceiver.sendCallback(false, "Something went wrong");
            return;
        }
        Intent createChooser;
        IntentSender intentSender = null;
        if (TargetChosenReceiver.isSupported()) {
            intentSender = TargetChosenReceiver.getSharingSenderIntent(this.reactContext);
            createChooser = Intent.createChooser(getIntent(), this.chooserTitle, intentSender);
        } else {
            createChooser = Intent.createChooser(getIntent(), this.chooserTitle);
        }
        createChooser.setFlags(Ints.MAX_POWER_OF_TWO);
        if (hasValidKey("showAppsToView", this.options)) {
            if (hasValidKey(ImagesContract.URL, this.options)) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setType(this.fileShare.getType());
                createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", getIntentsToViewFile(intent, this.fileShare.getURI()));
            }
        }
        String str = "excludedActivityTypes";
        if (!hasValidKey(str, this.options)) {
            currentActivity.startActivityForResult(createChooser, RNShareModule.SHARE_REQUEST_CODE);
        } else if (VERSION.SDK_INT >= 24) {
            createChooser.putExtra("android.intent.extra.EXCLUDE_COMPONENTS", this.options.getArray(str).toString());
            currentActivity.startActivityForResult(createChooser, RNShareModule.SHARE_REQUEST_CODE);
        } else {
            currentActivity.startActivityForResult(excludeChooserIntent(getIntent(), this.options), RNShareModule.SHARE_REQUEST_CODE);
        }
        if (intentSender == null) {
            TargetChosenReceiver.sendCallback(true, Boolean.valueOf(true), "OK");
        }
    }

    public static boolean isPackageInstalled(String str, Context context) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    protected Intent getIntent() {
        return this.intent;
    }

    protected void setIntent(Intent intent) {
        this.intent = intent;
    }

    public static boolean hasValidKey(String str, ReadableMap readableMap) {
        return (readableMap == null || !readableMap.hasKey(str) || readableMap.isNull(str)) ? false : true;
    }
}
