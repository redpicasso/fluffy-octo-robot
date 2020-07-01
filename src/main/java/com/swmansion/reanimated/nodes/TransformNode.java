package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.swmansion.reanimated.NodesManager;
import java.util.ArrayList;
import java.util.List;

public class TransformNode extends Node {
    private List<TransformConfig> mTransforms;

    private static abstract class TransformConfig {
        public String propertyName;

        public abstract Object getValue(NodesManager nodesManager);

        private TransformConfig() {
        }
    }

    private static class AnimatedTransformConfig extends TransformConfig {
        public int nodeID;

        private AnimatedTransformConfig() {
            super();
        }

        public Object getValue(NodesManager nodesManager) {
            return nodesManager.getNodeValue(this.nodeID);
        }
    }

    private static class StaticTransformConfig extends TransformConfig {
        public Object value;

        private StaticTransformConfig() {
            super();
        }

        public Object getValue(NodesManager nodesManager) {
            return this.value;
        }
    }

    private static List<TransformConfig> processTransforms(ReadableArray readableArray) {
        List<TransformConfig> arrayList = new ArrayList(readableArray.size());
        for (int i = 0; i < readableArray.size(); i++) {
            ReadableMap map = readableArray.getMap(i);
            String string = map.getString("property");
            String str = "nodeID";
            if (map.hasKey(str)) {
                AnimatedTransformConfig animatedTransformConfig = new AnimatedTransformConfig();
                animatedTransformConfig.propertyName = string;
                animatedTransformConfig.nodeID = map.getInt(str);
                arrayList.add(animatedTransformConfig);
            } else {
                Object string2;
                StaticTransformConfig staticTransformConfig = new StaticTransformConfig();
                staticTransformConfig.propertyName = string;
                string = "value";
                if (map.getType(string) == ReadableType.String) {
                    string2 = map.getString(string);
                } else {
                    string2 = Double.valueOf(map.getDouble(string));
                }
                staticTransformConfig.value = string2;
                arrayList.add(staticTransformConfig);
            }
        }
        return arrayList;
    }

    public TransformNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mTransforms = processTransforms(readableMap.getArray("transform"));
    }

    protected WritableArray evaluate() {
        List arrayList = new ArrayList(this.mTransforms.size());
        for (TransformConfig transformConfig : this.mTransforms) {
            arrayList.add(JavaOnlyMap.of(transformConfig.propertyName, transformConfig.getValue(this.mNodesManager)));
        }
        return JavaOnlyArray.from(arrayList);
    }
}
