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
	//private File stream;
	@Embedded
	private Fichier stream;
	@Embedded
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
	/*
	public File getStream() {
		return stream;
	}

	public void setStream(File stream) {
		this.stream = stream;
	}
	*/
	public Vector<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Vector<Annotation> annotations) {
		this.annotations = annotations;
	}
	
	public Video() {
		setStream(null);
		annotations = new Vector<Annotation>();
	}
	
	public Video(String n, Fichier f) {
		nom = n;
		setStream(f);
		annotations = new Vector<Annotation>();
	}
	
	public Video(String n, File f) throws IOException {
		nom = n;
		setStream(new Fichier(f));
		annotations = new Vector<Annotation>();
	}
	
	public Video(String n, File f, String e) throws IOException {
		nom = n;
		setStream(new Fichier(e, f));
		annotations = new Vector<Annotation>();
	}

	public Fichier getStream() {
		return stream;
	}

	public void setStream(Fichier stream) {
		this.stream = stream;
	}
	
	
	
}
