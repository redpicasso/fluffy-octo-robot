package com.google.firebase.functions;

import android.content.Context;
import androidx.annotation.GuardedBy;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
class FunctionsMultiResourceComponent {
    private final Context applicationContext;
    private final ContextProvider contextProvider;
    @GuardedBy("this")
    private final Map<String, FirebaseFunctions> instances = new HashMap();
    private final String projectId;

    FunctionsMultiResourceComponent(Context context, ContextProvider contextProvider, String str) {
        this.applicationContext = context;
        this.contextProvider = contextProvider;
        this.projectId = str;
    }

    synchronized FirebaseFunctions get(String str) {
        FirebaseFunctions firebaseFunctions;
        firebaseFunctions = (FirebaseFunctions) this.instances.get(str);
        if (firebaseFunctions == null) {
            firebaseFunctions = new FirebaseFunctions(this.applicationContext, this.projectId, str, this.contextProvider);
            this.instances.put(str, firebaseFunctions);
        }
        return firebaseFunctions;
    }
}
