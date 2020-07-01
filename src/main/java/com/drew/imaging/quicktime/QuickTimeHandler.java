package com.drew.imaging.quicktime;

import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.Metadata;
import com.drew.metadata.mov.QuickTimeDirectory;
import com.drew.metadata.mov.atoms.Atom;
import java.io.IOException;

public abstract class QuickTimeHandler<T extends QuickTimeDirectory> {
    @NotNull
    protected T directory = getDirectory();
    @NotNull
    protected Metadata metadata;

    @NotNull
    protected abstract T getDirectory();

    protected abstract QuickTimeHandler processAtom(@NotNull Atom atom, @Nullable byte[] bArr) throws IOException;

    protected abstract boolean shouldAcceptAtom(@NotNull Atom atom);

    protected abstract boolean shouldAcceptContainer(@NotNull Atom atom);

    public QuickTimeHandler(@NotNull Metadata metadata) {
        this.metadata = metadata;
        metadata.addDirectory(this.directory);
    }

    protected QuickTimeHandler processContainer(@NotNull Atom atom) throws IOException {
        return processAtom(atom, null);
    }

    public void addError(@NotNull String str) {
        this.directory.addError(str);
    }
}
