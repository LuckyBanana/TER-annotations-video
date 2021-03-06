package com.annotations.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Annotation;
import models.Quadrant;
import models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import quadrants.QuadrantActivity;
import restclient.AnnotationsRESTClientUsage;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.VideoView;

public class AnnotationActivity extends Activity {

	public AnnotationsRESTClientUsage client = new AnnotationsRESTClientUsage(this);
	public Video video = new Video();
	private VideoView vid;
	private Quadrant quadrant = new Quadrant();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		String path = intent.getStringExtra(MainActivity.PATH);
		String videoId = intent.getStringExtra(MainActivity.ID);

		super.onCreate(savedInstanceState);
		if(isTabletDevice(this)) {
			setContentView(R.layout.activity_annotation_tablet);
			vid = (VideoView)findViewById(R.id.videoMin);
			listViewInit(vid);
		}
		else {
			//Bloquer l'orientation de l'appareil en fonction de l'orientation de la video
			/*
			MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
			metaRetriever.setDataSource(path);

			String rotation = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
			Log.d("Rotation :", rotation);

			if(rotation.equals("0")) {
				Log.d("Orientation :", "LANDSCAPE");
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			if(rotation.equals("90")) {
				Log.d("Orientation :", "PORTRAIT");
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
			*/
			setContentView(R.layout.activity_annotation);
			vid = (VideoView)findViewById(R.id.videoMin);
		}

