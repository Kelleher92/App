package com.eyes.eyes;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
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
                returnedImage = processFrame.processFrame(input, radius);
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

