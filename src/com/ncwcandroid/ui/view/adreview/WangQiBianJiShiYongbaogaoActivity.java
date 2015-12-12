package com.ncwcandroid.ui.view.adreview;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ncwcandroid.ui.R;
import com.ncwcandroid.ui.base.AppManager;
import com.ncwcandroid.ui.base.BaseActivity;
import com.ncwcandroid.ui.config.Globals;
import com.ncwcandroid.ui.config.VolleyHttpUtil;
import com.ncwcandroid.ui.interfac.SelectReturn;
import com.ncwcandroid.ui.util.AsyncLoadingPicture;
import com.ncwcandroid.ui.util.CameraManager;
import com.ncwcandroid.ui.util.ImageTools;
import com.ncwcandroid.ui.util.ScreenUtils;
import com.ncwcandroid.ui.view.communal.NewDialog;
import com.ncwcandroid.ui.view.perscenter.LoginActivity;
import com.ncwcandroid.ui.widget.DialogSelect;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WangQiBianJiShiYongbaogaoActivity extends BaseActivity {

	// 照相机应用
	private File outFile;
	private List<Bitmap> listbitmap;
	private Uri uri;
	private Bitmap bitmap, last_bitmap, bitmap64;
	private boolean choose = true;
	private Button btn_sybaogao;
	private ImageView img_back_03, img_sybaogao_xieyi, img_sybaogao_camera, img_sybaogao;
	private TextView tv_sybaogao_xieyi, tv_sybaogao_title, tv_sybaogao_info;
	private EditText ed_sybaogao_waiguan, ed_sybaogao_xingneng, ed_sybaogao_price;
	private RatingBar star_sybaogao;
	private String try_id;
	private String appearance_info, quality_info, price_info, score;
	private String title_, info_, img_;
	private VolleyHttpUtil vhu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(WangQiBianJiShiYongbaogaoActivity.this);
		setContentView(R.layout.wangqi_shiyongbaogao);
		init();
		setListener();

	}

	private void init() {
		btn_sybaogao = (Button) findViewById(R.id.btn_sybaogao);
		img_back_03 = (ImageView) findViewById(R.id.img_back_03);
		img_sybaogao_xieyi = (ImageView) findViewById(R.id.img_sybaogao_xieyi);
		tv_sybaogao_xieyi = (TextView) findViewById(R.id.tv_sybaogao_xieyi);
		img_sybaogao_camera = (ImageView) findViewById(R.id.img_sybaogao_camera);
		ed_sybaogao_waiguan = (EditText) findViewById(R.id.ed_sybaogao_waiguan);
		ed_sybaogao_xingneng = (EditText) findViewById(R.id.ed_sybaogao_xingneng);
		ed_sybaogao_price = (EditText) findViewById(R.id.ed_sybaogao_price);
		star_sybaogao = (RatingBar) findViewById(R.id.star_sybaogao);

		img_sybaogao = (ImageView) findViewById(R.id.img_sybaogao);
		tv_sybaogao_title = (TextView) findViewById(R.id.tv_sybaogao_title);
		tv_sybaogao_info = (TextView) findViewById(R.id.tv_sybaogao_info);

		title_ = getIntent().getStringExtra("title");
		info_ = getIntent().getStringExtra("info");
		img_ = getIntent().getStringExtra("img");

		if (title_.length() > 18) {
			tv_sybaogao_title.setText(title_.substring(0, 18) + "...");
		} else {
			tv_sybaogao_title.setText(title_);
		}
		tv_sybaogao_info.setTextSize(10);
		if (info_.length() > 40) {
			tv_sybaogao_info.setText(info_.substring(0, 40) + "...");
		} else {
			tv_sybaogao_info.setText(info_);
		}
		AsyncLoadingPicture.getInstance(this).LoadPicture(img_, img_sybaogao);

		vhu = VolleyHttpUtil.getInstance(this);
		try_id = getIntent().getStringExtra("id");
	}

	private void setListener() {
		btn_sybaogao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gethttp();
			}
		});

		img_back_03.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		img_sybaogao_xieyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (choose) {
					choose = false;
					img_sybaogao_xieyi.setImageResource(R.drawable.weixuanzhong);
				} else {
					choose = true;
					img_sybaogao_xieyi.setImageResource(R.drawable.yixuanzhong);
				}
			}
		});

		tv_sybaogao_xieyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(WangQiBianJiShiYongbaogaoActivity.this, NewDialog.class).putExtra("type", "old"));
			}
		});

		img_sybaogao_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Toast.makeText(WangQiBianJiShiYongbaogaoActivity.this, "照相",
				 * Toast.LENGTH_SHORT).show();
				 */
				DialogSelect dialogselect = new DialogSelect(WangQiBianJiShiYongbaogaoActivity.this);
				dialogselect.getWindow().setGravity(Gravity.BOTTOM);
				dialogselect.show();
				dialogselect.getWindow().setLayout(ScreenUtils.getScreenWidth(getApplicationContext()), LayoutParams.WRAP_CONTENT);
				dialogselect.setCanceledOnTouchOutside(true);
				dialogselect.getSelectReturnListener(new SelectReturn() {

					public void phoTo() {
						// TODO Auto-generated method stub
						/* 拍照 */
						CameraManager.openCamera(WangQiBianJiShiYongbaogaoActivity.this, outFile);
					}

					@Override
					public void exit() {
						// TODO Auto-generated method stub
						/** 退出 */
					}

					@Override
					public void alBum() {
						// TODO Auto-generated method stub
						/** 相册 */
						CameraManager.openAlbum(WangQiBianJiShiYongbaogaoActivity.this);
					}
				});
			}
		});
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				String returnJson = msg.getData().getString("returnJson");
				// Log.e("@@@@====@@@@", returnJson);
				try {
					JSONObject obj = new JSONObject(returnJson);
					int code = obj.getInt("code");
					if (code == 200) {
						JSONObject datas = obj.getJSONObject("datas");
						int stauts = datas.getInt("status");
						String error = "";
						if (stauts != 1) {
							error = datas.getString("error");
						}
						if (stauts == 0) {// 异常
							Toast.makeText(WangQiBianJiShiYongbaogaoActivity.this, error, Toast.LENGTH_SHORT).show();
						} else if (stauts == 100) {// 未登录状态
							Toast.makeText(WangQiBianJiShiYongbaogaoActivity.this, error, Toast.LENGTH_SHORT).show();
							startActivity(new Intent(WangQiBianJiShiYongbaogaoActivity.this, LoginActivity.class));
						} else {// 成功
							startActivity(new Intent(WangQiBianJiShiYongbaogaoActivity.this, WangQiHuikuiGanxieActivity.class));
							finish();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	};

	private void gethttp() {
		appearance_info = ed_sybaogao_waiguan.getText().toString();
		quality_info = ed_sybaogao_xingneng.getText().toString();
		price_info = ed_sybaogao_price.getText().toString();
		score = star_sybaogao.getRating() + "";

		if (TextUtils.isEmpty(ed_sybaogao_waiguan.getText()) || TextUtils.isEmpty(ed_sybaogao_xingneng.getText()) || TextUtils.isEmpty(ed_sybaogao_price.getText())) {
			Toast.makeText(this, Globals.UNFULL, Toast.LENGTH_SHORT).show();
		} else if (last_bitmap == null) {
			Toast.makeText(this, Globals.UNPIC, Toast.LENGTH_SHORT).show();
		} else {
			vhu.GiveShiYongBaoGao(this, try_id, appearance_info, score, quality_info, price_info, bitmap64, handler);

			ed_sybaogao_waiguan.setText("");
			ed_sybaogao_xingneng.setText("");
			ed_sybaogao_price.setText("");
			star_sybaogao.setRating(0);
		}

	}

	@Override
	public void initData() {
		File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
		listbitmap = new ArrayList<Bitmap>();
	}

	/**
	 * 获取相册
	 * 
	 * @param contentUri
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public String getRealPathFromURI(Uri contentUri) {
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} catch (Exception e) {
			return contentUri.getPath();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Globals.INTENT_ACTION_PICTURE && resultCode == Activity.RESULT_OK && null != data) {
			uri = data.getData();
			bitmap64 = ImageTools.compressImageFromFile(getRealPathFromURI(uri));
			if (uri != null) {
				CameraManager.openCrop(WangQiBianJiShiYongbaogaoActivity.this, uri);
			} else {

			}
		} else if (requestCode == Globals.INTENT_ACTION_CAREMA && resultCode == Activity.RESULT_OK) {
			bitmap64 = ImageTools.compressImageFromFile(outFile.getAbsolutePath());
			CameraManager.openCrop(WangQiBianJiShiYongbaogaoActivity.this, Uri.fromFile(outFile));
		} else if (requestCode == Globals.INTENT_ACTION_CROP && resultCode == Activity.RESULT_OK && null != data) {
			if (data != null) {
				bitmap = data.getParcelableExtra("data");
				listbitmap.add(bitmap);
				last_bitmap = bitmap;
				img_sybaogao_camera.setImageBitmap(last_bitmap);
			}
			ImageLoader.getInstance().clearDiskCache();
			ImageLoader.getInstance().clearMemoryCache();
			try {
				outFile.delete();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
