package com.ncwcandroid.ui.view.freetrial;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import com.mob.tools.utils.UIHandler;
import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.bean.ShareModel;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.widget.FlippingLoadingDialog;
import com.ncwcandroid.ui.widget.SharePopupWindow;
import com.ncwcandroid.ui.widget.TimerTextView;

public class ShouYeProductActivity extends FragmentActivity implements PlatformActionListener, Callback, VolleyStringListener, OnClickListener {

	private ImageView img_share_xiangqing;
	private SharePopupWindow share;
	private String imageurl = "http://img.nichewoche.com/logo.jpg";
	private String url = "http://www.nichewoche.com/downapk/";
	private RelativeLayout rela;
	private ImageView img_product_;
	private TextView tv_product_title_, tv_product_content_, tv_num_, tv_persons_, tv_qi_num_, tv_price;
	private TimerTextView timer_text_view_;
	private Button button1_;
	private Bundle bundle;
	private int presence;
	private int status;
	private String msg;
	private int where;
	private String id;

	public String getId() {
		return id;
	}

	private FlippingLoadingDialog flippingload;
	private FragmentTabHost mTabHost;
	@SuppressWarnings("rawtypes")
	private Class fragmentArray[] = { ShouYeProductInfoFragment.class, ShouYeJoinPinglunFragment.class };
	// Tab选项卡的文字
	private String mTextviewArray[] = { "产品详情", "产品评论" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouye_product);
		AppManager.getInstance().addActivity(ShouYeProductActivity.this);
		// 初始化分享
		ShareSDK.initSDK(this);
		init();
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		if (flippingload != null) {
			flippingload.dismiss();
		}

	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	// 定义一个布局;'
	private LayoutInflater layoutInflater;

