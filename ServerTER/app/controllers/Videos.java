package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.activation.MimetypesFileTypeMap;

import models.Annotation;
import models.Video;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class Videos  extends Controller{
	
	private static String dirFile = "oasis.mp4";
	private static String nameGFS = "mp4";
	//public static Form<Video> form = Form.form(Video.class);

	
	
	
	public static Result list() {
		
		List<Video> res = MorphiaObject.datastore.find(Video.class).asList();
		return ok(Json.toJson(res));
	}
	
	public static Result getAnnotationsOnVideo(String id) {
		
		return ok();
	}
	
	public static Result upload() {
		Vector<Annotation> ann = new Vector<Annotation>();
		ann.add(new Annotation("a 1"));
		ann.add(new Annotation("a 2"));
		MultipartFormData body = request().body().asMultipartFormData();
		DynamicForm df = Form.form().bindFromRequest();
		FilePart video = body.getFile("stream");
		if (video != null) {
			String fileName;
			if(df.hasErrors()) {
				fileName = video.getFilename();
			}
			else {
				fileName = df.get("nom");
			}
			File file = video.getFile();
			Video vid;
			try {
				vid = new Video(fileName, file);
				vid.setAnnotations(ann);
				MorphiaObject.datastore.save(vid);
				return ok("File uploaded");
			} catch (IOException e) {
				flash("error", "Missing file");
				return redirect("video");    
			}
			
		} else {
			flash("error", "Missing file");
			return redirect("video");    
		}

	}
	
	public static Result save() throws Exception
	{

		
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
		Video video = MorphiaObject.datastore.find(Video.class, "nom =", "oasis 1").get();
		File someFile = new File("test");
		someFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(someFile);
		fos.write(video.getStream().getData());
		fos.flush();
		fos.close();
		return ok(someFile);
	}
	
	
	
	
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
	
}
