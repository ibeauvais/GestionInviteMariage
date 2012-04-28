package controllers;

import java.math.BigDecimal;
import java.util.List;

import models.Invite;
import models.Tarif;
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
	                Invite.page(page, 50, sortBy, order, filter),
	                sortBy, order, filter
	            )
	        );
  }
	
	
	
	
	 public static Result stats() {
			Tarif tarif=Tarif.find.findUnique();
		 Stats aStats=StatsHelper.computStats(tarif); 
		 return ok(stats.render(aStats,tarif));
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
    
    
    public static Result editTarif() {
        Form<Tarif> tarifForm = form(Tarif.class).fill(
        		Tarif.find.findUnique());
        
        return ok(
            editTarif.render(tarifForm)
        );
    }
    
    
    @Transactional
    public static Result updateTarif() {
        Form<Tarif> tarifForm = form(Tarif.class).bindFromRequest();
        if(tarifForm.hasErrors()) {
            return badRequest(editTarif.render(tarifForm));
        }
        tarifForm.get().update(Tarif.find.findUnique().id);
        flash("success", "Tarif  has been updated");
        return stats();
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
    
    
    public static class Stat{
    	
    	public BigDecimal repas=BigDecimal.ZERO;
    	public BigDecimal vin=BigDecimal.ZERO;
    	public BigDecimal dimanche=BigDecimal.ZERO;;
    	public BigDecimal total=BigDecimal.ZERO;
    	
    	public int nbAdulteRepas;
    	public int nbAdulteVin;
    	public int nbAdulteDimanche;
    	public int nbAdulteTotal;
    	
    	public int nbEnfantRepas;
    	public int nbEnfantVin;
    	public int nbEnfantDimanche;
    	public int nbEnfantTotal;
    	
    }
    
    public static class Stats{
    	public Stat aurelie=new Stat();
    	public Stat ivan=new Stat();
    	public Stat amis=new Stat();
    	public Stat total=new Stat();
    	
    }
    
    
}