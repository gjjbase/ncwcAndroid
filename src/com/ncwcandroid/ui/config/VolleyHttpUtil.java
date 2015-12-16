package com.ncwcandroid.ui.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.ImageTools;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.util.ThreadUtil;
import com.ncwcandroid.ui.widget.FlippingLoadingDialog;

/**
 * volley的网络请求
 * 
 * @author Hy
 * 
 */
public class VolleyHttpUtil {
	private RequestQueue requestQueue;
	private static VolleyHttpUtil volleyhtp = null;
	private VolleyStringListener objectLister;
	private FlippingLoadingDialog flippingLoadingdfialog;

	public synchronized static VolleyHttpUtil getInstance(Context context) {
		if (volleyhtp == null) {
			volleyhtp = new VolleyHttpUtil(context);
		}
		return volleyhtp;
	}

	public VolleyHttpUtil(Context context) {
		// TODO Auto-generated constructor stub
		requestQueue = Volley.newRequestQueue(context);
	}

	public void setObjectList(VolleyStringListener objectLister) {
		this.objectLister = objectLister;
	}

	protected void onData(String response, String type) throws JSONException {
		if (objectLister != null) {
			objectLister.ReturnVolleyString(response, type);
		}
	}

	/**
	 * 
	 * @param activity
	 *            当前activity
	 * @param url
	 *            请求的地址
	 * @param Params
	 *            地址参数
	 * @param type
	 *            请求类型
	 * @param view
	 *            是否显示正在加载的对话框
	 */
	protected void getObject(final Activity activity, String url,
			final Map<String, String> Params, final String type, boolean view) {
		StringBuilder sb = new StringBuilder();
		if (Params != null && !Params.isEmpty()) {
			for (Map.Entry<String, String> entry : Params.entrySet()) {
				sb.append(entry.getKey()).append("=");
				try {
					sb.append(URLEncoder.encode(entry.getValue(),
							PostService.CODEING));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		if (view) {
			flippingLoadingdfialog = new FlippingLoadingDialog(activity);
			flippingLoadingdfialog.show();
		}
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url + "&" + sb, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						if (flippingLoadingdfialog != null) {
							flippingLoadingdfialog.dismiss();
						}

						try {
							onData(response, type);
						} catch (Exception e) {
							// TODO: handle exception
							GlobalUtil.showToast(activity, Globals.MSG_WHAT_2);
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("$$$$$$$$$$", error + "");
						if (flippingLoadingdfialog != null) {
							flippingLoadingdfialog.dismiss();
						}
						GlobalUtil.showToast(activity, Globals.MSG_WHAT_2);
					}
				}) {

			@Override
			protected Response<String> parseNetworkResponse(
					NetworkResponse response) {

				try {
					return Response.success(new String(response.data, "UTF-8"),
							HttpHeaderParser.parseCacheHeaders(response));
				} catch (UnsupportedEncodingException e) {
					return Response.error(new ParseError(e));
				} catch (Exception je) {
					return Response.error(new ParseError(je));
				}
			}

		};

		requestQueue.add(stringRequest);
		requestQueue.start();

	}

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @param getjson
	 * @throws Exception
	 */
	public void Login(Activity activity, String username, String password)
			throws Exception {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("username", username);
		Params.put("password", password);
		Params.put("client", PostService.ANDROID);
		getObject(activity, PostService.URL_LOGIN, Params,
				PostService.TYPE_LOGIN, true);
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 */
	public void sendMsg(Activity activity, String mobile) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("mobile", mobile);
		Log.e("moblie", mobile);
		getObject(activity, PostService.URL_SENDMSG, Params,
				PostService.TYPE_SENDMSG, true);
	}

	public void ThirdLogin(Activity activity, String opened, String truename,
			int sex, String avatar) {
		HashMap<String, String> Params = new HashMap<String, String>();
		Params.put("openid", opened);
		Params.put("truename", truename);
		Params.put("sex", String.valueOf(sex));
		Params.put("avatar", avatar);
		getObject(activity, PostService.THRID_LOGIN, Params,
				PostService.THRID_TYPE, true);
	}

	/**
	 * 发送邮箱验证码
	 * 
	 * @param activity
	 * @param email
	 */
	public void sendEmail(Activity activity, String email) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("email", email);
		getObject(activity, PostService.URL_SENDEMAIL, Params,
				PostService.TYPE_SENDEAMIL, true);
	}

