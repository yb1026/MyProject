package com.cultivator.myproject.myView;

import java.math.BigDecimal;

import junit.framework.Assert;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.cultivator.myproject.R;

/**
 * 自定义五边形控件
 * 
 * @author Administrator
 * 
 */
public class LifeWheelRadarGraph extends View {
	private int count = 5;
	private float angle = 360 / count;
	private int point_radius = 5; // 画点的半径
	private int regionwidth = 40; // 选择分值小区域宽度
	private int valueRulingCount = 10; // 画等分值线
	private int radius;
	private int centerX;
	private int centerY;
	private String[] titles = { "用眼距离", "用眼角度", "电子屏幕的使用", "光照环境", "户外活动" };

	private Point[] pts; // 维度端点
	
	// private Point[] titlespts; //标题端点
	private Region[] regions; // 打分点区域
	private float[] regionValues; // 打分点分数
	private Path valuePath;
	private float[] values = { 5, 5, 5, 5, 5 }; // 各维度分值
	private int maxValue = 10;
	private Point[] value_pts; // 维度端点
	private Paint paint;
	private Paint valuePaint;
	private Bitmap bitmap;

	public float[] getValues() {
		return values;
	}

	public void setValues(float[] values) {
		Assert.assertTrue("传递的values数组大小不是" + count, values.length == count);
		this.values = values;
		invalidate();
	}

	public LifeWheelRadarGraph(Context context) {
		super(context);
		init();
	}

	private void init() {

		paint = new Paint();
		valuePaint = new Paint();
		pts = new Point[count];
		// titlespts= new Point[count];
		value_pts = new Point[count];
		valuePath = new Path();
		for (int i = 0; i < count; i++) {
			pts[i] = new Point();
			value_pts[i] = new Point();
			// titlespts[i] = new Point();
		}

		regionValues = new float[count * valueRulingCount * 2];
		regions = new Region[count * valueRulingCount * 2];
		for (int i = 0; i < regions.length; i++) {
			regions[i] = new Region();
		}

	}

	public LifeWheelRadarGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LifeWheelRadarGraph(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		radius = Math.min(h, w) / 4;
		centerX = w / 2;
		centerY = h / 2;

		for (int i = 0; i < count; i++) {
			pts[i].x = centerX
					+ (int) (radius * Math.cos((angle * i - this.startAngle) * 3.14 / 180));
			pts[i].y = centerY
					+ (int) (radius * Math.sin((angle * i -  this.startAngle) * 3.14 / 180));

			for (int j = 1; j <= valueRulingCount * 2; j++) {
				int x = centerX + (pts[i].x - centerX) / (valueRulingCount * 2)
						* j;
				int y = centerY + (pts[i].y - centerY) / (valueRulingCount * 2)
						* j;
				regions[i * valueRulingCount * 2 + j - 1].set(x - regionwidth
						/ 2, y - regionwidth / 2, x + regionwidth / 2, y
						+ regionwidth / 2);
				regionValues[i * valueRulingCount * 2 + j - 1] = j;
			}
		}
		postInvalidate();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	//
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// int action = event.getAction();
	// float x = event.getX();
	// float y = event.getY();
	//
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// for (int i = 0; i < regions.length; i++) {
	// if (regions[i].contains((int) x, (int) y)) {
	// values[(int) (i / (valueRulingCount * 2))] = regionValues[i];
	// break;
	// }
	// }
	// invalidate();
	// break;
	// case MotionEvent.ACTION_MOVE:
	//
	// break;
	// case MotionEvent.ACTION_UP:
	//
	// break;
	// }
	// return super.onTouchEvent(event);
	// }

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyLongPress(keyCode, event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		/* 设置画布的颜色 */
		// canvas.drawColor(Color.WHITE);
		
		
		//TODO  yb1026  取短边
		this.centre = getWidth()>getHeight()? getHeight()/2:getWidth()/2;

		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
				R.mipmap.fiveimage1), 0f, 0f, paint);

