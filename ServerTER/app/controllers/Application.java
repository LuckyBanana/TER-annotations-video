package controllers;

import java.util.List;

import org.bson.types.ObjectId;

import models.Annotation;
import models.Video;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.video;

public class Application extends Controller  {



	public static Result index() throws Exception {
	
		List<Video> myVideos = MorphiaObject.datastore.find(Video.class).asList();
		Video myVideo = myVideos.get(3);
		ObjectId myID = myVideo.getId();
		
	
		
		List<Annotation> myAnnos = myVideo.getAnnotations();
		String myPath = myVideo.getPath();
		//return ok(index.render(myPath, myAnnos));
		return ok(myAnnos.size()+"");
	}


	public static Result options(String url) {

		return redirect(url);
	}




}