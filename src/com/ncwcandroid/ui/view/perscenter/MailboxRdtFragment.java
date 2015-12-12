package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.text.Html;
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
 * 邮箱注册
 * 
 * @author Administrator
 * 
 */
public class MailboxRdtFragment extends BaseFragment implements OnClickListener, VolleyStringListener {
	private TextView txt_code, login;
	private CheckBox login_check;
	private EditText edt_email, edt_rcode, edt_psw, edt_rpsw, edt_username, edt_refcode;
	private Intent intent = new Intent();
	private String stremail, strcode, strpass, strrpass, strusername, strrefcode;
	private static int TIME = 1000;
	private int fag = 120;
	private Runnable runnable;

	@Override
	protected View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragmentmailbox, null);
		login_check = (CheckBox) view.findViewById(R.id.login_check);
		edt_email = (EditText) view.findViewById(R.id.edt_email);
		edt_rcode = (EditText) view.findViewById(R.id.edt_rcode);
		edt_psw = (EditText) view.findViewById(R.id.edt_psw);
		edt_refcode = (EditText) view.findViewById(R.id.edt_refcode);
		edt_username = (EditText) view.findViewById(R.id.edt_username);
		edt_rpsw = (EditText) view.findViewById(R.id.edt_rpsw);
		txt_code = (TextView) view.findViewById(R.id.txt_code);
		login = (TextView) view.findViewById(R.id.login);
		login_check.setText(Html.fromHtml("<font size=\"3\" color=\"black\">我已阅读并同意</font><font size=\"3\" color=\"blue\">《你车我车用户协议》</font>"));
		login_check.setOnClickListener(this);
		edt_rcode.setOnClickListener(this);
		login.setOnClickListener(this);
		txt_code.setOnClickListener(this);
		return view;
	}

	public void initData() {

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
					txt_code.setEnabled(false);
					txt_code.setText(Integer.toString(fag--) + "秒");
					if (fag == 0) {
						txt_code.setEnabled(true);
						txt_code.setEnabled(true);
						txt_code.refreshDrawableState();
						txt_code.setText(R.string.tpl_get_verify_code);
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

	/**
	 * 发送邮件验证码
	 * 
	 * @param email
	 */
	private void sendMsg(String email) {
		if (!GlobalUtil.isEmailNO(email)) {
			GlobalUtil.showToast(getActivity(), Globals.NOEMAIL);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getActivity());
		vtp.sendEmail(getActivity(), email);
		vtp.setObjectList(this);
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
	public void onClick(View arg0) {
		strusername = edt_username.getText().toString().trim();
		stremail = edt_email.getText().toString().trim();
		strcode = edt_rcode.getText().toString().trim();
		strpass = edt_psw.getText().toString().trim();
		strrpass = edt_rpsw.getText().toString().trim();
		strrefcode = edt_refcode.getText().toString();
		switch (arg0.getId()) {
		case R.id.login_check:
			intent.setClass(getActivity(), NewDialog.class).putExtra("type", "free");
			this.startActivityForResult(intent, Globals.REQUEST_CODE);
			break;
		case R.id.edt_rcode:
			Log.e("code", "获取区号");
			break;
		case R.id.txt_code:
			sendMsg(stremail);
			break;
		case R.id.login:
			if (strusername.isEmpty())
				GlobalUtil.showToast(getActivity(), Globals.NOUSERNAME);
			else if (stremail.isEmpty())
				GlobalUtil.showToast(getActivity(), Globals.YESEMAIL);
			else if (!GlobalUtil.isEmailNO(stremail))
				GlobalUtil.showToast(getActivity(), Globals.NOEMAIL);
			else if (strcode.isEmpty())
				GlobalUtil.showToast(getActivity(), Globals.NOCODE);
			else if (strpass.isEmpty())
				GlobalUtil.showToast(getActivity(), Globals.NOPSAA);
			else if (!strpass.equals(strrpass))
				GlobalUtil.showToast(getActivity(), Globals.NORPSAA);
			else if (!login_check.isChecked())
				GlobalUtil.showToast(getActivity(), Globals.NOCHECK);
			else
				initMsg();
			break;

		}
	}

	private void initMsg() {
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getActivity());
		vtp.MailBoxRdtData(getActivity(), strusername, strcode, strrpass, stremail, strrefcode);
		vtp.setObjectList(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		try {
			JSONObject jso;
			if (type.equals(PostService.TYPE_SENDEAMIL)) {
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
						if (!jso.getJSONObject(Globals.DATAS).getString(Globals.MSG).equals("")) {
							/** 发送邮件 */
							GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.MSG));
							MsgTimer();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			} else if (type.equals(PostService.TYPE_MOBILREGISTER)) {
				jso = new JSONObject(response);
				if (jso.getInt("code") == Globals.CODE) {
					try {
						GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
						return;
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (!jso.getJSONObject(Globals.DATAS).getString(Globals.EMAIL).equals("")) {
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
						SharepreUtil.putIntValue(getActivity(), Globals.LOGINTYPE, Globals.EMAILLOGIN);
						SharepreUtil.putStringValue(getActivity(), Globals.MOBILE, logine.getString(Globals.MOBILE));
						SharepreUtil.putStringValue(getActivity(), Globals.EMAIL, logine.getString(Globals.EMAIL));
						RemSharepreUtil.putStringValue(getActivity(), Globals.REMBERPSW, Globals.REMBERPSW);
						RemSharepreUtil.putStringValue(getActivity(), Globals.REMUSERNAME, logine.getString(Globals.MOBILE));
						RemSharepreUtil.putStringValue(getActivity(), Globals.REMPSW, strrpass);
						GlobalUtil.showToast(getActivity(), Globals.REGSUC);
						startActivity(new Intent(getActivity(), MainActivity.class));
						getActivity().finish();
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			GlobalUtil.showToast(getActivity(), Globals.MSG_WHAT_2);
		}
	}

}
