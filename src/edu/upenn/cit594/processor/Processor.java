package edu.upenn.cit594.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import edu.upenn.cit594.data.Violation;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;

//This class is responsible ONLY for processing data
public class Processor {
    protected Reader<String, Double> reader;
	protected TextFileReader populationData;
    protected PropertyValueCSVFileReader propertyData;
	protected Map<String, Double> totalMarketValueEachZip;
	protected Map<String, Integer> numOfResidenceEachZip;
	protected Map<String, Double> totalLivableAreaEachZip;
	protected Map<String, Double> populationEachZip;
	protected List<Violation> violations;

	public Processor(String violationFilename, String propertyValueFileName, String populationFileName) throws Exception {
    	this.reader = reader;
    	this.populationData = new TextFileReader(populationFileName);
    	this.propertyData = new PropertyValueCSVFileReader(propertyValueFileName);
    	this.totalMarketValueEachZip = propertyData.buildMarketValueEachZipMap();
    	this.numOfResidenceEachZip = propertyData.buildNumOfResidenceEachZipMap();
    	this.totalLivableAreaEachZip = propertyData.buildLivableAreaEachZipMap();
    	this.populationEachZip = populationData.buildPopulationZipMap();
    	ViolationFileReader violationReader = violationFilename.indexOf(".json") > -1 ?
				new ViolationJSONFileReader() :
				new ViolationCsvFileReader();
    	this.violations = violationReader.parse(violationFilename);
	}
	
	public int totalPopulationAllZips() throws Exception {
		return (populationData.getTotalPopulation());
	}

	public double averageMarketValueInOneZip(String zip) throws FileNotFoundException, IOException {
		double averageMarketValue;
    	if (totalMarketValueEachZip.containsKey(zip) && numOfResidenceEachZip.containsKey(zip)) {
    		double marketValue = totalMarketValueEachZip.get(zip);
    		int residents = numOfResidenceEachZip.get(zip);
		    averageMarketValue = marketValue / residents;
    	}
    	else {
    	//	for(Map.Entry<String, Integer> e : numOfResidenceEachZip.entrySet()) {
    	//		System.out.printf("    <%s> = %d\n", e.getKey(), e.getValue());
    	//	}
    		throw new RuntimeException("Unknown zip code " + zip); 
    	}
        return averageMarketValue; 
    }
    
    public double averageLivableAreaInOneZip(String zip) throws FileNotFoundException, IOException {
    	 double averageLivableArea;
    	 if (totalLivableAreaEachZip.containsKey(zip) && numOfResidenceEachZip.containsKey(zip)) {
    		double livableArea = totalLivableAreaEachZip.get(zip);
     		int residents = numOfResidenceEachZip.get(zip);
     		averageLivableArea = livableArea/ residents;
    	 }
    	 else {
     		throw new RuntimeException("Unknown zip code " + zip); 
    	 }
    	 return averageLivableArea; 
    }
    
    public double marketValuePerCapitaInOneZip(String zip) throws Exception {
        double marketValuePerCapita = totalMarketValueEachZip.get(zip)/populationEachZip.get(zip);
 	    return marketValuePerCapita; 
    }

    public List<String> totalFinesPerCapita() {
		Iterator<String> it = populationEachZip.keySet().iterator();
		List<String> lines = new ArrayList<>();
		while (it.hasNext()) {
			String zip = it.next();
			List<Violation> violations = findViolationsPerZip(zip);
			int sum = 0;
			for (Violation violation: violations) {
				sum += violation.getFine();
			}
			Double perCapita = sum / populationEachZip.get(zip);
			String line = zip + " " + String.format("%.4f", perCapita);
			lines.add(line);
			System.out.println(line);
		}
		return lines;
	}

	private List<Violation> findViolationsPerZip(String zip) {
		List<Violation> result = new ArrayList<>();
		for (Violation violation: violations) {
			if (violation.getZip() == zip) {
				result.add(violation);
			}
		}
		return result;
	}


    public void writeLog(String msg) {
         Logger.getInstance().log(msg);
    }
}

   
   
   
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    