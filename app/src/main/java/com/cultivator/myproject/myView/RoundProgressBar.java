package com.cultivator.myproject.myView;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.cultivator.myproject.R;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 * 
 * @author xiaanming
 *
 */
public class RoundProgressBar extends View {
	/**
	 * 最大进度
	 */
	private static final int MAX = 100;

	/**
	 * 画笔对象的引用
	 */
	private Paint paint;
	private SweepGradient  sweepGradient;

	/**
	 * 圆环的颜色
	 */
	private int roundColor;

	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int textColor;

	/**
	 * 进度圆环渐变颜色
	 */
	private int processColor1;
	private int processColor2;

	/**
	 * 中间进度百分比的字符串的字体
	 */
	private float textSize;

	/**
	 * 圆环的宽度
	 */
	private float roundWidth;

	/**
	 * 当前进度
	 */
	private int progress;

	public RoundProgressBar(Context context) {
		this(context, null);
	}

	public RoundProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		paint = new Paint();

		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundProgressBar);

		// 获取自定义属性和默认值
		roundColor = mTypedArray.getColor(
				R.styleable.RoundProgressBar_roundColor, Color.GRAY);
		textColor = mTypedArray.getColor(
				R.styleable.RoundProgressBar_textColor, Color.GREEN);
		processColor1 = mTypedArray.getColor(
				R.styleable.RoundProgressBar_processColor1, Color.GREEN);
		processColor2 = mTypedArray.getColor(
				R.styleable.RoundProgressBar_processColor2, Color.GREEN);

		textSize = mTypedArray.getDimension(
				R.styleable.RoundProgressBar_roundTextSize, 15);

		roundWidth = mTypedArray.getDimension(
				R.styleable.RoundProgressBar_roundWidth, 5);

		mTypedArray.recycle();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		
		paint.setShader(null);//取消渲染效果
		/**
		 * 画圆环
		 */
		int centre = getWidth() / 2; // 获取圆心的x坐标
		int radius = (int) (centre - roundWidth / 2); // 圆环的半径
		paint.setColor(roundColor); // 设置圆环的颜色
		paint.setStyle(Paint.Style.STROKE); // 设置空心
		paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
		paint.setAntiAlias(true); // 消除锯齿
		canvas.drawCircle(centre, centre, radius, paint); // 画出圆环


		/**
		 * 画进度百分比
		 */
		paint.setStrokeWidth(0);
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
		int percent = (int) (((float) progress / (float) MAX) * 100); // 中间的进度百分比，先转换成float在进行除法运算，不然都为0
		float textWidth = paint.measureText(percent + "%"); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间

		if (percent != 0) {
			canvas.drawText(percent + "%", centre - textWidth / 2, centre
					+ textSize / 2, paint); // 画出进度百分比
		}

		/**
		 * 画圆弧 ，圆环的进度
		 */
		sweepGradient = new SweepGradient(centre, centre,
				new int[] { processColor1, processColor2 }, null);
		Matrix matrix = new Matrix();
		matrix.setRotate(-90, centre, centre);
		sweepGradient.setLocalMatrix(matrix);
		paint.setShader(sweepGradient);
		paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
		paint.setColor(Color.GREEN); // 设置进度的颜色
		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawArc(oval, -90, 360 * progress / MAX, false, paint); // 根据进度画圆弧

	}

	public synchronized int getMax() {
		return MAX;
	}

	/**
	 * 获取进度.需要同步
	 * 
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步 刷新界面调用postInvalidate()能在非UI线程刷新
	 * 
	 * @param progress
	 */
	public synchronized void setProgress(int targetProgress) {
		if (targetProgress < 0) {
			throw new IllegalArgumentException("progress not less than 0");
		}
		if (targetProgress > MAX) {
			targetProgress = MAX;
		}
		if (targetProgress <= MAX) {
			this.progress = targetProgress;
			postInvalidate();
		}

	}

	private int curProgress;

	/**
	 * 启动进度动画
	 */
	public void start(int targetProgress) {
		if(this.progress==targetProgress){
			return;
		}
		if (targetProgress < 0) {
			throw new IllegalArgumentException("progress not less than 0");
		}
		if (targetProgress > MAX) {
			targetProgress = MAX;
		}
        final int tageProgress = targetProgress;
		curProgress = 0;
		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				curProgress++;
				if (curProgress == tageProgress) {
					timer.cancel();
				}
				setProgress(curProgress);
			}
		};
		timer.schedule(timerTask, 300, 20);
	}

}
