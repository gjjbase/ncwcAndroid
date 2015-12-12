package com.ncwcandroid.ui.view.freetrial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.ncwcandroid.ui.R;

public class ShouYeJoinInfoActivity extends Activity {

	private Button btn_info_back;
	private ImageView img_back_06;
	private Bundle bundles;
	private int where;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouye_dialog_info);

		bundles = getIntent().getExtras();
		where = bundles.getInt("where");

		init();

	}

	private void init() {
		btn_info_back = (Button) findViewById(R.id.btn_info_back);
		img_back_06 = (ImageView) findViewById(R.id.img_back_06);

		btn_info_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(ShouYeJoinInfoActivity.this,
						ShouYeProductActivity.class);
				if (where == 0) {
					// 让shouyeproduct的button1_失效
					bundles.putInt("where", 1);
					in.putExtras(bundles);
				} else {
					Bundle bundle = new Bundle();
					bundle.putInt("where", bundles.getInt("where"));
					bundle.putString("qi_num", bundles.getString("qi_num"));
					bundle.putString("img", bundles.getString("img"));
					bundle.putString("id", bundles.getString("id"));
					bundle.putString("num", bundles.getString("num"));
					bundle.putString("persons", bundles.getString("persons"));
					bundle.putString("titletv", bundles.getString("titletv"));
					bundle.putString("price", bundles.getString("price"));
					bundle.putString("contenttv",
							bundles.getString("contenttv"));
					// 剩余时间
					bundle.putLong("h", bundles.getLong("h"));
					bundle.putLong("m", bundles.getLong("m"));
					bundle.putLong("s", bundles.getLong("s"));

					in.putExtras(bundle);
				}
				startActivity(in);
				finish();
			}
		});

		img_back_06.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}
}
