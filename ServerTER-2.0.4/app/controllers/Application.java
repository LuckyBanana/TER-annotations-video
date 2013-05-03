package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller  {
	
	
  
  public static Result index() throws Exception {
  

  return ok(controllers.Annotations.list().toString());
  	  	
  }
  
  
  public static Result options(String url) {
	  
	  return redirect(url);
  }
  
  

  
}