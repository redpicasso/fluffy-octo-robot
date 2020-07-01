package com.google.firebase.firestore.auth;

import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class User {
    public static final User UNAUTHENTICATED = new User(null);
    @Nullable
    private final String uid;

    public User(@Nullable String str) {
        this.uid = str;
    }

    @Nullable
    public String getUid() {
        return this.uid;
    }

    public boolean isAuthenticated() {
        return this.uid != null;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        String str = this.uid;
        if (str != null) {
            z = str.equals(user.uid);
        } else if (user.uid != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        String str = this.uid;
        return str != null ? str.hashCode() : 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User(uid:");
        stringBuilder.append(this.uid);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
