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

public class Filter extends Activity {
	
	ImageView filter1;
	ImageView filter2;
	ImageView filter3;
	ImageView filter4;
	ImageView filter5;
	ImageView filter6;
	Bitmap[] images;
	Bitmap[] fullImages;
	
	byte[] data = MainActivity.bitmapData;
	//Drawable myDrawable = getResources().getDrawable(R.drawable.ic_launcher);
	//Bitmap filterImage = ((BitmapDrawable) myDrawable).getBitmap();
	
	 Bitmap filterImage = BitmapFactory.decodeByteArray(data, 0, data.length); 
	//@Override
	public void onDrawing() {
		Log.i("a", "d");
		FilterEffects fe =new FilterEffects();
		fullImages[0] = fe.doInvert(filterImage);
		images[0] = getResizedBitmap(fullImages[0], fullImages[0].getHeight()/5,fullImages[0].getWidth()/5);
		Log.i("a", "d");
		//images[0].setImageBitmap(mmodFilterImage);
		Log.i("a", "d");
		
		fullImages[1] = fe.smooth(filterImage,1);
		images[1] = getResizedBitmap(fullImages[1], fullImages[1].getHeight()/5,fullImages[1].getWidth()/5);
		
		//images[1].setImageBitmap(mmodFilterImage);
		
		fullImages[2] = fe.emboss(filterImage);
		images[2] = getResizedBitmap(fullImages[2], fullImages[2].getHeight()/5,fullImages[2].getWidth()/5);
		
		//images[2].setImageBitmap(mmodFilterImage);
		
		fullImages[3] = fe.applyGaussianBlur(filterImage);
		images[3] = getResizedBitmap(fullImages[3], fullImages[3].getHeight()/5,fullImages[3].getWidth()/5);
		
		//images[3].setImageBitmap(mmodFilterImage);
		
		fullImages[4] = fe.flip(filterImage);
		images[4] = getResizedBitmap(fullImages[4], fullImages[4].getHeight()/5,fullImages[4].getWidth()/5);
		
		//images[4].setImageBitmap(mmodFilterImage);
		
		fullImages[5] = fe.engrave(filterImage);
		images[5] = getResizedBitmap(fullImages[5], fullImages[5].getHeight()/5,fullImages[5].getWidth()/5);
		
		//images[5].setImageBitmap(mmodFilterImage);
		
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
	
	public void bitmapRecycle(Bitmap bitmap) {
		bitmap.recycle();
		bitmap = null;
	}

}
