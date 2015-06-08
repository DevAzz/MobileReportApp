package ru.kpfu.mobilereportapp.views;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import ru.kpfu.mobilereportapp.R;

/**
 * Created by Azz on 01.06.2015.
 */
public class PhotoDialog extends DialogFragment {

    final String LOG_TAG = "myLogs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View v = inflater.inflate(R.layout.complaint_photo_dialog_layout, null);
       String title = bundle.getString("title");
        if (null != title) {
            getDialog().setTitle(bundle.getString("title"));
        } else {
            getDialog().setTitle("Здание не выбрано");
        }



        ImageView imageView = (ImageView) v.findViewById(R.id.imageViewPhotoDialog);

        File file = new File(bundle.getString("path"));
        Picasso.with(getActivity()).load(file).into(imageView);
        return v;
    }
}
