package com.google.android.gms.internal.clearcut;

import com.facebook.common.util.UriUtil;
import com.google.logging.type.LogSeverity;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zzga {
    public static <T extends zzfz> String zza(T t) {
        String valueOf;
        String str = "Error printing proto: ";
        if (t == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (IllegalAccessException e) {
            valueOf = String.valueOf(e.getMessage());
            return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        } catch (InvocationTargetException e2) {
            valueOf = String.valueOf(e2.getMessage());
            return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        }
    }

    private static void zza(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        if (obj != null) {
            int length;
            if (obj instanceof zzfz) {
                String str2;
                int length2 = stringBuffer.length();
                if (str != null) {
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(zzl(str));
                    stringBuffer2.append(" <\n");
                    stringBuffer.append("  ");
                }
                Class cls = obj.getClass();
                for (Field field : cls.getFields()) {
                    int modifiers = field.getModifiers();
                    String name = field.getName();
                    if (!("cachedSize".equals(name) || (modifiers & 1) != 1 || (modifiers & 8) == 8)) {
                        str2 = "_";
                        if (!(name.startsWith(str2) || name.endsWith(str2))) {
                            Class type = field.getType();
                            Object obj2 = field.get(obj);
                            if (!type.isArray() || type.getComponentType() == Byte.TYPE) {
                                zza(name, obj2, stringBuffer, stringBuffer2);
                            } else {
                                modifiers = obj2 == null ? 0 : Array.getLength(obj2);
                                for (int i = 0; i < modifiers; i++) {
                                    zza(name, Array.get(obj2, i), stringBuffer, stringBuffer2);
                                }
                            }
                        }
                    }
                }
                Method[] methods = cls.getMethods();
                length = methods.length;
                int i2 = 0;
                while (i2 < length) {
                    String name2 = methods[i2].getName();
                    if (name2.startsWith("set")) {
                        name2 = name2.substring(3);
                        try {
                            String str3 = "has";
                            str2 = String.valueOf(name2);
                            if (((Boolean) cls.getMethod(str2.length() != 0 ? str3.concat(str2) : new String(str3), new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                                str3 = "get";
                                str2 = String.valueOf(name2);
                                zza(name2, cls.getMethod(str2.length() != 0 ? str3.concat(str2) : new String(str3), new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                            }
                        } catch (NoSuchMethodException unused) {
                            i2++;
                        }
                    }
                }
                if (str != null) {
                    stringBuffer.setLength(length2);
                    stringBuffer2.append(stringBuffer);
                    stringBuffer2.append(">\n");
                }
                return;
            }
            str = zzl(str);
            stringBuffer2.append(stringBuffer);
            stringBuffer2.append(str);
            stringBuffer2.append(": ");
            int length3;
            if (obj instanceof String) {
                String str4 = (String) obj;
                if (!str4.startsWith(UriUtil.HTTP_SCHEME) && str4.length() > LogSeverity.INFO_VALUE) {
                    str4 = String.valueOf(str4.substring(0, LogSeverity.INFO_VALUE)).concat("[...]");
                }
                length3 = str4.length();
                StringBuilder stringBuilder = new StringBuilder(length3);
                for (length = 0; length < length3; length++) {
                    char charAt = str4.charAt(length);
                    if (charAt < ' ' || charAt > '~' || charAt == '\"' || charAt == '\'') {
                        stringBuilder.append(String.format("\\u%04x", new Object[]{Integer.valueOf(charAt)}));
                    } else {
                        stringBuilder.append(charAt);
                    }
                }
                str = stringBuilder.toString();
                str4 = "\"";
                stringBuffer2.append(str4);
                stringBuffer2.append(str);
                stringBuffer2.append(str4);
            } else if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                if (bArr == null) {
                    stringBuffer2.append("\"\"");
                } else {
                    stringBuffer2.append('\"');
                    for (byte b : bArr) {
                        int i3 = b & 255;
                        if (i3 == 92 || i3 == 34) {
                            stringBuffer2.append('\\');
                        } else if (i3 < 32 || i3 >= 127) {
                            stringBuffer2.append(String.format("\\%03o", new Object[]{Integer.valueOf(i3)}));
                        }
                        stringBuffer2.append((char) i3);
                    }
                    stringBuffer2.append('\"');
                }
            } else {
                stringBuffer2.append(obj);
            }
            stringBuffer2.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
        }
    }

    private static String zzl(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (i != 0) {
                if (Character.isUpperCase(charAt)) {
                    stringBuffer.append('_');
                }
                stringBuffer.append(charAt);
            }
            charAt = Character.toLowerCase(charAt);
            stringBuffer.append(charAt);
        }
        return stringBuffer.toString();
    }
}
