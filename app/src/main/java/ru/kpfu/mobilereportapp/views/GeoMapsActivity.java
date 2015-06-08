package ru.kpfu.mobilereportapp.views;

import android.app.Activity;
import android.os.Bundle;

import ru.kpfu.mobilereportapp.R;

public class GeoMapsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container_map_fragment, new MapFragment())
                    .commit();
        }
    }
}
