package com.andersen.dogsapp.dogs.camera;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder mHolder;
    Camera mCamera;
    Context mContext;

    public CameraPreview(Context context, Camera camera) { //1
        super(context);
        mContext = context;
        mCamera = camera;
        mHolder = getHolder(); //2
        mHolder.addCallback(this); //3

//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //4
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { //5
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }
        catch (IOException e) {
            Toast.makeText(mContext, "Камера превью провалилась", Toast.LENGTH_LONG).show();
        }
    }

    // 6
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
