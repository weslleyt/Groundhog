import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataProcessorUnique {

	public static ArrayList<ArrayList<String>> projectsByCategory = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> categoriesName = new ArrayList<String>();
	static File fileSourceFolder;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// String rootSourceFolder = "/home/weslley/mestrado2014/FinalProjects/";
		String rootSourceFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/LogProjetos2015/";

		// String rootSourceFolder = "/home/weslley/mestrado/FinalProjects/";

		// String rootSourceFolder = "/home/wst/groundhog/FinalProjects/";

		String rootDestinyFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/ResultadoMetricas/Geral";

		// String rootDestinyFolder = "/home/weslley/mestrado2014/Resultados";

		// String rootDestinyFolder = "C:/Epona/TMCcorrecao/AnaliseIndividual/Backport";

		// String projectsByCategoriesFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/projetcsByCategory";

		String metricsNameSourceFolder = "/Users/weslleytorres/Google Drive/doutorado/ArtigoGroundHogJSS/Dados/metricsNames.txt";

		fileSourceFolder = new File(rootSourceFolder);
		File metricsNameFile = new File(metricsNameSourceFolder);
		// fillProjectsCategory(projectsByCategoriesFolder);

		MetricProcessFileManager.destinyFolder = rootDestinyFolder;

		// verifica qual categoria faz parte
		// compareProjectsCategory(projectsByCategoriesFolder, rootSourceFolder);

		// JUCContructsMetricProcessor juc = new JUCContructsMetricProcessor(
		// "j.u.c", true, true,true);
		// juc.process(fileSourceFolder);

		ArrayList<String> metricsNames = getMetricNames(metricsNameFile);

		// LOC Por ano escolhido
		// LoCMetricProcessor loc = new LoCMetricProcessor(true, "2005");
		// loc.process(fileSourceFolder);

		// Pegar primeira Versão que utiliza JUC - sem import
		// getProjectsFirstVersionMetric("j.u.c.WITHOUT.imports", true, true, "2012", true);

		// Pegar projetos lançados em Tal ano e a ultima versao e analizada
		// generateSingleMetrics(metricsNames, true, true, true, "2005", true);
		// generateSingleMetrics(metricsNames, false, true, true, "2005", true);

		// Pegar a evolucao2
		// generateSingleMetricsEvolution(metricsNames, true,true,true,true, "2012",true);
		// generateSingleMetricsEvolution(metricsNames, false,true,true, true, "2012", true);

		// para coletar dados de 1 só projeto
		// generateSingleMetrics(metricsNames, true,true,true,"2005",false, true);
		// generateSingleMetrics(metricsNames, false,true,true,"2005",false, true);

		// ArrayList<String> teste = new ArrayList<String>();
		// teste.add("Lines of Code");
		// generateSingleMetrics(teste, false,true,true, false);

		// DateDoubleMetricProcessor dmdmp233 = new DateDoubleMetricProcessor("extends Thread", "Lines of Code",false, true);
		// dmdmp233.process(fileSourceFolder);

		// Para gerar as metricas gerais
		generateSingleMetrics(metricsNames, false, true, true, true);
		generateSingleMetrics(metricsNames, true, true, true, true);

		// soma LOC normal
		// LoCMetricProcessor loc = new LoCMetricProcessor(true);
		// loc.process(fileSourceFolder);
		// LoCMetricProcessor loc2 = new LoCMetricProcessor(false);
		// loc2.process(fileSourceFolder);

		// para gerar dados utilizados nos gráficos de comparar old e new

		// DateDoubleMetricProcessor dmdmp633 = new DateDoubleMetricProcessor("ReentrantLock", "Lines of Code",false, true);
		// dmdmp633.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp6133 = new DateDoubleMetricProcessor("sync blocks", "Lines of Code",false, true);
		// dmdmp6133.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp6233 = new DateDoubleMetricProcessor("ReentrantReadWriteLock", "Lines of Code",false, true);
		// dmdmp6233.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp636 = new DateDoubleMetricProcessor("sync methods", "Lines of Code",false, true);
		// dmdmp636.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp635 = new DateDoubleMetricProcessor("volatile", "Lines of Code",false, true);
		// dmdmp635.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp33 = new DateDoubleMetricProcessor("implements Runnable", "Lines of Code",false, true);
		// dmdmp33.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp133 = new DateDoubleMetricProcessor("extends Runnable", "Lines of Code",false, true);
		// dmdmp133.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp233 = new DateDoubleMetricProcessor("extends Thread", "Lines of Code",false, true);
		// dmdmp233.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp36 = new DateDoubleMetricProcessor("HashMap", "Lines of Code",false, true);
		// dmdmp36.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp35 = new DateDoubleMetricProcessor("Hashtable", "Lines of Code",false, true);
		// dmdmp35.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp137 = new DateDoubleMetricProcessor("executorService", "Lines of Code",false, true);
		// dmdmp137.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp37 = new DateDoubleMetricProcessor("ConcurrentHashMap", "Lines of Code",false, true);
		// dmdmp37.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp40 = new DateDoubleMetricProcessor("linkedBlockingQueue", "Lines of Code",false, true);
		// dmdmp40.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp41 = new DateDoubleMetricProcessor("priorityBlockingQueue", "Lines of Code",false, true);
		// dmdmp41.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp42 = new DateDoubleMetricProcessor("synchronousQueue", "Lines of Code",false, true);
		// dmdmp42.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp43 = new DateDoubleMetricProcessor("CountDownLatch", "Lines of Code",false, true);
		// dmdmp43.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp432 = new DateDoubleMetricProcessor("CyclicBarrier", "Lines of Code",false, true);
		// dmdmp432.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp44 = new DateDoubleMetricProcessor("executors", "Lines of Code",false, true);
		// dmdmp44.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp45 = new DateDoubleMetricProcessor("CyclicBarrier", "Lines of Code",false, true);
		// dmdmp45.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp55 = new DateDoubleMetricProcessor("wait()", "Lines of Code",false, true);
		// dmdmp55.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp56 = new DateDoubleMetricProcessor("notify()", "Lines of Code",false, true);
		// dmdmp56.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp57 = new DateDoubleMetricProcessor("notifyAll()", "Lines of Code",false, true);
		// dmdmp57.process(fileSourceFolder);
		//

		// para gerar dados utilizados nos gráficos de comparar versão mais antiga com a mais nova (Pedido Fernando email
		/*
		 * Calcular correlações entre primeira e última versões. Ao invés de dividir nos dois grupos baseados em sequências de anos (grupos
		 * Old e Recent), gostaria de saber se temos correlações interessantes quando, para um projeto com duas versões, calculamos os
		 * deltas levando em conta as versòes mais recentes e antigas. A vantagem de fazer esse cálculo é que temos um grupo muito maior de
		 * projetos para avaliar e a partir do qual podemos calcular correlações. A desvantagem é que perdemos a perspectiva temporal.
		 * Weslley: você faz isso?
		 */
		/*
		 * DateDoubleMetricProcessor dmdmp224 = new DateDoubleMetricProcessor("volatile", "sync blocks",false, true, false);
		 * dmdmp224.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp324 = new DateDoubleMetricProcessor("volatile",
		 * "j.u.c.WITHOUT.imports",false, true, false); dmdmp324.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp424 = new
		 * DateDoubleMetricProcessor("volatile", "Atomic variables",false, true, false); dmdmp424.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp3 = new DateDoubleMetricProcessor("notifyAll()","CountDownLatch",false, true, false);
		 * dmdmp3.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp4 = new DateDoubleMetricProcessor(
		 * "wait()","CountDownLatch",false, true, false); dmdmp4.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp26 = new
		 * DateDoubleMetricProcessor( "notify()", "CountDownLatch",false, true, false); dmdmp26.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp726 = new DateDoubleMetricProcessor( "notify()", "notifyAll()",false, true, false);
		 * dmdmp726.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp643 = new DateDoubleMetricProcessor("Callable",
		 * "Executors contructs",false, true, false); dmdmp643.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp33 = new
		 * DateDoubleMetricProcessor("CountDownLatch", "CyclicBarrier",false, true, false); dmdmp33.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp43 = new DateDoubleMetricProcessor("CountDownLatch", "Executors contructs",false, true, false);
		 * dmdmp43.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp263 = new DateDoubleMetricProcessor("CyclicBarrier",
		 * "Executors contructs",false, true, false); dmdmp263.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp35 = new
		 * DateDoubleMetricProcessor("sync methods", "Executors contructs",false, true, false); dmdmp35.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp265 = new DateDoubleMetricProcessor("sync methods", "CountDownLatch",false, true, false);
		 * dmdmp265.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp243 = new DateDoubleMetricProcessor("sync methods",
		 * "CyclicBarrier",false, true, false); dmdmp243.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp45 = new
		 * DateDoubleMetricProcessor("sync blocks", "Executors contructs",false, true, false); dmdmp45.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp261 = new DateDoubleMetricProcessor("sync blocks", "CountDownLatch",false, true, false);
		 * dmdmp261.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp262 = new DateDoubleMetricProcessor("sync blocks",
		 * "CyclicBarrier",false, true, false); dmdmp262.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp260 = new
		 * DateDoubleMetricProcessor("Hashtable", "CountDownLatch",false, true, false); dmdmp260.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp264 = new DateDoubleMetricProcessor("Hashtable", "CyclicBarrier",false, true, false);
		 * dmdmp264.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp266 = new DateDoubleMetricProcessor("Hashtable",
		 * "Atomic variables",false, true, false); dmdmp266.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp267 = new
		 * DateDoubleMetricProcessor("HashMap", "CountDownLatch",false, true, false); dmdmp267.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp268 = new DateDoubleMetricProcessor("HashMap", "CyclicBarrier",false, true, false);
		 * dmdmp268.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp269 = new DateDoubleMetricProcessor("HashMap",
		 * "Atomic variables",false, true, false); dmdmp269.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp227 = new
		 * DateDoubleMetricProcessor("ConcurrentHashMap", "CountDownLatch",false, true, false); dmdmp227.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp228 = new DateDoubleMetricProcessor("ConcurrentHashMap", "CyclicBarrier",false, true, false);
		 * dmdmp228.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp229 = new DateDoubleMetricProcessor("ConcurrentHashMap",
		 * "Atomic variables",false, true, false); dmdmp229.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp9 = new
		 * DateDoubleMetricProcessor("sync blocks", "Atomic variables",false, true, false); dmdmp9.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp8 = new DateDoubleMetricProcessor("sync methods", "Atomic variables",false, true, false);
		 * dmdmp8.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp15 = new DateDoubleMetricProcessor("Atomic variables",
		 * "Concurrent collections",false, true, false); dmdmp15.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp16 = new
		 * DateDoubleMetricProcessor("sync methods", "Juc.Locks",false, true, false); dmdmp16.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp17 = new DateDoubleMetricProcessor("sync blocks", "Juc.Locks",false, true, false);
		 * dmdmp17.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp6 = new DateDoubleMetricProcessor("sync blocks",
		 * "j.u.c.WITHOUT.imports",false, true, false); dmdmp6.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp7 = new
		 * DateDoubleMetricProcessor("sync methods", "j.u.c.WITHOUT.imports",false, true, false); dmdmp7.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp10 = new DateDoubleMetricProcessor("sync methods", "sync blocks",false, true, false);
		 * dmdmp10.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp11 = new DateDoubleMetricProcessor("Hashtable",
		 * "HashMap",false, true, false); dmdmp11.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp12 = new
		 * DateDoubleMetricProcessor("HashMap", "ConcurrentHashMap",false, true, false); dmdmp12.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp13 = new DateDoubleMetricProcessor("Hashtable", "ConcurrentHashMap",false, true, false);
		 * dmdmp13.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp18 = new DateDoubleMetricProcessor("wait()", "notify()",false,
		 * true, false); dmdmp18.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp19 = new DateDoubleMetricProcessor("wait()",
		 * "notifyAll()",false, true, false); dmdmp19.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp20 = new
		 * DateDoubleMetricProcessor("ReentrantLock", "sync blocks",false, true, false); dmdmp20.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp21 = new DateDoubleMetricProcessor("ReentrantLock", "sync methods",false, true, false);
		 * dmdmp21.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp22 = new DateDoubleMetricProcessor("ReentrantReadWriteLock",
		 * "sync blocks",false, true, false); dmdmp22.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp23 = new
		 * DateDoubleMetricProcessor("ReentrantReadWriteLock", "sync methods",false, true, false); dmdmp23.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp25 = new DateDoubleMetricProcessor("implements Runnable", "Executors contructs",false, true,
		 * false); dmdmp25.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp24 = new DateDoubleMetricProcessor("extends Thread",
		 * "Executors contructs",false, true, false); dmdmp24.process(fileSourceFolder);
		 */

		// para gerar correlacao
		/*
		 * DateDoubleMetricProcessor dmdmp224 = new DateDoubleMetricProcessor("volatile", "sync blocks",false, true);
		 * dmdmp224.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp324 = new DateDoubleMetricProcessor("volatile",
		 * "j.u.c.WITHOUT.imports",false, true); dmdmp324.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp424 = new
		 * DateDoubleMetricProcessor("volatile", "Atomic variables",false, true); dmdmp424.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp3 = new DateDoubleMetricProcessor("notifyAll()","CountDownLatch",false, true);
		 * dmdmp3.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp4 = new DateDoubleMetricProcessor(
		 * "wait()","CountDownLatch",false, true); dmdmp4.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp26 = new
		 * DateDoubleMetricProcessor( "notify()", "CountDownLatch",false, true); dmdmp26.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp726 = new DateDoubleMetricProcessor( "notify()", "notifyAll()",false, true);
		 * dmdmp726.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp643 = new DateDoubleMetricProcessor("Callable",
		 * "Executors contructs",false, true); dmdmp643.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp33 = new
		 * DateDoubleMetricProcessor("CountDownLatch", "CyclicBarrier",false, true); dmdmp33.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp43 = new DateDoubleMetricProcessor("CountDownLatch", "Executors contructs",false, true);
		 * dmdmp43.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp263 = new DateDoubleMetricProcessor("CyclicBarrier",
		 * "Executors contructs",false, true); dmdmp263.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp35 = new
		 * DateDoubleMetricProcessor("sync methods", "Executors contructs",false, true); dmdmp35.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp265 = new DateDoubleMetricProcessor("sync methods", "CountDownLatch",false, true);
		 * dmdmp265.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp243 = new DateDoubleMetricProcessor("sync methods",
		 * "CyclicBarrier",false, true); dmdmp243.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp45 = new
		 * DateDoubleMetricProcessor("sync blocks", "Executors contructs",false, true); dmdmp45.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp261 = new DateDoubleMetricProcessor("sync blocks", "CountDownLatch",false, true);
		 * dmdmp261.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp262 = new DateDoubleMetricProcessor("sync blocks",
		 * "CyclicBarrier",false, true); dmdmp262.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp260 = new
		 * DateDoubleMetricProcessor("Hashtable", "CountDownLatch",false, true); dmdmp260.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp264 = new DateDoubleMetricProcessor("Hashtable", "CyclicBarrier",false, true);
		 * dmdmp264.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp266 = new DateDoubleMetricProcessor("Hashtable",
		 * "Atomic variables",false, true); dmdmp266.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp267 = new
		 * DateDoubleMetricProcessor("HashMap", "CountDownLatch",false, true); dmdmp267.process(fileSourceFolder); DateDoubleMetricProcessor
		 * dmdmp268 = new DateDoubleMetricProcessor("HashMap", "CyclicBarrier",false, true); dmdmp268.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp269 = new DateDoubleMetricProcessor("HashMap", "Atomic variables",false, true);
		 * dmdmp269.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp227 = new DateDoubleMetricProcessor("ConcurrentHashMap",
		 * "CountDownLatch",false, true); dmdmp227.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp228 = new
		 * DateDoubleMetricProcessor("ConcurrentHashMap", "CyclicBarrier",false, true); dmdmp228.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp229 = new DateDoubleMetricProcessor("ConcurrentHashMap", "Atomic variables",false, true);
		 * dmdmp229.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp9 = new DateDoubleMetricProcessor("sync blocks",
		 * "Atomic variables",false, true); dmdmp9.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp8 = new
		 * DateDoubleMetricProcessor("sync methods", "Atomic variables",false, true); dmdmp8.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp15 = new DateDoubleMetricProcessor("Atomic variables", "Concurrent collections",false, true);
		 * dmdmp15.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp16 = new DateDoubleMetricProcessor("sync methods",
		 * "Juc.Locks",false, true); dmdmp16.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp17 = new
		 * DateDoubleMetricProcessor("sync blocks", "Juc.Locks",false, true); dmdmp17.process(fileSourceFolder); DateDoubleMetricProcessor
		 * dmdmp6 = new DateDoubleMetricProcessor("sync blocks", "j.u.c.WITHOUT.imports",false, true); dmdmp6.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp7 = new DateDoubleMetricProcessor("sync methods", "j.u.c.WITHOUT.imports",false, true);
		 * dmdmp7.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp10 = new DateDoubleMetricProcessor("sync methods",
		 * "sync blocks",false, true); dmdmp10.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp11 = new
		 * DateDoubleMetricProcessor("Hashtable", "HashMap",false, true); dmdmp11.process(fileSourceFolder); DateDoubleMetricProcessor
		 * dmdmp12 = new DateDoubleMetricProcessor("HashMap", "ConcurrentHashMap",false, true); dmdmp12.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp13 = new DateDoubleMetricProcessor("Hashtable", "ConcurrentHashMap",false, true);
		 * dmdmp13.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp18 = new DateDoubleMetricProcessor("wait()", "notify()",false,
		 * true); dmdmp18.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp19 = new DateDoubleMetricProcessor("wait()",
		 * "notifyAll()",false, true); dmdmp19.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp20 = new
		 * DateDoubleMetricProcessor("ReentrantLock", "sync blocks",false, true); dmdmp20.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp21 = new DateDoubleMetricProcessor("ReentrantLock", "sync methods",false, true);
		 * dmdmp21.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp22 = new DateDoubleMetricProcessor("ReentrantReadWriteLock",
		 * "sync blocks",false, true); dmdmp22.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp23 = new
		 * DateDoubleMetricProcessor("ReentrantReadWriteLock", "sync methods",false, true); dmdmp23.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp25 = new DateDoubleMetricProcessor("implements Runnable", "Executors contructs",false, true);
		 * dmdmp25.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp24 = new DateDoubleMetricProcessor("extends Thread",
		 * "Executors contructs",false, true); dmdmp24.process(fileSourceFolder);
		 */

		// --

		// DateDoubleMetricProcessor dmdmp2 = new DateDoubleMetricProcessor("blockingQueue", "notify()",false, true);
		// dmdmp2.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp388 = new DateDoubleMetricProcessor("blockingQueue", "notifyAll()",false, true);
		// dmdmp388.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp488 = new DateDoubleMetricProcessor("blockingQueue", "wait()",false, true);
		// dmdmp488.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp2688 = new DateDoubleMetricProcessor("arrayBlockingQueue", "notifyAll()",false, true);
		// dmdmp2688.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp27 = new DateDoubleMetricProcessor("arrayBlockingQueue", "notify()",false, true);
		// dmdmp27.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp28 = new DateDoubleMetricProcessor("arrayBlockingQueue", "wait()",false, true);
		// dmdmp28.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp29 = new DateDoubleMetricProcessor("synchronousQueue", "notifyAll()",false, true);
		// dmdmp29.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp30 = new DateDoubleMetricProcessor("synchronousQueue", "notify()",false, true);
		// dmdmp30.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp31 = new DateDoubleMetricProcessor("synchronousQueue", "wait()",false, true);
		// dmdmp31.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp32 = new DateDoubleMetricProcessor("priorityBlockingQueue", "notifyAll()",false, true);
		// dmdmp32.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp3388 = new DateDoubleMetricProcessor("priorityBlockingQueue", "notify()",false, true);
		// dmdmp3388.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp34 = new DateDoubleMetricProcessor("priorityBlockingQueue", "wait()",false, true);
		// dmdmp34.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp3588 = new DateDoubleMetricProcessor("linkedBlockingQueue", "notifyAll()",false, true);
		// dmdmp3588.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp36 = new DateDoubleMetricProcessor("linkedBlockingQueue", "notify()",false, true);
		// dmdmp36.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp37 = new DateDoubleMetricProcessor("linkedBlockingQueue", "wait()",false, true);
		// dmdmp37.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp38 = new DateDoubleMetricProcessor("linkedBlockingDeque", "notifyAll()",false, true);
		// dmdmp38.process(fileSourceFolder);
		//
		// // DateDoubleMetricProcessor dmdmp39 = new DateDoubleMetricProcessor("linkedBlockingDeque", "notify()",false, true);
		// // dmdmp39.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp40 = new DateDoubleMetricProcessor("linkedBlockingDeque", "wait()",false, true);
		// dmdmp40.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp41 = new DateDoubleMetricProcessor("delayQueue", "notifyAll()",false, true);
		// dmdmp41.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp42 = new DateDoubleMetricProcessor("delayQueue", "notify()",false, true);
		// dmdmp42.process(fileSourceFolder);
		//
		// DateDoubleMetricProcessor dmdmp4377 = new DateDoubleMetricProcessor("delayQueue", "wait()",false, true);
		// dmdmp4377.process(fileSourceFolder);
		// //*/

		/*
		 * //REFAZER NO FINAL DateDoubleMetricProcessor dmdmp14 = new DateDoubleMetricProcessor("wait()", "CountDownLatch",false, true);
		 * dmdmp14.process(fileSourceFolder); // DateDoubleMetricProcessor dmdmp5 = new DateDoubleMetricProcessor("Lines of Code",
		 * "Lines of Code",false, true); // dmdmp5.process(fileSourceFolder);
		 */

		// para gerar evoluï¿œï¿œo software

		/*
		 * DateDoubleMetricProcessor dmdmp6 = new DateDoubleMetricProcessor("Synchronized keyword", "Atomic variables",false, true);
		 * dmdmp6.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp7 = new DateDoubleMetricProcessor("extends Thread",
		 * "executors",false, true); dmdmp7.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp8 = new
		 * DateDoubleMetricProcessor("Synchronized keyword", "Juc.Locks",false, true); dmdmp8.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp9 = new DateDoubleMetricProcessor("Hashtable", "Concurrent collections",false, true);
		 * dmdmp9.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp10 = new DateDoubleMetricProcessor("HashMap",
		 * "Concurrent collections",false, true); dmdmp10.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp12 = new
		 * DateDoubleMetricProcessor("sync methods", "sync blocks",false, true); dmdmp12.process(fileSourceFolder);
		 * DateDoubleMetricProcessor dmdmp13 = new DateDoubleMetricProcessor("Hashtable", "HashMap",false, true);
		 * dmdmp13.process(fileSourceFolder); DateDoubleMetricProcessor dmdmp14 = new DateDoubleMetricProcessor("sync methods",
		 * "sync blocks",false, true); dmdmp14.process(fileSourceFolder);
		 */
		// DateDoubleMetricProcessor dmdmp11 = new DateDoubleMetricProcessor("synchronizedCollection", "Concurrent collections",false,
		// true);
		// dmdmp11.process(fileSourceFolder);

		// para gerar os resultados relativos a mï¿œtrica por 100KLOC

		/*
		 * DateRelationshipMetricProcessor dmdmp12 = new DateRelationshipMetricProcessor("sync methods", "Lines of Code",false,true);
		 * dmdmp12.process(fileSourceFolder); DateRelationshipMetricProcessor dmdmp13 = new DateRelationshipMetricProcessor("sync blocks",
		 * "Lines of Code",false,true); dmdmp13.process(fileSourceFolder); DateRelationshipMetricProcessor dmdmp11 = new
		 * DateRelationshipMetricProcessor("j.u.c", "Lines of Code",false,true); dmdmp11.process(fileSourceFolder);
		 * DateRelationshipMetricProcessor dmdmp8 = new DateRelationshipMetricProcessor("extends Thread", "Lines of Code",false,true);
		 * dmdmp8.process(fileSourceFolder); DateRelationshipMetricProcessor dmdmp9 = new
		 * DateRelationshipMetricProcessor("implements Runnable", "Lines of Code",false,true); dmdmp9.process(fileSourceFolder);
		 * DateRelationshipMetricProcessor dmdmp10 = new DateRelationshipMetricProcessor("extends Runnable", "Lines of Code",false,true);
		 * dmdmp10.process(fileSourceFolder); DateRelationshipMetricProcessor dmdmp7 = new
		 * DateRelationshipMetricProcessor("Synchronized keyword", "Lines of Code",false,true); dmdmp7.process(fileSourceFolder);
		 */
		// termina o cï¿œdigo referente a mï¿œtrica por 100KlOC

		// LOC normal
		// LoCMetricProcessor loc = new LoCMetricProcessor(true);
		// loc.process(fileSourceFolder);

		// LoCMetricProcessor loc2 = new LoCMetricProcessor(false);
		// loc2.process(fileSourceFolder);

		// DateDoubleMetricProcessor2 dmdmp8 = new DateDoubleMetricProcessor2(
		// "Synchronized keyword", "Thread methods",false);
		// dmdmp8.process(fileSourceFolder);

		// DateDoubleMetricProcessor2 dmdmp6 = new DateDoubleMetricProcessor2(
		// "j.u.c", "extends Thread",true);
		// dmdmp6.process(fileSourceFolder);

		// comentei a baixo dia 3/7/11 as 22.30h
		/*
		 * DateDoubleMetricProcessor2 dmdmp6 = new DateDoubleMetricProcessor2( "Atomic variables", "Synchronized keyword",false);
		 * dmdmp6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmp7 = new DateDoubleMetricProcessor2( "extends Thread",
		 * "Synchronized keyword",false); dmdmp7.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmp8 = new
		 * DateDoubleMetricProcessor2( "New Thread", "Synchronized keyword",false); dmdmp8.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmp9 = new DateDoubleMetricProcessor2( "implements Runnable", "extends Thread",false);
		 * dmdmp9.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmp10 = new DateDoubleMetricProcessor2( "implements Runnable",
		 * "Synchronized keyword",false); dmdmp10.process(fileSourceFolder); // A linha abaixo era para dizer a intersessï¿œo geral de
		 * extends thred e cyn DoubleMetricProcessor dmp = new DoubleMetricProcessor(true, "extends Thread", "Synchronized keyword");
		 * dmp.process(fileSourceFolder); // ADT e suas variantes DateDoubleMetricProcessor2 admdmp6 = new DateDoubleMetricProcessor2(
		 * "Atomic variables", "Synchronized keyword",false); admdmp6.process(fileSourceFolder); DateDoubleMetricProcessor2 admdmp7 = new
		 * DateDoubleMetricProcessor2( "Atomic variables", "wait()",false); admdmp7.process(fileSourceFolder); DateDoubleMetricProcessor2
		 * admdmp8 = new DateDoubleMetricProcessor2( "Atomic variables", "notify()",false); admdmp8.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 admdmp9 = new DateDoubleMetricProcessor2( "Atomic variables", "notifyAll()",false);
		 * admdmp9.process(fileSourceFolder); DateDoubleMetricProcessor2 admdmp10 = new DateDoubleMetricProcessor2( "Atomic variables",
		 * "Concurrent collections",false); admdmp10.process(fileSourceFolder); DateDoubleMetricProcessor2 admdmp11 = new
		 * DateDoubleMetricProcessor2( "Atomic variables", "Juc.Locks",false); admdmp11.process(fileSourceFolder); // ConcurrentCollections
		 * e suas variantes DateDoubleMetricProcessor2 dmdmpt6 = new DateDoubleMetricProcessor2( "Concurrent collections",
		 * "Synchronized keyword",false); dmdmpt6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmpt7 = new
		 * DateDoubleMetricProcessor2( "Concurrent collections", "wait()",false); dmdmpt7.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmpt8 = new DateDoubleMetricProcessor2( "Concurrent collections", "notify()",false);
		 * dmdmpt8.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmpt9 = new DateDoubleMetricProcessor2( "Concurrent collections",
		 * "notifyAll()",false); dmdmpt9.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmpt10 = new DateDoubleMetricProcessor2(
		 * "Concurrent collections", "Atomic variables",false); dmdmpt10.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmpt11 =
		 * new DateDoubleMetricProcessor2( "Concurrent collections", "Juc.Locks",false); dmdmpt11.process(fileSourceFolder); // sync e suas
		 * variantes DateDoubleMetricProcessor2 dmpf6 = new DateDoubleMetricProcessor2( "Synchronized keyword", "Concurrent collections",
		 * false); dmpf6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspf6 = new DateDoubleMetricProcessor2(
		 * "Synchronized keyword", "Juc.Locks", false); dmdmwspf6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspf7 = new
		 * DateDoubleMetricProcessor2( "Synchronized keyword", "Atomic variables", false); dmdmwspf7.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmwspf8 = new DateDoubleMetricProcessor2( "Synchronized keyword", "wait()", false);
		 * dmdmwspf8.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspf9 = new DateDoubleMetricProcessor2(
		 * "Synchronized keyword", "notify()", false); dmdmwspf9.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspf10 = new
		 * DateDoubleMetricProcessor2( "Synchronized keyword", "notifyAll", false); dmdmwspf10.process(fileSourceFolder); //wait
		 * DateDoubleMetricProcessor2 dmpfq6 = new DateDoubleMetricProcessor2( "wait()", "Concurrent collections", false);
		 * dmpfq6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfq6 = new DateDoubleMetricProcessor2( "wait()", "Juc.Locks",
		 * false); dmdmwspfq6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfq7 = new DateDoubleMetricProcessor2( "wait()",
		 * "Atomic variables", false); dmdmwspfq7.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfq8 = new
		 * DateDoubleMetricProcessor2( "wait()", "Synchronized keyword", false); dmdmwspfq8.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmwspfq9 = new DateDoubleMetricProcessor2( "wait()", "notify()", false);
		 * dmdmwspfq9.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfq10 = new DateDoubleMetricProcessor2( "wait()",
		 * "notifyAll", false); dmdmwspfq10.process(fileSourceFolder); // // notify DateDoubleMetricProcessor2 dmpfqw6 = new
		 * DateDoubleMetricProcessor2( "notify()", "Concurrent collections", false); dmpfqw6.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmwspfqw6 = new DateDoubleMetricProcessor2( "notify()", "Juc.Locks", false);
		 * dmdmwspfqw6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfqw7 = new DateDoubleMetricProcessor2( "notify()",
		 * "Atomic variables", false); dmdmwspfqw7.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfqw8 = new
		 * DateDoubleMetricProcessor2( "notify()", "Synchronized keyword", false); dmdmwspfqw8.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmwspfqw9 = new DateDoubleMetricProcessor2( "notify()", "wait()", false);
		 * dmdmwspfqw9.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfqw10 = new DateDoubleMetricProcessor2( "notify()",
		 * "notifyAll", false); dmdmwspfqw10.process(fileSourceFolder); // // notifyall DateDoubleMetricProcessor2 dmpfqwr6 = new
		 * DateDoubleMetricProcessor2( "notifyAll()", "Concurrent collections", false); dmpfqwr6.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmwspfqwr6 = new DateDoubleMetricProcessor2( "notifyAll()", "Juc.Locks", false);
		 * dmdmwspfqwr6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfqwr7 = new DateDoubleMetricProcessor2( "notifyAll()",
		 * "Atomic variables", false); dmdmwspfqwr7.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfqwr8 = new
		 * DateDoubleMetricProcessor2( "notifyAll()", "Synchronized keyword", false); dmdmwspfqwr8.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmwspfqwr9 = new DateDoubleMetricProcessor2( "notifyAll()", "wait()", false);
		 * dmdmwspfqwr9.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwspfqwr10 = new DateDoubleMetricProcessor2( "notifyAll()",
		 * "notify", false); dmdmwspfqwr10.process(fileSourceFolder); // // JUC LOCK E SUAS VARIANTES DateDoubleMetricProcessor2 dmp6 = new
		 * DateDoubleMetricProcessor2( "Juc.Locks", "Concurrent collections", false); dmp6.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmwsp6 = new DateDoubleMetricProcessor2( "Juc.Locks", "Synchronized keyword", false);
		 * dmdmwsp6.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwsp7 = new DateDoubleMetricProcessor2( "Juc.Locks",
		 * "Atomic variables", false); dmdmwsp7.process(fileSourceFolder); DateDoubleMetricProcessor2 dmdmwsp8 = new
		 * DateDoubleMetricProcessor2( "Juc.Locks", "wait()", false); dmdmwsp8.process(fileSourceFolder); DateDoubleMetricProcessor2
		 * dmdmwsp9 = new DateDoubleMetricProcessor2( "Juc.Locks", "notify()", false); dmdmwsp9.process(fileSourceFolder);
		 * DateDoubleMetricProcessor2 dmdmwsp10 = new DateDoubleMetricProcessor2( "Juc.Locks", "notifyAll", false);
		 * dmdmwsp10.process(fileSourceFolder);
		 */

	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric)
			throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric);
			smp.process(fileSourceFolder);
		}
	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			boolean proporcionalLoc) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, proporcionalLoc);
			smp.process(fileSourceFolder);
		}
	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			String year) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, year);
			smp.process(fileSourceFolder);
		}
	}

	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			String year, Boolean proporcionalLoc) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, year, proporcionalLoc);
			smp.process(fileSourceFolder);
		}
	}

	private static void getProjectsFirstVersionMetric(String metricName, boolean concurrentProjects, boolean printProjectName, String releasedDate,
			boolean proporcionalLoc) throws IOException {

		SingleMetricProcessor smp = new SingleMetricProcessor(metricName, concurrentProjects, printProjectName, releasedDate, proporcionalLoc);
		smp.process(fileSourceFolder);

	}

	private static void generateSingleMetricsEvolution(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			boolean evolution, String year, Boolean proporcionalLoc) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, evolution, year,
					proporcionalLoc);
			smp.process(fileSourceFolder);
		}
	}

	/**
	 * Metodo utilizado para coletar todas as mÃ©tricas de um sÃ³ projeto mas lembre-se de passar o caminho apenas do projeto.
	 */
	private static void generateSingleMetrics(ArrayList<String> metricsNames, boolean hasThreshold, boolean printProjectName, boolean hasMetric,
			String year, Boolean proporcionalLoc, Boolean onlyOneProject) throws IOException {

		for (int i = 0; i < metricsNames.size(); i++) {
			SingleMetricProcessor smp = new SingleMetricProcessor(metricsNames.get(i), hasThreshold, printProjectName, hasMetric, year, proporcionalLoc,
					onlyOneProject);
			smp.process(fileSourceFolder);
		}
	}

	private static void fillProjectsCategory(String sourceFolder) throws IOException {
		File fileFolder = new File(sourceFolder);
		File[] listFiles = fileFolder.listFiles();
		String extention = ".txt";

		for (int i = 0; i < listFiles.length; i++) {
			ArrayList<String> projects = new ArrayList<String>();
			categoriesName.add(listFiles[i].getName().substring(0, listFiles[i].getName().length() - extention.length()));
			BufferedReader in = new BufferedReader(new FileReader(listFiles[i]));
			String str;
			while ((str = in.readLine()) != null) {
				projects.add(str);
			}
			projectsByCategory.add(projects);
		}
	}

	private static void compareProjectsCategory(String projectsCategoriesFolder, String projectsCSVfile) throws IOException {
		System.out.println("Comecou:");
		File fileFolder = new File(projectsCategoriesFolder);
		File[] listFiles = fileFolder.listFiles();

		File projectsCSVfileFolder = new File(projectsCSVfile);
		File[] listFilesCSVprojects = projectsCSVfileFolder.listFiles();

		for (int i = 0; i < listFiles.length; i++) {
			ArrayList<String> projects = new ArrayList<String>();
			BufferedReader in = new BufferedReader(new FileReader(listFiles[i]));
			String str;
			int t = 0;
			int contadorProjetos = 0, contador = 0;
			while ((str = in.readLine()) != null) {
				BufferedReader inProj = new BufferedReader(new FileReader(listFilesCSVprojects[0]));
				String proj = "";
				int s = 0;
				while ((proj = inProj.readLine()) != null) {
					String[] splitProjectsName = proj.split(",");
					if (splitProjectsName[0].startsWith(str)) {
						contadorProjetos++;
					}
				}
				// t++;
				// System.out.println("valor S: " + s);
				inProj.close();
				// System.out.println("era para ser o tamanho do arquivo csv: " + s);
				// contador++;

			}
			System.out.println(listFiles[i].getName() + " : " + contadorProjetos);
			// System.out.println("valor de t: " + t);
			in.close();

		}

		System.out.println("Terminou");
	}

	private static ArrayList<String> getMetricNames(File file) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));
		String str;
		ArrayList<String> names = new ArrayList<String>();
		while ((str = in.readLine()) != null) {
			String[] nameS = str.split(":");
			names.add(nameS[0].trim());
		}
		return names;
	}

}
