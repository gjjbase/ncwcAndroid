package com.ncwcandroid.ui.interfac;

import org.json.JSONException;

/**
 * 地址操作返回接口
 * 
 * @author Administrator
 * 
 */
public interface AdsEditListener {
	void onEdit(int id);

	void onDelect(int id) throws JSONException;
	void SetDef(int id) throws JSONException;
}
