package com.fourapps.cabkonnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by nanakay on 6/11/13.
 */
public class Starter extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter);

        Button driver = (Button) findViewById(R.id.passenger);

        driver.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.passenger) {
            Intent main = new Intent(Starter.this, Main.class);

            startActivity(main);
        }
    }
}
