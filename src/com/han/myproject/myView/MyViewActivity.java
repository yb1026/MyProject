package com.han.myproject.myView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.han.myproject.R;

public class MyViewActivity extends Activity {

	private RoundProgressBar  roundprogress;
	private LifeWheelRadarGraph  fiveimage;
	
	
	
	private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myview_layout);
        btn = (Button)this.findViewById(R.id.btn);
        
        btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				roundprogress.start(90);
			}
		});
        
        roundprogress = (RoundProgressBar)this.findViewById(R.id.roundprogress);
        
        roundprogress.setProgress(50);
        
        fiveimage = (LifeWheelRadarGraph)this.findViewById(R.id.fiveimage);
        fiveimage.setVisibility(View.VISIBLE);
        fiveimage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				roundprogress.setProgress(10);
				
			}
		});
     
    }

}
