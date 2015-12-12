package com.ncwcandroid.ui.view.adreview;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.freetrial.ShouYeAppleyTryFalseInfo;
import com.ncwcandroid.ui.view.perscenter.AddAdsActivity;
import com.ncwcandroid.ui.view.perscenter.LoginActivity;

public class WangQiSurePlaceActivity extends Activity implements
		VolleyStringListener {

	private ImageView img_back_02;
	private EditText ed_sureplace_name, ed_sureplace_place, ed_sureplace_phone,
			ed_sureplace_youbian, ed_type, ed_by;
	private String member_id, try_id, key, name, place, phone, youbian, type,
			by;
	private Button btn_sureplace;
	private VolleyHttpUtil vhu;
	private int result;
	private String msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wangqi_sureplace);
		AppManager.getInstance().addActivity(WangQiSurePlaceActivity.this);
		img_back_02 = (ImageView) findViewById(R.id.img_back_02);
		ed_sureplace_name = (EditText) findViewById(R.id.ed_sureplace_name);
		ed_sureplace_place = (EditText) findViewById(R.id.ed_sureplace_place);
		ed_sureplace_phone = (EditText) findViewById(R.id.ed_sureplace_phone);
		ed_sureplace_youbian = (EditText) findViewById(R.id.ed_sureplace_youbian);
		ed_by = (EditText) findViewById(R.id.ed_by);
		ed_type = (EditText) findViewById(R.id.ed_type);
		btn_sureplace = (Button) findViewById(R.id.btn_sureplace);
		if (SharepreUtil.getStringValue(getApplicationContext(),
				Globals.ADDRESS, "").equals("")) {
			GlobalUtil.showToast(getApplicationContext(), Globals.PUTADS);
			startActivity(new Intent(getApplicationContext(),
					AddAdsActivity.class).putExtra("type", "syads"));
			return;
		} else {
			init();
		}

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// Log.e("@@@@@", "@@@@@");
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			ed_sureplace_name.setText(SharepreUtil.getStringValue(
					getApplicationContext(), Globals.ADSTURE, ""));
			ed_sureplace_place.setText(SharepreUtil.getStringValue(
					getApplicationContext(), Globals.ADDRESS, ""));
			ed_sureplace_phone.setText(SharepreUtil.getStringValue(
					getApplicationContext(), Globals.ADSMOB, ""));
			ed_sureplace_youbian.setText(SharepreUtil.getStringValue(
					getApplicationContext(), Globals.ADSCODE, ""));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void init() {
		vhu = VolleyHttpUtil.getInstance(this);
		member_id = SharepreUtil.getStringValue(this, Globals.MEMBERID, "0");
		try_id = getIntent().getStringExtra("id");
		key = SharepreUtil.getStringValue(this, Globals.LOGINKEY, "-1");
		btn_sureplace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = ed_sureplace_name.getText().toString();
				place = ed_sureplace_place.getText().toString();
				phone = ed_sureplace_phone.getText().toString();
				youbian = ed_sureplace_youbian.getText().toString();
				type = ed_type.getText().toString();
				by = ed_by.getText().toString();
				if (type.isEmpty()
						|| TextUtils.isEmpty(ed_sureplace_name.getText())
						|| TextUtils.isEmpty(ed_sureplace_place.getText())
						|| TextUtils.isEmpty(ed_sureplace_phone.getText())
						|| TextUtils.isEmpty(ed_sureplace_youbian.getText())) {
					Intent iss = new Intent(WangQiSurePlaceActivity.this,
							ShouYeAppleyTryFalseInfo.class);
					iss.putExtra("msg", Globals.UNFULL);
					startActivity(iss);
				} else if (name.length() > 10) {
					Intent issd = new Intent(WangQiSurePlaceActivity.this,
							ShouYeAppleyTryFalseInfo.class);
					issd.putExtra("msg", Globals.DAYUSHI);
					startActivity(issd);
				} else {
					gethttp(member_id, try_id, key, name, place, phone,
							youbian, type, by);

					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(1000);
								if (result == 1) {
									Intent is = new Intent(
											WangQiSurePlaceActivity.this,
											ShouYeAppleyTryFalseInfo.class);
									is.putExtra("msg", "确认成功");
									startActivity(is);
									finish();
								} else if (result == 100) {
									Toast.makeText(
											WangQiSurePlaceActivity.this, msg,
											Toast.LENGTH_SHORT).show();
									startActivity(new Intent(
											WangQiSurePlaceActivity.this,
											LoginActivity.class));
								} else {
									Intent iss = new Intent(
											WangQiSurePlaceActivity.this,
											ShouYeAppleyTryFalseInfo.class);
									iss.putExtra("msg", msg);
									startActivity(iss);
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			}
		});

		img_back_02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivity(new
				// Intent(WangQiSurePlaceActivity.this,WangQiProductActivity.class));
				finish();
			}
		});
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// Log.v("##############", response);
		try {
			JSONObject obj = new JSONObject(response);
			int code = obj.getInt("code");
			if (code == 200) {
				JSONObject some = obj.getJSONObject("datas");
				result = some.getInt("status");
				if (result != 1) {
					msg = some.getString("error");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void gethttp(String member_id, String try_id, String key,
			String name, String address, String phone, String youbian,
			String type, String by) {
		vhu.SurePlace(this, try_id, name, phone, address, youbian, type, by);
		vhu.setObjectList(this);
	}

}
