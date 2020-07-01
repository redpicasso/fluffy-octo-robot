package com.google.firebase.functions;

import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
final /* synthetic */ class FunctionsRegistrar$$Lambda$1 implements ComponentFactory {
    private static final FunctionsRegistrar$$Lambda$1 instance = new FunctionsRegistrar$$Lambda$1();

    private FunctionsRegistrar$$Lambda$1() {
    }

    public static ComponentFactory lambdaFactory$() {
        return instance;
    }

    public Object create(ComponentContainer componentContainer) {
        return new FirebaseContextProvider(componentContainer.getProvider(InternalAuthProvider.class), componentContainer.getProvider(FirebaseInstanceIdInternal.class));
    }
}
