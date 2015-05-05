package ru.kpfu.mobilereportapp;

import android.net.Uri;

/**
 * Created by Azz on 25.04.2015.
 */
public class DialogSortModel {

    private String _nameSort;

    private Uri _iconSort;

    public DialogSortModel(String _nameSort, Uri _iconSort) {
        this._nameSort = _nameSort;
        this._iconSort = _iconSort;
    }

    public String get_nameSort() {
        return _nameSort;
    }

    public void set_nameSort(String _nameSort) {
        this._nameSort = _nameSort;
    }

    public Uri get_iconSort() {
        return _iconSort;
    }

    public void set_iconSort(Uri _iconSort) {
        this._iconSort = _iconSort;
    }
}
