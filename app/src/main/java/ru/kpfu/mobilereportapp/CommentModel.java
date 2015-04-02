package ru.kpfu.mobilereportapp;

/**
 * Модель для комментария
 * Created by Azz on 31.03.2015.
 */
public class CommentModel {
    /** */
    private int idComment;

    /** */
    private String nameUser;

    /** */
    private int idUser;

    /** */
    private String textComment;

    /** */
    private String dateComment;

    /**
     * Конструктор
     * @param nameUser имя юзера
     * @param textComment текст комментария
     * @param dateComment дата комментария
     */
    public CommentModel(String nameUser, String textComment, String dateComment) {
        this.nameUser = nameUser;
        this.textComment = textComment;
        this.dateComment = dateComment;
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
}
