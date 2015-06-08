package ru.kpfu.mobilereportapp.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.kpfu.mobilereportapp.R;
import ru.kpfu.mobilereportapp.entities.UserEntity;
import ru.kpfu.mobilereportapp.utils.Tasks.LoginTask;


public class LogInActivity extends ActionBarActivity {

    private static final String TAG = "LogInActivity";

    public static final String APP_PREFERENCES = "mysettings";

    private static UserEntity currentUser;

    private android.support.v7.app.ActionBar actionBar;

    private SharedPreferences sPref;

    private LoginTask loginTask;

    private int flagCountClickLogin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
            sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
            actionBar = createActionBar();

            // В случае, если пользователь перевернет устройство во время выполнения запроса
            loginTask = (LoginTask) getLastCustomNonConfigurationInstance();
            if (null == loginTask) {
                loginTask = new LoginTask();
            }
            loginTask.link(LogInActivity.this);

            final EditText textEditName = (EditText) findViewById(R.id.editTextlogin);
            final EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
            Button buttonLogin = (Button) findViewById(R.id.buttonLogIn);


            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flagCountClickLogin++;
                    String name = textEditName.getText().toString();
                    String password = editTextPass.getText().toString();
                    if ((!name.isEmpty()) && (!password.isEmpty())) {


                       if (flagCountClickLogin > 1) {
                           loginTask = new LoginTask();
                       }
                       loginTask.execute(name, password);


                    } else {
                        Toast.makeText(LogInActivity.this, R.string.emptyFieldsMessage, Toast.LENGTH_SHORT).show();
                    }

                }
            });

            Button buttonReg = (Button) findViewById(R.id.buttonReg);
            buttonReg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LogInActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                }
            });
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        try {
            // удаляем из MyTask ссылку на старое Activity при пересоздатнии после поворота
            loginTask.unLink();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginTask;
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

    public android.support.v7.app.ActionBar createActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
        actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_white));
        actionBar.setCustomView(R.layout.login_custom_actionbar);
        return actionBar;
    }

    public SharedPreferences getPref() {
        return sPref;
    }

    public static void setCurrentUser(UserEntity currentUser) {
        LogInActivity.currentUser = currentUser;
    }
}
