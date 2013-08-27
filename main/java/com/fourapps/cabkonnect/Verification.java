package com.fourapps.cabkonnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by nanakay on 6/6/13.
 */
public class Verification extends Activity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);

        Button verify = (Button) findViewById(R.id.verify);
        verify.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.verify) {
            Intent home = new Intent(Verification.this, Home.class);
            startActivity(home);

        }
    }
}
