package com.xfdingustc.materialprogressbar.progressarc;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class ProgressArcDrawable extends Drawable {
  private final static String TAG = ProgressArcDrawable.class.getSimpleName();
  private int mArcColor;
  private float mStrokeWidth;
  private Paint mPaint;

  private float mCurrentRotationAngle = 180;
  private float mCurrentRotationAngleOffset;
  private float mCurrentSweepAngle = 0;

  private RectF mArcBounds = new RectF();

  ProgressArcDrawable(float strokeWidth, int arcColor, boolean roundedStroke) {
    this.mStrokeWidth = strokeWidth;
    this.mArcColor = arcColor;

    initPaint(roundedStroke);
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

    startAngle = 0;
    sweepAngle = 350;

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
