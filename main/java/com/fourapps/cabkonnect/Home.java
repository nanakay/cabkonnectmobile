package com.fourapps.cabkonnect;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import android.support.v4.app.FragmentActivity;

/**
 * Created by nanakay on 6/6/13.
 */
public class Home extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

 //    MapView mv = (MapView) findViewById(R.id.mapview);
//        mv.setBuiltInZoomControls(true);
    }

//    @Override
//    protected boolean isRouteDisplayed() {
//        return false;
//    }

}
