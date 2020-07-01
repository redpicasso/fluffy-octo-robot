package com.drew.metadata.mp4.media;

import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.mp4.Mp4MediaHandler;
import com.drew.metadata.mp4.boxes.Box;
import java.io.IOException;

public class Mp4TextHandler extends Mp4MediaHandler<Mp4TextDirectory> {
    protected String getMediaInformation() {
        return "text";
    }

    protected void processMediaInformation(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
    }

    protected void processSampleDescription(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
    }

    protected void processTimeToSample(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
    }

    public Mp4TextHandler(Metadata metadata) {
        super(metadata);
    }

    @NotNull
    protected Mp4TextDirectory getDirectory() {
        return (Mp4TextDirectory) this.directory;
    }
}
