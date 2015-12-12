package com.ncwcandroid.ui.adapter;

import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class MenuBaseAdapter<T, Q> extends BaseAdapter {

	private List<T> list;
	
	public MenuBaseAdapter() {
		super();
	}

	public MenuBaseAdapter(Context context, List<T> list) {
		super();
		this.list = list;
	}

	public MenuBaseAdapter(Context context, List<T> list, Q view) {
		super();
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
