package br.cin.ufpe.groundhog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class CheckDates {

	public static void main (String []args){
		//descomentar abaixo para voltar ao normal
		//String root = "C:/Epona/logsGPCE/logs";

		//String root = "C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhada";

		//String root = "C:/Epona/ArtigoOUTUBRO/Log";
		//		String root = "C:/Epona/ArtigoJANEIRO/LogBaixandoDatas";
		//String root = "C:/Epona/Projetos2012/log";
		//String root = "C:/Epona/ICSM/log";
		//String root = "/home/wst/groundhog/LogProjetos2015";
		//String root = "/home/weslley/mestrado/FinalProjects";
		//String root = "/home/weslleytorres/ArtigoGroundHog2015/LogProjetos2015";
		String root = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/LogProjetos2015";
		
		//String root = "/home/weslley/mestrado2014/FinalProjects";

//		Vector<String> listaProjetosNaoDatados = null;
//		try {
//			listaProjetosNaoDatados = readProjectsNames();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

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
				System.out.println(firstLevel[i].getName());
			}else{
				badProjects++;
				completed = true;
				//if(!listaProjetosNaoDatados.contains(firstLevel[i].getName())){
					System.out.println(firstLevel[i].getName());
				//}
			}
		}

		System.out.println(okProjects + "-----" + badProjects);
	}

	public static Vector<String> readProjectsNames() throws IOException {
		Vector<String> retorno = new Vector<String>();

		File sFile = new File(
				Configuration.getStringProperty("epona.projectsnames_path"));
		BufferedReader in = new BufferedReader(new FileReader(sFile));

		String str;

		while ((str = in.readLine()) != null) {
			retorno.add(str);
		}
		return retorno;
	}

}
