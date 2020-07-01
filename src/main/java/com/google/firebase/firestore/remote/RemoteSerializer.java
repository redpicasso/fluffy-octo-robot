package com.google.firebase.firestore.remote;

import androidx.annotation.Nullable;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.core.Bound;
import com.google.firebase.firestore.core.NaNFilter;
import com.google.firebase.firestore.core.NullFilter;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.RelationFilter;
import com.google.firebase.firestore.local.QueryData;
import com.google.firebase.firestore.local.QueryPurpose;
import com.google.firebase.firestore.model.BasePath;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.Document.DocumentState;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation.Remove;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation.Union;
import com.google.firebase.firestore.model.mutation.DeleteMutation;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.FieldTransform;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationResult;
import com.google.firebase.firestore.model.mutation.NumericIncrementTransformOperation;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.model.mutation.ServerTimestampOperation;
import com.google.firebase.firestore.model.mutation.SetMutation;
import com.google.firebase.firestore.model.mutation.TransformMutation;
import com.google.firebase.firestore.model.mutation.TransformOperation;
import com.google.firebase.firestore.model.value.ArrayValue;
import com.google.firebase.firestore.model.value.BlobValue;
import com.google.firebase.firestore.model.value.BooleanValue;
import com.google.firebase.firestore.model.value.DoubleValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.GeoPointValue;
import com.google.firebase.firestore.model.value.IntegerValue;
import com.google.firebase.firestore.model.value.NullValue;
import com.google.firebase.firestore.model.value.NumberValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.model.value.ReferenceValue;
import com.google.firebase.firestore.model.value.StringValue;
import com.google.firebase.firestore.model.value.TimestampValue;
import com.google.firebase.firestore.remote.WatchChange.ExistenceFilterWatchChange;
import com.google.firebase.firestore.remote.WatchChange.WatchTargetChange;
import com.google.firebase.firestore.remote.WatchChange.WatchTargetChangeType;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.v1.BatchGetDocumentsResponse;
import com.google.firestore.v1.BatchGetDocumentsResponse.ResultCase;
import com.google.firestore.v1.Cursor;
import com.google.firestore.v1.Document;
import com.google.firestore.v1.DocumentChange;
import com.google.firestore.v1.DocumentDelete;
import com.google.firestore.v1.DocumentMask;
import com.google.firestore.v1.DocumentRemove;
import com.google.firestore.v1.DocumentTransform;
import com.google.firestore.v1.DocumentTransform.FieldTransform.ServerValue;
import com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase;
import com.google.firestore.v1.ExistenceFilter;
import com.google.firestore.v1.ListenResponse;
import com.google.firestore.v1.ListenResponse.ResponseTypeCase;
import com.google.firestore.v1.MapValue;
import com.google.firestore.v1.Precondition.ConditionTypeCase;
import com.google.firestore.v1.StructuredQuery;
import com.google.firestore.v1.StructuredQuery.CollectionSelector;
import com.google.firestore.v1.StructuredQuery.CompositeFilter;
import com.google.firestore.v1.StructuredQuery.Direction;
import com.google.firestore.v1.StructuredQuery.FieldFilter;
import com.google.firestore.v1.StructuredQuery.FieldReference;
import com.google.firestore.v1.StructuredQuery.Filter;
import com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase;
import com.google.firestore.v1.StructuredQuery.Order;
import com.google.firestore.v1.StructuredQuery.UnaryFilter;
import com.google.firestore.v1.StructuredQuery.UnaryFilter.Operator;
import com.google.firestore.v1.Target;
import com.google.firestore.v1.Target.DocumentsTarget;
import com.google.firestore.v1.Target.QueryTarget;
import com.google.firestore.v1.TargetChange;
import com.google.firestore.v1.TargetChange.TargetChangeType;
import com.google.firestore.v1.Value;
import com.google.firestore.v1.Write;
import com.google.firestore.v1.Write.OperationCase;
import com.google.firestore.v1.WriteResult;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import com.google.protobuf.Timestamp.Builder;
import com.google.type.LatLng;
import io.grpc.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class RemoteSerializer {
    private final DatabaseId databaseId;
    private final String databaseName;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.remote.RemoteSerializer$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$local$QueryPurpose = new int[QueryPurpose.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase = new int[TransformTypeCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase = new int[ResponseTypeCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase = new int[ConditionTypeCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction = new int[Direction.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase = new int[FilterTypeCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator = new int[Operator.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType = new int[TargetChangeType.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Write$OperationCase = new int[OperationCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:30:?, code:
            $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[com.google.firestore.v1.TargetChange.TargetChangeType.UNRECOGNIZED.ordinal()] = 6;
     */
        /* JADX WARNING: Missing block: B:35:?, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction[com.google.firestore.v1.StructuredQuery.Direction.DESCENDING.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:49:0x00f2, code:
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = new int[com.google.firebase.firestore.core.Filter.Operator.values().length];
     */
        /* JADX WARNING: Missing block: B:51:?, code:
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[com.google.firebase.firestore.core.Filter.Operator.LESS_THAN.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:53:?, code:
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[com.google.firebase.firestore.core.Filter.Operator.LESS_THAN_OR_EQUAL.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:55:?, code:
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[com.google.firebase.firestore.core.Filter.Operator.EQUAL.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:57:?, code:
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN.ordinal()] = 4;
     */
        /* JADX WARNING: Missing block: B:59:?, code:
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN_OR_EQUAL.ordinal()] = 5;
     */
        /* JADX WARNING: Missing block: B:61:?, code:
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[com.google.firebase.firestore.core.Filter.Operator.ARRAY_CONTAINS.ordinal()] = 6;
     */
        /* JADX WARNING: Missing block: B:62:0x0137, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator = new int[com.google.firestore.v1.StructuredQuery.UnaryFilter.Operator.values().length];
     */
        /* JADX WARNING: Missing block: B:64:?, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator[com.google.firestore.v1.StructuredQuery.UnaryFilter.Operator.IS_NAN.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:66:?, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator[com.google.firestore.v1.StructuredQuery.UnaryFilter.Operator.IS_NULL.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:67:0x0154, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase = new int[com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.values().length];
     */
        /* JADX WARNING: Missing block: B:69:?, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.COMPOSITE_FILTER.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:71:?, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.FIELD_FILTER.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:73:?, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.UNARY_FILTER.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:74:0x017b, code:
            $SwitchMap$com$google$firebase$firestore$local$QueryPurpose = new int[com.google.firebase.firestore.local.QueryPurpose.values().length];
     */
        /* JADX WARNING: Missing block: B:76:?, code:
            $SwitchMap$com$google$firebase$firestore$local$QueryPurpose[com.google.firebase.firestore.local.QueryPurpose.LISTEN.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:78:?, code:
            $SwitchMap$com$google$firebase$firestore$local$QueryPurpose[com.google.firebase.firestore.local.QueryPurpose.EXISTENCE_FILTER_MISMATCH.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:80:?, code:
            $SwitchMap$com$google$firebase$firestore$local$QueryPurpose[com.google.firebase.firestore.local.QueryPurpose.LIMBO_RESOLUTION.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:81:0x01a2, code:
            $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase = new int[com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.values().length];
     */
        /* JADX WARNING: Missing block: B:83:?, code:
            $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.SET_TO_SERVER_VALUE.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:85:?, code:
            $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.APPEND_MISSING_ELEMENTS.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:87:?, code:
            $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.REMOVE_ALL_FROM_ARRAY.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:89:?, code:
            $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.INCREMENT.ordinal()] = 4;
     */
        /* JADX WARNING: Missing block: B:90:0x01d3, code:
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase = new int[com.google.firestore.v1.Precondition.ConditionTypeCase.values().length];
     */
        /* JADX WARNING: Missing block: B:92:?, code:
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[com.google.firestore.v1.Precondition.ConditionTypeCase.UPDATE_TIME.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:94:?, code:
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[com.google.firestore.v1.Precondition.ConditionTypeCase.EXISTS.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:96:?, code:
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[com.google.firestore.v1.Precondition.ConditionTypeCase.CONDITIONTYPE_NOT_SET.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:97:0x01fa, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase = new int[com.google.firestore.v1.Write.OperationCase.values().length];
     */
        /* JADX WARNING: Missing block: B:99:?, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase[com.google.firestore.v1.Write.OperationCase.UPDATE.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:101:?, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase[com.google.firestore.v1.Write.OperationCase.DELETE.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:103:?, code:
            $SwitchMap$com$google$firestore$v1$Write$OperationCase[com.google.firestore.v1.Write.OperationCase.TRANSFORM.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:104:0x0221, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase = new int[com.google.firestore.v1.Value.ValueTypeCase.values().length];
     */
        /* JADX WARNING: Missing block: B:106:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.NULL_VALUE.ordinal()] = 1;
     */
        /* JADX WARNING: Missing block: B:108:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.BOOLEAN_VALUE.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:110:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.INTEGER_VALUE.ordinal()] = 3;
     */
        /* JADX WARNING: Missing block: B:112:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.DOUBLE_VALUE.ordinal()] = 4;
     */
        /* JADX WARNING: Missing block: B:114:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.TIMESTAMP_VALUE.ordinal()] = 5;
     */
        /* JADX WARNING: Missing block: B:116:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.GEO_POINT_VALUE.ordinal()] = 6;
     */
        /* JADX WARNING: Missing block: B:118:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.BYTES_VALUE.ordinal()] = 7;
     */
        /* JADX WARNING: Missing block: B:120:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.REFERENCE_VALUE.ordinal()] = 8;
     */
        /* JADX WARNING: Missing block: B:122:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.STRING_VALUE.ordinal()] = 9;
     */
        /* JADX WARNING: Missing block: B:124:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.ARRAY_VALUE.ordinal()] = 10;
     */
        /* JADX WARNING: Missing block: B:126:?, code:
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[com.google.firestore.v1.Value.ValueTypeCase.MAP_VALUE.ordinal()] = 11;
     */
        /* JADX WARNING: Missing block: B:127:0x02a1, code:
            return;
     */
        static {
            /*
            r0 = com.google.firestore.v1.ListenResponse.ResponseTypeCase.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase = r0;
            r0 = 1;
            r1 = $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = com.google.firestore.v1.ListenResponse.ResponseTypeCase.TARGET_CHANGE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = com.google.firestore.v1.ListenResponse.ResponseTypeCase.DOCUMENT_CHANGE;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = 3;
            r3 = $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = com.google.firestore.v1.ListenResponse.ResponseTypeCase.DOCUMENT_DELETE;	 Catch:{ NoSuchFieldError -> 0x002a }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r3[r4] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r3 = 4;
            r4 = $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = com.google.firestore.v1.ListenResponse.ResponseTypeCase.DOCUMENT_REMOVE;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r4[r5] = r3;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            r4 = 5;
            r5 = $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r6 = com.google.firestore.v1.ListenResponse.ResponseTypeCase.FILTER;	 Catch:{ NoSuchFieldError -> 0x0040 }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0040 }
            r5[r6] = r4;	 Catch:{ NoSuchFieldError -> 0x0040 }
        L_0x0040:
            r5 = 6;
            r6 = $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase;	 Catch:{ NoSuchFieldError -> 0x004b }
            r7 = com.google.firestore.v1.ListenResponse.ResponseTypeCase.RESPONSETYPE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x004b }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x004b }
            r6[r7] = r5;	 Catch:{ NoSuchFieldError -> 0x004b }
        L_0x004b:
            r6 = com.google.firestore.v1.TargetChange.TargetChangeType.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType = r6;
            r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType;	 Catch:{ NoSuchFieldError -> 0x005e }
            r7 = com.google.firestore.v1.TargetChange.TargetChangeType.NO_CHANGE;	 Catch:{ NoSuchFieldError -> 0x005e }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x005e }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x005e }
        L_0x005e:
            r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType;	 Catch:{ NoSuchFieldError -> 0x0068 }
            r7 = com.google.firestore.v1.TargetChange.TargetChangeType.ADD;	 Catch:{ NoSuchFieldError -> 0x0068 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0068 }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x0068 }
        L_0x0068:
            r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType;	 Catch:{ NoSuchFieldError -> 0x0072 }
            r7 = com.google.firestore.v1.TargetChange.TargetChangeType.REMOVE;	 Catch:{ NoSuchFieldError -> 0x0072 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0072 }
            r6[r7] = r2;	 Catch:{ NoSuchFieldError -> 0x0072 }
        L_0x0072:
            r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType;	 Catch:{ NoSuchFieldError -> 0x007c }
            r7 = com.google.firestore.v1.TargetChange.TargetChangeType.CURRENT;	 Catch:{ NoSuchFieldError -> 0x007c }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x007c }
            r6[r7] = r3;	 Catch:{ NoSuchFieldError -> 0x007c }
        L_0x007c:
            r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType;	 Catch:{ NoSuchFieldError -> 0x0086 }
            r7 = com.google.firestore.v1.TargetChange.TargetChangeType.RESET;	 Catch:{ NoSuchFieldError -> 0x0086 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0086 }
            r6[r7] = r4;	 Catch:{ NoSuchFieldError -> 0x0086 }
        L_0x0086:
            r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType;	 Catch:{ NoSuchFieldError -> 0x0090 }
            r7 = com.google.firestore.v1.TargetChange.TargetChangeType.UNRECOGNIZED;	 Catch:{ NoSuchFieldError -> 0x0090 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0090 }
            r6[r7] = r5;	 Catch:{ NoSuchFieldError -> 0x0090 }
        L_0x0090:
            r6 = com.google.firestore.v1.StructuredQuery.Direction.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction = r6;
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction;	 Catch:{ NoSuchFieldError -> 0x00a3 }
            r7 = com.google.firestore.v1.StructuredQuery.Direction.ASCENDING;	 Catch:{ NoSuchFieldError -> 0x00a3 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00a3 }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x00a3 }
        L_0x00a3:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction;	 Catch:{ NoSuchFieldError -> 0x00ad }
            r7 = com.google.firestore.v1.StructuredQuery.Direction.DESCENDING;	 Catch:{ NoSuchFieldError -> 0x00ad }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00ad }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x00ad }
        L_0x00ad:
            r6 = com.google.firestore.v1.StructuredQuery.FieldFilter.Operator.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator = r6;
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator;	 Catch:{ NoSuchFieldError -> 0x00c0 }
            r7 = com.google.firestore.v1.StructuredQuery.FieldFilter.Operator.LESS_THAN;	 Catch:{ NoSuchFieldError -> 0x00c0 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00c0 }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x00c0 }
        L_0x00c0:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator;	 Catch:{ NoSuchFieldError -> 0x00ca }
            r7 = com.google.firestore.v1.StructuredQuery.FieldFilter.Operator.LESS_THAN_OR_EQUAL;	 Catch:{ NoSuchFieldError -> 0x00ca }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00ca }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x00ca }
        L_0x00ca:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator;	 Catch:{ NoSuchFieldError -> 0x00d4 }
            r7 = com.google.firestore.v1.StructuredQuery.FieldFilter.Operator.EQUAL;	 Catch:{ NoSuchFieldError -> 0x00d4 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00d4 }
            r6[r7] = r2;	 Catch:{ NoSuchFieldError -> 0x00d4 }
        L_0x00d4:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator;	 Catch:{ NoSuchFieldError -> 0x00de }
            r7 = com.google.firestore.v1.StructuredQuery.FieldFilter.Operator.GREATER_THAN_OR_EQUAL;	 Catch:{ NoSuchFieldError -> 0x00de }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00de }
            r6[r7] = r3;	 Catch:{ NoSuchFieldError -> 0x00de }
        L_0x00de:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator;	 Catch:{ NoSuchFieldError -> 0x00e8 }
            r7 = com.google.firestore.v1.StructuredQuery.FieldFilter.Operator.GREATER_THAN;	 Catch:{ NoSuchFieldError -> 0x00e8 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00e8 }
            r6[r7] = r4;	 Catch:{ NoSuchFieldError -> 0x00e8 }
        L_0x00e8:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator;	 Catch:{ NoSuchFieldError -> 0x00f2 }
            r7 = com.google.firestore.v1.StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS;	 Catch:{ NoSuchFieldError -> 0x00f2 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x00f2 }
            r6[r7] = r5;	 Catch:{ NoSuchFieldError -> 0x00f2 }
        L_0x00f2:
            r6 = com.google.firebase.firestore.core.Filter.Operator.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = r6;
            r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;	 Catch:{ NoSuchFieldError -> 0x0105 }
            r7 = com.google.firebase.firestore.core.Filter.Operator.LESS_THAN;	 Catch:{ NoSuchFieldError -> 0x0105 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0105 }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x0105 }
        L_0x0105:
            r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;	 Catch:{ NoSuchFieldError -> 0x010f }
            r7 = com.google.firebase.firestore.core.Filter.Operator.LESS_THAN_OR_EQUAL;	 Catch:{ NoSuchFieldError -> 0x010f }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x010f }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x010f }
        L_0x010f:
            r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;	 Catch:{ NoSuchFieldError -> 0x0119 }
            r7 = com.google.firebase.firestore.core.Filter.Operator.EQUAL;	 Catch:{ NoSuchFieldError -> 0x0119 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0119 }
            r6[r7] = r2;	 Catch:{ NoSuchFieldError -> 0x0119 }
        L_0x0119:
            r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;	 Catch:{ NoSuchFieldError -> 0x0123 }
            r7 = com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN;	 Catch:{ NoSuchFieldError -> 0x0123 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0123 }
            r6[r7] = r3;	 Catch:{ NoSuchFieldError -> 0x0123 }
        L_0x0123:
            r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;	 Catch:{ NoSuchFieldError -> 0x012d }
            r7 = com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN_OR_EQUAL;	 Catch:{ NoSuchFieldError -> 0x012d }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x012d }
            r6[r7] = r4;	 Catch:{ NoSuchFieldError -> 0x012d }
        L_0x012d:
            r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;	 Catch:{ NoSuchFieldError -> 0x0137 }
            r7 = com.google.firebase.firestore.core.Filter.Operator.ARRAY_CONTAINS;	 Catch:{ NoSuchFieldError -> 0x0137 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0137 }
            r6[r7] = r5;	 Catch:{ NoSuchFieldError -> 0x0137 }
        L_0x0137:
            r6 = com.google.firestore.v1.StructuredQuery.UnaryFilter.Operator.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator = r6;
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator;	 Catch:{ NoSuchFieldError -> 0x014a }
            r7 = com.google.firestore.v1.StructuredQuery.UnaryFilter.Operator.IS_NAN;	 Catch:{ NoSuchFieldError -> 0x014a }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x014a }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x014a }
        L_0x014a:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator;	 Catch:{ NoSuchFieldError -> 0x0154 }
            r7 = com.google.firestore.v1.StructuredQuery.UnaryFilter.Operator.IS_NULL;	 Catch:{ NoSuchFieldError -> 0x0154 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0154 }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x0154 }
        L_0x0154:
            r6 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase = r6;
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase;	 Catch:{ NoSuchFieldError -> 0x0167 }
            r7 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.COMPOSITE_FILTER;	 Catch:{ NoSuchFieldError -> 0x0167 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0167 }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x0167 }
        L_0x0167:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase;	 Catch:{ NoSuchFieldError -> 0x0171 }
            r7 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.FIELD_FILTER;	 Catch:{ NoSuchFieldError -> 0x0171 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0171 }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x0171 }
        L_0x0171:
            r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase;	 Catch:{ NoSuchFieldError -> 0x017b }
            r7 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.UNARY_FILTER;	 Catch:{ NoSuchFieldError -> 0x017b }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x017b }
            r6[r7] = r2;	 Catch:{ NoSuchFieldError -> 0x017b }
        L_0x017b:
            r6 = com.google.firebase.firestore.local.QueryPurpose.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firebase$firestore$local$QueryPurpose = r6;
            r6 = $SwitchMap$com$google$firebase$firestore$local$QueryPurpose;	 Catch:{ NoSuchFieldError -> 0x018e }
            r7 = com.google.firebase.firestore.local.QueryPurpose.LISTEN;	 Catch:{ NoSuchFieldError -> 0x018e }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x018e }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x018e }
        L_0x018e:
            r6 = $SwitchMap$com$google$firebase$firestore$local$QueryPurpose;	 Catch:{ NoSuchFieldError -> 0x0198 }
            r7 = com.google.firebase.firestore.local.QueryPurpose.EXISTENCE_FILTER_MISMATCH;	 Catch:{ NoSuchFieldError -> 0x0198 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0198 }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x0198 }
        L_0x0198:
            r6 = $SwitchMap$com$google$firebase$firestore$local$QueryPurpose;	 Catch:{ NoSuchFieldError -> 0x01a2 }
            r7 = com.google.firebase.firestore.local.QueryPurpose.LIMBO_RESOLUTION;	 Catch:{ NoSuchFieldError -> 0x01a2 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x01a2 }
            r6[r7] = r2;	 Catch:{ NoSuchFieldError -> 0x01a2 }
        L_0x01a2:
            r6 = com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase = r6;
            r6 = $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase;	 Catch:{ NoSuchFieldError -> 0x01b5 }
            r7 = com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.SET_TO_SERVER_VALUE;	 Catch:{ NoSuchFieldError -> 0x01b5 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x01b5 }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x01b5 }
        L_0x01b5:
            r6 = $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase;	 Catch:{ NoSuchFieldError -> 0x01bf }
            r7 = com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.APPEND_MISSING_ELEMENTS;	 Catch:{ NoSuchFieldError -> 0x01bf }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x01bf }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x01bf }
        L_0x01bf:
            r6 = $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase;	 Catch:{ NoSuchFieldError -> 0x01c9 }
            r7 = com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.REMOVE_ALL_FROM_ARRAY;	 Catch:{ NoSuchFieldError -> 0x01c9 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x01c9 }
            r6[r7] = r2;	 Catch:{ NoSuchFieldError -> 0x01c9 }
        L_0x01c9:
            r6 = $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase;	 Catch:{ NoSuchFieldError -> 0x01d3 }
            r7 = com.google.firestore.v1.DocumentTransform.FieldTransform.TransformTypeCase.INCREMENT;	 Catch:{ NoSuchFieldError -> 0x01d3 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x01d3 }
            r6[r7] = r3;	 Catch:{ NoSuchFieldError -> 0x01d3 }
        L_0x01d3:
            r6 = com.google.firestore.v1.Precondition.ConditionTypeCase.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase = r6;
            r6 = $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase;	 Catch:{ NoSuchFieldError -> 0x01e6 }
            r7 = com.google.firestore.v1.Precondition.ConditionTypeCase.UPDATE_TIME;	 Catch:{ NoSuchFieldError -> 0x01e6 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x01e6 }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x01e6 }
        L_0x01e6:
            r6 = $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase;	 Catch:{ NoSuchFieldError -> 0x01f0 }
            r7 = com.google.firestore.v1.Precondition.ConditionTypeCase.EXISTS;	 Catch:{ NoSuchFieldError -> 0x01f0 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x01f0 }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x01f0 }
        L_0x01f0:
            r6 = $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase;	 Catch:{ NoSuchFieldError -> 0x01fa }
            r7 = com.google.firestore.v1.Precondition.ConditionTypeCase.CONDITIONTYPE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x01fa }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x01fa }
            r6[r7] = r2;	 Catch:{ NoSuchFieldError -> 0x01fa }
        L_0x01fa:
            r6 = com.google.firestore.v1.Write.OperationCase.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$Write$OperationCase = r6;
            r6 = $SwitchMap$com$google$firestore$v1$Write$OperationCase;	 Catch:{ NoSuchFieldError -> 0x020d }
            r7 = com.google.firestore.v1.Write.OperationCase.UPDATE;	 Catch:{ NoSuchFieldError -> 0x020d }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x020d }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x020d }
        L_0x020d:
            r6 = $SwitchMap$com$google$firestore$v1$Write$OperationCase;	 Catch:{ NoSuchFieldError -> 0x0217 }
            r7 = com.google.firestore.v1.Write.OperationCase.DELETE;	 Catch:{ NoSuchFieldError -> 0x0217 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0217 }
            r6[r7] = r1;	 Catch:{ NoSuchFieldError -> 0x0217 }
        L_0x0217:
            r6 = $SwitchMap$com$google$firestore$v1$Write$OperationCase;	 Catch:{ NoSuchFieldError -> 0x0221 }
            r7 = com.google.firestore.v1.Write.OperationCase.TRANSFORM;	 Catch:{ NoSuchFieldError -> 0x0221 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0221 }
            r6[r7] = r2;	 Catch:{ NoSuchFieldError -> 0x0221 }
        L_0x0221:
            r6 = com.google.firestore.v1.Value.ValueTypeCase.values();
            r6 = r6.length;
            r6 = new int[r6];
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase = r6;
            r6 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x0234 }
            r7 = com.google.firestore.v1.Value.ValueTypeCase.NULL_VALUE;	 Catch:{ NoSuchFieldError -> 0x0234 }
            r7 = r7.ordinal();	 Catch:{ NoSuchFieldError -> 0x0234 }
            r6[r7] = r0;	 Catch:{ NoSuchFieldError -> 0x0234 }
        L_0x0234:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x023e }
            r6 = com.google.firestore.v1.Value.ValueTypeCase.BOOLEAN_VALUE;	 Catch:{ NoSuchFieldError -> 0x023e }
            r6 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x023e }
            r0[r6] = r1;	 Catch:{ NoSuchFieldError -> 0x023e }
        L_0x023e:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x0248 }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.INTEGER_VALUE;	 Catch:{ NoSuchFieldError -> 0x0248 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0248 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0248 }
        L_0x0248:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x0252 }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.DOUBLE_VALUE;	 Catch:{ NoSuchFieldError -> 0x0252 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0252 }
            r0[r1] = r3;	 Catch:{ NoSuchFieldError -> 0x0252 }
        L_0x0252:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x025c }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.TIMESTAMP_VALUE;	 Catch:{ NoSuchFieldError -> 0x025c }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x025c }
            r0[r1] = r4;	 Catch:{ NoSuchFieldError -> 0x025c }
        L_0x025c:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x0266 }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.GEO_POINT_VALUE;	 Catch:{ NoSuchFieldError -> 0x0266 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0266 }
            r0[r1] = r5;	 Catch:{ NoSuchFieldError -> 0x0266 }
        L_0x0266:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x0271 }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.BYTES_VALUE;	 Catch:{ NoSuchFieldError -> 0x0271 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0271 }
            r2 = 7;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0271 }
        L_0x0271:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x027d }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.REFERENCE_VALUE;	 Catch:{ NoSuchFieldError -> 0x027d }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x027d }
            r2 = 8;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x027d }
        L_0x027d:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x0289 }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.STRING_VALUE;	 Catch:{ NoSuchFieldError -> 0x0289 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0289 }
            r2 = 9;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0289 }
        L_0x0289:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x0295 }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.ARRAY_VALUE;	 Catch:{ NoSuchFieldError -> 0x0295 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0295 }
            r2 = 10;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0295 }
        L_0x0295:
            r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;	 Catch:{ NoSuchFieldError -> 0x02a1 }
            r1 = com.google.firestore.v1.Value.ValueTypeCase.MAP_VALUE;	 Catch:{ NoSuchFieldError -> 0x02a1 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x02a1 }
            r2 = 11;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x02a1 }
        L_0x02a1:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.RemoteSerializer.1.<clinit>():void");
        }
    }

    public RemoteSerializer(DatabaseId databaseId) {
        this.databaseId = databaseId;
        this.databaseName = encodedDatabaseId(databaseId).canonicalString();
    }

    public Timestamp encodeTimestamp(com.google.firebase.Timestamp timestamp) {
        Builder newBuilder = Timestamp.newBuilder();
        newBuilder.setSeconds(timestamp.getSeconds());
        newBuilder.setNanos(timestamp.getNanoseconds());
        return (Timestamp) newBuilder.build();
    }

    public com.google.firebase.Timestamp decodeTimestamp(Timestamp timestamp) {
        return new com.google.firebase.Timestamp(timestamp.getSeconds(), timestamp.getNanos());
    }

    public Timestamp encodeVersion(SnapshotVersion snapshotVersion) {
        return encodeTimestamp(snapshotVersion.getTimestamp());
    }

    public SnapshotVersion decodeVersion(Timestamp timestamp) {
        if (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0) {
            return SnapshotVersion.NONE;
        }
        return new SnapshotVersion(decodeTimestamp(timestamp));
    }

    private LatLng encodeGeoPoint(GeoPoint geoPoint) {
        return (LatLng) LatLng.newBuilder().setLatitude(geoPoint.getLatitude()).setLongitude(geoPoint.getLongitude()).build();
    }

    private GeoPoint decodeGeoPoint(LatLng latLng) {
        return new GeoPoint(latLng.getLatitude(), latLng.getLongitude());
    }

    public String encodeKey(DocumentKey documentKey) {
        return encodeResourceName(this.databaseId, documentKey.getPath());
    }

    public DocumentKey decodeKey(String str) {
        ResourcePath decodeResourceName = decodeResourceName(str);
        Assert.hardAssert(decodeResourceName.getSegment(1).equals(this.databaseId.getProjectId()), "Tried to deserialize key from different project.", new Object[0]);
        Assert.hardAssert(decodeResourceName.getSegment(3).equals(this.databaseId.getDatabaseId()), "Tried to deserialize key from different database.", new Object[0]);
        return DocumentKey.fromPath(extractLocalPathFromResourceName(decodeResourceName));
    }

    private String encodeQueryPath(ResourcePath resourcePath) {
        return encodeResourceName(this.databaseId, resourcePath);
    }

    private ResourcePath decodeQueryPath(String str) {
        ResourcePath decodeResourceName = decodeResourceName(str);
        if (decodeResourceName.length() == 4) {
            return ResourcePath.EMPTY;
        }
        return extractLocalPathFromResourceName(decodeResourceName);
    }

    private String encodeResourceName(DatabaseId databaseId, ResourcePath resourcePath) {
        return ((ResourcePath) ((ResourcePath) encodedDatabaseId(databaseId).append("documents")).append((BasePath) resourcePath)).canonicalString();
    }

    private ResourcePath decodeResourceName(String str) {
        ResourcePath fromString = ResourcePath.fromString(str);
        Assert.hardAssert(isValidResourceName(fromString), "Tried to deserialize invalid key %s", fromString);
        return fromString;
    }

    private static ResourcePath encodedDatabaseId(DatabaseId databaseId) {
        return ResourcePath.fromSegments(Arrays.asList(new String[]{"projects", databaseId.getProjectId(), "databases", databaseId.getDatabaseId()}));
    }

    private static ResourcePath extractLocalPathFromResourceName(ResourcePath resourcePath) {
        boolean z = resourcePath.length() > 4 && resourcePath.getSegment(4).equals("documents");
        Assert.hardAssert(z, "Tried to deserialize invalid key %s", resourcePath);
        return (ResourcePath) resourcePath.popFirst(5);
    }

    private static boolean isValidResourceName(ResourcePath resourcePath) {
        if (resourcePath.length() >= 4 && resourcePath.getSegment(0).equals("projects") && resourcePath.getSegment(2).equals("databases")) {
            return true;
        }
        return false;
    }

    public String databaseName() {
        return this.databaseName;
    }

    public Value encodeValue(FieldValue fieldValue) {
        Value.Builder newBuilder = Value.newBuilder();
        if (fieldValue instanceof NullValue) {
            newBuilder.setNullValueValue(0);
            return (Value) newBuilder.build();
        }
        Object value = fieldValue.value();
        Assert.hardAssert(value != null, "Encoded field value should not be null.", new Object[0]);
        if (fieldValue instanceof BooleanValue) {
            newBuilder.setBooleanValue(((Boolean) value).booleanValue());
        } else if (fieldValue instanceof IntegerValue) {
            newBuilder.setIntegerValue(((Long) value).longValue());
        } else if (fieldValue instanceof DoubleValue) {
            newBuilder.setDoubleValue(((Double) value).doubleValue());
        } else if (fieldValue instanceof StringValue) {
            newBuilder.setStringValue((String) value);
        } else if (fieldValue instanceof ArrayValue) {
            newBuilder.setArrayValue(encodeArrayValue((ArrayValue) fieldValue));
        } else if (fieldValue instanceof ObjectValue) {
            newBuilder.setMapValue(encodeMapValue((ObjectValue) fieldValue));
        } else if (fieldValue instanceof TimestampValue) {
            newBuilder.setTimestampValue(encodeTimestamp(((TimestampValue) fieldValue).getInternalValue()));
        } else if (fieldValue instanceof GeoPointValue) {
            newBuilder.setGeoPointValue(encodeGeoPoint((GeoPoint) value));
        } else if (fieldValue instanceof BlobValue) {
            newBuilder.setBytesValue(((Blob) value).toByteString());
        } else if (fieldValue instanceof ReferenceValue) {
            newBuilder.setReferenceValue(encodeResourceName(((ReferenceValue) fieldValue).getDatabaseId(), ((DocumentKey) value).getPath()));
        } else {
            throw Assert.fail("Can't serialize %s", fieldValue);
        }
        return (Value) newBuilder.build();
    }

    public FieldValue decodeValue(Value value) {
        switch (value.getValueTypeCase()) {
            case NULL_VALUE:
                return NullValue.nullValue();
            case BOOLEAN_VALUE:
                return BooleanValue.valueOf(Boolean.valueOf(value.getBooleanValue()));
            case INTEGER_VALUE:
                return IntegerValue.valueOf(Long.valueOf(value.getIntegerValue()));
            case DOUBLE_VALUE:
                return DoubleValue.valueOf(Double.valueOf(value.getDoubleValue()));
            case TIMESTAMP_VALUE:
                return TimestampValue.valueOf(decodeTimestamp(value.getTimestampValue()));
            case GEO_POINT_VALUE:
                return GeoPointValue.valueOf(decodeGeoPoint(value.getGeoPointValue()));
            case BYTES_VALUE:
                return BlobValue.valueOf(Blob.fromByteString(value.getBytesValue()));
            case REFERENCE_VALUE:
                ResourcePath decodeResourceName = decodeResourceName(value.getReferenceValue());
                return ReferenceValue.valueOf(DatabaseId.forDatabase(decodeResourceName.getSegment(1), decodeResourceName.getSegment(3)), DocumentKey.fromPath(extractLocalPathFromResourceName(decodeResourceName)));
            case STRING_VALUE:
                return StringValue.valueOf(value.getStringValue());
            case ARRAY_VALUE:
                return decodeArrayValue(value.getArrayValue());
            case MAP_VALUE:
                return decodeMapValue(value.getMapValue());
            default:
                throw Assert.fail("Unknown value %s", value);
        }
    }

    private com.google.firestore.v1.ArrayValue encodeArrayValue(ArrayValue arrayValue) {
        List<FieldValue> internalValue = arrayValue.getInternalValue();
        com.google.firestore.v1.ArrayValue.Builder newBuilder = com.google.firestore.v1.ArrayValue.newBuilder();
        for (FieldValue encodeValue : internalValue) {
            newBuilder.addValues(encodeValue(encodeValue));
        }
        return (com.google.firestore.v1.ArrayValue) newBuilder.build();
    }

    private ArrayValue decodeArrayValue(com.google.firestore.v1.ArrayValue arrayValue) {
        int valuesCount = arrayValue.getValuesCount();
        List arrayList = new ArrayList(valuesCount);
        for (int i = 0; i < valuesCount; i++) {
            arrayList.add(decodeValue(arrayValue.getValues(i)));
        }
        return ArrayValue.fromList(arrayList);
    }

    private MapValue encodeMapValue(ObjectValue objectValue) {
        MapValue.Builder newBuilder = MapValue.newBuilder();
        Iterator it = objectValue.getInternalValue().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            newBuilder.putFields((String) entry.getKey(), encodeValue((FieldValue) entry.getValue()));
        }
        return (MapValue) newBuilder.build();
    }

    private ObjectValue decodeMapValue(MapValue mapValue) {
        return decodeFields(mapValue.getFieldsMap());
    }

    public ObjectValue decodeFields(Map<String, Value> map) {
        ObjectValue emptyObject = ObjectValue.emptyObject();
        for (Entry entry : map.entrySet()) {
            emptyObject = emptyObject.set(FieldPath.fromSingleSegment((String) entry.getKey()), decodeValue((Value) entry.getValue()));
        }
        return emptyObject;
    }

    public Document encodeDocument(DocumentKey documentKey, ObjectValue objectValue) {
        Document.Builder newBuilder = Document.newBuilder();
        newBuilder.setName(encodeKey(documentKey));
        Iterator it = objectValue.getInternalValue().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            newBuilder.putFields((String) entry.getKey(), encodeValue((FieldValue) entry.getValue()));
        }
        return (Document) newBuilder.build();
    }

    public MaybeDocument decodeMaybeDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        if (batchGetDocumentsResponse.getResultCase().equals(ResultCase.FOUND)) {
            return decodeFoundDocument(batchGetDocumentsResponse);
        }
        if (batchGetDocumentsResponse.getResultCase().equals(ResultCase.MISSING)) {
            return decodeMissingDocument(batchGetDocumentsResponse);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown result case: ");
        stringBuilder.append(batchGetDocumentsResponse.getResultCase());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private com.google.firebase.firestore.model.Document decodeFoundDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        Assert.hardAssert(batchGetDocumentsResponse.getResultCase().equals(ResultCase.FOUND), "Tried to deserialize a found document from a missing document.", new Object[0]);
        DocumentKey decodeKey = decodeKey(batchGetDocumentsResponse.getFound().getName());
        ObjectValue decodeFields = decodeFields(batchGetDocumentsResponse.getFound().getFieldsMap());
        SnapshotVersion decodeVersion = decodeVersion(batchGetDocumentsResponse.getFound().getUpdateTime());
        Assert.hardAssert(decodeVersion.equals(SnapshotVersion.NONE) ^ 1, "Got a document response with no snapshot version", new Object[0]);
        return new com.google.firebase.firestore.model.Document(decodeKey, decodeVersion, decodeFields, DocumentState.SYNCED, batchGetDocumentsResponse.getFound());
    }

    private NoDocument decodeMissingDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        Assert.hardAssert(batchGetDocumentsResponse.getResultCase().equals(ResultCase.MISSING), "Tried to deserialize a missing document from a found document.", new Object[0]);
        DocumentKey decodeKey = decodeKey(batchGetDocumentsResponse.getMissing());
        SnapshotVersion decodeVersion = decodeVersion(batchGetDocumentsResponse.getReadTime());
        Assert.hardAssert(decodeVersion.equals(SnapshotVersion.NONE) ^ 1, "Got a no document response with no snapshot version", new Object[0]);
        return new NoDocument(decodeKey, decodeVersion, false);
    }

    public Write encodeMutation(Mutation mutation) {
        Write.Builder newBuilder = Write.newBuilder();
        if (mutation instanceof SetMutation) {
            newBuilder.setUpdate(encodeDocument(mutation.getKey(), ((SetMutation) mutation).getValue()));
        } else if (mutation instanceof PatchMutation) {
            PatchMutation patchMutation = (PatchMutation) mutation;
            newBuilder.setUpdate(encodeDocument(mutation.getKey(), patchMutation.getValue()));
            newBuilder.setUpdateMask(encodeDocumentMask(patchMutation.getMask()));
        } else if (mutation instanceof TransformMutation) {
            TransformMutation transformMutation = (TransformMutation) mutation;
            DocumentTransform.Builder newBuilder2 = DocumentTransform.newBuilder();
            newBuilder2.setDocument(encodeKey(transformMutation.getKey()));
            for (FieldTransform encodeFieldTransform : transformMutation.getFieldTransforms()) {
                newBuilder2.addFieldTransforms(encodeFieldTransform(encodeFieldTransform));
            }
            newBuilder.setTransform(newBuilder2);
        } else if (mutation instanceof DeleteMutation) {
            newBuilder.setDelete(encodeKey(mutation.getKey()));
        } else {
            throw Assert.fail("unknown mutation type %s", mutation.getClass());
        }
        if (!mutation.getPrecondition().isNone()) {
            newBuilder.setCurrentDocument(encodePrecondition(mutation.getPrecondition()));
        }
        return (Write) newBuilder.build();
    }

    public Mutation decodeMutation(Write write) {
        Precondition decodePrecondition;
        if (write.hasCurrentDocument()) {
            decodePrecondition = decodePrecondition(write.getCurrentDocument());
        } else {
            decodePrecondition = Precondition.NONE;
        }
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$Write$OperationCase[write.getOperationCase().ordinal()];
        boolean z = true;
        if (i != 1) {
            if (i == 2) {
                return new DeleteMutation(decodeKey(write.getDelete()), decodePrecondition);
            }
            if (i == 3) {
                List arrayList = new ArrayList();
                for (DocumentTransform.FieldTransform decodeFieldTransform : write.getTransform().getFieldTransformsList()) {
                    arrayList.add(decodeFieldTransform(decodeFieldTransform));
                }
                Boolean exists = decodePrecondition.getExists();
                if (exists == null || !exists.booleanValue()) {
                    z = false;
                }
                Assert.hardAssert(z, "Transforms only support precondition \"exists == true\"", new Object[0]);
                return new TransformMutation(decodeKey(write.getTransform().getDocument()), arrayList);
            }
            throw Assert.fail("Unknown mutation operation: %d", write.getOperationCase());
        } else if (write.hasUpdateMask()) {
            return new PatchMutation(decodeKey(write.getUpdate().getName()), decodeFields(write.getUpdate().getFieldsMap()), decodeDocumentMask(write.getUpdateMask()), decodePrecondition);
        } else {
            return new SetMutation(decodeKey(write.getUpdate().getName()), decodeFields(write.getUpdate().getFieldsMap()), decodePrecondition);
        }
    }

    private com.google.firestore.v1.Precondition encodePrecondition(Precondition precondition) {
        Assert.hardAssert(precondition.isNone() ^ 1, "Can't serialize an empty precondition", new Object[0]);
        com.google.firestore.v1.Precondition.Builder newBuilder = com.google.firestore.v1.Precondition.newBuilder();
        if (precondition.getUpdateTime() != null) {
            return (com.google.firestore.v1.Precondition) newBuilder.setUpdateTime(encodeVersion(precondition.getUpdateTime())).build();
        }
        if (precondition.getExists() != null) {
            return (com.google.firestore.v1.Precondition) newBuilder.setExists(precondition.getExists().booleanValue()).build();
        }
        throw Assert.fail("Unknown Precondition", new Object[0]);
    }

    private Precondition decodePrecondition(com.google.firestore.v1.Precondition precondition) {
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[precondition.getConditionTypeCase().ordinal()];
        if (i == 1) {
            return Precondition.updateTime(decodeVersion(precondition.getUpdateTime()));
        }
        if (i == 2) {
            return Precondition.exists(precondition.getExists());
        }
        if (i == 3) {
            return Precondition.NONE;
        }
        throw Assert.fail("Unknown precondition", new Object[0]);
    }

    private DocumentMask encodeDocumentMask(FieldMask fieldMask) {
        DocumentMask.Builder newBuilder = DocumentMask.newBuilder();
        for (FieldPath canonicalString : fieldMask.getMask()) {
            newBuilder.addFieldPaths(canonicalString.canonicalString());
        }
        return (DocumentMask) newBuilder.build();
    }

    private FieldMask decodeDocumentMask(DocumentMask documentMask) {
        int fieldPathsCount = documentMask.getFieldPathsCount();
        Set hashSet = new HashSet(fieldPathsCount);
        for (int i = 0; i < fieldPathsCount; i++) {
            hashSet.add(FieldPath.fromServerFormat(documentMask.getFieldPaths(i)));
        }
        return FieldMask.fromSet(hashSet);
    }

    private DocumentTransform.FieldTransform encodeFieldTransform(FieldTransform fieldTransform) {
        TransformOperation operation = fieldTransform.getOperation();
        if (operation instanceof ServerTimestampOperation) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setSetToServerValue(ServerValue.REQUEST_TIME).build();
        }
        if (operation instanceof Union) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setAppendMissingElements(encodeArrayTransformElements(((Union) operation).getElements())).build();
        } else if (operation instanceof Remove) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setRemoveAllFromArray(encodeArrayTransformElements(((Remove) operation).getElements())).build();
        } else if (operation instanceof NumericIncrementTransformOperation) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setIncrement(encodeValue(((NumericIncrementTransformOperation) operation).getOperand())).build();
        } else {
            throw Assert.fail("Unknown transform: %s", operation);
        }
    }

    private com.google.firestore.v1.ArrayValue encodeArrayTransformElements(List<FieldValue> list) {
        com.google.firestore.v1.ArrayValue.Builder newBuilder = com.google.firestore.v1.ArrayValue.newBuilder();
        for (FieldValue encodeValue : list) {
            newBuilder.addValues(encodeValue(encodeValue));
        }
        return (com.google.firestore.v1.ArrayValue) newBuilder.build();
    }

    private FieldTransform decodeFieldTransform(DocumentTransform.FieldTransform fieldTransform) {
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[fieldTransform.getTransformTypeCase().ordinal()];
        if (i == 1) {
            Assert.hardAssert(fieldTransform.getSetToServerValue() == ServerValue.REQUEST_TIME, "Unknown transform setToServerValue: %s", fieldTransform.getSetToServerValue());
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), ServerTimestampOperation.getInstance());
        } else if (i == 2) {
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new Union(decodeArrayTransformElements(fieldTransform.getAppendMissingElements())));
        } else {
            if (i == 3) {
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new Remove(decodeArrayTransformElements(fieldTransform.getRemoveAllFromArray())));
            }
            if (i == 4) {
                Assert.hardAssert(decodeValue(fieldTransform.getIncrement()) instanceof NumberValue, "Expected NUMERIC_ADD transform to be of number type, but was %s", decodeValue(fieldTransform.getIncrement()).getClass().getCanonicalName());
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new NumericIncrementTransformOperation((NumberValue) decodeValue(fieldTransform.getIncrement())));
            }
            throw Assert.fail("Unknown FieldTransform proto: %s", fieldTransform);
        }
    }

    private List<FieldValue> decodeArrayTransformElements(com.google.firestore.v1.ArrayValue arrayValue) {
        int valuesCount = arrayValue.getValuesCount();
        List<FieldValue> arrayList = new ArrayList(valuesCount);
        for (int i = 0; i < valuesCount; i++) {
            arrayList.add(decodeValue(arrayValue.getValues(i)));
        }
        return arrayList;
    }

    public MutationResult decodeMutationResult(WriteResult writeResult, SnapshotVersion snapshotVersion) {
        SnapshotVersion decodeVersion = decodeVersion(writeResult.getUpdateTime());
        if (!SnapshotVersion.NONE.equals(decodeVersion)) {
            snapshotVersion = decodeVersion;
        }
        List list = null;
        int transformResultsCount = writeResult.getTransformResultsCount();
        if (transformResultsCount > 0) {
            list = new ArrayList(transformResultsCount);
            for (int i = 0; i < transformResultsCount; i++) {
                list.add(decodeValue(writeResult.getTransformResults(i)));
            }
        }
        return new MutationResult(snapshotVersion, list);
    }

    @Nullable
    public Map<String, String> encodeListenRequestLabels(QueryData queryData) {
        String encodeLabel = encodeLabel(queryData.getPurpose());
        if (encodeLabel == null) {
            return null;
        }
        Map<String, String> hashMap = new HashMap(1);
        hashMap.put("goog-listen-tags", encodeLabel);
        return hashMap;
    }

    @Nullable
    private String encodeLabel(QueryPurpose queryPurpose) {
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$local$QueryPurpose[queryPurpose.ordinal()];
        if (i == 1) {
            return null;
        }
        if (i == 2) {
            return "existence-filter-mismatch";
        }
        if (i == 3) {
            return "limbo-document";
        }
        throw Assert.fail("Unrecognized query purpose: %s", queryPurpose);
    }

    public Target encodeTarget(QueryData queryData) {
        Target.Builder newBuilder = Target.newBuilder();
        Query query = queryData.getQuery();
        if (query.isDocumentQuery()) {
            newBuilder.setDocuments(encodeDocumentsTarget(query));
        } else {
            newBuilder.setQuery(encodeQueryTarget(query));
        }
        newBuilder.setTargetId(queryData.getTargetId());
        newBuilder.setResumeToken(queryData.getResumeToken());
        return (Target) newBuilder.build();
    }

    public DocumentsTarget encodeDocumentsTarget(Query query) {
        DocumentsTarget.Builder newBuilder = DocumentsTarget.newBuilder();
        newBuilder.addDocuments(encodeQueryPath(query.getPath()));
        return (DocumentsTarget) newBuilder.build();
    }

    public Query decodeDocumentsTarget(DocumentsTarget documentsTarget) {
        Assert.hardAssert(documentsTarget.getDocumentsCount() == 1, "DocumentsTarget contained other than 1 document %d", Integer.valueOf(documentsTarget.getDocumentsCount()));
        return Query.atPath(decodeQueryPath(documentsTarget.getDocuments(0)));
    }

    public QueryTarget encodeQueryTarget(Query query) {
        QueryTarget.Builder newBuilder = QueryTarget.newBuilder();
        StructuredQuery.Builder newBuilder2 = StructuredQuery.newBuilder();
        ResourcePath path = query.getPath();
        boolean z = true;
        if (query.getCollectionGroup() != null) {
            Assert.hardAssert(path.length() % 2 == 0, "Collection Group queries should be within a document path or root.", new Object[0]);
            newBuilder.setParent(encodeQueryPath(path));
            CollectionSelector.Builder newBuilder3 = CollectionSelector.newBuilder();
            newBuilder3.setCollectionId(query.getCollectionGroup());
            newBuilder3.setAllDescendants(true);
            newBuilder2.addFrom(newBuilder3);
        } else {
            if (path.length() % 2 == 0) {
                z = false;
            }
            Assert.hardAssert(z, "Document queries with filters are not supported.", new Object[0]);
            newBuilder.setParent(encodeQueryPath((ResourcePath) path.popLast()));
            CollectionSelector.Builder newBuilder4 = CollectionSelector.newBuilder();
            newBuilder4.setCollectionId(path.getLastSegment());
            newBuilder2.addFrom(newBuilder4);
        }
        if (query.getFilters().size() > 0) {
            newBuilder2.setWhere(encodeFilters(query.getFilters()));
        }
        for (OrderBy encodeOrderBy : query.getOrderBy()) {
            newBuilder2.addOrderBy(encodeOrderBy(encodeOrderBy));
        }
        if (query.hasLimit()) {
            newBuilder2.setLimit(Int32Value.newBuilder().setValue((int) query.getLimit()));
        }
        if (query.getStartAt() != null) {
            newBuilder2.setStartAt(encodeBound(query.getStartAt()));
        }
        if (query.getEndAt() != null) {
            newBuilder2.setEndAt(encodeBound(query.getEndAt()));
        }
        newBuilder.setStructuredQuery(newBuilder2);
        return (QueryTarget) newBuilder.build();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009c  */
    public com.google.firebase.firestore.core.Query decodeQueryTarget(com.google.firestore.v1.Target.QueryTarget r14) {
        /*
        r13 = this;
        r0 = r14.getParent();
        r0 = r13.decodeQueryPath(r0);
        r14 = r14.getStructuredQuery();
        r1 = r14.getFromCount();
        r2 = 0;
        r3 = 0;
        if (r1 <= 0) goto L_0x003b;
    L_0x0014:
        r4 = 1;
        if (r1 != r4) goto L_0x0018;
    L_0x0017:
        goto L_0x0019;
    L_0x0018:
        r4 = 0;
    L_0x0019:
        r1 = new java.lang.Object[r3];
        r5 = "StructuredQuery.from with more than one collection is not supported.";
        com.google.firebase.firestore.util.Assert.hardAssert(r4, r5, r1);
        r1 = r14.getFrom(r3);
        r4 = r1.getAllDescendants();
        if (r4 == 0) goto L_0x0031;
    L_0x002a:
        r1 = r1.getCollectionId();
        r5 = r0;
        r6 = r1;
        goto L_0x003d;
    L_0x0031:
        r1 = r1.getCollectionId();
        r0 = r0.append(r1);
        r0 = (com.google.firebase.firestore.model.ResourcePath) r0;
    L_0x003b:
        r5 = r0;
        r6 = r2;
    L_0x003d:
        r0 = r14.hasWhere();
        if (r0 == 0) goto L_0x004c;
    L_0x0043:
        r0 = r14.getWhere();
        r0 = r13.decodeFilters(r0);
        goto L_0x0050;
    L_0x004c:
        r0 = java.util.Collections.emptyList();
    L_0x0050:
        r7 = r0;
        r0 = r14.getOrderByCount();
        if (r0 <= 0) goto L_0x006e;
    L_0x0057:
        r1 = new java.util.ArrayList;
        r1.<init>(r0);
    L_0x005c:
        if (r3 >= r0) goto L_0x006c;
    L_0x005e:
        r4 = r14.getOrderBy(r3);
        r4 = r13.decodeOrderBy(r4);
        r1.add(r4);
        r3 = r3 + 1;
        goto L_0x005c;
    L_0x006c:
        r8 = r1;
        goto L_0x0073;
    L_0x006e:
        r0 = java.util.Collections.emptyList();
        r8 = r0;
    L_0x0073:
        r0 = -1;
        r3 = r14.hasLimit();
        if (r3 == 0) goto L_0x0084;
    L_0x007b:
        r0 = r14.getLimit();
        r0 = r0.getValue();
        r0 = (long) r0;
    L_0x0084:
        r9 = r0;
        r0 = r14.hasStartAt();
        if (r0 == 0) goto L_0x0095;
    L_0x008b:
        r0 = r14.getStartAt();
        r0 = r13.decodeBound(r0);
        r11 = r0;
        goto L_0x0096;
    L_0x0095:
        r11 = r2;
    L_0x0096:
        r0 = r14.hasEndAt();
        if (r0 == 0) goto L_0x00a4;
    L_0x009c:
        r14 = r14.getEndAt();
        r2 = r13.decodeBound(r14);
    L_0x00a4:
        r12 = r2;
        r14 = new com.google.firebase.firestore.core.Query;
        r4 = r14;
        r4.<init>(r5, r6, r7, r8, r9, r11, r12);
        return r14;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.RemoteSerializer.decodeQueryTarget(com.google.firestore.v1.Target$QueryTarget):com.google.firebase.firestore.core.Query");
    }

    private Filter encodeFilters(List<com.google.firebase.firestore.core.Filter> list) {
        Iterable arrayList = new ArrayList(list.size());
        for (com.google.firebase.firestore.core.Filter filter : list) {
            if (filter instanceof RelationFilter) {
                arrayList.add(encodeRelationFilter((RelationFilter) filter));
            } else {
                arrayList.add(encodeUnaryFilter(filter));
            }
        }
        if (list.size() == 1) {
            return (Filter) arrayList.get(0);
        }
        CompositeFilter.Builder newBuilder = CompositeFilter.newBuilder();
        newBuilder.setOp(CompositeFilter.Operator.AND);
        newBuilder.addAllFilters(arrayList);
        return (Filter) Filter.newBuilder().setCompositeFilter(newBuilder).build();
    }

    private List<com.google.firebase.firestore.core.Filter> decodeFilters(Filter filter) {
        List filtersList;
        if (filter.getFilterTypeCase() == FilterTypeCase.COMPOSITE_FILTER) {
            Assert.hardAssert(filter.getCompositeFilter().getOp() == CompositeFilter.Operator.AND, "Only AND-type composite filters are supported, got %d", filter.getCompositeFilter().getOp());
            filtersList = filter.getCompositeFilter().getFiltersList();
        } else {
            filtersList = Collections.singletonList(filter);
        }
        List<com.google.firebase.firestore.core.Filter> arrayList = new ArrayList(filtersList.size());
        for (Filter filter2 : filtersList) {
            int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[filter2.getFilterTypeCase().ordinal()];
            if (i == 1) {
                throw Assert.fail("Nested composite filters are not supported.", new Object[0]);
            } else if (i == 2) {
                arrayList.add(decodeRelationFilter(filter2.getFieldFilter()));
            } else if (i == 3) {
                arrayList.add(decodeUnaryFilter(filter2.getUnaryFilter()));
            } else {
                throw Assert.fail("Unrecognized Filter.filterType %d", filter2.getFilterTypeCase());
            }
        }
        return arrayList;
    }

    private Filter encodeRelationFilter(RelationFilter relationFilter) {
        FieldFilter.Builder newBuilder = FieldFilter.newBuilder();
        newBuilder.setField(encodeFieldPath(relationFilter.getField()));
        newBuilder.setOp(encodeRelationFilterOperator(relationFilter.getOperator()));
        newBuilder.setValue(encodeValue(relationFilter.getValue()));
        return (Filter) Filter.newBuilder().setFieldFilter(newBuilder).build();
    }

    private com.google.firebase.firestore.core.Filter decodeRelationFilter(FieldFilter fieldFilter) {
        return com.google.firebase.firestore.core.Filter.create(FieldPath.fromServerFormat(fieldFilter.getField().getFieldPath()), decodeRelationFilterOperator(fieldFilter.getOp()), decodeValue(fieldFilter.getValue()));
    }

    private Filter encodeUnaryFilter(com.google.firebase.firestore.core.Filter filter) {
        UnaryFilter.Builder newBuilder = UnaryFilter.newBuilder();
        newBuilder.setField(encodeFieldPath(filter.getField()));
        if (filter instanceof NaNFilter) {
            newBuilder.setOp(Operator.IS_NAN);
        } else if (filter instanceof NullFilter) {
            newBuilder.setOp(Operator.IS_NULL);
        } else {
            throw Assert.fail("Unrecognized filter: %s", filter.getCanonicalId());
        }
        return (Filter) Filter.newBuilder().setUnaryFilter(newBuilder).build();
    }

    private com.google.firebase.firestore.core.Filter decodeUnaryFilter(UnaryFilter unaryFilter) {
        FieldPath fromServerFormat = FieldPath.fromServerFormat(unaryFilter.getField().getFieldPath());
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator[unaryFilter.getOp().ordinal()];
        if (i == 1) {
            return new NaNFilter(fromServerFormat);
        }
        if (i == 2) {
            return new NullFilter(fromServerFormat);
        }
        throw Assert.fail("Unrecognized UnaryFilter.operator %d", unaryFilter.getOp());
    }

    private FieldReference encodeFieldPath(FieldPath fieldPath) {
        return (FieldReference) FieldReference.newBuilder().setFieldPath(fieldPath.canonicalString()).build();
    }

    private FieldFilter.Operator encodeRelationFilterOperator(com.google.firebase.firestore.core.Filter.Operator operator) {
        switch (operator) {
            case LESS_THAN:
                return FieldFilter.Operator.LESS_THAN;
            case LESS_THAN_OR_EQUAL:
                return FieldFilter.Operator.LESS_THAN_OR_EQUAL;
            case EQUAL:
                return FieldFilter.Operator.EQUAL;
            case GREATER_THAN:
                return FieldFilter.Operator.GREATER_THAN;
            case GREATER_THAN_OR_EQUAL:
                return FieldFilter.Operator.GREATER_THAN_OR_EQUAL;
            case ARRAY_CONTAINS:
                return FieldFilter.Operator.ARRAY_CONTAINS;
            default:
                throw Assert.fail("Unknown operator %d", operator);
        }
    }

    private com.google.firebase.firestore.core.Filter.Operator decodeRelationFilterOperator(FieldFilter.Operator operator) {
        switch (operator) {
            case LESS_THAN:
                return com.google.firebase.firestore.core.Filter.Operator.LESS_THAN;
            case LESS_THAN_OR_EQUAL:
                return com.google.firebase.firestore.core.Filter.Operator.LESS_THAN_OR_EQUAL;
            case EQUAL:
                return com.google.firebase.firestore.core.Filter.Operator.EQUAL;
            case GREATER_THAN_OR_EQUAL:
                return com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN_OR_EQUAL;
            case GREATER_THAN:
                return com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN;
            case ARRAY_CONTAINS:
                return com.google.firebase.firestore.core.Filter.Operator.ARRAY_CONTAINS;
            default:
                throw Assert.fail("Unhandled FieldFilter.operator %d", operator);
        }
    }

    private Order encodeOrderBy(OrderBy orderBy) {
        Order.Builder newBuilder = Order.newBuilder();
        if (orderBy.getDirection().equals(OrderBy.Direction.ASCENDING)) {
            newBuilder.setDirection(Direction.ASCENDING);
        } else {
            newBuilder.setDirection(Direction.DESCENDING);
        }
        newBuilder.setField(encodeFieldPath(orderBy.getField()));
        return (Order) newBuilder.build();
    }

    private OrderBy decodeOrderBy(Order order) {
        OrderBy.Direction direction;
        FieldPath fromServerFormat = FieldPath.fromServerFormat(order.getField().getFieldPath());
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$Direction[order.getDirection().ordinal()];
        if (i == 1) {
            direction = OrderBy.Direction.ASCENDING;
        } else if (i == 2) {
            direction = OrderBy.Direction.DESCENDING;
        } else {
            throw Assert.fail("Unrecognized direction %d", order.getDirection());
        }
        return OrderBy.getInstance(direction, fromServerFormat);
    }

    private Cursor encodeBound(Bound bound) {
        Cursor.Builder newBuilder = Cursor.newBuilder();
        newBuilder.setBefore(bound.isBefore());
        for (FieldValue encodeValue : bound.getPosition()) {
            newBuilder.addValues(encodeValue(encodeValue));
        }
        return (Cursor) newBuilder.build();
    }

    private Bound decodeBound(Cursor cursor) {
        int valuesCount = cursor.getValuesCount();
        List arrayList = new ArrayList(valuesCount);
        for (int i = 0; i < valuesCount; i++) {
            arrayList.add(decodeValue(cursor.getValues(i)));
        }
        return new Bound(arrayList, cursor.getBefore());
    }

    public WatchChange decodeWatchChange(ListenResponse listenResponse) {
        WatchChange watchTargetChange;
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase[listenResponse.getResponseTypeCase().ordinal()];
        Status status = null;
        List targetIdsList;
        if (i == 1) {
            WatchTargetChangeType watchTargetChangeType;
            TargetChange targetChange = listenResponse.getTargetChange();
            i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[targetChange.getTargetChangeType().ordinal()];
            if (i == 1) {
                watchTargetChangeType = WatchTargetChangeType.NoChange;
            } else if (i == 2) {
                watchTargetChangeType = WatchTargetChangeType.Added;
            } else if (i == 3) {
                watchTargetChangeType = WatchTargetChangeType.Removed;
                status = fromStatus(targetChange.getCause());
            } else if (i == 4) {
                watchTargetChangeType = WatchTargetChangeType.Current;
            } else if (i == 5) {
                watchTargetChangeType = WatchTargetChangeType.Reset;
            } else {
                throw new IllegalArgumentException("Unknown target change type");
            }
            watchTargetChange = new WatchTargetChange(watchTargetChangeType, targetChange.getTargetIdsList(), targetChange.getResumeToken(), status);
        } else if (i == 2) {
            DocumentChange documentChange = listenResponse.getDocumentChange();
            targetIdsList = documentChange.getTargetIdsList();
            List removedTargetIdsList = documentChange.getRemovedTargetIdsList();
            DocumentKey decodeKey = decodeKey(documentChange.getDocument().getName());
            SnapshotVersion decodeVersion = decodeVersion(documentChange.getDocument().getUpdateTime());
            Assert.hardAssert(decodeVersion.equals(SnapshotVersion.NONE) ^ true, "Got a document change without an update time", new Object[0]);
            MaybeDocument document = new com.google.firebase.firestore.model.Document(decodeKey, decodeVersion, decodeFields(documentChange.getDocument().getFieldsMap()), DocumentState.SYNCED, documentChange.getDocument());
            return new WatchChange.DocumentChange(targetIdsList, removedTargetIdsList, document.getKey(), document);
        } else if (i == 3) {
            DocumentDelete documentDelete = listenResponse.getDocumentDelete();
            targetIdsList = documentDelete.getRemovedTargetIdsList();
            MaybeDocument noDocument = new NoDocument(decodeKey(documentDelete.getDocument()), decodeVersion(documentDelete.getReadTime()), false);
            return new WatchChange.DocumentChange(Collections.emptyList(), targetIdsList, noDocument.getKey(), noDocument);
        } else if (i == 4) {
            DocumentRemove documentRemove = listenResponse.getDocumentRemove();
            watchTargetChange = new WatchChange.DocumentChange(Collections.emptyList(), documentRemove.getRemovedTargetIdsList(), decodeKey(documentRemove.getDocument()), null);
        } else if (i == 5) {
            ExistenceFilter filter = listenResponse.getFilter();
            return new ExistenceFilterWatchChange(filter.getTargetId(), new ExistenceFilter(filter.getCount()));
        } else {
            throw new IllegalArgumentException("Unknown change type set");
        }
        return watchTargetChange;
    }

    public SnapshotVersion decodeVersionFromListenResponse(ListenResponse listenResponse) {
        if (listenResponse.getResponseTypeCase() != ResponseTypeCase.TARGET_CHANGE) {
            return SnapshotVersion.NONE;
        }
        if (listenResponse.getTargetChange().getTargetIdsCount() != 0) {
            return SnapshotVersion.NONE;
        }
        return decodeVersion(listenResponse.getTargetChange().getReadTime());
    }

    private Status fromStatus(com.google.rpc.Status status) {
        return Status.fromCodeValue(status.getCode()).withDescription(status.getMessage());
    }
}
