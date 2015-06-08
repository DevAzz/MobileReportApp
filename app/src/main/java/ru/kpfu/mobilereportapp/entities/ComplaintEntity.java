package ru.kpfu.mobilereportapp.entities;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных жалобы
 * Created by Azz on 19.03.2015.
 */
public class ComplaintEntity implements Parcelable {

    /**
     * Идентификатор жалобы
     */
    private int id;

    /**
     * URL Фото жалобы
     */
    private String photo;

    /**
     * Заголовок жалобы
     */
    private String title;

    /**
     * Список комментариев типа {@link CommentEntity}
     */
    private ArrayList<CommentEntity> comments;

    /**
     * Текст жалобы
     */
    private String description;

    /**
     * Статус жалобы
     */
    private String status;

    /**
     * Дата от которой жалоба
     */
    private String date;

    /**
     * Автор жалобы. {@link ru.kpfu.mobilereportapp.entities.UserEntity}
     */
    private UserEntity owner;

    /**
     * Местоположение. {@link BuildingEntity}
     */
    private BuildingEntity location;

    /**
     * Рейтинг
     */
    private int rating;

    /**
     * Конструктор
     *
     * @param id          Идентификатор жалобы
     * @param photo       URL Фото жалобы
     * @param title       Заголовок жалобы
     * @param comments    Список комментариев
     * @param description Текст жалобы
     * @param status      Статус жалобы
     * @param date        Дата от которой жалоба
     * @param owner       Автор жалобы
     * @param location    Местоположение
     */
    public ComplaintEntity(int id, String photo, String title, ArrayList<CommentEntity> comments, String description, String status, String date, UserEntity owner, BuildingEntity location, int rating) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.comments = comments;
        this.description = description;
        this.status = status;
        this.date = date;
        this.owner = owner;
        this.location = location;
        this.rating = rating;
    }

    /**
     * Конструктор
     *
     * @param in Container for a message (data and object references) that can be sent through an IBinder.
     */
    public ComplaintEntity(Parcel in) {
        this.owner = (UserEntity) in.readParcelable(getClass().getClassLoader());
        this.location = (BuildingEntity) in.readParcelable(getClass().getClassLoader());

        if (comments == null) {
            comments = new ArrayList<CommentEntity>();
        }

        in.readTypedList(comments, CommentEntity.CREATOR);

        Bundle bundle = in.readBundle();
        this.id = bundle.getInt("id");
        this.photo = bundle.getString("photo");
        this.title = bundle.getString("title");
        //this.comments = bundle.getParcelableArrayList("comments");
        this.description = bundle.getString("description");
        this.status = bundle.getString("status");
        this.date = bundle.getString("date");
        this.rating = bundle.getInt("rating");
    }

    /**
     * Геттер {@link #rating}
     *
     * @return {@link #rating}
     */
    public int getRating() {
        return rating;
    }

    /**
     * Сеттер {@link #rating}
     *
     * @param rating Рейтинг
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Геттер {@link #id}
     *
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * Сеттер {@link #id}
     *
     * @param id Идентификатор жалобы
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Геттер {@link #photo}
     *
     * @return {@link #photo}
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Сеттер {@link #photo}
     *
     * @param photo URL Фото жалобы
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Геттер {@link #title}
     *
     * @return {@link #title}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Сеттер {@link #title}
     *
     * @param title Заголовок жалобы
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Геттер {@link #comments}
     *
     * @return {@link #comments}
     */
    public List<CommentEntity> getComments() {
        return comments;
    }

    /**
     * Сеттер {@link #comments}
     *
     * @param comments Список комментариев
     */
    public void setComments(ArrayList<CommentEntity> comments) {
        this.comments = comments;
    }

    /**
     * Геттер {@link #description}
     *
     * @return {@link #description}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Сеттер {@link #description}
     *
     * @param description Текст жалобы
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Геттер {@link #status}
     *
     * @return {@link #status}
     */
    public String getStatus() {
        return status;
    }

    /**
     * Сеттер {@link #status}
     *
     * @param status Статус жалобы
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Геттер {@link #date}
     *
     * @return {@link #date}
     */
    public String getDate() {
        return date;
    }

    /**
     * Сеттер {@link #date}
     *
     * @param date Дата от которой жалоба
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Геттер {@link #owner}
     *
     * @return {@link #owner}
     */
    public UserEntity getOwner() {
        return owner;
    }

    /**
     * Сеттер {@link #owner}
     *
     * @param owner Автор жалобы
     */
    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    /**
     * Геттер {@link #location}
     *
     * @return {@link #location}
     */
    public BuildingEntity getLocation() {
        return location;
    }

    /**
     * Сеттер {@link #location}
     *
     * @param location Местоположение
     */
    public void setLocation(BuildingEntity location) {
        this.location = location;
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
        bundle.putInt("id", id);
        bundle.putString("photo", photo);
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putString("status", status);
        bundle.putString("date", date);
        bundle.putInt("rating", rating);
        dest.writeParcelable(owner, flags);
        dest.writeParcelable(location, flags);
        dest.writeTypedList(comments);
        dest.writeBundle(bundle);
    }

    /**
     * CREATOR типа Parcelable.Creator<{@link ComplaintEntity}> используется для создания экземпляра нашего {@link ComplaintEntity} и заполнения его данными из Parcel.
     */
    public static final Parcelable.Creator<ComplaintEntity> CREATOR = new Parcelable.Creator<ComplaintEntity>() {

        /**
         *{@inheritDoc}
         */
        @Override
        public ComplaintEntity createFromParcel(Parcel source) {
            return new ComplaintEntity(source);
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public ComplaintEntity[] newArray(int size) {
            return new ComplaintEntity[0];
        }
    };
}
