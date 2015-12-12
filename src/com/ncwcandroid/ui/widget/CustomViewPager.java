package com.ncwcandroid.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
	private boolean isCanScroll = true;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		if (isCanScroll) {
			super.scrollTo(x, y);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (arg0.getAction() == arg0.ACTION_UP) {
			if (getCurrentItem() == 1) {
				this.setCurrentItem(0);
			} else {
				this.setCurrentItem(1);
			}
		}
		return true;
	}

}
