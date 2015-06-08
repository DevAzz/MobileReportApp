package ru.kpfu.mobilereportapp.utils.server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by azz on 19.05.2015.
 */
public class ServerApiHelper {

    public static final String BASE_URL = "http://jblab-kzn.ru:8001";

    public static String getAbsoluteUrl(String relativeUrl) {
        return ServerApiHelper.BASE_URL + relativeUrl;
    }

    public static String getResponseSuccessResult(String response)throws JSONException {
        JSONObject object = new JSONObject(response);
        return  object.getString("token");
    }

    public static String getResponseErrorMessage(String response)throws JSONException {
        JSONObject object = new JSONObject(response);
        return  object.getString("msg");
    }
}
