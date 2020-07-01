package com.google.firebase.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.annotations.PublicApi;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface ChildEventListener {
    @PublicApi
    void onCancelled(@NonNull DatabaseError databaseError);

    @PublicApi
    void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    @PublicApi
    void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    @PublicApi
    void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String str);

    @PublicApi
    void onChildRemoved(@NonNull DataSnapshot dataSnapshot);
}
