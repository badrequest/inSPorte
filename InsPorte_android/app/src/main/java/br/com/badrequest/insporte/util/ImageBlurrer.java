package br.com.badrequest.insporte.util;

/**
 * Created by gmarques on 4/12/14.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class ImageBlurrer {
    public static final int MAX_SUPPORTED_BLUR_PIXELS = 25;

    private RenderScript mRS;
    private ScriptIntrinsicBlur mSIBlur;
    private Allocation mTmpIn;
    private Allocation mTmpOut;

    public ImageBlurrer(Context context) {
        mRS = RenderScript.create(context);
        mSIBlur = ScriptIntrinsicBlur.create(mRS, Element.U8_4(mRS));
    }

    public Bitmap blurBitmap(Bitmap src, float radius) {
        Bitmap dest = Bitmap.createBitmap(src);
        if ((int) radius == 0) {
            return dest;
        }

        if (mTmpIn != null) {
            mTmpIn.destroy();
        }
        if (mTmpOut != null) {
            mTmpOut.destroy();
        }

        mTmpIn = Allocation.createFromBitmap(mRS, src);
        mTmpOut = Allocation.createFromBitmap(mRS, dest);

        mSIBlur.setRadius((int) radius);
        mSIBlur.setInput(mTmpIn);
        mSIBlur.forEach(mTmpOut);
        mTmpOut.copyTo(dest);
        return dest;
    }

    public void destroy() {
        mSIBlur.destroy();
        if (mTmpIn != null) {
            mTmpIn.destroy();
        }
        if (mTmpOut != null) {
            mTmpOut.destroy();
        }
        mRS.destroy();
    }
}
