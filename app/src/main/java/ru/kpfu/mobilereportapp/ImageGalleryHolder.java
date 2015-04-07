package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.widget.ImageView;

/**
 * Created by Azz on 30.03.2015.
 */
public class ImageGalleryHolder {

    private Activity _activity;
    private ImageView mImageView;

    public ImageGalleryHolder(Activity aCtivity, ImageView aImageView) {
        _activity = aCtivity;
        mImageView = aImageView;
    }

    public void readImage() {

    }
}
