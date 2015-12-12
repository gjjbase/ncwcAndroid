package com.ncwcandroid.ui.view.perscenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;

/**
 * 用户反馈页
 * 
 * @author Administrator
 * 
 */
public class FeedBookActivity extends BaseActivity implements VolleyStringListener {
	private EditText edt_feed;
	private TextView submit;
	private String strsubmit;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(FeedBookActivity.this);
		setContentView(R.layout.activity_feedbook);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		edt_feed = (EditText) findViewById(R.id.edt_feed);
		submit = (TextView) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initMsg();
			}
		});
	}

	private void initMsg() {
		// TODO Auto-generated method stub
		if (SharepreUtil.getStringValue(getApplicationContext(), Globals.LOGINKEY, Globals.LOGINKEY).equals(Globals.LOGINKEY)) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NEEDLOGIN);
			Intent intnet = new Intent("com.ncwcandroid.ui.interfac.BootBroadcastReceiver");
			sendBroadcast(intnet);
			return;
		}
		strsubmit = edt_feed.getText().toString().trim();
		if (strsubmit.isEmpty()) {
			GlobalUtil.showToast(getApplicationContext(), "请输入反馈信息");
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.FeedBoxData(FeedBookActivity.this, strsubmit, SharepreUtil.getStringValue(getApplicationContext(), Globals.LOGINKEY, ""));
		vtp.setObjectList(this);

	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		GlobalUtil.showToast(getApplicationContext(), "谢谢您的反馈");
		finish();
	}

}
