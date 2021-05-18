package com.example.login1703.Models;

public class Markers {
    String snippet;
    double latitude;
    double longitude;
    String type;

    public Markers(){}

    public Markers(String snippet, double latitude, double longitude, String type) {
        this.snippet = snippet;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
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

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
