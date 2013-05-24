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
public class Video {

	@Id
	private ObjectId id;
	private String nom;
	private String path;

	private Vector<Annotation> annotations;
	private Vector<Observation> observations;

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

	/*
	 * Renvoie la liste de tous les quadrants issus des annotations.
	 */

	public List<Quadrant> getQuadrants() {
		List<Quadrant> quadrants = new ArrayList<Quadrant>();
		for(Annotation a : getAnnotations()) {
			Quadrant qd = a.getQuadrant();
			qd.getMap().put("an", getAnnotations().indexOf(a));
			try {
				if(qd.getMap().get("x") != 0);
				quadrants.add(qd);
			}
			catch(NullPointerException npe) {

			}

		}
		return quadrants;
	}



	/*
	 * Générer un quadrant pour une video donnée
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
			try {
				float x = quadrant.getMap().get("x");
				float y = quadrant.getMap().get("y");
				g.setColor(Quadrant.quelleDominante((int) x, (int)y, quadrants));
				g.fillOval((int) x, (int) y, 20, 20);


				//Ajouter le timecode à coté du point

				g.drawString("["+getAnnotations().get(quadrant.getMap().get("an")).getTimecodeDebut()+" , "+
						getAnnotations().get(quadrant.getMap().get("an")).getTimecodeFin()+"]", x, y);

				//Translation des points dans un repere avec pour origine le point (600, 400)
				trans.put(new Point((int)x-600,(int)y-400), (float) Math.toDegrees(Math.atan((y-400)/(x-600))));
			}
			catch(NullPointerException e) {

			}
		}

		List<Point> keys = new ArrayList<Point>(trans.keySet());
		Collections.sort(keys, new TransComparator(trans));		

		Quadrant.relierPoints(keys, quadrants, g);


		File file = new File("public"+File.separator+"quadrant"+File.separator+getId().toString()+".png");
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Vector<Observation> getObservations() {
		return observations;
	}

	public void setObservations(Vector<Observation> observations) {
		this.observations = observations;
	}
}
