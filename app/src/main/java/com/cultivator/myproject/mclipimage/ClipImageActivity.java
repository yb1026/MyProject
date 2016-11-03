package com.cultivator.myproject.mclipimage;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cultivator.myproject.R;
import com.cultivator.myproject.common.base.BaseActivity;
import com.cultivator.myproject.mclipimage.view.ClipImageLayout;
/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 * @author zhy
 *
 */
public class ClipImageActivity extends BaseActivity
{
	private ClipImageLayout mClipImageLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clipimage_layout);

		mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);


		getToolBar().setTitleRight("剪切");
		getToolBar().setRightActionBarOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap bitmap = mClipImageLayout.clip();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] datas = baos.toByteArray();

				Intent intent = new Intent(ClipImageActivity.this, ShowImageActivity.class);
				intent.putExtra("bitmap", datas);
				startActivity(intent);
			}
		});
	}


}
