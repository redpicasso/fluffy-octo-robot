package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class zzm implements Creator<zzl> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzl[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        ArrayList arrayList = null;
        zzo zzo = arrayList;
        int i2 = 0;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(readHeader);
            if (fieldId == 1) {
                i = SafeParcelReader.readInt(parcel, readHeader);
                hashSet.add(Integer.valueOf(1));
            } else if (fieldId == 2) {
                arrayList = SafeParcelReader.createTypedList(parcel, readHeader, zzr.CREATOR);
                hashSet.add(Integer.valueOf(2));
            } else if (fieldId == 3) {
                i2 = SafeParcelReader.readInt(parcel, readHeader);
                hashSet.add(Integer.valueOf(3));
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, readHeader);
            } else {
                zzo = (zzo) SafeParcelReader.createParcelable(parcel, readHeader, zzo.CREATOR);
                hashSet.add(Integer.valueOf(4));
            }
        }
        if (parcel.dataPosition() == validateObjectHeader) {
            return new zzl(hashSet, i, arrayList, i2, zzo);
        }
        StringBuilder stringBuilder = new StringBuilder(37);
        stringBuilder.append("Overread allowed size end=");
        stringBuilder.append(validateObjectHeader);
        throw new ParseException(stringBuilder.toString(), parcel);
    }
}
