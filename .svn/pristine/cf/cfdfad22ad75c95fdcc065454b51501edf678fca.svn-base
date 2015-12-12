package com.ncwcandroid.ui.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class Utils {

	/**
	* 改变目标字段字体大小、颜色、背景色
	* _num:目标文字前面的文字
	* num:目标文字
	* num_:目标文字后面的文字
	* tv:textview控件
	* color:目标字段字体颜色
	* background:目标字段背景色
	*/
	public static void setTextViewSpan(String _num,String num,String num_,TextView tv,int color,int background) {
	    SpannableString spanString = new SpannableString(_num+num+num_);
	    AbsoluteSizeSpan span = new AbsoluteSizeSpan(30);
	    ForegroundColorSpan colorspan = new ForegroundColorSpan(color);
	    BackgroundColorSpan backgroundspan = new BackgroundColorSpan(background);
	    spanString.setSpan(span, 0, num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    spanString.setSpan(colorspan, 0, num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    spanString.setSpan(backgroundspan, 0, num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    tv.append(spanString);
	}
	
	/**
	* 获取现在时间
	*
	* @return返回字符串格式 MM-dd HH:mm
	*/
	public static String getStringDate() {
	Date currentTime = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
	String dateString = formatter.format(currentTime);
	return dateString;
	}
	
}
