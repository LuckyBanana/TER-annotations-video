package models;

import java.util.HashMap;
import java.util.Map;

import quadrants.QuadrantActivity;

public class Quadrant {
	
	private int x;
	private int y;
	private Map<String, String> traits = new HashMap<String, String>();
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Map<String, String> getTraits() {
		return traits;
	}
	public void setTraits(Map<String, String> traits) {
		this.traits = traits;
	}
	
	public Quadrant() {
		this.x = 0;
		this.y = 0;
		traits.put("volonte", QuadrantActivity.VOLONTE);
		traits.put("imagination", QuadrantActivity.IMAGINATION);
		traits.put("perception", QuadrantActivity.PERCEPTION);
		traits.put("memoire", QuadrantActivity.MEMOIRE);
		traits.put("entrainement", QuadrantActivity.ENTRAINEMENT);
	}
	
	public Quadrant(int x, int y, Map<String, String> m) {
		this.x = x;
		this.y = y;
		this.traits = m;
	}

}
