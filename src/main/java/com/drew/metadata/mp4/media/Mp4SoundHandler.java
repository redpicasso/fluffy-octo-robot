package com.drew.metadata.mp4.media;

import com.drew.lang.SequentialReader;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.Metadata;
import com.drew.metadata.mp4.Mp4MediaHandler;
import com.drew.metadata.mp4.boxes.AudioSampleEntry;
import com.drew.metadata.mp4.boxes.Box;
import com.drew.metadata.mp4.boxes.SoundMediaHeaderBox;
import com.drew.metadata.mp4.boxes.TimeToSampleBox;
import java.io.IOException;

public class Mp4SoundHandler extends Mp4MediaHandler<Mp4SoundDirectory> {
    protected String getMediaInformation() {
        return "smhd";
    }

    public Mp4SoundHandler(Metadata metadata) {
        super(metadata);
    }

    @NotNull
    protected Mp4SoundDirectory getDirectory() {
        return new Mp4SoundDirectory();
    }

    public void processSampleDescription(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
        new AudioSampleEntry(sequentialReader, box).addMetadata((Mp4SoundDirectory) this.directory);
    }

    public void processMediaInformation(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
        new SoundMediaHeaderBox(sequentialReader, box).addMetadata((Mp4SoundDirectory) this.directory);
    }

    protected void processTimeToSample(@NotNull SequentialReader sequentialReader, @NotNull Box box) throws IOException {
        new TimeToSampleBox(sequentialReader, box).addMetadata((Mp4SoundDirectory) this.directory);
    }
}
