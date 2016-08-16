package com.cultivator.myproject.components;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

public class MyIntentService extends IntentService{

	public MyIntentService() {
		super("");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		new Handler(getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				
			}
		});
	}

}
