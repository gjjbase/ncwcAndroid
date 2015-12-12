package com.ncwcandroid.ui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * 全局方法工具类
 * 
 */
@SuppressLint("DefaultLocale")
public class GlobalUtil {
	protected static int count = 0;
	public static Context context;

	/**
	 * 生成随机的唯一ID
	 * 
	 * @return
	 */
	public static synchronized String getUUID() {
		count++;
		long time = System.currentTimeMillis();

		String uuid = "G" + Long.toHexString(time) + Integer.toHexString(count) + Long.toHexString(Double.doubleToLongBits(Math.random()));

		uuid = uuid.substring(0, 24).toUpperCase();

		return uuid;
	}

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.replaceAll("/", "").getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

	public static void showToast(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 计算字符长度，汉字占两位，字母数字占一位
	 * 
	 * @param s
	 * @return
	 */
	public static int getWordCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;
		}
		return length;
	}

	/**
	 * 判断手机号的正则表达式
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		if (mobiles.isEmpty() && mobiles.length() != 11) {
			return false;
		}

		return true;
	}

	/**
	 * 判断邮箱的正则表达式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmailNO(String email) {
		boolean tag = true;
		final String str_pattern = "@";
		final Pattern pattern = Pattern.compile(str_pattern);
		final Matcher matcher = pattern.matcher(email);
		if (!matcher.find() || email.isEmpty()) {
			tag = false;
		}

		return tag;
	}

	// public static boolean isEmailNO(String email) {
	//
	// String check =
	// "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	// Pattern regex = Pattern.compile(check);
	// Matcher matcher = regex.matcher("email");
	// return matcher.matches();
	// }

}
