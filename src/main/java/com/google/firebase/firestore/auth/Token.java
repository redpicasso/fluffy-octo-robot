package com.google.firebase.firestore.auth;

import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Token {
    private final User user;
    @Nullable
    private final String value;

    public Token(@Nullable String str, User user) {
        this.value = str;
        this.user = user;
    }

    @Nullable
    public String getValue() {
        return this.value;
    }

    public User getUser() {
        return this.user;
    }
}
