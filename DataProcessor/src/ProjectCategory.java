import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box.Filler;

public class ProjectCategory {

	public static ArrayList<ArrayList<String>> projectsByCategory = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> categoriesName = new ArrayList<String>();
	static File fileSourceFolder;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		String projectsNameSourceFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/projectsNames.txt";
		String projectsByCategoriesFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/projetcsByCategory";
		

		File projectsNamesFile = new File(projectsNameSourceFolder);
		fillProjectsCategory(projectsByCategoriesFolder);

		
		 ArrayList<String> projectsNames = getProjectcNames(projectsNamesFile);
		 
		 listarProjetosCategoria(projectsNames);
		

	}

	
	public static void listarProjetosCategoria(ArrayList<String> projectsNames){
		
		for(int i = 1; i < categoriesName.size() ; i++){
			System.out.println("\n CATEGORIA " + categoriesName.get(i)+ ":");
			for (String nomeProjeto : projectsNames) {
				if(projectsByCategory.get(i).contains(nomeProjeto)){
					System.out.println(nomeProjeto);
				}
			}
		}
		
	}

	private static void fillProjectsCategory(String sourceFolder)
			throws IOException {
		File fileFolder = new File(sourceFolder);
		File[] listFiles = fileFolder.listFiles();
		String extention = ".txt";

		for (int i = 0; i < listFiles.length; i++) {
			ArrayList<String> projects = new ArrayList<String>();
			categoriesName.add(listFiles[i].getName().substring(0,
					listFiles[i].getName().length() - extention.length()));
			BufferedReader in = new BufferedReader(new FileReader(listFiles[i]));
			String str;
			while ((str = in.readLine()) != null) {
				projects.add(str);
			}
			projectsByCategory.add(projects);
		}
	}

	private static ArrayList<String> getProjectcNames(File file)
			throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));
		String str;
		ArrayList<String> names = new ArrayList<String>();
		while ((str = in.readLine()) != null) {
			String[] nameS = str.split(":");
			names.add(nameS[0].trim());
		}
		return names;
	}

}
