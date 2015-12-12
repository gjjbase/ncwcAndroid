package com.ncwcandroid.ui.view.adreview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.adapter.ZhongJiangMDAdapter;

public class WangQiZhongjiangMdActivity extends Activity {

	private ImageButton imgbtn_back;
	private ListView lv_zjmd;
	private ZhongJiangMDAdapter adapter;

	private String zj_mingdan;
	private JSONArray array;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wangqi_zhongjiangmingdan);
		zj_mingdan = getIntent().getStringExtra("zhongjiang");
		init();

	}

	private void init() {
		imgbtn_back = (ImageButton) findViewById(R.id.imgbtn_back);
		lv_zjmd = (ListView) findViewById(R.id.lv_zjmd);

		imgbtn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		try {
			JSONObject obj = new JSONObject(zj_mingdan);
			array = obj.getJSONArray("list");
			adapter = new ZhongJiangMDAdapter(this, array);
			lv_zjmd.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
