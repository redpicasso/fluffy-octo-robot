package io.grpc.okhttp;

import androidx.core.app.NotificationCompat;
import com.google.common.base.Preconditions;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.grpc.okhttp.internal.framed.FrameWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.Nullable;
import okio.Buffer;

class OutboundFlowController {
    private final OutboundFlowState connectionState = new OutboundFlowState(0);
    private final FrameWriter frameWriter;
    private int initialWindowSize = 65535;
    private final OkHttpClientTransport transport;

    private final class OutboundFlowState {
        int allocatedBytes;
        final Queue<Frame> pendingWriteQueue;
        int queuedBytes;
        OkHttpClientStream stream;
        final int streamId;
        int window;

        private final class Frame {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            final Buffer data;
            final boolean endStream;
            boolean enqueued;

            Frame(Buffer buffer, boolean z) {
                this.data = buffer;
                this.endStream = z;
            }

            int size() {
                return (int) this.data.size();
            }

            void enqueue() {
                if (!this.enqueued) {
                    this.enqueued = true;
                    OutboundFlowState.this.pendingWriteQueue.offer(this);
                    OutboundFlowState outboundFlowState = OutboundFlowState.this;
                    outboundFlowState.queuedBytes += size();
                }
            }

            void write() {
                do {
                    int size = size();
                    int min = Math.min(size, OutboundFlowController.this.frameWriter.maxDataLength());
                    if (min == size) {
                        int i = -size;
                        OutboundFlowController.this.connectionState.incrementStreamWindow(i);
                        OutboundFlowState.this.incrementStreamWindow(i);
                        try {
                            OutboundFlowController.this.frameWriter.data(this.endStream, OutboundFlowState.this.streamId, this.data, size);
                            OutboundFlowState.this.stream.transportState().onSentBytes(size);
                            if (this.enqueued) {
                                OutboundFlowState outboundFlowState = OutboundFlowState.this;
                                outboundFlowState.queuedBytes -= size;
                                OutboundFlowState.this.pendingWriteQueue.remove(this);
                            }
                            return;
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    }
                    split(min).write();
                } while (size() > 0);
            }

            Frame split(int i) {
                i = Math.min(i, (int) this.data.size());
                Buffer buffer = new Buffer();
                buffer.write(this.data, (long) i);
                Frame frame = new Frame(buffer, false);
                if (this.enqueued) {
                    OutboundFlowState outboundFlowState = OutboundFlowState.this;
                    outboundFlowState.queuedBytes -= i;
                }
                return frame;
            }
        }

        OutboundFlowState(int i) {
            this.window = OutboundFlowController.this.initialWindowSize;
            this.streamId = i;
            this.pendingWriteQueue = new ArrayDeque(2);
        }

        OutboundFlowState(OutboundFlowController outboundFlowController, OkHttpClientStream okHttpClientStream) {
            this(okHttpClientStream.id());
            this.stream = okHttpClientStream;
        }

        int window() {
            return this.window;
        }

        void allocateBytes(int i) {
            this.allocatedBytes += i;
        }

        int allocatedBytes() {
            return this.allocatedBytes;
        }

        int unallocatedBytes() {
            return streamableBytes() - this.allocatedBytes;
        }

        void clearAllocatedBytes() {
            this.allocatedBytes = 0;
        }

