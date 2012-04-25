package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class ToDo extends Model {
	
	
	@Id
	public long id;
	
	
	@OneToOne
	public Invite invite;
	
	
	public String description;


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ToDo [id=").append(id).append(", description=")
				.append(description).append("]");
		return builder.toString();
	}

}
