package com.ncwcandroid.ui.view.perscenter;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
/**
 * 关于你车我车页面
 * @author Administrator
 *
 */
public class AboutsUsActivity extends BaseActivity {
	private WebView wb_agment;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(AboutsUsActivity.this);
		setContentView(R.layout.activity_aboutus);
		wb_agment=(WebView) findViewById(R.id.wb_agment);
		WebSettings webSettings = wb_agment.getSettings();
		webSettings.setSaveFormData(false);
		webSettings.setSupportZoom(false);
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		wb_agment.loadUrl("http://m.nichewoche.com/mobile/protocol/about.html");
	}

}
