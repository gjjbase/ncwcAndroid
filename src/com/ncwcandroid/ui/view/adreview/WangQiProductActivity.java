package com.ncwcandroid.ui.view.adreview;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import com.mob.tools.utils.UIHandler;
import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.adapter.WangQi_ListViewAdapter;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.bean.ShareModel;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.IXListViewListener;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.widget.FlippingLoadingDialog;
import com.ncwcandroid.ui.widget.SharePopupWindow;
import com.ncwcandroid.ui.xlistview.XListView;

/**
 * 往期详情页面
 * 
 * @author Administrator
 * 
 */
public class WangQiProductActivity extends Activity implements
		PlatformActionListener, Callback, VolleyStringListener,
		IXListViewListener {

	int nums = 20;
	int index;
	View v;
	int top;

	private XListView listview;
	private WangQi_ListViewAdapter adapter;
	private ImageView img_share_02;
	private TextView img_back_01;
	private String id;
	private String img;
	private String big_img;
	private String qi_num;
	private String title;
	private String num;
	private String persons;
	private String price;
	/** 判断是否中奖 */
	private String prize = "0";
	/** 判断是否申请过 */
	private String presence = "0";
	// 分享部分
	private SharePopupWindow share;
	private String text;// 产品的small_info
	private String imageurl = "http://img.nichewoche.com/logo.jpg";
	private String url = "http://www.nichewoche.com/downapk/";
	private FlippingLoadingDialog flippingload;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wangqi_product);
		AppManager.getInstance().addActivity(WangQiProductActivity.this);
		Intent in = getIntent();
		id = in.getStringExtra("id");// 33
		img = in.getStringExtra("img");
		big_img = in.getStringExtra("big_img");
		qi_num = in.getStringExtra("qi_num");// 1
		title = in.getStringExtra("name");// D
		text = in.getStringExtra("small_info");
		num = in.getStringExtra("num");// 100
		persons = in.getStringExtra("persons");// 20
		price = in.getStringExtra("price");
		prize = in.getStringExtra("prize");
		try {
			presence = in.getStringExtra("presence");
		} catch (Exception e) {
			// TODO: handle exception
		}
		init();

		setdata();

	}

	private void init() {
		flippingload = new FlippingLoadingDialog(WangQiProductActivity.this);
		listview = (XListView) findViewById(R.id.lv_wangqi_product);
		img_back_01 = (TextView) findViewById(R.id.img_back_01);
		img_share_02 = (ImageView) findViewById(R.id.img_share_02);

		img_back_01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivity(new
				// Intent(WangQiProductActivity.this,WangQiMainActivity.class));
				finish();
			}
		});

		img_share_02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				share = new SharePopupWindow(WangQiProductActivity.this,
						flippingload);
				share.setPlatformActionListener(WangQiProductActivity.this);
				ShareModel model = new ShareModel();
				model.setImageUrl(imageurl);
				model.setUrl(url);
				share.initShareParams(model);
				share.showShareWindow();
				// 显示位置
				share.showAtLocation(WangQiProductActivity.this
						.findViewById(R.id.share_wangqi), Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0, 0);
			}
		});

	}

	private void setdata() {
		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(this);
		// 请求网络数据_试用报告
		vhu.GetShiYongBaoGao(this, id, "10");
		vhu.setObjectList(this);
	}

	private void getshiyongBG_loadmore(String num) {
		index = listview.getFirstVisiblePosition();
		v = listview.getChildAt(0);
		top = (v == null) ? 0 : v.getTop();
		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(this);
		// 请求网络数据_试用报告
		vhu.GetShiYongBaoGao(this, id, num);
		vhu.setObjectList(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (share != null) {
			share.dismiss();
		}
		if (flippingload != null) {
			flippingload.dismiss();
		}
		nums = 20;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
		nums = 20;
		this.finish();
	}

	@Override
	public boolean handleMessage(Message msg) {
		int what = msg.what;
		if (what == 1) {
			Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
		}
		if (share != null) {
			share.dismiss();
		}
		return false;
	}

	@Override
	public void onCancel(Platform arg0, int arg1) {
		Message msg = new Message();
		msg.what = 0;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		Message msg = new Message();
		msg.what = 1;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		if (type == PostService.TYPE_GETSHIYONGBAOGAO) {
			Log.v("====get试用报告====", response);
			try {
				JSONObject obj = new JSONObject(response);
				int code = obj.getInt("code");
				if (code == 200) {
					JSONObject datas = obj.getJSONObject("datas");
					String allnum_ = datas.getString("allnum");
					int allnum = Integer.parseInt(allnum_);
					JSONArray array_report = datas.getJSONArray("list");

					adapter = new WangQi_ListViewAdapter(this, array_report,
							id, qi_num, title, text, num, persons, big_img,
							img, presence, price, prize, allnum);
					listview.setPullRefreshEnable(false);
					listview.setPullLoadEnable(true);
					listview.setXListViewListener(this);
					listview.setAdapter(adapter);
					listview.setSelectionFromTop(index, top);
				} else {
					GlobalUtil.showToast(this, Globals.FAULTERROE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onLoadMore() {
		getshiyongBG_loadmore(nums + "");
		nums += 10;
		Toast.makeText(this, R.string.load_ok, Toast.LENGTH_SHORT).show();
		onLoad();
	}

	@Override
	public void onLoad() {
		listview.stopLoadMore();
		// adapter.notifyDataSetChanged();
		// listview.setSelection(adapter.getCount() - 1);
	}

}
