package com.ncwcandroid.ui.view.adreview;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.BaseFragment;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.OnBorderListener;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.widget.MyImageButton;
import com.ncwcandroid.ui.widget.ScrollViewExtend;

/**
 * 往期回顾首页
 * 
 * @author Administrator
 * 
 */
public class OldFragment extends BaseFragment implements VolleyStringListener {

	// private String member_id, key;

	private ImageView res_new;
	private ScrollViewExtend sv_wqhg;

	private DisplayMetrics dm;
	private LinearLayout leftLinear;
	private LinearLayout rightLinear;
	private int titleWidth;
	// private int titleHeight;
	private LinearLayout.LayoutParams leftParams;
	private LinearLayout.LayoutParams rightParams;
	private View rootView;
	int i = 10;
	private ArrayList<MyImageButton> list_img_btn = new ArrayList<MyImageButton>();

	@SuppressLint("InflateParams")
	@Override
	protected View initView(LayoutInflater inflater) {
		rootView = inflater.inflate(R.layout.activity_wangqi_main, null);
		res_new = (ImageView) rootView.findViewById(R.id.res_new);
		sv_wqhg = (ScrollViewExtend) rootView.findViewById(R.id.sv_wqhg);

		res_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), R.string.res_new, Toast.LENGTH_SHORT).show();
			}
		});

		sv_wqhg.SetBorderListener(new OnBorderListener() {

			@Override
			public void onTop() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBottom() {
				i += 5;
				// Log.v("DODODODODODODODODODO", i + "pppppppppppppppppppp");
				gethttp_w(i + "");
			}
		});

		// 获取界面控件
		leftLinear = (LinearLayout) rootView.findViewById(R.id.layout_linear_l);
		rightLinear = (LinearLayout) rootView.findViewById(R.id.layout_linear_r);

		// 获取手机屏幕宽高
		dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		titleWidth = dm.widthPixels / 2 - 15;
		// titleHeight = dm.heightPixels / 2;

		leftParams = new LinearLayout.LayoutParams(titleWidth, titleWidth);
		leftParams.leftMargin = 10;
		leftParams.rightMargin = 15;
		leftParams.bottomMargin = 15;
		rightParams = new LinearLayout.LayoutParams(titleWidth, titleWidth);
		rightParams.leftMargin = 15;
		rightParams.rightMargin = 10;
		rightParams.bottomMargin = 15;

		return rootView;
	}

	@Override
	public void initData() {
		gethttp();
	}

	private void gethttp() {
		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(getActivity());
		vhu.ListProducts(getActivity(), "2", "10", "1", true);
		vhu.setObjectList(this);
	}

	public void gethttp_w(String eachnum) {
		VolleyHttpUtil vhu = VolleyHttpUtil.getInstance(getActivity());
		vhu.ListProducts(getActivity(), "2", eachnum, "1", false);
		vhu.setObjectList(this);
	}

	@SuppressLint({ "NewApi", "ResourceAsColor" })
	@Override
	public void ReturnVolleyString(String response, String type) {
		// Log.v("===========", response);
		try {
			JSONObject obj = new JSONObject(response);
			int code = obj.getInt("code");
			if (code == 200) {
				JSONObject obj_ = obj.getJSONObject("datas");
				int status = obj_.getInt("status");
				if (status == 0) {
					String error = obj_.getString("error");
					Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
				}
				int hasmore = obj_.getInt("hasmore");
				JSONArray array = obj_.getJSONArray("list");
				if (array.length() == 0) {
					Toast.makeText(getActivity(), Globals.NOINFO, Toast.LENGTH_SHORT).show();
					// Intent intent = new Intent(getActivity(),
					// LoadFalse.class);
					// Bundle bund = new Bundle();
					// bund.putInt("from", 4);
					// intent.putExtras(bund);
					// startActivity(intent);
					// if (getActivity() instanceof MainActivity) {
					// ((MainActivity) getActivity()).finish();
					// }
				} else {
					if (i <= 10) {
						doWithResult(array);
					} else {
						for (int j = 0; j < list_img_btn.size(); j++) {
							list_img_btn.get(j).setVisibility(View.GONE);
						}
						list_img_btn.clear();
						doWithResult(array);
						sv_wqhg.fullScroll(ScrollView.FOCUS_DOWN);
						if (hasmore == 0) {
							Toast.makeText(getActivity(), R.string.load_ok, Toast.LENGTH_SHORT).show();
						}
					}
				}
			} else {
				GlobalUtil.showToast(getActivity(), Globals.FAULTERROE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void doWithResult(JSONArray arrays) {
		// 循环array动态添加按钮，并为按钮控件添加设置属性信息
		for (int i = 0; i < arrays.length(); i++) {
			JSONObject item;
			try {
				item = arrays.getJSONObject(i);
				// 每次循环是的数组中的数据，作为传递的数据信息
				final String id = item.getString("id");
				final String big_img = item.getString("big_img");
				final String qi_num = item.getString("period_no");
				final String title = item.getString("title");
				final String small_info = item.getString("small_info");
				final String num = item.getString("number");
				final String persons = item.getString("try_people");
				final String presence = item.getString("presence");
				final String price = item.getString("price");
				final String prize = item.getString("prize");
				String str = "";
				if (Integer.parseInt(qi_num) < 10) {
					str = "第0" + qi_num + "期" + "  " + title;
				} else {
					str = "第" + qi_num + "期" + "  " + title;
				}
				final String img = item.getString("img");

				// 为按钮控件设置属性信息
				MyImageButton myimg = new MyImageButton(getActivity());
				myimg.setMinimumWidth(titleWidth);
				myimg.setMaxWidth(titleWidth);
				myimg.setMinimumHeight(titleWidth);
				myimg.setMaxHeight(titleWidth);
				myimg.setScaleType(ImageView.ScaleType.CENTER_CROP);
				AsyncLoadingPicture.getInstance(getActivity()).LoadPicture(img, myimg);
				if (str.length() >= 16) {
					str = str.substring(0, 16) + "..";
				}
				str = "\u3000" + str;
				myimg.setText(str);
				myimg.setColor(Color.WHITE);
				myimg.setTextSize(dip2px(getActivity(), 10));

				// 动态添加界面控件
				if (i % 2 != 0) {
					myimg.setLayoutParams(rightParams);
					rightLinear.addView(myimg);
				} else {
					myimg.setLayoutParams(leftParams);
					leftLinear.addView(myimg);
				}

				list_img_btn.add(myimg);

				// 为动态添加过的按钮控件设置点击监听事件
				myimg.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent in = new Intent(new Intent(getActivity(), WangQiProductActivity.class));
						in.putExtra("id", id);
						in.putExtra("img", img);
						in.putExtra("big_img", big_img);
						in.putExtra("qi_num", qi_num);
						in.putExtra("name", title);
						in.putExtra("small_info", small_info);
						in.putExtra("num", num);
						in.putExtra("persons", persons);
						in.putExtra("presence", presence);
						in.putExtra("price", price);
						in.putExtra("prize", prize);
						startActivity(in);
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

}