package com.drew.metadata.gif;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class GifAnimationDescriptor extends TagDescriptor<GifAnimationDirectory> {
    public GifAnimationDescriptor(@NotNull GifAnimationDirectory gifAnimationDirectory) {
        super(gifAnimationDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i != 1) {
            return super.getDescription(i);
        }
        return getIterationCountDescription();
    }

    @Nullable
    public String getIterationCountDescription() {
        Integer integer = ((GifAnimationDirectory) this._directory).getInteger(1);
        if (integer == null) {
            return null;
        }
        String str;
        if (integer.intValue() == 0) {
            str = "Infinite";
        } else if (integer.intValue() == 1) {
            str = "Once";
        } else if (integer.intValue() == 2) {
            str = "Twice";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(integer.toString());
            stringBuilder.append(" times");
            str = stringBuilder.toString();
        }
        return str;
    }
}
