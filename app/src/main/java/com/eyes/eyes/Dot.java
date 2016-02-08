package com.eyes.eyes;

import android.content.res.Resources;
import android.view.SurfaceHolder;

/**
 * Created by Ian on 08-Feb-16.
 */
public class Dot {
    private Thread thread;
    private Anim anim;
    float x, y, vx, vy;

    public Dot(){
        x = 887;
        y = 467;
        vx = 10;
        vy = 10;
    }

    public Dot(SurfaceHolder holder, Resources resources) {
        anim = new Anim(holder, resources);
        thread = new Thread(anim);
    }

    public void start() {
        thread.start();
    }

    public void stop() throws InterruptedException {
        anim.end();
        thread.join();
    }

    public void step() {
        x += vx;
        y += vy;

        if (x < 1 || x > 1773)
            vx = -vx;
        if (y < 1 || y > 933)
            vy = -vy;
    }
}

