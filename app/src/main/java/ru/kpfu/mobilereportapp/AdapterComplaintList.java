package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.kpfu.mobilereportapp.Entity.ComplaintEntity;

/**
 * Адаптер для списка жалоб
 * Created by Azz on 09.03.2015.
 */
public class AdapterComplaintList extends ArrayAdapter<ComplaintEntity> {
    private final List<ComplaintEntity> list;
    private final Activity context;

    AdapterComplaintList(Activity context, List<ComplaintEntity> list) {
        super(context, R.layout.complaint_layout, list);
        this.context = context;
        this.list = list;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.complaint_layout, null);
            final ViewHolder holder = new ViewHolder();
            holder.textViewOwner = (TextView) view.findViewById(R.id.textViewOwner);
            holder.textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            holder.textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            holder.ivImage = (ImageView) view.findViewById(R.id.ivImage);
            holder.textViewTextComplaint = (TextView) view.findViewById(R.id.textViewTextComplaint);
            holder.textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
            holder.textViewCountComments =(TextView) view.findViewById(R.id.textViewCountComments);
            holder.imageViewCountComments = (ImageView) view.findViewById(R.id.imageViewCountComments);
            holder.textViewRating = (TextView) view.findViewById(R.id.textViewRating);
            holder.imageViewRating = (ImageView) view.findViewById(R.id.imageViewRating);
            view.setTag(holder);
        } else {
            view = convertView;
        }
        try {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.textViewOwner.setText(list.get(position).getOwner().getNameUser());
            holder.textViewTitle.setText(list.get(position).getTitle());
            holder.textViewDate.setText(list.get(position).getDate());
            //TODO Временно, исправить
            holder.ivImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
            holder.textViewTextComplaint.setText(list.get(position).getDescription());
            holder.textViewStatus.setText(list.get(position).getStatus());
            holder.textViewCountComments.setText(String.valueOf(list.get(position).getComments().size()));
            holder.imageViewCountComments.setImageDrawable(context.getResources().getDrawable(R.drawable.comment));
            holder.textViewRating.setText(String.valueOf(list.get(position).getRating()));
            holder.imageViewRating.setImageDrawable(context.getResources().getDrawable(R.drawable.star));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    static class ViewHolder {
        TextView textViewOwner;
        TextView textViewTitle;
        TextView textViewDate;
        ImageView ivImage;
        TextView textViewTextComplaint;
        TextView textViewStatus;
        TextView textViewCountComments;
        ImageView imageViewCountComments;
        TextView textViewRating;
        ImageView imageViewRating;
    }

}

