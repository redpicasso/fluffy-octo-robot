package com.facebook.common.media;

import com.facebook.common.internal.ImmutableMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class MediaUtils {
    public static final Map<String, String> ADDITIONAL_ALLOWED_MIME_TYPES = ImmutableMap.of("mkv", "video/x-matroska", "glb", "model/gltf-binary");

    public static boolean isPhoto(@Nullable String str) {
        return str != null && str.startsWith("image/");
    }

    public static boolean isVideo(@Nullable String str) {
        return str != null && str.startsWith("video/");
    }

    public static boolean isThreeD(@Nullable String str) {
        return str != null && str.equals("model/gltf-binary");
    }

    @Nullable
    public static String extractMime(String str) {
        str = extractExtension(str);
        if (str == null) {
            return null;
        }
        str = str.toLowerCase(Locale.US);
        String mimeTypeFromExtension = MimeTypeMapWrapper.getMimeTypeFromExtension(str);
        if (mimeTypeFromExtension == null) {
            mimeTypeFromExtension = (String) ADDITIONAL_ALLOWED_MIME_TYPES.get(str);
        }
        return mimeTypeFromExtension;
    }

    @Nullable
    private static String extractExtension(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return (lastIndexOf < 0 || lastIndexOf == str.length() - 1) ? null : str.substring(lastIndexOf + 1);
    }

    public static boolean isNonNativeSupportedMimeType(String str) {
        return ADDITIONAL_ALLOWED_MIME_TYPES.containsValue(str);
    }
}
