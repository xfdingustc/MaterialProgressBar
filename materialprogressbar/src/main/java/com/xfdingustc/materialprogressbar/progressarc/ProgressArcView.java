package com.xfdingustc.materialprogressbar.progressarc;

import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by Xiaofei on 2015/7/23.
 */
public class ProgressArcView extends ProgressBar {
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

  public void show() {
    setAlpha(1);
  }

  private void init() {
    setAlpha(0);
    ProgressArcDrawable arcDrawable = new ProgressArcDrawable(mArcWidth, mArcColor, mRoundedStroke);
    setIndeterminateDrawable(arcDrawable);
  }


}
