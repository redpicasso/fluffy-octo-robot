package com.drew.metadata.exif.makernotes;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.StringValue;
import com.drew.metadata.TagDescriptor;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReconyxUltraFireMakernoteDescriptor extends TagDescriptor<ReconyxUltraFireMakernoteDirectory> {
    public ReconyxUltraFireMakernoteDescriptor(@NotNull ReconyxUltraFireMakernoteDirectory reconyxUltraFireMakernoteDirectory) {
        super(reconyxUltraFireMakernoteDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        String str = "0x%08X";
        String str2 = null;
        String str3 = "%d";
        switch (i) {
            case 0:
                return ((ReconyxUltraFireMakernoteDirectory) this._directory).getString(i);
            case 10:
                return String.format(str, new Object[]{((ReconyxUltraFireMakernoteDirectory) this._directory).getInteger(i)});
            case 14:
                return String.format(str3, new Object[]{((ReconyxUltraFireMakernoteDirectory) this._directory).getInteger(i)});
            case 18:
                return String.format(str, new Object[]{((ReconyxUltraFireMakernoteDirectory) this._directory).getInteger(i)});
            case 22:
                return String.format(str3, new Object[]{((ReconyxUltraFireMakernoteDirectory) this._directory).getInteger(i)});
            case 24:
            case 31:
            case 38:
            case 45:
            case 52:
                return ((ReconyxUltraFireMakernoteDirectory) this._directory).getString(i);
            case 53:
                if (((ReconyxUltraFireMakernoteDirectory) this._directory).getIntArray(i) == null) {
                    return null;
                }
                return String.format("%d/%d", new Object[]{Integer.valueOf(((ReconyxUltraFireMakernoteDirectory) this._directory).getIntArray(i)[0]), Integer.valueOf(((ReconyxUltraFireMakernoteDirectory) this._directory).getIntArray(i)[1])});
            case 55:
                return String.format(str3, new Object[]{((ReconyxUltraFireMakernoteDirectory) this._directory).getInteger(i)});
            case 59:
                String string = ((ReconyxUltraFireMakernoteDirectory) this._directory).getString(i);
                try {
                    DateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                    return simpleDateFormat.format(simpleDateFormat.parse(string));
                } catch (ParseException unused) {
                    return null;
                }
            case 67:
                return getIndexedDescription(i, "New", "Waxing Crescent", "First Quarter", "Waxing Gibbous", "Full", "Waning Gibbous", "Last Quarter", "Waning Crescent");
            case 68:
            case 70:
                return String.format(str3, new Object[]{((ReconyxUltraFireMakernoteDirectory) this._directory).getInteger(i)});
            case 72:
                return getIndexedDescription(i, "Off", "On");
            case 73:
                Double doubleObject = ((ReconyxUltraFireMakernoteDirectory) this._directory).getDoubleObject(i);
                DecimalFormat decimalFormat = new DecimalFormat("0.000");
                if (doubleObject != null) {
                    str2 = decimalFormat.format(doubleObject);
                }
                return str2;
            case 75:
                StringValue stringValue = ((ReconyxUltraFireMakernoteDirectory) this._directory).getStringValue(i);
                if (stringValue == null) {
                    return null;
                }
                return stringValue.toString();
            case 80:
                return ((ReconyxUltraFireMakernoteDirectory) this._directory).getString(i);
            default:
                return super.getDescription(i);
        }
    }
}
