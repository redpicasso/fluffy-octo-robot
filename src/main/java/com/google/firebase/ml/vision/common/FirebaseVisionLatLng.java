package com.google.firebase.ml.vision.common;

public class FirebaseVisionLatLng {
    private final double zzaxh;
    private final double zzaxi;

    public double getLatitude() {
        return this.zzaxh;
    }

    public double getLongitude() {
        return this.zzaxi;
    }

    public FirebaseVisionLatLng(double d, double d2) {
        this.zzaxh = d;
        this.zzaxi = d2;
    }
}
