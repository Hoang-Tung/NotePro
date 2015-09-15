package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class LineEditText extends EditText{

	private Rect mRect;
	private Paint mPaint;
	
	public LineEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mRect = new Rect();
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.BLACK);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int height = canvas.getHeight();
		int curHeight = 0;
		Rect r = mRect;
		Paint paint = mPaint;
		int baseLine = getLineBounds(0, r);
		
		for (curHeight = baseLine + 1; curHeight < height; curHeight += getLineHeight()){
			canvas.drawLine(r.left, curHeight, r.right, curHeight, paint);
		}
		
		super.onDraw(canvas);
		
	}
	
	
}
