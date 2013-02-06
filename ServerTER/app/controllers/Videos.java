package controllers;

import java.io.File;
import java.io.IOException;
<<<<<<< HEAD
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
=======
>>>>>>> squelette api

import com.mongodb.DBCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Videos extends Controller{

	public static Result saveBinary(String id, String myDirFile) throws IOException
	{

		File file = new File(myDirFile);
		GridFS gridfs = new GridFS(MorphiaObject.datastore.getDB(), "videos");
		GridFSInputFile gfsFile = null;
		try {
			gfsFile = gridfs.createFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gfsFile.setFilename(file.getName());
		gfsFile.save();
		
		return ok();
	}
	
	public static Result list() {
		GridFS gridfs = new GridFS(MorphiaObject.datastore.getDB(), "videos");
		DBCursor cursor = gridfs.getFileList();
		return ok(Json.toJson(cursor.toArray()));
	}
	
	public static Result getVideo(String id) {
		
		return ok();
	}
	
	public static Result getAnnotationsOnVideo(String id) {
		
		return ok();
	}
	
	public static Result save() {
		
		return ok();
	}
	
	public static Result delete(String id) {
		
		return ok();
	}

}
