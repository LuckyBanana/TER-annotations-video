package models;

import java.io.File;
import java.util.Vector;

public class Video {
	
	private String id;
	private String nom;
	private String path;
	private File stream;
	private Vector<Annotation> annotations;
	private Vector<Observation> observations;
	
	public Video() {
		setAnnotations(new Vector<Annotation>());
		setObservations(new Vector<Observation>());
	}
	
	public Video(String n, String f) {
		setNom(n);
		setPath(f);
		setAnnotations(new Vector<Annotation>());
		setObservations(new Vector<Observation>());
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

	public File getStream() {
		return stream;
	}

	public void setStream(File stream) {
		this.stream = stream;
	}

	public Vector<Observation> getObservations() {
		return observations;
	}

	public void setObservations(Vector<Observation> observations) {
		this.observations = observations;
	}
	

}
