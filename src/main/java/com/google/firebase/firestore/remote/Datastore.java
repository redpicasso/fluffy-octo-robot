package com.google.firebase.firestore.remote;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationResult;
import com.google.firebase.firestore.remote.WriteStream.Callback;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.FirestoreChannel;
import com.google.firebase.firestore.util.Supplier;
import com.google.firestore.v1.BatchGetDocumentsRequest;
import com.google.firestore.v1.BatchGetDocumentsResponse;
import com.google.firestore.v1.CommitRequest;
import com.google.firestore.v1.CommitRequest.Builder;
import com.google.firestore.v1.CommitResponse;
import com.google.firestore.v1.FirestoreGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.android.AndroidChannelBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Datastore {
    public static final Set<String> WHITE_LISTED_HEADERS = new HashSet(Arrays.asList(new String[]{"date", "x-google-backends", "x-google-netmon-label", "x-google-service", "x-google-gfe-request-trace"}));
    private static Supplier<ManagedChannelBuilder<?>> overrideChannelBuilderSupplier;
    private final FirestoreChannel channel;
    private final DatabaseInfo databaseInfo;
    private final RemoteSerializer serializer;
    private final AsyncQueue workerQueue;

    @VisibleForTesting
    public static void overrideChannelBuilder(Supplier<ManagedChannelBuilder<?>> supplier) {
        overrideChannelBuilderSupplier = supplier;
    }

    public Datastore(DatabaseInfo databaseInfo, AsyncQueue asyncQueue, CredentialsProvider credentialsProvider, Context context) {
        ManagedChannelBuilder managedChannelBuilder;
        this.databaseInfo = databaseInfo;
        this.workerQueue = asyncQueue;
        this.serializer = new RemoteSerializer(databaseInfo.getDatabaseId());
        Supplier supplier = overrideChannelBuilderSupplier;
        if (supplier != null) {
            managedChannelBuilder = (ManagedChannelBuilder) supplier.get();
        } else {
            managedChannelBuilder = ManagedChannelBuilder.forTarget(databaseInfo.getHost());
            if (!databaseInfo.isSslEnabled()) {
                managedChannelBuilder.usePlaintext();
            }
        }
        managedChannelBuilder.keepAliveTime(30, TimeUnit.SECONDS);
        managedChannelBuilder.executor(asyncQueue.getExecutor());
        this.channel = new FirestoreChannel(asyncQueue, credentialsProvider, AndroidChannelBuilder.fromBuilder(managedChannelBuilder).context(context).build(), databaseInfo.getDatabaseId());
    }

    void shutdown() {
        this.channel.shutdown();
    }

    AsyncQueue getWorkerQueue() {
        return this.workerQueue;
    }

    DatabaseInfo getDatabaseInfo() {
        return this.databaseInfo;
    }

    WatchStream createWatchStream(Callback callback) {
        return new WatchStream(this.channel, this.workerQueue, this.serializer, callback);
    }

    WriteStream createWriteStream(Callback callback) {
        return new WriteStream(this.channel, this.workerQueue, this.serializer, callback);
    }

    public Task<List<MutationResult>> commit(List<Mutation> list) {
        Builder newBuilder = CommitRequest.newBuilder();
        newBuilder.setDatabase(this.serializer.databaseName());
        for (Mutation encodeMutation : list) {
            newBuilder.addWrites(this.serializer.encodeMutation(encodeMutation));
        }
        return this.channel.runRpc(FirestoreGrpc.getCommitMethod(), (CommitRequest) newBuilder.build()).continueWith(this.workerQueue.getExecutor(), Datastore$$Lambda$1.lambdaFactory$(this));
    }

    static /* synthetic */ List lambda$commit$0(Datastore datastore, Task task) throws Exception {
        if (task.isSuccessful()) {
            CommitResponse commitResponse = (CommitResponse) task.getResult();
            SnapshotVersion decodeVersion = datastore.serializer.decodeVersion(commitResponse.getCommitTime());
            int writeResultsCount = commitResponse.getWriteResultsCount();
            List arrayList = new ArrayList(writeResultsCount);
            for (int i = 0; i < writeResultsCount; i++) {
                arrayList.add(datastore.serializer.decodeMutationResult(commitResponse.getWriteResults(i), decodeVersion));
            }
            return arrayList;
        }
        if ((task.getException() instanceof FirebaseFirestoreException) && ((FirebaseFirestoreException) task.getException()).getCode() == Code.UNAUTHENTICATED) {
            datastore.channel.invalidateToken();
        }
        throw task.getException();
    }

    public Task<List<MaybeDocument>> lookup(List<DocumentKey> list) {
        BatchGetDocumentsRequest.Builder newBuilder = BatchGetDocumentsRequest.newBuilder();
        newBuilder.setDatabase(this.serializer.databaseName());
        for (DocumentKey encodeKey : list) {
            newBuilder.addDocuments(this.serializer.encodeKey(encodeKey));
        }
        return this.channel.runStreamingResponseRpc(FirestoreGrpc.getBatchGetDocumentsMethod(), (BatchGetDocumentsRequest) newBuilder.build()).continueWith(this.workerQueue.getExecutor(), Datastore$$Lambda$2.lambdaFactory$(this, list));
    }

    static /* synthetic */ List lambda$lookup$1(Datastore datastore, List list, Task task) throws Exception {
        if (!task.isSuccessful() && (task.getException() instanceof FirebaseFirestoreException) && ((FirebaseFirestoreException) task.getException()).getCode() == Code.UNAUTHENTICATED) {
            datastore.channel.invalidateToken();
        }
        Map hashMap = new HashMap();
        for (BatchGetDocumentsResponse decodeMaybeDocument : (List) task.getResult()) {
            MaybeDocument decodeMaybeDocument2 = datastore.serializer.decodeMaybeDocument(decodeMaybeDocument);
            hashMap.put(decodeMaybeDocument2.getKey(), decodeMaybeDocument2);
        }
        List arrayList = new ArrayList();
        for (DocumentKey documentKey : list) {
            arrayList.add((MaybeDocument) hashMap.get(documentKey));
        }
        return arrayList;
    }

    public static boolean isPermanentError(Status status) {
        switch (status.getCode()) {
            case OK:
                throw new IllegalArgumentException("Treated status OK as error");
            case CANCELLED:
            case UNKNOWN:
            case DEADLINE_EXCEEDED:
            case RESOURCE_EXHAUSTED:
            case INTERNAL:
            case UNAVAILABLE:
            case UNAUTHENTICATED:
                return false;
            case INVALID_ARGUMENT:
            case NOT_FOUND:
            case ALREADY_EXISTS:
            case PERMISSION_DENIED:
            case FAILED_PRECONDITION:
            case ABORTED:
            case OUT_OF_RANGE:
            case UNIMPLEMENTED:
            case DATA_LOSS:
                return true;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown gRPC status code: ");
                stringBuilder.append(status.getCode());
                throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static boolean isPermanentWriteError(Status status) {
        return isPermanentError(status) && !status.getCode().equals(Status.Code.ABORTED);
    }
}
