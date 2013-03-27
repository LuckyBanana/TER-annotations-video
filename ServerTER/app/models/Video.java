package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import comparator.TransComparator;

@Entity("Video")
public class Video{

	@Id
	private ObjectId id;
	private String nom;
	private String path;

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

	public Vector<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Vector<Annotation> annotations) {
		this.annotations = annotations;
	}

	public Video() {
		annotations = new Vector<Annotation>();
	}

	public Video(String n, String p) {
		nom = n;
		path = p;
		annotations = new Vector<Annotation>();
	}

	public Video(String n, File f) {
		nom = n;
		annotations = new Vector<Annotation>();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public List<Quadrant> getQuadrants() {
		List<Quadrant> quadrants = new ArrayList<Quadrant>();
		for(Annotation a : getAnnotations()) {
			quadrants.add(a.getQuadrant());
		}
		return quadrants;
	}



	/*
	 * Générer un quadrant pour une video
	 */

	public void genererQuadrant() {
		List<Quadrant> quadrants = getQuadrants();

		BufferedImage image = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1200, 800);
		g.setColor(Color.BLACK);
		g.drawLine(600, 0, 600, 800);
		g.drawLine(0, 400, 1200, 400);

		Map<Point, Float> trans = new HashMap<Point, Float>();
		for(Quadrant quadrant : quadrants) {
			float x = quadrant.getMap().get("X");
			float y = quadrant.getMap().get("Y");
			g.setColor(Quadrant.quelleDominante((int) x, (int)y, quadrants));

			g.fillOval((int) x, (int) y, 20, 20);
			trans.put(new Point((int)x-600,(int)y-400), (float) Math.toDegrees(Math.atan((y-400)/(x-600))));
		}

		List<Point> keys = new ArrayList<Point>(trans.keySet());
		Collections.sort(keys, new TransComparator(trans));		

		Quadrant.relierPoints(keys, quadrants, g);


		File file = new File(getId().toString()+".png");
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
