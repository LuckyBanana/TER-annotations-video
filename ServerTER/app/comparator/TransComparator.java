package comparator;

import java.awt.Point;
import java.util.Comparator;
import java.util.Map;


public class TransComparator implements Comparator<Point>{
	
	private Map<Point, Float> map;
	
	public TransComparator(Map<Point, Float> map) {
		this.map = map;
	}

	@Override
	public int compare(Point o1, Point o2) {
		Float a1 = map.get(o1);		
		Float a2 = map.get(o2);	
		return a1.compareTo(a2);
	}

}