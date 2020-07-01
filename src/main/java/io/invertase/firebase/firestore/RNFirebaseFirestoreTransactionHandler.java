package io.invertase.firebase.firestore;

import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;

class RNFirebaseFirestoreTransactionHandler {
    boolean aborted = false;
    private String appName;
    private ReadableArray commandBuffer;
    private final Condition condition;
    private Transaction firestoreTransaction;
    private final ReentrantLock lock;
    boolean timeout = false;
    private long timeoutAt;
    private int transactionId;

    RNFirebaseFirestoreTransactionHandler(String str, int i) {
        this.appName = str;
        this.transactionId = i;
        updateInternalTimeout();
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
    }

    void abort() {
        this.aborted = true;
        safeUnlock();
    }

    void resetState(Transaction transaction) {
        this.commandBuffer = null;
        this.firestoreTransaction = transaction;
    }

    void signalBufferReceived(ReadableArray readableArray) {
        this.lock.lock();
        try {
            this.commandBuffer = readableArray;
            this.condition.signalAll();
        } finally {
            safeUnlock();
        }
    }

    void await() {
        this.lock.lock();
        updateInternalTimeout();
        while (!this.aborted && !this.timeout && !this.condition.await(10, TimeUnit.MILLISECONDS)) {
            try {
                if (System.currentTimeMillis() > this.timeoutAt) {
                    this.timeout = true;
                }
            } catch (InterruptedException unused) {
                safeUnlock();
            } catch (Throwable th) {
                safeUnlock();
            }
        }
        safeUnlock();
    }

    ReadableArray getCommandBuffer() {
        return this.commandBuffer;
    }

    void getDocument(DocumentReference documentReference, Promise promise) {
        updateInternalTimeout();
        try {
            promise.resolve(FirestoreSerialize.snapshotToWritableMap(this.firestoreTransaction.get(documentReference)));
        } catch (FirebaseFirestoreException e) {
            WritableMap jSError = RNFirebaseFirestore.getJSError(e);
            promise.reject(jSError.getString("code"), jSError.getString("message"));
        }
    }

    WritableMap createEventMap(@Nullable FirebaseFirestoreException firebaseFirestoreException, String str) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", this.transactionId);
        createMap.putString("appName", this.appName);
        String str2 = "type";
        if (firebaseFirestoreException != null) {
            str = ReactVideoView.EVENT_PROP_ERROR;
            createMap.putString(str2, str);
            createMap.putMap(str, RNFirebaseFirestore.getJSError(firebaseFirestoreException));
        } else {
            createMap.putString(str2, str);
        }
        return createMap;
    }

    private void safeUnlock() {
        if (this.lock.isLocked()) {
            this.lock.unlock();
        }
    }

    private void updateInternalTimeout() {
        this.timeoutAt = System.currentTimeMillis() + 15000;
    }
}
