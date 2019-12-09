package edu.upenn.cit594.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import edu.upenn.cit594.data.Violation;
import edu.upenn.cit594.data.ZipPropertyData;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.ui.NoDataForZipException;

//This class is responsible ONLY for processing data
public class Processor {
	protected TextFileReader populationData;
	protected PropertyValueCSVFileReader propertyData;
	protected Map<String, ZipPropertyData> propertyDataMap;
	protected Map<String, Double> populationEachZip;
	protected List<Violation> violations;

	public Processor(String violationFilename, String propertyValueFileName, String populationFileName)
			throws Exception {
		this.populationData = new TextFileReader(populationFileName);
		this.propertyData = new PropertyValueCSVFileReader(propertyValueFileName);
		this.propertyDataMap = propertyData.buildPropertyDataForEachZipMap();
		this.populationEachZip = populationData.buildPopulationZipMap();
		ViolationFileReader violationReader = violationFilename.indexOf(".json") > -1 ? new ViolationJSONFileReader()
				: new ViolationCsvFileReader();
		this.violations = violationReader.parse(violationFilename);
	}

	public int totalPopulationAllZips() throws Exception {
		return (populationData.getTotalPopulation());
	}

	public int averageMarketValueInOneZip(String zip) throws FileNotFoundException, IOException {
		if (!propertyDataMap.containsKey(zip)) {
			return formatOutputToBeInteger(0);
		}
		
		double averageMarketValue = 0;
		double totalMarketValueForThisZip = propertyDataMap.get(zip).getTotalMarketValueForOneZip();
		int validResidenceNumForThisZip = propertyDataMap.get(zip).getValidResidenceNumForMarketValueForOneZip();
		averageMarketValue = totalMarketValueForThisZip / validResidenceNumForThisZip;
		return formatOutputToBeInteger(averageMarketValue);
	}

	public int averageLivableAreaInOneZip(String zip) throws FileNotFoundException, IOException {
		if (!propertyDataMap.containsKey(zip)) {
			return formatOutputToBeInteger(0);
		}

		double averageLivableArea = 0;
		double totalLivableAreaForThisZip = propertyDataMap.get(zip).getTotalLivableAreaForOneZip();
		int validResidenceNumForThisZip = propertyDataMap.get(zip).getValidResidenceNumForLivableAreaForOneZip();
		averageLivableArea = totalLivableAreaForThisZip / validResidenceNumForThisZip;
		return formatOutputToBeInteger(averageLivableArea);
	}

	public int marketValuePerCapitaInOneZip(String zip) throws Exception {
		if (!propertyDataMap.containsKey(zip) || !populationEachZip.containsKey(zip)) {
			return formatOutputToBeInteger(0);
		}

		double population = populationEachZip.get(zip);
		if (population == 0) {
			return formatOutputToBeInteger(0);
		}

		double marketValue = propertyDataMap.get(zip).getTotalMarketValueForOneZip();
		double marketValuePerCapita = marketValue / population;
		return formatOutputToBeInteger(marketValuePerCapita);
	}

	public List<String> totalFinesPerCapita() {
		List<String> lines = new ArrayList<>();
		Map<String, Double> totalFinesPerCapitaByZip = totalFinesPerCapitaByZip();
		Iterator<String> it = totalFinesPerCapitaByZip.keySet().iterator();
		while (it.hasNext()) {
			String zip = it.next();
			double perCapita = totalFinesPerCapitaByZip.get(zip);
			lines.add(formatTotalFinesOutput(zip, perCapita));
		}
		// print in ascending order by zip
		Collections.sort(lines);
		return lines;
	}
	
	private int formatOutputToBeInteger(double result) {
		//return new DecimalFormat("#").format(result);
		return ((int)result);
	}

	private String formatTotalFinesOutput(String zip, double finesPerCapita) {
		return zip + " " + String.format("%.4f", finesPerCapita);
	}

	private Map<String, Double> totalFinesPerCapitaByZip() {
		Map<String, Double> result = new HashMap<>();
		Iterator<String> it = populationEachZip.keySet().iterator();
		while (it.hasNext()) {
			String zip = it.next();
			List<Violation> violations = findViolationsPerZipWithPA(zip);
			int sum = 0;
			for (Violation violation : violations) {
				sum += violation.getFine();
			}
			Double perCapita = sum / populationEachZip.get(zip);
			result.put(zip, perCapita);
		}
		return result;
	}

	private List<Violation> findViolationsPerZipWithPA(String zip) {
		List<Violation> result = new ArrayList<>();
		for (Violation violation : violations) {
			// check is that violation is within the zip and has a PA plate
			if (violation.getZip().equals(zip) && violation.getStateOfPlate().toLowerCase().equals("pa")) {
				result.add(violation);
			}
		}
		return result;
	}

	/**
	 * @param threshold allows user to define a threshold for average market value
	 *                  of housing in a single zip
	 * @return List of lines describing totalFinesPerCapital ONLY for zip codes
	 *         which an average market value of house greater than the threshold
	 *         value
	 */
	public List<String> totalFinesPerCapitaThreshold(double threshold) {
		List<String> lines = new ArrayList<>();
		Map<String, Double> totalFinesPerCapitaByZip = totalFinesPerCapitaByZip();
		Iterator<String> it = totalFinesPerCapitaByZip.keySet().iterator();
		while (it.hasNext()) {
			String zip = it.next();
			// filter out values that don't meet threshold
			try {
				int averageValueInZip = averageMarketValueInOneZip(zip);
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
		return lines;
	}

	public void writeLog(String msg) {
		Logger.getInstance().log(msg);
	}
}
