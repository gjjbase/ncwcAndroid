package com.ncwcandroid.ui.interfac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ncwcandroid.ui.view.perscenter.LoginActivity;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent StartIntent = new Intent(context, LoginActivity.class); // 接收到广播后，跳转到MainActivity
		StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(StartIntent);
	}

}
