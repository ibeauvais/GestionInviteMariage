

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import models.Adulte;
import models.Enfant;
import models.Invite;
import models.Invite.Presence;
import models.Invite.PresentDimanche;
import models.Invite.Type;
import models.Tarif;
import models.ToDo;

import org.apache.commons.lang.StringUtils;

import play.Application;
import play.GlobalSettings;
import play.Logger;

import com.google.common.io.CharStreams;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application arg0) {
		
		super.onStart(arg0);
		
		//importDataIfDbEmpty();
		 Logger.info("Application has started");
	}

	private void importDataIfDbEmpty() {
		if(Invite.find.all().size()==0){
			try {
				importDataFromCsv("listes.csv");
			} catch (IOException e) {
				Logger.error("error",e);
			}
		}
		
	}

	private void importDataFromCsv(String fileName) throws IOException {
		
		FileReader file=new FileReader("conf/"+fileName);
		String data=CharStreams.toString(file);
		
		String[] lines=data.split("\n");
		boolean firstLine=true;
		for(String line:lines){
			if(firstLine){firstLine=false;
			Logger.error(getLineUsed(line));
			continue;
			}
			if(StringUtils.isBlank(line))
				continue;
			
			addInvite(line);
		}
		
		Tarif tarif=new Tarif();
		tarif.tarifDimanche=BigDecimal.valueOf(30);
		tarif.tarifRepas=BigDecimal.valueOf(60);
		tarif.tarifVinHonneur=BigDecimal.valueOf(20);
		tarif.save();
	}

	private String getLineUsed(String line) {
	
		
		String[] column=line.split(";");
		StringBuilder builder=new StringBuilder();
		appendColumn(column,0,builder);
		appendColumn(column,9,builder);
		appendColumn(column,10,builder);
		appendColumn(column,11,builder);
		
		return builder.toString();
	}

	private void appendColumn(String[] column, int i,StringBuilder builder) {
		if(builder.length()>0)
			builder.append(", ");
		builder.append(i);
		builder.append("=>");
		builder.append(column[i]);
		
	}

	private void addInvite(String line) {

		Invite invite=convertToInvite(line);
		if(invite!=null){
			invite.save();
			Logger.info("save invite "+invite);
		}
		
	}

	private Invite convertToInvite(String line) {
		String[] column=line.split(";");
		if(StringUtils.isBlank(column[9]))
				return null;
		Invite invite=new Invite();
		invite.nom=column[0];
		invite.presence=toPresence(column,invite.nom);
		invite.type=toType(column,invite.nom);
		addAdulte(invite,column[3]);
		addEnfant(invite,column[4]);
		invite.presentDimanche=presentDimanche(column[12]);
		return invite;
	}
	
	

	private PresentDimanche presentDimanche(String value) {
		if(StringUtils.isBlank(value))
			return PresentDimanche.NON_RENSEIGNE;
		
		if(isCheck2(value))
			return PresentDimanche.OUI;
		else
			return PresentDimanche.NON;
	}

	private boolean isCheck2(String value) {
		int valueInt=Integer.parseInt(value);
		
		return valueInt>0;
			
		
	}

	private void addEnfant(Invite invite, String nbEnfantString) {
		try{
			int nbEnfant=Integer.parseInt(nbEnfantString);
			invite.nbEnfant=nbEnfant;
			for(int i=0;i<nbEnfant;i++){
				Enfant enfant=new Enfant();
				enfant.nom="enfant"+(i+1)+" nom";
				enfant.age="18 Mois";
				invite.addEnfant(enfant);
				
			}
			
			if(invite.todo==null){
				invite.todo=new ToDo();
				invite.todo.invite=invite;
				invite.todo.description="";
			}
			invite.todo.description+="Completer Enfants";
		}
		catch(NumberFormatException  e){
			Logger.error("["+invite.nom+"]"+"NumberFormatException for convert "+nbEnfantString);
		}
		
	}

	private void addAdulte(Invite invite, String nbAdulteString) {
		try{
			int nbAdulte=Integer.parseInt(nbAdulteString);
			invite.nbAdulte=nbAdulte;
			for(int i=0;i<nbAdulte;i++){
				Adulte adulte=new Adulte();
				adulte.nom="adulte"+(i+1)+" nom";
				adulte.prenom="adulte"+(i+1)+" prenom";
				invite.addAdultes(adulte);
				
			}
			
			if(invite.todo==null){
				invite.todo=new ToDo();
				invite.todo.invite=invite;
				invite.todo.description="";
			}
			invite.todo.description+="Completer Adultes";
		}
		catch(NumberFormatException  e){
			Logger.error("["+invite.nom+"]"+"NumberFormatException for convert "+nbAdulteString);
		}
		
	}
	
	private Type toType(String[] column,String nom) {
		boolean fAurelie=isCheck(column[5]);
		boolean fIvan=isCheck(column[6]);
		boolean amis=isCheck(column[7]);
		
		if((fAurelie && fIvan) || (fAurelie && amis) || (fIvan && amis))
				Logger.error("["+nom+"] Trop d'info coche fAurelie="+fAurelie+" fIvan="+fIvan+" amis="+amis);
		
		if(fAurelie)return Type.FAMILLE_AURELIE;
		if(fIvan)return Type.FAMILLE_IVAN;
		if(amis)return Type.AMIS;
		
		return null;
	}

	private Presence toPresence(String[] column,String nom) {
		boolean repas=isCheck(column[9]);
		boolean vindho=isCheck(column[10]);
		boolean absence=isCheck(column[11]);
		
		if((repas && vindho) || (repas && absence) || (vindho && absence))
				Logger.error("["+nom+"] Trop d'info coche repas="+repas+" absence="+absence+" vindho="+vindho);
		
		if(repas)return Presence.REPAS;
		if(vindho)return Presence.VIN_D_HONNEUR;
		if(absence)return Presence.ABSENCE;
		
		return null;
	}

	private boolean isCheck(String value) {
		return "1".equals(value);
	}

}
