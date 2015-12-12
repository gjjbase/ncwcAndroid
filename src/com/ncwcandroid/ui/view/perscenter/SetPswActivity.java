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
 * 手机重置密码页面
 * 
 * @author Administrator
 * 
 */
public class SetPswActivity extends BaseActivity implements OnClickListener, VolleyStringListener {
	private TextView txt_rdt, txt_fidtxt;
	private EditText edt_phone, edt_putcode;
	private String strphone, str_putcode;
	private final int SETPSW = 300;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(SetPswActivity.this);
		setContentView(R.layout.activity_setpsw);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		strphone = edt_phone.getText().toString().trim();
		str_putcode = edt_putcode.getText().toString().trim();
		switch (arg0.getId()) {
		case R.id.txt_rdt:
			if (!GlobalUtil.isMobileNO(strphone)) {
				GlobalUtil.showToast(getApplicationContext(), Globals.YESPHONO);
			} else if (str_putcode.isEmpty() || str_putcode.length() != 6) {
				GlobalUtil.showToast(getApplicationContext(), Globals.NOCODE);
			} else {
				startActivityForResult(new Intent(getApplicationContext(), SetPswEnterActivity.class).putExtra("strphone", strphone).putExtra("str_code", str_putcode).putExtra("type", Globals.MOBILELONG), SETPSW);
			}
			break;
		case R.id.txt_fidtxt:
			sendMsg(strphone);
			break;
		}
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 */
	private void sendMsg(String mobile) {
		// TODO Auto-generated method stub
		if (!GlobalUtil.isMobileNO(mobile)) {
			GlobalUtil.showToast(getApplicationContext(), Globals.YESPHONO);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.sendMsg(SetPswActivity.this, mobile);
		vtp.setObjectList(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		AppManager.getInstance().addActivity(SetPswActivity.this);
		txt_rdt = (TextView) findViewById(R.id.txt_rdt);
		txt_fidtxt = (TextView) findViewById(R.id.txt_fidtxt);
		edt_phone = (EditText) findViewById(R.id.edt_phone);
		edt_putcode = (EditText) findViewById(R.id.edt_putcode);
		edt_phone.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.MOBILE, ""));
		txt_rdt.setOnClickListener(this);
		txt_fidtxt.setOnClickListener(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		try {
			JSONObject jso = new JSONObject(response);
			if (jso.getInt("code") == Globals.CODE) {
				try {
					if (!jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE).equals("")) {
						/* 发送短信 */
						SharepreUtil.putStringValue(getApplicationContext(), Globals.MOBILE, jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE));
						GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.MSG));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
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
