package com.ncwcandroid.ui.view.perscenter;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.RemSharepreUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.MainActivity;
import com.ncwcandroid.ui.widget.FlippingLoadingDialog;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements OnClickListener, Callback, PlatformActionListener, VolleyStringListener {
	private static final int MSG_SMSSDK_CALLBACK = 1;
	private static final int MSG_AUTH_CANCEL = 2;
	private static final int MSG_AUTH_ERROR = 3;
	private static final int MSG_AUTH_COMPLETE = 4;
	private EditText edt_pasw, edt_username;
	private CheckBox login_check;
	private String username = "", password = "";
	private FlippingLoadingDialog flippingload;
	private ImageView tvWeixin, tvWeibo, Qq;
	private TextView login, txt_rdt, txt_fidtxt;
	/** 获取三方登陆中返回的数据 */
	private Platform platform;
	private Handler handler;
	private JSONObject jso, logine;
	private int sexnum = Globals.SEXCON;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(LoginActivity.this);
		RemSharepreUtil.putStringValue(getApplicationContext(), Globals.LOGINSUC, Globals.LOGINSUC);
		setContentView(R.layout.login);
		tvWeixin = (ImageView) findViewById(R.id.tvWeixin);
		tvWeibo = (ImageView) findViewById(R.id.tvWeibo);
		Qq = (ImageView) findViewById(R.id.Qq);

		login = (TextView) findViewById(R.id.login);
		txt_rdt = (TextView) findViewById(R.id.txt_rdt);
		txt_fidtxt = (TextView) findViewById(R.id.txt_fidtxt);

		edt_pasw = (EditText) findViewById(R.id.edt_pasw);
		edt_username = (EditText) findViewById(R.id.edt_username);
		login_check = (CheckBox) findViewById(R.id.login_check);

		tvWeixin.setOnClickListener(this);
		tvWeibo.setOnClickListener(this);
		Qq.setOnClickListener(this);

		login.setOnClickListener(this);
		txt_rdt.setOnClickListener(this);
		txt_fidtxt.setOnClickListener(this);
		if (RemSharepreUtil.getStringValue(getApplicationContext(), Globals.REMBERPSW, Globals.NOREMBERPSW).equals(Globals.REMBERPSW)) {
			edt_username.setText(RemSharepreUtil.getStringValue(getApplicationContext(), Globals.REMUSERNAME, ""));
			edt_username.setSelection(RemSharepreUtil.getStringValue(getApplicationContext(), Globals.REMUSERNAME, "").length());
			edt_pasw.setText(RemSharepreUtil.getStringValue(getApplicationContext(), Globals.REMPSW, ""));
			login_check.setChecked(true);
		}
		flippingload = new FlippingLoadingDialog(LoginActivity.this);
		handler = new Handler(this);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (username.isEmpty()) {
			if (RemSharepreUtil.getStringValue(getApplicationContext(), Globals.REMBERPSW, Globals.NOREMBERPSW).equals(Globals.REMBERPSW)) {
				edt_username.setText(RemSharepreUtil.getStringValue(getApplicationContext(), Globals.REMUSERNAME, ""));
				edt_username.setSelection(RemSharepreUtil.getStringValue(getApplicationContext(), Globals.REMUSERNAME, "").length());
				edt_pasw.setText(RemSharepreUtil.getStringValue(getApplicationContext(), Globals.REMPSW, ""));
				login_check.setChecked(true);
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvWeixin: {
			/* 微信登录,打包签名apk,然后才能产生微信的登录 */
			flippingload.show();
			authorize(ShareSDK.getPlatform(Wechat.NAME));
		}
			break;
		case R.id.tvWeibo: {
			/* 新浪微博 ,打包签名的apk，才能授权 */
			flippingload.show();
			authorize(ShareSDK.getPlatform(SinaWeibo.NAME));
		}
			break;
		case R.id.Qq: {
			/* QQ */
			Log.e("start", "***************");
			// GlobalUtil.showToast(getApplicationContext(), Globals.NOMSG);
			flippingload.show();
			authorize(ShareSDK.getPlatform(QQ.NAME));
		}
			break;
		case R.id.login: {
			initview();
		}
			break;
		case R.id.txt_rdt: {
			startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
		}
			break;
		case R.id.txt_fidtxt: {
			startActivity(new Intent(getApplicationContext(), FindAcitivty.class));
		}
			break;

		}
	}

	// 执行授权,获取用户信息
	private void authorize(Platform plat) {
		if (plat == null) {
			return;
		}
		plat.setPlatformActionListener(this);
		// 关闭SSO授权
		plat.SSOSetting(false);
		plat.showUser(null);
	}

	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			Message msg = new Message();
			msg.what = MSG_AUTH_COMPLETE;
			msg.obj = new Object[] { platform.getName(), res };
			handler.sendMessage(msg);
			if (flippingload != null) {
				flippingload.dismiss();
			}
		}
	}

	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_ERROR);
		}
		t.printStackTrace();
	}

	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_CANCEL);
		}
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_AUTH_CANCEL: {
			// 取消授权
			Toast.makeText(getApplicationContext(), R.string.auth_cancel, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_ERROR: {
			// 授权失败
			Toast.makeText(getApplicationContext(), R.string.auth_error, Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_COMPLETE: {
			// 授权成功
			Log.e("end", "***************");
			SharepreUtil.putStringValue(getApplicationContext(), Globals.PLATFROM, (String) (((Object[]) msg.obj)[0]));
			platform = ShareSDK.getPlatform(SharepreUtil.getStringValue(LoginActivity.this, Globals.PLATFROM, ""));
			if (platform != null) {
				if (platform.getDb().getUserGender().equals("m")) {
					/** 性别设置 */
					sexnum = Globals.SEXMAN;
				} else {
					sexnum = Globals.SEXWOMEN;
				}
			}
			String url = platform.getDb().getUserIcon();

			VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
			vtp.ThirdLogin(LoginActivity.this, platform.getDb().getUserId(), platform.getDb().getUserName(), sexnum, url);
			vtp.setObjectList(this);
		}
			break;
		case MSG_SMSSDK_CALLBACK:
			break;
		}
		return false;
	}

	public void initview() {
		username = edt_username.getText().toString().trim();
		password = edt_pasw.getText().toString().trim();
		if (username.isEmpty()) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NONAME);
			return;
		}
		if (password.isEmpty()) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOPSAA);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		try {
			vtp.Login(LoginActivity.this, username, password);
			vtp.setObjectList(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ReturnVolleyString(String response, String type) throws JSONException {
		// TODO Auto-generated method stub
		Log.e("@@@@@", response);
		/** 邮箱登录 */

		if (GlobalUtil.isEmailNO(username)) {
			SharepreUtil.putIntValue(getApplicationContext(), Globals.LOGINTYPE, Globals.EMAILLOGIN);
		}
		/** 手机登录 */
		else if (GlobalUtil.isMobileNO(username)) {
			SharepreUtil.putIntValue(getApplicationContext(), Globals.LOGINTYPE, Globals.MOBILELONG);
		} else {
			/** 用户名登陆 */
			SharepreUtil.putIntValue(getApplicationContext(), Globals.LOGINTYPE, Globals.USERLOGIN);
		}

		if (type.equals(PostService.THRID_TYPE)) {
			jso = new JSONObject(response);
			logine = jso.getJSONObject(Globals.DATAS);
			SharepreUtil.putIntValue(getApplicationContext(), Globals.LOGINTYPE, Globals.THIRDLOGIN);
		} else if (type.equals(PostService.TYPE_LOGIN)) {
			jso = new JSONObject(response);
			logine = jso.getJSONObject(Globals.DATAS);
		}
		if (jso.getInt("code") == Globals.CODE) {

			try {
				/** 登陆错误 */
				GlobalUtil.showToast(getApplicationContext(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
				return;

			} catch (Exception e) {
				// TODO: handle exception
			}
			SharepreUtil.putStringValue(getApplicationContext(), Globals.LOGINKEY, logine.getString(Globals.LOGINKEY));
			SharepreUtil.putStringValue(getApplicationContext(), Globals.USERNAME, logine.getString(Globals.USERNAME));
			SharepreUtil.putStringValue(getApplicationContext(), Globals.MEMBERID, logine.getString(Globals.MEMBERID));
			SharepreUtil.putStringValue(getApplicationContext(), Globals.AVATAR, logine.getString(Globals.AVATAR));
			int sexnum = Globals.SEXCON;
			if (logine.getString(Globals.SEX).equals("")) {

			} else {
				sexnum = Integer.parseInt(logine.getString(Globals.SEX));
			}

			switch (sexnum) {
			case Globals.SEXCON:
				SharepreUtil.putStringValue(getApplicationContext(), Globals.SEX, Globals.CON);
				break;
			case Globals.SEXMAN:
				SharepreUtil.putStringValue(getApplicationContext(), Globals.SEX, Globals.MAN);
				break;
			case Globals.SEXWOMEN:
				SharepreUtil.putStringValue(getApplicationContext(), Globals.SEX, Globals.WOMEN);
				break;

			default:

				break;
			}
			SharepreUtil.putIntValue(getApplicationContext(), Globals.NUMSEX, sexnum);
			SharepreUtil.putStringValue(getApplicationContext(), Globals.MOBILE, logine.getString(Globals.MOBILE));
			SharepreUtil.putStringValue(getApplicationContext(), Globals.EMAIL, logine.getString(Globals.EMAIL));
			try {
				SharepreUtil.putStringValue(getApplicationContext(), Globals.ADDRESS, logine.getJSONObject(Globals.ADDRESS).getString(Globals.ADSINFO) + logine.getJSONObject(Globals.ADDRESS).getString(Globals.ADDRESS));
				SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSCODE, logine.getJSONObject(Globals.ADDRESS).getString(Globals.ZIPCODE));
				SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSTURE, logine.getJSONObject(Globals.ADDRESS).getString(Globals.TRNAME));
				SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSMOB, logine.getJSONObject(Globals.ADDRESS).getString(Globals.ADSMOB));
			} catch (Exception e) {
				// TODO: handle exception
			}

			SharepreUtil.putStringValue(getApplicationContext(), Globals.TRUENAME, logine.getString(Globals.TRUENAME).toString());
			SharepreUtil.putStringValue(getApplicationContext(), Globals.PSWD, password);
			RemSharepreUtil.putStringValue(getApplicationContext(), Globals.REMUSERNAME, username);
			RemSharepreUtil.putStringValue(getApplicationContext(), Globals.REMPSW, password);
			if (login_check.isChecked()) {
				RemSharepreUtil.putStringValue(getApplicationContext(), Globals.REMBERPSW, Globals.REMBERPSW);
			} else {
				RemSharepreUtil.putStringValue(getApplicationContext(), Globals.REMBERPSW, Globals.NOREMBERPSW);
			}
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		if (flippingload != null) {
			flippingload.dismiss();
		}
	}
}
