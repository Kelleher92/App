package com.eyes.eyes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * Created by Ian on 08-Feb-16.
 */
public class DotAnimation extends View{
    Bitmap dot;
    int x, y;
    
    public DotAnimation(Context context){
        super(context);
        
        dot = BitmapFactory.decodeResource(getResources(), R.drawable.red_dot);
    }
}
