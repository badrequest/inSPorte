package com.nvanbenschoten.motion;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.widget.ImageView;

/*
 * Copyright 2014 Nathan VanBenschoten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ParallaxImageView extends ImageView implements SensorEventListener {

    private static final String TAG = ParallaxImageView.class.getName();

    /**
     * The intensity of the parallax effect, giving the perspective of depth.
     */
    private float mParallaxIntensity = 1.1f;

    // Instance variables used during matrix manipulation.
    private SensorInterpreter mSensorInterpreter;
    private SensorManager mSensorManager;
    private Matrix mTranslationMatrix;
    private float mXTranslation;
    private float mYTranslation;
    private float mXOffset;
    private float mYOffset;

    public ParallaxImageView(Context context) {
        super(context);
        init(context, null);
    }

    public ParallaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * Initiates the ParallaxImageView with any given attributes.
     */
    private void init(Context context, AttributeSet attrs) {
        // Instantiate future objects
        mTranslationMatrix = new Matrix();
        mSensorInterpreter = new SensorInterpreter();

        // Sets scale type
        setScaleType(ScaleType.MATRIX);

        // Set available attributes
        if (attrs != null) {
            final TypedArray customAttrs = context.obtainStyledAttributes(attrs, R.styleable.ParallaxImageView);

            if (customAttrs != null) {
                if (customAttrs.hasValue(R.styleable.ParallaxImageView_intensity))
                    setParallaxIntensity(customAttrs.getFloat(R.styleable.ParallaxImageView_intensity, mParallaxIntensity));

                if (customAttrs.hasValue(R.styleable.ParallaxImageView_tiltSensitivity))
                    setTiltSensitivity(customAttrs.getFloat(R.styleable.ParallaxImageView_tiltSensitivity,
                            mSensorInterpreter.getTiltSensitivity()));

                if (customAttrs.hasValue(R.styleable.ParallaxImageView_forwardTiltOffset))
                    setForwardTiltOffset(customAttrs.getFloat(R.styleable.ParallaxImageView_forwardTiltOffset,
                            mSensorInterpreter.getForwardTiltOffset()));

                customAttrs.recycle();
            }
        }

        // Configure matrix as early as possible by posting to MessageQueue
        post(new Runnable() {
            @Override
            public void run() {
                configureMatrix();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        configureMatrix();
    }

    /**
     * Sets the intensity of the parallax effect. The stronger the effect, the more distance
     * the image will have to move around.
     *
     * @param parallaxIntensity the new intensity
     */
    public void setParallaxIntensity(float parallaxIntensity) {
        if (parallaxIntensity < 1)
            throw new IllegalArgumentException("Parallax effect must have a intensity of 1.0 or greater");

        mParallaxIntensity = parallaxIntensity;
        configureMatrix();
    }

    /**
     * Sets the parallax tilt sensitivity for the image view. The stronger the sensitivity,
     * the more a given tilt will adjust the image and the smaller needed tilt to reach the
     * image bounds.
     *
     * @param sensitivity the new tilt sensitivity
     */
    public void setTiltSensitivity(float sensitivity) {
        mSensorInterpreter.setTiltSensitivity(sensitivity);
    }

    /**
     * Sets the forward tilt offset dimension, allowing for the image to be
     * centered while the phone is "naturally" tilted forwards.
     *
     * @param forwardTiltOffset the new tilt forward adjustment
     */
    public void setForwardTiltOffset(float forwardTiltOffset) {
        if (Math.abs(forwardTiltOffset) > 1)
            throw new IllegalArgumentException("Parallax forward tilt offset must be less than or equal to 1.0");

        mSensorInterpreter.setForwardTiltOffset(forwardTiltOffset);
    }

    /**
     * Sets the image view's translation coordinates. These values must be between -1 and 1,
     * representing the transaction percentage from the center.
     *
     * @param x the horizontal translation
     * @param y the vertical translation
     */
    private void setTranslate(float x, float y) {
        if (Math.abs(x) > 1 || Math.abs(y) > 1)
            throw new IllegalArgumentException("Parallax effect cannot translate more than 100% of its off-screen size");

        mXTranslation = x * mXOffset;
        mYTranslation = y * mYOffset;

        configureMatrix();
    }

    /**
     * Configures the ImageView's imageMatrix to allow for movement of the
     * source image.
     */
    private void configureMatrix() {
        if (getDrawable() == null || getWidth() == 0 || getHeight() == 0) return;

        int dWidth = getDrawable().getIntrinsicWidth();
        int dHeight = getDrawable().getIntrinsicHeight();
        int vWidth = getWidth();
        int vHeight = getHeight();

        float scale;
        float dx, dy;

        if (dWidth * vHeight > vWidth * dHeight) {
            scale = (float) vHeight / (float) dHeight;
            mXOffset = (vWidth - dWidth * scale * mParallaxIntensity) * 0.5f;
            mYOffset = (vHeight - dHeight * scale * mParallaxIntensity) * 0.5f;
        } else {
            scale = (float) vWidth / (float) dWidth;
            mXOffset = (vWidth - dWidth * scale * mParallaxIntensity) * 0.5f;
            mYOffset = (vHeight - dHeight * scale * mParallaxIntensity) * 0.5f;
        }

        dx = mXOffset + mXTranslation;
        dy = mYOffset + mYTranslation;

        mTranslationMatrix.set(getImageMatrix());
        mTranslationMatrix.setScale(mParallaxIntensity * scale, mParallaxIntensity * scale);
        mTranslationMatrix.postTranslate(dx, dy);
        setImageMatrix(mTranslationMatrix);
    }

    /**
     * Registers a sensor manager with the parallax ImageView. Should be called in onResume
     * from an Activity or Fragment.
     *
     */
    @SuppressWarnings("deprecation")
    public void registerSensorManager() {
        if (getContext() == null || mSensorManager != null) return;

        // Acquires a sensor manager
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager != null) {
            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    /**
     * Unregisters the ParallaxImageView's SensorManager. Should be called in onPause from
     * an Activity or Fragment to avoid continuing sensor usage.
     */
    public void unregisterSensorManager() {
        if (mSensorManager == null) return;

        mSensorManager.unregisterListener(this);
        mSensorManager = null;

        setTranslate(0, 0);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float [] vectors = mSensorInterpreter.interpretSensorEvent(getContext(), event);

        // Return if interpretation of data failed
        if (vectors == null) return;

        // Set translation on ImageView matrix
        setTranslate(vectors[2], vectors[1]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

}
