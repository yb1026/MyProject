package com.han.myproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private RoundProgressBar  roundprogress;
	private FiveDimensionView  fiveimage;
	
	
	
	private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)this.findViewById(R.id.btn);
        
        btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				roundprogress.start(90);
			}
		});
        
        roundprogress = (RoundProgressBar)this.findViewById(R.id.roundprogress);
        
        roundprogress.setProgress(50);
        
        fiveimage = (FiveDimensionView)this.findViewById(R.id.fiveimage);
        fiveimage.setVisibility(View.VISIBLE);
     
    }

}
