package com.google.firebase.ml.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.vision.text.Text;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseVisionText {
    private final String text;
    private final List<TextBlock> zzazm = new ArrayList();

    static class TextBase {
        private final Float confidence;
        private final Point[] cornerPoints;
        private final String text;
        private final Rect zzawz;
        private final List<RecognizedLanguage> zzaxs;

        TextBase(@NonNull Text text) {
            Preconditions.checkNotNull(text, "Text to construct FirebaseVisionText classes can't be null");
            this.confidence = null;
            this.text = text.getValue();
            this.zzawz = text.getBoundingBox();
            this.cornerPoints = text.getCornerPoints();
            this.zzaxs = Collections.emptyList();
        }

        private TextBase(@NonNull String str, @Nullable Rect rect, @NonNull List<RecognizedLanguage> list, @Nullable Float f) {
            Preconditions.checkNotNull(str, "Text string cannot be null");
            Preconditions.checkNotNull(list, "Text languages cannot be null");
            this.confidence = f;
            this.cornerPoints = null;
            this.text = str;
            this.zzawz = rect;
            this.zzaxs = list;
        }

        @Nullable
        public Rect getBoundingBox() {
            return this.zzawz;
        }

        @Nullable
        public Point[] getCornerPoints() {
            return this.cornerPoints;
        }

        public String getText() {
            String str = this.text;
            return str == null ? "" : str;
        }

        @Nullable
        public Float getConfidence() {
            return this.confidence;
        }

        public List<RecognizedLanguage> getRecognizedLanguages() {
            return this.zzaxs;
        }

        /* synthetic */ TextBase(String str, Rect rect, List list, Float f, zzb zzb) {
            this(str, rect, list, f);
        }
    }

    public static class Element extends TextBase {
        Element(@NonNull com.google.android.gms.vision.text.Element element) {
            super(element);
        }

        public Element(@NonNull String str, @Nullable Rect rect, @NonNull List<RecognizedLanguage> list, @Nullable Float f) {
            super(str, rect, list, f, null);
        }
    }

    public static class Line extends TextBase {
        @GuardedBy("this")
        private final List<Element> zzazn;

        Line(@NonNull com.google.android.gms.vision.text.Line line) {
            super(line);
            this.zzazn = new ArrayList();
            for (Text text : line.getComponents()) {
                if (text instanceof com.google.android.gms.vision.text.Element) {
                    this.zzazn.add(new Element((com.google.android.gms.vision.text.Element) text));
                } else {
                    Log.e("FirebaseVisionText", "A subcomponent of line is should be an element!");
                }
            }
        }

        public Line(@NonNull String str, @Nullable Rect rect, @NonNull List<RecognizedLanguage> list, @NonNull List<Element> list2, @Nullable Float f) {
            super(str, rect, list, f, null);
            this.zzazn = list2;
        }

        public synchronized List<Element> getElements() {
            return this.zzazn;
        }
    }

    public static class TextBlock extends TextBase {
        @GuardedBy("this")
        private final List<Line> zzazo;

        TextBlock(@NonNull com.google.android.gms.vision.text.TextBlock textBlock) {
            super(textBlock);
            this.zzazo = new ArrayList();
            for (Text text : textBlock.getComponents()) {
                if (text instanceof com.google.android.gms.vision.text.Line) {
                    this.zzazo.add(new Line((com.google.android.gms.vision.text.Line) text));
                } else {
                    Log.e("FirebaseVisionText", "A subcomponent of textblock is should be a line!");
                }
            }
        }

        public TextBlock(@NonNull String str, @Nullable Rect rect, @NonNull List<RecognizedLanguage> list, @NonNull List<Line> list2, @Nullable Float f) {
            super(str, rect, list, f, null);
            this.zzazo = list2;
        }

        public synchronized List<Line> getLines() {
            return this.zzazo;
        }
    }

    public List<TextBlock> getTextBlocks() {
        return Collections.unmodifiableList(this.zzazm);
    }

    public String getText() {
        return this.text;
    }

    public FirebaseVisionText(@NonNull SparseArray<com.google.android.gms.vision.text.TextBlock> sparseArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sparseArray.size(); i++) {
            com.google.android.gms.vision.text.TextBlock textBlock = (com.google.android.gms.vision.text.TextBlock) sparseArray.get(sparseArray.keyAt(i));
            if (textBlock != null) {
                TextBase textBlock2 = new TextBlock(textBlock);
                this.zzazm.add(textBlock2);
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
                }
                if (textBlock.getValue() != null) {
                    stringBuilder.append(textBlock2.getText());
                }
            }
        }
        this.text = stringBuilder.toString();
    }

    public FirebaseVisionText(@NonNull String str, @NonNull List<TextBlock> list) {
        this.text = str;
        this.zzazm.addAll(list);
    }
}
