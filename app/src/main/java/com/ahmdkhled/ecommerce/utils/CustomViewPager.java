package com.ahmdkhled.ecommerce.utils;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;



    public class CustomViewPager extends ViewPager {

        private float initialXValue;
        private String direction;

        public CustomViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.direction = "all";
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return this.IsSwipeAllowed(event) && super.onTouchEvent(event);

        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            return this.IsSwipeAllowed(event) && super.onInterceptTouchEvent(event);

        }

        private boolean IsSwipeAllowed(MotionEvent event) {
            if(this.direction.equals("all")) return true;

            if(direction.equals("none") )//disable any swipe
                return false;

            if(event.getAction()==MotionEvent.ACTION_DOWN) {
                initialXValue = event.getX();
                return true;
            }

            if(event.getAction()==MotionEvent.ACTION_MOVE) {
                try {
                    float diffX = event.getX() - initialXValue;
                    if (diffX > 0 && direction.equals("right") ) {
                        // swipe from left to right detected
                        return false;
                    }else if (diffX < 0 && direction.equals("left")) {
                        // swipe from right to left detected
                        return false;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            return true;
        }

        public void setAllowedDirecrion(String direction) {
            this.direction = direction;
        }



}
