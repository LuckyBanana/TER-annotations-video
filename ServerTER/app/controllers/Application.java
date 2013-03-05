package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.video;

public class Application extends Controller  {



	public static Result index() throws Exception {

		return ok(video.render("salut"));
		//return ok(index.render("oasis.mp4"));

	}


	public static Result options(String url) {

		return redirect(url);
	}




}