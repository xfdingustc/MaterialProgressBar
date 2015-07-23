package com.xfdingustc.materialprogressbar.progressarc;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.xfdingustc.materialprogressbar.progressarc.animations.ArcAnimation;
import com.xfdingustc.materialprogressbar.progressarc.animations.ArcAnimationFactory;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class ProgressArcDrawable extends Drawable implements Animatable{
  private final static String TAG = ProgressArcDrawable.class.getSimpleName();
  private int mArcColor;
  private float mStrokeWidth;
  private Paint mPaint;

  private float mCurrentRotationAngle = 180;
  private float mCurrentRotationAngleOffset;
  private float mCurrentSweepAngle = 0;

  private ArcAnimationFactory mAnimationFactory = new ArcAnimationFactory();

  private RectF mArcBounds = new RectF();
  private ValueAnimator mRotateAnimation;

  ProgressArcDrawable(float strokeWidth, int arcColor, boolean roundedStroke) {
    this.mStrokeWidth = strokeWidth;
    this.mArcColor = arcColor;

    initPaint(roundedStroke);
    setupAnimations();
  }

  @Override
  public void start() {
    mRotateAnimation.start();
    invalidateSelf();
  }

  @Override
  public void stop() {

  }

  @Override
  public boolean isRunning() {
    return false;
  }

  private void setupAnimations() {
    setupRotateAniamtio();
  }

  private void setupRotateAniamtio() {
    mRotateAnimation = mAnimationFactory.build(ArcAnimationFactory.Type.ROTATE, new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float angle = (Float)animation.getAnimatedValue();
        updateCurrentRotationAngle(angle);
      }
    });
  }

  private void updateCurrentRotationAngle(float angle) {
    Logger.t(TAG).d("angle = " + angle);
    this.mCurrentRotationAngle = angle;
    invalidateSelf();
  }

  private void initPaint(boolean roundedStroke) {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeWidth(mStrokeWidth);
    mPaint.setStrokeCap(roundedStroke ? Paint.Cap.ROUND : Paint.Cap.ROUND);
    mPaint.setColor(mArcColor);
  }

  @Override
  public void draw(Canvas canvas) {
    float startAngle = mCurrentRotationAngle - mCurrentRotationAngleOffset;
    float sweepAngle = mCurrentSweepAngle;
    sweepAngle = 100;


    canvas.drawArc(mArcBounds, startAngle, sweepAngle, false, mPaint);
  }

  @Override
  public void setAlpha(int alpha) {
    mPaint.setAlpha(alpha);
  }

  @Override
  public void setColorFilter(ColorFilter cf) {
    mPaint.setColorFilter(cf);
  }

  @Override
  public int getOpacity() {
    return PixelFormat.RGB_565;
  }

  @Override
  protected void onBoundsChange(Rect bounds) {
    super.onBoundsChange(bounds);
    mArcBounds.left = bounds.left ;
    mArcBounds.right = bounds.right ;
    mArcBounds.top = bounds.top ;
    mArcBounds.bottom = bounds.bottom ;
  }


}
