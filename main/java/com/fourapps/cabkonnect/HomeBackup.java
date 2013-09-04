package com.fourapps.cabkonnect;

import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.location.Location;
import android.graphics.Color;
import android.location.LocationListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * Created by nanakay on 6/6/13.
 */
class Home_Backup extends FragmentActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {
    GoogleMap map;
    private LocationClient mLocationClient;
    private static final LatLng GOLDEN_GATE_BRIDGE = new LatLng(37.828891,-122.485884);
    private static final LatLng APPLE = new LatLng(37.3325004578, -122.03099823);
    Location currentLocation;
    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
            setUserLocation();
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        if (map == null) {
            Toast.makeText(this, "Google maps not available", Toast.LENGTH_LONG).show();
        }
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is
        // present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_sethybrid:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.menu_showtraffic:
                map.setTrafficEnabled(true);
                break;
            case R.id.menu_zoomin:
                map.animateCamera(CameraUpdateFactory.zoomIn());
                break;

            case R.id.menu_zoomout:
                map.animateCamera(CameraUpdateFactory.zoomOut());
                break;
            case R.id.menu_gotolocation:
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(GOLDEN_GATE_BRIDGE) // Sets the center of the map to
                                // Golden Gate Bridge
                        .zoom(17)                   // Sets the zoom
                        .bearing(90) // Sets the orientation of the camera to east
                        .tilt(30)    // Sets the tilt of the camera to 30 degrees
                        .build();    // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(
                        cameraPosition));
                break;
            case R.id.menu_addmarker:
                map.addMarker(new MarkerOptions()
                        .position(GOLDEN_GATE_BRIDGE)
                        .title("Golden Gate Bridge")
                        .snippet("San Francisco")
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_launcher)));
                break;
            case R.id.menu_getcurrentlocation:
                // ---get your current location and display a blue dot---
                map.setMyLocationEnabled(true);

                break;
            case R.id.menu_showcurrentlocation:
                Location myLocation = map.getMyLocation();
                LatLng myLatLng = new LatLng(myLocation.getLatitude(),
                        myLocation.getLongitude());

                CameraPosition myPosition = new CameraPosition.Builder()
                        .target(myLatLng).zoom(17).bearing(90).tilt(30).build();
                map.animateCamera(
                        CameraUpdateFactory.newCameraPosition(myPosition));
                break;
            case R.id.menu_lineconnecttwopoints:
                //---add a marker at Apple---
                map.addMarker(new MarkerOptions()
                        .position(APPLE)
                        .title("Apple")
                        .snippet("Cupertino")
                        .icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_AZURE)));

                //---draw a line connecting Apple and Golden Gate Bridge---
                map.addPolyline(new PolylineOptions()
                        .add(GOLDEN_GATE_BRIDGE, APPLE).width(5).color(Color.RED));
                break;
        }
        return true;
    }

    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            Toast.makeText(getApplicationContext(), "Waiting for location",
                    Toast.LENGTH_SHORT).show();
            mLocationClient = new LocationClient(this, this, // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        currentLocation = mLocationClient.getLastLocation();
        if (currentLocation != null) {
            Toast.makeText(getApplicationContext(), "Found!",
                    Toast.LENGTH_SHORT).show();
            setUserLocation();
        }
    }

    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void setUserLocation() {
        map.setMyLocationEnabled(true);
        LatLng myLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        CameraPosition myPosition = new CameraPosition.Builder().target(myLatLng).zoom(16).bearing(90).tilt(30).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));

        MarkerOptions markerOpts = new MarkerOptions().position(myLatLng).title(
                "Your Current Location");
        map.addMarker(markerOpts);
    }

}


