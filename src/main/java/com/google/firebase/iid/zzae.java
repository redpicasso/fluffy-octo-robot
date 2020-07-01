package com.google.firebase.iid;

import android.os.Bundle;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzae extends zzah<Void> {
    zzae(int i, int i2, Bundle bundle) {
        super(i, 2, bundle);
    }

    final boolean zza() {
        return true;
    }

    final void zza(Bundle bundle) {
        if (bundle.getBoolean("ack", false)) {
            zza(null);
        } else {
            zza(new zzag(4, "Invalid response to one way request"));
        }
    }
}
