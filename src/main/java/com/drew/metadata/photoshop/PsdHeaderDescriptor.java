package com.drew.metadata.photoshop;

import com.bumptech.glide.Registry;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class PsdHeaderDescriptor extends TagDescriptor<PsdHeaderDirectory> {
    public PsdHeaderDescriptor(@NotNull PsdHeaderDirectory psdHeaderDirectory) {
        super(psdHeaderDirectory);
    }

    public String getDescription(int i) {
        if (i == 1) {
            return getChannelCountDescription();
        }
        if (i == 2) {
            return getImageHeightDescription();
        }
        if (i == 3) {
            return getImageWidthDescription();
        }
        if (i == 4) {
            return getBitsPerChannelDescription();
        }
        if (i != 5) {
            return super.getDescription(i);
        }
        return getColorModeDescription();
    }

    @Nullable
    public String getChannelCountDescription() {
        Integer integer = ((PsdHeaderDirectory) this._directory).getInteger(1);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer);
        stringBuilder.append(" channel");
        stringBuilder.append(integer.intValue() == 1 ? "" : "s");
        return stringBuilder.toString();
    }

    @Nullable
    public String getBitsPerChannelDescription() {
        Integer integer = ((PsdHeaderDirectory) this._directory).getInteger(4);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer);
        stringBuilder.append(" bit");
        stringBuilder.append(integer.intValue() == 1 ? "" : "s");
        stringBuilder.append(" per channel");
        return stringBuilder.toString();
    }

    @Nullable
    public String getColorModeDescription() {
        return getIndexedDescription(5, Registry.BUCKET_BITMAP, "Grayscale", "Indexed", "RGB", "CMYK", null, null, "Multichannel", "Duotone", "Lab");
    }

    @Nullable
    public String getImageHeightDescription() {
        Integer integer = ((PsdHeaderDirectory) this._directory).getInteger(2);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer);
        stringBuilder.append(" pixel");
        stringBuilder.append(integer.intValue() == 1 ? "" : "s");
        return stringBuilder.toString();
    }

    @Nullable
    public String getImageWidthDescription() {
        String str = null;
        try {
            Integer integer = ((PsdHeaderDirectory) this._directory).getInteger(3);
            if (integer == null) {
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(integer);
            stringBuilder.append(" pixel");
            stringBuilder.append(integer.intValue() == 1 ? "" : "s");
            str = stringBuilder.toString();
        } catch (Exception unused) {
            return str;
        }
    }
}
