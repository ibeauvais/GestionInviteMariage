package models;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Page;



@Entity 
public class Invite  extends Model{
	
	
	public enum Presence{REPAS,VIN_D_HONNEUR,ABSENCE}
	
	
	@Id
	public long Id;
	
	@Required
	public String nom;
	
	@Required
	public Presence presence;
	
	//public boolean isPresentDimanche;
	
	public List<Adulte>adultes;
	public List<Enfant>enfants;
	
	
    public static Finder<Long,Invite> find = new Finder<Long,Invite>(Long.class, Invite.class); 
	
	 public static Page<Invite> page(int page, int pageSize, String sortBy, String order, String filter) {
	        return 
	            find.where()
	                .ilike("nom", "%" + filter + "%")
	                .orderBy(sortBy + " " + order)
	               // .fetch("company")
	                .findPagingList(pageSize)
	                .getPage(page);
	    }
}
