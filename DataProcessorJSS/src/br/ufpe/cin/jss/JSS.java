package br.ufpe.cin.jss;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JSS {
	
	public static ArrayList<ArrayList<String>> projectsByCategory = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> categoriesName = new ArrayList<String>();
	static File fileSourceFolder;
		
	public static void main(String[] args) {
		
		String dominioSourceFolder = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/Dominio/DominioProjetos";
		String logSourceFolder ="C:/Users/BenitoAvell/Documents/mestrado/JSS/log/";
		//String logSourceFolder = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/LogProjetos2015Final/";		
		String rootDestinyFolder = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/ResumoCaracteristicas/";
		
		try {
			
			ProjectDataProcessor processor = new ProjectDataProcessor();
			processor.fillProjectsCategory(dominioSourceFolder);
			
			DataProjectFileManager fileManager = new DataProjectFileManager(rootDestinyFolder);
			
			fileSourceFolder = new File (logSourceFolder);			
			processor.fillProjectVersions(fileSourceFolder,false,null);
			
			for (Projeto projeto : processor.getProjetos()) {
				
//				for (Projeto sub : projeto.getSubProjetos()) {
//					for (Versao versao : sub.getVersoes()) {
//						System.out.println(projeto.toString() +" "+ sub.toString() +" "+ versao.getVersaoLog().getName());
//					}
//				}	
				
				fileManager.writeProjectDataIntoCSV(projeto);
							
			}
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
	}
	
	

}