	/**
	 * 邮箱注册
	 * 
	 * @param strusername
	 * @param strcode
	 * @param strrpass
	 * @param stremail
	 */
	public void MailBoxRdtData(Activity activity, String strusername,
			String strcode, String strrpass, String stremail, String str_refcode) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("member_truename", strusername);
		Params.put("mobile", "");
		Params.put("code", strcode);
		Params.put("type", PostService.EMAILEREG);
		Params.put("password", strrpass);
		Params.put("password_confirm", strrpass);
		Params.put("email", stremail);
		Params.put("referred_code", str_refcode);
		Params.put("client", PostService.ANDROID);
		getObject(activity, PostService.URL_REGISTER, Params,
				PostService.TYPE_MOBILREGISTER, true);
	}

	/**
	 * 手机注册
	 * 
	 * @param strusername
	 * @param strphone
	 * @param strcode
	 * @param strrpass
	 */
	public void PhoneRdtData(Activity activity, String strusername,
			String strphone, String strcode, String strrpass, String str_refcode) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("member_truename", strusername);
		Params.put("mobile", strphone);
		Params.put("code", strcode);
		Params.put("type", PostService.MOBILEREG);
		Params.put("password", strrpass);
		Params.put("password_confirm", strrpass);
		Params.put("email", "");
		Params.put("referred_code", str_refcode);
		Params.put("client", PostService.ANDROID);
		getObject(activity, PostService.URL_REGISTER, Params,
				PostService.TYPE_PHONEREGISTER, true);
	}

	/**
	 * 手机重置密码
	 * 
	 * @param activity
	 * @param code
	 * @param password
	 * @param password_confirm
	 * @param parameter
	 * @param type
	 */
	public void ResetPswd(Activity activity, String code, String password,
			String password_confirm, String parameter) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("code", code);
		Params.put("password", password);
		Params.put("password_confirm", password_confirm);
		Params.put("parameter", parameter);
		Params.put("type", PostService.MOB_RESET);
		getObject(activity, PostService.URl_RESET, Params,
				PostService.TYPE_RESET, true);
	}

	/**
	 * 邮箱重置密码
	 * 
	 * @param activity
	 * @param code
	 * @param password
	 * @param password_confirm
	 */
	public void ResetEmail(Activity activity, String code, String password,
			String password_confirm, String parameter) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("code", code);
		Params.put("password", password);
		Params.put("password_confirm", password_confirm);
		Params.put("parameter", parameter);
		Params.put("type", PostService.EMA_RESET);
		getObject(activity, PostService.URl_RESET, Params,
				PostService.TYPE_RESET, true);
	}

	/**
	 * 修改个人信息
	 * 
	 * @param activity
	 * @param member_id
	 * @param key
	 * @param avatar
	 * @param sex
	 * @param member_truename
	 */

	public void ModPerMsg(Activity activity, Handler handler, Bitmap bitmap,
			int sex, String member_truename) {
		String member_id = SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, Globals.MEMBERID);
		String loginkey = SharepreUtil.getStringValue(activity,
				Globals.LOGINKEY, Globals.LOGINKEY);

		ThreadUtil thread = new ThreadUtil(handler);
		HashMap<String, String> Params = new HashMap<String, String>();
		Params.put("member_id", member_id);
		Params.put("key", loginkey);
		if (bitmap == null) {
			Params.put("avatar", "");
		} else {
			Params.put("avatar", StringEscapeUtils.unescapeJava(ImageTools
					.bitmapToBase64(bitmap)));
		}
		Params.put("sex", String.valueOf(sex));
		Params.put("truename", member_truename);
		thread.doStartWebServicerequestWebService(activity, Params,
				PostService.URL_MODPER, true);
	}

	/**
	 * 重置手机号
	 * 
	 * @param activity
	 * @param member_id
	 * @param key
	 * @param mobile
	 * @param code
	 */
	public void SetMobile(Activity activity, String mobile, String code) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("member_id",
				SharepreUtil.getStringValue(activity, Globals.MEMBERID, ""));
		Params.put("key",
				SharepreUtil.getStringValue(activity, Globals.LOGINKEY, ""));
		Params.put("mobile", mobile);
		Params.put("code", code);
		getObject(activity, PostService.URL_MODPER, Params, PostService.MODPER,
				true);
	}

	/**
	 * 重置邮箱
	 * 
	 * @param activity
	 * @param email
	 * @param code
	 */
	public void SetEmail(Activity activity, String email, String code) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("member_id",
				SharepreUtil.getStringValue(activity, Globals.MEMBERID, ""));
		Params.put("key",
				SharepreUtil.getStringValue(activity, Globals.LOGINKEY, ""));
		Params.put("email", email);
		Params.put("code", code);
		getObject(activity, PostService.URL_MODPER, Params, PostService.MODPER,
				true);
	}

	/**
	 * 查看个人收货地址
	 * 
	 * @param activity
	 * @param member_id
	 * @param key
	 */
	public void ListAds(Activity activity) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("member_id", SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, Globals.MEMBERID));
		Params.put("key", SharepreUtil.getStringValue(activity,
				Globals.LOGINKEY, Globals.LOGINKEY));
		getObject(activity, PostService.URL_LISTADS, Params,
				PostService.LISTADS, true);

	}

	/**
	 * 新增地址
	 * 
	 * @param activity
	 * @param truename
	 * @param address
	 * @param tel_phone
	 * @param mob_phone
	 */
	public void AddAds(Activity activity, String truename, String address,
			String mob_phone, String zip_code) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("key", SharepreUtil.getStringValue(activity,
				Globals.LOGINKEY, Globals.LOGINKEY));
		Params.put("member_id", SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, Globals.MEMBERID));
		Params.put("true_name", truename);
		Params.put("address", address);
		Params.put("mob_phone", mob_phone);
		Params.put("zip_code", zip_code);
		getObject(activity, PostService.URL_ADDADS, Params, PostService.ADDADS,
				true);
	}

	/**
	 * 编辑地址操作
	 * 
	 * @param activity
	 * @param address_id
	 * @param true_name
	 * @param address
	 * @param mob_phone
	 * @param zip_code
	 */
	public void EditAds(Activity activity, String address_id, String true_name,
			String address, String mob_phone, String zip_code) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("member_id", SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, Globals.MEMBERID));
		Params.put("key", SharepreUtil.getStringValue(activity,
				Globals.LOGINKEY, Globals.LOGINKEY));
		Params.put("address_id", address_id);
		Params.put("true_name", true_name);
		Params.put("address", address);
		Params.put("mob_phone", mob_phone);
		Params.put("zip_code", zip_code);
		getObject(activity, PostService.URL_EDITADS, Params,
				PostService.EDITADS, true);
	}

	/**
	 * 删除地址
	 * 
	 * @param activity
	 * @param address_id
	 */
	public void DelAds(Activity activity, String address_id) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("member_id", SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, Globals.MEMBERID));
		Params.put("key", SharepreUtil.getStringValue(activity,
				Globals.LOGINKEY, Globals.LOGINKEY));
		Params.put("address_id", address_id);
		getObject(activity, PostService.URL_DELADS, Params, PostService.DELADS,
				true);
	}

	/**
	 * 设置默认收货地址操作
	 * 
	 * @param activity
	 * @param address_id
	 */
	public void DefAds(Activity activity, String address_id) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("member_id", SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, Globals.MEMBERID));
		Params.put("key", SharepreUtil.getStringValue(activity,
				Globals.LOGINKEY, Globals.LOGINKEY));
		Params.put("address_id", address_id);
		getObject(activity, PostService.URL_DEFADS, Params, PostService.DEFADS,
				true);
	}

	/**
	 * 获取用户个人活动记录 每页5条数据
	 * 
	 * @param member_id
	 * @param key
	 * @param curpage
	 * @param eachNum
	 */
	public void UserInfoRed(Activity activity, String member_id, String key,
			int curpage, boolean metdel) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put(Globals.LOGINKEY, key);
		Params.put("member_id", member_id);
		Params.put("curpage", String.valueOf(curpage));
		Params.put("eachNum", PostService.CURPAGE);
		if (metdel) {
			getObject(activity, PostService.URL_PERINFO, Params,
					PostService.USERRECROD, true);
		} else {
			getObject(activity, PostService.URL_PERINFO, Params,
					PostService.USERRECROD, false);
		}

	}

	/**
	 * 用户反馈
	 * 
	 * @param feedboox
	 * @param key
	 */
	public void FeedBoxData(Activity activity, String feedboox, String key) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put(Globals.LOGINKEY, key);
		Params.put("feedbook", feedboox);
		getObject(activity, PostService.URL_FEEDBACK_ADD, Params,
				PostService.TYPE_MOBILREGISTER, true);
	}

	/**
	 * 检查更新
	 */
	public void UpMsgData(Activity activity) {
		getObject(activity, PostService.URL_VERSION_UPDATE,
				new HashMap<String, String>(), PostService.TYPE_VERSION_UPDATE,
				false);
	}

	/**
	 * 首页获取产品列表信息
	 * 
	 * @param type
	 *            类型：1最新，2往期，3最新
	 * @param eachNum
	 *            几条一页
	 * @param curpage
	 *            第几页
	 * @param member_id
	 *            用户ID
	 * @param key
	 *            登录KEY
	 */
	public void ListProducts(Activity activity, String type, String eachNum,
			String curpage, Boolean flag) {
		Map<String, String> par = new HashMap<String, String>();
		par.put("type", type);
		par.put("eachNum", eachNum);
		par.put("curpage", curpage);
		String member_id = SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, "0");
		String key = SharepreUtil.getStringValue(activity, Globals.LOGINKEY,
				"-1");
		par.put("member_id", member_id);
		par.put("key", key);
		getObject(activity, PostService.URL_CONTEXTPATH + "act=try&op=list",
				par, PostService.TYPE_LISTPRODUCTS, flag);
		Log.e("uuuuuuuuu", PostService.URL_CONTEXTPATH + "act=try&op=list");
	}

	/**
	 * 获取首页模块里的评论
	 * 
	 * @param pro_id
	 *            要得到评论的产品
	 */
	public void GetPingLun(Activity activity, String pro_id, String eachNum) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("tryId", pro_id);
		Params.put("curpage", "1");
		Params.put("eachNum", eachNum);
		getObject(activity, PostService.URL_CONTEXTPATH
				+ "act=try&op=getComment", Params, PostService.TYPE_GETPINGLUN,
				false);
	}

	/**
	 * 获取首页模块里的评论(刷新)
	 * 
	 * @param pro_id
	 *            要得到评论的产品
	 */
	public void GetPingLun_s(Activity activity, String pro_id, String eachNum,
			Handler handler) {
		ThreadUtil thread = new ThreadUtil(handler);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tryId", pro_id);
		params.put("curpage", "1");
		params.put("eachNum", eachNum);
		thread.doStartWebServicerequestWebService(activity, params,
				PostService.URL_CONTEXTPATH + "act=try&op=getComment", true);
	}

	/**
	 * 获取试用报告
	 * 
	 * @param tryId
	 *            试用产品的ID
	 */
	public void GetShiYongBaoGao(Activity activity, String tryId, String eachNum) {
		Map<String, String> Params = new HashMap<String, String>();
		Params.put("tryId", tryId);
		Params.put("curpage", "1");
		Params.put("eachNum", eachNum);
		getObject(activity, PostService.URL_GETSHIYONGBAOGAO, Params,
				PostService.TYPE_GETSHIYONGBAOGAO, true);
	}

	/**
	 * 获取中奖名单
	 * 
	 * @param tryId
	 *            试用产品的ID
	 */
	public void GetZhongJiangMingDan(Activity activity, String tryId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("try_id", tryId);
		params.put("curpage", "1");
		params.put("eachNum", "1000");
		getObject(activity, PostService.URL_CONTEXTPATH
				+ "act=try&op=getWinning", params,
				PostService.TYPE_GETZHONGJIANGMINGDAN, false);
	}

	/**
	 * 提交评论
	 * 
	 * @param content
	 *            评论内容
	 * @param member_id
	 *            用户ID
	 * @param try_id
	 *            产品ID
	 */
	public void GiveComment(Activity activity, String content, String try_id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("content", content);
		String member_id = SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, "0");
		String key = SharepreUtil.getStringValue(activity, Globals.LOGINKEY,
				"-1");
		params.put("member_id", member_id);
		params.put("key", key);
		params.put("try_id", try_id);
		getObject(activity, PostService.URL_ADDCOMMENT, params,
				PostService.TYPE_ADDCOMMENT, true);
	}

	/**
	 * 申请试用
	 * 
	 * @param member_id
	 *            用户ID
	 * @param try_id
	 *            产品ID
	 */
	public void ApplyTry(Activity activity, String try_id) {
		PackageManager manager;
		PackageInfo info = null;
		manager = activity.getPackageManager();
		try {
			info = manager.getPackageInfo(activity.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		String versionName = info.versionName;
		Map<String, String> params = new HashMap<String, String>();
		String member_id = SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, "0");
		String key = SharepreUtil.getStringValue(activity, Globals.LOGINKEY,
				"-1");
		params.put("member_id", member_id);
		params.put("key", key);
		params.put("try_id", try_id);
		params.put("version", versionName);
		params.put("client", "android");
		getObject(activity, PostService.URL_APPLYTRY, params,
				PostService.TYPE_APPLEYTRY, false);
	}

	/**
	 * 确认地址（往期回顾）
	 * 
	 * @param member_id
	 *            用户ID
	 * @param try_id
	 *            产品ID
	 * @param phone
	 *            用户手机号
	 * @param address
	 *            用户地址
	 * @param zip_code
	 *            用户居住地邮编
	 */
	public void SurePlace(Activity activity, String try_id, String name,
			String phone, String address, String zip_code, String type,
			String by) {
		Map<String, String> params = new HashMap<String, String>();
		String member_id = SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, "0");
		String key = SharepreUtil.getStringValue(activity, Globals.LOGINKEY,
				"-1");
		params.put("member_id", member_id);
		params.put("key", key);
		params.put("id", try_id);
		params.put("name", name);
		params.put("phone", phone);
		params.put("address", address);
		params.put("zip_code", zip_code);
		params.put("car_name", type);
		params.put("other", by);
		getObject(activity, PostService.URL_SURESPLACE, params,
				PostService.TYPE_SURESPLACE, false);
	}

	/**
	 * 提交试用报告
	 * 
	 * @param member_id
	 *            用户ID
	 * @param try_id
	 *            产品ID
	 * @param key
	 *            用户手登录Key
	 * @param appearance_info
	 *            产品外观
	 * @param score
	 *            产品评分
	 * @param quality_info
	 *            产品质量
	 * @param price_info
	 *            产品质量
	 * @param img
	 *            用户上传产品图片
	 */
	public void GiveShiYongBaoGao(Activity activity, String try_id,
			String appearance_info, String score, String quality_info,
			String price_info, Bitmap bitmap, Handler handler) {
		ThreadUtil thread = new ThreadUtil(handler);
		HashMap<String, String> params = new HashMap<String, String>();
		String member_id = SharepreUtil.getStringValue(activity,
				Globals.MEMBERID, "0");
		String key = SharepreUtil.getStringValue(activity, Globals.LOGINKEY,
				"-1");
		params.put("member_id", member_id);
		params.put("key", key);
		params.put("try_id", try_id);
		params.put("appearance_info", appearance_info);
		params.put("score", score);
		params.put("quality_info", quality_info);
		params.put("price_info", price_info);
		params.put("img", StringEscapeUtils.unescapeJava(ImageTools
				.bitmapToBase64(bitmap)));
		thread.doStartWebServicerequestWebService(activity, params,
				PostService.URL_ADDREPORT, true);
	}

	/**
	 * 产品详情
	 * 
	 * @param id
	 *            产品ID
	 */
	public void GetProInfo(Activity activity, String id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		getObject(activity, PostService.URL_PRODUCTINFO, params,
				PostService.TYPE_PRODUCTINFO, false);
	}

}
