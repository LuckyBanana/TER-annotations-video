package models;

public class Observation {
	
	// codeObservation = { 1, .9, .8, .7, .6, .5, .4, .2 }
	private String codeObservation;
	private String timecode;
	
	public Observation() {
		
	}
	
	public Observation(String c, String t) {
		setCodeObservation(c);
		setTimecode(t);
	}
	
	public String getCodeObservation() {
		return codeObservation;
	}
	public void setCodeObservation(String codeObservation) {
		this.codeObservation = codeObservation;
	}
	public String getTimecode() {
		return timecode;
	}
	public void setTimecode(String timecode) {
		this.timecode = timecode;
	}

}
