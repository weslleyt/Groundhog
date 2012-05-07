package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class parserChecker {

	static File fileSourceFolder;
	static int count= 0;

	public static void main(String[] args) throws IOException {
//		String rootSourceFolder = "C:/logslinux";
//		fileSourceFolder = new File(rootSourceFolder);
		//recursiveSearchLineCodeZero(fileSourceFolder);
		
		//String rootSourceFolder = "C:/Epona/logsGPCE/logs";
		String rootSourceFolder = "C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhada";
		
		
		fileSourceFolder = new File(rootSourceFolder);
		recursiveSearchEmptyFolder(fileSourceFolder);
		recursiveSearchLineCodeZero(fileSourceFolder);
		System.out.println(count);
	}

	public static void recursiveSearchLineCodeZero(File file) throws IOException {
		File subFiles[] = file.listFiles();

		if (subFiles != null) {
			for (int i = 0; i < subFiles.length; i++) {
				if (subFiles[i].isDirectory()) {
					recursiveSearchLineCodeZero(subFiles[i]);
				} else if (subFiles[i].isFile()
						&& !subFiles[i].getName().contains("lv-.txt")) {

					BufferedReader in = new BufferedReader(new FileReader(subFiles[i]));
					String str;
					while ((str = in.readLine()) != null) {

						if (str.contains("Lines of Code: 0")) {
							in.close();
							String path = subFiles[i].getAbsolutePath();
							//path = path.replace("C:", "C:\\removedLogsLinux");
							path = path.replace("C:", "C:\\removidosVersaoDetalhada");
							File newFile = new File(path);
							if(!newFile.getParentFile().exists())
							{
								newFile.getParentFile().mkdirs();
							}
							subFiles[i].renameTo(newFile);
							break;
						}
					}
					
				}
			}
		}
	}
	
	public static void recursiveSearchEmptyFolder(File file) throws IOException {
		File subFiles[] = file.listFiles();

		if (subFiles != null) {
			for (int i = 0; i < subFiles.length; i++) {
				if (subFiles[i].isDirectory()) {
					if(subFiles[i].listFiles().length == 0)
					{
						System.out.println(subFiles[i].getAbsolutePath());
						subFiles[i].delete();
					}
					recursiveSearchEmptyFolder(subFiles[i]);
				}
			}
		}
	}
	
	public static void recursiveSearchLineCodeCount(File file) throws IOException {
		File subFiles[] = file.listFiles();

		if (subFiles != null) {
			for (int i = 0; i < subFiles.length; i++) {
				if (subFiles[i].isDirectory()) {
					recursiveSearchLineCodeCount(subFiles[i]);
				} else if (subFiles[i].isFile()
						&& !subFiles[i].getName().contains("lv-.txt")) {

					BufferedReader in = new BufferedReader(new FileReader(subFiles[i]));
					String str;
					while ((str = in.readLine()) != null) {

						if (str.contains("Lines of Code")) {
							String[] metric= str.split(":");
							count += Integer.parseInt(metric[1].trim());
						}
					}
					
					in.close();
					
				}
			}
		}
	}

}

