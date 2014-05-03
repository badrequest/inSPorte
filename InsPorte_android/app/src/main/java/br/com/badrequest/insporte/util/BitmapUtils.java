package br.com.badrequest.insporte.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.FloatMath;
import android.widget.ImageView;

import java.io.*;

public class BitmapUtils {
	/** Used to tag logs */
	@SuppressWarnings("unused")
	private static final String TAG = "BitmapUtils";

    public static Bitmap getSampledBitmap(String filePath, int reqWidth, int reqHeight) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		
		BitmapFactory.decodeFile(filePath, options);
		
		// Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	        if (width > height) {
	            inSampleSize = (int)FloatMath.floor(((float)height / reqHeight)+0.5f); //Math.round((float)height / (float)reqHeight);
	        } else {
	            inSampleSize = (int)FloatMath.floor(((float)width / reqWidth)+0.5f); //Math.round((float)width / (float)reqWidth);
	        }
	    }
	    
	    options.inSampleSize = inSampleSize;
	    options.inJustDecodeBounds = false;
	    	    
	    return BitmapFactory.decodeFile(filePath, options);
	}


    public static Bitmap resizeImage(String path, int targetH, int targetW) {

        Options bmOptions = new Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(path, bmOptions);

    }

    public static String openAsBase64(String imgPath) {
        Bitmap photo = BitmapFactory.decodeFile(imgPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 85, baos);
        byte[] b = baos.toByteArray();
        photo.recycle();
        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    public static BitmapSize getBitmapSize(String filePath) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		
		BitmapFactory.decodeFile(filePath, options);
		
		return new BitmapSize(options.outWidth, options.outHeight);
	}
	
	public static BitmapSize getScaledSize(int originalWidth, int originalHeight, int numPixels) {
		float ratio = (float)originalWidth/originalHeight;
		
		int scaledHeight = (int)FloatMath.sqrt((float)numPixels/ratio);
		int scaledWidth = (int)(ratio * FloatMath.sqrt((float)numPixels/ratio));
				
		return new BitmapSize(scaledWidth, scaledHeight);
	}

    public static void stripImageView(ImageView view) {
        if ( view.getDrawable() instanceof BitmapDrawable) {
            ((BitmapDrawable)view.getDrawable()).getBitmap().recycle();
        }
        view.getDrawable().setCallback(null);
        view.setImageDrawable(null);
        view.getResources().flushLayoutCache();
        view.destroyDrawingCache();
    }

    public static void deleteImage(String path) {
        File image = new File(path);
        image.delete();
    }

    public static void saveResizedImage(String fileName, String newFileName) throws IOException {
        File newFile = new File(newFileName);
        newFile.createNewFile();

        OutputStream fOut = new FileOutputStream(newFile);
        BitmapSize originalSize = getBitmapSize(fileName);

        BitmapSize newSize = getScaledSize(originalSize.width, originalSize.height, 655630); //1.3Megapixel

        Bitmap foto = resizeImage(fileName, newSize.height, newSize.width);
        foto.compress(Bitmap.CompressFormat.JPEG, 85, fOut);

        fOut.flush();
        fOut.close();

        foto.recycle();
    }


    public static class BitmapSize {
		public int width;
		public int height;
		
		public BitmapSize(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}
}
