package com.google.firebase.ml.vision.barcode;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_ml.zzmd.zzv.zza;
import com.google.android.gms.internal.firebase_ml.zzmd.zzv.zzb;
import com.google.android.gms.vision.barcode.Barcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseVisionBarcode {
    public static final int FORMAT_ALL_FORMATS = 0;
    public static final int FORMAT_AZTEC = 4096;
    public static final int FORMAT_CODABAR = 8;
    public static final int FORMAT_CODE_128 = 1;
    public static final int FORMAT_CODE_39 = 2;
    public static final int FORMAT_CODE_93 = 4;
    public static final int FORMAT_DATA_MATRIX = 16;
    public static final int FORMAT_EAN_13 = 32;
    public static final int FORMAT_EAN_8 = 64;
    public static final int FORMAT_ITF = 128;
    public static final int FORMAT_PDF417 = 2048;
    public static final int FORMAT_QR_CODE = 256;
    public static final int FORMAT_UNKNOWN = -1;
    public static final int FORMAT_UPC_A = 512;
    public static final int FORMAT_UPC_E = 1024;
    public static final int TYPE_CALENDAR_EVENT = 11;
    public static final int TYPE_CONTACT_INFO = 1;
    public static final int TYPE_DRIVER_LICENSE = 12;
    public static final int TYPE_EMAIL = 2;
    public static final int TYPE_GEO = 10;
    public static final int TYPE_ISBN = 3;
    public static final int TYPE_PHONE = 4;
    public static final int TYPE_PRODUCT = 5;
    public static final int TYPE_SMS = 6;
    public static final int TYPE_TEXT = 7;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_URL = 8;
    public static final int TYPE_WIFI = 9;
    private static final Map<Integer, zza> zzavq = new HashMap();
    private static final Map<Integer, zzb> zzavr = new HashMap();
    private final Barcode zzavs;

    public static class Address {
        public static final int TYPE_HOME = 2;
        public static final int TYPE_UNKNOWN = 0;
        public static final int TYPE_WORK = 1;
        private final com.google.android.gms.vision.barcode.Barcode.Address zzavt;

        public @interface AddressType {
        }

        Address(@NonNull com.google.android.gms.vision.barcode.Barcode.Address address) {
            this.zzavt = (com.google.android.gms.vision.barcode.Barcode.Address) Preconditions.checkNotNull(address);
        }

        @AddressType
        public int getType() {
            return this.zzavt.type;
        }

        @NonNull
        public String[] getAddressLines() {
            return this.zzavt.addressLines;
        }
    }

    public @interface BarcodeFormat {
    }

    public @interface BarcodeValueType {
    }

    public static class CalendarDateTime {
        private final com.google.android.gms.vision.barcode.Barcode.CalendarDateTime zzavu;

        CalendarDateTime(@NonNull com.google.android.gms.vision.barcode.Barcode.CalendarDateTime calendarDateTime) {
            this.zzavu = calendarDateTime;
        }

        public int getYear() {
            return this.zzavu.year;
        }

        public int getMonth() {
            return this.zzavu.month;
        }

        public int getDay() {
            return this.zzavu.day;
        }

        public int getHours() {
            return this.zzavu.hours;
        }

        public int getMinutes() {
            return this.zzavu.minutes;
        }

        public int getSeconds() {
            return this.zzavu.seconds;
        }

        public boolean isUtc() {
            return this.zzavu.isUtc;
        }

        @Nullable
        public String getRawValue() {
            return this.zzavu.rawValue;
        }
    }

    public static class CalendarEvent {
        private final com.google.android.gms.vision.barcode.Barcode.CalendarEvent calendarEvent;

        CalendarEvent(@NonNull com.google.android.gms.vision.barcode.Barcode.CalendarEvent calendarEvent) {
            this.calendarEvent = (com.google.android.gms.vision.barcode.Barcode.CalendarEvent) Preconditions.checkNotNull(calendarEvent);
        }

        @Nullable
        public String getSummary() {
            return this.calendarEvent.summary;
        }

        @Nullable
        public String getDescription() {
            return this.calendarEvent.description;
        }

        @Nullable
        public String getLocation() {
            return this.calendarEvent.location;
        }

        @Nullable
        public String getOrganizer() {
            return this.calendarEvent.organizer;
        }

        @Nullable
        public String getStatus() {
            return this.calendarEvent.status;
        }

        @Nullable
        public CalendarDateTime getStart() {
            if (this.calendarEvent.start == null) {
                return null;
            }
            return new CalendarDateTime(this.calendarEvent.start);
        }

        @Nullable
        public CalendarDateTime getEnd() {
            if (this.calendarEvent.end == null) {
                return null;
            }
            return new CalendarDateTime(this.calendarEvent.end);
        }
    }

    public static class ContactInfo {
        private final com.google.android.gms.vision.barcode.Barcode.ContactInfo contactInfo;

        ContactInfo(@NonNull com.google.android.gms.vision.barcode.Barcode.ContactInfo contactInfo) {
            this.contactInfo = (com.google.android.gms.vision.barcode.Barcode.ContactInfo) Preconditions.checkNotNull(contactInfo);
        }

        @Nullable
        public PersonName getName() {
            if (this.contactInfo.name == null) {
                return null;
            }
            return new PersonName(this.contactInfo.name);
        }

        @Nullable
        public String getOrganization() {
            return this.contactInfo.organization;
        }

        @Nullable
        public String getTitle() {
            return this.contactInfo.title;
        }

        public List<Phone> getPhones() {
            List<Phone> arrayList = new ArrayList();
            if (this.contactInfo.phones == null) {
                return arrayList;
            }
            for (com.google.android.gms.vision.barcode.Barcode.Phone phone : this.contactInfo.phones) {
                if (phone != null) {
                    arrayList.add(new Phone(phone));
                }
            }
            return arrayList;
        }

        public List<Email> getEmails() {
            List<Email> arrayList = new ArrayList();
            if (this.contactInfo.emails == null) {
                return arrayList;
            }
            for (com.google.android.gms.vision.barcode.Barcode.Email email : this.contactInfo.emails) {
                if (email != null) {
                    arrayList.add(new Email(email));
                }
            }
            return arrayList;
        }

        @Nullable
        public String[] getUrls() {
            return this.contactInfo.urls;
        }

        public List<Address> getAddresses() {
            List<Address> arrayList = new ArrayList();
            if (this.contactInfo.addresses == null) {
                return arrayList;
            }
            for (com.google.android.gms.vision.barcode.Barcode.Address address : this.contactInfo.addresses) {
                if (address != null) {
                    arrayList.add(new Address(address));
                }
            }
            return arrayList;
        }
    }

    public static class DriverLicense {
        private com.google.android.gms.vision.barcode.Barcode.DriverLicense driverLicense;

        DriverLicense(@NonNull com.google.android.gms.vision.barcode.Barcode.DriverLicense driverLicense) {
            this.driverLicense = (com.google.android.gms.vision.barcode.Barcode.DriverLicense) Preconditions.checkNotNull(driverLicense);
        }

        @Nullable
        public String getDocumentType() {
            return this.driverLicense.documentType;
        }

        @Nullable
        public String getFirstName() {
            return this.driverLicense.firstName;
        }

        @Nullable
        public String getMiddleName() {
            return this.driverLicense.middleName;
        }

        @Nullable
        public String getLastName() {
            return this.driverLicense.lastName;
        }

        @Nullable
        public String getGender() {
            return this.driverLicense.gender;
        }

        @Nullable
        public String getAddressStreet() {
            return this.driverLicense.addressStreet;
        }

        @Nullable
        public String getAddressCity() {
            return this.driverLicense.addressCity;
        }

        @Nullable
        public String getAddressState() {
            return this.driverLicense.addressState;
        }

        @Nullable
        public String getAddressZip() {
            return this.driverLicense.addressZip;
        }

        @Nullable
        public String getLicenseNumber() {
            return this.driverLicense.licenseNumber;
        }

        @Nullable
        public String getIssueDate() {
            return this.driverLicense.issueDate;
        }

        @Nullable
        public String getExpiryDate() {
            return this.driverLicense.expiryDate;
        }

        @Nullable
        public String getBirthDate() {
            return this.driverLicense.birthDate;
        }

        @Nullable
        public String getIssuingCountry() {
            return this.driverLicense.issuingCountry;
        }
    }

    public static class Email {
        public static final int TYPE_HOME = 2;
        public static final int TYPE_UNKNOWN = 0;
        public static final int TYPE_WORK = 1;
        private final com.google.android.gms.vision.barcode.Barcode.Email email;

        public @interface FormatType {
        }

        Email(@NonNull com.google.android.gms.vision.barcode.Barcode.Email email) {
            this.email = (com.google.android.gms.vision.barcode.Barcode.Email) Preconditions.checkNotNull(email);
        }

        @FormatType
        public int getType() {
            return this.email.type;
        }

        @Nullable
        public String getAddress() {
            return this.email.address;
        }

        @Nullable
        public String getSubject() {
            return this.email.subject;
        }

        @Nullable
        public String getBody() {
            return this.email.body;
        }
    }

    public static class GeoPoint {
        private final com.google.android.gms.vision.barcode.Barcode.GeoPoint geoPoint;

        GeoPoint(@NonNull com.google.android.gms.vision.barcode.Barcode.GeoPoint geoPoint) {
            this.geoPoint = (com.google.android.gms.vision.barcode.Barcode.GeoPoint) Preconditions.checkNotNull(geoPoint);
        }

        public double getLat() {
            return this.geoPoint.lat;
        }

        public double getLng() {
            return this.geoPoint.lng;
        }
    }

    public static class PersonName {
        private final com.google.android.gms.vision.barcode.Barcode.PersonName zzavv;

        PersonName(@NonNull com.google.android.gms.vision.barcode.Barcode.PersonName personName) {
            this.zzavv = (com.google.android.gms.vision.barcode.Barcode.PersonName) Preconditions.checkNotNull(personName);
        }

        @Nullable
        public String getFormattedName() {
            return this.zzavv.formattedName;
        }

        @Nullable
        public String getPronunciation() {
            return this.zzavv.pronunciation;
        }

        @Nullable
        public String getPrefix() {
            return this.zzavv.prefix;
        }

        @Nullable
        public String getFirst() {
            return this.zzavv.first;
        }

        @Nullable
        public String getMiddle() {
            return this.zzavv.middle;
        }

        @Nullable
        public String getLast() {
            return this.zzavv.last;
        }

        @Nullable
        public String getSuffix() {
            return this.zzavv.suffix;
        }
    }

    public static class Phone {
        public static final int TYPE_FAX = 3;
        public static final int TYPE_HOME = 2;
        public static final int TYPE_MOBILE = 4;
        public static final int TYPE_UNKNOWN = 0;
        public static final int TYPE_WORK = 1;
        private final com.google.android.gms.vision.barcode.Barcode.Phone phone;

        public @interface FormatType {
        }

        Phone(@NonNull com.google.android.gms.vision.barcode.Barcode.Phone phone) {
            this.phone = (com.google.android.gms.vision.barcode.Barcode.Phone) Preconditions.checkNotNull(phone);
        }

        @Nullable
        public String getNumber() {
            return this.phone.number;
        }

        @FormatType
        public int getType() {
            return this.phone.type;
        }
    }

    public static class Sms {
        private final com.google.android.gms.vision.barcode.Barcode.Sms sms;

        Sms(@NonNull com.google.android.gms.vision.barcode.Barcode.Sms sms) {
            this.sms = (com.google.android.gms.vision.barcode.Barcode.Sms) Preconditions.checkNotNull(sms);
        }

        @Nullable
        public String getMessage() {
            return this.sms.message;
        }

        @Nullable
        public String getPhoneNumber() {
            return this.sms.phoneNumber;
        }
    }

    public static class UrlBookmark {
        private final com.google.android.gms.vision.barcode.Barcode.UrlBookmark zzavw;

        UrlBookmark(@NonNull com.google.android.gms.vision.barcode.Barcode.UrlBookmark urlBookmark) {
            this.zzavw = (com.google.android.gms.vision.barcode.Barcode.UrlBookmark) Preconditions.checkNotNull(urlBookmark);
        }

        @Nullable
        public String getTitle() {
            return this.zzavw.title;
        }

        @Nullable
        public String getUrl() {
            return this.zzavw.url;
        }
    }

    public static class WiFi {
        public static final int TYPE_OPEN = 1;
        public static final int TYPE_WEP = 3;
        public static final int TYPE_WPA = 2;
        private final com.google.android.gms.vision.barcode.Barcode.WiFi zzavx;

        public @interface EncryptionType {
        }

        WiFi(@NonNull com.google.android.gms.vision.barcode.Barcode.WiFi wiFi) {
            this.zzavx = (com.google.android.gms.vision.barcode.Barcode.WiFi) Preconditions.checkNotNull(wiFi);
        }

        @Nullable
        public String getSsid() {
            return this.zzavx.ssid;
        }

        @Nullable
        public String getPassword() {
            return this.zzavx.password;
        }

        @EncryptionType
        public int getEncryptionType() {
            return this.zzavx.encryptionType;
        }
    }

    public FirebaseVisionBarcode(@NonNull Barcode barcode) {
        this.zzavs = (Barcode) Preconditions.checkNotNull(barcode);
    }

    @Nullable
    public Rect getBoundingBox() {
        return this.zzavs.getBoundingBox();
    }

    @Nullable
    public Point[] getCornerPoints() {
        return this.zzavs.cornerPoints;
    }

    @Nullable
    public String getRawValue() {
        return this.zzavs.rawValue;
    }

    @Nullable
    public String getDisplayValue() {
        return this.zzavs.displayValue;
    }

    @BarcodeFormat
    public int getFormat() {
        int i = this.zzavs.format;
        return (i > 4096 || i == 0) ? -1 : i;
    }

    @BarcodeValueType
    public int getValueType() {
        return this.zzavs.valueFormat;
    }

    @Nullable
    public Email getEmail() {
        return this.zzavs.email != null ? new Email(this.zzavs.email) : null;
    }

    @Nullable
    public Phone getPhone() {
        return this.zzavs.phone != null ? new Phone(this.zzavs.phone) : null;
    }

    @Nullable
    public Sms getSms() {
        return this.zzavs.sms != null ? new Sms(this.zzavs.sms) : null;
    }

    @Nullable
    public WiFi getWifi() {
        return this.zzavs.wifi != null ? new WiFi(this.zzavs.wifi) : null;
    }

    @Nullable
    public UrlBookmark getUrl() {
        return this.zzavs.url != null ? new UrlBookmark(this.zzavs.url) : null;
    }

    @Nullable
    public GeoPoint getGeoPoint() {
        return this.zzavs.geoPoint != null ? new GeoPoint(this.zzavs.geoPoint) : null;
    }

    @Nullable
    public CalendarEvent getCalendarEvent() {
        return this.zzavs.calendarEvent != null ? new CalendarEvent(this.zzavs.calendarEvent) : null;
    }

    @Nullable
    public ContactInfo getContactInfo() {
        return this.zzavs.contactInfo != null ? new ContactInfo(this.zzavs.contactInfo) : null;
    }

    @Nullable
    public DriverLicense getDriverLicense() {
        return this.zzavs.driverLicense != null ? new DriverLicense(this.zzavs.driverLicense) : null;
    }

    public final zza zznd() {
        zza zza = (zza) zzavq.get(Integer.valueOf(getFormat()));
        return zza == null ? zza.FORMAT_UNKNOWN : zza;
    }

    public final zzb zzne() {
        zzb zzb = (zzb) zzavr.get(Integer.valueOf(getValueType()));
        return zzb == null ? zzb.TYPE_UNKNOWN : zzb;
    }

    static {
        zzavq.put(Integer.valueOf(-1), zza.FORMAT_UNKNOWN);
        Map map = zzavq;
        Integer valueOf = Integer.valueOf(1);
        map.put(valueOf, zza.FORMAT_CODE_128);
        map = zzavq;
        Integer valueOf2 = Integer.valueOf(2);
        map.put(valueOf2, zza.FORMAT_CODE_39);
        map = zzavq;
        Integer valueOf3 = Integer.valueOf(4);
        map.put(valueOf3, zza.FORMAT_CODE_93);
        map = zzavq;
        Integer valueOf4 = Integer.valueOf(8);
        map.put(valueOf4, zza.FORMAT_CODABAR);
        zzavq.put(Integer.valueOf(16), zza.FORMAT_DATA_MATRIX);
        zzavq.put(Integer.valueOf(32), zza.FORMAT_EAN_13);
        zzavq.put(Integer.valueOf(64), zza.FORMAT_EAN_8);
        zzavq.put(Integer.valueOf(128), zza.FORMAT_ITF);
        zzavq.put(Integer.valueOf(256), zza.FORMAT_QR_CODE);
        zzavq.put(Integer.valueOf(512), zza.FORMAT_UPC_A);
        zzavq.put(Integer.valueOf(1024), zza.FORMAT_UPC_E);
        zzavq.put(Integer.valueOf(2048), zza.FORMAT_PDF417);
        zzavq.put(Integer.valueOf(4096), zza.FORMAT_AZTEC);
        zzavr.put(Integer.valueOf(0), zzb.TYPE_UNKNOWN);
        zzavr.put(valueOf, zzb.TYPE_CONTACT_INFO);
        zzavr.put(valueOf2, zzb.TYPE_EMAIL);
        zzavr.put(Integer.valueOf(3), zzb.TYPE_ISBN);
        zzavr.put(valueOf3, zzb.TYPE_PHONE);
        zzavr.put(Integer.valueOf(5), zzb.TYPE_PRODUCT);
        zzavr.put(Integer.valueOf(6), zzb.TYPE_SMS);
        zzavr.put(Integer.valueOf(7), zzb.TYPE_TEXT);
        zzavr.put(valueOf4, zzb.TYPE_URL);
        zzavr.put(Integer.valueOf(9), zzb.TYPE_WIFI);
        zzavr.put(Integer.valueOf(10), zzb.TYPE_GEO);
        zzavr.put(Integer.valueOf(11), zzb.TYPE_CALENDAR_EVENT);
        zzavr.put(Integer.valueOf(12), zzb.TYPE_DRIVER_LICENSE);
    }
}
