package controllers;

import java.util.Date;

import models.Annotation;
import models.Intervenant;
import models.Phase;
import models.Timecode;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.google.code.morphia.query.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public class Annotations extends Controller{

	
	public static Result list() throws Exception {
		Query<Annotation> res = MorphiaObject.datastore.find(Annotation.class);
		
		return ok(res.toString());
	}
	
	public static Result save() throws Exception{
		
		Form<Annotation> formAnnot = form(Annotation.class).bindFromRequest();
		if(formAnnot.hasErrors()) {
			return(ok("Erreur"));
		}
		else {
			
			MorphiaObject.datastore.save();//formAnnot.get());
		}

		return ok("Post");
		
	}
	
	public static Result getById(String id) {
		
		return ok();
	}
	
	public static Result delete(String id) {
		
		return ok();
	}
	
}