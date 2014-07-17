package com.example.cameraapp;

import java.io.ByteArrayOutputStream;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MaskActivity extends Activity {

	static int id = 0;
	private Button saveButton;
	// private Button effectsButton;
	public Bitmap croppedBitmap;
	public static byte[] bitData;
	public int finalx, finaly;
	
	
	// float heightMaskConst = 0.51056f;
	
	ImageView filter1;
	ImageView filter2;
	ImageView filter3;
	ImageView filter4;
	ImageView filter5;
	ImageView filter6;
	ImageView filter7;
	Filter filter = new Filter();

	// int hMask;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mask_activity);
		filter1 = (ImageView) findViewById(R.id.filter1);
		filter2 = (ImageView) findViewById(R.id.filter2);
		filter3 = (ImageView) findViewById(R.id.filter3);
		filter4 = (ImageView) findViewById(R.id.filter4);
		filter5 = (ImageView) findViewById(R.id.filter5);
		filter6 = (ImageView) findViewById(R.id.filter6);
		filter7 = (ImageView) findViewById(R.id.filter7);
		final Filter filter = new Filter();
		filter.onDrawing();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i("x", "y");
		
		Log.i("wMask", "" + MainActivity.wMask);
		final LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		final DrawingView dv = new DrawingView(this);
		ll.addView(dv);

		saveButton = (Button) findViewById(R.id.save_button);

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(getBaseContext(), "reaching",
				// Toast.LENGTH_SHORT).show();

				class ToSaveCropped {

					class NewDrawingView extends View {
						// Bitmap bitmap;
						float x, y;
						int id;

						public NewDrawingView(Context context) {
							super(context);
							// bitmap =
							// BitmapFactory.decodeResource(context.getResources(),
							// R.drawable.ic_launcher);
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
								finalx = (int) x;
								y = (int) event.getY();
								finaly = (int) y;
								// System.out.println(".................." + x +
								// "......" + y);
								// // x=
								// 345
								// y=530
								invalidate();
								break;
							}
							return true;
						}

						// byte[] data = MainActivity.getbitmapData();
						// Bitmap mImage = BitmapFactory.decodeByteArray(data,
						// 0,
						// data.length);

						Bitmap mImage = MainActivity.bitmap;

						Drawable myMask = getResources().getDrawable(
								R.drawable.mask);
						Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();

						//Bitmap mmImage = filter.bitmapRotate(mImage);

						//Bitmap mmmImage = filter.getResizedBitmap(mmImage,
								//height, width);

						//Bitmap mmMask = filter.getResizedBitmap(mMask, wMask,
								//wMask);

						@Override
						public void onDraw(Canvas canvas) {
							// Log.i(""+data[0],""+data[1]);

							Paint maskPaint = new Paint();
							maskPaint.setXfermode(new PorterDuffXfermode(
									PorterDuff.Mode.DST_IN));

							Paint imagePaint = new Paint();
							imagePaint.setXfermode(new PorterDuffXfermode(
									PorterDuff.Mode.DST_OVER));

							canvas.drawBitmap(mMask, x, y, maskPaint);
							canvas.drawBitmap(mImage, 0, 0, imagePaint);

						}
					}

					NewDrawingView ndv = new NewDrawingView(getBaseContext());
					float x = finalx;
					float y = finaly;

					// private byte[] data = MainActivity.getbitmapData();
					Bitmap mImage = Filter.filteredBitmap;

					Drawable myMask = getResources().getDrawable(
							R.drawable.mask);
					// Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();

					// Bitmap mmImage = dv.mmImage;

					//Bitmap mmmImage = filter.getResizedBitmap(ndv.mmImage,
							//height, width);

					Bitmap mmMask = filter.getResizedBitmap(dv.mMask, MainActivity.wMask,
							MainActivity.wMask);

					public void onDraw() {

						Canvas canvas;
						int w = mmMask.getWidth(), h = mmMask.getHeight();
						Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see
																		// other
																		// conf
																		// types
						croppedBitmap = Bitmap.createBitmap(w, h, conf); // this
																			// creates
																			// a
																			// MUTABLE
																			// bitmap
						canvas = new Canvas(croppedBitmap);

						Paint maskPaint = new Paint();
						maskPaint.setXfermode(new PorterDuffXfermode(
								PorterDuff.Mode.DST_IN));

						Paint imagePaint = new Paint();
						imagePaint.setXfermode(new PorterDuffXfermode(
								PorterDuff.Mode.DST_OVER));
						// Log.i(""+x,""+y);
						canvas.drawBitmap(mmMask,x,y,maskPaint);
						canvas.drawBitmap(mImage, -x, -y, imagePaint);
					}

				}
				ToSaveCropped tsc = new ToSaveCropped();
				tsc.onDraw();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				croppedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				bitData = stream.toByteArray();

				SaveFile sv = new SaveFile();
				sv.save();

				MediaStore.Images.Media.insertImage(getContentResolver(),
						croppedBitmap, "", "");

				// dv.bitmapRecycle(croppedBitmap);

			}
		});

		HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		filter1.setImageBitmap(filter.image);
		filter2.setImageBitmap(filter.image);
		filter3.setImageBitmap(filter.image);
		filter4.setImageBitmap(filter.image);
		filter5.setImageBitmap(filter.image);
		filter6.setImageBitmap(filter.image);
		filter7.setImageBitmap(filter.image);
		//int id;

		class NewDrawingView extends View {
			// Bitmap bitmap;
			float x, y;
			//int id;

			public NewDrawingView(Context context) {
				super(context);
				// bitmap =
				// BitmapFactory.decodeResource(context.getResources(),
				// R.drawable.ic_launcher);
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
					finalx = (int) x;
					y = (int) event.getY();
					finaly = (int) y;
					// System.out.println(".................." + x +
					// "......" + y);
					// // x=
					// 345
					// y=530
					invalidate();
					break;
				}
				return true;
			}

			// byte[] data = MainActivity.getbitmapData();
			// Bitmap mImage = BitmapFactory.decodeByteArray(data, 0,
			// data.length);


			Drawable myMask = getResources().getDrawable(R.drawable.mask);
			Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();
			
			Bitmap mImage = Filter.filteredBitmap;

			//Bitmap mmImage = filter.bitmapRotate(mImage);

			//Bitmap mmmImage = filter.getResizedBitmap(mmImage, height, width);

			//Bitmap mmMask = filter.getResizedBitmap(mMask, wMask, wMask);

			@Override
			public void onDraw(Canvas canvas) {
				// Log.i(""+data[0],""+data[1]);

				Paint maskPaint = new Paint();
				maskPaint.setXfermode(new PorterDuffXfermode(
						PorterDuff.Mode.DST_IN));

				Paint imagePaint = new Paint();
				imagePaint.setXfermode(new PorterDuffXfermode(
						PorterDuff.Mode.DST_OVER));

				canvas.drawBitmap(mMask, x, y, maskPaint);
				canvas.drawBitmap(mImage, 0, 0, imagePaint);

			}

			public void bitmapRecycle(Bitmap bitmap) {
				bitmap.recycle();
				bitmap = null;
			}
		}

		filter1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Filter.id = 1;
				Filter.onDrawing();
				ll.removeAllViews();
				NewDrawingView ndv = new NewDrawingView(getBaseContext());
				ll.addView(ndv);
			}
		});

		filter2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Filter.id = 2;
				Filter.onDrawing();
				ll.removeAllViews();
				NewDrawingView ndv = new NewDrawingView(getBaseContext());
				ll.addView(ndv);
			}
		});

		filter3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Filter.id = 3;
				Filter.onDrawing();
				NewDrawingView ndv = new NewDrawingView(getBaseContext());
				ll.addView(ndv);
			}
		});

		filter4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Filter.id = 4;
				Filter.onDrawing();
				NewDrawingView ndv = new NewDrawingView(getBaseContext());
				ll.addView(ndv);
			}
		});

		filter5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Filter.id = 5;
				Filter.onDrawing();
				NewDrawingView ndv = new NewDrawingView(getBaseContext());
				ll.addView(ndv);
			}
		});

		filter6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Filter.id = 6;
				Filter.onDrawing();
				NewDrawingView ndv = new NewDrawingView(getBaseContext());
				ll.addView(ndv);
			}
		});

		filter7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Filter.id = 7;
				Filter.onDrawing();
				NewDrawingView ndv = new NewDrawingView(getBaseContext());
				ll.addView(ndv);
			}
		});

	}

	class DrawingView extends View {
		// Bitmap bitmap;
		float x, y;
		int id;

		public DrawingView(Context context) {
			super(context);
			// bitmap = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.ic_launcher);
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
				finalx = (int) x;
				y = (int) event.getY();
				finaly = (int) y;
				// System.out.println(".................." + x + "......" + y);
				// // x=
				// 345
				// y=530
				invalidate();
				break;
			}
			return true;
		}

		//byte[] data = MainActivity.getbitmapData();
		Bitmap mImage = MainActivity.bitmap;

		Drawable myMask = getResources().getDrawable(R.drawable.mask);
		Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();

		//Bitmap mmImage = filter.bitmapRotate(mImage);

		//Bitmap mmmImage = filter.getResizedBitmap(mmImage, height, width);

		//Bitmap mmMask = filter.getResizedBitmap(mMask, wMask, wMask);

		@Override
		public void onDraw(Canvas canvas) {
			// Log.i(""+data[0],""+data[1]);

			Paint maskPaint = new Paint();
			maskPaint
					.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

			Paint imagePaint = new Paint();
			imagePaint.setXfermode(new PorterDuffXfermode(
					PorterDuff.Mode.DST_OVER));

			canvas.drawBitmap(mMask, x, y, maskPaint);
			canvas.drawBitmap(mImage, 0, 0, imagePaint);

		}
	}
}