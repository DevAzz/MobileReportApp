package ru.kpfu.mobilereportapp.utils.server;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ru.kpfu.mobilereportapp.entities.UserEntity;
import ru.kpfu.mobilereportapp.utils.server.exeptions.CurrentUserException;
import ru.kpfu.mobilereportapp.utils.server.exeptions.LoginException;
import ru.kpfu.mobilereportapp.utils.server.exeptions.RegistrationUserException;

/**
 * Created by azz on 02.06.2015.
 */
public class ServerApi {

    private static final String PATH_KEY = "photo_path";

    private static final String TAG = "ServerApi";

    private static String mToken;

    public static void cancel() {
        SynchronousRequest.cancel();
    }

    /**
     * Вход в систему
     *
     * @param userName введенное имя пользователя
     * @param password введенный пароль
     * @return если запрос прошел удачно - возвращаем токен, иначе - сообщение об ошибке
     * @throws IOException   исключение
     * @throws JSONException исключение
     */
    public static String login(String userName, String password) throws IOException, JSONException, LoginException {
        String url = "/api/login";
        String result = "";
        JSONObject loginParams = new JSONObject();

        loginParams.put("username", userName);
        loginParams.put("password", password);

        String responseStr = SynchronousRequest.get().post(url + addParametersLogin(userName, password), loginParams.toString());

        if (responseStr.contains("error")) {
            result = ServerApiHelper.getResponseErrorMessage(responseStr);
            throw new LoginException(result);
        } else {
            mToken = ServerApiHelper.getResponseSuccessResult(responseStr);
        }

        return result;

    }

    /**
     * Метод, возвращающий текущего пользователя
     *
     * @return id, имя и факультет текущего пользователя
     * @throws IOException   исключение
     * @throws JSONException исключение
     */
    public static String getCurrentUser() throws IOException, JSONException, CurrentUserException {
        String url = "/api/v1/user/current?token=" + mToken;
        String result = "";
        String responseStr = SynchronousRequest.get().get(url);
        if (responseStr.contains("error")) {
            result = ServerApiHelper.getResponseErrorMessage(responseStr);
            throw new CurrentUserException(result);
        } else {
            result = responseStr;
        }
        return result;
    }

    /**
     * Метод регистрации пользователя
     *
     * @param login           логин
     * @param facultyId       идентификатор факультета
     * @param password        введенный пароль
     * @param confirmPassword подтверждение пароля
     * @return ответ сервера
     */
    public static String regUser(String login, int facultyId, String password, String confirmPassword) throws JSONException, IOException, RegistrationUserException {
        String url = "/registration";
        String result = "";
        JSONObject regUser = new JSONObject();
        regUser.put("login", login);
        regUser.put("facultyID", facultyId);
        regUser.put("password", password);
        regUser.put("confirmPassword", confirmPassword);

        String responseStr = SynchronousRequest.get().post(url, regUser.toString());
        if (responseStr.contains("error")) {
            result = ServerApiHelper.getResponseErrorMessage(responseStr);
            throw new RegistrationUserException(responseStr);
        } else {
            result = responseStr;
        }
        return responseStr;
    }

    public static String getComplaints () throws JSONException, IOException {

        return null;
    }

    /**
     * Метод, добавляющий в url login запроса пользователя и пароль
     * @param username
     * @param password
     * @return
     */
    private static String addParametersLogin(String username, String password) {
        String params = "?username=" + username + "&password=" + password;
        return params;
    }

    public static UserEntity createCurrentUser (String responseUser,  SharedPreferences sPref) throws JSONException {
        JSONObject userJson = new JSONObject(responseUser);
        UserEntity user = new UserEntity(userJson.getInt("id"), userJson.getString("name"), userJson.getString("faculty"));
        user.setAvatarPath(sPref.getString(PATH_KEY, ""));
        return user;
    }

    public static String getToken() {
        return mToken;
    }
}
