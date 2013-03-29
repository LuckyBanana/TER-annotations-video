package depreciated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import models.Annotation;
import models.Video;

@SuppressWarnings("rawtypes")
public class HttpClientAsync {

	public static void postVideoData(final Video donnees) {
		
		//String result ="";
		
		
		/*
		System.out.println("Async start !");
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub
				try {
					HttpClient.post("api/video", donnees, 1);
					System.out.println("Success !");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Failure !");
				}
				return null;
			}

		};
		*/
		
	}

	public void postVideoStream(final Video donnees) throws IOException {
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub
				try {
					HttpClient.post("api/video/id="+donnees.getId(), donnees, 2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

		};
	}

	public void postAnnotation(final Annotation donnees) throws IOException {
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub
				try {
					HttpClient.post("api/annotation", donnees);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

		};
	}

	public Video getVideo(final String id) throws IOException {
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub
				String result;
				try {
					result = HttpClient.getOne("api/video", id);
					JSONObject json = new JSONObject(result);
					Video video = (Video) json.get("video");
					return video;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return null;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}				
			}

		};
		return null;


	}

	public Annotation getAnnotation(final String id) throws IOException {
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub
				String result;
				try {
					result = HttpClient.getOne("api/video", id);
					JSONObject json = new JSONObject(result);
					Annotation annotation = (Annotation) json.get("annotation");
					return annotation;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}

			}

		};

		return null;

	}

	public List<Video> listVideos() throws IOException {
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... params) {
				String result;
				try {
					result = HttpClient.get("api/video");
					List<Video> list = new ArrayList<Video>();
					JSONArray jsonArray = new JSONArray(result);
					if (jsonArray != null) { 
						int len = jsonArray.length();
						for (int i=0;i<len;i++){ 
							list.add((Video) jsonArray.get(i));
						} 
					} 
					return list;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return null;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}

			}

		};

		return null;

	}

	public List<Annotation> listAnnotations() throws IOException {
		new AsyncTask() {

			@Override
			protected Object doInBackground(Object... params) {
				String result;
				try {
					result = HttpClient.get("api/annotations");
					List<Annotation> list = new ArrayList<Annotation>();
					JSONArray jsonArray = new JSONArray(result);
					if (jsonArray != null) { 
						int len = jsonArray.length();
						for (int i=0;i<len;i++){ 
							list.add((Annotation) jsonArray.get(i));
						}
					}
					return list;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}

			}

		};
		return null;

	}

}
