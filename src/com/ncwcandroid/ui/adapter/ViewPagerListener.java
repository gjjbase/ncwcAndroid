package com.ncwcandroid.ui.adapter;

import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ViewPagerListener implements OnPageChangeListener {
	FragmentTabHost mTabHost;

	public ViewPagerListener(FragmentTabHost mTabHost) {
		this.mTabHost = mTabHost;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		mTabHost.setCurrentTab(arg0);
	}

}
