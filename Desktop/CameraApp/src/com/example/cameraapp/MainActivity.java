package com.example.cameraapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	
    private Camera mCamera;
    private CameraPreview mPreview;
    private Button captureButton;
    private Button cancelButton;
    public static final int MEDIA_TYPE_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        captureButton = (Button) findViewById(R.id.camera_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
         
        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        mCamera.startPreview();
        
        captureButton.setOnClickListener(
        	    new View.OnClickListener() {
        	        @Override
        	        public void onClick(View v) {
        	            // get an image from the camera
        	        	//mCamera.stopPreview();
        	            mCamera.takePicture(null, null, mPicture);
        	            
        	        }
        	    }
        	);
    }

    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
               // Log.d("Error in PictureCallback", "Error creating media file, check storage permissions: " +
                   // e.getMessage());
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("In PictureCalback", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("In PictureCalback", "Error accessing file: " + e.getMessage());
            }
            camera.startPreview();
        }
    };
    
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }
    
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
    
    // to check whether camera is available or not?
    private boolean checkCameraHardware(Context context){
    	if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
    		
    		//the phone has camera
    		
    		return true;
    	}else{
    		
    		//the phone doesn't have a camera
    		
    		return false;
    	}
    }
    
    //accessing the camera
    
    public static Camera getCameraInstance(){
    	Camera c = null;
    	try{
    		c=Camera.open();
    	}catch(Exception e){
    		// camera not available or in use by some other application
    	}
    	return c;
    }
    
}