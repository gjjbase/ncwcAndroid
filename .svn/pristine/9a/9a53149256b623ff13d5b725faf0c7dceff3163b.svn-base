package com.ncwcandroid.ui.view.communal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;

/**
 * 协议内容
 * 
 * @author Administrator
 * 
 */
public class NewDialog extends BaseActivity implements OnClickListener {
	private Intent intent = new Intent();
	private TextView login;
	private WebView wb_agment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(NewDialog.this);
		setContentView(R.layout.agreemactivity);
		wb_agment = (WebView) findViewById(R.id.wb_agment);
		WebSettings webSettings = wb_agment.getSettings();
		webSettings.setSaveFormData(false);
		webSettings.setSupportZoom(false);
		if (getIntent().getStringExtra("type").equals("free")) {
			wb_agment.loadUrl("http://m.nichewoche.com/mobile/protocol/service.html");
		} else if (getIntent().getStringExtra("type").equals("old")) {
			wb_agment.loadUrl("http://m.nichewoche.com/mobile/protocol/trial.html");
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
			setResult(Globals.RESULT_CODE, intent);
			finish();
			break;
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		login = (TextView) findViewById(R.id.login);
		login.setOnClickListener(this);
	}
}
