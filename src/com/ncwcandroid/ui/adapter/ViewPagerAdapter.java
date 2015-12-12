package com.ncwcandroid.ui.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 填充ViewPager页面的配器
 */
public class ViewPagerAdapter extends PagerAdapter {
	private List<View> views;
	private int mChildCount = 0;
	public ViewPagerAdapter(List<View> views) {
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1));
		return views.get(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	@Override
	public void finishUpdate(View arg0) {
	}
    public void notifyDataSetChanged() {         
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

  @Override
  public int getItemPosition(Object object)   {          
        if ( mChildCount > 0) {
        mChildCount --;
        return POSITION_NONE;
        }
        return super.getItemPosition(object);
  }

}
