package com.ncwcandroid.ui.view.adreview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ncwcandroid.ui.R;

public class WangQiHuikuiGanxieActivity extends Activity {

	private ImageView img_back_04;
	private TextView textView2;
	private int a = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wangqi_tijiaoganxie);

		img_back_04 = (ImageView) findViewById(R.id.img_back_04);

		img_back_04.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// startActivity(new
				// Intent(WangQiHuikuiGanxieActivity.this,WangQiProductActivity.class));
				finish();
			}
		});

		textView2 = (TextView) findViewById(R.id.textView2);

		three_second();
	}

	private void three_second() {
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						if (a == 0) {
							finish();
						}
						textView2.setText(a + "秒后自动返回>>");
						a--;
					}
				});
				try {
					Thread.sleep(1000);
					three_second();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		th.start();
	}

}
