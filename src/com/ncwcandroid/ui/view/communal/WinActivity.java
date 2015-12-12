package com.ncwcandroid.ui.view.communal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.RemSharepreUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.perscenter.LoginActivity;

/**
 * 操作成功页面
 * 
 * @author Administrator
 * 
 */
public class WinActivity extends BaseActivity {
	private TextView txt_enter, login;
	private ImageView img_enter;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(WinActivity.this);
		setContentView(R.layout.activity_win);
		txt_enter = (TextView) findViewById(R.id.txt_enter);
		login = (TextView) findViewById(R.id.login);
		img_enter = (ImageView) findViewById(R.id.img_enter);
		try {
			if (getIntent().getStringExtra("win").equals("phone")) {
				txt_enter.setText(getResources().getString(R.string.enterphonepsw));
				img_enter.setImageResource(R.drawable.check);
			} else if (getIntent().getStringExtra("win").equals("email")) {
				txt_enter.setText(getResources().getString(R.string.enterphonepsw));
				img_enter.setImageResource(R.drawable.message);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		switch (SharepreUtil.getIntValue(getApplicationContext(), Globals.LOGINTYPE, Globals.ERRORCODE)) {
		case Globals.MOBILELONG:
			/** 邮箱登陆 */
			txt_enter.setText(getResources().getString(R.string.enterphonepsw));
			img_enter.setImageResource(R.drawable.message);
			break;
		case Globals.EMAILLOGIN:
			/** 手机登陆 */
			txt_enter.setText(getResources().getString(R.string.enterphonepsw));
			img_enter.setImageResource(R.drawable.check);
			break;
		case Globals.USERLOGIN:
			/** 用户名登陆 */
		case Globals.THIRDLOGIN:
			/** 第三方登陆 */
			if (GlobalUtil.isMobileNO(SharepreUtil.getStringValue(getApplicationContext(), Globals.MOBILE, Globals.ERROE))) {
				/** 手机存在 */
				txt_enter.setText(getResources().getString(R.string.enterphonepsw));
				img_enter.setImageResource(R.drawable.check);
				break;
			}
			if (GlobalUtil.isEmailNO(SharepreUtil.getStringValue(getApplicationContext(), Globals.EMAIL, Globals.ERROE))) {
				/** 邮箱存在 */
				txt_enter.setText(getResources().getString(R.string.enterphonepsw));
				img_enter.setImageResource(R.drawable.message);
				break;
			}
			break;
		case Globals.ERRORCODE:
			Log.e("@@@@@@", "@@@@@@");
			break;
		default:
			break;
		}
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GlobalUtil.showToast(getApplicationContext(), Globals.RETURNMSG);
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
				SharepreUtil.clear(getApplicationContext());
				RemSharepreUtil.putStringValue(getApplicationContext(), Globals.REMBERPSW, Globals.REMBERPSW);
				finish();
			}
		});
	}
}
