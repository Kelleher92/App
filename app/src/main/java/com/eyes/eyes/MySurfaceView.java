package com.eyes.eyes;

import android.content.Context;
import android.content.res.Resources;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ian on 08-Feb-16.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final Resources resources;
    private Dot dot;

    public MySurfaceView(Context context, Resources resources) {
        super(context);
        this.resources = resources;
        dot = null;

        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        dot = new Dot(holder, resources, getContext());
        dot.start();
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
        dot = null;
    }

}
