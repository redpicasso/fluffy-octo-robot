package com.google.firebase.database.android;

import androidx.annotation.NonNull;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.database.core.AuthTokenProvider;
import com.google.firebase.database.core.AuthTokenProvider.GetTokenCompletionListener;
import com.google.firebase.database.core.AuthTokenProvider.TokenChangeListener;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import java.util.concurrent.ExecutorService;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public abstract class AndroidAuthTokenProvider implements AuthTokenProvider {
    public static AuthTokenProvider forAuthenticatedAccess(@NonNull final InternalAuthProvider internalAuthProvider) {
        return new AuthTokenProvider() {
            public void removeTokenChangeListener(TokenChangeListener tokenChangeListener) {
            }

            public void getToken(boolean z, @NonNull GetTokenCompletionListener getTokenCompletionListener) {
                internalAuthProvider.getAccessToken(z).addOnSuccessListener(AndroidAuthTokenProvider$1$$Lambda$1.lambdaFactory$(getTokenCompletionListener)).addOnFailureListener(AndroidAuthTokenProvider$1$$Lambda$2.lambdaFactory$(getTokenCompletionListener));
            }

            static /* synthetic */ void lambda$getToken$1(GetTokenCompletionListener getTokenCompletionListener, Exception exception) {
                if (AndroidAuthTokenProvider.isUnauthenticatedUsage(exception)) {
                    getTokenCompletionListener.onSuccess(null);
                } else {
                    getTokenCompletionListener.onError(exception.getMessage());
                }
            }

            public void addTokenChangeListener(ExecutorService executorService, TokenChangeListener tokenChangeListener) {
                internalAuthProvider.addIdTokenListener(AndroidAuthTokenProvider$1$$Lambda$3.lambdaFactory$(executorService, tokenChangeListener));
            }
        };
    }

    public static AuthTokenProvider forUnauthenticatedAccess() {
        return new AuthTokenProvider() {
            public void removeTokenChangeListener(TokenChangeListener tokenChangeListener) {
            }

            public void getToken(boolean z, GetTokenCompletionListener getTokenCompletionListener) {
                getTokenCompletionListener.onSuccess(null);
            }

            public void addTokenChangeListener(ExecutorService executorService, TokenChangeListener tokenChangeListener) {
                executorService.execute(AndroidAuthTokenProvider$2$$Lambda$1.lambdaFactory$(tokenChangeListener));
            }
        };
    }

    private static boolean isUnauthenticatedUsage(Exception exception) {
        return (exception instanceof FirebaseApiNotAvailableException) || (exception instanceof FirebaseNoSignedInUserException);
    }
}
