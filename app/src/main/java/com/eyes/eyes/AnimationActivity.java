package com.eyes.eyes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Ian on 08-Feb-16.
 */

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);

        relativeLayout.addView(new MySurfaceView(this, this.getResources()));
    }

    public void Back(View view) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

}
