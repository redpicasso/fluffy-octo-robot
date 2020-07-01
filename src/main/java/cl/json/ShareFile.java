package cl.json;

import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.ReactApplicationContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareFile {
    private String filename;
    private final ReactApplicationContext reactContext;
    private String type;
    private Uri uri;
    private String url;

    public ShareFile(String str, String str2, String str3, ReactApplicationContext reactApplicationContext) {
        this(str, str3, reactApplicationContext);
        this.type = str2;
        this.filename = str3;
    }

    public ShareFile(String str, String str2, ReactApplicationContext reactApplicationContext) {
        this.url = str;
        this.uri = Uri.parse(this.url);
        this.reactContext = reactApplicationContext;
        this.filename = str2;
    }

    private String getMimeType(String str) {
        str = MimeTypeMap.getFileExtensionFromUrl(str);
        return str != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(str) : null;
    }

    public boolean isFile() {
        return isBase64File() || isLocalFile();
    }

    private boolean isBase64File() {
        if (this.uri.getScheme() == null || !this.uri.getScheme().equals("data")) {
            return false;
        }
        this.type = this.uri.getSchemeSpecificPart().substring(0, this.uri.getSchemeSpecificPart().indexOf(";"));
        return true;
    }

    private boolean isLocalFile() {
        if (this.uri.getScheme() == null || (!this.uri.getScheme().equals("content") && !this.uri.getScheme().equals(UriUtil.LOCAL_FILE_SCHEME))) {
            return false;
        }
        if (this.type != null) {
            return true;
        }
        this.type = getMimeType(this.uri.toString());
        if (this.type == null) {
            String realPathFromURI = getRealPathFromURI(this.uri);
            if (realPathFromURI == null) {
                return false;
            }
            this.type = getMimeType(realPathFromURI);
        }
        if (this.type == null) {
            this.type = "*/*";
        }
        return true;
    }

    public String getType() {
        String str = this.type;
        return str == null ? "*/*" : str;
    }

    private String getRealPathFromURI(Uri uri) {
        return RNSharePathUtil.getRealPathFromURI(this.reactContext, uri);
    }

    public Uri getURI() {
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(getType());
        if (isBase64File()) {
            String substring = this.uri.getSchemeSpecificPart().substring(this.uri.getSchemeSpecificPart().indexOf(";base64,") + 8);
            String str = this.filename;
            if (str == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(System.nanoTime());
                stringBuilder.append("");
                str = stringBuilder.toString();
            }
            try {
                File file = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS);
                if (file.exists() || file.mkdirs()) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str);
                    stringBuilder2.append(".");
                    stringBuilder2.append(extensionFromMimeType);
                    File file2 = new File(file, stringBuilder2.toString());
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    fileOutputStream.write(Base64.decode(substring, 0));
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return RNSharePathUtil.compatUriFromFile(this.reactContext, file2);
                }
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("mkdirs failed on ");
                stringBuilder3.append(file.getAbsolutePath());
                throw new IOException(stringBuilder3.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (isLocalFile()) {
                Uri parse = Uri.parse(this.url);
                if (parse.getPath() == null) {
                    return null;
                }
                return RNSharePathUtil.compatUriFromFile(this.reactContext, new File(parse.getPath()));
            }
            return null;
        }
    }
}
