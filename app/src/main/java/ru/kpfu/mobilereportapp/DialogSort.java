package ru.kpfu.mobilereportapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

import ru.kpfu.mobilereportapp.Entity.UserEntity;

/**
 * Created by Azz on 25.04.2015.
 */
public class DialogSort extends DialogFragment {

    private android.widget.AdapterView.OnItemClickListener onItemClickListener;
    private ListView listSort;
    public static String TAG = "DialogSort";
    public static String KEY = "Activity";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_sort, null);

        listSort = (ListView) v.findViewById(R.id.listViewSort);
        SortListHolder holder = new SortListHolder();
        AdapterSortList adapter = new AdapterSortList(getActivity(), holder.getListModelSort());
        listSort.setAdapter(adapter);

        // Находим наш list
        ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.exListView);
        //Создаем набор данных для адаптера
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> children1 = new ArrayList<String>();

        String textAgreed = getString(R.string.sortStatusAgreed);
        String textDuring = getString(R.string.sortStatusDuring);
        String textDenied = getString(R.string.sortStatusDenied);
        children1.add(textAgreed);
        children1.add(textDuring);
        children1.add(textDenied);
        groups.add(children1);

        //Создаем адаптер и передаем context и список с данными
        AdapterExpandableList adapterExp = new AdapterExpandableList(v.getContext(), groups);
        listView.setAdapter(adapterExp);

        ListView listViewFilter = (ListView) v.findViewById(R.id.listViewFilter);
        AdapterSortList adapterFilter = new AdapterSortList(getActivity(), holder.getListModelFilter());
        listViewFilter.setAdapter(adapterFilter);
        listViewFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {

                    case 0:
                        Intent intent = new Intent(getActivity(), GeoMapsActivity.class);
                        intent.putExtra(KEY, TAG);
                        intent.putExtra(UserEntity.class.getCanonicalName(), getActivity().getIntent().getParcelableExtra(UserEntity.class.getCanonicalName()));
                        startActivity(intent);
                        break;
                }

            }
        });

        builder.setView(v);
        return builder.create();
    }

}
