package com.google.firebase.functions;

import android.content.Context;
import androidx.annotation.Keep;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

@Keep
/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
public class FunctionsRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(ContextProvider.class).add(Dependency.optionalProvider(InternalAuthProvider.class)).add(Dependency.requiredProvider(FirebaseInstanceIdInternal.class)).factory(FunctionsRegistrar$$Lambda$1.lambdaFactory$()).build(), Component.builder(FunctionsMultiResourceComponent.class).add(Dependency.required(Context.class)).add(Dependency.required(ContextProvider.class)).add(Dependency.required(FirebaseOptions.class)).factory(FunctionsRegistrar$$Lambda$2.lambdaFactory$()).build(), LibraryVersionComponent.create("fire-fn", "17.0.0")});
    }
}
