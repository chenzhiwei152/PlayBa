package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.graphics.Rect;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
/**
 * Created by luo.xiao on 2016/3/1.
 */
public class ScrollLayout extends ViewGroup{
    private Scroller mScroller;
    public ScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int totalHeight=0;
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View childView=getChildAt(i);
            childView.layout(l, totalHeight, r, totalHeight+childView.getMeasuredHeight());
            totalHeight+=childView.getMeasuredHeight();
        }
    }

//    private VelocityTracker mVelocityTracker;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        int count=getChildCount();
        for(int i=0;i<count;i++){
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            }
        setMeasuredDimension(width, height);
    }

    private float mLastMotionY;
    int movey;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if(mVelocityTracker==null){
//            mVelocityTracker=VelocityTracker.obtain();
//        }
//        mVelocityTracker.addMovement(event);

        int action=event.getAction();

//        float y;
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
//                y =event.getY();
                mLastMotionY= (int)event.getY();
                movey = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int deltaY=(int) (mLastMotionY-y);
                movey += deltaY;
                mLastMotionY = y;
                if ((getScrollY() <= 0 && deltaY < 0)){
                    return true;
                } else{
                    if (getScrollY() + deltaY < 0){
                        deltaY = 0-getScrollY();
                    }
                }
                if(getScrollY() > getHeight()*(getChildCount()-1)&&deltaY>0){
                    return true;
                }else{
                    if(getScrollY() + deltaY > getHeight()*(getChildCount()-1)){
                        deltaY = getHeight()*(getChildCount()-1) - getScrollY();
                    }
                }Log.i("aaa", "de: "+deltaY);
                scrollBy(0, deltaY);
                //mScroller.startScroll(0, getScrollY(), 0, deltaY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                float yy = event.getY();
                int deltaYup=(int) (mLastMotionY-yy);
                if ((getScrollY() <= 0 && deltaYup < 0)|| (getScrollY() > getHeight()*(getChildCount()-1)&&deltaYup>0)){
                    return true;
                }
//                if(mVelocityTracker!=null){
//                    mVelocityTracker.recycle();
//                    mVelocityTracker=null;
//                }

                if(getScrollY()<0){
                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                }else if(getScrollY()>(getHeight()*(getChildCount()-1))){
                    mScroller.startScroll(0,getScrollY(),0,getScrollY()-(getChildCount()-1)*getHeight());
                }else{
                    int position=getScrollY()/getHeight();
                    int mod=getScrollY()%getHeight();
                    if(mod>getHeight()/4 && movey > 0) {
                        mScroller.startScroll(0, getScrollY(), 0, getHeight()*(position+1)-getScrollY(),800);
                    }else if (getHeight()*(position+1) - getScrollY() > getHeight()/4){
                        mScroller.startScroll(0, getScrollY(), 0, getHeight()*(position)-getScrollY(),800);
                    }else{
                        if (movey > 0){
                            mod=getScrollY()%getHeight();
                            mScroller.startScroll(0, getScrollY(), 0, -mod);
                        }else{
                            mScroller.startScroll(0, getScrollY(), 0, (getHeight()*(position+1) - getScrollY()));
                        }

                    }
                }
                movey = 0;
                invalidate();
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        boolean b = mScroller.computeScrollOffset();
        if(b){
            int y = mScroller.getCurrY();
            if (getScrollY() == y){
                y = y+1;
            }
            scrollTo(0, y);
        }
    }


    float x;
    float y;
    float dsy;
    float dsx;
    Rect rect;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                dsx = 0;
                dsy = 0;
                x = ev.getX();
                y = ev.getY();
                mLastMotionY = (int)y;
            case MotionEvent.ACTION_MOVE:
                float movex = ev.getX();
                float movey = ev.getY();
                dsx += (movex - x);
                dsy += (movey - y);
                x = movex;
                y = movey;
                if (Math.abs(dsx)*0.5f < Math.abs(dsy) ){
                    return true;
                }
            case MotionEvent.ACTION_UP:
                break;
        }

        return result;
    }
    boolean interrupt;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (interrupt){
            ev.setAction(MotionEvent.ACTION_DOWN);
            interrupt = false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept == false){
            interrupt = true;
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

}
