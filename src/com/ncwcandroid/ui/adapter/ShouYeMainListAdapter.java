package com.ncwcandroid.ui.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.bean.Timer;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.freetrial.ShouYeAppleyTryFalseInfo;
import com.ncwcandroid.ui.view.freetrial.ShouYeInfoProductDialogActivity;
import com.ncwcandroid.ui.view.freetrial.ShouYeJoinInfoActivity;
import com.ncwcandroid.ui.view.freetrial.ShouYeProductActivity;

public class ShouYeMainListAdapter extends BaseAdapter implements VolleyStringListener {

	private Context c;
	private JSONArray jso;
	private String member_id;
	private int status;
	private String msg;

	/**
	 * code:判断是run页面还是will页面 run:0 will:1
	 */
	private int code;

	private String[] imgs = new String[1000];
	private String[] qi_num = new String[1000];
	private String[] id = new String[1000];
	private String[] num = new String[1000];
	private String[] persons = new String[1000];
	private String[] title = new String[1000];
	private String[] small_info = new String[1000];
	private int[] presence = new int[1000];
	private String[] price = new String[1000];
	// 倒计时操作
	private ArrayList<Timer> times;
	private String strtime;
	private long alltime;
	private long[] hs = new long[20];
	private long[] ms = new long[20];
	private long[] ss = new long[20];

	public ShouYeMainListAdapter(Context context, JSONArray jso, ArrayList<Timer> times, int code) {
		this.c = context;
		this.jso = jso;
		this.times = times;
		this.code = code;
		member_id = SharepreUtil.getStringValue(c, Globals.MEMBERID, "0");
	}

