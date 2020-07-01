package com.google.common.reflect;

import com.facebook.common.util.UriUtil;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.Attributes.Name;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
public final class ClassPath {
    private static final String CLASS_FILE_NAME_EXTENSION = ".class";
    private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(" ").omitEmptyStrings();
    private static final Predicate<ClassInfo> IS_TOP_LEVEL = new Predicate<ClassInfo>() {
        public boolean apply(ClassInfo classInfo) {
            return classInfo.className.indexOf(36) == -1;
        }
    };
    private static final Logger logger = Logger.getLogger(ClassPath.class.getName());
    private final ImmutableSet<ResourceInfo> resources;

    @Beta
    public static class ResourceInfo {
        final ClassLoader loader;
        private final String resourceName;

        static ResourceInfo of(String str, ClassLoader classLoader) {
            if (str.endsWith(ClassPath.CLASS_FILE_NAME_EXTENSION)) {
                return new ClassInfo(str, classLoader);
            }
            return new ResourceInfo(str, classLoader);
        }

        ResourceInfo(String str, ClassLoader classLoader) {
            this.resourceName = (String) Preconditions.checkNotNull(str);
            this.loader = (ClassLoader) Preconditions.checkNotNull(classLoader);
        }

        public final URL url() {
            URL resource = this.loader.getResource(this.resourceName);
            if (resource != null) {
                return resource;
            }
            throw new NoSuchElementException(this.resourceName);
        }

        public final ByteSource asByteSource() {
            return Resources.asByteSource(url());
        }

        public final CharSource asCharSource(Charset charset) {
            return Resources.asCharSource(url(), charset);
        }

        public final String getResourceName() {
            return this.resourceName;
        }

