package com.ncwcandroid.ui.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;

public class HttpHelper {
	/**
	 * 
	 * @param path
	 * @param params
	 * @return
	 */
	public static String sendPOSTRequest(String path, Map<String, String> params) throws Exception {
		// title=liming&length=30
		StringBuilder sb = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=");
				sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		byte[] data = sb.toString().getBytes();

		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setReadTimeout(10000);
		conn.setConnectTimeout(10000);

		conn.setRequestMethod("POST");
		conn.setDoOutput(true);//
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", data.length + "");

		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		String result = "";
		String readLine = "";
		Log.e("conn.getResponseCode()", conn.getResponseCode() + "");
		try {
			if (conn.getResponseCode() == 200) {

				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				// StringBuilder stringBuilder = new StringBuilder();
				while ((readLine = reader.readLine()) != null) {

					result += readLine;
					System.out.println(readLine);
					// stringBuilder.append(readLine);
				}

				reader.close();
				return result;
			} else {
				Log.e("null", "null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}
}
