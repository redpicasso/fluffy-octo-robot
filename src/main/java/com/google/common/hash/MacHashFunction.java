package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.security.Key;
import javax.crypto.Mac;

@Immutable
final class MacHashFunction extends AbstractHashFunction {
    private final int bits = (this.prototype.getMacLength() * 8);
    private final Key key;
    private final Mac prototype;
    private final boolean supportsClone = supportsClone(this.prototype);
    private final String toString;

    private static final class MacHasher extends AbstractByteHasher {
        private boolean done;
        private final Mac mac;

        private MacHasher(Mac mac) {
            this.mac = mac;
        }

        protected void update(byte b) {
            checkNotDone();
            this.mac.update(b);
        }

        protected void update(byte[] bArr) {
            checkNotDone();
            this.mac.update(bArr);
        }

        protected void update(byte[] bArr, int i, int i2) {
            checkNotDone();
            this.mac.update(bArr, i, i2);
        }

        protected void update(ByteBuffer byteBuffer) {
            checkNotDone();
            Preconditions.checkNotNull(byteBuffer);
            this.mac.update(byteBuffer);
        }

        private void checkNotDone() {
            Preconditions.checkState(this.done ^ 1, "Cannot re-use a Hasher after calling hash() on it");
        }

        public HashCode hash() {
            checkNotDone();
            this.done = true;
            return HashCode.fromBytesNoCopy(this.mac.doFinal());
        }
    }

    MacHashFunction(String str, Key key, String str2) {
        this.prototype = getMac(str, key);
        this.key = (Key) Preconditions.checkNotNull(key);
        this.toString = (String) Preconditions.checkNotNull(str2);
    }

    public int bits() {
        return this.bits;
    }

    private static boolean supportsClone(Mac mac) {
        try {
            mac.clone();
            return true;
        } catch (CloneNotSupportedException unused) {
            return false;
        }
    }

    private static Mac getMac(String str, Key key) {
        try {
            Mac instance = Mac.getInstance(str);
            instance.init(key);
            return instance;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        } catch (Throwable e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public Hasher newHasher() {
        if (this.supportsClone) {
            try {
                return new MacHasher((Mac) this.prototype.clone());
            } catch (CloneNotSupportedException unused) {
                return new MacHasher(getMac(this.prototype.getAlgorithm(), this.key));
            }
        }
    }

    public String toString() {
        return this.toString;
    }
}
