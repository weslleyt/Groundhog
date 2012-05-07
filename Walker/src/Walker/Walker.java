package Walker;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Walker{
	public String rootFolder;
	public ExecutorService b;
	
	public Walker (String root){
		rootFolder = root;
	}
	
	public void extractFiles(File file){
		
		if (file.getName().lastIndexOf('.') > 0){
			File destinyFolder = new File (rootFolder
					+ "/" + file.getParentFile().getParentFile().getName() 
					+ "/" + file.getParentFile().getName()
					+ "/" + file.getName());
			
			//System.out.println(destinyFolder);
			
			if (file.getName().endsWith(".zip")){
				Uncompressor a = new Uncompressor (file, destinyFolder,0);
				b.execute(a);
			}else if (file.getName().endsWith(".rar")){
				Uncompressor a = new Uncompressor (file, destinyFolder,1);
				b.execute(a);
			}else if (file.getName().endsWith(".gz")){
				Uncompressor a = new Uncompressor (file, destinyFolder,2);
				b.execute(a);
			}
		}
		
	}

	public void recursiveSearch(File file){
		b = Executors.newFixedThreadPool(4);
		File subFiles[] = file.listFiles();
		
		if (subFiles != null){
			for (int i = 0; i < subFiles.length; i++){
				//System.out.println(subFiles[i].getName());
				if (subFiles[i].isDirectory()){
					recursiveSearch(subFiles[i]);
				}else if (subFiles[i].isFile()){					
					extractFiles(subFiles[i]);
				}
			}
		}
		
		b.shutdown();
	}
	
	
	public void extractFile(File file)
	{
//		b = Executors.newSingleThreadExecutor();	
//		extractFiles(fileSource);	
//		b.shutdown();
		
		if (file.getName().lastIndexOf('.') > 0){
			File destinyFolder = new File (rootFolder
					+ "/" + file.getParent().substring(file.getParent().lastIndexOf('\\')+1) 
					+ "/" + file.getName());
			
			//System.out.println(file.getName().substring(0,file.getName().lastIndexOf('.')));
			//System.out.println(rootFolder
			//		+ "/" + file.getParent().substring(file.getParent().lastIndexOf('\\')+1));
			
			if (file.getName().endsWith(".zip")){
				Uncompressor a = new Uncompressor (file, destinyFolder,0);
				a.run();				
			}else if (file.getName().endsWith(".rar")){
				Uncompressor a = new Uncompressor (file, destinyFolder,1);
				a.run();
			}else if (file.getName().endsWith(".gz")){
				Uncompressor a = new Uncompressor (file, destinyFolder,2);
				a.run();
			}
			
		}
		
	}
	
	public void extractFile(File fileSource, String destinyFolder)
	{
		rootFolder = destinyFolder;		
		extractFiles(fileSource);		
	}
	
	public static void main(String args[]){
		//Here goes the path to destiny folder
		Walker walker = new Walker ("C:\\Projetos2012\\EponaUncompressedProjects");
		//Here goes your source folder
		
		File file = new File ("C:\\Projetos2012\\Projetos");
		walker.recursiveSearch(file);
	}
}
