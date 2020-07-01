package com.drew.imaging;

import com.drew.lang.ByteTrie;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.avi.AviDirectory;
import com.drew.metadata.wav.WavDirectory;
import com.drew.metadata.webp.WebpDirectory;
import com.google.common.base.Ascii;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;

public class FileTypeDetector {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final HashMap<String, FileType> _ftypMap = new HashMap();
    private static final ByteTrie<FileType> _root = new ByteTrie();

    static {
        _root.setDefaultValue(FileType.Unknown);
        _root.addPath(FileType.Jpeg, new byte[]{(byte) -1, (byte) -40});
        ByteTrie byteTrie = _root;
        FileType fileType = FileType.Tiff;
        r2 = new byte[2][];
        r2[0] = "II".getBytes();
        r2[1] = new byte[]{(byte) 42, (byte) 0};
        byteTrie.addPath(fileType, r2);
        _root.addPath(FileType.Tiff, "MM".getBytes(), new byte[]{(byte) 0, (byte) 42});
        _root.addPath(FileType.Psd, "8BPS".getBytes());
        _root.addPath(FileType.Png, new byte[]{(byte) -119, (byte) 80, (byte) 78, (byte) 71, Ascii.CR, (byte) 10, Ascii.SUB, (byte) 10, (byte) 0, (byte) 0, (byte) 0, Ascii.CR, (byte) 73, (byte) 72, (byte) 68, (byte) 82});
        _root.addPath(FileType.Bmp, "BM".getBytes());
        _root.addPath(FileType.Bmp, "BA".getBytes());
        _root.addPath(FileType.Bmp, "CI".getBytes());
        _root.addPath(FileType.Bmp, "CP".getBytes());
        _root.addPath(FileType.Bmp, "IC".getBytes());
        _root.addPath(FileType.Bmp, "PT".getBytes());
        _root.addPath(FileType.Gif, "GIF87a".getBytes());
        _root.addPath(FileType.Gif, "GIF89a".getBytes());
        _root.addPath(FileType.Ico, new byte[]{(byte) 0, (byte) 0, (byte) 1, (byte) 0});
        _root.addPath(FileType.Pcx, new byte[]{(byte) 10, (byte) 0, (byte) 1});
        _root.addPath(FileType.Pcx, new byte[]{(byte) 10, (byte) 2, (byte) 1});
        _root.addPath(FileType.Pcx, new byte[]{(byte) 10, (byte) 3, (byte) 1});
        _root.addPath(FileType.Pcx, new byte[]{(byte) 10, (byte) 5, (byte) 1});
        _root.addPath(FileType.Riff, "RIFF".getBytes());
        _root.addPath(FileType.Arw, "II".getBytes(), new byte[]{(byte) 42, (byte) 0, (byte) 8, (byte) 0});
        _root.addPath(FileType.Crw, "II".getBytes(), new byte[]{Ascii.SUB, (byte) 0, (byte) 0, (byte) 0}, "HEAPCCDR".getBytes());
        _root.addPath(FileType.Cr2, "II".getBytes(), new byte[]{(byte) 42, (byte) 0, Ascii.DLE, (byte) 0, (byte) 0, (byte) 0, (byte) 67, (byte) 82});
        _root.addPath(FileType.Orf, "IIRO".getBytes(), new byte[]{(byte) 8, (byte) 0});
        _root.addPath(FileType.Orf, "MMOR".getBytes(), new byte[]{(byte) 0, (byte) 0});
        _root.addPath(FileType.Orf, "IIRS".getBytes(), new byte[]{(byte) 8, (byte) 0});
        _root.addPath(FileType.Raf, "FUJIFILMCCD-RAW".getBytes());
        _root.addPath(FileType.Rw2, "II".getBytes(), new byte[]{(byte) 85, (byte) 0});
        _root.addPath(FileType.Eps, "%!PS".getBytes());
        _root.addPath(FileType.Eps, new byte[]{(byte) -59, (byte) -48, (byte) -45, (byte) -58});
        _ftypMap.put("ftypmoov", FileType.Mov);
        _ftypMap.put("ftypwide", FileType.Mov);
        _ftypMap.put("ftypmdat", FileType.Mov);
        _ftypMap.put("ftypfree", FileType.Mov);
        _ftypMap.put("ftypqt  ", FileType.Mov);
        _ftypMap.put("ftypavc1", FileType.Mp4);
        _ftypMap.put("ftypiso2", FileType.Mp4);
        _ftypMap.put("ftypisom", FileType.Mp4);
        _ftypMap.put("ftypM4A ", FileType.Mp4);
        _ftypMap.put("ftypM4B ", FileType.Mp4);
        _ftypMap.put("ftypM4P ", FileType.Mp4);
        _ftypMap.put("ftypM4V ", FileType.Mp4);
        _ftypMap.put("ftypM4VH", FileType.Mp4);
        _ftypMap.put("ftypM4VP", FileType.Mp4);
        _ftypMap.put("ftypmmp4", FileType.Mp4);
        _ftypMap.put("ftypmp41", FileType.Mp4);
        _ftypMap.put("ftypmp42", FileType.Mp4);
        _ftypMap.put("ftypmp71", FileType.Mp4);
        _ftypMap.put("ftypMSNV", FileType.Mp4);
        _ftypMap.put("ftypNDAS", FileType.Mp4);
        _ftypMap.put("ftypNDSC", FileType.Mp4);
        _ftypMap.put("ftypNDSH", FileType.Mp4);
        _ftypMap.put("ftypNDSM", FileType.Mp4);
        _ftypMap.put("ftypNDSP", FileType.Mp4);
        _ftypMap.put("ftypNDSS", FileType.Mp4);
        _ftypMap.put("ftypNDXC", FileType.Mp4);
        _ftypMap.put("ftypNDXH", FileType.Mp4);
        _ftypMap.put("ftypNDXM", FileType.Mp4);
        _ftypMap.put("ftypNDXP", FileType.Mp4);
        _ftypMap.put("ftypNDXS", FileType.Mp4);
        _ftypMap.put("ftypmif1", FileType.Heif);
        _ftypMap.put("ftypmsf1", FileType.Heif);
        _ftypMap.put("ftypheic", FileType.Heif);
        _ftypMap.put("ftypheix", FileType.Heif);
        _ftypMap.put("ftyphevc", FileType.Heif);
        _ftypMap.put("ftyphevx", FileType.Heif);
        _root.addPath(FileType.Aac, new byte[]{(byte) -1, (byte) -15});
        _root.addPath(FileType.Aac, new byte[]{(byte) -1, (byte) -7});
        _root.addPath(FileType.Asf, new byte[]{(byte) 48, (byte) 38, (byte) -78, (byte) 117, (byte) -114, (byte) 102, (byte) -49, (byte) 17, (byte) -90, (byte) -39, (byte) 0, (byte) -86, (byte) 0, (byte) 98, (byte) -50, (byte) 108});
        _root.addPath(FileType.Cfbf, new byte[]{(byte) -48, (byte) -49, (byte) 17, (byte) -32, (byte) -95, (byte) -79, Ascii.SUB, (byte) -31, (byte) 0});
        _root.addPath(FileType.Flv, new byte[]{(byte) 70, (byte) 76, (byte) 86});
        _root.addPath(FileType.Indd, new byte[]{(byte) 6, (byte) 6, (byte) -19, (byte) -11, (byte) -40, Ascii.GS, (byte) 70, (byte) -27, (byte) -67, (byte) 49, (byte) -17, (byte) -25, (byte) -2, (byte) 116, (byte) -73, Ascii.GS});
        _root.addPath(FileType.Mxf, new byte[]{(byte) 6, Ascii.SO, (byte) 43, (byte) 52, (byte) 2, (byte) 5, (byte) 1, (byte) 1, Ascii.CR, (byte) 1, (byte) 2, (byte) 1, (byte) 1, (byte) 2});
        _root.addPath(FileType.Qxp, new byte[]{(byte) 0, (byte) 0, (byte) 73, (byte) 73, (byte) 88, (byte) 80, (byte) 82, (byte) 51});
        _root.addPath(FileType.Qxp, new byte[]{(byte) 0, (byte) 0, (byte) 77, (byte) 77, (byte) 88, (byte) 80, (byte) 82, (byte) 51});
        _root.addPath(FileType.Ram, new byte[]{(byte) 114, (byte) 116, (byte) 115, (byte) 112, (byte) 58, (byte) 47, (byte) 47});
        _root.addPath(FileType.Rtf, new byte[]{(byte) 123, (byte) 92, (byte) 114, (byte) 116, (byte) 102, (byte) 49});
        _root.addPath(FileType.Sit, new byte[]{(byte) 83, (byte) 73, (byte) 84, (byte) 33, (byte) 0});
        _root.addPath(FileType.Sit, new byte[]{(byte) 83, (byte) 116, (byte) 117, (byte) 102, (byte) 102, (byte) 73, (byte) 116, (byte) 32, (byte) 40, (byte) 99, (byte) 41, (byte) 49, (byte) 57, (byte) 57, (byte) 55, (byte) 45});
        _root.addPath(FileType.Sitx, new byte[]{(byte) 83, (byte) 116, (byte) 117, (byte) 102, (byte) 102, (byte) 73, (byte) 116, (byte) 33});
        _root.addPath(FileType.Swf, "CWS".getBytes());
        _root.addPath(FileType.Swf, "FWS".getBytes());
        _root.addPath(FileType.Swf, "ZWS".getBytes());
        _root.addPath(FileType.Vob, new byte[]{(byte) 0, (byte) 0, (byte) 1, (byte) -70});
        _root.addPath(FileType.Zip, "PK".getBytes());
    }

