package ru.kpfu.mobilereportapp.utils.holders;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.mobilereportapp.R;
import ru.kpfu.mobilereportapp.entities.DialogSortEntity;

/**
 * Created by Azz on 25.04.2015.
 */
public class SortListHolder {

    private static final Uri S0RT_BUILDING_URI = Uri.parse("android.resource://ru.kpfu.mobilereportapp/" + R.drawable.ic_domain_black_48dp);
    private static final Uri SORT_DATE_URI = Uri.parse("android.resource://ru.kpfu.mobilereportapp/" + R.drawable.ic_event_black_48dp);
    private static final Uri SORT_FlOOR_URI = Uri.parse("android.resource://ru.kpfu.mobilereportapp/" + R.drawable.ic_layers_black_36dp);
    private static final Uri SORT_RATING_URI = Uri.parse("android.resource://ru.kpfu.mobilereportapp/" + R.drawable.ic_star_border_black_36dp);


    public List<DialogSortEntity> getListModelSort() {
        List<DialogSortEntity> list = new ArrayList<>();
        list.add(getModel("Рейтинг", SORT_RATING_URI));
        list.add(getModel("Дата",  SORT_DATE_URI));
        return list;
    }

    public List<DialogSortEntity> getListModelFilter() {
        List<DialogSortEntity> list = new ArrayList<>();
        list.add(getModel("Здание", S0RT_BUILDING_URI));
        list.add(getModel("Этаж", SORT_FlOOR_URI));
        return list;
    }


    private DialogSortEntity getModel (String nameSort, Uri imageUri) {
        return new DialogSortEntity(nameSort, imageUri);
    }
}
