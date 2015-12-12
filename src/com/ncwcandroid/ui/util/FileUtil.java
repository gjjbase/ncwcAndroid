package com.ncwcandroid.ui.util;

import android.os.Environment;

public class FileUtil {
	
	private FileUtil() {
	}

	/*
	 * <!-- 在SDCard中创建与删除文件权限 --> <uses-permission
	 * android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/> <!--
	 * 往SDCard写入数据权限 --> <uses-permission
	 * android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	 */

	// =================get SDCard information===================
	public static boolean isSdcardAvailable() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}


}
