package com.cultivator.myproject;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cultivator.myproject.imageloader.HomeActivity;
import com.cultivator.myproject.myView.MyViewActivity;
import com.cultivator.myproject.web.MyWebViewActivity;

public class MainActivity extends Activity {

	private Button myview;

	private Button imageloader;

	private TextView show;

	private Button webviewbtn;
	
	private Button fullscreen;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myview = (Button) this.findViewById(R.id.myview);
		imageloader = (Button) this.findViewById(R.id.imageloader);
		webviewbtn = (Button) this.findViewById(R.id.webviewbtn);
		fullscreen = (Button) this.findViewById(R.id.fullscreen);
		
		myview.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MainActivity.this.startActivity(new Intent(MainActivity.this,
						MyViewActivity.class));
			}
		});
		imageloader.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MainActivity.this.startActivity(new Intent(MainActivity.this,
						HomeActivity.class));
			}
		});

		show = (TextView) this.findViewById(R.id.show);

		show.setText(new Date().toString());

		webviewbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MainActivity.this.startActivity(new Intent(MainActivity.this,
						MyWebViewActivity.class));
			}
		});

		imageloader.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MainActivity.this.startActivity(new Intent(MainActivity.this,
						HomeActivity.class));
			}
		});
		
		fullscreen.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if(iffull){
					quitFullScreen();
				}else{
					setFullScreen();
				}
			}
		});
	}

	private boolean iffull = false;
	
	private void setFullScreen() {
		iffull= true;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	private void quitFullScreen() {
		iffull= false;
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

}
