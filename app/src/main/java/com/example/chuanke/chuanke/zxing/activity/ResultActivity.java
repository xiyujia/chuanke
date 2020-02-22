package com.example.chuanke.chuanke.zxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.DeviceDetailActivity;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.ScreenDetailBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.example.chuanke.chuanke.zxing.decode.DecodeThread;


public class ResultActivity extends BaseActivity {

	private ImageView mResultImage;
	private TextView mResultText;
	private ScreenDetailBean screenDetailBean;
	private String sid;


	@Override
	public int getLayoutFile() {
		return R.layout.activity_result;
	}

	@Override
	public void initView() {
		topBar.setText("设备信息");
	}

	@Override
	public void initEvent() {

	}

	@Override
	public void initData() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();

		mResultImage = findViewById(R.id.result_image);
		mResultText = findViewById(R.id.result_text);

		if (null != extras) {
//			int width = extras.getInt("width");
//			int height = extras.getInt("height");

//			LayoutParams lps = new LayoutParams(width, height);
//			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//
//			mResultImage.setLayoutParams(lps);

			String result = extras.getString("result");

			sid = result;

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid",sid);
			HttpUtil.doJsonPost(handler, URL.BASE_URL+ "api/Lists/screen",jsonObject.toJSONString());

//			mResultText.setText(result);

			Bitmap barcode = null;
			byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
			if (compressedBitmap != null) {
				barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
				// Mutable copy:
				barcode = barcode.copy(Bitmap.Config.RGB_565, true);
			}

//			mResultImage.setImageBitmap(barcode);
		}
	}

	@Override
	protected void initSetting() {

	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			String result = (String) msg.obj;
			if (result != null && !result.equals("null") && !result.equals("")) {
                Intent intent = new Intent(ResultActivity.this, DeviceDetailActivity.class);
                intent.putExtra("sid",sid);
				startActivity(intent);
				finish();
			} else {
				mResultImage.setVisibility(View.VISIBLE);
				mResultText.setText("设备走丢了，请扫描正确的二维码");
			}
		}
	};

	@Override
	public void onClick(View view) {

	}
}
