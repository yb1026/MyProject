package com.cultivator.myproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.cultivator.myproject.common.base.BaseActivity;
import com.cultivator.myproject.common.util.sys.RingerUtil;
import com.cultivator.myproject.common.util.sys.VibratorUtil;

public class VibratorRingActivity extends BaseActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ring_vibrator_test);

		initView();
	}


	public void initView() {
		getToolBar().setTitle("我的应用");



		findViewById(R.id.ring).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (!isringing) {
					//start
					handler.obtainMessage(RING).sendToTarget();
				} else {
					isringing = false;
				}
			}
		});

		findViewById(R.id.vibrator).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (!isvibrating) {
					//start
					handler.obtainMessage(VIBRA).sendToTarget();
				} else {
					isvibrating= false;
				}
			}
		});
	}




	private boolean isringing = false;
	private boolean isvibrating = false;
	private final static int RING= 1;
	private final static int VIBRA= 2;


	private Handler handler = new Handler(){
		@Override
		public synchronized  void handleMessage(final Message msg) {

			switch (msg.what) {
				case RING:
					RingerUtil.playRing(VibratorRingActivity.this,2000);
					break;
				case VIBRA:
					VibratorUtil.Vibrate(VibratorRingActivity.this, 2000);
					break;

			}



			this.postDelayed(new Runnable() {
				@Override
				public void run() {
					handler.obtainMessage(msg.what);
				}
			},4000);

		}
	};





}
