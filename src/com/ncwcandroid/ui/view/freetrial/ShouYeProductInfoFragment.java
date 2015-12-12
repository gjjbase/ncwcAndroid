package com.ncwcandroid.ui.view.freetrial;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Toast;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.PostService;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.SharepreUtil;

/***
 * 首页详情中产品详情页面
 * 
 * @author Administrator
 * 
 */
public class ShouYeProductInfoFragment extends Fragment implements VolleyStringListener {

	private WebView img_pro_info;

	@SuppressLint({ "SetJavaScriptEnabled", "InflateParams" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_shouye_product_info, null, false);

		img_pro_info = (WebView) view.findViewById(R.id.img_pro_info);
		WebSettings webSettings = img_pro_info.getSettings();
		webSettings.setBlockNetworkImage(false);
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		Log.e("@@@@@@@@", android.os.Build.VERSION.SDK_INT + "");
		if (android.os.Build.VERSION.SDK_INT <= 18) {
			webSettings.setUseWideViewPort(true);// 关键点
			webSettings.setBuiltInZoomControls(true);
			// 自适应屏幕
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
			webSettings.setLoadWithOverviewMode(true);
		}

		VolleyHttpUtil vhus = new VolleyHttpUtil(getActivity());
		vhus.GetProInfo(getActivity(), SharepreUtil.getStringValue(getActivity(), "id", ""));
		vhus.setObjectList(this);

		return view;
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		if (type == PostService.TYPE_PRODUCTINFO) {
			// Toast.makeText(getActivity(), response, 3000).show();
			try {
				JSONObject obj = new JSONObject(response);
				int code = obj.getInt("code");
				if (code == 200) {
					JSONObject datas = obj.getJSONObject("datas");

					String info = datas.getString("info");
					info = info.replaceAll("&amp;", "");
					info = info.replaceAll("&quot;", "\"");
					info = info.replaceAll("&lt;", "<");
					info = info.replaceAll("&gt;", ">");
					String html = "<style>img{width:100%;max-height:100%;text-align:center;}</style>" + info;
					if (android.os.Build.VERSION.SDK_INT <= 18) {
						info = info.replaceAll("18px", "36px");
						info = info.replaceAll("14px", "28px");
						info = info.replaceAll("12px", "24px");
						info = info.replaceAll("10px", "20px");
						html = "<style>img{width:100%;max-height:100%;text-align:center;}p{width:100%;max-height:100%;text-align:center;font-size:36px;}</style>" + info;
					}
					img_pro_info.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

				} else {
					Toast.makeText(getActivity(), Globals.FAULTERROE, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
