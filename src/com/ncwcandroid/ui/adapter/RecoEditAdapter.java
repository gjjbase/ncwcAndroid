package com.ncwcandroid.ui.adapter;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.QLBaseAdapter;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.DateTools;
import com.ncwcandroid.ui.view.adreview.WangQiBianJiShiYongbaogaoActivity;

public class RecoEditAdapter extends QLBaseAdapter<String, Integer> {
	private Context context;
	private JSONArray jso;
	private ViewHolder viewholder;

	public RecoEditAdapter(Context context, JSONArray jso) {
		super(context, jso);
		this.context = context;
		this.jso = jso;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			viewholder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.recoadapter_item, null);
			viewholder.txt_period = (TextView) arg1.findViewById(R.id.txt_period);
			viewholder.txt_title = (TextView) arg1.findViewById(R.id.txt_title);
			viewholder.txt_time = (TextView) arg1.findViewById(R.id.txt_time);
			viewholder.img_type = (ImageView) arg1.findViewById(R.id.img_type);
			viewholder.img_exp = (ImageView) arg1.findViewById(R.id.img_exp);
			arg1.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) arg1.getTag();
		}
		try {
			/** 加载图片 */
			try {
				AsyncLoadingPicture.getInstance(context).LoadPicture(jso.getJSONObject(arg0).getString("img"), viewholder.img_exp);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			String num = jso.getJSONObject(arg0).getString("period_no");
			if (Integer.parseInt(num) < 10) {
				num = "0" + jso.getJSONObject(arg0).getString("period_no") + "\n期";
			}
			viewholder.txt_period.setText(num);
			viewholder.txt_title.setText(jso.getJSONObject(arg0).getString("title"));
			viewholder.txt_time.setText(DateTools.getStrTime_ymd_hm(jso.getJSONObject(arg0).getString("open_prize")));
			switch (jso.getJSONObject(arg0).getInt(Globals.STATUS)) {
			case 0:
				/** 审核 */
				viewholder.img_type.setImageResource(R.drawable.shenhe);
				break;
			case 3:
				/** 通过，中奖 */
				viewholder.img_type.setImageResource(R.drawable.zhongjianglianjie);
				viewholder.img_type.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							Intent intet = new Intent();
							intet.setClass(context, WangQiBianJiShiYongbaogaoActivity.class);
							intet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intet.putExtra("id", jso.getJSONObject(arg0).getString("try_id"));
							intet.putExtra("img", jso.getJSONObject(arg0).getString("img"));
							intet.putExtra("title", jso.getJSONObject(arg0).getString("title"));
							intet.putExtra("info", jso.getJSONObject(arg0).getString("small_info"));
							context.startActivity(intet);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				break;
			case 2:
				/** 失败,未中奖 */
				viewholder.img_type.setImageResource(R.drawable.weizhongjiang);
				break;

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return arg1;
	}

	class ViewHolder {
		TextView txt_period, txt_title, txt_time;
		ImageView img_type, img_exp;
	}
}
