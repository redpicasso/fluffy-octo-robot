package com.drew.metadata;

import com.drew.lang.Rational;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.lang.annotations.SuppressWarnings;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public abstract class Directory {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String _floatFormatPattern = "0.###";
    @NotNull
    protected final Collection<Tag> _definedTagList = new ArrayList();
    protected TagDescriptor _descriptor;
    @NotNull
    private final Collection<String> _errorList = new ArrayList(4);
    @Nullable
    private Directory _parent;
    @NotNull
    protected final Map<Integer, Object> _tagMap = new HashMap();

    @NotNull
    public abstract String getName();

    @NotNull
    protected abstract HashMap<Integer, String> getTagNameMap();

    protected Directory() {
    }

    public boolean isEmpty() {
        return this._errorList.isEmpty() && this._definedTagList.isEmpty();
    }

    public boolean containsTag(int i) {
        return this._tagMap.containsKey(Integer.valueOf(i));
    }

    @NotNull
    public Collection<Tag> getTags() {
        return Collections.unmodifiableCollection(this._definedTagList);
    }

    public int getTagCount() {
        return this._definedTagList.size();
    }

    public void setDescriptor(@NotNull TagDescriptor tagDescriptor) {
        if (tagDescriptor != null) {
            this._descriptor = tagDescriptor;
            return;
        }
        throw new NullPointerException("cannot set a null descriptor");
    }

    public void addError(@NotNull String str) {
        this._errorList.add(str);
    }

    public boolean hasErrors() {
        return this._errorList.size() > 0;
    }

    @NotNull
    public Iterable<String> getErrors() {
        return Collections.unmodifiableCollection(this._errorList);
    }

    public int getErrorCount() {
        return this._errorList.size();
    }

    @Nullable
    public Directory getParent() {
        return this._parent;
    }

    public void setParent(@NotNull Directory directory) {
        this._parent = directory;
    }

    public void setInt(int i, int i2) {
        setObject(i, Integer.valueOf(i2));
    }

    public void setIntArray(int i, @NotNull int[] iArr) {
        setObjectArray(i, iArr);
    }

    public void setFloat(int i, float f) {
        setObject(i, Float.valueOf(f));
    }

    public void setFloatArray(int i, @NotNull float[] fArr) {
        setObjectArray(i, fArr);
    }

    public void setDouble(int i, double d) {
        setObject(i, Double.valueOf(d));
    }

    public void setDoubleArray(int i, @NotNull double[] dArr) {
        setObjectArray(i, dArr);
    }

    public void setStringValue(int i, @NotNull StringValue stringValue) {
        if (stringValue != null) {
            setObject(i, stringValue);
            return;
        }
        throw new NullPointerException("cannot set a null StringValue");
    }

    public void setString(int i, @NotNull String str) {
        if (str != null) {
            setObject(i, str);
            return;
        }
        throw new NullPointerException("cannot set a null String");
    }

    public void setStringArray(int i, @NotNull String[] strArr) {
        setObjectArray(i, strArr);
    }

    public void setStringValueArray(int i, @NotNull StringValue[] stringValueArr) {
        setObjectArray(i, stringValueArr);
    }

    public void setBoolean(int i, boolean z) {
        setObject(i, Boolean.valueOf(z));
    }

    public void setLong(int i, long j) {
        setObject(i, Long.valueOf(j));
    }

    public void setDate(int i, @NotNull Date date) {
        setObject(i, date);
    }

    public void setRational(int i, @NotNull Rational rational) {
        setObject(i, rational);
    }

    public void setRationalArray(int i, @NotNull Rational[] rationalArr) {
        setObjectArray(i, rationalArr);
    }

    public void setByteArray(int i, @NotNull byte[] bArr) {
        setObjectArray(i, bArr);
    }

    public void setObject(int i, @NotNull Object obj) {
        if (obj != null) {
            if (!this._tagMap.containsKey(Integer.valueOf(i))) {
                this._definedTagList.add(new Tag(i, this));
            }
            this._tagMap.put(Integer.valueOf(i), obj);
            return;
        }
        throw new NullPointerException("cannot set a null object");
    }

    public void setObjectArray(int i, @NotNull Object obj) {
        setObject(i, obj);
    }

    public int getInt(int i) throws MetadataException {
        Integer integer = getInteger(i);
        if (integer != null) {
            return integer.intValue();
        }
        Object object = getObject(i);
        String str = "Tag '";
        if (object == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(getTagName(i));
            stringBuilder.append("' has not been set -- check using containsTag() first");
            throw new MetadataException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(i);
        stringBuilder2.append("' cannot be converted to int.  It is of type '");
        stringBuilder2.append(object.getClass());
        stringBuilder2.append("'.");
        throw new MetadataException(stringBuilder2.toString());
    }

    @Nullable
    public Integer getInteger(int i) {
        byte[] bytes;
        Object object = getObject(i);
        if (object == null) {
            return null;
        }
        if (object instanceof Number) {
            return Integer.valueOf(((Number) object).intValue());
        }
        int i2 = 0;
        if ((object instanceof String) || (object instanceof StringValue)) {
            try {
                object = Integer.valueOf(Integer.parseInt(object.toString()));
                return object;
            } catch (NumberFormatException unused) {
                bytes = object.toString().getBytes();
                long j = 0;
                while (i2 < bytes.length) {
                    j = (j << 8) + ((long) (bytes[i2] & 255));
                    i2++;
                }
                return Integer.valueOf((int) j);
            }
        }
        if (object instanceof Rational[]) {
            Rational[] rationalArr = (Rational[]) object;
            if (rationalArr.length == 1) {
                return Integer.valueOf(rationalArr[0].intValue());
            }
        } else if (object instanceof byte[]) {
            bytes = (byte[]) object;
            if (bytes.length == 1) {
                return Integer.valueOf(bytes[0]);
            }
        } else if (object instanceof int[]) {
            int[] iArr = (int[]) object;
            if (iArr.length == 1) {
                return Integer.valueOf(iArr[0]);
            }
        } else if (object instanceof short[]) {
            short[] sArr = (short[]) object;
            if (sArr.length == 1) {
                return Integer.valueOf(sArr[0]);
            }
        }
        return null;
    }

    @Nullable
    public String[] getStringArray(int i) {
        Object object = getObject(i);
        String[] strArr = null;
        if (object == null) {
            return null;
        }
        if (object instanceof String[]) {
            return (String[]) object;
        }
        int i2 = 0;
        if (object instanceof String) {
            return new String[]{(String) object};
        } else if (object instanceof StringValue) {
            return new String[]{object.toString()};
        } else if (object instanceof StringValue[]) {
            StringValue[] stringValueArr = (StringValue[]) object;
            strArr = new String[stringValueArr.length];
            while (i2 < strArr.length) {
                strArr[i2] = stringValueArr[i2].toString();
                i2++;
            }
            return strArr;
        } else if (object instanceof int[]) {
            int[] iArr = (int[]) object;
            strArr = new String[iArr.length];
            while (i2 < strArr.length) {
                strArr[i2] = Integer.toString(iArr[i2]);
                i2++;
            }
            return strArr;
        } else if (object instanceof byte[]) {
            byte[] bArr = (byte[]) object;
            strArr = new String[bArr.length];
            while (i2 < strArr.length) {
                strArr[i2] = Byte.toString(bArr[i2]);
                i2++;
            }
            return strArr;
        } else {
            if (object instanceof Rational[]) {
                Rational[] rationalArr = (Rational[]) object;
                strArr = new String[rationalArr.length];
                for (int i3 = 0; i3 < strArr.length; i3++) {
                    strArr[i3] = rationalArr[i3].toSimpleString(false);
                }
            }
            return strArr;
        }
    }

    @Nullable
    public StringValue[] getStringValueArray(int i) {
        Object object = getObject(i);
        StringValue[] stringValueArr = null;
        if (object == null) {
            return null;
        }
        if (object instanceof StringValue[]) {
            return (StringValue[]) object;
        }
        if (object instanceof StringValue) {
            stringValueArr = new StringValue[]{(StringValue) object};
        }
        return stringValueArr;
    }

    @Nullable
    public int[] getIntArray(int i) {
        Object object = getObject(i);
        int[] iArr = null;
        if (object == null) {
            return null;
        }
        if (object instanceof int[]) {
            return (int[]) object;
        }
        int i2 = 0;
        if (object instanceof Rational[]) {
            Rational[] rationalArr = (Rational[]) object;
            iArr = new int[rationalArr.length];
            while (i2 < iArr.length) {
                iArr[i2] = rationalArr[i2].intValue();
                i2++;
            }
            return iArr;
        } else if (object instanceof short[]) {
            short[] sArr = (short[]) object;
            iArr = new int[sArr.length];
            while (i2 < sArr.length) {
                iArr[i2] = sArr[i2];
                i2++;
            }
            return iArr;
        } else if (object instanceof byte[]) {
            byte[] bArr = (byte[]) object;
            iArr = new int[bArr.length];
            while (i2 < bArr.length) {
                iArr[i2] = bArr[i2];
                i2++;
            }
            return iArr;
        } else if (object instanceof CharSequence) {
            CharSequence charSequence = (CharSequence) object;
            iArr = new int[charSequence.length()];
            while (i2 < charSequence.length()) {
                iArr[i2] = charSequence.charAt(i2);
                i2++;
            }
            return iArr;
        } else {
            if (object instanceof Integer) {
                iArr = new int[]{((Integer) object).intValue()};
            }
            return iArr;
        }
    }

    @Nullable
    public byte[] getByteArray(int i) {
        Object object = getObject(i);
        byte[] bArr = null;
        if (object == null) {
            return null;
        }
        if (object instanceof StringValue) {
            return ((StringValue) object).getBytes();
        }
        int i2 = 0;
        if (object instanceof Rational[]) {
            Rational[] rationalArr = (Rational[]) object;
            bArr = new byte[rationalArr.length];
            while (i2 < bArr.length) {
                bArr[i2] = rationalArr[i2].byteValue();
                i2++;
            }
            return bArr;
        } else if (object instanceof byte[]) {
            return (byte[]) object;
        } else {
            if (object instanceof int[]) {
                int[] iArr = (int[]) object;
                bArr = new byte[iArr.length];
                while (i2 < iArr.length) {
                    bArr[i2] = (byte) iArr[i2];
                    i2++;
                }
                return bArr;
            } else if (object instanceof short[]) {
                short[] sArr = (short[]) object;
                bArr = new byte[sArr.length];
                while (i2 < sArr.length) {
                    bArr[i2] = (byte) sArr[i2];
                    i2++;
                }
                return bArr;
            } else if (object instanceof CharSequence) {
                CharSequence charSequence = (CharSequence) object;
                bArr = new byte[charSequence.length()];
                while (i2 < charSequence.length()) {
                    bArr[i2] = (byte) charSequence.charAt(i2);
                    i2++;
                }
                return bArr;
            } else {
                if (object instanceof Integer) {
                    bArr = new byte[]{((Integer) object).byteValue()};
                }
                return bArr;
            }
        }
    }

    public double getDouble(int i) throws MetadataException {
        Double doubleObject = getDoubleObject(i);
        if (doubleObject != null) {
            return doubleObject.doubleValue();
        }
        Object object = getObject(i);
        String str = "Tag '";
        if (object == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(getTagName(i));
            stringBuilder.append("' has not been set -- check using containsTag() first");
            throw new MetadataException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(i);
        stringBuilder2.append("' cannot be converted to a double.  It is of type '");
        stringBuilder2.append(object.getClass());
        stringBuilder2.append("'.");
        throw new MetadataException(stringBuilder2.toString());
    }

    @Nullable
    public Double getDoubleObject(int i) {
        Object object = getObject(i);
        if (object == null) {
            return null;
        }
        if ((object instanceof String) || (object instanceof StringValue)) {
            try {
                return Double.valueOf(Double.parseDouble(object.toString()));
            } catch (NumberFormatException unused) {
                return null;
            }
        } else if (object instanceof Number) {
            return Double.valueOf(((Number) object).doubleValue());
        } else {
            return null;
        }
    }

    public float getFloat(int i) throws MetadataException {
        Float floatObject = getFloatObject(i);
        if (floatObject != null) {
            return floatObject.floatValue();
        }
        Object object = getObject(i);
        String str = "Tag '";
        if (object == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(getTagName(i));
            stringBuilder.append("' has not been set -- check using containsTag() first");
            throw new MetadataException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(i);
        stringBuilder2.append("' cannot be converted to a float.  It is of type '");
        stringBuilder2.append(object.getClass());
        stringBuilder2.append("'.");
        throw new MetadataException(stringBuilder2.toString());
    }

    @Nullable
    public Float getFloatObject(int i) {
        Object object = getObject(i);
        if (object == null) {
            return null;
        }
        if ((object instanceof String) || (object instanceof StringValue)) {
            try {
                return Float.valueOf(Float.parseFloat(object.toString()));
            } catch (NumberFormatException unused) {
                return null;
            }
        } else if (object instanceof Number) {
            return Float.valueOf(((Number) object).floatValue());
        } else {
            return null;
        }
    }

    public long getLong(int i) throws MetadataException {
        Long longObject = getLongObject(i);
        if (longObject != null) {
            return longObject.longValue();
        }
        Object object = getObject(i);
        String str = "Tag '";
        if (object == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(getTagName(i));
            stringBuilder.append("' has not been set -- check using containsTag() first");
            throw new MetadataException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(i);
        stringBuilder2.append("' cannot be converted to a long.  It is of type '");
        stringBuilder2.append(object.getClass());
        stringBuilder2.append("'.");
        throw new MetadataException(stringBuilder2.toString());
    }

    @Nullable
    public Long getLongObject(int i) {
        Object object = getObject(i);
        if (object == null) {
            return null;
        }
        if (object instanceof Number) {
            return Long.valueOf(((Number) object).longValue());
        }
        if ((object instanceof String) || (object instanceof StringValue)) {
            try {
                return Long.valueOf(Long.parseLong(object.toString()));
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        if (object instanceof Rational[]) {
            Rational[] rationalArr = (Rational[]) object;
            if (rationalArr.length == 1) {
                return Long.valueOf(rationalArr[0].longValue());
            }
        } else if (object instanceof byte[]) {
            byte[] bArr = (byte[]) object;
            if (bArr.length == 1) {
                return Long.valueOf((long) bArr[0]);
            }
        } else if (object instanceof int[]) {
            int[] iArr = (int[]) object;
            if (iArr.length == 1) {
                return Long.valueOf((long) iArr[0]);
            }
        } else if (object instanceof short[]) {
            short[] sArr = (short[]) object;
            if (sArr.length == 1) {
                return Long.valueOf((long) sArr[0]);
            }
        }
        return null;
    }

    public boolean getBoolean(int i) throws MetadataException {
        Boolean booleanObject = getBooleanObject(i);
        if (booleanObject != null) {
            return booleanObject.booleanValue();
        }
        Object object = getObject(i);
        String str = "Tag '";
        if (object == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(getTagName(i));
            stringBuilder.append("' has not been set -- check using containsTag() first");
            throw new MetadataException(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(i);
        stringBuilder2.append("' cannot be converted to a boolean.  It is of type '");
        stringBuilder2.append(object.getClass());
        stringBuilder2.append("'.");
        throw new MetadataException(stringBuilder2.toString());
    }

    @Nullable
    @SuppressWarnings(justification = "keep API interface consistent", value = "NP_BOOLEAN_RETURN_NULL")
    public Boolean getBooleanObject(int i) {
        Object object = getObject(i);
        if (object == null) {
            return null;
        }
        if (object instanceof Boolean) {
            return (Boolean) object;
        }
        if ((object instanceof String) || (object instanceof StringValue)) {
            try {
                return Boolean.valueOf(Boolean.getBoolean(object.toString()));
            } catch (NumberFormatException unused) {
                return null;
            }
        } else if (!(object instanceof Number)) {
            return null;
        } else {
            return Boolean.valueOf(((Number) object).doubleValue() != 0.0d);
        }
    }

    @Nullable
    public Date getDate(int i) {
        return getDate(i, null, null);
    }

    @Nullable
    public Date getDate(int i, @Nullable TimeZone timeZone) {
        return getDate(i, null, timeZone);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00bb A:{RETURN} */
    @com.drew.lang.annotations.Nullable
    public java.util.Date getDate(int r17, @com.drew.lang.annotations.Nullable java.lang.String r18, @com.drew.lang.annotations.Nullable java.util.TimeZone r19) {
        /*
        r16 = this;
        r0 = r16.getObject(r17);
        r1 = r0 instanceof java.util.Date;
        if (r1 == 0) goto L_0x000b;
    L_0x0008:
        r0 = (java.util.Date) r0;
        return r0;
    L_0x000b:
        r1 = r0 instanceof java.lang.String;
        r2 = 0;
        if (r1 != 0) goto L_0x001a;
    L_0x0010:
        r1 = r0 instanceof com.drew.metadata.StringValue;
        if (r1 == 0) goto L_0x0015;
    L_0x0014:
        goto L_0x001a;
    L_0x0015:
        r3 = r18;
    L_0x0017:
        r0 = r2;
        goto L_0x00b9;
    L_0x001a:
        r3 = "yyyy:MM:dd HH:mm:ss";
        r4 = "yyyy:MM:dd HH:mm";
        r5 = "yyyy-MM-dd HH:mm:ss";
        r6 = "yyyy-MM-dd HH:mm";
        r7 = "yyyy.MM.dd HH:mm:ss";
        r8 = "yyyy.MM.dd HH:mm";
        r9 = "yyyy-MM-dd'T'HH:mm:ss";
        r10 = "yyyy-MM-dd'T'HH:mm";
        r11 = "yyyy-MM-dd";
        r12 = "yyyy-MM";
        r13 = "yyyyMMdd";
        r14 = "yyyy";
        r1 = new java.lang.String[]{r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14};
        r0 = r0.toString();
        r3 = "(\\d\\d:\\d\\d:\\d\\d)(\\.\\d+)";
        r3 = java.util.regex.Pattern.compile(r3);
        r3 = r3.matcher(r0);
        r4 = r3.find();
        if (r4 == 0) goto L_0x005e;
    L_0x004a:
        r0 = 2;
        r0 = r3.group(r0);
        r4 = 1;
        r0 = r0.substring(r4);
        r4 = "$1";
        r3 = r3.replaceAll(r4);
        r15 = r3;
        r3 = r0;
        r0 = r15;
        goto L_0x0060;
    L_0x005e:
        r3 = r18;
    L_0x0060:
        r4 = "(Z|[+-]\\d\\d:\\d\\d)$";
        r4 = java.util.regex.Pattern.compile(r4);
        r4 = r4.matcher(r0);
        r5 = r4.find();
        r6 = "GMT";
        if (r5 == 0) goto L_0x0096;
    L_0x0072:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r0.append(r6);
        r5 = r4.group();
        r7 = "";
        r8 = "Z";
        r5 = r5.replaceAll(r8, r7);
        r0.append(r5);
        r0 = r0.toString();
        r0 = java.util.TimeZone.getTimeZone(r0);
        r4 = r4.replaceAll(r7);
        goto L_0x0099;
    L_0x0096:
        r4 = r0;
        r0 = r19;
    L_0x0099:
        r5 = r1.length;
        r7 = 0;
    L_0x009b:
        if (r7 >= r5) goto L_0x0017;
    L_0x009d:
        r8 = r1[r7];
        r9 = new java.text.SimpleDateFormat;	 Catch:{ ParseException -> 0x00b6 }
        r9.<init>(r8);	 Catch:{ ParseException -> 0x00b6 }
        if (r0 == 0) goto L_0x00aa;
    L_0x00a6:
        r9.setTimeZone(r0);	 Catch:{ ParseException -> 0x00b6 }
        goto L_0x00b1;
    L_0x00aa:
        r8 = java.util.TimeZone.getTimeZone(r6);	 Catch:{ ParseException -> 0x00b6 }
        r9.setTimeZone(r8);	 Catch:{ ParseException -> 0x00b6 }
    L_0x00b1:
        r0 = r9.parse(r4);	 Catch:{ ParseException -> 0x00b6 }
        goto L_0x00b9;
    L_0x00b6:
        r7 = r7 + 1;
        goto L_0x009b;
    L_0x00b9:
        if (r0 != 0) goto L_0x00bc;
    L_0x00bb:
        return r2;
    L_0x00bc:
        if (r3 != 0) goto L_0x00bf;
    L_0x00be:
        return r0;
    L_0x00bf:
        r1 = new java.lang.StringBuilder;	 Catch:{ NumberFormatException -> 0x00f2 }
        r1.<init>();	 Catch:{ NumberFormatException -> 0x00f2 }
        r2 = ".";
        r1.append(r2);	 Catch:{ NumberFormatException -> 0x00f2 }
        r1.append(r3);	 Catch:{ NumberFormatException -> 0x00f2 }
        r1 = r1.toString();	 Catch:{ NumberFormatException -> 0x00f2 }
        r1 = java.lang.Double.parseDouble(r1);	 Catch:{ NumberFormatException -> 0x00f2 }
        r3 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        r1 = r1 * r3;
        r1 = (int) r1;	 Catch:{ NumberFormatException -> 0x00f2 }
        if (r1 < 0) goto L_0x00f2;
    L_0x00de:
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r1 >= r2) goto L_0x00f2;
    L_0x00e2:
        r2 = java.util.Calendar.getInstance();	 Catch:{ NumberFormatException -> 0x00f2 }
        r2.setTime(r0);	 Catch:{ NumberFormatException -> 0x00f2 }
        r3 = 14;
        r2.set(r3, r1);	 Catch:{ NumberFormatException -> 0x00f2 }
        r0 = r2.getTime();	 Catch:{ NumberFormatException -> 0x00f2 }
    L_0x00f2:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.drew.metadata.Directory.getDate(int, java.lang.String, java.util.TimeZone):java.util.Date");
    }

    @Nullable
    public Rational getRational(int i) {
        Object object = getObject(i);
        Rational rational = null;
        if (object == null) {
            return null;
        }
        if (object instanceof Rational) {
            return (Rational) object;
        }
        if (object instanceof Integer) {
            return new Rational((long) ((Integer) object).intValue(), 1);
        }
        if (object instanceof Long) {
            rational = new Rational(((Long) object).longValue(), 1);
        }
        return rational;
    }

    @Nullable
    public Rational[] getRationalArray(int i) {
        Object object = getObject(i);
        if (object != null && (object instanceof Rational[])) {
            return (Rational[]) object;
        }
        return null;
    }

    @Nullable
    public String getString(int i) {
        Object object = getObject(i);
        if (object == null) {
            return null;
        }
        if (object instanceof Rational) {
            return ((Rational) object).toSimpleString(true);
        }
        boolean isArray = object.getClass().isArray();
        String str = _floatFormatPattern;
        if (isArray) {
            int length = Array.getLength(object);
            Class componentType = object.getClass().getComponentType();
            StringBuilder stringBuilder = new StringBuilder();
            int i2 = 0;
            if (Object.class.isAssignableFrom(componentType)) {
                while (i2 < length) {
                    if (i2 != 0) {
                        stringBuilder.append(' ');
                    }
                    stringBuilder.append(Array.get(object, i2).toString());
                    i2++;
                }
            } else if (componentType.getName().equals("int")) {
                while (i2 < length) {
                    if (i2 != 0) {
                        stringBuilder.append(' ');
                    }
                    stringBuilder.append(Array.getInt(object, i2));
                    i2++;
                }
            } else if (componentType.getName().equals("short")) {
                while (i2 < length) {
                    if (i2 != 0) {
                        stringBuilder.append(' ');
                    }
                    stringBuilder.append(Array.getShort(object, i2));
                    i2++;
                }
            } else if (componentType.getName().equals("long")) {
                while (i2 < length) {
                    if (i2 != 0) {
                        stringBuilder.append(' ');
                    }
                    stringBuilder.append(Array.getLong(object, i2));
                    i2++;
                }
            } else {
                String str2 = "0";
                String str3 = "-0";
                DecimalFormat decimalFormat;
                if (componentType.getName().equals("float")) {
                    decimalFormat = new DecimalFormat(str);
                    while (i2 < length) {
                        if (i2 != 0) {
                            stringBuilder.append(' ');
                        }
                        str = decimalFormat.format((double) Array.getFloat(object, i2));
                        if (str.equals(str3)) {
                            str = str2;
                        }
                        stringBuilder.append(str);
                        i2++;
                    }
                } else if (componentType.getName().equals("double")) {
                    decimalFormat = new DecimalFormat(str);
                    while (i2 < length) {
                        if (i2 != 0) {
                            stringBuilder.append(' ');
                        }
                        str = decimalFormat.format(Array.getDouble(object, i2));
                        if (str.equals(str3)) {
                            str = str2;
                        }
                        stringBuilder.append(str);
                        i2++;
                    }
                } else if (componentType.getName().equals("byte")) {
                    while (i2 < length) {
                        if (i2 != 0) {
                            stringBuilder.append(' ');
                        }
                        stringBuilder.append(Array.getByte(object, i2) & 255);
                        i2++;
                    }
                } else {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Unexpected array component type: ");
                    stringBuilder2.append(componentType.getName());
                    addError(stringBuilder2.toString());
                }
            }
            return stringBuilder.toString();
        } else if (object instanceof Double) {
            return new DecimalFormat(str).format(((Double) object).doubleValue());
        } else {
            if (object instanceof Float) {
                return new DecimalFormat(str).format((double) ((Float) object).floatValue());
            }
            return object.toString();
        }
    }

    @Nullable
    public String getString(int i, String str) {
        byte[] byteArray = getByteArray(i);
        if (byteArray == null) {
            return null;
        }
        try {
            return new String(byteArray, str);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    @Nullable
    public StringValue getStringValue(int i) {
        Object object = getObject(i);
        return object instanceof StringValue ? (StringValue) object : null;
    }

    @Nullable
    public Object getObject(int i) {
        return this._tagMap.get(Integer.valueOf(i));
    }

    @NotNull
    public String getTagName(int i) {
        HashMap tagNameMap = getTagNameMap();
        if (tagNameMap.containsKey(Integer.valueOf(i))) {
            return (String) tagNameMap.get(Integer.valueOf(i));
        }
        StringBuilder stringBuilder;
        String toHexString = Integer.toHexString(i);
        while (toHexString.length() < 4) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("0");
            stringBuilder.append(toHexString);
            toHexString = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown tag (0x");
        stringBuilder.append(toHexString);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean hasTagName(int i) {
        return getTagNameMap().containsKey(Integer.valueOf(i));
    }

    @Nullable
    public String getDescription(int i) {
        return this._descriptor.getDescription(i);
    }

    public String toString() {
        Object[] objArr = new Object[3];
        objArr[0] = getName();
        objArr[1] = Integer.valueOf(this._tagMap.size());
        objArr[2] = this._tagMap.size() == 1 ? "tag" : "tags";
        return String.format("%s Directory (%d %s)", objArr);
    }
}
