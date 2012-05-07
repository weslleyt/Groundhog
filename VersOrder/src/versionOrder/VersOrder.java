package versionOrder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.Collection;

public class VersOrder implements Runnable{
	public File file;
	public List<File> filesList;
	public String destFolder;
	
	public VersOrder(File file, String destFolder){
		this.file = file;
		filesList = new LinkedList<File>();
		this.destFolder = destFolder;
	}
	
	public void run(){
		getNames(file);
	}
	
	private String findNumber(String str){
		int beginIndex = 0, endIndex = 0;
		boolean found = false;
		
		str = str.substring(0, str.lastIndexOf('.'));
		
		for (int i = 0; i < str.length(); i++){
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				beginIndex = i;
				found = true;
				break;
			}
		}
		
		for (int i = str.length() - 1; i >= 0 ; i--){
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				endIndex = i;
				break;
			}
		}
		
		if (found){
			return str.substring(beginIndex, endIndex+1);
		}else{
			return null;
		}
	}
	
	public void getNames(File sFile){
		File subFiles[] = sFile.listFiles();
		Vector<Organizer> projects = new Vector<Organizer>();
		String ramification = null;
		String lastVersion = null;
		int lvFile = 0;
		boolean lvFileEx = false;
		
		File projectFolder = new File(destFolder + "/" + sFile.getParentFile().getName());
		//System.out.println(sFile.getParentFile().getName());
		if (projectFolder.exists()){
			File lv[] = projectFolder.listFiles();
			//System.out.println(lv[0].getName() + " aqui");
			for (int i = 0;  i <  lv.length; i++){
				if (lv[i].getName().startsWith("lv-")){
					lvFile = i;
					lvFileEx = true;
					break;
				}
			}			
			
			if (lvFileEx){
				try {
					FileReader log = new FileReader(lv[lvFile]);
					BufferedReader readFile = new BufferedReader(log);
					lastVersion = readFile.readLine();
					readFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	        
		}
		
		Organizer last = null;
		for (int i = 0; i < subFiles.length; i++){
			if (!subFiles[i].getName().startsWith("lv-")){
				Boolean isLast = false;
				Organizer a = new Organizer();
				a.file = subFiles[i];
				a.name = subFiles[i].getName();
				String version = findNumber(a.name);
				if (version != null){
					a.separateVersions(version);
					if (lastVersion != null){
						if (last == null && isLastVersion(lastVersion, a)){
							last = a;
							isLast = true;
							System.out.println(a.name + "--  MATCH!!!!");
						}
					}
				}
				if (!isLast){
					projects.add(a);
				}
			}
		}
		
		Collections.sort(projects);
		
		if (last != null){
			projects.add(last);
		}
		
		int i = 0;
		for (Organizer a: projects){
			File novo = new File(a.file.getParentFile().getAbsolutePath() + "/" + i + ".txt");
			a.file.renameTo(novo);
			i++;
		}
		
		
		//saveVersionsLog(sFile, projects);  
		    		
	}

	private void saveVersionsLog(File sFile, Vector<Organizer> projects) {
		String saveLog = destFolder + "/" + sFile.getParentFile().getName();
		File a = new File(saveLog);
		if (!a.exists()){
			a.mkdirs();
		}
		
		FileWriter log;
		try {
			log = new FileWriter (saveLog + "/" + sFile.getName() + ".txt");
			BufferedWriter out = new BufferedWriter(log);
			for (int j = 0; j < projects.size(); j++){
				out.write(projects.elementAt(j).name + '\n');
			}
			out.close();
		} catch (IOException e) {
		}
	}
	
	private	Boolean isLastVersion (String vers, Organizer a){
		Boolean match = true;
		String ver[] = vers.split("[-\\._]");
		
		if (a.version.length == ver.length){
			for (int i = 0; i < ver.length; i++){
				if (ver[i].compareTo(a.version[i]) != 0){
					match = false;
					break;
				}
			}
		}else{
			match = false;
		}
		
		return match;
	}
}