        int incrementStreamWindow(int i) {
            if (i <= 0 || Integer.MAX_VALUE - i >= this.window) {
                this.window += i;
                return this.window;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Window size overflow for stream: ");
            stringBuilder.append(this.streamId);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        int writableWindow() {
            return Math.min(this.window, OutboundFlowController.this.connectionState.window());
        }

        int streamableBytes() {
            return Math.max(0, Math.min(this.window, this.queuedBytes));
        }

        Frame newFrame(Buffer buffer, boolean z) {
            return new Frame(buffer, z);
        }

        boolean hasFrame() {
            return this.pendingWriteQueue.isEmpty() ^ 1;
        }

        private Frame peek() {
            return (Frame) this.pendingWriteQueue.peek();
        }

        int writeBytes(int i, WriteStatus writeStatus) {
            int min = Math.min(i, writableWindow());
            int i2 = 0;
            while (hasFrame()) {
                Frame peek = peek();
                if (min >= peek.size()) {
                    writeStatus.incrementNumWrites();
                    i2 += peek.size();
                    peek.write();
                } else if (min <= 0) {
                    break;
                } else {
                    Frame split = peek.split(min);
                    writeStatus.incrementNumWrites();
                    i2 += split.size();
                    split.write();
                }
                min = Math.min(i - i2, writableWindow());
            }
            return i2;
        }
    }

    private static final class WriteStatus {
        int numWrites;

        private WriteStatus() {
        }

        void incrementNumWrites() {
            this.numWrites++;
        }

        boolean hasWritten() {
            return this.numWrites > 0;
        }
    }

    OutboundFlowController(OkHttpClientTransport okHttpClientTransport, FrameWriter frameWriter) {
        this.transport = (OkHttpClientTransport) Preconditions.checkNotNull(okHttpClientTransport, NotificationCompat.CATEGORY_TRANSPORT);
        this.frameWriter = (FrameWriter) Preconditions.checkNotNull(frameWriter, "frameWriter");
    }

    boolean initialOutboundWindowSize(int i) {
        if (i >= 0) {
            int i2 = i - this.initialWindowSize;
            this.initialWindowSize = i;
            for (OkHttpClientStream okHttpClientStream : this.transport.getActiveStreams()) {
                OutboundFlowState outboundFlowState = (OutboundFlowState) okHttpClientStream.getOutboundFlowState();
                if (outboundFlowState == null) {
                    okHttpClientStream.setOutboundFlowState(new OutboundFlowState(this, okHttpClientStream));
                } else {
                    outboundFlowState.incrementStreamWindow(i2);
                }
            }
            if (i2 > 0) {
                return true;
            }
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid initial window size: ");
        stringBuilder.append(i);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    int windowUpdate(@Nullable OkHttpClientStream okHttpClientStream, int i) {
        if (okHttpClientStream == null) {
            int incrementStreamWindow = this.connectionState.incrementStreamWindow(i);
            writeStreams();
            return incrementStreamWindow;
        }
        OutboundFlowState state = state(okHttpClientStream);
        i = state.incrementStreamWindow(i);
        WriteStatus writeStatus = new WriteStatus();
        state.writeBytes(state.writableWindow(), writeStatus);
        if (writeStatus.hasWritten()) {
            flush();
        }
        return i;
    }

    void data(boolean z, int i, Buffer buffer, boolean z2) {
        Preconditions.checkNotNull(buffer, Param.SOURCE);
        OkHttpClientStream stream = this.transport.getStream(i);
        if (stream != null) {
            OutboundFlowState state = state(stream);
            int writableWindow = state.writableWindow();
            boolean hasFrame = state.hasFrame();
            Frame newFrame = state.newFrame(buffer, z);
            if (hasFrame || writableWindow < newFrame.size()) {
                newFrame.enqueue();
                if (hasFrame || writableWindow <= 0) {
                    if (z2) {
                        flush();
                    }
                    return;
                }
                newFrame.split(writableWindow).write();
                if (z2) {
                    flush();
                }
                return;
            }
            newFrame.write();
            if (z2) {
                flush();
            }
        }
    }

    void flush() {
        try {
            this.frameWriter.flush();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private OutboundFlowState state(OkHttpClientStream okHttpClientStream) {
        OutboundFlowState outboundFlowState = (OutboundFlowState) okHttpClientStream.getOutboundFlowState();
        if (outboundFlowState != null) {
            return outboundFlowState;
        }
        outboundFlowState = new OutboundFlowState(this, okHttpClientStream);
        okHttpClientStream.setOutboundFlowState(outboundFlowState);
        return outboundFlowState;
    }

    void writeStreams() {
        int i;
        WriteStatus writeStatus;
        OkHttpClientStream[] activeStreams;
        OkHttpClientStream[] activeStreams2 = this.transport.getActiveStreams();
        int window = this.connectionState.window();
        int length = activeStreams2.length;
        while (true) {
            i = 0;
            if (length <= 0 || window <= 0) {
                writeStatus = new WriteStatus();
                activeStreams = this.transport.getActiveStreams();
                length = activeStreams.length;
            } else {
                int ceil = (int) Math.ceil((double) (((float) window) / ((float) length)));
                int i2 = 0;
                while (i < length && window > 0) {
                    OkHttpClientStream okHttpClientStream = activeStreams2[i];
                    OutboundFlowState state = state(okHttpClientStream);
                    int min = Math.min(window, Math.min(state.unallocatedBytes(), ceil));
                    if (min > 0) {
                        state.allocateBytes(min);
                        window -= min;
                    }
                    if (state.unallocatedBytes() > 0) {
                        int i3 = i2 + 1;
                        activeStreams2[i2] = okHttpClientStream;
                        i2 = i3;
                    }
                    i++;
                }
                length = i2;
            }
        }
        writeStatus = new WriteStatus();
        activeStreams = this.transport.getActiveStreams();
        length = activeStreams.length;
        while (i < length) {
            OutboundFlowState state2 = state(activeStreams[i]);
            state2.writeBytes(state2.allocatedBytes(), writeStatus);
            state2.clearAllocatedBytes();
            i++;
        }
        if (writeStatus.hasWritten()) {
            flush();
        }
    }
}
