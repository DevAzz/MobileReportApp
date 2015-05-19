package ru.kpfu.mobilereportapp;

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
    final String TAG = "myLogs";
    private LatLng mainBuild = new LatLng(55.790447, 49.121421);
    private LatLng building14 = new LatLng(55.792148, 49.122044);

    private static Building[] BUILDINGS_ARRAY = new Building[]{
            new Building("Учебное здание №01 (Главный корпус университета)","г.Казань, ул.Кремлевская, д.18", new LatLng(55.790447, 49.121421)),
            new Building("Учебное здание №14 (Корпус № 2)","г.Казань, ул.Кремлевская, д.35", new LatLng(55.792148, 49.122044)),
    };



    //интервал обновления положения всплывающего окна.
    //для плавности необходимо 60 fps, то есть 1000 ms / 60 = 16 ms между обновлениями.
    private static final int POPUP_POSITION_REFRESH_INTERVAL = 16;
    //длительность анимации перемещения карты
    private static final int ANIMATION_DURATION = 500;

    private Map<Marker, Building> buildingMap;

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
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);

        map.clear();
        buildingMap.clear();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pin_shadow);
        for (Building building : BUILDINGS_ARRAY) {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(building.getPosition())
                    .title("Title")
                    .snippet("Subtitle")
                    .icon(icon));
            buildingMap.put(marker, building);
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
        String name = (String) v.getTag();
        Toast.makeText(this.getActivity(), name, Toast.LENGTH_SHORT).show();
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

        Building building = buildingMap.get(marker);
        textViewName.setText(building.getName());
        textViewAddress.setText(building.getAddress());
        button.setTag(building.getName());

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
