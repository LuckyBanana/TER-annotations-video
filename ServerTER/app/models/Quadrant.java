package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.NotSaved;

@Embedded
public class Quadrant {

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
	
	public Quadrant(int x, int y, int v, int i, int p, int m, int e) {
		map.put("x", x);
		map.put("y", y);
		map.put("volonte", v);
		map.put("imagination", i);
		map.put("perception", p);
		map.put("memoire", m);
		map.put("entrainement", e);
	}
	
	/*
	 * Initialise les couleurs correspondant à chanque trait de caractere.
	 */
	
	public void initCouleurs() {
		codeCouleur.put("volonte", Color.red);
		codeCouleur.put("imagination", Color.yellow);
		codeCouleur.put("perception", Color.gray);
		codeCouleur.put("memoire", Color.green);
		codeCouleur.put("entrainement", Color.blue);
	}
	
	/*
	 * Initialise une liste de quadrants avec des valeurs aleatoires.
	 */
	
	public void test(List<Quadrant> quadrants) {
		for(int i = 0; i < 15; i++) {
			Quadrant map = new Quadrant();
			Random r = new Random();
			map.getMap().put("x", r.nextInt(1200));
			map.getMap().put("y", r.nextInt(800));
			map.getMap().put("volonte", r.nextInt(10));
			map.getMap().put("imagination", r.nextInt(10));
			map.getMap().put("perception", r.nextInt(10));
			map.getMap().put("memoire", r.nextInt(10));
			map.getMap().put("entrainement", r.nextInt(10));
			quadrants.add(map);
		}
	}
	
	/*
	 * Renvoie la couleur avec laquelle un point doit etre dessine,
	 * 	en fonction du trait de caractere dominant.
	 */
	
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
	 * Renvoie le trait de caractere dominant.
	 */
	
	public static Color quelleDominante(int x, int y, List<Quadrant> quadrants) {
		int max = 0;
		String code = "Red";
		//for(Quadrant map : quadrants) {
		for(int i = 0; i < quadrants.size(); i++) {
			Quadrant map = quadrants.get(i);
			if(map.getMap().get("x") == x) {
				if(map.getMap().get("y") == y) {
					max = map.getMap().get("volonte");
					code = "volonte";
					if(map.getMap().get("imagination") > max) {
						max = map.getMap().get("imagination");
						code = "imagination";
					}
					if(map.getMap().get("perception") > max) {
						max = map.getMap().get("perception");
						code = "perception";
					}
					if(map.getMap().get("memoire") > max) {
						max = map.getMap().get("memoire");
						code = "memoire";
					}
					if(map.getMap().get("entrainement") > max) {
						max = map.getMap().get("entrainement");
						code = "entrainement";
					}
				}
			}
		}
		return codeCouleur.get(code);
	}

	/*
	 * Renvoie le quart de quadrant dans lequel le point passe en parametre se trouve.
	 * 							1|2
	 * 							---
	 * 							4|3
	 */
	
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
	 * Relie les points entre eux par quadrant et à l'origine.
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

