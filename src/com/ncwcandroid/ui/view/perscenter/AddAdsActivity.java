package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONException;
import org.json.JSONObject;

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
 * 增加地址页面
 * 
 * @author Administrator
 * 
 */
public class AddAdsActivity extends BaseActivity implements VolleyStringListener {
	private TextView txt_rdt;
	private EditText edt_username, edt_ads, edt_phone, edt_code;
	private String str_username, str_ads, str_phone, str_code;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(AddAdsActivity.this);
		setContentView(R.layout.activity_addads);
		txt_rdt = (TextView) findViewById(R.id.txt_rdt);
		edt_username = (EditText) findViewById(R.id.edt_username);
		edt_ads = (EditText) findViewById(R.id.edt_ads);
		edt_phone = (EditText) findViewById(R.id.edt_phone);
		edt_code = (EditText) findViewById(R.id.edt_code);
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
		} else if (str_code.length() != 6) {
			GlobalUtil.showToast(getApplicationContext(), Globals.RCODE);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.AddAds(AddAdsActivity.this, str_username, str_ads, str_phone, str_code);
		vtp.setObjectList(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) throws JSONException {
		// TODO Auto-generated method stub

		JSONObject jso = new JSONObject(response);
		if (jso.getInt("code") == Globals.CODE) {
			JSONObject json = jso.getJSONObject(Globals.DATAS);
			try {
				SharepreUtil.putStringValue(getApplicationContext(), Globals.ADDRESSID, json.getString(Globals.ADDRESSID));
				try {
					if (getIntent().getStringExtra("type").equals("syads")) {
						VolleyHttpUtil vtp = new VolleyHttpUtil(getApplicationContext());
						vtp.DefAds(AddAdsActivity.this, json.getString(Globals.ADDRESSID));
						vtp.setObjectList(new VolleyStringListener() {

							@Override
							public void ReturnVolleyString(String response, String type) {
								// TODO Auto-generated method stub
								try {
									JSONObject jso = new JSONObject(response);
									if (jso.getInt("code") == Globals.CODE) {
										if (jso.getJSONObject(Globals.DATAS).getInt(Globals.STATUS) == 1) {
											GlobalUtil.showToast(getApplicationContext(), "设置成功");
											try {
												SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSTURE, str_username);
												SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSMOB, str_phone);
												SharepreUtil.putStringValue(getApplicationContext(), Globals.ADDRESS, str_ads);
												SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSCODE, str_code);
											} catch (Exception e) {
												// TODO: handle
												// exception
											}
										} else {
											GlobalUtil.showToast(getApplicationContext(), "设置失败");
										}
									}
								} catch (Exception e) {
									// TODO: handle exception
								}

							}
						});
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.MSG));
				finish();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}
}