	@SuppressLint({ "ResourceAsColor", "InflateParams" })
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.rettabdef_item_view, null);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);
		View leftview = view.findViewById(R.id.leftview);
		if (index == 1)
			leftview.setVisibility(View.GONE);
		return view;
	}

	private void init() {
		layoutInflater = LayoutInflater.from(this);
		rela = (RelativeLayout) findViewById(R.id.rela);
		img_product_ = (ImageView) findViewById(R.id.img_product_);
		tv_qi_num_ = (TextView) findViewById(R.id.tv_qi_num_);
		tv_product_title_ = (TextView) findViewById(R.id.tv_product_title_);
		tv_product_content_ = (TextView) findViewById(R.id.tv_product_content_);
		tv_num_ = (TextView) findViewById(R.id.tv_num_);
		tv_persons_ = (TextView) findViewById(R.id.tv_persons_);
		tv_price = (TextView) findViewById(R.id.tv_price);
		timer_text_view_ = (TimerTextView) findViewById(R.id.timer_text_view_);
		button1_ = (Button) findViewById(R.id.button1_);
		flippingload = new FlippingLoadingDialog(ShouYeProductActivity.this);
		img_share_xiangqing = (ImageView) findViewById(R.id.img_share_xiangqing);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// 得到fragment的个数
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			// mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selectordef_maintab_background);
		}

		img_share_xiangqing.setOnClickListener(this);

		button1_.setOnClickListener(this);

		bundle = getIntent().getExtras();
		where = bundle.getInt("where");
		id = bundle.getString("id");
		SharepreUtil.putStringValue(getApplicationContext(), "id", id);
		switch (bundle.getInt("where")) {
		case 1:
			AsyncLoadingPicture.getInstance(this).LoadPicture(bundle.getString("img"), img_product_);
			String qinum = bundle.getString("qi_num");
			if (Integer.parseInt(qinum) < 10) {
				tv_qi_num_.setText("0" + bundle.getString("qi_num"));
			} else {
				tv_qi_num_.setText(bundle.getString("qi_num"));
			}
			tv_num_.setText(bundle.getString("num"));
			tv_persons_.setText(bundle.getString("persons"));
			String ttt = bundle.getString("titletv");
			tv_price.setText(Html.fromHtml("<small>原价:</small><big><font color='#FF0000'>￥" + bundle.getString("price") + "</font></big>"));
			tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			if (ttt.length() > 10) {
				tv_product_title_.setText(ttt.substring(0, 10) + "...");
			} else {
				tv_product_title_.setText(ttt);
			}
			String ccc = bundle.getString("contenttv");
			if (ccc.length() > 40) {
				tv_product_content_.setText("\u3000\u3000" + ccc.substring(0, 40) + "...");
			} else {
				tv_product_content_.setText(ccc);
			}
			timer_text_view_.setTimes(bundle.getLong("h"), bundle.getLong("m"), bundle.getLong("s"));
			timer_text_view_.beginRun();
			button1_.setText("已申请");
			button1_.setBackgroundResource(R.drawable.corners_button_no_bg);
			button1_.setEnabled(false);
			break;
		case 2:
			img_share_xiangqing.setVisibility(View.GONE);
			rela.setVisibility(View.GONE);
			break;
		case 0:
			// where=0 默认无操作

			AsyncLoadingPicture.getInstance(this).LoadPicture(bundle.getString("img"), img_product_);
			String qinums = bundle.getString("qi_num");
			if (Integer.parseInt(qinums) < 10) {
				tv_qi_num_.setText("0" + bundle.getString("qi_num"));
			} else {
				tv_qi_num_.setText(bundle.getString("qi_num"));
			}
			tv_num_.setText(bundle.getString("num"));
			tv_persons_.setText(bundle.getString("persons"));
			String tis = bundle.getString("titletv");
			tv_price.setText(Html.fromHtml("<small>价格:</small><big><font color='#FF0000'>￥" + bundle.getString("price") + "</font></big>"));
			tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			if (tis.length() > 10) {
				tv_product_title_.setText(tis.substring(0, 10) + "...");
			} else {
				tv_product_title_.setText(tis);
			}
			String cos = bundle.getString("contenttv");
			if (cos.length() > 40) {
				tv_product_content_.setText("\u3000\u3000" + cos.substring(0, 40) + "...");
			} else {
				tv_product_content_.setText(cos);
			}
			timer_text_view_.setTimes(bundle.getLong("h"), bundle.getLong("m"), bundle.getLong("s"));
			timer_text_view_.beginRun();
			presence = bundle.getInt("presence");
			if (presence == 1) {
				button1_.setText("已申请");
				button1_.setBackgroundResource(R.drawable.corners_button_no_bg);
				button1_.setEnabled(false);
			}
			break;
		default:
			break;
		}
	}

	// 申请结果
	private void resultOfappley() {
		if (SharepreUtil.getStringValue(getApplicationContext(), Globals.LOGINKEY, Globals.LOGINKEY).equals(Globals.LOGINKEY)) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NEEDLOGIN);
			Intent intnet = new Intent("com.ncwcandroid.ui.interfac.BootBroadcastReceiver");
			sendBroadcast(intnet);
			return;
		}
		if (status == 1) {
			if (where == 0) {
				SharedPreferences sp = getSharedPreferences("isClose", Activity.MODE_PRIVATE);

				if (sp.getBoolean("isClose", false) == false) {
					Intent in = new Intent(ShouYeProductActivity.this, ShouYeInfoProductDialogActivity.class);
					bundle.putInt("where", 0);
					in.putExtras(bundle);
					startActivity(in);
				} else {
					Intent in = new Intent(ShouYeProductActivity.this, ShouYeJoinInfoActivity.class);
					bundle.putInt("where", 0);
					in.putExtras(bundle);
					startActivity(in);
				}
				finish();
			}
		}
		/*
		 * else { Intent is = new Intent(ShouYeProductActivity.this,
		 * ShouYeAppleyTryFalseInfo.class); is.putExtra("msg", msg);
		 * startActivity(is); }
		 */
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
	public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
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
	protected void onResume() {
		super.onResume();
		if (share != null) {
			share.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		if (type == PostService.TYPE_APPLEYTRY) {
			// Log.v("++++++++++++++++++", response);
			try {
				JSONObject obj = new JSONObject(response);
				JSONObject datas = obj.getJSONObject("datas");
				status = datas.getInt("status");
				if (status == 1) {
					msg = datas.getString("msg");
				} else {
					msg = datas.getString("error");
					Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void getHttp(String member_id, String try_id) {
		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(this);
		vhu.ApplyTry(this, id);
		vhu.setObjectList(this);
	}

	public void back(View v) {
		timer_text_view_.stopRun();
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_share_xiangqing:
			share = new SharePopupWindow(ShouYeProductActivity.this, flippingload);
			share.setPlatformActionListener(ShouYeProductActivity.this);
			ShareModel model = new ShareModel();
			model.setImageUrl(imageurl);
			model.setUrl(url);
			share.initShareParams(model);
			share.showShareWindow();
			// 显示位置
			share.showAtLocation(ShouYeProductActivity.this.findViewById(R.id.share_parent), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			break;
		case R.id.button1_:
			String member_id = SharepreUtil.getStringValue(ShouYeProductActivity.this, Globals.LOGINKEY, "0");
			String try_id = id;
			getHttp(member_id, try_id);
			resultOfappley();

			break;
		default:
			break;
		}
	}
}
