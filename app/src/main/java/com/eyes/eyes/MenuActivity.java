package com.eyes.eyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    Button image, video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        image = (Button) findViewById(R.id.Imagebutton);
        video = (Button) findViewById(R.id.Videobutton);
    }

    public void Image(View view) {
        Intent i = new Intent(this, ImageActivity.class);
        startActivity(i);
        finish();
    }

    public void Video(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}