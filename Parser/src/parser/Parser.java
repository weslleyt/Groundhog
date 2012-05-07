package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Parser implements Runnable {
	public File file;
	public List<File> filesList;

	public Parser(File file) {
		this.file = file;
		filesList = new LinkedList<File>();
	}

	public void run() {
		try {
			recursiveSearch(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CodeAnalyzerController controller = new CodeAnalyzerController();
		controller.invokeProcessor(filesList, file);

		System.out.println(file.getName() + ".txt");
	}

	public void recursiveSearch(File sFile) throws IOException {
		File subFiles[] = sFile.listFiles();

		if (subFiles != null) {
			for (int i = 0; i < subFiles.length; i++) {
				if (subFiles[i].isDirectory()) {
					recursiveSearch(subFiles[i]);
				} else if (subFiles[i].isFile()) {

					if ((subFiles[i].getAbsolutePath().contains("\\docs\\") || subFiles[i]
							.getAbsolutePath().contains("\\metadada\\"))
							|| (subFiles[i].getAbsolutePath()
									.contains("\\__MACOSX\\"))) {
					} else if (subFiles[i].getName().endsWith(".java")) {
						//ATENCAO
						//antes a linha abaixo tava descomentada, eu comentei e o erro sumiu, erro 
						//de dizer o numero errado de blocks sync do cassandra
					//	if (checkforenumvar(subFiles[i]))
							filesList.add(subFiles[i]);
						
					}
				}
			}
		}
	}

	private boolean checkforenumvar(File javaFile) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(javaFile));
		String str;
		while ((str = in.readLine()) != null) {

			if (str.contains(" enum =") || str.contains(" enum=")
					|| str.contains("= enum ") || str.contains(" enum.")
					|| str.contains(" enum)") || str.contains(" enum,")
					|| str.contains(" enum;") || str.contains(".enum.")
					|| str.contains(")enum.") || str.contains(";enum.")
					|| str.contains("(enum.")|| str.contains(".enum ")
					|| str.contains(" enum ")) {
				in.close();
				return false;
			}
		}
		in.close();
		return true;
	}

}
