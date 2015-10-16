package com.xfdingustc.materialprogressbar.progressarc.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Xiaofei on 2015/10/16.
 */
public class CompleteArcAnimation implements ArcAnimation {

    private ValueAnimator completeAnim;

    CompleteArcAnimation(ValueAnimator.AnimatorUpdateListener updateListener, Animator.AnimatorListener listener) {

        completeAnim = ValueAnimator.ofFloat(ArcAnimationFactory.MAX_SWEEP_ANGLE,
            ArcAnimationFactory.MIN_SWEEP_ANGLE);
        completeAnim.setInterpolator(new DecelerateInterpolator());
        completeAnim.setDuration(ArcAnimationFactory.COMPLETE_ANIM_DURATION);
        completeAnim.addUpdateListener(updateListener);
        completeAnim.addListener(listener);
    }

    @Override
    public ValueAnimator getAnimator() {
        return completeAnim;
    }
}
