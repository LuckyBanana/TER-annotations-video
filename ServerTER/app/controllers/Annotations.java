package controllers;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.WriteResult;

import models.Annotation;
import models.Quadrant;
import models.Video;
import play.data.DynamicForm;
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

		DynamicForm requestData = Form.form().bindFromRequest();

		ObjectId oid = new ObjectId(id);
		UpdateOperations<Video> ops;
		Query<Video> updateQuery = MorphiaObject.datastore.createQuery(Video.class).field("_id").equal(oid);
		String result = "";

		if(requestData.hasErrors() || requestData.get("nom") == null) {
			return ok("Error : Must specify name.");
		}
		else {
			Annotation post;
			try {
				post = new Annotation(
						requestData.get("nom"),
						requestData.get("commentaire"), 
						requestData.get("timecodeDebut"), 
						requestData.get("timecodeFin"), 
						new Quadrant(
								Integer.parseInt(requestData.get("x")),
								Integer.parseInt(requestData.get("y")),
								Integer.parseInt(requestData.get("volonte")),
								Integer.parseInt(requestData.get("imagination")),
								Integer.parseInt(requestData.get("perception")),
								Integer.parseInt(requestData.get("memoire")),
								Integer.parseInt(requestData.get("entrainement"))));
			}
			catch (NumberFormatException nfe) {
				post = new Annotation(
						requestData.get("nom"),
						requestData.get("commentaire"), 
						requestData.get("timecodeDebut"), 
						requestData.get("timecodeFin"));
			}
			
			Key<Annotation> key = MorphiaObject.datastore.save(post);
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
		List<Annotation> res = MorphiaObject.datastore.find(Annotation.class).asList();
		Annotation ann = new Annotation();
		for(Annotation a : res) {
			if(a.getId().toString().equals(id))
				ann = a;
		}
		WriteResult wr = MorphiaObject.datastore.delete(ann);
		return ok(wr.toString());
	}

}