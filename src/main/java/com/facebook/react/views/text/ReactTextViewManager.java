package com.facebook.react.views.text;

import android.content.Context;
import android.text.Spannable;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.IViewManagerWithChildren;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.yoga.YogaMeasureMode;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(name = "RCTText")
public class ReactTextViewManager extends ReactTextAnchorViewManager<ReactTextView, ReactTextShadowNode> implements IViewManagerWithChildren {
    @VisibleForTesting
    public static final String REACT_CLASS = "RCTText";

    public String getName() {
        return REACT_CLASS;
    }

    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    public ReactTextView createViewInstance(ThemedReactContext themedReactContext) {
        return new ReactTextView(themedReactContext);
    }

    public void updateExtraData(ReactTextView reactTextView, Object obj) {
        ReactTextUpdate reactTextUpdate = (ReactTextUpdate) obj;
        if (reactTextUpdate.containsImages()) {
            TextInlineImageSpan.possiblyUpdateInlineImageSpans(reactTextUpdate.getText(), reactTextView);
        }
        reactTextView.setText(reactTextUpdate);
    }

    public ReactTextShadowNode createShadowNodeInstance() {
        return new ReactTextShadowNode();
    }

    public Class<ReactTextShadowNode> getShadowNodeClass() {
        return ReactTextShadowNode.class;
    }

    protected void onAfterUpdateTransaction(ReactTextView reactTextView) {
        super.onAfterUpdateTransaction(reactTextView);
        reactTextView.updateView();
    }

    public Object updateLocalData(ReactTextView reactTextView, ReactStylesDiffMap reactStylesDiffMap, ReactStylesDiffMap reactStylesDiffMap2) {
        Spannable orCreateSpannableForText = TextLayoutManager.getOrCreateSpannableForText(reactTextView.getContext(), reactStylesDiffMap2.getMap("attributedString"));
        reactTextView.setSpanned(orCreateSpannableForText);
        TextAttributeProps textAttributeProps = new TextAttributeProps(reactStylesDiffMap);
        return new ReactTextUpdate(orCreateSpannableForText, -1, false, textAttributeProps.getStartPadding(), textAttributeProps.getTopPadding(), textAttributeProps.getEndPadding(), textAttributeProps.getBottomPadding(), textAttributeProps.getTextAlign(), 1, 0);
    }

    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        String str = "registrationName";
        return MapBuilder.of("topTextLayout", MapBuilder.of(str, "onTextLayout"), "topInlineViewLayout", MapBuilder.of(str, "onInlineViewLayout"));
    }

    public long measure(Context context, ReadableMap readableMap, ReadableMap readableMap2, ReadableMap readableMap3, float f, YogaMeasureMode yogaMeasureMode, float f2, YogaMeasureMode yogaMeasureMode2) {
        return TextLayoutManager.measureText(context, readableMap, readableMap2, f, yogaMeasureMode, f2, yogaMeasureMode2);
    }
}
