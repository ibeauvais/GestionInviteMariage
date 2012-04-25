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
	public String nom;
	public String age;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enfant [Id=").append(Id).append(", nom=").append(nom)
				.append(", age=").append(age).append("]");
		return builder.toString();
	}

}
