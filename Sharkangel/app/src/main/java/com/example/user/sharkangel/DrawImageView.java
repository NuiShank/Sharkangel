package com.example.user.sharkangel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class DrawImageView extends ImageView {

	public DrawImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	Paint paint = new Paint();
	{
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(10.0f);//设置线宽
		paint.setAlpha(100);
	};
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int canvasW = getWidth();
		int canvasH = getHeight();
		Point centerOfCanvas = new Point(canvasW / 2, canvasH / 2);
		int rectW = 800;
		int rectH = 1650;
		int left = centerOfCanvas.x - (rectW / 2);
		int top = centerOfCanvas.y - (rectH / 2);
		int right = centerOfCanvas.x + (rectW / 2);
		int bottom = centerOfCanvas.y + (rectH / 2);
		Rect rect = new Rect(left, top, right, bottom);
        Log.i("123",rect.height()+"X"+rect.width());
		canvas.drawRect(rect, paint);




		
	}
	
	


	
}
