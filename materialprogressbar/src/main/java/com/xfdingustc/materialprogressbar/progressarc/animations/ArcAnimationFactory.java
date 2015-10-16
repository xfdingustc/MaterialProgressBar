package com.xfdingustc.materialprogressbar.progressarc.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class ArcAnimationFactory {
  public enum Type {
    ROTATE, GROW, SHRINK, COMPLETE
  }

  public static final int ROTATE_ANIMATOR_DURATION = 2000;
  public static final int SWEEP_ANIMATOR_DURATION = 1000;
  public static final int COMPLETE_ANIM_DURATION = SWEEP_ANIMATOR_DURATION * 2;
  public static final int COMPLETE_ROTATE_DURATION = COMPLETE_ANIM_DURATION * 6;

  public static final int MIN_SWEEP_ANGLE = 20;
  public static final int MAX_SWEEP_ANGLE = 300;


  public ValueAnimator build(Type type,
                             ValueAnimator.AnimatorUpdateListener updateListener,
                             Animator.AnimatorListener animatorListener) {
    ArcAnimation arcAnimation;

    switch (type) {
      case ROTATE:
        arcAnimation = new RotateArcAnimation(updateListener);
        break;
      case GROW:
        arcAnimation = new GrowArcAnimation(updateListener, animatorListener);
        break;
      case SHRINK:
        arcAnimation = new ShrinkArcAnimation(updateListener, animatorListener);
        break;
      default:
        arcAnimation = new CompleteArcAnimation(updateListener, animatorListener);

    }

    return arcAnimation.getAnimator();
  }
}
