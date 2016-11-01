package com.cultivator.myproject;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.cultivator.myproject.common.base.BaseActivity;
import com.cultivator.myproject.common.log.MyLog;
import com.cultivator.myproject.common.view.drage.DragCallback;
import com.cultivator.myproject.common.view.drage.DragGridView;
import com.cultivator.myproject.common.view.drage.DragGridViewAdapter;
import com.cultivator.myproject.common.view.drage.HomeGridItem;
import com.cultivator.myproject.mclipimage.ClipImageActivity;
import com.cultivator.myproject.myView.MyViewActivity;
import com.cultivator.myproject.web.CommonWebActivity;

public class MainActivity extends BaseActivity {



	private DragGridView mDragGridView;
	private DragGridViewAdapter mAdapter;
	private List<HomeGridItem> list = new ArrayList<>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}


	public void initView() {
		getToolBar().setTitle("我的应用");
		mDragGridView = (DragGridView) findViewById(R.id.home_grideview);
		initDragGrid();
	}

	public void initDragGrid() {
		initData();
		MyLog.d(getClass(), "initDragGrid...：" + list.size());
		if (mAdapter == null) {
			mAdapter = new DragGridViewAdapter(this);
			mAdapter.setlist(list);
			mDragGridView.setAdapter(mAdapter);
			mDragGridView.setDragCallback(new DragCallback() {
				@Override
				public void startDrag(int position) {
					Log.i("liuy", "start drag at " + position);
				}

				@Override
				public void endDrag(int position) {
					Log.i("liuy", "end drag at " + position);
				}
			});
			mDragGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					HomeGridItem item = list.get(position);
					if (item.getMipmapId() != 0) {
						startActivity(item.getClass());
						mDragGridView.clicked(position);
					}
				}
			});
			mDragGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

					HomeGridItem item = list.get(position);
					if (item.getMipmapId() != 0) {
						mDragGridView.startDrag(position);
					}
					return false;
				}
			});
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}


	private void initData(){

		String[] names = {"自定义组建",
				"裁切图片",
				"音屏震动",
				"全屏切换",
				"webview",
				"httpimages"};
		Class[] classes = {MyViewActivity.class,
				ClipImageActivity.class,
				VibratorRingActivity.class,
				FullscreenActivity.class,
				CommonWebActivity.class};

		for (int i = 0; i < names.length; i++) {
			HomeGridItem item = new HomeGridItem();
			item.setMipmapId(R.mipmap.ic_launcher);
			item.setName(names[i]);
			list.add(item);
		}

		HomeGridItem item = new HomeGridItem();
		for (int i = 0; i < list.size() % 4; i++) {
			list.add(item);
		}
	}






}
