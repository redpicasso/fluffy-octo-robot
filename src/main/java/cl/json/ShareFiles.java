package cl.json;

import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ShareFiles {
    private ArrayList<String> filenames;
    private String intentType;
    private final ReactApplicationContext reactContext;
    private ArrayList<Uri> uris;

    public ShareFiles(ReadableArray readableArray, ArrayList<String> arrayList, String str, ReactApplicationContext reactApplicationContext) {
        this(readableArray, arrayList, reactApplicationContext);
        this.intentType = str;
    }

    public ShareFiles(ReadableArray readableArray, ArrayList<String> arrayList, ReactApplicationContext reactApplicationContext) {
        this.uris = new ArrayList();
        for (int i = 0; i < readableArray.size(); i++) {
            String string = readableArray.getString(i);
            if (string != null) {
                this.uris.add(Uri.parse(string));
            }
        }
        this.filenames = arrayList;
        this.reactContext = reactApplicationContext;
    }

    private String getMimeType(String str) {
        str = MimeTypeMap.getFileExtensionFromUrl(str);
        return str != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(str) : null;
    }

    public boolean isFile() {
        Iterator it = this.uris.iterator();
        boolean z = true;
        while (it.hasNext()) {
            Uri uri = (Uri) it.next();
            if (isBase64File(uri) || isLocalFile(uri)) {
                z = true;
                continue;
            } else {
                z = false;
                continue;
            }
            if (!z) {
                break;
            }
        }
        return z;
    }

    private boolean isBase64File(Uri uri) {
        if (uri.getScheme() == null || !uri.getScheme().equals("data")) {
            return false;
        }
        String substring = uri.getSchemeSpecificPart().substring(0, uri.getSchemeSpecificPart().indexOf(";"));
        String str = this.intentType;
        if (str == null) {
            this.intentType = substring;
        } else {
            if (!str.equalsIgnoreCase(substring)) {
                String str2 = "/";
                if (this.intentType.split(str2)[0].equalsIgnoreCase(substring.split(str2)[0])) {
                    this.intentType = this.intentType.split(str2)[0].concat("/*");
                }
            }
            if (!this.intentType.equalsIgnoreCase(substring)) {
                this.intentType = "*/*";
            }
        }
        return true;
    }

    private boolean isLocalFile(Uri uri) {
        if (uri.getScheme() == null || !uri.getScheme().equals("content")) {
            if (!UriUtil.LOCAL_FILE_SCHEME.equals(uri.getScheme())) {
                return false;
            }
        }
        String mimeType = getMimeType(uri.toString());
        if (mimeType == null) {
            mimeType = getMimeType(getRealPathFromURI(uri));
        }
        String str = "*/*";
        if (mimeType == null) {
            mimeType = str;
        }
        String str2 = this.intentType;
        if (str2 == null) {
            this.intentType = mimeType;
        } else {
            if (!str2.equalsIgnoreCase(mimeType)) {
                String str3 = "/";
                if (this.intentType.split(str3)[0].equalsIgnoreCase(mimeType.split(str3)[0])) {
                    this.intentType = this.intentType.split(str3)[0].concat("/*");
                }
            }
            if (!this.intentType.equalsIgnoreCase(mimeType)) {
                this.intentType = str;
            }
        }
        return true;
    }

    public String getType() {
        String str = this.intentType;
        return str == null ? "*/*" : str;
    }

    private String getRealPathFromURI(Uri uri) {
        return RNSharePathUtil.getRealPathFromURI(this.reactContext, uri);
    }

    public ArrayList<Uri> getURI() {
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        ArrayList<Uri> arrayList = new ArrayList();
        for (int i = 0; i < this.uris.size(); i++) {
            Uri uri = (Uri) this.uris.get(i);
            if (isBase64File(uri)) {
                String extensionFromMimeType = singleton.getExtensionFromMimeType(uri.getSchemeSpecificPart().substring(0, uri.getSchemeSpecificPart().indexOf(";")));
                String substring = uri.getSchemeSpecificPart().substring(uri.getSchemeSpecificPart().indexOf(";base64,") + 8);
                if (this.filenames.size() >= i + 1) {
                    extensionFromMimeType = (String) this.filenames.get(i);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(System.currentTimeMillis());
                    stringBuilder.append(".");
                    stringBuilder.append(extensionFromMimeType);
                    extensionFromMimeType = stringBuilder.toString();
                }
                try {
                    File file = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS);
                    if (file.exists() || file.mkdirs()) {
                        File file2 = new File(file, extensionFromMimeType);
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        fileOutputStream.write(Base64.decode(substring, 0));
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        arrayList.add(RNSharePathUtil.compatUriFromFile(this.reactContext, file2));
                    } else {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("mkdirs failed on ");
                        stringBuilder2.append(file.getAbsolutePath());
                        throw new IOException(stringBuilder2.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (isLocalFile(uri) && uri.getPath() != null) {
                if (this.filenames.size() >= i + 1) {
                    arrayList.add(RNSharePathUtil.compatUriFromFile(this.reactContext, new File(uri.getPath(), (String) this.filenames.get(i))));
                } else {
                    arrayList.add(RNSharePathUtil.compatUriFromFile(this.reactContext, new File(uri.getPath())));
                }
            }
        }
        return arrayList;
    }
}
