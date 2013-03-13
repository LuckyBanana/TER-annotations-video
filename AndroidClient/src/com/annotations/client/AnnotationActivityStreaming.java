package com.annotations.client;

import java.util.ArrayList;
import java.util.List;

import models.Annotation;
import models.Video;
import restclient.AnnotationsRESTClientUsage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class AnnotationActivityStreaming extends Activity {

	public AnnotationsRESTClientUsage client = new AnnotationsRESTClientUsage(this);
	public Video video = new Video();
	private static ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annotation);

		Intent intent = getIntent();
		String path = intent.getStringExtra(MainActivity.PATH);
		String videoId = intent.getStringExtra(MainActivity.ID);

		final VideoView vid = (VideoView)findViewById(R.id.videoMin);
		MediaController mc = new MediaController(this);
		String url = "http://ter-server.herokuapp.com/"+path;
		System.out.println(url);
		Uri uri = Uri.parse(url);

		video.setId(videoId);

		progressDialog = ProgressDialog.show(AnnotationActivityStreaming.this, "", "Buffering video...", true);
		progressDialog.setCancelable(true);  


		mc.setAnchorView(vid);
		vid.setMediaController(mc);
		vid.setVideoURI(uri);
		vid.requestFocus(); 
		vid.setOnPreparedListener(new OnPreparedListener()
		{
			public void onPrepared(MediaPlayer mp)
			{                  
				progressDialog.dismiss();     
			}
		});

		Button bouton_debut = (Button)findViewById(R.id.bouton_debut_annotation);
		Button bouton_fin = (Button)findViewById(R.id.bouton_fin_annotation);
		Button bouton_valider = (Button)findViewById(R.id.validerAnnotation);

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
					cp = java.lang.String.valueOf(vid.getCurrentPosition()).substring(0, java.lang.String.valueOf(vid.getCurrentPosition()).length()-3);
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
					Toast.makeText(AnnotationActivityStreaming.this, "All Fields Required.", 
							Toast.LENGTH_SHORT).show();
				}
				else {
					Annotation post = new Annotation(vNom, vCommentaire, vTcdm.concat(vTcds), vTcfm.concat(vTcfs));
					client.postAnnotation(post, video);
				}
			}
		});


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
			Intent intent = new Intent(AnnotationActivityStreaming.this, ListAnnotationActivity.class);
			intent.putExtra(MainActivity.ID, video.getId());
			startActivity(intent);
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
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


}
