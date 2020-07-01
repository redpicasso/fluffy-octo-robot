package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Indicator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.internal.auth.zzaz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Class(creator = "AccountTransferMsgCreator")
public final class zzl extends zzaz {
    public static final Creator<zzl> CREATOR = new zzm();
    private static final HashMap<String, Field<?, ?>> zzaz;
    @Indicator
    private final Set<Integer> zzba;
    @SafeParcelable.Field(getter = "getAuthenticatorDatas", id = 2)
    private ArrayList<zzr> zzbb;
    @SafeParcelable.Field(getter = "getRequestType", id = 3)
    private int zzbc;
    @SafeParcelable.Field(getter = "getProgress", id = 4)
    private zzo zzbd;
    @VersionField(id = 1)
    private final int zzv;

    @Constructor
    zzl(@Indicator Set<Integer> set, @Param(id = 1) int i, @Param(id = 2) ArrayList<zzr> arrayList, @Param(id = 3) int i2, @Param(id = 4) zzo zzo) {
        this.zzba = set;
        this.zzv = i;
        this.zzbb = arrayList;
        this.zzbc = i2;
        this.zzbd = zzo;
    }

    public zzl() {
        this.zzba = new HashSet(1);
        this.zzv = 1;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        Set set = this.zzba;
        if (set.contains(Integer.valueOf(1))) {
            SafeParcelWriter.writeInt(parcel, 1, this.zzv);
        }
        if (set.contains(Integer.valueOf(2))) {
            SafeParcelWriter.writeTypedList(parcel, 2, this.zzbb, true);
        }
        if (set.contains(Integer.valueOf(3))) {
            SafeParcelWriter.writeInt(parcel, 3, this.zzbc);
        }
        if (set.contains(Integer.valueOf(4))) {
            SafeParcelWriter.writeParcelable(parcel, 4, this.zzbd, i, true);
        }
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    protected final boolean isFieldSet(Field field) {
        return this.zzba.contains(Integer.valueOf(field.getSafeParcelableFieldId()));
    }

    protected final Object getFieldValue(Field field) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 1) {
            return Integer.valueOf(this.zzv);
        }
        if (safeParcelableFieldId == 2) {
            return this.zzbb;
        }
        if (safeParcelableFieldId == 4) {
            return this.zzbd;
        }
        int safeParcelableFieldId2 = field.getSafeParcelableFieldId();
        StringBuilder stringBuilder = new StringBuilder(37);
        stringBuilder.append("Unknown SafeParcelable id=");
        stringBuilder.append(safeParcelableFieldId2);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final <T extends FastJsonResponse> void addConcreteTypeArrayInternal(Field<?, ?> field, String str, ArrayList<T> arrayList) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 2) {
            this.zzbb = arrayList;
            this.zzba.add(Integer.valueOf(safeParcelableFieldId));
            return;
        }
        throw new IllegalArgumentException(String.format("Field with id=%d is not a known ConcreteTypeArray type. Found %s", new Object[]{Integer.valueOf(safeParcelableFieldId), arrayList.getClass().getCanonicalName()}));
    }

    public final <T extends FastJsonResponse> void addConcreteTypeInternal(Field<?, ?> field, String str, T t) {
        int safeParcelableFieldId = field.getSafeParcelableFieldId();
        if (safeParcelableFieldId == 4) {
            this.zzbd = (zzo) t;
            this.zzba.add(Integer.valueOf(safeParcelableFieldId));
            return;
        }
        throw new IllegalArgumentException(String.format("Field with id=%d is not a known custom type. Found %s", new Object[]{Integer.valueOf(safeParcelableFieldId), t.getClass().getCanonicalName()}));
    }

    public final /* synthetic */ Map getFieldMappings() {
        return zzaz;
    }

    static {
        HashMap hashMap = new HashMap();
        zzaz = hashMap;
        String str = "authenticatorData";
        hashMap.put(str, Field.forConcreteTypeArray(str, 2, zzr.class));
        hashMap = zzaz;
        str = NotificationCompat.CATEGORY_PROGRESS;
        hashMap.put(str, Field.forConcreteType(str, 4, zzo.class));
    }
}
