package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.sqlite.CursorWrapper;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@KeepName
@KeepForSdk
@Class(creator = "DataHolderCreator", validate = true)
/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class DataHolder extends AbstractSafeParcelable implements Closeable {
    @KeepForSdk
    public static final Creator<DataHolder> CREATOR = new zac();
    private static final Builder zamb = new zab(new String[0], null);
    private boolean mClosed;
    @VersionField(id = 1000)
    private final int zali;
    @Field(getter = "getColumns", id = 1)
    private final String[] zalt;
    private Bundle zalu;
    @Field(getter = "getWindows", id = 2)
    private final CursorWindow[] zalv;
    @Field(getter = "getStatusCode", id = 3)
    private final int zalw;
    @Field(getter = "getMetadata", id = 4)
    private final Bundle zalx;
    private int[] zaly;
    private int zalz;
    private boolean zama;

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static class Builder {
        private final String[] zalt;
        private final ArrayList<HashMap<String, Object>> zamc;
        private final String zamd;
        private final HashMap<Object, Integer> zame;
        private boolean zamf;
        private String zamg;

        private Builder(String[] strArr, String str) {
            this.zalt = (String[]) Preconditions.checkNotNull(strArr);
            this.zamc = new ArrayList();
            this.zamd = str;
            this.zame = new HashMap();
            this.zamf = false;
            this.zamg = null;
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x0031  */
        public com.google.android.gms.common.data.DataHolder.Builder zaa(java.util.HashMap<java.lang.String, java.lang.Object> r5) {
            /*
            r4 = this;
            com.google.android.gms.common.internal.Asserts.checkNotNull(r5);
            r0 = r4.zamd;
            r1 = -1;
            if (r0 != 0) goto L_0x000a;
        L_0x0008:
            r0 = -1;
            goto L_0x002f;
        L_0x000a:
            r0 = r5.get(r0);
            if (r0 != 0) goto L_0x0011;
        L_0x0010:
            goto L_0x0008;
        L_0x0011:
            r2 = r4.zame;
            r2 = r2.get(r0);
            r2 = (java.lang.Integer) r2;
            if (r2 != 0) goto L_0x002b;
        L_0x001b:
            r2 = r4.zame;
            r3 = r4.zamc;
            r3 = r3.size();
            r3 = java.lang.Integer.valueOf(r3);
            r2.put(r0, r3);
            goto L_0x0008;
        L_0x002b:
            r0 = r2.intValue();
        L_0x002f:
            if (r0 != r1) goto L_0x0037;
        L_0x0031:
            r0 = r4.zamc;
            r0.add(r5);
            goto L_0x0041;
        L_0x0037:
            r1 = r4.zamc;
            r1.remove(r0);
            r1 = r4.zamc;
            r1.add(r0, r5);
        L_0x0041:
            r5 = 0;
            r4.zamf = r5;
            return r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.DataHolder.Builder.zaa(java.util.HashMap):com.google.android.gms.common.data.DataHolder$Builder");
        }

        @KeepForSdk
        public Builder withRow(ContentValues contentValues) {
            Asserts.checkNotNull(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Entry entry : contentValues.valueSet()) {
                hashMap.put((String) entry.getKey(), entry.getValue());
            }
            return zaa(hashMap);
        }

        @KeepForSdk
        public DataHolder build(int i) {
            return new DataHolder(this, i, null, null);
        }

        @KeepForSdk
        public DataHolder build(int i, Bundle bundle) {
            return new DataHolder(this, i, bundle, -1, null);
        }

        /* synthetic */ Builder(String[] strArr, String str, zab zab) {
            this(strArr, null);
        }
    }

    /* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
    public static class zaa extends RuntimeException {
        public zaa(String str) {
            super(str);
        }
    }

    @Constructor
    DataHolder(@Param(id = 1000) int i, @Param(id = 1) String[] strArr, @Param(id = 2) CursorWindow[] cursorWindowArr, @Param(id = 3) int i2, @Param(id = 4) Bundle bundle) {
        this.mClosed = false;
        this.zama = true;
        this.zali = i;
        this.zalt = strArr;
        this.zalv = cursorWindowArr;
        this.zalw = i2;
        this.zalx = bundle;
    }

    @KeepForSdk
    public DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.zama = true;
        this.zali = 1;
        this.zalt = (String[]) Preconditions.checkNotNull(strArr);
        this.zalv = (CursorWindow[]) Preconditions.checkNotNull(cursorWindowArr);
        this.zalw = i;
        this.zalx = bundle;
        zaby();
    }

    private DataHolder(CursorWrapper cursorWrapper, int i, Bundle bundle) {
        this(cursorWrapper.getColumnNames(), zaa(cursorWrapper), i, bundle);
    }

    @KeepForSdk
    public DataHolder(Cursor cursor, int i, Bundle bundle) {
        this(new CursorWrapper(cursor), i, bundle);
    }

    private DataHolder(Builder builder, int i, Bundle bundle) {
        this(builder.zalt, zaa(builder, -1), i, null);
    }

    private DataHolder(Builder builder, int i, Bundle bundle, int i2) {
        this(builder.zalt, zaa(builder, -1), i, bundle);
    }

    public final void zaby() {
        this.zalu = new Bundle();
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = this.zalt;
            if (i2 >= strArr.length) {
                break;
            }
            this.zalu.putInt(strArr[i2], i2);
            i2++;
        }
        this.zaly = new int[this.zalv.length];
        i2 = 0;
        while (true) {
            CursorWindow[] cursorWindowArr = this.zalv;
            if (i < cursorWindowArr.length) {
                this.zaly[i] = i2;
                i2 += this.zalv[i].getNumRows() - (i2 - cursorWindowArr[i].getStartPosition());
                i++;
            } else {
                this.zalz = i2;
                return;
            }
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringArray(parcel, 1, this.zalt, false);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zalv, i, false);
        SafeParcelWriter.writeInt(parcel, 3, getStatusCode());
        SafeParcelWriter.writeBundle(parcel, 4, getMetadata(), false);
        SafeParcelWriter.writeInt(parcel, 1000, this.zali);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        if ((i & 1) != 0) {
            close();
        }
    }

    @KeepForSdk
    public final int getStatusCode() {
        return this.zalw;
    }

    @KeepForSdk
    public final Bundle getMetadata() {
        return this.zalx;
    }

    private static CursorWindow[] zaa(CursorWrapper cursorWrapper) {
        ArrayList arrayList = new ArrayList();
        try {
            int i;
            int count = cursorWrapper.getCount();
            CursorWindow window = cursorWrapper.getWindow();
            if (window == null || window.getStartPosition() != 0) {
                i = 0;
            } else {
                window.acquireReference();
                cursorWrapper.setWindow(null);
                arrayList.add(window);
                i = window.getNumRows();
            }
            while (i < count && cursorWrapper.moveToPosition(i)) {
                CursorWindow window2 = cursorWrapper.getWindow();
                if (window2 != null) {
                    window2.acquireReference();
                    cursorWrapper.setWindow(null);
                } else {
                    window2 = new CursorWindow(false);
                    window2.setStartPosition(i);
                    cursorWrapper.fillWindow(i, window2);
                }
                if (window2.getNumRows() == 0) {
                    break;
                }
                arrayList.add(window2);
                i = window2.getStartPosition() + window2.getNumRows();
            }
            cursorWrapper.close();
            return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
        } catch (Throwable th) {
            cursorWrapper.close();
        }
    }

    private static CursorWindow[] zaa(Builder builder, int i) {
        int i2 = 0;
        if (builder.zalt.length == 0) {
            return new CursorWindow[0];
        }
        List zab;
        if (i < 0 || i >= builder.zamc.size()) {
            zab = builder.zamc;
        } else {
            zab = builder.zamc.subList(0, i);
        }
        int size = zab.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow);
        cursorWindow.setNumColumns(builder.zalt.length);
        CursorWindow cursorWindow2 = cursorWindow;
        int i3 = 0;
        Object obj = null;
        while (i3 < size) {
            try {
                StringBuilder stringBuilder;
                String str = "DataHolder";
                if (!cursorWindow2.allocRow()) {
                    stringBuilder = new StringBuilder(72);
                    stringBuilder.append("Allocating additional cursor window for large data set (row ");
                    stringBuilder.append(i3);
                    stringBuilder.append(")");
                    Log.d(str, stringBuilder.toString());
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i3);
                    cursorWindow2.setNumColumns(builder.zalt.length);
                    arrayList.add(cursorWindow2);
                    if (!cursorWindow2.allocRow()) {
                        Log.e(str, "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow2);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) zab.get(i3);
                boolean z = true;
                for (int i4 = 0; i4 < builder.zalt.length && z; i4++) {
                    String str2 = builder.zalt[i4];
                    Object obj2 = map.get(str2);
                    if (obj2 == null) {
                        z = cursorWindow2.putNull(i3, i4);
                    } else if (obj2 instanceof String) {
                        z = cursorWindow2.putString((String) obj2, i3, i4);
                    } else if (obj2 instanceof Long) {
                        z = cursorWindow2.putLong(((Long) obj2).longValue(), i3, i4);
                    } else if (obj2 instanceof Integer) {
                        z = cursorWindow2.putLong((long) ((Integer) obj2).intValue(), i3, i4);
                    } else if (obj2 instanceof Boolean) {
                        z = cursorWindow2.putLong(((Boolean) obj2).booleanValue() ? 1 : 0, i3, i4);
                    } else if (obj2 instanceof byte[]) {
                        z = cursorWindow2.putBlob((byte[]) obj2, i3, i4);
                    } else if (obj2 instanceof Double) {
                        z = cursorWindow2.putDouble(((Double) obj2).doubleValue(), i3, i4);
                    } else if (obj2 instanceof Float) {
                        z = cursorWindow2.putDouble((double) ((Float) obj2).floatValue(), i3, i4);
                    } else {
                        String valueOf = String.valueOf(obj2);
                        StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(str2).length() + 32) + String.valueOf(valueOf).length());
                        stringBuilder2.append("Unsupported object for column ");
                        stringBuilder2.append(str2);
                        stringBuilder2.append(": ");
                        stringBuilder2.append(valueOf);
                        throw new IllegalArgumentException(stringBuilder2.toString());
                    }
                }
                if (z) {
                    obj = null;
                } else if (obj == null) {
                    stringBuilder = new StringBuilder(74);
                    stringBuilder.append("Couldn't populate window data for row ");
                    stringBuilder.append(i3);
                    stringBuilder.append(" - allocating new window.");
                    Log.d(str, stringBuilder.toString());
                    cursorWindow2.freeLastRow();
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i3);
                    cursorWindow2.setNumColumns(builder.zalt.length);
                    arrayList.add(cursorWindow2);
                    i3--;
                    obj = 1;
                } else {
                    throw new zaa("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                }
                i3++;
            } catch (RuntimeException e) {
                i = arrayList.size();
                while (i2 < i) {
                    ((CursorWindow) arrayList.get(i2)).close();
                    i2++;
                }
                throw e;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    private final void zaa(String str, int i) {
        Bundle bundle = this.zalu;
        if (bundle == null || !bundle.containsKey(str)) {
            String str2 = "No such column: ";
            str = String.valueOf(str);
            throw new IllegalArgumentException(str.length() != 0 ? str2.concat(str) : new String(str2));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.zalz) {
            throw new CursorIndexOutOfBoundsException(i, this.zalz);
        }
    }

    @KeepForSdk
    public final boolean hasColumn(String str) {
        return this.zalu.containsKey(str);
    }

    @KeepForSdk
    public final long getLong(String str, int i, int i2) {
        zaa(str, i);
        return this.zalv[i2].getLong(i, this.zalu.getInt(str));
    }

    @KeepForSdk
    public final int getInteger(String str, int i, int i2) {
        zaa(str, i);
        return this.zalv[i2].getInt(i, this.zalu.getInt(str));
    }

    @KeepForSdk
    public final String getString(String str, int i, int i2) {
        zaa(str, i);
        return this.zalv[i2].getString(i, this.zalu.getInt(str));
    }

    @KeepForSdk
    public final boolean getBoolean(String str, int i, int i2) {
        zaa(str, i);
        return Long.valueOf(this.zalv[i2].getLong(i, this.zalu.getInt(str))).longValue() == 1;
    }

    public final float zaa(String str, int i, int i2) {
        zaa(str, i);
        return this.zalv[i2].getFloat(i, this.zalu.getInt(str));
    }

    public final double zab(String str, int i, int i2) {
        zaa(str, i);
        return this.zalv[i2].getDouble(i, this.zalu.getInt(str));
    }

    @KeepForSdk
    public final byte[] getByteArray(String str, int i, int i2) {
        zaa(str, i);
        return this.zalv[i2].getBlob(i, this.zalu.getInt(str));
    }

    public final void zaa(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zaa(str, i);
        this.zalv[i2].copyStringToBuffer(i, this.zalu.getInt(str), charArrayBuffer);
    }

    @KeepForSdk
    public final boolean hasNull(String str, int i, int i2) {
        zaa(str, i);
        return this.zalv[i2].isNull(i, this.zalu.getInt(str));
    }

    @KeepForSdk
    public final int getCount() {
        return this.zalz;
    }

    @KeepForSdk
    public final int getWindowIndex(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.zalz;
        Preconditions.checkState(z);
        while (true) {
            int[] iArr = this.zaly;
            if (i2 >= iArr.length) {
                break;
            } else if (i < iArr[i2]) {
                i2--;
                break;
            } else {
                i2++;
            }
        }
        return i2 == this.zaly.length ? i2 - 1 : i2;
    }

    @KeepForSdk
    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    @KeepForSdk
    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.zalv) {
                    close.close();
                }
            }
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (this.zama && this.zalv.length > 0 && !isClosed()) {
                close();
                String obj = toString();
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(obj).length() + 178);
                stringBuilder.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
                stringBuilder.append(obj);
                stringBuilder.append(")");
                Log.e("DataBuffer", stringBuilder.toString());
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    @KeepForSdk
    public static Builder builder(String[] strArr) {
        return new Builder(strArr, null, null);
    }

    @KeepForSdk
    public static DataHolder empty(int i) {
        return new DataHolder(zamb, i, null);
    }
}
