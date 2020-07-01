package com.airbnb.android.react.maps;

import android.content.Context;
import android.graphics.Bitmap;
import com.facebook.react.bridge.ReadableArray;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class AirMapOverlay extends AirMapFeature implements ImageReadable {
    private LatLngBounds bounds;
    private GroundOverlay groundOverlay;
    private GroundOverlayOptions groundOverlayOptions;
    private Bitmap iconBitmap;
    private BitmapDescriptor iconBitmapDescriptor;
    private final ImageReader mImageReader;
    private GoogleMap map;
    private boolean tappable;
    private float transparency;
    private float zIndex;

    public AirMapOverlay(Context context) {
        super(context);
        this.mImageReader = new ImageReader(context, getResources(), this);
    }

    public void setBounds(ReadableArray readableArray) {
        this.bounds = new LatLngBounds(new LatLng(readableArray.getArray(1).getDouble(0), readableArray.getArray(0).getDouble(1)), new LatLng(readableArray.getArray(0).getDouble(0), readableArray.getArray(1).getDouble(1)));
        GroundOverlay groundOverlay = this.groundOverlay;
        if (groundOverlay != null) {
            groundOverlay.setPositionFromBounds(this.bounds);
        }
    }

    public void setZIndex(float f) {
        this.zIndex = f;
        GroundOverlay groundOverlay = this.groundOverlay;
        if (groundOverlay != null) {
            groundOverlay.setZIndex(f);
        }
    }

    public void setImage(String str) {
        this.mImageReader.setImage(str);
    }

    public void setTappable(boolean z) {
        this.tappable = z;
        GroundOverlay groundOverlay = this.groundOverlay;
        if (groundOverlay != null) {
            groundOverlay.setClickable(this.tappable);
        }
    }

    public GroundOverlayOptions getGroundOverlayOptions() {
        if (this.groundOverlayOptions == null) {
            this.groundOverlayOptions = createGroundOverlayOptions();
        }
        return this.groundOverlayOptions;
    }

    private GroundOverlayOptions createGroundOverlayOptions() {
        GroundOverlayOptions groundOverlayOptions = this.groundOverlayOptions;
        if (groundOverlayOptions != null) {
            return groundOverlayOptions;
        }
        groundOverlayOptions = new GroundOverlayOptions();
        BitmapDescriptor bitmapDescriptor = this.iconBitmapDescriptor;
        if (bitmapDescriptor != null) {
            groundOverlayOptions.image(bitmapDescriptor);
        } else {
            groundOverlayOptions.image(BitmapDescriptorFactory.defaultMarker());
            groundOverlayOptions.visible(false);
        }
        groundOverlayOptions.positionFromBounds(this.bounds);
        groundOverlayOptions.zIndex(this.zIndex);
        return groundOverlayOptions;
    }

    public Object getFeature() {
        return this.groundOverlay;
    }

    public void addToMap(GoogleMap googleMap) {
        GroundOverlayOptions groundOverlayOptions = getGroundOverlayOptions();
        if (groundOverlayOptions != null) {
            this.groundOverlay = googleMap.addGroundOverlay(groundOverlayOptions);
            this.groundOverlay.setClickable(this.tappable);
            return;
        }
        this.map = googleMap;
    }

    public void removeFromMap(GoogleMap googleMap) {
        this.map = null;
        GroundOverlay groundOverlay = this.groundOverlay;
        if (groundOverlay != null) {
            groundOverlay.remove();
            this.groundOverlay = null;
            this.groundOverlayOptions = null;
        }
    }

    public void setIconBitmap(Bitmap bitmap) {
        this.iconBitmap = bitmap;
    }

    public void setIconBitmapDescriptor(BitmapDescriptor bitmapDescriptor) {
        this.iconBitmapDescriptor = bitmapDescriptor;
    }

    public void update() {
        this.groundOverlay = getGroundOverlay();
        GroundOverlay groundOverlay = this.groundOverlay;
        if (groundOverlay != null) {
            groundOverlay.setVisible(true);
            this.groundOverlay.setImage(this.iconBitmapDescriptor);
            this.groundOverlay.setClickable(this.tappable);
        }
    }

    private GroundOverlay getGroundOverlay() {
        GroundOverlay groundOverlay = this.groundOverlay;
        if (groundOverlay != null) {
            return groundOverlay;
        }
        if (this.map == null) {
            return null;
        }
        GroundOverlayOptions groundOverlayOptions = getGroundOverlayOptions();
        if (groundOverlayOptions != null) {
            return this.map.addGroundOverlay(groundOverlayOptions);
        }
        return null;
    }
}
