package controllers;

import java.io.File;
import java.io.IOException;

import com.mongodb.DBCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.io.FileInputStream;

public class Videos  extends Controller{
	
	private static String dirFile = "C:\\Users\\Administrateur\\Desktop\\Zics\\danm\\Umek - Analok.mp3";
	private static String nameGFS = "mp3";

	
	public static Result saveBinary(String id, String myDirFile) throws IOException
	{
		return ok();
	}
	
	public static Result list() {
		GridFS gridfs = new GridFS(MorphiaObject.datastore.getDB(), "videos");
		DBCursor cursor = gridfs.getFileList();
		return ok(Json.toJson(cursor.toArray()));
	}
	
	public static Result getAnnotationsOnVideo(String id) {
		
		return ok();
	}
	
	public static Result save(String nameFile) throws Exception
	{
		
		try {
			
			GridFS gridFS = new GridFS(MorphiaObject.mongo.getDB("test"), nameGFS);
			File file = new File(dirFile);
			FileInputStream fileInputStream = new FileInputStream(file);
			GridFSInputFile gfsFile = gridFS.createFile(fileInputStream);
			gfsFile.setFilename(nameFile);
			gfsFile.save();
			
		} catch (IOException e) {
			
				Logger.debug(e.toString());
				e.printStackTrace();
				return ok("File SAVE : ERROR");
		}
		
		
		return ok("File SAVE : OK");
	}

	public static Result delete(String nameFile) throws Exception
	{	
		GridFS gfsFile = new GridFS(MorphiaObject.mongo.getDB("test"), nameGFS);
		gfsFile.remove(gfsFile.findOne(nameFile));
		return ok("File DELETE : OK");
	}
	
	public static Result getVideo(String nameFile) throws Exception
	{	
		GridFS gfsFile = new GridFS(MorphiaObject.mongo.getDB("test"), nameGFS);
		GridFSDBFile outputFile = gfsFile.findOne(nameFile);
		return ok(outputFile.toString());
	}
	
}
