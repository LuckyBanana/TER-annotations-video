package quadrants;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.annotations.client.R;

import dialogs.TraitsDialog;
import dialogs.TraitsDialog.OnTraitsDialogResult;

public class QuadrantActivity extends Activity {

	QuadrantView qv;
	public final static String X = "com.annotations.client.X";
	public final static String Y = "com.annotations.client.Y";
	public final static String VOLONTE = "com.annotations.client.VOLONTE";
	public final static String IMAGINATION = "com.annotations.client.IMAGINATION";
	public final static String PERCEPTION = "com.annotations.client.PERCEPTION";
	public final static String MEMOIRE = "com.annotations.client.MEMOIRE";
	public final static String ENTRAINEMENT = "com.annotations.client.ENTRAINEMENT";
	private Map<String, String> values = new HashMap<String, String>();

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
			i.putExtra(QuadrantActivity.VOLONTE, values.get("vonlonte"));
			i.putExtra(QuadrantActivity.IMAGINATION, values.get("imagination"));
			i.putExtra(QuadrantActivity.PERCEPTION, values.get("perception"));
			i.putExtra(QuadrantActivity.MEMOIRE, values.get("memoire"));
			i.putExtra(QuadrantActivity.ENTRAINEMENT, values.get("entrainement"));
			setResult(RESULT_OK, i);
			finish();
			return true;
		case R.id.action_settings:
			return true;
		case R.id.entrer_traits:
			TraitsDialog traitsDialog = new TraitsDialog();
			traitsDialog.show(getFragmentManager(), "salut");
			traitsDialog.setDialogResult(new OnTraitsDialogResult() {
				@Override
				public void finish(Map<String, String> result) {
					values = result;
				}
			});
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void initValues() {
		values.put("volonte", "0");
		values.put("imagination", "0");
		values.put("perception", "0");
		values.put("memoire", "0");
		values.put("entrainement", "0");
	}

}
