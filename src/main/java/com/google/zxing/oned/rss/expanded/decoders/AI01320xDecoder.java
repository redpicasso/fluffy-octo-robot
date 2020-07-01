package com.google.zxing.oned.rss.expanded.decoders;

import com.drew.metadata.photoshop.PhotoshopDirectory;
import com.google.zxing.common.BitArray;

final class AI01320xDecoder extends AI013x0xDecoder {
    protected int checkWeight(int i) {
        return i < PhotoshopDirectory.TAG_PRINT_FLAGS_INFO ? i : i - PhotoshopDirectory.TAG_PRINT_FLAGS_INFO;
    }

    AI01320xDecoder(BitArray bitArray) {
        super(bitArray);
    }

    protected void addWeightCode(StringBuilder stringBuilder, int i) {
        if (i < PhotoshopDirectory.TAG_PRINT_FLAGS_INFO) {
            stringBuilder.append("(3202)");
        } else {
            stringBuilder.append("(3203)");
        }
    }
}
