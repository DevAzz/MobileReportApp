package ru.kpfu.mobilereportapp.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.kpfu.mobilereportapp.R;
import ru.kpfu.mobilereportapp.utils.Tasks.RegistrationTask;

public class RegistrationActivity extends ActionBarActivity {

    public static final String APP_PREFERENCES = "mysettings";

    private static final String PATH_KEY = "photo_path";

    private static final String TAG = "RegistrationActivity";

    private EditText editTextLogin;

    private EditText editTextFacultyId;

    private EditText editTextPass;

    private EditText editTextConfirmPassword;

    private Button buttonReg;

    private ActionBar actionBar;

    private RegistrationTask regTask;

    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        setListeners();
    }

    private void init() {
        editTextLogin = (EditText) findViewById(R.id.editTextLoginReg);
        editTextFacultyId = (EditText) findViewById(R.id.editTextFacultyId);
        editTextPass = (EditText) findViewById(R.id.editTextPasswordReg);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        buttonReg = (Button) findViewById(R.id.buttonRegistrationReg);

        actionBar = createActionBar();
        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
    }

    private void setListeners() {
        buttonReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String login = editTextLogin.getText().toString();
                String facultyId = editTextFacultyId.getText().toString();
                String password = editTextPass.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                if ((!login.isEmpty()) && (!facultyId.isEmpty()) && (!password.isEmpty()) && (!confirmPassword.isEmpty())) {
                    // В случае, если пользователь перевернет устройство во время выполнения запроса
                    regTask = (RegistrationTask) getLastCustomNonConfigurationInstance();
                    if (null == regTask) {
                        regTask = new RegistrationTask();
                    }
                    regTask.link(RegistrationActivity.this);
                    regTask.execute(login, facultyId, password, confirmPassword);
                } else {
                    Toast.makeText(RegistrationActivity.this, R.string.emptyFieldsMessage, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    public android.support.v7.app.ActionBar createActionBar () {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //не показываем иконку приложения
        actionBar.setDisplayShowTitleEnabled(false); // и заголовок тоже прячем
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_white));
        actionBar.setCustomView(R.layout.reg_custom_actionbar);
        return actionBar;
    }

    public SharedPreferences getPref() {
        return sPref;
    }
}
