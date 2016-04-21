package marc.customviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gilbertm on 04/03/2016.
 */
public class OpenRightRecyclerView extends RecyclerView{

    OnInterceptTouchListener onInterceptTouchListener;

    public OpenRightRecyclerView(Context context) {
        super(context);
    }

    public OpenRightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OpenRightRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        Boolean intercept = false;
        if( onInterceptTouchListener!=null ){
            intercept = onInterceptTouchListener.onInterceptTouchEvent(e);
        }
        else{
            intercept = super.onInterceptTouchEvent(e);
        }

        return  intercept;
    }

    interface OnInterceptTouchListener {
        public boolean onInterceptTouchEvent(MotionEvent e);
    }

    public void setOnInterceptTouchListener(OnInterceptTouchListener onInterceptTouchListener) {
        this.onInterceptTouchListener = onInterceptTouchListener;
    }

}
