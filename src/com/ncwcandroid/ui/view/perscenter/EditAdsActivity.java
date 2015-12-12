package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;

/**
 * 增加地址页面
 * 
 * @author Administrator
 * 
 */
public class EditAdsActivity extends BaseActivity implements VolleyStringListener {
	private TextView txt_rdt;
	private EditText edt_username, edt_ads, edt_phone, edt_code;
	private String str_username, str_ads, str_phone, str_code;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addads);
		txt_rdt = (TextView) findViewById(R.id.txt_rdt);
		edt_username = (EditText) findViewById(R.id.edt_username);
		edt_ads = (EditText) findViewById(R.id.edt_ads);
		edt_phone = (EditText) findViewById(R.id.edt_phone);
		edt_code = (EditText) findViewById(R.id.edt_code);
		try {
			edt_username.setText(getIntent().getStringExtra("true_name"));
			edt_phone.setText(getIntent().getStringExtra("mob_phone"));
			edt_ads.setText(getIntent().getStringExtra("address"));
			edt_code.setText(getIntent().getStringExtra("zip_code"));
		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		txt_rdt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initMsg();
			}
		});
	}

	private void initMsg() {
		// TODO Auto-generated method stub
		str_username = edt_username.getText().toString().trim();
		str_ads = edt_ads.getText().toString().trim();
		str_phone = edt_phone.getText().toString().trim();
		str_code = edt_code.getText().toString().trim();
		if (!GlobalUtil.isMobileNO(str_phone)) {
			GlobalUtil.showToast(getApplicationContext(), Globals.YESPHONO);
			return;
		} else if (str_username.isEmpty()) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOUSERNAME);
			return;
		} else if (str_code.isEmpty()) {
			GlobalUtil.showToast(getApplicationContext(), Globals.RCODE);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.EditAds(EditAdsActivity.this, getIntent().getStringExtra(Globals.ADDRESSID), str_username, str_ads, str_phone, str_code);
		vtp.setObjectList(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		try {
			JSONObject jso = new JSONObject(response);
			if (jso.getInt("code") == Globals.CODE) {
				JSONObject json = jso.getJSONObject(Globals.DATAS);
				if (json.getInt("stauts") == 1) {
					// SharepreUtil.putStringValue(getApplicationContext(),
					// Globals.ADDRESSID, json.getString(Globals.ADDRESSID));
					GlobalUtil.showToast(getApplicationContext(), json.getString(Globals.MSG));
					if (getIntent().getStringExtra("is_default").equals("1")) {
						try {
							SharepreUtil.putStringValue(getApplicationContext(), Globals.ADDRESS, str_ads);
							SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSCODE, str_code);
							SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSTURE, str_username);
							SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSMOB, str_phone);
						} catch (Exception e) {
							// TODO: handle
							// exception
						}
					}
					finish();
				} else {
					GlobalUtil.showToast(getApplicationContext(), json.getString(Globals.ERROE));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			GlobalUtil.showToast(getApplicationContext(), Globals.MSG_WHAT_2);
		}
	}
}
