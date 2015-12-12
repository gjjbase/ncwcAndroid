package com.ncwcandroid.ui.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.DateTools;

public class ShouYeProductAdapter extends BaseAdapter {

	private Context c;
	private JSONArray array;

	public ShouYeProductAdapter(Context context, JSONArray array) {
		this.c = context;
		this.array = array;
	}

	@Override
	public int getCount() {
		if (array.length() == 0) {
			return 1;
		} else {
			return array.length();
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(c).inflate(R.layout.list_shouye_pinglun_item, null);
			vh = new ViewHolder();
			vh.img_user = (ImageView) convertView.findViewById(R.id.img_user);
			vh.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
			vh.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			vh.tv_pinglu = (TextView) convertView.findViewById(R.id.tv_pinglu);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		if (array.length() == 0) {
			vh.tv_username.setVisibility(View.GONE);
			vh.tv_time.setVisibility(View.GONE);
			vh.tv_pinglu.setText("\u3000\u3000" + "来第一个评论吧！");
		} else {
			try {
				JSONObject obj = array.getJSONObject(position);
				// 头像
				AsyncLoadingPicture.getInstance(c).LoadPicture(obj.getString("member_avatar"), vh.img_user);
				/*
				 * Bitmap bitmap = ImageLoader.getInstance().loadImageSync(
				 * obj.getString("member_avatar")); if (bitmap != null) {
				 * vh.img_user.setImageBitmap(bitmap); } else { // 无操作 }
				 */
				// 用户名和昵称
				if (!obj.getString("member_truename").equals("")) {
					if (obj.getString("member_truename").length() > 16) {
						vh.tv_username.setText(obj.getString("member_truename").substring(0, 16) + "...");
					} else {
						vh.tv_username.setText(obj.getString("member_truename"));
					}
				} else {
					if (!obj.getString("member_name").equals("")) {
						if (obj.getString("member_name").length() > 16) {
							vh.tv_username.setText(obj.getString("member_name").substring(0, 16) + "...");
						} else {
							vh.tv_username.setText(obj.getString("member_name"));
						}
					}
				}
				// 时间
				vh.tv_time.setText(DateTools.getNewsDetailsDate_s(obj.getString("add_time")));
				// 评论内容
				vh.tv_pinglu.setText("\u3000\u3000" + obj.getString("content"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return convertView;
	}

	private class ViewHolder {
		ImageView img_user;
		TextView tv_username;
		TextView tv_time;
		TextView tv_pinglu;
	}

}
