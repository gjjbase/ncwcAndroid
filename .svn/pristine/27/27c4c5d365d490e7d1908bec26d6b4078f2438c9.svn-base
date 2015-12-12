package com.ncwcandroid.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import cn.sharesdk.framework.ShareSDK;

public abstract class BaseActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	/**
	 * 子类实现初始化数据操作(子类自己调用)
	 */
	public abstract void initData();

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ShareSDK.stopSDK();
	}

	/**
	 * 返回操作
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();
	}
}
