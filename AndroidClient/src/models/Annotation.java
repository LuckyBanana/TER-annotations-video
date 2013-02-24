package models;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("url_du_server/api/annotation");
		
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		     nameValuePairs.add(new BasicNameValuePair("name", getNom()));
		     nameValuePairs.add(new BasicNameValuePair("commentaire", getCommentaire()));
		     nameValuePairs.add(new BasicNameValuePair("timecodeDebut", getTimecodeDebut()));
		     nameValuePairs.add(new BasicNameValuePair("timecodeFin", getTimecodeFin()));
		     post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		     HttpResponse response = client.execute(post);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
