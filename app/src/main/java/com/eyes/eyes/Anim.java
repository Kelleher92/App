package com.eyes.eyes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ian on 08-Feb-16.
 */

public class Anim implements Runnable {
    private SurfaceHolder surfaceHolder;
    private boolean running = true;
    private Dot dot;
    private Paint paint;
    private Bitmap dotBitmap;
    private Context mContext;
    int i;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private boolean mInitSuccesful;

    public Anim(SurfaceHolder holder, Resources resources, Context context) {
        dot = new Dot();
        this.surfaceHolder = holder;
        i = 0;
        mContext = context;

        paint = new Paint();

        dotBitmap = BitmapFactory.decodeResource(resources, R.drawable.red_dot);
    }

    @Override
    public void run() {
        while (running) {
//            try {
//                if (!mInitSuccesful)
//                    initRecorder(surfaceHolder.getSurface());
//                mMediaRecorder.start();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

            while (i < 100) {
                drawGraphics();
                i++;
            }

            i = 0;
            while (i < 134) {
                moveDotSE();
                drawGraphics();
                i++;
            }

            i = 0;
            while (i < 30) {
                drawGraphics();
                i++;
            }

            i = 0;
            while (i < 134) {
                moveDotNE();
                drawGraphics();
                i++;
            }

            i = 0;
            while (i < 30) {
                drawGraphics();
                i++;
            }

            i = 0;
            while (i < 134) {
                moveDotNW();
                drawGraphics();
                i++;
            }

            i = 0;
            while (i < 30) {
                drawGraphics();
                i++;
            }

            i = 0;
            while (i < 134) {
                moveDotSW();
                drawGraphics();
                i++;
            }

            i = 0;
            while (i < 30) {
                drawGraphics();
                i++;
            }

//            shutdown();
            end();
        }

        Intent myIntent = new Intent(mContext, MenuActivity.class);
        mContext.startActivity(myIntent);
    }

    private void drawGraphics() {
        Canvas canvas = surfaceHolder.lockCanvas();

        if (canvas == null) {
            return;
        }

        try {
            synchronized (surfaceHolder) {
                drawDot(canvas);
            }
        } finally {
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawDot(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#D96E70"));
        canvas.drawBitmap(dotBitmap, dot.x, dot.y, paint);
    }

    private void moveDotSE() {
        dot.stepSE();
    }

    private void moveDotNE() {
        dot.stepNE();
    }

    private void moveDotSW() {
        dot.stepSW();
    }

    private void moveDotNW() {
        dot.stepNW();
    }

    public void end() {
        running = false;
    }

//    private void shutdown() {
//        // Release MediaRecorder and especially the Camera as it's a shared
//        // object that can be used by other applications
//        mMediaRecorder.reset();
//        mMediaRecorder.release();
//        mMediaRecorder.stop();
//        mMediaRecorder.reset();
//        mCamera.release();
//
//        // once the objects have been released they can't be reused
//        mMediaRecorder = null;
//        mCamera = null;
//    }
//
//    private void configure(Camera camera) {
//        Camera.Parameters params = camera.getParameters();
//
//        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
//        params.setColorEffect(Camera.Parameters.EFFECT_MONO);
//
//        camera.setParameters(params);
//    }
//
//    private void initRecorder(Surface surface) throws IOException {
//        // It is very important to unlock the camera before doing setCamera
//        // or it will results in a black preview
//        if (mCamera == null) {
//            mCamera = Camera.open(1);
//            configure(mCamera);
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            Camera.getCameraInfo(1, info);
//            Log.i("can disable sound", " = " + info.canDisableShutterSound);
//            if (info.canDisableShutterSound) {
//                mCamera.enableShutterSound(false);
//            }
//            mCamera.unlock();
//        }
//
//        if (mMediaRecorder == null)
//            mMediaRecorder = new MediaRecorder();
//        mMediaRecorder.setPreviewDisplay(surface);
//
//        mMediaRecorder.setCamera(mCamera);
//
//        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");
//
//        mMediaRecorder.setOutputFile(file.getAbsolutePath());
//
//        // No limit. Check the space on disk!
//        mMediaRecorder.setMaxDuration(-1);
//        mMediaRecorder.setVideoFrameRate(15);
//
//        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
//
//        try {
//            mMediaRecorder.prepare();
//        } catch (IllegalStateException e) {
//            // This is thrown if the previous calls are not called with the
//            // proper order
//            e.printStackTrace();
//        }
//
//        mInitSuccesful = true;
//    }
}
