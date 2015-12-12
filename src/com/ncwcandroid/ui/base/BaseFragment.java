package com.ncwcandroid.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.perscenter.RegisterActivity;

public abstract class BaseFragment extends Fragment {

	private View rootView;
	protected Context context;
	private Boolean hasInitData = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = initView(inflater);
		}
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (!hasInitData) {
			initData();
			hasInitData = true;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		((ViewGroup) rootView.getParent()).removeView(rootView);
	}

	/**
	 * 子类实现初始化View操作
	 */
	protected abstract View initView(LayoutInflater inflater);

	/**
	 * 子类实现初始化数据操作(子类自己调用)
	 */
	public abstract void initData();

	public void onRegiste() {
		if (SharepreUtil.getStringValue(getActivity(), Globals.LOGINKEY, "").equals("")){
			startActivity(new Intent(getActivity(), RegisterActivity.class));
			return;
		}
			
	}

	public void back(View v) {
		getActivity().finish();
	}

}
