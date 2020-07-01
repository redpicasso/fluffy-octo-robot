package com.google.firebase.storage;

import com.google.firebase.annotations.PublicApi;
import java.io.IOException;

@PublicApi
/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class CancelException extends IOException {
    @PublicApi
    CancelException() {
        super("The operation was canceled.");
    }
}
