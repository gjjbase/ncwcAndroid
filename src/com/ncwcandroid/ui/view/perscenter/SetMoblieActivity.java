package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.communal.SuccPswActivity;

/**
 * 个人设置里重置手机号
 * 
 * @author Administrator
 * 
 */
public class SetMoblieActivity extends BaseActivity implements VolleyStringListener, OnClickListener {
	private TextView txt_rdt, txt_verif, txt_top;
	private EditText edt_phone, edt_rcode;
	private String str_phone, str_code;
	private static int TIME = 1000;
	private int fag = 120;
	private Runnable runnable;
	/** 手机号 */
	private final int SETMOB = 400;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(SetMoblieActivity.this);
		setContentView(R.layout.activity_setmobile);
		txt_rdt = (TextView) findViewById(R.id.txt_rdt);
		txt_verif = (TextView) findViewById(R.id.txt_verif);
		txt_top = (TextView) findViewById(R.id.txt_top);
		edt_phone = (EditText) findViewById(R.id.edt_phone);
		edt_rcode = (EditText) findViewById(R.id.edt_rcode);
		try {
			if (getIntent().getStringExtra("strmobile").isEmpty()) {
				txt_top.setText(R.string.bindphone);
			}
			edt_phone.setText(getIntent().getStringExtra("strmobile"));
			edt_phone.setSelection(getIntent().getStringExtra("strmobile").length());
		} catch (Exception e) {
			// TODO: handle exception
		}
		txt_rdt.setOnClickListener(this);
		txt_verif.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	private void initMsg() {
		// TODO Auto-generated method stub
		if (!GlobalUtil.isMobileNO(str_phone)) {
			GlobalUtil.showToast(getApplicationContext(), Globals.YESPHONO);
		} else if (str_code.length() != 6) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOCODE);
		} else {
			VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
			vtp.SetMobile(SetMoblieActivity.this, str_phone, str_code);
			vtp.setObjectList(this);
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
		if (str_phone.equals(SharepreUtil.getStringValue(getApplicationContext(), Globals.MOBILE, Globals.ERROE))) {
			GlobalUtil.showToast(getApplicationContext(), "手机号没有变化");
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.sendMsg(SetMoblieActivity.this, mobile);
		vtp.setObjectList(this);
	}

	/**
	 * 计时器
	 */
	public void MsgTimer() {
		final Handler handler = new Handler();
		runnable = new Runnable() {

			@Override
			public void run() {
				// handler自带方法实现定时器
				try {
					handler.postDelayed(this, TIME);
					edt_phone.setEnabled(false);
					txt_verif.setEnabled(false);
					txt_verif.setText(Integer.toString(fag--) + "秒");
					if (fag == 0) {
						edt_phone.setEnabled(true);
						txt_verif.setEnabled(true);
						txt_verif.refreshDrawableState();
						txt_verif.setText(R.string.tpl_get_verify_code);
						fag = 120;
						handler.removeCallbacks(runnable);
					}
					System.out.println("do...");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("exception...");
				}
			}
		};
		handler.postDelayed(runnable, TIME);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		try {
			JSONObject jso;
			if (type.equals(PostService.TYPE_SENDMSG)) {
				jso = new JSONObject(response);
				if (jso.getInt("code") == Globals.CODE) {
					try {
						if (!jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE).equals("")) {
							GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						if (!jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE).equals("")) {
							/**
							 * 发送短信
							 */
							SharepreUtil.putStringValue(getApplicationContext(), Globals.MOBILE, jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE));
							GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.MSG));
							MsgTimer();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			} else if (type.equals(PostService.MODPER)) {
				jso = new JSONObject(response);
				if (jso.getInt("code") == Globals.CODE) {
					try {
						if (jso.getJSONObject(Globals.DATAS).getInt(Globals.STATUS) == 1) {
							SharepreUtil.putStringValue(getApplicationContext(), Globals.MOBILE, jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE));
							startActivityForResult(new Intent(getApplicationContext(), SuccPswActivity.class).putExtra("set", "setmobile").putExtra("title", txt_top.getText().toString()), SETMOB);
						} else {
							try {
								GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						GlobalUtil.showToast(getApplicationContext(), Globals.MSG_WHAT_2);
					}
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
		if (requestCode == SETMOB && resultCode == Activity.RESULT_OK && null != data) {
			setResult(Activity.RESULT_OK, new Intent());
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		str_phone = edt_phone.getText().toString().trim();
		str_code = edt_rcode.getText().toString().trim();
		switch (v.getId()) {
		case R.id.txt_verif:
			sendMsg(str_phone);
			break;
		case R.id.txt_rdt:
			initMsg();
			break;
		default:

			break;
		}
	}
}
