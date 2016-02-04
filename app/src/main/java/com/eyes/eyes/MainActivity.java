package com.eyes.eyes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int VIDEO_CAPTURE = 101;
    int RESULT_LOAD_IMAGE = 1;
    int radius = 25;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);   //  PHONE IMAGES
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);   //  PHONE VIDEOS
        //startActivityForResult(i, RESULT_LOAD_IMAGE);
        //}

        File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Uri videoUri = Uri.fromFile(mediaFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();

                MediaMetadataRetriever retriever = new MediaMetadataRetriever();

                try {
                    retriever.setDataSource(data.getData().getPath());

                    final Bitmap input = toGrayscale(retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST));

                    final ImageView imageView = (ImageView) findViewById(R.id.imgView);

                    ProcessRequests processRequests = new ProcessRequests(this);
                    processRequests.processFrameInBackground(input, radius, new GetImageCallback() {
                        @Override
                        public void done(Bitmap returnedImage) {
                            if (returnedImage == null) {
                                imageView.setImageBitmap(input);
                                Log.i("MyActivity", "No returned image");
                            } else {
                                imageView.setImageBitmap(returnedImage);
                                Log.i("MyActivity", "Returned image");
                            }
                        }
                    });

                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        retriever.release();
                    } catch (RuntimeException ex) {
                    }
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video", Toast.LENGTH_LONG).show();
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            final Bitmap input = toGrayscale(BitmapFactory.decodeFile(picturePath));
//
//            final ImageView imageView = (ImageView) findViewById(R.id.imgView);
//
//            ProcessRequests processRequests = new ProcessRequests(this);
//            processRequests.processFrameInBackground(input, radius, new GetImageCallback() {
//                @Override
//                public void done(Bitmap returnedImage) {
//                    if (returnedImage == null) {
//                        imageView.setImageBitmap(input);
//                        Log.i("MyActivity", "No returned image");
//                    } else {
//                        imageView.setImageBitmap(returnedImage);
//                        Log.i("MyActivity", "Returned image");
//                    }
//                }
//            });
//        }
//    }

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
}


