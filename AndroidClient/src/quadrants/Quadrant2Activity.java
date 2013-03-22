package quadrants;

import com.annotations.client.R;
import com.annotations.client.R.layout;
import com.annotations.client.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;

public class Quadrant2Activity extends Activity {

	Quadrant2View qv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quadrant);
		setContentView(R.layout.activity_quadrant);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		qv = new Quadrant2View(this);
		setContentView(qv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quadrant_v2, menu);
		return true;
	}

}
