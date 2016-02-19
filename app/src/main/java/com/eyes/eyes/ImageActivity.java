package com.eyes.eyes;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by Ian on 04-Feb-16.
 */

public class ImageActivity extends AppCompatActivity {

    private static final float FACE_POSITION_RADIUS = 10.0f;
    int RESULT_LOAD_IMAGE = 1;
    int radius = 25;
    ImageView imageView;
    Button back;
    Bitmap input, output;
    FaceDetector detector;
    private SparseArray<Face> mFaces;
    Paint p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        back = (Button) findViewById(R.id.Backbutton);
        imageView = (ImageView) findViewById(R.id.imgView);

        detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        p = new Paint();

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);   //  PHONE IMAGES
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            input = BitmapFactory.decodeFile(picturePath);

            Frame frame = new Frame.Builder().setBitmap(input).build();
            mFaces = detector.detect(frame);
            Face face = mFaces.get(0);

            Bitmap mutableBitmap = input.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(mutableBitmap);
            canvas.drawCircle(canvas.getWidth() - face.getLandmarks().get(0).getPosition().x, face.getLandmarks().get(0).getPosition().y, FACE_POSITION_RADIUS, p);
            canvas.drawCircle(canvas.getWidth() - face.getLandmarks().get(1).getPosition().x, face.getLandmarks().get(1).getPosition().y, FACE_POSITION_RADIUS, p);


            //Rect rect = new Rect(((int) left), ((int) top), ((int) right), ((int) bottom));

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

            imageView.setImageBitmap(mutableBitmap);

            detector.release();
        }
    }

    public void Back(View view) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}


