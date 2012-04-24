package controllers;

import models.Invite;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

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
	
	
	
		
    public static Result create() {
        Form<Invite> inviteForm = form(Invite.class);
        return ok(
        		createForm.render(inviteForm)
        );
    }
  
    
   
    public static Result save() {
        Form<Invite> inviteForm = form(Invite.class).bindFromRequest();
        if(inviteForm.hasErrors()) {
            return badRequest(createForm.render(inviteForm));
        }
        inviteForm.get().save();
        flash("success", "Invite " + inviteForm.get().nom + " has been created");
        return  redirect(routes.Application.index(0, "nom", "asc", ""));
    }
    
}