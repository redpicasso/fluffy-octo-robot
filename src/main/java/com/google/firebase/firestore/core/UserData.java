package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.BasePath;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.FieldTransform;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.model.mutation.SetMutation;
import com.google.firebase.firestore.model.mutation.TransformMutation;
import com.google.firebase.firestore.model.mutation.TransformOperation;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class UserData {

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    /* renamed from: com.google.firebase.firestore.core.UserData$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$UserData$Source = new int[Source.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:8:?, code:
            $SwitchMap$com$google$firebase$firestore$core$UserData$Source[com.google.firebase.firestore.core.UserData.Source.Argument.ordinal()] = 4;
     */
        static {
            /*
            r0 = com.google.firebase.firestore.core.UserData.Source.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$firebase$firestore$core$UserData$Source = r0;
            r0 = $SwitchMap$com$google$firebase$firestore$core$UserData$Source;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.firebase.firestore.core.UserData.Source.Set;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$firebase$firestore$core$UserData$Source;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.firebase.firestore.core.UserData.Source.MergeSet;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$google$firebase$firestore$core$UserData$Source;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.google.firebase.firestore.core.UserData.Source.Update;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            r0 = $SwitchMap$com$google$firebase$firestore$core$UserData$Source;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = com.google.firebase.firestore.core.UserData.Source.Argument;	 Catch:{ NoSuchFieldError -> 0x0035 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0035 }
            r2 = 4;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0035 }
        L_0x0035:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.core.UserData.1.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class ParseAccumulator {
        private final Source dataSource;
        private final Set<FieldPath> fieldMask = new HashSet();
        private final ArrayList<FieldTransform> fieldTransforms = new ArrayList();

        public ParseAccumulator(Source source) {
            this.dataSource = source;
        }

        public Source getDataSource() {
            return this.dataSource;
        }

        public List<FieldTransform> getFieldTransforms() {
            return this.fieldTransforms;
        }

        public ParseContext rootContext() {
            return new ParseContext(this, FieldPath.EMPTY_PATH, false, null);
        }

        public boolean contains(FieldPath fieldPath) {
            for (FieldPath isPrefixOf : this.fieldMask) {
                if (fieldPath.isPrefixOf(isPrefixOf)) {
                    return true;
                }
            }
            Iterator it = this.fieldTransforms.iterator();
            while (it.hasNext()) {
                if (fieldPath.isPrefixOf(((FieldTransform) it.next()).getFieldPath())) {
                    return true;
                }
            }
            return false;
        }

        void addToFieldMask(FieldPath fieldPath) {
            this.fieldMask.add(fieldPath);
        }

        void addToFieldTransforms(FieldPath fieldPath, TransformOperation transformOperation) {
            this.fieldTransforms.add(new FieldTransform(fieldPath, transformOperation));
        }

        public ParsedSetData toMergeData(ObjectValue objectValue) {
            return new ParsedSetData(objectValue, FieldMask.fromSet(this.fieldMask), Collections.unmodifiableList(this.fieldTransforms));
        }

        public ParsedSetData toMergeData(ObjectValue objectValue, FieldMask fieldMask) {
            List arrayList = new ArrayList();
            Iterator it = this.fieldTransforms.iterator();
            while (it.hasNext()) {
                FieldTransform fieldTransform = (FieldTransform) it.next();
                if (fieldMask.covers(fieldTransform.getFieldPath())) {
                    arrayList.add(fieldTransform);
                }
            }
            return new ParsedSetData(objectValue, fieldMask, Collections.unmodifiableList(arrayList));
        }

        public ParsedSetData toSetData(ObjectValue objectValue) {
            return new ParsedSetData(objectValue, null, Collections.unmodifiableList(this.fieldTransforms));
        }

        public ParsedUpdateData toUpdateData(ObjectValue objectValue) {
            return new ParsedUpdateData(objectValue, FieldMask.fromSet(this.fieldMask), Collections.unmodifiableList(this.fieldTransforms));
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class ParseContext {
        private final ParseAccumulator accumulator;
        private final boolean arrayElement;
        @Nullable
        private final FieldPath path;
        private final Pattern reservedFieldRegex;

        /* synthetic */ ParseContext(ParseAccumulator parseAccumulator, FieldPath fieldPath, boolean z, AnonymousClass1 anonymousClass1) {
            this(parseAccumulator, fieldPath, z);
        }

        private ParseContext(ParseAccumulator parseAccumulator, @Nullable FieldPath fieldPath, boolean z) {
            this.reservedFieldRegex = Pattern.compile("^__.*__$");
            this.accumulator = parseAccumulator;
            this.path = fieldPath;
            this.arrayElement = z;
        }

        public boolean isArrayElement() {
            return this.arrayElement;
        }

        public Source getDataSource() {
            return this.accumulator.dataSource;
        }

        public FieldPath getPath() {
            return this.path;
        }

        public boolean isWrite() {
            int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$core$UserData$Source[this.accumulator.dataSource.ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                return true;
            }
            if (i == 4) {
                return false;
            }
            throw Assert.fail("Unexpected case for UserDataSource: %s", this.accumulator.dataSource.name());
        }

        public ParseContext childContext(String str) {
            FieldPath fieldPath = this.path;
            ParseContext parseContext = new ParseContext(this.accumulator, fieldPath == null ? null : (FieldPath) fieldPath.append(str), false);
            parseContext.validatePathSegment(str);
            return parseContext;
        }

        public ParseContext childContext(FieldPath fieldPath) {
            FieldPath fieldPath2 = this.path;
            ParseContext parseContext = new ParseContext(this.accumulator, fieldPath2 == null ? null : (FieldPath) fieldPath2.append((BasePath) fieldPath), false);
            parseContext.validatePath();
            return parseContext;
        }

        public ParseContext childContext(int i) {
            return new ParseContext(this.accumulator, null, true);
        }

        public void addToFieldMask(FieldPath fieldPath) {
            this.accumulator.addToFieldMask(fieldPath);
        }

        public void addToFieldTransforms(FieldPath fieldPath, TransformOperation transformOperation) {
            this.accumulator.addToFieldTransforms(fieldPath, transformOperation);
        }

        public RuntimeException createError(String str) {
            String str2;
            FieldPath fieldPath = this.path;
            if (fieldPath == null || fieldPath.isEmpty()) {
                str2 = "";
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" (found in field ");
                stringBuilder.append(this.path.toString());
                stringBuilder.append(")");
                str2 = stringBuilder.toString();
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Invalid data. ");
            stringBuilder2.append(str);
            stringBuilder2.append(str2);
            return new IllegalArgumentException(stringBuilder2.toString());
        }

        private void validatePath() {
            if (this.path != null) {
                for (int i = 0; i < this.path.length(); i++) {
                    validatePathSegment(this.path.getSegment(i));
                }
            }
        }

        private void validatePathSegment(String str) {
            if (isWrite() && this.reservedFieldRegex.matcher(str).find()) {
                throw createError("Document fields cannot begin and end with __");
            }
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class ParsedSetData {
        private final ObjectValue data;
        @Nullable
        private final FieldMask fieldMask;
        private final List<FieldTransform> fieldTransforms;

        ParsedSetData(ObjectValue objectValue, @Nullable FieldMask fieldMask, List<FieldTransform> list) {
            this.data = objectValue;
            this.fieldMask = fieldMask;
            this.fieldTransforms = list;
        }

        public List<Mutation> toMutationList(DocumentKey documentKey, Precondition precondition) {
            List arrayList = new ArrayList();
            FieldMask fieldMask = this.fieldMask;
            if (fieldMask != null) {
                arrayList.add(new PatchMutation(documentKey, this.data, fieldMask, precondition));
            } else {
                arrayList.add(new SetMutation(documentKey, this.data, precondition));
            }
            if (!this.fieldTransforms.isEmpty()) {
                arrayList.add(new TransformMutation(documentKey, this.fieldTransforms));
            }
            return arrayList;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class ParsedUpdateData {
        private final ObjectValue data;
        private final FieldMask fieldMask;
        private final List<FieldTransform> fieldTransforms;

        ParsedUpdateData(ObjectValue objectValue, FieldMask fieldMask, List<FieldTransform> list) {
            this.data = objectValue;
            this.fieldMask = fieldMask;
            this.fieldTransforms = list;
        }

        public List<FieldTransform> getFieldTransforms() {
            return this.fieldTransforms;
        }

        public List<Mutation> toMutationList(DocumentKey documentKey, Precondition precondition) {
            List arrayList = new ArrayList();
            arrayList.add(new PatchMutation(documentKey, this.data, this.fieldMask, precondition));
            if (!this.fieldTransforms.isEmpty()) {
                arrayList.add(new TransformMutation(documentKey, this.fieldTransforms));
            }
            return arrayList;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Source {
        Set,
        MergeSet,
        Update,
        Argument
    }

    private UserData() {
    }
}
