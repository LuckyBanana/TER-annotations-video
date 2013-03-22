package quadrants;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class Quadrant2View extends SurfaceView {

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Paint paintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
	List<Path> paths = new ArrayList<Path>();

	public Quadrant2View(Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		paint.setTextSize(30);
		paintPoint.setColor(Color.RED);
		initPaths();
	}


	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawLine(640, 0, 640, 640, paint);
		canvas.drawLine(0, 352, 1280, 352, paint);
		canvas.drawCircle(50, 50, 20, paintPoint);
		canvas.drawCircle(570, 300, 20, paintPoint);
		canvas.drawText("Attaque", 640, 20, paint);
		canvas.drawText("Défense", 640, 635, paint);
		canvas.drawText("Programmé", 0, 352, paint);
		canvas.drawText("Automatique", 1100, 352, paint);
		for (Path path : paths) {
			canvas.drawPath(path, paint);
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point point = new Point();
		point.x = (int) event.getX();
		point.y = (int) event.getY();
		if(isTouched(30, 30, point.x, point.y)){
			System.out.println("salut");
		}
		else if(isTouched(570, 300, point.x, point.y)) {
			System.out.println("yohan");
		}
		
		invalidate();
		return true;
	}
	
	//Centre du cercle en parametre, 
	public boolean isTouched(int cx, int cy, int ex, int ey) {
		if(ex > cx-20 && ex < cx+20 && ey > cy-20 && ey < cy+20) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void initPaths() {
		Path p = new Path();
		p.moveTo(0, 352);
		//p.quadTo(320, 176, 640, 0);
		p.lineTo(640, 0);
		paths.add(p);
	}


}
