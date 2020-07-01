package com.drew.metadata.mov.atoms;

import com.drew.lang.SequentialReader;
import com.drew.metadata.Directory;
import com.drew.metadata.mov.QuickTimeDictionary;
import com.drew.metadata.mov.media.QuickTimeVideoDirectory;
import java.io.IOException;

public class VideoSampleDescriptionAtom extends SampleDescriptionAtom<VideoSampleDescription> {

    class VideoSampleDescription extends SampleDescription {
        int colorTableID;
        String compressorName;
        long dataSize;
        int depth;
        int frameCount;
        int height;
        long horizontalResolution;
        int revisionLevel;
        long spatialQuality;
        long temporalQuality;
        String vendor;
        int version;
        long verticalResolution;
        int width;

        public VideoSampleDescription(SequentialReader sequentialReader) throws IOException {
            super(sequentialReader);
            this.version = sequentialReader.getUInt16();
            this.revisionLevel = sequentialReader.getUInt16();
            this.vendor = sequentialReader.getString(4);
            this.temporalQuality = sequentialReader.getUInt32();
            this.spatialQuality = sequentialReader.getUInt32();
            this.width = sequentialReader.getUInt16();
            this.height = sequentialReader.getUInt16();
            this.horizontalResolution = sequentialReader.getUInt32();
            this.verticalResolution = sequentialReader.getUInt32();
            this.dataSize = sequentialReader.getUInt32();
            this.frameCount = sequentialReader.getUInt16();
            this.compressorName = sequentialReader.getString(sequentialReader.getUInt8());
            this.depth = sequentialReader.getUInt16();
            this.colorTableID = sequentialReader.getInt16();
        }
    }

    public VideoSampleDescriptionAtom(SequentialReader sequentialReader, Atom atom) throws IOException {
        super(sequentialReader, atom);
    }

    VideoSampleDescription getSampleDescription(SequentialReader sequentialReader) throws IOException {
        return new VideoSampleDescription(sequentialReader);
    }

    public void addMetadata(QuickTimeVideoDirectory quickTimeVideoDirectory) {
        Directory directory = quickTimeVideoDirectory;
        VideoSampleDescription videoSampleDescription = (VideoSampleDescription) this.sampleDescriptions.get(0);
        QuickTimeDictionary.setLookup(1, videoSampleDescription.vendor, directory);
        QuickTimeDictionary.setLookup(10, videoSampleDescription.dataFormat, directory);
        directory.setLong(2, videoSampleDescription.temporalQuality);
        directory.setLong(3, videoSampleDescription.spatialQuality);
        directory.setInt(4, videoSampleDescription.width);
        directory.setInt(5, videoSampleDescription.height);
        directory.setString(8, videoSampleDescription.compressorName.trim());
        directory.setInt(9, videoSampleDescription.depth);
        directory.setInt(13, videoSampleDescription.colorTableID);
        directory.setDouble(6, ((double) ((videoSampleDescription.horizontalResolution & -65536) >> 16)) + (((double) (videoSampleDescription.horizontalResolution & 65535)) / Math.pow(2.0d, 4.0d)));
        directory.setDouble(7, ((double) ((videoSampleDescription.verticalResolution & -65536) >> 16)) + (((double) (videoSampleDescription.verticalResolution & 65535)) / Math.pow(2.0d, 4.0d)));
    }
}
