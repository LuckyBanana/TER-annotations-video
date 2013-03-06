package models;

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
	
	
	

}
