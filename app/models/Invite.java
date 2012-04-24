package models;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Page;



@Entity 
public class Invite  extends Model{
	
	
	public enum Presence{REPAS,VIN_D_HONNEUR,ABSENCE}
	
	
	@Id
	public long id;
	

	public String nom;
	public Presence presence;
	
//	public Boolean presentDimanche;
//	
//	public List<Adulte>adultes;
//	public List<Enfant>enfants;
	
	
    public static Finder<Long,Invite> find = new Finder<Long,Invite>(Long.class, Invite.class); 
	
	 public static Page<Invite> page(int page, int pageSize, String sortBy, String order, String filter) {
	        return 
	            find.where()
	                .ilike("nom", "%" + filter + "%")
	                .orderBy(sortBy + " " + order)
	                .findPagingList(pageSize)
	                .getPage(page);
	    }
	 
	 
	 
	 public static Map<String,String> presence(){
		 
		 Map<String,String>  result=new HashMap<String,String>(Presence.values().length);
		 for(Presence presence:Presence.values())
			 result.put(presence.toString(),presence.toString());
		 
		 return result;
	 }
}
