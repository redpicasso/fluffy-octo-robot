package com.google.firebase.database.core.utilities;

import android.util.Base64;
import com.bumptech.glide.load.Key;
import com.facebook.common.util.UriUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.RepoInfo;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Utilities {
    private static final char[] HEX_CHARACTERS = "0123456789abcdef".toCharArray();

    public static int compareInts(int i, int i2) {
        return i < i2 ? -1 : i == i2 ? 0 : 1;
    }

    public static int compareLongs(long j, long j2) {
        int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        return i < 0 ? -1 : i == 0 ? 0 : 1;
    }

    public static ParsedUrl parseUrl(String str) throws DatabaseException {
        String str2 = "/";
        try {
            int indexOf = str.indexOf("//");
            if (indexOf != -1) {
                indexOf += 2;
                int indexOf2 = str.substring(indexOf).indexOf(str2);
                if (indexOf2 != -1) {
                    indexOf2 += indexOf;
                    String[] split = str.substring(indexOf2).split(str2, -1);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < split.length; i++) {
                        if (!split[i].equals("")) {
                            stringBuilder.append(str2);
                            stringBuilder.append(URLEncoder.encode(split[i], Key.STRING_CHARSET_NAME));
                        }
                    }
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str.substring(0, indexOf2));
                    stringBuilder2.append(stringBuilder.toString());
                    str = stringBuilder2.toString();
                }
                URI uri = new URI(str);
                str = uri.getPath().replace("+", " ");
                Validation.validateRootPathString(str);
                Path path = new Path(str);
                str = uri.getScheme();
                RepoInfo repoInfo = new RepoInfo();
                repoInfo.host = uri.getHost().toLowerCase();
                int port = uri.getPort();
                if (port != -1) {
                    repoInfo.secure = str.equals(UriUtil.HTTPS_SCHEME);
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(repoInfo.host);
                    stringBuilder3.append(":");
                    stringBuilder3.append(port);
                    repoInfo.host = stringBuilder3.toString();
                } else {
                    repoInfo.secure = true;
                }
                repoInfo.namespace = repoInfo.host.split("\\.", -1)[0].toLowerCase();
                repoInfo.internalHost = repoInfo.host;
                ParsedUrl parsedUrl = new ParsedUrl();
                parsedUrl.path = path;
                parsedUrl.repoInfo = repoInfo;
                return parsedUrl;
            }
            throw new URISyntaxException(str, "Invalid scheme specified");
        } catch (Throwable e) {
            throw new DatabaseException("Invalid Firebase Database url specified", e);
        } catch (Throwable e2) {
            throw new DatabaseException("Failed to URLEncode the path", e2);
        }
    }

    public static String[] splitIntoFrames(String str, int i) {
        int i2 = 0;
        if (str.length() <= i) {
            return new String[]{str};
        }
        ArrayList arrayList = new ArrayList();
        while (i2 < str.length()) {
            int i3 = i2 + i;
            arrayList.add(str.substring(i2, Math.min(i3, str.length())));
            i2 = i3;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String sha1HexDigest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes(Key.STRING_CHARSET_NAME));
            return Base64.encodeToString(instance.digest(), 2);
        } catch (Throwable e) {
            throw new RuntimeException("Missing SHA-1 MessageDigest provider.", e);
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("UTF-8 encoding is required for Firebase Database to run!");
        }
    }

    public static String stringHashV2Representation(String str) {
        String replace = str.indexOf(92) != -1 ? str.replace("\\", "\\\\") : str;
        if (str.indexOf(34) != -1) {
            replace = replace.replace("\"", "\\\"");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\"');
        stringBuilder.append(replace);
        stringBuilder.append('\"');
        return stringBuilder.toString();
    }

    public static String doubleToHashString(double d) {
        StringBuilder stringBuilder = new StringBuilder(16);
        long doubleToLongBits = Double.doubleToLongBits(d);
        for (int i = 7; i >= 0; i--) {
            int i2 = (int) ((doubleToLongBits >>> (i * 8)) & 255);
            int i3 = (i2 >> 4) & 15;
            i2 &= 15;
            stringBuilder.append(HEX_CHARACTERS[i3]);
            stringBuilder.append(HEX_CHARACTERS[i2]);
        }
        return stringBuilder.toString();
    }

    public static Integer tryParseInt(String str) {
        if (str.length() > 11 || str.length() == 0) {
            return null;
        }
        int i = 0;
        Object obj = 1;
        if (str.charAt(0) != '-') {
            obj = null;
        } else if (str.length() == 1) {
            return null;
        } else {
            i = 1;
        }
        long j = 0;
        for (i = 
/*
Method generation error in method: com.google.firebase.database.core.utilities.Utilities.tryParseInt(java.lang.String):java.lang.Integer, dex: classes2.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r0_5 'i' int) = (r0_4 'i' int), (r0_2 'i' int) binds: {(r0_4 'i' int)=B:9:0x0021, (r0_2 'i' int)=B:10:0x0023} in method: com.google.firebase.database.core.utilities.Utilities.tryParseInt(java.lang.String):java.lang.Integer, dex: classes2.dex
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:228)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:183)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:173)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:323)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:260)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:222)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:77)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:10)
	at jadx.core.ProcessClass.process(ProcessClass.java:38)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:293)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:201)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:539)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:511)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:222)
	... 19 more

*/

    public static <C> C castOrNull(Object obj, Class<C> cls) {
        return cls.isAssignableFrom(obj.getClass()) ? obj : null;
    }

    public static <C> C getOrNull(Object obj, String str, Class<C> cls) {
        if (obj == null) {
            return null;
        }
        obj = ((Map) castOrNull(obj, Map.class)).get(str);
        if (obj != null) {
            return castOrNull(obj, cls);
        }
        return null;
    }

    public static void hardAssert(boolean z) {
        hardAssert(z, "");
    }

    public static void hardAssert(boolean z, String str) {
        if (!z) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("hardAssert failed: ");
            stringBuilder.append(str);
            throw new AssertionError(stringBuilder.toString());
        }
    }

    public static Pair<Task<Void>, CompletionListener> wrapOnComplete(CompletionListener completionListener) {
        if (completionListener != null) {
            return new Pair(null, completionListener);
        }
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        return new Pair(taskCompletionSource.getTask(), new CompletionListener() {
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    taskCompletionSource.setException(databaseError.toException());
                } else {
                    taskCompletionSource.setResult(null);
                }
            }
        });
    }
}
