package com.eyes.eyes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by Ian on 08-Feb-16.
 */
public class Anim implements Runnable {
    private SurfaceHolder surfaceHolder;
    private boolean running = true;
    private Dot dot;
    private Paint paint;
    private Bitmap dotBitmap;

//    public Anim() {
//        dot = new Dot();
//    }

    public Anim(SurfaceHolder holder, Resources resources) {
        dot = new Dot();

        this.surfaceHolder = holder;

        paint = new Paint();

        dotBitmap = BitmapFactory.decodeResource(resources, R.drawable.red_dot);
    }

    @Override
    public void run() {
        while (running) {
            moveDot();
            drawGraphics();
        }
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

    private void moveDot() {
        dot.step();
    }

    public void end() {
        running = false;
    }
}
