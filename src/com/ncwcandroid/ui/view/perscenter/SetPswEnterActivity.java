package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONObject;

import android.app.Activity;
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
import com.ncwcandroid.ui.util.RemSharepreUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.communal.WinActivity;

/**
 * 确认密码页
 * 
 * @author Administrator
 * 
 */
public class SetPswEnterActivity extends BaseActivity implements VolleyStringListener {
	private TextView txt_rdt;
	private EditText edt_pswd, edt_rpswd;
	private String str_pswd, str_rpswd;
	private final int SETPSW = 300;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(SetPswEnterActivity.this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_setpswnext);
		AppManager.getInstance().addActivity(SetPswEnterActivity.this);
		txt_rdt = (TextView) findViewById(R.id.txt_rdt);
		edt_pswd = (EditText) findViewById(R.id.edt_pswd);
		edt_rpswd = (EditText) findViewById(R.id.edt_rpswd);
		txt_rdt.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				str_pswd = edt_pswd.getText().toString().trim();
				str_rpswd = edt_rpswd.getText().toString().trim();
				if (str_rpswd.isEmpty()) {
					GlobalUtil.showToast(getApplicationContext(), Globals.NOPSAA);
				} else if (!str_rpswd.equals(str_pswd)) {
					GlobalUtil.showToast(getApplicationContext(), Globals.NORPSAA);
				} else {
					initMsg();
				}
			}
		});
	}

	/**
	 * 验证是否成功
	 */
	private void initMsg() {
		// TODO Auto-generated method stub
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		switch (SharepreUtil.getIntValue(getApplicationContext(), Globals.LOGINTYPE, Globals.ERRORCODE)) {
		case Globals.EMAILLOGIN:
			/** 邮箱登陆 */
			vtp.ResetEmail(SetPswEnterActivity.this, getIntent().getStringExtra("str_code"), str_pswd, str_rpswd, getIntent().getStringExtra("strphone"));
			break;
		case Globals.MOBILELONG:
			/** 手机登陆 */
			vtp.ResetPswd(SetPswEnterActivity.this, getIntent().getStringExtra("str_code"), str_pswd, str_rpswd, getIntent().getStringExtra("strphone"));
			break;
		case Globals.USERLOGIN:
			/** 用户名登陆 */
		case Globals.THIRDLOGIN:
			/** 第三方登陆 */
			if (GlobalUtil.isMobileNO(getIntent().getStringExtra("strphone"))) {
				/** 手机存在 */
				vtp.ResetPswd(SetPswEnterActivity.this, getIntent().getStringExtra("str_code"), str_pswd, str_rpswd, getIntent().getStringExtra("strphone"));
				break;
			}
			if (GlobalUtil.isEmailNO(getIntent().getStringExtra("strphone"))) {
				/** 邮箱存在 */
				vtp.ResetEmail(SetPswEnterActivity.this, getIntent().getStringExtra("str_code"), str_pswd, str_rpswd, getIntent().getStringExtra("strphone"));
				break;
			}
			break;
		default:
			break;
		}
		vtp.setObjectList(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		try {
			JSONObject jso = new JSONObject(response);
			if (jso.getInt("code") == Globals.CODE) {
				try {
					if (jso.getJSONObject(Globals.DATAS).getJSONObject(Globals.ERROE).getBoolean(Globals.STATUS)) {
						RemSharepreUtil.putStringValue(getApplicationContext(), Globals.REMUSERNAME, getIntent().getStringExtra("strphone"));
						RemSharepreUtil.putStringValue(getApplicationContext(), Globals.REMPSW, str_rpswd);
						switch (SharepreUtil.getIntValue(getApplicationContext(), Globals.LOGINTYPE, Globals.ERRORCODE)) {
						case Globals.MOBILELONG:
							/** 邮箱登陆 */
							startActivityForResult(new Intent(getApplicationContext(), WinActivity.class), SETPSW);
							break;
						case Globals.EMAILLOGIN:
							/** 手机登陆 */
							startActivityForResult(new Intent(getApplicationContext(), WinActivity.class), SETPSW);
							break;
						case Globals.USERLOGIN:
							/** 用户名登陆 */
						case Globals.THIRDLOGIN:
							/** 第三方登陆 */
							if (GlobalUtil.isMobileNO(getIntent().getStringExtra("strphone"))) {
								/** 手机存在 */
								startActivityForResult(new Intent(getApplicationContext(), WinActivity.class).putExtra("win", "phone"), SETPSW);
								break;
							}
							if (GlobalUtil.isEmailNO(getIntent().getStringExtra("strphone"))) {
								/** 邮箱存在 */
								startActivityForResult(new Intent(getApplicationContext(), WinActivity.class).putExtra("win", "email"), SETPSW);
								break;
							}
							break;
						default:
							break;
						}
					} else {
						try {
							GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getJSONObject(Globals.ERROE).getString(Globals.MSG));
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			GlobalUtil.showToast(getApplicationContext(), Globals.MSG_WHAT_2);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SETPSW && resultCode == Activity.RESULT_OK && null != data) {
			setResult(Activity.RESULT_OK, new Intent());
			finish();
		}
	}
}
