package com.example.kimp.magicmaprebulid2;

import android.content.Intent;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapSelect extends FragmentActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    static String TAG = "mapSelect";
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Button btn_nextStep;
    String lat, lng;
    LatLng latlng = new LatLng(22.764702, 120.371935);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapselect);
        setUpMapIfNeeded();
        findV();
        clickListener();
        setMap();
    }

    public void findV() {
        btn_nextStep = (Button) findViewById(R.id.next);
    }

    public void setMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15.0f));
        mMap.setOnMapClickListener(this);
    }

    public void clickListener() {
        btn_nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (lat.length() == 0 && lng.length() == 0) {
                        Toast.makeText(mapSelect.this, R.string.mapSelect_text_wrong, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(mapSelect.this, Activity_Title.class);
                        Bundle bnndle = new Bundle();
                        bnndle.putString("lat", lat);
                        bnndle.putString("lng", lng);
                        intent.putExtras(bnndle);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(mapSelect.this, R.string.mapSelect_text_wrong, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    @Override
    public void onMapClick(LatLng point) {

        mMap.clear();
        MarkerOptions clickMarker = new MarkerOptions().position(point).title(point.toString());
        mMap.addMarker(clickMarker);
        lat = point.latitude + "";
        lng = point.longitude + " ";
        Log.e(TAG, point.toString() + " LatLng:" + lat + ", " + lng);
    }
}
