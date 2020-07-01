package com.drew.metadata.mp4.media;

import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.mp4.Mp4BoxTypes;
import com.drew.metadata.mp4.Mp4MediaHandler;
import com.drew.metadata.mp4.boxes.Box;
import com.drew.metadata.mp4.boxes.HintMediaHeaderBox;
import java.io.IOException;

public class Mp4HintHandler extends Mp4MediaHandler<Mp4HintDirectory> {
    protected String getMediaInformation() {
        return Mp4BoxTypes.BOX_HINT_MEDIA_INFO;
    }

    protected void processSampleDescription(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
    }

    protected void processTimeToSample(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
    }

    public Mp4HintHandler(Metadata metadata) {
        super(metadata);
    }

    @NotNull
    protected Mp4HintDirectory getDirectory() {
        return new Mp4HintDirectory();
    }

    protected void processMediaInformation(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
        new HintMediaHeaderBox(sequentialReader, box).addMetadata((Mp4HintDirectory) this.directory);
    }
}
