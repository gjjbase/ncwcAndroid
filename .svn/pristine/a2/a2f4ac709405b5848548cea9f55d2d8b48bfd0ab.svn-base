package com.ncwcandroid.ui.util;

import android.content.Context;

import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.interfac.DialogListener;
import com.ncwcandroid.ui.widget.UpDataDialog;
import com.ncwcandroid.ui.widget.UpdataProgress;

/**
 * apk更新模块
 * 
 * @author Administrator
 * 
 */
public class UpdateManager {

	private Context mContext;
	private String apkUrl;
	private String version;

	public UpdateManager(Context context, String apkUrl, String version) {
		/**
		 * AppUtils.getAppName(context) 获得当前版本的名称
		 */
		this.mContext = context;
		this.apkUrl = apkUrl;
		this.version = version;
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo() {
		if (FileUtil.isSdcardAvailable())
			showNoticeDialog();
		else
			GlobalUtil.showToast(mContext, Globals.NOSDVSER);
	}

	private void showNoticeDialog() {
		UpDataDialog updata = new UpDataDialog(mContext, Globals.UPMSG);
		updata.show();
		updata.SetDialogListener(new DialogListener() {

			@Override
			public void onExit() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				UpdataProgress updataProgress = new UpdataProgress(mContext, apkUrl, version);
				updataProgress.show();
			}
		});
	}

}
