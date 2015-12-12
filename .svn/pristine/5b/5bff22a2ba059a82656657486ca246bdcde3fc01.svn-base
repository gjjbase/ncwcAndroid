package com.ncwcandroid.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.util.RemSharepreUtil;
import com.ncwcandroid.ui.view.MainActivity;
import com.ncwcandroid.ui.widget.RotateDownPageTransformer;
import com.ncwcandroid.ui.widget.ViewPagerCompat;
import com.ncwcandroid.ui.widget.ViewPagerCompat.OnPageChangeListener;

/**
 * 引导页面
 * 
 * @author Administrator
 * 
 */
public class SplashActivity extends BaseActivity {
	private ViewPagerCompat viewpager;

	private int[] pics = { R.drawable.splark1, R.drawable.splark2, R.drawable.splark3 };

	private Runnable viewpagerRunnable;
	private static Handler handler;
	private static final int TIME = 12000;
	private List<ImageView> mImageViews = new ArrayList<ImageView>();
	private ImageView[] dots;
	// 记录当前选中位置
	private int currentIndex;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/** 添加activity放入栈中，后期一键退出结束所有的activity */
		AppManager.getInstance().addActivity(SplashActivity.this);
		if (RemSharepreUtil.getStringValue(getApplicationContext(), Globals.LOGINSUC, Globals.LOGINFAI).equals(Globals.LOGINSUC)) {
			setContentView(R.layout.imageview_layout);
			ImageView image = (ImageView) findViewById(R.id.imageview);
			image.setScaleType(ImageView.ScaleType.FIT_CENTER);
			/** 通过判断服务端的数据来判断是直接进入mainactivity还是停留在此界面 */
			// image.setImageResource(getResources().getDrawable(R.id.img_loading));
			// AsyncLoadingPicture.getInstance(getApplicationContext()).LoadPicture("",
			// image);
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
					finish();
				}
			});
			thread.start();
			return;

		} else {
			setContentView(R.layout.activity_splash);
			viewpager = (ViewPagerCompat) findViewById(R.id.Splash_viewpager);
			viewpager.setPageTransformer(true, new RotateDownPageTransformer());
			for (int imgId : pics) {
				ImageView imageView = new ImageView(getApplicationContext());
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageView.setImageResource(imgId);
				mImageViews.add(imageView);
			}
			mImageViews.get(2).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					RemSharepreUtil.putStringValue(getApplicationContext(), Globals.LOGINSUC, Globals.LOGINSUC);
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
					finish();
				}
			});
			viewpager.setAdapter(new PagerAdapter() {
				@Override
				public Object instantiateItem(ViewGroup container, int position) {

					container.addView(mImageViews.get(position));
					return mImageViews.get(position);
				}

				@Override
				public void destroyItem(ViewGroup container, int position, Object object) {

					container.removeView(mImageViews.get(position));
				}

				@Override
				public boolean isViewFromObject(View view, Object object) {
					return view == object;
				}

				@Override
				public int getCount() {
					return pics.length;
				}
			});

			initListener();
			// 开启自动切换图片
			// initRunnable();
		}
	}

	@Override
	public void initData() {

	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
			dots[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = (Integer) v.getTag();
					setCurView(position);
					setCurDot(position);
				}
			});
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		viewpager.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	/**
	 * 添加事件监听
	 */

	private void initListener() {
		initDots();
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			boolean isScrolled = false;

			@Override
			public void onPageScrollStateChanged(int status) {
				switch (status) {
				case 1:// 手势滑动
					isScrolled = false;
					break;
				case 2:// 界面切换
					isScrolled = true;
					break;
				case 0:// 滑动结束

					// 当前为最后一张，此时从右向左滑，则切换到第一张
					if (viewpager.getCurrentItem() == viewpager.getAdapter().getCount() - 1 && !isScrolled) {
						RemSharepreUtil.putStringValue(getApplicationContext(), Globals.LOGINSUC, Globals.LOGINSUC);
						startActivity(new Intent(getApplicationContext(), MainActivity.class));
						finish();
					}
					// 当前为第一张，此时从左向右滑，则切换到最后一张
					else if (viewpager.getCurrentItem() == 0 && !isScrolled) {
						viewpager.setCurrentItem(viewpager.getAdapter().getCount() - 1);
					}
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageSelected(int index) {
				setCurDot(index);
			}
		});
	}

	protected void initRunnable() {
		handler = new Handler();
		viewpagerRunnable = new Runnable() {

			@Override
			public void run() {

				int nowIndex = viewpager.getCurrentItem();
				int count = viewpager.getAdapter().getCount();
				// 如果下一张的索引大于最后一张，则切换到第一张
				if (nowIndex + 1 >= count) {
					startActivity(new Intent(getApplicationContext(), MainActivity.class));
					handler.removeCallbacks(viewpagerRunnable);
				} else {
					handler.postDelayed(viewpagerRunnable, TIME);
					viewpager.setCurrentItem(nowIndex + 1);
				}
			}
		};
		Thread mythread = new Thread(viewpagerRunnable);
		mythread.start();
	}
}
