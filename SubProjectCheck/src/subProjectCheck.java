import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.Collection;


public class subProjectCheck implements Runnable{
	private File folder;
	private int x=0;
	public subProjectCheck(File folder) {
		this.folder = folder;
	}
	
	public void run(){
		File subpro[] = folder.listFiles();
		if (subpro != null){
			ArrayList<String> subp = new ArrayList<String>();
			
			for (File e:subpro){
				subp.add(verificaNome(e));
			}
			
			Collections.sort(subp);
			
			Project projects[] = new Project[subp.size() +1];
			int i = 0;
			
			for (String e:subp){
				String name;

				if (e.length() > 4){
					name = e.substring(0, 4).toLowerCase();
				}else{
					name = e.toLowerCase();
				}				
				projects[i] = new Project(name.toLowerCase(),findNumber(e),returnNumber(e));
				++i;
			}

			if(HasDifferentNumber(projects))
			{
				int numberOfDifferent = 2;
				boolean found = false;
				for (i = 0; projects[i] != null &&  i < projects.length && !found; i++){
					if (projects[i].hasNumber){
						for (int j = i+1; projects[j] != null && j < i+numberOfDifferent && !found; j++){
							if (projects[j].hasNumber){
								if (projects[i].alias.compareTo(projects[j].alias) == 0){
									if (j == i + numberOfDifferent-1){
										found = true;
									}
								}else{
									break;
								}
							}else{
								break;
							}
						}
					}
				}
				
				if (found){
					System.out.println(folder.getName());
					File organize = new File (folder.getAbsolutePath() + "/" + folder.getName());
					organize.mkdirs();
					
					File firstLevel[] = folder.listFiles();
					for (i = 0; i < firstLevel.length; i++){
						File secondLevel[] = firstLevel[i].listFiles();
						if (secondLevel != null && !firstLevel[i].getName().equals(folder.getName())){
							x++;
							for (int j = 0; j < secondLevel.length; j++){
								secondLevel[j].renameTo(new File (organize.getAbsolutePath() + "/" + secondLevel[j].getName()));
							}
						}
					}
					//System.out.println("subprojetos"+x);
					firstLevel = folder.listFiles();
					for (i = 0; i < firstLevel.length; i++){
						File secondLevel[] = firstLevel[i].listFiles();
						if (secondLevel != null && secondLevel.length == 0 ){
							firstLevel[i].deleteOnExit();
						}
					}
					//System.out.println("subprojetos"+x);
				}
			}
		}
	}
	
	
	private boolean HasDifferentNumber(Project projects[])
	{
		boolean has = false;
		
		for (int i = 0; projects[i] != null &&  i < projects.length; i++) {
			if(projects[i].number != null)
			{
				for (int j = i+1; projects[j] != null && j < projects.length; j++) {
					if(projects[j].number != null  && 
							!projects[i].number.equals(projects[j].number))
					{
						has = true;
					}
				}
			}
		}
		
		return has; 
	}
	
	public String verificaNome(File e){
		String nome = e.getName();
		
		
		
		nome  = nome.replaceAll("%20", " ");
		nome  = nome.replaceAll("%5B", "[");
		nome  = nome.replaceAll("%5D", "]");
		nome  = nome.replaceAll("%28", "(");
		nome  = nome.replace("%29", ")");
		

		File novo = new File(e.getParentFile().getAbsolutePath() + "/" + nome + "/");
		e.renameTo(novo);		
		return novo.getName();		
	}
		
	private boolean findNumber(String str){
		int beginIndex = 0, endIndex = 0;
		boolean found = false;
		for (int i = 0; i < str.length(); i++){
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				beginIndex = i;
				found = true;
				break;
			}
		}
		
		for (int i = str.length() - 1; i >= 0 ; i--){
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				endIndex = i;
				break;
			}
		}
		
		if (found){
			return true;
		}else{
			return false;
		}
	
	}
	
	private String returnNumber(String str){
		int beginIndex = 0, endIndex = 0;
		boolean found = false;
		
		for (int i = 0; i < str.length(); i++){
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				beginIndex = i;
				found = true;
				break;
			}
		}
		
		for (int i = str.length() - 1; i >= 0 ; i--){
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				endIndex = i;
				break;
			}
		}
		
		if (found){
			return str.substring(beginIndex, endIndex+1);
		}else{
			return null;
		}
	}
	
	class Project{
		String alias;
		boolean hasNumber;
		String number;
		public Project(String name,boolean h,String number) {
			this.alias = name;
			this.hasNumber = h;
			this.number = number;			
		}
	}
}

