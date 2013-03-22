package quadrants;

import com.annotations.client.R;
import com.annotations.client.R.id;
import com.annotations.client.R.layout;
import com.annotations.client.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class QuadrantActivity extends Activity {
	
	QuadrantView qv;
	public final static String X = "com.annotations.client.X";
	public final static String Y = "com.annotations.client.Y";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quadrant);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		qv = new QuadrantView(this);
		setContentView(qv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quadrant, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.valider_quadrant:
			Intent i = new Intent();
			i.putExtra(QuadrantActivity.X, qv.points.get(0).x+"");
			i.putExtra(QuadrantActivity.Y, qv.points.get(0).y+"");
			setResult(RESULT_OK, i);
			finish();
			return true;
		case R.id.action_settings:
			return true;
		case R.id.entrer_traits:
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
