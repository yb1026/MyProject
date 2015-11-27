package com.han.myproject.clipimage;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.han.myproject.R;

public class SystemClipActivity extends Activity {

	private Button cemera;

	private Button picture;

	private ImageView show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.sys_clip_layout);

		cemera = (Button) this.findViewById(R.id.cemera);
		picture = (Button) this.findViewById(R.id.picture);
		cemera.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				getCemera();
			}
		});
		picture.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				getPictures();
			}
		});

		show = (ImageView) this.findViewById(R.id.show);

	}

	private final int getPictures = 0;
	private final int getCemera = 1;
	private final int goClip = 2;

	private String photoPath;

	private void getPictures() {

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		this.startActivityForResult(intent, getPictures);
	}

	private void getCemera() {
		String sdPath = "";

		// 存在sd卡
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			sdPath = sdDir.toString();
		}

		photoPath = sdPath + "/temps" + ".jpg";

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoPath)));  
		this.startActivityForResult(intent, getCemera);
	}

	private void goClip(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");// 可裁剪
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);// 若为false则表示不返回数据，返回Uri
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, goClip);
	}

	private Bitmap decodeFromUri(Uri uri) throws FileNotFoundException {
		return BitmapFactory.decodeStream(this.getContentResolver()
				.openInputStream(uri));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (getPictures == requestCode) {
			goClip(data.getData());
		}

		if (getCemera == requestCode) {
			goClip(Uri.fromFile(new File(photoPath)));
			
//			Uri uri = null;
//			
//			Bitmap bitmap = 	data.getParcelableExtra("data");// 获取相机返回的数据，并转换为Bitmap图片格式
//			if (data.getData() != null) {
//				uri = data.getData();
//			} else {
//				uri = Uri.parse(MediaStore.Images.Media.insertImage(
//						getContentResolver(), bitmap, null, null));
//			}
//			goClip(uri);
		}

		if (goClip == requestCode) {
			try {
				show.setImageBitmap(decodeFromUri(data.getData()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
