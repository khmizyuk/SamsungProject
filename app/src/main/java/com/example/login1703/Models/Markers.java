package com.example.login1703.Models;

import com.google.android.gms.maps.model.LatLng;

public class Markers {
    String snippet;
    //LatLng latLng;
    double latitude;
    double longitude;
    String type;
    String id;

    public Markers(){}

    public Markers(String snippet, double latitude, double longitude, String type, String id) {
        this.snippet = snippet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.id = id;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
