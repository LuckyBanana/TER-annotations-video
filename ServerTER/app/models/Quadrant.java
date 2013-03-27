package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.NotSaved;

public class Quadrant {

	@Id
	ObjectId id = new ObjectId();
	private Map<String, Integer> map = new HashMap<String, Integer>();

	/*
	 * Clés : X, Y, Volonté, Imagination ...
	 */

	/*
	 * Volonté, Imagination, Perception, Mémoire, Entrainement
	 */
	@NotSaved
	private static final Map<String, Color> codeCouleur = new HashMap<String, Color>();
	
	public Quadrant() {
		initCouleurs();
	}
	
	public void initCouleurs() {
		codeCouleur.put("Volonte", Color.red);
		codeCouleur.put("Imagination", Color.yellow);
		codeCouleur.put("Perception", Color.gray);
		codeCouleur.put("Memoire", Color.green);
		codeCouleur.put("Entrainement", Color.blue);
	}
	
	public void test(List<Quadrant> quadrants) {
		for(int i = 0; i < 15; i++) {
			Quadrant map = new Quadrant();
			Random r = new Random();
			map.getMap().put("X", r.nextInt(1200));
			map.getMap().put("Y", r.nextInt(800));
			map.getMap().put("Volonte", r.nextInt(10));
			map.getMap().put("Imagination", r.nextInt(10));
			map.getMap().put("Perception", r.nextInt(10));
			map.getMap().put("Memoire", r.nextInt(10));
			map.getMap().put("Entrainement", r.nextInt(10));
			quadrants.add(map);
		}
	}
	
	public static Color quelleCouleur(List<Point> list, List<Quadrant> quadrants) {
		int dist = 0;
		Point res = new Point();
		for(Point point : list) {
			int temp = point.x*point.x + point.y*point.y;
			if(temp > dist) {
				dist = temp;
				res = point;
			}
		}
		return quelleDominante(res.x+600, res.y+400, quadrants);
	}

	/*
	 * Volonté, Imagination, Perception, Mémoire, Entrainement
	 */
	
	public static Color quelleDominante(int x, int y, List<Quadrant> quadrants) {
		int max = 0;
		String code = "";
		for(Quadrant map : quadrants) {
			if(map.getMap().get("X") == x) {
				if(map.getMap().get("Y") == y) {
					max = map.getMap().get("Volonte");
					code = "Volonte";
					if(map.getMap().get("Imagination") > max) {
						max = map.getMap().get("Imagination");
						code = "Imagination";
					}
					if(map.getMap().get("Perception") > max) {
						max = map.getMap().get("Perception");
						code = "Perception";
					}
					if(map.getMap().get("Memoire") > max) {
						max = map.getMap().get("Memoire");
						code = "Memoire";
					}
					if(map.getMap().get("Entrainement") > max) {
						max = map.getMap().get("Entrainement");
						code = "Entrainement";
					}
				}
			}
		}
		return codeCouleur.get(code);
	}

	public static int quelQuadrant(Point point) {
		if(point.x > -600 && point.x < 0 && point.y > -400 && point.y < 0) {
			return 1;
		}
		if(point.x > 0 && point.x < 600 && point.y > -400 && point.y < 0) {
			return 2;
		}
		if(point.x > -600 && point.x < 0 && point.y > 0 && point.y < 400) {
			return 4;
		}
		else {
			return 3;
		}
	}
	
	/*
	 * Liste de points, Liste de 4 couleurs
	 */

