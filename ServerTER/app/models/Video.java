package models;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity("Video")
public class Video{
	
	@Id
	private ObjectId id;
	private String nom;
	private String path;
	
	private Vector<Annotation> annotations;
	// private User user;
	// private Date date;
	
	/*
	 * ACCESSEURS
	 */
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
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
	
	public Video() {
		annotations = new Vector<Annotation>();
	}
	
	public Video(String n, String p) {
		nom = n;
		path = p;
		annotations = new Vector<Annotation>();
	}
	
	public Video(String n, File f) {
		nom = n;
		annotations = new Vector<Annotation>();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	public void upload() {
		
	}
	
	public void download() {
		
	}
	
}
