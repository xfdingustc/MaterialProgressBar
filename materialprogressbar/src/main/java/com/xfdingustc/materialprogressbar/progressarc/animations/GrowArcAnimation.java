package com.xfdingustc.materialprogressbar.progressarc.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class GrowArcAnimation implements ArcAnimation {
  private ValueAnimator mAnimator;

  public GrowArcAnimation(ValueAnimator.AnimatorUpdateListener updateListener, Animator
      .AnimatorListener listener) {
    mAnimator = ValueAnimator.ofFloat(ArcAnimationFactory.MIN_SWEEP_ANGLE, ArcAnimationFactory
        .MAX_SWEEP_ANGLE);
    mAnimator.setInterpolator(new DecelerateInterpolator());
    mAnimator.setDuration(ArcAnimationFactory.SWEEP_ANIMATOR_DURATION);
    mAnimator.addUpdateListener(updateListener);
    mAnimator.addListener(listener);
  }

  @Override
  public ValueAnimator getAnimator() {
    return mAnimator;
  }
}
