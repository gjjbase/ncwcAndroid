package com.ncwcandroid.ui.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseFragmentActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.view.adreview.OldFragment;
import com.ncwcandroid.ui.view.freetrial.TrialFragment;
import com.ncwcandroid.ui.view.perscenter.MyFragment;

public class MainActivity extends BaseFragmentActivity {

	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	// 定义一个布局;
	private LayoutInflater layoutInflater;

	// 定义数组来存放Fragment界面
	@SuppressWarnings("rawtypes")
	private Class fragmentArray[] = { TrialFragment.class, OldFragment.class, MyFragment.class };

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_home_btn, R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn };

	// Tab选项卡的文字
	private String mTextviewArray[] = { Globals.FREETRIAL, Globals.PROPROID, Globals.PERCENTER };
	private long exitTime = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout);
		AppManager.getInstance().addActivity(MainActivity.this);
		initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_maintab_background);
		}

	}

	/** 捕捉返回键，去掉loginactivity中回滚监听的操作 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				GlobalUtil.showToast(getApplicationContext(), Globals.FINISH);
				exitTime = System.currentTimeMillis();
			} else {
				/** 结束之前activity栈中所有的activity */
				AppManager.getInstance().killAllActivity();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	@SuppressLint("InflateParams")
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);

		return view;
	}

}
