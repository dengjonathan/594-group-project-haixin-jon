package edu.upenn.cit594.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.TextFileReader;
import edu.upenn.cit594.datamanagement.CSVFileReader;
import edu.upenn.cit594.datamanagement.PropertyValueCSVFileReader;
import edu.upenn.cit594.logging.Logger;

//This class is responsible ONLY for processing data
public class Processor {
    protected Reader<String, Double> reader;
 //   protected Map<String, Double> populationEachZip;
    protected PropertyValueCSVFileReader propertyData;
	protected TextFileReader populationData;
//	protected Map<String, Double> totalMarketValueEachZip;
//	protected Map<String, Integer> numOfResidenceEachZip;
//	protected Map<String, Double> totalLivableAreaEachZip;

	public Processor(Reader<String, Double> reader, String propertyFile, String populationFileName) throws FileNotFoundException, IOException {
    	this.reader = reader;
    	propertyData = new PropertyValueCSVFileReader(propertyFile);
    	populationData = new TextFileReader(populationFileName);
   // 	this.totalMarketValueEachZip = propertyData.buildMarketValueEachZipMap();
   // 	this.numOfResidenceEachZip = propertyData.buildNumOfResidenceEachZipMap();
   // 	this.totalLivableAreaEachZip = propertyData.buildLivableAreaEachZipMap();
	}
	
	public int totalPopulationAllZips() throws Exception {
		return(populationData.getTotalPopulation());
	}

	public double averageMarketValueInOneZip(String zip) throws FileNotFoundException, IOException {
    	Map<String, Double> totalMarketValueEachZip = propertyData.buildMarketValueEachZipMap();
    	Map<String, Integer> numOfResidenceEachZip = propertyData.buildNumOfResidenceEachZipMap();
		double averageMarketValue;
    	if (totalMarketValueEachZip.containsKey(zip) && numOfResidenceEachZip.containsKey(zip)) {
    		double marketValue = totalMarketValueEachZip.get(zip);
    		int residents = numOfResidenceEachZip.get(zip);
		    averageMarketValue = marketValue / residents;
    	}
    	else {
    		for(Map.Entry<String, Integer> e : numOfResidenceEachZip.entrySet()) {
    			System.out.printf("    <%s> = %d\n", e.getKey(), e.getValue());
    		}
    		throw new RuntimeException("Unknown zip code " + zip); 
    	}
        return averageMarketValue; // TO DO: truncate data?
    }
    
    public double averageLivableAreaInOneZip(String zip) throws FileNotFoundException, IOException {
    	Map<String, Double> totalLivableAreaEachZip = propertyData.buildLivableAreaEachZipMap();
    	Map<String, Integer> numOfResidenceEachZip = propertyData.buildNumOfResidenceEachZipMap();
    	 double averageLivableArea;
    	 if (totalLivableAreaEachZip.containsKey(zip) && numOfResidenceEachZip.containsKey(zip)) {
    		double livableArea = totalLivableAreaEachZip.get(zip);
     		int residents = numOfResidenceEachZip.get(zip);
     		averageLivableArea = livableArea/ residents;
    	 }
    	 else {
     		throw new RuntimeException("Unknown zip code " + zip); 
    	 }
    	 return averageLivableArea; // TO DO: truncate data?
    }
    
    public double marketValuePerCapitaInOneZip(String zip) throws Exception {
    	Map<String, Double> totalMarketValueEachZip = propertyData.buildMarketValueEachZipMap();
 	    Map<String, Double> populationEachZip = populationData.buildPopulationZipMap();
 	   double marketValuePerCapita = totalMarketValueEachZip.get(zip)/populationEachZip.get(zip);
 	   return marketValuePerCapita; // TO DO: truncate data?
    }
    
    public void writeLog(String msg) {
         Logger.getInstance().log(msg);
    }
}

   
   
   
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    