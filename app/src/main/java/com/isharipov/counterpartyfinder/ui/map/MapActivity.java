package com.isharipov.counterpartyfinder.ui.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.isharipov.counterpartyfinder.R;
import com.isharipov.counterpartyfinder.data.db.repository.BaseDao;
import com.isharipov.counterpartyfinder.data.db.repository.model.DataAnswerDto;
import com.isharipov.counterpartyfinder.data.network.model.Location;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * 08.04.2018.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static String EXTRA_INFO = "extra_info";

    private Realm realm;
    private DataAnswerDto dataAnswerDto;

    @BindView(R.id.map_toolbar)
    Toolbar toolbar;

    public static void start(Activity activity, String hid) {
        Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra(EXTRA_INFO, hid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        ButterKnife.bind(this);
        initToolbar();
        realm = Realm.getDefaultInstance();
        String hid = (String) getIntent().getSerializableExtra(EXTRA_INFO);
        dataAnswerDto = BaseDao.get(realm, DataAnswerDto.class, hid);
        createGoogleMap();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.map_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Location location = dataAnswerDto.getLocation();
        final LatLng marker = new LatLng(location.getGeoLat(), location.getGeoLon());
        googleMap.addMarker(new MarkerOptions().position(marker));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker)             // Center Set
                .zoom(17.5f)                // Zoom
                .tilt(30)                   // Tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void createGoogleMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }
}