package com.ncwcandroid.ui.view.adreview;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;

public class WangQiBigImageActivity extends Activity {

	private ImageView bigimage_wq;
	private String bigimg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigimage_wangqi);
		AppManager.getInstance().addActivity(WangQiBigImageActivity.this);
		bigimage_wq = (ImageView) findViewById(R.id.bigimage_wq);

		bigimg = getIntent().getStringExtra("bigimg");

		AsyncLoadingPicture.getInstance(this).LoadPicture(bigimg, bigimage_wq);

	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == event.ACTION_UP) {
			finish();
		}
		return true;
	}

}
