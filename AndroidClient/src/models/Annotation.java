package models;

public class Annotation {

	private String id;
	private String nom;
	private String commentaire;
	private String timecodeDebut;
	private String timecodeFin;
	private Quadrant quadrant;
	
	public Annotation() {
		
	}
	
	public Annotation(String n, String c, String td, String tf) {
		setNom(n);
		setCommentaire(c);
		setTimecodeDebut(td);
		setTimecodeFin(tf);
	}
	
	public Annotation(String n, String c, String td, String tf, Quadrant q) {
		this(n, c, td, tf);
		setQuadrant(q);
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
	public Quadrant getQuadrant() {
		return quadrant;
	}
	public void setQuadrant(Quadrant quadrant) {
		this.quadrant = quadrant;
	}
	
	
	

}
