package com.reactnative.ivpusic.imagepicker;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.RNFetchBlob.RNFetchBlobConst;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.PromiseImpl;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.yalantis.ucrop.UCrop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

class PickerModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private static final int CAMERA_PICKER_REQUEST = 61111;
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_CALLBACK_ERROR = "E_CALLBACK_ERROR";
    private static final String E_CAMERA_IS_NOT_AVAILABLE = "E_CAMERA_IS_NOT_AVAILABLE";
    private static final String E_CANNOT_LAUNCH_CAMERA = "E_CANNOT_LAUNCH_CAMERA";
    private static final String E_ERROR_WHILE_CLEANING_FILES = "E_ERROR_WHILE_CLEANING_FILES";
    private static final String E_FAILED_TO_OPEN_CAMERA = "E_FAILED_TO_OPEN_CAMERA";
    private static final String E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";
    private static final String E_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND";
    private static final String E_PERMISSIONS_MISSING = "E_PERMISSION_MISSING";
    private static final String E_PICKER_CANCELLED_KEY = "E_PICKER_CANCELLED";
    private static final String E_PICKER_CANCELLED_MSG = "User cancelled image selection";
    private static final int IMAGE_PICKER_REQUEST = 61110;
    private final String DEFAULT_TINT;
    private final String DEFAULT_WIDGET_COLOR;
    private Compression compression;
    private String cropperActiveWidgetColor;
    private boolean cropperCircleOverlay = false;
    private String cropperStatusBarColor;
    private String cropperToolbarColor;
    private String cropperToolbarTitle;
    private boolean cropping = false;
    private boolean disableCropperColorSetters = false;
    private boolean enableRotationGesture = false;
    private boolean freeStyleCropEnabled = false;
    private int height;
    private boolean hideBottomControls = false;
    private boolean includeBase64 = false;
    private boolean includeExif = false;
    private Uri mCameraCaptureURI;
    private String mCurrentMediaPath;
    private String mediaType = "any";
    private boolean multiple = false;
    private ReadableMap options;
    private ReactApplicationContext reactContext;
    private ResultCollector resultCollector;
    private boolean showCropFrame = true;
    private boolean showCropGuidelines = true;
    private boolean useFrontCamera = false;
    private int width;

    public String getName() {
        return "ImageCropPicker";
    }

    public void onNewIntent(Intent intent) {
    }

    PickerModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        String str = "#424242";
        this.DEFAULT_TINT = str;
        this.cropperActiveWidgetColor = str;
        this.cropperStatusBarColor = str;
        this.cropperToolbarColor = str;
        this.cropperToolbarTitle = null;
        this.DEFAULT_WIDGET_COLOR = "#03A9F4";
        this.width = 0;
        this.height = 0;
        this.resultCollector = new ResultCollector();
        this.compression = new Compression();
        reactApplicationContext.addActivityEventListener(this);
        this.reactContext = reactApplicationContext;
    }

    private String getTmpDir(Activity activity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(activity.getCacheDir());
        stringBuilder.append("/react-native-image-crop-picker");
        String stringBuilder2 = stringBuilder.toString();
        new File(stringBuilder2).mkdir();
        return stringBuilder2;
    }

    private void setConfiguration(ReadableMap readableMap) {
        String str = "mediaType";
        this.mediaType = readableMap.hasKey(str) ? readableMap.getString(str) : "any";
        str = "multiple";
        boolean z = true;
        boolean z2 = readableMap.hasKey(str) && readableMap.getBoolean(str);
        this.multiple = z2;
        str = "includeBase64";
        z2 = readableMap.hasKey(str) && readableMap.getBoolean(str);
        this.includeBase64 = z2;
        str = "includeExif";
        z2 = readableMap.hasKey(str) && readableMap.getBoolean(str);
        this.includeExif = z2;
        str = "width";
        this.width = readableMap.hasKey(str) ? readableMap.getInt(str) : 0;
        str = "height";
        this.height = readableMap.hasKey(str) ? readableMap.getInt(str) : 0;
        str = "cropping";
        z2 = readableMap.hasKey(str) && readableMap.getBoolean(str);
        this.cropping = z2;
        str = "cropperActiveWidgetColor";
        String str2 = "#424242";
        this.cropperActiveWidgetColor = readableMap.hasKey(str) ? readableMap.getString(str) : str2;
        str = "cropperStatusBarColor";
        this.cropperStatusBarColor = readableMap.hasKey(str) ? readableMap.getString(str) : str2;
        str = "cropperToolbarColor";
        if (readableMap.hasKey(str)) {
            str2 = readableMap.getString(str);
        }
        this.cropperToolbarColor = str2;
        str = "cropperToolbarTitle";
        this.cropperToolbarTitle = readableMap.hasKey(str) ? readableMap.getString(str) : null;
        str = "cropperCircleOverlay";
        z2 = readableMap.hasKey(str) && readableMap.getBoolean(str);
        this.cropperCircleOverlay = z2;
        z2 = readableMap.hasKey("freeStyleCropEnabled") && readableMap.getBoolean("freeStyleCropEnabled");
        this.freeStyleCropEnabled = z2;
        z2 = !readableMap.hasKey("showCropGuidelines") || readableMap.getBoolean("showCropGuidelines");
        this.showCropGuidelines = z2;
        z2 = !readableMap.hasKey("showCropFrame") || readableMap.getBoolean("showCropFrame");
        this.showCropFrame = z2;
        z2 = readableMap.hasKey("hideBottomControls") && readableMap.getBoolean("hideBottomControls");
        this.hideBottomControls = z2;
        z2 = readableMap.hasKey("enableRotationGesture") && readableMap.getBoolean("enableRotationGesture");
        this.enableRotationGesture = z2;
        z2 = readableMap.hasKey("disableCropperColorSetters") && readableMap.getBoolean("disableCropperColorSetters");
        this.disableCropperColorSetters = z2;
        if (!(readableMap.hasKey("useFrontCamera") && readableMap.getBoolean("useFrontCamera"))) {
            z = false;
        }
        this.useFrontCamera = z;
        this.options = readableMap;
    }

    private void deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File deleteRecursive : file.listFiles()) {
                deleteRecursive(deleteRecursive);
            }
        }
        file.delete();
    }

    @ReactMethod
    public void clean(final Promise promise) {
        final Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
        } else {
            permissionsCheck(currentActivity, promise, Collections.singletonList("android.permission.WRITE_EXTERNAL_STORAGE"), new Callable<Void>() {
                public Void call() {
                    try {
                        File file = new File(this.getTmpDir(currentActivity));
                        if (file.exists()) {
                            this.deleteRecursive(file);
                            promise.resolve(null);
                            return null;
                        }
                        throw new Exception("File does not exist");
                    } catch (Exception e) {
                        e.printStackTrace();
                        promise.reject(PickerModule.E_ERROR_WHILE_CLEANING_FILES, e.getMessage());
                    }
                }
            });
        }
    }

    @ReactMethod
    public void cleanSingle(final String str, final Promise promise) {
        if (str == null) {
            promise.reject(E_ERROR_WHILE_CLEANING_FILES, "Cannot cleanup empty path");
            return;
        }
        Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
        } else {
            permissionsCheck(currentActivity, promise, Collections.singletonList("android.permission.WRITE_EXTERNAL_STORAGE"), new Callable<Void>() {
                public Void call() throws Exception {
                    try {
                        String str = str;
                        if (str.startsWith("file://")) {
                            str = str.substring(7);
                        }
                        File file = new File(str);
                        if (file.exists()) {
                            this.deleteRecursive(file);
                            promise.resolve(null);
                            return null;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("File does not exist. Path: ");
                        stringBuilder.append(str);
                        throw new Exception(stringBuilder.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        promise.reject(PickerModule.E_ERROR_WHILE_CLEANING_FILES, e.getMessage());
                    }
                }
            });
        }
    }

    private void permissionsCheck(Activity activity, final Promise promise, List<String> list, final Callable<Void> callable) {
        List arrayList = new ArrayList();
        for (String str : list) {
            if (ContextCompat.checkSelfPermission(activity, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            try {
                callable.call();
            } catch (Throwable e) {
                promise.reject(E_CALLBACK_ERROR, "Unknown error", e);
            }
            return;
        }
        ((PermissionAwareActivity) activity).requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 1, new PermissionListener() {
            public boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
                if (i == 1) {
                    for (int i2 : iArr) {
                        if (i2 == -1) {
                            promise.reject(PickerModule.E_PERMISSIONS_MISSING, "Required permission missing");
                            return true;
                        }
                    }
                    try {
                        callable.call();
                    } catch (Throwable e) {
                        promise.reject(PickerModule.E_CALLBACK_ERROR, "Unknown error", e);
                    }
                }
                return true;
            }
        });
    }

    @ReactMethod
    public void openCamera(ReadableMap readableMap, Promise promise) {
        final Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
        } else if (isCameraAvailable(currentActivity)) {
            setConfiguration(readableMap);
            this.resultCollector.setup(promise, false);
            permissionsCheck(currentActivity, promise, Arrays.asList(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"}), new Callable<Void>() {
                public Void call() {
                    PickerModule.this.initiateCamera(currentActivity);
                    return null;
                }
            });
        } else {
            promise.reject(E_CAMERA_IS_NOT_AVAILABLE, "Camera not available");
        }
    }

    private void initiateCamera(Activity activity) {
        try {
            String str;
            File createVideoFile;
            if (this.mediaType.equals("video")) {
                str = "android.media.action.VIDEO_CAPTURE";
                createVideoFile = createVideoFile();
            } else {
                str = "android.media.action.IMAGE_CAPTURE";
                createVideoFile = createImageFile();
            }
            Intent intent = new Intent(str);
            if (VERSION.SDK_INT < 21) {
                this.mCameraCaptureURI = Uri.fromFile(createVideoFile);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(activity.getApplicationContext().getPackageName());
                stringBuilder.append(".provider");
                this.mCameraCaptureURI = FileProvider.getUriForFile(activity, stringBuilder.toString(), createVideoFile);
            }
            intent.putExtra("output", this.mCameraCaptureURI);
            if (this.useFrontCamera) {
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            }
            if (intent.resolveActivity(activity.getPackageManager()) == null) {
                this.resultCollector.notifyProblem(E_CANNOT_LAUNCH_CAMERA, "Cannot launch camera");
            } else {
                activity.startActivityForResult(intent, CAMERA_PICKER_REQUEST);
            }
        } catch (Throwable e) {
            this.resultCollector.notifyProblem(E_FAILED_TO_OPEN_CAMERA, e);
        }
    }

    private void initiatePicker(Activity activity) {
        try {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            String str = "image/*";
            if (!this.cropping) {
                if (!this.mediaType.equals("photo")) {
                    String str2 = "video/*";
                    if (this.mediaType.equals("video")) {
                        intent.setType(str2);
                    } else {
                        intent.setType("*/*");
                        intent.putExtra("android.intent.extra.MIME_TYPES", new String[]{str, str2});
                    }
                    intent.setFlags(67108864);
                    intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", this.multiple);
                    intent.addCategory("android.intent.category.OPENABLE");
                    activity.startActivityForResult(Intent.createChooser(intent, "Pick an image"), IMAGE_PICKER_REQUEST);
                }
            }
            intent.setType(str);
            intent.setFlags(67108864);
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", this.multiple);
            intent.addCategory("android.intent.category.OPENABLE");
            activity.startActivityForResult(Intent.createChooser(intent, "Pick an image"), IMAGE_PICKER_REQUEST);
        } catch (Throwable e) {
            this.resultCollector.notifyProblem(E_FAILED_TO_SHOW_PICKER, e);
        }
    }

    @ReactMethod
    public void openPicker(ReadableMap readableMap, Promise promise) {
        final Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }
        setConfiguration(readableMap);
        this.resultCollector.setup(promise, this.multiple);
        permissionsCheck(currentActivity, promise, Collections.singletonList("android.permission.WRITE_EXTERNAL_STORAGE"), new Callable<Void>() {
            public Void call() {
                PickerModule.this.initiatePicker(currentActivity);
                return null;
            }
        });
    }

    @ReactMethod
    public void openCropper(ReadableMap readableMap, Promise promise) {
        final Activity currentActivity = access$700();
        if (currentActivity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }
        setConfiguration(readableMap);
        this.resultCollector.setup(promise, false);
        final Uri parse = Uri.parse(readableMap.getString(RNFetchBlobConst.RNFB_RESPONSE_PATH));
        permissionsCheck(currentActivity, promise, Collections.singletonList("android.permission.WRITE_EXTERNAL_STORAGE"), new Callable<Void>() {
            public Void call() {
                PickerModule.this.startCropping(currentActivity, parse);
                return null;
            }
        });
    }

    private String getBase64StringFromFile(String str) {
        try {
            InputStream fileInputStream = new FileInputStream(new File(str));
            byte[] bArr = new byte[8192];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                try {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String getMimeType(String str) {
        Uri fromFile = Uri.fromFile(new File(str));
        if (fromFile.getScheme().equals("content")) {
            return this.reactContext.getContentResolver().getType(fromFile);
        }
        str = MimeTypeMap.getFileExtensionFromUrl(fromFile.toString());
        return str != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.toLowerCase()) : null;
    }

    private WritableMap getSelection(Activity activity, Uri uri, boolean z) throws Exception {
        String resolveRealPath = resolveRealPath(activity, uri, z);
        if (resolveRealPath == null || resolveRealPath.isEmpty()) {
            throw new Exception("Cannot resolve asset path.");
        }
        String mimeType = getMimeType(resolveRealPath);
        if (mimeType == null || !mimeType.startsWith("video/")) {
            return getImage(activity, resolveRealPath);
        }
        getVideo(activity, resolveRealPath, mimeType);
        return null;
    }

    private void getAsyncSelection(Activity activity, Uri uri, boolean z) throws Exception {
        String resolveRealPath = resolveRealPath(activity, uri, z);
        if (resolveRealPath == null || resolveRealPath.isEmpty()) {
            this.resultCollector.notifyProblem(E_NO_IMAGE_DATA_FOUND, "Cannot resolve asset path.");
            return;
        }
        String mimeType = getMimeType(resolveRealPath);
        if (mimeType == null || !mimeType.startsWith("video/")) {
            this.resultCollector.notifySuccess(getImage(activity, resolveRealPath));
        } else {
            getVideo(activity, resolveRealPath, mimeType);
        }
    }

    private Bitmap validateVideo(String str) throws Exception {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime();
        if (frameAtTime != null) {
            return frameAtTime;
        }
        throw new Exception("Cannot retrieve video data");
    }

    private void getVideo(Activity activity, String str, String str2) throws Exception {
        validateVideo(str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTmpDir(activity));
        stringBuilder.append("/");
        stringBuilder.append(UUID.randomUUID().toString());
        stringBuilder.append(".mp4");
        final String stringBuilder2 = stringBuilder.toString();
        final Activity activity2 = activity;
        final String str3 = str;
        final String str4 = str2;
        new Thread(new Runnable() {
            public void run() {
                PickerModule.this.compression.compressVideo(activity2, PickerModule.this.options, str3, stringBuilder2, new PromiseImpl(new Callback() {
                    public void invoke(Object... objArr) {
                        String str = (String) objArr[0];
                        try {
                            Bitmap access$600 = PickerModule.this.validateVideo(str);
                            long lastModified = new File(str).lastModified();
                            WritableMap writableNativeMap = new WritableNativeMap();
                            writableNativeMap.putInt("width", access$600.getWidth());
                            writableNativeMap.putInt("height", access$600.getHeight());
                            writableNativeMap.putString("mime", str4);
                            writableNativeMap.putInt("size", (int) new File(str).length());
                            String str2 = RNFetchBlobConst.RNFB_RESPONSE_PATH;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("file://");
                            stringBuilder.append(str);
                            writableNativeMap.putString(str2, stringBuilder.toString());
                            writableNativeMap.putString("modificationDate", String.valueOf(lastModified));
                            PickerModule.this.resultCollector.notifySuccess(writableNativeMap);
                        } catch (Throwable e) {
                            PickerModule.this.resultCollector.notifyProblem(PickerModule.E_NO_IMAGE_DATA_FOUND, e);
                        }
                    }
                }, new Callback() {
                    public void invoke(Object... objArr) {
                        WritableNativeMap writableNativeMap = (WritableNativeMap) objArr[0];
                        PickerModule.this.resultCollector.notifyProblem(writableNativeMap.getString("code"), writableNativeMap.getString("message"));
                    }
                }));
            }
        }).run();
    }

    private String resolveRealPath(Activity activity, Uri uri, boolean z) throws IOException {
        if (VERSION.SDK_INT < 21) {
            return RealPathUtil.getRealPathFromURI(activity, uri);
        }
        if (z) {
            return Uri.parse(this.mCurrentMediaPath).getPath();
        }
        return RealPathUtil.getRealPathFromURI(activity, uri);
    }

    private Options validateImage(String str) throws Exception {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Config.RGB_565;
        options.inDither = true;
        BitmapFactory.decodeFile(str, options);
        if (options.outMimeType != null && options.outWidth != 0 && options.outHeight != 0) {
            return options;
        }
        throw new Exception("Invalid image selected");
    }

    private WritableMap getImage(Activity activity, String str) throws Exception {
        WritableMap writableNativeMap = new WritableNativeMap();
        if (str.startsWith("http://") || str.startsWith("https://")) {
            throw new Exception("Cannot select remote files");
        }
        String path = this.compression.compressImage(this.options, str, validateImage(str)).getPath();
        Options validateImage = validateImage(path);
        long lastModified = new File(str).lastModified();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("file://");
        stringBuilder.append(path);
        writableNativeMap.putString(RNFetchBlobConst.RNFB_RESPONSE_PATH, stringBuilder.toString());
        writableNativeMap.putInt("width", validateImage.outWidth);
        writableNativeMap.putInt("height", validateImage.outHeight);
        writableNativeMap.putString("mime", validateImage.outMimeType);
        writableNativeMap.putInt("size", (int) new File(path).length());
        writableNativeMap.putString("modificationDate", String.valueOf(lastModified));
        if (this.includeBase64) {
            writableNativeMap.putString("data", getBase64StringFromFile(path));
        }
        if (this.includeExif) {
            try {
                writableNativeMap.putMap("exif", ExifExtractor.extract(str));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return writableNativeMap;
    }

    private void configureCropperColors(UCrop.Options options) {
        int parseColor = Color.parseColor(this.cropperActiveWidgetColor);
        int parseColor2 = Color.parseColor(this.cropperToolbarColor);
        int parseColor3 = Color.parseColor(this.cropperStatusBarColor);
        options.setToolbarColor(parseColor2);
        options.setStatusBarColor(parseColor3);
        if (parseColor == Color.parseColor("#424242")) {
            options.setActiveWidgetColor(Color.parseColor("#03A9F4"));
        } else {
            options.setActiveWidgetColor(parseColor);
        }
    }

    private void startCropping(Activity activity, Uri uri) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(CompressFormat.JPEG);
        options.setCompressionQuality(100);
        options.setCircleDimmedLayer(this.cropperCircleOverlay);
        options.setFreeStyleCropEnabled(this.freeStyleCropEnabled);
        options.setShowCropGrid(this.showCropGuidelines);
        options.setShowCropFrame(this.showCropFrame);
        options.setHideBottomControls(this.hideBottomControls);
        String str = this.cropperToolbarTitle;
        if (str != null) {
            options.setToolbarTitle(str);
        }
        if (this.enableRotationGesture) {
            options.setAllowedGestures(3, 3, 3);
        }
        if (!this.disableCropperColorSetters) {
            configureCropperColors(options);
        }
        String tmpDir = getTmpDir(activity);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UUID.randomUUID().toString());
        stringBuilder.append(".jpg");
        UCrop withOptions = UCrop.of(uri, Uri.fromFile(new File(tmpDir, stringBuilder.toString()))).withOptions(options);
        int i = this.width;
        if (i > 0) {
            int i2 = this.height;
            if (i2 > 0) {
                withOptions.withAspectRatio((float) i, (float) i2);
            }
        }
        withOptions.start(activity);
    }

    private void imagePickerResult(Activity activity, int i, int i2, Intent intent) {
        if (i2 == 0) {
            this.resultCollector.notifyProblem(E_PICKER_CANCELLED_KEY, E_PICKER_CANCELLED_MSG);
        } else if (i2 == -1) {
            boolean z = this.multiple;
            String str = E_NO_IMAGE_DATA_FOUND;
            if (z) {
                ClipData clipData = intent.getClipData();
                if (clipData == null) {
                    try {
                        this.resultCollector.setWaitCount(1);
                        getAsyncSelection(activity, intent.getData(), false);
                    } catch (Exception e) {
                        this.resultCollector.notifyProblem(str, e.getMessage());
                    }
                } else {
                    this.resultCollector.setWaitCount(clipData.getItemCount());
                    for (int i3 = 0; i3 < clipData.getItemCount(); i3++) {
                        getAsyncSelection(activity, clipData.getItemAt(i3).getUri(), false);
                    }
                }
            } else {
                Uri data = intent.getData();
                if (data == null) {
                    this.resultCollector.notifyProblem(str, "Cannot resolve image url");
                } else if (this.cropping) {
                    startCropping(activity, data);
                } else {
                    try {
                        getAsyncSelection(activity, data, false);
                    } catch (Exception e2) {
                        this.resultCollector.notifyProblem(str, e2.getMessage());
                    }
                }
            }
        }
    }

    private void cameraPickerResult(Activity activity, int i, int i2, Intent intent) {
        if (i2 == 0) {
            this.resultCollector.notifyProblem(E_PICKER_CANCELLED_KEY, E_PICKER_CANCELLED_MSG);
        } else if (i2 == -1) {
            Uri uri = this.mCameraCaptureURI;
            String str = E_NO_IMAGE_DATA_FOUND;
            if (uri == null) {
                this.resultCollector.notifyProblem(str, "Cannot resolve image url");
            } else if (this.cropping) {
                new UCrop.Options().setCompressionFormat(CompressFormat.JPEG);
                startCropping(activity, uri);
            } else {
                try {
                    this.resultCollector.setWaitCount(1);
                    WritableMap selection = getSelection(activity, uri, true);
                    if (selection != null) {
                        this.resultCollector.notifySuccess(selection);
                    }
                } catch (Exception e) {
                    this.resultCollector.notifyProblem(str, e.getMessage());
                }
            }
        }
    }

    private void croppingResult(Activity activity, int i, int i2, Intent intent) {
        if (intent != null) {
            Uri output = UCrop.getOutput(intent);
            String str = E_NO_IMAGE_DATA_FOUND;
            if (output != null) {
                try {
                    if (this.width > 0 && this.height > 0) {
                        output = Uri.fromFile(this.compression.resize(output.getPath(), this.width, this.height, 100));
                    }
                    WritableMap selection = getSelection(activity, output, false);
                    if (selection != null) {
                        selection.putMap("cropRect", getCroppedRectMap(intent));
                        this.resultCollector.setWaitCount(1);
                        this.resultCollector.notifySuccess(selection);
                        return;
                    }
                    throw new Exception("Cannot crop video files");
                } catch (Exception e) {
                    this.resultCollector.notifyProblem(str, e.getMessage());
                    return;
                }
            }
            this.resultCollector.notifyProblem(str, "Cannot find image data");
            return;
        }
        this.resultCollector.notifyProblem(E_PICKER_CANCELLED_KEY, E_PICKER_CANCELLED_MSG);
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        if (i == IMAGE_PICKER_REQUEST) {
            imagePickerResult(activity, i, i2, intent);
        } else if (i == CAMERA_PICKER_REQUEST) {
            cameraPickerResult(activity, i, i2, intent);
        } else if (i == 69) {
            croppingResult(activity, i, i2, intent);
        }
    }

    private boolean isCameraAvailable(Activity activity) {
        return activity.getPackageManager().hasSystemFeature("android.hardware.camera") || activity.getPackageManager().hasSystemFeature("android.hardware.camera.any");
    }

    private File createImageFile() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("image-");
        stringBuilder.append(UUID.randomUUID().toString());
        String stringBuilder2 = stringBuilder.toString();
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!(externalStoragePublicDirectory.exists() || externalStoragePublicDirectory.isDirectory())) {
            externalStoragePublicDirectory.mkdirs();
        }
        File createTempFile = File.createTempFile(stringBuilder2, ".jpg", externalStoragePublicDirectory);
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("file:");
        stringBuilder3.append(createTempFile.getAbsolutePath());
        this.mCurrentMediaPath = stringBuilder3.toString();
        return createTempFile;
    }

    private File createVideoFile() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("video-");
        stringBuilder.append(UUID.randomUUID().toString());
        String stringBuilder2 = stringBuilder.toString();
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!(externalStoragePublicDirectory.exists() || externalStoragePublicDirectory.isDirectory())) {
            externalStoragePublicDirectory.mkdirs();
        }
        File createTempFile = File.createTempFile(stringBuilder2, ".mp4", externalStoragePublicDirectory);
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("file:");
        stringBuilder3.append(createTempFile.getAbsolutePath());
        this.mCurrentMediaPath = stringBuilder3.toString();
        return createTempFile;
    }

    private static WritableMap getCroppedRectMap(Intent intent) {
        WritableMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putInt("x", intent.getIntExtra(UCrop.EXTRA_OUTPUT_OFFSET_X, -1));
        writableNativeMap.putInt("y", intent.getIntExtra(UCrop.EXTRA_OUTPUT_OFFSET_Y, -1));
        writableNativeMap.putInt("width", intent.getIntExtra(UCrop.EXTRA_OUTPUT_IMAGE_WIDTH, -1));
        writableNativeMap.putInt("height", intent.getIntExtra(UCrop.EXTRA_OUTPUT_IMAGE_HEIGHT, -1));
        return writableNativeMap;
    }
}
