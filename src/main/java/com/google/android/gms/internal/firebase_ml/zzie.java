package com.google.android.gms.internal.firebase_ml;

import com.bumptech.glide.load.Key;
import java.net.URLDecoder;

public final class zzie {
    private static final zzif zzaah = new zzig("-_.*", true);
    private static final zzif zzaai = new zzig("-_.!~*'()@:$&,;=", false);
    private static final zzif zzaaj = new zzig("-_.!~*'()@:$&,;=+/?", false);
    private static final zzif zzaak = new zzig("-_.!~*'():$&,;=", false);
    private static final zzif zzaal = new zzig("-_.!~*'()@:$,;/?:", false);

    public static String zzaq(String str) {
        return zzaah.zzaw(str);
    }

    public static String zzar(String str) {
        try {
            return URLDecoder.decode(str, Key.STRING_CHARSET_NAME);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String zzas(String str) {
        return zzaai.zzaw(str);
    }

    public static String zzat(String str) {
        return zzaaj.zzaw(str);
    }

    public static String zzau(String str) {
        return zzaak.zzaw(str);
    }

    public static String zzav(String str) {
        return zzaal.zzaw(str);
    }
}
