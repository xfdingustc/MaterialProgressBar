package com.xfdingustc.materialprogressbar.progressarc;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.xfdingustc.materialprogressbar.utils.AnimationUtils;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class ProgressArcView extends ProgressBar {
    private ArcListener mInternalListener;
    private boolean mRoundedStroke;
    private int mArcWidth;
    private int mArcColor;

    public ProgressArcView(Context context, int arcColor, int arcWidth, boolean roundedStroke) {
        super(context);
        this.mArcColor = arcColor;
        this.mArcWidth = arcWidth;
        this.mRoundedStroke = roundedStroke;

        init();
    }


    private void init() {
        setAlpha(0);
        ProgressArcDrawable arcDrawable = new ProgressArcDrawable(mArcWidth, mArcColor, mRoundedStroke);
        setIndeterminateDrawable(arcDrawable);
    }

    public void setInternalListener(ArcListener internalListener) {
        this.mInternalListener = internalListener;
    }

    public void show() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setAlpha(1);
                getDrawable().reset();
            }
        }, AnimationUtils.SHOW_SCALE_ANIM_DELAY);

    }


    public void stop() {
        getDrawable().stop();
        ValueAnimator fadeOutAnim = ObjectAnimator.ofFloat(this, "alpha", 1, 0);
        fadeOutAnim.setDuration(100).start();
    }

    public void reset() {
        getDrawable().reset();

        ValueAnimator arcScaleX = ObjectAnimator.ofFloat(this, "scaleX", 1);
        ValueAnimator arcScaleY = ObjectAnimator.ofFloat(this, "scaleY", 1);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(0).setInterpolator(new DecelerateInterpolator());
        set.playTogether(arcScaleX, arcScaleY);
        set.start();
    }

    public void requestCompleteAnimation() {
        getDrawable().requestCompleteAnimation(mInternalListener);
    }

    private ProgressArcDrawable getDrawable() {
        Drawable ret = getIndeterminateDrawable();
        return (ProgressArcDrawable) ret;
    }


}
