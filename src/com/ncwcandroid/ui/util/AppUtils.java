﻿package com.ncwcandroid.ui.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;


/**
 * 跟App相关的辅助类
 * @author Hy
 *
 */
public class AppUtils {

	private AppUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称，展示给用户
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前程序的versincode,主要用于升级，不展示给用户，为integer类型
	 */
	public static Integer getVersionCode(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* 卸载apk */
	public static void uninstallApk(Context context, String packageName) {
		Uri uri = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, uri);
		context.startActivity(intent);
	}

	/**
	 * 启动手机中的一个APK文件
	 * 
	 * @param context
	 * @param apk
	 */
	public static void startApk(Context context, String apk) {

		Intent intent = context.getPackageManager().getLaunchIntentForPackage(apk);
		if (intent != null) {
			context.startActivity(intent);
		}
	}

	// 或者在要启动的程序里面定义一个Activity，来启动这个Activity，也能启动程序
	public static void startApkActivity(Context context, String activity) {
		// 启动test.apk里面的一个Activity,例如叫做testActivity
		Intent intent = new Intent(activity);
		if (intent != null) {
			context.startActivity(intent);
		}
	}
}
