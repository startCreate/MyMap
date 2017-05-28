package voronov.com.googlemapapp;

import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapInit();

    }

    private void mapInit(){

       MapFragment fragmentById = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
       fragmentById.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        newMarker(googleMap);

    }

    private void newMarker(final GoogleMap googleMap) {
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.addMarker(new MarkerOptions().position(latLng).title("My marker"));
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target(new LatLng(googleMap.getMyLocation().getLatitude(),
                                googleMap.getMyLocation().getLongitude())).
                        zoom(15).bearing(50).
                        tilt(50).build();
                googleMap.addCircle(new CircleOptions()
                        .center(new LatLng(googleMap.getMyLocation().getLatitude(),googleMap.getMyLocation().getLongitude()))
                        .radius(200f).fillColor(Color.GREEN));
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                return true;
            }
        });
            }



}
