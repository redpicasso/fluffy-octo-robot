package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.util.Assert;
import com.google.protobuf.ByteString;
import io.grpc.Status;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public abstract class WatchChange {

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum WatchTargetChangeType {
        NoChange,
        Added,
        Removed,
        Current,
        Reset
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class DocumentChange extends WatchChange {
        private final DocumentKey documentKey;
        @Nullable
        private final MaybeDocument newDocument;
        private final List<Integer> removedTargetIds;
        private final List<Integer> updatedTargetIds;

        public DocumentChange(List<Integer> list, List<Integer> list2, DocumentKey documentKey, @Nullable MaybeDocument maybeDocument) {
            super();
            this.updatedTargetIds = list;
            this.removedTargetIds = list2;
            this.documentKey = documentKey;
            this.newDocument = maybeDocument;
        }

        public List<Integer> getUpdatedTargetIds() {
            return this.updatedTargetIds;
        }

        public List<Integer> getRemovedTargetIds() {
            return this.removedTargetIds;
        }

        @Nullable
        public MaybeDocument getNewDocument() {
            return this.newDocument;
        }

        public DocumentKey getDocumentKey() {
            return this.documentKey;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DocumentChange{updatedTargetIds=");
            stringBuilder.append(this.updatedTargetIds);
            stringBuilder.append(", removedTargetIds=");
            stringBuilder.append(this.removedTargetIds);
            stringBuilder.append(", key=");
            stringBuilder.append(this.documentKey);
            stringBuilder.append(", newDocument=");
            stringBuilder.append(this.newDocument);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            DocumentChange documentChange = (DocumentChange) obj;
            if (!this.updatedTargetIds.equals(documentChange.updatedTargetIds) || !this.removedTargetIds.equals(documentChange.removedTargetIds) || !this.documentKey.equals(documentChange.documentKey)) {
                return false;
            }
            MaybeDocument maybeDocument = this.newDocument;
            if (maybeDocument != null) {
                z = maybeDocument.equals(documentChange.newDocument);
            } else if (documentChange.newDocument != null) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            int hashCode = ((((this.updatedTargetIds.hashCode() * 31) + this.removedTargetIds.hashCode()) * 31) + this.documentKey.hashCode()) * 31;
            MaybeDocument maybeDocument = this.newDocument;
            return hashCode + (maybeDocument != null ? maybeDocument.hashCode() : 0);
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class ExistenceFilterWatchChange extends WatchChange {
        private final ExistenceFilter existenceFilter;
        private final int targetId;

        public ExistenceFilterWatchChange(int i, ExistenceFilter existenceFilter) {
            super();
            this.targetId = i;
            this.existenceFilter = existenceFilter;
        }

        public int getTargetId() {
            return this.targetId;
        }

        public ExistenceFilter getExistenceFilter() {
            return this.existenceFilter;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ExistenceFilterWatchChange{targetId=");
            stringBuilder.append(this.targetId);
            stringBuilder.append(", existenceFilter=");
            stringBuilder.append(this.existenceFilter);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class WatchTargetChange extends WatchChange {
        @Nullable
        private final Status cause;
        private final WatchTargetChangeType changeType;
        private final ByteString resumeToken;
        private final List<Integer> targetIds;

        public WatchTargetChange(WatchTargetChangeType watchTargetChangeType, List<Integer> list) {
            this(watchTargetChangeType, list, WatchStream.EMPTY_RESUME_TOKEN, null);
        }

        public WatchTargetChange(WatchTargetChangeType watchTargetChangeType, List<Integer> list, ByteString byteString) {
            this(watchTargetChangeType, list, byteString, null);
        }

        public WatchTargetChange(WatchTargetChangeType watchTargetChangeType, List<Integer> list, ByteString byteString, @Nullable Status status) {
            super();
            boolean z = status == null || watchTargetChangeType == WatchTargetChangeType.Removed;
            Assert.hardAssert(z, "Got cause for a target change that was not a removal", new Object[0]);
            this.changeType = watchTargetChangeType;
            this.targetIds = list;
            this.resumeToken = byteString;
            if (status == null || status.isOk()) {
                this.cause = null;
            } else {
                this.cause = status;
            }
        }

        public WatchTargetChangeType getChangeType() {
            return this.changeType;
        }

        public List<Integer> getTargetIds() {
            return this.targetIds;
        }

        public ByteString getResumeToken() {
            return this.resumeToken;
        }

        @Nullable
        public Status getCause() {
            return this.cause;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("WatchTargetChange{changeType=");
            stringBuilder.append(this.changeType);
            stringBuilder.append(", targetIds=");
            stringBuilder.append(this.targetIds);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            WatchTargetChange watchTargetChange = (WatchTargetChange) obj;
            if (this.changeType != watchTargetChange.changeType || !this.targetIds.equals(watchTargetChange.targetIds) || !this.resumeToken.equals(watchTargetChange.resumeToken)) {
                return false;
            }
            Status status = this.cause;
            if (status != null) {
                if (watchTargetChange.cause == null || !status.getCode().equals(watchTargetChange.cause.getCode())) {
                    z = false;
                }
                return z;
            }
            if (watchTargetChange.cause != null) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            int hashCode = ((((this.changeType.hashCode() * 31) + this.targetIds.hashCode()) * 31) + this.resumeToken.hashCode()) * 31;
            Status status = this.cause;
            return hashCode + (status != null ? status.getCode().hashCode() : 0);
        }
    }

    private WatchChange() {
    }
}
