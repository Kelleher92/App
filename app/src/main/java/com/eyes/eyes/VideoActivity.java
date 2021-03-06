package com.eyes.eyes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ian on 04-Feb-16.
 */

public class VideoActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Button back;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private MediaRecorder mMediaRecorder;
    private boolean mInitSuccesful;
    private View mToggleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        back = (Button) findViewById(R.id.Backbutton);
        // we shall take the video in landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mToggleButton = (ToggleButton) findViewById(R.id.toggleRecordingButton);

        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // toggle video recording
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    mMediaRecorder.start();
                } else {
                    mMediaRecorder.stop();
                    mMediaRecorder.reset();
                    try {
                        initRecorder(mHolder.getSurface());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /* Init the MediaRecorder, the order the methods are called is vital to
     * its correct functioning */
    private void initRecorder(Surface surface) throws IOException {
        // It is very important to unlock the camera before doing setCamera
        // or it will results in a black preview
        if (mCamera == null) {
            mCamera = Camera.open(1);
            configure(mCamera);
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(1, info);
            if (info.canDisableShutterSound) {
                mCamera.enableShutterSound(false);
            }
            mCamera.unlock();
        }

        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();

        mMediaRecorder.setPreviewDisplay(surface);
        mMediaRecorder.setCamera(mCamera);

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");

        mMediaRecorder.setOutputFile(file.getAbsolutePath());

        // No limit. Check the space on disk!
        mMediaRecorder.setMaxDuration(-1);
        mMediaRecorder.setVideoFrameRate(15);

        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            // This is thrown if the previous calls are not called with the
            // proper order
            e.printStackTrace();
        }

        mInitSuccesful = true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (!mInitSuccesful)
                initRecorder(mHolder.getSurface());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        shutdown();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            public void onPreviewFrame(byte[] _data, Camera _camera) {
                Log.d("SurfaceChanged", String.format("Got %d bytes of camera data", _data.length));
            }
        });
    }

    private void configure(Camera camera) {
        Camera.Parameters params = camera.getParameters();

        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        params.setColorEffect(Camera.Parameters.EFFECT_MONO);

        camera.setParameters(params);
    }

    private void shutdown() {
        // Release MediaRecorder and especially the Camera as it's a shared
        // object that can be used by other applications
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mCamera.release();

        // once the objects have been released they can't be reused
        mMediaRecorder = null;
        mCamera = null;
    }

    public void Back(View view) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}


