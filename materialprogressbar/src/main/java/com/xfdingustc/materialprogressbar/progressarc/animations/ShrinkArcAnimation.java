package com.xfdingustc.materialprogressbar.progressarc.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class ShrinkArcAnimation implements ArcAnimation {
  private ValueAnimator mAnimation;

  public ShrinkArcAnimation(ValueAnimator.AnimatorUpdateListener updateListener, Animator.AnimatorListener
      listener) {
    mAnimation = ValueAnimator.ofFloat(ArcAnimationFactory.MAX_SWEEP_ANGLE, ArcAnimationFactory
        .MIN_SWEEP_ANGLE);
    mAnimation.setInterpolator(new DecelerateInterpolator());
    mAnimation.setDuration(ArcAnimationFactory.SWEEP_ANIMATOR_DURATION);
    mAnimation.addUpdateListener(updateListener);
    mAnimation.addListener(listener);
  }

  @Override
  public ValueAnimator getAnimator() {
    return mAnimation;
  }
}
