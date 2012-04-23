package controllers;

import models.Invite;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.avaje.ebean.annotation.Transactional;

public class Application extends Controller {
  
	@Transactional(readOnly=true)
  public static Result index(int page, String sortBy, String order, String filter) {
	  return ok(index.render(
	                Invite.page(page, 10, sortBy, order, filter),
	                sortBy, order, filter
	            )
	        );
  }
  
}