package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
@Entity 
public class Tarif extends Model {

	
	@Id
	public long id;
	
	public BigDecimal tarifRepas=BigDecimal.ZERO;
	public BigDecimal tarifRepasEnfant=BigDecimal.ZERO;
	public BigDecimal tarifVinHonneur=BigDecimal.ZERO;
	public BigDecimal tarifVinHonneurEnfant=BigDecimal.ZERO;
	public BigDecimal tarifDimanche=BigDecimal.ZERO;
	public BigDecimal tarifDimancheEnfant=BigDecimal.ZERO;
	public BigDecimal animations=BigDecimal.ZERO;
	public BigDecimal vaisselles=BigDecimal.ZERO;
	public BigDecimal serveurs=BigDecimal.ZERO;
	
	
	
	public static Finder<Long,Tarif> find = new Finder<Long,Tarif>(Long.class, Tarif.class);
	
}
