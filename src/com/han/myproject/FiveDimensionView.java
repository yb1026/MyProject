package com.han.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.view.View;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 * 
 * @author xiaanming
 *
 */
public class FiveDimensionView extends View {

	private String bigColor = "#C0C0C0";
	private String smallColor = "#0E9BC9";
	private String textColor = "#000000";
	private float textSize = 60.0f;

	float[] bx = new float[5];
	float[] by = new float[5];
	float[] sx = new float[5];
	float[] sy = new float[5];

	private int score = 600;
	private float pro1 = 0.8f;
	private float pro2 = 0.5f;
	private float pro3 = 0.4f;
	private float pro4 = 0.5f;
	private float pro5 = 0.3f;

	public FiveDimensionView(Context context) {
		this(context, null);
	}

	public FiveDimensionView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FiveDimensionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		float centre = getWidth() / 2;
		initData(centre);

		Path bpath = new Path();
		bpath.moveTo(bx[0], by[0]);
		for (int i = 1; i < 5; i++) {
			bpath.lineTo(bx[i], by[i]);
		}
		bpath.close();
		ShapeDrawable bDrawable = new ShapeDrawable(new PathShape(bpath, 100,
				100));
		bDrawable.getPaint().setColor(Color.parseColor(bigColor));
		bDrawable.setBounds(0, 0, 100, 100);
		bDrawable.draw(canvas);

		Path spath = new Path();

		spath.moveTo(sx[0], sy[0]);
		for (int i = 1; i < 5; i++) {
			spath.lineTo(sx[i], sy[i]);
		}

		bpath.close();
		ShapeDrawable sDrawable = new ShapeDrawable(new PathShape(spath, 100,
				100));
		sDrawable.getPaint().setColor(Color.parseColor(smallColor));
		sDrawable.setBounds(0, 0, 100, 100);
		sDrawable.draw(canvas);

		// 十根线
		Paint linePaint = new Paint();
		linePaint.setStrokeWidth(1);
		linePaint.setColor(Color.parseColor("#666666"));

		for (int i = 0; i < 5; i++) {
			canvas.drawLine(centre, centre, bx[i], by[i], linePaint);
			int k = i + 1;
			if (k >= 5) {
				k = 0;
			}
			canvas.drawLine(bx[i], by[i], bx[k], by[k], linePaint);
		}

		Paint paint = new Paint();
		paint.setStrokeWidth(0);
		paint.setColor(Color.parseColor(textColor));
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
		float textWidth = paint.measureText(String.valueOf(score)); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间

		canvas.drawText(String.valueOf(score), centre - textWidth / 2, centre
				+ textSize / 3, paint); // 画出进度百分比

	}

	private void initData(float centre) {
		float len = centre / 2;
		bx[0] = centre;
		bx[1] = centre + cos18(len);
		bx[2] = centre + cos54(len);
		bx[3] = centre - cos54(len);
		bx[4] = centre - cos18(len);

		by[0] = centre - len;
		by[1] = centre - sin18(len);
		by[2] = centre + sin54(len);
		by[3] = centre + sin54(len);
		by[4] = centre - sin18(len);

		sx[0] = centre;
		sx[1] = centre + cos18(len * pro2);
		sx[2] = centre + cos54(len * pro3);
		sx[3] = centre - cos54(len * pro4);
		sx[4] = centre - cos18(len * pro5);

		sy[0] = centre - len * pro1;
		sy[1] = centre - sin18(len * pro2);
		sy[2] = centre + sin54(len * pro3);
		sy[3] = centre + sin54(len * pro4);
		sy[4] = centre - sin18(len * pro5);

	}

	private float cos18(float len) {
		Double retr = len * Math.cos(2 * Math.PI * 18 / 360);
		return Float.parseFloat(retr.toString());
	}

	private float sin18(float len) {
		Double retr = len * Math.sin(2 * Math.PI * 18 / 360);
		return Float.parseFloat(retr.toString());
	}

	private float cos54(float len) {
		Double retr = len * Math.cos(2 * Math.PI * 54 / 360);
		return Float.parseFloat(retr.toString());
	}

	private float sin54(float len) {
		Double retr = len * Math.sin(2 * Math.PI * 54 / 360);
		return Float.parseFloat(retr.toString());
	}

}