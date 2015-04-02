package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Azz on 30.03.2015.
 */
public class AdditionPhotoGallery {

    private Activity _activity;
    private ImageView mImageView;

    public AdditionPhotoGallery (Activity aCtivity, ImageView aImageView) {
        _activity = aCtivity;
        mImageView = aImageView;
        readImage();
    }

    private void readImage() {
        int px =  _activity.getResources().getDimensionPixelSize(R.dimen.image_size);
        File file = new File(Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"map.jpg");
        Bitmap bitmap = decodeSampledBitmapFromResource(file.getAbsolutePath(), px, px);
        Log.d("log", String.format("Required size = %s, bitmap size = %sx%s, byteCount = %s",
                px, bitmap.getWidth(), bitmap.getHeight(), bitmap.getByteCount()));
        mImageView.setImageBitmap(bitmap);
    }

    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         int reqWidth, int reqHeight) {

        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
