package chat21.android.demo.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Custom viewpager with the swipe disabled
 */
public class UnSwipeableViewPager extends ViewPager {

    public UnSwipeableViewPager(Context context) {
        super(context);
    }

    public UnSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}