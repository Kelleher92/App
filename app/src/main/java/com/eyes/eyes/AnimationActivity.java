package com.eyes.eyes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

public class AnimationActivity extends AppCompatActivity {
    Bitmap dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);

        relativeLayout.addView(new MySurfaceView(this, this.getResources()));

        //dot = BitmapFactory.decodeResource(getResources(), R.drawable.red_dot);
    }

    public void Back(View view) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}
