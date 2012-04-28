package models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

import com.avaje.ebean.Page;



@Entity 
public class Invite  extends Model{
	
	
	public enum Presence{REPAS,VIN_D_HONNEUR,ABSENCE,REPAS_SANS_VIN,DIMANCHE_UNIQUEMENT}
	public enum Type{FAMILLE_IVAN,FAMILLE_AURELIE,AMIS}
	public enum PresentDimanche{NON_RENSEIGNE,NON,OUI}
	
	@Id
	public long id;
	

	public String nom;
	public Presence presence;
	public Type type;
	
	public int nbAdulte;
	public int nbEnfant;
	
	@OneToOne(mappedBy="invite")
	public ToDo todo;
	
	public PresentDimanche presentDimanche;
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<Adulte>adultes;
	@OneToMany(cascade=CascadeType.ALL)
	public List<Enfant>enfants;
	
	public boolean valide;
	
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
	 
public static Map<String,String> types(){
		 
		 Map<String,String>  result=new HashMap<String,String>(Type.values().length);
		 for(Type type:Type.values())
			 result.put(type.toString(),type.toString());
		 
		 return result;
	 }

public static Map<String,String> presencesDimanche(){
	 
	Map<String,String>  result=new HashMap<String,String>(PresentDimanche.values().length);
	 for(PresentDimanche presentDimanche:PresentDimanche.values())
		 result.put(presentDimanche.toString(),presentDimanche.toString());
	 
	 return result;
}




	 public void addAdultes(Adulte adulte){
		 if(adultes==null)
			 adultes=new ArrayList<Adulte>();
		 adultes.add(adulte);
	 }
	 
	 public void addEnfant(Enfant enfant){
		 if(enfants==null)
			 enfants=new ArrayList<Enfant>();
		 enfants.add(enfant);
	 }

	 
	 public int getNbAdulte(){
//		 if(adultes==null)
//			 return 0;
//		 else
//			 return adultes.size();
		 return nbAdulte;
	 }
	 
	 public int getNbEnfant(){
//		 if(enfants==null)
//			 return 0;
//		 else
//			 return enfants.size();
		 return nbEnfant;
	 }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invite [id=").append(id).append(", nom=").append(nom)
				.append(", presence=").append(presence).append(", type=")
				.append(type).append(", nbAdulte=").append(nbAdulte)
				.append(", nbEnfant=").append(nbEnfant).append(", todo=")
				.append(todo).append(", presentDimanche=")
				.append(presentDimanche).append("]");
		return builder.toString();
	}
}
