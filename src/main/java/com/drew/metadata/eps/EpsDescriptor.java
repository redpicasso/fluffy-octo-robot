package com.drew.metadata.eps;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class EpsDescriptor extends TagDescriptor<EpsDirectory> {
    public EpsDescriptor(@NotNull EpsDirectory epsDirectory) {
        super(epsDirectory);
    }

    public String getDescription(int i) {
        switch (i) {
            case 28:
            case 29:
                return getPixelDescription(i);
            case 30:
                return getColorTypeDescription();
            case 32:
            case 33:
                return getByteSizeDescription(i);
            default:
                return ((EpsDirectory) this._directory).getString(i);
        }
    }

    public String getPixelDescription(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((EpsDirectory) this._directory).getString(i));
        stringBuilder.append(" pixels");
        return stringBuilder.toString();
    }

    public String getByteSizeDescription(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((EpsDirectory) this._directory).getString(i));
        stringBuilder.append(" bytes");
        return stringBuilder.toString();
    }

    @Nullable
    public String getColorTypeDescription() {
        return getIndexedDescription(30, 1, "Grayscale", "Lab", "RGB", "CMYK");
    }
}
