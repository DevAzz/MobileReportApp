package ru.kpfu.mobilereportapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.mobilereportapp.Entity.BuildingEntity;
import ru.kpfu.mobilereportapp.Entity.ComplaintEntity;
import ru.kpfu.mobilereportapp.Entity.UserEntity;

public class ComplaintActivity extends ActionBarActivity {

    public static final String APP_PREFERENCES = "mysettings";
    private Drawer.Result drawerResult = null;
    FragmentManager manager;//  = getFragmentManager();
    private UserEntity user;
    ImageView imageViewLogo;

    public UserEntity getUser() {
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       try {
           super.onCreate(savedInstanceState);
           manager = getFragmentManager();
           setContentView(R.layout.activity_complaint);
           SharedPreferences sPref = getPreferences(MODE_PRIVATE);
           user = getIntent().getParcelableExtra(UserEntity.class.getCanonicalName());
           Toast.makeText(this, user.getAvatarPath(), Toast.LENGTH_SHORT).show();
           final ListView listview = (ListView) findViewById(R.id.listView);
           ArrayAdapter<ComplaintEntity> adapter = new AdapterComplaintList(this,
                   getModel());
           final DialogSort dlgSort = new DialogSort();

           listview.setAdapter(adapter);
           listview.setOnItemClickListener(new OnItemClickListener() {
               public void onItemClick(AdapterView<?> parent, View view,
                                       int position, long id) {
                   Intent intent = new Intent(ComplaintActivity.this, DetailComplaintActivity.class);
                   intent.putExtra(UserEntity.class.getCanonicalName(), user);
                   startActivity(intent);
               }
           });
           listview.setOnItemSelectedListener(new OnItemSelectedListener() {
               public void onItemSelected(AdapterView<?> parent, View view,
                                          int position, long id) {
               }

               public void onNothingSelected(AdapterView<?> parent) {
               }
           });
           android.support.v7.app.ActionBar actionBar = getSupportActionBar();
           actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
           actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
           actionBar.setDisplayShowCustomEnabled(true);
           actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_backgroundp));
           actionBar.setCustomView(R.layout.custom_actionbar);

           ImageButton buttonAddComplaint = (ImageButton) findViewById(R.id.imageButtonAddComplaint);

           buttonAddComplaint.setOnClickListener(new View.OnClickListener()

                                                 {
                                                     public void onClick(View v) {
                                                         Intent intent = new Intent(ComplaintActivity.this, AddComplaintActivity.class);
                                                         intent.putExtra(UserEntity.class.getCanonicalName(), user);
                                                         startActivity(intent);
                                                     }
                                                 }

           );

           ImageButton buttonSort = (ImageButton) findViewById(R.id.imageButtonSort);
           buttonSort.setOnClickListener(new View.OnClickListener()

                                         {
                                             public void onClick(View v) {
                                                 dlgSort.show(manager, DialogSort.TAG);
                                             }
                                         }

           );
       } catch ( Exception e) {
           e.printStackTrace();
       }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sPref = getSharedPreferences(APP_PREFERENCES , MODE_PRIVATE);
        NavigationDrawerComplaint drawerComplaint = new NavigationDrawerComplaint(drawerResult, this, user, sPref);
        // Инициализируем Navigation Drawer
        drawerResult = drawerComplaint.init();
    }

    private List<ComplaintEntity> getModel() {
        List<ComplaintEntity> list = new ArrayList<>();
        List<CommentModel> comments = new ArrayList<>();
        comments.add(new CommentModel(user.getNameUser(), "ывмымыв", "24.05.2015"));
        comments.add(new CommentModel(user.getNameUser(), "sbvsbsdb", "24.05.2015"));
        //FIXME Временно
        list.add(get(0,"", "Жалоба", comments, "sfbvsdvsvsvdscvdsvsbfbsbd", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,"", "Жалоба", comments, "sfbvsdvsvsvdscvdsvsbfbsbd", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,"", "Жалоба", comments, "sfbvsdvsvsvdscvdsvsbfbsbd", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,"", "Жалоба", comments, "sfbvsdvsvsvdscvdsvsbfbsbd", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,"", "Жалоба", comments, "sfbvsdvsvsvdscvdsvsbfbsbd", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,"", "Жалоба", comments, "sfbvsdvsvsvdscvdsvsbfbsbd", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,"", "Жалоба", comments, "sfbvsdvsvsvdscvdsvsbfbsbd", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,"", "Жалоба", comments, "sfbvsdvsvsvdscvdsvsbfbsbd", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        return list;
    }

    private ComplaintEntity get(int id, String photo, String title, List<CommentModel> comments, String description, String status, String date, UserEntity owner, BuildingEntity location, int rating) {
        return new ComplaintEntity(id, photo, title, comments, description, status, date, owner, location, rating);
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

    // Заглушка, работа с меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_complaint, menu);
        return true;
    }

    // Заглушка, работа с меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            try {
                Intent intent = new Intent(ComplaintActivity.this, SettingActivity.class);
                intent.putExtra(UserEntity.class.getCanonicalName(), user);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}