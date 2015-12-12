package com.ncwcandroid.ui.view.perscenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.util.SharepreUtil;

/**
 * 性别编辑页
 * 
 * @author Administrator
 * 
 */
public class SetSexActivity extends BaseActivity {
	private RadioGroup group_sex;
	private RadioButton sex_man, sex_woman, sex_none;
	private String strsex;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setsex);
		AppManager.getInstance().addActivity(SetSexActivity.this);
		group_sex = (RadioGroup) findViewById(R.id.group_sex);
		sex_man = (RadioButton) findViewById(R.id.sex_man);
		sex_woman = (RadioButton) findViewById(R.id.sex_woman);
		sex_none = (RadioButton) findViewById(R.id.sex_none);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		strsex = getIntent().getStringExtra("strsex");
		switch (SharepreUtil.getIntValue(getApplicationContext(), Globals.NUMSEX, Globals.SEXCON)) {
		case Globals.SEXMAN:
			sex_man.setChecked(true);
			break;
		case Globals.SEXWOMEN:
			sex_woman.setChecked(true);
			break;
		case Globals.SEXCON:
			sex_none.setChecked(true);
			break;
		default:
			sex_none.setChecked(true);
			break;
		}
		group_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg1 == sex_man.getId()) {
					strsex = sex_man.getText().toString().trim();
				} else if (arg1 == sex_woman.getId()) {
					strsex = sex_woman.getText().toString().trim();
				} else if (arg1 == sex_none.getId()) {
					strsex = sex_none.getText().toString().trim();
				}
			}
		});
	}

	public void infinish(View v) {
		/**
		 * 传递数据至PerSetActivity
		 */
		setResult(Activity.RESULT_OK, new Intent().putExtra("strsex", strsex));
		finish();
	}

}
