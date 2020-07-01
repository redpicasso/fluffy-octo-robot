package org.reactnative.barcodedetector;

import android.util.SparseArray;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BarcodeFormatUtils {
    public static final SparseArray<String> FORMATS;
    public static final Map<String, Integer> REVERSE_FORMATS;
    public static final Map<String, Integer> REVERSE_TYPES;
    public static final SparseArray<String> TYPES;
    private static final int UNKNOWN_FORMAT_INT = -1;
    private static final String UNKNOWN_FORMAT_STRING = "UNKNOWN_FORMAT";
    private static final String UNKNOWN_TYPE_STRING = "UNKNOWN_TYPE";

    static {
        SparseArray sparseArray = new SparseArray();
        sparseArray.put(1, "CODE_128");
        sparseArray.put(2, "CODE_39");
        sparseArray.put(4, "CODE_93");
        sparseArray.put(8, "CODABAR");
        sparseArray.put(16, "DATA_MATRIX");
        sparseArray.put(32, "EAN_13");
        sparseArray.put(64, "EAN_8");
        sparseArray.put(128, "ITF");
        sparseArray.put(256, "QR_CODE");
        String str = "UPC_A";
        sparseArray.put(512, str);
        sparseArray.put(1024, "UPC_E");
        sparseArray.put(2048, "PDF417");
        sparseArray.put(4096, "AZTEC");
        int i = 0;
        sparseArray.put(0, "ALL");
        sparseArray.put(512, str);
        str = "None";
        sparseArray.put(-1, str);
        FORMATS = sparseArray;
        Map hashMap = new HashMap();
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            hashMap.put(sparseArray.valueAt(i2), Integer.valueOf(sparseArray.keyAt(i2)));
        }
        REVERSE_FORMATS = Collections.unmodifiableMap(hashMap);
        sparseArray = new SparseArray();
        sparseArray.put(11, "CALENDAR_EVENT");
        sparseArray.put(1, "CONTACT_INFO");
        sparseArray.put(12, "DRIVER_LICENSE");
        sparseArray.put(2, "EMAIL");
        sparseArray.put(10, "GEO");
        sparseArray.put(3, "ISBN");
        sparseArray.put(4, "PHONE");
        sparseArray.put(5, "PRODUCT");
        sparseArray.put(6, "SMS");
        sparseArray.put(7, "TEXT");
        sparseArray.put(8, "URL");
        sparseArray.put(9, "WIFI");
        sparseArray.put(-1, str);
        TYPES = sparseArray;
        Map hashMap2 = new HashMap();
        while (i < sparseArray.size()) {
            hashMap2.put(sparseArray.valueAt(i), Integer.valueOf(sparseArray.keyAt(i)));
            i++;
        }
        REVERSE_TYPES = Collections.unmodifiableMap(hashMap2);
    }

    public static String get(int i) {
        return (String) TYPES.get(i, UNKNOWN_TYPE_STRING);
    }

    public static String getFormat(int i) {
        return (String) FORMATS.get(i, UNKNOWN_FORMAT_STRING);
    }

    public static int get(String str) {
        return REVERSE_FORMATS.containsKey(str) ? ((Integer) REVERSE_FORMATS.get(str)).intValue() : -1;
    }
}
