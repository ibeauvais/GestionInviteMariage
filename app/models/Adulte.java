package models;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity 
public class Adulte extends Model {
	
	
	public String nom;
	@Required
	public String prenom;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adulte [nom=").append(nom).append(", prenom=")
				.append(prenom).append("]");
		return builder.toString();
	}
	
	

}
