package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by luo.xiao on 2016/3/1.
 */
public class PullToRefreshView extends PullToRefreshBase<ScrollLayout> {

    public PullToRefreshView(Context context) {
        super(context);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected ScrollLayout createRefreshableView(Context context, AttributeSet attrs) {
        ScrollLayout scrollView;
        scrollView = new ScrollLayout(context, attrs);

        scrollView.setId(R.id.scrollview);
        return scrollView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }


        /**
         * Taken from the AOSP ScrollView source
         */
        private int getScrollRange() {
            int scrollRange = 0;
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
            }
            return scrollRange;
        }
}