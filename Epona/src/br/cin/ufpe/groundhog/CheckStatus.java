package br.cin.ufpe.groundhog;

import java.io.File;
import java.util.Scanner;

public class CheckStatus {
	
	public static void main (String []args){

		//String root = "C:/testando";
		String root = "C:/Projetos2012/Projetos";
		
		
		File folder = new File(root);
		int okProjects = 0;
		int badProjects = 0;
		int x = 0;
		File firstLevel[] = folder.listFiles();
		
		boolean completed = true;
		
		for (int i = 0; i < firstLevel.length; i++){
			//if (firstLevel)
			File secondLevel[] = firstLevel[i].listFiles();
			
			if (firstLevel[i].listFiles().length == 0){
				System.out.println("aqui: " + firstLevel[i].getName());
				//firstLevel[i].delete();
				
			}
			
			if (secondLevel != null){
				for (int k = 0; secondLevel!=null &&  k < secondLevel.length; k++){
					//System.out.println(secondLevel[k].getName()+i);
					if (secondLevel[k].isDirectory()){
						
						if(secondLevel[k].getName().toUpperCase().contains("LATEST") && secondLevel.length==1)
							System.out.println(firstLevel[i].getName());
					
						if(secondLevel[k].getName().toUpperCase().isEmpty())
							System.out.println("as: " + firstLevel[i].getName());
					
						
						//if(secondLevel[k].getName().toUpperCase().contains("DEVELOPMENT") || secondLevel[k].getName().toUpperCase().contains("LATEST")){ //ADD CANDIDATE tb
						/*if(secondLevel[k].getName().toUpperCase().contains("LATEST")&& secondLevel.length==1){
							System.out.println(secondLevel[k].getName()+i);
							File thirdLevel[] = secondLevel[k].listFiles();
							for (int z = 0; thirdLevel!=null && z<thirdLevel.length; z++)
								thirdLevel[z].delete();

							boolean ster = secondLevel[k].delete();
							System.out.println("Apagou? " + ster );
						}*/
					}

				}
					
			}
			
			/*if (secondLevel != null){
				for(int j = 0; j < secondLevel.length && completed; j++){
					
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
			}*/
			
			
			if (completed){
				okProjects++;
			}else{
				badProjects++;
				completed = true;
				//System.out.println(firstLevel[i].getName());
			}
		}
		
		System.out.println(okProjects + "-----" + badProjects);
	}

}
