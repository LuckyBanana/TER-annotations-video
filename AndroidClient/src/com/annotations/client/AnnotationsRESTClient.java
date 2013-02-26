package com.annotations.client;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.Annotation;
import models.Video;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AnnotationsRESTClient {
	
	private static final String BASE_URL = "url_du_server";

	  private static AsyncHttpClient client = new AsyncHttpClient();

	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), params, responseHandler);
	  }
	  
	  public static void postAnnotation(Annotation a) {
		  RequestParams params = new RequestParams();
			params.put("nom", a.getNom());
			params.put("commentaire", a.getCommentaire());
			params.put("timecodeDebut", a.getTimecodeDebut());
			params.put("timecodeFin", a.getTimecodeFin());
			
			post("api/annotation", params, new JsonHttpResponseHandler() {
				 @Override
		            public void onSuccess(JSONArray response) {
		                // Pull out the first event on the public timeline
		                JSONObject firstEvent;
						try {
							firstEvent = (JSONObject) response.get(0);
			                String tweetText = firstEvent.getString("text");

			                // Do something with the response
			                System.out.println(tweetText);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

		            }
			});
	  }
	  
	  public static void postVideo(Video v) {
		  RequestParams params = new RequestParams();
			params.put("nom", v.getNom());
			
			
			post("api/video", params, new JsonHttpResponseHandler() {
				 @Override
		            public void onSuccess(JSONArray response) {
		                // Pull out the first event on the public timeline
		                JSONObject firstEvent;
						try {
							firstEvent = (JSONObject) response.get(0);
			                String tweetText = firstEvent.getString("text");

			                // Do something with the response
			                System.out.println(tweetText);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

		            }
			});
	  }
	  
	  public static void postStream(File f) {
		  RequestParams params = new RequestParams();
		  try {
			params.put("stream", f);
			// -- post --
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }
	  
	  

}
