package models;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class Observation {
	
	@Id 
	private ObjectId id;
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
