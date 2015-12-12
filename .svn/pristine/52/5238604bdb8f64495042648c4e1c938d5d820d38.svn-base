package com.ncwcandroid.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.interfac.DialogListener;

public class UpDataDialog extends Dialog implements android.view.View.OnClickListener {
	private TextView txt_exit;
	private TextView txt_enter;
	private DialogListener dialglistener;
	private TextView msg;
	private int type;
	
	public UpDataDialog(Context context, int type) {
		super(context, R.style.MyDialogStyle);
		this.type = type;
		// TODO Auto-generated constructor stub
	}

	public void SetDialogListener(DialogListener dialglistener) {
		this.dialglistener = dialglistener;
	}

	public void onEnter() {
		if (dialglistener != null) {
			dialglistener.onEnter();
		}
	}

	public void onExit() {
		if (dialglistener != null) {
			dialglistener.onExit();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diapros);
		txt_enter = (TextView) findViewById(R.id.txt_enter);
		txt_exit = (TextView) findViewById(R.id.txt_exit);
		msg = (TextView) findViewById(R.id.msg);
		txt_enter.setOnClickListener(this);
		txt_exit.setOnClickListener(this);
		switch (type) {
		case Globals.UPMSG:
			txt_enter.setText(R.string.immdownload);
			msg.setText(R.string.upmsg);
			break;
		case Globals.FINISHAPP:
			txt_enter.setText(R.string.enter);
			msg.setText(R.string.finishappenter);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.txt_enter:
			onEnter();
			dismiss();
			break;
		case R.id.txt_exit:
			onExit();
			dismiss();
			break;

		}
	}
}
