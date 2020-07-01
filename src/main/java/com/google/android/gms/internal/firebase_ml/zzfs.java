package com.google.android.gms.internal.firebase_ml;

enum zzfs {
    PLUS(Character.valueOf('+'), "", ",", false, true),
    HASH(Character.valueOf('#'), "#", ",", false, true),
    DOT(Character.valueOf('.'), ".", ".", false, false),
    FORWARD_SLASH(Character.valueOf('/'), "/", "/", false, false),
    SEMI_COLON(Character.valueOf(';'), ";", ";", true, false),
    QUERY(Character.valueOf('?'), "?", "&", true, false),
    AMP(Character.valueOf('&'), "&", "&", true, false),
    SIMPLE(null, "", ",", false, false);
    
    private final Character zzvr;
    private final String zzvs;
    private final String zzvt;
    private final boolean zzvu;
    private final boolean zzvv;

    private zzfs(Character ch, String str, String str2, boolean z, boolean z2) {
        this.zzvr = ch;
        this.zzvs = (String) zzks.checkNotNull(str);
        this.zzvt = (String) zzks.checkNotNull(str2);
        this.zzvu = z;
        this.zzvv = z2;
        if (ch != null) {
            zzfr.zzvi.put(ch, this);
        }
    }

    final String zzfr() {
        return this.zzvs;
    }

    final String zzfs() {
        return this.zzvt;
    }

    final boolean zzft() {
        return this.zzvu;
    }

    final int zzfu() {
        return this.zzvr == null ? 0 : 1;
    }

    final String zzak(String str) {
        if (this.zzvv) {
            return zzie.zzas(str);
        }
        return zzie.zzaq(str);
    }

    final boolean zzfv() {
        return this.zzvv;
    }
}
