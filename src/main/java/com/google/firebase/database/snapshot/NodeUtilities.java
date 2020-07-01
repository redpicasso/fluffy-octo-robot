package com.google.firebase.database.snapshot;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.core.ServerValues;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class NodeUtilities {
    public static Node NodeFromJSON(Object obj) throws DatabaseException {
        return NodeFromJSON(obj, PriorityUtilities.NullPriority());
    }

    public static Node NodeFromJSON(Object obj, Node node) throws DatabaseException {
        String str = ".value";
        String str2 = ".priority";
        try {
            if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.containsKey(str2)) {
                    node = PriorityUtilities.parsePriority(map.get(str2));
                }
                if (map.containsKey(str)) {
                    obj = map.get(str);
                }
            }
            if (obj == null) {
                return EmptyNode.Empty();
            }
            if (obj instanceof String) {
                return new StringNode((String) obj, node);
            }
            if (obj instanceof Long) {
                return new LongNode((Long) obj, node);
            }
            if (obj instanceof Integer) {
                return new LongNode(Long.valueOf((long) ((Integer) obj).intValue()), node);
            }
            if (obj instanceof Double) {
                return new DoubleNode((Double) obj, node);
            }
            if (obj instanceof Boolean) {
                return new BooleanNode((Boolean) obj, node);
            }
            if ((obj instanceof Map) || (obj instanceof List)) {
                Map hashMap;
                String stringBuilder;
                Node NodeFromJSON;
                if (obj instanceof Map) {
                    Map map2 = (Map) obj;
                    if (map2.containsKey(ServerValues.NAME_SUBKEY_SERVERVALUE)) {
                        return new DeferredValueNode(map2, node);
                    }
                    hashMap = new HashMap(map2.size());
                    for (String stringBuilder2 : map2.keySet()) {
                        if (!stringBuilder2.startsWith(".")) {
                            NodeFromJSON = NodeFromJSON(map2.get(stringBuilder2));
                            if (!NodeFromJSON.isEmpty()) {
                                hashMap.put(ChildKey.fromString(stringBuilder2), NodeFromJSON);
                            }
                        }
                    }
                } else {
                    List list = (List) obj;
                    hashMap = new HashMap(list.size());
                    for (int i = 0; i < list.size(); i++) {
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("");
                        stringBuilder3.append(i);
                        stringBuilder2 = stringBuilder3.toString();
                        NodeFromJSON = NodeFromJSON(list.get(i));
                        if (!NodeFromJSON.isEmpty()) {
                            hashMap.put(ChildKey.fromString(stringBuilder2), NodeFromJSON);
                        }
                    }
                }
                if (hashMap.isEmpty()) {
                    return EmptyNode.Empty();
                }
                return new ChildrenNode(Builder.fromMap(hashMap, ChildrenNode.NAME_ONLY_COMPARATOR), node);
            }
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Failed to parse node with class ");
            stringBuilder4.append(obj.getClass().toString());
            throw new DatabaseException(stringBuilder4.toString());
        } catch (Throwable e) {
            throw new DatabaseException("Failed to parse node", e);
        }
    }

    public static int nameAndPriorityCompare(ChildKey childKey, Node node, ChildKey childKey2, Node node2) {
        int compareTo = node.compareTo(node2);
        if (compareTo != 0) {
            return compareTo;
        }
        return childKey.compareTo(childKey2);
    }
}
