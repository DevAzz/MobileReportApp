package ru.kpfu.mobilereportapp.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.kpfu.mobilereportapp.R;
import ru.kpfu.mobilereportapp.entities.BuildingEntity;
import ru.kpfu.mobilereportapp.entities.UserEntity;
import ru.kpfu.mobilereportapp.utils.holders.ImageCameraHolder;


public class AddComplaintActivity extends ActionBarActivity implements DialogSortSelectFloors.OnCompleteListener  {

    /** */
    private static final String PATH_KEY = "complaint_photo_path";

    /** */
    private static final LinearLayout.LayoutParams DEFAULT_WEIGHT_LAYOUT_TEXT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 15f);

    /** */
    private static final LinearLayout.LayoutParams MIN_WEIGHT_LAYOUT_TEXT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 2f);

    /** */
    private static final LinearLayout.LayoutParams WEIGHT_LAYOUT_PARAMS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 3f);

    public static String TAG = "AddComplaintActivity";

    public static String KEY = "Activity";

    private Drawer.Result drawerResult = null;

    private ImageCameraHolder cameraHolder;

    public static final String APP_PREFERENCES = "mysettings";

    private UserEntity user;

    private ImageView mImageViewPhotoComplaint;

    final int DIALOG_EXIT = 1;

    private BuildingEntity building;

    private boolean flagEnabledSpinner;

    public static final String SELECT_BUILDING = "selectBuilding";

    private android.support.v7.app.ActionBar actionBar;

    private LinearLayout linearLayoutEditText;

    private LinearLayout linearLayoutParamsComplaint;

    private TextView textViewNameBuilding;

    private TextView textViewAddressBuilding;

    private TextView textViewFloor;

    private ImageView imageViewMap;

    private ImageView imageViewAddPhoto;

    private ImageView imageViewFloor;

    private ImageView imageViewSend;

    private SharedPreferences sPref;

    private DialogSortSelectFloors dialog = new DialogSortSelectFloors();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_complaint2);

            init();
            addListeners();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> convertIntArrIntoList(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int val : arr) {
            list.add(val);
        }
        return list;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        NavigationDrawerComplaint drawerComplaint = new NavigationDrawerComplaint(drawerResult, this, user, sPref);
        // Инициализируем Navigation Drawer
        drawerResult = drawerComplaint.init();
        Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 0) {
                sPref = getPreferences(MODE_PRIVATE);
                cameraHolder.handleBigCameraPhoto(sPref);
                linearLayoutEditText.setLayoutParams(MIN_WEIGHT_LAYOUT_TEXT);
                linearLayoutParamsComplaint.setLayoutParams(WEIGHT_LAYOUT_PARAMS);
            } else if (requestCode == 1) {
                linearLayoutEditText.setLayoutParams(MIN_WEIGHT_LAYOUT_TEXT);
                linearLayoutParamsComplaint.setLayoutParams(WEIGHT_LAYOUT_PARAMS);
                File file = new File(getAbsolutePath(data.getData()));
                Picasso.with(this).load(file).resizeDimen(R.dimen.image_size_w, R.dimen.image_size_h).into(mImageViewPhotoComplaint);

                sPref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(PATH_KEY, file.getAbsolutePath());
                ed.commit();
            }
        }
    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
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
            adb.setIcon(R.drawable.ic_image_black_48dp);
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

    public android.support.v7.app.ActionBar createActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
        actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_white));
        actionBar.setCustomView(R.layout.add_complaint_custom_actionbar);
        return actionBar;
    }

    /**
     * Метод, инициализирующий компоненты
     */
    private void init() {
        actionBar = createActionBar();
        linearLayoutEditText = (LinearLayout) findViewById(R.id.linearLayoutEditText);
        linearLayoutParamsComplaint = (LinearLayout) findViewById(R.id.linearLayoutParamsComplaint);
        textViewNameBuilding = (TextView) findViewById(R.id.textViewNameBuilding);
        textViewAddressBuilding = (TextView) findViewById(R.id.textViewAddressBuilding);
        textViewFloor = (TextView) findViewById(R.id.textViewFloor);
        imageViewMap = (ImageView) findViewById(R.id.imageViewMap);
        imageViewAddPhoto = (ImageView) findViewById(R.id.imageViewAddPhoto);
        imageViewFloor = (ImageView) findViewById(R.id.imageViewFloor);
        imageViewSend = (ImageView) findViewById(R.id.imageViewAddComplaint);
        mImageViewPhotoComplaint = (ImageView) findViewById(R.id.imageViewAddComplaintPhoto);

        cameraHolder = new ImageCameraHolder(this, mImageViewPhotoComplaint);

        sPref = getPreferences(MODE_PRIVATE);

        user = getIntent().getParcelableExtra(UserEntity.class.getCanonicalName());
        building = getIntent().getParcelableExtra(BuildingEntity.class.getCanonicalName());
        String whence = getIntent().getStringExtra(KEY);
        if ("MapFragment".equals(whence)) {
            linearLayoutEditText.setLayoutParams(MIN_WEIGHT_LAYOUT_TEXT);
            linearLayoutParamsComplaint.setLayoutParams(WEIGHT_LAYOUT_PARAMS);
            textViewNameBuilding.setText(building.getName());
            textViewAddressBuilding.setText(building.getAddress());

            if (!sPref.getString(PATH_KEY, "").isEmpty()) {
                File file = new File(sPref.getString(PATH_KEY, ""));
                Picasso.with(this).load(file).resizeDimen(R.dimen.image_size_pref_w, R.dimen.image_size_pref_h).into(mImageViewPhotoComplaint);
            }
        } else {
            SharedPreferences.Editor ed = sPref.edit();
            ed.clear();
            ed.commit();
        }
    }

    /**
     * Метод, устанавливающий слушатели элементам управления
     */
    private void addListeners() {
        imageViewMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddComplaintActivity.this, GeoMapsActivity.class);
                intent.putExtra(KEY, TAG);
                intent.putExtra(UserEntity.class.getCanonicalName(), user);
                startActivity(intent);

            }
        });
        imageViewAddPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DIALOG_EXIT);
            }
        });
        imageViewFloor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                    if (null != building) {
                        if (null != building.getFloors()) {
                            bundle.putIntegerArrayList("floors", convertIntArrIntoList(building.getFloors()));
                            dialog.setArguments(bundle);
                            dialog.show(getFragmentManager(), "dialog");
                        } else {
                            Toast.makeText(AddComplaintActivity.this, "Нет данных по этажам у данного здания", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddComplaintActivity.this, R.string.headerDialogSelectFloorsFalse, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        imageViewSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                System.out.println(dateFormat.format(new Date()));

            }
        });
        mImageViewPhotoComplaint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PhotoDialog dlg = new PhotoDialog();
                Bundle bundle = new Bundle();
                bundle.putString("path", sPref.getString(PATH_KEY, ""));
                if (null != building) {
                    bundle.putString("title", building.getName());
                }
                dlg.setArguments(bundle);
                dlg.show(getFragmentManager(), "dlgPhoto");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor ed = sPref.edit();
        ed.clear();
        ed.commit();
    }

    @Override
    public void onComplete(String time) {
        textViewFloor.setText(time + " этаж");
    }
}