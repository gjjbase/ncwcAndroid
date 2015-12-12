package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.ncwcandroid.ui.view.communal.WinActivity;

/**
 * 手机重置
 * 
 * @author Administrator
 * 
 */
public class FindPhoneFragment extends BaseFragment implements VolleyStringListener, OnClickListener {
	private TextView login, txt_verif;
	private EditText edt_phone, edt_rcode;
	private EditText pass, rpass;
	private String phone, rcode, strpass, strrpass;
	private static int TIME = 1000;
	private int fag = 120;
	private Runnable runnable;

	@SuppressLint("InflateParams")
	@Override
	protected View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_findphone, null);
		login = (TextView) view.findViewById(R.id.login);
		txt_verif = (TextView) view.findViewById(R.id.txt_verif);
		edt_phone = (EditText) view.findViewById(R.id.edt_phone);
		edt_rcode = (EditText) view.findViewById(R.id.edt_rcode);
		pass = (EditText) view.findViewById(R.id.pass);
		rpass = (EditText) view.findViewById(R.id.rpass);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		login.setOnClickListener(this);
		txt_verif.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		phone = edt_phone.getText().toString().trim();
		rcode = edt_rcode.getText().toString().trim();
		strpass = pass.getText().toString().trim();
		strrpass = rpass.getText().toString().trim();
		switch (arg0.getId()) {
		case R.id.login:
			// TODO Auto-generated method stub
			if (!GlobalUtil.isMobileNO(phone)) {
				GlobalUtil.showToast(getActivity(), Globals.YESPHONO);
			} else if (rcode.isEmpty()) {
				GlobalUtil.showToast(getActivity(), Globals.NOCODE);
			} else if (strpass.isEmpty()) {
				GlobalUtil.showToast(getActivity(), Globals.NOPSAA);
			} else if (!strrpass.equals(strrpass)) {
				GlobalUtil.showToast(getActivity(), Globals.NORPSAA);
			} else {
				initMsg();
			}

			break;
		case R.id.txt_verif:
			sendMsg(phone);
			break;

		}
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

	/**
	 * 验证是否成功
	 */
	private void initMsg() {
		// TODO Auto-generated method stub
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getActivity());
		vtp.ResetPswd(getActivity(), rcode, strpass, strrpass, phone);
		vtp.setObjectList(this);
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 */
	private void sendMsg(String mobile) {
		// TODO Auto-generated method stub
		if (!GlobalUtil.isMobileNO(mobile)) {
			GlobalUtil.showToast(getActivity(), Globals.YESPHONO);
			return;
		}
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getActivity());
		vtp.sendMsg(getActivity(), mobile);
		vtp.setObjectList(this);
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
							GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						if (!jso.getJSONObject(Globals.DATAS).getString(Globals.MOBILE).equals("")) {
							/* 发送短信 */
							GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.MSG));
							MsgTimer();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			} else if (type.equals(PostService.TYPE_RESET)) {
				/* 修改密码 */
				jso = new JSONObject(response);
				if (jso.getInt("code") == Globals.CODE) {
					try {
						if (jso.getJSONObject(Globals.DATAS).getJSONObject(Globals.ERROE).getBoolean(Globals.STATUS)) {
							RemSharepreUtil.putStringValue(getActivity(), Globals.REMUSERNAME, phone);
							RemSharepreUtil.putStringValue(getActivity(), Globals.REMPSW, strrpass);
							Intent intent = new Intent();
							intent.setClass(getActivity(), WinActivity.class);
							intent.putExtra("win", "phone");
							getActivity().startActivity(intent);
							getActivity().finish();
						} else {
							try {
								GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getJSONObject(Globals.ERROE).getString(Globals.MSG));
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			GlobalUtil.showToast(getActivity(), Globals.MSG_WHAT_2);
		}
	}

}
