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
		String concurrentProjectListFilePath = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/ResultadoMetricas/Geral/Lines of CodeProjectNameHasMetric.csv";		
		String rootDestinyFolder = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/ResumoCaracteristicasV6/";
		String projectNamesFilePath = "C:/Users/BenitoAvell/Google Drive/jss/ArtigoGroundHogJSS/Dados/projectsNames.txt";
		try {
			
			ProjectDataProcessor processor = new ProjectDataProcessor();
			processor.fillProjects(projectNamesFilePath);
			processor.fillProjectsCategory(dominioSourceFolder);
			
			DataProjectFileManager fileManager = new DataProjectFileManager(rootDestinyFolder);
			
			fileSourceFolder = new File (logSourceFolder);			
			processor.fillProjectVersions(fileSourceFolder,false,null);
			processor.fillConcurrencyPropertie(new File(concurrentProjectListFilePath));
			
			System.out.println("CONCURRENT");
			for (Category category : processor.getProjetos().getCategoriesFromProjects(true)) {
				System.out.println(category.getName()+";"+category.getConcurrentTimes());
			}
			
			System.out.println("NON-CONCURRENT");
			for (Category category : processor.getProjetos().getCategoriesFromProjects(false)) {
				System.out.println(category.getName()+";"+category.getNonConcurrentTimes());
			}
			
			
			for (Projeto projeto : processor.getProjetos().getProjetos()) {
				
//				for (Projeto sub : projeto.getSubProjetos()) {
//					for (Versao versao : sub.getVersoes()) {
//						System.out.println(projeto.toString() +" "+ sub.toString() +" "+ versao.getVersaoLog().getName());
//					}
//				}	
				
				fileManager.writeProjectDataIntoCSV(projeto);
				fileManager.writeSubProjectDataIntoCSV(projeto);
							
			}
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
	}
	
	

}
