package com.google.android.gms.internal.firebase_auth;

final class zzkg {
    static String zzd(zzgf zzgf) {
        zzki zzkf = new zzkf(zzgf);
        StringBuilder stringBuilder = new StringBuilder(zzkf.size());
        for (int i = 0; i < zzkf.size(); i++) {
            byte zzp = zzkf.zzp(i);
            if (zzp == (byte) 34) {
                stringBuilder.append("\\\"");
            } else if (zzp == (byte) 39) {
                stringBuilder.append("\\'");
            } else if (zzp != (byte) 92) {
                switch (zzp) {
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
                        if (zzp >= (byte) 32 && zzp <= (byte) 126) {
                            stringBuilder.append((char) zzp);
                            break;
                        }
                        stringBuilder.append('\\');
                        stringBuilder.append((char) (((zzp >>> 6) & 3) + 48));
                        stringBuilder.append((char) (((zzp >>> 3) & 7) + 48));
                        stringBuilder.append((char) ((zzp & 7) + 48));
                        break;
                }
            } else {
                stringBuilder.append("\\\\");
            }
        }
        return stringBuilder.toString();
    }
}
