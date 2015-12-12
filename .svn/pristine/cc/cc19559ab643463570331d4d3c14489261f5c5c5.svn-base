package com.ncwcandroid.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TimerTextView extends TextView implements Runnable {

	private long mday;
	private long mhour, mmin, msecond;// 天，小时，分钟，秒
	private boolean run = false; // 是否启动了

	public TimerTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void run() {
		// 标示已经启动
		if (run) {
			ComputeTime();

			String md = mday + "";
			String mh = mhour + "";
			String mm = mmin + "";
			String ms = msecond + "";
			if (Integer.parseInt(md) < 10) {
				md = "0" + md;
			}
			if (Integer.parseInt(mh) < 10) {
				mh = "0" + mh;
			}
			if (Integer.parseInt(mm) < 10) {
				mm = "0" + mm;
			}
			if (Integer.parseInt(ms) < 10) {
				ms = "0" + ms;
			}
			String strTime = md + "天 " + mh + "时 " + mm + "分 " + ms + "秒";

			this.setText(strTime);

			postDelayed(this, 1000);
		} else {
			removeCallbacks(this);
		}
	}

	public void setTimes(long h, long m, long s) {
		mhour = h % 24;
		mmin = m;
		msecond = s;
		mday = h / 24;

	}

	/**
	 * 倒计时计算
	 */
	private void ComputeTime() {
		msecond--;
		if (msecond < 0) {
			msecond = 59;
			mmin--;
			if (mmin < 0) {
				mmin = 59;
				mhour--;
				if (mhour < 0) {
					mhour = 23;
					mday--;
					if (mday <= 0) {
						mday = 0;
					}
				}
			}
		}
	}

	public boolean isRun() {
		return run;
	}

	public void beginRun() {
		this.run = true;
		run();
	}

	public void stopRun() {
		this.run = false;
	}

}
