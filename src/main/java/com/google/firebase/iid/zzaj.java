package com.google.firebase.iid;

import android.os.Bundle;

/* compiled from: com.google.firebase:firebase-iid@@20.0.0 */
final class zzaj extends zzah<Bundle> {
    zzaj(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    final boolean zza() {
        return false;
    }

    final void zza(Bundle bundle) {
        Object bundle2 = bundle.getBundle("data");
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        zza(bundle2);
    }
}
