package com.drew.imaging.jpeg;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class JpegSegmentData {
    @NotNull
    private final HashMap<Byte, List<byte[]>> _segmentDataMap = new HashMap(10);

    public void addSegment(byte b, @NotNull byte[] bArr) {
        getOrCreateSegmentList(b).add(bArr);
    }

    public Iterable<JpegSegmentType> getSegmentTypes() {
        Iterable hashSet = new HashSet();
        for (Byte b : this._segmentDataMap.keySet()) {
            JpegSegmentType fromByte = JpegSegmentType.fromByte(b.byteValue());
            if (fromByte != null) {
                hashSet.add(fromByte);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Should not have a segmentTypeByte that is not in the enum: ");
                stringBuilder.append(Integer.toHexString(b.byteValue()));
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        return hashSet;
    }

    @Nullable
    public byte[] getSegment(byte b) {
        return getSegment(b, 0);
    }

    @Nullable
    public byte[] getSegment(@NotNull JpegSegmentType jpegSegmentType) {
        return getSegment(jpegSegmentType.byteValue, 0);
    }

    @Nullable
    public byte[] getSegment(@NotNull JpegSegmentType jpegSegmentType, int i) {
        return getSegment(jpegSegmentType.byteValue, i);
    }

    @Nullable
    public byte[] getSegment(byte b, int i) {
        List segmentList = getSegmentList(b);
        return (segmentList == null || segmentList.size() <= i) ? null : (byte[]) segmentList.get(i);
    }

    @NotNull
    public Iterable<byte[]> getSegments(@NotNull JpegSegmentType jpegSegmentType) {
        return getSegments(jpegSegmentType.byteValue);
    }

    @NotNull
    public Iterable<byte[]> getSegments(byte b) {
        Iterable<byte[]> segmentList = getSegmentList(b);
        return segmentList == null ? new ArrayList() : segmentList;
    }

    @Nullable
    private List<byte[]> getSegmentList(byte b) {
        return (List) this._segmentDataMap.get(Byte.valueOf(b));
    }

    @NotNull
    private List<byte[]> getOrCreateSegmentList(byte b) {
        if (this._segmentDataMap.containsKey(Byte.valueOf(b))) {
            return (List) this._segmentDataMap.get(Byte.valueOf(b));
        }
        ArrayList arrayList = new ArrayList();
        this._segmentDataMap.put(Byte.valueOf(b), arrayList);
        return arrayList;
    }

    public int getSegmentCount(@NotNull JpegSegmentType jpegSegmentType) {
        return getSegmentCount(jpegSegmentType.byteValue);
    }

    public int getSegmentCount(byte b) {
        List segmentList = getSegmentList(b);
        if (segmentList == null) {
            return 0;
        }
        return segmentList.size();
    }

    public void removeSegmentOccurrence(@NotNull JpegSegmentType jpegSegmentType, int i) {
        removeSegmentOccurrence(jpegSegmentType.byteValue, i);
    }

    public void removeSegmentOccurrence(byte b, int i) {
        ((List) this._segmentDataMap.get(Byte.valueOf(b))).remove(i);
    }

    public void removeSegment(@NotNull JpegSegmentType jpegSegmentType) {
        removeSegment(jpegSegmentType.byteValue);
    }

    public void removeSegment(byte b) {
        this._segmentDataMap.remove(Byte.valueOf(b));
    }

    public boolean containsSegment(@NotNull JpegSegmentType jpegSegmentType) {
        return containsSegment(jpegSegmentType.byteValue);
    }

    public boolean containsSegment(byte b) {
        return this._segmentDataMap.containsKey(Byte.valueOf(b));
    }
}
