package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Адаптер для списка комментариев
 * Created by Azz on 01.04.2015.
 */
public class AdapterCommentsList extends ArrayAdapter<CommentModel> {
    private final List<CommentModel> list;
    private final Activity context;

    public AdapterCommentsList(Activity context, List<CommentModel> list) {
        super(context, R.layout.comment_layout, list);
        this.context = context;
        this.list = list;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.comment_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            viewHolder.nameUser = (TextView) view.findViewById(R.id.textViewUser);
            viewHolder.dateComment = (TextView) view.findViewById(R.id.textViewDate);
            viewHolder.textComment = (TextView) view.findViewById(R.id.textViewTextComment);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.nameUser.setText(list.get(position).getNameUser());
        holder.dateComment.setText(list.get(position).getDateComment());
        holder.textComment.setText(String.valueOf(list.get(position).getTextComment()));
        return view;
    }

    static class ViewHolder {
        protected ImageView imageViewAvatar;
        protected TextView dateComment;
        protected TextView nameUser;
        protected TextView textComment;

    }
}
