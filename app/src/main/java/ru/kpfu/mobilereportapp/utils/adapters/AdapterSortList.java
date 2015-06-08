package ru.kpfu.mobilereportapp.utils.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.mobilereportapp.R;
import ru.kpfu.mobilereportapp.entities.DialogSortEntity;

/**
 * Created by Azz on 25.04.2015.
 */
public class AdapterSortList extends ArrayAdapter<DialogSortEntity> {

    private List<DialogSortEntity> list;

    private final Activity context;

    public AdapterSortList (Activity context, List<DialogSortEntity> list) {
        super(context, R.layout.list_item_sort, list);
        this.context = context;
        if(list != null) this.list = list;
        else list = new ArrayList<DialogSortEntity>();
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view;
       if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.list_item_sort, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewNameSort = (TextView) view.findViewById(R.id.textViewTypeSort);
            viewHolder.imageViewIconSort = (ImageView) view.findViewById(R.id.imageViewSort);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.imageViewIconSort.setImageURI(list.get(position).get_iconSort());
        holder.textViewNameSort.setText(list.get(position).get_nameSort());

        return view;
    }

    static class ViewHolder {
        protected TextView textViewNameSort;
        protected ImageView imageViewIconSort;
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
