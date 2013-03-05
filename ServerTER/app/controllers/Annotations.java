package controllers;

import java.util.List;

import com.google.code.morphia.Key;

import models.Annotation;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


public class Annotations extends Controller{

	
	
	
	public static Result list() throws Exception {
		List<Annotation> res = MorphiaObject.datastore.find(Annotation.class).asList();
		return ok(Json.toJson(res));
	}
	
	public static Result save() throws Exception{
		
		
		
		Form<Annotation> formAnnot = Form.form(Annotation.class).bindFromRequest();
		String result = "";
		
		if(formAnnot.hasErrors() || formAnnot.get().getNom() == null) {
			return(ok("Error : Must specify name."));
		}
		else {
			
			Key<Annotation> key = MorphiaObject.datastore.save(formAnnot.get());
			result = key.getId().toString();
		}
		
		return ok();

	}
	
	public static Result getById(String id) {
		
		return ok();
	}
	
	public static Result delete(String id) {
		
		return ok();
	}
	
}