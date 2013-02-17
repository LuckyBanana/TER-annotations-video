package models;

import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;

import com.google.code.morphia.annotations.Embedded;
import com.google.common.io.Files;

@Embedded
public class Fichier {
	
	private String type;
	private byte[] data;
	
	public Fichier() {
		
	}
	
	public Fichier(File f) throws IOException {
		this.type = new MimetypesFileTypeMap().getContentType(f);
		this.setData(Files.toByteArray(f));
	}
	
	public Fichier(String t, File f) throws IOException {
		this.setType(t);
		this.setData(Files.toByteArray(f));
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
