package com.example.nhom8_udluyenthitracnghiem.Misc;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

import com.example.nhom8_udluyenthitracnghiem.Common.Common;


public class CustomViewPager extends ViewPager
{

//    private boolean isPagingEnabled = true;

    private float initialXValue;
    private Common.SwipeDirection direction;

    public CustomViewPager(Context context)
    {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.direction = Common.SwipeDirection.all;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (this.IsSwipeAllowed(event)) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if (this.IsSwipeAllowed(event)) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

//    public void setPagingEnabled(boolean b)
//    {
//        this.isPagingEnabled = b;
//    }

    private boolean IsSwipeAllowed(MotionEvent event)
    {
        if(this.direction == Common.SwipeDirection.all) return true;

        if(direction == Common.SwipeDirection.none )//disable any swipe
            return false;

        if(event.getAction()== MotionEvent.ACTION_DOWN)
        {
            initialXValue = event.getX();
            return true;
        }

        if(event.getAction()== MotionEvent.ACTION_MOVE)
        {
            try
            {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == Common.SwipeDirection.right )
                {
                    // swipe from left to right detected
                    return false;
                }else if (diffX < 0 && direction == Common.SwipeDirection.left )
                {
                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        return true;
    }

    public void setAllowedSwipeDirection(Common.SwipeDirection direction)
    {
        this.direction = direction;
    }
}