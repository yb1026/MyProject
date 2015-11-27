package com.han.myproject;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.han.myproject.clipimage.SystemClipActivity;
import com.han.myproject.imageloader.HomeActivity;
import com.han.myproject.myView.MyViewActivity;

public class MainActivity extends Activity {

	
	
	
	private Button myview;
	
	private Button imageloader;
	
	private TextView  show;
	
	
	
	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myview = (Button)this.findViewById(R.id.myview);
        imageloader = (Button)this.findViewById(R.id.imageloader);
        myview.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MainActivity.this.startActivity(new Intent(MainActivity.this,MyViewActivity.class));
			}
		});
        imageloader.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MainActivity.this.startActivity(new Intent(MainActivity.this,HomeActivity.class));
			}
		});
   
        show =  (TextView)this.findViewById(R.id.show);
        
        show.setText(new Date().toString());
        
        
        show.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MainActivity.this.startActivity(new Intent(MainActivity.this,SystemClipActivity.class));
			}
		});
    }

}
