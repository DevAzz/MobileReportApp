package ru.kpfu.mobilereportapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.mobilereportapp.Entity.BuildingEntity;
import ru.kpfu.mobilereportapp.Entity.ComplaintEntity;
import ru.kpfu.mobilereportapp.Entity.UserEntity;


public class UserComplaintActivity extends ActionBarActivity {

    private Drawer.Result drawerResult = null;

    public static final String APP_PREFERENCES = "mysettings";

    UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        user = getIntent().getParcelableExtra(UserEntity.class.getCanonicalName());

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
        actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_actionbar);

        final ListView listview = (ListView) findViewById(R.id.listView);
        ArrayAdapter<ComplaintEntity> adapter = new AdapterComplaintList(this,
                getModel());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(UserComplaintActivity.this, DetailComplaintActivity.class);
                startActivity(intent);
                Log.e("LOG", "itemClick: position = " + position + ", id = "
                        + id);
            }
        });
        listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.e("LOG", "itemSelect: position = " + position + ", id = "
                        + id);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("LOG", "itemSelect: nothing");
            }
        });

        ImageButton buttonAddComplaint = (ImageButton) findViewById(R.id.imageButtonAddComplaint);

        buttonAddComplaint.setOnClickListener(new View.OnClickListener()

                                              {
                                                  public void onClick(View v) {
                                                      Intent intent = new Intent(UserComplaintActivity.this, AddComplaintActivity.class);
                                                      startActivity(intent);
                                                  }
                                              }

        );
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sPref = getSharedPreferences(APP_PREFERENCES , MODE_PRIVATE);
        NavigationDrawerComplaint drawerComplaint = new NavigationDrawerComplaint(drawerResult, this, user, sPref);
        // Инициализируем Navigation Drawer
        drawerResult = drawerComplaint.init();
    }
}
