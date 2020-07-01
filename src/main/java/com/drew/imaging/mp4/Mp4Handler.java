package com.drew.imaging.mp4;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Metadata;
import com.drew.metadata.mp4.Mp4Directory;
import com.drew.metadata.mp4.boxes.Box;
import java.io.IOException;

public abstract class Mp4Handler<T extends Mp4Directory> {
    @NotNull
    protected T directory = getDirectory();
    @NotNull
    protected Metadata metadata;

    @NotNull
    protected abstract T getDirectory();

    protected abstract Mp4Handler processBox(@NotNull Box box, @Nullable byte[] bArr) throws IOException;

    protected abstract boolean shouldAcceptBox(@NotNull Box box);

    protected abstract boolean shouldAcceptContainer(@NotNull Box box);

    public Mp4Handler(@NotNull Metadata metadata) {
        this.metadata = metadata;
        metadata.addDirectory(this.directory);
    }

    protected Mp4Handler processContainer(@NotNull Box box) throws IOException {
        return processBox(box, null);
    }

    public void addError(@NotNull String str) {
        this.directory.addError(str);
    }
}
