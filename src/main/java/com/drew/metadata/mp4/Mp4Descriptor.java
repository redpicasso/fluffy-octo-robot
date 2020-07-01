package com.drew.metadata.mp4;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Directory;
import com.drew.metadata.TagDescriptor;
import java.util.ArrayList;
import java.util.Arrays;

public class Mp4Descriptor<T extends Directory> extends TagDescriptor<Mp4Directory> {
    public Mp4Descriptor(@NotNull Mp4Directory mp4Directory) {
        super(mp4Directory);
    }

    public String getDescription(int i) {
        if (i == 1) {
            return getMajorBrandDescription();
        }
        if (i == 3) {
            return getCompatibleBrandsDescription();
        }
        if (i != 259) {
            return ((Mp4Directory) this._directory).getString(i);
        }
        return getDurationDescription();
    }

    private String getMajorBrandDescription() {
        byte[] byteArray = ((Mp4Directory) this._directory).getByteArray(1);
        if (byteArray == null) {
            return null;
        }
        return Mp4Dictionary.lookup(1, new String(byteArray));
    }

    private String getCompatibleBrandsDescription() {
        String[] stringArray = ((Mp4Directory) this._directory).getStringArray(3);
        if (stringArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : stringArray) {
            Object obj2;
            String lookup = Mp4Dictionary.lookup(1, obj2);
            if (lookup != null) {
                obj2 = lookup;
            }
            arrayList.add(obj2);
        }
        return Arrays.toString(arrayList.toArray());
    }

    private String getDurationDescription() {
        Long longObject = ((Mp4Directory) this._directory).getLongObject(259);
        if (longObject == null) {
            return null;
        }
        Integer valueOf = Integer.valueOf((int) Math.ceil((((double) longObject.longValue()) / Math.pow(60.0d, 0.0d)) - ((double) (Integer.valueOf((int) ((((double) longObject.longValue()) / Math.pow(60.0d, 1.0d)) - ((double) (Integer.valueOf((int) (((double) longObject.longValue()) / Math.pow(60.0d, 2.0d))).intValue() * 60)))).intValue() * 60))));
        return String.format("%1$02d:%2$02d:%3$02d", new Object[]{r1, r2, valueOf});
    }
}
