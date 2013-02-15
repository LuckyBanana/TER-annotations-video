package models;

import java.io.File;
import java.io.Serializable;
import java.util.Vector;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

@Entity("Video")
public class Video implements Serializable{
	
	@Id
	private ObjectId id;
	private String nom;
	private File stream;
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
	
	public Video() {
		stream = null;
		annotations = new Vector<Annotation>();
	}
	
	public Video(String n, File f) {
		nom = n;
		stream = f;
		annotations = new Vector<Annotation>();
	}
	
	
	
}
