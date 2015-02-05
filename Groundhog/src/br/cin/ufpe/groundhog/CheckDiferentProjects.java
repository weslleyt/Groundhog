package br.cin.ufpe.groundhog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class CheckDiferentProjects {

	public static void main (String []args){
		//descomentar abaixo para voltar ao normal
		//String root = "C:/Epona/logsGPCE/logs";

		//String root = "C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhada";

		//String root = "C:/Epona/ArtigoOUTUBRO/Log";
		//String root = "C:/Epona/ArtigoJANEIRO/LogBaixandoDatas";
		//String root = "C:/Epona/Projetos2012/log";
		//String root = "C:/Epona/ICSM/log";
		//String root = "/home/wst/groundhog/LogProjetos2015";
		String root = "/home/weslley/ArtigoGroundHog2015/LogProjetos2015";
		
		//String listaProjetosSemData = "/home/weslley/ArtigoGroundHog2015/projectsNames2015.txt";

		Vector<String> listaProjetosNaoDatados = null;
		try {
			listaProjetosNaoDatados = readProjectsNames();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File folder = new File(root);
		

		File firstLevel[] = folder.listFiles();

		for (int i = 0; i < firstLevel.length; i++){

			if(!listaProjetosNaoDatados.contains(firstLevel[i].getName())){
				System.out.println(firstLevel[i].getName());
			}
		}
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

	public static File findFile(File[] file, String nomeProjeto) throws IOException {
		for (int i = 0; i < file.length; i++){
			if(file[i].getName().equals(nomeProjeto)){
				return file[i];
			}else if(nomeProjeto.endsWith(".txt") && file[i].getName().endsWith(nomeProjeto)){
				return file[i];
			}
		}
		return null;
	}

}
