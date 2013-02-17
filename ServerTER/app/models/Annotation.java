package models;


import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


@Entity("Annotation")
public class Annotation {

	@Id 
	private ObjectId id;
	private String nom;

	private String commentaire;
	private String timecodeDebut;
	private String timecodeFin;
	//private User user;
	//private Date date;
	//private Timecode timecode;
	//private Phase phase;
	//private Intervenant intervenant;

	
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
	
	public Annotation() {
		
	}
	
	public Annotation(String n) {
		nom = n;
	}
	
	public Annotation(String n, String c, String td, String tf) {
		nom = n;
		commentaire = c;
		timecodeDebut = td;
		timecodeFin = tf;
	}

	
	


}