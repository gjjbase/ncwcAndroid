package com.ncwcandroid.ui.widget;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.adapter.ShareAdapter;
import com.ncwcandroid.ui.bean.ShareModel;
import com.ncwcandroid.ui.config.Globals;

/**
 * TODO<分享工具>
 * 
 * @author ZhuZiQiang
 * @data: 2014-7-21 下午2:45:38
 * @version: V1.0
 */

public class SharePopupWindow extends PopupWindow implements PlatformActionListener {

	private Context context;
	private PlatformActionListener platformActionListener;
	private ShareParams shareParams;
	private FlippingLoadingDialog flippingload;

	public SharePopupWindow(Context cx, FlippingLoadingDialog flippingload) {
		this.context = cx;
		this.flippingload = flippingload;
	}

	public PlatformActionListener getPlatformActionListener() {
		return platformActionListener;
	}

	public void setPlatformActionListener(PlatformActionListener platformActionListener) {
		this.platformActionListener = platformActionListener;
	}

	@SuppressLint("InflateParams")
	public void showShareWindow() {
		View view = LayoutInflater.from(context).inflate(R.layout.share_layout, null);
		GridView gridView = (GridView) view.findViewById(R.id.share_gridview);
		ShareAdapter adapter = new ShareAdapter(context);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setAdapter(adapter);

		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		// 取消按钮
		btn_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 销毁弹出框
				dismiss();
			}
		});

		// 设置SelectPicPopupWindow的View
		this.setContentView(view);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.MyDialogStyleBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		this.setAnimationStyle(R.style.MyDialogStyle);
		gridView.setOnItemClickListener(new ShareItemClickListener(this));
	}

	private class ShareItemClickListener implements OnItemClickListener {
		private PopupWindow pop;

		public ShareItemClickListener(PopupWindow pop) {
			this.pop = pop;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			share(position);
			pop.dismiss();
		}
	}

	/**
	 * 分享
	 * 
	 * @param position
	 */
	private void share(int position) {
		flippingload.show();
		if (position == 1) {
			qq();
		} else if (position == 4) {
			qzone();
		} else if (position == 5) {
			shortMessage();
		} else {
			Platform plat = null;
			plat = ShareSDK.getPlatform(context, getPlatform(position));
			if (platformActionListener != null) {
				plat.setPlatformActionListener(this);
			}

			plat.share(shareParams);
		}
	}

	/**
	 * 初始化分享参数
	 * 
	 * @param shareModel
	 */
	public void initShareParams(ShareModel shareModel) {
		if (shareModel != null) {
			ShareParams sp = new ShareParams();
			sp.setShareType(Platform.SHARE_TEXT);
			sp.setShareType(Platform.SHARE_WEBPAGE);
			sp.setUrl(shareModel.getUrl());
			sp.setImageUrl(shareModel.getImageUrl());
			sp.setTitle(Globals.SHARETIE);
			sp.setText(Globals.SHARECONTENT);
			shareParams = sp;
		}
	}

	/**
	 * 获取平台
	 * 
	 * @param position
	 * @return
	 */
	private String getPlatform(int position) {
		String platform = "";
		switch (position) {
		case 0:
			platform = Wechat.NAME;// Wechat.NAME
			break;
		case 1:
			platform = QQ.NAME;// QQ.NAME
			break;
		case 2:
			platform = SinaWeibo.NAME;// SinaWeibo.NAME
			break;
		case 3:
			platform = WechatMoments.NAME;// WechatMoments.NAME
			break;
		case 4:
			platform = QZone.NAME;// QZone.NAME
			break;
		case 5:
			platform = ShortMessage.NAME;// ShortMessage.NAME
			break;
		}
		return platform;
	}

	/**
	 * 分享到QQ空间
	 */
	private void qzone() {
		ShareParams sp = new ShareParams();
		sp.setTitle(Globals.SHARETIE);
		sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
		sp.setText(Globals.SHARECONTENT);
		sp.setImageUrl(shareParams.getImageUrl());
		sp.setComment("我对此分享内容的评论");
		sp.setSite(shareParams.getTitle());
		sp.setSiteUrl(shareParams.getUrl());

		Platform qzone = ShareSDK.getPlatform(context, "QZone");

		qzone.setPlatformActionListener(this); // 设置分享事件回调 //
		// 执行图文分享
		qzone.share(sp);
	}

	private void qq() {
		ShareParams sp = new ShareParams();
		sp.setTitle(Globals.SHARETIE);
		sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
		sp.setText(Globals.SHARECONTENT);
		sp.setImageUrl(shareParams.getImageUrl());
		sp.setComment("我对此分享内容的评论");
		sp.setSite(shareParams.getTitle());
		sp.setSiteUrl(shareParams.getUrl());
		Platform qq = ShareSDK.getPlatform(context, "QQ");
		qq.setPlatformActionListener(this);
		qq.share(sp);
	}

	/**
	 * 分享到短信
	 */
	private void shortMessage() {
		ShareParams sp = new ShareParams();
		sp.setAddress("");
		sp.setText(shareParams.getText() + "这是网址《" + shareParams.getUrl() + "》很给力哦！");

		Platform circle = ShareSDK.getPlatform(context, "ShortMessage");
		circle.setPlatformActionListener(this); // 设置分享事件回调
		// 执行图文分享
		circle.share(sp);
	}

	@Override
	public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg1 == Platform.ACTION_USER_INFOR) {
			if (flippingload != null) {
				flippingload.dismiss();
			}
		}

	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
		// TODO Auto-generated method stub
		if (arg1 == Platform.ACTION_USER_INFOR) {
			if (flippingload != null) {
				flippingload.dismiss();
			}
		}
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		if (arg1 == Platform.ACTION_USER_INFOR) {
			if (flippingload != null) {
				flippingload.dismiss();
			}
		}
	}

}
