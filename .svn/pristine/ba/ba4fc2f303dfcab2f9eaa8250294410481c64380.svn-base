package com.ncwcandroid.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.view.adreview.WangQiProductActivity;

public class LoadFalse extends Activity {

	private Button button1;
	private int from;// 确定那个请求异常
	private Intent intent_all;

	// private Bundle bundle_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_false);
		AppManager.getInstance().addActivity(LoadFalse.this);
		intent_all = getIntent();
		// bundle_all = intent_all.getExtras();

		init();

	}

	private void init() {
		button1 = (Button) findViewById(R.id.button1);

		from = getIntent().getExtras().getInt("from");

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (from) {
				case 0:// 正在进行&即将开始
					@SuppressWarnings("unused")
					Intent in_0 = new Intent(LoadFalse.this, MainActivity.class);
					break;
				case 1:// 申请试用

					break;
				/*
				 * case 2:// 获取评论 Intent in_2 = new Intent(LoadFalse.this,
				 * MainActivity.class); break;
				 */
				case 3:// 提交评论

					break;
				case 4:// 往期回顾
						// Intent in_4 = new Intent(LoadFalse.this,
						// MainActivity.class);
					break;
				case 5:// 获取试用报告
						// id = in.getStringExtra("id");
						// qi_num = in.getStringExtra("qi_num");
						// title = in.getStringExtra("name");
						// text = in.getStringExtra("small_info");
						// num = in.getStringExtra("num");
						// persons = in.getStringExtra("persons");
					Intent in_5 = new Intent(LoadFalse.this,
							WangQiProductActivity.class);
					in_5.putExtra("id", intent_all.getStringExtra("id"));
					in_5.putExtra("big_img",
							intent_all.getStringExtra("big_img"));
					in_5.putExtra("qi_num", intent_all.getStringExtra("qi_num"));
					in_5.putExtra("name", intent_all.getStringExtra("name"));
					in_5.putExtra("small_info",
							intent_all.getStringExtra("small_info"));
					in_5.putExtra("num", intent_all.getStringExtra("num"));
					in_5.putExtra("persons",
							intent_all.getStringExtra("persons"));
					startActivity(in_5);
					break;
				case 6:// 确认地址

					break;
				case 7:// 提交试用报告

					break;
				case 8:// 获取获奖名单
					Intent in_8 = new Intent(LoadFalse.this, MainActivity.class);
					in_8.putExtra("zhongjiang",
							intent_all.getStringExtra("zhongjiang"));
					startActivity(in_8);
					break;
				case 9://

					break;
				case 10://

					break;
				}
				finish();
			}
		});
	}

}