    private FileTypeDetector() throws Exception {
        throw new Exception("Not intended for instantiation");
    }

    @NotNull
    public static FileType detectFileType(@NotNull BufferedInputStream bufferedInputStream) throws IOException {
        if (bufferedInputStream.markSupported()) {
            int max = Math.max(16, _root.getMaxDepth());
            bufferedInputStream.mark(max);
            byte[] bArr = new byte[max];
            if (bufferedInputStream.read(bArr) != -1) {
                bufferedInputStream.reset();
                FileType fileType = (FileType) _root.find(bArr);
                if (fileType == FileType.Unknown) {
                    FileType fileType2 = (FileType) _ftypMap.get(new String(bArr, 4, 8));
                    if (fileType2 != null) {
                        return fileType2;
                    }
                } else if (fileType == FileType.Riff) {
                    String str = new String(bArr, 8, 4);
                    if (str.equals(WavDirectory.FORMAT)) {
                        return FileType.Wav;
                    }
                    if (str.equals(AviDirectory.FORMAT)) {
                        return FileType.Avi;
                    }
                    if (str.equals(WebpDirectory.FORMAT)) {
                        fileType = FileType.WebP;
                    }
                }
                return fileType;
            }
            throw new IOException("Stream ended before file's magic number could be determined.");
        }
        throw new IOException("Stream must support mark/reset");
    }
}
