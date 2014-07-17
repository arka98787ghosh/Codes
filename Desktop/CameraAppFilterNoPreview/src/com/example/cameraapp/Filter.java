package com.example.cameraapp;

import com.jhlabs.image.ContrastFilter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class Filter {

	static Bitmap image;
	static Bitmap toRotate;
	static Bitmap resizedBitmap;
	static Bitmap fullImage = MainActivity.previewBitmap;
	static Bitmap filteredBitmap;
	static int id = 1;

	byte[] data = MainActivity.bitmapData;

	Bitmap filterImage = BitmapFactory.decodeByteArray(data, 0, data.length);

	public static void onDrawing() {
		Log.i("a", "d");
		FilterEffects fe = new FilterEffects();

		image = getResizedBitmap(fullImage, fullImage.getHeight(),
				fullImage.getWidth());

		switch (id) {
		case 1:
			filteredBitmap = MainActivity.bitmap;
			break;
		case 2:
			filteredBitmap = FilterEffects.doInvert(MainActivity.bitmap);
			break;
		case 3:
			filteredBitmap = FilterEffects.doGamma(MainActivity.bitmap,1,1,1);
			break;
		case 4:
			filteredBitmap = FilterEffects.doGreyscale(MainActivity.bitmap);
			break;
		case 5:
			filteredBitmap = FilterEffects.createContrast(MainActivity.bitmap, 50);
			break;
		case 6:
			filteredBitmap = FilterEffects.doBrightness(MainActivity.bitmap, 30);
			break;
		case 7:
			filteredBitmap = FilterEffects.createSepiaToningEffect(MainActivity.bitmap, 0, 1.0, 1.0, 1.0);	
			break;
		default:
			break;
		}

		/*
		 * 
		 * images[1] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth()); Log.i("a", "d"); //
		 * images[0].setImageBitmap(mmodFilterImage); Log.i("a", "d");
		 * 
		 * images[2] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[1].setImageBitmap(mmodFilterImage);
		 * 
		 * images[3] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[2].setImageBitmap(mmodFilterImage);
		 * 
		 * images[4] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[3].setImageBitmap(mmodFilterImage);
		 * 
		 * images[5] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[4].setImageBitmap(mmodFilterImage);
		 * 
		 * images[6] = getResizedBitmap(fullImage, fullImage.getHeight(),
		 * fullImage.getWidth());
		 * 
		 * // images[5].setImageBitmap(mmodFilterImage);
		 */

	}

	public static Bitmap bitmapRotate(Bitmap original) {
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		toRotate = Bitmap.createBitmap(original, 0, 0, original.getWidth(),
				original.getHeight(), matrix, true);
		return toRotate;
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

		int width = bm.getWidth();

		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;

		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation

		Matrix matrix = new Matrix();

		// resize the bit map

		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap

		resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				false);
		return resizedBitmap;

	}

	public void bitmapRecycle(Bitmap bitmap) {
		bitmap.recycle();
		bitmap = null;
	}

}
