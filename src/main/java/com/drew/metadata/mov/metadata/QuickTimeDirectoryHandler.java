package com.drew.metadata.mov.metadata;

import com.drew.imaging.quicktime.QuickTimeHandler;
import com.drew.lang.SequentialByteArrayReader;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Metadata;
import com.drew.metadata.mov.QuickTimeMetadataHandler;
import com.drew.metadata.mov.atoms.Atom;
import java.io.IOException;

public class QuickTimeDirectoryHandler extends QuickTimeMetadataHandler {
    private String currentData;

    protected void processKeys(@NotNull SequentialByteArrayReader sequentialByteArrayReader) throws IOException {
    }

    public QuickTimeDirectoryHandler(Metadata metadata) {
        super(metadata);
    }

    protected boolean shouldAcceptAtom(@NotNull Atom atom) {
        return atom.type.equals("data");
    }

    protected boolean shouldAcceptContainer(@NotNull Atom atom) {
        return QuickTimeMetadataDirectory._tagIntegerMap.containsKey(atom.type) || atom.type.equals("ilst");
    }

    protected QuickTimeHandler processAtom(@NotNull Atom atom, @Nullable byte[] bArr) throws IOException {
        if (bArr != null) {
            SequentialByteArrayReader sequentialByteArrayReader = new SequentialByteArrayReader(bArr);
            if (!atom.type.equals("data") || this.currentData == null) {
                this.currentData = new String(sequentialByteArrayReader.getBytes(4));
            } else {
                processData(bArr, sequentialByteArrayReader);
            }
        } else if (QuickTimeMetadataDirectory._tagIntegerMap.containsKey(atom.type)) {
            this.currentData = atom.type;
        } else {
            this.currentData = null;
        }
        return this;
    }

    protected void processData(@NotNull byte[] bArr, @NotNull SequentialByteArrayReader sequentialByteArrayReader) throws IOException {
        sequentialByteArrayReader.skip(8);
        this.directory.setString(((Integer) QuickTimeMetadataDirectory._tagIntegerMap.get(this.currentData)).intValue(), new String(sequentialByteArrayReader.getBytes(bArr.length - 8)));
    }
}
