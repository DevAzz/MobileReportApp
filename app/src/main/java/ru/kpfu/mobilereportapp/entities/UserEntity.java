package ru.kpfu.mobilereportapp.entities;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Класс, описывающий сущность - Пользователь
 * Created by Azz on 23.05.2015.
 */
public class UserEntity implements Parcelable {

    /** Идентификатор Пользователя */
    private int idUser;

    /** Имя Пользователя */
    private String nameUser;

    /** Факультет Пользователя */
    private String facultyUser;

    /** Путь до аватара пользователя. Только клиент */
    private String avatarPath;

    /**
     * Конструктор
     * @param idUser Идентификатор Пользователя
     * @param nameUser Имя Пользователя
     * @param facultyUser Факультет Пользователя
     */
    public UserEntity(int idUser, String nameUser, String facultyUser) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.facultyUser = facultyUser;
    }

    /**
     * Конструктор
     * @param idUser Идентификатор Пользователя
     * @param nameUser Имя Пользователя
     * @param facultyUser Факультет Пользователя
     * @param avatarPath Путь до аватара пользоватедя
     */
    public UserEntity(int idUser, String nameUser, String facultyUser, String avatarPath) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.facultyUser = facultyUser;
        this.avatarPath = avatarPath;
    }

    /**
     * Конструктор
     * @param in Container for a message (data and object references) that can be sent through an IBinder.
     */
    public UserEntity(Parcel in) {
        Bundle bundle = in.readBundle();
        nameUser = bundle.getString("name");
        facultyUser = bundle.getString("faculty");
        avatarPath = bundle.getString("photo");
        idUser = bundle.getInt("id");
    }

    /**
     * Геттер {@link #idUser}
     * @return {@link #idUser}
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Сеттер {@link #idUser}
     * @param idUser идентификатор пользователя
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Геттер {@link #nameUser}
     * @return {@link #nameUser}
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * Сеттер {@link #nameUser}
     * @param nameUser имя пользователя
     */
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    /**
     * Геттер {@link #facultyUser}
     * @return {@link #facultyUser}
     */
    public String getFacultyUser() {
        return facultyUser;
    }

    /**
     * Сеттер {@link #facultyUser}
     * @param facultyUser Факультет Пользователя
     */
    public void setFacultyUser(String facultyUser) {
        this.facultyUser = facultyUser;
    }

    /**
     * Геттер {@link #avatarPath}
     * @return {@link #avatarPath}
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Сеттер {@link #avatarPath}
     * @param avatarPath Путь до аватара пользователя
     */
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("name", nameUser);
        bundle.putString("faculty", facultyUser);
        bundle.putString("photo", avatarPath);
        bundle.putInt("id", idUser);
        dest.writeBundle(bundle);
    }

    /**
     * CREATOR типа Parcelable.Creator<{@link UserEntity}> используется для создания экземпляра нашего {@link UserEntity} и заполнения его данными из Parcer.
     */
    public static final Parcelable.Creator<UserEntity> CREATOR = new Parcelable.Creator<UserEntity>() {

        /**
         *{@inheritDoc}
         */
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[0];
        }
    };
}
