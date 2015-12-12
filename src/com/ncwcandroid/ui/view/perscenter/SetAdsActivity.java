package com.ncwcandroid.ui.view.perscenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.adapter.AdsEditAdapter;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.AdsEditListener;
import com.ncwcandroid.ui.interfac.VolleyStringListener;
import com.ncwcandroid.ui.util.GlobalUtil;
import com.ncwcandroid.ui.util.SharepreUtil;

/**
 * 地址编辑页面
 * 
 * @author Administrator
 * 
 */
public class SetAdsActivity extends BaseActivity implements VolleyStringListener, OnClickListener {
	private ListView list_ads;
	private AdsEditAdapter adapter;
	private ImageView add_edit;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ads);
		initMsg();
	}

	private void initMsg() {
		// TODO Auto-generated method stub
		VolleyHttpUtil vtp = new VolleyHttpUtil(getApplicationContext());
		vtp.ListAds(SetAdsActivity.this);
		vtp.setObjectList(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		list_ads = (ListView) findViewById(R.id.list_ads);
		add_edit = (ImageView) findViewById(R.id.add_edit);
		/* 增加地址* */
		add_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent().setClass(getApplicationContext(), AddAdsActivity.class).putExtra("type", "perset"));
			}
		});
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		initMsg();
	}

	@Override
	public void ReturnVolleyString(String response, String type) {
		// TODO Auto-generated method stub
		try {
			JSONObject jso = new JSONObject(response);
			if (jso.getInt("code") == Globals.CODE) {
				JSONObject json = jso.getJSONObject(Globals.DATAS);
				if (json.getInt(Globals.STATUS) == 1) {
					final JSONArray jsonArray = json.getJSONArray("list");
					adapter = new AdsEditAdapter(getApplicationContext(), jsonArray);
					list_ads.setAdapter(adapter);
					if (jsonArray.length() == 0) {
						try {
							SharepreUtil.putStringValue(getApplicationContext(), Globals.ADDRESS, "");
							SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSCODE, "");
							SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSTURE, "");
							SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSMOB, "");
						} catch (Exception e) {
							// TODO: handle
							// exception
						}
					}
					/** 编辑删除操作 */
					adapter.setAdsEditListener(new AdsEditListener() {

						@Override
						public void onEdit(int id) {
							// TODO Auto-generated method stub
							try {
								Intent intent = new Intent();
								intent.putExtra("true_name", jsonArray.getJSONObject(id).getString("true_name"));
								intent.putExtra("mob_phone", jsonArray.getJSONObject(id).getString("mob_phone"));
								intent.putExtra("address", jsonArray.getJSONObject(id).getString("address"));
								intent.putExtra("address_id", jsonArray.getJSONObject(id).getString(Globals.ADDRESSID));
								intent.putExtra("zip_code", jsonArray.getJSONObject(id).getString(Globals.ZIPCODE));
								intent.putExtra("is_default", jsonArray.getJSONObject(id).getString("is_default"));
								intent.setClass(getApplicationContext(), EditAdsActivity.class);
								startActivity(intent);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						@Override
						public void onDelect(final int id) throws JSONException {
							VolleyHttpUtil vtpde = VolleyHttpUtil.getInstance(getApplicationContext());
							try {
								vtpde.DelAds(SetAdsActivity.this, jsonArray.getJSONObject(id).getString(Globals.ADDRESSID));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							vtpde.setObjectList(new VolleyStringListener() {

								@Override
								public void ReturnVolleyString(String response, String type) {
									// TODO Auto-generated method stub

									try {
										JSONObject jso = new JSONObject(response);
										JSONObject json = jso.getJSONObject(Globals.DATAS);
										if (jso.getInt("code") == Globals.CODE) {
											try {
												GlobalUtil.showToast(getApplicationContext(), json.getString(Globals.ERROE));
											} catch (Exception e) {
												// TODO: handle exception
											}
											try {
												GlobalUtil.showToast(getApplicationContext(), json.getString(Globals.MSG));
												initMsg();
											} catch (Exception e) {
												// TODO: handle exception
											}
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});

						}

						@Override
						public void SetDef(final int id) throws JSONException {
							// TODO Auto-generated method stub

							VolleyHttpUtil vtp = new VolleyHttpUtil(getApplicationContext());
							try {
								vtp.DefAds(SetAdsActivity.this, jsonArray.getJSONObject(id).getString(Globals.ADDRESSID));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							vtp.setObjectList(new VolleyStringListener() {

								@Override
								public void ReturnVolleyString(String response, String type) {
									// TODO Auto-generated method stub
									try {
										JSONObject jso = new JSONObject(response);
										if (jso.getInt("code") == Globals.CODE) {
											if (jso.getJSONObject(Globals.DATAS).getInt(Globals.STATUS) == 1) {
												GlobalUtil.showToast(getApplicationContext(), "设置成功");

												try {
													SharepreUtil.putStringValue(getApplicationContext(), Globals.ADDRESS, jsonArray.getJSONObject(id).getString(Globals.ADSINFO) + jsonArray.getJSONObject(id).getString(Globals.ADDRESS));
													SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSCODE, jsonArray.getJSONObject(id).getString(Globals.ZIPCODE));
													SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSTURE, jsonArray.getJSONObject(id).getString(Globals.TRNAME));
													SharepreUtil.putStringValue(getApplicationContext(), Globals.ADSMOB, jsonArray.getJSONObject(id).getString(Globals.ADSMOB));
												} catch (Exception e) {
													// TODO: handle
													// exception
												}
												initMsg();
											} else {
												GlobalUtil.showToast(getApplicationContext(), "设置失败");
											}
										}
									} catch (Exception e) {
										// TODO: handle exception
									}

								}
							});

						}
					});
				} else {

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
