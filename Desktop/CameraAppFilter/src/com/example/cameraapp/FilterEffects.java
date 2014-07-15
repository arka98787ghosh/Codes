package com.example.cameraapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;

public class FilterEffects {

	public Bitmap doInvert(Bitmap src) {
		// create new bitmap with the same settings as source bitmap
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				src.getConfig());
		// color info
		int A, R, G, B;
		int pixelColor;
		// image size
		int height = src.getHeight();
		int width = src.getWidth();

		// scan through every pixel
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// get one pixel
				pixelColor = src.getPixel(x, y);
				// saving alpha channel
				A = Color.alpha(pixelColor);
				// inverting byte for each R/G/B channel
				R = 255 - Color.red(pixelColor);
				G = 255 - Color.green(pixelColor);
				B = 255 - Color.blue(pixelColor);
				// set newly-inverted pixel to output image
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}
		return bmOut;
	}
	
	public Bitmap smooth(Bitmap src, double value) {
	    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
	    convMatrix.setAll(1);
	    convMatrix.Matrix[1][1] = value;
	    convMatrix.Factor = value + 8;
	    convMatrix.Offset = 1;
	    return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}
	
	public Bitmap emboss(Bitmap src) {
	    double[][] EmbossConfig = new double[][] {
	        { -1 ,  0, -1 },
	        {  0 ,  4,  0 },
	        { -1 ,  0, -1 }
	    };
	    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
	    convMatrix.applyConfig(EmbossConfig);
	    convMatrix.Factor = 1;
	    convMatrix.Offset = 127;
	    return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}
	
	public Bitmap applyGaussianBlur(Bitmap src) {
	    double[][] GaussianBlurConfig = new double[][] {
	        { 1, 2, 1 },
	        { 2, 4, 2 },
	        { 1, 2, 1 }
	    };
	    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
	    convMatrix.applyConfig(GaussianBlurConfig);
	    convMatrix.Factor = 16;
	    convMatrix.Offset = 0;
	    return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}
	 
	public Bitmap flip(Bitmap src) {
	    // create new matrix for transformation
	    Matrix matrix = new Matrix();
	    matrix.preScale(-1.0f, 1.0f);
	 
	    // return transformed image
	    return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}
	
	public Bitmap engrave(Bitmap src) {
	    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
	    convMatrix.setAll(0);
	    convMatrix.Matrix[0][0] = -2;
	    convMatrix.Matrix[1][1] = 2;
	    convMatrix.Factor = 1;
	    convMatrix.Offset = 95;
	    return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}
	
}
