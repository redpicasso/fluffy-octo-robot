package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;

public class CondNode extends Node {
    private final int mCondID;
    private final int mElseBlockID;
    private final int mIfBlockID;

    public CondNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mCondID = readableMap.getInt("cond");
        String str = "ifBlock";
        int i2 = -1;
        this.mIfBlockID = readableMap.hasKey(str) ? readableMap.getInt(str) : -1;
        str = "elseBlock";
        if (readableMap.hasKey(str)) {
            i2 = readableMap.getInt(str);
        }
        this.mElseBlockID = i2;
    }

    protected Object evaluate() {
        Object nodeValue = this.mNodesManager.getNodeValue(this.mCondID);
        if (!(nodeValue instanceof Number) || ((Number) nodeValue).doubleValue() == 0.0d) {
            return this.mElseBlockID != -1 ? this.mNodesManager.getNodeValue(this.mElseBlockID) : ZERO;
        }
        return this.mIfBlockID != -1 ? this.mNodesManager.getNodeValue(this.mIfBlockID) : ZERO;
    }
}
