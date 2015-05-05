package ru.kpfu.mobilereportapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeoMapsActivity extends FragmentActivity {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Marker mainBuildingMarker;
    private Marker building14Marker;
    final String TAG = "myLogs";
    private LatLng mainBuild = new LatLng (55.790447, 49.121421);
    private LatLng building14 = new LatLng (55.792148, 49.122044);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_maps);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        if (map == null) {
            finish();
            return;
        }

        init();
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                if (mainBuild.equals(marker.getPosition())) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Главное здание", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            }
        });

    }

    private void init() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mainBuild)
                .zoom(15)
                .bearing(0)
                .tilt(0)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);

        mainBuildingMarker = map.addMarker(new MarkerOptions().position(mainBuild).icon(
                BitmapDescriptorFactory.defaultMarker()).title("\t\n" +
                "Учебное здание №01 (Главный корпус университета)").snippet("г.Казань, ул.Кремлевская, д.18"));
        building14Marker = map.addMarker(new MarkerOptions().position(new LatLng(55.792148, 49.122044)).icon(
                BitmapDescriptorFactory.defaultMarker()).title("Учебное здание №14 (Корпус № 2)").snippet("г.Казань, ул.Кремлевская, д.35"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_geo_maps, menu);
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
