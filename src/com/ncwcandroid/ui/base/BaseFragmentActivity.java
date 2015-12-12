package com.ncwcandroid.ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class BaseFragmentActivity extends FragmentActivity {
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	}

	public void back(View v) {
		finish();
	}

	 
}
