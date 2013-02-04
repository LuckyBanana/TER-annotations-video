package models;


import java.util.Date;

import org.bson.types.ObjectId;

import play.db.ebean.Model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;


@Entity//("Annotation")
public class Annotation {

	@Id private ObjectId id;
	private String nom;
	//private User user;
	//private Date date;
	private Timecode timecode;
	//private Phase phase;
	//private Intervenant intervenant;
	private String commentaire; // video ?
	//private Video videoAnnotee; // ?

	
	public Annotation() {
		
	}
	
	public Annotation(String n, Timecode t, String c) {
		nom = n;
		timecode = t;
		commentaire = c;
	}
	
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

	/*
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

*/
	public Timecode getTimecode() {
		return timecode;
	}

	public void setTimecode(Timecode timecode) {
		this.timecode = timecode;
	}

	/*
	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public Intervenant getIntervenant() {
		return intervenant;
	}

	public void setIntervenant(Intervenant intervenant) {
		this.intervenant = intervenant;
	}
	*/

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


}