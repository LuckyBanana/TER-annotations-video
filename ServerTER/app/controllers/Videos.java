package controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import models.Annotation;
import models.Video;
import play.Logger;
import play.api.Play;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import com.mongodb.DBCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class Videos  extends Controller{
	
	private static String dirFile = "oasis.mp4";
	private static String nameGFS = "mp4";
	public static Form<Video> form = Form.form(Video.class);

	
	public static Result saveBinary(String id/*, File file*/) throws IOException
	{
		
		try {
			
			GridFS gridFS = new GridFS(MorphiaObject.mongo.getDB("test"), nameGFS);
			File file = new File(dirFile);
			FileInputStream fileInputStream = new FileInputStream(file);
			GridFSInputFile gfsFile = gridFS.createFile(fileInputStream);
			gfsFile.setFilename(dirFile);
			gfsFile.save();
			
		} catch (IOException e) {
			
				Logger.debug(e.toString());
				e.printStackTrace();
				return ok("File SAVE : ERROR");
		}
		
		
		return ok();
	}
	
	public static Result list() {
		
		List<Video> res = MorphiaObject.datastore.find(Video.class).asList();
		return ok(Json.toJson(res));
	}
	
	public static Result getAnnotationsOnVideo(String id) {
		
		return ok();
	}
	
	public static Result upload() {
		  MultipartFormData body = request().body().asMultipartFormData();
		  FilePart video = body.getFile("stream");
		  if (video != null) {
		    String fileName = video.getFilename();
		    File file = video.getFile();
		    Video vid = new Video(fileName, file);
		    //file.delete();
		    MorphiaObject.datastore.save(vid);
		    return ok("File uploaded");
		  } else {
		    flash("error", "Missing file");
		    return redirect("erreur");    
		  }

		}
	
	public static Result save() throws Exception
	{

		/*
		Form<Video> filled = form.bindFromRequest();
		if(filled.hasErrors()) {
			return(ok("Erreur : form has errors"));
		}
		else {
			File f = new File("oasis.mp4");
			//File f = filled.get().getStream();
			try {
				//File file = new File(dirFile);
				FileInputStream fileInputStream = new FileInputStream(f);
				GridFSInputFile gfsFile = MorphiaObject.fs.createFile(fileInputStream);
				gfsFile.setFilename(f.getName());
				gfsFile.save();
				
			} catch (IOException e) {
					Logger.debug(e.toString());
					e.printStackTrace();
					return ok("File SAVE : ERROR");
			} catch (NullPointerException e) {
				return ok("null file... :(");
			}
			
			MorphiaObject.datastore.save(filled.get());	
		}
		*/
		return ok(""+response());
	}

	public static Result delete(String id) throws Exception
	{	
		GridFS gfsFile = new GridFS(MorphiaObject.mongo.getDB("test"), nameGFS);
		gfsFile.remove(gfsFile.findOne(id));
		return ok("File DELETE : OK");
	}
	
	public static Result getVideo(String id) throws Exception
	{	
		File f = new File(id);
		GridFS gfsFile = new GridFS(MorphiaObject.mongo.getDB("test"), nameGFS);
		GridFSDBFile outputFile = gfsFile.findOne(id);
		outputFile.writeTo(f);
		return ok(outputFile.toString());
	}
	
}
