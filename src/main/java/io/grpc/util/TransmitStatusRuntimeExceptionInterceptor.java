package io.grpc.util;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import io.grpc.Attributes;
import io.grpc.ExperimentalApi;
import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.SerializingExecutor;
import javax.annotation.Nullable;

@ExperimentalApi("https://github.com/grpc/grpc-java/issues/2189")
public final class TransmitStatusRuntimeExceptionInterceptor implements ServerInterceptor {

    private static class SerializingServerCall<ReqT, RespT> extends SimpleForwardingServerCall<ReqT, RespT> {
        private static final String ERROR_MSG = "Encountered error during serialized access";
        private boolean closeCalled = false;
        private final SerializingExecutor serializingExecutor = new SerializingExecutor(MoreExecutors.directExecutor());

        SerializingServerCall(ServerCall<ReqT, RespT> serverCall) {
            super(serverCall);
        }

        public void sendMessage(final RespT respT) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    super.access$001(respT);
                }
            });
        }

        public void request(final int i) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    super.access$101(i);
                }
            });
        }

        public void sendHeaders(final Metadata metadata) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    super.access$201(metadata);
                }
            });
        }

        public void close(final Status status, final Metadata metadata) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    if (!SerializingServerCall.this.closeCalled) {
                        SerializingServerCall.this.closeCalled = true;
                        super.access$401(status, metadata);
                    }
                }
            });
        }

        public boolean isReady() {
            String str = ERROR_MSG;
            final SettableFuture create = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    create.set(Boolean.valueOf(super.access$501()));
                }
            });
            try {
                str = ((Boolean) create.get()).booleanValue();
                return str;
            } catch (Throwable e) {
                throw new RuntimeException(str, e);
            } catch (Throwable e2) {
                throw new RuntimeException(str, e2);
            }
        }

        public boolean isCancelled() {
            String str = ERROR_MSG;
            final SettableFuture create = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    create.set(Boolean.valueOf(super.access$601()));
                }
            });
            try {
                str = ((Boolean) create.get()).booleanValue();
                return str;
            } catch (Throwable e) {
                throw new RuntimeException(str, e);
            } catch (Throwable e2) {
                throw new RuntimeException(str, e2);
            }
        }

        public void setMessageCompression(final boolean z) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    super.access$701(z);
                }
            });
        }

        public void setCompression(final String str) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    super.access$801(str);
                }
            });
        }

        public Attributes getAttributes() {
            String str = ERROR_MSG;
            final SettableFuture create = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    create.set(super.access$901());
                }
            });
            try {
                return (Attributes) create.get();
            } catch (Throwable e) {
                throw new RuntimeException(str, e);
            } catch (Throwable e2) {
                throw new RuntimeException(str, e2);
            }
        }

        @Nullable
        public String getAuthority() {
            String str = ERROR_MSG;
            final SettableFuture create = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    create.set(super.access$1001());
                }
            });
            try {
                return (String) create.get();
            } catch (Throwable e) {
                throw new RuntimeException(str, e);
            } catch (Throwable e2) {
                throw new RuntimeException(str, e2);
            }
        }
    }

    private TransmitStatusRuntimeExceptionInterceptor() {
    }

    public static ServerInterceptor instance() {
        return new TransmitStatusRuntimeExceptionInterceptor();
    }

    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        final ServerCall serializingServerCall = new SerializingServerCall(serverCall);
        return new SimpleForwardingServerCallListener<ReqT>(serverCallHandler.startCall(serializingServerCall, metadata)) {
            public void onMessage(ReqT reqT) {
                try {
                    super.onMessage(reqT);
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            public void onCancel() {
                try {
                    super.onCancel();
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            public void onComplete() {
                try {
                    super.onComplete();
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            public void onReady() {
                try {
                    super.onReady();
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            private void closeWithException(StatusRuntimeException statusRuntimeException) {
                Metadata trailers = statusRuntimeException.getTrailers();
                if (trailers == null) {
                    trailers = new Metadata();
                }
                serializingServerCall.close(statusRuntimeException.getStatus(), trailers);
            }
        };
    }
}
