package com.xfdingustc.materialprogressbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.xfdingustc.materialprogressbar.progressarc.ProgressArcView;

/**
 * Created by Xiaofei on 2015/7/23.
 */
@DefaultBehavior(FABProgressBar.Behavior.class)
public class FABProgressBar extends FrameLayout {
    private final static String TAG = FABProgressBar.class.getSimpleName();
    private boolean mViewsAdded = false;
    private ProgressArcView mProgressArc;

    private FABProgressBarListener mListener;

    private int mArcColor;
    private int mArcWidth;
    private boolean mRoundedStroke;
    private boolean mReusable;

    public FABProgressBar(Context context) {
        super(context);
    }

    public FABProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FABProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FABProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public interface FABProgressBarListener {
        void onFABProgressAnimationEnd();
    }


    private void init(AttributeSet attrs) {
        setupInitialAttributes(attrs);
    }

    private void setupInitialAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable
                .FABProgressBar, 0, 0);

            mArcColor = attrArray.getColor(R.styleable.FABProgressBar_mpb_arc_color,
                getResources().getColor(R.color.fab_orange_dark));
            mArcWidth = attrArray.getDimensionPixelSize(R.styleable.FABProgressBar_mpb_arc_width,
                getResources().getDimensionPixelSize(R.dimen.progress_arc_stroke_width));
            mRoundedStroke = attrArray.getBoolean(R.styleable.FABProgressBar_mpb_rounded_stroke, false);

            mReusable = attrArray.getBoolean(R.styleable.FABProgressBar_mpb_reusable, false);
            attrArray.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO: we need user fab's size to calculate self size
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mViewsAdded) {
            addArcView();
            mViewsAdded = true;
        }
    }

    private void addArcView() {
        setClipChildren(false);
        mProgressArc = new ProgressArcView(getContext(), mArcColor, mArcWidth, mRoundedStroke);

        LayoutParams params = new FrameLayout.LayoutParams(getFabDimension() + mArcWidth,
            getFabDimension() + mArcWidth,
            Gravity.CENTER);

        addView(mProgressArc, params);
    }


    // TODO: we need refine this logic;
    private int getFabDimension() {
        return getResources().getDimensionPixelSize(R.dimen.fab_size_normal);

    }

    public static class Behavior extends CoordinatorLayout.Behavior<FABProgressBar> {

        public Behavior() {

        }

        public boolean layoutDependsOn(CoordinatorLayout parent, FABProgressBar child, View dependency) {
            return dependency instanceof Snackbar.SnackbarLayout;
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, FABProgressBar child, View
            dependency) {
            if (dependency instanceof FloatingActionButton) {
                FloatingActionButton fab = (FloatingActionButton) dependency;
                child.setScaleX(fab.getScaleX());
                child.setScaleY(fab.getScaleY());
            }

            return false;
        }

    }

    public void show() {
        mProgressArc.show();
    }

    public void hide() {
        mProgressArc.stop();
    }

    public void addListener(FABProgressBarListener listener) {
        mListener = listener;
    }
}
