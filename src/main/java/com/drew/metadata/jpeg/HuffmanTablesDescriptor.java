package com.drew.metadata.jpeg;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

public class HuffmanTablesDescriptor extends TagDescriptor<HuffmanTablesDirectory> {
    public HuffmanTablesDescriptor(@NotNull HuffmanTablesDirectory huffmanTablesDirectory) {
        super(huffmanTablesDirectory);
    }

    @Nullable
    public String getDescription(int i) {
        if (i != 1) {
            return super.getDescription(i);
        }
        return getNumberOfTablesDescription();
    }

    @Nullable
    public String getNumberOfTablesDescription() {
        Integer integer = ((HuffmanTablesDirectory) this._directory).getInteger(1);
        if (integer == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(integer);
        stringBuilder.append(integer.intValue() == 1 ? " Huffman table" : " Huffman tables");
        return stringBuilder.toString();
    }
}
