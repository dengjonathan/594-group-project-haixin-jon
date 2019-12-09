package edu.upenn.cit594.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import edu.upenn.cit594.data.Violation;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;

//This class is responsible ONLY for processing data
public class Processor {
	protected TextFileReader populationData;
    protected PropertyValueCSVFileReader propertyData;
	protected Map<String, Double> totalMarketValueEachZip;
	protected Map<String, Integer> numOfResidenceEachZip;
	protected Map<String, Double> totalLivableAreaEachZip;
	protected Map<String, Double> populationEachZip;
	protected List<Violation> violations;
	protected Map<String, List<Violation>> violationsByZip;
	protected List<String> violationsPerCapitaOutput;

	public Processor(String violationFilename, String propertyValueFileName, String populationFileName) throws Exception {
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
    	this.violationsByZip = partitionViolationsByZip(filterViolationsForPAPlate());

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
		List<String> lines = new ArrayList<>();
		Map<String, Double> totalFinesPerCapitaByZip = totalFinesPerCapitaByZip();
		Iterator<String> it = totalFinesPerCapitaByZip.keySet().iterator();
		while(it.hasNext()) {
			String zip = it.next();
			double perCapita = totalFinesPerCapitaByZip.get(zip);
			lines.add(formatTotalFinesOutput(zip, perCapita));
		}
		// print in ascending order by zip
		Collections.sort(lines);
		return lines;
	}

	private String formatTotalFinesOutput(String zip, double finesPerCapita) {
		return zip + " " + String.format("%.4f",finesPerCapita);
	}

	private Map<String, Double> totalFinesPerCapitaByZip() {
		Map<String, Double> result = new HashMap<>();
		Iterator<String> it = populationEachZip.keySet().iterator();
		List<String> lines = new ArrayList<>();
		while (it.hasNext()) {
			String zip = it.next();
			List<Violation> violations = violationsByZip.get(zip);
			if (violations == null) {
				continue;
			}
			int sum = 0;
			for (Violation violation: violations) {
				sum += violation.getFine();
			}
			Double perCapita = sum / populationEachZip.get(zip);
			result.put(zip, perCapita);
		}
		return result;
	}

	/**
	 * Uses List Data Structure to filter out non-PA plates
	 * @return
	 */
	private List<Violation> filterViolationsForPAPlate() {
		List<Violation> result = new ArrayList<>();
		for (Violation violation: violations) {
			// check is that violation has a PA plate
			if (violation.getStateOfPlate().toLowerCase().equals("pa")) {
				result.add(violation);
			}
		}
		return result;
	}

	/**
	 * Creates key-value map by ZipCode -> List<Violation> to support operations by ZipCode
	 * @param violations
	 * @return Map of ZipCode to List<Violation> for violations that occurred in that zip code
	 */
	private Map<String, List<Violation>> partitionViolationsByZip(List<Violation> violations) {
		Map<String, List<Violation>> violationsByZip = new HashMap<>();
		for (Violation violation: violations) {
			List<Violation> violationsForZip = violationsByZip.get(violation.getZip());
			if (violationsForZip == null) {
				violationsByZip.put(violation.getZip(), new ArrayList<>());
				violationsForZip = violationsByZip.get(violation.getZip());
			}
			violationsForZip.add(violation);
		}
		return violationsByZip;
	}

	/**
	 * @param threshold allows user to define a threshold for average market value of housing in a single zip
	 * @return List of lines describing totalFinesPerCapital ONLY for zip codes which an average market value of
	 * house greater than the threshold value
	 */
	public List<String> totalFinesPerCapitaThreshold(double threshold) {
		if (violationsPerCapitaOutput != null) {
			return violationsPerCapitaOutput;
		}
		List<String> lines = new ArrayList<>();
		Map<String, Double> totalFinesPerCapitaByZip = totalFinesPerCapitaByZip();
		Iterator<String> it = totalFinesPerCapitaByZip.keySet().iterator();
		while(it.hasNext()) {
			String zip = it.next();
			// filter out values that don't meet threshold
			try {
				double averageValueInZip = averageMarketValueInOneZip(zip);
				if (averageValueInZip <= threshold) {
					continue;
				}
			} catch (Exception e) {
				// if zip value not found continue
				continue;
			}
			double perCapita = totalFinesPerCapitaByZip.get(zip);
			lines.add(formatTotalFinesOutput(zip, perCapita));
		}
		// print in ascending order by zip
		Collections.sort(lines);
		// memoize the output
		violationsPerCapitaOutput = lines;
		return lines;
	}
}

   
   
   
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    