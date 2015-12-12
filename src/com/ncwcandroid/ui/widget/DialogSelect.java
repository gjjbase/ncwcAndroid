package com.ncwcandroid.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.interfac.SelectReturn;
/**
 * 底部弹出对话框
 * @author Administrator
 *
 */
public class DialogSelect extends Dialog implements android.view.View.OnClickListener {
	TextView txt_album, txt_photo, txt_exit;
	private SelectReturn selectReturn;

	public void getSelectReturnListener(SelectReturn selectReturn) {
		this.selectReturn = selectReturn;
	}

	public DialogSelect(Context context) {
		super(context, R.style.MyDialogStyleBottom);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.botmselectlayout);
		txt_exit = (TextView) findViewById(R.id.txt_exit);
		txt_album = (TextView) findViewById(R.id.txt_album);
		txt_photo = (TextView) findViewById(R.id.txt_photo);

		txt_album.setOnClickListener(this);
		txt_photo.setOnClickListener(this);
		txt_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_exit:
			selectReturn.exit();
			dismiss();
			break;
		case R.id.txt_album:
			selectReturn.alBum();
			dismiss();
			break;
		case R.id.txt_photo:
			selectReturn.phoTo();
			dismiss();
			break;

		}
	}
}
