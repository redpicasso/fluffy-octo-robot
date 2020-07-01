package com.google.firebase.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.google.common.base.Preconditions;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.firestore.util.Util;
import com.google.protobuf.ByteString;

@PublicApi
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Blob implements Comparable<Blob> {
    private final ByteString bytes;

    private Blob(ByteString byteString) {
        this.bytes = byteString;
    }

    @PublicApi
    @NonNull
    public static Blob fromBytes(@NonNull byte[] bArr) {
        Preconditions.checkNotNull(bArr, "Provided bytes array must not be null.");
        return new Blob(ByteString.copyFrom(bArr));
    }

    @NonNull
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static Blob fromByteString(@NonNull ByteString byteString) {
        Preconditions.checkNotNull(byteString, "Provided ByteString must not be null.");
        return new Blob(byteString);
    }

    @PublicApi
    @NonNull
    public byte[] toBytes() {
        return this.bytes.toByteArray();
    }

    @NonNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Blob { bytes=");
        stringBuilder.append(Util.toDebugString(this.bytes));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @NonNull
    @RestrictTo({Scope.LIBRARY_GROUP})
    public ByteString toByteString() {
        return this.bytes;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Blob) && this.bytes.equals(((Blob) obj).bytes);
    }

    public int hashCode() {
        return this.bytes.hashCode();
    }

    @PublicApi
    public int compareTo(@NonNull Blob blob) {
        int min = Math.min(this.bytes.size(), blob.bytes.size());
        for (int i = 0; i < min; i++) {
            int byteAt = this.bytes.byteAt(i) & 255;
            int byteAt2 = blob.bytes.byteAt(i) & 255;
            if (byteAt < byteAt2) {
                return -1;
            }
            if (byteAt > byteAt2) {
                return 1;
            }
        }
        return Util.compareIntegers(this.bytes.size(), blob.bytes.size());
    }
}
