package com.ncwcandroid.ui.view.freetrial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.ncwcandroid.ui.R;

public class ShouYeInfoProductDialogActivity extends Activity {

	private Button btn_dialog_shouye;
	private Bundle bundles;
	private ImageView nowei;
	private int where;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_shouye);

		init();

		setListener();

	}

	private void init() {
		btn_dialog_shouye = (Button) findViewById(R.id.btn_dialog_shouye);
		nowei = (ImageView) findViewById(R.id.nowei);
		bundles = getIntent().getExtras();
		where = bundles.getInt("where");
	}

	private void setListener() {
		btn_dialog_shouye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (where == 0) {
					Intent in = new Intent(ShouYeInfoProductDialogActivity.this, ShouYeJoinInfoActivity.class);
					in.putExtras(bundles);
					startActivity(in);
				} else {
					Intent in = new Intent(ShouYeInfoProductDialogActivity.this, ShouYeJoinInfoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("where", bundles.getInt("where"));
					bundle.putString("qi_num", bundles.getString("qi_num"));
					bundle.putString("img", bundles.getString("img"));
					bundle.putString("id", bundles.getString("id"));
					bundle.putString("num", bundles.getString("num"));
					bundle.putString("persons", bundles.getString("persons"));
					bundle.putString("titletv", bundles.getString("titletv"));
					bundle.putString("contenttv", bundles.getString("contenttv"));
					bundle.putString("price", bundles.getString("price"));
					// 剩余时间
					bundle.putLong("h", bundles.getLong("h"));
					bundle.putLong("m", bundles.getLong("m"));
					bundle.putLong("s", bundles.getLong("s"));

					in.putExtras(bundle);
					startActivity(in);
				}
				finish();
			}
		});

		nowei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences sp = getSharedPreferences("isClose", Activity.MODE_PRIVATE);
				SharedPreferences.Editor ed = sp.edit();
				if (sp.getBoolean("isClose", false) == false) {
					ed.clear();
					ed.putBoolean("isClose", true);
					nowei.setImageResource(R.drawable.yixuanzhong);
				} else {
					ed.clear();
					ed.putBoolean("isClose", false);
					nowei.setImageResource(R.drawable.weixuanzhong);
				}
				ed.commit();
			}
		});

	}

}