        public int hashCode() {
            return this.resourceName.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ResourceInfo)) {
                return false;
            }
            ResourceInfo resourceInfo = (ResourceInfo) obj;
            if (this.resourceName.equals(resourceInfo.resourceName) && this.loader == resourceInfo.loader) {
                return true;
            }
            return false;
        }

        public String toString() {
            return this.resourceName;
        }
    }

    static abstract class Scanner {
        private final Set<File> scannedUris = Sets.newHashSet();

        protected abstract void scanDirectory(ClassLoader classLoader, File file) throws IOException;

        protected abstract void scanJarFile(ClassLoader classLoader, JarFile jarFile) throws IOException;

        Scanner() {
        }

        public final void scan(ClassLoader classLoader) throws IOException {
            Iterator it = getClassPathEntries(classLoader).entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                scan((File) entry.getKey(), (ClassLoader) entry.getValue());
            }
        }

        @VisibleForTesting
        final void scan(File file, ClassLoader classLoader) throws IOException {
            if (this.scannedUris.add(file.getCanonicalFile())) {
                scanFrom(file, classLoader);
            }
        }

        private void scanFrom(File file, ClassLoader classLoader) throws IOException {
            try {
                if (file.exists()) {
                    if (file.isDirectory()) {
                        scanDirectory(classLoader, file);
                    } else {
                        scanJar(file, classLoader);
                    }
                }
            } catch (SecurityException e) {
                Logger access$100 = ClassPath.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot access ");
                stringBuilder.append(file);
                stringBuilder.append(": ");
                stringBuilder.append(e);
                access$100.warning(stringBuilder.toString());
            }
        }

        private void scanJar(File file, ClassLoader classLoader) throws IOException {
            try {
                JarFile jarFile = new JarFile(file);
                try {
                    Iterator it = getClassPathFromManifest(file, jarFile.getManifest()).iterator();
                    while (it.hasNext()) {
                        scan((File) it.next(), classLoader);
                    }
                    scanJarFile(classLoader, jarFile);
                    try {
                        jarFile.close();
                    } catch (IOException unused) {
                    }
                } catch (Throwable th) {
                    try {
                        jarFile.close();
                    } catch (IOException unused2) {
                        throw th;
                    }
                }
            } catch (IOException unused3) {
            }
        }

        @VisibleForTesting
        static ImmutableSet<File> getClassPathFromManifest(File file, @NullableDecl Manifest manifest) {
            if (manifest == null) {
                return ImmutableSet.of();
            }
            Builder builder = ImmutableSet.builder();
            CharSequence value = manifest.getMainAttributes().getValue(Name.CLASS_PATH.toString());
            if (value != null) {
                for (String str : ClassPath.CLASS_PATH_ATTRIBUTE_SEPARATOR.split(value)) {
                    String str2;
                    try {
                        str2 = getClassPathEntry(file, str2);
                        if (str2.getProtocol().equals(UriUtil.LOCAL_FILE_SCHEME)) {
                            builder.add(ClassPath.toFile(str2));
                        }
                    } catch (MalformedURLException unused) {
                        Logger access$100 = ClassPath.logger;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid Class-Path entry: ");
                        stringBuilder.append(str2);
                        access$100.warning(stringBuilder.toString());
                    }
                }
            }
            return builder.build();
        }

        @VisibleForTesting
        static ImmutableMap<File, ClassLoader> getClassPathEntries(ClassLoader classLoader) {
            Map newLinkedHashMap = Maps.newLinkedHashMap();
            ClassLoader parent = classLoader.getParent();
            if (parent != null) {
                newLinkedHashMap.putAll(getClassPathEntries(parent));
            }
            Iterator it = getClassLoaderUrls(classLoader).iterator();
            while (it.hasNext()) {
                URL url = (URL) it.next();
                if (url.getProtocol().equals(UriUtil.LOCAL_FILE_SCHEME)) {
                    File toFile = ClassPath.toFile(url);
                    if (!newLinkedHashMap.containsKey(toFile)) {
                        newLinkedHashMap.put(toFile, classLoader);
                    }
                }
            }
            return ImmutableMap.copyOf(newLinkedHashMap);
        }

        private static ImmutableList<URL> getClassLoaderUrls(ClassLoader classLoader) {
            if (classLoader instanceof URLClassLoader) {
                return ImmutableList.copyOf(((URLClassLoader) classLoader).getURLs());
            }
            if (classLoader.equals(ClassLoader.getSystemClassLoader())) {
                return parseJavaClassPath();
            }
            return ImmutableList.of();
        }

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:7:0x0039, code:
            r3 = move-exception;
     */
        /* JADX WARNING: Missing block: B:9:?, code:
            r0.add(new java.net.URL(com.facebook.common.util.UriUtil.LOCAL_FILE_SCHEME, null, new java.io.File(r2).getAbsolutePath()));
     */
        /* JADX WARNING: Missing block: B:11:0x0050, code:
            r4 = com.google.common.reflect.ClassPath.access$100();
            r5 = java.util.logging.Level.WARNING;
            r6 = new java.lang.StringBuilder();
            r6.append("malformed classpath entry: ");
            r6.append(r2);
            r4.log(r5, r6.toString(), r3);
     */
        @com.google.common.annotations.VisibleForTesting
        static com.google.common.collect.ImmutableList<java.net.URL> parseJavaClassPath() {
            /*
            r0 = com.google.common.collect.ImmutableList.builder();
            r1 = com.google.common.base.StandardSystemProperty.PATH_SEPARATOR;
            r1 = r1.value();
            r1 = com.google.common.base.Splitter.on(r1);
            r2 = com.google.common.base.StandardSystemProperty.JAVA_CLASS_PATH;
            r2 = r2.value();
            r1 = r1.split(r2);
            r1 = r1.iterator();
        L_0x001c:
            r2 = r1.hasNext();
            if (r2 == 0) goto L_0x006b;
        L_0x0022:
            r2 = r1.next();
            r2 = (java.lang.String) r2;
            r3 = new java.io.File;	 Catch:{ SecurityException -> 0x003b }
            r3.<init>(r2);	 Catch:{ SecurityException -> 0x003b }
            r3 = r3.toURI();	 Catch:{ SecurityException -> 0x003b }
            r3 = r3.toURL();	 Catch:{ SecurityException -> 0x003b }
            r0.add(r3);	 Catch:{ SecurityException -> 0x003b }
            goto L_0x001c;
        L_0x0039:
            r3 = move-exception;
            goto L_0x0050;
        L_0x003b:
            r3 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0039 }
            r4 = "file";
            r5 = 0;
            r6 = new java.io.File;	 Catch:{ MalformedURLException -> 0x0039 }
            r6.<init>(r2);	 Catch:{ MalformedURLException -> 0x0039 }
            r6 = r6.getAbsolutePath();	 Catch:{ MalformedURLException -> 0x0039 }
            r3.<init>(r4, r5, r6);	 Catch:{ MalformedURLException -> 0x0039 }
            r0.add(r3);	 Catch:{ MalformedURLException -> 0x0039 }
            goto L_0x001c;
        L_0x0050:
            r4 = com.google.common.reflect.ClassPath.logger;
            r5 = java.util.logging.Level.WARNING;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "malformed classpath entry: ";
            r6.append(r7);
            r6.append(r2);
            r2 = r6.toString();
            r4.log(r5, r2, r3);
            goto L_0x001c;
        L_0x006b:
            r0 = r0.build();
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.reflect.ClassPath.Scanner.parseJavaClassPath():com.google.common.collect.ImmutableList<java.net.URL>");
        }

        @VisibleForTesting
        static URL getClassPathEntry(File file, String str) throws MalformedURLException {
            return new URL(file.toURI().toURL(), str);
        }
    }

    @Beta
    public static final class ClassInfo extends ResourceInfo {
        private final String className;

        ClassInfo(String str, ClassLoader classLoader) {
            super(str, classLoader);
            this.className = ClassPath.getClassName(str);
        }

        public String getPackageName() {
            return Reflection.getPackageName(this.className);
        }

        public String getSimpleName() {
            int lastIndexOf = this.className.lastIndexOf(36);
            if (lastIndexOf != -1) {
                return CharMatcher.digit().trimLeadingFrom(this.className.substring(lastIndexOf + 1));
            }
            String packageName = getPackageName();
            if (packageName.isEmpty()) {
                return this.className;
            }
            return this.className.substring(packageName.length() + 1);
        }

        public String getName() {
            return this.className;
        }

        public Class<?> load() {
            try {
                return this.loader.loadClass(this.className);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }

        public String toString() {
            return this.className;
        }
    }

    @VisibleForTesting
    static final class DefaultScanner extends Scanner {
        private final SetMultimap<ClassLoader, String> resources = MultimapBuilder.hashKeys().linkedHashSetValues().build();

        DefaultScanner() {
        }

        ImmutableSet<ResourceInfo> getResources() {
            Builder builder = ImmutableSet.builder();
            for (Entry entry : this.resources.entries()) {
                builder.add(ResourceInfo.of((String) entry.getValue(), (ClassLoader) entry.getKey()));
            }
            return builder.build();
        }

        protected void scanJarFile(ClassLoader classLoader, JarFile jarFile) {
            Enumeration entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) entries.nextElement();
                if (!jarEntry.isDirectory()) {
                    if (!jarEntry.getName().equals("META-INF/MANIFEST.MF")) {
                        this.resources.get(classLoader).add(jarEntry.getName());
                    }
                }
            }
        }

        protected void scanDirectory(ClassLoader classLoader, File file) throws IOException {
            Set hashSet = new HashSet();
            hashSet.add(file.getCanonicalFile());
            scanDirectory(file, classLoader, "", hashSet);
        }

        private void scanDirectory(File file, ClassLoader classLoader, String str, Set<File> set) throws IOException {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                Logger access$100 = ClassPath.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot read directory ");
                stringBuilder.append(file);
                access$100.warning(stringBuilder.toString());
                return;
            }
            for (File file2 : listFiles) {
                File file22;
                String name = file22.getName();
                if (file22.isDirectory()) {
                    file22 = file22.getCanonicalFile();
                    if (set.add(file22)) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(str);
                        stringBuilder2.append(name);
                        stringBuilder2.append("/");
                        scanDirectory(file22, classLoader, stringBuilder2.toString(), set);
                        set.remove(file22);
                    }
                } else {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(str);
                    stringBuilder3.append(name);
                    String stringBuilder4 = stringBuilder3.toString();
                    if (!stringBuilder4.equals("META-INF/MANIFEST.MF")) {
                        this.resources.get(classLoader).add(stringBuilder4);
                    }
                }
            }
        }
    }

    private ClassPath(ImmutableSet<ResourceInfo> immutableSet) {
        this.resources = immutableSet;
    }

    public static ClassPath from(ClassLoader classLoader) throws IOException {
        DefaultScanner defaultScanner = new DefaultScanner();
        defaultScanner.scan(classLoader);
        return new ClassPath(defaultScanner.getResources());
    }

    public ImmutableSet<ResourceInfo> getResources() {
        return this.resources;
    }

    public ImmutableSet<ClassInfo> getAllClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).filter(IS_TOP_LEVEL).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses(String str) {
        Preconditions.checkNotNull(str);
        Builder builder = ImmutableSet.builder();
        Iterator it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            Object obj = (ClassInfo) it.next();
            if (obj.getPackageName().equals(str)) {
                builder.add(obj);
            }
        }
        return builder.build();
    }

    public ImmutableSet<ClassInfo> getTopLevelClassesRecursive(String str) {
        Preconditions.checkNotNull(str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append('.');
        str = stringBuilder.toString();
        Builder builder = ImmutableSet.builder();
        Iterator it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            Object obj = (ClassInfo) it.next();
            if (obj.getName().startsWith(str)) {
                builder.add(obj);
            }
        }
        return builder.build();
    }

    @VisibleForTesting
    static String getClassName(String str) {
        return str.substring(0, str.length() - 6).replace('/', '.');
    }

    @VisibleForTesting
    static File toFile(URL url) {
        Preconditions.checkArgument(url.getProtocol().equals(UriUtil.LOCAL_FILE_SCHEME));
        try {
            return new File(url.toURI());
        } catch (URISyntaxException unused) {
            return new File(url.getPath());
        }
    }
}
