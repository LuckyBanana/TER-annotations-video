package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class Video {
	
	private String id;
	private String nom;
	private String path;
	private File stream;
	private Vector<Annotation> annotations;
	
	public Video() {
		setAnnotations(new Vector<Annotation>());
	}
	
	public Video(String n, String f) {
		setNom(n);
		setPath(f);
		setAnnotations(new Vector<Annotation>());
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

	public Vector<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Vector<Annotation> annotations) {
		this.annotations = annotations;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String post() {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("url_du_server/api/annotation");
		
		String id = "";
		
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		     nameValuePairs.add(new BasicNameValuePair("name", getNom()));
		     post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		     HttpResponse response = client.execute(post);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}

	public File getStream() {
		return stream;
	}

	public void setStream(File stream) {
		this.stream = stream;
	}
	

}
