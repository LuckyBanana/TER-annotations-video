package com.annotations.client;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {

	public final static String PATH = "com.annotations.client.PATH";
	public final static String NAME = "com.annotations.client.NAME";
	public final static String ID = "com.annotations.client.ID";

	public AnnotationsRESTClientUsage client = new AnnotationsRESTClientUsage();

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

		} else {
			/*
			 * Mode hors connexion
			 */
			System.out.println("Déconnecté !");
		}


		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			

				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("video/*");
				startActivityForResult(intent, 1);
			}
			 
		});
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
				
				

				
				String id = "";

				Intent i = new Intent(MainActivity.this, AnnotationActivity.class);
				i.putExtra(ID, id);
				i.putExtra(PATH, path);
				i.putExtra(NAME, name);
				startActivity(i);
				 
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
