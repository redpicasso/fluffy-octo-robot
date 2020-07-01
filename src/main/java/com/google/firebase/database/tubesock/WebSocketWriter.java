package com.google.firebase.database.tubesock;

import com.drew.metadata.exif.ExifDirectoryBase;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class WebSocketWriter {
    private WritableByteChannel channel;
    private boolean closeSent = false;
    private final Thread innerThread = WebSocket.getThreadFactory().newThread(new Runnable() {
        public void run() {
            WebSocketWriter.this.runWriter();
        }
    });
    private BlockingQueue<ByteBuffer> pendingBuffers;
    private final Random random = new Random();
    private volatile boolean stop = false;
    private WebSocket websocket;

    WebSocketWriter(WebSocket webSocket, String str, int i) {
        ThreadInitializer intializer = WebSocket.getIntializer();
        Thread innerThread = getInnerThread();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("Writer-");
        stringBuilder.append(i);
        intializer.setName(innerThread, stringBuilder.toString());
        this.websocket = webSocket;
        this.pendingBuffers = new LinkedBlockingQueue();
    }

    void setOutput(OutputStream outputStream) {
        this.channel = Channels.newChannel(outputStream);
    }

    private ByteBuffer frameInBuffer(byte b, boolean z, byte[] bArr) throws IOException {
        int i = z ? 6 : 2;
        int length = bArr.length;
        int i2 = 126;
        if (length >= 126) {
            i = length <= 65535 ? i + 2 : i + 8;
        }
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + i);
        allocate.put((byte) (b | -128));
        int i3 = 0;
        if (length < 126) {
            if (z) {
                length |= 128;
            }
            allocate.put((byte) length);
        } else if (length <= 65535) {
            if (z) {
                i2 = ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE;
            }
            allocate.put((byte) i2);
            allocate.putShort((short) length);
        } else {
            int i4 = 127;
            if (z) {
                i4 = 255;
            }
            allocate.put((byte) i4);
            allocate.putInt(0);
            allocate.putInt(length);
        }
        if (z) {
            byte[] generateMask = generateMask();
            allocate.put(generateMask);
            while (i3 < bArr.length) {
                allocate.put((byte) (bArr[i3] ^ generateMask[i3 % 4]));
                i3++;
            }
        }
        allocate.flip();
        return allocate;
    }

    private byte[] generateMask() {
        byte[] bArr = new byte[4];
        this.random.nextBytes(bArr);
        return bArr;
    }

    synchronized void send(byte b, boolean z, byte[] bArr) throws IOException {
        ByteBuffer frameInBuffer = frameInBuffer(b, z, bArr);
        if (!this.stop || (!this.closeSent && b == (byte) 8)) {
            if (b == (byte) 8) {
                this.closeSent = true;
            }
            this.pendingBuffers.add(frameInBuffer);
        } else {
            throw new WebSocketException("Shouldn't be sending");
        }
    }

    private void writeMessage() throws InterruptedException, IOException {
        this.channel.write((ByteBuffer) this.pendingBuffers.take());
    }

    void stopIt() {
        this.stop = true;
    }

    private void handleError(WebSocketException webSocketException) {
        this.websocket.handleReceiverError(webSocketException);
    }

    private void runWriter() {
        while (!this.stop && !Thread.interrupted()) {
            try {
                writeMessage();
            } catch (Throwable e) {
                handleError(new WebSocketException("IO Exception", e));
            } catch (InterruptedException unused) {
                return;
            }
        }
        for (int i = 0; i < this.pendingBuffers.size(); i++) {
            writeMessage();
        }
    }

    Thread getInnerThread() {
        return this.innerThread;
    }
}
