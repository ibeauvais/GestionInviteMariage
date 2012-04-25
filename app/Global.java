

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import models.Invite;
import models.Invite.Presence;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import com.google.common.io.CharStreams;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application arg0) {
		
		super.onStart(arg0);
		
		importDataIfDbEmpty();
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
		return invite;
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
