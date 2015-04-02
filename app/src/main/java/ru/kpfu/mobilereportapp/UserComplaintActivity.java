package ru.kpfu.mobilereportapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.List;


public class UserComplaintActivity extends ActionBarActivity {

    private Drawer.Result drawerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        final ListView listview = (ListView) findViewById(R.id.listView);
        ArrayAdapter<ComplaintModel> adapter = new AdapterComplaintList(this,
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
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
        actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_actionbar);
        ImageButton buttonAddComplaint = (ImageButton) findViewById(R.id.imageButtonAddComplaint);

        buttonAddComplaint.setOnClickListener(new View.OnClickListener()

                                              {
                                                  public void onClick(View v) {
                                                      Intent intent = new Intent(UserComplaintActivity.this, AddComplaintActivity.class);
                                                      startActivity(intent);
                                                  }
                                              }

        );

        NavigationDrawerComplaint drawerComplaint = new NavigationDrawerComplaint(drawerResult, this);
        // Инициализируем Navigation Drawer
        drawerResult = drawerComplaint.init();
    }

    private List<ComplaintModel> getModel() {
        List<ComplaintModel> list = new ArrayList<>();
        //FIXME Временно
        list.add(get("Текст жалобы", "В процессе", 0, 0));
        list.add(get("Текст жалобы2", "Решено", 1, 1));
        return list;
    }

    private ComplaintModel get(String aTextComplaint, String aStatus, int aCountComments, int aRating) {
        return new ComplaintModel(aTextComplaint, aStatus, aCountComments, aRating);
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
}
