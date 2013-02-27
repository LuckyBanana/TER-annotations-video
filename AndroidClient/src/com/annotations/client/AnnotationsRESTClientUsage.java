package com.annotations.client;

import java.io.File;
import java.io.FileNotFoundException;

import models.Annotation;
import models.Video;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AnnotationsRESTClientUsage {
	
	 public void test() {
		  
		 
		 
		  System.out.println("static test method");
		  AnnotationsRESTClient.get("api/test", null, new AsyncHttpResponseHandler() {
			  @Override
			  public void onSuccess(String response) {
				  System.out.println("Success");
			  }
			  
			  @Override
			  public void onFailure(Throwable error, String content) {
			  if ( error.getCause() instanceof ConnectTimeoutException ) {
			  System.out.println("Connection timeout !");
			  }
			  }
		  });
		  /*
		  AnnotationsRESTClient.get("api/test", null, new JsonHttpResponseHandler() {
			  
			  @Override
			     public void onStart() {
			         // Initiated the request
					System.out.println("Start !");
			     }
			  
			  @Override
			  public void onSuccess(JSONArray response) {
				  try {
					JSONObject object = (JSONObject) response.get(0);
					String st = object.getString("id");
					System.out.println("Success !");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  
			  @Override
			  public void onFailure(Throwable e, String response) {
					System.out.println("Failure !");
			  }
			  
			  @Override
			     public void onFinish() {
			         // Completed the request (either success or failure)
				  System.out.println("Finish !");
			     }
		  });
		  */
		  
	  }
	  
	  public static void postAnnotation(Annotation a) {
		  RequestParams params = new RequestParams();
			params.put("nom", a.getNom());
			params.put("commentaire", a.getCommentaire());
			params.put("timecodeDebut", a.getTimecodeDebut());
			params.put("timecodeFin", a.getTimecodeFin());
			
			AnnotationsRESTClient.post("api/annotation", params, new JsonHttpResponseHandler() {
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
			
			
			AnnotationsRESTClient.post("api/video", params, new JsonHttpResponseHandler() {
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

}
