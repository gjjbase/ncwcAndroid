package com.ncwcandroid.ui.widget;

import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.ncwcandroid.ui.R;
/**
 * 请求的是加载的进度条显示
 * @author Administrator
 *
 */
public class FlippingLoadingDialog extends Dialog {

	private GifView xlistview_header_progressbar;

	public FlippingLoadingDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.common_flipping_loading_diloag);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		xlistview_header_progressbar = (GifView) findViewById(R.id.xlistview_header_progressbar);
		try {
			xlistview_header_progressbar.setGifImage(getContext().getResources().getAssets().open("loading.gif"));
			xlistview_header_progressbar.setGifImageType(GifImageType.COVER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			xlistview_header_progressbar.destroyDrawingCache();
			super.dismiss();
		}
	}
}
