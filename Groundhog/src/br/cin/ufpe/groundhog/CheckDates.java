package br.cin.ufpe.groundhog;

import java.io.File;
import java.util.Scanner;

public class CheckDates {
	
	public static void main (String []args){
		//descomentar abaixo para voltar ao normal
		//String root = "C:/Epona/logsGPCE/logs";
		
		//String root = "C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhada";
		
		//String root = "C:/Epona/ArtigoOUTUBRO/Log";
//		String root = "C:/Epona/ArtigoJANEIRO/LogBaixandoDatas";
		//String root = "C:/Epona/Projetos2012/log";
		//String root = "C:/Epona/ICSM/log";
		String root = "/home/wst/groundhog/LogProjetos2012";
		

		
		File folder = new File(root);
		int okProjects = 0;
		int badProjects = 0;
		int x = 0;
		File firstLevel[] = folder.listFiles();
		
		boolean completed = true;
		
		for (int i = 0; i < firstLevel.length; i++){
			
			File secondLevel[] = firstLevel[i].listFiles();
			//System.out.println(firstLevel[i].getName());
			if (secondLevel != null){
				for(int j = 0; j < secondLevel.length && completed; j++){
					//System.out.println(secondLevel[j].getName());
					if (secondLevel[j].getName().compareTo(".DS_Store") != 0){
						File tirdLevel[] = secondLevel[j].listFiles();
						x++;
						for (int k = 0;tirdLevel!=null &&  k < tirdLevel.length && completed; k++){
							if (tirdLevel[k].getName().compareTo(".DS_Store") != 0){
								if (!(tirdLevel[k].getName().startsWith("[") && tirdLevel[k].getName().contains("]"))){
									completed = false;
								}
							}
						}
					}
				}
			}//System.out.println("asdasd" + x);
			if (completed){
				okProjects++;
			//	System.out.println("COMPLETED: "+firstLevel[i].getName());
			}else{
				badProjects++;
				completed = true;
				System.out.println(firstLevel[i].getName());
			}
		}
		
		System.out.println(okProjects + "-----" + badProjects);
	}

}
