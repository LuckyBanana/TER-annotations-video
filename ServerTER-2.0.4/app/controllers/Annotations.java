package controllers;

import java.util.List;

import models.Annotation;
import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;


public class Annotations extends Controller{

	
	
	
	public static Result list() throws Exception {
		List<Annotation> res = MorphiaObject.datastore.find(Annotation.class).asList();
		
		return ok(res.toString());
	}
	
	public static Result save() throws Exception{
		
		
		/*
		//Form<Annotation> formAnnot;// = form(Annotation.class).bindFromRequest();
		 
		
		if(formAnnot.hasErrors() || formAnnot.get().getNom() == null) {
			return(ok("Error : Must specify name."));
		}
		else {
			
			MorphiaObject.datastore.save(formAnnot.get());
		}
		*/
		return ok();

	}
	
	public static Result getById(String id) {
		
		return ok();
	}
	
	public static Result delete(String id) {
		
		return ok();
	}
	
}