		paint.setAntiAlias(true);
		// 画边框线
		paint.setColor(Color.GRAY);

		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		for (int i = 0; i < count; i++) {
			int end = i + 1 == count ? 0 : i + 1;

			// for(int j=1; j<=valueRulingCount; j++)
			// {
			// canvas.drawLine(centerX+(pts[i].x-centerX)/5*j,
			// centerY+(pts[i].y-centerY)/5*j,
			// centerX+(pts[end].x-centerX)/5*j,
			// centerY+(pts[end].y-centerY)/5*j, paint);
			// }

			canvas.drawLine(centerX + (pts[i].x - centerX), centerY
					+ (pts[i].y - centerY), centerX + (pts[end].x - centerX),
					centerY + (pts[end].y - centerY), paint);
			canvas.drawLine(centerX, centerY, pts[i].x, pts[i].y, paint);
		}

		// 写文字
		paint.setTextSize(20);
		paint.setColor(Color.BLACK);
		FontMetrics fontMetrics = paint.getFontMetrics();
		float fontHegiht = -fontMetrics.ascent;
		
		
		 initFiveImagesData();
		
		 fiveImages(canvas);
		
//		for (int i = 0; i < count; i++) {
//			// int textWidth = getTextWidth(paint, titles[i]);
//			if ((i == 0)) { // 用眼角度
//				bitmap = drawableToBitmap(getResources().getDrawable(
//						R.drawable.dimension3));
//				canvas.drawBitmap(bitmap, pts[i].x,
//						pts[i].y - bitmap.getHeight() / 2, paint);
//			} else if ((i == 1))
//
//			{
//				// 电子屏幕的使用
//				bitmap = drawableToBitmap(getResources().getDrawable(
//						R.drawable.dimension5));
//				canvas.drawBitmap(bitmap,
//						pts[i].x - bitmap.getWidth() / 2 + 60, pts[i].y
//								+ bitmap.getHeight() / 12 - 100, paint);
//			} else if ((i == 2)) {
//				// 光照环境
//				bitmap = drawableToBitmap(getResources().getDrawable(
//						R.drawable.dimension4));
//				canvas.drawBitmap(bitmap,
//						pts[i].x - bitmap.getWidth() / 2 - 60, pts[i].y
//								+ bitmap.getHeight() / 12 - 100, paint);
//			}
//
//			else if (i == 3) {
//				// 户外活动
//				bitmap = drawableToBitmap(getResources().getDrawable(
//						R.drawable.dimension2));
//				canvas.drawBitmap(bitmap, pts[i].x - bitmap.getWidth(),
//						pts[i].y - bitmap.getHeight() / 2, paint);
//			}
//
//			else if (i == 4) {
//				// 用眼距离
//				bitmap = drawableToBitmap(getResources().getDrawable(
//						R.drawable.dimension1));
//				//
//
//				canvas.drawBitmap(bitmap, pts[i].x - bitmap.getWidth() / 2,
//						pts[i].y - bitmap.getHeight(), paint);
//			}
//		}

		// 画方向盘分值区域
		for (int i = 0; i < count; i++) {
			// value_pts[i].x = centerX
			// + (int) (radius * 3 / 4 * Math
			// .cos((angle * i - 18) * 3.14 / 180));
			// value_pts[i].y = centerY
			// + (int) (radius * 3 / 4 * Math
			// .sin((angle * i - 18) * 3.14 / 180));
			value_pts[i].x = (int) (centerX + (pts[i].x - centerX) * values[i]
					/ maxValue);
			value_pts[i].y = (int) (centerY + (pts[i].y - centerY) * values[i]
					/ maxValue);

		}

		valuePath.reset();
		valuePaint.setAntiAlias(true);
		// 护眼绿色
		valuePaint.setARGB(100, 53, 238, 226);

		valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

		// new int[] {Color.rgb(72, 221, 211), Color.argb(1, 72, 221, 211) }
		Shader mShader = new RadialGradient(centerX, centerY, radius,
				Color.rgb(30, 221, 211), Color.argb(60, 72, 221, 211),
				Shader.TileMode.REPEAT);

