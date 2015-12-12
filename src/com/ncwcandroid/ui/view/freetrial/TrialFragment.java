package com.ncwcandroid.ui.view.freetrial;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.adapter.MyFragmentPageAdapter;
import com.ncwcandroid.ui.base.BaseFragment;

/**
 * 最新试用首页
 * 
 * @author Administrator
 * 
 */
public class TrialFragment extends BaseFragment {

	private TextView btn_free_running, btn_free_willdo;
	private Drawable select, unselect;

	private ViewPager vp_product_list;
	private ArrayList<Fragment> fragments;
	private ShouYeMainRunFragment fragment_run;
	private ShouYeMainWillFragment fragment_will;
	private MyFragmentPageAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	protected View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.activity_shouye_main, null);

		init(view);

		setadapter();

		return view;
	}

	@Override
	public void initData() {
		// 点击事件
		btn_free_running.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearyoubiao();
				btn_free_running.setCompoundDrawables(null, null, null, select);
				adapter.notifyDataSetChanged();
				vp_product_list.setCurrentItem(0);
				btn_free_running.setTextColor(getResources().getColor(R.color.bgtitlecolor));
				btn_free_willdo.setTextColor(getResources().getColor(R.color.black));
			}
		});
		btn_free_willdo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearyoubiao();
				btn_free_willdo.setCompoundDrawables(null, null, null, select);
				adapter.notifyDataSetChanged();
				vp_product_list.setCurrentItem(1);
				btn_free_running.setTextColor(getResources().getColor(R.color.black));
				btn_free_willdo.setTextColor(getResources().getColor(R.color.bgtitlecolor));
			}
		});

		vp_product_list.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				clearyoubiao();
				switch (position) {
				case 0:
					btn_free_running.setCompoundDrawables(null, null, null, select);
					btn_free_running.setTextColor(getResources().getColor(R.color.bgtitlecolor));
					btn_free_willdo.setTextColor(getResources().getColor(R.color.black));
					break;
				case 1:
					btn_free_willdo.setCompoundDrawables(null, null, null, select);
					btn_free_running.setTextColor(getResources().getColor(R.color.black));
					btn_free_willdo.setTextColor(getResources().getColor(R.color.bgtitlecolor));
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	private void init(View view) {
		btn_free_running = (TextView) view.findViewById(R.id.btn_free_running);
		btn_free_willdo = (TextView) view.findViewById(R.id.btn_free_willdo);
		// 没有选中的情况
		unselect = this.getResources().getDrawable(R.drawable.youbiao_sanjiao_un);
		unselect.setAlpha(0);
		unselect.setBounds(0, 0, 4, 4);
		// 选中的情况
		select = this.getResources().getDrawable(R.drawable.youbiao_sanjiao);
		select.setBounds(0, 0, 30, 30);
		// 默认让第一个按钮选中
		btn_free_running.setCompoundDrawables(null, null, null, select);
		btn_free_running.setTextColor(getResources().getColor(R.color.bgtitlecolor));
		btn_free_willdo.setTextColor(getResources().getColor(R.color.black));
		// viewpager初始化
		vp_product_list = (ViewPager) view.findViewById(R.id.vp_product_list);
		fragments = new ArrayList<Fragment>();
		fragment_run = new ShouYeMainRunFragment();
		fragment_will = new ShouYeMainWillFragment();
		fragments.add(fragment_run);
		fragments.add(fragment_will);

		// 记录判断是否一直提醒（是否开启ShouYeInfoProductDialogActivity）
		SharedPreferences sp = getActivity().getSharedPreferences("isClose", Activity.MODE_PRIVATE);
		if (sp.getBoolean("isClose", false) == false) {
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean("isClose", false);
			editor.commit();
		}

	}

	private void setadapter() {
		adapter = new MyFragmentPageAdapter(this.getChildFragmentManager());
		adapter.setList(fragments);
		vp_product_list.setAdapter(adapter);
		vp_product_list.setCurrentItem(0);
	}

	private void clearyoubiao() {
		btn_free_running.setCompoundDrawables(null, null, null, unselect);
		btn_free_willdo.setCompoundDrawables(null, null, null, unselect);
	}

}
