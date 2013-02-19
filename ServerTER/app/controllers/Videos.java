package controllers;

import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import models.Video;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

public class Videos  extends Controller{
		
	
	public static Result list() {
		
		List<Video> res = MorphiaObject.datastore.find(Video.class).asList();
		return ok(Json.toJson(res));
	}
	
	public static Result getAnnotationsOnVideo(String id) {
		
		return ok();
	}
	
	public static Result upload() {
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
			String path = "uploads"+File.separator+fileName;
			file.renameTo(new File(path));
			file.delete();
			MorphiaObject.datastore.save(new Video(fileName, path));
			return ok(file.getAbsolutePath());


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
		
		return ok("File DELETE : OK");
	}
	
	public static Result getVideo(String id) throws Exception
	{	
		
		return ok();
	}
	
	
	
	
	public static Result saveBinary(String id/*, File file*/) throws IOException
	{
		List<Video> res = MorphiaObject.datastore.find(Video.class).asList();
		Video vid = res.get(0);
		File f = new File("oasis.mp4");
		
		return ok(f);
	}
	
}
