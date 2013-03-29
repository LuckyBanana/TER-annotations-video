package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;


public class Intervenant {
	
	@Id
	private ObjectId id;
	private String nom;
	private Status status;
	
	public Intervenant() {
		
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

}