		valuePaint.setShader(mShader);
		for (int i = 0; i < pts.length; i++) {
			// 给valuePath赋值
			if (i == 0) {

				valuePath.moveTo(value_pts[i].x, value_pts[i].y);
			}

			else {

				valuePath.lineTo(value_pts[i].x, value_pts[i].y);
			}
			// 画取分圆圈
			// canvas.drawCircle(value_pts[i].x, value_pts[i].y, point_radius,
			// paint);
		}
		valuePaint.setAlpha(150);
		canvas.drawPath(valuePath, valuePaint);

	}

	public static int getTextWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if (bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}

		if (drawable.getIntrinsicWidth() <= 0
				|| drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single

		} else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}
	
	
	
	
	
	private Bitmap getImageBitMap(int id) {
		int newSize = floatToInt(this.centre
				* this.bitmapScale);
		Bitmap  temp = BitmapFactory.decodeResource(getResources(), id);
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
	
	
	/**
	 * 五个域的图形
	 * 
	 * @param canvas
	 */
	private void fiveImages(Canvas canvas) {
		for (int i = 0; i < 5; i++) {
			canvas.drawBitmap(bitmaps[i], imageLeft[i], imageTop[i], paint);
		}

	}
	
	private float centre;
	private final float bitmapScale = 0.25f;
	private final float paddingScale = 0.08f;
	// 五边形起始角度(顶点按顺时针排序,角度数递减)
	private final int startAngle = 18;
	
	private final float[] imageLeft = new float[5];
	private final float[] imageTop = new float[5];

	private final int[] drawableIds = new int[] { R.mipmap.fiveimage1,
			R.mipmap.fiveimage2, R.mipmap.fiveimage3,
			R.mipmap.fiveimage4, R.mipmap.fiveimage5 };
	
	private final Bitmap[] bitmaps = new Bitmap[5];
	
	private void initFiveImagesData() {
		for (int i = 0; i < 5; i++) {
			bitmaps[i] = this.getImageBitMap(this.drawableIds[i]);
		}
		float imageMargin = paddingScale*this.centre;
		
		
		for (int i = 0; i < 5; i++) {
			imageLeft[i] = pts[i].x + cos(imageMargin, startAngle -  i* 72);
			imageTop[i] = pts[i].y - sin(imageMargin, startAngle - i * 72);
		}
		
		//微调系数，自己调
		
		                    
//		float[] xS = {0.5f,0,0,1,1}; 
//		float[] yS = {0.8f,0.5f,0.2f,0.2f,0.5f}; 
		
		
		float[] xS = {0,0,1,1,0.5f}; 
		float[] yS = {0.5f,0.2f,0.2f,0.5f,1}; 
		
		for (int i = 0; i < 5; i++) {
			imageLeft[i] = imageLeft[i]- bitmaps[i].getWidth() * xS[i];
			imageTop[i] = imageTop[i] - bitmaps[i].getHeight() * yS[i];
		}
		
//		imageLeft[0] = pts[0].x + cos(imageMargin, startAngle - 0 * 360 / 5)
//				- bitmaps[0].getWidth() / 2;
//		imageTop[0] = pts[0].y - sin(imageMargin, startAngle - 0 * 360 / 5)
//				- bitmaps[0].getHeight()*0.8f;
//
//		imageLeft[1] = pts[1].x + cos(imageMargin, startAngle - 1 * 360 / 5) - 0;
//		imageTop[1] = pts[1].y - sin(imageMargin, startAngle - 1 * 360 / 5)
//				- bitmaps[1].getHeight() / 2;
//
//		imageLeft[2] = pts[2].x + cos(imageMargin, startAngle - 2 * 360 / 5) - 0;
//		imageTop[2] = pts[2].y - sin(imageMargin, startAngle - 2 * 360 / 5)
//				- bitmaps[2].getHeight() / 5;
//
//		imageLeft[3] = pts[3].x + cos(imageMargin, startAngle - 3 * 360 / 5)
//				- bitmaps[3].getWidth();
//		imageTop[3] = pts[3].y - sin(imageMargin, startAngle - 3 * 360 / 5)
//				- bitmaps[3].getHeight() / 5;
//
//		imageLeft[4] = pts[4].x + cos(imageMargin, startAngle - 4 * 360 / 5)
//				- bitmaps[4].getWidth();
//		imageTop[4] = pts[4].y - sin(imageMargin, startAngle - 4 * 360 / 5)
//				- bitmaps[3].getHeight() / 2;

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