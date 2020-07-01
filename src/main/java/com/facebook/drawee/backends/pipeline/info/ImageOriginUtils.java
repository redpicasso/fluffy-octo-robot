package com.facebook.drawee.backends.pipeline.info;

import androidx.core.os.EnvironmentCompat;
import com.google.android.gms.common.internal.ImagesContract;

public class ImageOriginUtils {
    public static String toString(int i) {
        return i != 2 ? i != 3 ? i != 4 ? i != 5 ? i != 6 ? EnvironmentCompat.MEDIA_UNKNOWN : ImagesContract.LOCAL : "memory_bitmap" : "memory_encoded" : "disk" : "network";
    }

    public static int mapProducerNameToImageOrigin(java.lang.String r7) {
        /*
        r0 = r7.hashCode();
        r1 = 6;
        r2 = 2;
        r3 = 3;
        r4 = 4;
        r5 = 5;
        r6 = 1;
        switch(r0) {
            case -1917159454: goto L_0x00a6;
            case -1914072202: goto L_0x009c;
            case -1683996557: goto L_0x0091;
            case -1579985851: goto L_0x0086;
            case -1307634203: goto L_0x007c;
            case -1224383234: goto L_0x0072;
            case 473552259: goto L_0x0067;
            case 656304759: goto L_0x005d;
            case 957714404: goto L_0x0053;
            case 1019542023: goto L_0x0048;
            case 1023071510: goto L_0x003d;
            case 1721672898: goto L_0x0032;
            case 1793127518: goto L_0x0026;
            case 2109593398: goto L_0x001b;
            case 2113652014: goto L_0x000f;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x00b1;
    L_0x000f:
        r0 = "LocalContentUriFetchProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x0017:
        r7 = 9;
        goto L_0x00b2;
    L_0x001b:
        r0 = "PartialDiskCacheProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x0023:
        r7 = 5;
        goto L_0x00b2;
    L_0x0026:
        r0 = "LocalContentUriThumbnailFetchProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x002e:
        r7 = 10;
        goto L_0x00b2;
    L_0x0032:
        r0 = "DataFetchProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x003a:
        r7 = 7;
        goto L_0x00b2;
    L_0x003d:
        r0 = "PostprocessedBitmapMemoryCacheProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x0045:
        r7 = 2;
        goto L_0x00b2;
    L_0x0048:
        r0 = "LocalAssetFetchProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x0050:
        r7 = 8;
        goto L_0x00b2;
    L_0x0053:
        r0 = "BitmapMemoryCacheProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x005b:
        r7 = 1;
        goto L_0x00b2;
    L_0x005d:
        r0 = "DiskCacheProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x0065:
        r7 = 4;
        goto L_0x00b2;
    L_0x0067:
        r0 = "VideoThumbnailProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x006f:
        r7 = 13;
        goto L_0x00b2;
    L_0x0072:
        r0 = "NetworkFetchProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x007a:
        r7 = 6;
        goto L_0x00b2;
    L_0x007c:
        r0 = "EncodedMemoryCacheProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x0084:
        r7 = 3;
        goto L_0x00b2;
    L_0x0086:
        r0 = "LocalFileFetchProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x008e:
        r7 = 11;
        goto L_0x00b2;
    L_0x0091:
        r0 = "LocalResourceFetchProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x0099:
        r7 = 12;
        goto L_0x00b2;
    L_0x009c:
        r0 = "BitmapMemoryCacheGetProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x00a4:
        r7 = 0;
        goto L_0x00b2;
    L_0x00a6:
        r0 = "QualifiedResourceFetchProducer";
        r7 = r7.equals(r0);
        if (r7 == 0) goto L_0x00b1;
    L_0x00ae:
        r7 = 14;
        goto L_0x00b2;
    L_0x00b1:
        r7 = -1;
    L_0x00b2:
        switch(r7) {
            case 0: goto L_0x00ba;
            case 1: goto L_0x00ba;
            case 2: goto L_0x00ba;
            case 3: goto L_0x00b9;
            case 4: goto L_0x00b8;
            case 5: goto L_0x00b8;
            case 6: goto L_0x00b7;
            case 7: goto L_0x00b6;
            case 8: goto L_0x00b6;
            case 9: goto L_0x00b6;
            case 10: goto L_0x00b6;
            case 11: goto L_0x00b6;
            case 12: goto L_0x00b6;
            case 13: goto L_0x00b6;
            case 14: goto L_0x00b6;
            default: goto L_0x00b5;
        };
    L_0x00b5:
        return r6;
    L_0x00b6:
        return r1;
    L_0x00b7:
        return r2;
    L_0x00b8:
        return r3;
    L_0x00b9:
        return r4;
    L_0x00ba:
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.drawee.backends.pipeline.info.ImageOriginUtils.mapProducerNameToImageOrigin(java.lang.String):int");
    }

    private ImageOriginUtils() {
    }
}
