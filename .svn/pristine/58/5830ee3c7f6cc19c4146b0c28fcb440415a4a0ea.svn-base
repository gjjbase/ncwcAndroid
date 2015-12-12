package com.ncwcandroid.ui.util;

/**
 * android线程对webservice返回结果进行封装类(异步访问)
 */
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ncwcandroid.ui.config.HttpHelper;
import com.ncwcandroid.ui.widget.FlippingLoadingDialog;

public class ThreadUtil extends Thread {

	private Handler handler;
	private HashMap<String, String> jsonParamStr;
	private String methodStr;
	private FlippingLoadingDialog progressDialog = null;

	public ThreadUtil(Handler handler) {
		this.handler = handler;
	}

	// 线程开始，调用webservice
	public void doStartWebServicerequestWebService(Activity activity, HashMap<String, String> jsonParamStr, String methodStr, boolean view) {
		this.jsonParamStr = jsonParamStr;
		this.methodStr = methodStr;
		// 开始请求
		if (view) {// 是否显示请求窗口
			progressDialog = new FlippingLoadingDialog(activity);
			progressDialog.show();
		}
		this.start();
	}

	@Override
	public void run() {
		super.run();
		Message message = handler.obtainMessage();// 构造消息
		Bundle b = new Bundle();
		try {
			String soapObject = HttpHelper.sendPOSTRequest(methodStr, jsonParamStr);
			message.what = 1;// 消息的类型
			if (soapObject != null && !soapObject.equals("")) {// 代表返回结果有数据
				try {
					b.putString("returnJson", soapObject);
				} catch (Exception e2) {
				}
			} else {
				message.what = 2;
			}
		} catch (Exception e) {// 异常
			message.what = 2;
			b.putString("error", e.getMessage());
			interrupt();
		}
		message.setData(b);
		handler.sendMessage(message);
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
}
