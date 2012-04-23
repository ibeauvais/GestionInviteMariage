package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity 
public class Enfant extends Model {
	
	@Id
	public long Id;
	@Required
	private String nom;
	private int age;

}
