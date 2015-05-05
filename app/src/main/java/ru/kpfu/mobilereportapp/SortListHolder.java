package ru.kpfu.mobilereportapp;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azz on 25.04.2015.
 */
public class SortListHolder {

    private static final Uri S0RT_BUILDING_URI = Uri.parse("android.resource://ru.kpfu.mobilereportapp/" + R.drawable.map);
    private static final Uri SORT_DATE_URI = Uri.parse("android.resource://ru.kpfu.mobilereportapp/" + R.drawable.date);
   private static final Uri SORT_FlOOR_URI = Uri.parse("android.resource://ru.kpfu.mobilereportapp/" + R.drawable.stairs);
    private static final Uri SORT_RATING_URI = Uri.parse("android.resource://ru.kpfu.mobilereportapp/" + R.drawable.star);


    public List<DialogSortModel> getListModelSort() {
        List<DialogSortModel> list = new ArrayList<>();
        list.add(getModel("Рейтинг", SORT_RATING_URI));
        list.add(getModel("Дата",  SORT_DATE_URI));
        return list;
    }

    public List<DialogSortModel> getListModelFilter() {
        List<DialogSortModel> list = new ArrayList<>();
        list.add(getModel("Здание", S0RT_BUILDING_URI));
        list.add(getModel("Этаж", SORT_FlOOR_URI));
        return list;
    }


    private DialogSortModel getModel (String nameSort, Uri imageUri) {
        return new DialogSortModel(nameSort, imageUri);
    }
}
