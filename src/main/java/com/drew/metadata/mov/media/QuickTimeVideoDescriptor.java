package com.drew.metadata.mov.media;

import com.drew.lang.annotations.NotNull;
import com.drew.metadata.mov.QuickTimeDescriptor;
import com.drew.metadata.mov.QuickTimeDirectory;

public class QuickTimeVideoDescriptor extends QuickTimeDescriptor {
    public QuickTimeVideoDescriptor(@NotNull QuickTimeVideoDirectory quickTimeVideoDirectory) {
        super(quickTimeVideoDirectory);
    }

    public String getDescription(int i) {
        if (i == 4 || i == 5) {
            return getPixelDescription(i);
        }
        if (i == 9) {
            return getDepthDescription(i);
        }
        if (i == 11) {
            return getGraphicsModeDescription();
        }
        if (i != 13) {
            return super.getDescription(i);
        }
        return getColorTableDescription(i);
    }

    private String getPixelDescription(int i) {
        String string = ((QuickTimeDirectory) this._directory).getString(i);
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" pixels");
        return stringBuilder.toString();
    }

    private String getDepthDescription(int i) {
        Integer integer = ((QuickTimeDirectory) this._directory).getInteger(i);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        StringBuilder stringBuilder;
        if (intValue == 34 || intValue == 36 || intValue == 40) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(integer.intValue() - 32);
            stringBuilder.append("-bit grayscale");
            return stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown (");
        stringBuilder.append(integer);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private String getColorTableDescription(int i) {
        Integer integer = ((QuickTimeDirectory) this._directory).getInteger(i);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == -1) {
            return ((QuickTimeDirectory) this._directory).getInteger(9).intValue() < 16 ? "Default" : "None";
        } else {
            if (intValue == 0) {
                return "Color table within file";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown (");
            stringBuilder.append(integer);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private String getGraphicsModeDescription() {
        Integer integer = ((QuickTimeDirectory) this._directory).getInteger(11);
        if (integer == null) {
            return null;
        }
        int intValue = integer.intValue();
        if (intValue == 0) {
            return "Copy";
        }
        if (intValue == 32) {
            return "Blend";
        }
        if (intValue == 36) {
            return "Transparent";
        }
        if (intValue == 64) {
            return "Dither copy";
        }
        switch (intValue) {
            case 256:
                return "Straight alpha";
            case 257:
                return "Premul white alpha";
            case 258:
                return "Premul black alpha";
            case 259:
                return "Composition (dither copy)";
            case 260:
                return "Straight alpha blend";
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown (");
                stringBuilder.append(integer);
                stringBuilder.append(")");
                return stringBuilder.toString();
        }
    }
}