		video.setId(videoId);
		videoInit(path, vid);
		buttonsInit(vid);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.annotation, menu);
		return true;
	}

	/*
	 * Retourne une liste contenant deux chaines
	 * Secondes : index = 0
	 * Minutes : index = 1
	 */
	public List<String> CurrentPositionToSecAndMin(String cp) {
		String sec;
		String min;
		switch(cp.length()) {
		case 1 :
			sec = "0".concat(cp);
			min = "00";
			break;
		case 2 :
			sec = cp;
			min = "00";
			break;
		case 3 :
			sec = cp.substring(1);
			min = "0".concat(cp.substring(0, 1));
			break;
		default :
			sec = cp.substring(cp.length()-2, cp.length()-1);
			min = cp.substring(0, cp.length()-3);
			break;
		}
		List<String> result = new ArrayList<String>();
		result.add(sec);
		result.add(min);
		return result;
	}

	/*
	 * INIT
	 */

	public void buttonsInit(final VideoView vid) {
		Button bouton_debut = (Button)findViewById(R.id.bouton_debut_annotation);
		Button bouton_fin = (Button)findViewById(R.id.bouton_fin_annotation);
		Button bouton_valider = (Button)findViewById(R.id.validerAnnotation);
		Button bouton_quadrant = (Button)findViewById(R.id.creerQuadrant);

		final EditText nom_field = (EditText)findViewById(R.id.textNom);
		final EditText commentaire_field = (EditText)findViewById(R.id.textCommentaire);

		final EditText tcd_s = (EditText)findViewById(R.id.timecodeDebutSec);
		final EditText tcd_m = (EditText)findViewById(R.id.timecodeDebutMin);
		final EditText tcf_s = (EditText)findViewById(R.id.timecodeFinSec);
		final EditText tcf_m = (EditText)findViewById(R.id.timecodeFinMin);

		bouton_debut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String cp;
				try {
					cp = java.lang.String.valueOf(vid.getCurrentPosition()).substring(0, java.lang.String.valueOf(vid.getCurrentPosition()).length()-3);
				}
				catch(Exception e) {
					cp = "0";
				}
				List<String> res = CurrentPositionToSecAndMin(cp);
				String sec = res.get(0);
				String min = res.get(1);
				tcd_s.setText(sec);
				tcd_m.setText(min);

			}
		});

		bouton_fin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String cp;
				try {
					/*
					 *  vid.getCurrentPostion = resultat en ms
					 *  inutile pour notre usage on elimine les trois derniers chiffres du resultat
					 */
					cp = java.lang.String.valueOf(vid.getCurrentPosition())
							.substring(0, java.lang.String.valueOf(vid.getCurrentPosition()).length()-3);
				}
				catch(Exception e) {
					cp = "0";
				}
				List<String> res = CurrentPositionToSecAndMin(cp);
				String sec = res.get(0);
				String min = res.get(1);
				tcf_s.setText(sec);
				tcf_m.setText(min);
				vid.pause();
			}
		});

		bouton_valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String vNom = nom_field.getText().toString();
				String vCommentaire = commentaire_field.getText().toString();
				String vTcds = tcd_s.getText().toString();
				String vTcdm = tcd_m.getText().toString();
				String vTcfs = tcf_s.getText().toString();
				String vTcfm = tcd_m.getText().toString();

				if(vNom.equals("") || vCommentaire.equals("") 
						|| vTcds.equals("") || vTcdm.equals("") 
						|| vTcfs.equals("") || vTcfm.equals("")) {
					Toast.makeText(AnnotationActivity.this, "Vous devez remplir tous les champs.", 
							Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(AnnotationActivity.this, "Post en cours.", 
							Toast.LENGTH_SHORT).show();
					System.out.println("final"+quadrant.afficher());
					Annotation post = new Annotation(vNom, vCommentaire, vTcdm.concat(vTcds), vTcfm.concat(vTcfs), quadrant);
					client.postAnnotation(post, video);

					/*
					 * Vider les champs
					 */

					nom_field.setText("");
					commentaire_field.setText("");
					tcd_s.setText(""); 
					tcd_m.setText("");
					tcf_s.setText("");
					tcf_m.setText("");
				}
			}
		});

		bouton_quadrant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(AnnotationActivity.this, QuadrantActivity.class);
				startActivityForResult(i, 2);
			}
		});
	}


	public void listViewInit(final VideoView vid) {
		final ListView listView = (ListView)findViewById(R.id.list_annotation_view);
		Intent intent = getIntent();
		String videoId = intent.getStringExtra(MainActivity.ID);
		System.out.println(videoId);
		String result = client.getAnnotationsOnVideo(videoId);
		System.out.println(result);
		JSONArray array = client.getJsonArray();
		final ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		for(int i = 0; i < array.length(); i++) {
			try {				
				JSONObject obj = array.getJSONObject(i);
				map = new HashMap<String, String>();
				map.put("nom", "Nom : "+obj.getString("nom"));
				map.put("tcd", "Debut : "+obj.getString("timecodeDebut").charAt(0)+obj.getString("timecodeDebut").charAt(1)+
						":"+obj.getString("timecodeDebut").charAt(2)+obj.getString("timecodeDebut").charAt(3));
				map.put("tcf", "Fin : "+obj.getString("timecodeFin").charAt(0)+obj.getString("timecodeFin").charAt(1)+
						":"+obj.getString("timecodeFin").charAt(2)+obj.getString("timecodeFin").charAt(3));
				map.put("com", "Commentaire : "+obj.getString("commentaire"));

				listItem.add(map);	       
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.annotation_item,
				new String[] {"nom", "tcd", "tcf", "com"}, new int[] {R.id.annotation_item_nom, R.id.annotation_item_tcd,
			R.id.annotation_item_tcf, R.id.annotation_item_commentaire});
		listView.setAdapter(mSchedule);


		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// Action quand annotation cliqu�e ?
				/*
				String tcd = listItem.get(position).get("tcd");
				vid.seekTo(Integer.parseInt(tcd));
				 */
			}
		});

	}
	
	/*
	 * Initialise la videoView
	 */
	public void videoInit(String path, final VideoView vid) {
		MediaController mc = new MediaController(this);
		

		mc.setAnchorView(vid);
		vid.setMediaController(mc);
		vid.setVideoPath(path);
		vid.requestFocus(); 

		vid.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(vid.isPlaying()) {
					vid.pause();
				}
				else {
					vid.start();
				}
				return false;
			}
		});

	}

	/**
	 * Checks if the device is a tablet or a phone
	 * 
	 * @param activityContext
	 *            The Activity Context.
	 * @return Returns true if the device is a Tablet
	 */
	public static boolean isTabletDevice(Context activityContext) {
		// Verifies if the Generalized Size of the device is XLARGE to be
		// considered a Tablet
		boolean xlarge = ((activityContext.getResources().getConfiguration().screenLayout & 
				Configuration.SCREENLAYOUT_SIZE_MASK) == 
				Configuration.SCREENLAYOUT_SIZE_XLARGE);

		// If XLarge, checks if the Generalized Density is at least MDPI
		// (160dpi)
		if (xlarge) {
			DisplayMetrics metrics = new DisplayMetrics();
			Activity activity = (Activity) activityContext;
			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

			// MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
			// DENSITY_TV=213, DENSITY_XHIGH=320
			if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
					|| metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
					|| metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
					|| metrics.densityDpi == DisplayMetrics.DENSITY_TV
					|| metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {

				// Yes, this is a tablet!
				return true;
			}
		}

		// No, this is not a tablet!
		return false;
	}
}
