package br.cin.ufpe.groundhog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class CopyDateProjects {

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
		String rootCompleto = "/home/weslley/mestrado2014/FinalProjects";
		//String listaProjetosSemData = "/home/weslley/ArtigoGroundHog2015/projectsNames2015.txt";

		//Para alguns projetos nao datados
/*		Vector<String> listaProjetosNaoDatados = null;
		try {
			listaProjetosNaoDatados = readProjectsNames();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		File folder = new File(root);
		File folderCompleto = new File(rootCompleto);

		int x = 0;
		File firstLevel[] = folder.listFiles();
		File firstLevelCompleto[] = folderCompleto.listFiles();

		boolean completed = true;

		for (int i = 0; i < firstLevel.length; i++){

			//if(listaProjetosNaoDatados.contains(firstLevel[i].getName())){

				File projetoDatado=null;
				try {
					projetoDatado = findFile(firstLevelCompleto, firstLevel[i].getName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(projetoDatado!=null){

					File secondLevel[] = firstLevel[i].listFiles();
					//System.out.println(firstLevel[i].getName());
					if (secondLevel != null){
						for(int j = 0; j < secondLevel.length && completed; j++){
							//System.out.println(secondLevel[j].getName());
							if (secondLevel[j].getName().compareTo(".DS_Store") != 0){
								File thirdLevel[] = secondLevel[j].listFiles();
								x++;
								for (int k = 0;thirdLevel!=null &&  k < thirdLevel.length && completed; k++){
									if (thirdLevel[k].getName().compareTo(".DS_Store") != 0){
										if (!(thirdLevel[k].getName().startsWith("[") && thirdLevel[k].getName().contains("]"))){

											//File secondLevelCompleto[] = projetoDatado.listFiles();
											File secondLevelCompletoAuxiliar;
											File thirdLevelCompletoAuxiliar;
											try {
												secondLevelCompletoAuxiliar = findFile(projetoDatado.listFiles(), secondLevel[j].getName());
												if(secondLevelCompletoAuxiliar!=null){
													thirdLevelCompletoAuxiliar = findFile(secondLevelCompletoAuxiliar.listFiles(), thirdLevel[k].getName());

													if (thirdLevelCompletoAuxiliar!=null && thirdLevelCompletoAuxiliar.getName().startsWith("[") && thirdLevelCompletoAuxiliar.getName().contains("]")){

														if(thirdLevelCompletoAuxiliar!=null && (thirdLevelCompletoAuxiliar.getName().endsWith(thirdLevel[k].getName()) || (compararProjetoSemExtensao(thirdLevelCompletoAuxiliar.getName(),thirdLevel[k].getName() )))){

															File rename = new File(thirdLevel[k].getParent() + "/" + thirdLevelCompletoAuxiliar.getName());

															thirdLevel[k].renameTo(rename);
															String novoNome = thirdLevel[k].getName();

														}
													}

												}
											}catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
								}
							}//System.out.println("asdasd" + x);
						}
					}
				//}

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
			}else if((nomeProjeto.endsWith(".txt") && file[i].getName().endsWith(nomeProjeto)) || compararProjetoSemExtensao(file[i].getName(),nomeProjeto) ){
				return file[i];
			}
		}
		return null;
	}
	
	public static boolean compararProjetoSemExtensao(String nomeProjeto, String nomeProjetoComparar) throws IOException {
		
		if(nomeProjeto.contains("jplate-foundation-src-0.2")){
			System.out.println("nada");
		}
		
		if(nomeProjeto.contains("tar.gza.txt")){
			nomeProjeto = nomeProjeto.substring(0,nomeProjeto.length()-11);
		}
		if(nomeProjetoComparar.contains("tar.gza.txt")){
			nomeProjetoComparar = nomeProjetoComparar.substring(0,nomeProjetoComparar.length()-11);
		}
		if(nomeProjeto.contains("zipa.txt")){
			nomeProjeto = nomeProjeto.substring(0,nomeProjeto.length()-8);
		}
		if(nomeProjetoComparar.contains("zipa.txt")){
			nomeProjetoComparar = nomeProjetoComparar.substring(0,nomeProjetoComparar.length()-8);
		}
		if(nomeProjeto.contains("[")){
			nomeProjeto = nomeProjeto.substring(12);
		}
		if(nomeProjeto.equals(nomeProjetoComparar)){
			return true;
		}
		else
			return false;
		
		
	}

}
