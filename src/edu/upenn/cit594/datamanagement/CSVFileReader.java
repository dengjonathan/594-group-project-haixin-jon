package edu.upenn.cit594.datamanagement;

import java.util.*;
import org.json.simple.parser.ParseException;
import java.io.*;

public class CSVFileReader extends Conversion implements Reader<String, String>
{
    protected String fileName;
    
    public CSVFileReader(String name) {
    	fileName = name;
    }
    
    public Map<String, Double> buildAMap() throws FileNotFoundException, IOException, ParseException {
    	Map<String, Double> totalFineEachZip = new HashMap<String, Double>();
    	Scanner in = null;
    	String line = "";
    	
    	try {
    		File file = new File(fileName);
    		assert(file.exists());
    		in = new Scanner(file);
    		while (in.hasNextLine()) {
    			line = in.nextLine();
    			String[] info = line.split(",");
    			String zip = info[6];
    			double aFine = convertStringToDouble(info[1]);
	
    			if (totalFineEachZip.get(zip) == null) {
    				totalFineEachZip.put(zip, aFine);
    			}
    			else {
    				totalFineEachZip.put(zip, totalFineEachZip.get(zip) + aFine);
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
        return totalFineEachZip;
    }
}
