package ru.kpfu.mobilereportapp.entities;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Модель для комментария
 * Created by Azz on 31.03.2015.
 */
public class CommentEntity implements Parcelable {

    /** Идентификатор комментария */
    private int idComment;

    /** Имя пользователя */
    private String nameUser;

    /** Идентификатор пользователя */
    private int idUser;

    /** Текст комментария */
    private String textComment;

    /** Дата Комментария */
    private String dateComment;

    /**
     * Конструктор
     * @param nameUser имя юзера
     * @param textComment текст комментария
     * @param dateComment дата комментария
     */
    public CommentEntity(String nameUser, String textComment, String dateComment) {
        this.nameUser = nameUser;
        this.textComment = textComment;
        this.dateComment = dateComment;
    }

    /**
     * Конструктор
     * @param in Container for a message (data and object references) that can be sent through an IBinder.
     */
    public CommentEntity (Parcel in) {
        Bundle bundle = in.readBundle();
        idComment = bundle.getInt("idComment");
        idUser = bundle.getInt("idUser");
        nameUser = bundle.getString("nameUser");
        textComment = bundle.getString("textComment");
        dateComment = bundle.getString("dateComment");
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public String getDateComment() {
        return dateComment;
    }

    public void setDateComment(String dateComment) {
        this.dateComment = dateComment;
    }

    /**
     *{@inheritDoc}
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
        bundle.putString("nameUser", nameUser);
        bundle.putString("textComment", textComment);
        bundle.putString("dateComment", dateComment);
        bundle.putInt("idUser", idUser);
        bundle.putInt("idComment", idComment);
        dest.writeBundle(bundle);
    }

    /**
     * CREATOR типа Parcelable.Creator<{@link ru.kpfu.mobilereportapp.entities.UserEntity}> используется для создания экземпляра нашего {@link ru.kpfu.mobilereportapp.entities.UserEntity} и заполнения его данными из Parcer.
     */
    public static final Parcelable.Creator<CommentEntity> CREATOR = new Parcelable.Creator<CommentEntity>() {

        /**
         *{@inheritDoc}
         */
        @Override
        public CommentEntity createFromParcel(Parcel source) {
            return new CommentEntity(source);
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public CommentEntity[] newArray(int size) {
            return new CommentEntity[0];
        }
    };
}
