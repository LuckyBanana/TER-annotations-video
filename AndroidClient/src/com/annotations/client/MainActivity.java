package com.annotations.client;

import java.io.File;

import models.Video;
import restclient.AnnotationsRESTClientUsage;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String PATH = "com.annotations.client.PATH";
	public final static String NAME = "com.annotations.client.NAME";
	public final static String ID = "com.annotations.client.ID";

	public AnnotationsRESTClientUsage client = new AnnotationsRESTClientUsage(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//Disable NetworkOnMainThreadException
		//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		//StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button btn = (Button)findViewById(R.id.button1);

		ConnectivityManager connMgr = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			/*
			 * Mode connecté
			 */
			System.out.println("Connecté !");
			
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("video/*");
					startActivityForResult(intent, 1);
					
				}
				 
			});

		} else {
			/*
			 * Mode hors connexion
			 */
			System.out.println("Déconnecté !");
			Toast.makeText(this, "This apps requires an internet connection.", Toast.LENGTH_LONG).show();
		}


		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1) {
			if(resultCode == RESULT_OK) {
				Uri contactUri = data.getData();
				String path = getRealPathFromURI(contactUri);
				String name = getRealNameFromURI(contactUri);
				System.out.println(name);
				System.out.println(path);

				final Video video = new Video(name, path);
				video.setStream(new File(path));
				
				String result = client.postVideo(video);
				Log.d("Video Post Result", result);
				
				
				if(!result.equals("")) {
					video.setId(result);
					//client.postStream(video.getId(), video.getStream());
					String id = result;
					Intent i = new Intent(MainActivity.this, AnnotationActivity.class);
					i.putExtra(ID, id);
					i.putExtra(PATH, path);
					i.putExtra(NAME, name);
					startActivity(i);
				}
				else {
					Toast.makeText(getApplicationContext(), "Error while upload. Please try again.", Toast.LENGTH_LONG).show();
				}
				 
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	//http://stackoverflow.com/questions/3401579/get-filename-and-path-from-uri-from-mediastore
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };    
		Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public String getRealNameFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DISPLAY_NAME };
		Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
