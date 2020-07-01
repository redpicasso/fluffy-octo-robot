package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class MemoryPooledByteBufferFactory implements PooledByteBufferFactory {
    private final MemoryChunkPool mPool;
    private final PooledByteStreams mPooledByteStreams;

    public MemoryPooledByteBufferFactory(MemoryChunkPool memoryChunkPool, PooledByteStreams pooledByteStreams) {
        this.mPool = memoryChunkPool;
        this.mPooledByteStreams = pooledByteStreams;
    }

    public MemoryPooledByteBuffer newByteBuffer(int i) {
        Preconditions.checkArgument(i > 0);
        Object obj = this.mPool.get(i);
        MemoryPooledByteBuffer memoryPooledByteBuffer = this.mPool;
        CloseableReference of = CloseableReference.of(obj, (ResourceReleaser) memoryPooledByteBuffer);
        try {
            memoryPooledByteBuffer = new MemoryPooledByteBuffer(of, i);
            return memoryPooledByteBuffer;
        } finally {
            of.close();
        }
    }

    public MemoryPooledByteBuffer newByteBuffer(InputStream inputStream) throws IOException {
        MemoryPooledByteBufferOutputStream memoryPooledByteBufferOutputStream = new MemoryPooledByteBufferOutputStream(this.mPool);
        try {
            MemoryPooledByteBuffer newByteBuf = newByteBuf(inputStream, memoryPooledByteBufferOutputStream);
            return newByteBuf;
        } finally {
            memoryPooledByteBufferOutputStream.close();
        }
    }

    public MemoryPooledByteBuffer newByteBuffer(byte[] bArr) {
        MemoryPooledByteBufferOutputStream memoryPooledByteBufferOutputStream = new MemoryPooledByteBufferOutputStream(this.mPool, bArr.length);
        try {
            memoryPooledByteBufferOutputStream.write(bArr, 0, bArr.length);
            MemoryPooledByteBuffer toByteBuffer = memoryPooledByteBufferOutputStream.toByteBuffer();
            memoryPooledByteBufferOutputStream.close();
            return toByteBuffer;
        } catch (Throwable e) {
            throw Throwables.propagate(e);
        } catch (Throwable th) {
            memoryPooledByteBufferOutputStream.close();
        }
    }

    public MemoryPooledByteBuffer newByteBuffer(InputStream inputStream, int i) throws IOException {
        MemoryPooledByteBufferOutputStream memoryPooledByteBufferOutputStream = new MemoryPooledByteBufferOutputStream(this.mPool, i);
        try {
            MemoryPooledByteBuffer newByteBuf = newByteBuf(inputStream, memoryPooledByteBufferOutputStream);
            return newByteBuf;
        } finally {
            memoryPooledByteBufferOutputStream.close();
        }
    }

    @VisibleForTesting
    MemoryPooledByteBuffer newByteBuf(InputStream inputStream, MemoryPooledByteBufferOutputStream memoryPooledByteBufferOutputStream) throws IOException {
        this.mPooledByteStreams.copy(inputStream, memoryPooledByteBufferOutputStream);
        return memoryPooledByteBufferOutputStream.toByteBuffer();
    }

    public MemoryPooledByteBufferOutputStream newOutputStream() {
        return new MemoryPooledByteBufferOutputStream(this.mPool);
    }

    public MemoryPooledByteBufferOutputStream newOutputStream(int i) {
        return new MemoryPooledByteBufferOutputStream(this.mPool, i);
    }
}
