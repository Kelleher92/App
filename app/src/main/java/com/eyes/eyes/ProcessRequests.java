package com.eyes.eyes;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by temp2015 on 04-Feb-16.
 */
public class ProcessRequests {
    ProgressDialog progressDialog;

    public ProcessRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public void processFrameInBackground(Bitmap input, int radius, GetImageCallback imageCallback) {
        progressDialog.show();
        new processFrameAsyncTask(input, radius, imageCallback).execute();
    }

    public class processFrameAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        Bitmap input;
        GetImageCallback imageCallback;
        int radius;

        public processFrameAsyncTask(Bitmap input, int radius, GetImageCallback imageCallback) {
            this.input = input;
            this.radius = radius;
            this.imageCallback = imageCallback;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap returnedImage = null;
            try {
                Bitmap image = toGrayscale(input);
                returnedImage = processFrame.processFrame(image, radius);
                Log.i("MyActivity", "got output");
            } catch (IOException e) {
                Log.i("MyActivity", "Didnt get output");
                e.printStackTrace();
            }

            return returnedImage;
        }

        @Override
        protected void onPostExecute(Bitmap input) {
            progressDialog.dismiss();
            imageCallback.done(input);
            super.onPostExecute(input);
        }
    }
}

