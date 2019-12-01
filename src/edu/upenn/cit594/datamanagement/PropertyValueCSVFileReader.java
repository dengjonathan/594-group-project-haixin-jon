package edu.upenn.cit594.datamanagement;

import java.util.*;
import java.io.*;

public class PropertyValueCSVFileReader {
    protected String fileName;
    
    public PropertyValueCSVFileReader(String name) {
    	fileName = name;
    }
  
    public Map<String, Double> buildMarketValueEachZipMap() throws FileNotFoundException, IOException {
    	Map<String, Double> totalMarketValueEachZip = new HashMap<String, Double>();

    	Scanner in = null;
    	String line = "";
    	
    	try {
    		File file = new File(fileName);
    		assert(file.exists());
    		in = new Scanner(file);
    		in.nextLine(); // consume the top row
    		while (in.hasNextLine()) {
    			line = in.nextLine();
    			String[] info = line.split(",");
			//    System.out.println("hello");

    			String marketValue = info[34];
    		//	    System.out.println(marketValue);

    			String zip;
    			double marketValueNum;
    			try {
    			    zip = info[72].substring(0, 5);
    			//    System.out.println(zip);
        			marketValueNum = Double.parseDouble(marketValue);

    			}		
	            catch (Exception e) {
    				// skip the invalid data line
    				continue;
    			}

    			if (totalMarketValueEachZip.get(zip) == null) {
    				totalMarketValueEachZip.put(zip, marketValueNum);
    			}
    			else {
    			//	   System.out.println(zip);

    				totalMarketValueEachZip.put(zip, totalMarketValueEachZip.get(zip) + marketValueNum);
    			}
    		}
    	}
    	catch (Exception e) {
    		throw new IllegalStateException(e);
    	}
    	finally {
    		if(in != null) {
    			in.close();
    		}
    	}

        return totalMarketValueEachZip;
    }
    
    public Map<String, Integer> buildNumOfResidenceEachZipMap() throws FileNotFoundException, IOException {
    	Map<String, Integer> numOfResidenceEachZip = new HashMap<String, Integer>();

    	Scanner in = null;
    	String line = "";
    	
    	try {
    		File file = new File(fileName);
    		assert(file.exists());
    		in = new Scanner(file);
    		in.nextLine(); // consume the top row
    		while (in.hasNextLine()) {
    			line = in.nextLine();
    			String[] info = line.split(",");
    			String zip;
    			try {
    			    zip = info[72].substring(0, 5);
        //				   System.out.println(zip);
    			}
                catch (Exception e) {
                	// skip the invalid data line
                	continue;
                }
    			
    			if (numOfResidenceEachZip.get(zip) == null) {
    				numOfResidenceEachZip.put(zip, 1);
    			}
    			else {
    				numOfResidenceEachZip.put(zip, numOfResidenceEachZip.get(zip) + 1);
    			}
    		}
    	}
    	catch (Exception e) {
    		throw new IllegalStateException(e);
    	}
    	finally {
    		if(in != null) {
    			in.close();
    		}
    	}
        return numOfResidenceEachZip;
    }
    
    public Map<String, Double> buildLivableAreaEachZipMap() throws FileNotFoundException, IOException {
    	Map<String, Double> totalLivableAreaEachZip = new HashMap<String, Double>();//double check if int

    	Scanner in = null;
    	String line = "";
    	
    	try {
    		File file = new File(fileName);
    		assert(file.exists());
    		in = new Scanner(file);
    		in.nextLine(); // consume the top row
    		while (in.hasNextLine()) {
    			line = in.nextLine();
    			String[] info = line.split(",");
    			String livableArea = info[64];
    			String zip;
    			double livableAreaNum;
    			try {
    			    zip = info[72].substring(0, 5);
    			    livableAreaNum = Double.parseDouble(livableArea);
    			}
    			catch (Exception e) {
    				// skip the invalid data line
    				continue;
    			}
	
    			if (totalLivableAreaEachZip.get(zip) == null) {
    				totalLivableAreaEachZip.put(zip, livableAreaNum);
    			}
    			else {
    				totalLivableAreaEachZip.put(zip, totalLivableAreaEachZip.get(zip) + livableAreaNum);
    			}
    		}
    	}
    	catch (Exception e) {
    		throw new IllegalStateException(e);
    	}
    	finally {
    		if(in != null) {
    			in.close();
    		}
    	}
        return totalLivableAreaEachZip;
    }
}
