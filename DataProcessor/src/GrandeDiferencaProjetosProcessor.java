import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GrandeDiferencaProjetosProcessor extends DoubleMetricProcessor {


	
	ArrayList<Integer> recentDates = new ArrayList<Integer>();
	ArrayList<Integer> middleDates = new ArrayList<Integer>();
	ArrayList<Integer> oldDates = new ArrayList<Integer>();
	
	ArrayList<Double> recentMetric1 = new ArrayList<Double>();
	ArrayList<Double> recentMetric2 = new ArrayList<Double>();
	
	ArrayList<Double> oldMetric1 = new ArrayList<Double>();
	ArrayList<Double> oldMetric2 = new ArrayList<Double>();
	
	ArrayList<Double> middleMetric1 = new ArrayList<Double>();
	ArrayList<Double> middleMetric2 = new ArrayList<Double>();
	
	
	
	private GroupMetric group;
	
	public static enum GroupMetric{
		RECENT, MID, OLD
	}
	
	public GrandeDiferencaProjetosProcessor(String metric1, String metric2,
			boolean hasTreshold) {
		super(metric1, metric2, hasTreshold);
		//initializeDates();

	}

	public GrandeDiferencaProjetosProcessor(String metric1, String metric2,
			boolean hasTreshold, boolean printProjectName) {
		super(metric1, metric2, hasTreshold, printProjectName);
		//initializeDates();

	}
	
	public double valorMetrica(BufferedReader versao){
		String str;
		int valorMetrica=0;
		int valorLOC = 0;
		try {
			while ((str = versao.readLine()) != null) {
				String[] splitMetrics = str.split(":");
				if(splitMetrics[0].trim().startsWith(getMetricsNames().get(0))){
					valorMetrica = Integer.parseInt(splitMetrics[1].trim());
				}else
					//linha de código
					if(splitMetrics[0].trim().startsWith(getMetricsNames().get(1))){
					valorLOC = Integer.parseInt(splitMetrics[1].trim());
				}
			}
			
			
			
			versao.close();
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return (((double)valorMetrica/valorLOC)*100000);
	}
	
	public ArrayList<Double> valorMetricaComLoc(BufferedReader versao){
		String str;
		int valorMetrica=0;
		int valorLOC = 0;
		ArrayList<Double> valorFinal = new ArrayList<Double>();
		try {
			while ((str = versao.readLine()) != null) {
				String[] splitMetrics = str.split(":");
				if(splitMetrics[0].trim().startsWith(getMetricsNames().get(0))){
					valorMetrica = Integer.parseInt(splitMetrics[1].trim());
				}else
					//linha de código
					if(splitMetrics[0].trim().startsWith(getMetricsNames().get(1))){
					valorLOC = Integer.parseInt(splitMetrics[1].trim());
				}
			}
			
			
			
			versao.close();
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		valorFinal.add((double)valorMetrica);
		valorFinal.add((double)valorLOC);
		
		//((double)valorMetrica/valorLOC)*100000);
		return (valorFinal);
	}

	public boolean verificaDiferencaVersoes(File arquivoInicial, File arquivoFinal, int percentual, Double valor1, Double valor2){
		BufferedReader versaoInicial=null;
		BufferedReader versaoFinal=null;
		double valorInicial, valorFinal, diferenca;
		
		try {
			versaoInicial = new BufferedReader(new FileReader(arquivoInicial));
			versaoFinal = new BufferedReader(new FileReader(arquivoFinal));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		valorInicial = this.valorMetrica(versaoInicial);
		valorFinal = this.valorMetrica(versaoFinal);
		diferenca = valorFinal - valorInicial;
		
		valor1 = valorInicial;
		valor2 = valorFinal;
		
		if(valorInicial > 0 || valorFinal > 0){
			if(((percentual*valorInicial)/100)<=diferenca){
				return true;
			}else if(((percentual*valorFinal)/100)>=diferenca){
				return true;
			}
		}
		
		
		return false;
	}

	@Override
	public void readMetrics() throws IOException {

		
		
		for (ArrayList<File> projectVersions : getProjectVersions()) {

			if (projectVersions.size() > 1) {
				
					if (inLimit(1, projectVersions, getMetricsNames().get(0))
							&& (inLimit(1, projectVersions, getMetricsNames().get(1)))) {
						
						int x = 1;
						
						while (x<projectVersions.size()){
							Double test1 = new Double(0);
							Double test2 = new Double(0);
							if(this.verificaDiferencaVersoes(projectVersions.get(x-1), projectVersions.get(x), 50, test1, test2)){
							
								BufferedReader versaoInicial =null;
								BufferedReader versaoFinal = null;
								try {
									
									versaoInicial = new BufferedReader(new FileReader(projectVersions.get(x-1)));
									versaoFinal = new BufferedReader(new FileReader(projectVersions.get(x)));
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								ArrayList<Double> valorInicial = this.valorMetricaComLoc(versaoInicial);
								ArrayList<Double> valorFinal = this.valorMetricaComLoc(versaoFinal);
								//versao 1
								recentMetric1.add(valorInicial.get(0));
								recentMetric1.add(valorInicial.get(1));
								//versao 2
								recentMetric2.add(valorFinal.get(0));
								recentMetric2.add(valorFinal.get(1));
								
								
								String projeto1 = projectVersions.get(x-1).getParentFile().getParentFile().getName()+"/"+projectVersions.get(x-1).getParentFile().getName()+"/"+projectVersions.get(x-1).getName();
								String projeto2 = projectVersions.get(x).getParentFile().getParentFile().getName()+"/"+projectVersions.get(x).getParentFile().getName()+"/"+projectVersions.get(x).getName();
								
								getProjectNames().add(projeto1);
								getProjectNamesSecondVersion().add(projeto2);
								break;
							}
							test1 = null;
							test2 = null;
							x++;
						}

						
						
						}
					}
			}
		}


	@Override
	public void writeMetrics() throws IOException
	{		
		
		getMetricsNumberNew1().addAll(recentMetric1);
		getMetricsNumberNew2().addAll(recentMetric2);
		DoubleMetricProcessorFileManager doubleMetricProcessorFileManager = new DoubleMetricProcessorFileManager(this, true);
		doubleMetricProcessorFileManager.writeMetricsIntoCSV();	
		getMetricsNumberNew1().removeAll(recentMetric1);
		getMetricsNumberNew2().removeAll(recentMetric2);
		
		
	}

	public void setGroup(GroupMetric group) {
		this.group = group;
	}

	public GroupMetric getGroup() {
		return group;
	}
}
