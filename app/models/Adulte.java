package models;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity 
public class Adulte extends Model {
	
	
	public String nom;
	@Required
	public String prenom;
	
	

}
