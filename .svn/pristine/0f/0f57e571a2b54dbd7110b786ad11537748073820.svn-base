package com.ncwcandroid.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.widget.ImageView;

public class MyImageButton extends ImageView {

	private String _text = "";
	private int _color = 0;
	private float _textsize = 0f;

	public MyImageButton(Context context) {
		super(context);
	}

	public void setText(String text) {
		this._text = text;
	}

	public void setColor(int color) {
		this._color = color;
	}

	public void setTextSize(float textsize) {
		this._textsize = textsize;
	}

	@SuppressLint({ "ResourceAsColor", "DrawAllocation" })
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint backpaint = new Paint();
		backpaint.setColor(Color.argb(180, 0, 162, 249));
		Paint textpaint = new Paint();
		textpaint.setTextAlign(Align.LEFT);
		textpaint.setColor(_color);
		textpaint.setTextSize(_textsize);
		canvas.drawRect(0, canvas.getHeight() - 74, canvas.getWidth(), canvas.getWidth(), backpaint);
		canvas.drawText(_text, 40, canvas.getHeight() - 27, textpaint);
	}

}
