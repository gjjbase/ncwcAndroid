package com.ncwcandroid.ui.interfac;

import org.json.JSONException;


/**
 * volley数据返回接口
 * @author Administrator
 *
 */
public interface VolleyStringListener {
	/**
	 * 
	 * @param response 请求的返回数据
	 * @param type 发送请求的参数，用于发送不同的网络请求
	 * @throws JSONException 
	 */
	void ReturnVolleyString(String response,String type) throws JSONException;
}
