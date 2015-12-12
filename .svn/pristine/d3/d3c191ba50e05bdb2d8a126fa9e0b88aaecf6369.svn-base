package com.ncwcandroid.ui.view.communal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;

/**
 * 操作成功页面
 * 
 * @author Administrator
 * 
 */
public class SuccPswActivity extends BaseActivity {
	private TextView txt_enter, login, txt_title;
	private String set = "";

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(SuccPswActivity.this);
		setContentView(R.layout.activity_succpsw);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		txt_enter = (TextView) findViewById(R.id.txt_enter);
		login = (TextView) findViewById(R.id.login);
		txt_title = (TextView) findViewById(R.id.txt_title);
		try {
			txt_title.setText(getIntent().getStringExtra("title"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			set = getIntent().getStringExtra("set");
			if (set.equals("setmobile")) {
				// 绑定手机
				txt_title.setText(R.string.removemoile);
				txt_enter.setText(R.string.phonesucced);
			} else if (set.equals("setemail")) {
				// 找回密码
				txt_title.setText(R.string.removeeamil);
				txt_enter.setText(R.string.emailsucced);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(Activity.RESULT_OK, new Intent());
				finish();
			}
		});
	}

}
