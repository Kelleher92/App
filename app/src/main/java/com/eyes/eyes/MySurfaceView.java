package com.eyes.eyes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Ian on 08-Feb-16.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final Resources resources;
    Camera.Parameters parameters;
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private MediaRecorder mMediaRecorder;
    private boolean mInitSuccesful;
    private int count;
    private ByteBuffer buffer;

    public MySurfaceView(Context context, Resources resources) {
        super(context);
        this.resources = resources;

        count = 0;

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
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            public void onPreviewFrame(byte[] _data, Camera _camera) {
                Log.d("SurfaceChanged", String.format("Got %d bytes of camera data", _data.length));
                count++;
                Log.i("SurfaceChanged", "Frame " + count);

//                Camera.Size size = parameters.getPreviewSize();
//                YuvImage image = new YuvImage(_data, parameters.getPreviewFormat(), size.width, size.height, null);
//
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                image.compressToJpeg(new Rect(0, 0, size.width, size.height), 50, out);
//                byte[] imageBytes = out.toByteArray();
//                Bitmap newimage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//
//                try {
//                    Bitmap output = processFrame.processFrame(newimage, radius);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Log.i("test","test");
            }
        });

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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
        parameters = mCamera.getParameters();

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        parameters.setColorEffect(Camera.Parameters.EFFECT_MONO);

        camera.setParameters(parameters);
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
