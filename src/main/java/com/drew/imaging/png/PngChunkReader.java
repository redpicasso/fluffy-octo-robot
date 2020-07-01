package com.drew.imaging.png;

import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PngChunkReader {
    private static final byte[] PNG_SIGNATURE_BYTES = new byte[]{(byte) -119, (byte) 80, (byte) 78, (byte) 71, Ascii.CR, (byte) 10, Ascii.SUB, (byte) 10};

    public Iterable<PngChunk> extract(@NotNull SequentialReader sequentialReader, @Nullable Set<PngChunkType> set) throws PngProcessingException, IOException {
        sequentialReader.setMotorolaByteOrder(true);
        byte[] bArr = PNG_SIGNATURE_BYTES;
        if (Arrays.equals(bArr, sequentialReader.getBytes(bArr.length))) {
            Iterable arrayList = new ArrayList();
            Set hashSet = new HashSet();
            Object obj = null;
            Object obj2 = null;
            while (obj == null) {
                int int32 = sequentialReader.getInt32();
                if (int32 >= 0) {
                    PngChunkType pngChunkType = new PngChunkType(sequentialReader.getBytes(4));
                    Object obj3 = (set == null || set.contains(pngChunkType)) ? 1 : null;
                    byte[] bytes = sequentialReader.getBytes(int32);
                    sequentialReader.skip(4);
                    if (obj3 == null || !hashSet.contains(pngChunkType) || pngChunkType.areMultipleAllowed()) {
                        if (pngChunkType.equals(PngChunkType.IHDR)) {
                            obj2 = 1;
                        } else if (obj2 == null) {
                            throw new PngProcessingException(String.format("First chunk should be '%s', but '%s' was observed", new Object[]{PngChunkType.IHDR, pngChunkType}));
                        }
                        if (pngChunkType.equals(PngChunkType.IEND)) {
                            obj = 1;
                        }
                        if (obj3 != null) {
                            arrayList.add(new PngChunk(pngChunkType, bytes));
                        }
                        hashSet.add(pngChunkType);
                    } else {
                        throw new PngProcessingException(String.format("Observed multiple instances of PNG chunk '%s', for which multiples are not allowed", new Object[]{pngChunkType}));
                    }
                }
                throw new PngProcessingException("PNG chunk length exceeds maximum");
            }
            return arrayList;
        }
        throw new PngProcessingException("PNG signature mismatch");
    }
}
