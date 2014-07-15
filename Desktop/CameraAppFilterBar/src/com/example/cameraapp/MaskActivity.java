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

	private Button saveButton;
	// private Button effectsButton;
	public Bitmap croppedBitmap;
	public static byte[] bitData;
	public int finalx, finaly;
	int height;
	int width;
	float widthMaskConst = 0.93125f;
	// float heightMaskConst = 0.51056f;
	int wMask;
	Filter filter = new Filter();

	// int hMask;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mask_activity);

		Display display = getWindowManager().getDefaultDisplay();
		Point point = new Point();
		point.x = display.getWidth();
		point.y = display.getHeight();
		width = point.x;
		height = point.y;
		Log.i("x", "y");
		wMask = (int) (width * widthMaskConst);
		Log.i("wMask", "" + wMask);
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

					float x = finalx;
					float y = finaly;

					// private byte[] data = MainActivity.getbitmapData();
					// Bitmap mImage = BitmapFactory.decodeByteArray(data, 0,
					// data.length);

					Drawable myMask = getResources().getDrawable(
							R.drawable.mask);
					// Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();

					// Bitmap mmImage = dv.mmImage;

					Bitmap mmmImage = dv.getResizedBitmap(dv.mmImage, height,
							width);

					Bitmap mmMask = dv.getResizedBitmap(dv.mMask, wMask, wMask);

					public void onDraw() {

						dv.bitmapRecycle(dv.mImage);
						dv.bitmapRecycle(dv.mmImage);
						dv.bitmapRecycle(dv.mMask);

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
						// canvas.drawBitmap(mMask,x,y,maskPaint);
						canvas.drawBitmap(mmmImage, -x, -y, imagePaint);
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

		dv.onDrawing();

		HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);

		LinearLayout topLinearLayout = new LinearLayout(this);
		// topLinearLayout.setLayoutParams(android.widget.LinearLayout.LayoutParams.FILL_PARENT,android.widget.LinearLayout.LayoutParams.FILL_PARENT);
		topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

		for (int i = 0; i < 6; i++) {

			final ImageView imageView = new ImageView(this);

			imageView.setTag(i);

			imageView.setImageBitmap(dv.images[i]);

			// Log.i("as", "ds");

			topLinearLayout.addView(imageView);

			imageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Toast.makeText(getBaseContext(),
					// "Oh so you want this filter!!",
					// Toast.LENGTH_SHORT).show();
				}
			});

		}

		scrollView.addView(topLinearLayout);

	}

	class DrawingView extends View {
		// Bitmap bitmap;
		float x, y;

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

		// Drawable myPic = getResources().getDrawable(R.drawable.frame);
		
		byte[] data = MainActivity.getbitmapData();
		Bitmap mImage = BitmapFactory.decodeByteArray(data, 0, data.length);
		
		Drawable myMask = getResources().getDrawable(R.drawable.mask);
		Bitmap mMask = ((BitmapDrawable) myMask).getBitmap();

		Bitmap mmImage = bitmapRotate(mImage);

		Bitmap mmmImage = getResizedBitmap(mmImage, height, width);

		Bitmap mmMask = getResizedBitmap(mMask, wMask, wMask);

		@Override
		public void onDraw(Canvas canvas) {
			// Log.i(""+data[0],""+data[1]);

			Paint maskPaint = new Paint();
			maskPaint
					.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

			Paint imagePaint = new Paint();
			imagePaint.setXfermode(new PorterDuffXfermode(
					PorterDuff.Mode.DST_OVER));

			canvas.drawBitmap(mmMask, x, y, maskPaint);
			canvas.drawBitmap(mmmImage, 0, 0, imagePaint);

		}

		Bitmap modFilterImage;
		Bitmap mmodFilterImage;
		Bitmap filterImage = BitmapFactory
				.decodeByteArray(data, 0, data.length);
		FilterEffects fe = new FilterEffects();
		Bitmap[] images = new Bitmap[6];

		// @Override
		public void onDrawing() {
			// Log.i("a", "d");

			modFilterImage = fe.doInvert(filterImage);
			images[0] = getResizedBitmap(modFilterImage,
					modFilterImage.getHeight() / 5,
					modFilterImage.getWidth() / 5);
			Log.i("a", "d");
			// images[0].setImageBitmap(mmodFilterImage);
			Log.i("a", "d");

			modFilterImage = fe.smooth(filterImage, 1);
			images[1] = getResizedBitmap(modFilterImage,
					modFilterImage.getHeight() / 5,
					modFilterImage.getWidth() / 5);

			// images[1].setImageBitmap(mmodFilterImage);

			modFilterImage = fe.emboss(filterImage);
			images[2] = getResizedBitmap(modFilterImage,
					modFilterImage.getHeight() / 5,
					modFilterImage.getWidth() / 5);

			// images[2].setImageBitmap(mmodFilterImage);

			modFilterImage = fe.applyGaussianBlur(filterImage);
			images[3] = getResizedBitmap(modFilterImage,
					modFilterImage.getHeight() / 5,
					modFilterImage.getWidth() / 5);

			// images[3].setImageBitmap(mmodFilterImage);

			modFilterImage = fe.flip(filterImage);
			images[4] = getResizedBitmap(modFilterImage,
					modFilterImage.getHeight() / 5,
					modFilterImage.getWidth() / 5);

			// images[4].setImageBitmap(mmodFilterImage);

			modFilterImage = fe.engrave(filterImage);
			images[5] = getResizedBitmap(modFilterImage,
					modFilterImage.getHeight() / 5,
					modFilterImage.getWidth() / 5);

			// images[5].setImageBitmap(mmodFilterImage);

		}

		public Bitmap bitmapRotate(Bitmap original) {
			Bitmap rotated;
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			rotated = Bitmap.createBitmap(original, 0, 0, original.getWidth(),
					original.getHeight(), matrix, true);
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

			Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
					matrix, false);
			return resizedBitmap;

		}

		public void bitmapRecycle(Bitmap bitmap) {
			bitmap.recycle();
			bitmap = null;
		}
	}
}