package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import ru.kpfu.mobilereportapp.Entity.BuildingEntity;
import ru.kpfu.mobilereportapp.Entity.ComplaintEntity;
import ru.kpfu.mobilereportapp.Entity.UserEntity;


public class AddComplaintActivity extends ActionBarActivity {

    public static String TAG = "AddComplaintActivity";

    public static String KEY = "Activity";

    private Drawer.Result drawerResult = null;

    private ImageCameraHolder cameraHolder;

    public static final String APP_PREFERENCES = "mysettings";

    private UserEntity user;

    private ImageView mImageView;

    final int DIALOG_EXIT = 1;

    private ComplaintEntity mComplaint;

    private Integer [] floors = {1, 2, 3, 4, 5};

    private BuildingEntity building;

    private boolean flagEnabledSpinner;

    private Spinner spinner;

    public static final String SELECT_BUILDING = "selectBuilding";

    // просто почему бы и нет
    PropertyChangeListener listener = new PropertyChangeListener() {

        @Override
        public void propertyChange(PropertyChangeEvent event) {

            if (flagEnabledSpinner) {
                spinner.setEnabled(true);
            } else {
                spinner.setEnabled(false);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        setContentView(R.layout.activity_add_complaint);

        user = getIntent().getParcelableExtra(UserEntity.class.getCanonicalName());

        mImageView = (ImageView) findViewById(R.id.imageViewComplaint);
        Button buttonAddPhoto = (Button) findViewById(R.id.buttonAddPhoto);
        EditText editTextComplaint = (EditText) findViewById(R.id.editTextComplaint);
        Button buttonSend = (Button) findViewById(R.id.buttonSend);

        cameraHolder = new ImageCameraHolder (this, mImageView);

        // адаптер
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, floors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinnerFloor);
        spinner.setPrompt("Выберете этаж");
        spinner.setEnabled(false);
        spinner.setAdapter(adapter);



        Button buttonSelectBuilding = (Button) findViewById(R.id.buttonSelectBuilding);
        buttonSelectBuilding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (spinner.isEnabled()) {
                    setFlagEnabledSpinner(false);
                } else {
                    setFlagEnabledSpinner(true);
                }

                Intent intent = new Intent(AddComplaintActivity.this, GeoMapsActivity.class);
                intent.putExtra(KEY, TAG);
                intent.putExtra(UserEntity.class.getCanonicalName(), user);
                startActivity(intent);
            }
        });

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

    private void setFlagEnabledSpinner (boolean aFlagEnabledSpinner) {
        notifyListeners(this, SELECT_BUILDING, this.flagEnabledSpinner, this.flagEnabledSpinner = aFlagEnabledSpinner);
    }

    private void notifyListeners(Object object, String property, boolean oldValue, boolean newValue) {
        listener.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
    }

    private boolean isFlagEnabledSpinner() {
        return flagEnabledSpinner;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sPref = getSharedPreferences(APP_PREFERENCES , MODE_PRIVATE);
        NavigationDrawerComplaint drawerComplaint = new NavigationDrawerComplaint(drawerResult, this, user, sPref);
        // Инициализируем Navigation Drawer
        drawerResult = drawerComplaint.init();
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