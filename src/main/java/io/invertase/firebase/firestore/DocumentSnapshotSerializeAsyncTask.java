package io.invertase.firebase.firestore;

import android.os.AsyncTask;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.google.firebase.firestore.DocumentSnapshot;
import java.lang.ref.WeakReference;

class DocumentSnapshotSerializeAsyncTask extends AsyncTask<Object, Void, WritableMap> {
    private WeakReference<ReactContext> reactContextWeakReference;
    private WeakReference<RNFirebaseFirestoreDocumentReference> referenceWeakReference;

    protected void onPostExecute(WritableMap writableMap) {
    }

    DocumentSnapshotSerializeAsyncTask(ReactContext reactContext, RNFirebaseFirestoreDocumentReference rNFirebaseFirestoreDocumentReference) {
        this.referenceWeakReference = new WeakReference(rNFirebaseFirestoreDocumentReference);
        this.reactContextWeakReference = new WeakReference(reactContext);
    }

    protected final WritableMap doInBackground(Object... objArr) {
        try {
            return FirestoreSerialize.snapshotToWritableMap((DocumentSnapshot) objArr[0]);
        } catch (Exception e) {
            if (isAvailable().booleanValue()) {
                ((ReactContext) this.reactContextWeakReference.get()).handleException(e);
                return null;
            }
            throw e;
        }
    }

    private Boolean isAvailable() {
        boolean z = (this.reactContextWeakReference.get() == null || this.referenceWeakReference.get() == null) ? false : true;
        return Boolean.valueOf(z);
    }
}
