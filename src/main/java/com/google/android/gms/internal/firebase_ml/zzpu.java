package com.google.android.gms.internal.firebase_ml;

import android.os.SystemClock;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public final class zzpu {
    private static final GmsLogger zzaoz = new GmsLogger("StreamingFormatChecker", "");
    private final LinkedList<Long> zzaxm = new LinkedList();
    private long zzaxn = -1;

    public final void zzb(zzpz zzpz) {
        if (zzpz.zzaxe.getBitmap() != null) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.zzaxm.add(Long.valueOf(elapsedRealtime));
            if (this.zzaxm.size() > 5) {
                this.zzaxm.removeFirst();
            }
            if (this.zzaxm.size() == 5 && elapsedRealtime - ((Long) this.zzaxm.peekFirst()).longValue() < 5000) {
                long j = this.zzaxn;
                if (j == -1 || elapsedRealtime - j >= TimeUnit.SECONDS.toMillis(5)) {
                    this.zzaxn = elapsedRealtime;
                    zzaoz.w("StreamingFormatChecker", "ML Kit has detected that you seem to pass camera frames to the detector as a Bitmap object. This is inefficient. Please use YUV_420_888 format for camera2 API or NV21 format for (legacy) camera API and directly pass down the byte array to ML Kit.");
                }
            }
        }
    }
}
