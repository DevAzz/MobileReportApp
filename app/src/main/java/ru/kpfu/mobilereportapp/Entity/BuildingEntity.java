package ru.kpfu.mobilereportapp.Entity;

import android.os.Bundle;
import android.os.Parcel;

import com.google.android.gms.maps.model.LatLng;

/**
 * Модель данных здания (Местоположения жалобы)
 * Created by Azz on 23.05.2015.
 */
public class BuildingEntity {

    /** Наименование здания */
    private String name;

    /** Адрес здания */
    private String address;

    /** Координаты здания */
    private LatLng position;

    /**
     * Конструктор
     * @param name Наименование здания
     * @param address Адрес здания
     * @param position Координаты здания
     */
    public BuildingEntity(String name, String address, LatLng position) {
        this.name = name;
        this.address = address;
        this.position = position;
    }

    /**
     * Конструктор
     * @param in Container for a message (data and object references) that can be sent through an IBinder.
     */
    public BuildingEntity(Parcel in) {
        Bundle bundle = in.readBundle();
        name = bundle.getString("name");
        address = bundle.getString("address");
    }

    /**
     * Сеттер {@link #name}
     * @param name Наименование здания
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Сеттер {@link #address}
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Сеттер {@link #position}
     * @param position Координаты здания
     */
    public void setPosition(LatLng position) {
        this.position = position;
    }

    /**
     * Геттер {@link #address}
     * @return {@link #address}
     */
    public String getAddress() {
        return address;
    }

    /**
     * Геттер {@link #name}
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * Геттер {@link #position}
     * @return {@link #position}
     */
    public LatLng getPosition() {
        return position;
    }
}
