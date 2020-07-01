package com.RNFetchBlob;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.NonNull;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;
import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

class RNFetchBlobBody extends RequestBody {
    private File bodyCache;
    private Boolean chunkedEncoding = Boolean.valueOf(false);
    private long contentLength = 0;
    private ReadableArray form;
    private String mTaskId;
    private MediaType mime;
    private String rawBody;
    int reported = 0;
    private InputStream requestStream;
    private RequestType requestType;

    /* renamed from: com.RNFetchBlob.RNFetchBlobBody$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType = new int[RequestType.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:6:?, code:
            $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType[com.RNFetchBlob.RNFetchBlobReq.RequestType.Others.ordinal()] = 3;
     */
        static {
            /*
            r0 = com.RNFetchBlob.RNFetchBlobReq.RequestType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType = r0;
            r0 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.RNFetchBlob.RNFetchBlobReq.RequestType.SingleFile;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.RNFetchBlob.RNFetchBlobReq.RequestType.AsIs;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.RNFetchBlob.RNFetchBlobReq.RequestType.Others;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobBody.1.<clinit>():void");
        }
    }

    private class FormField {
        public String data;
        String filename;
        String mime;
        public String name;

        FormField(ReadableMap readableMap) {
            String str = ConditionalUserProperty.NAME;
            if (readableMap.hasKey(str)) {
                this.name = readableMap.getString(str);
            }
            str = "filename";
            if (readableMap.hasKey(str)) {
                this.filename = readableMap.getString(str);
            }
            str = "type";
            if (readableMap.hasKey(str)) {
                this.mime = readableMap.getString(str);
            } else {
                this.mime = this.filename == null ? "text/plain" : "application/octet-stream";
            }
            str = "data";
            if (readableMap.hasKey(str)) {
                this.data = readableMap.getString(str);
            }
        }
    }

    RNFetchBlobBody(String str) {
        this.mTaskId = str;
    }

    RNFetchBlobBody chunkedEncoding(boolean z) {
        this.chunkedEncoding = Boolean.valueOf(z);
        return this;
    }

    RNFetchBlobBody setMIME(MediaType mediaType) {
        this.mime = mediaType;
        return this;
    }

    RNFetchBlobBody setRequestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    RNFetchBlobBody setBody(String str) {
        this.rawBody = str;
        if (this.rawBody == null) {
            this.rawBody = "";
            this.requestType = RequestType.AsIs;
        }
        try {
            int i = AnonymousClass1.$SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType[this.requestType.ordinal()];
            if (i == 1) {
                this.requestStream = getRequestStream();
                this.contentLength = (long) this.requestStream.available();
            } else if (i == 2) {
                this.contentLength = (long) this.rawBody.getBytes().length;
                this.requestStream = new ByteArrayInputStream(this.rawBody.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RNFetchBlob failed to create single content request body :");
            stringBuilder.append(e.getLocalizedMessage());
            stringBuilder.append("\r\n");
            RNFetchBlobUtils.emitWarningEvent(stringBuilder.toString());
        }
        return this;
    }

    RNFetchBlobBody setBody(ReadableArray readableArray) {
        this.form = readableArray;
        try {
            this.bodyCache = createMultipartBodyCache();
            this.requestStream = new FileInputStream(this.bodyCache);
            this.contentLength = this.bodyCache.length();
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RNFetchBlob failed to create request multipart body :");
            stringBuilder.append(e.getLocalizedMessage());
            RNFetchBlobUtils.emitWarningEvent(stringBuilder.toString());
        }
        return this;
    }

    public long contentLength() {
        return this.chunkedEncoding.booleanValue() ? -1 : this.contentLength;
    }

    public MediaType contentType() {
        return this.mime;
    }

    public void writeTo(@NonNull BufferedSink bufferedSink) {
        try {
            pipeStreamToSink(this.requestStream, bufferedSink);
        } catch (Exception e) {
            RNFetchBlobUtils.emitWarningEvent(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    boolean clearRequestBody() {
        try {
            if (this.bodyCache != null && this.bodyCache.exists()) {
                this.bodyCache.delete();
            }
            return true;
        } catch (Exception e) {
            RNFetchBlobUtils.emitWarningEvent(e.getLocalizedMessage());
            return false;
        }
    }

    private InputStream getRequestStream() throws Exception {
        StringBuilder stringBuilder;
        String str = "error when getting request stream: ";
        String normalizePath;
        if (this.rawBody.startsWith(RNFetchBlobConst.FILE_PREFIX)) {
            normalizePath = RNFetchBlobFS.normalizePath(this.rawBody.substring(19));
            if (RNFetchBlobFS.isAsset(normalizePath)) {
                try {
                    return RNFetchBlob.RCTContext.getAssets().open(normalizePath.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, ""));
                } catch (Exception e) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("error when getting request stream from asset : ");
                    stringBuilder2.append(e.getLocalizedMessage());
                    throw new Exception(stringBuilder2.toString());
                }
            }
            File file = new File(RNFetchBlobFS.normalizePath(normalizePath));
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                return new FileInputStream(file);
            } catch (Exception e2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(e2.getLocalizedMessage());
                throw new Exception(stringBuilder.toString());
            }
        } else if (this.rawBody.startsWith(RNFetchBlobConst.CONTENT_PREFIX)) {
            normalizePath = this.rawBody.substring(22);
            try {
                normalizePath = RNFetchBlob.RCTContext.getContentResolver().openInputStream(Uri.parse(normalizePath));
                return normalizePath;
            } catch (Throwable e3) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("error when getting request stream for content URI: ");
                stringBuilder.append(normalizePath);
                throw new Exception(stringBuilder.toString(), e3);
            }
        } else {
            try {
                return new ByteArrayInputStream(Base64.decode(this.rawBody, 0));
            } catch (Exception e22) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(e22.getLocalizedMessage());
                throw new Exception(stringBuilder.toString());
            }
        }
    }

    /* JADX WARNING: Missing block: B:26:0x0148, code:
            if (r10 != null) goto L_0x014a;
     */
    /* JADX WARNING: Missing block: B:27:0x014a, code:
            r10.close();
     */
    /* JADX WARNING: Missing block: B:32:0x016f, code:
            if (r10 == null) goto L_0x01bd;
     */
    private java.io.File createMultipartBodyCache() throws java.io.IOException {
        /*
        r17 = this;
        r1 = r17;
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r2 = "RNFetchBlob-";
        r0.append(r2);
        r2 = r1.mTaskId;
        r0.append(r2);
        r2 = r0.toString();
        r0 = com.RNFetchBlob.RNFetchBlob.RCTContext;
        r0 = r0.getCacheDir();
        r3 = "";
        r4 = "rnfb-form-tmp";
        r4 = java.io.File.createTempFile(r4, r3, r0);
        r5 = new java.io.FileOutputStream;
        r5.<init>(r4);
        r0 = r17.countFormDataLength();
        r6 = com.RNFetchBlob.RNFetchBlob.RCTContext;
        r7 = r0.iterator();
    L_0x0032:
        r0 = r7.hasNext();
        r8 = "--";
        if (r0 == 0) goto L_0x01c8;
    L_0x003a:
        r0 = r7.next();
        r0 = (com.RNFetchBlob.RNFetchBlobBody.FormField) r0;
        r9 = r0.data;
        r10 = r0.name;
        if (r10 == 0) goto L_0x0032;
    L_0x0046:
        if (r9 != 0) goto L_0x0049;
    L_0x0048:
        goto L_0x0032;
    L_0x0049:
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r11.append(r8);
        r11.append(r2);
        r8 = "\r\n";
        r11.append(r8);
        r11 = r11.toString();
        r12 = r0.filename;
        r13 = "\r\n\r\n";
        r14 = "Content-Type: ";
        r15 = "\"\r\n";
        r16 = r7;
        r7 = "Content-Disposition: form-data; name=\"";
        if (r12 == 0) goto L_0x0181;
    L_0x006b:
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r12.append(r11);
        r12.append(r7);
        r12.append(r10);
        r7 = "\"; filename=\"";
        r12.append(r7);
        r7 = r0.filename;
        r12.append(r7);
        r12.append(r15);
        r7 = r12.toString();
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r10.append(r7);
        r10.append(r14);
        r0 = r0.mime;
        r10.append(r0);
        r10.append(r13);
        r0 = r10.toString();
        r0 = r0.getBytes();
        r5.write(r0);
        r0 = "RNFetchBlob-file://";
        r0 = r9.startsWith(r0);
        r7 = ", ";
        if (r0 == 0) goto L_0x012a;
    L_0x00b2:
        r0 = 19;
        r0 = r9.substring(r0);
        r9 = com.RNFetchBlob.RNFetchBlobFS.normalizePath(r0);
        r0 = com.RNFetchBlob.RNFetchBlobFS.isAsset(r9);
        if (r0 == 0) goto L_0x00f6;
    L_0x00c2:
        r0 = "bundle-assets://";
        r0 = r9.replace(r0, r3);	 Catch:{ IOException -> 0x00d5 }
        r10 = r6.getAssets();	 Catch:{ IOException -> 0x00d5 }
        r0 = r10.open(r0);	 Catch:{ IOException -> 0x00d5 }
        r1.pipeStreamToFileStream(r0, r5);	 Catch:{ IOException -> 0x00d5 }
        goto L_0x01bd;
    L_0x00d5:
        r0 = move-exception;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Failed to create form data asset :";
        r10.append(r11);
        r10.append(r9);
        r10.append(r7);
        r0 = r0.getLocalizedMessage();
        r10.append(r0);
        r0 = r10.toString();
        com.RNFetchBlob.RNFetchBlobUtils.emitWarningEvent(r0);
        goto L_0x01bd;
    L_0x00f6:
        r0 = new java.io.File;
        r7 = com.RNFetchBlob.RNFetchBlobFS.normalizePath(r9);
        r0.<init>(r7);
        r7 = r0.exists();
        if (r7 == 0) goto L_0x010f;
    L_0x0105:
        r7 = new java.io.FileInputStream;
        r7.<init>(r0);
        r1.pipeStreamToFileStream(r7, r5);
        goto L_0x01bd;
    L_0x010f:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r7 = "Failed to create form data from path :";
        r0.append(r7);
        r0.append(r9);
        r7 = ", file not exists.";
        r0.append(r7);
        r0 = r0.toString();
        com.RNFetchBlob.RNFetchBlobUtils.emitWarningEvent(r0);
        goto L_0x01bd;
    L_0x012a:
        r0 = "RNFetchBlob-content://";
        r0 = r9.startsWith(r0);
        if (r0 == 0) goto L_0x0178;
    L_0x0132:
        r0 = 22;
        r9 = r9.substring(r0);
        r10 = 0;
        r0 = r6.getContentResolver();	 Catch:{ Exception -> 0x0150 }
        r11 = android.net.Uri.parse(r9);	 Catch:{ Exception -> 0x0150 }
        r10 = r0.openInputStream(r11);	 Catch:{ Exception -> 0x0150 }
        r1.pipeStreamToFileStream(r10, r5);	 Catch:{ Exception -> 0x0150 }
        if (r10 == 0) goto L_0x01bd;
    L_0x014a:
        r10.close();
        goto L_0x01bd;
    L_0x014e:
        r0 = move-exception;
        goto L_0x0172;
    L_0x0150:
        r0 = move-exception;
        r11 = new java.lang.StringBuilder;	 Catch:{ all -> 0x014e }
        r11.<init>();	 Catch:{ all -> 0x014e }
        r12 = "Failed to create form data from content URI:";
        r11.append(r12);	 Catch:{ all -> 0x014e }
        r11.append(r9);	 Catch:{ all -> 0x014e }
        r11.append(r7);	 Catch:{ all -> 0x014e }
        r0 = r0.getLocalizedMessage();	 Catch:{ all -> 0x014e }
        r11.append(r0);	 Catch:{ all -> 0x014e }
        r0 = r11.toString();	 Catch:{ all -> 0x014e }
        com.RNFetchBlob.RNFetchBlobUtils.emitWarningEvent(r0);	 Catch:{ all -> 0x014e }
        if (r10 == 0) goto L_0x01bd;
    L_0x0171:
        goto L_0x014a;
    L_0x0172:
        if (r10 == 0) goto L_0x0177;
    L_0x0174:
        r10.close();
    L_0x0177:
        throw r0;
    L_0x0178:
        r0 = 0;
        r0 = android.util.Base64.decode(r9, r0);
        r5.write(r0);
        goto L_0x01bd;
    L_0x0181:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r9.append(r11);
        r9.append(r7);
        r9.append(r10);
        r9.append(r15);
        r7 = r9.toString();
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r9.append(r7);
        r9.append(r14);
        r7 = r0.mime;
        r9.append(r7);
        r9.append(r13);
        r7 = r9.toString();
        r7 = r7.getBytes();
        r5.write(r7);
        r0 = r0.data;
        r0 = r0.getBytes();
        r5.write(r0);
    L_0x01bd:
        r0 = r8.getBytes();
        r5.write(r0);
        r7 = r16;
        goto L_0x0032;
    L_0x01c8:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r0.append(r8);
        r0.append(r2);
        r2 = "--\r\n";
        r0.append(r2);
        r0 = r0.toString();
        r0 = r0.getBytes();
        r5.write(r0);
        r5.flush();
        r5.close();
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobBody.createMultipartBodyCache():java.io.File");
    }

    private void pipeStreamToSink(InputStream inputStream, BufferedSink bufferedSink) throws IOException {
        byte[] bArr = new byte[10240];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr, 0, 10240);
            if (read > 0) {
                bufferedSink.write(bArr, 0, read);
                j += (long) read;
                emitUploadProgress(j);
            } else {
                inputStream.close();
                return;
            }
        }
    }

    private void pipeStreamToFileStream(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        byte[] bArr = new byte[10240];
        while (true) {
            int read = inputStream.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                inputStream.close();
                return;
            }
        }
    }

    private ArrayList<FormField> countFormDataLength() throws IOException {
        ArrayList<FormField> arrayList = new ArrayList();
        ReactApplicationContext reactApplicationContext = RNFetchBlob.RCTContext;
        long j = 0;
        for (int i = 0; i < this.form.size(); i++) {
            FormField formField = new FormField(this.form.getMap(i));
            arrayList.add(formField);
            if (formField.data == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RNFetchBlob multipart request builder has found a field without `data` property, the field `");
                stringBuilder.append(formField.name);
                stringBuilder.append("` will be removed implicitly.");
                RNFetchBlobUtils.emitWarningEvent(stringBuilder.toString());
            } else {
                int available;
                long length;
                if (formField.filename != null) {
                    String str = formField.data;
                    if (str.startsWith(RNFetchBlobConst.FILE_PREFIX)) {
                        str = RNFetchBlobFS.normalizePath(str.substring(19));
                        if (RNFetchBlobFS.isAsset(str)) {
                            try {
                                available = reactApplicationContext.getAssets().open(str.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, "")).available();
                            } catch (IOException e) {
                                RNFetchBlobUtils.emitWarningEvent(e.getLocalizedMessage());
                            }
                        } else {
                            length = new File(RNFetchBlobFS.normalizePath(str)).length();
                            j += length;
                        }
                    } else if (str.startsWith(RNFetchBlobConst.CONTENT_PREFIX)) {
                        str = str.substring(22);
                        InputStream inputStream = null;
                        try {
                            inputStream = reactApplicationContext.getContentResolver().openInputStream(Uri.parse(str));
                            str = inputStream.available();
                            j += (long) str;
                            if (inputStream == null) {
                            }
                        } catch (Exception e2) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Failed to estimate form data length from content URI:");
                            stringBuilder2.append(str);
                            stringBuilder2.append(", ");
                            stringBuilder2.append(e2.getLocalizedMessage());
                            RNFetchBlobUtils.emitWarningEvent(stringBuilder2.toString());
                            if (inputStream == null) {
                            }
                        } catch (Throwable th) {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        }
                        inputStream.close();
                    } else {
                        available = Base64.decode(str, 0).length;
                    }
                } else {
                    available = formField.data.getBytes().length;
                }
                length = (long) available;
                j += length;
            }
        }
        this.contentLength = j;
        return arrayList;
    }

    private void emitUploadProgress(long j) {
        RNFetchBlobProgressConfig reportUploadProgress = RNFetchBlobReq.getReportUploadProgress(this.mTaskId);
        if (reportUploadProgress != null) {
            long j2 = this.contentLength;
            if (j2 != 0 && reportUploadProgress.shouldReport(((float) j) / ((float) j2))) {
                WritableMap createMap = Arguments.createMap();
                createMap.putString("taskId", this.mTaskId);
                createMap.putString("written", String.valueOf(j));
                createMap.putString("total", String.valueOf(this.contentLength));
                ((RCTDeviceEventEmitter) RNFetchBlob.RCTContext.getJSModule(RCTDeviceEventEmitter.class)).emit(RNFetchBlobConst.EVENT_UPLOAD_PROGRESS, createMap);
            }
        }
    }
}
