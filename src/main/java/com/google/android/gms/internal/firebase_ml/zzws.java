package com.google.android.gms.internal.firebase_ml;

final class zzws {
    static String zzd(zzsw zzsw) {
        zzwu zzwt = new zzwt(zzsw);
        StringBuilder stringBuilder = new StringBuilder(zzwt.size());
        for (int i = 0; i < zzwt.size(); i++) {
            byte zzcl = zzwt.zzcl(i);
            if (zzcl == (byte) 34) {
                stringBuilder.append("\\\"");
            } else if (zzcl == (byte) 39) {
                stringBuilder.append("\\'");
            } else if (zzcl != (byte) 92) {
                switch (zzcl) {
                    case (byte) 7:
                        stringBuilder.append("\\a");
                        break;
                    case (byte) 8:
                        stringBuilder.append("\\b");
                        break;
                    case (byte) 9:
                        stringBuilder.append("\\t");
                        break;
                    case (byte) 10:
                        stringBuilder.append("\\n");
                        break;
                    case (byte) 11:
                        stringBuilder.append("\\v");
                        break;
                    case (byte) 12:
                        stringBuilder.append("\\f");
                        break;
                    case (byte) 13:
                        stringBuilder.append("\\r");
                        break;
                    default:
                        if (zzcl >= (byte) 32 && zzcl <= (byte) 126) {
                            stringBuilder.append((char) zzcl);
                            break;
                        }
                        stringBuilder.append('\\');
                        stringBuilder.append((char) (((zzcl >>> 6) & 3) + 48));
                        stringBuilder.append((char) (((zzcl >>> 3) & 7) + 48));
                        stringBuilder.append((char) ((zzcl & 7) + 48));
                        break;
                }
            } else {
                stringBuilder.append("\\\\");
            }
        }
        return stringBuilder.toString();
    }
}
