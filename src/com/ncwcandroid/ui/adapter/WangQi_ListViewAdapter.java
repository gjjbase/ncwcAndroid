package com.ncwcandroid.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.DateTools;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.adreview.WangQiBianJiShiYongbaogaoActivity;
import com.ncwcandroid.ui.view.adreview.WangQiBigImageActivity;
import com.ncwcandroid.ui.view.adreview.WangQiSurePlaceActivity;
import com.ncwcandroid.ui.view.adreview.WangQiZhongjiangMdActivity;
import com.ncwcandroid.ui.view.freetrial.ShouYeProductActivity;
import com.ncwcandroid.ui.view.perscenter.LoginActivity;

public class WangQi_ListViewAdapter extends BaseAdapter implements VolleyStringListener {
	Context c;
	mPagerAdapter adaper;
	private ArrayList<ImageView> vp_items_imgs; // 上方viewpager的图片

	private String id;
	private String img;
	// private String big_img;
	// private String qi_num;
	private String title;
	private String info;
	private String number;
	private String persons;
	private String presence;
	private String price;
	private String prize;
	private JSONArray array_report;
	private int allnum;// 总个数
	private String zhongjiangmingdan;// 中奖名单json字符串

	private ViewPager vp;
	private LinearLayout littleimgs_layout;
	private ImageView[] littleImages;
	private ImageView littleImage;
	private List<ImageView> listimgs = new ArrayList<ImageView>();// 动态改变小viewpager下方小图标所需要
	private View TopView;
	// viewpager自动切换
	// private int oldPosition = 0;// 记录上一次点的位置
	private int currentItem; // 当前页面
	private ScheduledExecutorService scheduledExecutorService;

	private ArrayList<String> list_big_img = new ArrayList<String>();
	int num = 0;

	public WangQi_ListViewAdapter(Context context) {
		c = context;
		adaper = new mPagerAdapter();
		vp_items_imgs = new ArrayList<ImageView>();
	}

