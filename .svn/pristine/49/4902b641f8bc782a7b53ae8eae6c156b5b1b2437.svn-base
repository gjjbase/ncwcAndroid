package com.ncwcandroid.ui.adapter;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.widget.CircularImage;

public class ZhongJiangMDAdapter extends BaseAdapter {

	private Context c;
	private JSONArray array;

	public ZhongJiangMDAdapter(Context context, JSONArray array) {
		this.c = context;
		this.array = array;
	}

	@Override
	public int getCount() {
		return (int) Math.ceil(((double) array.length() + 1) / 2.0);
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Log.v("99999999999999999", array.toString());
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(c).inflate(
					R.layout.wangqi_zjmd_item, null);

			vh.img_wangqi_user_l = (CircularImage) convertView
					.findViewById(R.id.img_wangqi_user_l);
			vh.tv_wangqi_username_l = (TextView) convertView
					.findViewById(R.id.tv_wangqi_username_l);
			vh.img_wangqi_user_r = (CircularImage) convertView
					.findViewById(R.id.img_wangqi_user_r);
			vh.tv_wangqi_username_r = (TextView) convertView
					.findViewById(R.id.tv_wangqi_username_r);
			vh.rel_left = (RelativeLayout) convertView
					.findViewById(R.id.rel_left);
			vh.rel_rigt = (RelativeLayout) convertView
					.findViewById(R.id.rel_rigt);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		try {
			AsyncLoadingPicture.getInstance(c).LoadPicture(
					array.getJSONObject(position * 2)
							.getString("member_avatar"), vh.img_wangqi_user_l);
			String content = array.getJSONObject(position * 2).getString(
					"member_name");
			String nickname = array.getJSONObject(position * 2).getString(
					"member_truename");
			if (content.length() >= 12) {
				content = content.substring(0, 12) + "..";
			}
			if (nickname.length() >= 12) {
				nickname = nickname.substring(0, 12) + "..";
			}
			if (nickname == "null" || nickname.equals("")) {
				vh.tv_wangqi_username_l.setText(content);
			} else {
				vh.tv_wangqi_username_l.setText(nickname);
			}
		} catch (JSONException e1) {
			vh.rel_left.setVisibility(View.GONE);
			e1.printStackTrace();
		}
		try {
			AsyncLoadingPicture.getInstance(c).LoadPicture(
					array.getJSONObject(position * 2 + 1).getString(
							"member_avatar"), vh.img_wangqi_user_r);
			String content = array.getJSONObject(position * 2 + 1).getString(
					"member_name");
			String nickname = array.getJSONObject(position * 2 + 1).getString(
					"member_truename");
			// Log.v("000000000000000000000000", nickname + "dddddddddddd" + d);
			if (content.length() >= 12) {
				content = content.substring(0, 12) + "..";
			}
			if (nickname.length() >= 12) {
				nickname = nickname.substring(0, 12) + "..";
			}
			if (nickname == "null" || nickname.equals("")) {
				vh.tv_wangqi_username_r.setText(content);
			} else {
				vh.tv_wangqi_username_r.setText(nickname);
			}
		} catch (Exception e) {
			vh.rel_rigt.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		CircularImage img_wangqi_user_l, img_wangqi_user_r;
		TextView tv_wangqi_username_l, tv_wangqi_username_r;
		RelativeLayout rel_left, rel_rigt;
	}

}
