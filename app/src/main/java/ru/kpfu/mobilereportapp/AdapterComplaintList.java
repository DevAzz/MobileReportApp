package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Адаптер для списка жалоб
 * Created by Azz on 09.03.2015.
 */
public class AdapterComplaintList extends ArrayAdapter<ComplaintModel> {
    private final List<ComplaintModel> list;
    private final Activity context;

    AdapterComplaintList(Activity context, List<ComplaintModel> list) {
        super(context, R.layout.my_list, list);
        this.context = context;
        this.list = list;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.my_list, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.textComplaint = (TextView) view.findViewById(R.id.textViewTextComplaint);
            viewHolder.status = (TextView) view.findViewById(R.id.textViewStatus);
            viewHolder.countComments = (TextView) view.findViewById(R.id.textViewCountComments);
            viewHolder.rating = (TextView) view.findViewById(R.id.textViewRating);
            viewHolder.imageButtonComments = (ImageButton) view.findViewById(R.id.imageButtonComments);
            viewHolder.imageButtonComments.setFocusable(false);
            viewHolder.imageButtonRating = (ImageButton) view.findViewById(R.id.imageButtonRating);
            viewHolder.imageButtonRating.setFocusable(false);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.textComplaint.setText(list.get(position).get_text());
        holder.status.setText(list.get(position).get_status());
        holder.countComments.setText(String.valueOf(list.get(position).get_countComment()));
        holder.rating.setText(String.valueOf(list.get(position).get_rating()));
        return view;
    }

    static class ViewHolder {
        protected TextView textComplaint;
        protected TextView status;
        protected TextView countComments;
        protected TextView rating;
        protected ImageButton imageButtonComments;
        protected ImageButton imageButtonRating;

    }

}

