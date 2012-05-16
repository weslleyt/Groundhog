package Walker;
import java.io.*;

public class Uncompressor extends Thread{
	
	public File file;
	public File destinyFolder;
	public int extension;
	
	public Uncompressor(File file, File destinyFolder, int extension) {
		this.file = file;
		this.destinyFolder = destinyFolder;	
		//extension == 0 -> .zip
		//extension == 1 -> .rar
		//extension == 2 -> .gz
		this.extension = extension;
	}
	
	public void run (){	
		file.canExecute();
		file.canWrite();
		file.canRead();
		if (extension == 0){
			extractZIP(file,destinyFolder);
		}
		
		//descometei para realizar testes projetos rar 2012
		else if (extension == 1){
			try {
				extractRAR(file,destinyFolder);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (extension == 2){
			try {
				new GzUnconpressor(file,destinyFolder);
			} catch (Exception e) {
				System.out.println(file.getName());
			}
		}
		//it does not work yet
		/*else if (extension == 3){
			try {
				extractTGZ(file,destinyFolder);
			} catch (Exception e) {
				System.out.println(file.getName());
			}
		}*/
	}
	
	public void extractZIP(File file, File destinyFolder){
		try{			
			ZipHelper.unzip(file,destinyFolder);
		}catch(IOException e){
			System.out.println(file.getName());
			e.printStackTrace();
			//System.out.println(file.getName()+":IO Exception extracting .zip file.");
		}
		System.out.println(file.getName());
	}
	
	public void extractRAR(File file, File destinyFolder) throws InterruptedException{
		Process p = null;
		
		//Download Unrar (http://www.rarlab.com/rar_add.htm)
		
		if (!destinyFolder.exists()) {
			destinyFolder.mkdirs();
		}
		
		
		long tamanho = file.getUsableSpace();
		
		try{
			//verify UnRar path before running 
	
			p = Runtime.getRuntime().exec("C:/Temp/Epona/UnRAR.exe x " 
					+ "\"" 
					+ file.getPath() 
					+ "\" \""
					+ destinyFolder.getPath()
					+ "\"");
			//p.waitFor();
			
		}catch(Exception e){
			System.out.println(file.getName()+":Error extracting .rar file");
		}
		
		System.out.println(file.getName());
		Thread.currentThread().sleep(20000);
		

		/*long proportion = 54039392256l / 20;
		long waitTime;
		waitTime = tamanho / proportion;

		try {
			Thread.sleep(waitTime * 1000);
		} catch (InterruptedException e) {

		}*/
		
		//System.out.println(waitTime);
		System.out.println(file.getName());
	}
	
	//it does not work yet
	public void extractTGZ(File file, File destinyFolder) throws InterruptedException{
		Process p = null;
			System.out.println("aqui");
		if (!destinyFolder.exists()) {
			destinyFolder.mkdirs();
		}
		
		
		long tamanho = file.getUsableSpace();
		
		try{
			//verify UnRar path before running 
	
			p = Runtime.getRuntime().exec("C:/Program Files (x86)/WinRAR/Rar.exe x " 
					+ "\"" 
					+ file.getPath() 
					+ "\" \""
					+ destinyFolder.getPath()
					+ "\"");
			//p.waitFor();
			
		}catch(Exception e){
			System.out.println(file.getName()+":Error extracting .tgz file");
		}
		
		System.out.println(file.getName());
		Thread.currentThread().sleep(20000);
		
		System.out.println(file.getName());
	}
}