	public static void relierPoints(List<Point> keys, List<Quadrant> quadrants, Graphics2D g) {
		
		List<Color> couleurs = new ArrayList<Color>();

		/*
		 * Tri des points selon le cadrant.
		 */
		List<Point> keysQuadrant1 = new ArrayList<Point>();
		List<Point> keysQuadrant2 = new ArrayList<Point>();
		List<Point> keysQuadrant3 = new ArrayList<Point>();
		List<Point> keysQuadrant4 = new ArrayList<Point>();

		for(int i=0; i<keys.size(); i++) {
			if(quelQuadrant(keys.get(i)) == 1) {
				keysQuadrant1.add(keys.get(i));
			}
			if(quelQuadrant(keys.get(i)) == 2) {
				keysQuadrant2.add(keys.get(i));
			}
			if(quelQuadrant(keys.get(i)) == 3) {
				keysQuadrant3.add(keys.get(i));
			}
			if(quelQuadrant(keys.get(i)) == 4) {
				keysQuadrant4.add(keys.get(i));
			}
		}
		
		couleurs.add(quelleCouleur(keysQuadrant1, quadrants));
		couleurs.add(quelleCouleur(keysQuadrant2, quadrants));
		couleurs.add(quelleCouleur(keysQuadrant3, quadrants));
		couleurs.add(quelleCouleur(keysQuadrant4, quadrants));
		
		System.out.println(couleurs.get(0));

		/*
		 * Relier les points de même quadrant
		 * Relier les points entre deux quadrants adjacents
		 */

		/*
		 * Q1
		 */

		g.setColor(Color.BLACK);
		
		for(int i=0; i<keysQuadrant1.size()-1; i++) {
			g.drawLine(keysQuadrant1.get(i).x+600, keysQuadrant1.get(i).y+400, 
					keysQuadrant1.get(i+1).x+600, keysQuadrant1.get(i+1).y+400);
		}

		if(keysQuadrant1.size() != 0) {
			/*
			 * Relier à l'origine
			 */
			g.drawLine(600, 400, keysQuadrant1.get(0).x+600, keysQuadrant1.get(0).y+400);
			g.drawLine(600, 400, keysQuadrant1.get(keysQuadrant1.size()-1).x+600,
					keysQuadrant1.get(keysQuadrant1.size()-1).y+400);
			
			/*
			 * Colorer le quadrant
			 */


			int i = 1;
			int xs[] = new int[keysQuadrant1.size()+1];
			int ys[] = new int[keysQuadrant1.size()+1];
			xs[0] = 600;
			ys[0] = 400;
			for(Point point : keysQuadrant1) {
				xs[i] = point.x+600;
				ys[i] = point.y+400;
				i++;
			}
			g.setColor(couleurs.get(0));
			g.fillPolygon(xs, ys, keysQuadrant1.size()+1);

			//int xs[] = {100, 200, 200, 100};
			//int ys[] = {100, 100, 200, 200};


			/*
			 * Relier aux quadrants adjacents
			 */ 
			//g.drawLine(keysQuadrant1.get(keysQuadrant1.size()-1).x+600, keysQuadrant1.get(keysQuadrant1.size()-1).y+400,
			//	keysQuadrant2.get(0).x+600, keysQuadrant2.get(0).y+400);

		}




		/*
		 * Q2
		 */

		g.setColor(Color.BLACK);
		for(int i=0; i<keysQuadrant2.size()-1; i++) {
			g.drawLine(keysQuadrant2.get(i).x+600, keysQuadrant2.get(i).y+400, 
					keysQuadrant2.get(i+1).x+600, keysQuadrant2.get(i+1).y+400);
		}

		if(keysQuadrant2.size() != 0) {
			/*
			 * Relier à l'origine
			 */
			g.drawLine(600, 400, keysQuadrant2.get(0).x+600, keysQuadrant2.get(0).y+400);
			g.drawLine(600, 400, keysQuadrant2.get(keysQuadrant2.size()-1).x+600,
					keysQuadrant2.get(keysQuadrant2.size()-1).y+400);
			
			/*
			 * Colorer le quadrant
			 */
			
			int i = 1;
			int xs[] = new int[keysQuadrant2.size()+1];
			int ys[] = new int[keysQuadrant2.size()+1];
			xs[0] = 600;
			ys[0] = 400;
			for(Point point : keysQuadrant2) {
				xs[i] = point.x+600;
				ys[i] = point.y+400;
				i++;
			}
			g.setColor(couleurs.get(1));
			g.fillPolygon(xs, ys, keysQuadrant2.size()+1);

			/*
			 * Relier aux quadrants adjacents
			 */ 
			//g.drawLine(keysQuadrant2.get(keysQuadrant2.size()-1).x+600, keysQuadrant2.get(keysQuadrant2.size()-1).y+400,
			//keysQuadrant3.get(0).x+600, keysQuadrant3.get(0).y+400);
		}


		/*
		 * Q3
		 */

		g.setColor(Color.BLACK);
		for(int i=0; i<keysQuadrant3.size()-1; i++) {
			g.drawLine(keysQuadrant3.get(i).x+600, keysQuadrant3.get(i).y+400, 
					keysQuadrant3.get(i+1).x+600, keysQuadrant3.get(i+1).y+400);
		}

		if(keysQuadrant3.size() != 0) {
			/*
			 * Relier à l'origine
			 */
			g.drawLine(600, 400, keysQuadrant3.get(0).x+600, keysQuadrant3.get(0).y+400);
			g.drawLine(600, 400, keysQuadrant3.get(keysQuadrant3.size()-1).x+600,
					keysQuadrant3.get(keysQuadrant3.size()-1).y+400);
			
			/*
			 * Colorer le quadrant
			 */
			
			int i = 1;
			int xs[] = new int[keysQuadrant3.size()+1];
			int ys[] = new int[keysQuadrant3.size()+1];
			xs[0] = 600;
			ys[0] = 400;
			for(Point point : keysQuadrant3) {
				xs[i] = point.x+600;
				ys[i] = point.y+400;
				i++;
			}
			g.setColor(couleurs.get(2));
			g.fillPolygon(xs, ys, keysQuadrant3.size()+1);

			/*
			 * Relier aux quadrants adjacents
			 */ 
			//g.drawLine(keysQuadrant3.get(keysQuadrant3.size()-1).x+600, keysQuadrant3.get(keysQuadrant3.size()-1).y+400,
			//keysQuadrant4.get(0).x+600, keysQuadrant4.get(0).y+400);
		}


		/*
		 * Q4
		 */

		g.setColor(Color.BLACK);
		for(int i=0; i<keysQuadrant4.size()-1; i++) {
			g.drawLine(keysQuadrant4.get(i).x+600, keysQuadrant4.get(i).y+400, 
					keysQuadrant4.get(i+1).x+600, keysQuadrant4.get(i+1).y+400);
		}

		if(keysQuadrant4.size() != 0) {
			/*
			 * Relier à l'origine
			 */
			g.drawLine(600, 400, keysQuadrant4.get(0).x+600, keysQuadrant4.get(0).y+400);
			g.drawLine(600, 400, keysQuadrant4.get(keysQuadrant4.size()-1).x+600,
					keysQuadrant4.get(keysQuadrant4.size()-1).y+400);
			
			/*
			 * Colorer le quadrant
			 */
			
			int i = 1;
			int xs[] = new int[keysQuadrant4.size()+1];
			int ys[] = new int[keysQuadrant4.size()+1];
			xs[0] = 600;
			ys[0] = 400;
			for(Point point : keysQuadrant4) {
				xs[i] = point.x+600;
				ys[i] = point.y+400;
				i++;
			}
			g.setColor(couleurs.get(3));
			g.fillPolygon(xs, ys, keysQuadrant4.size()+1);


			/*
			 * Relier aux quadrants adjacents
			 */ 
			//g.drawLine(keysQuadrant4.get(keysQuadrant4.size()-1).x+600, keysQuadrant4.get(keysQuadrant4.size()-1).y+400,
			//keysQuadrant1.get(0).x+600, keysQuadrant1.get(0).y+400);

		}

	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

}

