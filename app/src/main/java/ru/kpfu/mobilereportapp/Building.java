package ru.kpfu.mobilereportapp;

import com.google.android.gms.maps.model.LatLng;

public class Building {
    private String name;
    private String address;
    private LatLng position;

    public Building(String name,String address, LatLng position) {
        this.name = name;
        this.address = address;
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public LatLng getPosition() {
        return position;
    }
}
