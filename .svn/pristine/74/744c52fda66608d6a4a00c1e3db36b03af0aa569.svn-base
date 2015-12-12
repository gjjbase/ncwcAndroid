package com.ncwcandroid.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.ncwcandroid.ui.interfac.OnBorderListener;

/**
 * 
 */
public class ScrollViewExtend extends ScrollView {
	private float xDistance, yDistance, xLast, yLast;
	private OnBorderListener onBorderListener;
	private View contentView;

	public ScrollViewExtend(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewExtend(Context context) {
		super(context);
	}

	public void SetBorderListener(OnBorderListener onBorderListener) {
		this.onBorderListener = onBorderListener;
		if (onBorderListener == null) {
			return;
		}

		if (contentView == null) {
			contentView = getChildAt(0);
		}
	}

	private void doOnBorderListener() {
		if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {// 滚动到底部判断
			if (onBorderListener != null) {
				onBorderListener.onBottom();
			}
		} else if (getScrollY() == 0) {// 滚动到顶部判断
			if (onBorderListener != null) {
				onBorderListener.onTop();
			}
		}
	}

	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		doOnBorderListener();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			if (xDistance > yDistance) {
				return false;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}
}