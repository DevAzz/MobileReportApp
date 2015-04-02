package ru.kpfu.mobilereportapp;

/**
 * Created by Azz on 19.03.2015.
 */
public class ComplaintModel {
    private String _text;

    private String _status;

    private int _countComment;

    private int _rating;

    //FIXME Здесь будет картинка жалобы

    public ComplaintModel(String _text, String _status, int _countComment, int _rating) {
        this._text = _text;
        this._status = _status;
        this._countComment = _countComment;
        this._rating = _rating;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public int get_countComment() {
        return _countComment;
    }

    public void set_countComment(int _countComment) {
        this._countComment = _countComment;
    }

    public int get_rating() {
        return _rating;
    }

    public void set_rating(int _rating) {
        this._rating = _rating;
    }
}
