package com.example.dragimageview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);    
	    LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
	    DrawingView dv= new DrawingView(this);
	    ll.addView(dv);
	}

	class DrawingView extends View {
		Bitmap bitmap;

		float x, y;

		public DrawingView(Context context) {
			super(context);
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_launcher);
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
				y = (int) event.getY();
				//System.out.println(".................." + x + "......" + y); // x=
																				// 345
																				// y=530
				invalidate();
				break;
			}
			return true;
		}
		
		Drawable myPic = getResources().getDrawable(R.drawable.frame);
        Bitmap mImage = ((BitmapDrawable) myPic).getBitmap();
        
        Drawable myMask = getResources().getDrawable(R.drawable.mask);
        Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();
		

		@Override
		public void onDraw(Canvas canvas) {
			Paint maskPaint = new Paint();
            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

            Paint imagePaint = new Paint();
            imagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            
            canvas.drawBitmap(mMask,x,y,maskPaint);
        	canvas.drawBitmap(mImage,0,0,imagePaint);
		}
	}
}