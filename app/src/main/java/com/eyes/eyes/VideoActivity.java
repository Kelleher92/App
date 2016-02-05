package com.eyes.eyes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by Ian on 04-Feb-16.
 */

public class VideoActivity extends AppCompatActivity {

    private static final int VIDEO_CAPTURE = 101;
    int radius = 22;
    VideoView videoView;
    Button back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        back = (Button) findViewById(R.id.Backbutton);

        File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Uri videoUri = Uri.fromFile(mediaFile);
        videoView = (VideoView) findViewById(R.id.videoView);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();

//                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//
//                try {
//                    retriever.setDataSource(data.getData().getPath());

                    videoView.setVideoPath(data.getData().getPath());

//                    ProcessRequests processRequests = new ProcessRequests(this);
//                    processRequests.processVideoInBackground(input, radius, new GetVideoCallback() {
//                        @Override
//                        public void done(MediaStore.Video returnedVideo) {
//                            if (returnedVideo == null) {
//
//                                Log.i("MyActivity", "No returned video");
//                            } else {
//
//                                Log.i("MyActivity", "Returned video");
//                            }
//                        }
//                    });
//
//                } catch (IllegalArgumentException ex) {
//                    ex.printStackTrace();
//                } catch (RuntimeException ex) {
//                    ex.printStackTrace();
//                } finally {
//                    try {
//                        retriever.release();
//                    } catch (RuntimeException ex) {
//                    }
//                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Back(View view) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}


