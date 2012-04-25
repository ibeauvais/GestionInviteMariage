package controllers;

import models.Invite;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import com.avaje.ebean.annotation.Transactional;

public class Application extends Controller {
	
	
	 public static Result GO_HOME = redirect(
		        routes.Application.index(0, "nom", "asc", "")
		    );
		    
		   
		
	
  
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
    public static Result edit(Long id) {
        Form<Invite> computerForm = form(Invite.class).fill(
        		Invite.find.byId(id)
        );
        return ok(
            editForm.render(id, computerForm)
        );
    }
    @Transactional
    public static Result update(Long id) {
        Form<Invite> inviteForm = form(Invite.class).bindFromRequest();
        if(inviteForm.hasErrors()) {
            return badRequest(editForm.render(id, inviteForm));
        }
        inviteForm.get().update(id);
        flash("success", "Computer " + inviteForm.get().nom + " has been updated");
        return GO_HOME;
    }
    
    @Transactional
    public static Result save() {
        Form<Invite> inviteForm = form(Invite.class).bindFromRequest();
        if(inviteForm.hasErrors()) {
            return badRequest(createForm.render(inviteForm));
        }
        inviteForm.get().save();
        flash("success", "Invite " + inviteForm.get().nom + " has been created");
        return  GO_HOME;
    }
    
    public static Result delete(Long id) {
        Invite.find.ref(id).delete();
        flash("success", "Invite has been deleted");
        return GO_HOME;
    }
    
    
}