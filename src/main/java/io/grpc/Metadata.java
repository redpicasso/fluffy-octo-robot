package io.grpc;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public final class Metadata {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final AsciiMarshaller<String> ASCII_STRING_MARSHALLER = new AsciiMarshaller<String>() {
        public String parseAsciiString(String str) {
            return str;
        }

        public String toAsciiString(String str) {
            return str;
        }
    };
    public static final BinaryMarshaller<byte[]> BINARY_BYTE_MARSHALLER = new BinaryMarshaller<byte[]>() {
        public byte[] parseBytes(byte[] bArr) {
            return bArr;
        }

        public byte[] toBytes(byte[] bArr) {
            return bArr;
        }
    };
    public static final String BINARY_HEADER_SUFFIX = "-bin";
    private byte[][] namesAndValues;
    private int size;

    public interface AsciiMarshaller<T> {
        T parseAsciiString(String str);

        String toAsciiString(T t);
    }

    public interface BinaryMarshaller<T> {
        T parseBytes(byte[] bArr);

        byte[] toBytes(T t);
    }

    private final class IterableAt<T> implements Iterable<T> {
        private final Key<T> key;
        private int startIdx;

        /* synthetic */ IterableAt(Metadata metadata, Key key, int i, AnonymousClass1 anonymousClass1) {
            this(key, i);
        }

        private IterableAt(Key<T> key, int i) {
            this.key = key;
            this.startIdx = i;
        }

        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private boolean hasNext = true;
                private int idx = IterableAt.this.startIdx;

                public boolean hasNext() {
                    if (this.hasNext) {
                        return true;
                    }
                    while (this.idx < Metadata.this.size) {
                        if (Metadata.this.bytesEqual(IterableAt.this.key.asciiName(), Metadata.this.name(this.idx))) {
                            this.hasNext = true;
                            return this.hasNext;
                        }
                        this.idx++;
                    }
                    return false;
                }

                public T next() {
                    if (hasNext()) {
                        this.hasNext = false;
                        Key access$200 = IterableAt.this.key;
                        Metadata metadata = Metadata.this;
                        int i = this.idx;
                        this.idx = i + 1;
                        return access$200.parseBytes(metadata.value(i));
                    }
                    throw new NoSuchElementException();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    @Immutable
    public static abstract class Key<T> {
        private static final BitSet VALID_T_CHARS = generateValidTChars();
        private final String name;
        private final byte[] nameBytes;
        private final String originalName;

        abstract T parseBytes(byte[] bArr);

        abstract byte[] toBytes(T t);

        /* synthetic */ Key(String str, boolean z, AnonymousClass1 anonymousClass1) {
            this(str, z);
        }

        public static <T> Key<T> of(String str, BinaryMarshaller<T> binaryMarshaller) {
            return new BinaryKey(str, binaryMarshaller, null);
        }

        public static <T> Key<T> of(String str, AsciiMarshaller<T> asciiMarshaller) {
            return of(str, false, (AsciiMarshaller) asciiMarshaller);
        }

        static <T> Key<T> of(String str, boolean z, AsciiMarshaller<T> asciiMarshaller) {
            return new AsciiKey(str, z, asciiMarshaller, null);
        }

        static <T> Key<T> of(String str, boolean z, TrustedAsciiMarshaller<T> trustedAsciiMarshaller) {
            return new TrustedAsciiKey(str, z, trustedAsciiMarshaller, null);
        }

        private static BitSet generateValidTChars() {
            int i;
            BitSet bitSet = new BitSet(127);
            bitSet.set(45);
            bitSet.set(95);
            bitSet.set(46);
            for (i = 48; i <= 57; i = (char) (i + 1)) {
                bitSet.set(i);
            }
            for (i = 97; i <= 122; i = (char) (i + 1)) {
                bitSet.set(i);
            }
            return bitSet;
        }

        private static String validateName(String str, boolean z) {
            Preconditions.checkNotNull(str, ConditionalUserProperty.NAME);
            Preconditions.checkArgument(str.isEmpty() ^ 1, "token must have at least 1 tchar");
            int i = 0;
            while (i < str.length()) {
                char charAt = str.charAt(i);
                if (!z || charAt != ':' || i != 0) {
                    Preconditions.checkArgument(VALID_T_CHARS.get(charAt), "Invalid character '%s' in key name '%s'", charAt, (Object) str);
                }
                i++;
            }
            return str;
        }

        private Key(String str, boolean z) {
            this.originalName = (String) Preconditions.checkNotNull(str, ConditionalUserProperty.NAME);
            this.name = validateName(this.originalName.toLowerCase(Locale.ROOT), z);
            this.nameBytes = this.name.getBytes(Charsets.US_ASCII);
        }

        public final String originalName() {
            return this.originalName;
        }

        public final String name() {
            return this.name;
        }

        @VisibleForTesting
        byte[] asciiName() {
            return this.nameBytes;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.name.equals(((Key) obj).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Key{name='");
            stringBuilder.append(this.name);
            stringBuilder.append("'}");
            return stringBuilder.toString();
        }
    }

    @Immutable
    interface TrustedAsciiMarshaller<T> {
        T parseAsciiString(byte[] bArr);

        byte[] toAsciiString(T t);
    }

    private static class AsciiKey<T> extends Key<T> {
        private final AsciiMarshaller<T> marshaller;

        /* synthetic */ AsciiKey(String str, boolean z, AsciiMarshaller asciiMarshaller, AnonymousClass1 anonymousClass1) {
            this(str, z, asciiMarshaller);
        }

        private AsciiKey(String str, boolean z, AsciiMarshaller<T> asciiMarshaller) {
            super(str, z, null);
            Object obj = Metadata.BINARY_HEADER_SUFFIX;
            Preconditions.checkArgument(str.endsWith(obj) ^ 1, "ASCII header is named %s.  Only binary headers may end with %s", (Object) str, obj);
            this.marshaller = (AsciiMarshaller) Preconditions.checkNotNull(asciiMarshaller, "marshaller");
        }

        byte[] toBytes(T t) {
            return this.marshaller.toAsciiString(t).getBytes(Charsets.US_ASCII);
        }

        T parseBytes(byte[] bArr) {
            return this.marshaller.parseAsciiString(new String(bArr, Charsets.US_ASCII));
        }
    }

    private static class BinaryKey<T> extends Key<T> {
        private final BinaryMarshaller<T> marshaller;

        /* synthetic */ BinaryKey(String str, BinaryMarshaller binaryMarshaller, AnonymousClass1 anonymousClass1) {
            this(str, binaryMarshaller);
        }

        private BinaryKey(String str, BinaryMarshaller<T> binaryMarshaller) {
            boolean z = false;
            super(str, false, null);
            Object obj = Metadata.BINARY_HEADER_SUFFIX;
            Preconditions.checkArgument(str.endsWith(obj), "Binary header is named %s. It must end with %s", (Object) str, obj);
            if (str.length() > 4) {
                z = true;
            }
            Preconditions.checkArgument(z, "empty key name");
            this.marshaller = (BinaryMarshaller) Preconditions.checkNotNull(binaryMarshaller, "marshaller is null");
        }

        byte[] toBytes(T t) {
            return this.marshaller.toBytes(t);
        }

        T parseBytes(byte[] bArr) {
            return this.marshaller.parseBytes(bArr);
        }
    }

    private static final class TrustedAsciiKey<T> extends Key<T> {
        private final TrustedAsciiMarshaller<T> marshaller;

        /* synthetic */ TrustedAsciiKey(String str, boolean z, TrustedAsciiMarshaller trustedAsciiMarshaller, AnonymousClass1 anonymousClass1) {
            this(str, z, trustedAsciiMarshaller);
        }

        private TrustedAsciiKey(String str, boolean z, TrustedAsciiMarshaller<T> trustedAsciiMarshaller) {
            super(str, z, null);
            Object obj = Metadata.BINARY_HEADER_SUFFIX;
            Preconditions.checkArgument(str.endsWith(obj) ^ 1, "ASCII header is named %s.  Only binary headers may end with %s", (Object) str, obj);
            this.marshaller = (TrustedAsciiMarshaller) Preconditions.checkNotNull(trustedAsciiMarshaller, "marshaller");
        }

        byte[] toBytes(T t) {
            return this.marshaller.toAsciiString(t);
        }

        T parseBytes(byte[] bArr) {
            return this.marshaller.parseAsciiString(bArr);
        }
    }

    Metadata(byte[]... bArr) {
        this(bArr.length / 2, bArr);
    }

    Metadata(int i, byte[]... bArr) {
        this.size = i;
        this.namesAndValues = bArr;
    }

    private byte[] name(int i) {
        return this.namesAndValues[i * 2];
    }

    private void name(int i, byte[] bArr) {
        this.namesAndValues[i * 2] = bArr;
    }

    private byte[] value(int i) {
        return this.namesAndValues[(i * 2) + 1];
    }

    private void value(int i, byte[] bArr) {
        this.namesAndValues[(i * 2) + 1] = bArr;
    }

    private int cap() {
        byte[][] bArr = this.namesAndValues;
        return bArr != null ? bArr.length : 0;
    }

    private int len() {
        return this.size * 2;
    }

    private boolean isEmpty() {
        return this.size == 0;
    }

    int headerCount() {
        return this.size;
    }

    public boolean containsKey(Key<?> key) {
        for (int i = 0; i < this.size; i++) {
            if (bytesEqual(key.asciiName(), name(i))) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public <T> T get(Key<T> key) {
        for (int i = this.size - 1; i >= 0; i--) {
            if (bytesEqual(key.asciiName(), name(i))) {
                return key.parseBytes(value(i));
            }
        }
        return null;
    }

    @Nullable
    public <T> Iterable<T> getAll(Key<T> key) {
        for (int i = 0; i < this.size; i++) {
            if (bytesEqual(key.asciiName(), name(i))) {
                return new IterableAt(this, key, i, null);
            }
        }
        return null;
    }

    public Set<String> keys() {
        if (isEmpty()) {
            return Collections.emptySet();
        }
        Set hashSet = new HashSet(this.size);
        for (int i = 0; i < this.size; i++) {
            hashSet.add(new String(name(i), 0));
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public <T> void put(Key<T> key, T t) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(t, "value");
        maybeExpand();
        name(this.size, key.asciiName());
        value(this.size, key.toBytes(t));
        this.size++;
    }

    private void maybeExpand() {
        if (len() == 0 || len() == cap()) {
            expand(Math.max(len() * 2, 8));
        }
    }

    private void expand(int i) {
        Object obj = new byte[i][];
        if (!isEmpty()) {
            System.arraycopy(this.namesAndValues, 0, obj, 0, len());
        }
        this.namesAndValues = obj;
    }

    public <T> boolean remove(Key<T> key, T t) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(t, "value");
        int i = 0;
        while (i < this.size) {
            if (bytesEqual(key.asciiName(), name(i)) && t.equals(key.parseBytes(value(i)))) {
                int i2 = i * 2;
                i = (i + 1) * 2;
                int len = len() - i;
                Object obj = this.namesAndValues;
                System.arraycopy(obj, i, obj, i2, len);
                this.size--;
                name(this.size, null);
                value(this.size, null);
                return true;
            }
            i++;
        }
        return false;
    }

    public <T> Iterable<T> removeAll(Key<T> key) {
        if (isEmpty()) {
            return null;
        }
        Iterable<T> iterable = null;
        int i = 0;
        for (int i2 = 0; i2 < this.size; i2++) {
            if (bytesEqual(key.asciiName(), name(i2))) {
                if (iterable == null) {
                    iterable = new ArrayList();
                }
                iterable.add(key.parseBytes(value(i2)));
            } else {
                name(i, name(i2));
                value(i, value(i2));
                i++;
            }
        }
        Arrays.fill(this.namesAndValues, i * 2, len(), null);
        this.size = i;
        return iterable;
    }

    @ExperimentalApi("https://github.com/grpc/grpc-java/issues/4691")
    public <T> void discardAll(Key<T> key) {
        if (!isEmpty()) {
            int i = 0;
            for (int i2 = 0; i2 < this.size; i2++) {
                if (!bytesEqual(key.asciiName(), name(i2))) {
                    name(i, name(i2));
                    value(i, value(i2));
                    i++;
                }
            }
            Arrays.fill(this.namesAndValues, i * 2, len(), null);
            this.size = i;
        }
    }

    @Nullable
    byte[][] serialize() {
        if (len() == cap()) {
            return this.namesAndValues;
        }
        Object obj = new byte[len()][];
        System.arraycopy(this.namesAndValues, 0, obj, 0, len());
        return obj;
    }

    public void merge(Metadata metadata) {
        if (!metadata.isEmpty()) {
            int cap = cap() - len();
            if (isEmpty() || cap < metadata.len()) {
                expand(len() + metadata.len());
            }
            System.arraycopy(metadata.namesAndValues, 0, this.namesAndValues, len(), metadata.len());
            this.size += metadata.size;
        }
    }

    public void merge(Metadata metadata, Set<Key<?>> set) {
        Preconditions.checkNotNull(metadata, "other");
        Map hashMap = new HashMap(set.size());
        for (Key key : set) {
            hashMap.put(ByteBuffer.wrap(key.asciiName()), key);
        }
        for (int i = 0; i < metadata.size; i++) {
            if (hashMap.containsKey(ByteBuffer.wrap(metadata.name(i)))) {
                maybeExpand();
                name(this.size, metadata.name(i));
                value(this.size, metadata.value(i));
                this.size++;
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Metadata(");
        for (int i = 0; i < this.size; i++) {
            if (i != 0) {
                stringBuilder.append(',');
            }
            String str = new String(name(i), Charsets.US_ASCII);
            stringBuilder.append(str);
            stringBuilder.append('=');
            if (str.endsWith(BINARY_HEADER_SUFFIX)) {
                stringBuilder.append(BaseEncoding.base64().encode(value(i)));
            } else {
                stringBuilder.append(new String(value(i), Charsets.US_ASCII));
            }
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    private boolean bytesEqual(byte[] bArr, byte[] bArr2) {
        return Arrays.equals(bArr, bArr2);
    }
}
