package ru.kpfu.mobilereportapp.views;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import ru.kpfu.mobilereportapp.R;
import ru.kpfu.mobilereportapp.entities.BuildingEntity;
import ru.kpfu.mobilereportapp.entities.UserEntity;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MapFragment
        extends
        com.google.android.gms.maps.MapFragment
        implements
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        OnClickListener {

    private Marker mainBuildingMarker;
    private Marker building14Marker;
    final String TAG = "MapFragment";
    public static String KEY = "Activity";
    private LatLng mainBuild = new LatLng(55.790447, 49.121421);
    private LatLng building14 = new LatLng(55.792148, 49.122044);

    private static BuildingEntity[] BUILDINGS_ARRAY = new BuildingEntity[]{
            new BuildingEntity("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18",55.790447, 49.121421),
            new BuildingEntity("Учебное здание №14 (Корпус № 2)","г.Казань, ул.Кремлевская, д.35", 55.792148, 49.122044, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}),
            new BuildingEntity("Учебное здание №17 (Центр информационных технологий)", "г. Казань, ул.Профессора Нужина, д.1/37", 55.792574, 49.124151),
            new BuildingEntity("Культурно-спортивный комплекс", "г.Казань, ул.Профессора Нужина, д. 2", 55.790511, 49.124151),
            new BuildingEntity("Учебное здание №04 (НИ химический институт им.А.М.Бутлерова)", "г.Казань, ул.Кремлевская, д.29/1", 55.792526, 49.119803),
            new BuildingEntity("Учебное здание №12 (Физический факультет)", "г.Казань, ул.Кремлевская, д.16а", 55.791798, 49.117684),
            new BuildingEntity("Учебное здание №16 (Химический факультет)", "г.Казань, ул.Лобачевского, д.1/29", 55.792498, 49.119800),
            new BuildingEntity("Учебное здание №18Б", "г.Казань, ул.Кремлевская/ М.Джалиля/ Рахматуллина, д.6/20/1", 55.793319, 49.114815),
            new BuildingEntity("Учебное здание №13Б (Геологический корпус, блок Б)", "г.Казань, ул.Кремлевская, д.4/5", 55.794142, 49.113774),
            new BuildingEntity("Учебное здание №23", "г.Казань, ул.К.Маркса, д.43/10", 55.795926, 49.130176),
            new BuildingEntity("Учебное здание №30", "г.Казань, ул.Лево-Булачная, д.34", 55.790073, 49.109102),
            new BuildingEntity("Учебное здание №31", "г.Казань, ул.Мартына Межлаука, д.3/45", 55.787438, 49.110212),
            new BuildingEntity("Учебное здание №33", "г.Казань, ул.Татарстан, д.2", 55.784075, 49.117138),
            new BuildingEntity("Учебное здание №22 (Здание учебного корпуса блок В)", "г.Казань, ул.Бутлерова, д.4", 55.786894, 49.127238),
            new BuildingEntity("Учебное здание №29 (Спортивный корпус)", "г.Казань, ул.Карла Маркса, д.74", 55.793938, 49.143653),
            new BuildingEntity("Учебное здание №32", "г.Казань, ул.Товарищеская, д.5", 55.789687, 49.158542),
            new BuildingEntity("Учебное здание №34", "г.Казань, просп.Ибрагимова, д.85А", 55.821883, 49.098314),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
//            new Building("", "", new LatLng()),
    };



    //интервал обновления положения всплывающего окна.
    //для плавности необходимо 60 fps, то есть 1000 ms / 60 = 16 ms между обновлениями.
    private static final int POPUP_POSITION_REFRESH_INTERVAL = 16;
    //длительность анимации перемещения карты
    private static final int ANIMATION_DURATION = 500;

    private Map<Marker, BuildingEntity> buildingMap;

    //точка на карте, соответственно перемещению которой перемещается всплывающее окно
    private LatLng trackedPosition;

    //Handler, запускающий обновление окна с заданным интервалом
    private Handler handler;

    //Runnable, который обновляет положение окна
    private Runnable positionUpdaterRunnable;

    //смещения всплывающего окна, позволяющее
    //скорректировать его положение относительно маркера
    private int popupXOffset;
    private int popupYOffset;
    //высота маркера
    private int markerHeight;
    private AbsoluteLayout.LayoutParams overlayLayoutParams;

    //слушатель, который будет обновлять смещения
    private ViewTreeObserver.OnGlobalLayoutListener infoWindowLayoutListener;

    //контейнер всплывающего окна
    private View infoWindowContainer;
    private TextView textViewName;
    private TextView textViewAddress;
    private TextView button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildingMap = new HashMap<>();
        markerHeight = getResources().getDrawable(R.drawable.pin_shadow).getIntrinsicHeight();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, null);

        FrameLayout containerMap = (FrameLayout) rootView.findViewById(R.id.container_map);
        View mapView = super.onCreateView(inflater, container, savedInstanceState);
        containerMap.addView(mapView, new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        GoogleMap map = getMap();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mainBuild)
                .zoom(15)
                .bearing(0)
                .tilt(0)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);

        map.clear();
        buildingMap.clear();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pin_shadow);
        for (BuildingEntity buildingEntity : BUILDINGS_ARRAY) {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(buildingEntity.convertDoubleIntoLatLng(buildingEntity.getLatitude(), buildingEntity.getLongitude()))
                    .title("Title")
                    .snippet("Subtitle")
                    .icon(icon));
            buildingMap.put(marker, buildingEntity);
        }

        infoWindowContainer = rootView.findViewById(R.id.container_popup);
        //подписываемся на изменения размеров всплывающего окна
        infoWindowLayoutListener = new InfoWindowLayoutListener();
        infoWindowContainer.getViewTreeObserver().addOnGlobalLayoutListener(infoWindowLayoutListener);
        overlayLayoutParams = (AbsoluteLayout.LayoutParams) infoWindowContainer.getLayoutParams();

        textViewName = (TextView) infoWindowContainer.findViewById(R.id.textview_title);
        textViewAddress = (TextView) infoWindowContainer.findViewById(R.id.textViewAddress);
        button = (TextView) infoWindowContainer.findViewById(R.id.button_view_article);
        button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //очистка
        handler = new Handler(Looper.getMainLooper());
        positionUpdaterRunnable = new PositionUpdaterRunnable();
        handler.post(positionUpdaterRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        infoWindowContainer.getViewTreeObserver().removeGlobalOnLayoutListener(infoWindowLayoutListener);
        handler.removeCallbacks(positionUpdaterRunnable);
        handler = null;
    }

    @Override
    public void onClick(View v) {
        String whence = getActivity().getIntent().getStringExtra(KEY);
        String name = (String) v.getTag();
        Toast.makeText(this.getActivity(), name, Toast.LENGTH_SHORT).show();
        if ("AddComplaintActivity".equals(whence)) {
            Intent intent = new Intent(getActivity(), AddComplaintActivity.class);
            intent.putExtra(KEY, TAG);
            intent.putExtra(UserEntity.class.getCanonicalName(),  getActivity().getIntent().getParcelableExtra(UserEntity.class.getCanonicalName()));
            intent.putExtra(BuildingEntity.class.getCanonicalName(), getBuildingByName(name));
            startActivity(intent);
        } else if ("DialogSort".equals(whence)) {
            Intent intent = new Intent(getActivity(), ComplaintActivity.class);
            intent.putExtra(KEY, TAG);
            intent.putExtra(UserEntity.class.getCanonicalName(),  getActivity().getIntent().getParcelableExtra(UserEntity.class.getCanonicalName()));
            intent.putExtra(BuildingEntity.class.getCanonicalName(), getBuildingByName(name));
            startActivity(intent);
        }

    }

    private BuildingEntity getBuildingByName (String name) {
        BuildingEntity building = null;
        for (BuildingEntity val : BUILDINGS_ARRAY) {
            if (name.equals(val.getName())) {
                building = val;
            }
        }
        return building;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        infoWindowContainer.setVisibility(INVISIBLE);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        GoogleMap map = getMap();
        Projection projection = map.getProjection();
        trackedPosition = marker.getPosition();
        Point trackedPoint = projection.toScreenLocation(trackedPosition);
        trackedPoint.y -= popupYOffset / 2;
        LatLng newCameraLocation = projection.fromScreenLocation(trackedPoint);
        map.animateCamera(CameraUpdateFactory.newLatLng(newCameraLocation), ANIMATION_DURATION, null);

        BuildingEntity buildingEntity = buildingMap.get(marker);
        textViewName.setText(buildingEntity.getName());
        textViewAddress.setText(buildingEntity.getAddress());
        button.setTag(buildingEntity.getName());

        infoWindowContainer.setVisibility(VISIBLE);

        return true;
    }

    private class InfoWindowLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            //размеры окна изменились, обновляем смещения
            popupXOffset = infoWindowContainer.getWidth() / 2;
            popupYOffset = infoWindowContainer.getHeight();
        }
    }

    private class PositionUpdaterRunnable implements Runnable {
        private int lastXPosition = Integer.MIN_VALUE;
        private int lastYPosition = Integer.MIN_VALUE;

        @Override
        public void run() {
            //помещаем в очередь следующий цикл обновления
            handler.postDelayed(this, POPUP_POSITION_REFRESH_INTERVAL);

            //если всплывающее окно скрыто, ничего не делаем
            if (trackedPosition != null && infoWindowContainer.getVisibility() == VISIBLE) {
                Point targetPosition = getMap().getProjection().toScreenLocation(trackedPosition);

                //если положение окна не изменилось, ничего не делаем
                if (lastXPosition != targetPosition.x || lastYPosition != targetPosition.y) {
                    //обновляем положение
                    overlayLayoutParams.x = targetPosition.x - popupXOffset;
                    overlayLayoutParams.y = targetPosition.y - popupYOffset - markerHeight -30;
                    infoWindowContainer.setLayoutParams(overlayLayoutParams);

                    //запоминаем текущие координаты
                    lastXPosition = targetPosition.x;
                    lastYPosition = targetPosition.y;
                }
            }
        }
    }
}
