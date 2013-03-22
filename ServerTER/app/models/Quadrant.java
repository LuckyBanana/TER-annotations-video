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
import java.util.Random;

import javax.imageio.ImageIO;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import comparator.TransComparator;

public class Quadrant {

	@Id
	ObjectId id = new ObjectId();
	/*
	 * Clés : X, Y, Volonté, Imagination ...
	 */
	
	/*
	 * Gérer le cas des quadrants vides de points !
	 */
	
	Map<String, Integer> map = new HashMap<String, Integer>();

	public void genererResume() {
		List<HashMap<String, Integer>> quadrants = new ArrayList<HashMap<String, Integer>>();
		for(int i = 0; i < 15; i++) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			Random r = new Random();
			map.put("X", r.nextInt(1200));
			map.put("Y", r.nextInt(800));
			map.put("Volonte", r.nextInt(10));
			map.put("Imagination", r.nextInt(10));
			quadrants.add(map);
		}
		
		BufferedImage image = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1200, 800);
		g.setColor(Color.BLACK);
		g.drawLine(600, 0, 600, 800);
		g.drawLine(0, 400, 1200, 400);

		Map<Point, Float> trans = new HashMap<Point, Float>();
		System.out.println("GO !");
		for(HashMap<String, Integer> quadrant : quadrants) {
			float x = quadrant.get("X");
			float y = quadrant.get("Y");
			g.setColor(Color.RED);
			if(quadrant.get("Imagination") > quadrant.get("Volonte")) {
				g.setColor(Color.YELLOW);
			}
			g.fillOval((int) x, (int) y, 10, 10);
			trans.put(new Point((int)x-600,(int)y-400), (float) Math.toDegrees(Math.atan((y-400)/(x-600))));
		}

		List<Point> keys = new ArrayList<Point>(trans.keySet());
		Collections.sort(keys, new TransComparator(trans));

		g.setColor(Color.BLACK);

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
		
		for(int i=0; i<keysQuadrant1.size()-1; i++) {
			g.drawLine(keysQuadrant1.get(i).x+600, keysQuadrant1.get(i).y+400, 
					keysQuadrant1.get(i+1).x+600, keysQuadrant1.get(i+1).y+400);
		}
		
		g.drawLine(keysQuadrant1.get(keysQuadrant1.size()-1).x+600, keysQuadrant1.get(keysQuadrant1.size()-1).y+400,
				keysQuadrant2.get(0).x+600, keysQuadrant2.get(0).y+400);
		
		for(int i=0; i<keysQuadrant2.size()-1; i++) {
			g.drawLine(keysQuadrant2.get(i).x+600, keysQuadrant2.get(i).y+400, 
					keysQuadrant2.get(i+1).x+600, keysQuadrant2.get(i+1).y+400);
		}
		
		g.drawLine(keysQuadrant2.get(keysQuadrant2.size()-1).x+600, keysQuadrant2.get(keysQuadrant2.size()-1).y+400,
				keysQuadrant3.get(0).x+600, keysQuadrant3.get(0).y+400);
		
		for(int i=0; i<keysQuadrant3.size()-1; i++) {
			g.drawLine(keysQuadrant3.get(i).x+600, keysQuadrant3.get(i).y+400, 
					keysQuadrant3.get(i+1).x+600, keysQuadrant3.get(i+1).y+400);
		}
		
		g.drawLine(keysQuadrant3.get(keysQuadrant3.size()-1).x+600, keysQuadrant3.get(keysQuadrant3.size()-1).y+400,
				keysQuadrant4.get(0).x+600, keysQuadrant4.get(0).y+400);
		
		for(int i=0; i<keysQuadrant4.size()-1; i++) {
			g.drawLine(keysQuadrant4.get(i).x+600, keysQuadrant4.get(i).y+400, 
					keysQuadrant4.get(i+1).x+600, keysQuadrant4.get(i+1).y+400);
		}
		
		g.drawLine(keysQuadrant4.get(keysQuadrant4.size()-1).x+600, keysQuadrant4.get(keysQuadrant4.size()-1).y+400,
				keysQuadrant1.get(0).x+600, keysQuadrant1.get(0).y+400);

		File file = new File("quadrant.png");
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public int quelQuadrant(Point point) {
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

}

