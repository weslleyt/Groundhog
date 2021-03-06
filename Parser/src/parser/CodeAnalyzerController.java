package parser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class CodeAnalyzerController {

	public void invokeProcessor(List<File> files, File file) {

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);
		
		if (files.size() > 0) {
			Iterable<? extends JavaFileObject> compilationUnits1 = fileManager
					.getJavaFileObjectsFromFiles(files);
			
			final Collection<String> options = new ArrayList<String>();
			options.add("-g:none");
			options.add("-nowarn");
			options.add("-implicit:none");
			
			Iterable<String> opt = new Iterable<String>() {
				@Override
				public Iterator<String> iterator(){
					return options.iterator();
				}
			};
			
			CompilationTask task = compiler.getTask(null, fileManager, null,
					opt, null, compilationUnits1);
			
			LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();
			
			CollectedData data = new CollectedData();
			
			CodeAnalyzerProcessor processor = new CodeAnalyzerProcessor(data);
			
			processors.add(processor);

			task.setProcessors(processors);
			
			task.call();
			
			//DestinyFolder for log files
			//data.saveLog("C:/logsBenitoWindows/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//data.saveLog("C:/Epona/AnaliseDetalhada/LogsAnaliseDetalhada/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//data.saveLog("C:/testes2/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			
			
			//data.saveLog("C:/novoTESTEresultado/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//data.saveLog("C:/Epona/ArtigoOUTUBRO/Log/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//data.saveLog("C:/Epona/ArtigoNOVEMBRO/Log/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);

			//data.saveLog("C:/Epona/ArtigoJANEIRO/Log/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//data.saveLog("/home/weslley/descompactar/n/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			
			//teste 2015
			//data.saveLog("/home/weslley/logProjeto2015/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//caminho no Tilapia
			//data.saveLog("/home/wst/groundhog/LogProjetos2012/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//caminho no Tilapia 2015
			//data.saveLog("/home/wst/groundhog/LogProjetos2015GrupoNovo/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			data.saveLog("/home/weslley/TesteWalaFev/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			

			//data.saveLog("C:/Epona/ArtigoJANEIRO/Log/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//data.saveLog("C:/Epona/ArtigoFevereiro/logProjetosSelecionadosApache/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			
			//data.saveLog("C:/Epona/Benito/log/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			
			//data.saveLog("C:/Epona/Projetos2012/log/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			
			
			//data.saveLog("C:/Epona/ICSM/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			//data.saveLog("C:/TESTEnovo/log/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			
			

			//data.saveLog("C:/Users/jbfan/Desktop/Dezembro/log/" + file.getParentFile().getParentFile().getName()+"/"+ file.getParentFile().getName(), file);
			
			try {
				fileManager.close();
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}else {
			System.out.println("No valid source files to process. "
					+ "Extiting from the program" + file.getParentFile().getName());
			//System.exit(0);
		}
	}
}
