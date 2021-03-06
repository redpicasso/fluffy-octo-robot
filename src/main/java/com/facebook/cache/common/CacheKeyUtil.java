package com.facebook.cache.common;

import com.bumptech.glide.load.Key;
import com.facebook.common.util.SecureHashUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public final class CacheKeyUtil {
    public static List<String> getResourceIds(CacheKey cacheKey) {
        try {
            List<String> arrayList;
            if (cacheKey instanceof MultiCacheKey) {
                List cacheKeys = ((MultiCacheKey) cacheKey).getCacheKeys();
                arrayList = new ArrayList(cacheKeys.size());
                for (int i = 0; i < cacheKeys.size(); i++) {
                    arrayList.add(secureHashKey((CacheKey) cacheKeys.get(i)));
                }
                return arrayList;
            }
            arrayList = new ArrayList(1);
            arrayList.add(secureHashKey(cacheKey));
            return arrayList;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFirstResourceId(CacheKey cacheKey) {
        try {
            if (cacheKey instanceof MultiCacheKey) {
                return secureHashKey((CacheKey) ((MultiCacheKey) cacheKey).getCacheKeys().get(0));
            }
            return secureHashKey(cacheKey);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static String secureHashKey(CacheKey cacheKey) throws UnsupportedEncodingException {
        return SecureHashUtil.makeSHA1HashBase64(cacheKey.getUriString().getBytes(Key.STRING_CHARSET_NAME));
    }
}
