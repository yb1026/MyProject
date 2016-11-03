package com.cultivator.myproject.myView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cultivator.myproject.R;
import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.mclipimage.ShowImageActivity;

import java.io.ByteArrayOutputStream;

public class MyViewActivity extends Activity {

	private RoundProgressBar  roundprogress;
	private LifeWheelRadarGraph  fiveimage;
	
	
	
	private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myview);
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
            clipscreen();
			}
		});
     
    }



    private void clipscreen(){
        View  view = findViewById(R.id.layout);
        view.buildDrawingCache();


        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp =view.getDrawingCache();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();

        Intent intent = new Intent(this, ShowImageActivity.class);
        intent.putExtra("bitmap", datas);
        startActivity(intent);

        // 销毁缓存信息
        view.destroyDrawingCache();
    }

}
