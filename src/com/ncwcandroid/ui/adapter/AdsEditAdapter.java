package com.ncwcandroid.ui.adapter;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.QLBaseAdapter;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.interfac.AdsEditListener;

public class AdsEditAdapter extends QLBaseAdapter<String, Integer> {
	ViewHolder viewholder;

	public AdsEditListener adslister;

	public AdsEditAdapter(Context context, JSONArray jso) {
		super(context, jso);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(final int post, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			viewholder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.activity_adsit, null);
			viewholder.txt_username = (TextView) arg1.findViewById(R.id.txt_username);
			viewholder.txt_mobile = (TextView) arg1.findViewById(R.id.txt_mobile);
			viewholder.txt_ads = (TextView) arg1.findViewById(R.id.txt_ads);
			viewholder.txt_edit = (TextView) arg1.findViewById(R.id.txt_edit);
			viewholder.txt_delect = (TextView) arg1.findViewById(R.id.txt_delect);
			viewholder.txt_def = (CheckBox) arg1.findViewById(R.id.txt_def);
			arg1.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) arg1.getTag();
		}
		try {
			viewholder.txt_username.setText(jso.getJSONObject(post).getString("true_name"));
			viewholder.txt_mobile.setText(jso.getJSONObject(post).getString("mob_phone"));
			viewholder.txt_ads.setText(jso.getJSONObject(post).getString(Globals.ADDRESS));
			if (jso.getJSONObject(post).getString("is_default").equals("1")) {
				viewholder.txt_def.setChecked(true);
				viewholder.txt_def.setText(R.string.setdefult);
				viewholder.txt_def.setEnabled(false);
			} else {
				viewholder.txt_def.setChecked(false);
				viewholder.txt_def.setText(R.string.selectdefult);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		viewholder.txt_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onEdit(post);
			}
		});
		viewholder.txt_delect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					onDelect(post);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		viewholder.txt_def.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub

			}
		});
		viewholder.txt_def.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					SetDef(post);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return arg1;
	}

	class ViewHolder {
		TextView txt_username, txt_mobile, txt_ads, txt_edit, txt_delect;
		CheckBox txt_def;
	}

	public void setAdsEditListener(AdsEditListener adslister) {
		this.adslister = adslister;
	}

	public void onEdit(int id) {
		// TODO Auto-generated method stub
		if (adslister != null) {
			adslister.onEdit(id);
		}
	}

	public void onDelect(int id) throws JSONException {
		// TODO Auto-generated method stub
		if (adslister != null) {
			adslister.onDelect(id);
		}
	}

	public void SetDef(int id) throws JSONException {
		if (adslister != null) {
			adslister.SetDef(id);
		}
	}

}
