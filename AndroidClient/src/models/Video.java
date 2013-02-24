package models;

import java.io.File;
import java.util.Vector;

public class Video {
	
	private String id;
	private String nom;
	private File stream;
	private Vector<Annotation> annotations;
	
	public Video() {
		setAnnotations(new Vector<Annotation>());
	}
	
	public Video(String n, File f) {
		setNom(n);
		setStream(f);
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

	public File getStream() {
		return stream;
	}

	public void setStream(File stream) {
		this.stream = stream;
	}

	public Vector<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Vector<Annotation> annotations) {
		this.annotations = annotations;
	}
	
	

}
