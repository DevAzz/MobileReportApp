package ru.kpfu.mobilereportapp.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import ru.kpfu.mobilereportapp.R;
import ru.kpfu.mobilereportapp.entities.CommentEntity;
import ru.kpfu.mobilereportapp.entities.ComplaintEntity;
import ru.kpfu.mobilereportapp.entities.UserEntity;
import ru.kpfu.mobilereportapp.utils.adapters.AdapterCommentsList;


public class DetailComplaintActivity extends ActionBarActivity {

    public static final String KEY = "Activity";

    /** */
    private static final String PATH_KEY = "complaint_photo_path";

    private static final String TAG = "DetailComplaintActivity";

    public static final String APP_PREFERENCES = "mysettings";

    private Drawer.Result drawerResult = null;

    private UserEntity user;

    private  android.support.v7.app.ActionBar actionBar;

    /** */
    private ComplaintEntity complaint;

    /** */
    private ImageView imageView;

    /** */
    private TextView textViewDate;

    /** */
    private TextView textViewAuthor;

    /** */
    private TextView textViewNameBuilding;

    /** */
    private TextView textViewAddressBuilding;

    /** */
    private TextView textViewFloor;

    /** */
    private ListView listViewComments;

    /** */
    private TextView textViewStatus;

    private SharedPreferences sPref;

    /** */
    private com.sothree.slidinguppanel.SlidingUpPanelLayout sliding_layout;

    /** */
    private Button buttonAddComment;

    /** */
    private ImageView imageViewAddProposalDetailComplaint;

    /** */
    private TextView textViewNameUserAddCommentPanel;

    /** */
    private  TextView textViewDateAddCommentPanel;

    /** */
    private EditText editTextAddCommentPanel;

    /** */
    private ArrayAdapter<CommentEntity> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_complaint_slide_add_comment);
            init();
            setValues();
            setListeners ();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init () {

        sPref = getPreferences(MODE_PRIVATE);

        user = getIntent().getParcelableExtra(UserEntity.class.getCanonicalName());

        actionBar = createActionBar();

        Intent intent = getIntent();
        intent.setExtrasClassLoader(ComplaintEntity.class.getClassLoader());
        complaint = intent.getParcelableExtra(ComplaintEntity.class.getCanonicalName());

        imageView = (ImageView) findViewById(R.id.imageViewProposalDetailComplaint);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        listViewComments = (ListView) findViewById(R.id.listViewComments);
        textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);
        textViewNameBuilding = (TextView) findViewById(R.id.textViewAddressBuildingDetal);
        textViewAddressBuilding = (TextView) findViewById(R.id.textViewNameBuildingDetal);
        textViewFloor = (TextView) findViewById(R.id.textViewFloorDetal);
        textViewStatus = (TextView) findViewById(R.id.textViewStatusDetal);
        imageViewAddProposalDetailComplaint = (ImageView) findViewById(R.id.imageViewAddProposalDetailComplaint);

        //AddCommentPanel
        sliding_layout = (com.sothree.slidinguppanel.SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        buttonAddComment =(Button) findViewById(R.id.buttonAddCommentDetailComplaint);
        textViewNameUserAddCommentPanel = (TextView) findViewById(R.id.textViewNameUserAddCommentPanel);
        textViewDateAddCommentPanel = (TextView) findViewById(R.id.textViewDateAddCommentPanel);
        editTextAddCommentPanel = (EditText) findViewById(R.id.editTextAddCommentPanel);
    }

    private void setValues () {
        if (null != complaint.getOwner()) {
            if (null != complaint.getOwner().getNameUser()) {
                textViewAuthor.setText(complaint.getOwner().getNameUser());
                textViewNameUserAddCommentPanel.setText(complaint.getOwner().getNameUser());
            }
        }

        if (null != complaint.getDate()) {
            textViewDate.setText(complaint.getDate());
            textViewDateAddCommentPanel.setText(complaint.getDate());
        }

        if (null != complaint.getLocation()) {
            if (null != complaint.getLocation().getName()) {
                textViewNameBuilding.setText(complaint.getLocation().getName());
            }
            if (null != complaint.getLocation().getAddress()) {
                textViewAddressBuilding.setText(complaint.getLocation().getAddress());
            }
        }

        if (null != complaint.getStatus()) {
            textViewStatus.setText(complaint.getStatus());
        }

        if (null != complaint.getPhoto()) {
            // TODO Временное решение
            imageView.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(complaint.getPhoto(), null, null)));
        }

        adapter = new AdapterCommentsList(this, complaint.getComments());
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View header = inflater.inflate(R.layout.activity_detail_complaint_list_view_header, null);
        TextView textViewHeaderTextComplaint = (TextView) header.findViewById(R.id.textViewHeaderTextComplaint);
        if (null != complaint.getTitle()) {
            textViewHeaderTextComplaint.setText(complaint.getTitle());
        }
        TextView textViewText = (TextView) header.findViewById(R.id.textViewText);
        if (null != complaint.getDescription()) {
            textViewText.setText(complaint.getDescription());
        }

        listViewComments.addHeaderView(header);
        listViewComments.setAdapter(adapter);

        sliding_layout.setAnchorPoint(0.4f);
        sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

    }

    /**
     * Метод, устанавливающий слушатели элементам управления
     */
    private void setListeners () {

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PhotoDialog dlg = new PhotoDialog();
                Bundle bundle = new Bundle();
                bundle.putString("path", Uri.parse(complaint.getPhoto()).toString());
                if (null != complaint.getLocation()) {
                    bundle.putString("title", complaint.getLocation().getName());
                }
                dlg.setArguments(bundle);
                dlg.show(getFragmentManager(), "dlgPhoto");
            }
        });

        imageViewAddProposalDetailComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailComplaintActivity.this, AddComplaintActivity.class);
                intent.putExtra(UserEntity.class.getCanonicalName(), user);
                intent.putExtra(KEY, TAG);
                startActivity(intent);
            }
        });
        buttonAddComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = textViewNameUserAddCommentPanel.getText().toString();
                String textComment =  editTextAddCommentPanel.getText().toString();
                String date = textViewDateAddCommentPanel.getText().toString();
                if ((sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)|| (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    if (!textComment.isEmpty()) {
                        CommentEntity comment = new CommentEntity(name, textComment, date);
                        adapter.add(comment);
                        complaint.getComments().add(comment);

                        //TODO post запрос
                    } else {
                        Toast.makeText(DetailComplaintActivity.this, R.string.emptyTextCommentMessage, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
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

    public android.support.v7.app.ActionBar createActionBar () {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
        actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_white));
        actionBar.setCustomView(R.layout.detail_complaint_custom_actionbar);
        return actionBar;
    }
}
