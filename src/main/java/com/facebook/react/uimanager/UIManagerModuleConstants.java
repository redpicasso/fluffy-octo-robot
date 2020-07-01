package com.facebook.react.uimanager;

import android.widget.ImageView.ScaleType;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.events.TouchEventType;
import com.facebook.react.views.picker.events.PickerItemSelectEvent;
import java.util.Map;

class UIManagerModuleConstants {
    public static final String ACTION_DISMISSED = "dismissed";
    public static final String ACTION_ITEM_SELECTED = "itemSelected";

    UIManagerModuleConstants() {
    }

    static Map getBubblingEventTypeConstants() {
        String str = "captured";
        String str2 = "bubbled";
        String str3 = "phasedRegistrationNames";
        String str4 = "topChange";
        return MapBuilder.builder().put(str4, MapBuilder.of(str3, MapBuilder.of(str2, "onChange", str, "onChangeCapture"))).put(PickerItemSelectEvent.EVENT_NAME, MapBuilder.of(str3, MapBuilder.of(str2, "onSelect", str, "onSelectCapture"))).put(TouchEventType.getJSEventName(TouchEventType.START), MapBuilder.of(str3, MapBuilder.of(str2, "onTouchStart", str, "onTouchStartCapture"))).put(TouchEventType.getJSEventName(TouchEventType.MOVE), MapBuilder.of(str3, MapBuilder.of(str2, "onTouchMove", str, "onTouchMoveCapture"))).put(TouchEventType.getJSEventName(TouchEventType.END), MapBuilder.of(str3, MapBuilder.of(str2, "onTouchEnd", str, "onTouchEndCapture"))).put(TouchEventType.getJSEventName(TouchEventType.CANCEL), MapBuilder.of(str3, MapBuilder.of(str2, "onTouchCancel", str, "onTouchCancelCapture"))).build();
    }

    static Map getDirectEventTypeConstants() {
        String str = "registrationName";
        String str2 = "topLayout";
        str2 = "topLoadingError";
        str2 = "topLoadingFinish";
        str2 = "topLoadingStart";
        str2 = "topSelectionChange";
        str2 = "topMessage";
        str2 = "topClick";
        str2 = "topScrollBeginDrag";
        str2 = "topScrollEndDrag";
        str2 = "topScroll";
        str2 = "topMomentumScrollBegin";
        return MapBuilder.builder().put("topContentSizeChange", MapBuilder.of(str, "onContentSizeChange")).put(str2, MapBuilder.of(str, ViewProps.ON_LAYOUT)).put(str2, MapBuilder.of(str, "onLoadingError")).put(str2, MapBuilder.of(str, "onLoadingFinish")).put(str2, MapBuilder.of(str, "onLoadingStart")).put(str2, MapBuilder.of(str, "onSelectionChange")).put(str2, MapBuilder.of(str, "onMessage")).put(str2, MapBuilder.of(str, "onClick")).put(str2, MapBuilder.of(str, "onScrollBeginDrag")).put(str2, MapBuilder.of(str, "onScrollEndDrag")).put(str2, MapBuilder.of(str, "onScroll")).put(str2, MapBuilder.of(str, "onMomentumScrollBegin")).put("topMomentumScrollEnd", MapBuilder.of(str, "onMomentumScrollEnd")).build();
    }

    public static Map<String, Object> getConstants() {
        Map<String, Object> newHashMap = MapBuilder.newHashMap();
        newHashMap.put("UIView", MapBuilder.of("ContentMode", MapBuilder.of("ScaleAspectFit", Integer.valueOf(ScaleType.FIT_CENTER.ordinal()), "ScaleAspectFill", Integer.valueOf(ScaleType.CENTER_CROP.ordinal()), "ScaleAspectCenter", Integer.valueOf(ScaleType.CENTER_INSIDE.ordinal()))));
        newHashMap.put("StyleConstants", MapBuilder.of("PointerEventsValues", MapBuilder.of(ViewProps.NONE, Integer.valueOf(PointerEvents.NONE.ordinal()), "boxNone", Integer.valueOf(PointerEvents.BOX_NONE.ordinal()), "boxOnly", Integer.valueOf(PointerEvents.BOX_ONLY.ordinal()), "unspecified", Integer.valueOf(PointerEvents.AUTO.ordinal()))));
        String str = ACTION_ITEM_SELECTED;
        String str2 = ACTION_DISMISSED;
        newHashMap.put("PopupMenu", MapBuilder.of(str2, str2, str, str));
        newHashMap.put("AccessibilityEventTypes", MapBuilder.of("typeWindowStateChanged", Integer.valueOf(32), "typeViewFocused", Integer.valueOf(8), "typeViewClicked", Integer.valueOf(1)));
        return newHashMap;
    }
}
