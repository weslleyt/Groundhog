import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;




public class RandomProjects {

	/**
	 * @param args
	 */
	
	public static ArrayList<String> readProjectsNames(String url){
		ArrayList<String> retorno = new ArrayList<String>();

		File sFile = new File(url);
		BufferedReader in;
		
		try {
			in = new BufferedReader(new FileReader(sFile));
		
			String str;

			while ((str = in.readLine()) != null) {
		
				retorno.add(str);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	
	public static void main(String[] args) 
	{
		//String sourceFolder = "C:/Epona/logsGPCE/logs";
		//String sourceFolder = "C:/novosLogsCorrigidos";
				
		String sourceFile = "C:/Epona/ArtigoJANEIRO/projetosEscolhidosAleatoriamente/ProjetosAleatorios.txt";
		
		
		//File file = new File (sourceFolder);
		//File subFiles[] = file.listFiles();
		
		Random rand = new Random();
		ArrayList<String> ChosenProjects = new ArrayList<String>();
		
		ArrayList<String> names;
		
		names = readProjectsNames(sourceFile);
		
		String auxiliar;
		for (int i = 0; i < 100; i++) {
			//String name = subFiles[rand.nextInt(subFiles.length-1)].getName();
			//String name = subFiles[i].getName();
			
			auxiliar = names.get(rand.nextInt(names.size()-1));
			if(!ChosenProjects.contains(auxiliar)){
				
				ChosenProjects.add(auxiliar);
				System.out.println(auxiliar);				
			}else{
				i--;
			}
		}
		
		Random valorAleatorio = new Random();		
		System.out.println("\nProjetos escolhidos:");
		for (int i = 0; i<3;i++){
			System.out.println(ChosenProjects.get(valorAleatorio.nextInt(99)));
		}
		
		
	}
}
