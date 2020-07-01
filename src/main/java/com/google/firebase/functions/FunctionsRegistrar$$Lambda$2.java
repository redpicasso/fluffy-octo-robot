package com.google.firebase.functions;

import android.content.Context;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* compiled from: com.google.firebase:firebase-functions@@17.0.0 */
final /* synthetic */ class FunctionsRegistrar$$Lambda$2 implements ComponentFactory {
    private static final FunctionsRegistrar$$Lambda$2 instance = new FunctionsRegistrar$$Lambda$2();

    private FunctionsRegistrar$$Lambda$2() {
    }

    public static ComponentFactory lambdaFactory$() {
        return instance;
    }

    public Object create(ComponentContainer componentContainer) {
        return new FunctionsMultiResourceComponent((Context) componentContainer.get(Context.class), (ContextProvider) componentContainer.get(ContextProvider.class), ((FirebaseOptions) componentContainer.get(FirebaseOptions.class)).getProjectId());
    }
}
