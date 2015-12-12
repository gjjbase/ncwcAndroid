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
import com.ncwcandroid.ui.util.SharepreUtil;

/**
 * 邮箱重置重置密码页面
 * 
 * @author Administrator
 * 
 */
public class SetPswEmailActivity extends BaseActivity implements OnClickListener, VolleyStringListener {
	private TextView txt_rdt, txt_fidtxt;
	private EditText edt_email, edt_code;
	private String stremail, str_code;
	private final int SETPSW = 300;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setpswemail);
		AppManager.getInstance().addActivity(SetPswEmailActivity.this);
		txt_rdt = (TextView) findViewById(R.id.txt_rdt);
		edt_email = (EditText) findViewById(R.id.edt_email);
		edt_code = (EditText) findViewById(R.id.edt_code);
		txt_fidtxt = (TextView) findViewById(R.id.txt_fidtxt);
		edt_email.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.EMAIL, ""));
		txt_rdt.setOnClickListener(this);
		txt_fidtxt.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		stremail = edt_email.getText().toString().trim();
		str_code = edt_code.getText().toString().trim();
		switch (arg0.getId()) {
		case R.id.txt_rdt:
			if (GlobalUtil.isEmailNO(stremail)) {
				GlobalUtil.showToast(getApplicationContext(), Globals.NOEMAIL);
			} else if (str_code.length() != 6) {
				GlobalUtil.showToast(getApplicationContext(), Globals.NOCODE);
			} else {
				startActivityForResult(new Intent(getApplicationContext(), SetPswEnterActivity.class).putExtra("strphone", stremail).putExtra("str_code", str_code), SETPSW);
			}
			break;
		case R.id.txt_fidtxt:
			sendMsg(stremail);
			break;
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
	}

	/**
	 * 发送邮箱验证码
	 * 
	 * @param mobile
	 */
	private void sendMsg(String email) {
		// TODO Auto-generated method stub
		if (GlobalUtil.isEmailNO(email)) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOEMAIL);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.sendEmail(SetPswEmailActivity.this, email);
		vtp.setObjectList(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		try {
			JSONObject jso = new JSONObject(response);

			if (jso.getInt("code") == Globals.CODE) {

				try {
					if (!jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE).equals("")) {
						GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if (!jso.getJSONObject(Globals.DATAS).getString(Globals.MSG).equals("")) {
						/* 发送邮件 */
						GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.MSG));
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
