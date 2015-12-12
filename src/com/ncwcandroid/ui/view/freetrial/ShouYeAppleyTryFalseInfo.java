package com.ncwcandroid.ui.view.freetrial;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.config.Globals;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class ShouYeAppleyTryFalseInfo extends Activity {

	private String msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shouye_appleytry_false);

		try {
			msg = getIntent().getStringExtra("msg");
		} catch (Exception e) {
			msg = Globals.MSG_WHAT_2;
		}
		if (msg == null || msg == "") {
			// msg = Globals.NOPHONE;
		} else {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

}
