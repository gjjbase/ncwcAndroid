package com.ncwcandroid.ui.config;

/**
 * 全局常量
 * 
 * @author Hy
 * 
 */
public final class Globals {

	/** 抛出异常 */
	public static final String MSG_WHAT_2 = "服务繁忙，请稍后再试";
	/** 登录注册 **/
	public static final String REWRITE = "请重新填写信息";
	/** 没有数据 */
	public static final String NOINFO = "暂时没有数据";
	/** 连接错误 **/
	public static final String CONNERROE = "连接超时";
	public static final String FAULTERROE = "网络故障，请先检查网络连";
	/** 判断是否注册的消息 */
	public static final String NOREGISTER = "请注册成为会员";
	/** 对话框的返回参数 */
	public final static int RESULT_CODE = 101;
	public final static int REQUEST_CODE = 1;
	/** 打开相册，并截图 */
	public static final int INTENT_ACTION_PICTURE = 0;
	/** 打开相机照相 */
	public static final int INTENT_ACTION_CAREMA = 1;
	/** 照相后，截图 */
	public static final int INTENT_ACTION_CROP = 2;
	/** 个人信息 图片名字 */
	public static final String PICTURE_NAME = "userIcon.jpg";

	/** 判断注册时的错误信息 */
	public final static String NEEDLOGIN = "请先登陆";
	public final static String FINISH = "再按一次退出程序";
	public final static String NOPHONO = "手机号不能为空";
	public final static String YESPHONO = "请输入正确的手机号";
	public final static String NOEMAIL = "请输入正确的邮箱";
	public final static String RCODE = "请输入正确的邮编";
	public final static String YESEMAIL = "邮箱不能为空";
	public final static String NOCODE = "请输入正确的验证码";
	public final static String NOPSAA = "密码不能为空";
	public final static String NORPSAA = "2次输入的密码不一致";
	public final static String NOCHECK = "请同意用户协议";
	public final static String NOUSERNAME = "昵称不能为空";
	public final static String NONAME = "账号不能为空";
	public final static String NOMODF = "没有修改的内容";
	public final static String PUTADS = "请填写默认收货地址";
	public final static String REGSUC = "注册成功";
	public final static String NOMSG = "暂未开通，尽请期待";
	public final static String DEFADS = "已是默认地址";
	public final static String RETURNMSG = "修改成功,请重新登陆";
	public final static String DAYUSHI = "姓名不能超出十个字";
	/** 注册的2种方式 */
	public final static String PHONERIGHT = "手机注册";
	public final static String EMAILRIGHT = "邮箱注册";
	/** 底部菜单栏 */
	public final static String FREETRIAL = "免费试用";
	public final static String PROPROID = "往期回顾";
	public final static String PERCENTER = "个人中心";
	/** 缓存到本地login key的参数 */
	public final static String LOGINKEY = "key";
	/** 记住密码 */
	public final static String REMBERPSW = "remberpsw";
	public final static String NOREMBERPSW = "noremberpsw";
	public final static String REMUSERNAME = "remusername";
	public final static String REMPSW = "rempsw";
	/** 缓存到本地的mobile 参数 */
	public final static String MOBILE = "mobile";
	/** 缓存到本地的登陆用户的id */
	public final static String MEMBERID = "member_id";
	/** 缓存到本地用户的昵称 */
	public final static String TRUENAME = "member_truename";
	/** 缓存到本地的用户地址昵称 */
	public final static String TRNAME = "true_name";
	/** 缓存到本地的邮箱信息 */
	public final static String EMAIL = "email";
	/** 缓存到本地的用户性别 */
	public final static String SEX = "sex";
	public final static String NUMSEX = "numsex";
	/** 缓存到本地的密码的 */
	public final static String PSWD = "password";
	/** 判断用户是否修改了三方登陆的头像 */
	public final static String NOIMG = "NOIMG";
	public final static String YESIMG = "yesimg";
	/** 缓存到本地的性别类型 */
	public final static int SEXMAN = 1;
	public final static String MAN = "男";
	public final static int SEXWOMEN = 2;
	public final static String WOMEN = "女";
	public final static int SEXCON = 0;
	public final static String CON = "保密";
	/** 缓存到本地的用户username信息 */
	public final static String USERNAME = "username";
	/** 缓存到本地的头像信息 */
	public final static String AVATAR = "avatar";
	/** 缓存到本地的默认收货地址信息 */
	public final static String ADSINFO = "area_info";
	public final static String ADDRESS = "address";
	/** 缓存到本地的默认收货地址的姓名 */
	public final static String ADSTURE = "adstrue";
	/** 缓存到本地的默认邮编 */
	public final static String ADSCODE = "adscode";
	/** 缓存到本地的默认手机号码 */
	public final static String ADSMOB = "mob_phone";
	/** 缓存到本地的地址id */
	public final static String ADDRESSID = "address_id";
	/** 缓存到本地的邮编字段 */
	public final static String ZIPCODE = "zip_code";
	/** 缓存到本地的登陆成功status信息 */
	public final static String LOGINSUC = "loginsuc";
	public final static String LOGINFAI = "loginfai";
	/** 结束程序指令 */
	public final static String KILLKEY = "killkey";
	/** 缓存到本地服务端的错误信息 */
	public final static String ERROE = "error";
	/** 缓存到本地的服务端的消息 */
	public final static String MSG = "msg";
	/** 服务端返回code,data参数 */
	public final static int CODE = 200;
	public final static String DATAS = "datas";
	/** 返回的stauts */
	public final static String STATUS = "status";
	/** 判断SD的消息 */
	public final static String NOSDVSER = "SD不存在";
	/** 判断储存卡目录的信息 */
	public final static String NOSTCARD = "没有储存目录";
	/** 存放三方登录的数据 */
	public final static String PLATFROM = "platform";
	/** 登录的三种方式 */
	public final static String LOGINTYPE = "logintype";
	public final static int MOBILELONG = 1;
	public final static int EMAILLOGIN = 2;
	public final static int THIRDLOGIN = 3;
	/** 用户名登陆 */
	public final static int USERLOGIN = 4;
	/** 设置错误返回参数 */
	public final static int ERRORCODE = 0;
	/** 版本更新提示 */
	public final static int UPMSG = 1;
	/** 退出程序提示 */
	public final static int FINISHAPP = 2;
	/** 填写推荐人提示 */
	public final static int REFCODE = 3;
	/** 在edittext没有填写时提醒 */
	public final static String UNFULL = "请将信息填写完整";
	/** 提醒没有图片 */
	public final static String UNPIC = "请为试用产品拍照";
	/** 分享标题 */
	public final static String SHARETIE = "你车我车";
	/** 分享标题的链接 */
	public final static String SHARETITURL = "http://www.nichewoche.com";
	/** 分享的图片链接 */
	public final static String SHAREIMGURL = "http://img.nichewoche.com/logo.jpg";
	/** 分享内容 */
	public final static String SHARECONTENT = "寻找中国可信赖的汽车用品”你车我车“，所有产品免费试用!";
	/** 分享跳转的链接 */
	public final static String SHAREDOWN = "http://www.nichewoche.com/downapk/";
	/** 提交评论校验 */
	public final static String PINGLUNNULL = "请填写评论";
	/** 提交失败 */
	public final static String GIVEFALSE = "提交失败";
	/** 本地更新参数 */
	public final static String UPVER = "upver";
	public final static String UPYESVERMSG = "已是最新版本";
	public final static String UPVERMSG = "有最新版本V ";
	/** 请先绑定手机号 */
	public final static String NOPHONE = "请先绑定手机号";

}
