package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import com.google.protobuf.Internal.ProtobufList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class StructuredQuery extends GeneratedMessageLite<StructuredQuery, Builder> implements StructuredQueryOrBuilder {
    private static final StructuredQuery DEFAULT_INSTANCE = new StructuredQuery();
    public static final int END_AT_FIELD_NUMBER = 8;
    public static final int FROM_FIELD_NUMBER = 2;
    public static final int LIMIT_FIELD_NUMBER = 5;
    public static final int OFFSET_FIELD_NUMBER = 6;
    public static final int ORDER_BY_FIELD_NUMBER = 4;
    private static volatile Parser<StructuredQuery> PARSER = null;
    public static final int SELECT_FIELD_NUMBER = 1;
    public static final int START_AT_FIELD_NUMBER = 7;
    public static final int WHERE_FIELD_NUMBER = 3;
    private int bitField0_;
    private Cursor endAt_;
    private ProtobufList<CollectionSelector> from_ = GeneratedMessageLite.emptyProtobufList();
    private Int32Value limit_;
    private int offset_;
    private ProtobufList<Order> orderBy_ = GeneratedMessageLite.emptyProtobufList();
    private Projection select_;
    private Cursor startAt_;
    private Filter where_;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firestore.v1.StructuredQuery$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase = new int[FilterTypeCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$OperandTypeCase = new int[OperandTypeCase.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:10:?, code:
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.FIELD_FILTER.ordinal()] = 2;
     */
        /* JADX WARNING: Missing block: B:34:0x00b0, code:
            return;
     */
        static {
            /*
            r0 = com.google.firestore.v1.StructuredQuery.UnaryFilter.OperandTypeCase.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$OperandTypeCase = r0;
            r0 = 1;
            r1 = $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$OperandTypeCase;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = com.google.firestore.v1.StructuredQuery.UnaryFilter.OperandTypeCase.FIELD;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = r2.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1[r2] = r0;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r1 = 2;
            r2 = $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$OperandTypeCase;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = com.google.firestore.v1.StructuredQuery.UnaryFilter.OperandTypeCase.OPERANDTYPE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x001f }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r2 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.values();
            r2 = r2.length;
            r2 = new int[r2];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase = r2;
            r2 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r3 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.COMPOSITE_FILTER;	 Catch:{ NoSuchFieldError -> 0x0032 }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0032 }
            r2[r3] = r0;	 Catch:{ NoSuchFieldError -> 0x0032 }
        L_0x0032:
            r2 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase;	 Catch:{ NoSuchFieldError -> 0x003c }
            r3 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.FIELD_FILTER;	 Catch:{ NoSuchFieldError -> 0x003c }
            r3 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x003c }
            r2[r3] = r1;	 Catch:{ NoSuchFieldError -> 0x003c }
        L_0x003c:
            r2 = 3;
            r3 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase;	 Catch:{ NoSuchFieldError -> 0x0047 }
            r4 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.UNARY_FILTER;	 Catch:{ NoSuchFieldError -> 0x0047 }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x0047 }
            r3[r4] = r2;	 Catch:{ NoSuchFieldError -> 0x0047 }
        L_0x0047:
            r3 = 4;
            r4 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase;	 Catch:{ NoSuchFieldError -> 0x0052 }
            r5 = com.google.firestore.v1.StructuredQuery.Filter.FilterTypeCase.FILTERTYPE_NOT_SET;	 Catch:{ NoSuchFieldError -> 0x0052 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0052 }
            r4[r5] = r3;	 Catch:{ NoSuchFieldError -> 0x0052 }
        L_0x0052:
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.values();
            r4 = r4.length;
            r4 = new int[r4];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = r4;
            r4 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0065 }
            r5 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE;	 Catch:{ NoSuchFieldError -> 0x0065 }
            r5 = r5.ordinal();	 Catch:{ NoSuchFieldError -> 0x0065 }
            r4[r5] = r0;	 Catch:{ NoSuchFieldError -> 0x0065 }
        L_0x0065:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x006f }
            r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.IS_INITIALIZED;	 Catch:{ NoSuchFieldError -> 0x006f }
            r4 = r4.ordinal();	 Catch:{ NoSuchFieldError -> 0x006f }
            r0[r4] = r1;	 Catch:{ NoSuchFieldError -> 0x006f }
        L_0x006f:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0079 }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MAKE_IMMUTABLE;	 Catch:{ NoSuchFieldError -> 0x0079 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0079 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0079 }
        L_0x0079:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0083 }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER;	 Catch:{ NoSuchFieldError -> 0x0083 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0083 }
            r0[r1] = r3;	 Catch:{ NoSuchFieldError -> 0x0083 }
        L_0x0083:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x008e }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.VISIT;	 Catch:{ NoSuchFieldError -> 0x008e }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x008e }
            r2 = 5;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x008e }
        L_0x008e:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x0099 }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MERGE_FROM_STREAM;	 Catch:{ NoSuchFieldError -> 0x0099 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0099 }
            r2 = 6;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0099 }
        L_0x0099:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x00a4 }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE;	 Catch:{ NoSuchFieldError -> 0x00a4 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x00a4 }
            r2 = 7;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00a4 }
        L_0x00a4:
            r0 = $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;	 Catch:{ NoSuchFieldError -> 0x00b0 }
            r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_PARSER;	 Catch:{ NoSuchFieldError -> 0x00b0 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x00b0 }
            r2 = 8;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x00b0 }
        L_0x00b0:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.StructuredQuery.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface CollectionSelectorOrBuilder extends MessageLiteOrBuilder {
        boolean getAllDescendants();

        String getCollectionId();

        ByteString getCollectionIdBytes();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface CompositeFilterOrBuilder extends MessageLiteOrBuilder {
        Filter getFilters(int i);

        int getFiltersCount();

        List<Filter> getFiltersList();

        Operator getOp();

        int getOpValue();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Direction implements EnumLite {
        DIRECTION_UNSPECIFIED(0),
        ASCENDING(1),
        DESCENDING(2),
        UNRECOGNIZED(-1);
        
        public static final int ASCENDING_VALUE = 1;
        public static final int DESCENDING_VALUE = 2;
        public static final int DIRECTION_UNSPECIFIED_VALUE = 0;
        private static final EnumLiteMap<Direction> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new EnumLiteMap<Direction>() {
                public Direction findValueByNumber(int i) {
                    return Direction.forNumber(i);
                }
            };
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static Direction valueOf(int i) {
            return forNumber(i);
        }

        public static Direction forNumber(int i) {
            if (i == 0) {
                return DIRECTION_UNSPECIFIED;
            }
            if (i != 1) {
                return i != 2 ? null : DESCENDING;
            } else {
                return ASCENDING;
            }
        }

        public static EnumLiteMap<Direction> internalGetValueMap() {
            return internalValueMap;
        }

        private Direction(int i) {
            this.value = i;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface FieldFilterOrBuilder extends MessageLiteOrBuilder {
        FieldReference getField();

        Operator getOp();

        int getOpValue();

        Value getValue();

        boolean hasField();

        boolean hasValue();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface FieldReferenceOrBuilder extends MessageLiteOrBuilder {
        String getFieldPath();

        ByteString getFieldPathBytes();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface FilterOrBuilder extends MessageLiteOrBuilder {
        CompositeFilter getCompositeFilter();

        FieldFilter getFieldFilter();

        FilterTypeCase getFilterTypeCase();

        UnaryFilter getUnaryFilter();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface OrderOrBuilder extends MessageLiteOrBuilder {
        Direction getDirection();

        int getDirectionValue();

        FieldReference getField();

        boolean hasField();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface ProjectionOrBuilder extends MessageLiteOrBuilder {
        FieldReference getFields(int i);

        int getFieldsCount();

        List<FieldReference> getFieldsList();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface UnaryFilterOrBuilder extends MessageLiteOrBuilder {
        FieldReference getField();

        Operator getOp();

        int getOpValue();

        OperandTypeCase getOperandTypeCase();
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<StructuredQuery, Builder> implements StructuredQueryOrBuilder {
        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            super(StructuredQuery.DEFAULT_INSTANCE);
        }

        public boolean hasSelect() {
            return ((StructuredQuery) this.instance).hasSelect();
        }

        public Projection getSelect() {
            return ((StructuredQuery) this.instance).getSelect();
        }

        public Builder setSelect(Projection projection) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setSelect(projection);
            return this;
        }

        public Builder setSelect(Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setSelect(builder);
            return this;
        }

        public Builder mergeSelect(Projection projection) {
            copyOnWrite();
            ((StructuredQuery) this.instance).mergeSelect(projection);
            return this;
        }

        public Builder clearSelect() {
            copyOnWrite();
            ((StructuredQuery) this.instance).clearSelect();
            return this;
        }

        public List<CollectionSelector> getFromList() {
            return Collections.unmodifiableList(((StructuredQuery) this.instance).getFromList());
        }

        public int getFromCount() {
            return ((StructuredQuery) this.instance).getFromCount();
        }

        public CollectionSelector getFrom(int i) {
            return ((StructuredQuery) this.instance).getFrom(i);
        }

        public Builder setFrom(int i, CollectionSelector collectionSelector) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setFrom(i, collectionSelector);
            return this;
        }

        public Builder setFrom(int i, Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setFrom(i, builder);
            return this;
        }

        public Builder addFrom(CollectionSelector collectionSelector) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addFrom(collectionSelector);
            return this;
        }

        public Builder addFrom(int i, CollectionSelector collectionSelector) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addFrom(i, collectionSelector);
            return this;
        }

        public Builder addFrom(Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addFrom(builder);
            return this;
        }

        public Builder addFrom(int i, Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addFrom(i, builder);
            return this;
        }

        public Builder addAllFrom(Iterable<? extends CollectionSelector> iterable) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addAllFrom(iterable);
            return this;
        }

        public Builder clearFrom() {
            copyOnWrite();
            ((StructuredQuery) this.instance).clearFrom();
            return this;
        }

        public Builder removeFrom(int i) {
            copyOnWrite();
            ((StructuredQuery) this.instance).removeFrom(i);
            return this;
        }

        public boolean hasWhere() {
            return ((StructuredQuery) this.instance).hasWhere();
        }

        public Filter getWhere() {
            return ((StructuredQuery) this.instance).getWhere();
        }

        public Builder setWhere(Filter filter) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setWhere(filter);
            return this;
        }

        public Builder setWhere(Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setWhere(builder);
            return this;
        }

        public Builder mergeWhere(Filter filter) {
            copyOnWrite();
            ((StructuredQuery) this.instance).mergeWhere(filter);
            return this;
        }

        public Builder clearWhere() {
            copyOnWrite();
            ((StructuredQuery) this.instance).clearWhere();
            return this;
        }

        public List<Order> getOrderByList() {
            return Collections.unmodifiableList(((StructuredQuery) this.instance).getOrderByList());
        }

        public int getOrderByCount() {
            return ((StructuredQuery) this.instance).getOrderByCount();
        }

        public Order getOrderBy(int i) {
            return ((StructuredQuery) this.instance).getOrderBy(i);
        }

        public Builder setOrderBy(int i, Order order) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setOrderBy(i, order);
            return this;
        }

        public Builder setOrderBy(int i, Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setOrderBy(i, builder);
            return this;
        }

        public Builder addOrderBy(Order order) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addOrderBy(order);
            return this;
        }

        public Builder addOrderBy(int i, Order order) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addOrderBy(i, order);
            return this;
        }

        public Builder addOrderBy(Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addOrderBy(builder);
            return this;
        }

        public Builder addOrderBy(int i, Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addOrderBy(i, builder);
            return this;
        }

        public Builder addAllOrderBy(Iterable<? extends Order> iterable) {
            copyOnWrite();
            ((StructuredQuery) this.instance).addAllOrderBy(iterable);
            return this;
        }

        public Builder clearOrderBy() {
            copyOnWrite();
            ((StructuredQuery) this.instance).clearOrderBy();
            return this;
        }

        public Builder removeOrderBy(int i) {
            copyOnWrite();
            ((StructuredQuery) this.instance).removeOrderBy(i);
            return this;
        }

        public boolean hasStartAt() {
            return ((StructuredQuery) this.instance).hasStartAt();
        }

        public Cursor getStartAt() {
            return ((StructuredQuery) this.instance).getStartAt();
        }

        public Builder setStartAt(Cursor cursor) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setStartAt(cursor);
            return this;
        }

        public Builder setStartAt(com.google.firestore.v1.Cursor.Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setStartAt(builder);
            return this;
        }

        public Builder mergeStartAt(Cursor cursor) {
            copyOnWrite();
            ((StructuredQuery) this.instance).mergeStartAt(cursor);
            return this;
        }

        public Builder clearStartAt() {
            copyOnWrite();
            ((StructuredQuery) this.instance).clearStartAt();
            return this;
        }

        public boolean hasEndAt() {
            return ((StructuredQuery) this.instance).hasEndAt();
        }

        public Cursor getEndAt() {
            return ((StructuredQuery) this.instance).getEndAt();
        }

        public Builder setEndAt(Cursor cursor) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setEndAt(cursor);
            return this;
        }

        public Builder setEndAt(com.google.firestore.v1.Cursor.Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setEndAt(builder);
            return this;
        }

        public Builder mergeEndAt(Cursor cursor) {
            copyOnWrite();
            ((StructuredQuery) this.instance).mergeEndAt(cursor);
            return this;
        }

        public Builder clearEndAt() {
            copyOnWrite();
            ((StructuredQuery) this.instance).clearEndAt();
            return this;
        }

        public int getOffset() {
            return ((StructuredQuery) this.instance).getOffset();
        }

        public Builder setOffset(int i) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setOffset(i);
            return this;
        }

        public Builder clearOffset() {
            copyOnWrite();
            ((StructuredQuery) this.instance).clearOffset();
            return this;
        }

        public boolean hasLimit() {
            return ((StructuredQuery) this.instance).hasLimit();
        }

        public Int32Value getLimit() {
            return ((StructuredQuery) this.instance).getLimit();
        }

        public Builder setLimit(Int32Value int32Value) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setLimit(int32Value);
            return this;
        }

        public Builder setLimit(com.google.protobuf.Int32Value.Builder builder) {
            copyOnWrite();
            ((StructuredQuery) this.instance).setLimit(builder);
            return this;
        }

        public Builder mergeLimit(Int32Value int32Value) {
            copyOnWrite();
            ((StructuredQuery) this.instance).mergeLimit(int32Value);
            return this;
        }

        public Builder clearLimit() {
            copyOnWrite();
            ((StructuredQuery) this.instance).clearLimit();
            return this;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class CollectionSelector extends GeneratedMessageLite<CollectionSelector, Builder> implements CollectionSelectorOrBuilder {
        public static final int ALL_DESCENDANTS_FIELD_NUMBER = 3;
        public static final int COLLECTION_ID_FIELD_NUMBER = 2;
        private static final CollectionSelector DEFAULT_INSTANCE = new CollectionSelector();
        private static volatile Parser<CollectionSelector> PARSER;
        private boolean allDescendants_;
        private String collectionId_ = "";

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<CollectionSelector, Builder> implements CollectionSelectorOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(CollectionSelector.DEFAULT_INSTANCE);
            }

            public String getCollectionId() {
                return ((CollectionSelector) this.instance).getCollectionId();
            }

            public ByteString getCollectionIdBytes() {
                return ((CollectionSelector) this.instance).getCollectionIdBytes();
            }

            public Builder setCollectionId(String str) {
                copyOnWrite();
                ((CollectionSelector) this.instance).setCollectionId(str);
                return this;
            }

            public Builder clearCollectionId() {
                copyOnWrite();
                ((CollectionSelector) this.instance).clearCollectionId();
                return this;
            }

            public Builder setCollectionIdBytes(ByteString byteString) {
                copyOnWrite();
                ((CollectionSelector) this.instance).setCollectionIdBytes(byteString);
                return this;
            }

            public boolean getAllDescendants() {
                return ((CollectionSelector) this.instance).getAllDescendants();
            }

            public Builder setAllDescendants(boolean z) {
                copyOnWrite();
                ((CollectionSelector) this.instance).setAllDescendants(z);
                return this;
            }

            public Builder clearAllDescendants() {
                copyOnWrite();
                ((CollectionSelector) this.instance).clearAllDescendants();
                return this;
            }
        }

        private CollectionSelector() {
        }

        public String getCollectionId() {
            return this.collectionId_;
        }

        public ByteString getCollectionIdBytes() {
            return ByteString.copyFromUtf8(this.collectionId_);
        }

        private void setCollectionId(String str) {
            if (str != null) {
                this.collectionId_ = str;
                return;
            }
            throw new NullPointerException();
        }

        private void clearCollectionId() {
            this.collectionId_ = getDefaultInstance().getCollectionId();
        }

        private void setCollectionIdBytes(ByteString byteString) {
            if (byteString != null) {
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.collectionId_ = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean getAllDescendants() {
            return this.allDescendants_;
        }

        private void setAllDescendants(boolean z) {
            this.allDescendants_ = z;
        }

        private void clearAllDescendants() {
            this.allDescendants_ = false;
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!this.collectionId_.isEmpty()) {
                codedOutputStream.writeString(2, getCollectionId());
            }
            boolean z = this.allDescendants_;
            if (z) {
                codedOutputStream.writeBool(3, z);
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (!this.collectionId_.isEmpty()) {
                i = 0 + CodedOutputStream.computeStringSize(2, getCollectionId());
            }
            boolean z = this.allDescendants_;
            if (z) {
                i += CodedOutputStream.computeBoolSize(3, z);
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static CollectionSelector parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (CollectionSelector) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static CollectionSelector parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (CollectionSelector) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static CollectionSelector parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (CollectionSelector) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static CollectionSelector parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (CollectionSelector) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static CollectionSelector parseFrom(InputStream inputStream) throws IOException {
            return (CollectionSelector) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static CollectionSelector parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (CollectionSelector) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static CollectionSelector parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (CollectionSelector) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static CollectionSelector parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (CollectionSelector) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static CollectionSelector parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (CollectionSelector) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static CollectionSelector parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (CollectionSelector) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CollectionSelector collectionSelector) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(collectionSelector);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new CollectionSelector();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    CollectionSelector collectionSelector = (CollectionSelector) obj2;
                    this.collectionId_ = visitor.visitString(this.collectionId_.isEmpty() ^ true, this.collectionId_, true ^ collectionSelector.collectionId_.isEmpty(), collectionSelector.collectionId_);
                    boolean z = this.allDescendants_;
                    boolean z2 = collectionSelector.allDescendants_;
                    this.allDescendants_ = visitor.visitBoolean(z, z, z2, z2);
                    MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    Object obj3 = null;
                    while (obj3 == null) {
                        try {
                            int readTag = codedInputStream.readTag();
                            if (readTag != 0) {
                                if (readTag == 18) {
                                    this.collectionId_ = codedInputStream.readStringRequireUtf8();
                                } else if (readTag == 24) {
                                    this.allDescendants_ = codedInputStream.readBool();
                                } else if (codedInputStream.skipField(readTag)) {
                                }
                            }
                            obj3 = 1;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (CollectionSelector.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static CollectionSelector getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CollectionSelector> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class CompositeFilter extends GeneratedMessageLite<CompositeFilter, Builder> implements CompositeFilterOrBuilder {
        private static final CompositeFilter DEFAULT_INSTANCE = new CompositeFilter();
        public static final int FILTERS_FIELD_NUMBER = 2;
        public static final int OP_FIELD_NUMBER = 1;
        private static volatile Parser<CompositeFilter> PARSER;
        private int bitField0_;
        private ProtobufList<Filter> filters_ = GeneratedMessageLite.emptyProtobufList();
        private int op_;

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public enum Operator implements EnumLite {
            OPERATOR_UNSPECIFIED(0),
            AND(1),
            UNRECOGNIZED(-1);
            
            public static final int AND_VALUE = 1;
            public static final int OPERATOR_UNSPECIFIED_VALUE = 0;
            private static final EnumLiteMap<Operator> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Operator>() {
                    public Operator findValueByNumber(int i) {
                        return Operator.forNumber(i);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Operator valueOf(int i) {
                return forNumber(i);
            }

            public static Operator forNumber(int i) {
                if (i != 0) {
                    return i != 1 ? null : AND;
                } else {
                    return OPERATOR_UNSPECIFIED;
                }
            }

            public static EnumLiteMap<Operator> internalGetValueMap() {
                return internalValueMap;
            }

            private Operator(int i) {
                this.value = i;
            }
        }

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<CompositeFilter, Builder> implements CompositeFilterOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(CompositeFilter.DEFAULT_INSTANCE);
            }

            public int getOpValue() {
                return ((CompositeFilter) this.instance).getOpValue();
            }

            public Builder setOpValue(int i) {
                copyOnWrite();
                ((CompositeFilter) this.instance).setOpValue(i);
                return this;
            }

            public Operator getOp() {
                return ((CompositeFilter) this.instance).getOp();
            }

            public Builder setOp(Operator operator) {
                copyOnWrite();
                ((CompositeFilter) this.instance).setOp(operator);
                return this;
            }

            public Builder clearOp() {
                copyOnWrite();
                ((CompositeFilter) this.instance).clearOp();
                return this;
            }

            public List<Filter> getFiltersList() {
                return Collections.unmodifiableList(((CompositeFilter) this.instance).getFiltersList());
            }

            public int getFiltersCount() {
                return ((CompositeFilter) this.instance).getFiltersCount();
            }

            public Filter getFilters(int i) {
                return ((CompositeFilter) this.instance).getFilters(i);
            }

            public Builder setFilters(int i, Filter filter) {
                copyOnWrite();
                ((CompositeFilter) this.instance).setFilters(i, filter);
                return this;
            }

            public Builder setFilters(int i, Builder builder) {
                copyOnWrite();
                ((CompositeFilter) this.instance).setFilters(i, builder);
                return this;
            }

            public Builder addFilters(Filter filter) {
                copyOnWrite();
                ((CompositeFilter) this.instance).addFilters(filter);
                return this;
            }

            public Builder addFilters(int i, Filter filter) {
                copyOnWrite();
                ((CompositeFilter) this.instance).addFilters(i, filter);
                return this;
            }

            public Builder addFilters(Builder builder) {
                copyOnWrite();
                ((CompositeFilter) this.instance).addFilters(builder);
                return this;
            }

            public Builder addFilters(int i, Builder builder) {
                copyOnWrite();
                ((CompositeFilter) this.instance).addFilters(i, builder);
                return this;
            }

            public Builder addAllFilters(Iterable<? extends Filter> iterable) {
                copyOnWrite();
                ((CompositeFilter) this.instance).addAllFilters(iterable);
                return this;
            }

            public Builder clearFilters() {
                copyOnWrite();
                ((CompositeFilter) this.instance).clearFilters();
                return this;
            }

            public Builder removeFilters(int i) {
                copyOnWrite();
                ((CompositeFilter) this.instance).removeFilters(i);
                return this;
            }
        }

        private CompositeFilter() {
        }

        public int getOpValue() {
            return this.op_;
        }

        public Operator getOp() {
            Operator forNumber = Operator.forNumber(this.op_);
            return forNumber == null ? Operator.UNRECOGNIZED : forNumber;
        }

        private void setOpValue(int i) {
            this.op_ = i;
        }

        private void setOp(Operator operator) {
            if (operator != null) {
                this.op_ = operator.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        private void clearOp() {
            this.op_ = 0;
        }

        public List<Filter> getFiltersList() {
            return this.filters_;
        }

        public List<? extends FilterOrBuilder> getFiltersOrBuilderList() {
            return this.filters_;
        }

        public int getFiltersCount() {
            return this.filters_.size();
        }

        public Filter getFilters(int i) {
            return (Filter) this.filters_.get(i);
        }

        public FilterOrBuilder getFiltersOrBuilder(int i) {
            return (FilterOrBuilder) this.filters_.get(i);
        }

        private void ensureFiltersIsMutable() {
            if (!this.filters_.isModifiable()) {
                this.filters_ = GeneratedMessageLite.mutableCopy(this.filters_);
            }
        }

        private void setFilters(int i, Filter filter) {
            if (filter != null) {
                ensureFiltersIsMutable();
                this.filters_.set(i, filter);
                return;
            }
            throw new NullPointerException();
        }

        private void setFilters(int i, Builder builder) {
            ensureFiltersIsMutable();
            this.filters_.set(i, (Filter) builder.build());
        }

        private void addFilters(Filter filter) {
            if (filter != null) {
                ensureFiltersIsMutable();
                this.filters_.add(filter);
                return;
            }
            throw new NullPointerException();
        }

        private void addFilters(int i, Filter filter) {
            if (filter != null) {
                ensureFiltersIsMutable();
                this.filters_.add(i, filter);
                return;
            }
            throw new NullPointerException();
        }

        private void addFilters(Builder builder) {
            ensureFiltersIsMutable();
            this.filters_.add((Filter) builder.build());
        }

        private void addFilters(int i, Builder builder) {
            ensureFiltersIsMutable();
            this.filters_.add(i, (Filter) builder.build());
        }

        private void addAllFilters(Iterable<? extends Filter> iterable) {
            ensureFiltersIsMutable();
            AbstractMessageLite.addAll(iterable, this.filters_);
        }

        private void clearFilters() {
            this.filters_ = GeneratedMessageLite.emptyProtobufList();
        }

        private void removeFilters(int i) {
            ensureFiltersIsMutable();
            this.filters_.remove(i);
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (this.op_ != Operator.OPERATOR_UNSPECIFIED.getNumber()) {
                codedOutputStream.writeEnum(1, this.op_);
            }
            for (int i = 0; i < this.filters_.size(); i++) {
                codedOutputStream.writeMessage(2, (MessageLite) this.filters_.get(i));
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int i2 = 0;
            i = this.op_ != Operator.OPERATOR_UNSPECIFIED.getNumber() ? CodedOutputStream.computeEnumSize(1, this.op_) + 0 : 0;
            while (i2 < this.filters_.size()) {
                i += CodedOutputStream.computeMessageSize(2, (MessageLite) this.filters_.get(i2));
                i2++;
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static CompositeFilter parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (CompositeFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static CompositeFilter parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (CompositeFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static CompositeFilter parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (CompositeFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static CompositeFilter parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (CompositeFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static CompositeFilter parseFrom(InputStream inputStream) throws IOException {
            return (CompositeFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static CompositeFilter parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (CompositeFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static CompositeFilter parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (CompositeFilter) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static CompositeFilter parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (CompositeFilter) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static CompositeFilter parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (CompositeFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static CompositeFilter parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (CompositeFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CompositeFilter compositeFilter) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(compositeFilter);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            boolean z = false;
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new CompositeFilter();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    this.filters_.makeImmutable();
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    CompositeFilter compositeFilter = (CompositeFilter) obj2;
                    boolean z2 = this.op_ != 0;
                    int i = this.op_;
                    if (compositeFilter.op_ != 0) {
                        z = true;
                    }
                    this.op_ = visitor.visitInt(z2, i, z, compositeFilter.op_);
                    this.filters_ = visitor.visitList(this.filters_, compositeFilter.filters_);
                    if (visitor == MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= compositeFilter.bitField0_;
                    }
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    while (!z) {
                        try {
                            int readTag = codedInputStream.readTag();
                            if (readTag != 0) {
                                if (readTag == 8) {
                                    this.op_ = codedInputStream.readEnum();
                                } else if (readTag == 18) {
                                    if (!this.filters_.isModifiable()) {
                                        this.filters_ = GeneratedMessageLite.mutableCopy(this.filters_);
                                    }
                                    this.filters_.add((Filter) codedInputStream.readMessage(Filter.parser(), extensionRegistryLite));
                                } else if (codedInputStream.skipField(readTag)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (CompositeFilter.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static CompositeFilter getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CompositeFilter> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class FieldFilter extends GeneratedMessageLite<FieldFilter, Builder> implements FieldFilterOrBuilder {
        private static final FieldFilter DEFAULT_INSTANCE = new FieldFilter();
        public static final int FIELD_FIELD_NUMBER = 1;
        public static final int OP_FIELD_NUMBER = 2;
        private static volatile Parser<FieldFilter> PARSER = null;
        public static final int VALUE_FIELD_NUMBER = 3;
        private FieldReference field_;
        private int op_;
        private Value value_;

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public enum Operator implements EnumLite {
            OPERATOR_UNSPECIFIED(0),
            LESS_THAN(1),
            LESS_THAN_OR_EQUAL(2),
            GREATER_THAN(3),
            GREATER_THAN_OR_EQUAL(4),
            EQUAL(5),
            ARRAY_CONTAINS(7),
            UNRECOGNIZED(-1);
            
            public static final int ARRAY_CONTAINS_VALUE = 7;
            public static final int EQUAL_VALUE = 5;
            public static final int GREATER_THAN_OR_EQUAL_VALUE = 4;
            public static final int GREATER_THAN_VALUE = 3;
            public static final int LESS_THAN_OR_EQUAL_VALUE = 2;
            public static final int LESS_THAN_VALUE = 1;
            public static final int OPERATOR_UNSPECIFIED_VALUE = 0;
            private static final EnumLiteMap<Operator> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Operator>() {
                    public Operator findValueByNumber(int i) {
                        return Operator.forNumber(i);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Operator valueOf(int i) {
                return forNumber(i);
            }

            public static Operator forNumber(int i) {
                if (i == 0) {
                    return OPERATOR_UNSPECIFIED;
                }
                if (i == 1) {
                    return LESS_THAN;
                }
                if (i == 2) {
                    return LESS_THAN_OR_EQUAL;
                }
                if (i == 3) {
                    return GREATER_THAN;
                }
                if (i == 4) {
                    return GREATER_THAN_OR_EQUAL;
                }
                if (i != 5) {
                    return i != 7 ? null : ARRAY_CONTAINS;
                } else {
                    return EQUAL;
                }
            }

            public static EnumLiteMap<Operator> internalGetValueMap() {
                return internalValueMap;
            }

            private Operator(int i) {
                this.value = i;
            }
        }

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<FieldFilter, Builder> implements FieldFilterOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(FieldFilter.DEFAULT_INSTANCE);
            }

            public boolean hasField() {
                return ((FieldFilter) this.instance).hasField();
            }

            public FieldReference getField() {
                return ((FieldFilter) this.instance).getField();
            }

            public Builder setField(FieldReference fieldReference) {
                copyOnWrite();
                ((FieldFilter) this.instance).setField(fieldReference);
                return this;
            }

            public Builder setField(Builder builder) {
                copyOnWrite();
                ((FieldFilter) this.instance).setField(builder);
                return this;
            }

            public Builder mergeField(FieldReference fieldReference) {
                copyOnWrite();
                ((FieldFilter) this.instance).mergeField(fieldReference);
                return this;
            }

            public Builder clearField() {
                copyOnWrite();
                ((FieldFilter) this.instance).clearField();
                return this;
            }

            public int getOpValue() {
                return ((FieldFilter) this.instance).getOpValue();
            }

            public Builder setOpValue(int i) {
                copyOnWrite();
                ((FieldFilter) this.instance).setOpValue(i);
                return this;
            }

            public Operator getOp() {
                return ((FieldFilter) this.instance).getOp();
            }

            public Builder setOp(Operator operator) {
                copyOnWrite();
                ((FieldFilter) this.instance).setOp(operator);
                return this;
            }

            public Builder clearOp() {
                copyOnWrite();
                ((FieldFilter) this.instance).clearOp();
                return this;
            }

            public boolean hasValue() {
                return ((FieldFilter) this.instance).hasValue();
            }

            public Value getValue() {
                return ((FieldFilter) this.instance).getValue();
            }

            public Builder setValue(Value value) {
                copyOnWrite();
                ((FieldFilter) this.instance).setValue(value);
                return this;
            }

            public Builder setValue(com.google.firestore.v1.Value.Builder builder) {
                copyOnWrite();
                ((FieldFilter) this.instance).setValue(builder);
                return this;
            }

            public Builder mergeValue(Value value) {
                copyOnWrite();
                ((FieldFilter) this.instance).mergeValue(value);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((FieldFilter) this.instance).clearValue();
                return this;
            }
        }

        private FieldFilter() {
        }

        public boolean hasField() {
            return this.field_ != null;
        }

        public FieldReference getField() {
            FieldReference fieldReference = this.field_;
            return fieldReference == null ? FieldReference.getDefaultInstance() : fieldReference;
        }

        private void setField(FieldReference fieldReference) {
            if (fieldReference != null) {
                this.field_ = fieldReference;
                return;
            }
            throw new NullPointerException();
        }

        private void setField(Builder builder) {
            this.field_ = (FieldReference) builder.build();
        }

        private void mergeField(FieldReference fieldReference) {
            FieldReference fieldReference2 = this.field_;
            if (fieldReference2 == null || fieldReference2 == FieldReference.getDefaultInstance()) {
                this.field_ = fieldReference;
            } else {
                this.field_ = (FieldReference) ((Builder) FieldReference.newBuilder(this.field_).mergeFrom(fieldReference)).buildPartial();
            }
        }

        private void clearField() {
            this.field_ = null;
        }

        public int getOpValue() {
            return this.op_;
        }

        public Operator getOp() {
            Operator forNumber = Operator.forNumber(this.op_);
            return forNumber == null ? Operator.UNRECOGNIZED : forNumber;
        }

        private void setOpValue(int i) {
            this.op_ = i;
        }

        private void setOp(Operator operator) {
            if (operator != null) {
                this.op_ = operator.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        private void clearOp() {
            this.op_ = 0;
        }

        public boolean hasValue() {
            return this.value_ != null;
        }

        public Value getValue() {
            Value value = this.value_;
            return value == null ? Value.getDefaultInstance() : value;
        }

        private void setValue(Value value) {
            if (value != null) {
                this.value_ = value;
                return;
            }
            throw new NullPointerException();
        }

        private void setValue(com.google.firestore.v1.Value.Builder builder) {
            this.value_ = (Value) builder.build();
        }

        private void mergeValue(Value value) {
            Value value2 = this.value_;
            if (value2 == null || value2 == Value.getDefaultInstance()) {
                this.value_ = value;
            } else {
                this.value_ = (Value) ((com.google.firestore.v1.Value.Builder) Value.newBuilder(this.value_).mergeFrom(value)).buildPartial();
            }
        }

        private void clearValue() {
            this.value_ = null;
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (this.field_ != null) {
                codedOutputStream.writeMessage(1, getField());
            }
            if (this.op_ != Operator.OPERATOR_UNSPECIFIED.getNumber()) {
                codedOutputStream.writeEnum(2, this.op_);
            }
            if (this.value_ != null) {
                codedOutputStream.writeMessage(3, getValue());
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (this.field_ != null) {
                i = 0 + CodedOutputStream.computeMessageSize(1, getField());
            }
            if (this.op_ != Operator.OPERATOR_UNSPECIFIED.getNumber()) {
                i += CodedOutputStream.computeEnumSize(2, this.op_);
            }
            if (this.value_ != null) {
                i += CodedOutputStream.computeMessageSize(3, getValue());
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static FieldFilter parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (FieldFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static FieldFilter parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (FieldFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static FieldFilter parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (FieldFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static FieldFilter parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (FieldFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static FieldFilter parseFrom(InputStream inputStream) throws IOException {
            return (FieldFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static FieldFilter parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static FieldFilter parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (FieldFilter) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static FieldFilter parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldFilter) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static FieldFilter parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (FieldFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static FieldFilter parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FieldFilter fieldFilter) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(fieldFilter);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            boolean z = false;
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new FieldFilter();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    FieldFilter fieldFilter = (FieldFilter) obj2;
                    this.field_ = (FieldReference) visitor.visitMessage(this.field_, fieldFilter.field_);
                    boolean z2 = this.op_ != 0;
                    int i = this.op_;
                    if (fieldFilter.op_ != 0) {
                        z = true;
                    }
                    this.op_ = visitor.visitInt(z2, i, z, fieldFilter.op_);
                    this.value_ = (Value) visitor.visitMessage(this.value_, fieldFilter.value_);
                    MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    while (!z) {
                        try {
                            int readTag = codedInputStream.readTag();
                            if (readTag != 0) {
                                if (readTag == 10) {
                                    Builder builder = this.field_ != null ? (Builder) this.field_.toBuilder() : null;
                                    this.field_ = (FieldReference) codedInputStream.readMessage(FieldReference.parser(), extensionRegistryLite);
                                    if (builder != null) {
                                        builder.mergeFrom(this.field_);
                                        this.field_ = (FieldReference) builder.buildPartial();
                                    }
                                } else if (readTag == 16) {
                                    this.op_ = codedInputStream.readEnum();
                                } else if (readTag == 26) {
                                    com.google.firestore.v1.Value.Builder builder2 = this.value_ != null ? (com.google.firestore.v1.Value.Builder) this.value_.toBuilder() : null;
                                    this.value_ = (Value) codedInputStream.readMessage(Value.parser(), extensionRegistryLite);
                                    if (builder2 != null) {
                                        builder2.mergeFrom(this.value_);
                                        this.value_ = (Value) builder2.buildPartial();
                                    }
                                } else if (codedInputStream.skipField(readTag)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (FieldFilter.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static FieldFilter getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FieldFilter> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class FieldReference extends GeneratedMessageLite<FieldReference, Builder> implements FieldReferenceOrBuilder {
        private static final FieldReference DEFAULT_INSTANCE = new FieldReference();
        public static final int FIELD_PATH_FIELD_NUMBER = 2;
        private static volatile Parser<FieldReference> PARSER;
        private String fieldPath_ = "";

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<FieldReference, Builder> implements FieldReferenceOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(FieldReference.DEFAULT_INSTANCE);
            }

            public String getFieldPath() {
                return ((FieldReference) this.instance).getFieldPath();
            }

            public ByteString getFieldPathBytes() {
                return ((FieldReference) this.instance).getFieldPathBytes();
            }

            public Builder setFieldPath(String str) {
                copyOnWrite();
                ((FieldReference) this.instance).setFieldPath(str);
                return this;
            }

            public Builder clearFieldPath() {
                copyOnWrite();
                ((FieldReference) this.instance).clearFieldPath();
                return this;
            }

            public Builder setFieldPathBytes(ByteString byteString) {
                copyOnWrite();
                ((FieldReference) this.instance).setFieldPathBytes(byteString);
                return this;
            }
        }

        private FieldReference() {
        }

        public String getFieldPath() {
            return this.fieldPath_;
        }

        public ByteString getFieldPathBytes() {
            return ByteString.copyFromUtf8(this.fieldPath_);
        }

        private void setFieldPath(String str) {
            if (str != null) {
                this.fieldPath_ = str;
                return;
            }
            throw new NullPointerException();
        }

        private void clearFieldPath() {
            this.fieldPath_ = getDefaultInstance().getFieldPath();
        }

        private void setFieldPathBytes(ByteString byteString) {
            if (byteString != null) {
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.fieldPath_ = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!this.fieldPath_.isEmpty()) {
                codedOutputStream.writeString(2, getFieldPath());
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (!this.fieldPath_.isEmpty()) {
                i = 0 + CodedOutputStream.computeStringSize(2, getFieldPath());
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static FieldReference parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (FieldReference) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static FieldReference parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (FieldReference) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static FieldReference parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (FieldReference) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static FieldReference parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (FieldReference) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static FieldReference parseFrom(InputStream inputStream) throws IOException {
            return (FieldReference) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static FieldReference parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldReference) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static FieldReference parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (FieldReference) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static FieldReference parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldReference) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static FieldReference parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (FieldReference) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static FieldReference parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (FieldReference) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FieldReference fieldReference) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(fieldReference);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new FieldReference();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    FieldReference fieldReference = (FieldReference) obj2;
                    this.fieldPath_ = ((Visitor) obj).visitString(this.fieldPath_.isEmpty() ^ true, this.fieldPath_, true ^ fieldReference.fieldPath_.isEmpty(), fieldReference.fieldPath_);
                    MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    Object obj3 = null;
                    while (obj3 == null) {
                        try {
                            int readTag = codedInputStream.readTag();
                            if (readTag != 0) {
                                if (readTag == 18) {
                                    this.fieldPath_ = codedInputStream.readStringRequireUtf8();
                                } else if (codedInputStream.skipField(readTag)) {
                                }
                            }
                            obj3 = 1;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (FieldReference.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static FieldReference getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FieldReference> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Filter extends GeneratedMessageLite<Filter, Builder> implements FilterOrBuilder {
        public static final int COMPOSITE_FILTER_FIELD_NUMBER = 1;
        private static final Filter DEFAULT_INSTANCE = new Filter();
        public static final int FIELD_FILTER_FIELD_NUMBER = 2;
        private static volatile Parser<Filter> PARSER = null;
        public static final int UNARY_FILTER_FIELD_NUMBER = 3;
        private int filterTypeCase_ = 0;
        private Object filterType_;

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public enum FilterTypeCase implements EnumLite {
            COMPOSITE_FILTER(1),
            FIELD_FILTER(2),
            UNARY_FILTER(3),
            FILTERTYPE_NOT_SET(0);
            
            private final int value;

            private FilterTypeCase(int i) {
                this.value = i;
            }

            @Deprecated
            public static FilterTypeCase valueOf(int i) {
                return forNumber(i);
            }

            public static FilterTypeCase forNumber(int i) {
                if (i == 0) {
                    return FILTERTYPE_NOT_SET;
                }
                if (i == 1) {
                    return COMPOSITE_FILTER;
                }
                if (i != 2) {
                    return i != 3 ? null : UNARY_FILTER;
                } else {
                    return FIELD_FILTER;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Filter, Builder> implements FilterOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(Filter.DEFAULT_INSTANCE);
            }

            public FilterTypeCase getFilterTypeCase() {
                return ((Filter) this.instance).getFilterTypeCase();
            }

            public Builder clearFilterType() {
                copyOnWrite();
                ((Filter) this.instance).clearFilterType();
                return this;
            }

            public CompositeFilter getCompositeFilter() {
                return ((Filter) this.instance).getCompositeFilter();
            }

            public Builder setCompositeFilter(CompositeFilter compositeFilter) {
                copyOnWrite();
                ((Filter) this.instance).setCompositeFilter(compositeFilter);
                return this;
            }

            public Builder setCompositeFilter(Builder builder) {
                copyOnWrite();
                ((Filter) this.instance).setCompositeFilter(builder);
                return this;
            }

            public Builder mergeCompositeFilter(CompositeFilter compositeFilter) {
                copyOnWrite();
                ((Filter) this.instance).mergeCompositeFilter(compositeFilter);
                return this;
            }

            public Builder clearCompositeFilter() {
                copyOnWrite();
                ((Filter) this.instance).clearCompositeFilter();
                return this;
            }

            public FieldFilter getFieldFilter() {
                return ((Filter) this.instance).getFieldFilter();
            }

            public Builder setFieldFilter(FieldFilter fieldFilter) {
                copyOnWrite();
                ((Filter) this.instance).setFieldFilter(fieldFilter);
                return this;
            }

            public Builder setFieldFilter(Builder builder) {
                copyOnWrite();
                ((Filter) this.instance).setFieldFilter(builder);
                return this;
            }

            public Builder mergeFieldFilter(FieldFilter fieldFilter) {
                copyOnWrite();
                ((Filter) this.instance).mergeFieldFilter(fieldFilter);
                return this;
            }

            public Builder clearFieldFilter() {
                copyOnWrite();
                ((Filter) this.instance).clearFieldFilter();
                return this;
            }

            public UnaryFilter getUnaryFilter() {
                return ((Filter) this.instance).getUnaryFilter();
            }

            public Builder setUnaryFilter(UnaryFilter unaryFilter) {
                copyOnWrite();
                ((Filter) this.instance).setUnaryFilter(unaryFilter);
                return this;
            }

            public Builder setUnaryFilter(Builder builder) {
                copyOnWrite();
                ((Filter) this.instance).setUnaryFilter(builder);
                return this;
            }

            public Builder mergeUnaryFilter(UnaryFilter unaryFilter) {
                copyOnWrite();
                ((Filter) this.instance).mergeUnaryFilter(unaryFilter);
                return this;
            }

            public Builder clearUnaryFilter() {
                copyOnWrite();
                ((Filter) this.instance).clearUnaryFilter();
                return this;
            }
        }

        private Filter() {
        }

        public FilterTypeCase getFilterTypeCase() {
            return FilterTypeCase.forNumber(this.filterTypeCase_);
        }

        private void clearFilterType() {
            this.filterTypeCase_ = 0;
            this.filterType_ = null;
        }

        public CompositeFilter getCompositeFilter() {
            if (this.filterTypeCase_ == 1) {
                return (CompositeFilter) this.filterType_;
            }
            return CompositeFilter.getDefaultInstance();
        }

        private void setCompositeFilter(CompositeFilter compositeFilter) {
            if (compositeFilter != null) {
                this.filterType_ = compositeFilter;
                this.filterTypeCase_ = 1;
                return;
            }
            throw new NullPointerException();
        }

        private void setCompositeFilter(Builder builder) {
            this.filterType_ = builder.build();
            this.filterTypeCase_ = 1;
        }

        private void mergeCompositeFilter(CompositeFilter compositeFilter) {
            if (this.filterTypeCase_ != 1 || this.filterType_ == CompositeFilter.getDefaultInstance()) {
                this.filterType_ = compositeFilter;
            } else {
                this.filterType_ = ((Builder) CompositeFilter.newBuilder((CompositeFilter) this.filterType_).mergeFrom(compositeFilter)).buildPartial();
            }
            this.filterTypeCase_ = 1;
        }

        private void clearCompositeFilter() {
            if (this.filterTypeCase_ == 1) {
                this.filterTypeCase_ = 0;
                this.filterType_ = null;
            }
        }

        public FieldFilter getFieldFilter() {
            if (this.filterTypeCase_ == 2) {
                return (FieldFilter) this.filterType_;
            }
            return FieldFilter.getDefaultInstance();
        }

        private void setFieldFilter(FieldFilter fieldFilter) {
            if (fieldFilter != null) {
                this.filterType_ = fieldFilter;
                this.filterTypeCase_ = 2;
                return;
            }
            throw new NullPointerException();
        }

        private void setFieldFilter(Builder builder) {
            this.filterType_ = builder.build();
            this.filterTypeCase_ = 2;
        }

        private void mergeFieldFilter(FieldFilter fieldFilter) {
            if (this.filterTypeCase_ != 2 || this.filterType_ == FieldFilter.getDefaultInstance()) {
                this.filterType_ = fieldFilter;
            } else {
                this.filterType_ = ((Builder) FieldFilter.newBuilder((FieldFilter) this.filterType_).mergeFrom(fieldFilter)).buildPartial();
            }
            this.filterTypeCase_ = 2;
        }

        private void clearFieldFilter() {
            if (this.filterTypeCase_ == 2) {
                this.filterTypeCase_ = 0;
                this.filterType_ = null;
            }
        }

        public UnaryFilter getUnaryFilter() {
            if (this.filterTypeCase_ == 3) {
                return (UnaryFilter) this.filterType_;
            }
            return UnaryFilter.getDefaultInstance();
        }

        private void setUnaryFilter(UnaryFilter unaryFilter) {
            if (unaryFilter != null) {
                this.filterType_ = unaryFilter;
                this.filterTypeCase_ = 3;
                return;
            }
            throw new NullPointerException();
        }

        private void setUnaryFilter(Builder builder) {
            this.filterType_ = builder.build();
            this.filterTypeCase_ = 3;
        }

        private void mergeUnaryFilter(UnaryFilter unaryFilter) {
            if (this.filterTypeCase_ != 3 || this.filterType_ == UnaryFilter.getDefaultInstance()) {
                this.filterType_ = unaryFilter;
            } else {
                this.filterType_ = ((Builder) UnaryFilter.newBuilder((UnaryFilter) this.filterType_).mergeFrom(unaryFilter)).buildPartial();
            }
            this.filterTypeCase_ = 3;
        }

        private void clearUnaryFilter() {
            if (this.filterTypeCase_ == 3) {
                this.filterTypeCase_ = 0;
                this.filterType_ = null;
            }
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (this.filterTypeCase_ == 1) {
                codedOutputStream.writeMessage(1, (CompositeFilter) this.filterType_);
            }
            if (this.filterTypeCase_ == 2) {
                codedOutputStream.writeMessage(2, (FieldFilter) this.filterType_);
            }
            if (this.filterTypeCase_ == 3) {
                codedOutputStream.writeMessage(3, (UnaryFilter) this.filterType_);
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (this.filterTypeCase_ == 1) {
                i = 0 + CodedOutputStream.computeMessageSize(1, (CompositeFilter) this.filterType_);
            }
            if (this.filterTypeCase_ == 2) {
                i += CodedOutputStream.computeMessageSize(2, (FieldFilter) this.filterType_);
            }
            if (this.filterTypeCase_ == 3) {
                i += CodedOutputStream.computeMessageSize(3, (UnaryFilter) this.filterType_);
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static Filter parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Filter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Filter parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Filter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Filter parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Filter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Filter parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Filter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Filter parseFrom(InputStream inputStream) throws IOException {
            return (Filter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Filter parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Filter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Filter parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Filter) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Filter parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Filter) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Filter parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Filter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Filter parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Filter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Filter filter) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(filter);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            boolean z = false;
            int i;
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Filter();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    Filter filter = (Filter) obj2;
                    i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[filter.getFilterTypeCase().ordinal()];
                    if (i == 1) {
                        if (this.filterTypeCase_ == 1) {
                            z = true;
                        }
                        this.filterType_ = visitor.visitOneofMessage(z, this.filterType_, filter.filterType_);
                    } else if (i == 2) {
                        if (this.filterTypeCase_ == 2) {
                            z = true;
                        }
                        this.filterType_ = visitor.visitOneofMessage(z, this.filterType_, filter.filterType_);
                    } else if (i == 3) {
                        if (this.filterTypeCase_ == 3) {
                            z = true;
                        }
                        this.filterType_ = visitor.visitOneofMessage(z, this.filterType_, filter.filterType_);
                    } else if (i == 4) {
                        if (this.filterTypeCase_ != 0) {
                            z = true;
                        }
                        visitor.visitOneofNotSet(z);
                    }
                    if (visitor == MergeFromVisitor.INSTANCE) {
                        i = filter.filterTypeCase_;
                        if (i != 0) {
                            this.filterTypeCase_ = i;
                        }
                    }
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    while (!z) {
                        try {
                            i = codedInputStream.readTag();
                            if (i != 0) {
                                if (i == 10) {
                                    Builder builder = this.filterTypeCase_ == 1 ? (Builder) ((CompositeFilter) this.filterType_).toBuilder() : null;
                                    this.filterType_ = codedInputStream.readMessage(CompositeFilter.parser(), extensionRegistryLite);
                                    if (builder != null) {
                                        builder.mergeFrom((CompositeFilter) this.filterType_);
                                        this.filterType_ = builder.buildPartial();
                                    }
                                    this.filterTypeCase_ = 1;
                                } else if (i == 18) {
                                    Builder builder2 = this.filterTypeCase_ == 2 ? (Builder) ((FieldFilter) this.filterType_).toBuilder() : null;
                                    this.filterType_ = codedInputStream.readMessage(FieldFilter.parser(), extensionRegistryLite);
                                    if (builder2 != null) {
                                        builder2.mergeFrom((FieldFilter) this.filterType_);
                                        this.filterType_ = builder2.buildPartial();
                                    }
                                    this.filterTypeCase_ = 2;
                                } else if (i == 26) {
                                    Builder builder3 = this.filterTypeCase_ == 3 ? (Builder) ((UnaryFilter) this.filterType_).toBuilder() : null;
                                    this.filterType_ = codedInputStream.readMessage(UnaryFilter.parser(), extensionRegistryLite);
                                    if (builder3 != null) {
                                        builder3.mergeFrom((UnaryFilter) this.filterType_);
                                        this.filterType_ = builder3.buildPartial();
                                    }
                                    this.filterTypeCase_ = 3;
                                } else if (codedInputStream.skipField(i)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (Filter.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static Filter getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Filter> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Order extends GeneratedMessageLite<Order, Builder> implements OrderOrBuilder {
        private static final Order DEFAULT_INSTANCE = new Order();
        public static final int DIRECTION_FIELD_NUMBER = 2;
        public static final int FIELD_FIELD_NUMBER = 1;
        private static volatile Parser<Order> PARSER;
        private int direction_;
        private FieldReference field_;

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Order, Builder> implements OrderOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(Order.DEFAULT_INSTANCE);
            }

            public boolean hasField() {
                return ((Order) this.instance).hasField();
            }

            public FieldReference getField() {
                return ((Order) this.instance).getField();
            }

            public Builder setField(FieldReference fieldReference) {
                copyOnWrite();
                ((Order) this.instance).setField(fieldReference);
                return this;
            }

            public Builder setField(Builder builder) {
                copyOnWrite();
                ((Order) this.instance).setField(builder);
                return this;
            }

            public Builder mergeField(FieldReference fieldReference) {
                copyOnWrite();
                ((Order) this.instance).mergeField(fieldReference);
                return this;
            }

            public Builder clearField() {
                copyOnWrite();
                ((Order) this.instance).clearField();
                return this;
            }

            public int getDirectionValue() {
                return ((Order) this.instance).getDirectionValue();
            }

            public Builder setDirectionValue(int i) {
                copyOnWrite();
                ((Order) this.instance).setDirectionValue(i);
                return this;
            }

            public Direction getDirection() {
                return ((Order) this.instance).getDirection();
            }

            public Builder setDirection(Direction direction) {
                copyOnWrite();
                ((Order) this.instance).setDirection(direction);
                return this;
            }

            public Builder clearDirection() {
                copyOnWrite();
                ((Order) this.instance).clearDirection();
                return this;
            }
        }

        private Order() {
        }

        public boolean hasField() {
            return this.field_ != null;
        }

        public FieldReference getField() {
            FieldReference fieldReference = this.field_;
            return fieldReference == null ? FieldReference.getDefaultInstance() : fieldReference;
        }

        private void setField(FieldReference fieldReference) {
            if (fieldReference != null) {
                this.field_ = fieldReference;
                return;
            }
            throw new NullPointerException();
        }

        private void setField(Builder builder) {
            this.field_ = (FieldReference) builder.build();
        }

        private void mergeField(FieldReference fieldReference) {
            FieldReference fieldReference2 = this.field_;
            if (fieldReference2 == null || fieldReference2 == FieldReference.getDefaultInstance()) {
                this.field_ = fieldReference;
            } else {
                this.field_ = (FieldReference) ((Builder) FieldReference.newBuilder(this.field_).mergeFrom(fieldReference)).buildPartial();
            }
        }

        private void clearField() {
            this.field_ = null;
        }

        public int getDirectionValue() {
            return this.direction_;
        }

        public Direction getDirection() {
            Direction forNumber = Direction.forNumber(this.direction_);
            return forNumber == null ? Direction.UNRECOGNIZED : forNumber;
        }

        private void setDirectionValue(int i) {
            this.direction_ = i;
        }

        private void setDirection(Direction direction) {
            if (direction != null) {
                this.direction_ = direction.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        private void clearDirection() {
            this.direction_ = 0;
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (this.field_ != null) {
                codedOutputStream.writeMessage(1, getField());
            }
            if (this.direction_ != Direction.DIRECTION_UNSPECIFIED.getNumber()) {
                codedOutputStream.writeEnum(2, this.direction_);
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (this.field_ != null) {
                i = 0 + CodedOutputStream.computeMessageSize(1, getField());
            }
            if (this.direction_ != Direction.DIRECTION_UNSPECIFIED.getNumber()) {
                i += CodedOutputStream.computeEnumSize(2, this.direction_);
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static Order parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Order) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Order parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Order) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Order parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Order) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Order parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Order) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Order parseFrom(InputStream inputStream) throws IOException {
            return (Order) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Order parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Order) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Order parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Order) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Order parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Order) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Order parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Order) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Order parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Order) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Order order) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(order);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            boolean z = false;
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Order();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    Order order = (Order) obj2;
                    this.field_ = (FieldReference) visitor.visitMessage(this.field_, order.field_);
                    boolean z2 = this.direction_ != 0;
                    int i = this.direction_;
                    if (order.direction_ != 0) {
                        z = true;
                    }
                    this.direction_ = visitor.visitInt(z2, i, z, order.direction_);
                    MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    while (!z) {
                        try {
                            int readTag = codedInputStream.readTag();
                            if (readTag != 0) {
                                if (readTag == 10) {
                                    Builder builder = this.field_ != null ? (Builder) this.field_.toBuilder() : null;
                                    this.field_ = (FieldReference) codedInputStream.readMessage(FieldReference.parser(), extensionRegistryLite);
                                    if (builder != null) {
                                        builder.mergeFrom(this.field_);
                                        this.field_ = (FieldReference) builder.buildPartial();
                                    }
                                } else if (readTag == 16) {
                                    this.direction_ = codedInputStream.readEnum();
                                } else if (codedInputStream.skipField(readTag)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (Order.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static Order getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Order> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Projection extends GeneratedMessageLite<Projection, Builder> implements ProjectionOrBuilder {
        private static final Projection DEFAULT_INSTANCE = new Projection();
        public static final int FIELDS_FIELD_NUMBER = 2;
        private static volatile Parser<Projection> PARSER;
        private ProtobufList<FieldReference> fields_ = GeneratedMessageLite.emptyProtobufList();

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Projection, Builder> implements ProjectionOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(Projection.DEFAULT_INSTANCE);
            }

            public List<FieldReference> getFieldsList() {
                return Collections.unmodifiableList(((Projection) this.instance).getFieldsList());
            }

            public int getFieldsCount() {
                return ((Projection) this.instance).getFieldsCount();
            }

            public FieldReference getFields(int i) {
                return ((Projection) this.instance).getFields(i);
            }

            public Builder setFields(int i, FieldReference fieldReference) {
                copyOnWrite();
                ((Projection) this.instance).setFields(i, fieldReference);
                return this;
            }

            public Builder setFields(int i, Builder builder) {
                copyOnWrite();
                ((Projection) this.instance).setFields(i, builder);
                return this;
            }

            public Builder addFields(FieldReference fieldReference) {
                copyOnWrite();
                ((Projection) this.instance).addFields(fieldReference);
                return this;
            }

            public Builder addFields(int i, FieldReference fieldReference) {
                copyOnWrite();
                ((Projection) this.instance).addFields(i, fieldReference);
                return this;
            }

            public Builder addFields(Builder builder) {
                copyOnWrite();
                ((Projection) this.instance).addFields(builder);
                return this;
            }

            public Builder addFields(int i, Builder builder) {
                copyOnWrite();
                ((Projection) this.instance).addFields(i, builder);
                return this;
            }

            public Builder addAllFields(Iterable<? extends FieldReference> iterable) {
                copyOnWrite();
                ((Projection) this.instance).addAllFields(iterable);
                return this;
            }

            public Builder clearFields() {
                copyOnWrite();
                ((Projection) this.instance).clearFields();
                return this;
            }

            public Builder removeFields(int i) {
                copyOnWrite();
                ((Projection) this.instance).removeFields(i);
                return this;
            }
        }

        private Projection() {
        }

        public List<FieldReference> getFieldsList() {
            return this.fields_;
        }

        public List<? extends FieldReferenceOrBuilder> getFieldsOrBuilderList() {
            return this.fields_;
        }

        public int getFieldsCount() {
            return this.fields_.size();
        }

        public FieldReference getFields(int i) {
            return (FieldReference) this.fields_.get(i);
        }

        public FieldReferenceOrBuilder getFieldsOrBuilder(int i) {
            return (FieldReferenceOrBuilder) this.fields_.get(i);
        }

        private void ensureFieldsIsMutable() {
            if (!this.fields_.isModifiable()) {
                this.fields_ = GeneratedMessageLite.mutableCopy(this.fields_);
            }
        }

        private void setFields(int i, FieldReference fieldReference) {
            if (fieldReference != null) {
                ensureFieldsIsMutable();
                this.fields_.set(i, fieldReference);
                return;
            }
            throw new NullPointerException();
        }

        private void setFields(int i, Builder builder) {
            ensureFieldsIsMutable();
            this.fields_.set(i, (FieldReference) builder.build());
        }

        private void addFields(FieldReference fieldReference) {
            if (fieldReference != null) {
                ensureFieldsIsMutable();
                this.fields_.add(fieldReference);
                return;
            }
            throw new NullPointerException();
        }

        private void addFields(int i, FieldReference fieldReference) {
            if (fieldReference != null) {
                ensureFieldsIsMutable();
                this.fields_.add(i, fieldReference);
                return;
            }
            throw new NullPointerException();
        }

        private void addFields(Builder builder) {
            ensureFieldsIsMutable();
            this.fields_.add((FieldReference) builder.build());
        }

        private void addFields(int i, Builder builder) {
            ensureFieldsIsMutable();
            this.fields_.add(i, (FieldReference) builder.build());
        }

        private void addAllFields(Iterable<? extends FieldReference> iterable) {
            ensureFieldsIsMutable();
            AbstractMessageLite.addAll(iterable, this.fields_);
        }

        private void clearFields() {
            this.fields_ = GeneratedMessageLite.emptyProtobufList();
        }

        private void removeFields(int i) {
            ensureFieldsIsMutable();
            this.fields_.remove(i);
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            for (int i = 0; i < this.fields_.size(); i++) {
                codedOutputStream.writeMessage(2, (MessageLite) this.fields_.get(i));
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int i2 = 0;
            for (i = 0; i < this.fields_.size(); i++) {
                i2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.fields_.get(i));
            }
            this.memoizedSerializedSize = i2;
            return i2;
        }

        public static Projection parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Projection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Projection parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Projection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Projection parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Projection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Projection parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Projection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Projection parseFrom(InputStream inputStream) throws IOException {
            return (Projection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Projection parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Projection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Projection parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Projection) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Projection parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Projection) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Projection parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Projection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Projection parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Projection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Projection projection) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(projection);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Projection();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    this.fields_.makeImmutable();
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    this.fields_ = ((Visitor) obj).visitList(this.fields_, ((Projection) obj2).fields_);
                    MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    Object obj3 = null;
                    while (obj3 == null) {
                        try {
                            int readTag = codedInputStream.readTag();
                            if (readTag != 0) {
                                if (readTag == 18) {
                                    if (!this.fields_.isModifiable()) {
                                        this.fields_ = GeneratedMessageLite.mutableCopy(this.fields_);
                                    }
                                    this.fields_.add((FieldReference) codedInputStream.readMessage(FieldReference.parser(), extensionRegistryLite));
                                } else if (codedInputStream.skipField(readTag)) {
                                }
                            }
                            obj3 = 1;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (Projection.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static Projection getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Projection> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class UnaryFilter extends GeneratedMessageLite<UnaryFilter, Builder> implements UnaryFilterOrBuilder {
        private static final UnaryFilter DEFAULT_INSTANCE = new UnaryFilter();
        public static final int FIELD_FIELD_NUMBER = 2;
        public static final int OP_FIELD_NUMBER = 1;
        private static volatile Parser<UnaryFilter> PARSER;
        private int op_;
        private int operandTypeCase_ = 0;
        private Object operandType_;

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public enum OperandTypeCase implements EnumLite {
            FIELD(2),
            OPERANDTYPE_NOT_SET(0);
            
            private final int value;

            private OperandTypeCase(int i) {
                this.value = i;
            }

            @Deprecated
            public static OperandTypeCase valueOf(int i) {
                return forNumber(i);
            }

            public static OperandTypeCase forNumber(int i) {
                if (i != 0) {
                    return i != 2 ? null : FIELD;
                } else {
                    return OPERANDTYPE_NOT_SET;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public enum Operator implements EnumLite {
            OPERATOR_UNSPECIFIED(0),
            IS_NAN(2),
            IS_NULL(3),
            UNRECOGNIZED(-1);
            
            public static final int IS_NAN_VALUE = 2;
            public static final int IS_NULL_VALUE = 3;
            public static final int OPERATOR_UNSPECIFIED_VALUE = 0;
            private static final EnumLiteMap<Operator> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Operator>() {
                    public Operator findValueByNumber(int i) {
                        return Operator.forNumber(i);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Operator valueOf(int i) {
                return forNumber(i);
            }

            public static Operator forNumber(int i) {
                if (i == 0) {
                    return OPERATOR_UNSPECIFIED;
                }
                if (i != 2) {
                    return i != 3 ? null : IS_NULL;
                } else {
                    return IS_NAN;
                }
            }

            public static EnumLiteMap<Operator> internalGetValueMap() {
                return internalValueMap;
            }

            private Operator(int i) {
                this.value = i;
            }
        }

        /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<UnaryFilter, Builder> implements UnaryFilterOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(UnaryFilter.DEFAULT_INSTANCE);
            }

            public OperandTypeCase getOperandTypeCase() {
                return ((UnaryFilter) this.instance).getOperandTypeCase();
            }

            public Builder clearOperandType() {
                copyOnWrite();
                ((UnaryFilter) this.instance).clearOperandType();
                return this;
            }

            public int getOpValue() {
                return ((UnaryFilter) this.instance).getOpValue();
            }

            public Builder setOpValue(int i) {
                copyOnWrite();
                ((UnaryFilter) this.instance).setOpValue(i);
                return this;
            }

            public Operator getOp() {
                return ((UnaryFilter) this.instance).getOp();
            }

            public Builder setOp(Operator operator) {
                copyOnWrite();
                ((UnaryFilter) this.instance).setOp(operator);
                return this;
            }

            public Builder clearOp() {
                copyOnWrite();
                ((UnaryFilter) this.instance).clearOp();
                return this;
            }

            public FieldReference getField() {
                return ((UnaryFilter) this.instance).getField();
            }

            public Builder setField(FieldReference fieldReference) {
                copyOnWrite();
                ((UnaryFilter) this.instance).setField(fieldReference);
                return this;
            }

            public Builder setField(Builder builder) {
                copyOnWrite();
                ((UnaryFilter) this.instance).setField(builder);
                return this;
            }

            public Builder mergeField(FieldReference fieldReference) {
                copyOnWrite();
                ((UnaryFilter) this.instance).mergeField(fieldReference);
                return this;
            }

            public Builder clearField() {
                copyOnWrite();
                ((UnaryFilter) this.instance).clearField();
                return this;
            }
        }

        private UnaryFilter() {
        }

        public OperandTypeCase getOperandTypeCase() {
            return OperandTypeCase.forNumber(this.operandTypeCase_);
        }

        private void clearOperandType() {
            this.operandTypeCase_ = 0;
            this.operandType_ = null;
        }

        public int getOpValue() {
            return this.op_;
        }

        public Operator getOp() {
            Operator forNumber = Operator.forNumber(this.op_);
            return forNumber == null ? Operator.UNRECOGNIZED : forNumber;
        }

        private void setOpValue(int i) {
            this.op_ = i;
        }

        private void setOp(Operator operator) {
            if (operator != null) {
                this.op_ = operator.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        private void clearOp() {
            this.op_ = 0;
        }

        public FieldReference getField() {
            if (this.operandTypeCase_ == 2) {
                return (FieldReference) this.operandType_;
            }
            return FieldReference.getDefaultInstance();
        }

        private void setField(FieldReference fieldReference) {
            if (fieldReference != null) {
                this.operandType_ = fieldReference;
                this.operandTypeCase_ = 2;
                return;
            }
            throw new NullPointerException();
        }

        private void setField(Builder builder) {
            this.operandType_ = builder.build();
            this.operandTypeCase_ = 2;
        }

        private void mergeField(FieldReference fieldReference) {
            if (this.operandTypeCase_ != 2 || this.operandType_ == FieldReference.getDefaultInstance()) {
                this.operandType_ = fieldReference;
            } else {
                this.operandType_ = ((Builder) FieldReference.newBuilder((FieldReference) this.operandType_).mergeFrom(fieldReference)).buildPartial();
            }
            this.operandTypeCase_ = 2;
        }

        private void clearField() {
            if (this.operandTypeCase_ == 2) {
                this.operandTypeCase_ = 0;
                this.operandType_ = null;
            }
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (this.op_ != Operator.OPERATOR_UNSPECIFIED.getNumber()) {
                codedOutputStream.writeEnum(1, this.op_);
            }
            if (this.operandTypeCase_ == 2) {
                codedOutputStream.writeMessage(2, (FieldReference) this.operandType_);
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            i = 0;
            if (this.op_ != Operator.OPERATOR_UNSPECIFIED.getNumber()) {
                i = 0 + CodedOutputStream.computeEnumSize(1, this.op_);
            }
            if (this.operandTypeCase_ == 2) {
                i += CodedOutputStream.computeMessageSize(2, (FieldReference) this.operandType_);
            }
            this.memoizedSerializedSize = i;
            return i;
        }

        public static UnaryFilter parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (UnaryFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static UnaryFilter parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UnaryFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static UnaryFilter parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (UnaryFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static UnaryFilter parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UnaryFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static UnaryFilter parseFrom(InputStream inputStream) throws IOException {
            return (UnaryFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static UnaryFilter parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UnaryFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UnaryFilter parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (UnaryFilter) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static UnaryFilter parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UnaryFilter) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UnaryFilter parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (UnaryFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static UnaryFilter parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UnaryFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(UnaryFilter unaryFilter) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(unaryFilter);
        }

        protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            boolean z = false;
            int i;
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new UnaryFilter();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    UnaryFilter unaryFilter = (UnaryFilter) obj2;
                    this.op_ = visitor.visitInt(this.op_ != 0, this.op_, unaryFilter.op_ != 0, unaryFilter.op_);
                    i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$OperandTypeCase[unaryFilter.getOperandTypeCase().ordinal()];
                    if (i == 1) {
                        if (this.operandTypeCase_ == 2) {
                            z = true;
                        }
                        this.operandType_ = visitor.visitOneofMessage(z, this.operandType_, unaryFilter.operandType_);
                    } else if (i == 2) {
                        if (this.operandTypeCase_ != 0) {
                            z = true;
                        }
                        visitor.visitOneofNotSet(z);
                    }
                    if (visitor == MergeFromVisitor.INSTANCE) {
                        i = unaryFilter.operandTypeCase_;
                        if (i != 0) {
                            this.operandTypeCase_ = i;
                        }
                    }
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    while (!z) {
                        try {
                            i = codedInputStream.readTag();
                            if (i != 0) {
                                if (i == 8) {
                                    this.op_ = codedInputStream.readEnum();
                                } else if (i == 18) {
                                    Builder builder = this.operandTypeCase_ == 2 ? (Builder) ((FieldReference) this.operandType_).toBuilder() : null;
                                    this.operandType_ = codedInputStream.readMessage(FieldReference.parser(), extensionRegistryLite);
                                    if (builder != null) {
                                        builder.mergeFrom((FieldReference) this.operandType_);
                                        this.operandType_ = builder.buildPartial();
                                    }
                                    this.operandTypeCase_ = 2;
                                } else if (codedInputStream.skipField(i)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (UnaryFilter.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static UnaryFilter getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UnaryFilter> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    private StructuredQuery() {
    }

    public boolean hasSelect() {
        return this.select_ != null;
    }

    public Projection getSelect() {
        Projection projection = this.select_;
        return projection == null ? Projection.getDefaultInstance() : projection;
    }

    private void setSelect(Projection projection) {
        if (projection != null) {
            this.select_ = projection;
            return;
        }
        throw new NullPointerException();
    }

    private void setSelect(Builder builder) {
        this.select_ = (Projection) builder.build();
    }

    private void mergeSelect(Projection projection) {
        Projection projection2 = this.select_;
        if (projection2 == null || projection2 == Projection.getDefaultInstance()) {
            this.select_ = projection;
        } else {
            this.select_ = (Projection) ((Builder) Projection.newBuilder(this.select_).mergeFrom(projection)).buildPartial();
        }
    }

    private void clearSelect() {
        this.select_ = null;
    }

    public List<CollectionSelector> getFromList() {
        return this.from_;
    }

    public List<? extends CollectionSelectorOrBuilder> getFromOrBuilderList() {
        return this.from_;
    }

    public int getFromCount() {
        return this.from_.size();
    }

    public CollectionSelector getFrom(int i) {
        return (CollectionSelector) this.from_.get(i);
    }

    public CollectionSelectorOrBuilder getFromOrBuilder(int i) {
        return (CollectionSelectorOrBuilder) this.from_.get(i);
    }

    private void ensureFromIsMutable() {
        if (!this.from_.isModifiable()) {
            this.from_ = GeneratedMessageLite.mutableCopy(this.from_);
        }
    }

    private void setFrom(int i, CollectionSelector collectionSelector) {
        if (collectionSelector != null) {
            ensureFromIsMutable();
            this.from_.set(i, collectionSelector);
            return;
        }
        throw new NullPointerException();
    }

    private void setFrom(int i, Builder builder) {
        ensureFromIsMutable();
        this.from_.set(i, (CollectionSelector) builder.build());
    }

    private void addFrom(CollectionSelector collectionSelector) {
        if (collectionSelector != null) {
            ensureFromIsMutable();
            this.from_.add(collectionSelector);
            return;
        }
        throw new NullPointerException();
    }

    private void addFrom(int i, CollectionSelector collectionSelector) {
        if (collectionSelector != null) {
            ensureFromIsMutable();
            this.from_.add(i, collectionSelector);
            return;
        }
        throw new NullPointerException();
    }

    private void addFrom(Builder builder) {
        ensureFromIsMutable();
        this.from_.add((CollectionSelector) builder.build());
    }

    private void addFrom(int i, Builder builder) {
        ensureFromIsMutable();
        this.from_.add(i, (CollectionSelector) builder.build());
    }

    private void addAllFrom(Iterable<? extends CollectionSelector> iterable) {
        ensureFromIsMutable();
        AbstractMessageLite.addAll(iterable, this.from_);
    }

    private void clearFrom() {
        this.from_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void removeFrom(int i) {
        ensureFromIsMutable();
        this.from_.remove(i);
    }

    public boolean hasWhere() {
        return this.where_ != null;
    }

    public Filter getWhere() {
        Filter filter = this.where_;
        return filter == null ? Filter.getDefaultInstance() : filter;
    }

    private void setWhere(Filter filter) {
        if (filter != null) {
            this.where_ = filter;
            return;
        }
        throw new NullPointerException();
    }

    private void setWhere(Builder builder) {
        this.where_ = (Filter) builder.build();
    }

    private void mergeWhere(Filter filter) {
        Filter filter2 = this.where_;
        if (filter2 == null || filter2 == Filter.getDefaultInstance()) {
            this.where_ = filter;
        } else {
            this.where_ = (Filter) ((Builder) Filter.newBuilder(this.where_).mergeFrom(filter)).buildPartial();
        }
    }

    private void clearWhere() {
        this.where_ = null;
    }

    public List<Order> getOrderByList() {
        return this.orderBy_;
    }

    public List<? extends OrderOrBuilder> getOrderByOrBuilderList() {
        return this.orderBy_;
    }

    public int getOrderByCount() {
        return this.orderBy_.size();
    }

    public Order getOrderBy(int i) {
        return (Order) this.orderBy_.get(i);
    }

    public OrderOrBuilder getOrderByOrBuilder(int i) {
        return (OrderOrBuilder) this.orderBy_.get(i);
    }

    private void ensureOrderByIsMutable() {
        if (!this.orderBy_.isModifiable()) {
            this.orderBy_ = GeneratedMessageLite.mutableCopy(this.orderBy_);
        }
    }

    private void setOrderBy(int i, Order order) {
        if (order != null) {
            ensureOrderByIsMutable();
            this.orderBy_.set(i, order);
            return;
        }
        throw new NullPointerException();
    }

    private void setOrderBy(int i, Builder builder) {
        ensureOrderByIsMutable();
        this.orderBy_.set(i, (Order) builder.build());
    }

    private void addOrderBy(Order order) {
        if (order != null) {
            ensureOrderByIsMutable();
            this.orderBy_.add(order);
            return;
        }
        throw new NullPointerException();
    }

    private void addOrderBy(int i, Order order) {
        if (order != null) {
            ensureOrderByIsMutable();
            this.orderBy_.add(i, order);
            return;
        }
        throw new NullPointerException();
    }

    private void addOrderBy(Builder builder) {
        ensureOrderByIsMutable();
        this.orderBy_.add((Order) builder.build());
    }

    private void addOrderBy(int i, Builder builder) {
        ensureOrderByIsMutable();
        this.orderBy_.add(i, (Order) builder.build());
    }

    private void addAllOrderBy(Iterable<? extends Order> iterable) {
        ensureOrderByIsMutable();
        AbstractMessageLite.addAll(iterable, this.orderBy_);
    }

    private void clearOrderBy() {
        this.orderBy_ = GeneratedMessageLite.emptyProtobufList();
    }

    private void removeOrderBy(int i) {
        ensureOrderByIsMutable();
        this.orderBy_.remove(i);
    }

    public boolean hasStartAt() {
        return this.startAt_ != null;
    }

    public Cursor getStartAt() {
        Cursor cursor = this.startAt_;
        return cursor == null ? Cursor.getDefaultInstance() : cursor;
    }

    private void setStartAt(Cursor cursor) {
        if (cursor != null) {
            this.startAt_ = cursor;
            return;
        }
        throw new NullPointerException();
    }

    private void setStartAt(com.google.firestore.v1.Cursor.Builder builder) {
        this.startAt_ = (Cursor) builder.build();
    }

    private void mergeStartAt(Cursor cursor) {
        Cursor cursor2 = this.startAt_;
        if (cursor2 == null || cursor2 == Cursor.getDefaultInstance()) {
            this.startAt_ = cursor;
        } else {
            this.startAt_ = (Cursor) ((com.google.firestore.v1.Cursor.Builder) Cursor.newBuilder(this.startAt_).mergeFrom(cursor)).buildPartial();
        }
    }

    private void clearStartAt() {
        this.startAt_ = null;
    }

    public boolean hasEndAt() {
        return this.endAt_ != null;
    }

    public Cursor getEndAt() {
        Cursor cursor = this.endAt_;
        return cursor == null ? Cursor.getDefaultInstance() : cursor;
    }

    private void setEndAt(Cursor cursor) {
        if (cursor != null) {
            this.endAt_ = cursor;
            return;
        }
        throw new NullPointerException();
    }

    private void setEndAt(com.google.firestore.v1.Cursor.Builder builder) {
        this.endAt_ = (Cursor) builder.build();
    }

    private void mergeEndAt(Cursor cursor) {
        Cursor cursor2 = this.endAt_;
        if (cursor2 == null || cursor2 == Cursor.getDefaultInstance()) {
            this.endAt_ = cursor;
        } else {
            this.endAt_ = (Cursor) ((com.google.firestore.v1.Cursor.Builder) Cursor.newBuilder(this.endAt_).mergeFrom(cursor)).buildPartial();
        }
    }

    private void clearEndAt() {
        this.endAt_ = null;
    }

    public int getOffset() {
        return this.offset_;
    }

    private void setOffset(int i) {
        this.offset_ = i;
    }

    private void clearOffset() {
        this.offset_ = 0;
    }

    public boolean hasLimit() {
        return this.limit_ != null;
    }

    public Int32Value getLimit() {
        Int32Value int32Value = this.limit_;
        return int32Value == null ? Int32Value.getDefaultInstance() : int32Value;
    }

    private void setLimit(Int32Value int32Value) {
        if (int32Value != null) {
            this.limit_ = int32Value;
            return;
        }
        throw new NullPointerException();
    }

    private void setLimit(com.google.protobuf.Int32Value.Builder builder) {
        this.limit_ = (Int32Value) builder.build();
    }

    private void mergeLimit(Int32Value int32Value) {
        Int32Value int32Value2 = this.limit_;
        if (int32Value2 == null || int32Value2 == Int32Value.getDefaultInstance()) {
            this.limit_ = int32Value;
        } else {
            this.limit_ = (Int32Value) ((com.google.protobuf.Int32Value.Builder) Int32Value.newBuilder(this.limit_).mergeFrom(int32Value)).buildPartial();
        }
    }

    private void clearLimit() {
        this.limit_ = null;
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        if (this.select_ != null) {
            codedOutputStream.writeMessage(1, getSelect());
        }
        int i = 0;
        for (int i2 = 0; i2 < this.from_.size(); i2++) {
            codedOutputStream.writeMessage(2, (MessageLite) this.from_.get(i2));
        }
        if (this.where_ != null) {
            codedOutputStream.writeMessage(3, getWhere());
        }
        while (i < this.orderBy_.size()) {
            codedOutputStream.writeMessage(4, (MessageLite) this.orderBy_.get(i));
            i++;
        }
        if (this.limit_ != null) {
            codedOutputStream.writeMessage(5, getLimit());
        }
        i = this.offset_;
        if (i != 0) {
            codedOutputStream.writeInt32(6, i);
        }
        if (this.startAt_ != null) {
            codedOutputStream.writeMessage(7, getStartAt());
        }
        if (this.endAt_ != null) {
            codedOutputStream.writeMessage(8, getEndAt());
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        int computeMessageSize = this.select_ != null ? CodedOutputStream.computeMessageSize(1, getSelect()) + 0 : 0;
        for (i = 0; i < this.from_.size(); i++) {
            computeMessageSize += CodedOutputStream.computeMessageSize(2, (MessageLite) this.from_.get(i));
        }
        if (this.where_ != null) {
            computeMessageSize += CodedOutputStream.computeMessageSize(3, getWhere());
        }
        while (i2 < this.orderBy_.size()) {
            computeMessageSize += CodedOutputStream.computeMessageSize(4, (MessageLite) this.orderBy_.get(i2));
            i2++;
        }
        if (this.limit_ != null) {
            computeMessageSize += CodedOutputStream.computeMessageSize(5, getLimit());
        }
        i = this.offset_;
        if (i != 0) {
            computeMessageSize += CodedOutputStream.computeInt32Size(6, i);
        }
        if (this.startAt_ != null) {
            computeMessageSize += CodedOutputStream.computeMessageSize(7, getStartAt());
        }
        if (this.endAt_ != null) {
            computeMessageSize += CodedOutputStream.computeMessageSize(8, getEndAt());
        }
        this.memoizedSerializedSize = computeMessageSize;
        return computeMessageSize;
    }

    public static StructuredQuery parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (StructuredQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static StructuredQuery parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (StructuredQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static StructuredQuery parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (StructuredQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static StructuredQuery parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (StructuredQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static StructuredQuery parseFrom(InputStream inputStream) throws IOException {
        return (StructuredQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static StructuredQuery parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (StructuredQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static StructuredQuery parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (StructuredQuery) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static StructuredQuery parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (StructuredQuery) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static StructuredQuery parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (StructuredQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static StructuredQuery parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (StructuredQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(StructuredQuery structuredQuery) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(structuredQuery);
    }

    protected final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new StructuredQuery();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.from_.makeImmutable();
                this.orderBy_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                StructuredQuery structuredQuery = (StructuredQuery) obj2;
                this.select_ = (Projection) visitor.visitMessage(this.select_, structuredQuery.select_);
                this.from_ = visitor.visitList(this.from_, structuredQuery.from_);
                this.where_ = (Filter) visitor.visitMessage(this.where_, structuredQuery.where_);
                this.orderBy_ = visitor.visitList(this.orderBy_, structuredQuery.orderBy_);
                this.startAt_ = (Cursor) visitor.visitMessage(this.startAt_, structuredQuery.startAt_);
                this.endAt_ = (Cursor) visitor.visitMessage(this.endAt_, structuredQuery.endAt_);
                boolean z2 = this.offset_ != 0;
                int i = this.offset_;
                if (structuredQuery.offset_ != 0) {
                    z = true;
                }
                this.offset_ = visitor.visitInt(z2, i, z, structuredQuery.offset_);
                this.limit_ = (Int32Value) visitor.visitMessage(this.limit_, structuredQuery.limit_);
                if (visitor == MergeFromVisitor.INSTANCE) {
                    this.bitField0_ |= structuredQuery.bitField0_;
                }
                return this;
            case MERGE_FROM_STREAM:
                CodedInputStream codedInputStream = (CodedInputStream) obj;
                ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                while (!z) {
                    try {
                        int readTag = codedInputStream.readTag();
                        if (readTag != 0) {
                            com.google.firestore.v1.Cursor.Builder builder;
                            if (readTag == 10) {
                                Builder builder2 = this.select_ != null ? (Builder) this.select_.toBuilder() : null;
                                this.select_ = (Projection) codedInputStream.readMessage(Projection.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom(this.select_);
                                    this.select_ = (Projection) builder2.buildPartial();
                                }
                            } else if (readTag == 18) {
                                if (!this.from_.isModifiable()) {
                                    this.from_ = GeneratedMessageLite.mutableCopy(this.from_);
                                }
                                this.from_.add((CollectionSelector) codedInputStream.readMessage(CollectionSelector.parser(), extensionRegistryLite));
                            } else if (readTag == 26) {
                                Builder builder3 = this.where_ != null ? (Builder) this.where_.toBuilder() : null;
                                this.where_ = (Filter) codedInputStream.readMessage(Filter.parser(), extensionRegistryLite);
                                if (builder3 != null) {
                                    builder3.mergeFrom(this.where_);
                                    this.where_ = (Filter) builder3.buildPartial();
                                }
                            } else if (readTag == 34) {
                                if (!this.orderBy_.isModifiable()) {
                                    this.orderBy_ = GeneratedMessageLite.mutableCopy(this.orderBy_);
                                }
                                this.orderBy_.add((Order) codedInputStream.readMessage(Order.parser(), extensionRegistryLite));
                            } else if (readTag == 42) {
                                com.google.protobuf.Int32Value.Builder builder4 = this.limit_ != null ? (com.google.protobuf.Int32Value.Builder) this.limit_.toBuilder() : null;
                                this.limit_ = (Int32Value) codedInputStream.readMessage(Int32Value.parser(), extensionRegistryLite);
                                if (builder4 != null) {
                                    builder4.mergeFrom(this.limit_);
                                    this.limit_ = (Int32Value) builder4.buildPartial();
                                }
                            } else if (readTag == 48) {
                                this.offset_ = codedInputStream.readInt32();
                            } else if (readTag == 58) {
                                builder = this.startAt_ != null ? (com.google.firestore.v1.Cursor.Builder) this.startAt_.toBuilder() : null;
                                this.startAt_ = (Cursor) codedInputStream.readMessage(Cursor.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.startAt_);
                                    this.startAt_ = (Cursor) builder.buildPartial();
                                }
                            } else if (readTag == 66) {
                                builder = this.endAt_ != null ? (com.google.firestore.v1.Cursor.Builder) this.endAt_.toBuilder() : null;
                                this.endAt_ = (Cursor) codedInputStream.readMessage(Cursor.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.endAt_);
                                    this.endAt_ = (Cursor) builder.buildPartial();
                                }
                            } else if (codedInputStream.skipField(readTag)) {
                            }
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e.setUnfinishedMessage(this));
                    } catch (IOException e2) {
                        throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                    }
                }
                break;
            case GET_DEFAULT_INSTANCE:
                break;
            case GET_PARSER:
                if (PARSER == null) {
                    synchronized (StructuredQuery.class) {
                        if (PARSER == null) {
                            PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                        }
                    }
                }
                return PARSER;
            default:
                throw new UnsupportedOperationException();
        }
        return DEFAULT_INSTANCE;
    }

    static {
        DEFAULT_INSTANCE.makeImmutable();
    }

    public static StructuredQuery getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<StructuredQuery> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
