package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.BaseFragment;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.AppUtils;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.ImageTools;
import com.ncwcandroid.ui.util.RemSharepreUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.util.UpdateManager;
import com.ncwcandroid.ui.widget.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 个人首页
 * 
 * @author Administrator
 * 
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MyFragment extends BaseFragment implements OnClickListener, VolleyStringListener {
	private RelativeLayout my_setupdata, my_aboutus, my_setfeedbook, rel_recoaction, my_introd;
	private ImageView txt_myset, txt_backg;
	private CircularImage img_tx;
	private TextView txt_nickname, txt_upmsg;
	private View rootView;

	@SuppressLint("InflateParams")
	@Override
	protected View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		rootView = inflater.inflate(R.layout.fragmnetmy, null);
		my_setupdata = (RelativeLayout) rootView.findViewById(R.id.my_setupdata);
		my_aboutus = (RelativeLayout) rootView.findViewById(R.id.my_aboutus);
		my_setfeedbook = (RelativeLayout) rootView.findViewById(R.id.my_setfeedbook);
		rel_recoaction = (RelativeLayout) rootView.findViewById(R.id.rel_recoaction);
		my_introd = (RelativeLayout) rootView.findViewById(R.id.my_introd);
		txt_myset = (ImageView) rootView.findViewById(R.id.txt_myset);
		txt_backg = (ImageView) rootView.findViewById(R.id.txt_backg);
		txt_nickname = (TextView) rootView.findViewById(R.id.txt_nickname);
		txt_upmsg = (TextView) rootView.findViewById(R.id.txt_upmsg);
		img_tx = (CircularImage) rootView.findViewById(R.id.img_tx);
		txt_myset.setOnClickListener(this);
		my_setupdata.setOnClickListener(this);
		my_aboutus.setOnClickListener(this);
		my_introd.setOnClickListener(this);
		my_setfeedbook.setOnClickListener(this);
		rel_recoaction.setOnClickListener(this);
		txt_upmsg.setText(RemSharepreUtil.getStringValue(getActivity(), Globals.UPVER, Globals.UPYESVERMSG));
		return rootView;
	}

	/** 监听txt_backg图片的变化(耗时操作),绘制高斯模糊图片 */
	private void applyBlur() {
		txt_backg.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				txt_backg.getViewTreeObserver().removeOnPreDrawListener(this);
				txt_backg.buildDrawingCache();
				Bitmap bmp = txt_backg.getDrawingCache();
				ImageTools.blur(getActivity(), bmp, txt_backg);
				return true;
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		/** 检查更新 */
		case R.id.my_setupdata:
			upMsgData();
			break;
		/**
		 * 关于我们页面
		 */
		case R.id.my_aboutus:
			startActivity(new Intent(getActivity(), AboutsUsActivity.class));
			break;
		/**
		 * 用户反馈页面
		 */
		case R.id.my_setfeedbook:
			startActivity(new Intent().setClass(getActivity(), FeedBookActivity.class));
			break;
		/**
		 * 个人信息设置页面
		 */
		case R.id.txt_myset:
			if (SharepreUtil.getStringValue(getActivity(), Globals.LOGINKEY, Globals.LOGINKEY).equals(Globals.LOGINKEY)) {
				GlobalUtil.showToast(getActivity(), Globals.NEEDLOGIN);
				Intent intnet = new Intent("com.ncwcandroid.ui.interfac.BootBroadcastReceiver");
				getActivity().sendBroadcast(intnet);
				return;
			}
			startActivity(new Intent().setClass(getActivity(), PerSetActivity.class));
			break;
		/**
		 * 活动记录页面
		 */
		case R.id.rel_recoaction:
			if (SharepreUtil.getStringValue(getActivity(), Globals.LOGINKEY, Globals.LOGINKEY).equals(Globals.LOGINKEY)) {
				GlobalUtil.showToast(getActivity(), Globals.NEEDLOGIN);
				Intent intnet = new Intent("com.ncwcandroid.ui.interfac.BootBroadcastReceiver");
				getActivity().sendBroadcast(intnet);
				return;
			}
			startActivity(new Intent().setClass(getActivity(), RecoActionActivity.class));
			break;
		/** 活动介绍页面 */
		case R.id.my_introd:
			startActivity(new Intent().setClass(getActivity(), InTrodActionActivity.class));
			break;

		}
	}

	/**
	 * 检查更新中的apk
	 */
	private void upMsgData() {
		// TODO Auto-generated method stub
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getActivity());
		vtp.UpMsgData(getActivity());
		vtp.setObjectList(this);
	}

	@Override
	public void ReturnVolleyString(String response, String type) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject jso = new JSONObject(response);
		if (jso.getInt("code") == Globals.CODE) {
			String version = jso.getJSONObject(Globals.DATAS).getString("version");
			if (AppUtils.getVersionName(getActivity()).equals(version)) {
				GlobalUtil.showToast(getActivity(), "已是最新版本了");
			} else {
				String apkUrl = jso.getJSONObject(Globals.DATAS).getString("url");
				UpdateManager upmanager = new UpdateManager(getActivity(), apkUrl, version);
				upmanager.checkUpdateInfo();
			}
		} else {
			GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		applyBlur();
		txt_upmsg.setText(RemSharepreUtil.getStringValue(getActivity(), Globals.UPVER, Globals.UPYESVERMSG));
		switch (SharepreUtil.getIntValue(getActivity(), Globals.LOGINTYPE, Globals.ERRORCODE)) {
		case Globals.ERRORCODE:
			txt_nickname.setText("未登陆");
			txt_backg.setBackground(getResources().getDrawable(R.color.bg_color));
			break;

		case Globals.MOBILELONG:
		case Globals.EMAILLOGIN:
		case Globals.THIRDLOGIN:
		case Globals.USERLOGIN:

			try {
				txt_nickname.setText(SharepreUtil.getStringValue(getActivity(), Globals.TRUENAME, ""));
			} catch (Exception e) {
				// TODO: handle exception
			}
			AsyncLoadingPicture.getInstance(getActivity()).LoadPicture(SharepreUtil.getStringValue(getActivity(), Globals.AVATAR, ""), img_tx);
			Bitmap bitmap = ImageLoader.getInstance().loadImageSync(SharepreUtil.getStringValue(getActivity(), Globals.AVATAR, ""));
			if (bitmap != null) {
				txt_backg.setBackground(new BitmapDrawable(getResources(), bitmap));
			} else {
				txt_backg.setBackground(getResources().getDrawable(R.color.bg_color));
			}

			break;

		}

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		/**
		 * 检查更新中的apk
		 */
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getActivity());
		vtp.UpMsgData(getActivity());
		vtp.setObjectList(new VolleyStringListener() {

			@Override
			public void ReturnVolleyString(String response, String type) throws JSONException {
				// TODO Auto-generated method stub
				JSONObject jso = new JSONObject(response);
				if (jso.getInt("code") == Globals.CODE) {
					String version = jso.getJSONObject(Globals.DATAS).getString("version");
					if (AppUtils.getVersionName(getActivity()).equals(version)) {
						RemSharepreUtil.putStringValue(getActivity(), Globals.UPVER, Globals.UPYESVERMSG);
					} else {
						RemSharepreUtil.putStringValue(getActivity(), Globals.UPVER, Globals.UPVERMSG + version);
						String apkUrl = jso.getJSONObject(Globals.DATAS).getString("url");
						UpdateManager upmanager = new UpdateManager(getActivity(), apkUrl, version);
						upmanager.checkUpdateInfo();
					}
					txt_upmsg.setText(RemSharepreUtil.getStringValue(getActivity(), Globals.UPVER, Globals.UPYESVERMSG));
				} else {
					GlobalUtil.showToast(getActivity(), jso.getJSONObject(Globals.DATAS).getString(Globals.ERROE));
				}
			}
		});
	}

}