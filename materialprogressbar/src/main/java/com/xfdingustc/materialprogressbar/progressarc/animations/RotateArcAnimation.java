package com.xfdingustc.materialprogressbar.progressarc.animations;

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;


/**
 * Created by Xiaofei on 2015/7/23.
 */
public class RotateArcAnimation implements ArcAnimation {

  private ValueAnimator mAniamtior;

  public RotateArcAnimation(ValueAnimator.AnimatorUpdateListener updateListener) {
    mAniamtior = ValueAnimator.ofFloat(0f, 360f);
    mAniamtior.setInterpolator(new LinearInterpolator());
    mAniamtior.setDuration(ArcAnimationFactory.ROTATE_ANIMATOR_DURATION);
    mAniamtior.addUpdateListener(updateListener);
    mAniamtior.setRepeatCount(ValueAnimator.INFINITE);
    mAniamtior.setRepeatMode(ValueAnimator.RESTART);
  }

  @Override
  public ValueAnimator getAniamator() {
    return mAniamtior;
  }
}
