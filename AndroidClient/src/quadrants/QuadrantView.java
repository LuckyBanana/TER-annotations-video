package quadrants;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class QuadrantView extends SurfaceView {

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint paintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
	List<Point> points = new ArrayList<Point>();

	public QuadrantView(Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		paint.setTextSize(30);
		paintPoint.setColor(Color.RED);
	}


	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawLine(640, 0, 640, 640, paint);
		canvas.drawLine(0, 352, 1280, 352, paint);
		canvas.drawText("Attaque", 640, 20, paint);
		canvas.drawText("Défense", 640, 635, paint);
		canvas.drawText("Programmé", 0, 352, paint);
		canvas.drawText("Automatique", 1100, 352, paint);
		for (Point point : points) {
			canvas.drawCircle(point.x, point.y, 5, paint);
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point point = new Point();
		point.x = (int) event.getX();
		point.y = (int) event.getY();
		points.clear();
		points.add(point);
		System.out.println("ok");
		System.out.println(point.toString());
		invalidate();
		//invalidate(new Rect(point.x-10, point.y-10, point.x+10, point.y+10));
		return true;
	}


}
