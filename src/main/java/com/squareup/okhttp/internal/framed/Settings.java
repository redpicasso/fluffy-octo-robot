package com.squareup.okhttp.internal.framed;

import java.util.Arrays;

public final class Settings {
    static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
    static final int COUNT = 10;
    static final int CURRENT_CWND = 5;
    static final int DEFAULT_INITIAL_WINDOW_SIZE = 65536;
    static final int DOWNLOAD_BANDWIDTH = 2;
    static final int DOWNLOAD_RETRANS_RATE = 6;
    static final int ENABLE_PUSH = 2;
    static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 1;
    static final int FLOW_CONTROL_OPTIONS = 10;
    static final int FLOW_CONTROL_OPTIONS_DISABLED = 1;
    static final int HEADER_TABLE_SIZE = 1;
    static final int INITIAL_WINDOW_SIZE = 7;
    static final int MAX_CONCURRENT_STREAMS = 4;
    static final int MAX_FRAME_SIZE = 5;
    static final int MAX_HEADER_LIST_SIZE = 6;
    static final int PERSISTED = 2;
    static final int PERSIST_VALUE = 1;
    static final int ROUND_TRIP_TIME = 3;
    static final int UPLOAD_BANDWIDTH = 1;
    private int persistValue;
    private int persisted;
    private int set;
    private final int[] values = new int[10];

    void clear() {
        this.persisted = 0;
        this.persistValue = 0;
        this.set = 0;
        Arrays.fill(this.values, 0);
    }

    Settings set(int i, int i2, int i3) {
        if (i >= this.values.length) {
            return this;
        }
        int i4 = 1 << i;
        this.set |= i4;
        if ((i2 & 1) != 0) {
            this.persistValue |= i4;
        } else {
            this.persistValue &= ~i4;
        }
        if ((i2 & 2) != 0) {
            this.persisted |= i4;
        } else {
            this.persisted &= ~i4;
        }
        this.values[i] = i3;
        return this;
    }

    boolean isSet(int i) {
        return ((1 << i) & this.set) != 0;
    }

    int get(int i) {
        return this.values[i];
    }

    int flags(int i) {
        int i2 = isPersisted(i) ? 2 : 0;
        return persistValue(i) ? i2 | 1 : i2;
    }

    int size() {
        return Integer.bitCount(this.set);
    }

    int getUploadBandwidth(int i) {
        return (this.set & 2) != 0 ? this.values[1] : i;
    }

    int getHeaderTableSize() {
        return (this.set & 2) != 0 ? this.values[1] : -1;
    }

    int getDownloadBandwidth(int i) {
        return (this.set & 4) != 0 ? this.values[2] : i;
    }

    boolean getEnablePush(boolean z) {
        int i = (this.set & 4) != 0 ? this.values[2] : z ? 1 : 0;
        return i == 1;
    }

    int getRoundTripTime(int i) {
        return (this.set & 8) != 0 ? this.values[3] : i;
    }

    int getMaxConcurrentStreams(int i) {
        return (this.set & 16) != 0 ? this.values[4] : i;
    }

    int getCurrentCwnd(int i) {
        return (this.set & 32) != 0 ? this.values[5] : i;
    }

    int getMaxFrameSize(int i) {
        return (this.set & 32) != 0 ? this.values[5] : i;
    }

    int getDownloadRetransRate(int i) {
        return (this.set & 64) != 0 ? this.values[6] : i;
    }

    int getMaxHeaderListSize(int i) {
        return (this.set & 64) != 0 ? this.values[6] : i;
    }

    int getInitialWindowSize(int i) {
        return (this.set & 128) != 0 ? this.values[7] : i;
    }

    int getClientCertificateVectorSize(int i) {
        return (this.set & 256) != 0 ? this.values[8] : i;
    }

    boolean isFlowControlDisabled() {
        return (((this.set & 1024) != 0 ? this.values[10] : 0) & 1) != 0;
    }

    boolean persistValue(int i) {
        return ((1 << i) & this.persistValue) != 0;
    }

    boolean isPersisted(int i) {
        return ((1 << i) & this.persisted) != 0;
    }

    void merge(Settings settings) {
        for (int i = 0; i < 10; i++) {
            if (settings.isSet(i)) {
                set(i, settings.flags(i), settings.get(i));
            }
        }
    }
}
