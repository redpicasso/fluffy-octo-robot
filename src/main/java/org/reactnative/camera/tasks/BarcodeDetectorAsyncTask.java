package org.reactnative.camera.tasks;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ViewProps;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.Address;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.CalendarDateTime;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.Email;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.PersonName;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.Phone;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata.Builder;
import java.util.Iterator;
import java.util.List;
import org.reactnative.barcodedetector.BarcodeFormatUtils;
import org.reactnative.barcodedetector.RNBarcodeDetector;
import org.reactnative.camera.utils.ImageDimensions;

public class BarcodeDetectorAsyncTask extends AsyncTask<Void, Void, Void> {
    private String TAG = "RNCamera";
    private RNBarcodeDetector mBarcodeDetector;
    private BarcodeDetectorAsyncTaskDelegate mDelegate;
    private int mHeight;
    private byte[] mImageData;
    private ImageDimensions mImageDimensions;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mRotation;
    private double mScaleX;
    private double mScaleY;
    private int mWidth;

    private String getPhoneType(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "UNKNOWN" : "Mobile" : "Fax" : "Home" : "Work";
    }

    public BarcodeDetectorAsyncTask(BarcodeDetectorAsyncTaskDelegate barcodeDetectorAsyncTaskDelegate, RNBarcodeDetector rNBarcodeDetector, byte[] bArr, int i, int i2, int i3, float f, int i4, int i5, int i6, int i7, int i8) {
        this.mImageData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mRotation = i3;
        this.mDelegate = barcodeDetectorAsyncTaskDelegate;
        this.mBarcodeDetector = rNBarcodeDetector;
        this.mImageDimensions = new ImageDimensions(i, i2, i3, i4);
        this.mScaleX = ((double) i5) / ((double) (((float) this.mImageDimensions.getWidth()) * f));
        this.mScaleY = (double) (1.0f / f);
        this.mPaddingLeft = i7;
        this.mPaddingTop = i8;
    }

