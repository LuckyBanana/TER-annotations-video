package models;

import java.util.HashMap;
import java.util.Map;

import quadrants.QuadrantActivity;

public class Quadrant {

	private String x;
	private String y;
	private Map<String, String> traits = new HashMap<String, String>();

	public Map<String, String> getTraits() {
		return traits;
	}
	public void setTraits(Map<String, String> traits) {
		this.traits = traits;
	}

	public Quadrant() {
		this.x = "0";
		this.y = "0";
		traits.put("volonte", QuadrantActivity.VOLONTE);
		traits.put("imagination", QuadrantActivity.IMAGINATION);
		traits.put("perception", QuadrantActivity.PERCEPTION);
		traits.put("memoire", QuadrantActivity.MEMOIRE);
		traits.put("entrainement", QuadrantActivity.ENTRAINEMENT);
	}

	public Quadrant(String x, String y, Map<String, String> m) {
		this.x = x;
		this.y = y;
		this.traits = m;
	}

	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String afficher() {

		return x+" "+y+" "+
				traits.get("volonte")+" "+
				traits.get("imagination")+" "+
				traits.get("perception")+" "+
				traits.get("memoire")+" "+
				traits.get("entrainement");
	}

}
