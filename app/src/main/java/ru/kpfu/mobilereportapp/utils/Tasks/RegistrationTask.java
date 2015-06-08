package ru.kpfu.mobilereportapp.utils.Tasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import ru.kpfu.mobilereportapp.entities.UserEntity;
import ru.kpfu.mobilereportapp.utils.server.ServerApi;
import ru.kpfu.mobilereportapp.utils.server.exeptions.CurrentUserException;
import ru.kpfu.mobilereportapp.utils.server.exeptions.LoginException;
import ru.kpfu.mobilereportapp.utils.server.exeptions.RegistrationUserException;
import ru.kpfu.mobilereportapp.views.ComplaintActivity;
import ru.kpfu.mobilereportapp.views.RegistrationActivity;

/**
 * Created by Azz on 06.06.2015.
 */
public class RegistrationTask extends AsyncTask<String, Void, UserEntity> {

    private static final String TAG = "RegistrationTask";

    private RegistrationActivity mActivity;

    private String mExceptionMessage = null;

    private SharedPreferences sPref;

    // получаем ссылку на Activity
    public void link(RegistrationActivity act) {
        mActivity = act;
        this.sPref = mActivity.getPref();
    }

    // обнуляем ссылку
    public void unLink() {
        mActivity = null;
    }

    @Override
    protected UserEntity doInBackground(String... params) {
        String responseUser = "";
        UserEntity user = null;
        try {
            ServerApi.regUser(params[0], Integer.valueOf(params[1]), params[2], params[3]);

            ServerApi.login(params[0], params[2]);
            responseUser = ServerApi.getCurrentUser();
            user = ServerApi.createCurrentUser(responseUser, sPref);
        } catch (RegistrationUserException e) {
            Log.e(TAG, e.getMessage(), e);
            mExceptionMessage = e.getMessage();
            return null;
        } catch (CurrentUserException e) {
            Log.e(TAG, e.getMessage(), e);
            mExceptionMessage = e.getMessage();
            return null;
        } catch (LoginException e) {
            Log.e(TAG, e.getMessage(), e);
            mExceptionMessage = e.getMessage();
            return null;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            mExceptionMessage = e.getMessage();
            return null;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            mExceptionMessage = e.getMessage();
            return null;
        }

        return user;
    }

    @Override
    protected void onPostExecute(UserEntity result) {
        super.onPostExecute(result);
        //TODO Временно, пока не отдает пользователя
//            if (null == mExceptionMessage) {
        try {
            Intent intent = new Intent(mActivity, ComplaintActivity.class);
            intent.putExtra(UserEntity.class.getCanonicalName(), result);
            mActivity.startActivity(intent);
        } catch (Exception e) {

        }

//            } else {
//                Toast.makeText(mActivity, mExceptionMessage, Toast.LENGTH_SHORT).show();
//            }

    }
}
