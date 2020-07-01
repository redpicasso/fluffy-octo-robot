package com.google.firebase.database;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import com.google.firebase.annotations.PublicApi;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DatabaseException extends RuntimeException {
    @RestrictTo({Scope.LIBRARY_GROUP})
    public DatabaseException(String str) {
        super(str);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public DatabaseException(String str, Throwable th) {
        super(str, th);
    }
}