	@Override
	public int getCount() {
		return jso.length();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "NewApi", "InflateParams" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(c).inflate(R.layout.list_shouye_main_item, null);
			vh = new ViewHolder();
			vh.img_product = (ImageView) convertView.findViewById(R.id.img_product);
			vh.tv_qi_num = (TextView) convertView.findViewById(R.id.tv_qi_num);
			vh.tv_product_title = (TextView) convertView.findViewById(R.id.tv_product_title);
			vh.tv_product_content = (TextView) convertView.findViewById(R.id.tv_product_content);
			vh.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			vh.tv_persons = (TextView) convertView.findViewById(R.id.tv_persons);
			vh.timer_text_view = (TextView) convertView.findViewById(R.id.timer_text_view);
			vh.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			vh.button1 = (Button) convertView.findViewById(R.id.button1);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		/**
		 * 具体操作
		 */
		// 相同的操作
		JSONObject item = null;
		try {
			item = jso.getJSONObject(position);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		Timer timer = times.get(position);
		alltime = timer.getTime() / 1000;
		String d_l = alltime / (60 * 60 * 24) + "";
		String h_l = alltime / (60 * 60) % 24 + "";
		String m_l = (alltime / (60)) % 60 + "";
		String s_l = alltime % 60 + "";
		if (Integer.parseInt(d_l) < 10) {
			d_l = "0" + d_l;
		}
		if (Integer.parseInt(h_l) < 10) {
			h_l = "0" + h_l;
		}
		if (Integer.parseInt(m_l) < 10) {
			m_l = "0" + m_l;
		}
		if (Integer.parseInt(s_l) < 10) {
			s_l = "0" + s_l;
		}

		strtime = d_l + "天 " + h_l + "时 " + m_l + "分 " + s_l + "秒 ";

		vh.timer_text_view.setText(strtime);

		hs[position] = alltime / (60 * 60);
		ms[position] = (alltime / (60)) % 60;
		ss[position] = alltime % 60;

		// 不同的操作
		if (code == 0) {
			// 来自run页面的处理
			try {
				id[position] = item.getString("id");
				presence[position] = item.getInt("presence");

				imgs[position] = item.getString("img");
				qi_num[position] = item.getString("period_no");
				String qinum = qi_num[position];
				if (Integer.parseInt(qinum) < 10) {
					vh.tv_qi_num.setText("0" + qi_num[position]);
				} else {
					vh.tv_qi_num.setText(qi_num[position]);
				}
				num[position] = item.getString("number");
				vh.tv_num.setText(num[position]);
				persons[position] = item.getString("try_people");
				vh.tv_persons.setText(persons[position]);
				title[position] = item.getString("title");
				String tit = item.getString("title");
				price[position] = item.getString("price");
				vh.tv_price.setText(Html.fromHtml("<small>原价:</small><big><font color='#FF0000'>￥" + item.getString("price") + "</font></big>"));
				vh.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				if (tit.length() > 16) {
					vh.tv_product_title.setText(tit.substring(0, 16) + "...");
				} else {
					vh.tv_product_title.setText(tit);
				}
				small_info[position] = item.getString("small_info");
				String content_ = item.getString("small_info");
				if (content_.length() > 40) {
					vh.tv_product_content.setText("\u3000\u3000" + content_.substring(0, 40) + "...");
				} else {
					vh.tv_product_content.setText("\u3000\u3000" + content_);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			AsyncLoadingPicture.getInstance(c).LoadPicture(imgs[position], vh.img_product);

			vh.img_product.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent in = new Intent(c, ShouYeProductActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("where", 0);
					// Log.v("OOOOOOOOOOOwwwwwwwwwwwwwwwwwO", "000000000000000"
					// + presence[position]);
					bundle.putInt("presence", presence[position]);
					bundle.putString("img", imgs[position]);
					bundle.putString("qi_num", qi_num[position]);
					bundle.putString("id", id[position]);
					bundle.putString("num", num[position]);
					bundle.putString("persons", persons[position]);
					bundle.putString("titletv", title[position]);
					bundle.putString("contenttv", small_info[position]);
					bundle.putString("price", price[position]);
					// 剩余时间
					bundle.putLong("h", hs[position]);
					bundle.putLong("m", ms[position]);
					bundle.putLong("s", ss[position]);

					in.putExtras(bundle);
					c.startActivity(in);
				}
			});

			// 按钮处理
			if (presence[position] == 1) {
				vh.button1.setText("已申请");
				vh.button1.setBackgroundResource(R.drawable.corners_button_no_bg);
				vh.button1.setEnabled(false);
			} else if (presence[position] == 0) {
				vh.button1.setText("免费试用");
				vh.button1.setBackgroundResource(R.drawable.corners_button_bg);
				vh.button1.setEnabled(true);
			}

			vh.button1.setOnClickListener(new OnClickListener() {

				private void setoncli_this() {

					if (status == 1) {
						// Log.v("000000000000000",
						// "00000000000000000000000");
						SharedPreferences sp = c.getSharedPreferences("isClose", Activity.MODE_PRIVATE);

						if (sp.getBoolean("isClose", false) == false) {
							Intent in = new Intent(c, ShouYeInfoProductDialogActivity.class);
							Bundle bundle = new Bundle();
							bundle.putInt("where", 1);
							bundle.putString("qi_num", qi_num[position]);
							bundle.putString("img", imgs[position]);
							bundle.putString("id", id[position]);
							bundle.putString("num", num[position]);
							bundle.putString("persons", persons[position]);
							bundle.putString("titletv", title[position]);
							bundle.putString("contenttv", small_info[position]);
							bundle.putString("price", price[position]);
							// 剩余时间
							bundle.putLong("h", hs[position]);
							bundle.putLong("m", ms[position]);
							bundle.putLong("s", ss[position]);

							in.putExtras(bundle);
							c.startActivity(in);
						} else {
							Intent in = new Intent(c, ShouYeJoinInfoActivity.class);
							Bundle bundle = new Bundle();
							bundle.putInt("where", 1);
							bundle.putString("qi_num", qi_num[position]);
							bundle.putString("img", imgs[position]);
							bundle.putString("id", id[position]);
							bundle.putString("num", num[position]);
							bundle.putString("persons", persons[position]);
							bundle.putString("titletv", title[position]);
							bundle.putString("contenttv", small_info[position]);
							bundle.putString("price", price[position]);
							// 剩余时间
							bundle.putLong("h", hs[position]);
							bundle.putLong("m", ms[position]);
							bundle.putLong("s", ss[position]);

							in.putExtras(bundle);
							c.startActivity(in);
						}
						Intent is = new Intent(c, ShouYeAppleyTryFalseInfo.class);
						is.putExtra("msg", msg);
						c.startActivity(is);
					}

				}

				@Override
				public void onClick(View v) {
					if (SharepreUtil.getStringValue(c, Globals.LOGINKEY, Globals.LOGINKEY).equals(Globals.LOGINKEY)) {
						GlobalUtil.showToast(c, Globals.NEEDLOGIN);
						Intent intnet = new Intent("com.ncwcandroid.ui.interfac.BootBroadcastReceiver");
						c.sendBroadcast(intnet);
						return;
					}
					getHttp(member_id, id[position]);

					if (SharepreUtil.getStringValue(c, Globals.LOGINKEY, Globals.LOGINKEY).equals(Globals.LOGINKEY)) {
						setoncli_this();
					} else {
						new Thread(new Runnable() {

							@Override
							public void run() {
								try {
									Thread.sleep(800);
									setoncli_this();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}).start();
					}

				}

			});

		} else if (code == 1) {
			// 来自will页面的处理
			try {
				AsyncLoadingPicture.getInstance(c).LoadPicture(item.getString("img"), vh.img_product);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			try {
				String qii = item.getString("period_no");
				if (Integer.parseInt(qii) < 10) {
					vh.tv_qi_num.setText("0" + item.getString("period_no"));
				} else {
					vh.tv_qi_num.setText(item.getString("period_no"));
				}
				vh.tv_num.setText(item.getString("number"));
				vh.tv_persons.setText(item.getString("try_people"));
				String tits = item.getString("title");
				vh.tv_price.setText(Html.fromHtml("<small>原价:</small><big><font color='#FF0000'>￥" + item.getString("price") + "</font></big>"));
				vh.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				if (tits.length() > 16) {
					vh.tv_product_title.setText(tits.substring(0, 16) + "...");
				} else {
					vh.tv_product_title.setText(tits);
				}
				String conten = item.getString("small_info");
				if (conten.length() > 40) {
					vh.tv_product_content.setText("\u3000\u3000" + conten.substring(0, 40) + "...");
				} else {
					vh.tv_product_content.setText("\u3000\u3000" + conten);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			vh.img_product.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(c, "该期活动尚未开启", Toast.LENGTH_SHORT).show();
				}
			});
			// 按钮处理
			vh.button1.setText("敬请期待");
			vh.button1.setBackgroundResource(R.drawable.corners_button_no_bg);
			vh.button1.setEnabled(false);
		} else {
			// 无操作
		}

		return convertView;
	}

	private class ViewHolder {
		ImageView img_product;
		TextView tv_qi_num;
		TextView tv_product_title;
		TextView tv_product_content;
		TextView tv_num;
		TextView tv_persons;
		TextView timer_text_view;
		TextView tv_price;
		Button button1;
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// Log.v("++++++++++++++++++", response);
		try {
			JSONObject obj = new JSONObject(response);
			int code = obj.getInt("code");
			if (code == 200) {
				JSONObject datas = obj.getJSONObject("datas");
				status = datas.getInt("status");
				// Toast.makeText(c, response, 9000000).show();
				if (status == 1) {
					msg = datas.getString("msg");
				} else {
					msg = datas.getString("error");
					Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void getHttp(String member_id, String try_id) {
		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(c);
		vhu.ApplyTry((Activity) c, try_id);
		vhu.setObjectList(this);
	}

}
