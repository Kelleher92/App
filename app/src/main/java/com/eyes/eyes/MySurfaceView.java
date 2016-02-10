package com.eyes.eyes;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ian on 08-Feb-16.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final Resources resources;
    private Dot dot;
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private MediaRecorder mMediaRecorder;
    private boolean mInitSuccesful;

    public MySurfaceView(Context context, Resources resources) {
        super(context);
        this.resources = resources;
        //dot = null;

        getHolder().addCallback(this);
        mHolder = getHolder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (!mInitSuccesful)
                initRecorder(mHolder.getSurface());
            mMediaRecorder.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //dot = new Dot(holder, resources, getContext());
        //dot.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (dot != null) {
            try {
                dot.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //dot = null;
        shutdown();
    }

    private void shutdown() {
        // Release MediaRecorder and especially the Camera as it's a shared
        // object that can be used by other applications
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mCamera.release();

        // once the objects have been released they can't be reused
        mMediaRecorder = null;
        mCamera = null;
    }

    private void configure(Camera camera) {
        Camera.Parameters params = camera.getParameters();

        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        params.setColorEffect(Camera.Parameters.EFFECT_MONO);

        camera.setParameters(params);
    }

    private void initRecorder(Surface surface) throws IOException {
        // It is very important to unlock the camera before doing setCamera
        // or it will results in a black preview
        if (mCamera == null) {
            mCamera = Camera.open(1);
            configure(mCamera);
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(1, info);
            Log.i("can disable sound", " = " + info.canDisableShutterSound);
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
}
