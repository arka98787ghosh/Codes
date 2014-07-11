package com.example.cameraapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MaskActivity extends Activity {
	
	private Button saveButton;
	private Button backButton;
	public Bitmap croppedBitmap;
	public static byte[] bitData;
	public int finalx,finaly;
	int height;
	int width;
	float widthMaskConst = 0.93125f;
	//float heightMaskConst = 0.51056f;
	int wMask;
	//int hMask;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.mask_activity);
	    
	    Display display = getWindowManager().getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);
	    width = size.x;
	    height = size.y;
	    Log.i("x","y");
	    wMask = (int) (width*widthMaskConst);
	    Log.i("wMask",""+wMask);
	    
	    
	    LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
	    final DrawingView dv= new DrawingView(this);
	    ll.addView(dv);
	    
	    saveButton = (Button) findViewById(R.id.save_button);
	    
	    backButton = (Button) findViewById(R.id.back_button);
	    
	    saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getBaseContext(), "reaching", Toast.LENGTH_SHORT).show();
	        	
	        	class ToSaveCropped{
	        		
	        		float x=finalx;
	        		float y=finaly;

					private byte[] data = MainActivity.getbitmapData();
			        Bitmap mImage = BitmapFactory.decodeByteArray(data, 0, data.length);
			        
			        Drawable myMask = getResources().getDrawable(R.drawable.mask);
			        Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();
			        Bitmap mmImage = dv.bitmapRotate(mImage);
			        
			        Bitmap mmmImage = dv.getResizedBitmap(mmImage,height,width);
			        
			        Bitmap mmMask = dv.getResizedBitmap(mMask,wMask,wMask);
					
					public void onDraw(){
						Canvas canvas;       			
	        			int w = mmMask.getWidth(), h = mmMask.getHeight();
	        			Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
	        			croppedBitmap = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
	        			canvas = new Canvas(croppedBitmap);
	        			
	        			Paint maskPaint = new Paint();
	        			maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

	                    Paint imagePaint = new Paint();
	                    imagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
	        			//Log.i(""+x,""+y);
	                	//canvas.drawBitmap(mMask,x,y,maskPaint);
	                	canvas.drawBitmap(mmmImage,-x,-y,imagePaint);
					}
					
	        	}	        	
	        	ToSaveCropped tsc = new ToSaveCropped();
	        	tsc.onDraw();
	        	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        	croppedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	        	bitData = stream.toByteArray();
				
				SaveFile sv = new SaveFile();
				sv.save();
				
				MediaStore.Images.Media.insertImage(getContentResolver(), croppedBitmap, "", "");
				
			}
		}); 
	    
	    backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent launchactivity= new Intent(MaskActivity.this,MainActivity.class);                               
				startActivity(launchactivity);
				finish();
			}
		});
	    
	}
	
	class DrawingView extends View {
		//Bitmap bitmap;

		float x, y;

		public DrawingView(Context context) {
			super(context);
			//bitmap = BitmapFactory.decodeResource(context.getResources(),
					//R.drawable.ic_launcher);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {

			}
				break;

			case MotionEvent.ACTION_MOVE: {
				x = (int) event.getX();
				y = (int) event.getY();

				invalidate();
			}

				break;
			case MotionEvent.ACTION_UP:

				x = (int) event.getX();
				finalx=(int) x;
				y = (int) event.getY();
				finaly=(int) y;
				//System.out.println(".................." + x + "......" + y); // x=
																				// 345
																				// y=530
				invalidate();
				break;
			}
			return true;
		}
		
		//Drawable myPic = getResources().getDrawable(R.drawable.frame);
		private byte[] data = MainActivity.getbitmapData();
        Bitmap mImage = BitmapFactory.decodeByteArray(data, 0, data.length);
        
        Drawable myMask = getResources().getDrawable(R.drawable.mask);
        Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();
        
        Bitmap mmImage = bitmapRotate(mImage);
        
        Bitmap mmmImage = getResizedBitmap(mmImage,height,width);
        
        Bitmap mmMask = getResizedBitmap(mMask,wMask,wMask);
        
		@Override
		public void onDraw(Canvas canvas) {
	
			Paint maskPaint = new Paint();
            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            
            Paint imagePaint = new Paint();
            imagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

            canvas.drawBitmap(mmMask,x,y,maskPaint);
        	canvas.drawBitmap(mmmImage,0,0,imagePaint);
        	
		}	
		
		public Bitmap bitmapRotate(Bitmap original){
			Bitmap rotated;
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			rotated = Bitmap.createBitmap(original, 0, 0, 
			                              original.getWidth(), original.getHeight(), 
			                              matrix, true);
			return rotated;
		}
		
		public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
			 
			int width = bm.getWidth();
			 
			int height = bm.getHeight();
			 
			float scaleWidth = ((float) newWidth) / width;
			 
			float scaleHeight = ((float) newHeight) / height;
			 
			// create a matrix for the manipulation
			 
			Matrix matrix = new Matrix();
			 
			// resize the bit map
			 
			matrix.postScale(scaleWidth, scaleHeight);
			 
			// recreate the new Bitmap
			 
			Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
			return resizedBitmap;
			 
			}
		
	}
}