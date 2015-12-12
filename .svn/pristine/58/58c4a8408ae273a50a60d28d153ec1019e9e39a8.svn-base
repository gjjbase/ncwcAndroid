package com.ncwcandroid.ui.view.freetrial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.adapter.ShouYeProductAdapter;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.widget.AllListView;

/***
 * 
 * 首页详情评论页面
 * 
 * @author Administrator
 * 
 */
public class ShouYeJoinPinglunFragment extends Fragment implements VolleyStringListener {

	private String id;
	private int num_s = 20;

	private EditText et_write_pinglun;
	private Button btn_send_pinglun;
	private TextView refrush_listview;
	private String content;

	private AllListView lv_show_pinglun;
	private ShouYeProductAdapter adapter;
	// 数据
	private VolleyHttpUtil vhu;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_shouye_product_pinglun, null, false);

		et_write_pinglun = (EditText) view.findViewById(R.id.et_write_pinglun);
		btn_send_pinglun = (Button) view.findViewById(R.id.btn_send_pinglun);
		refrush_listview = (TextView) view.findViewById(R.id.refrush_listview);
		lv_show_pinglun = (AllListView) view.findViewById(R.id.lv_show_pinglun);
		lv_show_pinglun.setEnabled(false);

		// Log.v("teyyyyyyyyyyyyy", id);
		id = ((ShouYeProductActivity) getActivity()).getId();

		getHttp();

		init();

		return view;
	}

	private void getHttp() {
		vhu = VolleyHttpUtil.getInstance(getActivity());
		vhu.GetPingLun(getActivity(), id, "10");
		vhu.setObjectList(this);
	}

	private void getHttp(String nums) {
		vhu = VolleyHttpUtil.getInstance(getActivity());
		vhu.GetPingLun_s(getActivity(), id, nums, handler);
		et_write_pinglun.clearFocus();
		et_write_pinglun.setFocusable(false);
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				String returnJson = msg.getData().getString("returnJson");
				// Log.e("@@@@====@@@@", returnJson);
				try {
					JSONObject all = new JSONObject(returnJson);
					int code = all.getInt("code");
					if (code == 200) {
						JSONObject datass = all.getJSONObject("datas");
						int stauts = datass.getInt("status");
						if (stauts == 1) {
							JSONArray arrayss = datass.getJSONArray("list");

							ShouYeProductAdapter adapters = new ShouYeProductAdapter(getActivity(), arrayss);
							lv_show_pinglun.setAdapter(adapters);
						} else {

						}
					} else {
						Toast.makeText(getActivity(), Globals.MSG_WHAT_2, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	};

	private void init() {
		btn_send_pinglun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SharepreUtil.getStringValue(getActivity(), Globals.LOGINKEY, Globals.LOGINKEY).equals(Globals.LOGINKEY)) {
					GlobalUtil.showToast(getActivity(), Globals.NEEDLOGIN);
					Intent intnet = new Intent("com.ncwcandroid.ui.interfac.BootBroadcastReceiver");
					getActivity().sendBroadcast(intnet);
					return;
				}
				content = et_write_pinglun.getText().toString();
				if ("".equals(content)) {
					GlobalUtil.showToast(getActivity(), Globals.PINGLUNNULL);
				} else {
					getHttp_t();
					et_write_pinglun.setText("");
				}
			}
		});
		refrush_listview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getHttp(num_s + "");
				num_s += 10;
			}
		});
		et_write_pinglun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getFoucs();
			}
		});
	}

	private void getFoucs() {
		et_write_pinglun.setFocusable(true);
		et_write_pinglun.setFocusableInTouchMode(true);
		et_write_pinglun.requestFocus();
		et_write_pinglun.findFocus();
	}

	private void getHttp_t() {
		vhu.GiveComment(getActivity(), content, SharepreUtil.getStringValue(getActivity(), "id", ""));
		vhu.setObjectList(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		if (type == PostService.TYPE_ADDCOMMENT) {
			// Log.v("=============", response);
			try {
				JSONObject obj = new JSONObject(response);
				int code = obj.getInt("code");
				if (code == 200) {
					JSONObject datas = obj.getJSONObject("datas");
					int result = datas.getInt("status");
					String msg = datas.getString("msg");
					if (result == 1) {
						getHttp();
						Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(), Globals.GIVEFALSE, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (type == PostService.TYPE_GETPINGLUN) {
			// Log.v("=============", response);
			try {
				JSONObject all = new JSONObject(response);
				int code = all.getInt("code");
				if (code == 200) {
					JSONObject datas = all.getJSONObject("datas");
					int stauts = datas.getInt("status");
					if (stauts == 1) {
						JSONArray arrays = datas.getJSONArray("list");
						// Log.v("999999999999999999999999999", array.toString()
						// + "9999");
						adapter = new ShouYeProductAdapter(getActivity(), arrays);

						lv_show_pinglun.setAdapter(adapter);

					} else {
						String mgs = datas.getString("mgs");
						lv_show_pinglun.setVisibility(View.GONE);
						Toast.makeText(getActivity(), "评论" + mgs, Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(), Globals.MSG_WHAT_2, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		num_s = 20;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		num_s = 20;
	}

}
