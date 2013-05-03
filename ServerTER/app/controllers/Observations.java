package controllers;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import models.Observation;
import models.Video;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Observations extends Controller{
	
	public static Result list() throws Exception {
		List<Observation> res = MorphiaObject.datastore.find(Observation.class).asList();
		return ok(Json.toJson(res));
	}
	
	public static Result save(String id) {

		Form<Observation> form = Form.form(Observation.class).bindFromRequest();
		ObjectId oid = new ObjectId(id);
		UpdateOperations<Video> ops;
		Query<Video> updateQuery = MorphiaObject.datastore.createQuery(Video.class).field("_id").equal(oid);
		
		String result = "";
		if(form.hasErrors() ) {
			return ok("Error : Form has error(s).");
		}
		else {
			Key<Observation> key = MorphiaObject.datastore.save(form.get());
			result = key.getId().toString();
			Observation o = MorphiaObject.datastore.find(Observation.class, "_id", key.getId()).get();
			ops = MorphiaObject.datastore.createUpdateOperations(Video.class).add("observations", o);
			MorphiaObject.datastore.update(updateQuery, ops);
		}

		return ok(result);
	}

}
