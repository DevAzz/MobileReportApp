package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import java.io.File;


public class AddComplaintActivity extends ActionBarActivity {

    private Drawer.Result drawerResult = null;

    private ImageCameraHolder cameraHolder;

    private ImageGalleryHolder galleryHolder;

    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;

    private ImageView mImageView;

    final int DIALOG_EXIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        setContentView(R.layout.activity_add_complaint);

        mImageView = (ImageView) findViewById(R.id.imageViewComplaint);
        Button buttonAddPhoto = (Button) findViewById(R.id.buttonAddPhoto);
        EditText editTextComplaint = (EditText) findViewById(R.id.editTextComplaint);
        Button buttonSend = (Button) findViewById(R.id.buttonSend);

        cameraHolder = new ImageCameraHolder (this, mImageView);
        galleryHolder = new ImageGalleryHolder(this,mImageView);

        // Инициализируем Navigation Drawer
        NavigationDrawerComplaint drawerComplaint = new NavigationDrawerComplaint (drawerResult, this);
        drawerResult = drawerComplaint.init();

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        buttonAddPhoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_EXIT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 0) {
                cameraHolder.handleBigCameraPhoto();
            } else if (requestCode == 1){
                File file = new File(getAbsolutePath(data.getData()));
                Picasso.with(this).load(file).resizeDimen(R.dimen.image_size_w, R.dimen.image_size_h).into(mImageView);
            }
        }
    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EXIT) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            // заголовок
            adb.setTitle(R.string.exit);
            // сообщение
            adb.setMessage(R.string.save_data);
            // иконка
            adb.setIcon(android.R.drawable.ic_dialog_info);
            // кнопка положительного ответа
            adb.setPositiveButton(R.string.yes, myClickListener);
            // кнопка отрицательного ответа
            adb.setNegativeButton(R.string.no, myClickListener);
            // кнопка нейтрального ответа
            adb.setNeutralButton(R.string.cancel, myClickListener);
            // создаем диалог
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // положительная кнопка
                case Dialog.BUTTON_POSITIVE:
                    cameraHolder.dispatchTakePictureIntent();
                    break;
                // негаитвная кнопка
                case Dialog.BUTTON_NEGATIVE:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(
                            intent,
                            1);
                    break;
                // нейтральная кнопка
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };

}