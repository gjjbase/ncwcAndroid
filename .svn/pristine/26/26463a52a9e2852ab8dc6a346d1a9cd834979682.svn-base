package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.adapter.RecoEditAdapter;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.IXListViewListener;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.DateTimeUtil;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;
import com.ncwcandroid.ui.view.adreview.WangQiProductActivity;
import com.ncwcandroid.ui.xlistview.XListView;

/**
 * 活动记录页面
 * 
 * @author Administrator
 * 
 */
public class RecoActionActivity extends BaseActivity implements IXListViewListener, VolleyStringListener {
	private XListView list_edit;
	private RecoEditAdapter adapter;
	private String date;
	private boolean bolg = true;
	private int page = 1;
	private int hasmore;
	private JSONArray jsonarray;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(RecoActionActivity.this);
		setContentView(R.layout.activity_setads);
		list_edit = (XListView) findViewById(R.id.list_edit);
		list_edit.setXListViewListener(this);
		list_edit.setPullLoadEnable(true);
		onLoad();
	}

	@Override
	public void initData() {

	}

	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 1;
		onLoad();
	}

	public void onLoadMore() {
		// TODO Auto-generated method stub
		page++;
		onLoad();
	}

	public void onLoad() {
		// TODO Auto-generated method stub
		VolleyHttpUtil vtp = VolleyHttpUtil.getInstance(getApplicationContext());
		vtp.UserInfoRed(RecoActionActivity.this, SharepreUtil.getStringValue(getApplicationContext(), Globals.MEMBERID, ""), SharepreUtil.getStringValue(getApplicationContext(), Globals.LOGINKEY, ""), page, bolg);
		vtp.setObjectList(this);
		if (bolg) {
			list_edit.setRefreshTime(":" + DateTimeUtil.getNowTime());
			bolg = false;
		} else {
			list_edit.setRefreshTime(":" + date);
		}
		date = DateTimeUtil.getNowTime();
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		Log.e("@@@@@@", response);
		try {
			JSONObject jso = new JSONObject(response);
			if (jso.getInt("code") == Globals.CODE) {
				hasmore = jso.getJSONObject(Globals.DATAS).getInt("hasmore");
				JSONArray jsona = jso.getJSONObject(Globals.DATAS).getJSONArray("list");
				if (page == 1)
					jsonarray = new JSONArray();
				for (int i = 0; i < jsona.length(); i++)
					jsonarray.put(jsona.getJSONObject(i));
				if (page == 1) {
					adapter = new RecoEditAdapter(getApplicationContext(), jsonarray);
					list_edit.setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
				}
				if (hasmore == 0 && page != 1) {
					GlobalUtil.showToast(getApplicationContext(), "没有更多数据了");
				}
				list_edit.stopRefresh();
				list_edit.stopLoadMore();
				list_edit.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						try {
							Intent in = new Intent(new Intent(getApplicationContext(), WangQiProductActivity.class));
							in.putExtra("id", jsonarray.getJSONObject(arg2 - 1).getString("try_id"));
							in.putExtra("img", jsonarray.getJSONObject(arg2 - 1).getString("img"));
							in.putExtra("big_img", jsonarray.getJSONObject(arg2 - 1).getString("big_img"));
							in.putExtra("qi_num", jsonarray.getJSONObject(arg2 - 1).getString("period_no"));
							in.putExtra("name", jsonarray.getJSONObject(arg2 - 1).getString("title"));
							in.putExtra("small_info", jsonarray.getJSONObject(arg2 - 1).getString("small_info"));
							in.putExtra("num", jsonarray.getJSONObject(arg2 - 1).getString("number"));
							in.putExtra("persons", jsonarray.getJSONObject(arg2 - 1).getString("try_people"));
							in.putExtra("price", jsonarray.getJSONObject(arg2 - 1).getString("price"));
							if (jsonarray.getJSONObject(arg2 - 1).getInt("status") == 3) {
								in.putExtra("prize", "1");
							} else {
								in.putExtra("prize", "0");
							}

							in.putExtra("presence", "1");
							startActivity(in);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
			list_edit.stopRefresh();
			list_edit.stopLoadMore();
		}
	}
}
