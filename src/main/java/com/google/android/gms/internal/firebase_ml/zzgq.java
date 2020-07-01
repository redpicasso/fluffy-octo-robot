package com.google.android.gms.internal.firebase_ml;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

final class zzgq extends zzgi {
    private final zzgn zzxb;
    private final zzqn zzxc;
    private List<String> zzxd = new ArrayList();
    private zzgm zzxe;
    private String zzxf;

    zzgq(zzgn zzgn, zzqn zzqn) {
        this.zzxb = zzgn;
        this.zzxc = zzqn;
        zzqn.setLenient(true);
    }

    public final void close() throws IOException {
        this.zzxc.close();
    }

    public final String zzgj() {
        if (this.zzxd.isEmpty()) {
            return null;
        }
        List list = this.zzxd;
        return (String) list.get(list.size() - 1);
    }

    public final zzgm zzgi() {
        return this.zzxe;
    }

    public final zzge zzgg() {
        return this.zzxb;
    }

    public final byte zzgl() {
        zzgy();
        return Byte.parseByte(this.zzxf);
    }

    public final short zzgm() {
        zzgy();
        return Short.parseShort(this.zzxf);
    }

    public final int getIntValue() {
        zzgy();
        return Integer.parseInt(this.zzxf);
    }

    public final float zzgn() {
        zzgy();
        return Float.parseFloat(this.zzxf);
    }

    public final BigInteger zzgq() {
        zzgy();
        return new BigInteger(this.zzxf);
    }

    public final BigDecimal zzgr() {
        zzgy();
        return new BigDecimal(this.zzxf);
    }

    public final double zzgp() {
        zzgy();
        return Double.parseDouble(this.zzxf);
    }

    public final long zzgo() {
        zzgy();
        return Long.parseLong(this.zzxf);
    }

    private final void zzgy() {
        boolean z = this.zzxe == zzgm.VALUE_NUMBER_INT || this.zzxe == zzgm.VALUE_NUMBER_FLOAT;
        zzks.checkArgument(z);
    }

    public final String getText() {
        return this.zzxf;
    }

    public final zzgm zzgh() throws IOException {
        zzqp zznq;
        if (this.zzxe != null) {
            int i = zzgr.zzwl[this.zzxe.ordinal()];
            if (i == 1) {
                this.zzxc.beginArray();
                this.zzxd.add(null);
            } else if (i == 2) {
                this.zzxc.beginObject();
                this.zzxd.add(null);
            }
        }
        try {
            zznq = this.zzxc.zznq();
        } catch (EOFException unused) {
            zznq = zzqp.END_DOCUMENT;
        }
        List list;
        switch (zznq) {
            case BEGIN_ARRAY:
                this.zzxf = "[";
                this.zzxe = zzgm.START_ARRAY;
                break;
            case END_ARRAY:
                this.zzxf = "]";
                this.zzxe = zzgm.END_ARRAY;
                list = this.zzxd;
                list.remove(list.size() - 1);
                this.zzxc.endArray();
                break;
            case BEGIN_OBJECT:
                this.zzxf = "{";
                this.zzxe = zzgm.START_OBJECT;
                break;
            case END_OBJECT:
                this.zzxf = "}";
                this.zzxe = zzgm.END_OBJECT;
                list = this.zzxd;
                list.remove(list.size() - 1);
                this.zzxc.endObject();
                break;
            case BOOLEAN:
                if (!this.zzxc.nextBoolean()) {
                    this.zzxf = "false";
                    this.zzxe = zzgm.VALUE_FALSE;
                    break;
                }
                this.zzxf = "true";
                this.zzxe = zzgm.VALUE_TRUE;
                break;
            case NULL:
                this.zzxf = "null";
                this.zzxe = zzgm.VALUE_NULL;
                this.zzxc.nextNull();
                break;
            case STRING:
                this.zzxf = this.zzxc.nextString();
                this.zzxe = zzgm.VALUE_STRING;
                break;
            case NUMBER:
                zzgm zzgm;
                this.zzxf = this.zzxc.nextString();
                if (this.zzxf.indexOf(46) == -1) {
                    zzgm = zzgm.VALUE_NUMBER_INT;
                } else {
                    zzgm = zzgm.VALUE_NUMBER_FLOAT;
                }
                this.zzxe = zzgm;
                break;
            case NAME:
                this.zzxf = this.zzxc.nextName();
                this.zzxe = zzgm.FIELD_NAME;
                list = this.zzxd;
                list.set(list.size() - 1, this.zzxf);
                break;
            default:
                this.zzxf = null;
                this.zzxe = null;
                break;
        }
        return this.zzxe;
    }

    public final zzgi zzgk() throws IOException {
        if (this.zzxe != null) {
            int i = zzgr.zzwl[this.zzxe.ordinal()];
            if (i == 1) {
                this.zzxc.skipValue();
                this.zzxf = "]";
                this.zzxe = zzgm.END_ARRAY;
            } else if (i == 2) {
                this.zzxc.skipValue();
                this.zzxf = "}";
                this.zzxe = zzgm.END_OBJECT;
            }
        }
        return this;
    }
}
