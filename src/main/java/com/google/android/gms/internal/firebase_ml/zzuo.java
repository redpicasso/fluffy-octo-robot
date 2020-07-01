package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;

public class zzuo extends IOException {
    private zzvo zzbph = null;

    public zzuo(String str) {
        super(str);
    }

    public final zzuo zzg(zzvo zzvo) {
        this.zzbph = zzvo;
        return this;
    }

    static zzuo zzrm() {
        return new zzuo("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzuo zzrn() {
        return new zzuo("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzuo zzro() {
        return new zzuo("Protocol message contained an invalid tag (zero).");
    }

    static zzup zzrp() {
        return new zzup("Protocol message tag had invalid wire type.");
    }

    static zzuo zzrq() {
        return new zzuo("Failed to parse the message.");
    }

    static zzuo zzrr() {
        return new zzuo("Protocol message had invalid UTF-8.");
    }
}
