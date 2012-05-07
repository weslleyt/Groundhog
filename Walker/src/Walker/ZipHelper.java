package Walker;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipHelper {
	
	//This part is taken from:
	//http://www.webartigos.com/articles/28886/1/ZipUnzip-em-Java/pagina1.html
	public static void unzip(File zipFile, File dir) throws IOException {
		ZipFile zip = null;
		File arquivo = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = new byte[1024];

		try {
			// cria diret�rio informado, caso n�o exista
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!dir.exists() || !dir.isDirectory()) {
				throw new IOException("O diret�rio " + dir.getName() + " n�o � um diret�rio v�lido");
			}

			
			zip = new ZipFile(zipFile);
			Enumeration e = zip.entries();
			while (e.hasMoreElements()) {
				ZipEntry entrada = (ZipEntry) e.nextElement();
				arquivo = new File(dir, entrada.getName());

				// se for diret�rio inexistente, cria a estrutura e pula pra pr�xima entrada
				if (entrada.isDirectory() && !arquivo.exists()) {
					arquivo.mkdirs();
					continue;
				}

				// se a estrutura de diret�rios n�o existe, cria
				if (!arquivo.getParentFile().exists()) {
					arquivo.getParentFile().mkdirs();
				}
				try {
					// l� o arquivo do zip e grava em disco
					is = zip.getInputStream(entrada);
					os = new FileOutputStream(arquivo);
					int bytesLidos = 0;
					if (is == null) {
						throw new ZipException("Erro ao ler a entrada do zip: " + entrada.getName());
					}
					while ((bytesLidos = is.read(buffer)) > 0) {
						os.write(buffer, 0, bytesLidos);
					}
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (Exception ex) {
						}
					}
					if (os != null) {
						try {
							os.close();
						} catch (Exception ex) {
						}
					}
				}
			}
		}
		
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		
		finally {
			if (zip != null) {
				try {
					zip.close();
				} catch (Exception e) {
				}
			}
		}
	}
}