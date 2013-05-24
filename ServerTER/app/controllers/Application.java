package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.doc;

public class Application extends Controller  {



	public static Result index() throws Exception {

		return ok(doc.render());
	}


	public static Result options(String url) {

		return redirect(url);
	}




}