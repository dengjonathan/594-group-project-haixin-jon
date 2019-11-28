package edu.upenn.cit594.processor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.CSVFileReader;
import edu.upenn.cit594.logging.Logger;

//This class is responsible ONLY for processing data
public class Processor {
    protected Reader<String, String> reader;
    protected Map<String, String> stateMap;
    
    public Processor(Reader<String, String> reader, String populationFile) throws Exception {
    	this.reader = reader;
    	// TO DO
    }
    
    // TO DO
    
    public void writeLog(String msg) {
         Logger.getInstance().log(msg);
    }
}

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    