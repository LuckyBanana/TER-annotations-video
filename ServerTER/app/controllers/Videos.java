package controllers;

import java.io.File;
import java.io.IOException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import play.mvc.Controller;

public class Videos  extends Controller{
	
	public void save(String myDirFile, String myNameFile, String myNameDB ) throws IOException
	{
		 
		File file = new File(myDirFile);
		GridFS gridfs = new GridFS(MorphiaObject.mongo.getDB(myNameDB), "videos");
		GridFSInputFile gfsFile = null;
		try {
			gfsFile = gridfs.createFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gfsFile.setFilename(myNameFile);
		gfsFile.save();
	}

}
