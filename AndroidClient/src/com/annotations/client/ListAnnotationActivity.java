package com.annotations.client;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import restclient.AnnotationsRESTClientUsage;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ListAnnotationActivity extends Activity {
	
	AnnotationsRESTClientUsage client = new AnnotationsRESTClientUsage(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_annotation);
		listViewInit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_annotation, menu);
		return true;
	}
	
	public void listViewInit() {
		final ListView listView = (ListView)findViewById(R.id.list_annotation_view);
		Intent intent = getIntent();
		String videoId = intent.getStringExtra(MainActivity.ID);
		System.out.println(videoId);
		String result = client.getAnnotationsOnVideo(videoId);
		System.out.println(result);
		JSONArray array = client.getJsonArray();
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;

		for(int i = 0; i < array.length(); i++) {
			try {

				JSONObject obj = array.getJSONObject(i);
				map = new HashMap<String, String>();
				map.put("nom", obj.getString("nom"));
				map.put("tcd", obj.getString("timecodeDebut"));
				map.put("tcf", obj.getString("timecodeFin"));
				map.put("com", obj.getString("commentaire"));

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
				// Action quand annotation cliquée ?
			}
		});
	}

}
