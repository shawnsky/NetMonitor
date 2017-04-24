package com.xtlog.shawn.netmonitor.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtils {
	
	public static Bitmap getResizedBitmap(Drawable drawable){
		
		BitmapDrawable bitmapDrawable =  (BitmapDrawable)drawable;
		Bitmap bitmap = bitmapDrawable.getBitmap();
		return Bitmap.createScaledBitmap(bitmap, 96, 96, false);
	}

}
