package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import java.io.File;

import ru.kpfu.mobilereportapp.Entity.UserEntity;

/**
 * Created by Azz on 24.05.2015.
 */
public class SettingActivity extends ActionBarActivity {

    public static final String APP_PREFERENCES = "mysettings";

    private ImageView mImageView;

    private String mPhotoPath;

    private static final String DEFAULT_PATH = "android.resource://ru.kpfu.mobilereportapp/drawable/answer";

    private static final String PATH_KEY = "photo_path";

    private SharedPreferences sPref;

    private UserEntity user;

    private Drawer.Result drawerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
            sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
            user = (UserEntity) getIntent().getParcelableExtra(UserEntity.class.getCanonicalName());

            mImageView = (ImageView) findViewById(R.id.imageViewPhoto);

            mPhotoPath = sPref.getString(PATH_KEY, "");

            user.setAvatarPath(mPhotoPath);

            if (!mPhotoPath.isEmpty()) {
                File file = new File(mPhotoPath);
                Picasso.with(this).load(file).resizeDimen(R.dimen.image_size_pref_w, R.dimen.image_size_pref_h).into(mImageView);
            } else {
                File file = new File(DEFAULT_PATH);
                Picasso.with(this).load(file).resizeDimen(R.dimen.image_size_pref_w, R.dimen.image_size_pref_h).into(mImageView);
            }

            Button buttonLoadPhoto = (Button) findViewById(R.id.buttonPreferencesUserPhoto);
            buttonLoadPhoto.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(
                            intent,
                            1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
           if (requestCode == 1){
                File file = new File(getAbsolutePath(data.getData()));
                Picasso.with(this).load(file).resizeDimen(R.dimen.image_size_pref_w, R.dimen.image_size_pref_h).into(mImageView);
               mPhotoPath = file.getAbsolutePath();
               savePath ();
            }
        }
    }

    private void savePath () {
        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(PATH_KEY, mPhotoPath);
        ed.commit();
        Toast.makeText(this, "Фото сохранено", Toast.LENGTH_SHORT).show();
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
    protected void onStart() {
        super.onStart();
        NavigationDrawerComplaint drawerComplaint = new NavigationDrawerComplaint(drawerResult, this, user, sPref);
        // Инициализируем Navigation Drawer
        drawerResult = drawerComplaint.init();
    }
}
