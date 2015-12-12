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
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.communal.SuccPswActivity;

/**
 * 个人设置里重置邮箱
 * 
 * @author Administrator
 * 
 */
public class SetEmailActivity extends BaseActivity implements VolleyStringListener, OnClickListener {
	private TextView txt_verif, txt_rdt, txt_top;
	private EditText edt_email, edt_rcode;
	private String str_email, str_rcode;
	private static int TIME = 1000;
	private int fag = 120;
	private Runnable runnable;
	/** 邮箱 */
	private final int SETEMA = 500;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setemail);
		txt_verif = (TextView) findViewById(R.id.txt_verif);
		txt_top = (TextView) findViewById(R.id.txt_top);
		txt_rdt = (TextView) findViewById(R.id.txt_rdt);
		edt_email = (EditText) findViewById(R.id.edt_email);
		edt_rcode = (EditText) findViewById(R.id.edt_rcode);
		try {
			if (getIntent().getStringExtra("stremail").equals("")) {
				txt_top.setText(R.string.modifemail);
			}
			edt_email.setText(getIntent().getStringExtra("stremail"));
			edt_email.setSelection(getIntent().getStringExtra("strmobile").length());
		} catch (Exception e) {
			// TODO: handle exception
		}
		txt_rdt.setOnClickListener(this);
		txt_verif.setOnClickListener(this);
	}

	/**
	 * 发送邮件
	 * 
	 * @param mobile
	 */
	private void sendMsg(String email) {
		// TODO Auto-generated method stub
		if (!GlobalUtil.isMobileNO(email)) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOEMAIL);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.sendEmail(SetEmailActivity.this, email);
		vtp.setObjectList(this);
	}

	private void initMsg() {
		if (!GlobalUtil.isEmailNO(str_email)) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOEMAIL);
		} else if (str_rcode.length() != 6) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOCODE);
		} else {

			VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
			vtp.SetEmail(SetEmailActivity.this, str_email, str_rcode);
			vtp.setObjectList(this);
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

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
					edt_email.setEnabled(false);
					txt_verif.setEnabled(false);
					txt_verif.setText(Integer.toString(fag--) + "秒");
					if (fag == 0) {
						edt_email.setEnabled(true);
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

			if (type.equals(PostService.TYPE_SENDEAMIL)) {
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
							MsgTimer();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			} else if (type.equals(PostService.MODPER)) {
				JSONObject jso = new JSONObject(response);
				if (jso.getInt("code") == Globals.CODE) {
					try {
						if (jso.getJSONObject(Globals.DATAS).getInt(Globals.STATUS) == 1) {
							SharepreUtil.putStringValue(getApplicationContext(), Globals.EMAIL, jso.getJSONObject(Globals.DATAS).getString(Globals.EMAIL));
							startActivityForResult(new Intent(getApplicationContext(), SuccPswActivity.class).putExtra("set", "setemail").putExtra("title", txt_top.getText().toString()), SETEMA);
						} else {
							try {
								GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
								SharepreUtil.putStringValue(getApplicationContext(), Globals.EMAIL, str_email);
								setResult(Activity.RESULT_OK, new Intent());
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
		if (requestCode == SETEMA && resultCode == Activity.RESULT_OK && null != data) {
			setResult(Activity.RESULT_OK, new Intent());
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		str_email = edt_email.getText().toString().trim();
		str_rcode = edt_rcode.getText().toString().trim();
		switch (v.getId()) {
		case R.id.txt_rdt:
			initMsg();
			break;
		case R.id.txt_verif:
			sendMsg(str_email);
			break;

		default:
			break;
		}
	}
}
