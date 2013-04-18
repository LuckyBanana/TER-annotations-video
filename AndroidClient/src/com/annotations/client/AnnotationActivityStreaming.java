package com.annotations.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import models.Annotation;
import models.Observation;
import models.Quadrant;
import models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import quadrants.QuadrantActivity;
import restclient.AnnotationsRESTClientUsage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

@SuppressWarnings("unused")
public class AnnotationActivityStreaming extends Activity {

	private final String LIST_ITEM_INDEX = "ListItemIndex";
	private final String LIST_ITEM_COLOR = "ListItemColor";
	private final String REFRESH_LIST_VIEW = "RefreshLIstView";

	private AnnotationsRESTClientUsage client = new AnnotationsRESTClientUsage(this);
	private Video video = new Video();
	private VideoView vid;
	private static ProgressDialog progressDialog;
	private Quadrant quadrant = new Quadrant();
	private ListView annotationsListView;
	private ListView observationsListView;
	private Map<String, String> observationsCodes = new HashMap<String, String>();
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("msg");
			if(msg.getData().getInt(REFRESH_LIST_VIEW) == 0) {
				annotationsViewInit(vid);
			}
			else {
				try {
					annotationsListView
					.getChildAt(msg.getData().getInt(LIST_ITEM_INDEX))
					.setBackgroundColor(msg.getData().getInt(LIST_ITEM_COLOR));
				}
				catch(NullPointerException npe) {
					Log.d("handler", "");
				}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Intent intent = getIntent();
		String path = intent.getStringExtra(MainActivity.PATH);
		String videoId = intent.getStringExtra(MainActivity.ID);
		video.setId(videoId);
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		observationsCodesMapInit();

		super.onCreate(savedInstanceState);
		if(isTabletDevice(this)) {
			setContentView(R.layout.activity_annotation_tablet);
			buttonBarInit();
			vid = (VideoView)findViewById(R.id.videoMin);
			listItem = annotationsViewInit(vid);
			observationsViewInit();
		}
		else {
			setContentView(R.layout.activity_annotation);
			vid = (VideoView)findViewById(R.id.videoMin);
		}

		videoInit(path, vid);
		buttonsInit(vid);

		if(isTabletDevice(this)) {
			initColor(vid, listItem);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.annotation, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.list_annotations:

			/*
			Intent intent = new Intent(AnnotationActivityStreaming.this, ListAnnotationActivity.class);
			intent.putExtra(MainActivity.ID, video.getId());
			startActivity(intent);
			 */

			String url = client.getQuadrant(video.getId());
			Intent i = new Intent(Intent.ACTION_VIEW);
			Uri u = Uri.parse(url);
			i.setData(u);
			startActivity(i);
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 2) {
			if(resultCode == RESULT_OK) {
				Map<String, String> values = new HashMap<String, String>();
				values.put("volonte", data.getStringExtra(QuadrantActivity.VOLONTE));
				values.put("imagination", data.getStringExtra(QuadrantActivity.IMAGINATION));
				values.put("perception", data.getStringExtra(QuadrantActivity.PERCEPTION));
				values.put("memoire", data.getStringExtra(QuadrantActivity.MEMOIRE));
				values.put("entrainement", data.getStringExtra(QuadrantActivity.ENTRAINEMENT));
				quadrant = new Quadrant(
						data.getStringExtra(QuadrantActivity.X),
						data.getStringExtra(QuadrantActivity.Y),
						values);
				System.out.println(quadrant.afficher());
			}
		}
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

	public void buttonBarInit() {
		Button bb_button1 = (Button)findViewById(R.id.bb_button1);
		Button bb_button2 = (Button)findViewById(R.id.bb_button2);
		Button bb_button3 = (Button)findViewById(R.id.bb_button3);
		Button bb_button4 = (Button)findViewById(R.id.bb_button4);
		Button bb_button5 = (Button)findViewById(R.id.bb_button5);
		Button bb_button6 = (Button)findViewById(R.id.bb_button6);
		Button bb_button7 = (Button)findViewById(R.id.bb_button7);
		Button bb_button8 = (Button)findViewById(R.id.bb_button8);

		bb_button1.setText("Stand By");
		bb_button2.setText("Try to grasp w/o contact");
		bb_button3.setText("Try to grasp w/ contact");
		bb_button4.setText("1 grasped hand");
		bb_button5.setText("1 grasped hand, the other in contact");
		bb_button6.setText("2 grasped hands");
		bb_button7.setText("Attack");
		bb_button8.setText("Throw");

		bb_button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				Observation post = new Observation("1", min.concat(sec));
				client.postObservation(post, video);
			}
		});
		
		bb_button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				Observation post = new Observation("0.9", min.concat(sec));
				client.postObservation(post, video);
			}
		});
		
		bb_button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				Observation post = new Observation("0.8", min.concat(sec));
				client.postObservation(post, video);
			}
		});
		
		bb_button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				Observation post = new Observation("0.7", min.concat(sec));
				client.postObservation(post, video);
			}
		});
		
		bb_button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				Observation post = new Observation("0.6", min.concat(sec));
				client.postObservation(post, video);
			}
		});
		
		bb_button6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				Observation post = new Observation("0.5", min.concat(sec));
				client.postObservation(post, video);
			}
		});
		
		bb_button7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				Observation post = new Observation("0.4", min.concat(sec));
				client.postObservation(post, video);
			}
		});
		
		bb_button8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				Observation post = new Observation("0.2", min.concat(sec));
				client.postObservation(post, video);
			}
		});
	}

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
					Toast.makeText(AnnotationActivityStreaming.this, "Vous devez remplir tous les champs.", 
							Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(AnnotationActivityStreaming.this, "Post en cours.", 
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

					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							Bundle messageBundle = new Bundle();
							Message message;
							message = handler.obtainMessage();
							messageBundle.putInt(REFRESH_LIST_VIEW, 0);
							message.setData(messageBundle);
							handler.sendMessage(message);
						}

					}, 3000);
				}
			}
		});

		bouton_quadrant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(AnnotationActivityStreaming.this, QuadrantActivity.class);
				startActivityForResult(i, 2);
			}
		});
	}


	/*
	 * INIT
	 */

	public void videoInit(String path, final VideoView vid) {
		MediaController mc = new MediaController(this);
		String url = "http://ter-server.herokuapp.com/"+path;
		System.out.println(url);
		Uri uri = Uri.parse(url);

		progressDialog = ProgressDialog.show(AnnotationActivityStreaming.this, "", "Chargement de la video...", true);
		progressDialog.setCancelable(false);  

		mc.setAnchorView(vid);
		vid.setMediaController(mc);
		vid.setVideoURI(uri);
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

		vid.setOnPreparedListener(new OnPreparedListener()
		{
			public void onPrepared(MediaPlayer mp)
			{                  
				progressDialog.dismiss();     
			}
		});
	}
	
	public void observationsCodesMapInit() {
		
		observationsCodes.put("1", "Stand By");
		observationsCodes.put("0.9", "Try to grasp w/o contact");
		observationsCodes.put("0.8", "Try to grasp w/ contact");
		observationsCodes.put("0.7", "1 grasped hand");
		observationsCodes.put("0.6", "1 grasped hand, the other in contact");
		observationsCodes.put("0.5", "2 grasped hands");
		observationsCodes.put("0.4", "Attack");
		observationsCodes.put("0.2", "Throw");
	}
	
	public void observationsViewInit() {
		observationsListView = (ListView)findViewById(R.id.list_observation_view);
		Intent intent = getIntent();
		String videoId = intent.getStringExtra(MainActivity.ID);
		String result = client.getObservationsOnVideo(videoId);
		JSONArray array = client.getJsonArray();
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		
		for(int i = 0; i < array.length(); i++) {
			try {				
				JSONObject obj = array.getJSONObject(i);
				map = new HashMap<String, String>();
				map.put("codeObservation", "Code : "+observationsCodes.get(obj.getString("codeObservation")));
				map.put("timecode", "Date : "+obj.getString("timecode").charAt(0)+obj.getString("timecode").charAt(1)+
						":"+obj.getString("timecode").charAt(2)+obj.getString("timecode").charAt(3));

				listItem.add(map);	       
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.observation_item,
				new String[] {"codeObservation", "timecode"}, new int[] {R.id.observation_item_code, R.id.observation_item_timecode});
		observationsListView.setAdapter(mSchedule);
	}

	public ArrayList<HashMap<String, String>> annotationsViewInit(final VideoView vid) {
		annotationsListView = (ListView)findViewById(R.id.list_annotation_view);
		Intent intent = getIntent();
		String videoId = intent.getStringExtra(MainActivity.ID);
		String result = client.getAnnotationsOnVideo(videoId);
		JSONArray array = client.getJsonArray();
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
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
		annotationsListView.setAdapter(mSchedule);

		annotationsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// Action quand annotation cliquée ?
				/*
				String tcd = listItem.get(position).get("tcd");
				vid.seekTo(Integer.parseInt(tcd));
				 */
			}
		});

		return listItem;

	}

	public void initColor(final VideoView vid, final ArrayList<HashMap<String, String>> listItem) {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				Bundle messageBundle = new Bundle();
				Message message;

				if(vid.isPlaying()) {
					for(int i = 0; i < listItem.size(); i++) {
						// Il existe probablement un autre moyen largement moins dégueulasse (à chercher)
						String tcd_m = ""+listItem.get(i).get("tcd").charAt(8)+listItem.get(i).get("tcd").charAt(9);
						String tcd_s = ""+listItem.get(i).get("tcd").charAt(11)+listItem.get(i).get("tcd").charAt(12);
						String tcf_m = ""+listItem.get(i).get("tcf").charAt(6)+listItem.get(i).get("tcf").charAt(7);
						String tcf_s = ""+listItem.get(i).get("tcf").charAt(9)+listItem.get(i).get("tcf").charAt(10);
						int tcd = Integer.parseInt(tcd_m)*100
								+ Integer.parseInt(tcd_s);
						int tcf = Integer.parseInt(tcf_m)*100
								+ Integer.parseInt(tcf_s);

						if(vid.getCurrentPosition()/1000 > tcd &&
								vid.getCurrentPosition()/1000 < tcf) {
							message = handler.obtainMessage();
							messageBundle.putInt(REFRESH_LIST_VIEW, 1);
							messageBundle.putInt(LIST_ITEM_INDEX, i);
							messageBundle.putInt(LIST_ITEM_COLOR, Color.RED);
							message.setData(messageBundle);
							handler.sendMessage(message);
							//listView.getChildAt(i).setBackgroundColor(Color.RED);
						}
						else {
							message = handler.obtainMessage();
							messageBundle.putInt(REFRESH_LIST_VIEW, 1);
							messageBundle.putInt(LIST_ITEM_INDEX, i);
							messageBundle.putInt(LIST_ITEM_COLOR, Color.WHITE);
							message.setData(messageBundle);
							handler.sendMessage(message);
							//listView.getChildAt(i).setBackgroundColor(Color.WHITE);
						}
					}

				}
			}

		}, 0, 1000);


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
