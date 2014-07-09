package com.example.cameraapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private static byte[] bitmapData;
	private Bitmap bitmap;
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

		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// get an image from the camera
				// mCamera.stopPreview();
				mCamera.takePicture(null, null, mPicture);
				
				// start another activity
				Intent launchactivity= new Intent(MainActivity.this,MaskActivity.class);                               
				startActivity(launchactivity);
			}
		});
	}

	PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			bitmapData = data;
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		}
	};
	
	public static byte[] getbitmapData(){
		return bitmapData;
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera(); // release the camera immediately on pause event
	}

	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release(); // release the camera for other applications
			mCamera = null;
		}
	}

	// to check whether camera is available or not?
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {

			// the phone has camera

			return true;
		} else {

			// the phone doesn't have a camera

			return false;
		}
	}

	// accessing the camera

	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
			// camera not available or in use by some other application
		}
		return c;
	}

}