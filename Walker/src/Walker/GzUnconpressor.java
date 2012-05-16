package Walker;
import java.io.*;

import com.ice.tar.*;
import java.util.zip.GZIPInputStream;

public class GzUnconpressor {

	public static InputStream getInputStream(String tarFileName) throws Exception{

		if(tarFileName.substring(tarFileName.lastIndexOf(".") + 1, tarFileName.lastIndexOf(".") + 3).equalsIgnoreCase("gz")){
			//System.out.println("Creating an GZIPInputStream for the file");
			return new GZIPInputStream(new FileInputStream(new File(tarFileName)));

		}else{
			//System.out.println("Creating an InputStream for the file");
			return new FileInputStream(new File(tarFileName));
		}
	}

	private static void untar(InputStream in, String untarDir){

		//System.out.println("Reading TarInputStream... (using classes from http://www.trustice.com/java/tar/)");
		TarInputStream tin = new TarInputStream(in);
		TarEntry tarEntry;
		try {
			tarEntry = tin.getNextEntry();
		
			if(new File(untarDir).exists()){
				while (tarEntry != null){
					File destPath = new File(untarDir + File.separatorChar + tarEntry.getName());
					//System.out.println("Processing " + destPath.getAbsoluteFile());
					if(!tarEntry.isDirectory()){
						FileOutputStream fout = new FileOutputStream(destPath);
						tin.copyEntryContents(fout);
						
						
						fout.close();
					}else{
						destPath.mkdir();
					}
					tarEntry = tin.getNextEntry();
				}
				tin.close();
			}else{
				//System.out.println("That destination directory doesn't exist! " + untarDir);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public GzUnconpressor (File sourceFile, File dest) throws Exception{

	
			InputStream in = getInputStream(sourceFile.getAbsolutePath());
			
			if (!dest.exists()) {
				dest.mkdirs();
			}

			untar(in, dest.getAbsolutePath());
			System.out.println(sourceFile.getName());	

	}
}
