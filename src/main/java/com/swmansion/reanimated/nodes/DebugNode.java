package com.swmansion.reanimated.nodes;

import android.util.Log;
import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;

public class DebugNode extends Node {
    private final String mMessage;
    private final int mValueID;

    public DebugNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mMessage = readableMap.getString("message");
        this.mValueID = readableMap.getInt("value");
    }

    protected Object evaluate() {
        Log.d("REANIMATED", String.format("%s %s", new Object[]{this.mMessage, this.mNodesManager.findNodeById(this.mValueID, Node.class).value()}));
        return this.mNodesManager.findNodeById(this.mValueID, Node.class).value();
    }
}
