package io.grpc.stub;

import com.google.common.base.Preconditions;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.grpc.ExperimentalApi;
import java.util.Iterator;

@ExperimentalApi("https://github.com/grpc/grpc-java/issues/4694")
public final class StreamObservers {
    public static <V> void copyWithFlowControl(final Iterator<V> it, final CallStreamObserver<V> callStreamObserver) {
        Preconditions.checkNotNull(it, Param.SOURCE);
        Preconditions.checkNotNull(callStreamObserver, "target");
        callStreamObserver.setOnReadyHandler(new Runnable() {
            public void run() {
                while (callStreamObserver.isReady() && it.hasNext()) {
                    callStreamObserver.onNext(it.next());
                }
                if (!it.hasNext()) {
                    callStreamObserver.onCompleted();
                }
            }
        });
    }

    public static <V> void copyWithFlowControl(Iterable<V> iterable, CallStreamObserver<V> callStreamObserver) {
        Preconditions.checkNotNull(iterable, Param.SOURCE);
        copyWithFlowControl(iterable.iterator(), (CallStreamObserver) callStreamObserver);
    }
}
