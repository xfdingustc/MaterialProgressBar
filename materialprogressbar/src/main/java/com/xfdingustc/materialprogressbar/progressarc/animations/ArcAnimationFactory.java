package com.xfdingustc.materialprogressbar.progressarc.animations;

import android.animation.ValueAnimator;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class ArcAnimationFactory {
  public enum Type {
    ROTATE,
  }

  public static final int ROTATE_ANIMATOR_DURATION = 2000;


  public ValueAnimator build(Type type, ValueAnimator.AnimatorUpdateListener listener) {
    ArcAnimation arcAnimation;

    switch (type) {
      case ROTATE:
        arcAnimation = new RotateArcAnimation(listener);
        break;
      default:
        arcAnimation = new RotateArcAnimation(listener);

    }

    return arcAnimation.getAniamator();
  }
}
