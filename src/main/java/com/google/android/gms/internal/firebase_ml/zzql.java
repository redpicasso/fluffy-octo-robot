package com.google.android.gms.internal.firebase_ml;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionText.Element;
import com.google.firebase.ml.vision.text.FirebaseVisionText.Line;
import com.google.firebase.ml.vision.text.FirebaseVisionText.TextBlock;
import com.google.firebase.ml.vision.text.RecognizedLanguage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class zzql {
    private static final GmsLogger zzaoz = new GmsLogger("TextAnnotationConverter", "");

    @Nullable
    static FirebaseVisionText zzb(@NonNull zzjs zzjs, float f) {
        float f2 = f;
        Preconditions.checkNotNull(zzjs, "The input TextAnnotation can not be null");
        String str = "TextAnnotationConverter";
        FirebaseVisionText firebaseVisionText = null;
        if (zzjs.getPages().size() <= 0) {
            zzaoz.d(str, "Text Annotation is null, return null");
            return null;
        }
        if (zzjs.getPages().size() > 1) {
            zzaoz.d(str, "Text Annotation has more than one page, which should not happen");
        }
        List arrayList = new ArrayList();
        Iterator it = zzjs.getPages().iterator();
        while (it.hasNext()) {
            zzjs zzjs2;
            Iterator it2 = ((zzjl) it.next()).getBlocks().iterator();
            while (it2.hasNext()) {
                Iterator it3;
                Object obj;
                Iterator it4;
                zziu zziu = (zziu) it2.next();
                Preconditions.checkNotNull(zziu, "Input block can not be null");
                List arrayList2 = new ArrayList();
                if (zziu.getParagraphs() == null) {
                    it3 = it;
                    obj = firebaseVisionText;
                    it4 = it2;
                } else {
                    ArrayList arrayList3;
                    int size;
                    Iterator it5 = zziu.getParagraphs().iterator();
                    while (it5.hasNext()) {
                        zzjm zzjm = (zzjm) it5.next();
                        if (zzjm != null) {
                            Iterator it6;
                            Preconditions.checkNotNull(zzjm, "Input Paragraph can not be null");
                            Collection arrayList4 = new ArrayList();
                            ArrayList arrayList5 = new ArrayList();
                            Set<RecognizedLanguage> hashSet = new HashSet();
                            StringBuilder stringBuilder = new StringBuilder();
                            float f3 = 0.0f;
                            List list = arrayList5;
                            int i = 0;
                            while (i < zzjm.getWords().size()) {
                                zzjx zzjx = (zzjx) zzjm.getWords().get(i);
                                if (zzjx != null) {
                                    Element element;
                                    String str2 = "Input Word can not be null";
                                    Preconditions.checkNotNull(zzjx, str2);
                                    Rect zza = zzpm.zza(zzjx.zzhq(), f2);
                                    it3 = it;
                                    List zze = zze(zzjx.zzhr());
                                    Preconditions.checkNotNull(zzjx, str2);
                                    String str3 = "";
                                    if (zzjx.getSymbols() == null) {
                                        it4 = it2;
                                        str2 = str3;
                                    } else {
                                        StringBuilder stringBuilder2 = new StringBuilder();
                                        for (zzjr text : zzjx.getSymbols()) {
                                            it4 = it2;
                                            stringBuilder2.append(text.getText());
                                            it2 = it4;
                                        }
                                        it4 = it2;
                                        str2 = stringBuilder2.toString();
                                    }
                                    if (str2.isEmpty()) {
                                        it6 = it5;
                                        element = null;
                                    } else {
                                        it6 = it5;
                                        element = new Element(str2, zza, zze, zzjx.getConfidence());
                                    }
                                    if (element != null) {
                                        list.add(element);
                                        float zza2 = f3 + zzpm.zza(element.getConfidence());
                                        hashSet.addAll(element.getRecognizedLanguages());
                                        stringBuilder.append(element.getText());
                                        str = "Input word can not be null";
                                        Preconditions.checkNotNull(zzjx, str);
                                        str2 = zza(zzjx);
                                        String str4 = "HYPHEN";
                                        if (str2 != null) {
                                            if (str2.equals("SPACE") || str2.equals("SURE_SPACE")) {
                                                str3 = " ";
                                            } else if (str2.equals(str4)) {
                                                str3 = "-";
                                            }
                                        }
                                        stringBuilder.append(str3);
                                        Preconditions.checkNotNull(zzjx, str);
                                        str = zza(zzjx);
                                        obj = (str == null || !(str.equals("EOL_SURE_SPACE") || str.equals("LINE_BREAK") || str.equals(str4))) ? null : 1;
                                        if (obj == null) {
                                            if (i != zzjm.getWords().size() - 1) {
                                                f3 = zza2;
                                            }
                                        }
                                        Preconditions.checkNotNull(list, "Input elements can not be null");
                                        arrayList3 = (ArrayList) list;
                                        size = arrayList3.size();
                                        int i2 = 0;
                                        Rect rect = null;
                                        while (i2 < size) {
                                            Object obj2 = arrayList3.get(i2);
                                            i2++;
                                            Element element2 = (Element) obj2;
                                            if (element2.getBoundingBox() != null) {
                                                if (rect == null) {
                                                    rect = new Rect();
                                                }
                                                Rect rect2 = rect;
                                                rect2.union(element2.getBoundingBox());
                                                rect = rect2;
                                            }
                                        }
                                        str3 = stringBuilder.toString();
                                        List arrayList6 = new ArrayList();
                                        for (RecognizedLanguage recognizedLanguage : hashSet) {
                                            if (!(recognizedLanguage == null || recognizedLanguage.getLanguageCode() == null || recognizedLanguage.getLanguageCode().isEmpty())) {
                                                arrayList6.add(recognizedLanguage);
                                            }
                                        }
                                        f3 = new Line(str3, rect, arrayList6, list, Float.compare(zza2, 0.0f) > 0 ? Float.valueOf(zza2 / ((float) list.size())) : null);
                                        arrayList4.add(f3);
                                        ArrayList arrayList7 = new ArrayList();
                                        hashSet.clear();
                                        list = arrayList7;
                                        stringBuilder = new StringBuilder();
                                    }
                                } else {
                                    it3 = it;
                                    it4 = it2;
                                    it6 = it5;
                                }
                                i++;
                                zzjs2 = zzjs;
                                it = it3;
                                it2 = it4;
                                it5 = it6;
                            }
                            it3 = it;
                            it4 = it2;
                            it6 = it5;
                            arrayList2.addAll(arrayList4);
                        }
                        zzjs2 = zzjs;
                    }
                    it3 = it;
                    it4 = it2;
                    if (arrayList2.isEmpty()) {
                        obj = null;
                    } else {
                        StringBuilder stringBuilder3 = new StringBuilder();
                        arrayList3 = (ArrayList) arrayList2;
                        int size2 = arrayList3.size();
                        size = 0;
                        while (size < size2) {
                            Object obj3 = arrayList3.get(size);
                            size++;
                            stringBuilder3.append(((Line) obj3).getText());
                            stringBuilder3.append(ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE);
                        }
                        TextBlock textBlock = new TextBlock(stringBuilder3.toString(), zzpm.zza(zziu.zzhq(), f2), zze(zziu.zzhr()), arrayList2, zziu.getConfidence());
                    }
                }
                if (obj != null) {
                    arrayList.add(obj);
                }
                firebaseVisionText = null;
                zzjs2 = zzjs;
                it = it3;
                it2 = it4;
            }
            zzjs2 = zzjs;
        }
        return new FirebaseVisionText(zzjs.getText(), arrayList);
    }

    public static List<RecognizedLanguage> zze(@Nullable zzjt zzjt) {
        List<RecognizedLanguage> arrayList = new ArrayList();
        if (!(zzjt == null || zzjt.zzia() == null)) {
            for (zziz zza : zzjt.zzia()) {
                RecognizedLanguage zza2 = RecognizedLanguage.zza(zza);
                if (zza2 != null) {
                    arrayList.add(zza2);
                }
            }
        }
        return arrayList;
    }

    @VisibleForTesting
    @Nullable
    private static String zza(@NonNull zzjx zzjx) {
        Preconditions.checkNotNull(zzjx, "Input Word can not be null");
        if (zzjx.getSymbols() != null && zzjx.getSymbols().size() > 0) {
            zzjr zzjr = (zzjr) zzjx.getSymbols().get(zzjx.getSymbols().size() - 1);
            if (!(zzjr.zzhr() == null || zzjr.zzhr().zzhz() == null)) {
                return ((zzjr) zzjx.getSymbols().get(zzjx.getSymbols().size() - 1)).zzhr().zzhz().getType();
            }
        }
        return null;
    }
}
