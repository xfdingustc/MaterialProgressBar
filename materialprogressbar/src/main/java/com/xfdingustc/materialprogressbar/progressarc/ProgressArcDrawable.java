package com.xfdingustc.materialprogressbar.progressarc;



import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;

import com.xfdingustc.materialprogressbar.progressarc.animations.ArcAnimationFactory;
import com.xfdingustc.materialprogressbar.utils.AnimationUtils;

final class ProgressArcDrawable extends Drawable implements Animatable {

    private final RectF arcBounds = new RectF();

    private float currentSweepAngle;
    private float currentRotationAngleOffset;
    private float currentRotationAngle;

    private ArcAnimationFactory animationFactory;
    private ValueAnimator rotateAnim;
    private ValueAnimator growAnim;
    private ValueAnimator shrinkAnim;
    private ValueAnimator completeAnim;

    private boolean animationPlaying;
    private boolean growing;
    private boolean completeAnimOnNextCycle;

    private Paint paint;

    private float strokeWidth;
    private int arcColor;
    private int minSweepAngle;
    private int maxSweepAngle;

    private ArcListener internalListener;

    ProgressArcDrawable(float strokeWidth, int arcColor, boolean roundedStroke) {
        this.strokeWidth = strokeWidth;
        this.arcColor = arcColor;
        initPaint(roundedStroke);
        setupAnimations();
    }

    private void initPaint(boolean roundedStroke) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(roundedStroke ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        paint.setColor(arcColor);
    }

    private void setupAnimations() {
        animationFactory = new ArcAnimationFactory();
        minSweepAngle = ArcAnimationFactory.MIN_SWEEP_ANGLE;
        maxSweepAngle = ArcAnimationFactory.MAX_SWEEP_ANGLE;

        setupRotateAnimation();
        setupGrowAnimation();
        setupShrinkAnimation();
        setupCompleteAnimation();
    }

    private void setupRotateAnimation() {
        rotateAnim = animationFactory.build(ArcAnimationFactory.Type.ROTATE,
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float angle = AnimationUtils.getAnimatedFraction(animation) * 360f;
                    updateCurrentRotationAngle(angle);
                }
            }, null);
    }

    private void setupGrowAnimation() {
        growAnim = animationFactory.build(ArcAnimationFactory.Type.GROW,
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedFraction = AnimationUtils.getAnimatedFraction(animation);
                    float angle = minSweepAngle + animatedFraction * (maxSweepAngle - minSweepAngle);
                    updateCurrentSweepAngle(angle);
                }
            }, new Animator.AnimatorListener() {
                boolean cancelled = false;

                @Override
                public void onAnimationStart(Animator animation) {
                    cancelled = false;
                    growing = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!cancelled) {
                        setShrinking();
                        shrinkAnim.start();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    cancelled = true;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
    }

    private void setupShrinkAnimation() {
        shrinkAnim = animationFactory.build(ArcAnimationFactory.Type.SHRINK,
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedFraction = AnimationUtils.getAnimatedFraction(animation);
                    updateCurrentSweepAngle(
                        maxSweepAngle - animatedFraction * (maxSweepAngle - minSweepAngle));
                }
            }, new Animator.AnimatorListener() {
                boolean cancelled;

                @Override
                public void onAnimationStart(Animator animation) {
                    cancelled = false;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!cancelled) {
                        setGrowing();
                        if (completeAnimOnNextCycle) {
                            completeAnimOnNextCycle = false;
                            completeAnim.start();
                        } else {
                            growAnim.start();
                        }
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    cancelled = true;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
    }

    private void setupCompleteAnimation() {
        completeAnim = animationFactory.build(ArcAnimationFactory.Type.COMPLETE,
            new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedFraction = AnimationUtils.getAnimatedFraction(animation);
                    float angle = minSweepAngle + animatedFraction * 360;
                    updateCurrentSweepAngle(angle);
                }
            }, new Animator.AnimatorListener() {
                boolean cancelled = false;

                @Override
                public void onAnimationStart(Animator animation) {
                    cancelled = false;
                    growing = true;
                    rotateAnim.setInterpolator(new DecelerateInterpolator());
                    rotateAnim.setDuration(ArcAnimationFactory.COMPLETE_ROTATE_DURATION);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!cancelled) {
                        stop();
                    }

                    completeAnim.removeListener(this);
                    internalListener.onArcAnimationComplete();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    cancelled = true;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
    }

    @Override
    public void draw(Canvas canvas) {
        float startAngle = currentRotationAngle - currentRotationAngleOffset;
        float sweepAngle = currentSweepAngle;
        if (!growing) {
            startAngle = startAngle + (360 - sweepAngle);
        }

        canvas.drawArc(arcBounds, startAngle, sweepAngle, false, paint);
    }

    public void reset() {
        stop();
        resetProperties();
        setupAnimations();
        start();
    }

    private void resetProperties() {
        currentSweepAngle = 0;
        currentRotationAngle = 0;
        currentRotationAngleOffset = 0;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        arcBounds.left = bounds.left;
        arcBounds.right = bounds.right;
        arcBounds.top = bounds.top;
        arcBounds.bottom = bounds.bottom;
    }

    private void setGrowing() {
        growing = true;
        currentRotationAngleOffset += minSweepAngle;
    }

    private void setShrinking() {
        growing = false;
        currentRotationAngleOffset = currentRotationAngleOffset + (360 - maxSweepAngle);
    }

    @Override
    public void start() {
        animationPlaying = true;
        resetProperties();
        rotateAnim.start();
        growAnim.start();
        invalidateSelf();
    }

    @Override
    public void stop() {
        animationPlaying = false;
        stopAnimators();
        invalidateSelf();
    }

    private void stopAnimators() {
        rotateAnim.cancel();
        growAnim.cancel();
        shrinkAnim.cancel();
        completeAnim.cancel();
    }

    void requestCompleteAnimation(final ArcListener internalListener) {
        if (!isRunning() || completeAnim.isRunning()) {
            return;
        }

        this.internalListener = internalListener;
        startCompleteAnimationOnNextCycle();
    }

    private void startCompleteAnimationOnNextCycle() {
        completeAnimOnNextCycle = true;
    }

    void updateCurrentRotationAngle(float currentRotationAngle) {
        this.currentRotationAngle = currentRotationAngle;
        invalidateSelf();
    }

    void updateCurrentSweepAngle(float currentSweepAngle) {
        this.currentSweepAngle = currentSweepAngle;
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return animationPlaying;
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.RGB_565;
    }
}

