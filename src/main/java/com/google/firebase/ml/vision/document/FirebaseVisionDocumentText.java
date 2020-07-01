package com.google.firebase.ml.vision.document;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.internal.firebase_ml.zziu;
import com.google.android.gms.internal.firebase_ml.zziz;
import com.google.android.gms.internal.firebase_ml.zzjl;
import com.google.android.gms.internal.firebase_ml.zzjm;
import com.google.android.gms.internal.firebase_ml.zzjr;
import com.google.android.gms.internal.firebase_ml.zzjs;
import com.google.android.gms.internal.firebase_ml.zzjt;
import com.google.android.gms.internal.firebase_ml.zzjx;
import com.google.android.gms.internal.firebase_ml.zzpm;
import com.google.android.gms.internal.firebase_ml.zzql;
import com.google.firebase.ml.vision.text.RecognizedLanguage;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseVisionDocumentText {
    private final List<Block> blocks;
    private final String text;

    static class DocumentTextBase {
        private final Float confidence;
        private final String text;
        private final Rect zzawz;
        private final List<RecognizedLanguage> zzaxs;
        private final RecognizedBreak zzaxt;

        DocumentTextBase(@NonNull List<RecognizedLanguage> list, @Nullable RecognizedBreak recognizedBreak, @Nullable Rect rect, @NonNull String str, @Nullable Float f) {
            this.text = str;
            this.zzaxs = list;
            this.zzaxt = recognizedBreak;
            this.zzawz = rect;
            this.confidence = f;
        }

        public String getText() {
            return this.text;
        }

        @Nullable
        public Rect getBoundingBox() {
            return this.zzawz;
        }

        public List<RecognizedLanguage> getRecognizedLanguages() {
            return this.zzaxs;
        }

        @Nullable
        public RecognizedBreak getRecognizedBreak() {
            return this.zzaxt;
        }

        @Nullable
        public Float getConfidence() {
            return this.confidence;
        }
    }

    public static class RecognizedBreak {
        public static final int EOL_SURE_SPACE = 3;
        public static final int HYPHEN = 4;
        public static final int LINE_BREAK = 5;
        public static final int SPACE = 1;
        public static final int SURE_SPACE = 2;
        public static final int UNKNOWN = 0;
        private final int type;
        private final boolean zzaxu;

        @Retention(RetentionPolicy.SOURCE)
        public @interface BreakType {
        }

        public int getDetectedBreakType() {
            return this.type;
        }

        public boolean getIsPrefix() {
            return this.zzaxu;
        }

        private RecognizedBreak(int i, boolean z) {
            this.type = i;
            this.zzaxu = z;
        }

        /* JADX WARNING: Removed duplicated region for block: B:34:0x0079  */
        @androidx.annotation.Nullable
        private static com.google.firebase.ml.vision.document.FirebaseVisionDocumentText.RecognizedBreak zzc(@androidx.annotation.Nullable com.google.android.gms.internal.firebase_ml.zzjt r8) {
            /*
            if (r8 == 0) goto L_0x008b;
        L_0x0002:
            r0 = r8.zzhz();
            if (r0 != 0) goto L_0x000a;
        L_0x0008:
            goto L_0x008b;
        L_0x000a:
            r0 = r8.zzhz();
            r0 = r0.getType();
            r1 = 4;
            r2 = 3;
            r3 = 2;
            r4 = 1;
            r5 = 0;
            if (r0 == 0) goto L_0x006e;
        L_0x0019:
            r0 = r8.zzhz();
            r0 = r0.getType();
            r6 = -1;
            r7 = r0.hashCode();
            switch(r7) {
                case -1651884996: goto L_0x0052;
                case -1571028039: goto L_0x0048;
                case 79100134: goto L_0x003e;
                case 1541383380: goto L_0x0034;
                case 2145946930: goto L_0x002a;
                default: goto L_0x0029;
            };
        L_0x0029:
            goto L_0x005b;
        L_0x002a:
            r7 = "HYPHEN";
            r0 = r0.equals(r7);
            if (r0 == 0) goto L_0x005b;
        L_0x0032:
            r6 = 3;
            goto L_0x005b;
        L_0x0034:
            r7 = "LINE_BREAK";
            r0 = r0.equals(r7);
            if (r0 == 0) goto L_0x005b;
        L_0x003c:
            r6 = 4;
            goto L_0x005b;
        L_0x003e:
            r7 = "SPACE";
            r0 = r0.equals(r7);
            if (r0 == 0) goto L_0x005b;
        L_0x0046:
            r6 = 0;
            goto L_0x005b;
        L_0x0048:
            r7 = "EOL_SURE_SPACE";
            r0 = r0.equals(r7);
            if (r0 == 0) goto L_0x005b;
        L_0x0050:
            r6 = 2;
            goto L_0x005b;
        L_0x0052:
            r7 = "SURE_SPACE";
            r0 = r0.equals(r7);
            if (r0 == 0) goto L_0x005b;
        L_0x005a:
            r6 = 1;
        L_0x005b:
            if (r6 == 0) goto L_0x006c;
        L_0x005d:
            if (r6 == r4) goto L_0x006a;
        L_0x005f:
            if (r6 == r3) goto L_0x0068;
        L_0x0061:
            if (r6 == r2) goto L_0x006f;
        L_0x0063:
            if (r6 == r1) goto L_0x0066;
        L_0x0065:
            goto L_0x006e;
        L_0x0066:
            r1 = 5;
            goto L_0x006f;
        L_0x0068:
            r1 = 3;
            goto L_0x006f;
        L_0x006a:
            r1 = 2;
            goto L_0x006f;
        L_0x006c:
            r1 = 1;
            goto L_0x006f;
        L_0x006e:
            r1 = 0;
        L_0x006f:
            r0 = r8.zzhz();
            r0 = r0.zzht();
            if (r0 == 0) goto L_0x0085;
        L_0x0079:
            r8 = r8.zzhz();
            r8 = r8.zzht();
            r5 = r8.booleanValue();
        L_0x0085:
            r8 = new com.google.firebase.ml.vision.document.FirebaseVisionDocumentText$RecognizedBreak;
            r8.<init>(r1, r5);
            return r8;
        L_0x008b:
            r8 = 0;
            return r8;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.ml.vision.document.FirebaseVisionDocumentText.RecognizedBreak.zzc(com.google.android.gms.internal.firebase_ml.zzjt):com.google.firebase.ml.vision.document.FirebaseVisionDocumentText$RecognizedBreak");
        }

        /* synthetic */ RecognizedBreak(int i, boolean z, zzb zzb) {
            this(5, false);
        }
    }

    public static class Block extends DocumentTextBase {
        private final List<Paragraph> paragraphs;

        public List<Paragraph> getParagraphs() {
            return this.paragraphs;
        }

        private Block(@NonNull List<RecognizedLanguage> list, @Nullable RecognizedBreak recognizedBreak, @Nullable Rect rect, @NonNull List<Paragraph> list2, @NonNull String str, Float f) {
            super(list, recognizedBreak, rect, str, f);
            this.paragraphs = list2;
        }

        private static Block zza(@NonNull zziu zziu, float f) {
            List arrayList = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            List zze = zzql.zze(zziu.zzhr());
            if (zziu.getParagraphs() != null) {
                for (zzjm zzjm : zziu.getParagraphs()) {
                    if (zzjm != null) {
                        DocumentTextBase zzb = Paragraph.zza(zzjm, f);
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
                        }
                        stringBuilder.append(zzb.getText());
                        arrayList.add(zzb);
                    }
                }
            }
            return new Block(zze, new RecognizedBreak(5, false, null), zzpm.zza(zziu.zzhq(), f), arrayList, stringBuilder.toString(), zziu.getConfidence());
        }
    }

    public static class Paragraph extends DocumentTextBase {
        private final List<Word> words;

        public List<Word> getWords() {
            return this.words;
        }

        private Paragraph(@NonNull List<RecognizedLanguage> list, @Nullable RecognizedBreak recognizedBreak, @Nullable Rect rect, @NonNull List<Word> list2, @NonNull String str, @Nullable Float f) {
            super(list, recognizedBreak, rect, str, f);
            this.words = list2;
        }

        private static Paragraph zza(@NonNull zzjm zzjm, float f) {
            List arrayList = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            List zze = zzql.zze(zzjm.zzhr());
            if (zzjm.getWords() != null) {
                for (zzjx zzjx : zzjm.getWords()) {
                    if (zzjx != null) {
                        DocumentTextBase zzb = Word.zza(zzjx, f);
                        stringBuilder.append(zzb.getText());
                        stringBuilder.append(FirebaseVisionDocumentText.zza(zzb.getRecognizedBreak()));
                        arrayList.add(zzb);
                    }
                }
            }
            return new Paragraph(zze, new RecognizedBreak(5, false, null), zzpm.zza(zzjm.zzhq(), f), arrayList, stringBuilder.toString(), zzjm.getConfidence());
        }
    }

    public static class Symbol extends DocumentTextBase {
        private Symbol(@NonNull List<RecognizedLanguage> list, @Nullable RecognizedBreak recognizedBreak, @Nullable Rect rect, @NonNull String str, Float f) {
            super(list, recognizedBreak, rect, str, f);
        }

        private static Symbol zza(@NonNull zzjr zzjr, float f) {
            return new Symbol(FirebaseVisionDocumentText.zza(zzjr.zzhr()), RecognizedBreak.zzc(zzjr.zzhr()), zzpm.zza(zzjr.zzhq(), f), zzpm.zzch(zzjr.getText()), zzjr.getConfidence());
        }
    }

    public static class Word extends DocumentTextBase {
        private final List<Symbol> symbols;

        public List<Symbol> getSymbols() {
            return this.symbols;
        }

        private Word(@NonNull List<RecognizedLanguage> list, @Nullable RecognizedBreak recognizedBreak, @Nullable Rect rect, @NonNull List<Symbol> list2, @NonNull String str, @Nullable Float f) {
            super(list, recognizedBreak, rect, str, f);
            this.symbols = list2;
        }

        private static Word zza(@NonNull zzjx zzjx, float f) {
            List arrayList = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            List zze = zzql.zze(zzjx.zzhr());
            RecognizedBreak recognizedBreak = null;
            if (zzjx.getSymbols() != null) {
                for (zzjr zzjr : zzjx.getSymbols()) {
                    if (zzjr != null) {
                        DocumentTextBase zzb = Symbol.zza(zzjr, f);
                        RecognizedBreak recognizedBreak2 = zzb.getRecognizedBreak();
                        stringBuilder.append(zzb.getText());
                        arrayList.add(Symbol.zza(zzjr, f));
                        recognizedBreak = recognizedBreak2;
                    }
                }
            }
            return new Word(zze, recognizedBreak, zzpm.zza(zzjx.zzhq(), f), arrayList, stringBuilder.toString(), zzjx.getConfidence());
        }
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public String getText() {
        String str = this.text;
        return str != null ? str : "";
    }

    private FirebaseVisionDocumentText(@NonNull String str, @NonNull List<Block> list) {
        this.text = str;
        this.blocks = list;
    }

    @Nullable
    public static FirebaseVisionDocumentText zza(@Nullable zzjs zzjs, float f) {
        if (zzjs == null) {
            return null;
        }
        String zzch = zzpm.zzch(zzjs.getText());
        List arrayList = new ArrayList();
        if (zzjs.getPages() != null) {
            for (zzjl zzjl : zzjs.getPages()) {
                if (zzjl != null) {
                    for (zziu zziu : zzjl.getBlocks()) {
                        if (zziu != null) {
                            arrayList.add(Block.zza(zziu, f));
                        }
                    }
                }
            }
        }
        return new FirebaseVisionDocumentText(zzch, arrayList);
    }

    private static List<RecognizedLanguage> zza(@Nullable zzjt zzjt) {
        if (zzjt == null) {
            return Collections.emptyList();
        }
        List<RecognizedLanguage> arrayList = new ArrayList();
        if (zzjt.zzia() != null) {
            for (zziz zza : zzjt.zzia()) {
                RecognizedLanguage zza2 = RecognizedLanguage.zza(zza);
                if (zza2 != null) {
                    arrayList.add(zza2);
                }
            }
        }
        return arrayList;
    }

    /* JADX WARNING: Missing block: B:12:0x0016, code:
            if (r2 != 5) goto L_0x0021;
     */
    private static java.lang.String zza(@androidx.annotation.Nullable com.google.firebase.ml.vision.document.FirebaseVisionDocumentText.RecognizedBreak r2) {
        /*
        r0 = "";
        if (r2 != 0) goto L_0x0005;
    L_0x0004:
        return r0;
    L_0x0005:
        r2 = r2.getDetectedBreakType();
        r1 = 1;
        if (r2 == r1) goto L_0x001f;
    L_0x000c:
        r1 = 2;
        if (r2 == r1) goto L_0x001f;
    L_0x000f:
        r1 = 3;
        if (r2 == r1) goto L_0x001c;
    L_0x0012:
        r1 = 4;
        if (r2 == r1) goto L_0x0019;
    L_0x0015:
        r1 = 5;
        if (r2 == r1) goto L_0x001c;
    L_0x0018:
        goto L_0x0021;
    L_0x0019:
        r0 = "-\n";
        goto L_0x0021;
    L_0x001c:
        r0 = "\n";
        goto L_0x0021;
    L_0x001f:
        r0 = " ";
    L_0x0021:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.ml.vision.document.FirebaseVisionDocumentText.zza(com.google.firebase.ml.vision.document.FirebaseVisionDocumentText$RecognizedBreak):java.lang.String");
    }
}
