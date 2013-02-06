package models;

import java.util.Date;
import java.util.Vector;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import play.db.ebean.Model;

@Entity
public class Video extends Model {
	
	@Id
	private ObjectId id;
	private String nom;
	private int stream; // stream video ?
	private Vector<Annotation> annotations;
	
	// protected User user;
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

	public int getStream() {
		return stream;
	}

	public void setStream(int stream) {
		this.stream = stream;
	}

	public Vector<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Vector<Annotation> annotations) {
		this.annotations = annotations;
	}

	public Video() {
		
	}
	
}
