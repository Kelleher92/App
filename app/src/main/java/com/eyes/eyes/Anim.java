package com.eyes.eyes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.View;

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
            while (i < 80) {
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
}
