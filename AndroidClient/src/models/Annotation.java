package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.annotations.client.AnnotationsRESTClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Annotation {

	private String id;
	private String nom;
	private String commentaire;
	private String timecodeDebut;
	private String timecodeFin;
	
	public Annotation() {
		
	}
	
	public Annotation(String n, String c, String td, String tf) {
		setNom(n);
		setCommentaire(c);
		setTimecodeDebut(td);
		setTimecodeFin(tf);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getTimecodeDebut() {
		return timecodeDebut;
	}
	public void setTimecodeDebut(String timecodeDebut) {
		this.timecodeDebut = timecodeDebut;
	}
	public String getTimecodeFin() {
		return timecodeFin;
	}
	public void setTimecodeFin(String timecodeFin) {
		this.timecodeFin = timecodeFin;
	}
	
	public void post() {
		RequestParams params = new RequestParams();
		params.put("nom", getNom());
		params.put("commentaire", getCommentaire());
		params.put("timecodeDebut", getTimecodeDebut());
		params.put("timecodeFin", getTimecodeFin());
		
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
	
	

}
