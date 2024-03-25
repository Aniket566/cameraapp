package com.snap.cameraapp;

import static com.otaliastudios.cameraview.controls.Preview.GL_SURFACE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.filter.Filters;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private CameraView cameraView;
    private CircleImageView captureButton;
    private ImageView image_gallery;
    ImageView previewImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        cameraView=findViewById ( R.id.cameraView );
        captureButton=findViewById ( R.id.captureButton );
        cameraView.setMode ( Mode.PICTURE );
        cameraView.open ();
        cameraView.setPreview(GL_SURFACE);
        cameraView.setFilter(Filters.BLACK_AND_WHITE.newInstance());
        cameraView.setLifecycleOwner(this);
        previewImageView = findViewById(R.id.imageView);

        cameraView.addCameraListener ( new CameraListener () {
            @Override
            public void onCameraOpened(@NonNull CameraOptions options) {
                super.onCameraOpened ( options );
            }

            @Override
            public void onCameraClosed() {
                super.onCameraClosed ();
            }

            @Override
            public void onCameraError(@NonNull CameraException exception) {
                super.onCameraError ( exception );
            }

            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                processPicture(result.getData());
            }
        } );
       cameraView.getFilter ();

        captureButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                cameraView.takePicture ();
            }
        } );

    }
    private void processPicture(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap filteredBitmap = applyFilter(bitmap);
        previewImageView.setImageBitmap(filteredBitmap);
        previewImageView.setVisibility(View.VISIBLE);
        cameraView.setVisibility(View.INVISIBLE);
    }

    private Bitmap applyFilter(Bitmap bitmap) {
        BlackAndWhiteFilter blackAndWhiteFilter = new BlackAndWhiteFilter();
        return blackAndWhiteFilter.processFilter(bitmap);
    }
    @Override
    protected void onResume() {
        super.onResume ();
        cameraView.open ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        cameraView.destroy ();
    }
}