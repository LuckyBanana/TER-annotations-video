package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller  {



	public static Result index() throws Exception {

		return ok("<h1>QUADRANT</h1>").as("text/html");
	}


	public static Result options(String url) {

		return redirect(url);
	}




}