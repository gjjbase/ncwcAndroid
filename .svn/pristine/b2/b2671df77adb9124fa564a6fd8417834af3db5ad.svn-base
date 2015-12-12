package com.ncwcandroid.ui.view.perscenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.DialogListener;
import com.ncwcandroid.ui.interfac.SelectReturn;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.CameraManager;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.ImageTools;
import com.ncwcandroid.ui.util.ScreenUtils;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.widget.CircularImage;
import com.ncwcandroid.ui.widget.DialogSelect;
import com.ncwcandroid.ui.widget.UpDataDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 个人信息设置主页
 * 
 * @author Administrator
 * 
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class PerSetActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout set_sex, set_ads, set_psw, set_moblie, set_email;
	private LinearLayout set_username;
	private CircularImage set_img;
	private EditText edt_nickname;
	private Button txt_finish;
	private TextView txt_mobile, txt_email, txt_sex, txt_ads, txt_psw, txt_enter;
	private String strmobile, stremail, strsex, strusername;
	/** 性别 */
	private final int SETSEX = 100;
	/** 地址 */
	private final int SETADS = 200;
	/** 密码 */
	private final int SETPSW = 300;
	/** 手机号 */
	private final int SETMOB = 400;
	/** 邮箱 */
	private final int SETEMA = 500;
	private Uri uri;
	private File outFile;
	private Bitmap bitmap = null;
	private ImageView txt_backg;
	private List<Bitmap> listbitmap;
	public static int SELECSEX = 3;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perset);
		AppManager.getInstance().addActivity(PerSetActivity.this);
		set_sex = (RelativeLayout) findViewById(R.id.set_sex);
		set_ads = (RelativeLayout) findViewById(R.id.set_ads);
		set_psw = (RelativeLayout) findViewById(R.id.set_psw);
		set_moblie = (RelativeLayout) findViewById(R.id.set_moblie);
		set_email = (RelativeLayout) findViewById(R.id.set_email);
		set_username = (LinearLayout) findViewById(R.id.set_username);
		set_img = (CircularImage) findViewById(R.id.set_img);
		edt_nickname = (EditText) findViewById(R.id.edt_nickname);
		txt_mobile = (TextView) findViewById(R.id.txt_mobile);
		txt_email = (TextView) findViewById(R.id.txt_email);
		txt_sex = (TextView) findViewById(R.id.txt_sex);
		txt_ads = (TextView) findViewById(R.id.txt_ads);
		txt_psw = (TextView) findViewById(R.id.txt_psw);
		txt_enter = (TextView) findViewById(R.id.txt_enter);
		txt_backg = (ImageView) findViewById(R.id.txt_backg);
		txt_finish = (Button) findViewById(R.id.txt_finish);
		set_sex.setOnClickListener(this);
		txt_finish.setOnClickListener(this);
		set_img.setOnClickListener(this);
		set_psw.setOnClickListener(this);
		set_moblie.setOnClickListener(this);
		set_username.setOnClickListener(this);
		set_ads.setOnClickListener(this);
		set_email.setOnClickListener(this);
		txt_enter.setOnClickListener(this);
		txt_ads.setOnClickListener(this);
		txt_email.setOnClickListener(this);
		txt_mobile.setOnClickListener(this);
		File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
		listbitmap = new ArrayList<Bitmap>();
		edt_nickname.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.TRUENAME, ""));
		edt_nickname.setSelection(edt_nickname.getText().length());
		try {
			txt_ads.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.ADDRESS, ""));
		} catch (Exception e) {
			// TODO: handle exception
		}
		txt_sex.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.SEX, ""));
		txt_email.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.EMAIL, ""));
		txt_mobile.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.MOBILE, ""));
		txt_psw.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.PSWD, ""));
		txt_psw.setText("");
		AsyncLoadingPicture.getInstance(getApplicationContext()).LoadPicture(SharepreUtil.getStringValue(getApplicationContext(), Globals.AVATAR, ""), set_img);
		Bitmap bitmap = ImageLoader.getInstance().loadImageSync(SharepreUtil.getStringValue(getApplicationContext(), Globals.AVATAR, ""));
		if (bitmap != null) {
			txt_backg.setBackground(new BitmapDrawable(getResources(), bitmap));
		} else {
			txt_backg.setBackground(getResources().getDrawable(R.color.bg_color));
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		applyBlur();
	}

	private void applyBlur() {
		txt_backg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@SuppressLint("NewApi")
			@Override
			public boolean onPreDraw() {
				txt_backg.getViewTreeObserver().removeOnPreDrawListener(this);
				txt_backg.buildDrawingCache();
				Bitmap bmp = txt_backg.getDrawingCache();
				ImageTools.blur(getApplicationContext(), bmp, txt_backg);
				return true;
			}
		});
	}

	@SuppressLint("InlinedApi")
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// onRegiste();
		strmobile = txt_mobile.getText().toString().trim();
		stremail = txt_email.getText().toString().trim();
		strsex = txt_sex.getText().toString().trim();
		strusername = edt_nickname.getText().toString().trim();
		switch (arg0.getId()) {
		case R.id.set_sex:
			/** 设置性别 */
			startActivityForResult(new Intent(getApplicationContext(), SetSexActivity.class).putExtra("strsex", strsex), SETSEX);
			break;
		case R.id.set_ads:
		case R.id.txt_ads:
			/** 设置地址 */
			startActivityForResult(new Intent(getApplicationContext(), SetAdsActivity.class), SETADS);
			break;

		case R.id.set_psw:
		case R.id.txt_psw:
			if (strmobile.isEmpty() && stremail.isEmpty()) {
				GlobalUtil.showToast(getApplicationContext(), "请先绑定手机号或邮箱");
				return;
			}
			/** 密码服务 */
			switch (SharepreUtil.getIntValue(getApplicationContext(), Globals.LOGINTYPE, Globals.ERRORCODE)) {
			case Globals.ERRORCODE:
				/** 错误 */
				break;
			case Globals.EMAILLOGIN:
				/** 邮箱登陆 */
				startActivityForResult(new Intent(getApplicationContext(), SetPswEmailActivity.class), SETPSW);
				break;
			case Globals.MOBILELONG:
				/** 手机登陆 */
				startActivityForResult(new Intent(getApplicationContext(), SetPswActivity.class), SETPSW);
				break;
			case Globals.USERLOGIN:
				/** 用户名登陆 */
			case Globals.THIRDLOGIN:
				/** 第三方登陆 */
				if (!strmobile.isEmpty()) {
					startActivityForResult(new Intent(getApplicationContext(), SetPswActivity.class), SETPSW);
					break;
				}
				if (!stremail.isEmpty()) {
					startActivityForResult(new Intent(getApplicationContext(), SetPswEmailActivity.class), SETPSW);
					break;
				}
				break;
			default:
				break;
			}

			break;
		case R.id.set_moblie:
		case R.id.txt_mobile:
			/** 设置手机号 */
			startActivityForResult(new Intent(getApplicationContext(), SetMoblieActivity.class).putExtra("strmobile", strmobile), SETMOB);
			break;
		case R.id.set_username:
			/** 设置昵称 */
			startActivityForResult(new Intent(getApplicationContext(), SetMoblieActivity.class), SETMOB);
			break;
		case R.id.set_email:
		case R.id.txt_email:
			/** 设置邮箱 */
			startActivityForResult(new Intent(getApplicationContext(), SetEmailActivity.class).putExtra("stremail", stremail), SETEMA);
			break;
		/** 设置头像 */
		case R.id.set_img:

			DialogSelect dialogselect = new DialogSelect(PerSetActivity.this);
			dialogselect.getWindow().setGravity(Gravity.BOTTOM);
			dialogselect.show();
			dialogselect.getWindow().setLayout(ScreenUtils.getScreenWidth(getApplicationContext()), LayoutParams.WRAP_CONTENT);
			dialogselect.setCanceledOnTouchOutside(true);
			dialogselect.getSelectReturnListener(new SelectReturn() {

				public void phoTo() {
					// TODO Auto-generated method stub
					/* 拍照 */
					CameraManager.openCamera(PerSetActivity.this, outFile);
				}

				@Override
				public void exit() {
					// TODO Auto-generated method stub
					/** 退出 */
				}

				@Override
				public void alBum() {
					// TODO Auto-generated method stub
					/** 相册 */
					CameraManager.openAlbum(PerSetActivity.this);
				}
			});

			break;
		/** 提交修改 */
		case R.id.txt_enter:
			initMsg();
			break;
		/** 结束程序 */
		case R.id.txt_finish:
			UpDataDialog updata = new UpDataDialog(PerSetActivity.this, Globals.FINISHAPP);
			updata.show();
			updata.SetDialogListener(new DialogListener() {

				@Override
				public void onExit() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onEnter() {
					// TODO Auto-generated method stub
					SharepreUtil.clear(getApplicationContext());
					ImageLoader.getInstance().clearDiskCache();
					ImageLoader.getInstance().clearMemoryCache();
					AppManager.getInstance().killAllActivity();
				}
			});
			break;

		}
	}

	/** 修改个人信息 */
	public void initMsg() {
		String sharename = SharepreUtil.getStringValue(getApplicationContext(), Globals.TRUENAME, "");
		String sharesex = SharepreUtil.getStringValue(getApplicationContext(), Globals.SEX, "");
		if (sharename.equals(strusername) && sharesex.equals(strsex) && bitmap == null) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOMODF);
			return;
		}
		if (strusername.isEmpty()) {
			GlobalUtil.showToast(getApplicationContext(), Globals.NOUSERNAME);
			return;
		}

		/**
		 * httpClient方式请求服务器,listbitmap采用数组形式，方便多张图片上传，图片之间用;
		 */
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.ModPerMsg(PerSetActivity.this, handler, bitmap, SELECSEX, strusername);

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				String returnJson = msg.getData().getString("returnJson");
				Log.e("@@@@@@@@@", returnJson);
				try {
					JSONObject jso = new JSONObject(returnJson);
					if (jso.getInt("code") == Globals.CODE) {
						JSONObject logine = jso.getJSONObject(Globals.DATAS);
						if (logine.getInt(Globals.STATUS) == 1) {
							GlobalUtil.showToast(getApplicationContext(), logine.getString(Globals.MSG));
							SharepreUtil.putStringValue(getApplicationContext(), Globals.TRUENAME, logine.getString(Globals.TRUENAME));
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
							onBackPressed();
						} else {
							GlobalUtil.showToast(getApplicationContext(), "修改失败");
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					GlobalUtil.showToast(getApplicationContext(), Globals.MSG_WHAT_2);
				}
				break;
			case 2:
				GlobalUtil.showToast(getApplicationContext(), Globals.MSG_WHAT_2);
				break;
			}
		}
	};

	@SuppressLint("NewApi")
	public int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
																			// 12
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Globals.INTENT_ACTION_PICTURE && resultCode == Activity.RESULT_OK && null != data) {
			uri = data.getData();
			if (uri != null) {
				CameraManager.openCrop(PerSetActivity.this, uri);
			} else {

			}
		} else if (requestCode == Globals.INTENT_ACTION_CAREMA && resultCode == Activity.RESULT_OK) {
			CameraManager.openCrop(PerSetActivity.this, Uri.fromFile(outFile));
		} else if (requestCode == Globals.INTENT_ACTION_CROP && resultCode == Activity.RESULT_OK && null != data) {
			if (data != null) {
				bitmap = data.getParcelableExtra("data");
				// txt_backg.setImageBitmap(bitmap);

				set_img.setImageBitmap(bitmap);
				txt_backg.setBackground(new BitmapDrawable(getResources(), bitmap));
				txt_backg.buildDrawingCache();
				Bitmap bmp = txt_backg.getDrawingCache();
				ImageTools.blur(getApplicationContext(), bmp, txt_backg);
				listbitmap.add(bitmap);
				ImageLoader.getInstance().clearDiskCache();
				ImageLoader.getInstance().clearMemoryCache();
			}
			try {
				// 将临时文件删除
				outFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (requestCode == SETADS && resultCode == Activity.RESULT_OK && null != data) {
			txt_ads.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.ADDRESS, ""));
		} else if (requestCode == SETSEX && resultCode == Activity.RESULT_OK && null != data) {
			String sex = data.getStringExtra("strsex");
			txt_sex.setText(sex);
			if (sex.equals(Globals.MAN)) {
				SELECSEX = Globals.SEXMAN;
			} else if (sex.equals(Globals.WOMEN)) {
				SELECSEX = Globals.SEXWOMEN;
			} else if (sex.equals(Globals.CON)) {
				SELECSEX = Globals.SEXCON;
			} else {

			}

		} else if (requestCode == SETMOB && resultCode == Activity.RESULT_OK && null != data) {
			txt_mobile.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.MOBILE, ""));
		} else if (requestCode == SETPSW && resultCode == Activity.RESULT_OK && null != data) {
			txt_psw.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.PSWD, ""));
		} else if (requestCode == SETEMA && resultCode == Activity.RESULT_OK && null != data) {
			txt_email.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.EMAIL, ""));
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		txt_ads.setText(SharepreUtil.getStringValue(getApplicationContext(), Globals.ADDRESS, ""));
	}

	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}
}
