package ru.kpfu.mobilereportapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.kpfu.mobilereportapp.Entity.UserEntity;


public class LogInActivity extends ActionBarActivity {

    public static final String APP_PREFERENCES = "mysettings";

    private static final String PATH_KEY = "photo_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText textEditName = (EditText) findViewById(R.id.editTextlogin);
        EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        Button button = (Button) findViewById(R.id.buttonLogIn);

        SharedPreferences sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        // TODO Временно
        final UserEntity user = new UserEntity(0, "Тестовый Юзер", "ВШ ИТИС");
        user.setAvatarPath(sPref.getString(PATH_KEY, ""));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(LogInActivity.this, ComplaintActivity.class);
                    intent.putExtra(UserEntity.class.getCanonicalName(), user);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
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
