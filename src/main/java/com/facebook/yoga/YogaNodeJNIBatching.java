package com.facebook.yoga;

import com.facebook.proguard.annotations.DoNotStrip;
import javax.annotation.Nullable;

@DoNotStrip
public class YogaNodeJNIBatching extends YogaNodeJNIBase {
    private static final byte BORDER = (byte) 4;
    private static final byte DOES_LEGACY_STRETCH_BEHAVIOUR = (byte) 8;
    private static final byte HAS_NEW_LAYOUT = (byte) 16;
    private static final byte LAYOUT_BORDER_START_INDEX = (byte) 14;
    private static final byte LAYOUT_DIRECTION_INDEX = (byte) 5;
    private static final byte LAYOUT_EDGE_SET_FLAG_INDEX = (byte) 0;
    private static final byte LAYOUT_HEIGHT_INDEX = (byte) 2;
    private static final byte LAYOUT_LEFT_INDEX = (byte) 3;
    private static final byte LAYOUT_MARGIN_START_INDEX = (byte) 6;
    private static final byte LAYOUT_PADDING_START_INDEX = (byte) 10;
    private static final byte LAYOUT_TOP_INDEX = (byte) 4;
    private static final byte LAYOUT_WIDTH_INDEX = (byte) 1;
    private static final byte MARGIN = (byte) 1;
    private static final byte PADDING = (byte) 2;
    @DoNotStrip
    @Nullable
    private float[] arr = null;
    private boolean mHasNewLayout = true;
    @DoNotStrip
    private int mLayoutDirection = 0;

    public YogaNodeJNIBatching(YogaConfig yogaConfig) {
        super(yogaConfig);
    }

    public void reset() {
        super.reset();
        this.arr = null;
        this.mHasNewLayout = true;
        this.mLayoutDirection = 0;
    }

    public float getLayoutX() {
        float[] fArr = this.arr;
        return fArr != null ? fArr[3] : 0.0f;
    }

    public float getLayoutY() {
        float[] fArr = this.arr;
        return fArr != null ? fArr[4] : 0.0f;
    }

    public float getLayoutWidth() {
        float[] fArr = this.arr;
        return fArr != null ? fArr[1] : 0.0f;
    }

    public float getLayoutHeight() {
        float[] fArr = this.arr;
        return fArr != null ? fArr[2] : 0.0f;
    }

    public boolean getDoesLegacyStretchFlagAffectsLayout() {
        float[] fArr = this.arr;
        return fArr != null && (((int) fArr[0]) & 8) == 8;
    }

    public float getLayoutMargin(YogaEdge yogaEdge) {
        float[] fArr = this.arr;
        if (fArr == null || (((int) fArr[0]) & 1) != 1) {
            return 0.0f;
        }
        switch (yogaEdge) {
            case LEFT:
                return this.arr[6];
            case TOP:
                return this.arr[7];
            case RIGHT:
                return this.arr[8];
            case BOTTOM:
                return this.arr[9];
            case START:
                return getLayoutDirection() == YogaDirection.RTL ? this.arr[8] : this.arr[6];
            case END:
                return getLayoutDirection() == YogaDirection.RTL ? this.arr[6] : this.arr[8];
            default:
                throw new IllegalArgumentException("Cannot get layout margins of multi-edge shorthands");
        }
    }

    public float getLayoutPadding(YogaEdge yogaEdge) {
        float[] fArr = this.arr;
        if (fArr != null) {
            int i = 0;
            if ((((int) fArr[0]) & 2) == 2) {
                if ((((int) fArr[0]) & 1) != 1) {
                    i = 4;
                }
                int i2 = 10 - i;
                switch (yogaEdge) {
                    case LEFT:
                        return this.arr[i2];
                    case TOP:
                        return this.arr[i2 + 1];
                    case RIGHT:
                        return this.arr[i2 + 2];
                    case BOTTOM:
                        return this.arr[i2 + 3];
                    case START:
                        return getLayoutDirection() == YogaDirection.RTL ? this.arr[i2 + 2] : this.arr[i2];
                    case END:
                        return getLayoutDirection() == YogaDirection.RTL ? this.arr[i2] : this.arr[i2 + 2];
                    default:
                        throw new IllegalArgumentException("Cannot get layout paddings of multi-edge shorthands");
                }
            }
        }
        return 0.0f;
    }

    public float getLayoutBorder(YogaEdge yogaEdge) {
        float[] fArr = this.arr;
        if (fArr != null) {
            int i = 0;
            if ((((int) fArr[0]) & 4) == 4) {
                int i2 = 14 - ((((int) fArr[0]) & 1) == 1 ? 0 : 4);
                if ((((int) this.arr[0]) & 2) != 2) {
                    i = 4;
                }
                i2 -= i;
                switch (yogaEdge) {
                    case LEFT:
                        return this.arr[i2];
                    case TOP:
                        return this.arr[i2 + 1];
                    case RIGHT:
                        return this.arr[i2 + 2];
                    case BOTTOM:
                        return this.arr[i2 + 3];
                    case START:
                        return getLayoutDirection() == YogaDirection.RTL ? this.arr[i2 + 2] : this.arr[i2];
                    case END:
                        return getLayoutDirection() == YogaDirection.RTL ? this.arr[i2] : this.arr[i2 + 2];
                    default:
                        throw new IllegalArgumentException("Cannot get layout border of multi-edge shorthands");
                }
            }
        }
        return 0.0f;
    }

    public YogaDirection getLayoutDirection() {
        float[] fArr = this.arr;
        return YogaDirection.fromInt(fArr != null ? (int) fArr[5] : this.mLayoutDirection);
    }

    public boolean hasNewLayout() {
        float[] fArr = this.arr;
        if (fArr == null) {
            return this.mHasNewLayout;
        }
        boolean z = false;
        if ((((int) fArr[0]) & 16) == 16) {
            z = true;
        }
        return z;
    }

    public void markLayoutSeen() {
        float[] fArr = this.arr;
        if (fArr != null) {
            fArr[0] = (float) (((int) fArr[0]) & -17);
        }
        this.mHasNewLayout = false;
    }
}
