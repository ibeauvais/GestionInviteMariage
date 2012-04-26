package controllers;

import java.math.BigDecimal;
import java.util.List;

import models.Invite;
import models.Tarif;
import controllers.Application.Stat;
import controllers.Application.Stats;

public class StatsHelper {
	
	
	
	
	
	  public static Stats computStats(Tarif tarif) {
			Stats stats=new Stats();
			
			List<Invite> invites=Invite.find.all();
		
			
			for(Invite invite:invites){
				updateStatForInvite(new CalculInvite(invite),stats,tarif);
			}
			
			stats.total.nbAdulteDimanche=stats.aurelie.nbAdulteDimanche+stats.ivan.nbAdulteDimanche+stats.amis.nbAdulteDimanche;
			stats.total.nbEnfantDimanche=stats.aurelie.nbEnfantDimanche+stats.ivan.nbEnfantDimanche+stats.amis.nbEnfantDimanche;
			
			stats.total.nbAdulteRepas=stats.aurelie.nbAdulteRepas+stats.ivan.nbAdulteRepas+stats.amis.nbAdulteRepas;
			stats.total.nbEnfantRepas=stats.aurelie.nbEnfantRepas+stats.ivan.nbEnfantRepas+stats.amis.nbEnfantRepas;
			
			stats.total.nbAdulteVin=stats.aurelie.nbAdulteVin+stats.ivan.nbAdulteVin+stats.amis.nbAdulteVin;
			stats.total.nbEnfantVin=stats.aurelie.nbEnfantVin+stats.ivan.nbEnfantVin+stats.amis.nbEnfantVin;
			
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
			
			
		}
		
		
		private static void updateStat(Stat stat, Tarif tarif,
				CalculInvite invite) {
			if(invite.isAbsent())
				return;
			
			if(invite.isDimanche()){
				stat.dimanche=compute(stat.dimanche,tarif.tarifDimanche,tarif.tarifDimancheEnfant,invite.getInvite().nbAdulte,invite.getInvite().nbEnfant);
				stat.nbAdulteDimanche+=invite.getInvite().nbAdulte;
				stat.nbEnfantDimanche+=invite.getInvite().nbEnfant;
			}
			
			if(invite.isRepas()){
				stat.repas=compute(stat.repas,tarif.tarifRepas,tarif.tarifRepasEnfant,invite.getInvite().nbAdulte,invite.getInvite().nbEnfant);
				stat.vin=compute(stat.vin,tarif.tarifVinHonneur,tarif.tarifVinHonneurEnfant,invite.getInvite().nbAdulte,invite.getInvite().nbEnfant);
				
				stat.nbAdulteRepas+=invite.getInvite().nbAdulte;
				stat.nbEnfantRepas+=invite.getInvite().nbEnfant;
				stat.nbAdulteVin+=invite.getInvite().nbAdulte;
				stat.nbEnfantVin+=invite.getInvite().nbEnfant;
			}
			
			if(invite.isVin()){
				stat.vin=compute(stat.vin,tarif.tarifVinHonneur,tarif.tarifVinHonneurEnfant,invite.getInvite().nbAdulte,invite.getInvite().nbEnfant);
				stat.nbAdulteVin+=invite.getInvite().nbAdulte;
				stat.nbEnfantVin+=invite.getInvite().nbEnfant;
			}
			
			stat.total=stat.vin.add(stat.repas).add(stat.dimanche);
			
		}


		private static BigDecimal compute(BigDecimal valueInit,
				BigDecimal tarif,BigDecimal tarifEnfant, int nbAdulte,int NbEnfant) {
		BigDecimal tarifAdulteTotal=tarif.multiply(BigDecimal.valueOf(nbAdulte));
		BigDecimal tarifEnfantTotal=tarifEnfant.multiply(BigDecimal.valueOf(NbEnfant));
			return valueInit.add(tarifAdulteTotal).add(tarifEnfantTotal);
			
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
