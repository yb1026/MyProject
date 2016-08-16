package com.cultivator.myproject.myView;

import java.math.BigDecimal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.view.View;

import com.cultivator.myproject.R;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 * 
 * @author xiaanming
 *
 */
public class FiveDimensionView extends View {

	// 变量
	private Context context;
	private final int score = 600;
	private final float[] proScale = new float[] { 0.8f, 0.5f, 0.4f, 0.5f, 0.3f };

	// 各个参数
	private final String bigColor = "#C0C0C0";
	private final String smallColor = "#0E9BC9";
	private final String textColor = "#000000";
	private final float textSize = 60.0f;
	private final String lineColor = "#666666";

	private float centre;
	private final float fiveDinScale = 0.6f;
	private final float bitmapScale = 0.25f;
	private final float paddingScale = 0.08f;
	// 五边形起始角度(顶点按顺时针排序,角度数递减)
	private final int startAngle = 90;

	private final float[] bx = new float[5];
	private final float[] by = new float[5];
	private final float[] sx = new float[5];
	private final float[] sy = new float[5];
	private final float[] imageLeft = new float[5];
	private final float[] imageTop = new float[5];

	private final int[] drawableIds = new int[] { R.drawable.fiveimage1,
			R.drawable.fiveimage2, R.drawable.fiveimage3,
			R.drawable.fiveimage4, R.drawable.fiveimage5 };

	private final Bitmap[] bitmaps = new Bitmap[5];

	public FiveDimensionView(Context context) {
		this(context, null);
	}

	public FiveDimensionView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FiveDimensionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	private Bitmap getImageBitMap(int id) {
		int newSize = floatToInt(this.centre
				* this.bitmapScale);
		Bitmap  temp = BitmapFactory.decodeResource(this.context.getResources(), id);
        float width = temp.getWidth();
        float height = temp.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newSize) / width;
        float scaleHeight = ((float) newSize) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(temp, 0, 0, (int) width,
                        (int) height, matrix, true);
        return bitmap;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		this.centre = getWidth() / 2;
		// 初始化五边形各个点数据
		initData();
		// 初始化五域图形位置数据
		initFiveImagesData();
		// 大五边形
		bigFiveDin(canvas);
		// 小五边形
		smallFiveDin(canvas);
		// 内外边框
		tenLine(canvas);
		// 中间文字
		centerText(canvas);
		// 五个域图形
		fiveImages(canvas);
	}

	/**
	 * 大五边形
	 * 
	 * @param canvas
	 */
	private void bigFiveDin(Canvas canvas) {
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
		bpath.close();
	}

	/**
	 * 小五边形
	 * 
	 * @param canvas
	 */
	private void smallFiveDin(Canvas canvas) {
		Path spath = new Path();

		spath.moveTo(sx[0], sy[0]);
		for (int i = 1; i < 5; i++) {
			spath.lineTo(sx[i], sy[i]);
		}

		ShapeDrawable sDrawable = new ShapeDrawable(new PathShape(spath, 100,
				100));
		sDrawable.getPaint().setColor(Color.parseColor(smallColor));
		sDrawable.setBounds(0, 0, 100, 100);
		sDrawable.draw(canvas);
		spath.close();
	}

	/**
	 * 十根线内外边框
	 * 
	 * @param canvas
	 */
	private void tenLine(Canvas canvas) {
		Paint linePaint = new Paint();
		linePaint.setStrokeWidth(1);
		linePaint.setColor(Color.parseColor(lineColor));

		for (int i = 0; i < 5; i++) {
			canvas.drawLine(centre, centre, bx[i], by[i], linePaint);
			int k = i + 1;
			if (k >= 5) {
				k = 0;
			}
			canvas.drawLine(bx[i], by[i], bx[k], by[k], linePaint);
		}
	}

	/**
	 * 中央字体
	 * 
	 * @param canvas
	 */
	private void centerText(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStrokeWidth(0);
		paint.setColor(Color.parseColor(textColor));
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
		float textWidth = paint.measureText(String.valueOf(score)); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间

		canvas.drawText(String.valueOf(score), centre - textWidth / 2, centre
				+ textSize / 3, paint); // 画出进度百分比
	}

	/**
	 * 五个域的图形
	 * 
	 * @param canvas
	 */
	private void fiveImages(Canvas canvas) {
		for (int i = 0; i < 5; i++) {
			canvas.drawBitmap(bitmaps[i], imageLeft[i], imageTop[i], null);
		}

	}

	private void initFiveImagesData() {
		for (int i = 0; i < 5; i++) {
			bitmaps[i] = this.getImageBitMap(this.drawableIds[i]);
		}
		float imageMargin = paddingScale*this.centre;
		
		for (int i = 0; i < 5; i++) {
			imageLeft[i] = bx[i] + cos(imageMargin, startAngle -  i* 72);
			imageTop[i] = by[i] - sin(imageMargin, startAngle - i * 72);
		}
		
		//微调系数，自己调
		float[] xS = {0.5f,0,0,1,1}; 
		float[] yS = {0.8f,0.5f,0.2f,0.2f,0.5f}; 
		
		for (int i = 0; i < 5; i++) {
			imageLeft[i] = imageLeft[i]- bitmaps[i].getWidth() * xS[i];
			imageTop[i] = imageTop[i] - bitmaps[i].getHeight() * yS[i];
		}
		

	}

	private void initData() {
		float len = centre * fiveDinScale;
		for (int i = 0; i < 5; i++) {
			bx[i] = centre + cos(len, startAngle - i * 360 / 5);
			by[i] = centre - sin(len, startAngle - i * 360 / 5);
			sx[i] = centre + cos(len * proScale[i], startAngle - i * 360 / 5);
			sy[i] = centre - sin(len * proScale[i], startAngle - i * 360 / 5);
		}
	}

	private float sin(float len, int c) {
		Double retr = len * Math.sin(2 * Math.PI * c / 360);
		return Float.parseFloat(retr.toString());
	}

	private float cos(float len, int c) {
		Double retr = len * Math.cos(2 * Math.PI * c / 360);
		return Float.parseFloat(retr.toString());
	}

	private int floatToInt(float i) {
		BigDecimal b = new BigDecimal(i);
		b.setScale(0, BigDecimal.ROUND_HALF_UP);
		return b.intValue();
	}

}