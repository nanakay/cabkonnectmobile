package com.fourapps.cabkonnect;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.location.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;


/**
 * Created by nanakay on 6/6/13.
 */
public class Home extends FragmentActivity {
    GoogleMap map;
    Location location;
    private LocationClient mLocationClient;
    private static final LatLng GOLDEN_GATE_BRIDGE = new LatLng(37.828891,-122.485884);
    private static final LatLng APPLE = new LatLng(37.3325004578, -122.03099823);
    Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            map = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            map.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            final String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            location = locationManager.getLastKnownLocation(provider);

            final LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // redraw the marker when get location update.
                    drawMarker(location);
                    setCurrentLocation();
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            if(location!=null){
                //PLACE THE INITIAL MARKER
                drawMarker(location);
                setCurrentLocation();
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, locationListener);
        }
    }

    private void drawMarker(Location location){
        map.clear();
        LatLng myLatLng = new LatLng(location.getLatitude(),
                location.getLongitude());
        CameraPosition myPosition = new CameraPosition.Builder().target(myLatLng).zoom(16).bearing(90).tilt(30).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));

        map.addMarker(new MarkerOptions()
                .position(myLatLng)
                .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude()));
    }

    private void setCurrentLocation () {
        Geocoder geoCoder = new Geocoder(this);
        List<Address> matches = null;
        try {
            matches = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
        String addressText = String.format("%s, %s, %s", bestMatch.getMaxAddressLineIndex() > 0 ? bestMatch.getAddressLine(0) : "", bestMatch.getLocality(), bestMatch.getCountryName());

        EditText currentLocation = (EditText)(findViewById(R.id.current_location));
        currentLocation.setText(addressText);
    }

}


