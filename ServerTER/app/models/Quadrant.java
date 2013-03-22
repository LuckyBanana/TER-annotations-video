package models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class Quadrant {
	
	@Id
	ObjectId id = new ObjectId();
	/*
	 * Clés : X, Y, Volonté, Imagination ...
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
		/*
		List<Point> points = new ArrayList<Point>();
		for(HashMap<String, Integer> quadrant : quadrants) {
			points.add(new Point(quadrant.get("X"), quadrant.get("Y")));
		}
		*/
		
		BufferedImage image = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1200, 800);
		g.setColor(Color.BLACK);
		g.drawLine(600, 0, 600, 800);
		g.drawLine(0, 400, 1200, 400);
		
		/*
		for(Point point : points) {
			g.drawOval(point.x, point.y, 10, 10);
		}
		*/
		
		for(HashMap<String, Integer> quadrant : quadrants) {
			g.setColor(Color.RED);
			if(quadrant.get("Imagination") > quadrant.get("Volonte")) {
				g.setColor(Color.YELLOW);
			}
			g.fillOval(quadrant.get("X"), quadrant.get("Y"), 10, 10);
		}
		
		File file = new File("quadrant.png");
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
