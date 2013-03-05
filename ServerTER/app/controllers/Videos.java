package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;


import com.google.code.morphia.Key;

import models.Video;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.index;

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
				return ok("File uploaded");


			} else {
				flash("error", "Missing file");
				return redirect("video");    
			}
		
		

	}

	public static Result uploadS() {

		Form<Video> form = Form.form(Video.class).bindFromRequest();

		String result = "";
		if(form.hasErrors() || form.get().getNom() == null) {
			return(ok("Error : Must specify name."));
		}
		else {

			Key<Video> key = MorphiaObject.datastore.save(form.get());
			result = key.getId().toString();
		}

		return ok(result);

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
		List<Video> res = MorphiaObject.datastore.find(Video.class).asList();
		Video vid = res.get(0);
		//response().setHeader("Content-Disposition", "attachment; filename=oasis.mp4");
		response().setContentType("video/mp4");
		File f = new File("oasis.mp4");
		return ok(index.render("oasis.mp4"));
	}




	public static Result saveBinary(String id/*, File file*/) throws IOException
	{

		return ok();
	}

}
