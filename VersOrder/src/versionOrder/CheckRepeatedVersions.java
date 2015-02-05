package versionOrder;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CheckRepeatedVersions {

	public File file;
	public String destFolder;

	public CheckRepeatedVersions(String basePath, String destFolder){
		file = new File (basePath);
		this.destFolder = destFolder;
	}

	public void folderSearch(){
		File sub2[] = null;
		File subFiles[] = file.listFiles();

		if (subFiles != null){
			for (int i = 0; i < subFiles.length; i++){
				sub2 = subFiles[i].listFiles();
				if (sub2 != null){
					for (int j = 0; j < sub2.length; j++){
						if (sub2[j].isDirectory()){
							checkRepeatedVersion(sub2[j]);							
						}
					}
				}
			}
		}

	}	

	private void checkRepeatedVersion(File file2) {
		File[] listFiles = file2.listFiles();
		for (int i = 0; i < listFiles.length-1; i++) {
			String fileName = listFiles[i].getName();
			if(!fileName.startsWith("lv-.txt")){
				String[] split = fileName.split("\\.");
				String extention =  split[split.length-2];

				String onlyName;
				if(extention.startsWith("gz")){
					onlyName = arrayToString(split,".",split.length-3);				
					compare(listFiles, i, split,2,onlyName);
				} else
				{
					onlyName = arrayToString(split,".",split.length-2);
					compare(listFiles, i, split,1,onlyName);
				}
			}
		}		
	}

	public boolean isNomeIgualSemDatas(String nome, String outroNome){

		if(nome.length()>12 && outroNome.length()>12){

			nome = nome.substring(12);
			outroNome = outroNome.substring(12);
			//[2009-11-02]ugl-mac-src-3.0.zipa
			if (nome.equals(outroNome)){
				return true;
			}
			else return false;
		}
		return false;
	}

	private void compare(File[] listFiles, int i, String[] split,int extensionIndexStart,String name) {
		String fileName;
		String extention;
		//Compare
		for (int j = i+1; j < listFiles.length; j++) {
			fileName = listFiles[j].getName();
			if(!fileName.startsWith("lv-.txt")){
				split = fileName.split("\\.");
				extention =  split[split.length - 2];
				String onlyNameNext;
				if(extention.startsWith("gz")){
					onlyNameNext = arrayToString(split,".",split.length-3);
				}else
				{
					onlyNameNext = arrayToString(split,".",split.length-2);
				}


				
				if(onlyNameNext.equals(name)){//|| isNomeIgualSemDatas(onlyNameNext, name)){
					System.out.println(listFiles[j].getAbsolutePath());
					//File newFile = new File(listFiles[j].getAbsolutePath().replace("C:", "C:/RepeatedVersions"));
					//File newFile = new File(listFiles[j].getAbsolutePath().replace("C:", "C:/RepeatedVersionsVersaoDetalhada"));
					//File newFile = new File(listFiles[j].getAbsolutePath().replace("C:", "C:/RepeatedVersionsVersaoOUTUBRO"));
					File newFile = new File(listFiles[j].getAbsolutePath().replace("/home/weslley/ArtigoGroundHog2015", "/home/weslley/ArtigoGroundHog2015/RepeatedVersions2015Do0"));

					if(!newFile.getParentFile().exists())
					{
						newFile.getParentFile().mkdirs();
					}
					listFiles[j].renameTo(newFile);
				}
			}
		}
	}

	private static String arrayToString(String[] a, String separator,int lastIndex) {
		StringBuffer result = new StringBuffer();
		if (a.length > 0) {
			result.append(a[0]);
			for (int i=1; i< lastIndex; i++) {
				result.append(separator);
				result.append(a[i]);
			}
		}
		return result.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("foi");
		//String sourceFolder = "C:/logsOficial/E_logs_LAST_VERSIONED/logs";
		//String sourceFolder = "C:/Epona/logsGPCE/logs";
		//String sourceFolder = "C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhada";
		String sourceFolder = "/home/weslley/ArtigoGroundHog2015/LogProjetos2015";
		String destinyFolder = sourceFolder;
		CheckRepeatedVersions parser = new CheckRepeatedVersions(sourceFolder,destinyFolder);
		parser.folderSearch();
	}
}
