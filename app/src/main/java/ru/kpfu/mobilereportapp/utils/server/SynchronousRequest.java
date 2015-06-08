package ru.kpfu.mobilereportapp.utils.server;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by azz on 01.06.2015.
 */
public class SynchronousRequest {
    private static final String TAG = "SynchronousRequest";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient();

    private static SynchronousRequest synchronousRequest;

    private SynchronousRequest() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);
    }

    public static SynchronousRequest get() {
        if (synchronousRequest == null) {
            synchronousRequest = new SynchronousRequest();
        }
        return synchronousRequest;
    }

    public static void cancel(){
        client.cancel("all"); //???
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(ServerApiHelper.getAbsoluteUrl(url))
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(ServerApiHelper.getAbsoluteUrl(url))
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
