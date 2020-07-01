package com.google.firebase.functions;

import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.inject.Provider;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
class FirebaseContextProvider implements ContextProvider {
    private final Provider<FirebaseInstanceIdInternal> instanceId;
    @Nullable
    private final Provider<InternalAuthProvider> tokenProvider;

    FirebaseContextProvider(@Nullable Provider<InternalAuthProvider> provider, Provider<FirebaseInstanceIdInternal> provider2) {
        this.tokenProvider = provider;
        this.instanceId = provider2;
    }

    public Task<HttpsCallableContext> getContext() {
        Provider provider = this.tokenProvider;
        if (provider != null) {
            return ((InternalAuthProvider) provider.get()).getAccessToken(false).continueWith(FirebaseContextProvider$$Lambda$1.lambdaFactory$(this));
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.setResult(new HttpsCallableContext(null, ((FirebaseInstanceIdInternal) this.instanceId.get()).getToken()));
        return taskCompletionSource.getTask();
    }

    static /* synthetic */ HttpsCallableContext lambda$getContext$0(FirebaseContextProvider firebaseContextProvider, Task task) throws Exception {
        String token;
        if (task.isSuccessful()) {
            token = ((GetTokenResult) task.getResult()).getToken();
        } else {
            Exception exception = task.getException();
            if (exception instanceof FirebaseNoSignedInUserException) {
                token = null;
            } else {
                throw exception;
            }
        }
        return new HttpsCallableContext(token, ((FirebaseInstanceIdInternal) firebaseContextProvider.instanceId.get()).getToken());
    }
}
