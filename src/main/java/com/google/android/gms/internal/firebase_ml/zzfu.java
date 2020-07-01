package com.google.android.gms.internal.firebase_ml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class zzfu implements zzhu {
    public static final String MEDIA_TYPE = new zzfg("application/x-www-form-urlencoded").zza(zzhc.UTF_8).zzew();

    public static void zze(String str, Object obj) {
        String str2 = str;
        Object obj2 = obj;
        if (str2 != null) {
            try {
                Reader stringReader = new StringReader(str2);
                Class cls = obj.getClass();
                zzhd zzc = zzhd.zzc(cls);
                List asList = Arrays.asList(new Type[]{cls});
                zzhm zzhm = zzhm.class.isAssignableFrom(cls) ? (zzhm) obj2 : null;
                Map map = Map.class.isAssignableFrom(cls) ? (Map) obj2 : null;
                zzgy zzgy = new zzgy(obj2);
                StringWriter stringWriter = new StringWriter();
                StringWriter stringWriter2 = new StringWriter();
                StringWriter stringWriter3 = stringWriter;
                Object obj3 = 1;
                while (true) {
                    int read = stringReader.read();
                    if (read == -1 || read == 38) {
                        String zzar = zzie.zzar(stringWriter3.toString());
                        if (zzar.length() != 0) {
                            String zzar2 = zzie.zzar(stringWriter2.toString());
                            zzhl zzao = zzc.zzao(zzar);
                            if (zzao != null) {
                                Type zza = zzhf.zza(asList, zzao.getGenericType());
                                if (zzia.zzc(zza)) {
                                    zza = zzia.zzb(asList, zzia.zzd(zza));
                                    zzgy.zza(zzao.zzhf(), zza, zza(zza, asList, zzar2));
                                } else if (zzia.zza(zzia.zzb(asList, zza), Iterable.class)) {
                                    Collection collection = (Collection) zzao.zzh(obj2);
                                    if (collection == null) {
                                        collection = zzhf.zzb(zza);
                                        zzao.zzb(obj2, collection);
                                    }
                                    collection.add(zza(zza == Object.class ? null : zzia.zze(zza), asList, zzar2));
                                } else {
                                    zzao.zzb(obj2, zza(zza, asList, zzar2));
                                }
                            } else if (map != null) {
                                ArrayList arrayList = (ArrayList) map.get(zzar);
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                    if (zzhm != null) {
                                        zzhm.zzb(zzar, arrayList);
                                    } else {
                                        map.put(zzar, arrayList);
                                    }
                                }
                                arrayList.add(zzar2);
                            }
                        }
                        StringWriter stringWriter4 = new StringWriter();
                        stringWriter = new StringWriter();
                        if (read == -1) {
                            zzgy.zzha();
                            return;
                        }
                        stringWriter3 = stringWriter4;
                        stringWriter2 = stringWriter;
                        obj3 = 1;
                    } else if (read != 61) {
                        if (obj3 != null) {
                            stringWriter3.write(read);
                        } else {
                            stringWriter2.write(read);
                        }
                    } else if (obj3 != null) {
                        obj3 = null;
                    } else {
                        stringWriter2.write(read);
                    }
                }
            } catch (Throwable e) {
                throw zzlb.zza(e);
            }
        }
    }

    private static Object zza(Type type, List<Type> list, String str) {
        return zzhf.zza(zzhf.zza((List) list, type), str);
    }

    public final <T> T zza(InputStream inputStream, Charset charset, Class<T> cls) throws IOException {
        throw new NoSuchMethodError();
    }
}
