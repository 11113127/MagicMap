package com.example.kimp.magicmaprebulid2;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user2 on 2015/10/6.
 */
public class Map extends FragmentActivity {

    GoogleMap map;
    String aid = "";
    LatLng latLng = new LatLng(22.764702, 120.371935);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        setUpMapIfNeeded();
        getValue();
        setMap();
        setMarker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setMap();
            }
        }
    }

    public void getValue(){
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        aid = bundle.getString("aid");
    }

    public void setMap(){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
    }

    public void setMarker(){
        Model model = new Model();
        model.setMapMarker(aid, getActivityInf);
    }

    private Handler getActivityInf = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            Log.e("LoginDebug", "Login Result:" + result);
            getMarker(result);
        }
    };

    public void getMarker(String result){
        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo;
            /*剖析JSON, 放進String 陣列*/
            for (int i=0; i<ja.length(); i++){
                if (i==ja.length()-1){
                    jo = new JSONObject(ja.get(i).toString());
                    MarkerOptions markerOptions = new MarkerOptions();
                    double lat = Double.parseDouble(jo.getString("lat"));
                    double lng = Double.parseDouble(jo.getString("lng"));
                    String title = jo.getString("name");

                    LatLng latLng = new LatLng(lat, lng);
                    Log.e("LatLng", title + ", " + lat + ", " + lng);
                    markerOptions.position(latLng);
                    markerOptions.title(title);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                    map.addMarker(markerOptions);
                }
                else {
                    jo = new JSONObject(ja.get(i).toString());
                    MarkerOptions markerOptions = new MarkerOptions();
                    double lat = Double.parseDouble(jo.getString("lat"));
                    double lng = Double.parseDouble(jo.getString("lng"));
                    String title = jo.getString("name");

                    LatLng latLng = new LatLng(lat, lng);
                    Log.e("LatLng", title + ", " + lat + ", " + lng);
                    markerOptions.position(latLng);
                    markerOptions.title(title);
                    map.addMarker(markerOptions);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
