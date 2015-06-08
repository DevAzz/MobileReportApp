package ru.kpfu.mobilereportapp.entities;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Модель данных здания (Местоположения жалобы)
 * Created by Azz on 23.05.2015.
 */
public class BuildingEntity implements Parcelable {

    /** Наименование здания */
    private String name;

    /** Адрес здания */
    private String address;

    /** Координаты здания */
    private LatLng position;

    /** широта */
    private double latitude;

    /** долгота */
    private double longitude;

    /** Список этажей */
    private int[] floors;

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
     * @param name Наименование здания
     * @param address Адрес здания
     * @param position Координаты здания
     */
    public BuildingEntity(String name, String address, LatLng position, int[] floors) {
        this.name = name;
        this.address = address;
        this.position = position;
        this.floors = floors;
    }

    /**
     * Конструктор
     * @param name Наименование здания
     * @param address Адрес здания
     * @param latitude широта
     * @param longitude долгота
     */
    public BuildingEntity(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    /**
     * Конструктор
     * @param name Наименование здания
     * @param address Адрес здания
     * @param latitude широта
     * @param longitude долгота
     */
    public BuildingEntity(String name, String address, double latitude, double longitude, int[] floors) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.floors = floors;

    }

    /**
     * Конструктор
     * @param in Container for a message (data and object references) that can be sent through an IBinder.
     */
    public BuildingEntity(Parcel in) {
        Bundle bundle = in.readBundle();
        name = bundle.getString("name");
        address = bundle.getString("address");
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        floors = bundle.getIntArray("floors");
    }

    /**
     * Геттер {@link #floors}
     * @return {@link #floors}
     */
    public int[] getFloors() {
        return floors;
    }

    /**
     * Сеттер {@link #floors}
     * @param floors список этажей
     */
    public void setFloors(int[] floors) {
        this.floors = floors;
    }

    /**
     * Геттер {@link #longitude}
     * @return {@link #longitude}
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Сеттер {@link #longitude}
     * @param longitude долгота
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Геттер {@link #latitude}
     * @return {@link #latitude}
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Сеттер {@link #latitude}
     * @param latitude широта
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
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

    /**
     * Метод, ковертирующий double широту и долготу в {@link LatLng}
     * @param latitude широта
     * @param longitude долгота
     * @return координаты типа {@link LatLng}
     */
    public LatLng convertDoubleIntoLatLng (double latitude, double longitude) {
        return new LatLng(latitude, longitude);
    }

    /**
     * Сеттер {@link #latitude} {@link #longitude} по {@link com.google.android.gms.maps.model.LatLng}
     * @param latLng коорбдинаты типа {@link com.google.android.gms.maps.model.LatLng}
     */
    public void setDoubleCoordinates (LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;

    }

    /**
     * Сеттер {@link #position} по широте и долготе
     * @param latitude широта
     * @param longitude долгота
     */
    public void setLatLngCoordinates (double latitude, double longitude) {
        this.position = new LatLng(latitude, longitude);
    }

    /**
     * Метод, ковертирующий координаты типа {@link com.google.android.gms.maps.model.LatLng} в массив типа double
     * @param latLng координаты типа {@link com.google.android.gms.maps.model.LatLng}
     * @return в массив типа double
     */
    public double[] convertLatLngIntoDouble (LatLng latLng) {
        return new double [] {latLng.latitude, latLng.longitude};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("address", address);
        bundle.putString("name", name);
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putIntArray("floors", floors);
        dest.writeBundle(bundle);
    }

    /**
     * CREATOR типа Parcelable.Creator<{@link UserEntity}> используется для создания экземпляра нашего {@link UserEntity} и заполнения его данными из Parcer.
     */
    public static final Parcelable.Creator<BuildingEntity> CREATOR = new Parcelable.Creator<BuildingEntity>() {

        /**
         *{@inheritDoc}
         */
        @Override
        public BuildingEntity createFromParcel(Parcel source) {
            return new BuildingEntity(source);
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public BuildingEntity[] newArray(int size) {
            return new BuildingEntity[0];
        }
    };
}
