package ru.kpfu.mobilereportapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;


public class DetailComplaintActivity extends ActionBarActivity {

    private static final String TAG = "DetailComplaintActivity";
    private Drawer.Result drawerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_complaint2);

        NavigationDrawerComplaint drawerComplaint = new NavigationDrawerComplaint(drawerResult, this);
        // Инициализируем Navigation Drawer
        drawerResult=drawerComplaint.init();

        ImageView imageView = (ImageView) findViewById(R.id.imageViewAvatar);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        ListView listViewComments = (ListView) findViewById(R.id.listViewComments);
        TextView textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);

        CommentHolder comment = new CommentHolder();

        ArrayAdapter<CommentModel> adapter = new AdapterCommentsList(this,
                comment.getListModel());



        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View header = inflater.inflate(R.layout.activity_detail_complaint_list_view_header, null);
        TextView textViewText = (TextView) header.findViewById(R.id.textViewText);
        textViewText.setText("dfgfgdgdgdfeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        listViewComments.addHeaderView(header);

        listViewComments.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_complaint, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
