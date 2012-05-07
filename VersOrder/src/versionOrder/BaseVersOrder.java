package versionOrder;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BaseVersOrder {
	public File file;
	public String destFolder;
	
	public BaseVersOrder(String basePath, String destFolder){
		file = new File (basePath);
		this.destFolder = destFolder;
	}
	
	public void folderSearch(){
		File sub2[] = null;
		File subFiles[] = file.listFiles();
	
		ExecutorService b = Executors.newFixedThreadPool(3);
		
		if (subFiles != null){
			for (int i = 0; i < subFiles.length; i++){
				sub2 = subFiles[i].listFiles();
				if (sub2 != null){
					for (int j = 0; j < sub2.length; j++){
						if (sub2[j].isDirectory()){
							VersOrder a = new VersOrder(sub2[j], destFolder);
							b.execute(a);
						}
					}
				}
			}
		}
		b.shutdown();
	}
	
	public static void main(String args[]){
		//String sourceFolder = "c:/logsOficial/logs";
		//String sourceFolder = "c:/novosLogsCorrigidos";
		String sourceFolder = "c:/Epona/ArtigoJANEIRO/Log";
		
		String destinyFolder = sourceFolder;
		BaseVersOrder parser = new BaseVersOrder(sourceFolder,destinyFolder);
		parser.folderSearch();	
	}
}

