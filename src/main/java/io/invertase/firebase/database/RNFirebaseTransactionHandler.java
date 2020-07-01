package io.invertase.firebase.database;

import com.brentvatne.react.ReactVideoView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import io.invertase.firebase.Utils;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;

public class RNFirebaseTransactionHandler {
    boolean abort = false;
    private String appName;
    private final Condition condition;
    private Map<String, Object> data;
    private String dbURL;
    boolean interrupted;
    private final ReentrantLock lock;
    private boolean signalled;
    boolean timeout = false;
    private int transactionId;
    public Object value;

    RNFirebaseTransactionHandler(int i, String str, String str2) {
        this.appName = str;
        this.dbURL = str2;
        this.transactionId = i;
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
    }

    void signalUpdateReceived(ReadableMap readableMap) {
        Map recursivelyDeconstructReadableMap = Utils.recursivelyDeconstructReadableMap(readableMap);
        this.lock.lock();
        this.value = recursivelyDeconstructReadableMap.get("value");
        this.abort = ((Boolean) recursivelyDeconstructReadableMap.get("abort")).booleanValue();
        try {
            if (this.signalled) {
                throw new IllegalStateException("This transactionUpdateHandler has already been signalled.");
            }
            this.signalled = true;
            this.data = recursivelyDeconstructReadableMap;
            this.condition.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    void await() throws InterruptedException {
        this.lock.lock();
        this.signalled = false;
        long currentTimeMillis = System.currentTimeMillis() + 5000;
        while (!this.timeout && !this.condition.await(250, TimeUnit.MILLISECONDS) && !this.signalled) {
            try {
                if (!this.signalled && System.currentTimeMillis() > currentTimeMillis) {
                    this.timeout = true;
                }
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }
        this.lock.unlock();
    }

    Map<String, Object> getUpdates() {
        return this.data;
    }

    WritableMap createUpdateMap(MutableData mutableData) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", this.transactionId);
        createMap.putString("type", "update");
        createMap.putString("appName", this.appName);
        createMap.putString("dbURL", this.dbURL);
        String str = "value";
        if (mutableData.hasChildren()) {
            Object castValue = RNFirebaseDatabaseUtils.castValue(mutableData);
            if (castValue instanceof WritableNativeArray) {
                createMap.putArray(str, (WritableArray) castValue);
            } else {
                createMap.putMap(str, (WritableMap) castValue);
            }
        } else {
            Utils.mapPutValue(str, mutableData.getValue(), createMap);
        }
        return createMap;
    }

    WritableMap createResultMap(@Nullable DatabaseError databaseError, boolean z, DataSnapshot dataSnapshot) {
        WritableMap createMap = Arguments.createMap();
        createMap.putInt("id", this.transactionId);
        createMap.putString("appName", this.appName);
        createMap.putString("dbURL", this.dbURL);
        createMap.putBoolean("timeout", this.timeout);
        createMap.putBoolean("committed", z);
        createMap.putBoolean("interrupted", this.interrupted);
        String str = "type";
        if (databaseError != null || this.timeout || this.interrupted) {
            String str2 = ReactVideoView.EVENT_PROP_ERROR;
            createMap.putString(str, str2);
            if (databaseError != null) {
                createMap.putMap(str2, RNFirebaseDatabase.getJSError(databaseError));
            }
            if (databaseError == null && this.timeout) {
                WritableMap createMap2 = Arguments.createMap();
                createMap2.putString("code", "DATABASE/INTERNAL-TIMEOUT");
                createMap2.putString("message", "A timeout occurred whilst waiting for RN JS thread to send transaction updates.");
                createMap.putMap(str2, createMap2);
            }
        } else {
            createMap.putString(str, "complete");
            createMap.putMap("snapshot", RNFirebaseDatabaseUtils.snapshotToMap(dataSnapshot));
        }
        return createMap;
    }
}
