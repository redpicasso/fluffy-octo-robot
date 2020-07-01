package com.google.firebase.database.core.utilities;

import com.google.firebase.database.snapshot.ChildKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class TreeNode<T> {
    public Map<ChildKey, TreeNode<T>> children = new HashMap();
    public T value;

    String toString(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("<value>: ");
        stringBuilder.append(this.value);
        String str2 = ReactEditTextInputConnectionWrapper.NEWLINE_RAW_VALUE;
        stringBuilder.append(str2);
        String stringBuilder2 = stringBuilder.toString();
        if (this.children.isEmpty()) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(stringBuilder2);
            stringBuilder3.append(str);
            stringBuilder3.append("<empty>");
            return stringBuilder3.toString();
        }
        for (Entry entry : this.children.entrySet()) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(stringBuilder2);
            stringBuilder4.append(str);
            stringBuilder4.append(entry.getKey());
            stringBuilder4.append(":\n");
            TreeNode treeNode = (TreeNode) entry.getValue();
            StringBuilder stringBuilder5 = new StringBuilder();
            stringBuilder5.append(str);
            stringBuilder5.append("\t");
            stringBuilder4.append(treeNode.toString(stringBuilder5.toString()));
            stringBuilder4.append(str2);
            stringBuilder2 = stringBuilder4.toString();
        }
        return stringBuilder2;
    }
}
