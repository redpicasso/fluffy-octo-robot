package com.google.firebase.functions;

import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
interface ContextProvider {
    Task<HttpsCallableContext> getContext();
}
