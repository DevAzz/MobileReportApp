package ru.kpfu.mobilereportapp.views;

import android.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.List;

import ru.kpfu.mobilereportapp.R;
import ru.kpfu.mobilereportapp.entities.BuildingEntity;
import ru.kpfu.mobilereportapp.entities.CommentEntity;
import ru.kpfu.mobilereportapp.entities.ComplaintEntity;
import ru.kpfu.mobilereportapp.entities.UserEntity;
import ru.kpfu.mobilereportapp.utils.adapters.AdapterComplaintList;


public class UserComplaintActivity extends ActionBarActivity {

    private Drawer.Result drawerResult = null;

    public static final String KEY = "Activity";

    public static final String TAG = "UserComplaintActivity";

    public static final String APP_PREFERENCES = "mysettings";

    private  android.support.v7.app.ActionBar actionBar;

    UserEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        actionBar = createActionBar();

        user = getIntent().getParcelableExtra(UserEntity.class.getCanonicalName());

        final DialogSort dlgSort = new DialogSort();

        final FragmentManager manager = getFragmentManager();

        final ListView listview = (ListView) findViewById(R.id.listView);
        ArrayAdapter<ComplaintEntity> adapter = new AdapterComplaintList(this,
                getModel());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ComplaintEntity complaint = (ComplaintEntity) listview.getAdapter().getItem(position);
                Intent intent = new Intent(UserComplaintActivity.this, DetailComplaintActivity.class);
                intent.putExtra(ComplaintEntity.class.getCanonicalName(), complaint);
                intent.putExtra(UserEntity.class.getCanonicalName(), user);
                startActivity(intent);
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

        ImageView imageViewAddComplaint = (ImageView) findViewById(R.id.imageViewAddProposalUserComplaint);

        imageViewAddComplaint.setOnClickListener(new View.OnClickListener()

                                                 {
                                                     public void onClick(View v) {
                                                         Intent intent = new Intent(UserComplaintActivity.this, AddComplaintActivity.class);
                                                         intent.putExtra(UserEntity.class.getCanonicalName(), user);
                                                         intent.putExtra(KEY, TAG);
                                                         startActivity(intent);
                                                     }
                                                 }

        );

        ImageView imageViewSort = (ImageView) findViewById(R.id.imageViewSort);
        imageViewSort.setOnClickListener(new View.OnClickListener()

                                         {
                                             public void onClick(View v) {
                                                 dlgSort.show(manager, DialogSort.TAG);
                                             }
                                         }

        );
    }

    private List<ComplaintEntity> getModel() {
        List<ComplaintEntity> list = new ArrayList<>();
        ArrayList<CommentEntity> comments = new ArrayList<>();
        comments.add(new CommentEntity(user.getNameUser(), "ывмымыв", "24.05.2015"));
        comments.add(new CommentEntity(user.getNameUser(), "sbvsbsdb", "24.05.2015"));
        //FIXME Временно
        list.add(get(0,getPackageName() + ":drawable/answer", "Жалоба", comments, "HelloWorld!!1", " В процессе", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,getPackageName() + ":drawable/header", "Жалоба", comments, "ффффф", " Решено", "12.02.2116", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,getPackageName() + ":drawable/header_logo", "Жалоба", comments, "ммммммммммммммм", " Решено", "41.99.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,getPackageName() + ":drawable/ic_launcher", "Жалоба", comments, "витвиваиваиваиваиваиваи", " Решено", "30.05.2013", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,getPackageName() + ":drawable/answer", "Жалоба", comments, "ывмыипватарьпголбпртваиывмвы", " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,getPackageName() + ":drawable/answer", "Жалоба", comments, "чвамиывмывм ывиывиыв", " Решено", "23.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,getPackageName() + ":drawable/answer", "Жалоба", comments, "ывпывриваиваиватват", " Решено", "24.02.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        list.add(get(0,getPackageName() + ":drawable/answer", "Жалоба", comments, "ывмывмыв вымывмывм ывмывмыв вымывмыв ывмывпмывмиsvoisndv sdvnosvnosdnvs sodfvsdnvosndvnsdvsvsdvnsdvnsdnvsdv sdolnvoisndvoisdnvisdv sdifvpsdnvsdinvpsdv soldivnsidnvisdnvlksdv lskdnvsdnvklnsdkvsd voinsdpvnpsdnvpsdnv sldknvpsndvpnsdkvn sdlkvnpsndvlsdnvklsdnv slkdnvksndvkl;nsdkl;v lsdk vlknsdlkvnklsdnvksd olksndvlnsdlkvnlskdvb;lsdkb;sdv  sldvsdknv;ksd;kvns dlksndvlknsdkl;vnsk;dvnmsd vsdklvnsd;klvnk;sdvnk;sdv sdklvnsdk;vn;sdknv;sdknvk;sdnvk;sdnv;sf;blmsd; sdv",
                " Решено", "24.05.2015", user, new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)), 100));
        return list;
    }

    private ComplaintEntity get(int id, String photo, String title, ArrayList<CommentEntity> comments, String description, String status, String date, UserEntity owner, BuildingEntity location, int rating) {
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

    public android.support.v7.app.ActionBar createActionBar () {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
        actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_white));
        actionBar.setCustomView(R.layout.user_complaint_custom_actionbar);
        return actionBar;
    }
}