    protected Void doInBackground(Void... voidArr) {
        if (!(isCancelled() || this.mDelegate == null || this.mBarcodeDetector == null)) {
            this.mBarcodeDetector.getDetector().detectInImage(FirebaseVisionImage.fromByteArray(this.mImageData, new Builder().setWidth(this.mWidth).setHeight(this.mHeight).setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12).setRotation(getFirebaseRotation()).build())).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                public void onSuccess(List<FirebaseVisionBarcode> list) {
                    BarcodeDetectorAsyncTask.this.mDelegate.onBarcodesDetected(BarcodeDetectorAsyncTask.this.serializeEventData(list));
                    BarcodeDetectorAsyncTask.this.mDelegate.onBarcodeDetectingTaskCompleted();
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(Exception exception) {
                    String access$000 = BarcodeDetectorAsyncTask.this.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Text recognition task failed");
                    stringBuilder.append(exception);
                    Log.e(access$000, stringBuilder.toString());
                    BarcodeDetectorAsyncTask.this.mDelegate.onBarcodeDetectingTaskCompleted();
                }
            });
        }
        return null;
    }

    private int getFirebaseRotation() {
        int i = this.mRotation;
        if (i == -90) {
            return 3;
        }
        if (i == 0) {
            return 0;
        }
        if (i == 90) {
            return 1;
        }
        if (i == 180) {
            return 2;
        }
        String str = this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad rotation value: ");
        stringBuilder.append(this.mRotation);
        Log.e(str, stringBuilder.toString());
        return 0;
    }

    private WritableArray serializeEventData(List<FirebaseVisionBarcode> list) {
        WritableArray createArray = Arguments.createArray();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            FirebaseVisionBarcode firebaseVisionBarcode = (FirebaseVisionBarcode) it.next();
            Rect boundingBox = firebaseVisionBarcode.getBoundingBox();
            String rawValue = firebaseVisionBarcode.getRawValue();
            int valueType = firebaseVisionBarcode.getValueType();
            int format = firebaseVisionBarcode.getFormat();
            WritableMap createMap = Arguments.createMap();
            String str = "lastName";
            String str2 = "middleName";
            String str3 = "firstName";
            String str4 = "phoneType";
            String str5 = "number";
            String str6 = "title";
            Iterator it2 = it;
            String str7 = "UNKNOWN";
            String str8;
            int length;
            if (valueType == 1) {
                createMap.putString("organization", firebaseVisionBarcode.getContactInfo().getOrganization());
                createMap.putString(str6, firebaseVisionBarcode.getContactInfo().getTitle());
                PersonName name = firebaseVisionBarcode.getContactInfo().getName();
                if (name != null) {
                    createMap.putString(str3, name.getFirst());
                    createMap.putString(str, name.getLast());
                    createMap.putString(str2, name.getMiddle());
                    createMap.putString("formattedName", name.getFormattedName());
                    createMap.putString("prefix", name.getPrefix());
                    createMap.putString("pronunciation", name.getPronunciation());
                    createMap.putString("suffix", name.getSuffix());
                }
                List<Phone> phones = firebaseVisionBarcode.getContactInfo().getPhones();
                WritableArray createArray2 = Arguments.createArray();
                for (Phone phone : phones) {
                    WritableMap createMap2 = Arguments.createMap();
                    createMap2.putString(str5, phone.getNumber());
                    createMap2.putString(str4, getPhoneType(phone.getType()));
                    createArray2.pushMap(createMap2);
                }
                createMap.putArray("phones", createArray2);
                List addresses = firebaseVisionBarcode.getContactInfo().getAddresses();
                createArray2 = Arguments.createArray();
                it = addresses.iterator();
                while (true) {
                    int i = 0;
                    if (it.hasNext()) {
                        Iterator it3;
                        Address address = (Address) it.next();
                        WritableMap createMap3 = Arguments.createMap();
                        WritableArray createArray3 = Arguments.createArray();
                        String[] addressLines = address.getAddressLines();
                        int length2 = addressLines.length;
                        while (i < length2) {
                            it3 = it;
                            createArray3.pushString(addressLines[i]);
                            i++;
                            it = it3;
                        }
                        it3 = it;
                        createMap3.putArray("addressLines", createArray3);
                        int type = address.getType();
                        str8 = type != 1 ? type != 2 ? str7 : "Home" : "Work";
                        createMap3.putString("addressType", str8);
                        createArray2.pushMap(createMap3);
                        it = it3;
                    } else {
                        createMap.putArray("addresses", createArray2);
                        List<Email> emails = firebaseVisionBarcode.getContactInfo().getEmails();
                        createArray2 = Arguments.createArray();
                        for (Email processEmail : emails) {
                            createArray2.pushMap(processEmail(processEmail));
                        }
                        createMap.putArray("emails", createArray2);
                        String[] urls = firebaseVisionBarcode.getContactInfo().getUrls();
                        createArray2 = Arguments.createArray();
                        length = urls.length;
                        while (i < length) {
                            createArray2.pushString(urls[i]);
                            i++;
                        }
                        createMap.putArray("urls", createArray2);
                    }
                }
            } else if (valueType == 2) {
                createMap.putMap("email", processEmail(firebaseVisionBarcode.getEmail()));
            } else if (valueType == 4) {
                str8 = firebaseVisionBarcode.getPhone().getNumber();
                int type2 = firebaseVisionBarcode.getPhone().getType();
                createMap.putString(str5, str8);
                createMap.putString(str4, getPhoneType(type2));
            } else if (valueType != 6) {
                switch (valueType) {
                    case 8:
                        str8 = firebaseVisionBarcode.getUrl().getTitle();
                        createMap.putString(ImagesContract.URL, firebaseVisionBarcode.getUrl().getUrl());
                        createMap.putString(str6, str8);
                        break;
                    case 9:
                        str8 = firebaseVisionBarcode.getWifi().getSsid();
                        str = firebaseVisionBarcode.getWifi().getPassword();
                        length = firebaseVisionBarcode.getWifi().getEncryptionType();
                        String str9 = length != 1 ? length != 2 ? length != 3 ? str7 : "WEP" : "WPA" : "Open";
                        createMap.putString("encryptionType", str9);
                        createMap.putString("password", str);
                        createMap.putString("ssid", str8);
                        break;
                    case 10:
                        createMap.putDouble("latitude", firebaseVisionBarcode.getGeoPoint().getLat());
                        createMap.putDouble("longitude", firebaseVisionBarcode.getGeoPoint().getLng());
                        break;
                    case 11:
                        createMap.putString("description", firebaseVisionBarcode.getCalendarEvent().getDescription());
                        createMap.putString(Param.LOCATION, firebaseVisionBarcode.getCalendarEvent().getLocation());
                        createMap.putString("organizer", firebaseVisionBarcode.getCalendarEvent().getOrganizer());
                        createMap.putString(NotificationCompat.CATEGORY_STATUS, firebaseVisionBarcode.getCalendarEvent().getStatus());
                        createMap.putString("summary", firebaseVisionBarcode.getCalendarEvent().getSummary());
                        CalendarDateTime start = firebaseVisionBarcode.getCalendarEvent().getStart();
                        CalendarDateTime end = firebaseVisionBarcode.getCalendarEvent().getEnd();
                        if (start != null) {
                            createMap.putString(ViewProps.START, start.getRawValue());
                        }
                        if (end == null) {
                            break;
                        }
                        createMap.putString(ViewProps.END, start.getRawValue());
                        break;
                    case 12:
                        createMap.putString("addressCity", firebaseVisionBarcode.getDriverLicense().getAddressCity());
                        createMap.putString("addressState", firebaseVisionBarcode.getDriverLicense().getAddressState());
                        createMap.putString("addressStreet", firebaseVisionBarcode.getDriverLicense().getAddressStreet());
                        createMap.putString("addressZip", firebaseVisionBarcode.getDriverLicense().getAddressZip());
                        createMap.putString("birthDate", firebaseVisionBarcode.getDriverLicense().getBirthDate());
                        createMap.putString("documentType", firebaseVisionBarcode.getDriverLicense().getDocumentType());
                        createMap.putString("expiryDate", firebaseVisionBarcode.getDriverLicense().getExpiryDate());
                        createMap.putString(str3, firebaseVisionBarcode.getDriverLicense().getFirstName());
                        createMap.putString(str2, firebaseVisionBarcode.getDriverLicense().getMiddleName());
                        createMap.putString(str, firebaseVisionBarcode.getDriverLicense().getLastName());
                        createMap.putString("gender", firebaseVisionBarcode.getDriverLicense().getGender());
                        createMap.putString("issueDate", firebaseVisionBarcode.getDriverLicense().getIssueDate());
                        createMap.putString("issuingCountry", firebaseVisionBarcode.getDriverLicense().getIssuingCountry());
                        createMap.putString("licenseNumber", firebaseVisionBarcode.getDriverLicense().getLicenseNumber());
                        break;
                    default:
                        break;
                }
            } else {
                str8 = firebaseVisionBarcode.getSms().getMessage();
                str = firebaseVisionBarcode.getSms().getPhoneNumber();
                createMap.putString("message", str8);
                createMap.putString(str6, str);
            }
            createMap.putString("data", firebaseVisionBarcode.getDisplayValue());
            createMap.putString("dataRaw", rawValue);
            createMap.putString("type", BarcodeFormatUtils.get(valueType));
            createMap.putString("format", BarcodeFormatUtils.getFormat(format));
            createMap.putMap("bounds", processBounds(boundingBox));
            createArray.pushMap(createMap);
            it = it2;
        }
        return createArray;
    }

    private WritableMap processEmail(Email email) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("address", email.getAddress());
        createMap.putString("body", email.getBody());
        createMap.putString("subject", email.getSubject());
        int type = email.getType();
        String str = type != 1 ? type != 2 ? "UNKNOWN" : "Home" : "Work";
        createMap.putString("emailType", str);
        return createMap;
    }

    private WritableMap processBounds(Rect rect) {
        WritableMap createMap = Arguments.createMap();
        int i = rect.left;
        int i2 = rect.top;
        if (rect.left < this.mWidth / 2) {
            i += this.mPaddingLeft / 2;
        } else if (rect.left > this.mWidth / 2) {
            i -= this.mPaddingLeft / 2;
        }
        i2 += this.mPaddingTop;
        createMap.putDouble("x", ((double) i) * this.mScaleX);
        createMap.putDouble("y", ((double) i2) * this.mScaleY);
        WritableMap createMap2 = Arguments.createMap();
        createMap2.putDouble("width", ((double) rect.width()) * this.mScaleX);
        createMap2.putDouble("height", ((double) rect.height()) * this.mScaleY);
        WritableMap createMap3 = Arguments.createMap();
        createMap3.putMap("origin", createMap);
        createMap3.putMap("size", createMap2);
        return createMap3;
    }
}
