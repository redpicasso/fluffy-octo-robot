package com.google.firebase.auth;

import androidx.annotation.Keep;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

@KeepForSdk
@Keep
public class FirebaseAuthRegistrar implements ComponentRegistrar {
    @Keep
    public List<Component<?>> getComponents() {
        r0 = new Component[2];
        r0[0] = Component.builder(FirebaseAuth.class, InternalAuthProvider.class).add(Dependency.required(FirebaseApp.class)).factory(zzp.zzjd).alwaysEager().build();
        r0[1] = LibraryVersionComponent.create("fire-auth", "17.0.0");
        return Arrays.asList(r0);
    }
}
