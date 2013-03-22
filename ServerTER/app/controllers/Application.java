package controllers;

import models.Quadrant;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller  {



	public static Result index() throws Exception {
	
		
		Quadrant quadrant = new Quadrant();
		quadrant.genererResume();

		return ok("<h1>SALUT</h1><img scr=\"quadrant.png\">").as("text/html");
	}


	public static Result options(String url) {

		return redirect(url);
	}




}