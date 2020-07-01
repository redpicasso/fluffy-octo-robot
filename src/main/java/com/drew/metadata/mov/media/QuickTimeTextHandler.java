package com.drew.metadata.mov.media;

import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.mov.QuickTimeMediaHandler;
import com.drew.metadata.mov.atoms.Atom;
import com.drew.metadata.mov.atoms.TextSampleDescriptionAtom;
import java.io.IOException;

public class QuickTimeTextHandler extends QuickTimeMediaHandler<QuickTimeTextDirectory> {
    protected String getMediaInformation() {
        return "gmhd";
    }

    protected void processMediaInformation(@NotNull SequentialReader sequentialReader, @NotNull Atom atom) throws IOException {
    }

    protected void processTimeToSample(@NotNull SequentialReader sequentialReader, @NotNull Atom atom) throws IOException {
    }

    public QuickTimeTextHandler(Metadata metadata) {
        super(metadata);
    }

    @NotNull
    protected QuickTimeTextDirectory getDirectory() {
        return new QuickTimeTextDirectory();
    }

    protected void processSampleDescription(@NotNull SequentialReader sequentialReader, @NotNull Atom atom) throws IOException {
        new TextSampleDescriptionAtom(sequentialReader, atom).addMetadata((QuickTimeTextDirectory) this.directory);
    }
}
