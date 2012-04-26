package controllers;

import java.math.BigDecimal;
import java.util.List;

import models.Invite;
import models.Tarif;
import controllers.Application.Stat;
import controllers.Application.Stats;

public class StatsHelper {
	
	
	
	
	
	  public static Stats computStats() {
			Stats stats=new Stats();
			
			List<Invite> invites=Invite.find.all();
			Tarif tarif=Tarif.find.findUnique();
			
			for(Invite invite:invites){
				updateStatForInvite(new CalculInvite(invite),stats,tarif);
				
				
			}
			
			return stats;
		}

		private static void updateStatForInvite(CalculInvite invite, Stats stats,Tarif tarif) {
			
			
			if(invite.isFamilleAurelie()){
				updateStat(stats.aurelie,tarif,invite);
			
			}
			
			if(invite.isFamilleIvan()){
				updateStat(stats.ivan,tarif,invite);
				
			}
			
			if(invite.isAmis()){
				updateStat(stats.amis,tarif,invite);
				
			}
			
			stats.total.dimanche=stats.aurelie.dimanche.add(stats.ivan.dimanche).add(stats.amis.dimanche);
			stats.total.repas=stats.aurelie.repas.add(stats.ivan.repas).add(stats.amis.repas);
			stats.total.vin=stats.aurelie.vin.add(stats.ivan.vin).add(stats.amis.vin);
			stats.total.total=stats.aurelie.total.add(stats.ivan.total).add(stats.amis.total);
			stats.total.nbPersonne=stats.aurelie.nbPersonne+stats.ivan.nbPersonne+stats.amis.nbPersonne;
		}
		
		
		private static void updateStat(Stat stat, Tarif tarif,
				CalculInvite invite) {
			if(invite.isAbsent())
				return;
			
			if(invite.isDimanche()){
				stat.dimanche=compute(stat.dimanche,tarif.tarifDimanche,invite.getInvite().nbAdulte);
			}
			stat.nbPersonne+=invite.getInvite().nbAdulte;
			if(invite.isRepas()){
				stat.repas=compute(stat.repas,tarif.tarifRepas,invite.getInvite().nbAdulte);
				stat.vin=compute(stat.vin,tarif.tarifVinHonneur,invite.getInvite().nbAdulte);
			}
			
			if(invite.isVin()){
				stat.vin=compute(stat.vin,tarif.tarifVinHonneur,invite.getInvite().nbAdulte);
			}
			
			stat.total=stat.vin.add(stat.repas).add(stat.dimanche);
		}


		private static BigDecimal compute(BigDecimal valueInit,
				BigDecimal tarif, int nbAdulte) {
		BigDecimal tarifTotal=tarif.multiply(BigDecimal.valueOf(nbAdulte));
			return valueInit.add(tarifTotal);
			
		}


		static public  class CalculInvite{
			
			private Invite invite;
			public CalculInvite(Invite invite){
				this.invite=invite;
			}
			
			public boolean isVin() {
				return Invite.Presence.VIN_D_HONNEUR.equals(invite.presence);
			}
			public boolean isAbsent() {
				return Invite.Presence.ABSENCE.equals(invite.presence);
			}

			public boolean isRepas() {
				return Invite.Presence.REPAS.equals(invite.presence);
			}

			public boolean isFamilleAurelie(){
				return Invite.Type.FAMILLE_AURELIE.equals(invite.type);
			}
			
			public boolean isFamilleIvan(){
				return Invite.Type.FAMILLE_IVAN.equals(invite.type);
			}
			
			public boolean isAmis(){
				return Invite.Type.AMIS.equals(invite.type);
			}

			public Invite getInvite() {
				return invite;
			}
			
			public boolean isDimanche(){
				return Invite.PresentDimanche.OUI.equals(invite.presentDimanche);
			}
		}

}
