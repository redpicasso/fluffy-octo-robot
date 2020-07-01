package com.google.firebase.database;

import androidx.annotation.NonNull;
import com.google.firebase.annotations.PublicApi;

@PublicApi
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface ValueEventListener {
    @PublicApi
    void onCancelled(@NonNull DatabaseError databaseError);

    @PublicApi
    void onDataChange(@NonNull DataSnapshot dataSnapshot);
}
