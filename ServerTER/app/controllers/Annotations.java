package controllers;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import models.Annotation;
import models.Video;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


public class Annotations extends Controller{


	/*
	 * GET
	 */

	public static Result list() throws Exception {
		List<Annotation> res = MorphiaObject.datastore.find(Annotation.class).asList();
		return ok(Json.toJson(res));
	}

	/*
	 * POST
	 */

	public static Result save(String id) throws Exception{

		Form<Annotation> formAnnot = Form.form(Annotation.class).bindFromRequest();
		ObjectId oid = new ObjectId(id);
		UpdateOperations<Video> ops;
		Query<Video> updateQuery = MorphiaObject.datastore.createQuery(Video.class).field("_id").equal(oid);
		String result = "";

		if(formAnnot.hasErrors() || formAnnot.get().getNom() == null) {
			return(ok("Error : Must specify name."));
		}
		else {
			Key<Annotation> key = MorphiaObject.datastore.save(formAnnot.get());
			result = key.getId().toString();
			
			Annotation a = MorphiaObject.datastore.find(Annotation.class, "_id", key.getId()).get();
			
			ops = MorphiaObject.datastore.createUpdateOperations(Video.class).add("annotations", a);
			MorphiaObject.datastore.update(updateQuery, ops);
			
			return ok(result);
			
		}

	}

	/*
	 * DELETE
	 */
	
	public static Result delete(String id) {

		return ok();
	}

}