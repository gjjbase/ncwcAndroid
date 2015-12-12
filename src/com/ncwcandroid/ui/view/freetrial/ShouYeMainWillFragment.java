package com.ncwcandroid.ui.view.freetrial;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.adapter.ShouYeMainListAdapter;
import com.ncwcandroid.ui.base.BaseFragment;
import com.ncwcandroid.ui.bean.Timer;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.IXListViewListener;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.DateTimeUtil;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.view.LoadFalse;
import com.ncwcandroid.ui.view.MainActivity;
import com.ncwcandroid.ui.xlistview.XListView;

/**
 * 即将开始页面
 * 
 * @author Administrator
 * 
 */
public class ShouYeMainWillFragment extends BaseFragment implements
		VolleyStringListener, IXListViewListener {

	int index = 0;
	int eachnum = 20;
	// 能否上拉加载
	int hasmore;
	private boolean bolg = true;
	private String date;

	private XListView lv_main_will;
	private JSONArray array;
	private ShouYeMainListAdapter adapter;
	// private String member_id, key;

	// 倒计时
	private ArrayList<Timer> times = new ArrayList<Timer>();
	private TextView txt_going;

	@SuppressLint("InflateParams")
	@Override
	protected View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_shouye_main_will, null,
				false);
		lv_main_will = (XListView) view.findViewById(R.id.lv_main_will);
		txt_going = (TextView) view.findViewById(R.id.txt_going);
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (index == 0) {
				init();
			}
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
	}

	private void init() {
		// member_id = SharepreUtil.getStringValue(getActivity(),
		// Globals.MEMBERID, "");
		// key = SharepreUtil.getStringValue(getActivity(), Globals.LOGINKEY,
		// "");
		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(getActivity());
		vhu.ListProducts(getActivity(), "3", "10", "1", false);
		vhu.setObjectList(this);
	}

	public void init(String eachnum) {
		// member_id = SharepreUtil.getStringValue(getActivity(),
		// Globals.MEMBERID, "");
		// key = SharepreUtil.getStringValue(getActivity(), Globals.LOGINKEY,
		// "");
		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(getActivity());
		vhu.ListProducts(getActivity(), "3", eachnum, "1", false);
		vhu.setObjectList(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// Log.v("+++++++++++++", response);
		try {
			txt_going.setVisibility(View.GONE);
			JSONObject obj = new JSONObject(response);
			int code = obj.getInt("code");
			if (code == 200) {
				JSONObject obj_ = obj.getJSONObject("datas");
				hasmore = obj_.getInt("hasmore");
				array = obj_.getJSONArray("list");
				if (array.length() == 0) {
					// Toast.makeText(getActivity(), "没有更多数据了",
					// Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(), LoadFalse.class);
					Bundle bund = new Bundle();
					bund.putInt("from", 0);
					intent.putExtras(bund);
					startActivity(intent);
					if (getActivity() instanceof MainActivity) {
						((MainActivity) getActivity()).finish();
					}
				} else {
					for (int i = 0; i < array.length(); i++) {
						try {
							JSONObject item = array.getJSONObject(i);
							Timer timer = new Timer((long) item.getInt("date")
									* 24 * 60 * 60 * 1000
									+ (long) item.getInt("hours") * 60 * 60
									* 1000 + (long) item.getInt("minutes") * 60
									* 1000 + (long) item.getInt("seconds")
									* 1000);
							times.add(timer);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					adapter = new ShouYeMainListAdapter(getActivity(), array,
							times, 1);
					lv_main_will.setPullRefreshEnable(true);
					lv_main_will.setPullLoadEnable(true);
					lv_main_will.setXListViewListener(this);
					lv_main_will.setAdapter(adapter);
					if (index == 0) {
						handler.sendEmptyMessage(1);
						index++;
					}
				}
			} else {
				GlobalUtil.showToast(getActivity(), Globals.NOINFO);
			}
		} catch (JSONException e) {
			GlobalUtil.showToast(getActivity(), Globals.NOINFO);
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				boolean isNeedCountTime = false;
				// 计算时间
				for (int i = 0; i < times.size(); i++) {
					Timer item_time = times.get(i);
					long time = item_time.getTime();
					// 判断是否还有条目能够倒计时，如果能够倒计时，延迟一秒，让他接着倒计时
					if (time > 1000) {
						isNeedCountTime = true;
						item_time.setTime(time - 1000);
					} else {
						item_time.setTime(0);
					}
				}
				// 循环执行
				adapter.notifyDataSetChanged();
				if (isNeedCountTime) {
					handler.sendEmptyMessageDelayed(1, 1000);
				}
				break;
			}
		}
	};

	@Override
	public void onRefresh() {
		init();
		eachnum = 20;
		Toast.makeText(getActivity(), R.string.res_new, Toast.LENGTH_SHORT).show();
		onLoad();
	}

	@Override
	public void onLoadMore() {
		if (bolg) {
			lv_main_will.setRefreshTime(":" + DateTimeUtil.getNowTime());
			bolg = false;
		} else {
			lv_main_will.setRefreshTime(":" + date);
		}
		date = DateTimeUtil.getNowTime();
		if (hasmore == 0) {
			Toast.makeText(getActivity(), R.string.load_ok, Toast.LENGTH_SHORT).show();
		} else {
			init(eachnum + "");
			eachnum += 10;
			Toast.makeText(getActivity(), R.string.load_ok, Toast.LENGTH_SHORT).show();
		}
		onLoad();
	}

	@Override
	public void onLoad() {
		lv_main_will.stopRefresh();
		lv_main_will.stopLoadMore();
		adapter.notifyDataSetChanged();
		// lv_main_will.setSelection((pullpage * 10) - 1);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		eachnum = 20;
	}
	
}
