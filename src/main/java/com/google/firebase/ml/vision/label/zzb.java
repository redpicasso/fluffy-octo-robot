package com.google.firebase.ml.vision.label;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.LinkedList;
import java.util.List;

final class zzb implements Continuation<List<FirebaseVisionImageLabel>, List<FirebaseVisionImageLabel>> {
    private final /* synthetic */ FirebaseVisionImageLabeler zzayz;

    zzb(FirebaseVisionImageLabeler firebaseVisionImageLabeler) {
        this.zzayz = firebaseVisionImageLabeler;
    }

    public final /* synthetic */ Object then(@NonNull Task task) throws Exception {
        List<FirebaseVisionImageLabel> list = (List) task.getResult();
        List linkedList = new LinkedList();
        for (FirebaseVisionImageLabel firebaseVisionImageLabel : list) {
            if (Float.compare(firebaseVisionImageLabel.getConfidence(), this.zzayz.zzayu.getConfidenceThreshold()) >= 0) {
                linkedList.add(firebaseVisionImageLabel);
            }
        }
        return linkedList;
    }
}