	public WangQi_ListViewAdapter(Context context, JSONArray array, String id, String qi_num, String title, String info, String number, String persons, String big_img, String img, String presence, String price, String prize, int allnum) {
		this.c = context;
		this.array_report = array;
		this.adaper = new mPagerAdapter();
		this.vp_items_imgs = new ArrayList<ImageView>();
		this.id = id;
		// this.qi_num = qi_num;
		this.title = title;
		this.info = info;
		this.number = number;
		this.persons = persons;
		// this.big_img = big_img;
		this.img = img;
		this.presence = presence;
		this.price = price;
		this.prize = prize;
		this.allnum = allnum;

		list_big_img.add(big_img);
		num = list_big_img.size();

		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(c);
		// 请求网络数据_中奖名单
		vhu.GetZhongJiangMingDan((Activity) c, id);
		vhu.setObjectList(this);

		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		// 每隔5秒钟切换一张图片
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5, 5, TimeUnit.SECONDS);
	}

	@Override
	public int getCount() {
		return array_report.length() + 2;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (position == 0)
			return 0;
		else if (position == 1)
			return 1;
		else
			return position - 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else if (position == 1) {
			return 1;
		} else {
			return 2;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@SuppressLint({ "ResourceAsColor", "NewApi", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder1 vh1 = null;
		ViewHolder2 vh2 = null;
		if (position == 0) {
			return getTopView();
		} else if (position == 1) {
			if (convertView == null) {
				convertView = LayoutInflater.from(c).inflate(R.layout.wangqi_product_item_1, null);
				vh1 = new ViewHolder1();
				vh1.tv_title = (TextView) convertView.findViewById(R.id.tv_wangqi_listitem1_title);
				vh1.img_to_sy_pro = (ImageView) convertView.findViewById(R.id.img_to_sy_pro);
				vh1.img_left = (ImageView) convertView.findViewById(R.id.img_left);
				vh1.tv_info = (TextView) convertView.findViewById(R.id.tv_wangqi_listitem1_scores);
				vh1.tv_some = (TextView) convertView.findViewById(R.id.tv_wangqi_listitem1_some);
				vh1.TextView01 = (TextView) convertView.findViewById(R.id.TextView01);
				vh1.tv_sureplace = (TextView) convertView.findViewById(R.id.tv_wangqi_sureplace);
				vh1.imgbtn_sybg = (TextView) convertView.findViewById(R.id.imgbtn_sybg);
				vh1.imgbtn_zjmd = (TextView) convertView.findViewById(R.id.imgbtn_zjmd);
				vh1.txt_price = (TextView) convertView.findViewById(R.id.txt_price);
				vh1.tv_sybg = (TextView) convertView.findViewById(R.id.tv_wangqi_baogaotijiao);
				convertView.setTag(vh1);
			} else {
				vh1 = (ViewHolder1) convertView.getTag();
			}
			vh1.img_left.setBackground(c.getResources().getDrawable(R.drawable.wangqi_bi));
			if (presence.equals("0")) {//
				vh1.tv_sureplace.setText(R.string.noselect);
				vh1.tv_sureplace.setBackground(c.getResources().getDrawable(R.drawable.corners_button_no_bg));
				vh1.tv_sureplace.setEnabled(false);
			} else if (presence.equals("1")) {
				vh1.tv_sureplace.setText(R.string.suresplace);
				vh1.tv_sureplace.setBackground(c.getResources().getDrawable(R.drawable.logintxt_shape));
				vh1.tv_sureplace.setEnabled(true);
			}
			if (prize.equals("0")) {
				vh1.imgbtn_sybg.setEnabled(false);
				vh1.imgbtn_sybg.setBackground(c.getResources().getDrawable(R.drawable.graycorners_button_bg));
				vh1.imgbtn_sybg.setTextColor(c.getResources().getColor(R.color.gray));
				vh1.img_left.setBackground(c.getResources().getDrawable(R.drawable.tijiaoshiyongbaogao2));
			}
			if (title.length() > 20) {
				vh1.tv_title.setText(title.substring(0, 20) + "...");
			} else {
				vh1.tv_title.setText(title);
			}
			vh1.tv_info.setTextColor(R.color.black);
			if (info.length() > 40) {
				vh1.tv_info.setText("\u3000\u3000" + info.substring(0, 40) + "...");
			} else {
				vh1.tv_info.setText("\u3000\u3000" + info);
			}
			vh1.tv_some.setText(Html.fromHtml("<small>免费提供:</small><big><font color='#FF0000'>" + number + "</font></big><small>份</small>"));
			vh1.TextView01.setText(Html.fromHtml("<small>参与人数:</small><big><font color='#FF0000'>" + persons + "</font></big><small>人</small>"));
			vh1.txt_price.setText(Html.fromHtml("<small>原价:</small><big><font color='#FF0000'>￥" + price + "</font></big>"));
			vh1.txt_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			vh1.tv_sybg.setText("试用报告  " + allnum);
			OnClickListener onclick = new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.tv_wangqi_sureplace:
						Intent inten = new Intent(c, WangQiSurePlaceActivity.class);
						inten.putExtra("id", id);
						c.startActivity(inten);
						break;
					case R.id.imgbtn_sybg:

						if (SharepreUtil.getStringValue(c, Globals.LOGINKEY, Globals.LOGINKEY).equals(Globals.LOGINKEY)) {
							GlobalUtil.showToast(c, Globals.NEEDLOGIN);
							c.startActivity(new Intent(c, LoginActivity.class));
							return;
						}
						Intent intet = new Intent(c, WangQiBianJiShiYongbaogaoActivity.class);
						intet.putExtra("id", id);
						intet.putExtra("img", img);
						intet.putExtra("title", title);
						intet.putExtra("info", info);
						c.startActivity(intet);
						break;
					case R.id.imgbtn_zjmd:
						Intent in = new Intent(c, WangQiZhongjiangMdActivity.class);
						in.putExtra("zhongjiang", zhongjiangmingdan);
						c.startActivity(in);
						break;
					}
				}
			};
			OnClickListener to_sy_pro = new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 跳转详情
					Intent ins = new Intent(c, ShouYeProductActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("where", 2);
					bundle.putString("id", id);
					ins.putExtras(bundle);
					c.startActivity(ins);
				}
			};
			vh1.tv_title.setOnClickListener(to_sy_pro);
			vh1.tv_info.setOnClickListener(to_sy_pro);
			vh1.img_to_sy_pro.setOnClickListener(to_sy_pro);
			vh1.tv_sureplace.setOnClickListener(onclick);
			vh1.imgbtn_sybg.setOnClickListener(onclick);
			vh1.imgbtn_zjmd.setOnClickListener(onclick);

			return convertView;
		} else {
			if (convertView == null) {
				convertView = LayoutInflater.from(c).inflate(R.layout.wangqi_product_item_2, null);
				vh2 = new ViewHolder2();
				vh2.img_user = (ImageView) convertView.findViewById(R.id.img_user);
				vh2.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
				vh2.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				vh2.star_zonghe = (RatingBar) convertView.findViewById(R.id.star_zonghe);
				vh2.tv_waiguan = (TextView) convertView.findViewById(R.id.tv_waiguan);
				vh2.tv_zhiliang = (TextView) convertView.findViewById(R.id.tv_zhiliang);
				vh2.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
				vh2.img_show_item = (ImageView) convertView.findViewById(R.id.img_show_item);
				convertView.setTag(vh2);
			} else {
				vh2 = (ViewHolder2) convertView.getTag();
			}

			// 具体操作（第三种复合类型）R.layout.wangqi_product_item_2
			try {
				JSONObject item_0 = array_report.getJSONObject(position - 2);
				AsyncLoadingPicture.getInstance(c).LoadPicture(item_0.getString("member_avatar"), vh2.img_user);

				if (!item_0.getString("member_truename").equals("")) {
					String truename = item_0.getString("member_truename");// 昵称
					if (truename.length() > 12) {
						vh2.tv_username.setText(truename.substring(0, 12) + "...");
					} else {
						vh2.tv_username.setText(truename);
					}
				} else {
					String name_ = item_0.getString("member_name");
					if (name_.length() > 12) {
						vh2.tv_username.setText(name_.substring(0, 12) + "...");
					} else {
						vh2.tv_username.setText(name_);
					}
				}
				vh2.tv_time.setText(DateTools.getStrTime_ymd_hm(item_0.getString("add_time")));
				vh2.star_zonghe.setRating(Integer.parseInt(item_0.getString("score")));
				vh2.tv_waiguan.setText(item_0.getString("appearance_info"));
				vh2.tv_zhiliang.setText(item_0.getString("quality_info"));
				vh2.tv_price.setText(item_0.getString("price_info"));
				AsyncLoadingPicture.getInstance(c).LoadPicture(item_0.getString("img_mini"), vh2.img_show_item);
				final String bigimage = item_0.getString("img");
				vh2.img_show_item.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent in_bigimage = new Intent(c, WangQiBigImageActivity.class);
						in_bigimage.putExtra("bigimg", bigimage);
						c.startActivity(in_bigimage);
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return convertView;
		}
	}

	// 对顶部viewpager处理
	@SuppressLint("InflateParams")
	private View getTopView() {

		if (TopView == null) {
			TopView = LayoutInflater.from(c).inflate(R.layout.wangqi_product_item_0, null);

			vp = (ViewPager) TopView.findViewById(R.id.vp);

			littleimgs_layout = (LinearLayout) TopView.findViewById(R.id.linear_vp);

			// 动态加载iewpager图片
			for (int i = 0; i < num; i++) {
				ImageView img = new ImageView(c);
				img.setScaleType(ImageView.ScaleType.FIT_CENTER);
				AsyncLoadingPicture.getInstance(c).LoadPicture(list_big_img.get(i), img);
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 打开该产品详情
					}
				});

				vp_items_imgs.add(img);
			}

			// 动态添加底部小圆点
			littleImages = new ImageView[num];
			for (int i = 0; i < littleImages.length; i++) {
				littleImage = new ImageView(c);

				LayoutParams params = new LayoutParams(56, 56);

				littleImage.setLayoutParams(params);
				if (i == 0) {
					littleImage.setImageResource(R.drawable.dot_selected);
				} else {
					littleImage.setImageResource(R.drawable.dot_noselected);
				}
				littleImage.setId(i);
				littleImages[i] = littleImage;
				littleimgs_layout.addView(littleImage);
			}

			vp.setAdapter(adaper);
			vp.setCurrentItem(0);
			vp.setOnPageChangeListener(new OnPageChangeListener() {

				// 页面选择

				@Override
				public void onPageSelected(int position) {
					// 将底部小圆点全部设置为未选中状态
					for (int i = 0; i < num; i++) {
						ImageView imgs = (ImageView) littleimgs_layout.findViewById(i);
						imgs.setImageResource(R.drawable.dot_noselected);
						listimgs.add(imgs);
					}
					// 匹配当前页页码找到要选中小圆点设置选中状态
					listimgs.get(position).setImageResource(R.drawable.dot_selected);
				}

				@Override
				public void onPageScrollStateChanged(int state) {

				}

				@Override
				public void onPageScrolled(int position,

				float positionOffset, int positionOffsetPixels) {

				}

			});
		}

		return TopView;
	}

	public class mPagerAdapter extends PagerAdapter {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;

		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public int getCount() {
			return vp_items_imgs.size();
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void destroyItem(View container, int position, Object object) {

			((ViewPager) container).removeView(vp_items_imgs.get(position));

		}

		@Override
		public Object instantiateItem(View container, int position) {

			((ViewPager) container).addView(vp_items_imgs.get(position));

			return vp_items_imgs.get(position);

		}

	};

	public class ViewHolder1 {
		TextView tv_title;
		ImageView img_to_sy_pro;
		ImageView img_left;
		TextView tv_info;
		TextView tv_some, TextView01;
		TextView tv_sureplace;
		TextView imgbtn_sybg, imgbtn_zjmd;
		TextView tv_sybg;
		TextView txt_price;
	}

	public class ViewHolder2 {
		ImageView img_user;
		TextView tv_username;
		TextView tv_time;
		RatingBar star_zonghe;
		TextView tv_waiguan;
		TextView tv_zhiliang;
		TextView tv_price;
		ImageView img_show_item;
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		if (type == PostService.TYPE_GETZHONGJIANGMINGDAN) {
			// Log.v("====get中奖名单====", response);
			zhongjiangmingdan = response;
		}
	}

	public class FixedSpeedScroller extends Scroller {
		private int mDuration = 1500;

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			// Ignore received duration, use fixed one instead
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		public void setmDuration(int time) {
			mDuration = time;
		}

		public int getmDuration() {
			return mDuration;
		}
	}

	// 切换图片
	private class ViewPagerTask implements Runnable {

		@Override
		public void run() {
			currentItem = (currentItem + 1) % vp_items_imgs.size();
			// 更新界面
			// handler.sendEmptyMessage(0);
			handler.obtainMessage().sendToTarget();
		}

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 设置当前页面
			vp.setCurrentItem(currentItem);
		}

	};

}
