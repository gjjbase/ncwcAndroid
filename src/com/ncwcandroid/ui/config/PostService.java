package com.ncwcandroid.ui.config;

/**
 * 服务器接口常量
 * 
 * @author Administrator
 * 
 */
public class PostService {
	public static final String PROTOCOL = "http://";

	/** 服务器域名 */
	public static final String HOST = "m.nichewoche.com";
	/** 服务器端口号 */
	public static final String PORT = "80";

	/** 应用上下文名 */
	public static final String APP = "/mobile";// /mobile
	/** 客户端类型 */
	public static final String ANDROID = "android";
	/** 数据请求编码 */
	public static final String CODEING = "UTF-8";
	/** 应用上下文完整路径 */
	public static final String URL_CONTEXTPATH = PROTOCOL + HOST + APP + "/index.php?";

	/** 首页请求地址 */
	public static final String URL_HOME = URL_CONTEXTPATH + "act=index&op=index";
	/** 登录请求地址 */
	public static final String URL_LOGIN = URL_CONTEXTPATH + "act=login";
	public static final String TYPE_LOGIN = "login";
	/** 三方登陆请求地址 */
	public static final String THRID_LOGIN = URL_CONTEXTPATH + "act=login&op=other_login";
	public static final String THRID_TYPE = "thrid_type";
	/** 注册 */
	public static final String URL_REGISTER = URL_CONTEXTPATH + "act=login&op=register";
	public static final String TYPE_PHONEREGISTER = "phone";
	public static final String TYPE_MOBILREGISTER = "mobile";
	/** 注册方式 */
	public static final String SPECIREEG = "1";
	public static final String MOBILEREG = "2";
	public static final String EMAILEREG = "3";
	/** 发送短信验证码 */
	public static final String URL_SENDMSG = URL_CONTEXTPATH + "act=member_security&op=send_modify_mobile";
	public static final String TYPE_SENDMSG = "sendmobile";
	/** 发送邮箱验证码 */
	public static final String URL_SENDEMAIL = URL_CONTEXTPATH + "act=member_security&op=send_bind_email";
	public static final String TYPE_SENDEAMIL = "sendemail";
	/** 重置密码 */
	public static final String URl_RESET = URL_CONTEXTPATH + "act=member_security&op=reset_pwd";
	public static final String TYPE_RESET = "reset";
	/** 找回密码的2种方式 */
	public static final String MOB_RESET = "2";
	public static final String EMA_RESET = "3";
	/** 获取用户的个人活动记录 */
	public static final String URL_PERINFO = URL_CONTEXTPATH + "act=try&op=userrecord";
	public static final String USERRECROD = "userrecord";
	/** 分页参数 */
	public static final String CURPAGE = "10";
	/** 修改个人信息 */
	public static final String URL_MODPER = URL_CONTEXTPATH + "act=login&op=edituser";
	public static final String MODPER = "modper";
	/** 查看个人收货地址 */
	public static final String URL_LISTADS = URL_CONTEXTPATH + "act=member_address&op=address_list";
	public static final String LISTADS = "listads";
	/** 编辑地址 */
	public static final String URL_EDITADS = URL_CONTEXTPATH + "act=member_address&op=address_edit";
	public static final String EDITADS = "editads";
	/** 新增地址 */
	public static final String URL_ADDADS = URL_CONTEXTPATH + "act=member_address&op=address_add";
	public static final String ADDADS = "addads";
	/** 删除地址 */
	public static final String URL_DELADS = URL_CONTEXTPATH + "act=member_address&op=address_del";
	public static final String DELADS = "delads";
	/** 设置默认收货地址 */
	public static final String URL_DEFADS = URL_CONTEXTPATH + "act=member_address&op=default_address";
	public static final String DEFADS = "defads";
	/** 退出登陆 */
	public static final String URL_EXIT = URL_CONTEXTPATH + "act=login&op=outlogin";
	public static final String OUTLOGIN = "exit";
	/** 版本检测 */
	public static final String URL_VERSION_UPDATE = URL_CONTEXTPATH + "act=index&op=apk_version";
	public static final String TYPE_VERSION_UPDATE = "updata";
	/** 用户反馈 */
	public static final String URL_FEEDBACK_ADD = URL_CONTEXTPATH + "act=member_feedback&op=feedback_add";
	public static final String TYPE_FEEDBACK_ADD = "feedback";

	/**
	 * 首页
	 */
	/** 首页——获取试用产品列表 */
	public static final String URL_LISTPRODUCTS = URL_CONTEXTPATH + "act=try&op=list";
	public static final String TYPE_LISTPRODUCTS = "listproducts";
	/** 首页——获取试用产品详情 */
	public static final String URL_PRODUCTINFO = URL_CONTEXTPATH + "act=try&op=info";
	public static final String TYPE_PRODUCTINFO = "productinfo";
	/** 首页——获取评论 */
	public static final String TYPE_GETPINGLUN = "getpinglun";
	/** 首页——发表评论 */
	public static final String URL_ADDCOMMENT = URL_CONTEXTPATH + "act=try&op=subComment";
	public static final String TYPE_ADDCOMMENT = "addcomment";
	/** 首页——申请试用 */
	public static final String URL_APPLYTRY = URL_CONTEXTPATH + "act=try&op=applyTry";
	public static final String TYPE_APPLEYTRY = "applytry";

	/**
	 * 往期回顾
	 */
	/** 往期回顾——获取试用报告 */
	public static final String URL_GETSHIYONGBAOGAO = URL_CONTEXTPATH + "act=try&op=report";
	public static final String TYPE_GETSHIYONGBAOGAO = "getshiyongbaogao";
	/** 往期回顾——获取中奖名单 */
	public static final String TYPE_GETZHONGJIANGMINGDAN = "zhongjiangmingdan";
	/** 往期回顾——确认地址 */
	public static final String URL_SURESPLACE = URL_CONTEXTPATH + "act=try&op=subTryMemberInfo";
	public static final String TYPE_SURESPLACE = "suresplace";
	/** 往期回顾——提交试用报告 */
	public static final String URL_ADDREPORT = URL_CONTEXTPATH + "act=try&op=subReport";
	public static final String TYPE_ADDREPORT = "addreport";
}
