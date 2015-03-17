package br.ufpe.cin.jss;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataProjectFileManager {
	
	private String destinyFolder;
	
	public DataProjectFileManager(String destinyFolder) {
		super();
		this.destinyFolder = destinyFolder;
	}

	public String getDestinyFolder() {
		return destinyFolder;
	}

	public void setDestinyFolder(String destinyFolder) {
		this.destinyFolder = destinyFolder;
	}

	public void writeProjectDataIntoCSV(Projeto projeto) throws IOException{
		
		File destinyFolderFile = new File(destinyFolder);
		
		if (!destinyFolderFile.exists()) {
			destinyFolderFile.mkdirs();
		}
		
		String csvFileName = projeto.getNome()+".csv";
		
		FileWriter log = new FileWriter(destinyFolderFile.getAbsolutePath() + "//" + csvFileName);
        BufferedWriter out = new BufferedWriter(log);
        
        out.write("Project Name; Categories;Subproject; #version;isConcurrent; versionName;#classes; #methods;loc");
        out.newLine();
        out.write(projeto.getNome() + ";" + projeto.getCategories());  
        
        out.newLine();
        
        for (SubProject sub : projeto.getSubProjetos()) {
        	 out.write(";;"+sub.getNome()+";"+sub.getVersions().size()+";"+sub.isConcurrent());
        	 out.newLine();
        	 for (Versao version : sub.getVersions()) {
        		 out.write(";;;;;"+version.getIdentificador()+";"+version.getnClasses()+";"+version.getnMetodos()+";"+version.getLoc());
        		 out.newLine();
			}
		}
       
        
        out.flush();
        out.close();
	}
}
