package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.BaseFragment;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.RemSharepreUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.MainActivity;
import com.ncwcandroid.ui.view.communal.NewDialog;

/**
 * 
 * 手机注册页
 * 
 * @author Administrator
 * 
 */
public class PhoneRdtFragment extends BaseFragment implements OnClickListener, VolleyStringListener {
	private TextView txt_code, txt_verif, login;
	private EditText edt_phone, edt_pass, edt_rpass, edt_username, edt_rcode, edt_refcode;
	private CheckBox login_check;
	private Intent intent = new Intent();
	private String strphone, strcode, strpass, strrpass, strusername, str_refcode;
	private static int TIME = 1000;
	private int fag = 120;
	private Runnable runnable;

	protected View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragmentphone, null);
		login_check = (CheckBox) view.findViewById(R.id.login_check);
		edt_phone = (EditText) view.findViewById(R.id.edt_phone);
		edt_pass = (EditText) view.findViewById(R.id.edt_pass);
		edt_rcode = (EditText) view.findViewById(R.id.edt_rcode);
		edt_username = (EditText) view.findViewById(R.id.edt_username);
		edt_rpass = (EditText) view.findViewById(R.id.edt_rpass);
		edt_refcode = (EditText) view.findViewById(R.id.edt_refcode);
		txt_code = (TextView) view.findViewById(R.id.txt_code);
		txt_verif = (TextView) view.findViewById(R.id.txt_verif);
		login = (TextView) view.findViewById(R.id.login);
		edt_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (GlobalUtil.isMobileNO(edt_phone.getText().toString().trim())) {
					txt_verif.setEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		return view;
	}

	/**
	 * 手机注册
	 */
	public void onEvent() {
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getActivity());
		vtp.PhoneRdtData(getActivity(), strusername, strphone, strcode, strrpass, str_refcode);
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		strusername = edt_username.getText().toString().trim();
		strphone = edt_phone.getText().toString().trim();
		strcode = edt_rcode.getText().toString().trim();
		strpass = edt_pass.getText().toString().trim();
		strrpass = edt_rpass.getText().toString().trim();
		str_refcode = edt_refcode.getText().toString().trim();
		switch (arg0.getId()) {
		case R.id.login_check:
			intent.setClass(getActivity(), NewDialog.class).putExtra("type", "free");
			this.startActivityForResult(intent, Globals.REQUEST_CODE);
			break;

		case R.id.txt_code:
			Log.e("code", "获取区号");
			break;

		case R.id.txt_verif:
			sendMsg(strphone);
			break;
		case R.id.login:
			if (strusername.isEmpty()) {
				GlobalUtil.showToast(getActivity(), Globals.NOUSERNAME);
			} else if (strphone.isEmpty()) {
				GlobalUtil.showToast(getActivity(), Globals.NOPHONO);
			} else if (!GlobalUtil.isMobileNO(strphone)) {
				GlobalUtil.showToast(getActivity(), Globals.YESPHONO);
			} else if (strcode.isEmpty()) {
				GlobalUtil.showToast(getActivity(), Globals.NOCODE);
			} else if (strpass.isEmpty()) {
				GlobalUtil.showToast(getActivity(), Globals.NOPSAA);
			} else if (!strpass.equals(strrpass)) {
				GlobalUtil.showToast(getActivity(), Globals.NORPSAA);
			} else if (!login_check.isChecked()) {
				GlobalUtil.showToast(getActivity(), Globals.NOCHECK);
			} else {
				onEvent();
			}
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
		if (!GlobalUtil.isMobileNO(strphone)) {
			GlobalUtil.showToast(getActivity(), Globals.YESPHONO);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getActivity());
		vtp.sendMsg(getActivity(), mobile);
		vtp.setObjectList(this);
	}

	/**
	 * 手机注册
	 */
	public void initData() {
		login_check.setText(Html.fromHtml("<font size=\"3\" color=\"black\">我已阅读并同意</font><font size=\"3\" color=\"blue\">《你车我车用户协议》</font>"));
		login_check.setOnClickListener(this);
		txt_code.setOnClickListener(this);
		txt_verif.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Globals.RESULT_CODE) {
			login_check.setChecked(true);
		}
	}

	@Override
	public void ReturnVolleyString(String response, String type) throws JSONException {
		// TODO Auto-generated method stub

		JSONObject jso;
		if (type.equals(PostService.TYPE_SENDMSG)) {
			jso = new JSONObject(response);
			if (jso.getInt("code") == Globals.CODE) {
				try {
					if (!jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE).equals("")) {
						GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if (!jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE).equals("")) {
						/**
						 * 发送短信
						 */
						Log.e("moblie", jso.getJSONObject(Globals.DATAS).getString("mobile"));
						SharepreUtil.putStringValue(getActivity(), Globals.MOBILE, jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE));
						GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.MSG));
						MsgTimer();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		} else if (type.equals(PostService.TYPE_PHONEREGISTER)) {
			/**
			 * 手机注册
			 */
			jso = new JSONObject(response);
			if (jso.getInt("code") == Globals.CODE) {

				try {
					GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
					return;
				} catch (Exception e) {
					// TODO: handle exception
				}

				if (!jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE).equals("")) {
					JSONObject logine = jso.getJSONObject(Globals.DATAS);
					SharepreUtil.putStringValue(getActivity(), Globals.LOGINKEY, logine.getString(Globals.LOGINKEY));
					SharepreUtil.putStringValue(getActivity(), Globals.USERNAME, logine.getString(Globals.USERNAME));
					SharepreUtil.putStringValue(getActivity(), Globals.TRUENAME, logine.getString(Globals.TRUENAME));
					SharepreUtil.putStringValue(getActivity(), Globals.AVATAR, logine.getString(Globals.AVATAR));
					SharepreUtil.putStringValue(getActivity(), Globals.SEX, Globals.CON);

					SharepreUtil.putStringValue(getActivity(), Globals.ADDRESS, "");
					SharepreUtil.putStringValue(getActivity(), Globals.ADSCODE, "");
					SharepreUtil.putStringValue(getActivity(), Globals.ADSTURE, "");
					SharepreUtil.putStringValue(getActivity(), Globals.ADSMOB, "");

					SharepreUtil.putStringValue(getActivity(), Globals.MOBILE, logine.getString(Globals.MOBILE));
					SharepreUtil.putStringValue(getActivity(), Globals.EMAIL, logine.getString(Globals.EMAIL));
					SharepreUtil.putIntValue(getActivity(), Globals.LOGINTYPE, Globals.MOBILELONG);
					RemSharepreUtil.putStringValue(getActivity(), Globals.REMBERPSW, Globals.REMBERPSW);
					RemSharepreUtil.putStringValue(getActivity(), Globals.REMUSERNAME, logine.getString(Globals.MOBILE));
					RemSharepreUtil.putStringValue(getActivity(), Globals.REMPSW, strrpass);
					GlobalUtil.showToast(getActivity(), Globals.REGSUC);
					startActivity(new Intent(getActivity(), MainActivity.class));
					getActivity().finish();
				}

			}

		}

	}

}
