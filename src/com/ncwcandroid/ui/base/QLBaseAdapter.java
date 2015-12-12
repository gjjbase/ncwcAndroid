package com.ncwcandroid.ui.base;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * Adapter基类
 * 
 */
public abstract class QLBaseAdapter<T, Q> extends BaseAdapter {

	public Context context;
	public JSONArray jso;//
	public Q view; // 这里不一定是ListView,比如GridView,CustomListView

	public QLBaseAdapter(Context context, JSONArray jso, Q view) {
		this.context = context;
		this.jso = jso;
		this.view = view;
	}

	public QLBaseAdapter(Context context, JSONArray jso) {
		this.context = context;
		this.jso = jso;

	}

	@Override
	public int getCount() {
		return jso.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return jso.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
