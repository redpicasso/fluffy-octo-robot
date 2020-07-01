package com.drew.metadata.mov.atoms;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MotionEventCompat;
import com.drew.lang.SequentialReader;
import com.drew.metadata.mov.QuickTimeDirectory;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MovieHeaderAtom extends FullAtom {
    long creationTime;
    long currentTime;
    long duration;
    int[] matrixStructure;
    long modificationTime;
    long nextTrackID;
    long posterTime;
    int preferredRate;
    int preferredVolume;
    long previewDuration;
    long previewTime;
    long selectionDuration;
    long selectionTime;
    long timescale;

    public MovieHeaderAtom(SequentialReader sequentialReader, Atom atom) throws IOException {
        super(sequentialReader, atom);
        this.creationTime = sequentialReader.getUInt32();
        this.modificationTime = sequentialReader.getUInt32();
        this.timescale = sequentialReader.getUInt32();
        this.duration = sequentialReader.getUInt32();
        this.preferredRate = sequentialReader.getInt32();
        this.preferredVolume = sequentialReader.getInt16();
        sequentialReader.skip(10);
        this.matrixStructure = new int[]{sequentialReader.getInt32(), sequentialReader.getInt32(), sequentialReader.getInt32(), sequentialReader.getInt32(), sequentialReader.getInt32(), sequentialReader.getInt32(), sequentialReader.getInt32(), sequentialReader.getInt32(), sequentialReader.getInt32()};
        this.previewTime = sequentialReader.getUInt32();
        this.previewDuration = sequentialReader.getUInt32();
        this.posterTime = sequentialReader.getUInt32();
        this.selectionTime = sequentialReader.getUInt32();
        this.selectionDuration = sequentialReader.getUInt32();
        this.currentTime = sequentialReader.getUInt32();
        this.nextTrackID = sequentialReader.getUInt32();
    }

    public void addMetadata(QuickTimeDirectory quickTimeDirectory) {
        Calendar instance = Calendar.getInstance();
        instance.set(1904, 0, 1, 0, 0, 0);
        long time = instance.getTime().getTime();
        quickTimeDirectory.setDate(256, new Date((this.creationTime * 1000) + time));
        quickTimeDirectory.setDate(257, new Date((this.modificationTime * 1000) + time));
        this.duration /= this.timescale;
        quickTimeDirectory.setLong(259, this.duration);
        quickTimeDirectory.setLong(258, this.timescale);
        int i = this.preferredRate;
        quickTimeDirectory.setDouble(260, ((double) ((SupportMenu.CATEGORY_MASK & i) >> 16)) + (((double) (i & 65535)) / Math.pow(2.0d, 4.0d)));
        i = this.preferredVolume;
        quickTimeDirectory.setDouble(261, ((double) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & i) >> 8)) + (((double) (i & 255)) / Math.pow(2.0d, 2.0d)));
        quickTimeDirectory.setLong(264, this.previewTime);
        quickTimeDirectory.setLong(265, this.previewDuration);
        quickTimeDirectory.setLong(266, this.posterTime);
        quickTimeDirectory.setLong(267, this.selectionTime);
        quickTimeDirectory.setLong(268, this.selectionDuration);
        quickTimeDirectory.setLong(269, this.currentTime);
        quickTimeDirectory.setLong(270, this.nextTrackID);
    }
}
