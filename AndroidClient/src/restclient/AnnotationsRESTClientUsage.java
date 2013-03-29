package restclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import models.Annotation;
import models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AnnotationsRESTClientUsage {

	public ArrayList<String> responseList = new ArrayList<String>();
	private JSONArray jsonArray = new JSONArray();
	private String rslt ="";
	private Activity activity;

	public AnnotationsRESTClientUsage(Activity activity) {
		this.activity = activity;
	}
	
	/*
	 * Annotations
	 */
	
	public String listAnnotations() {

		final CountDownLatch signal = new CountDownLatch(1);

		new Thread(new Runnable() {
			@Override
			public void run() {
				AnnotationsRESTClient.get("api/annotation", null, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray response) {
						setJsonArray(response);
						JSONObject firstEvent;
						String result = "";
						try {
							
							for(int i = 0; i < response.length(); i++){
								firstEvent = (JSONObject) response.get(i);
								result += firstEvent.toString();
							}

							setRslt(result);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onFinish() {
						Toast.makeText(activity, "Chargé !", Toast.LENGTH_SHORT).show();
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
	
	public String getAnnotationsOnVideo(final String id) {
		//GET api/video/:id/annotations
		final CountDownLatch signal = new CountDownLatch(1);

		new Thread(new Runnable() {
			@Override
			public void run() {
				AnnotationsRESTClient.get("api/video/"+id+"/annotations", null, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray response) {
						JSONObject firstObject;
						String result = "";
						try {
							setJsonArray(response);
							for(int i = 0; i < response.length(); i++){
								firstObject = (JSONObject) response.get(0);
								result += firstObject.toString();
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
	
	public void postAnnotation(Annotation a, Video v) {
		
		//POST "api/annotation/videoId" Annotation + VideoId
		
		RequestParams params = new RequestParams();
		params.put("nom", a.getNom());
		params.put("commentaire", a.getCommentaire());
		params.put("timecodeDebut", a.getTimecodeDebut());
		params.put("timecodeFin", a.getTimecodeFin());
		params.put("x", a.getQuadrant().getX());
		params.put("y", a.getQuadrant().getY());
		params.put("volonte", a.getQuadrant().getTraits().get("volonte"));
		params.put("imagination", a.getQuadrant().getTraits().get("imagination"));
		params.put("perception", a.getQuadrant().getTraits().get("perception"));
		params.put("memoire", a.getQuadrant().getTraits().get("memoire"));
		params.put("entrainement", a.getQuadrant().getTraits().get("entrainement"));
		
		System.out.println(a.getQuadrant().getTraits().get("volonte")+" "+
				a.getQuadrant().getTraits().get("imagination"));

		AnnotationsRESTClient.post("api/annotation/"+v.getId(), params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray response) {

			}
			@Override
			public void onFinish() {
				Toast.makeText(activity, "Annotation posted", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void deleteAnnotation(String VideoId, String AnnotationId) {
		//DELETE api/video/VideoId/AnnotationId
		AnnotationsRESTClient.delete("api/video/"+VideoId+"/"+AnnotationId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray response) {
				
			}
			@Override
			public void onFinish() {
			}
		});
	}
	
	/*
	 * Videos
	 */
	
	public String listVideo() {
		//GET api/video
		final CountDownLatch signal = new CountDownLatch(1);

		new Thread(new Runnable() {
			@Override
			public void run() {
				AnnotationsRESTClient.get("api/video", null, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray response) {
						JSONObject firstObject;
						String result = "";
						try {
							setJsonArray(response);
							for(int i = 0; i < response.length(); i++){
								firstObject = (JSONObject) response.get(0);
								result += firstObject.toString();
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
	
	public String postVideo(Video v) {
		
		final CountDownLatch signal = new CountDownLatch(1);
		final RequestParams params = new RequestParams();
		params.put("nom", v.getNom());
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				AnnotationsRESTClient.post("api/video", params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						setRslt(response);						
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
	
	public void postStream(String id, File f) {
		
		//POST api/video/:id
		RequestParams params = new RequestParams();

		try {
			params.put("stream", f);
			AnnotationsRESTClient.post("api/video/"+id, params, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray response) {
					Toast.makeText(activity, "Upload started", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFinish() {
					Toast.makeText(activity, "File uploaded", Toast.LENGTH_SHORT).show();
				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}			

	}

	public void deleteVideo(String id) {
		//DELETE api/video/id
		AnnotationsRESTClient.delete("api/video/"+id, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray response) {
				
			}
			@Override
			public void onFinish() {
			}
		});
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



}
