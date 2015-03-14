package br.ufpe.cin.jss;

import java.io.IOException;
import java.util.ArrayList;

public class JSS {
	
	public static ArrayList<ArrayList<String>> projectsByCategory = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> categoriesName = new ArrayList<String>();
		
	public static void main(String[] args) {
		
		String dominioSourceFolder = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/Dominio/DominioProjetos";
		String logSourceFolder = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/LogProjetos2015/";		
		//String rootDestinyFolder = "/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/ResumoCaracteristicas";
		
		try {
			
			ProjectDataProcessor processor = new ProjectDataProcessor();
			processor.fillProjectsCategory(dominioSourceFolder);
			
			for (Projeto projeto : processor.getProjetos()) {
				System.out.println(projeto.toString());				
			}
			
			processor.fillProjectVersion(logSourceFolder);
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	

}
