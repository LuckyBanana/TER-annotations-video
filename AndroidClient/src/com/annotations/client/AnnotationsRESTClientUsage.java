package com.annotations.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import models.Annotation;
import models.Video;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AnnotationsRESTClientUsage {

	public ArrayList<String> responseList = new ArrayList<String>();
	private JSONArray jsonArray = new JSONArray();
	private String rslt ="";
	private boolean state = false;

	public void test() {

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

	public String postVideo(Video v) {
		final RequestParams params = new RequestParams();
		params.put("nom", v.getNom());
		try {
			params.put("stream", v.getStream());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		final CountDownLatch signal = new CountDownLatch(1);

		new Thread(new Runnable() {

			@Override
			public void run() {
				AnnotationsRESTClient.post("api/video", params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray response) {
						JSONObject firstEvent;
						String result = "";
						try {
							setJsonArray(response);
							for(int i = 0; i < response.length(); i++){
								firstEvent = (JSONObject) response.get(0);
								result += firstEvent.toString();
							}

							setRslt(result);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFinish() {
						signal.countDown();
					}
				});
			}
		}).start();

		try {
			signal.await(); // wait for callback
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return getRslt();

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

	public String listAnnotations() throws Throwable{

		final CountDownLatch signal = new CountDownLatch(1);

		new Thread(new Runnable() {

			@Override
			public void run() {
				AnnotationsRESTClient.get("api/annotation", null, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray response) {
						JSONObject firstEvent;
						String result = "";
						try {
							setJsonArray(response);
							for(int i = 0; i < response.length(); i++){
								firstEvent = (JSONObject) response.get(0);
								result += firstEvent.toString();
							}

							setRslt(result);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFinish() {
						signal.countDown();
					}
				});
			}
		}).start();

		try {
			signal.await(); // wait for callback
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return getRslt();

	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getRslt() {
		return rslt;
	}

	public void setRslt(String rslt) {
		this.rslt = rslt;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}


}
